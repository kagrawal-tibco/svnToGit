package com.tibco.cep.studio.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.builder.StudioBuilderProblemHandler;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.core.rules.IResolutionContextProviderExtension;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.MapperQuickFixASTVisitor;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.validation.IQuickFixProvider;
import com.tibco.cep.studio.core.validation.MapperValidationUtils;
import com.tibco.cep.studio.core.validation.ValidationUtils;

/**
 * Find XPath/XSLT functions with fixable errors, apply the quick fix and re-save
 */
public class MapperFunctionMigrator extends FunctionMigrator {

	protected class ResourceResolutionContextProvider implements IResolutionContextProvider, IResolutionContextProviderExtension {
		
		private List<GlobalVariableDef> gvdList;
		private NodeType returnType;

		public ResourceResolutionContextProvider(List<GlobalVariableDef> gvdList, NodeType returnType) {
			this.gvdList = gvdList;
			this.returnType = returnType;
		}

		@Override
		public IResolutionContext getResolutionContext(ElementReference reference,
				ScopeBlock scope) {

			SimpleResolutionContext context = new SimpleResolutionContext(scope);
			for (GlobalVariableDef globalVariableDef : gvdList) {
				context.addGlobalVariable(globalVariableDef);
			}
			return context;
		}
	
		@Override
		public IResolutionContext getResolutionContext(
				ElementReference elementReference) {
			ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
			SimpleResolutionContext context = new SimpleResolutionContext(scope);
			for (GlobalVariableDef globalVariableDef : gvdList) {
				context.addGlobalVariable(globalVariableDef);
			}
			return context;
		}

		@Override
		public NodeType getReturnType() {
			return returnType != null ? returnType : NodeType.VOID;
		}
		
	}

	private boolean updateVersions = false;
	
	public void setUpdateVersions(boolean updateVersions) {
		this.updateVersions = updateVersions;
		initProviders();
	}
	
	@Override
	protected void createReplaceEdits(StringBuilder sb, File file) {
		MultiTextEdit textEdits = createTextEdits(context, file);
		boolean changed = false;
		if (textEdits != null && textEdits.getChildrenSize() > 0) {
			IDocument document = new Document();
			document.set(sb.toString());
			try {
				textEdits.apply(document);
				changed = true;
				sb = new StringBuilder(document.get());
			} catch (MalformedTreeException | BadLocationException e) {
				e.printStackTrace();
			}
		}
		if (changed) {
			writeFile(sb, file);
		}
	}

	public MultiTextEdit createTextEdits(IStudioProjectMigrationContext context, File file) {
		Path filePath = new Path(file.getAbsolutePath());
		if (!IndexUtils.isRuleType(filePath.getFileExtension()) && !IndexUtils.STATEMACHINE_EXTENSION.equalsIgnoreCase(filePath.getFileExtension())) {
			return null;
		}
		String projectName = context.getProjectLocation().getName();
		if (context.getXpathVersion() == XPATH_VERSION.XPATH_10) {
			return null; // no need to migrate
		}
		MultiTextEdit textEdits = null;
		if (IndexUtils.STATEMACHINE_EXTENSION.equals(filePath.getFileExtension())) {
			textEdits = new MultiTextEdit();
			processStateMachine(filePath, context, textEdits);
		} else {
			RulesASTNode tree = (RulesASTNode) RulesParserManager.parseRuleFile(projectName, file, null, true);
			Object data = ((RulesASTNode) tree).getData("element");
			List<GlobalVariableDef> gvdList = new ArrayList<GlobalVariableDef>();
			if (data instanceof RuleElement) {
				RuleElement rule = (RuleElement) data;
				gvdList.addAll(rule.getGlobalVariables());
			}

			ResourceResolutionContextProvider provider = new ResourceResolutionContextProvider(gvdList , NodeType.VOID);

			MapperQuickFixASTVisitor visitor = new MapperQuickFixASTVisitor(provider, projectName, IRulesSourceTypes.FULL_SOURCE);
			tree.accept(visitor);
			textEdits = visitor.getTextEdits();
		}
		return textEdits;
	}
	
