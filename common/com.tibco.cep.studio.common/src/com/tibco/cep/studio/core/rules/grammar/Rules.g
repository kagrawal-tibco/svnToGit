grammar Rules;

options {
language=Java;
output=AST;
superClass=BaseRulesParser;
ASTLabelType=RulesASTNode;
//backtrack=true;
}

import BaseElement, BaseLexer;

tokens {
	RULE_TEMPLATE; RULE_TEMPLATE_DECL; TEMPLATE_DECLARATOR; BINDINGS_BLOCK; BINDING_VIEW_STATEMENT; BINDINGS_VIEWS_BLOCK; BINDING_STATEMENT;
	DOMAIN_MODEL; DOMAIN_MODEL_STATEMENT; ACTION_CONTEXT_BLOCK; ACTION_CONTEXT_STATEMENT; ACTION_TYPE;
	QUALIFIER; QUALIFIED_NAME; SIMPLE_NAME; LOCAL_VARIABLE_DECL; DECLARATORS; VARIABLE_DECLARATOR;
	RULE; RULE_FUNCTION; RULE_DECL; NAME; RULE_BLOCK; ATTRIBUTE_BLOCK; STATEMENTS;
	DISPLAY_BLOCK; DISPLAY_PROPERTY; DECLARE_BLOCK; RULE_FUNC_DECL; SCOPE_BLOCK; WHEN_BLOCK; PREDICATES; BLOCKS;
	THEN_BLOCK; BODY_BLOCK; SCOPE_DECLARATOR; TYPE; DECLARATOR; VIRTUAL; PRIMITIVE_TYPE; 
	ANNOTATION; ARGS; METHOD_CALL; PRIMARY_ASSIGNMENT_EXPRESSION; SUFFIX; EXPRESSION;
	IF_RULE; WHILE_RULE; FOR_RULE; ELSE_STATEMENT; STATEMENT; RETURN_STATEMENT; FOR_LOOP;
	RETURN_TYPE; MAPPING_BLOCK; PRIORITY_STATEMENT; PRIORITY; VALIDITY_STATEMENT; VALIDITY; RANK_STATEMENT;
	REQUEUE_STATEMENT; FORWARD_CHAIN_STATEMENT; BACKWARD_CHAIN_STATEMENT; LASTMOD_STATEMENT; ALIAS_STATEMENT;
	LITERAL; PREDICATE_STATEMENT; RANGE_EXPRESSION; RANGE_START;
	RANGE_END; SET_MEMBERSHIP_EXPRESSION; EXPRESSIONS; BREAK_STATEMENT; CONTINUE_STATEMENT;
	THROW_STATEMENT; TRY_RULE; BLOCK; CATCH_RULE; IDENTIFIER; FINALLY_RULE; ARRAY_ACCESS_SUFFIX;
	INITIALIZER; LOCAL_INITIALIZER; ARRAY_LITERAL; ARRAY_ALLOCATOR; TRY_STATEMET; BODY; CATCH_CLAUSE;
	FINALLY_CLAUSE; TRY_STATEMENT; HEADER; VOID_LITERAL; MODIFIERS; BINARY_RELATION_NODE; UNARY_EXPRESSION_NODE;
	LHS; RHS; OPERATOR; PRIMARY_EXPRESSION; PREFIX; SUFFIXES; ASSIGNMENT_SUFFIX; SUFFIX_EXPRESSION;
}

@rulecatch {
catch (RecognitionException re) {
    reportError(re);
    recover(input,re);
}
catch (RewriteEmptyStreamException e) {
	// do nothing for now
	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
	// e.printStackTrace();
}
}

