package com.tibco.cep.studio.core.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeFactory;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesLexer;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public abstract class BaseRulesParser extends Parser {

	// The different types of names
	public static final int TYPE_METHOD_REF = 0;
	public static final int TYPE_RULE_DEF = 1;
	public static final int TYPE_REF = 2;
	public static final int TYPE_ARRAY_REF = 3;
	public static final int TYPE_VIEW_REF = 4;
	public static final int DOMAIN_REF = 5;
	
	// The different types of Scope blocks
	public static final int RULE_SCOPE = 0;
	public static final int BLOCK_SCOPE = 1;
	public static final int BODY_SCOPE = 2;
	public static final int THEN_SCOPE = 3;
	public static final int WHEN_SCOPE = 4;
	public static final int FOR_SCOPE = 5;
	public static final int ATTRIBUTE_SCOPE = 6;
	public static final int ACTION_CONTEXT_SCOPE = 7;
	public static final int BINDINGS_SCOPE = 8;
	public static final int BINDING_VIEWS_SCOPE = 9;
	public static final int DECLARE_SCOPE = 10;
	public static final int SCOPE_SCOPE = 11;
	public static final int IF_SCOPE = 12;
	public static final int CATCH_SCOPE = 13;
	public static final int DISPLAY_SCOPE = 14;
	
	protected int arrayDepth = 0;
	protected boolean inActionContextBlock = false;
	
	public boolean isInActionContextBlock() {
		return inActionContextBlock;
	}

	public void setInActionContextBlock(boolean b) {
		inActionContextBlock = b;
	}
	
	private List<IProblemHandler> fProblemCollectors = new ArrayList<IProblemHandler>();
	private Stack<ScopeBlock> fActiveScope;
	private RuleElement fRuleElement = IndexFactory.eINSTANCE.createRuleElement();
	
	private int level = 0;
	private ElementReference fLastReference;
	private ElementReference[] fReferenceArr = new ElementReference[100];
	private ScopeBlock fLastPoppedScope;
	private boolean fTypeReference;

	public BaseRulesParser(TokenStream arg0) {
		super(arg0);
		init();
	}

	public BaseRulesParser(TokenStream arg0, RecognizerSharedState arg1) {
		super(arg0, arg1);
		init();
	}

	private void init() {
		fActiveScope = new Stack<ScopeBlock>();
		RootScopeBlock rootScope = ScopeFactory.eINSTANCE.createRootScopeBlock();
		rootScope.setType(RULE_SCOPE);
		fActiveScope.push(rootScope);
		fRuleElement.setScope(rootScope);
	}

	@Override
	public void reportError(RecognitionException exception) {
		super.reportError(exception);

		RulesSyntaxProblem problem = new RulesSyntaxProblem(
				getErrorMessage(exception, getTokenNames()),
				exception);
		for (IProblemHandler collector : fProblemCollectors) {
			collector.handleProblem(problem);
		}
	}

	@Override
	public String getErrorMessage(RecognitionException ex, String[] tokenNames) {
		if (ex instanceof NoViableAltException) {
			if (ex.getUnexpectedType() == RulesLexer.EOF) {
				return "Unexpected End Of File.  Please enter a valid statement";
			}
		}
		if (ex instanceof MismatchedTokenException) {
			if (ex.getUnexpectedType() == RulesLexer.EOF && ((MismatchedTokenException)ex).expecting == RulesParser.SEMICOLON) {
				return "Unexpected End Of File.  Please enter a semicolon to complete the statement";
			}
		}
		if (ex instanceof MismatchedSetException) {
			if (((MismatchedSetException) ex).expecting == null) {
				return "Unexpected/invalid token '" + ex.token.getText() + "'";
			}
		}
		return super.getErrorMessage(ex, tokenNames);
	}

	public void addProblemCollector(IProblemHandler collector) {
		if (!fProblemCollectors.contains(collector)) {
			fProblemCollectors.add(collector);
		}
	}
	
	public void removeProblemCollector(IProblemHandler collector) {
		if (fProblemCollectors.contains(collector)) {
			fProblemCollectors.remove(collector);
		}
	}

	public Token getLastNonWhitespaceToken() {
		TokenStream stream = getTokenStream();
        int size = stream.index();
        for (int i = size-1; i >= 0; i--) {
            Token tkn = stream.get(i);
            if (tkn.getChannel() != 99) {
                return tkn;
            }
        }
        return null;
	}
	
	public CommonToken getCurrentToken() {
		TokenStream stream = getTokenStream();
		int size = ((CommonTokenStream)stream).index();
		for (int i = size-1; i >= 0; i--) {
			Token tkn = stream.get(i);
			if (tkn instanceof CommonToken) {
				return (CommonToken) tkn;
			}
		}
		return null;
	}
	
	public void pushScope(int type) {
		ScopeBlock scope = ScopeFactory.eINSTANCE.createScopeBlock();
		scope.setType(type);
		scope.setParentScopeDef(fActiveScope.lastElement());
		fActiveScope.push(scope);
		level++;
		Token token = getLastNonWhitespaceToken();
		if (token instanceof CommonToken) {
			scope.setOffset(((CommonToken)token).getStartIndex());
//			System.out.println("pushing at "+((CommonToken)token).getStartIndex()+" type "+scope.getType());
		}
	}
	
	public void popScope() {
		Stack<ScopeBlock> stack = fActiveScope;
		while (stack.size() > 0 && !(stack.lastElement() instanceof ScopeBlock)) {
			stack.pop();
		}
		if (stack.size() > 0 && stack.lastElement() instanceof ScopeBlock) {
			fLastPoppedScope = stack.pop();
			Token token = getLastNonWhitespaceToken();
			if (token instanceof CommonToken) {
//				System.out.println("popping at "+((CommonToken)token).getStopIndex() + " type "+fLastPoppedScope.getType());
				fLastPoppedScope.setLength(((CommonToken)token).getStopIndex()-fLastPoppedScope.getOffset());
				fRuleElement.getScope().setLength(((CommonToken)token).getStopIndex());
			}
		}
		level--;
	}
	
	public void pushGlobalScopeName(RulesASTNode node, RulesASTNode type) {
//		properly index these
		if (node == null) {
			return;
		}
		String nameString = RuleGrammarUtils.getFullNameFromNode(node, RuleGrammarUtils.NAME_FORMAT);
		String typeString = RuleGrammarUtils.getFullNameFromNode(type, RuleGrammarUtils.NAME_FORMAT);
		boolean array = false;
		if (type.getData("array") != null) {
			array = true;
		}
		GlobalVariableDef definition = createDefinition(nameString, typeString, node.getOffset(), node.getLength(), array);
//		System.out.println(getTabs(level)+"Pushing GLOBAL SCOPE: " + definition.toString());
		fRuleElement.getGlobalVariables().add(definition);
	}
	
	public void pushBindingScopeName(RulesASTNode node, RulesASTNode type) {
		if (node == null) {
			return;
		}
		String nameString = RuleGrammarUtils.getFullNameFromNode(node, RuleGrammarUtils.NAME_FORMAT);
		String typeString = RuleGrammarUtils.getFullNameFromNode(type, RuleGrammarUtils.NAME_FORMAT);
		boolean array = false;
		if (type.getData("array") != null) {
			array = true;
		}
		GlobalVariableDef definition = createBindingDefinition(nameString, typeString, node.getOffset(), node.getLength(), array);
		fRuleElement.getGlobalVariables().add(definition);
	}
	
	private LocalVariableDef createLocalDefinition(String name, String type,
			int offset, int length, boolean array) {
		LocalVariableDef definition = IndexFactory.eINSTANCE.createLocalVariableDef();
		definition.setName(name);
		definition.setType(type);
		definition.setOffset(offset);
		definition.setLength(length);
		definition.setArray(array);
		return definition;
	}
	
	private GlobalVariableDef createDefinition(String name, String type,
			int offset, int length, boolean array) {
		GlobalVariableDef definition = IndexFactory.eINSTANCE.createGlobalVariableDef();
		definition.setName(name);
		definition.setType(type);
		definition.setOffset(offset);
		definition.setLength(length);
		definition.setArray(array);
		return definition;
	}
	
	private GlobalVariableDef createBindingDefinition(String name, String type,
			int offset, int length, boolean array) {
		GlobalVariableDef definition = IndexFactory.eINSTANCE.createBindingVariableDef();
		definition.setName(name);
		definition.setType(type);
		definition.setOffset(offset);
		definition.setLength(length);
		definition.setArray(array);
		return definition;
	}


//	private ElementReference createElementReference(RulesASTNode nameNode) {
//		return createElementReference(nameNode, false);
//	}
	
	private ElementReference createElementReference(RulesASTNode nameNode) {
		if (nameNode.getType() == RulesParser.QUALIFIED_NAME) {
			ElementReference reference = IndexFactory.eINSTANCE.createElementReference();
			RulesASTNode qualifierNode = nameNode.getChildByFeatureId(RulesParser.QUALIFIER);
			if (RuleGrammarUtils.isName(qualifierNode)) {
				// recursively index qualifier
				reference.setQualifier(createElementReference(qualifierNode));
			}
			RulesASTNode simpleNode = nameNode.getFirstChildWithType(RulesParser.SIMPLE_NAME);
			String name = RuleGrammarUtils.getNameAsString(simpleNode, RuleGrammarUtils.NAME_FORMAT);
			reference.setName(name);
			reference.setOffset(simpleNode.getOffset());
			reference.setLength(simpleNode.getLength());
			if (simpleNode.getData("method") != null) {
				reference.setMethod(true);
			}
			if (isTypeReference()) {
				reference.setTypeRef(true);
			}
			return reference;
		} else {
			ElementReference reference = IndexFactory.eINSTANCE.createElementReference();
			String name = RuleGrammarUtils.getNameAsString(nameNode, RuleGrammarUtils.NAME_FORMAT);
			reference.setName(name);
			reference.setOffset(nameNode.getOffset());
			reference.setLength(nameNode.getLength());
			if (nameNode.getData("method") != null) {
				reference.setMethod(true);
			}
			if (isTypeReference()) {
				reference.setTypeRef(true);
			}
			return reference;
		}
	}
	
	private boolean isTypeReference() {
		return this.fTypeReference;
	}

	public void setTypeReference(boolean typeRef) {
		this.fTypeReference = typeRef;
	}
	
	public void setRuleElementType(ELEMENT_TYPES type) {
		fRuleElement.setElementType(type);
	}
	
	protected void defineRule(RulesASTNode node) {
		node = node.isNil() ? node.getFirstChild() : node;

		String folder = RuleGrammarUtils.getQualifierFromNode(node, RuleGrammarUtils.FOLDER_FORMAT);
		String name = RuleGrammarUtils.getSimpleNameFromNode(node);
		fRuleElement.setFolder(folder);
		fRuleElement.setName(name);
		
		ElementReference ref = createElementReference(node);
		fRuleElement.getScope().setDefinitionRef(ref);

	}
	
	public void pushDefineName(RulesASTNode node, RulesASTNode type) {
		if (node == null || type == null) {
			// can't properly index, just return
			System.err.println("Cannot index name, improper syntax in rule");
			return;
		}
		String nameString = RuleGrammarUtils.getFullNameFromNode(node, RuleGrammarUtils.NAME_FORMAT);
		String typeString = RuleGrammarUtils.getFullNameFromNode(type, RuleGrammarUtils.NAME_FORMAT);
		boolean array = false;
		if (type.getData("array") != null) {
			array = true;
		}
		LocalVariableDef definition = createLocalDefinition(nameString, typeString, node.getOffset(), node.getLength(), array);
		fActiveScope.lastElement().getDefs().add(definition);
	}
	
	protected void pushName(int type, RulesASTNode node) {
		switch (type) {
		case TYPE_REF:
		{
			pushNameReference(node);
			break;
		}

		case TYPE_METHOD_REF:
		{
			ElementReference ref = pushNameReference(node);
			ref.setMethod(true);
			break;
		}
		
		case TYPE_ARRAY_REF:
		{
			ElementReference ref = pushNameReference(node);
			ref.setArray(true);
			break;
		}
			
		case TYPE_RULE_DEF:
		{
			defineRule(node);
//			ElementReference ref = pushNameReference(node);
			break;
		}
			
		default:
			pushNameReference(node);
			break;
		}
	}
	
	protected void pushFieldAccessReference(RulesASTNode node, boolean attributeReference) {
		if (node == null) {
			return;
		}
		RulesASTNode nameNode = node.isNil() ? node.getFirstChild() : node;
		ElementReference ref = createElementReference(nameNode);
		if (ref != null) {
			fActiveScope.lastElement().getRefs().add(ref);
			if (fReferenceArr[arrayDepth] != null) {
				ref.setQualifier(fReferenceArr[arrayDepth]);
			}
//			if (attributeReference && "length".equals(ref.getName())) {
//				if (getTokenStream().index() > 3 && getTokenStream().LA(-3) == RulesParser.StringLiteral) {
//					Token token = getTokenStream().get(getTokenStream().index() - 3);
//					ElementReference reference = IndexFactory.eINSTANCE.createElementReference();
//					reference.setBinding("String");
//					reference.setOffset(((CommonToken)token).getStartIndex());
//					reference.setOffset(((CommonToken)token).getStopIndex() - ((CommonToken)token).getStartIndex());
//					reference.setName(token.getText());
//				}
//			}
			fReferenceArr[arrayDepth] = ref;
			ref.setAttRef(attributeReference);
			return;
		}
	}
	
//	private ElementReference pushNameReference(RulesASTNode node) {
//		return pushNameReference(node, false);
//	}
	
	private ElementReference pushNameReference(RulesASTNode node) {
		if (node == null) {
			return null;
		}
		RulesASTNode nameNode = node.isNil() ? node.getFirstChild() : node;
		ElementReference ref = createElementReference(nameNode);
		if (ref != null) {
			fActiveScope.lastElement().getRefs().add(ref);
			fReferenceArr[arrayDepth] = ref;
			return ref;
		}
		return null;
	}
	
	public void markAsArray(RulesASTNode node) {
		if (node == null) {
			return;
		}
		node.setData("array", Boolean.TRUE);
		ElementReference reference = fReferenceArr[arrayDepth];
		if (reference != null) {
			if (reference.getOffset() == node.getOffset()) {
				reference.setArray(true);
			} else if (reference.getQualifier() != null) {
				ElementReference rootRef = getRootQualifier(reference);
				if (rootRef.getOffset() == node.getOffset()) {
					reference.setArray(true);
				}
			}
		}
	}
	
	private ElementReference getRootQualifier(ElementReference ref) {
		if (ref.getQualifier() != null) {
			return getRootQualifier(ref.getQualifier());
		}
		return ref;
	}
	
	protected void markAsMethod(RulesASTNode node) {
		if (node.getType() == RulesParser.QUALIFIED_NAME) {
			node = node.getFirstChildWithType(RulesParser.SIMPLE_NAME);
		}
		node.setData("method", Boolean.TRUE);
	}
	
	protected void popName() {
		
	}
	
	public ScopeBlock getScope() {
		return fActiveScope.firstElement();
	}
	
	public ScopeBlock getLocalScope() {
		return fActiveScope.lastElement();
	}
	
	public ScopeBlock getLastPoppedScope() {
		return fLastPoppedScope;
	}
	
	public RuleElement getRuleElement() {
		return fRuleElement;
	}

	public void setRuleElement(RuleElement element) {
		fRuleElement = element;
		init();
	}
	
	public abstract RulesASTNode getHeaderNode();
//	{
//		return ((RulesLexer)getTokenStream().getTokenSource()).getHeaderNode();
//	}

	public abstract void setTreeAdaptor(TreeAdaptor treeAdaptor);

	public ParserRuleReturnScope startRule() throws RecognitionException {
		// overridden in concrete impl parsers
		return null;
	}
	
}
