package com.tibco.cep.studio.core.rules;

import java.util.HashMap;
import java.util.List;

import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDTerm;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.mapper.BEMapperCoreInputOutputAdapter;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.core.validation.IQuickFixProvider;
import com.tibco.cep.studio.core.validation.MapperValidationUtils;
import com.tibco.xml.mappermodel.emfapi.EMapperModelInputOutputAdapter;
import com.tibco.xml.mappermodel.emfapi.EMapperUtilities;
import com.tibco.xml.xmodel.DefaultNamespaceResolver;
import com.tibco.xml.xmodel.INamespaceResolver;
import com.tibco.xml.xmodel.IXDataConsts;
import com.tibco.xml.xmodel.XsltHelper;
import com.tibco.xml.xmodel.XsltVersion;
import com.tibco.xml.xmodel.bind.Binding;
import com.tibco.xml.xmodel.bind.FormulaCache;
import com.tibco.xml.xmodel.bind.TemplateBinding;
import com.tibco.xml.xmodel.bind.fix.IOperationCallback;
import com.tibco.xml.xmodel.bind.virt.BindingVirtualizer;
import com.tibco.xml.xmodel.schema.IElement;
import com.tibco.xml.xmodel.schema.IModelGroup;
import com.tibco.xml.xmodel.schema.MyExpandedName;
import com.tibco.xml.xmodel.schema.MyExpandedPath;
import com.tibco.xml.xmodel.xpath.Coercion;
import com.tibco.xml.xmodel.xpath.ErrorMessage;
import com.tibco.xml.xmodel.xpath.expr.Expr;
import com.tibco.xml.xmodel.xpath.func.FunctionResolver;
import com.tibco.xml.xmodel.xpath.type.XType;
import com.tibco.xml.xmodel.xpath.type.XTypeFactory;

/**
 * This class is responsible for traversing a Rule AST
 * and checking the semantics, for instance, whether
 * the arguments are valid for a method call, etc.
 * @author rhollom
 *
 */
public class MapperQuickFixASTVisitor extends RulesSemanticASTVisitor {

	class QuickFixCallback implements IOperationCallback {
		
		private TemplateBinding template;
		private boolean changed;

		public QuickFixCallback(TemplateBinding template) {
			this.template = template;
		}

		@Override
		public void fixReplaceXPathFormula(Binding binding, String xPath) {
			MyExpandedPath path = binding.getPath();
			Binding parent = template;
//			Iterator<MyExpandedName> iterator = path.iterator();
//			while (iterator.hasNext()) {
//				MyExpandedName name = (MyExpandedName) iterator
//						.next();
				parent = findBinding(parent, path, binding);
//			}
			if (parent != null) {
				parent.setFormula(xPath);
				changed = true;
			} else {
				System.out.println("Could not find parent at "+path.toString());
			}
		}
		
		private Binding findBinding(Binding parentBinding, MyExpandedPath path, Binding binding) {
			if (parentBinding.getPath().equals(path) && parentBinding.getName().equals(binding.getName())) {
				if (parentBinding.getFormula() == null) {
					int childCount = parentBinding.getChildCount();
					for (int i = 0; i < childCount; i++) {
						Binding child = parentBinding.getChild(i);
						if (child.getFormula() == binding.getFormula()) {
							return child;
						}
					}
				}
				if (parentBinding.getFormula() == binding.getFormula()) {
					return parentBinding;
				}
			}
			int childCount = parentBinding.getChildCount();
			for (int i = 0; i < childCount; i++) {
				Binding child = findBinding(parentBinding.getChild(i), path, binding);
				if (child != null) {
					return child;
				}
			}
			return null;
		}

		@Override
		public void fixReplaceMarkerBinding(Binding binding) {
		}
		
		@Override
		public void fixReplaceBinding(Binding oldBinding, Binding newBinding) {
		}
		
		@Override
		public void fixRemoveNode(Binding node) {
		}
		
		@Override
		public void fixRemoveCoercion(Coercion coercion) {
		}
		
		@Override
		public void fixRemoveChildNode(Binding parent, Binding child) {
		}
		
		@Override
		public void fixAddOtherwise(Binding binding, Binding otherwise) {
		}
		
		@Override
		public void fixAddChildAt(Binding parent, Binding child, int index) {
		}
		
		@Override
		public void fixAddChild(Binding parent, Binding child) {
		}

		public boolean hasChanges() {
			return changed;
		}
	}
	
	private MultiTextEdit edits;

	public MapperQuickFixASTVisitor(
			IResolutionContextProvider resolutionContextProvider,
			String projectName, int sourceType) {
		super(resolutionContextProvider, projectName, sourceType);
	}

	public MultiTextEdit getTextEdits() {
		if (edits == null) {
			edits = new MultiTextEdit();
		}
		return edits;
	}
	