@members {
protected boolean virtual = false;
protected RulesASTNode headerTree = null;

public void setRuleElementType(ELEMENT_TYPES type) {
	gBaseElement.setRuleElementType(type);
}

public void setTypeReference(boolean typeRef) {
	gBaseElement.setTypeReference(typeRef);
}
	
public void markAsArray(RulesASTNode node) {
	gBaseElement.markAsArray(node);
}

public void popScope() {
	gBaseElement.popScope();
}

public void pushScope(int type) {
	gBaseElement.pushScope(type);
}

public boolean isInActionContextBlock() {
	return gBaseElement.isInActionContextBlock();
}

public void setInActionContextBlock(boolean b) {
	gBaseElement.setInActionContextBlock(b);
}

public void pushGlobalScopeName(RulesASTNode node, RulesASTNode type) {
	gBaseElement.pushGlobalScopeName(node, type);
}
	
public void pushDefineName(RulesASTNode node, RulesASTNode type) {
	gBaseElement.pushDefineName(node, type);
}

public void addProblemCollector(IProblemHandler collector) {
	super.addProblemCollector(collector);
	gBaseElement.addProblemCollector(collector);
}

public RuleElement getRuleElement() {
	return gBaseElement.getRuleElement();
}

public void setRuleElement(RuleElement element) {
	gBaseElement.setRuleElement(element);
}

public CommonToken getCurrentToken() {
	return gBaseElement.getCurrentToken();
}

public ScopeBlock getLastPoppedScope() {
	return gBaseElement.getLastPoppedScope();
}

public ScopeBlock getScope() {
	return gBaseElement.getScope();
}

public ScopeBlock getLocalScope() {
	return gBaseElement.getLocalScope();
}

	// THIS METHOD IS UNIQUE TO EACH GRAMMAR, AS THE LEXER CLASS IS UNIQUE
	public RulesASTNode getHeaderNode()
	{
		return ((RulesLexer)getTokenStream().getTokenSource()).getHeaderNode();
	}
}

@header { 
package com.tibco.cep.studio.core.rules.grammar; 

import com.tibco.cep.studio.core.rules.*; 
import com.tibco.cep.studio.core.rules.ast.*; 
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
}

@lexer::members {

public void setProblemHandler(IProblemHandler handler) {
	gBaseLexer.setProblemHandler(handler);
}

public RulesASTNode getHeaderNode() {
	return gBaseLexer.getHeaderNode();
}
}
/***********************************************
 * THE Business Event Rule Grammar Begins here *
 ***********************************************/

/*
 * Program structuring syntax follows.
 */

startRule
	:	compilationUnit
	;
	
compilationUnit
	:	
		HeaderSection?
		(ruleFunctionDeclaration mappingBlock?
			-> ^(RULE_FUNCTION["rule function"] ruleFunctionDeclaration 
				^(MAPPING_BLOCK mappingBlock)?)
		| ruleDeclaration mappingBlock?
			-> ^(RULE["rule"] ruleDeclaration 
				^(MAPPING_BLOCK mappingBlock)?)
		| ruleTemplateDeclaration mappingBlock?
			-> ^(RULE_TEMPLATE["rule_template"] ruleTemplateDeclaration 
				^(MAPPING_BLOCK mappingBlock)?))* EOF
	;

mappingBlock
	: '<#mapping>' (~MappingEnd)* MappingEnd
	;

conditionBlock 
	:	predicateStatement* EOF
	;
	
actionBlock
	:	thenStatements EOF
	;
	
standaloneExpression
	:	predicate EOF
	;
	
standaloneThenStatement 
	:	thenStatement EOF
	;
	
ruleFunctionBody
	:	thenStatements EOF
	;
	
identifier
	:	(Identifier | keywordIdentifier) -> ^(SIMPLE_NAME Identifier? keywordIdentifier?)
	;

keywordIdentifier
	: 'body' | 'validity' | 'null' | 'lock' | 'exists' | 'and' | 'key' | 'alias' | 'rank' | 'virtual' |
		'abstract' | 'byte' | 'case' | 'char' | 'class' | 'const' | 'default' | 'do' | 'extends' | 'final' | 
		'float' | 'goto' | 'implements' | 'import' | 'interface' | 'moveto' | 'native' | 'new' | 'package' |
		'private' | 'protected' | 'public' | 'short' | 'static' | 'strictfp' | 'super' | 'switch' | 
		'synchronized' | 'this' | 'throws' | 'transient' | 'volatile' | 'create' | 'modify' | 'call' |
		'views' | 'bindings' | 'display' | 'event' | 'concept'
;

xsltLiteral
	: '""xslt://' '"' '"'
	;
	
ruleDeclaration
	: 'rule' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleNT RBRACE
	{ setRuleElementType(ELEMENT_TYPES.RULE); }
	-> ^(RULE_DECL["rule"] ^(NAME name) ^(BLOCKS ruleNT))
	;

ruleTemplateDeclaration
	: 'ruletemplate' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruletemplateNT RBRACE
	{ setRuleElementType(ELEMENT_TYPES.RULE_TEMPLATE); }
	-> ^(RULE_TEMPLATE_DECL["ruleTemplate"] ^(NAME name) ^(BLOCKS ruletemplateNT))
	;

