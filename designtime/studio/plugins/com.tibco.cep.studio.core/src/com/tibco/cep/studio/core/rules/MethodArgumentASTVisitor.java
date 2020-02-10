package com.tibco.cep.studio.core.rules;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.rules.text.RulesASTRewriter;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Element;

public class MethodArgumentASTVisitor extends DefaultRulesASTNodeVisitor {

	private FunctionsCatalogLookup fCatalogLookup;
	private IResolutionContextProvider fResolutionContextProvider;
	private String fProjectName;
	private List<EObject> fTargetEntities;
	private RulesSemanticASTVisitor fSemanticVisitor; // convenient way to get NodeTypes
	private List<EObject> fFoundEntities = new ArrayList<EObject>();

	private HashMap<String, Stack<Object>> fAssignmentMap = new HashMap<String, Stack<Object>>();
	private RulesASTRewriter fRewriter;
	private String[] fTargetMethodNames;
	
	public MethodArgumentASTVisitor(String projectName, IResolutionContextProvider provider, List<EObject> targetEntities, String[] targetMethodNames) {
		this.fProjectName = projectName;
		this.fResolutionContextProvider = provider;
		this.fTargetEntities = targetEntities;
		this.fCatalogLookup = new FunctionsCatalogLookup(fProjectName);
		this.fSemanticVisitor = new RulesSemanticASTVisitor(provider, projectName, null);
		this.fTargetMethodNames = targetMethodNames;
		Arrays.sort(fTargetMethodNames);
	}

	@Override
	public boolean visitVariableDeclaratorNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		String name = RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		Stack<Object> stack = fAssignmentMap.get(name);
		RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
		if (stack == null) {
			stack = new Stack<Object>();
			fAssignmentMap.put(name, stack);
		}
		stack.push(expNode);
		
