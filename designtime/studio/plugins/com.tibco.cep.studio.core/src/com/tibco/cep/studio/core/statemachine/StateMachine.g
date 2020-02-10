grammar StateMachine;

options {
language=Java;
output=AST;
//backtrack=true;
}

tokens {
	RULE; RULE_FUNCTION; RULE_DECL; NAME; RULE_BLOCK; ATTRIBUTE_BLOCK; STATEMENTS;
	DECLARE_BLOCK; RULE_FUNC_DECL; SCOPE_BLOCK; WHEN_BLOCK; PREDICATES; BLOCKS;
	THEN_BLOCK; BODY_BLOCK; SCOPE_DECLARATOR; TYPE; DECLARATOR; VIRTUAL; PRIMITIVE_TYPE; 
	ANNOTATION; 
	
	STATE_MACHINE; TRANSITION_DEFINITION; START_STATE; END_STATE; CONCURRENT_STATE_DEFINITION;
	REGIONS; REGION_DEFINITION; DEFINITIONS; PSEUDO_END_STATE_DEF; PSEUDO_START_STATE_DEF;
	RULES; SIMPLE_STATE_DEF; TIMEOUT_RULE; EXIT_RULE; ENTRY_RULE; COMPOSITE_STATE_DEFINITION;
}

@header { 
package com.tibco.cep.designer.core.statemachine; 
}

@members {
private boolean virtual = false;
}

@rulecatch {
catch (RecognitionException re) {
    reportError(re);
    recover(input,re);
}
catch (RewriteEmptyStreamException e) {
	// do nothing for now
	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
	e.printStackTrace();
}
}

@lexer::header {package com.tibco.cep.designer.core.statemachine;}

/********************************************************
 * THE Business Event State Machine Grammar Begins here *
 ********************************************************/

/*
 * Program structuring syntax follows.
 */

startRule
	: (stateMachineDefinition 
	| ruleDeclaration 
	| ruleFunctionDeclaration) EOF
	;
	
stateMachineDefinition
	: 'statemachine' name '{' stateMachineBlock* '}'
	-> ^(STATE_MACHINE ^(NAME name) ^(BLOCKS stateMachineBlock*))
	;

stateMachineBlock
	:	(compositeStateDefinition
		| concurrentStateDefinition
		| transitionDefinition)
	;

transitionDefinition
	: 'transition' name '(' startState=name ',' endState=name ')' '{'
		transitionBlock*
	'}'
	-> ^(TRANSITION_DEFINITION ^(NAME name) ^(START_STATE $startState)
		^(END_STATE $endState) ^(BLOCKS transitionBlock*))
	;

transitionBlock
	: declareNT | whenNT | thenNT
	;

concurrentStateDefinition
	: 'concurrent' 'state' name '{' regionDefinition* '}'
	-> ^(CONCURRENT_STATE_DEFINITION ^(NAME name) ^(REGIONS regionDefinition))
	;

regionDefinition
	: 'region' 'state' name '{' (stateDefinition | stateRule)* '}'
	-> ^(REGION_DEFINITION ^(NAME name) ^(DEFINITIONS stateDefinition*) 
		^(RULES stateRule*))
	;

stateDefinition
	: simpleStateDefinition | pseudoStartStateDefinition | pseudoEndStateDefinition
	;

pseudoEndStateDefinition
	: 'pseudo-end' 'state' name '{' stateRule* '}'
	-> ^(PSEUDO_END_STATE_DEF ^(NAME name) ^(RULES stateRule*))
	;

pseudoStartStateDefinition
	: 'pseudo-start' 'state' name '{' stateRule* '}'
	-> ^(PSEUDO_START_STATE_DEF ^(NAME name) ^(RULES stateRule*))
	;

simpleStateDefinition
	: 'simple' 'state' name '{' stateRule* '}'
	-> ^(SIMPLE_STATE_DEF ^(NAME name) ^(RULES stateRule*))
	;

stateRule
	: onEntryRule | onExitRule | onTimeOutRule
	;

onTimeOutRule
	: 'onTimeOut' '(' ')' '{' '}'
	-> ^(TIMEOUT_RULE)
	;

onExitRule
	: 'onExit' '(' ')' '{' '}'
	-> ^(EXIT_RULE)
	;

onEntryRule
	: 'onEntry' '(' ')' '{' '}'
	-> ^(ENTRY_RULE)
	;


compositeStateDefinition
	: 'composite' 'state' name '{' compositeStateBlock* '}'
	-> ^(COMPOSITE_STATE_DEFINITION ^(NAME name) ^(DEFINITIONS compositeStateBlock*))
	;