ruleFunctionDeclaration
	: ('virtual' { virtual=true;})? returnType? 'rulefunction' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleFunctionNT RBRACE
	{ setRuleElementType(ELEMENT_TYPES.RULE_FUNCTION); }
	-> ^(RULE_FUNC_DECL["ruleFunction"] ^(MODIFIERS ^(VIRTUAL 'virtual')?) ^(NAME name) ^(RETURN_TYPE returnType?) ^(BLOCKS ruleFunctionNT))
	;

returnType
	: { setTypeReference(true); } n=nameOrPrimitiveType { setTypeReference(false); } ( '['! ']'! { markAsArray((RulesASTNode) n.getTree()); } )?
		| voidLiteral
	;

voidLiteral
	: 'void' -> ^(VOID_LITERAL 'void')
	;
	
/** 
 * RuleNT (NT => Non Terminal Identifier. The RuleNT is specified as
 * Rule := "(AttributeNT | DeclareNT | WhenNT | ThenNT )*
 * The grammar does have a weakness that you can declare Attribute n nos of times, 
 * the Whenblock can be declared n nos
 * time. Need to change the grammar at some time.
 */
 
ruleNT	:	
	(attributeNT
	| declareNT
	| whenNT
	| thenNT)*
	;
	
ruleFunctionNT	:	
	(attributeNT
	| scopeNT
	| bodyNT)*
	;

ruletemplateNT
	: (attributeNT
	| bindingViewsNT
	| bindingsNT
	| displayNT
	| variablesNT
	| preConditionsNT
	| actionContextNT)*
	;

bindingViewsNT
	: 'views' bindingViewsNTBlock
	-> ^(BINDINGS_VIEWS_BLOCK["views"] bindingViewsNTBlock)
	;

bindingViewsNTBlock
	:  LBRACE { pushScope(BaseRulesParser.BINDING_VIEWS_SCOPE); } bindingViewStatement*
		RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS bindingViewStatement*))
	;

bindingViewStatement
	: { setTypeReference(true); } name[BaseRulesParser.TYPE_VIEW_REF] { setTypeReference(false); } SEMICOLON
	-> ^(BINDING_VIEW_STATEMENT["viewReference"] ^(NAME name))
	;
	
bindingsNT
	: 'bindings' bindingsNTBlock
	-> ^(BINDINGS_BLOCK["bindings"] bindingsNTBlock)
	;

bindingsNTBlock
	:  LBRACE { pushScope(BaseRulesParser.BINDINGS_SCOPE); } bindingStatement*
		RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS bindingStatement*))
	;

bindingStatement
	: { setTypeReference(true); } t=nameOrPrimitiveType { setTypeReference(false); } id=identifier
		{ pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); }
	(ASSIGN  ((localInitializerArrayLiteral (';' | ','))=>localInitializerArrayLiteral
	| expression))? domainModelStatement? SEMICOLON
	-> ^(BINDING_STATEMENT["binding"] ^(TYPE["type"] $t)
		^(NAME["name"] $id) ^(INITIALIZER localInitializerArrayLiteral)?
		^(EXPRESSION expression)? ^(DOMAIN_MODEL["domain model"] domainModelStatement)?)
	;
	
domainModelStatement
	: LPAREN { setTypeReference(true); } name[BaseRulesParser.DOMAIN_REF] { setTypeReference(false); } RPAREN
	-> ^(DOMAIN_MODEL_STATEMENT ^(NAME name))
	;
	
/*
constraint
	: rangeConstraint | nullConstraint
	;
	
rangeConstraint
	: min=integerLiteral 'to' max=integerLiteral
	->^(RANGE_CONSTRAINT["range"] ^(MIN["min"] $min) ^(MAX["max"] $max))
	;
	
nullConstraint
	: 'not' 'null'
	->^(NULL_CONSTRAINT["not null"])
	;
*/

actionContextNT
	: 'actionContext' actionContextNTBlock
	-> ^(ACTION_CONTEXT_BLOCK["action context"] actionContextNTBlock)
	;

actionContextNTBlock
	:  LBRACE { pushScope(BaseRulesParser.ACTION_CONTEXT_SCOPE); setInActionContextBlock(true); } actionContextStatements
		RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS actionContextStatements?))
	;

actionContextStatements
	: acStatement*
	;
	
