package com.tibco.cep.studio.core.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.ltk.core.refactoring.FileStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesSemanticASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.search.RuleVariableSearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;

public class LocalVariableRenameProcessor extends EntityRenameProcessor {

	private IDocument document;
	private IStorage storage;
	private RulesASTNode originalNode;

	public LocalVariableRenameProcessor(IRefactoringContext context, IDocument doc, IStorage storage, RulesASTNode originalNode) {
		super(context);
		this.document = doc;
		this.storage = storage;
		this.originalNode = originalNode;
	}

	class LocalReferencesContext extends RefactoringStatusContext {

		private List<EObject> matches;

		public LocalReferencesContext(List<EObject> matches) {
			this.matches = matches;
		}
		
		@Override
		public Object getCorrespondingElement() {
			return matches;
		}
		
	}
	
	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		return new RefactoringParticipant[0];
	}
	
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			OperationCanceledException {
		// need to check for duplicate variable conflict
		Object reference = getfContext().getElement();

		if (reference instanceof VariableDefinition) {
			String newName = getNewName();
			VariableDefinition copy = EcoreUtil.copy((VariableDefinition)reference);
			copy.setName(newName);
			RuleVariableSearchParticipant part = new RuleVariableSearchParticipant();
			EObject eContainer = ((VariableDefinition)reference).eContainer();
			ScopeBlock scope = getVariableScope(eContainer);
			
			RefactoringStatus status = new RefactoringStatus();
			String warningLabel = "'"+newName+"' conflicts with an existing variable name.";
			RefactoringStatusContext refactoringContext;
			RuleElement ruleElement = getRuleElement(scope);
			if (ruleElement != null) {
				EList<GlobalVariableDef> globalVariables = ruleElement.getGlobalVariables();
				for (GlobalVariableDef globalVariableDef : globalVariables) {
					if (globalVariableDef.getName().equals(newName)) {
						if (this.storage instanceof IFile) {
							refactoringContext = new FileStatusContext((IFile) storage, new Region(globalVariableDef.getOffset(), globalVariableDef.getLength()));
						} else {
							refactoringContext = new LocalReferencesContext(null);
						}
						status.addEntry(new RefactoringStatusEntry(RefactoringStatus.ERROR,
								warningLabel, refactoringContext));
						return status;
					}
				}
			}
			List<EObject> foundDefinitions = new ArrayList<EObject>();
			part.search(copy, getfContext().getProjectName(), newName, new NullProgressMonitor(), scope, foundDefinitions);
			if (foundDefinitions.size() > 0) {
				for (EObject eObject : foundDefinitions) {
					if (eObject == copy) {
						continue;
					}
					if (eObject instanceof VariableDefinition) {
						VariableDefinition def = (VariableDefinition) eObject;
						if (this.storage instanceof IFile) {
							refactoringContext = new FileStatusContext((IFile) storage, new Region(def.getOffset(), def.getLength()));
						} else {
							refactoringContext = new LocalReferencesContext(foundDefinitions);
						}
						status.addEntry(new RefactoringStatusEntry(RefactoringStatus.ERROR,
								warningLabel, refactoringContext));
						return status;
					}
				}
			}
		}
		return null;
	}

	private ScopeBlock getVariableScope(EObject eContainer) {
		if (eContainer == null) {
			return null;
		}
		if (eContainer instanceof ScopeBlock) {
			return (ScopeBlock) eContainer;
		} else if (eContainer instanceof RuleElement) {
			return ((RuleElement)eContainer).getScope();
		} 
		return getVariableScope(eContainer.eContainer());
	}
	
	private RuleElement getRuleElement(ScopeBlock scope) {
		while (scope.eContainer() instanceof ScopeBlock) {
			scope = (ScopeBlock) scope.eContainer();
		}
		if (scope.eContainer() instanceof RuleElement) {
			return (RuleElement) scope.eContainer();
		}
		return null;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		System.out.println("initial: "+getNewName());
		return super.checkInitialConditions(pm);
	}
	
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		Object reference = getfContext().getElement();

		if (reference instanceof VariableDefinition) {
			VariableDefinition varDef = (VariableDefinition) reference;
			RuleVariableSearchParticipant part = new RuleVariableSearchParticipant();
			SearchResult search = part.search(varDef, getfContext().getProjectName(), ((VariableDefinition) reference).getName(), new NullProgressMonitor());
			List<EObject> exactMatches = search.getExactMatches();
			DocumentChange change = new DocumentChange("Rename variable", this.document);
			MultiTextEdit edits = new MultiTextEdit();
			for (EObject eObject : exactMatches) {
				if (eObject instanceof VariableDefinition) {
					VariableDefinition variableDef = (VariableDefinition) eObject;
					TextEdit edit = new ReplaceEdit(variableDef.getOffset(), variableDef.getLength(), getNewName());
					edits.addChild(edit);
				} else if (eObject instanceof ElementReference) {
					if (!shouldUpdateReferences()) {
						continue;
					}
					ElementReference ref = (ElementReference) eObject;
					TextEdit edit = new ReplaceEdit(ref.getOffset(), ref.getLength(), getNewName());
					edits.addChild(edit);
				}
			}
			// look for XPath/XSLT variable references
			ScopeBlock variableScope = getVariableScope(varDef.eContainer());
			processVariableScope(variableScope, edits);
			
			change.setEdit(edits);
			return change;
		}	
		return null;
	}

	private void processVariableScope(ScopeBlock variableScope,
			MultiTextEdit edits) {
		EList<ElementReference> refs = variableScope.getRefs();
		for (ElementReference ref : refs) {
			if (ref.isMethod()) {
				if (ref.getBinding() instanceof JavaStaticFunctionWithXSLT) {
					processXSLTMethod(ref, edits);
				} else if (ref.getBinding() == null) {
					// check name
					System.out.println("Null binding");
				}
			}
		}
		EList<ScopeBlock> childScopeDefs = variableScope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processVariableScope(scopeBlock, edits);
		}
	}

	private RulesASTNode getMethodCallNode(RulesASTNode methodNode) {
		if (methodNode != null) {
			// method call node
			if (methodNode.getType() != RulesParser.METHOD_CALL) {
				while (methodNode.getParent() != null) {
					methodNode = (RulesASTNode) methodNode.getParent();
					if (methodNode.getType() == RulesParser.METHOD_CALL) {
						break;
					}
				}
			}
		}
		return methodNode;
	}
	
	private void processXSLTMethod(ElementReference ref, MultiTextEdit edits) {
		RulesASTNode methodNode = getNodeFromReference(ref);
		methodNode = getMethodCallNode(methodNode);
		if (methodNode != null) {
			processArgs(methodNode, edits);
		}
	}

    private void processArgs(RulesASTNode methodNode, MultiTextEdit edits) {
		VariableDefinition varDef = (VariableDefinition) getfContext().getElement();
		String xslt = RulesSemanticASTVisitor.getXsltFromMethodNode(methodNode);
		List varsInExpr = XSTemplateSerializer.searchForVariableNamesinExpression(xslt);
		if (varsInExpr.contains(varDef.getName())) {
			// this xslt/xpath expression uses the variable, replace references
			processRegex(methodNode, edits, xslt, "\\$"+varDef.getName()+"[^\\w]", '$'+getNewName());
			processRegex(methodNode, edits, xslt, "param name=\\\\\""+varDef.getName()+"\\\\", "param name=\\\""+getNewName());
			processRegex(methodNode, edits, xslt, "<variable>"+varDef.getName()+"<", "<variable>"+getNewName());

		}
		
	}

	private void processRegex(RulesASTNode methodNode, MultiTextEdit edits,
			String xslt, String regex, String replTemplate) {
		Pattern compile = Pattern.compile(regex);
		Matcher m = compile.matcher(xslt);
		int startDelta = methodNode.getChildByFeatureId(RulesParser.ARGS).getOffset()+1;
		while (m.find()) {
		    String varname = m.group();
		    int start = m.start()+startDelta;
		    char ch = varname.charAt(varname.length()-1);
		    ReplaceEdit edit = new ReplaceEdit(start, varname.length(), replTemplate+ch);
		    edits.addChild(edit);
		}
	}

	private RulesASTNode getNodeFromReference(ElementReference ref) {
		RulesASTNode rootNode = RuleGrammarUtils.getRootNode(originalNode);
		RulesASTNodeFinder finder = new RulesASTNodeFinder(ref.getOffset());
		rootNode.accept(finder);
		return finder.getFoundNode();
	}
	
}