	@Override
	protected void processXPathValidationResult(ValidationResult result, RulesASTNode node, com.tibco.xml.xmodel.xpath.ExprContext exprContext) {
		ErrorMessage[] errorMessages = result.errList.getErrorMessages();
		Expr expr = result.parser.getExpression();
		boolean changed = false;
        for (int i = 0; i < errorMessages.length; i++) {
        	expr.setTopLevel(); // not sure what this does, but BW does this internally before applying fix
        	ErrorMessage error = errorMessages[i];
        	if (error.getFixer() != null) {
        		// this file has now changed, and needs to be re-saved
        		expr = error.getFixer().applyFixer(expr, exprContext.getNamespaceResolver());
        		changed = true;
        	}
		}
        if (changed) {
        	addReplaceEdit(node, expr.toExactString());
        }
	}

	private void addReplaceEdit(RulesASTNode node, String newXPath) {
		newXPath = BEStringUtilities.escape(newXPath);
		RulesASTNode argsNode = node.getChildByFeatureId(RulesParser.ARGS);
		RulesASTNode xpathNode = argsNode.getChildByFeatureId(RulesParser.PREFIX);
		String text = xpathNode.getText();
		int startIdx = text.indexOf("<expr>") + "<expr>".length();
		int endIdx = text.indexOf("</expr>");
		ReplaceEdit edit = new ReplaceEdit(xpathNode.getOffset()+startIdx, endIdx-startIdx, newXPath);
		getTextEdits().addChild(edit);
	}

	private void addXSLTReplaceEdit(RulesASTNode node, String newXPath) {
		newXPath = BEStringUtilities.escape(newXPath);
		RulesASTNode argsNode = node.getChildByFeatureId(RulesParser.ARGS);
		RulesASTNode xpathNode = argsNode.getChildByFeatureId(RulesParser.PREFIX);
		String text = xpathNode.getText();
		int startIdx = text.indexOf(XSTemplateSerializer.COERCION_SEPARATOR);
		if (startIdx > 0) {
			int endIdx = text.indexOf(XSTemplateSerializer.ARG_END_DELIMITER);
			ReplaceEdit edit = new ReplaceEdit(xpathNode.getOffset()+startIdx, endIdx-startIdx, "");
			getTextEdits().addChild(edit);
		}
		startIdx = text.indexOf("<?xml");
		int endIdx = text.length()-1; // -1 for the trailing '"'
		ReplaceEdit edit = new ReplaceEdit(xpathNode.getOffset()+startIdx, endIdx-startIdx, newXPath);
//		Object data = node.getRoot().getData("element");
//		System.err.println("processing xslt for "+((RuleElement)data).getName());

		getTextEdits().addChild(edit);
	}
	
	public boolean hasChanges() {
		return false;
	}

