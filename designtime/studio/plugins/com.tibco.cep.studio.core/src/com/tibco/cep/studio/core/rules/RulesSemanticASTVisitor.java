package com.tibco.cep.studio.core.rules;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.antlr.runtime.tree.Tree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDTerm;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.parser.CompileErrors;
import com.tibco.be.parser.semantic.BuiltinLookup;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.RuleFunction.Validity;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo.NamespaceDeclaration;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportErrorFormatter;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportExtendedError;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportGenerationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChangeList;
import com.tibco.cep.mapper.xml.xdata.bind.fix.FormulaErrorBindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.functions.annotations.FunctionInfo;
import com.tibco.cep.studio.core.functions.annotations.ParamTypeInfo;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;
import com.tibco.cep.studio.core.index.model.BindingVariableDef;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.AmbiguousReference;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.ResolutionUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.mapper.BEMapperCoreInputOutputAdapter;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.core.util.mapper.XMLReadException;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingDisplayUtils;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorResources;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CodeErrorMessage;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.ErrCheckErrorList;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.mappermodel.emfapi.EMapperModelInputOutputAdapter;
import com.tibco.xml.mappermodel.emfapi.EMapperUtilities;
import com.tibco.xml.mappermodel.emfapi.EXsltError;
import com.tibco.xml.mappermodel.emfapi.EXsltValidator;
import com.tibco.xml.xmodel.DefaultNamespaceResolver;
import com.tibco.xml.xmodel.INamespaceResolver;
import com.tibco.xml.xmodel.IXDataConsts;
import com.tibco.xml.xmodel.XsltVersion;
import com.tibco.xml.xmodel.schema.IModelGroup;
import com.tibco.xml.xmodel.schema.IQNameResolver;
import com.tibco.xml.xmodel.schema.MyExpandedName;
import com.tibco.xml.xmodel.xpath.Coercion;
import com.tibco.xml.xmodel.xpath.ErrorMessage;
import com.tibco.xml.xmodel.xpath.ErrorMessageList;
import com.tibco.xml.xmodel.xpath.EvalTypeInfo;
import com.tibco.xml.xmodel.xpath.Lexer;
import com.tibco.xml.xmodel.xpath.Parser;
import com.tibco.xml.xmodel.xpath.expr.Expr;
import com.tibco.xml.xmodel.xpath.func.FunctionResolver;
import com.tibco.xml.xmodel.xpath.type.TypeChecker2;
import com.tibco.xml.xmodel.xpath.type.XType;
import com.tibco.xml.xmodel.xpath.type.XTypeFactory;

/**
 * This class is responsible for traversing a Rule AST
 * and checking the semantics, for instance, whether
 * the arguments are valid for a method call, etc.
 * @author rhollom
 *
 */
public class RulesSemanticASTVisitor extends DefaultRulesASTNodeVisitor {
	