acStatement
	: actionContextStatement | thenStatement
	;
	
actionContextStatement
	: (t='call' m=methodName id=identifier { pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode)m.getTree()); }| 
	 ((t='create' { setTypeReference(true); } nm=name[BaseRulesParser.TYPE_REF] { setTypeReference(false); } id=identifier { pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode) nm.getTree()); }
	 | t='modify' id=identifier)))
		SEMICOLON
		-> ^(ACTION_CONTEXT_STATEMENT["actionContext"] ^(ACTION_TYPE["action type"] $t) ^(TYPE["type"] $m? $nm?) ^(NAME["name"] $id))
	;
/*
callableStatement
	: 'call' m=methodName id=identifier SEMICOLON
		-> ^(CALLABLE_STATEMENT["call"] ^(TYPE["type"] $m) ^(NAME["name"] $id))
	;

creatableStatement
	: 'create' { setTypeReference(true); } nm=name[BaseRulesParser.TYPE_REF] { setTypeReference(false); } 
			id=identifier SEMICOLON
		-> ^(CREATABLE_STATEMENT["create"] ^(TYPE["type"] $nm) ^(NAME["name"] $id))
	;

modifiableStatement
	: 'modify' { setTypeReference(true); } nm=name[BaseRulesParser.TYPE_REF] { setTypeReference(false); } 
			id=identifier SEMICOLON
		-> ^(MODIFIABLE_STATEMENT["modify"] ^(TYPE["type"] $nm) ^(NAME["name"] $id))
	;
*/
preConditionsNT
	: 'when' preConditionsNTBlock
	-> ^(WHEN_BLOCK["when"] preConditionsNTBlock)
	;

preConditionsNTBlock
	:  LBRACE { pushScope(BaseRulesParser.WHEN_SCOPE); } preconditionStatements
		RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS preconditionStatements?))
	;

preconditionStatements
	: (preconditionStatement)*
	;
	
preconditionStatement
	: (predicateStatement)=> predicateStatement | thenStatement
	;
	
displayNT
	: 'display' displayNTBlock
	-> ^(DISPLAY_BLOCK["display"] displayNTBlock)
	;
	
displayNTBlock
	: LBRACE { pushScope(BaseRulesParser.DISPLAY_SCOPE); } displayProperty* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS["displayProperties"] displayProperty*))
	;

displayProperty
	: name[TYPE_REF] ASSIGN StringLiteral SEMICOLON
		-> ^(DISPLAY_PROPERTY ^(NAME name) ^(LITERAL StringLiteral))
	;
	
variablesNT
	: 'declare' variablesNTBlock
	-> ^(DECLARE_BLOCK["declare"] variablesNTBlock)
	;

variablesNTBlock
	: LBRACE { pushScope(BaseRulesParser.DECLARE_SCOPE); } templateDeclarator* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS["declarators"] templateDeclarator*))
	;

templateDeclarator
	: { setTypeReference(true); } t=nameOrPrimitiveType { setTypeReference(false); } id=identifier
		{ pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); }
	(ASSIGN  ((localInitializerArrayLiteral (';' | ','))=>localInitializerArrayLiteral
	| expression))? SEMICOLON
	-> ^(TEMPLATE_DECLARATOR["declarator"] ^(TYPE["type"] $t)
		^(NAME["name"] $id) ^(INITIALIZER localInitializerArrayLiteral)?
		^(EXPRESSION expression)?)
	;

attributeNT
	: 'attribute' attributeNTBlock
	-> ^(ATTRIBUTE_BLOCK["attribute"] attributeNTBlock)
	;
	
attributeNTBlock : LBRACE { pushScope(BaseRulesParser.ATTRIBUTE_SCOPE); }
					attributeBodyDeclaration* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS attributeBodyDeclaration*))
	;
	