	protected void validateXPathFunction(Predicate function, RulesASTNode node) {
		try {
			// initialize for xpath function call error checking
			RuleElement element = (RuleElement) getRuleElement(node);
			List<VariableDefinition> variableDefinitions = getVariableDefinitions(element, node);
			String xsltString = getXsltFromMethodNode(node);
			MapperInvocationContext context = new MapperInvocationContext(fProjectName, variableDefinitions, xsltString, function, null, node);
			updateCustomFuncs(context);
//			EMapperModelInputOutputAdapter modelInput = new BEMapperCoreInputOutputAdapter(context, MapperXSDUtils.getSourceElements(context), MapperXSDUtils.getTargetEntity(context));

			IQuickFixProvider[] fixProviders = MapperValidationUtils.getFixProviders();
			if (fixProviders != null && fixProviders.length > 0) {
				for (IQuickFixProvider fixProvider : fixProviders) {
					String newXSLT = fixProvider.findAndApplyMapperFixes(context, StudioCorePlugin.getSchemaCache(fProjectName).getResourceSet(), true);
					if (newXSLT != null) {
						addReplaceEdit(node, newXSLT);
						return;
					}
				}
				return;
			}
			validateXPath2Function(node, function, xsltString, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void validateBW6MapperFunction(RulesASTNode node,
			EMFTnsCache cache, MapperInvocationContext context) {
//		RuleElement element = (RuleElement) getRuleElement(node);
		updateCustomFuncs(context);

		IQuickFixProvider[] fixProviders = MapperValidationUtils.getFixProviders();
		if (fixProviders != null && fixProviders.length > 0) {
			for (IQuickFixProvider fixProvider : fixProviders) {
				String newXSLT = fixProvider.findAndApplyMapperFixes(context, StudioCorePlugin.getSchemaCache(fProjectName).getResourceSet(), false);
				if (newXSLT != null) {
					addXSLTReplaceEdit(node, newXSLT);
					return;
				}
			}
			return;
		}
		EMapperModelInputOutputAdapter modelInput = new BEMapperCoreInputOutputAdapter(context, MapperXSDUtils.getSourceElements(context), MapperXSDUtils.getTargetEntity(context));

		// no quick fix providers could be found (on a non-UI platform, for instance).  Try to apply fixes this way, though it likely won't resolve TIBCO functions in the XSLT
		com.tibco.xml.xmodel.bind.StylesheetBinding sb =modelInput.getXSLT() != null && modelInput.getXSLT().length() > 0 ? XsltHelper.parseStylesheet(modelInput.getXSLT(), false) : new com.tibco.xml.xmodel.bind.StylesheetBinding(null);
		TemplateBinding template = sb.getTemplate(0);
		if (template == null) return;
		TemplateBinding virtBind = (TemplateBinding)BindingVirtualizer.INSTANCE.virtualize(template);

		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(context.getProjectName());
		com.tibco.xml.xmodel.xpath.VariableDefinitionList varList = new com.tibco.xml.xmodel.xpath.VariableDefinitionList();
		XSDElementDeclaration gvarsTerm = (XSDElementDeclaration) MapperXSDUtils.convertGlobalVariables(provider, false);
		if (gvarsTerm != null) {
			XType t = XTypeFactory.createElement(MyExpandedName.makeName(gvarsTerm.getName()), EMapperUtilities.getElementProxy(gvarsTerm).getType());
			com.tibco.xml.xmodel.xpath.VariableDefinition variable = new com.tibco.xml.xmodel.xpath.VariableDefinition(gvarsTerm.getName(), t); 
			varList.add(variable);
		}
		List<VariableDefinition> definitions = context.getDefinitions();
		for (VariableDefinition variableDef : definitions) {
			String name = variableDef.getName();
			XSDTerm xsdTerm = BEMapperCoreInputOutputAdapter.getVariableDefinitionXSDTerm(variableDef);
			XType t = null;
			if (xsdTerm instanceof XSDElementDeclaration) {
				XSDElementDeclaration term = (XSDElementDeclaration) xsdTerm;
				t = XTypeFactory.createElement(MyExpandedName.makeName(name), EMapperUtilities.getElementProxy(term).getType());
			} else if (xsdTerm instanceof XSDModelGroup) {
				t = XTypeFactory.createModelGroup(null, (IModelGroup)EMapperUtilities.getITerm(xsdTerm, false));
				t = XTypeFactory.createDocument(t);
			}
			if (t != null) {
				com.tibco.xml.xmodel.xpath.VariableDefinition variable = new com.tibco.xml.xmodel.xpath.VariableDefinition(name, t); 
				varList.add(variable);
			} else {
				System.out.println("Could not create term");
			}
		}
//		String xpath = null;
//		try {
//			xpath = XSTemplateSerializer.deSerialize(context.getXslt(), new ArrayList(), new ArrayList());
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		final HashMap map = new HashMap();//.getNSPrefixesinXPath(xpath);
//		Iterator itr = map.keySet().iterator();
//		while (itr.hasNext()) {
//			String pfx = (String)itr.next();
//			String uri = (String)map.get(pfx);
////			nsmapper.getOrAddPrefixForNamespaceURI(uri, pfx);
//		}

		com.tibco.xml.xmodel.xpath.ExprContext exprContext = new com.tibco.xml.xmodel.xpath.ExprContext(varList, FunctionResolver.getInstance(XsltVersion.DEFAULT_VERSION),XsltVersion.DEFAULT_VERSION);
		INamespaceResolver resolver = new DefaultNamespaceResolver() {
			public String getNamespaceURIForPrefix(String prefix) {
				if (prefix == null)
					return null;
				
				// note that by allowing xsd and xs to be prefixes for XMLSchema
				// we have assumed that these prefixes will be valid at runtime.
				if (prefix.equals(IXDataConsts.XMLSCHEMA_PREFIX))
					return IXDataConsts.XMLSCHEMA_NAMESPACE;
				return (String) map.get(prefix);
			}
		};

		exprContext = exprContext.createWithNamespaceResolver(resolver);

		com.tibco.xml.xmodel.bind.TemplateReportArguments args = new com.tibco.xml.xmodel.bind.TemplateReportArguments();
		
		XType type = XTypeFactory.createElement((IElement)modelInput.getInternalTargetTerm(), null);
		
		// Set up template report arguments
		args.setCheckForMove(true);
		args.setCheckForRename(true);
		args.setSuggestOptimizations(false);
		
		// Prepare and run report
		exprContext.setQNameResolver(EMapperUtilities.getQNameResolver(StudioCorePlugin.getSchemaCache(fProjectName).getResourceSet()));
		com.tibco.xml.xmodel.bind.TemplateReport report =
			    virtBind.createTemplateReport(null, exprContext, type, type, IXDataConsts.STRICT, new FormulaCache(), args);

		com.tibco.xml.xmodel.bind.fix.BindingFixerChangeList changeList = new com.tibco.xml.xmodel.bind.fix.BindingFixerChangeList(true);
		changeList.setIncludeWarnings(true);
		changeList.setMissingIsWarning(false);
		
		// The changeList extracts errors from the templates and builds objects
		// that potentially know how to fix the errors and puts them in the changeList.
		changeList.run(report);
		QuickFixCallback callback = new QuickFixCallback(template);
		if (changeList.hasAnySelectedChanges()) {
			changeList.applyChanges(callback);
			if (callback.hasChanges()) {
				String newXSLT = sb.serializeWithIndentOption(false);
				addXSLTReplaceEdit(node, newXSLT);
			}
		}
		
	}
	

}