	private static final NodeType[] fMapperArgs = new NodeType[] { new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false ) };
	
	private static final String[] PRIMITIVE_NAMES = { "int", "long", "double", "boolean", "String", "DateTime", "null", CompileErrors.unknownTypeName(), "void", "Object" };
    private static final String[] RESERVED_KEYWORD_NAMES = { "body", "validity", "null", "lock", "exists", "and", "key", "alias", "rank", "virtual" };
    private static final String[] INVALID_GENERIC_SCOPE_TYPES = { "Integer", "Long", "Double", "Boolean" };
    private static final NodeTypeFlag[] PRIMITIVE_TYPES = { NodeTypeFlag.INT_FLAG, NodeTypeFlag.LONG_FLAG, NodeTypeFlag.DOUBLE_FLAG, NodeTypeFlag.BOOLEAN_FLAG, NodeTypeFlag.STRING_FLAG, NodeTypeFlag.DATETIME_FLAG, NodeTypeFlag.NULL_FLAG, NodeTypeFlag.UNKNOWN_FLAG, NodeTypeFlag.VOID_FLAG, NodeTypeFlag.OBJECT_FLAG };
	
    private static final String XSLT_ERROR_PREFIX	= "XSLT Mapper: ";
    private static final String XPATH_ERROR_PREFIX	= "XPath function: ";
    private static final String VALIDITY_ACTION		= "ACTION";
    private static final String VALIDITY_CONDITION 	= "CONDITION";
    private static final String VALIDITY_QUERY	 	= "QUERY";

	private static final int ADVISORY_FLAG = 0;
	private static final int ADVISORY_FLAG_ARR = 1;
	private static final int STRING_FLAG = 2;
	private static final int STRING_FLAG_ARR = 3;
	private static final int EXCEPTION_FLAG = 4;
	private static final int EXCEPTION_FLAG_ARR = 5;
	private static final int CONCEPT_FLAG = 6;
	private static final int CONCEPT_FLAG_ARR = 7;
	private static final int OBJECT_FLAG = 8;
	private static final int OBJECT_FLAG_ARR = 9;
	private static final int EVENT_FLAG = 10;
	private static final int EVENT_FLAG_ARR = 11;
	private static final int DATETIME_FLAG = 12;
	private static final int DATETIME_FLAG_ARR = 13;
	private static final int BOOLEAN_FLAG = 14;
	private static final int BOOLEAN_FLAG_ARR = 15;
	private static final int DOUBLE_FLAG = 16;
	private static final int DOUBLE_FLAG_ARR = 17;
	private static final int SIMPLE_EVENT_FLAG = 18;
	private static final int SIMPLE_EVENT_FLAG_ARR = 19;
	private static final int GENERIC_CONCEPT_FLAG = 20;
	private static final int GENERIC_CONCEPT_FLAG_ARR = 21;
	private static final int GENERIC_EVENT_FLAG = 22;
	private static final int GENERIC_EVENT_FLAG_ARR = 23;
	private static final int GENERIC_SIMPLE_EVENT_FLAG = 24;
	private static final int GENERIC_SIMPLE_EVENT_FLAG_ARR = 25;
	private static final int GENERIC_ADVISORY_EVENT_FLAG = 26;
	private static final int GENERIC_ADVISORY_EVENT_FLAG_ARR = 27;
	private static final int GENERIC_PROCESS_FLAG = 28;
	private static final int GENERIC_PROCESS_FLAG_ARR = 29;
	private static final int UNKNOWN_FLAG = 30;

	protected static final BigInteger MIN_LONG_MAGNITUDE = BigInteger.valueOf(Long.MIN_VALUE).negate();
	protected static final BigInteger MAX_LONG_MAGNITUDE = BigInteger.valueOf(Long.MAX_VALUE);
	protected static final BigInteger MIN_INT_MAGNITUDE = BigInteger.valueOf(Integer.MIN_VALUE).negate();
	protected static final BigInteger MAX_INT_MAGNITUDE = BigInteger.valueOf(Integer.MAX_VALUE);

	private Validity fValidity = Validity.ACTION;

	protected String fProjectName;
	protected IResolutionContextProvider fResolutionContextProvider;

	private FunctionsCatalogLookup fCatalogLookup;
	private BuiltinLookup fBuiltInLookup = new BuiltinLookup();
	private List<IRulesProblem> fSemanticErrors = new ArrayList<IRulesProblem>();
	private boolean fVirtual = false; // valid for rule functions only
	private int fSectionType;
	private int fValidityType;
	private NodeType fReturnNodeType;
	private HashMap<ScopeBlock, RulesASTNode> fReturnStatementMap = new HashMap<ScopeBlock, RulesASTNode>();
	private boolean fHasReturnStatement = false;
	private IFile fRuleFile = null; // the rule file, so that the rule name can be matched against the file name
	private boolean fInsideMethodCall;
	private boolean fUnreachableCode = false;
	private boolean fUpdatedCustomFuncs = false;
	
	private RulesASTNode fRuleNameNode;

	private ISemanticValidatorCallback fCallback;

	private static NodeType[] fGenericTypes;
	
	static {
		Arrays.sort(RESERVED_KEYWORD_NAMES);
		Arrays.sort(INVALID_GENERIC_SCOPE_TYPES);
		fGenericTypes = new NodeType[UNKNOWN_FLAG+1];
		
		fGenericTypes[ADVISORY_FLAG] = new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, false);
		fGenericTypes[ADVISORY_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, true);
		fGenericTypes[STRING_FLAG] = new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false);
		fGenericTypes[STRING_FLAG_ARR] = new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true);
		fGenericTypes[EXCEPTION_FLAG] = new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Exception");
		fGenericTypes[EXCEPTION_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Exception");
		fGenericTypes[CONCEPT_FLAG] = new NodeType(NodeTypeFlag.CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Concept");
		fGenericTypes[CONCEPT_FLAG_ARR] = new NodeType(NodeTypeFlag.CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Concept");
		fGenericTypes[OBJECT_FLAG] = new NodeType(NodeTypeFlag.OBJECT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Object");
		fGenericTypes[OBJECT_FLAG_ARR] = new NodeType(NodeTypeFlag.OBJECT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Object");
		fGenericTypes[EVENT_FLAG] = new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Event");
		fGenericTypes[EVENT_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Event");
		fGenericTypes[DATETIME_FLAG] = new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "DateTime");
		fGenericTypes[DATETIME_FLAG_ARR] = new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true);
		fGenericTypes[BOOLEAN_FLAG] = new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false);
		fGenericTypes[BOOLEAN_FLAG_ARR] = new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true);
		fGenericTypes[DOUBLE_FLAG] = new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false);
		fGenericTypes[DOUBLE_FLAG_ARR] = new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true);
		fGenericTypes[SIMPLE_EVENT_FLAG] = new NodeType(NodeTypeFlag.SIMPLE_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "SimpleEvent");
		fGenericTypes[SIMPLE_EVENT_FLAG_ARR] = new NodeType(NodeTypeFlag.SIMPLE_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "SimpleEvent");
		fGenericTypes[GENERIC_CONCEPT_FLAG] = new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Concept");
		fGenericTypes[GENERIC_CONCEPT_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Concept");
		fGenericTypes[GENERIC_EVENT_FLAG] = new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Event");
		fGenericTypes[GENERIC_EVENT_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Event");
		fGenericTypes[GENERIC_SIMPLE_EVENT_FLAG] = new NodeType(NodeTypeFlag.GENERIC_SIMPLE_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "SimpleEvent");
		fGenericTypes[GENERIC_SIMPLE_EVENT_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_SIMPLE_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "SimpleEvent");
		fGenericTypes[GENERIC_ADVISORY_EVENT_FLAG] = new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "SimpleEvent");
		fGenericTypes[GENERIC_ADVISORY_EVENT_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "SimpleEvent");
		fGenericTypes[GENERIC_PROCESS_FLAG] = new NodeType(NodeTypeFlag.GENERIC_PROCESS_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, false, "Process");
		fGenericTypes[GENERIC_PROCESS_FLAG_ARR] = new NodeType(NodeTypeFlag.GENERIC_PROCESS_FLAG, TypeContext.PRIMITIVE_CONTEXT, true, true, "Process");
		fGenericTypes[UNKNOWN_FLAG] = NodeType.UNKNOWN;
	}
	
	protected static class ValidationResult {
		ErrorMessageList errList;
		Parser parser;
	}
	
	public RulesSemanticASTVisitor(IResolutionContextProvider resolutionContextProvider, String projectName, IFile ruleFile) {
		this(resolutionContextProvider, projectName, 0);
		this.fRuleFile = ruleFile;
	}
	
	public RulesSemanticASTVisitor(IResolutionContextProvider resolutionContextProvider, String projectName, int sourceType) {
		this(resolutionContextProvider, projectName, sourceType, null);
	}
	
	public RulesSemanticASTVisitor(IResolutionContextProvider resolutionContextProvider, String projectName, int sourceType, ISemanticValidatorCallback callback) {
		this.fProjectName = projectName;
		this.fResolutionContextProvider = resolutionContextProvider;
		this.fCatalogLookup = new FunctionsCatalogLookup(fProjectName);
		this.fSectionType = sourceType;
		this.fCallback = callback;
		initialize();
	}
	
	private void initialize() {
		// wait for function catalog to be built
		FunctionsCatalogManager.waitForStaticRegistryUpdates();
	}

	@Override
	public boolean preVisit(RulesASTNode node) {
		if (fHasReturnStatement) {
			RuleElement element = (RuleElement) getRuleElement(node);
			ScopeBlock scope = element.getScope();
			if (scope != null) {
				ScopeBlock scopeBlock = getScope(scope, node.getOffset());
				RulesASTNode rulesASTNode = fReturnStatementMap.get(scopeBlock);
				if (rulesASTNode != null) {
					reportError(node, "Unreachable code.  There is already a return statement in this scope");
					return false;
				}
			}
		} 
		if (fUnreachableCode) {
			reportError(node, "Unreachable code.");
			fUnreachableCode = false;
			return false;
		}
		return super.preVisit(node);
	}

	@Override
	public boolean postVisit(RulesASTNode o) {
		if (o.getType() == RulesParser.THEN_BLOCK || o.getType() == RulesParser.BODY_BLOCK) {
			if (fReturnNodeType != null && !fHasReturnStatement && !fUnreachableCode) {
				if (!fReturnNodeType.isVoid()) {
					RulesASTNode errNode = fRuleNameNode == null ? o : fRuleNameNode;
					reportError(errNode, "Method must return a value of type \""+fReturnNodeType.getDisplayName()+"\"");
				}
			}
		}
		return super.postVisit(o);
	}

	@Override
	public boolean visitBinaryRelationNode(RulesASTNode node) {
		if (node.getChildCount() != 2) {
			System.out.println("Binary relation not complete");
			return false;
		}
		int opType = node.getType();
		RulesASTNode lhs = node.getChild(0);
		RulesASTNode rhs = node.getChild(1);
		
		NodeType lhsType = getNodeType(lhs);
		NodeType rhsType = node.getType() == RulesParser.INSTANCE_OF ? getNodeType(rhs, false, true) : getNodeType(rhs);
		
		if (!matchBinaryType(node, opType, lhsType, rhsType, rhs)) {
			return false;
		}
		
		if (visit(lhs)) {
			doVisitChildren(lhs);
		}
		if (visit(rhs)) {
			doVisitChildren(rhs);
		}
		
		return false;
	}

	@Override
	public boolean visitUnaryExpressionNode(RulesASTNode node) {
		RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
		RulesASTNode opNode = node.getChildByFeatureId(RulesParser.OPERATOR);
		
		NodeType nodeType = getNodeType(expNode);
		matchBinaryType(node, opNode.getType(), nodeType, null, null);
		return true;
	}
	
	@Override
	public boolean visitEmptyStatement(RulesASTNode node) {
		if (fSectionType == IRulesSourceTypes.CONDITION_SOURCE) {
			reportError(node, "Expecting a boolean in the conditions section");
			return false;
		}
		return true;
	}

	@Override
	public boolean visitMethodCallNode(RulesASTNode node) {
		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		String methodName = RuleGrammarUtils.getNameAsString(name, RuleGrammarUtils.NAME_FORMAT);
		FunctionRec function = null;
		try {
			function = fCatalogLookup.lookupFunction(methodName, null);
		} catch (Throwable e) {
			reportError(node, "Function lookup failed:"+methodName);
			StudioCorePlugin.log("Function lookup failed:"+methodName,e);
			return false;
		}
		if (function == null) {
			Object obj = resolveASTNode(node);
			if (obj != null && !(obj instanceof SharedElement)) {
				reportInvalidMethodCallError(methodName, node);
				return true;
			}
			return true; // nothing can be checked...
		}
		
		if (fSectionType == IRulesSourceTypes.CONDITION_SOURCE && !fInsideMethodCall && !insideBinaryRelation(node)) {
			if (!function.returnType.isBoolean()) {
				reportError(node, "Methods that do not return a boolean cannot be called in the conditions section");
				return false;
			}
		}

		if (fSectionType == IRulesSourceTypes.CONDITION_SOURCE) {
			if (!function.function.isValidInCondition()) {
				reportError(node, "Method \""+methodName+"\" is not valid in the conditions section");
				return false;
			}
		}
		if (fSectionType == IRulesSourceTypes.ACTION_SOURCE) {
			if (!function.function.isValidInAction()) {
				reportError(node, "Method \""+methodName+"\" is not valid in the actions section");
				return false;
			}
		}
		boolean insideNestedMethodCall = fInsideMethodCall;
		fInsideMethodCall = true;
		String type = fValidity.getName();
		if (fValidity == Validity.ACTION && !function.function.isValidInAction()) {
			reportNonAllowedMethodCallError(methodName, node, type);
			return false;
		} else if (fValidity == Validity.CONDITION && !function.function.isValidInCondition()) {
			reportNonAllowedMethodCallError(methodName, node, type);
			return false;
		} else if (fValidity == Validity.QUERY && !function.function.isValidInQuery()) {
			reportNonAllowedMethodCallError(methodName, node, type);
			return false;
		}
		
		NodeType[] argTypes = function.argTypes;
		Class[] arguments = function.function.getArguments();
		List<RulesASTNode> args = node.getChildrenByFeatureId(RulesParser.ARGS);
		matchArguments(node, function, argTypes, args);
		
		if (function.function instanceof JavaStaticFunctionWithXSLT) {
			if (((JavaStaticFunctionWithXSLT)function.function).isXsltFunction()) {
				validateMapperFunction(function.function, node);
			} else if (((JavaStaticFunctionWithXSLT)function.function).isXPathFunction()) {
				validateXPathFunction(function.function, node);
			}
		} else if (function.function instanceof JavaStaticFunction) {
			JavaStaticFunction jsf = (JavaStaticFunction) function.function;
			if ("{VRF}invokeVRFImplByName".equals(jsf.getName().castAsString())) {
				validateVRFFunction(function.function, node);
			}
		}

		// call this explicitly so that we know we're within another method call
		doVisitChildren(node);
		if (!insideNestedMethodCall) {
			fInsideMethodCall = false;
		}
		
		return false;
	}
	
	private void validateVRFFunction(Predicate function, RulesASTNode node) {
		List<RulesASTNode> argsNodes = node.getChildrenByFeatureId(RulesParser.ARGS);
		if (argsNodes.size() != 3) {
			// report error?  should already have been reported...
			return;
		}
		
		RulesASTNode dtNode = argsNodes.get(1);
		RulesASTNode prefixNode = dtNode.getChildByFeatureId(RulesParser.PREFIX);
		if (prefixNode.getType() == RulesParser.SIMPLE_NAME) {
			// node is a variable reference, return without validating
			return;
		}
		String text = prefixNode.getText();
		if (text.length() > 0 && text.charAt(0) == '"') {
			text = text.substring(1, text.length()-1);
		}

		List<DesignerElement> allElements = IndexUtils.getAllElements(fProjectName, text);
		for (DesignerElement designerElement : allElements) {
			if (designerElement instanceof DecisionTableElement) {
				// we found an implementation with the proper name, we can return
				return;
			}
		}
		
		// no implementation was found, report error
		reportError(node, "Decision Table \""+text+"\" does not exist.");
	}

	private boolean insideBinaryRelation(RulesASTNode node) {
		while (node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
			switch (node.getType()) {
			case RulesParser.LT:
			case RulesParser.GT:
			case RulesParser.LE:
			case RulesParser.GE:
			case RulesParser.EQ:
			case RulesParser.NE:
			case RulesParser.BINARY_RELATION_NODE:
			case RulesParser.POUND:
			case RulesParser.AND:
			case RulesParser.OR:
				return true;

			default:
				break;
			}
		}
		return false;
	}

	private void doVisitChildren(RulesASTNode node) {
        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RulesASTNode child = node.getChild(i);
            child.accept(this);
        }
	}

	protected void validateXPathFunction(Predicate function, RulesASTNode node) {
		try {
			// initialize for xpath function call error checking
			RuleElement element = (RuleElement) getRuleElement(node);
			List<VariableDefinition> variableDefinitions = getVariableDefinitions(element, node);
			String xsltString = getXsltFromMethodNode(node);
			MapperInvocationContext context = new MapperInvocationContext(fProjectName, variableDefinitions, xsltString, function, null, node);
	        if (TraxSupport.isXPath2DesignTime(fProjectName)) {
	        	validateXPath2Function(node, function, xsltString, context);
	        	return;
	        }	        

			VariableDefinitionList vdl = MapperCoreUtils.makeInputVariableDefinitions(StudioCorePlugin.getCache(context.getProjectName()), context);

			XiNode xpath = null;
			try {
				xpath = XSTemplateSerializer.deSerializeXPathString(context.getXslt());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			HashMap map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
			Iterator itr = map.keySet().iterator();
			NamespaceContextRegistry nsmapper = MapperCoreUtils.getNamespaceMapper();
			while (itr.hasNext()) {
				String pfx = (String)itr.next();
				String uri = (String)map.get(pfx);
				nsmapper.getOrAddPrefixForNamespaceURI(uri, pfx);
			}
			ExprContext ec = new ExprContext(vdl, StudioCorePlugin.getUIAgent(context.getProjectName()).getFunctionResolver()).createWithNamespaceMapper(nsmapper);
			// end initialization
			
			// actual error checking code
			ErrCheckErrorList errors = MapperCoreUtils.getErrors(XSTemplateSerializer.getXPathExpressionAsStringValue(xpath), ec, nsmapper, null, false);
			checkErrorList(errors, node, xpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void validateXPath2Function(RulesASTNode node, Predicate function,
			String xsltString, MapperInvocationContext context) {
		boolean untypedInputMode = false;
		updateCustomFuncs(context);
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
			String type = variableDef.getType();
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
		XiNode xpath = null;
		try {
			xpath = XSTemplateSerializer.deSerializeXPathString(context.getXslt());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		final HashMap map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
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

		// do the validation.
		ValidationResult result = validateXPath(xsltString, exprContext, MapperXSDUtils.convertClassToXType(MapperXSDUtils.primitiveToClass(function.getReturnClass().getCanonicalName())));
		if (result == null || result.errList == null) {
			return;
		}
		processXPathValidationResult(result, node, exprContext);
	}
	
	protected void processXPathValidationResult(ValidationResult result, RulesASTNode node, com.tibco.xml.xmodel.xpath.ExprContext exprContext) {
		ErrorMessage[] errorMessages = result.errList.getErrorMessages();
		Expr expr = result.parser.getExpression();
        for (int i = 0; i < errorMessages.length; i++) {
        	expr.setTopLevel(); // not sure what this does, but BW does this internally before applying fix
        	ErrorMessage error = errorMessages[i];
        	String message = "[BW6 XPath] "+error.getMessage();
        	
//        	if (error.isErrorNotWarning()) {
        		reportError(node, message, IMarker.SEVERITY_ERROR);
//        	} else {
//        		reportError(node, message, IMarker.SEVERITY_WARNING);
//        	}
		}
	}

	private ValidationResult validateXPath(String xsltString, com.tibco.xml.xmodel.xpath.ExprContext exprContext,
			XType retXType) {
		String xpath = XSTemplateSerializer.getXPathExpressionAsStringValue(xsltString);
		ValidationResult result = new ValidationResult();
		IQNameResolver qNameResolver = EMapperUtilities.getQNameResolver(StudioCorePlugin.getSchemaCache(fProjectName).getResourceSet());
		exprContext.setQNameResolver(qNameResolver);
		// parse the xpath.
		Parser parser = new Parser(Lexer.lex(xpath));
		result.parser = parser;
		
		// get the expr (top of the expression tree) and the errors.
		Expr expr = parser.getExpression();
		ErrorMessageList errors = parser.getErrorMessageList();

		// if we have a parsing error then return them now.
		if (errors.getErrorCount() > 0) {
			result.errList = errors;
			return result;
		}

		// validate the expression given the exprContext
		EvalTypeInfo info = new EvalTypeInfo();
		info.setRecordErrors(true);
		List<Coercion> coercions = MapperXSDUtils.getCoercionsFromXPath(xsltString);
		if (coercions.size() > 0) {
			// need to manually apply each coercion before validation
			for (Coercion coercion : coercions) {
				exprContext = coercion.applyTo(exprContext);
			}
		}
		XType actualRetType = expr.evalType(exprContext, info);

		// if we have errors then return them
		errors = info.getErrors();
		if (errors.getErrorAndWarningCount() > 0) {
			result.errList = errors;
			return result;
		}

		// check that return type is correct.
		if (retXType != null) {
			ErrorMessage error =
					TypeChecker2.checkTypeConversion(actualRetType, retXType, expr.getTextRange(),XsltVersion.DEFAULT_VERSION);
			if (error != null) {
				info.addError(error);
				result.errList = info.getErrors();
				return result;
			}
		}

		// no errors..
		return result;
	}
	
	private void checkErrorList(ErrCheckErrorList errors, RulesASTNode node, XiNode xpath) {
		if (errors == null || errors.getCount() == 0) {
			return;
		}
		int count = errors.getCount();
		if (count > 0) {
			CodeErrorMessage[] messages = errors.getMessages();
			for (int i = 0; i < messages.length; i++) {
				TextRange textRange = messages[i].getTextRange();
				String details = null;
				if (textRange != null) {
					try {
						int start = textRange.getStartPosition();
						int end = textRange.getEndPosition();
						if (start >= 0 && start < end);
						details = xpath.getStringValue().trim().substring(start, end);
					} catch (Exception e) {
					}
				}
				String message = XPATH_ERROR_PREFIX+messages[i].getMessage();
				if (details != null) {
					message += " (" + details + ")";
				}
				if (messages[i].getSeverity() == CodeErrorMessage.TYPE_ERROR) {
					reportError(node, message, IMarker.SEVERITY_ERROR);
				} else if (messages[i].getSeverity() == CodeErrorMessage.TYPE_WARNING) {
					reportError(node, message, IMarker.SEVERITY_WARNING);
				}
			}
		}
	}

	private void validateMapperFunction(Predicate function, RulesASTNode node) {
		try {
	        EMFTnsCache cache = StudioCorePlugin.getCache(fProjectName);
			RuleElement element = (RuleElement) getRuleElement(node);
			List<VariableDefinition> variableDefinitions = getVariableDefinitions(element, node);
			String xsltString = getXsltFromMethodNode(node);
	        MapperInvocationContext context = new MapperInvocationContext(fProjectName, variableDefinitions, xsltString, function, null, node);

	        if (TraxSupport.isXPath2DesignTime(fProjectName)) {
	        	validateBW6MapperFunction(node, cache, context);
	        } else {
	        	validateBW5MapperFunction(node, cache, context);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void validateBW6MapperFunction(RulesASTNode node,
			EMFTnsCache cache, MapperInvocationContext context) {
		updateCustomFuncs(context);
		EMapperModelInputOutputAdapter modelInput = new BEMapperCoreInputOutputAdapter(context, MapperXSDUtils.getSourceElements(context), MapperXSDUtils.getTargetEntity(context));
		EXsltError[] validateXslt = EXsltValidator.validateXslt(modelInput, false);
		if (validateXslt == null) {
			return;
		}
        for (int i = 0; i < validateXslt.length; i++) {
        	EXsltError error = validateXslt[i];
        	String message = "[BW6] "+error.getErrorText();

        	if (error.isErrorNotWarning()) {
        		reportError(node, message, IMarker.SEVERITY_ERROR);
        	} else {
        		reportError(node, message, IMarker.SEVERITY_WARNING);
        	}
		}

	}
	
	protected void updateCustomFuncs(MapperInvocationContext context) {
		if (!fUpdatedCustomFuncs) {
			MapperXSDUtils.updateCustomFunctions(context.getProjectName());
		}
		fUpdatedCustomFuncs = true;
	}

	private void validateBW5MapperFunction(final RulesASTNode node,
			EMFTnsCache cache, final MapperInvocationContext context) {
		final TemplateReportArguments args = new TemplateReportArguments();
		args.setSkippingTemplateParams(true); // these aren't shown in the tree, so we don't want them in the report.
		args.setCancelChecker(new DefaultCancelChecker());
		
		args.setRecordingMissing(true);
		TemplateEditorConfiguration tmpTec = null;
		try {
			tmpTec = MapperCoreUtils.createTemplateEditorConfiguration(context, cache);
		} catch (XMLReadException e) {
			reportError(node, XSLT_ERROR_PREFIX+"Error while reading xslt string.  "+e.getMessage());
			return;
		}
		final TemplateEditorConfiguration tec = tmpTec;
		Binding parent = tec.getBinding() != null ? tec.getBinding().getParent() : null;
		if (parent instanceof StylesheetBinding) {
			Set prefixesAsSet = ((StylesheetBinding) parent).getExcludedPrefixesAsSet();
			NamespaceDeclaration[] namespaceDeclarations = parent.getElementInfo().getNamespaceDeclarations();
			for (Object object : prefixesAsSet) {
				String pfx = (String) object;
				if (!findNamespaceByPrefix(pfx, namespaceDeclarations)) {
					reportError(node, XSLT_ERROR_PREFIX+"Unknown prefix '"+pfx+"' in 'exclude-result-prefixes' attribute of xslt string");
				}
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				BindingEditorPanel editorPanel = new BindingEditorPanel(StudioCorePlugin.getUIAgent(context.getProjectName()));
				
				editorPanel.getEditor().setTemplateEditorConfiguration(tec);
				BindingEditor editor = runLoad(editorPanel, tec);
				Object obj = editor.getBindingTree().getRootNode();
				if (obj instanceof BindingNode) {
					TemplateReport tr = ((BindingNode) obj).getTemplateReport();
					checkTemplateReport(tr, node);
				} else {
					TemplateReport tr = TemplateReport.create(tec,new TemplateReportFormulaCache(),args);
					checkTemplateReport(tr, node);
				}
			}
		});
	}

	private boolean findNamespaceByPrefix(String pfx,
			NamespaceDeclaration[] namespaceDeclarations) {
		for (int i = 0; i < namespaceDeclarations.length; i++) {
			NamespaceDeclaration declaration = namespaceDeclarations[i];
			if (pfx.equals(declaration.getPrefix())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean visitDecimalLiteralNode(RulesASTNode node) {
		String text = node.getText();
		if (text != null) {
			if(NodeTypeFlag.UNKNOWN_FLAG == getIntegerLiteralType(text,
					node.getType() == RulesParser.HexLiteral, node))
			{
				reportError(node, "long literal \""+text+"\" out of range");
			}
		}
		return true;
	}

	protected NodeTypeFlag getIntegerLiteralType(String text, boolean hex,
			RulesASTNode node) {
		BigInteger bigInt;
		int end = text.length();
		boolean isLong = false;
		if (text.charAt(end - 1) == 'l' || text.charAt(end - 1) == 'L') {
			end--;
			isLong = true;
		}

		if (hex) {
			bigInt = new BigInteger(text.substring(2, end), 16);
		} else {
			bigInt = new BigInteger(text.substring(0, end), 10);
		}

		Tree tree = node.getParent(); 
		boolean negative = 
					tree != null 
				&&  tree.getType() == RulesParser.PREFIX
				&& (tree = tree.getParent()) != null
				&&  tree.getType() == RulesParser.PRIMARY_EXPRESSION
				&& (tree = tree.getParent()) != null
				&&  tree.getType() == RulesParser.EXPRESSION
				&& (tree = tree.getParent()) != null
				&&  tree.getType() == RulesParser.UNARY_EXPRESSION_NODE
				&&  tree.getChildCount() == 2
				&& (tree = tree.getChild(1)) != null
				&&  tree.getType() == RulesParser.OPERATOR
				&&  tree.getChildCount() == 1
				&& (tree = tree.getChild(0)) != null
				&&  tree.getType() == RulesParser.MINUS;

		BigInteger limit;
		if (negative) limit = MIN_LONG_MAGNITUDE;
		else limit = MAX_LONG_MAGNITUDE;
		
		if(bigInt.compareTo(limit) > 0) return NodeTypeFlag.UNKNOWN_FLAG;
		if(!isLong) {
			if (negative) limit = MIN_INT_MAGNITUDE;
			else limit = MAX_INT_MAGNITUDE;
			if(bigInt.compareTo(limit) > 0) return NodeTypeFlag.LONG_PROMOTED_FLAG;
			else return NodeTypeFlag.INT_FLAG;
		}
		return NodeTypeFlag.LONG_FLAG;
	}
	
	//assigning a promoted long literal to an integer should give an error so that users aren't surprised that
	//they get MAX_INT instead of their value
	//don't have to check if rhs is a literal because only literals are given LONG_PROMOTED_FLAG 
	protected void checkPromotedIntLiteral(NodeType lhsType, NodeType rhsType, RulesASTNode rhs) {
		if(rhs != null) {
			if(rhsType == null) rhsType = getNodeType(rhs);
			if(lhsType.isInt() && rhsType.isPromotedLong()) {
				reportError(rhs, "int literal \"" + getIntegerLiteralText(rhs) + "\" out of range");
			}
		}
	}
	
	protected String getIntegerLiteralText(RulesASTNode node) {
		if(node == null) return null;
		int type = node.getType();
		if(type == RulesParser.DecimalLiteral || type == RulesParser.HexLiteral) { 
			return node.getText(); 
		}
		if(node.getChildCount() > 0) {
			for(RulesASTNode child : node.getChildren()) {
				String s = getIntegerLiteralText(child);
				if(s != null) return s;
			}
		}
		return null;
	}

	@Override
	public boolean visitPrimaryExpressionNode(RulesASTNode node) {
		if (fSectionType == IRulesSourceTypes.CONDITION_SOURCE && isSimpleNode(node)) {
			NodeType nodeType = getNodeType(node);
			if (!nodeType.isBoolean()) {
				reportError(node, "Expecting a boolean in the conditions section");
				return false;
			}
		}
		
		return true;
	}

	private boolean isSimpleNode(RulesASTNode node) {
		if (node.getParent() != null && node.getParent().getType() == RulesParser.EXPRESSION) {
			node = (RulesASTNode) node.getParent();
			if (node.getParent() != null && node.getParent().getType() == RulesParser.PREDICATE_STATEMENT) {
				node = (RulesASTNode) node.getParent();
				if (node.getParent() != null && node.getParent().getType() == RulesParser.STATEMENTS) {
					return true;
				}
			}
		}
		return false;
	}

	private BindingEditor runLoad(BindingEditorPanel editorPanel,
			TemplateEditorConfiguration tec) {
		DefaultCancelChecker cancelChecker = new DefaultCancelChecker();
        NamespaceContextRegistry ni = tec.getExprContext().getNamespaceMapper();
        TemplateEditorConfiguration tec2 = TemplateReportGenerationUtils.createConfiguration(tec,ni,cancelChecker);
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }

        // Now insert marker comments:
        editorPanel.getEditor().getBindingTree().getFormulaCache().clear();
        BindingDisplayUtils.addMarkers(tec2,editorPanel.getEditor().getBindingTree().getFormulaCache());
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }

        editorPanel.getEditor().setTemplateEditorConfiguration(tec2);
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }
        editorPanel.getEditor().getBindingTree().waitForReport(cancelChecker);
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }
//        if (state!=null)
//        {
//            m_bindingEditor.setInputTreeState(state.m_leftState);
//            m_bindingEditor.setBindingTreeState(state.m_rightState);
//        }
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }

        //m_bindingEditor.setIsDebuggerActive(isDebuggerActive);

        editorPanel.getEditor().loadPreferences();
        if (cancelChecker.hasBeenCancelled())
        {
            return null;
        }

        return editorPanel.getEditor();
	}

	private void checkTemplateReport(TemplateReport tr, RulesASTNode node) {
		if (tr.isRecursivelyErrorFree()) {
			return;
		}
        BindingFixerChangeList list = new BindingFixerChangeList();
        list.setIncludeOtherwiseCheck(true); // Always want these on here (they are off elsewhere because of the expense)
        list.setIncludeWarnings(true);
        list.run(tr);
        for (int i = 0; i < list.size(); i++) {
        	BindingFixerChange change = list.getChange(i);
        	String message = getErrorMessage(change);

        	if (change.getCategory() == BindingFixerChange.CATEGORY_ERROR) {
        		reportAWTError(node, message, IMarker.SEVERITY_ERROR);
        	} else if (change.getCategory() == BindingFixerChange.CATEGORY_WARNING) {
        		reportAWTError(node, message, IMarker.SEVERITY_WARNING);
        	}
		}
	}
	
	private String getErrorMessage(BindingFixerChange change) {
		String message = XSLT_ERROR_PREFIX+change.getMessage();
		if (!(change instanceof FormulaErrorBindingFixerChange)) {
			return message;
		}
		FormulaErrorBindingFixerChange fe = (FormulaErrorBindingFixerChange) change;
		TextRange textRange = fe.getErrorMessage().getTextRange();
		String details = null;
		if (textRange != null) {
			try {
				int start = textRange.getStartPosition();
				int end = textRange.getEndPosition();
				if (start >= 0 && start < end);
				details = fe.getTemplateReport().getXPathExpression().getExprValue().trim().substring(start, end);
			} catch (Exception e) {
			}
		}
		if (details != null) {
			message += " (" + details + ")";
		}
		return message;
	}

	// manually check the template report (seems to miss warnings)
	private void checkTemplateReport2(TemplateReport tr, RulesASTNode node) {
		if (tr.isRecursivelyErrorFree()) {
			return;
		}
        if (tr.getComputedType() != null && tr.getComputedType().getName() != null) {
        	String displayName = tr.getComputedType().getName().localName;
        	String formatError = TemplateReportErrorFormatter.formatError(tr, displayName);
        	String genericError = com.tibco.cep.mapper.util.ResourceBundleManager.getMessage(MessageCode.COMPONENTS_CONTAIN_ERRORS,displayName);
        	if (!genericError.equals(formatError)) {
        		// don't report generic "Components inside of 'xxx' contain errors", since we're reporting the leaf errors already
        		reportError(node, formatError);
        	}
        } 
        if (tr.getStructuralError()!=null) {
            // Indicates this node, at an XSLT element validation level (ignoring expected output, etc), is wrong,
            // so this is serious:
            reportError(node, tr.getStructuralError());
        }
        if (tr.getFormulaErrors().getErrorAndWarningMessages().length>0) {
        	reportError(node, BindingEditorResources.ERROR_FORMULA_HAS_ERRORS);
        }
        if (tr.getOutputContextError() != null) {
        	reportError(node, tr.getOutputContextError().getType());
        }
        if (tr.getExtendedErrors().length > 0) {
        	TemplateReportExtendedError[] extendedErrors = tr.getExtendedErrors();
        	for (TemplateReportExtendedError extendedError : extendedErrors) {
				reportError(node, extendedError.formatMessage(tr));
			}
        }
		TemplateReport[] children = tr.getChildren();
		for (TemplateReport templateReport : children) {
			checkTemplateReport(templateReport, node);
		}
	}

	public static String getXsltFromMethodNode(RulesASTNode node) {
		List<RulesASTNode> argsList = node.getChildrenByFeatureId(RulesParser.ARGS);
		if (argsList == null || argsList.size() == 0) {
			return "";
		}
		RulesASTNode argNode = argsList.get(0);
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

	protected RuleElement getRuleElement(RulesASTNode node) {
		if (node == null) {
			return null;
		}
		while (node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		return (RuleElement) node.getData("element");
	}

	protected List<VariableDefinition> getVariableDefinitions(
			RuleElement ruleElement, RulesASTNode node) {
		EObject elementReference = RuleGrammarUtils.getElementReference(RuleGrammarUtils.getMethodCallNode(node));
		List<VariableDefinition> vars = new ArrayList<VariableDefinition>();
		if (elementReference instanceof ElementReference) {
			IResolutionContext resolutionContext = fResolutionContextProvider.getResolutionContext((ElementReference) elementReference);
			for (GlobalVariableDef globalVariableDef : resolutionContext.getGlobalVariables()) {
				if (globalVariableDef.getOffset() < node.getOffset() || (globalVariableDef.getOffset() == 0 && node.getOffset() == 0)) {
					vars.add(globalVariableDef);
				}
			}
		} else {
			if (ruleElement == null) {
				return vars;
			}
			for (GlobalVariableDef globalVariableDef : ruleElement.getGlobalVariables()) {
				if (globalVariableDef.getOffset() < node.getOffset()) {
					vars.add(globalVariableDef);
				}
			}
		}

		ScopeBlock scope = ruleElement.getScope();
		processScope(scope, vars, node.getOffset());
		return vars;
	}

	private ScopeBlock getScope(ScopeBlock scope, int offset) {
		if (scope.getOffset() < offset && (scope.getOffset() + scope.getLength() > offset)) {
			EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
			for (int i = 0; i < childScopeDefs.size(); i++) {
				ScopeBlock childScope = childScopeDefs.get(i);
				ScopeBlock s = getScope(childScope, offset);
				if (s != null) {
					return s;
				}
			}
			return scope;
		}
		return null;
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
	@Override
	public boolean visitPrimaryAssignmentExpressionNode(RulesASTNode node) {
		if (fSectionType == IRulesSourceTypes.PRE_CONDITION_SOURCE) {
			reportError(node, "Assignments are not allowed in the pre-conditions section");
			return false;
		}
		RulesASTNode lhs = node.getChildByFeatureId(RulesParser.LHS);
		RulesASTNode op = node.getChildByFeatureId(RulesParser.OPERATOR);
		RulesASTNode rhs = node.getChildByFeatureId(RulesParser.RHS);
		
		NodeType lhsType = getNodeType(lhs);
		NodeType rhsType = null;
		if (rhs != null) {
			rhsType = getNodeType(rhs);
		}
		
		if (rhs == null) {
			return true;
		}
		
		if (!matchBinaryType(node, op.getType(), lhsType, rhsType, rhs)) {
			return false;
		}
		
		 checkDomainAssignments(node,lhs,rhs,rhsType);
		
		return true;
	}
	
	private void checkDomainAssignments(RulesASTNode node, RulesASTNode lhsNode,
			RulesASTNode rhsNode, NodeType rhsType) {
		RulesASTNode lPrefixNode = lhsNode
				.getChildByFeatureId(RulesParser.PREFIX);
		RulesASTNode rPrefixNode = rhsNode
				.getChildByFeatureId(RulesParser.PREFIX);
		if(null != rPrefixNode) {
			int type = rPrefixNode.getType();
			if(type == RulesParser.StringLiteral || type == RulesParser.HexDigit || type == RulesParser.DecimalLiteral || 
					type == RulesParser.FloatingPointLiteral || type == RulesParser.TRUE || type == RulesParser.FALSE) {
				String rhsText = rPrefixNode.getText();
				rhsText = rhsText.replace("\"", "");
				Object resolvedLhs = resolveASTNode(lPrefixNode);
				
				if (resolvedLhs instanceof PropertyDefinition) {
					EList<DomainInstance> domainList = ((PropertyDefinition) resolvedLhs)
							.getDomainInstances();
					if (null != domainList && domainList.size() > 0) {
						for (DomainInstance instance : domainList) {
							Entity entity = IndexUtils.getEntity(
									((PropertyDefinition) resolvedLhs)
											.getOwnerProjectName(), instance
											.getResourcePath(), ELEMENT_TYPES.DOMAIN);
							if (entity != null) {
								boolean isInDomain = false;
								Domain domain = (Domain) entity;
								EList<DomainEntry> domainEntries = domain.getEntries();
								getSuperDomainEnteries(domain, domainEntries);
								for (DomainEntry domainEntry : domainEntries) {
									if(matchDomainValues(domainEntry, rhsType, rhsText)) {
										isInDomain = true;
										break;
									}
								}
								
								if(!isInDomain) {
									StringBuffer message = new StringBuffer();
									message.append("Value \"");
									message.append(rhsText);
									message.append("\" is not present in the domain entries of the domain model \"");
									message.append(domain.getName());
									message.append("\"");
									reportError(node, message.toString(), IMarker.SEVERITY_WARNING);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void getSuperDomainEnteries(Domain domain,EList<DomainEntry> domainEntries) {
		if(null != domain.getSuperDomainPath()) {
			Entity superEntity = IndexUtils.getDomain(domain.getOwnerProjectName(), domain.getSuperDomainPath());
			if (null != superEntity) {
				Domain superDomain = (Domain) superEntity;
				getSuperDomainEnteries(superDomain, domainEntries);
				domainEntries.addAll(superDomain.getEntries());
			}
		} 
	}
	
	private boolean matchDomainValues(DomainEntry domainEntry,
			NodeType type, String rhsValue) {
		boolean matchesDomain = false;
		if (domainEntry instanceof Range) {
			Range domainRange = (Range) domainEntry;
			if (type.isInt() || type.isLong() || type.isDouble()) {
				double lower = Double.parseDouble(domainRange.getLower());
				double upper = Double.parseDouble(domainRange.getUpper());
				double rhsVal = Double.parseDouble(rhsValue);
				if (domainRange.isLowerInclusive()
						&& domainRange.isUpperInclusive()) {
					if (rhsVal >= lower && rhsVal <= upper) {
						matchesDomain = true;
					}
				} else if (domainRange.isLowerInclusive()) {
					if (rhsVal >= lower && rhsVal < upper) {
						matchesDomain = true;
					}
				} else if (domainRange.isUpperInclusive()) {
					if (rhsVal > lower && rhsVal <= upper) {
						matchesDomain = true;
					}
				} else {
					if (rhsVal > lower && rhsVal < upper) {
						matchesDomain = true;
					}
				}
			}
		} else {
			if (rhsValue.equals(domainEntry.getValue())) {
				matchesDomain = true;
			}
		}
		return matchesDomain;
	}

	@Override
	public boolean visitValidityStatementNode(RulesASTNode node) {
		RulesASTNode validity = node.getChildByFeatureId(RulesParser.VALIDITY);
		if (VALIDITY_ACTION.equalsIgnoreCase(validity.getText())) {
			fValidity = Validity.ACTION;
		} else if (VALIDITY_CONDITION.equalsIgnoreCase(validity.getText())) {
			fValidity = Validity.CONDITION;
		} else if (VALIDITY_QUERY.equalsIgnoreCase(validity.getText())) {
			fValidity = Validity.QUERY;
		}
		return false;
	}

	@Override
	public boolean visitVariableDeclaratorNode(RulesASTNode node) {
		RulesASTNode lhs = node.getChildByFeatureId(RulesParser.NAME);
		RulesASTNode rhs = node.getChildByFeatureId(RulesParser.EXPRESSION);
		if (rhs == null) {
			return true;
		}
		NodeType lhsType = getNodeType(lhs);
		NodeType rhsType = getNodeType(rhs);
		
		if (!matchBinaryType(node, RulesParser.ASSIGN, lhsType, rhsType, rhs)) {
			return false;
		}
		
		RulesASTNode initializer = node.getChildByFeatureId(RulesParser.INITIALIZER);
		if (initializer != null) {
			
		}
		return false;
	}

	@Override
	public boolean visitPriorityStatementNode(RulesASTNode node) {
		RulesASTNode priority = node.getChildByFeatureId(RulesParser.PRIORITY);
		if (priority != null) {
			int pri = Integer.parseInt(priority.getText());
			if (pri < 1 || pri > 10) {
				reportError(priority, "Invalid priority.  Value must be between 1 and 10");
			}
		}
		return false;
	}

	@Override
	public boolean visitReturnStatementNode(RulesASTNode node) {
		RuleElement element = (RuleElement) getRuleElement(node);
		ScopeBlock scope = element.getScope();
		ScopeBlock scopeBlock = null;
		if (scope != null) {
			scopeBlock = getScope(scope, node.getOffset());
			RulesASTNode rulesASTNode = fReturnStatementMap.get(scopeBlock);
			if (rulesASTNode != null) {
				fHasReturnStatement = true;
				reportError(node, "Unreachable code.  There is already a return statement in this scope");
				return false;
			}
		}
		RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
		NodeType nodeType = getNodeType(expNode);
		if (expNode == null) {
			nodeType = NodeType.VOID;
		}
		// check this against the rule's return type
		if (fReturnNodeType == null) {
			if (fResolutionContextProvider instanceof IResolutionContextProviderExtension) {
				fReturnNodeType = ((IResolutionContextProviderExtension) fResolutionContextProvider).getReturnType();
			} else {
				fReturnNodeType = NodeType.VOID;
			}
		}
		if (nodeType == null) {
			fHasReturnStatement = true;
//			reportMismatchedReturnType(expNode, fReturnNodeType, nodeType);
			return false;
		}
		IStatus stat = equivalentTypes(fReturnNodeType, nodeType);
		RulesASTNode errNode = expNode == null ? node : expNode;
		if (!stat.isOK()) {
			reportMismatchedReturnType(errNode, fReturnNodeType, nodeType, stat);
		} else if (!related(fReturnNodeType, nodeType)) {
			reportMismatchedReturnType(errNode, fReturnNodeType, nodeType, stat);
		}
		checkPromotedIntLiteral(fReturnNodeType, nodeType, expNode);
		doVisitChildren(node);
		if (scopeBlock != null) {
			fReturnStatementMap.put(scopeBlock, node);
		}
		fHasReturnStatement = true;
		return false;
	}

	private boolean related(NodeType node1, NodeType node2) {
		if (node1.isEntity() && node2.isEntity()) {
			String name = node1.getName();
			String name2 = node2.getName();
			if (("Event".equals(name) || "Event".equals(name2)) 
					|| ("SimpleEvent".equals(name) || "SimpleEvent".equals(name2))) {
				return true;
			}
			if ("Concept".equals(name) || "Concept".equals(name2)) {
				return true;
			}
			name = ModelUtils.convertPackageToPath(name);
			name2 = ModelUtils.convertPackageToPath(name2);
			DesignerElement element = IndexUtils.getElement(fProjectName, name);
			DesignerElement element2 = IndexUtils.getElement(fProjectName, name2);
			if (element == null || element2 == null) {
				return false;
			}
			if (!related(element, element2)) {
				return false;
			}
		} else {
			return true;
		}
		return true;
	}

	/**
	 * NOTE: element2 must extend from element, i.e. element2 is a sub-concept of element
	 * @param element
	 * @param element2
	 * @return
	 */
	private boolean related(DesignerElement element, DesignerElement element2) {
		if (element.getElementType() != element2.getElementType()) {
			return false;
		}
		if (element instanceof EntityElement && element2 instanceof EntityElement) {
			Entity entity = ((EntityElement)element).getEntity();
			Entity entity2 = ((EntityElement)element2).getEntity();
			if (entity instanceof Event && entity2 instanceof Event) {
				if (relatedEvents((Event)entity, (Event)entity2)) {
					return true;
				}
			}
			if (entity instanceof Concept && entity2 instanceof Concept) {
				if (relatedConcepts((Concept)entity, (Concept)entity2)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean relatedConcepts(Concept entity, Concept entity2) {
		if (entity2.equals(entity)) {
			return true;
		}
		Concept superConcept = entity2.getSuperConcept();
		while (superConcept != null) {
			if (superConcept.equals(entity)) {
				return true;
			}
			superConcept = superConcept.getSuperConcept();
		}
		return false;
	}

	private boolean relatedEvents(Event entity, Event entity2) {
		if (entity2.equals(entity)) {
			return true;
		}
		Event superEvent = entity2.getSuperEvent();
		while (superEvent != null) {
			if (superEvent.equals(entity)) {
				return true;
			}
			superEvent = superEvent.getSuperEvent();
		}
		return false;
	}

	@Override
	public boolean visitReturnTypeNode(RulesASTNode node) {
		RulesASTNode expNode = node.getFirstChild();
		if (expNode == null) {
			fReturnNodeType = NodeType.VOID;
		} else {
			fReturnNodeType = getNodeType(expNode, expNode.isArray(), true);
		}
		if (fReturnNodeType.isUnknown()) {
			reportError(expNode, "Unknown/invalid return type");
			return false;
		}
		if (fReturnNodeType.hasPropertyContext()) {
			reportError(expNode, "Invalid return type");
			return false;
		}
		return true;
	}

	private NodeType getBinaryRelationType(RulesASTNode node) {
		if (node.getChildCount() != 2) {
			System.out.println("Binary relation not complete");
			return NodeType.UNKNOWN;
		}
		int opType = node.getType();
		RulesASTNode lhs = node.getChild(0);
		RulesASTNode rhs = node.getChild(1);
		
		NodeType lhsType = getNodeType(lhs);
		NodeType rhsType = getNodeType(rhs);
		
		if (!matchBinaryType(node, opType, lhsType, rhsType, rhs)) {
			return NodeType.UNKNOWN;
		}
		
		return getExpectedType(opType, lhsType, rhsType);
	}
	
	public NodeType getNodeType(RulesASTNode node) {
		return getNodeType(node, false);
	}
	
	public NodeType getNodeType(RulesASTNode node, boolean isArray) {
		return getNodeType(node, isArray, false);
	}
	
	public NodeType getNodeType(RulesASTNode node, boolean isArray, boolean isTypeNode) {
		if (node == null) {
			return NodeType.UNKNOWN;
		}
		int type = node.getType();
		switch (type) {
		case RulesParser.SET_MEMBERSHIP_EXPRESSION:
			return new NodeType(NodeTypeFlag.VOID_FLAG, TypeContext.PRIMITIVE_CONTEXT, true);

		case RulesParser.PRIMARY_EXPRESSION:
			return getPrimaryExpressionType(node);
			
		case RulesParser.METHOD_CALL:
			return getMethodCallReturnType(node);
			
		case RulesParser.UNARY_EXPRESSION_NODE:
			return getUnaryExpressionType(node);
			
		case RulesParser.PLUS:
		case RulesParser.MINUS:
		case RulesParser.MULT:
		case RulesParser.DIVIDE:
		case RulesParser.MOD:
		case RulesParser.LT:
		case RulesParser.GT:
		case RulesParser.LE:
		case RulesParser.GE:
		case RulesParser.EQ:
		case RulesParser.NE:
		case RulesParser.ASSIGN:
			return getBinaryRelationType(node);

		case RulesParser.AND:
		case RulesParser.OR:
			return getBinaryRelationType(node);
			
		case RulesParser.DecimalLiteral:
			NodeTypeFlag flag = getIntegerLiteralType(node.getText(), false, node);
			//if(flag == NodeTypeFlag.UNKNOWN_FLAG) flag = NodeTypeFlag.INT_FLAG;
			return new NodeType(flag, TypeContext.PRIMITIVE_CONTEXT, true);
		case RulesParser.HexLiteral:
			flag = getIntegerLiteralType(node.getText(), true, node);
			//if(flag == NodeTypeFlag.UNKNOWN_FLAG) flag = NodeTypeFlag.INT_FLAG;
			return new NodeType(flag, TypeContext.PRIMITIVE_CONTEXT, true);
		case RulesParser.FloatingPointLiteral:
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, true);
			
		case RulesParser.StringLiteral:
			return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, true);
			
		case RulesParser.NullLiteral:
			return NodeType.NULL;
			
		case RulesParser.TRUE:
		case RulesParser.FALSE:
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, true);
			
		case RulesParser.NAME:
		case RulesParser.SIMPLE_NAME:
			return getNameType(node, isArray, isTypeNode);
			
		case RulesParser.QUALIFIED_NAME:
			RulesASTNode nameNode = node.getFirstChildWithType(RulesParser.SIMPLE_NAME);
			return getNameType(nameNode, isArray, isTypeNode);
			
		case RulesParser.ARRAY_ACCESS_SUFFIX:
			RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
			return getNodeType(expNode, isArray);
			
		case RulesParser.ARRAY_LITERAL:
			RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
			return getNodeType(typeNode, typeNode.isArray(), true);
			
		case RulesParser.ARRAY_ALLOCATOR:
			RulesASTNode arrNode = node.getChildByFeatureId(RulesParser.TYPE);
			return getNodeType(arrNode, arrNode.isArray(), true);
			
		case RulesParser.PRIMITIVE_TYPE:
			String primType = node.getFirstChild().getText();
			return getPrimitiveType(primType, isArray, false, true);
			
		case RulesParser.INSTANCE_OF:
			return getBinaryRelationType(node);
			
		case RulesParser.VOID_LITERAL:
			return NodeType.VOID;
			
		default:
			System.out.println("Something else");
			break;
		}
		return NodeType.UNKNOWN;
	}

	private NodeType getNameType(RulesASTNode node, boolean isArray, boolean isTypeNode) {
		Object resolvedObj = resolveASTNode(node);
		if (resolvedObj instanceof AmbiguousReference) {
			reportAmbiguousReferenceError(node, (AmbiguousReference) resolvedObj);
			return NodeType.UNKNOWN;
		}
		if (resolvedObj instanceof PropertyDefinition && fSectionType == IRulesSourceTypes.CONDITION_SOURCE) {
			checkPropertyAccessInConditionSection(node, resolvedObj);
		}
		if (resolvedObj instanceof String && !isTypeNode) {
			if (RuleGrammarUtils.isGenericType((String)resolvedObj)) {
				reportError(node, "Unable to resolve '"+resolvedObj+"'");
				return NodeType.UNKNOWN;
			}
		}
		Object b = node.getData("attr");
		boolean attr = false;
		if (b instanceof Boolean) {
			attr = (Boolean) b; 
		}

		return getObjectType(resolvedObj, isArray, attr);
	}
	
	private void reportAmbiguousReferenceError(RulesASTNode node, List<DesignerElement> elements) {
		StringBuffer buffer = new StringBuffer();
		buildAmbiguousReferenceError(buffer, elements);
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_AMBIGUOUS_REFERENCE_ERROR, buffer.toString(), node.getOffset(), node.getLength(), node.getLine());
		fSemanticErrors.add(error);
	}
	
	private void reportAmbiguousReferenceError(RulesASTNode node, AmbiguousReference resolvedObj) {
		StringBuffer buffer = new StringBuffer();
		List<? extends Object> potentialReferences = ((AmbiguousReference) resolvedObj).getPotentialReferences();
		buildAmbiguousReferenceError(buffer, potentialReferences);
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_AMBIGUOUS_REFERENCE_ERROR, buffer.toString(), node.getOffset(), node.getLength(), node.getLine());
		for (IRulesProblem er : fSemanticErrors) {
			if (buffer.toString().equals(er.getErrorMessage())) {
				return; // don't report the error twice
			}
		}
		fSemanticErrors.add(error);
	}

	private void buildAmbiguousReferenceError(StringBuffer buffer,
			List<? extends Object> potentialReferences) {
		buffer.append("Ambiguous Reference.  Reference must be qualified, as multiple elements exist with the same name [");
		for (int i=0; i<potentialReferences.size(); i++) {
			Object object = potentialReferences.get(i);
			if (object instanceof TypeElement) {
				buffer.append(((TypeElement) object).getFolder());
				buffer.append(((TypeElement) object).getName());
			} else if (object instanceof Entity) {
				buffer.append(((Entity) object).getFolder());
				buffer.append(((Entity) object).getName());
			} else if (object instanceof Predicate) {
				buffer.append("Function: ");
				buffer.append(((Predicate) object).getName());
			}
			if (i < potentialReferences.size() - 1) {
				buffer.append(',');
			}
		}
		buffer.append(']');
	}

	private void checkPropertyAccessInConditionSection(RulesASTNode node,
			Object resolvedObj) {
		if (node.getParent() != null) {
			RulesASTNode parent = (RulesASTNode) node.getParent();
			if (parent.getType() == RulesParser.QUALIFIED_NAME) {
				Object parentResolvedObj = resolveASTNode(parent.getChildByFeatureId(RulesParser.QUALIFIER));
				if (parentResolvedObj instanceof PropertyDefinition) {
					if (((PropertyDefinition) parentResolvedObj).getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
						reportError(node, "Access of properties of concept references disallowed in conditions section");
					}
				}
			} else if (parent.getType() == RulesParser.SUFFIXES) {
				// this is the case for @parent.PropName cases, which are invalid in conditions sections
				RulesASTNode child = parent.getFirstChild();
				if (child.getType() == RulesParser.SIMPLE_NAME && "parent".equals(child.getFirstChild().getText())) {
					reportError(node, "Access of properties of attributes disallowed in condition");
				}
			}
		}
	}

	private NodeType getObjectType(Object resolvedObj, boolean isArray, boolean isAttribute) {
		boolean array = isArray;
		boolean mutable = true;
		if (resolvedObj instanceof NodeType) {
			return (NodeType) resolvedObj;
		}
		if (resolvedObj instanceof Boolean) {
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, mutable);
		}
		boolean isVarDef = false;
		if (resolvedObj instanceof VariableDefinition) {
			isVarDef = true;
			VariableDefinition varDef = (VariableDefinition) resolvedObj;
			if (varDef instanceof GlobalVariableDef) {
				mutable = false;
			}
			array = varDef.isArray();
			if (isPrimitiveType(varDef.getType())) {
				return getPrimitiveType(varDef.getType(), array, false, mutable);
			}
			resolvedObj = ElementReferenceResolver.resolveVariableDefinitionType(varDef);
			if (resolvedObj == null) {
				return getPrimitiveType(varDef.getType(), array, false, mutable);
			}
		}
		if (resolvedObj instanceof EntityElement) {
			resolvedObj = ((EntityElement) resolvedObj).getEntity();
		}
		if (resolvedObj instanceof Concept) {
			NodeTypeFlag flag = NodeTypeFlag.CONCEPT_FLAG;
			if (((Concept) resolvedObj).isContained()) {
				flag = NodeTypeFlag.CONTAINED_CONCEPT_FLAG;
			}
			String name = ModelUtils.convertPathToPackage(((Concept) resolvedObj).getFullPath());
			TypeContext ctx = isAttribute ? TypeContext.PROPERTY_CONTEXT : TypeContext.PRIMITIVE_CONTEXT;
			return new NodeType(flag, ctx, mutable, array, name, isVarDef);
		}
		if (resolvedObj instanceof SimpleEvent) {
			String name = ModelUtils.convertPathToPackage(((Event) resolvedObj).getFullPath());
			return new NodeType(NodeTypeFlag.SIMPLE_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, mutable, array, name, isVarDef);
		}
		if (resolvedObj instanceof TimeEvent) {
			String name = ModelUtils.convertPathToPackage(((Event) resolvedObj).getFullPath());
			return new NodeType(NodeTypeFlag.TIME_EVENT_FLAG, TypeContext.PRIMITIVE_CONTEXT, mutable, array, name, isVarDef);
		}
		if (resolvedObj instanceof PropertyDefinition) {
			PROPERTY_TYPES type = ((PropertyDefinition) resolvedObj).getType();
			if (((PropertyDefinition) resolvedObj).isArray()) {
				mutable = false; // property arrays are not mutable
				if (type == PROPERTY_TYPES.CONCEPT || type == PROPERTY_TYPES.CONCEPT_REFERENCE) {
					String conceptPath = ModelUtils.convertPathToPackage(((PropertyDefinition) resolvedObj).getConceptTypePath());
					if (type == PROPERTY_TYPES.CONCEPT) {
						return new NodeType(NodeTypeFlag.CONTAINED_CONCEPT_FLAG, TypeContext.PROPERTY_CONTEXT, mutable, true, conceptPath);
					}
					String newPath = ModelUtils.convertPackageToPath(conceptPath);
					Concept c = IndexUtils.getConcept(fProjectName, newPath);
					NodeTypeFlag flag = (c != null && c.isContained()) ? NodeTypeFlag.CONTAINED_CONCEPT_REFERENCE_FLAG : NodeTypeFlag.CONCEPT_REFERENCE_FLAG;
					return new NodeType(flag, TypeContext.PROPERTY_CONTEXT, mutable, true, conceptPath);
				}
				NodeType primitiveType = getPrimitiveType(type.getLiteral(), true, true, false);
				if (primitiveType.isUnknown()) {
					return new NodeType(NodeTypeFlag.GENERIC_PROP_ARRAY_FLAG, TypeContext.PROPERTY_CONTEXT, mutable, true);
				}
				return primitiveType;
			}
			return getPropertyType((PropertyDefinition) resolvedObj, type, TypeContext.PROPERTY_CONTEXT, ((PropertyDefinition) resolvedObj).isArray());
		}
		if (resolvedObj instanceof FunctionRec) {
			return ((FunctionRec) resolvedObj).returnType;
		}
		if (resolvedObj instanceof JavaStaticFunction) {
			Class retClass = ((JavaStaticFunction) resolvedObj).getReturnClass();
			FunctionRec functionRec = fCatalogLookup.makeFunctionRec((JavaStaticFunction)resolvedObj);
			return functionRec.returnType;
		}
		if (resolvedObj instanceof String) {
			return getAttributeType((String) resolvedObj, array);
		}
		if (resolvedObj instanceof RuleElement) {
			String name = ModelUtils.convertPathToPackage(((RuleElement) resolvedObj).getRule().getFullPath());
			FunctionRec function = fCatalogLookup.lookupFunction(name, null);
			if (function != null) {
				return function.returnType;
			}
		}
		if (resolvedObj instanceof DomainEntry) {
			EObject eContainer = ((DomainEntry) resolvedObj).eContainer();
			Domain dom = (Domain) eContainer;
			DOMAIN_DATA_TYPES dataType = dom.getDataType();
			return getPrimitiveType(dataType.getLiteral(), isArray, false, mutable);
		}
		if (resolvedObj instanceof EMFModelJavaFunction) {
			FunctionInfo functionInfo = ((EMFModelJavaFunction) resolvedObj).getFunctionInfo();
			if (functionInfo.getReturnType().isPrimitive()) {
				ParamTypeInfo returnType = functionInfo.getReturnType();
				String name = returnType.getName() == null ? returnType.getTypeClassName() : returnType.getName();
				return getPrimitiveType(name, functionInfo.getReturnType().isArray(), false, mutable);
			}
		}
		return NodeType.UNKNOWN;
	}

	private NodeType getAttributeType(String att, boolean array) {
		if ("isSet".equals(att)) {
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("cause".equals(att)) {
			return new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("scheduleTime".equals(att)) {
			return new NodeType(NodeTypeFlag.DATETIME_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("payload".equals(att)
				|| "message".equals(att)
				|| "type".equals(att)
				|| "category".equals(att)
				|| "extId".equals(att)
				|| "closure".equals(att)
				|| "stackTrace".equals(att)
				|| "errorType".equals(att)
				|| "ruleUri".equals(att)) {
			return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("length".equals(att)
				|| "id".equals(att)
				|| "ttl".equals(att)
				|| "interval".equals(att)) {
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("parent".equals(att)) {
			return new NodeType(NodeTypeFlag.CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if("ruleScope".equals(att)) {
			return new NodeType(NodeTypeFlag.OBJECT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, true);
		}
		// query IElementResolutionProviders
		IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		for (IElementResolutionProvider provider : elementResolutionProviders) {
			NodeType type = provider.getAttributeType(att, array);
			if (type != null && !type.isUnknown()) {
				return type;
			}
		}

		return getGenericType(att, array);
	}

	private NodeType getGenericType(int genericTypeFlag) {
		NodeType type = null;
		if (genericTypeFlag <= fGenericTypes.length) {
			type = fGenericTypes[genericTypeFlag];
		}
		if (type == null) {
			type = NodeType.UNKNOWN;
		}
		return type;
	}
	
	private NodeType getGenericType(String att, boolean array) {
		if ("AdvisoryEvent".equals(att)) {
			return array ? getGenericType(GENERIC_ADVISORY_EVENT_FLAG_ARR) : getGenericType(GENERIC_ADVISORY_EVENT_FLAG);
		}
		if ("String".equals(att)) {
			return array ? getGenericType(STRING_FLAG_ARR) : getGenericType(STRING_FLAG);
		}
		if ("Exception".equals(att)) {
			return array ? getGenericType(EXCEPTION_FLAG_ARR) : getGenericType(EXCEPTION_FLAG);
		}
		if ("Concept".equals(att)) {
			return array ? getGenericType(GENERIC_CONCEPT_FLAG_ARR) : getGenericType(GENERIC_CONCEPT_FLAG);
		}
		if ("Process".equals(att)) {
			return array ? getGenericType(GENERIC_PROCESS_FLAG_ARR) : getGenericType(GENERIC_PROCESS_FLAG);
		}
		if ("Object".equals(att)) {
			return array ? getGenericType(OBJECT_FLAG_ARR) : getGenericType(OBJECT_FLAG);
		}
		if ("Event".equals(att)) {
			return array ? getGenericType(GENERIC_EVENT_FLAG_ARR) : getGenericType(GENERIC_EVENT_FLAG);
		}
		if ("SimpleEvent".equals(att)) {
			return array ? getGenericType(GENERIC_SIMPLE_EVENT_FLAG_ARR) : getGenericType(GENERIC_SIMPLE_EVENT_FLAG);
		}
		if ("DateTime".equals(att)) {
			return array ? getGenericType(DATETIME_FLAG_ARR) : getGenericType(DATETIME_FLAG);
		}
		if ("Boolean".equals(att)) {
			return array ? getGenericType(BOOLEAN_FLAG_ARR) : getGenericType(BOOLEAN_FLAG);
		}
		if ("Double".equals(att)) {
			return array ? getGenericType(DOUBLE_FLAG_ARR) : getGenericType(DOUBLE_FLAG);
		}
		return getGenericType(UNKNOWN_FLAG);
	}

	private NodeType getPropertyType(PropertyDefinition propertyDefinition, PROPERTY_TYPES type, TypeContext context, boolean isArray) {
		switch (type) {
		case BOOLEAN:
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, context, true, isArray);
			
		case CONCEPT_REFERENCE:
			String conceptRefName = ModelUtils.convertPathToPackage(propertyDefinition.getConceptTypePath());
			Concept c = IndexUtils.getConcept(fProjectName, propertyDefinition.getConceptTypePath());
			NodeTypeFlag flag = (c != null && c.isContained()) ? NodeTypeFlag.CONTAINED_CONCEPT_REFERENCE_FLAG : NodeTypeFlag.CONCEPT_REFERENCE_FLAG;
			return new NodeType(flag, context, true, isArray, conceptRefName);
		
		case CONCEPT:
			String conceptName = ModelUtils.convertPathToPackage(propertyDefinition.getConceptTypePath());
			Concept concept = IndexUtils.getConcept(fProjectName, propertyDefinition.getConceptTypePath());
			if (concept != null && concept.isContained()) {
				return new NodeType(NodeTypeFlag.CONTAINED_CONCEPT_FLAG, context, true, isArray, conceptName);
			}
			return new NodeType(NodeTypeFlag.CONCEPT_FLAG, context, true, isArray, conceptName);
			
		case DATE_TIME:
			return new NodeType(NodeTypeFlag.DATETIME_FLAG, context, true, isArray);
			
		case DOUBLE:
			return new NodeType(NodeTypeFlag.DOUBLE_FLAG, context, true, isArray);
			
		case INTEGER:
			return new NodeType(NodeTypeFlag.INT_FLAG, context, true, isArray);
			
		case LONG:
			return new NodeType(NodeTypeFlag.LONG_FLAG, context, true, isArray);
			
		case STRING:
			return new NodeType(NodeTypeFlag.STRING_FLAG, context, true, isArray);
			
		default:
			break;
		}
		return NodeType.UNKNOWN;
	}

	private NodeType getPrimaryExpressionType(RulesASTNode node) {
		RulesASTNode prefixNode = node.getChildByFeatureId(RulesParser.PREFIX);
		List<RulesASTNode> suffixChildren = node.getChildrenByFeatureId(RulesParser.SUFFIXES);
		if (suffixChildren != null && suffixChildren.size() > 0) {
			RulesASTNode lastSuffixNode = suffixChildren.get(suffixChildren.size()-1);
			if (lastSuffixNode.getType() == RulesParser.ARRAY_ACCESS_SUFFIX) {
				int i = suffixChildren.size() - 2;
				while (i >= 0) {
					prefixNode = suffixChildren.get(i);
					if (prefixNode.getType() == RulesParser.ARRAY_ACCESS_SUFFIX) {
						i--;
						continue;
					}
					break;
				}
				NodeType nodeType = getNodeType(prefixNode, false);
				Object b = prefixNode.getData("array");
				boolean array = false;
				if (b instanceof Boolean) {
					array = (Boolean) b; 
				}
				if (array && !nodeType.isArray()) {
					reportArrayAccessError(node, nodeType);
				}
				if (nodeType.hasPropertyContext()) {
					return nodeType.getComponentType(true);
				}
				boolean mutable = nodeType.isMutable();
				if (prefixNode.getBinding() instanceof GlobalVariableDef && nodeType.isArray() && array) {
					// it is valid to set values of array entries for global vars, i.e. Concepts.MyCon[] arr; arr[0] = null;
					mutable = true;
				}
				return nodeType.getComponentType(mutable);
			}
			return getNodeType(lastSuffixNode);
		}

		NodeType type = getNodeType(prefixNode);
		return type;
	}

	private NodeType getMethodCallReturnType(RulesASTNode node) {
		Object resolvedObj = resolveASTNode(node);
		if (resolvedObj instanceof AmbiguousReference) {
			reportAmbiguousReferenceError(node, (AmbiguousReference) resolvedObj);
			return NodeType.UNKNOWN;
		}
		if (resolvedObj == Boolean.FALSE) {
			return NodeType.UNKNOWN;
		}
		return getObjectType(resolvedObj, false, false);
	}

	private NodeType getUnaryExpressionType(RulesASTNode node) {
		RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
		
		return getNodeType(expNode);
	}

	private boolean matchBinaryType(RulesASTNode node, int opType, NodeType lhsType, NodeType rhsType, RulesASTNode rhs) {
		IStatus stat = null;
		switch (opType) {
		case RulesParser.PLUS:
		case RulesParser.INCR:
			if (rhsType == null) {
				if (!(lhsType.isInt() || lhsType.isDouble() || lhsType.isLong())) {
					reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				}

				return false;
			}
			if (lhsType.isString() || rhsType.isString()) {
				if ((lhsType.isEntity() && (!lhsType.isVariableType() && !lhsType.isGeneric() && !lhsType.hasPropertyContext())) 
						|| (rhsType.isEntity() && (!rhsType.isVariableType() && !rhsType.isGeneric() && !rhsType.hasPropertyContext()))) {
					// it is valid to concatenate a string with an entity if the entity comes from a local/global variable.  
					// Otherwise it is invalid.  That is, "Hi" + myconcept is valid but "Hi" + Concepts.MyConcept is not.
					reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
					return false;
				}
				return true; // this is valid, regardless of the other type
			}
			if (lhsType.isDateTime() && rhsType.isDateTime()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			if (lhsType != null) {
				stat = equivalentTypes(lhsType, rhsType);
				if (!stat.isOK()) {
					reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
					return false;
				}
			}
			break;

		case RulesParser.MINUS:
		case RulesParser.DECR:
			if (rhsType == null) {
				if (!(lhsType.isInt() || lhsType.isDouble() || lhsType.isLong())) {
					reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				}

				return false;
			}
			if (lhsType.isString() || rhsType.isString()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			if (lhsType.isDateTime() && rhsType.isDateTime()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			break;
			
		case RulesParser.MULT:
			if (lhsType.isString() || rhsType.isString()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			if (lhsType.isDateTime() && rhsType.isDateTime()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			break;
			
		case RulesParser.DIVIDE:
			if (lhsType.isString() || rhsType.isString()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			if (lhsType.isDateTime() && rhsType.isDateTime()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			break;
			
		case RulesParser.MOD:
			if (lhsType.isString() || rhsType.isString()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			if (lhsType.isDateTime() && rhsType.isDateTime()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			break;
			
		case RulesParser.LT:
		case RulesParser.GT:
		case RulesParser.LE:
		case RulesParser.GE:
		case RulesParser.EQ:
		case RulesParser.NE:
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			break;
			
		case RulesParser.INSTANCE_OF:
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			break;
			
		case RulesParser.AND:
		case RulesParser.OR:
			if (!lhsType.isBoolean() || !rhsType.isBoolean()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			break;
			
		case RulesParser.ASSIGN:
		case RulesParser.MULT_EQUAL:
		case RulesParser.DIV_EQUAL:
		case RulesParser.MOD_EQUAL:
		case RulesParser.MINUS_EQUAL:
		case RulesParser.PLUS_EQUAL:
			if (!lhsType.isMutable()) {
				reportImmutableAssignmentError(node, opType, lhsType, rhsType);
				return false;
			}
			stat = equivalentTypes(lhsType, rhsType);
			if (!stat.isOK()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, stat);
				return false;
			}
			checkPromotedIntLiteral(lhsType, rhsType, rhs);
			break;
			
		case RulesParser.POUND:
			if (!lhsType.isBoolean()) {
				reportMismatchedBinaryTypes(node, opType, lhsType, rhsType, ERROR_STATUS);
				return false;
			}
			break;
			

		default:
			System.out.println("something else");
			break;
		}
		return true;

	}

	private NodeType getExpectedType(int opType, NodeType lhsType, NodeType rhsType) {
		switch (opType) {
		case RulesParser.PLUS:
			if (lhsType.isString() || rhsType.isString()) {
				return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
			}
			if (lhsType != null && lhsType.equals(rhsType)) {
				return lhsType;
			}
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false); // TODO : make more granular (long + int, etc)
			
		case RulesParser.MINUS:
			if (lhsType != null && lhsType.equals(rhsType)) {
				return lhsType;
			}
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false); // TODO : make more granular (long - int, etc)
			
		case RulesParser.MULT:
			if (lhsType != null && lhsType.equals(rhsType)) {
				return lhsType;
			}
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false); // TODO : make more granular (long * int, etc)
			
		case RulesParser.DIVIDE:
			if (lhsType != null && lhsType.equals(rhsType)) {
				return lhsType;
			}
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false); // TODO : make more granular (long * int, etc)
			
		case RulesParser.MOD:
			if (lhsType != null && lhsType.equals(rhsType)) {
				return lhsType;
			}
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false); // TODO : make more granular (long % int, etc)
			
		case RulesParser.LT:
		case RulesParser.GT:
		case RulesParser.LE:
		case RulesParser.GE:
		case RulesParser.NE:
		case RulesParser.EQ:
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
			
		case RulesParser.AND:
		case RulesParser.OR:
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
			
		case RulesParser.INSTANCE_OF:
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
			
		default:
			System.out.println("something else");
			break;
		}
		return NodeType.UNKNOWN;
		
	}
	
	private void matchArguments(RulesASTNode methodCallNode, FunctionRec function, NodeType[] fnArgTypes, List<RulesASTNode> userArgs) {
		boolean varArgs = function.function.isVarargs();
		if (userArgs.size() < fnArgTypes.length -1 || (!varArgs && userArgs.size() != fnArgTypes.length)) {
			if (function.isMapper && userArgs.size() == 1) {
				// mapper functions only have 1 arg?
				RulesASTNode argNode = userArgs.get(0);
				NodeType type = getNodeType(argNode);
				if (!type.isString()) {
					reportMismatchedArgumentError(methodCallNode, function, fMapperArgs, userArgs);
				}
				return;
			} else if (function.isMapper) {
				reportMismatchedArgumentError(methodCallNode, function, fMapperArgs, userArgs);
				return;
			}
			reportMismatchedArgumentError(methodCallNode, function, fnArgTypes, userArgs);
			return;
		}
		if (fnArgTypes.length == 0) {
			return;
		}
        int varargsStart = Integer.MAX_VALUE;
        if(varArgs) varargsStart = function.argTypes.length - 1;
        NodeType fnArgType = null;
		for (int i=0; i<userArgs.size(); i++) {
			RulesASTNode argNode = userArgs.get(i);
			NodeType type = getNodeType(argNode);
			if (type == null) {
				// assume this error was already reported
				continue;
			}
            if(i <= varargsStart) {
                fnArgType = fnArgTypes[i];
                if(i == varargsStart && fnArgType != null && type != null) {
                    //if more than 1 args passed to varargs, always use component type for fnArgType
                	//for single non-array arguments, use the component type
                	//use original type (no change) for null arg and for arrays of compatible types
                    //use Object for arrays passed to an Object... parameter for consistency 
                	//between passing ..., ObjArray1) and ..., ObjArray1, ObjArray2), 
                	//otherwise adding the second arg would change how the first arg was passed
            		if(i < userArgs.size()-1 || fnArgType.isObject() || (i == userArgs.size()-1 && !type.isArray())) {
                        fnArgType = fnArgType.getComponentType(false);
                    }
                }
            }
			if (!type.isUnknown() && !argIsCompatible(fnArgType, type)) {
				if (function.isMapper) {
					reportMismatchedArgumentError(methodCallNode, function, fMapperArgs, userArgs);
					return;
				}
				reportMismatchedArgumentError(methodCallNode, function, fnArgTypes, userArgs);
				return;
			} 
		}

		
	}

	private NodeType getPrimitiveType(String primType, boolean isArray, boolean isProperty, boolean mutable) {
		if (primType == null) {
			return NodeType.UNKNOWN;
		}
		TypeContext context = null;
		if (isProperty) {
			context = TypeContext.PROPERTY_CONTEXT;
		} else {
			context = TypeContext.PRIMITIVE_CONTEXT;
		}
		for (int i=0; i<PRIMITIVE_NAMES.length; i++) {
			if (primType.equals(PRIMITIVE_NAMES[i])) {
				return new NodeType(PRIMITIVE_TYPES[i], context, mutable, isArray);
			}
		}
		if ("Concept".equals(primType)) {
			return new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, context, mutable, isArray, "Concept");
		} else if ("Event".equals(primType)) {
			return new NodeType(NodeTypeFlag.GENERIC_EVENT_FLAG, context, mutable, isArray, "Event");
		} else if ("SimpleEvent".equals(primType)) {
			return new NodeType(NodeTypeFlag.GENERIC_SIMPLE_EVENT_FLAG, context, mutable, isArray, "SimpleEvent");
		} else if ("AdvisoryEvent".equals(primType)) {
			return new NodeType(NodeTypeFlag.GENERIC_ADVISORY_EVENT_FLAG, context, mutable, isArray);
		} else if ("Exception".equals(primType)) {
			return new NodeType(NodeTypeFlag.GENERIC_EXCEPTION_FLAG, context, mutable, isArray);
		} 
		if (context == TypeContext.PRIMITIVE_CONTEXT) {
			context = TypeContext.BOXED_CONTEXT;
		}
		if ("Boolean".equals(primType)) {
			return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, context, mutable, isArray);
		} else if ("Long".equals(primType)) {
			return new NodeType(NodeTypeFlag.LONG_FLAG, context, mutable, isArray);
		} else if ("Integer".equals(primType)) {
			return new NodeType(NodeTypeFlag.INT_FLAG, context, mutable, isArray);
		} else if ("Double".equals(primType)) {
			return new NodeType(NodeTypeFlag.DOUBLE_FLAG, context, mutable, isArray);
		}
		return NodeType.UNKNOWN;
	}
	
    //This method tries to support the Java sematics for method invocation conversion detailed here:
    //http://java.sun.com/docs/books/jls/second_edition/html/conversions.doc.html#12687
    //for example a long or int can be passed to a function requiring a double argument.
    protected boolean argIsCompatible(NodeType fnType, NodeType userType) {
        if(fnType.isUnknown()) return true;
        if(userType.isUnknown()) return true;
        if(fnType.isObject()) return convertibleToObject(fnType.isArray(), userType);
        if(userType.isObject()) return convertibleFromObject(userType.isArray(), fnType);
        
        if(userType.isNull()) {
            //all property object argument types accept null
            //this is true even though in the language no array properties may be set to null
            //and only non-array concept or string properties may be set to null
            return !(fnType.isValueType() && fnType.hasPrimitiveContext() && !fnType.isArray());
        }
        
        //a user supplied PropertyAtom can be coerced into a primitive atom for the function argument
        if((fnType.hasPropertyContext() != userType.hasPropertyContext()) && (userType.hasPropertyContext() && userType.isArray())) {
            return false;
        }

          //generic property is compatible with arrays or non-arrays so this has to go before the next test
        if(fnType.isGenericProperty()) {
            return true;
        }
        
        if(fnType.isArray() != userType.isArray()) {
            return false;
        }
        
        if(fnType.isTypeEqual(userType)) {
            return true;
        }
        
        // rhollom - added generic exception check, as this was failing
        if (fnType.isGenericException() && userType.isGenericException()) {
        	return fnType.isArray() == userType.isArray();
        }
//        if(fnType.isObject() && (userType.isString() || userType.isEvent() || userType.isConcept() || userType.isLong() || userType.isInt())) {
//            return true;
//        }

        if(fnType.isGenericPropertyArray() || fnType.isGenericPropertyAtom()) {
            //property / non-property and array / non-array compatibility was already checked above
            return true;
        }
        
        /*
        if(fnType.isGenericConcept()) {
            return userType.isConcept() || userType.isGenericEntity();
        }
        if(fnType.isGenericConceptReference()) {
            return userType.isConcept() || userType.isGenericEntity();
        }
        if(fnType.isGenericContainedConcept()) {
            return userType.isContainedConcept() || (userType.isGeneric() && userType.isConcept()) || userType.isGenericEntity();
        }
        if(fnType.isGenericEvent()) {
            return userType.isEvent() || userType.isGenericEntity();
        }
        if(fnType.isGenericTimeEvent()) {
            return userType.isTimeEvent() || userType.isGenericEvent();
        }
        if(fnType.isGenericSimpleEvent()) {
            return userType.isSimpleEvent() || userType.isGenericEvent();
        }
        if(fnType.isGenericEntity()) {
            return userType.isEntity();
        }
        */
        
        if(fnType.isDouble()) {
            //numerical conversion only applies for primitive atoms
            return userType.isDouble() || (!fnType.isArray() && !fnType.hasPropertyContext() && (userType.isLong() || userType.isInt()));
        }
        if(fnType.isLong()) {
            //numerical conversion only applies for primitive atoms
            return userType.isLong() || (!fnType.isArray() && !fnType.hasPropertyContext() && (userType.isInt() || userType.isDouble()));
        }
		if (fnType.isInt()) {
			// numerical conversion only applies for primitive atoms		
        	//assigning a promoted long literal to an integer should give an error so that users aren't surprised that
        	//they get MAX_INT instead of their value
            return userType.isInt() || (!fnType.isArray() && !fnType.hasPropertyContext() 
            		&& !userType.isPromotedLong() && (userType.isLong() || userType.isDouble()));
		}

		if(fnType.isContainedConcept()) {
            //todo need a more specific condition?
            return isA(fnType, userType) || isA(userType, fnType); 
        }
        if(fnType.isConcept()) {
            return isA(fnType, userType) || isA(userType, fnType);
        }
        
        if(fnType.isEvent()) {
            return isA(fnType, userType) || isA(userType, fnType);
        }

        //.isEntity is also true for concept, contained concept, event etc above.
        //therefore all tests for more specific entity types should come before this test.
        if(fnType.isEntity()) {
            return userType.isEntity();
        }
        
        return false;
    }
    
//    protected boolean checkBinaryOpCompatibility(int op, NodeType lhsType, NodeType rhsType) {
//        
//        if(lhsType == null || rhsType == null || lhsType.isUnknown() || rhsType.isUnknown()) return true;
//    
//        if(ANTLRTokenUtils.isArrayAccess(op)) {
//            return lhsType.isArray() && rhsType.isNumber() && !rhsType.isArray();
//        }
//        
//        // null == null is considered a valid comparison here
//        if(ANTLRTokenUtils.isEqualityOp(op)) {
//            //object is comparable to any type (non-reference types will be boxed).
//            if(lhsType.isObject() || rhsType.isObject()) return true;
//            //null and object are both comparable to any atom of a reference type, and arrays of all types
//            if(rhsType.isNull() && !(lhsType.isValueType() && !lhsType.hasBoxedContext() && !lhsType.isArray())) return true;
//            if(lhsType.isNull() && !(rhsType.isValueType() && !rhsType.hasBoxedContext() && !rhsType.isArray())) return true;
//            
//            if(lhsType.isArray() != rhsType.isArray()) return false;
//            if(lhsType.isArray() && (lhsType.hasPropertyContext() != rhsType.hasPropertyContext())) return false;
//            
//            if(lhsType.isTypeEqual(rhsType)) return true;
//            //autoconversion for numeric types doesn't work for arrays
//            if(!lhsType.isArray() && lhsType.isNumber() && rhsType.isNumber()) return true;
//            if(lhsType.isEntity() && rhsType.isEntity()) {
//                return isA(lhsType, rhsType) || isA(rhsType, lhsType);
//            }
//            
//            return false;
//        }
//
//        //the following operators are not compatible with arrays
//        if(lhsType.isArray() || rhsType.isArray()) return false;
//        
//        if(op == RulesParser.PLUS || op == RulesParser.PLUS_EQUAL) {
//            if(lhsType.isString()) {
//                return !rhsType.isVoid();
//            } else if(rhsType.isString() && op != RulesParser.PLUS_EQUAL) {
//                return !lhsType.isVoid();
//            }
//            // Object += String
//            else if(rhsType.isString()) {
//                return lhsType.isObject();
//            }
//        }
//        
//        if(ANTLRTokenUtils.isBinaryArithmeticOp(op) || ANTLRTokenUtils.isCompoundAssignment(op)) {
//            return 
//                    lhsType.isNumber() && rhsType.isNumber()
//                ||  lhsType.isNumber() && rhsType.isObject()
//                ||  lhsType.isObject() && rhsType.isNumber();
//        }
//        
//        //eq(==) and ne(!=) are also comparison ops but they are handled separately
//        else if(ANTLRTokenUtils.isComparisonOp(op)) {
//            return 
//            //should also allow comparison to null literal?
//                (lhsType.isNumber() && (rhsType.isNumber() || rhsType.isObject()))
//             || ((lhsType.isNumber() || lhsType.isObject()) && rhsType.isNumber())
//             || (lhsType.isString() && (rhsType.isString() || rhsType.isObject()))
//             || ((lhsType.isString() || lhsType.isObject()) && rhsType.isString())
//             || (lhsType.isDateTime() && (rhsType.isDateTime() || rhsType.isObject()))
//             || ((lhsType.isDateTime() || lhsType.isObject()) && rhsType.isDateTime());
//        }
//        else if(ANTLRTokenUtils.isBinaryLogicalOp(op)) {
//            return 
//                    lhsType.isBoolean() && (rhsType.isBoolean() || rhsType.isObject())
//                ||  (lhsType.isBoolean() ||lhsType.isObject()) && rhsType.isBoolean();
//        }       
////        reportInternalError(CompileErrors.InternalErrors.unexpectedBinaryOperator(op.image), op);
//        return false;
//    }
    
    private boolean isA(NodeType a, NodeType b) {
    	if (fBuiltInLookup.isA(a, b)) {
    		return true;
    	}
        if(a.isArray() != b.isArray()) return false;
        if(a != null && b != null && !a.isUnknown() && !b.isUnknown()) {
            if(a.isConcept() && b.isConcept()) {
            	String name1 = a.getName();
            	String name2 = b.getName();
            	name1 = ModelUtils.convertPackageToPath(name1);
            	name2 = ModelUtils.convertPackageToPath(name2);
                Concept A = IndexUtils.getConcept(fProjectName, name1);
                Concept B = IndexUtils.getConcept(fProjectName, name2);
                if(A != null && B != null) {
                    return A.isA(B);
                }
            } else if(a.isEvent() && b.isEvent()) {
            	String name1 = a.getName();
            	String name2 = b.getName();
            	name1 = ModelUtils.convertPackageToPath(name1);
            	name2 = ModelUtils.convertPackageToPath(name2);
                Event A = IndexUtils.getSimpleEvent(fProjectName, name1);
                Event B = IndexUtils.getSimpleEvent(fProjectName, name2);
                if(A != null && B != null) {
                    return A.isA(B);
                }
            }
        }
        return false;
    }

    protected boolean convertibleToObject(boolean objectArray, NodeType typ) {
    	//any type can be cast (and boxed) to Object
    	if(!objectArray) return true;
    	if(typ.isObject()) return true;
    	if(typ.isNull()) return true;
    	//PropertyXYZArray is a single object, not an array
    	if(typ.hasPropertyContext()) return false;
    	if(typ.isArray()) {
    		//can't cast int[] to Object[]
    		return !(typ.hasPrimitiveContext() && (typ.isNumber() || typ.isBoolean()));
    	}
    	return false;
    }
    
    protected boolean convertibleFromObject(boolean objectArray, NodeType typ) {
    	//Object can be cast (and unboxed) to any type
    	if(!objectArray) return true;
    	if(typ.isObject()) return true;
    	//PropertyXYZArray is a single object, not an array
    	if(typ.hasPropertyContext()) return false;
    	if(typ.isArray()) {
    		//can't cast Object[] to int[]
    		return !(typ.hasPrimitiveContext() && (typ.isNumber() || typ.isBoolean()));
    	}
    	return false;
    }

    // generic error status with no message
    private static final Status ERROR_STATUS = new Status(IMarker.SEVERITY_ERROR, StudioCorePlugin.PLUGIN_ID, null);
    // generic ok status with no message
//    private static final Status OK_STATUS = new Status(IStatus.OK, StudioCorePlugin.PLUGIN_ID, null);
    
	private IStatus equivalentTypes(NodeType lhsType, NodeType rhsType) {
		if (lhsType == null || rhsType == null) {
			return ERROR_STATUS;
		}
		if (lhsType.isArray() != rhsType.isArray() && !(lhsType.isNull() || lhsType.isObject() || rhsType.isObject() || rhsType.isNull())) {
			return ERROR_STATUS;
		}
		if (lhsType.isArray() && rhsType.isNull() || rhsType.isArray() && lhsType.isNull()) {
			return Status.OK_STATUS;
		}
		if (lhsType.isGenericPropertyArray()) {
			if (rhsType.isObject()) {
				return createBinaryTypeWarning(lhsType, rhsType);
			}
			return createStatus(rhsType.isGenericPropertyArray() || rhsType.isNull() || (rhsType.hasPropertyContext() && rhsType.isArray()));
		}
		if (rhsType.isGenericPropertyArray()) {
//			if (lhsType.isObject()) {
//				createBinaryTypeWarning(lhsType, rhsType);
//			}
			return createStatus(lhsType.isObject() || lhsType.isGenericPropertyArray() || lhsType.isNull() || (lhsType.hasPropertyContext() && lhsType.isArray()));
		}
		if (lhsType.isGenericProperty() || lhsType.isGenericPropertyAtom()) {
			if (rhsType.isObject()) {
				return createBinaryTypeWarning(lhsType, rhsType);
			}
			return createStatus(rhsType.isGenericProperty() || rhsType.isNull() || rhsType.hasPropertyContext() || rhsType.isGenericPropertyAtom());
		}
		if (rhsType.isGenericProperty() || rhsType.isGenericPropertyAtom()) {
//			if (lhsType.isObject()) {
//				createBinaryTypeWarning(lhsType, rhsType);
//			}
			return createStatus(lhsType.isObject() || lhsType.isGenericProperty() || lhsType.isNull() || lhsType.hasPropertyContext() || lhsType.isGenericPropertyAtom());
		}
		if (lhsType.isInt() || lhsType.isDouble() || lhsType.isLong()) {
			if (rhsType.isObject()) {
				return createBinaryTypeWarning(lhsType, rhsType);
			}
			if (rhsType.isArray() && lhsType.isArray()) {
				return createStatus(rhsType.isTypeEqual(lhsType));
			}
			// type2 can be an int, double, long, others?
			return createStatus(rhsType.isInt() || rhsType.isDouble() || rhsType.isLong());
		}
		if (rhsType.isInt() || rhsType.isDouble() || rhsType.isLong()) {
//			if (lhsType.isObject()) {
//				return createBinaryTypeWarning(lhsType, rhsType);
//			}
			if (rhsType.isArray() && lhsType.isArray()) {
				return createStatus(rhsType.isTypeEqual(lhsType));
			}
			// type1 can be an int, double, long, others?
			return createStatus(lhsType.isObject() || lhsType.isInt() || lhsType.isDouble() || lhsType.isLong());
		}
		if (lhsType.isBoolean()) {
			if (rhsType.isObject()) {
				return createBinaryTypeWarning(lhsType, rhsType);
			}
			return createStatus(rhsType.isBoolean());
		}
		if (rhsType.isBoolean()) {
//			if (lhsType.isObject()) {
//				return createBinaryTypeWarning(lhsType, rhsType);
//			}
			return createStatus(lhsType.isObject() || lhsType.isBoolean());
		}
		if (lhsType.isEvent()) {
			return createStatus(rhsType.isEvent() || rhsType.isNull()  || rhsType.isObject() || rhsType.isGenericEntity());
		}
		if (rhsType.isEvent()) {
			return createStatus(lhsType.isEvent() || lhsType.isNull()  || lhsType.isObject() || lhsType.isGenericEntity());
		}
		if (lhsType.isDateTime() && (rhsType.isDateTime() || rhsType.isNull() || rhsType.isObject())) {
			return Status.OK_STATUS;
		}
		if (rhsType.isDateTime() && (lhsType.isDateTime() || lhsType.isNull() || lhsType.isObject())) {
			return Status.OK_STATUS;
		}
		if (lhsType.isConcept()) {
			if (lhsType.isArray() && rhsType.isConcept()) {
				return createStatus(lhsType.hasPrimitiveContext() && rhsType.hasPrimitiveContext() || lhsType.hasPropertyContext() && rhsType.hasPropertyContext());
			}
			return createStatus(rhsType.isConcept() || rhsType.isNull() || rhsType.isObject() || rhsType.isGenericEntity());
		}
		if (rhsType.isConcept()) {
			return createStatus(lhsType.isConcept() || lhsType.isNull() || lhsType.isObject() || lhsType.isGenericEntity());
		}
		if (lhsType.isString()) {
			return createStatus(rhsType.isString() || rhsType.isNull() || rhsType.isObject());
		}
		if (rhsType.isString()) {
			return createStatus(lhsType.isString() || lhsType.isNull() || lhsType.isObject());
		}
		if (lhsType.isException()) {
			return createStatus(rhsType.isException() || rhsType.isNull() || rhsType.isObject());
		}
		if (rhsType.isException()) {
			return createStatus(lhsType.isException() || lhsType.isNull() || lhsType.isObject());
		}
		if (lhsType.isObject()) {
			return createStatus(rhsType.isNull() || rhsType.isObject());
		}
		if (rhsType.isObject()) {
			return createStatus(lhsType.isNull() || lhsType.isObject());
		}
		if (lhsType != null && !lhsType.isTypeEqual(rhsType)) {
			return ERROR_STATUS;
		}
		return Status.OK_STATUS;
	}

	
	private IStatus createStatus(boolean equivalent) {
		if (equivalent) {
			return Status.OK_STATUS;
		}
		return ERROR_STATUS;
	}

	private IStatus createBinaryTypeWarning(NodeType lhsType, NodeType rhsType) {
		if (lhsType.isUnknown() || (rhsType != null && rhsType.isUnknown())) {
			return Status.OK_STATUS; // assume this is an unresolved reference/syntax error (?)
		}
		StringBuffer buffer = new StringBuffer();
		if (rhsType != null && rhsType.isObject()) {
			buffer.append("The type \"");
			buffer.append(rhsType.getDisplayName());
			buffer.append("\" is ambiguous and could result in an error when used with type \"");
			if (lhsType != null) {
				buffer.append(lhsType.getDisplayName());
			}
			buffer.append('\"');
		} else {
			buffer.append("The type \"");
			if (lhsType != null) {
				buffer.append(lhsType.getDisplayName());
			}
			buffer.append("\" is ambiguous and could result in an error when used with type \"");
			if (rhsType != null) {
				buffer.append(rhsType.getDisplayName());
			}
			buffer.append('\"');
		}

		return new Status(IMarker.SEVERITY_WARNING, StudioCorePlugin.PLUGIN_ID, buffer.toString());
	}

	@Override
	public boolean visitRuleTemplateDeclarationNode(RulesASTNode node) {
		checkRuleName(node);
		return true;
	}

	@Override
	public boolean visitRuleDeclarationNode(RulesASTNode node) {
		checkRuleName(node);
		return true;
	}

	private void checkRuleName(RulesASTNode node) {
		if (fRuleFile == null && fResolutionContextProvider instanceof IResolutionContextProviderExtension2) {
			fRuleFile = ((IResolutionContextProviderExtension2) fResolutionContextProvider).getRuleFile();
		}
		if (fRuleFile != null) {
			// match name in source against file name
			RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
			fRuleNameNode = nameNode;
			String name = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
			IPath sourcePath = new Path(name);
			IPath relativePath = null;
			if(fRuleFile.isLinked(IResource.CHECK_ANCESTORS)){
				IResource parent = ResourceHelper.getLinkedResourceParent(fRuleFile);
				relativePath = fRuleFile.getFullPath().makeRelativeTo(parent.getFullPath());
				
			} else {
				relativePath = fRuleFile.getProjectRelativePath();
			}
			relativePath = relativePath.removeFileExtension();
			if (sourcePath.segmentCount() != relativePath.segmentCount()) {
				reportError(nameNode, "Rule package does not match rule location on disk");
				return;
			}
			for (int i=0; i<sourcePath.segmentCount(); i++) {
				if (i == sourcePath.segmentCount()-1) {
					if (!sourcePath.segment(i).equals(relativePath.segment(i))) {
						reportError(nameNode, "Rule name does not match file name on disk");
						return;
					}
					continue;
				}
				if (!sourcePath.segment(i).equals(relativePath.segment(i))) {
					reportError(nameNode, "Rule package does not match rule location on disk");
					return;
				}
			}
		}
	}

	

	@Override
	public boolean visitRuleFunctionDeclarationNode(RulesASTNode node) {
		checkRuleName(node);

		RulesASTNode modifiers = node.getChildOfType(RulesParser.MODIFIERS);
		if (modifiers == null) {
			return true;
		}
		RulesASTNode virtualNode = modifiers.getChildOfType(RulesParser.VIRTUAL);
		this.fVirtual = virtualNode != null;
		return true;
	}
	
	private void reportNonAllowedMethodCallError(String methodName, RulesASTNode node, String type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("function \"");
		buffer.append(methodName);
		buffer.append("\" is not allowed with validity \"");
		buffer.append(type);
		buffer.append("\"");
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_NON_ALLOWED_METHOD_CALL, buffer.toString(), node.getOffset(), node.getLength(), node.getLine());
		fSemanticErrors.add(error);
	}
	
	private void reportInvalidMethodCallError(String methodName, RulesASTNode node) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(methodName);
		buffer.append(" is not a method");
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_INVALID_METHOD_CALL, buffer.toString(), node.getOffset(), node.getLength(), node.getLine());
		fSemanticErrors.add(error);
	}

	private void reportInvalidBodyError(RulesASTNode node) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Virtual rule functions cannot have a body");
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_INVALID_BODY, buffer.toString(), node.getOffset(), node.getLength(), node.getLine());
		fSemanticErrors.add(error);
	}

	private void reportDuplicateVariableError(RulesASTNode nameNode, String varName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Duplicate variable \"");
		buffer.append(varName);
		buffer.append("\"");
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_DUPLICATE_VARIABLE, buffer.toString(), nameNode.getOffset(), nameNode.getLength(), nameNode.getLine());
		fSemanticErrors.add(error);
	}

	private void reportError(RulesASTNode nameNode, String message) {
		reportError(nameNode, message, IMarker.SEVERITY_ERROR);
	}
	
	private void reportWarning(RulesASTNode nameNode, String message) {
		reportError(nameNode, message, IMarker.SEVERITY_WARNING);
	}
	
	private void reportError(RulesASTNode nameNode, String message, int severity) {
		if (message == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(message);
		if(nameNode != null){
			RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_UNKNOWN, buffer.toString(), nameNode.getOffset(), nameNode.getLength(), nameNode.getLine(), severity);
			fSemanticErrors.add(error);
		}
	}
	
	private void reportAWTError(RulesASTNode nameNode, String message, int severity) {
		if (message == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(message);
		if(nameNode != null){
			RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_UNKNOWN, buffer.toString(), nameNode.getOffset(), nameNode.getLength(), nameNode.getLine(), severity);
			if (fCallback != null) {
				fCallback.errorReported(error);
			} else {
				fSemanticErrors.add(error);
			}
		}
	}

	private void reportArrayAccessError(RulesASTNode node, NodeType nodeType) {
		if (nodeType.isUnknown()) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("The type \"");
		buffer.append(nodeType.getDisplayName());
		buffer.append("\" is not an array and cannot have an array accessor");
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_ARRAY_ACCESS_ERROR, buffer.toString(), node.getOffset(), node.getLength(), node.getLine());
		fSemanticErrors.add(error);
	}

	private void reportMismatchedArgumentError(RulesASTNode methodCallNode, FunctionRec function, NodeType[] argTypes,
			List<RulesASTNode> args) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("The argument(s) \"");
		writeArguments(args, buffer);
		buffer.append("\" are not compatible with function ");
//		buffer.append(function.function.template());
		ExpandedName name = function.function.getName();
		if (name.namespaceURI != null) {
			buffer.append(name.namespaceURI);
			buffer.append('.');
		}
		buffer.append(name.getLocalName());
		buffer.append('(');
		boolean varargs = function.function.isVarargs();
		for (int i=0; i<argTypes.length; i++) {
			NodeType argType = argTypes[i];
			if (i == argTypes.length-1 && varargs) {
				buffer.append(argType.getName(false)).append("...");
			} else {
				buffer.append(argType.getDisplayName());
			}
			if (i < argTypes.length - 1) {
				buffer.append(", ");
			}
		}
		buffer.append(')');
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_MISMATCHED_ARGUMENT_ERROR, buffer.toString(), methodCallNode.getOffset(), methodCallNode.getLength(), methodCallNode.getLine());
		fSemanticErrors.add(error);
	}

	private void reportMismatchedReturnType(RulesASTNode returnNode, NodeType lhsType, NodeType rhsType, IStatus stat) {
		if (lhsType.isUnknown() || (rhsType != null && rhsType.isUnknown())) {
			return; // assume this is an unresolved reference/syntax error (?)
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("Type mismatch \"");
		if (rhsType != null) {
			buffer.append(rhsType.getDisplayName());
		}
		buffer.append("\" cannot be converted to \"");
		buffer.append(lhsType.getDisplayName());
		buffer.append('\"');
		RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_MISMATCHED_RETURN_TYPE, buffer.toString(), returnNode.getOffset(), returnNode.getLength(), returnNode.getLine());
		fSemanticErrors.add(error);
	}
	
	private void reportMismatchedBinaryTypes(RulesASTNode binaryNode, int opType, NodeType lhsType, NodeType rhsType, IStatus stat) {
		if (lhsType.isUnknown() || (rhsType != null && rhsType.isUnknown())) {
			return; // assume this is an unresolved reference/syntax error (?)
		}
		if (stat.getMessage() != null && stat.getMessage().length() > 0) {
			RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_MISMATCHED_BINARY_TYPE, stat.getMessage(), binaryNode.getOffset(), binaryNode.getLength(), binaryNode.getLine(), stat.getSeverity());
			fSemanticErrors.add(error);
		} else if (opType == RulesParser.ASSIGN) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("The type \"");
			buffer.append(rhsType.getDisplayName());
			buffer.append("\" cannot be assigned to type \"");
			buffer.append(lhsType.getDisplayName());
			buffer.append('\"');
			RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_MISMATCHED_BINARY_TYPE, buffer.toString(), binaryNode.getOffset(), binaryNode.getLength(), binaryNode.getLine(), stat.getSeverity());
			fSemanticErrors.add(error);
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append("The operator \"");
			buffer.append(getOpText(opType));
			buffer.append('\"');
			buffer.append(" is not compatible with types \"");
			buffer.append(lhsType.getDisplayName());
			if (rhsType != null) {
				buffer.append(',');
				buffer.append(rhsType.getDisplayName());
			}
			buffer.append('\"');
			RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_MISMATCHED_BINARY_TYPE, buffer.toString(), binaryNode.getOffset(), binaryNode.getLength(), binaryNode.getLine(), stat.getSeverity());
			fSemanticErrors.add(error);
		}
	}
	
	private void reportImmutableAssignmentError(RulesASTNode binaryNode, int opType, NodeType lhsType, NodeType rhsType) {
		if (lhsType.isUnknown() || (rhsType != null && rhsType.isUnknown())) {
			return; // assume this is an unresolved reference/syntax error (?)
		}
		if (opType == RulesParser.ASSIGN) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("The assignment target is immutable and cannot accept assignments");
			RulesSemanticError error = new RulesSemanticError(IProblemTypes.PROBLEM_IMMUTABLE_ASSIGNMENT_ERROR, buffer.toString(), binaryNode.getOffset(), binaryNode.getLength(), binaryNode.getLine());
			fSemanticErrors.add(error);
		}
	}
	
	@Override
	public boolean visitBodyBlockNode(RulesASTNode blockNode) {
		fSectionType = IRulesSourceTypes.ACTION_SOURCE;
		RulesASTNode node = blockNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> statements = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		if (statements.size() > 0 && fVirtual) {
			reportInvalidBodyError(node.getFirstChild());
			return false;
		}
		return true;
	}

	@Override
	public boolean visitThenBlockNode(RulesASTNode node) {
		fSectionType = IRulesSourceTypes.ACTION_SOURCE;

		RulesASTNode blockNode = node.getChildByFeatureId(RulesParser.BLOCK);
		if (blockNode == null || blockNode.getChildren() == null || blockNode.getChildren().size() == 0) {
			reportError(node, "Empty action section not allowed");
		}
		return super.visitThenBlockNode(node);
	}

	@Override
	public boolean visitWhenBlockNode(RulesASTNode node) {
		RuleElement ruleElement = getRuleElement(node);
		if (ruleElement != null && ruleElement.getElementType() == ELEMENT_TYPES.RULE_TEMPLATE) {
			fSectionType = IRulesSourceTypes.PRE_CONDITION_SOURCE;
			return super.visitWhenBlockNode(node);
		}
		fSectionType = IRulesSourceTypes.CONDITION_SOURCE;
		return super.visitWhenBlockNode(node);
	}

	@Override
	public boolean visitActionContextNode(RulesASTNode node) {
		fSectionType = IRulesSourceTypes.ACTION_CONTEXT_SOURCE;
		return super.visitActionContextNode(node);
	}

	@Override
	public boolean visitBlockNode(RulesASTNode node) {
		return super.visitBlockNode(node);
	}

	private String getOpText(int opType) {

		switch (opType) {
		case RulesParser.PLUS: return "+";
		case RulesParser.MINUS: return "-";
		case RulesParser.MULT: return "*";
		case RulesParser.DIVIDE: return "/";
		case RulesParser.MOD: return "%";
		case RulesParser.PLUS_EQUAL: return "+=";
		case RulesParser.MINUS_EQUAL: return "-=";
		case RulesParser.MULT_EQUAL: return "*=";
		case RulesParser.DIV_EQUAL: return "/=";
		case RulesParser.MOD_EQUAL: return "%=";
		case RulesParser.INCR: return "++";
		case RulesParser.DECR: return "--";
		case RulesParser.LT: return "<";
		case RulesParser.GT: return ">";
		case RulesParser.LE: return "<=";
		case RulesParser.GE: return ">=";
		case RulesParser.NE: return "!=";
		case RulesParser.EQ: return "==";
		case RulesParser.AND: return "&&";
		case RulesParser.OR: return "||";
		case RulesParser.POUND: return "!";
		case RulesParser.INSTANCE_OF: return "instanceof";

		default:
			System.out.println("something else");
			break;
		}
		return new RulesParser(null).getTokenNames()[opType];
	}

	@Override
	public boolean visitLocalVariableDeclNode(RulesASTNode node) {
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		List<RulesASTNode> decls = node.getChildrenByFeatureId(RulesParser.DECLARATORS);
		Object b = typeNode.getData("array");
		boolean array = false;
		if (b instanceof Boolean) {
			array = (Boolean) b; 
		}
		if (fSectionType == IRulesSourceTypes.PRE_CONDITION_SOURCE) {
			reportError(node, "Local variable declarations are not allowed in the pre-conditions section");
			return false;
		}
		NodeType declType = getNodeType(typeNode, array, true);
		for (RulesASTNode declNode : decls) {
			if (checkVariableDeclaratorNode(declType, declNode)) {
				RulesASTNode initializer = declNode.getChildByFeatureId(RulesParser.INITIALIZER);
				if (initializer != null) {
					initializer.accept(this);
					// validate individual types inside of the initializer
					List<RulesASTNode> exps = initializer.getChildrenByFeatureId(RulesParser.EXPRESSIONS);
					for (int i = 0; i < exps.size(); i++) {
						RulesASTNode initNode = exps.get(i);
						NodeType initType = getNodeType(initNode, false);
						IStatus stat = equivalentTypes(declType.getComponentType(true), initType);
						if (!stat.isOK()) {
							reportMismatchedBinaryTypes(initNode, RulesParser.ASSIGN, declType.getComponentType(true), initType, stat);
							return false;
						}
						checkPromotedIntLiteral(declType, initType, initNode);
					}
				}
				RulesASTNode expression = declNode.getChildByFeatureId(RulesParser.EXPRESSION);
				if (expression != null) {
					expression.accept(this);
				}
				
			}
		}
		
		return false;
	}

	@Override
	public boolean visitSimpleNameNode(RulesASTNode node) {
//		String name = RuleGrammarUtils.getSimpleNameFromNode(node);
//		if (isReservedKeyword(name)) {
//			reportError(node, "Reserved keyword, cannot be used as an identifier");
//		}
		return true;
	}

	private boolean isReservedKeyword(String name) {
		if (Arrays.binarySearch(RESERVED_KEYWORD_NAMES, name) >= 0) {
			return true;
		}
		return false;
	}

	private boolean isInvalidScopeType(String name) {
		if (Arrays.binarySearch(INVALID_GENERIC_SCOPE_TYPES, name) >= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean visitDeclaratorNode(RulesASTNode node) {
		// the checks are the same, just delegate to visitScopeDeclaratorNode
		return visitScopeDeclaratorNode(node);
	}
	
	@Override
	public boolean visitTemplateDeclaratorNode(RulesASTNode node) {
		// the checks are the same, just delegate to visitScopeDeclaratorNode
		visitScopeDeclaratorNode(node);
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		Object b = typeNode.getData("array");
		boolean array = false;
		if (b instanceof Boolean) {
			array = (Boolean) b; 
		}
		NodeType declType = getNodeType(typeNode, array, true);

		if (!checkAssignment(declType, node)) {
			return false;
		}
		return false;
	}

	private boolean checkAssignment(NodeType declType, RulesASTNode node) {
		RulesASTNode rhs = node.getChildByFeatureId(RulesParser.EXPRESSION);
		if (rhs != null) {
			NodeType rhsType = getNodeType(rhs);
			
			if (!matchBinaryType(node, RulesParser.ASSIGN, declType, rhsType, rhs)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean visitDeclareBlockNode(RulesASTNode node) {
		RulesASTNode blockNode = node.getChildByFeatureId(RulesParser.BLOCK);
		if (blockNode == null || blockNode.getChildren() == null || blockNode.getChildren().size() == 0) {
			reportError(node, "Empty declare section not allowed");
		}
		return true;
	}
	
	

	@Override
	public boolean visitBindingStatementNode(RulesASTNode node) {
		visitScopeDeclaratorNode(node);
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		Object b = typeNode.getData("array");
		boolean array = false;
		if (b instanceof Boolean) {
			array = (Boolean) b; 
		}
		NodeType declType = getNodeType(typeNode, array, true);

		checkAssignment(declType, node);
		checkDomain(declType, node);
		
		return false;
	}

	private void checkDomain(NodeType declType, RulesASTNode node) {
		RulesASTNode domainNode = node.getChildByFeatureId(RulesParser.DOMAIN_MODEL);
		if (domainNode != null) {
			// TODO : check domain statement
			RulesASTNode nameNode = domainNode.getChildByFeatureId(RulesParser.NAME);
			String varName = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.NAME_FORMAT);
			EObject reference = RuleGrammarUtils.getElementReference(nameNode);
			Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, fResolutionContextProvider.getResolutionContext((ElementReference) reference));
			if (element == null) {
				reportError(nameNode, "Cannot resolve domain");
			} else if (element instanceof EntityElement) {
				element = ((EntityElement) element).getEntity();
			}
			if (element instanceof Domain) {
				Domain domain = (Domain) element;
				DOMAIN_DATA_TYPES dataType = domain.getDataType();
				NodeType domainType = getDomainType(dataType);
				matchBinaryType(domainNode, RulesParser.ASSIGN, declType, domainType, null);
			}
		}
		
	}

	private NodeType getDomainType(DOMAIN_DATA_TYPES dataType) {
		switch (dataType) {
		case BOOLEAN:
			return fGenericTypes[BOOLEAN_FLAG];

		case DATE_TIME:
			return fGenericTypes[DATETIME_FLAG];
			
		case DOUBLE:
			return fGenericTypes[DOUBLE_FLAG];
			
		case INTEGER:
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
			
		case LONG:
			return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
			
		case STRING:
			return fGenericTypes[STRING_FLAG];
			
		default:
			break;
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean visitScopeDeclaratorNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		String varName = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.NAME_FORMAT);
		if (isReservedKeyword(varName)) {
			reportError(nameNode, "Reserved keyword, cannot be used as an identifier");
		}
		EObject reference = RuleGrammarUtils.getElementReference(nameNode);
		if (reference != null) {
			if (reference instanceof GlobalVariableDef) {
				RuleElement element = (RuleElement) reference.eContainer();
				checkDuplicateVariable(nameNode, varName, element.getScope());
				checkUnusedVariable(nameNode, varName, element.getScope(), true);
				String type = ((GlobalVariableDef) reference).getType();
				if (isInvalidScopeType(type)) {
					String primType;
					if ("Integer".equals(type)) {
						primType = "int";
					} else {
						primType = type.toLowerCase();
					}
					if (element.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
						reportError(node.getChildByFeatureId(RulesParser.TYPE), "Invalid scope type.  Use '"+primType+"' instead");
					} else {
						reportError(node.getChildByFeatureId(RulesParser.TYPE), "Invalid declaration type.");
					}
				}
				
				if (type.indexOf('.') == -1) {
					// unqualified reference, check whether multiple elements with this name exist
					List<DesignerElement> allElements = IndexUtils.getAllElements(fProjectName, type);
					if (allElements != null && allElements.size() > 1) {
						reportAmbiguousReferenceError(node.getChildByFeatureId(RulesParser.TYPE), allElements);
					}
				}
			} else if (reference instanceof LocalVariableDef) {
				RuleElement element = (RuleElement) RuleGrammarUtils.getRootNode(node).getData("element");
				checkDuplicateVariable(nameNode, varName, element.getScope());
				checkUnusedVariable(nameNode, varName, element.getScope(), false);
			} else {
				ScopeBlock scope = RuleGrammarUtils.getScope((ElementReference)reference);
				checkDuplicateVariable(nameNode, varName, scope);
				checkUnusedVariable(nameNode, varName, scope, false);
			}
		}
		return false;
	}

    @Override
	public boolean visitRankStatementNode(RulesASTNode node) {
    	RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		String varName = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		DesignerElement element = IndexUtils.getElement(fProjectName, varName);
		if (element instanceof RuleElement) {
			Compilable rule = ((RuleElement) element).getRule();
			if (rule != null && !"double".equals(rule.getReturnType())) {
				reportError(nameNode, "Rank rule function must return a double");
			}
			if (rule instanceof RuleFunction) {
				com.tibco.cep.designtime.core.model.rule.Validity validity = ((RuleFunction) rule).getValidity();
				if (validity != com.tibco.cep.designtime.core.model.rule.Validity.CONDITION
						&& validity != com.tibco.cep.designtime.core.model.rule.Validity.QUERY) {
					reportError(nameNode, "Invalid Rank.  Rank rule function is not allowed with validity '"+validity.getName()+"'");
				}
			}
			checkRankParameters(node, rule);
		} else if (element != null) {
			reportError(nameNode, "Invalid Rank.  Rank must be a rule function");
		} else {
			reportError(nameNode, "Invalid/Unknown Rank");
		}
		return false;
	}

	private boolean checkRankParameters(RulesASTNode node, Compilable rule) {
		Object data = node.getRoot().getData("element");
		if (data instanceof RuleElement) {
			List<GlobalVariableDef> globalVariables = ((RuleElement) data).getGlobalVariables();
			
			List<GlobalVariableDef> globalVars = new ArrayList<GlobalVariableDef>();
			for(GlobalVariableDef globalVariableDef : globalVariables) {
				if(!(globalVariableDef instanceof BindingVariableDef)) {
					globalVars.add(globalVariableDef);
				}
			}
			
			// the parameters for the rank rule function must match the parameters for the rule
			Symbols symbols = rule.getSymbols();	
			if (symbols != null && symbols.getSymbolList().size() == globalVars.size()) {
				EList<Symbol> symbolList = symbols.getSymbolList();
				for (int i = 0; i < symbolList.size(); i++) {
					Symbol s = symbolList.get(i);
					GlobalVariableDef globalVariableDef = globalVars.get(i);
					String symbolType = s.getType();
					symbolType = ModelUtils.convertPathToPackage(symbolType);
					if (isPrimitiveType(symbolType)) {
						reportError(node, "Invalid Rank.  Rank rule function can not have primitive datatypes in its scope");
						return false;
					}
					String scopeType = globalVariableDef.getType();
					if (!symbolType.equals(scopeType)) {
						reportError(node, "Invalid Rank.  Rank rule function scope must match the rule declaration");
						return false;
					}
				}
			} else if (symbols != null && symbols.getSymbolList().size() != globalVars.size()) {
				reportError(node, "Invalid Rank.  Rank rule function scope must match the rule declaration");
				return false;
			}
		}
		return true;
	}

	private boolean isPrimitiveType(String symbolType) {
		for (int i=0; i<PRIMITIVE_NAMES.length; i++) {
			if (symbolType.equals(PRIMITIVE_NAMES[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean visitCatchRuleNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.IDENTIFIER);
		String varName = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.NAME_FORMAT);
		if (isReservedKeyword(varName)) {
			reportError(nameNode, "Reserved keyword, cannot be used as an identifier");
		}
		EObject reference = RuleGrammarUtils.getElementReference(nameNode);
		if (reference != null) {
			if (reference instanceof VariableDefinition) {
				ScopeBlock scope = (ScopeBlock) reference.eContainer();
				checkDuplicateVariable(nameNode, varName, scope);
				checkUnusedVariable(nameNode, varName, scope, reference instanceof GlobalVariableDef);
			} else {
				ScopeBlock scope = RuleGrammarUtils.getScope((ElementReference)reference);
				checkDuplicateVariable(nameNode, varName, scope);
				checkUnusedVariable(nameNode, varName, scope, false);
			}
		}
		return super.visitCatchRuleNode(node);
	}

	private boolean checkVariableDeclaratorNode(NodeType declType,
			RulesASTNode declNode) {
		RulesASTNode nameNode = declNode.getChildByFeatureId(RulesParser.NAME);
		String varName = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.NAME_FORMAT);
		if (isReservedKeyword(varName)) {
			reportError(nameNode, "Reserved keyword, cannot be used as an identifier");
		}

		EObject reference = RuleGrammarUtils.getElementReference(nameNode);
		if (reference != null) {
			if (reference instanceof VariableDefinition) {
				ScopeBlock scope = (ScopeBlock) reference.eContainer();
				checkDuplicateVariable(nameNode, varName, scope);
				checkUnusedVariable(nameNode, varName, scope, reference instanceof GlobalVariableDef);
			} else {
				ScopeBlock scope = RuleGrammarUtils.getScope((ElementReference)reference);
				checkDuplicateVariable(nameNode, varName, scope);
				checkUnusedVariable(nameNode, varName, scope, false);
			}
		}
		RulesASTNode rhs = declNode.getChildByFeatureId(RulesParser.EXPRESSION);
		if (rhs == null) {
			return true;
		}
		NodeType rhsType = getNodeType(rhs);
		
		if (!matchBinaryType(declNode, RulesParser.ASSIGN, declType, rhsType, rhs)) {
			return false;
		}
		return true;
	}

	private boolean checkDuplicateVariable(RulesASTNode nameNode, String varName, ScopeBlock scope) {
		EList<LocalVariableDef> defs = scope.getDefs();
		for (int i=0; i<defs.size(); i++) {
			LocalVariableDef def = defs.get(i);
			if (def.getOffset() < nameNode.getOffset() && varName.equals(def.getName())) {
				reportDuplicateVariableError(nameNode, varName);
				return false;
			}
		}
		if (scope.getParentScopeDef() != null) {
			return checkDuplicateVariable(nameNode, varName, scope.getParentScopeDef());
		}
		if (scope.eContainer() instanceof RuleElement) {
			RuleElement ruleElement = (RuleElement) scope.eContainer();
			EList<GlobalVariableDef> globalVariables = ruleElement.getGlobalVariables();
			for (int i=0; i<globalVariables.size(); i++) {
				GlobalVariableDef def = globalVariables.get(i);
				if (def.getOffset() != nameNode.getOffset() && varName.equals(def.getName())) {
					reportDuplicateVariableError(nameNode, varName);
					return false;
				}
			}
			
		}
		return true;
	}

	private boolean checkUnusedVariable(RulesASTNode nameNode, String varName, ScopeBlock scope, boolean globalVar) {
		if (isVirtual(nameNode)) {
			return true;
		}
		
		if (!variableReferenced(nameNode, varName, scope)) {
			if (globalVar) {
				reportWarning(nameNode, String.format("The declaration '%s' is unused", varName));
			} else {
				reportWarning(nameNode, String.format("The local variable '%s' is unused", varName));
			}
		}
		return true;
	}

	private boolean isVirtual(RulesASTNode node) {
		while (node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		List<RulesASTNode> modifiers = node.getFirstChild().getChildrenByFeatureId(RulesParser.MODIFIERS);
		if (modifiers != null && modifiers.size() > 0) {
			for (RulesASTNode modifier : modifiers) {
				if (modifier.getType() == RulesParser.VIRTUAL) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean variableReferenced(RulesASTNode nameNode, String varName, ScopeBlock scope) {
		EList<ElementReference> refs = scope.getRefs();
		for (int i=0; i<refs.size(); i++) {
			ElementReference ref = refs.get(i);
			if (ref.isMethod()) {
				Object resolveElement = ElementReferenceResolver.resolveElement(ref, fResolutionContextProvider.getResolutionContext(ref));
				if (resolveElement instanceof JavaStaticFunctionWithXSLT) {
					((JavaStaticFunctionWithXSLT) resolveElement).getMapperType();
					RulesASTNodeFinder finder = new RulesASTNodeFinder(ref.getOffset());
					RulesASTNode rootNode = RuleGrammarUtils.getRootNode(nameNode);
					rootNode.accept(finder);
					RulesASTNode foundNode = finder.getFoundNode();
					while (foundNode.getParent() != null) {
						if (foundNode.getType() == RulesParser.METHOD_CALL) {
							break;
						}
						foundNode = (RulesASTNode) foundNode.getParent();
					}
					if (foundNode != null) {
						String xslt = getXsltFromMethodNode(foundNode);
						if (xslt != null) {
							if (xslt.contains('$'+varName)) {
								return true;
							}
						}
					}
				}
				continue;
			}
			while (ref.getQualifier() != null) {
				ref = ref.getQualifier();
			}
			if (ref.getOffset() > nameNode.getOffset() && varName.equals(ref.getName())) {
				return true;
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (int i=0; i<childScopeDefs.size(); i++) {
			ScopeBlock scopeBlock = childScopeDefs.get(i);
			if (variableReferenced(nameNode, varName, scopeBlock)) {
				return true;
			}
		}
		return false;
	}

	private void writeArguments(List<RulesASTNode> args, StringBuffer buffer) {
		for (int i = 0; i < args.size(); i++) {
			RulesASTNode arg = args.get(i);
			NodeType type = getNodeType(arg);
			buffer.append(type.getDisplayName());
			if (i < args.size()-1) {
				buffer.append(",");
			}
		}
	}

	public List<IRulesProblem> getSemanticErrors() {
		return fSemanticErrors;
	}

	public Object resolveASTNode(RulesASTNode node) {
		EObject reference = RuleGrammarUtils.getElementReference(node);
		if (reference == null) {
			return false;
		}
		if (reference instanceof VariableDefinition) {
			return reference;
		}
		if (reference instanceof ElementReference) {
//			if ("length".equals(((ElementReference) reference).getName()) && node.getParent().getType() == RulesParser.SUFFIXES && node.getParent().getParent() instanceof RulesASTNode) {
//				try {
//					// FIXME : Workaround for "mystring"@length attribute access, since "mystring" does not create an ElementReference
//					RulesASTNode par = (RulesASTNode) node.getParent().getParent();
//					RulesASTNode firstChild = par.getFirstChild();
//					if (firstChild.getFirstChild().getType() == RulesParser.StringLiteral) {
//						// create a pseudo ElementReference for this case, as string literals do not have a corresponding ElementReference
//						String name = firstChild.getFirstChild().getText();
//						ElementReference ref = IndexFactory.eINSTANCE.createElementReference();
//						ref.setName(name);
//						ref.setBinding("String");
//						ref.setOffset(firstChild.getOffset());
//						ref.setLength(firstChild.getLength());
//						((ElementReference)reference).setQualifier(ref);
//					}
//				} catch (Exception e) {
//				}
//			}
			Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, fResolutionContextProvider.getResolutionContext((ElementReference) reference));
			node.setBinding(element);
			if (((ElementReference) reference).isAttRef()) {
				node.setData("attr", Boolean.TRUE);
			}
			return element;
		}
		return null;
	}

	@Override
	public boolean visitActionContextStatementNode(RulesASTNode node) {
		visitScopeDeclaratorNode(node);
		RulesASTNode actionTypeNode = node.getChildByFeatureId(RulesParser.ACTION_TYPE);
		if ("modify".equals(actionTypeNode.getText())) {
			RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
			String nameAsString = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.NAME_FORMAT);
			RuleElement ruleElement = getRuleElement(node);
			EList<GlobalVariableDef> globalVariables = null;
			boolean found = false;
			
			//If in Rule Template Form View check for global variable from  IGlobalVariablesProvider
			if( fResolutionContextProvider instanceof IGlobalVariablesProvider) {
				IGlobalVariablesProvider globalVariablesProvider = (IGlobalVariablesProvider) fResolutionContextProvider;
				if(globalVariablesProvider.isGlobalVariable(nameAsString)) {
					found = true;
				}
			} else {
				globalVariables = ruleElement.getGlobalVariables();
				for (int i=0; i<globalVariables.size(); i++) {
					GlobalVariableDef def = globalVariables.get(i);
					if (def.getName().equals(nameAsString)) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				reportError(nameNode, "Unable to resolve '"+nameAsString+"'");
			}
		}
		return false;
	}

	@Override
	public boolean visitIfRuleBlock(RulesASTNode node) {
		// check whether both 'if' and 'else' statements have return statements.  If so, mark any subsequent code as unreachable.
		// for now, only check top-level 'if' rules.  It would be better if we also check nested if rules, but this should catch 95% of the cases
		int parentType = 0;
		if (node.getParent() == null) {
			parentType = RulesParser.BODY_BLOCK;
		} else {
			parentType = node.getParent().isNil() || node.getParent().getParent().getParent() == null ? RulesParser.BODY_BLOCK : node.getParent().getParent().getParent().getType();
		}
		if (!(parentType == RulesParser.BODY_BLOCK || parentType == RulesParser.THEN_BLOCK)) {
			return true;
		}
		RulesASTNode ifBody = node.getFirstChildWithType(RulesParser.STATEMENT);
		RulesASTNode elseBody = node.getFirstChildWithType(RulesParser.ELSE_STATEMENT);
		if (elseBody == null || elseBody.getChildByFeatureId(RulesParser.BLOCK) == null) {
			// no 'else' statement, no need to continue
			return true;
		}
		RulesASTNode ifBlock = ifBody.getChildByFeatureId(RulesParser.BLOCK);
		RulesASTNode ifRet = null;
		if (ifBlock == null) {
			ifRet = ifBody.getChildByFeatureId(RulesParser.RETURN_STATEMENT);
		} else {
			ifRet = ifBlock.getChildByFeatureId(RulesParser.RETURN_STATEMENT);	
		}
		
		RulesASTNode elBlock = elseBody.getChildByFeatureId(RulesParser.BLOCK);
		RulesASTNode elseRet = null;
		if (elBlock == null) {
			elseRet = elseBody.getChildByFeatureId(RulesParser.RETURN_STATEMENT);
		} else {
			elseRet = elBlock.getChildByFeatureId(RulesParser.RETURN_STATEMENT);	
		}
		if (ifRet != null && elseRet != null) {
			// mark unreachable code
			fUnreachableCode = true;
			return false;
		}
		
		return true;
	}
	
	
	
}