	private void processStateMachine(Path filePath,
			IStudioProjectMigrationContext context, MultiTextEdit textEdits) {
		EObject entity = IndexUtils.loadEObject(filePath.toFile().toURI());
//		Entity entity = IndexUtils.getEntity(context.getProjectLocation().getName(), filePath.toOSString());
		
		boolean changed = false;
		if (entity instanceof StateMachine) {
			StateMachine sm = (StateMachine) entity;
			EList<StateEntity> stateEntities = sm.getStateEntities();
			for (StateEntity stateEntity : stateEntities) {
				if (processStateEntity(stateEntity, context.getProjectLocation().getName())) changed = true;
			}
			
			EList<StateTransition> stateTransitions = sm.getStateTransitions();
			for (StateTransition stateTransition : stateTransitions) {
				Rule guardRule = stateTransition.getGuardRule();
				if (processCompilable(guardRule, context.getProjectLocation().getName())) changed = true;
			}
			if (changed) {
				// serialize the entire state machine and have one big text edit
				try {
					byte[] bArr = ModelUtilsCore.getBytesForEObject(sm);
					ReplaceEdit edit = new ReplaceEdit(0, (int) filePath.toFile().length()-2, new String(bArr, ModelUtils.DEFAULT_ENCODING));
					textEdits.addChild(edit);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean processStateEntity(StateEntity se, String projectName) {
		boolean changed = false;
		
		if (se instanceof State){
			// process Rule problems
			Rule entryAction = ((State) se).getEntryAction();
			if (processCompilable(entryAction, projectName)) changed = true;
			
			Rule exitAction = ((State) se).getExitAction();
			if (processCompilable(exitAction, projectName)) changed = true;
			
			Rule timeoutAction = ((State) se).getTimeoutAction();
			if (processCompilable(timeoutAction, projectName)) changed = true;
		} 
		
		return changed;
	}

	protected boolean processCompilable(Compilable compilable, String projectName) {
		boolean changed = false;

		try {
			if (compilable == null) return false;
			String actionText = compilable.getActionText();
			String conditionText = compilable.getConditionText();
//			Collection<Symbol> symbolList = compilable.getSymbols().getSymbolMap().values();
			// create Global Variable definitions for Symbols
			final List<GlobalVariableDef> gvdList = new ArrayList<GlobalVariableDef>();
			for (Symbol symbol : compilable.getSymbols().getSymbolList()){
				String id = symbol.getIdName();
				String type = symbol.getType();
				if (id == null || type  == null || id.trim().length() == 0 || type.trim().length() == 0) continue;
				int index = type.indexOf('/');
				if (index != -1){
					type = type.replace('/', '.');
					if (type.startsWith(".")){
						type = type.substring(1);						
					}
					GlobalVariableDef gvd = ValidationUtils.createGVDefinition(id, type, -1, -1, false);			
					gvdList.add(gvd);			
				}
			}
			StudioBuilderProblemHandler collector = new StudioBuilderProblemHandler();
			RulesASTNode node = null;
			ResourceResolutionContextProvider provider = new ResourceResolutionContextProvider(gvdList, NodeType.VOID);
			if (conditionText != null && conditionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(compilable.getOwnerProjectName(), conditionText, collector);
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);

						MapperQuickFixASTVisitor visitor = new MapperQuickFixASTVisitor(provider, projectName, IRulesSourceTypes.CONDITION_SOURCE);
						node.accept(visitor);
						MultiTextEdit textEdits = visitor.getTextEdits();
						if (textEdits != null && textEdits.getChildrenSize() > 0) {
							IDocument doc = new Document(actionText);
							textEdits.apply(doc);
							compilable.setConditionText(doc.get());
							changed = true;
						}
					}
				}
			} 
			collector = new StudioBuilderProblemHandler();
			if (actionText != null && actionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseActionBlockString(compilable.getOwnerProjectName(), actionText, collector);
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement && true){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
						MapperQuickFixASTVisitor visitor = new MapperQuickFixASTVisitor(provider, projectName, IRulesSourceTypes.ACTION_SOURCE);
						node.accept(visitor);
						MultiTextEdit textEdits = visitor.getTextEdits();
						if (textEdits != null && textEdits.getChildrenSize() > 0) {
							IDocument doc = new Document(actionText);
							textEdits.apply(doc);
							compilable.setActionText(doc.get());
							changed = true;
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return changed;
	}

	@Override
	public void migrateProject(IStudioProjectMigrationContext context,
			IProgressMonitor monitor) {
		initProviders();
		String projName = context.getProjectLocation().getName();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
		if (!project.isAccessible()) {
			System.err.println("Project not accessible");
		}
		if (!project.isSynchronized(IResource.DEPTH_INFINITE)) {
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.migrateProject(context, monitor);
	}

	private void initProviders() {
		IQuickFixProvider[] fixProviders = MapperValidationUtils.getFixProviders();
		if (fixProviders != null && fixProviders.length > 0) {
			for (IQuickFixProvider fixProvider : fixProviders) {
				fixProvider.initializeProvider(updateVersions);
			}
		}
	}
	
	@Override
	public void migrateFunctionCalls(File studioProjDir) {
		initProviders();
		super.migrateFunctionCalls(studioProjDir);
	}


	@Override
	protected boolean isValidEntity(String extension) {
		return IndexUtils.isRuleType(extension) || IndexUtils.STATEMACHINE_EXTENSION.equals(extension);
	}


	@Override
	public int getPriority() {
		return 99; // low priority, do after all others
	}

	public void migrateFunctionCalls(String name, File studioProjDir) {
//		this.projectName = name;
		super.migrateFunctionCalls(studioProjDir);
	}
	
}
