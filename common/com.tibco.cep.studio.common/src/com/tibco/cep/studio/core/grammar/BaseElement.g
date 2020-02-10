parser grammar BaseElement;

options {
language=Java;
output=AST;
superClass=BaseRulesParser;
ASTLabelType=RulesASTNode;
//backtrack=true;
}

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

@members {
	public RulesASTNode getHeaderNode() {
		return null; // root grammars will return the proper value
	}
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

/***********************************************
 * THE Business Event Base Element Grammar Begins here *
 ***********************************************/

/*
 * Program structuring syntax follows.
 */

mappingBlock
	: '<#mapping>' (~MappingEnd)* MappingEnd
	;

conditionBlock 
	:	predicateStatement* EOF
	;
	
standaloneExpression
	:	predicate EOF
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
	
voidLiteral
	: 'void' -> ^(VOID_LITERAL 'void')
	;
	
predicateStatements
	: predicateStatement*
	;
	
predicateStatement
	: (predicate SEMICOLON -> ^(PREDICATE_STATEMENT ^(EXPRESSION predicate))
	| emptyStatement -> emptyStatement)
	;

predicate
	: expression
	;
	
expression
	: conditionalAndExpression
	( OR^ conditionalAndExpression)*
	;
	
conditionalAndExpression
	: equalityExpression
	( AND^ equalityExpression)*
	;
	
equalityExpression
	: (relationalExpression
	| (domainSpec)=>domainSpec 
	| comparisonNoLHS)
	( 
		(EQ^ | NE^)
		(relationalExpression
		| (domainSpec)=>domainSpec
	| comparisonNoLHS)
	)*
	;
	
comparisonNoLHS
	: (
		(EQ^ | NE^)
		(domainSpec)=>domainSpec
		| unaryExpression
	) |
	( INSTANCE_OF^ type ) |
	( (LT^ | GT^ | LE^ | GE^) additiveExpression)
	;
	
domainSpec
	: rangeExpression | setMembershipExpression
	;
	
rangeExpression
	: rangeStart start=expression? ',' end=expression? rangeEnd
		-> ^(RANGE_EXPRESSION ^(RANGE_START $start?) ^(RANGE_END $end))
	;

rangeStart
	: '(' | '[' { arrayDepth++; }
	;

rangeEnd
	: ')' | ']' { arrayDepth--; }
	;
	
setMembershipExpression
	: LBRACE (exp+=expression (',' exp+=expression)*)? RBRACE
		-> ^(SET_MEMBERSHIP_EXPRESSION ^(EXPRESSIONS $exp*))
	;

relationalExpression
	: lhs=additiveExpression
	(
		((LT | GT | LE | GE)^ rhs=additiveExpression
		| INSTANCE_OF^ type)
		//-> ^(BINARY_RELATION_NODE ^(LHS $lhs) ^(RHS $rhs) ^(OPERATOR $op))
	)*
	;
	
additiveExpression
	: multiplicativeExpression
	( (PLUS | MINUS)^ rhs=multiplicativeExpression
	//-> ^(BINARY_RELATION_NODE ^(LHS $additiveExpression) ^(RHS $rhs) ^(OPERATOR $op))
	)* 
	;
	
multiplicativeExpression
	: unaryExpression
	( (MULT | DIVIDE | MOD)^ rhs=unaryExpression
	//-> ^(BINARY_RELATION_NODE ^(LHS $multiplicativeExpression) ^(RHS $rhs) ^(OPERATOR $op))
	)* 
	;
	
unaryExpression
	: (op=PLUS | op=MINUS | op=POUND) unaryExpression 
		-> ^(UNARY_EXPRESSION_NODE ^(EXPRESSION unaryExpression) ^(OPERATOR $op))
	| primaryExpression
	;
	
primaryExpression
	: p=primaryPrefix (s+=primarySuffix[(RulesASTNode) p.getTree()])*
	-> ^(PRIMARY_EXPRESSION ^(PREFIX $p) ^(SUFFIXES $s*))
	;
	
primaryPrefix
	: literal
	| '('! expression ')'!
	| (type LBRACE)=>arrayLiteral
	| (arrayAllocator)=>arrayAllocator
	| (methodName '(')=>m=methodName argumentsSuffix { markAsMethod((RulesASTNode) m.getTree()); }
		-> ^(METHOD_CALL ^(NAME $m) ^(ARGS argumentsSuffix?))
	| expressionName
	;
	
primarySuffix[RulesASTNode type]
	: arrayAccessSuffix { markAsArray(type); } | fieldAccessSuffix
	;
	
arrayAccessSuffix
	: '[' { arrayDepth++; } expression ']' { arrayDepth--; }
		-> ^(ARRAY_ACCESS_SUFFIX ^(EXPRESSION expression))
	;
	
fieldAccessSuffix
@init { 
boolean attributeRef = false; 
}
	: (DOT! | ANNOTATE! { attributeRef = true; }) id=identifier { pushFieldAccessReference((RulesASTNode)id.getTree(), attributeRef); }
	;
	
argumentsSuffix 
	: '('! { arrayDepth++; } argumentList? ')'! { arrayDepth--; } 
	;
	
argumentList
	: expression (','! expression)*
	| xsltLiteral
	;
	
literal
	: integerLiteral
	| FloatingPointLiteral
	| StringLiteral
	| booleanLiteral
	| NullLiteral
	;

integerLiteral
	: DecimalLiteral
	| HexLiteral
	//| OctalLiteral
	;
	
booleanLiteral
	: TRUE | FALSE
	;

emptyStatement
	: SEMICOLON
	;
	
emptyBodyStatement
	: WS
	;	
	
//statement
//	: statementExpression
//	;
	
/* The only difference between this rule and listStatementExpression is
  the trailing SEMICOLON.  We need to have separate rules (for now), as 
  in some cases the SEMICOLON needs to be part of the token boundary for
  the AST node.  Otherwise, offsets/refactorings get messed up */
statementExpression
	: (methodName '(')=>m=methodName argumentsSuffix
	( primarySuffix[(RulesASTNode) m.getTree()]* (assignOp=assignmentOp expression | (op=INCR | op=DECR)))? SEMICOLON
		-> ^(METHOD_CALL ^(NAME $m) ^(ARGS argumentsSuffix?)
			^(SUFFIX primarySuffix*) ^(EXPRESSION expression?) ^(OPERATOR $op? $assignOp?))
	| primaryExpression (assignOp=assignmentOp expression | (op=INCR | op=DECR)) SEMICOLON
		-> ^(PRIMARY_ASSIGNMENT_EXPRESSION ^(LHS primaryExpression) ^(RHS expression?) ^(OPERATOR $op? $assignOp?))
	;
	
listStatementExpression
	: (methodName '(')=>m=methodName argumentsSuffix
	( primarySuffix[(RulesASTNode) m.getTree()]* (assignOp=assignmentOp expression | (op=INCR | op=DECR)))?
		-> ^(METHOD_CALL ^(NAME $m) ^(ARGS argumentsSuffix?)
			^(SUFFIX primarySuffix*) ^(EXPRESSION expression?) ^(OPERATOR $op? $assignOp?))
	| primaryExpression (assignOp=assignmentOp expression | (op=INCR | op=DECR))
		-> ^(PRIMARY_ASSIGNMENT_EXPRESSION ^(LHS primaryExpression) ^(RHS expression?) ^(OPERATOR $op? $assignOp?))
	;
	
assignmentOp 
	:	ASSIGN | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL
	;
	
returnStatement
	: 'return' (expression SEMICOLON | SEMICOLON)
		-> ^(RETURN_STATEMENT ^(EXPRESSION expression?))
	;
	
breakStatement
	: 'break' SEMICOLON
		-> ^(BREAK_STATEMENT 'break')
	;
	
continueStatement
	: 'continue' SEMICOLON
		-> ^(CONTINUE_STATEMENT)
	;
	
throwStatement
	: 'throw' expression SEMICOLON
		-> ^(THROW_STATEMENT ^(EXPRESSION expression))
	;
	
/*
block	: LBRACE { pushScope(BLOCK_SCOPE); } 
		( (type identifier)=> localVariableDeclaration | statement)* RBRACE
		{ popScope(); } 
		-> ^(BLOCK ^(STATEMENTS localVariableDeclaration* statement*))
	;
*/	
statementExpressionList
	: listStatementExpression (','! listStatementExpression)*
	;
	
ifRule	: 'if' '(' expression ')' { pushScope(IF_SCOPE); } ifst=statement { popScope(); }
	('else' { pushScope(IF_SCOPE); } elst=statement { popScope(); })?
	-> ^(IF_RULE ^(EXPRESSION expression) ^(STATEMENT $ifst) ^(ELSE_STATEMENT $elst?))
	;
	
whileRule : 'while' '(' expression ')' statement
	-> ^(WHILE_RULE ^(EXPRESSION expression) ^(STATEMENT statement))
	;
	
forRule	: 'for' '(' { pushScope(FOR_SCOPE); }
	( (type identifier)=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement)
	(expression)? SEMICOLON
	(statementExpressionList)? ')' 
		statement
	{ popScope(); }
		-> ^(FOR_LOOP localVariableDeclaration? statementExpressionList* 
		emptyStatement? expression? statement)
	;
	
tryCatchFinally
	: 'try' block 
	( catchRule finallyRule? // is more than one catch rule allowed, as in java?
	| finallyRule)
		-> ^(TRY_STATEMENT ^(BODY block) ^(CATCH_CLAUSE catchRule?) ^(FINALLY_CLAUSE finallyRule?))
	;
		
catchRule: 'catch' '(' { pushScope(BLOCK_SCOPE); } 
		t=type id=identifier { pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode) t.getTree()); }
		')' 
		/*block*/
		nonBraceBlock
		{ popScope(); } 
		-> ^(CATCH_RULE ^(TYPE $t) ^(IDENTIFIER $id) ^(BODY nonBraceBlock))
	;
	
