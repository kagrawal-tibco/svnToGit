parser grammar BaseRules;

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
	DECLARE_BLOCK; RULE_FUNC_DECL; SCOPE_BLOCK; WHEN_BLOCK; PREDICATES; BLOCKS;
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
}

/***********************************************
 * THE Business Event Rule Grammar Begins here *
 ***********************************************/

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
		( ({ inActionContextBlock }? actionContextStatement)=> actionContextStatement | (type identifier)=> localVariableDeclaration | statement)* RBRACE
		{ popScope(); } 
		-> ^(BLOCK ^(STATEMENTS actionContextStatement* localVariableDeclaration* statement*))
	;