attributeBodyDeclaration
	: ('priority' ASSIGN p=integerLiteral SEMICOLON
		-> ^(PRIORITY_STATEMENT ^(PRIORITY $p))
	| 'forwardChain' ASSIGN booleanLiteral SEMICOLON // only valid for rules...
		-> ^(FORWARD_CHAIN_STATEMENT ^(LITERAL booleanLiteral))
	| 'backwardChain' ASSIGN booleanLiteral SEMICOLON // only valid for rules...
		-> ^(BACKWARD_CHAIN_STATEMENT ^(LITERAL booleanLiteral))
	| 'requeue' ASSIGN booleanLiteral SEMICOLON
		-> ^(REQUEUE_STATEMENT ^(LITERAL booleanLiteral))
	| '$lastmod' ASSIGN StringLiteral SEMICOLON
		-> ^(LASTMOD_STATEMENT ^(LITERAL StringLiteral))	
	| 'validity' ASSIGN v=validityType SEMICOLON
		-> ^(VALIDITY_STATEMENT ^(VALIDITY $v))
	| 'alias' ASSIGN StringLiteral SEMICOLON
		-> ^(ALIAS_STATEMENT ^(LITERAL StringLiteral))
	| 'rank' ASSIGN { setTypeReference(true); } r=name[BaseRulesParser.TYPE_REF] { setTypeReference(false); } SEMICOLON
		-> ^(RANK_STATEMENT ^(NAME $r)))
	;

validityType
	: 'ACTION' | 'CONDITION' | 'QUERY'
	;
	
declareNT
	: 'declare' declareNTBlock
	-> ^(DECLARE_BLOCK["declare"] declareNTBlock)
	;
	
declareNTBlock : LBRACE { pushScope(BaseRulesParser.DECLARE_SCOPE); } declarator* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS["declarators"] declarator*))
	;

scopeNT
	: 'scope' scopeNTBlock
	-> ^(SCOPE_BLOCK["scope"] scopeNTBlock)
	;

scopeNTBlock : LBRACE { pushScope(BaseRulesParser.SCOPE_SCOPE); } scopeDeclarator* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS["declarators"] scopeDeclarator*))
	;
	
scopeDeclarator
	: { setTypeReference(true); } t=nameOrPrimitiveType { setTypeReference(false); } 
	 ( '[' ']' { markAsArray((RulesASTNode) t.getTree()); } )? id=identifier SEMICOLON
		{ pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); } 
	-> ^(SCOPE_DECLARATOR["declarator"] ^(TYPE["type"] $t)
		^(NAME["name"] $id))
	;

declarator
	: { setTypeReference(true); } nm=name[BaseRulesParser.TYPE_REF] { setTypeReference(false); } id=identifier SEMICOLON
		{ pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)nm.getTree()); }
	-> ^(DECLARATOR["declarator"] ^(TYPE["type"] $nm)
		^(NAME["name"] $id))
	;
	
whenNT	: 'when' whenNTBlock
	-> ^(WHEN_BLOCK["when"] whenNTBlock)
	;

whenNTBlock : LBRACE { pushScope(BaseRulesParser.WHEN_SCOPE); } predicateStatements
		RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS predicateStatements?))
	;
	
/******************
******* Then Body Specification begins
*/
thenNT	: 'then' thenNTBlock
	-> ^(THEN_BLOCK["then"] thenNTBlock)
	;
	
thenNTBlock : LBRACE { pushScope(BaseRulesParser.THEN_SCOPE); } thenStatements 
		RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS thenStatements*))
	;
	
bodyNT	: 'body' bodyNTBlock
	-> ^(BODY_BLOCK["body"] bodyNTBlock)
	;
	
bodyNTBlock : LBRACE { pushScope(BaseRulesParser.BODY_SCOPE); } thenStatements
		 RBRACE
		{ popScope(); } 
	-> ^(BLOCK ^(STATEMENTS thenStatements*))
	;
	
thenStatements
	:	thenStatement*
	;
	
thenStatement
	: ({ isInActionContextBlock() }? actionContextStatement)=> actionContextStatement
	| (type identifier)=>localVariableDeclaration
	| statement 
	| emptyBodyStatement
	;
	
statement
	: lineStatement | blockStatement
	;
	
lineStatement
	: emptyStatement
	| statementExpression
	| returnStatement
	| breakStatement
	| continueStatement
	| throwStatement
	| { isInActionContextBlock() }? => actionContextStatement
	;
	
blockStatement
	: ifRule
	| whileRule
	| forRule
	| block
	| tryCatchFinally
	;
	
// Overridden from BaseElement.g
block	: LBRACE { pushScope(BLOCK_SCOPE); } 
		( ({ isInActionContextBlock() }? actionContextStatement)=> actionContextStatement | (type identifier)=> localVariableDeclaration | statement)* RBRACE
		{ popScope(); } 
		-> ^(BLOCK ^(STATEMENTS actionContextStatement* localVariableDeclaration* statement*))
	;