compositeStateBlock
	: concurrentStateDefinition | compositeStateDefinition
	;
	
identifier
	:	Identifier
	;
	
xsltLiteral
	: '""xslt://' '"' '"'
	;
	
ruleDeclaration
	: 'rule' name '{' ruleNT '}'
	-> ^(RULE_DECL["rule"] name ^(BLOCKS ruleNT))
	;

ruleFunctionDeclaration
	: ('virtual' { virtual=true;})? 'rulefunction' name '{' ruleFunctionNT '}'
	-> ^(RULE_FUNC_DECL["ruleFunction"] ^(VIRTUAL 'virtual')? name ^(BLOCKS ruleFunctionNT))
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

attributeNT
	: 'attribute' '{' attributeBodyDeclaration* '}'
	-> ^(ATTRIBUTE_BLOCK["attribute"] attributeBodyDeclaration*)
	;
	
attributeBodyDeclaration
	: ('priority' ASSIGN integerLiteral SEMICOLON
	| 'requeue' ASSIGN booleanLiteral SEMICOLON
	| '$lastmod' ASSIGN StringLiteral SEMICOLON)
	;

declareNT
	: 'declare' LBRACE declarator* RBRACE
	-> ^(DECLARE_BLOCK["declare"] ^(STATEMENTS["declarators"] declarator*))
	;

scopeNT
	: 'scope' LBRACE scopeDeclarator* RBRACE
	-> ^(SCOPE_BLOCK["scope"] ^(STATEMENTS["declarators"] scopeDeclarator*))
	;

scopeDeclarator
	: (name | primitiveType) identifier SEMICOLON
	-> ^(SCOPE_DECLARATOR["declarator"] ^(TYPE["type"] name? primitiveType?)
		^(NAME["name"] identifier))
	;
	
/*
scopeType
    : ANNOTATE! ('Concept' | 'SimpleEvent' | 'TimeEvent')
    ;
*/
	
declarator
	: name identifier SEMICOLON
	-> ^(DECLARATOR["declarator"] ^(TYPE["type"] name)
		^(NAME["name"] identifier))
	;
	
whenNT	: 'when' LBRACE predicates RBRACE
	-> ^(WHEN_BLOCK["when"] predicates)
	;
	
predicates
	: (predicate SEMICOLON)*
	-> ^(PREDICATES predicate*)
	;

predicate
	: expression
	;
	
expression
	: conditionalAndExpression
	( '||' conditionalAndExpression)*
	;
	
conditionalAndExpression
	: equalityExpression
	( '&&' equalityExpression)*
	;
	
equalityExpression
	: (relationalExpression
	| (domainSpec)=>domainSpec 
	| comparisonNoLHS)
	( 
		(EQ | NE)
		((domainSpec)=>domainSpec
		| relationalExpression
		| comparisonNoLHS)
	)*
	;
	
comparisonNoLHS
	: (
		(EQ | NE)
		(domainSpec)=>domainSpec
		| unaryExpression
	) |
	( 'instanceof' type ) |
	( (LT | GT | LE | GE) additiveExpression)
	;
	
domainSpec
	: rangeExpression | setMembershipExpression
	;
	
rangeExpression
	: rangeStart expression? ',' expression? rangeEnd
	;

rangeStart
	: '(' | '['
	;

rangeEnd
	: ')' | ']'
	;
	
setMembershipExpression
	: '{' (expression (',' expression)*)? '}'
	;

relationalExpression
	: additiveExpression
	(
		(LT | GT | LE | GE) additiveExpression
		|
		'instanceof' type
	)*
	;
	
additiveExpression
	:	multiplicativeExpression
	( ('+' | '-') multiplicativeExpression)*
	;
	
multiplicativeExpression
	: unaryExpression
	( ('*' | '/' | '%') unaryExpression)*
	;
	
unaryExpression
	: ('+' | '-' | '!') unaryExpression | primaryExpression
	;
	
primaryExpression
	: primaryPrefix (primarySuffix)*
	;
	
primaryPrefix
	: literal
	| '(' expression ')'
	| (type '{')=>arrayLiteral
	| (arrayAllocator)=>arrayAllocator
	| (methodName '(')=>methodName argumentsSuffix
	| expressionName
	;
	
primarySuffix
	: arrayAccessSuffix | fieldAccessSuffix
	;
	
arrayAccessSuffix
	: '[' expression ']'
	;
	
fieldAccessSuffix
	: ('.' | '@') identifier
	;
	
argumentsSuffix
	: '(' argumentList? ')'
	;
	