nonBraceBlock	: LBRACE 
		( ({ isInActionContextBlock() }? actionContextStatement)=> actionContextStatement | (type identifier)=> localVariableDeclaration | statement)* 
		RBRACE
		-> ^(BLOCK ^(STATEMENTS actionContextStatement* localVariableDeclaration* statement*))
	;
	
finallyRule : 'finally' block
		-> ^(FINALLY_RULE ^(BODY block))
	;

semiColon
	: SEMICOLON
	;


/* There are two types of names, QUALIFIED_NAMEs and
   SIMPLE_NAMEs.  A QUALIFIED_NAME has two parts, a
   QUALIFIER and a SIMPLE_NAME.  The QUALIFIER is either
   a SIMPLE_NAME or another QUALIFIED_NAME.  Having this
   structure allows for easier tooling integration
*/
name[int nameType]
	: (identifier -> identifier)
		(DOT ident=identifier 
			-> ^(QUALIFIED_NAME ^(QUALIFIER $name)
				$ident))* { pushName(nameType, retval.tree); }
	;
	
nameOrPrimitiveType
	: name[BaseRulesParser.TYPE_REF] | primitiveType
	;
	
	
expressionName
	: name[TYPE_REF]
	;

methodName
	: name[TYPE_METHOD_REF]
	;