		return true;
	}

	@Override
	public boolean visitPrimaryAssignmentExpressionNode(RulesASTNode node) {
		RulesASTNode opNode = node.getChildByFeatureId(RulesParser.OPERATOR);
		if (opNode.getType() == RulesParser.ASSIGN) {
			// this is an assignment, track it
			RulesASTNode lhsNode = node.getChildByFeatureId(RulesParser.LHS);
			RulesASTNode rhsNode = node.getChildByFeatureId(RulesParser.RHS);
			String lhsName = getText(lhsNode);
			Stack<Object> stack = fAssignmentMap.get(lhsName);
			if (stack == null) {
				// no assignments yet for this name
				stack = new Stack<Object>();
				fAssignmentMap.put(lhsName, stack);
			}
			stack.push(rhsNode);
		}
		return true;
	}

	protected String getText(RulesASTNode node) {
		RulesASTRewriter writer = getRewriter();
		node.accept(writer);
		String name = writer.getText();
		if (name == null) {
			// need to look directly...
			System.out.println("name is null");
		}
		return name;
	}

	private RulesASTRewriter getRewriter() {
		if (fRewriter == null) {
			fRewriter = new RulesASTRewriter();
		}
		fRewriter.clear();
		return fRewriter;
	}

	@Override
	public boolean visitMethodCallNode(RulesASTNode node) {
		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		String methodName = RuleGrammarUtils.getNameAsString(name, RuleGrammarUtils.NAME_FORMAT);
		FunctionRec function = fCatalogLookup.lookupFunction(methodName, null);
		if (function == null) {
			return true; // nothing can be checked...
		}
		if (Arrays.binarySearch(fTargetMethodNames, methodName) < 0) {
			return true;
		}
		NodeType[] argTypes = function.argTypes;
		List<RulesASTNode> args = node.getChildrenByFeatureId(RulesParser.ARGS);
		searchArguments(node, function, argTypes, args);
		return true;
	}

	private void searchArguments(RulesASTNode methodCallNode, FunctionRec function,
			NodeType[] argTypes, List<RulesASTNode> args) {
		if (argTypes.length != args.size()) {
			if (function.isMapper && args.size() == 1) {
				if (function.function instanceof JavaStaticFunctionWithXSLT && ((JavaStaticFunctionWithXSLT) function.function).isXPathFunction()) {
					processXPathFunction(args, function.function);
				} else {
					processMapperFunction(args);
				}
			}
			return;
		}
		if (argTypes.length == 0) {
			return;
		}

		for (int i=0; i<argTypes.length; i++) {
			RulesASTNode argNode = args.get(i);
			NodeType type = fSemanticVisitor.getNodeType(argNode);
			if (type == null) {
				// assume this error was already reported
				continue;
			}
			if (type.isEvent()) {
				String dispName = type.getDisplayName();
				if (dispName == null || dispName.length() == 0) {
					continue;
				}
				if (!RuleGrammarUtils.isGenericType(dispName)) {
					dispName = ModelUtils.convertPackageToPath(dispName);
				}
				EObject entity = findTarget(dispName);
				if (entity != null) {
					processFoundEntity(entity, argNode, type);
					break;
				} else if ("Event".equals(dispName) || "SimpleEvent".equals(dispName)) {
					// use assignments for this variable to determine which events it *could* be
					String argName = getText(argNode);
					Stack<Object> stack = fAssignmentMap.get(argName);
					if (stack != null) {
						processAssignmentStack(stack);
					}
				}
			} else if (type.isString() && processStringLiterals()) {
				String name = getText(argNode);
				if (name != null && name.length() > 0 && name.charAt(0) == '"') {
					// String literal, assume that it is an entity path
					name = name.substring(1, name.length()-1);
					EObject entity = findTarget(name);
					if (entity != null) {
						processFoundEntity(entity, argNode, type);
					}
					continue;
				}
				Stack<Object> stack = fAssignmentMap.get(name);
				if (stack != null) {
					processAssignmentStack(stack);
				}
			}
		}
	}

	private List<VariableDefinition> processScope(ScopeBlock scope,
			List<VariableDefinition> vars, int offset) {
		if (scope == null || scope.getOffset() > offset) {
			return vars;
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getOffset() < offset) {
				vars.add(localVariableDef);
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processScope(scopeBlock, vars, offset);
		}
		return vars;
	}
	
	private String getXsltFromMethodNode(RulesASTNode argNode) {
		RulesASTNode prefixNode = argNode.getChildByFeatureId(RulesParser.PREFIX);
		if (prefixNode == null) {
			return "";
		}
		String text = prefixNode.getText();
		if (text.length() > 0 && text.charAt(0) == '"') {
			text = text.substring(1, text.length()-1);
		}
		return text;
	}

	private void processXPathFunction(List<RulesASTNode> args, Predicate function) {
		RulesASTNode argNode = args.get(0);
		NodeType type = fSemanticVisitor.getNodeType(argNode);
		if (!type.isString()) {
			return;
		}
		try {
			String xsltString = getXsltFromMethodNode(argNode);
			XiNode xpath = null;
			try {
				xpath = XSTemplateSerializer.deSerializeXPathString(xsltString);
				processXPathNode(argNode, type, xpath);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
		}
			
	}

	private void processXPathNode(RulesASTNode argNode, NodeType type, XiNode xpath) {
		if (xpath instanceof Element) {
			Element el = (Element) xpath;
			if ("expr".equals(el.getName().localName)) {
				String formula = el.getStringValue();
				processFormula(argNode, type, formula);
			}
		}
		Iterator children = xpath.getChildren();
		while (children.hasNext()) {
			Object object = (Object) children.next();
			if (object instanceof XiNode) {
				XiNode node = (XiNode) object;
				processXPathNode(argNode, type, node);
			}
		}
	}

	protected void processFoundEntity(EObject entity, RulesASTNode argNode, NodeType type) {
		if (!fFoundEntities.contains(entity)) {
			fFoundEntities.add(entity);
		}
	}

	private void processMapperFunction(List<RulesASTNode> args) {
		// mapper functions only have 1 arg?
		RulesASTNode argNode = args.get(0);
		NodeType type = fSemanticVisitor.getNodeType(argNode);
		if (!type.isString()) {
			return;
		}
		String xslt = "";
		if (argNode.getType() == RulesParser.PRIMARY_EXPRESSION) {
			xslt = argNode.getChildByFeatureId(RulesParser.PREFIX).getText();
		}
		if (xslt == null) {
			return;
		}
		xslt = xslt.substring(1, xslt.length()-1); // trim quotes
		List receivingParams = XSTemplateSerializer.getReceivingParms(xslt);
		if (receivingParams.size() == 0) {
			return;
		}
		String entityPath = (String) receivingParams.get(0);
		EObject entity = findTarget(entityPath);
		if (entity != null) {
			processFoundEntity(entity, argNode, type);
		}
		// find property definition references as well
		TemplateBinding binding = MapperCoreUtils.getBinding(xslt, new ArrayList());
		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding)binding.getParent();
		if (sb == null || sb.getElementInfo() == null) {
			return; // empty mapping
		}
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
		for (int i=0; i<nd.length; i++) {
			nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
		}
		processBindings(argNode, binding, type, entityPath);
	}

	private void processBindings(RulesASTNode argNode, TemplateBinding binding, NodeType type, String outputEntityPath) {

		Binding[] children = binding.getChildren();
		for (Binding childBinding : children) {
			processBinding(argNode, childBinding, type, outputEntityPath);
		}

	}

	private void processBinding(RulesASTNode argNode, Binding binding,
			NodeType type, String outputEntityPath) {
		String formula = binding.getFormula();
		if (formula == null && binding instanceof ElementBinding) {
			ElementBinding elBinding = (ElementBinding) binding;
			ExpandedName name = elBinding.getName();
			String locName = name.localName;
			// locName is the property name
			EObject entity = findTarget(outputEntityPath+"/"+locName);
			if (entity != null) {
				processFoundEntity(entity, argNode, type);
				return;
			}
		} else if (formula != null) {
			processFormula(argNode, type, formula);
		}
		if (binding.getChildCount() > 0) {
			Binding[] children = binding.getChildren();
			for (Binding childBinding : children) {
				processBinding(argNode, childBinding, type, outputEntityPath);
			}
		}
	}

	private void processFormula(RulesASTNode argNode, NodeType type, String formula) {
		try {
			byte[] bytes = formula.getBytes(com.tibco.cep.studio.core.utils.ModelUtils.DEFAULT_ENCODING);
			int start = -1;
			int end = -1;
			boolean foundStart = false;
			for (int i=0; i<bytes.length; i++) {
				byte b = bytes[i];
				if (foundStart) {
					if (b == '/') {
						end = i+1;
						String variable = new String(bytes, start, i-start, Charset.forName(ModelUtils.DEFAULT_ENCODING));
						byte[] barr = new byte[bytes.length - end];
						System.arraycopy(bytes, end, barr, 0, barr.length);
						processVariable(argNode, variable, formula, barr, type);
						foundStart = false;
						start = -1;
					} else if (!Character.isLetterOrDigit(b)) {
						// never found a '/', no properties to be found
					}
				}
				if (b == '$') {
					start = i+1;
					foundStart = true;
				}
			}
			if (foundStart) {
				// never found a '/', no properties to be found
//					String variable = formula.substring(start);
//					processVariable(variable, formula, start, end, binding, resolutionContext, element, edit);
//					foundStart = false;
//					start = -1;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void processVariable(RulesASTNode node, String variable, String formula, byte[] bytes, NodeType nodeType) throws UnsupportedEncodingException {
		ElementReference elementReference = (ElementReference) RuleGrammarUtils.getElementReference(RuleGrammarUtils.getMethodCallNode(node));
		ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
		ElementReference ref = IndexFactory.eINSTANCE.createElementReference();
		ref.setName(variable);
		ref.setOffset(node.getOffset());
		ref.setLength(node.getLength());
		
		Object el = ElementReferenceResolver.resolveElement(ref, fResolutionContextProvider.getResolutionContext(ref, scope));
		if (el == null) {
			return;
		}
		Object type = el;
		if (el instanceof VariableDefinition) {
			type = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) el);
		}

		// get the rest of the xslt to see if this is a reference we care about
		String remainder = new String(bytes, ModelUtils.DEFAULT_ENCODING);
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<remainder.length(); i++) {
			int codePoint = remainder.codePointAt(i);
			if (Character.isLetterOrDigit(codePoint) || codePoint == '/' || codePoint == '@' || codePoint == '_') {
				buf.appendCodePoint(codePoint);
				int charCount = Character.charCount(codePoint);
				i+= charCount-1;
			} else {
				break;
			}
		}
		if (buf.length() == 0) {
			return; // bug?
		}
		if (buf.charAt(0) == '/') {
			buf.deleteCharAt(0);
		}
		String propXslt = buf.toString();
		if (propXslt.length() > 0) {
			int idx = propXslt.indexOf('/');
			if (idx > 0) {
				propXslt = propXslt.substring(0, idx);
			}
			// resolve property against target entity
			if (type instanceof EntityElement) {
				String entityPath =((EntityElement)type).getEntity().getFullPath()+"/"+propXslt;
				EObject entity = findTarget(entityPath);
				if (entity != null) {
					processFoundEntity(entity, node, nodeType);
					return;
				}
			}

		}
	}

	protected EObject findTarget(String entityPath) {
		for (EObject obj : fTargetEntities) {
			if (obj instanceof Entity) {
				Entity entity = (Entity) obj;
				if (entityPath.equals(entity.getFullPath())) {
					return entity;
				}
			} else if (obj instanceof DecisionTableElement) {
				DecisionTableElement dt = (DecisionTableElement) obj;
				if (entityPath.equals(dt.getFolder()+dt.getName())) {
					return dt;
				} else if (entityPath.equals(dt.getName())) {
					return dt;
				}
			}
		}
		return null;
	}

	private void processAssignmentStack(Stack<Object> stack) {
		int size = stack.size();
		for (int i=0; i<size; i++) {
			Object obj = stack.get(i);
			NodeType type = null;
			if (obj instanceof RulesASTNode) {
				RulesASTNode rulesASTNode = (RulesASTNode) obj;
				type = fSemanticVisitor.getNodeType(rulesASTNode);
				if (type == null) {
					// assume this error was already reported
					continue;
				}
				if (!type.isPrimitiveType()) { // we want to keep the primitive types on the stack (i.e. Strings), so we can get the String value
					stack.insertElementAt(type, i);
					stack.remove(i+1);
				}
			} else if (obj instanceof NodeType) {
				type = (NodeType) obj;
			}
			if (type == null) {
				System.out.println("cannot check");
				continue;
			}
			if (type.isEvent()) {
				String dispName = type.getDisplayName();
				if (dispName == null || dispName.length() == 0) {
					continue;
				}
				if (!RuleGrammarUtils.isGenericType(dispName)) {
					dispName = ModelUtils.convertPackageToPath(dispName);
				}
				EObject entity = findTarget(dispName);
				if (entity != null) {
					if (!fFoundEntities.contains(entity)) {
						fFoundEntities.add(entity);
					}
					break;
				}
			} else if (type.isString() && processStringLiterals()) {
				// get the node text, assume it is an entity path
				RulesASTNode node = (RulesASTNode) obj;
				String text = getText(node);
				if (text != null && text.trim().length() > 0) {
					if (text.charAt(0) == '"') {
						text = text.substring(1, text.length()-1);
					}
					EObject entity = findTarget(text);
					if (entity != null) {
						if (!fFoundEntities.contains(entity)) {
							fFoundEntities.add(entity);
						}
						continue;
					}
				}
			}
		}
	}

	protected boolean processStringLiterals() {
		return true;
	}

	public List<EObject> getFoundEntities() {
		return fFoundEntities;
	}

}