argumentList
	: expression (',' expression)*
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
	;
	
booleanLiteral
	: 'true' | 'false'
	;

/******************
******* Then Body Specification begins
*/
thenNT	: 'then' LBRACE thenStatements RBRACE
	-> ^(THEN_BLOCK["then"] ^(STATEMENTS thenStatements*))
	;
	
bodyNT	: 'body' LBRACE thenStatements RBRACE
	-> ^(BODY_BLOCK["body"] ^(STATEMENTS thenStatements*))
	;
	
thenStatements
	:	thenStatement*
	;
	
thenStatement
	: (type identifier)=>localVariableDeclaration SEMICOLON
	| statement | emptyBodyStatement
	;
	
statement
	: lineStatement | blockStatement
	;
	
lineStatement
	: emptyStatement
	| statementExpression SEMICOLON
	| returnStatement
	| breakStatement
	| continueStatement
	| throwStatement
	;
	
blockStatement
	: ifRule
	| whileRule
	| forRule
	| block
	| tryCatchFinally
	;
	
emptyStatement
	: SEMICOLON
	;
	
emptyBodyStatement
	: WS
	;	
	
statementExpression
	: (methodName '(')=>methodName argumentsSuffix
	( primarySuffix* ASSIGN expression)?
	| primaryExpression ASSIGN expression
	;
	
returnStatement
	: 'return' (expression SEMICOLON | SEMICOLON)
	;
	
breakStatement
	: 'break' SEMICOLON
	;
	
continueStatement
	: 'continue' SEMICOLON
	;
	
throwStatement
	: 'throw' expression SEMICOLON
	;
	
block	: LBRACE ( (type identifier)=> localVariableDeclaration SEMICOLON | statement)* RBRACE
	;
	
statementExpressionList
	: statementExpression (',' statementExpression)*
	;
	
ifRule	: 'if' '(' expression ')' statement
	('else' statement)?
	;
	
whileRule	: 'while' '(' expression ')' statement
	;
	
forRule	: 'for' '('
	( (type identifier)=> localVariableDeclaration | statementExpressionList)? SEMICOLON
	(expression)? SEMICOLON
	(statementExpressionList)? ')'
	statement
	;
	
tryCatchFinally
	: tryRule 
	( catchRule finallyRule? )
	| finallyRule
	;
	
tryRule	: 'try' block
	;
	
catchRule: 'catch' '(' type identifier ')' block
	;
	
finallyRule : 'finally' block
	;

semiColon
	: SEMICOLON
	;
	
name	: identifier ('.' identifier)*
	-> ^(NAME["name"] identifier*)
	;
	
dot	: '.'
	;
	
expressionName
	: name
	;

methodName
	: name
	;

typeName
	: name
	;

type	: typeAdditionalArrayDim
	;
	
typeAdditionalArrayDim
	: (typeName | primitiveType) ( '[' ']' )?
	;
	
primitiveType
	: primitives
	-> ^(PRIMITIVE_TYPE["type"] primitives)
	;

primitives
	: 'boolean' | 'int' | 'long' | 'double'
	;

	
localVariableDeclaration
	: type variableDeclarator (',' variableDeclarator)*
	;
	
variableDeclarator
	: identifier (ASSIGN 
	((localInitializerArrayLiteral (';' | ','))=>localInitializerArrayLiteral
	| expression))?
	;

localInitializerArrayLiteral
	: LBRACE (expression (',' expression)*)? RBRACE
	;

arrayLiteral
	: type localInitializerArrayLiteral
	;

arrayAllocator
	: typeAdditionalArrayDim '[' expression ']' '{' '}'
	; 


// LEXER

ASSIGN 	: '=';

SEMICOLON 	: ';';
LBRACE	:	'{';
RBRACE	:	'}';
ANNOTATE :  '@';
EQ	:	'==';
NE	:	'!=' | 'not' 'equal';
LT	:	'<' | 'less' 'than';
GT	:	'>' | 'greater' 'than';
LE	:	'<=' | 'less' 'than' 'or' 'equal';
GE	:	'>=' | 'greater' 'than' 'or' 'equal';
NullLiteral	:	'null';

HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DecimalLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
IntegerTypeSuffix : ('l'|'L') ;

FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix
    ;

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

CharacterLiteral
    :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

//ENUM:   'enum' {if (!enumIsKeyword) $type=Identifier;}
//    ;
    
//ASSERT
//    :   'assert' {if (!assertIsKeyword) $type=Identifier;}
//    ;
    
Identifier 
    :   Letter (Letter|JavaIDDigit)*
    ;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;