typeName
	: name[TYPE_REF]
	;

type	: typeAdditionalArrayDim
	;
	
typeAdditionalArrayDim
	: ({ setTypeReference(true); } t=typeName { setTypeReference(false); }| p=primitiveType) ( '['! ']'! 
			{ if (t != null) markAsArray((RulesASTNode) t.getTree()); else markAsArray((RulesASTNode) p.getTree()); } )?
	;
	
primitiveType
	: primitive
	-> ^(PRIMITIVE_TYPE["type"] primitive)
	;

primitive
	: 'boolean' | 'int' | 'long' | 'double'
	;

	
localVariableDeclaration
	: t=type variableDeclarator[(RulesASTNode) t.getTree()] 
		(',' variableDeclarator[(RulesASTNode) t.getTree()])* SEMICOLON
		-> ^(LOCAL_VARIABLE_DECL ^(TYPE $t) ^(DECLARATORS variableDeclarator*))
	;
	
variableDeclarator[RulesASTNode type]
	: id=identifier { pushDefineName((RulesASTNode) id.getTree(), type); } (ASSIGN 
	((localInitializerArrayLiteral (';' | ','))=>localInitializerArrayLiteral
	| expression))?
		-> ^(VARIABLE_DECLARATOR ^(NAME $id) 
		^(INITIALIZER localInitializerArrayLiteral)?
		^(EXPRESSION expression)?)
	;

localInitializerArrayLiteral
	: LBRACE (exp+=expression (',' exp+=expression)*)? RBRACE
		-> ^(LOCAL_INITIALIZER ^(EXPRESSIONS $exp*))
	;

arrayLiteral
	: type localInitializerArrayLiteral
		-> ^(ARRAY_LITERAL ^(TYPE type) ^(INITIALIZER localInitializerArrayLiteral))
	;

arrayAllocator
	: t=typeAdditionalArrayDim 
		'[' { arrayDepth++;} expression ']' { arrayDepth--; markAsArray((RulesASTNode) t.getTree()); } LBRACE RBRACE
		-> ^(ARRAY_ALLOCATOR ^(TYPE $t) ^(EXPRESSION expression))
	; 

	
/***********************************************
 * THE Business Event Base Rule Grammar Begins here *
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
		( ({ isInActionContextBlock() }? actionContextStatement)=> actionContextStatement | (type identifier)=> localVariableDeclaration | statement)* RBRACE
		{ popScope(); } 
		-> ^(BLOCK ^(STATEMENTS actionContextStatement* localVariableDeclaration* statement*))
	;
	
actionContextStatement
	: (t='call' m=methodName id=identifier { pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode)m.getTree()); }| 
	 ((t='create' { setTypeReference(true); } nm=name[BaseRulesParser.TYPE_REF] { setTypeReference(false); } id=identifier { pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode) nm.getTree()); }
	 | t='modify' id=identifier)))
		SEMICOLON
		-> ^(ACTION_CONTEXT_STATEMENT["actionContext"] ^(ACTION_TYPE["action type"] $t) ^(TYPE["type"] $m? $nm?) ^(NAME["name"] $id))
	;
	