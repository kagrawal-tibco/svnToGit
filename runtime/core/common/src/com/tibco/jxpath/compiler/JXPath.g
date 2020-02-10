/**
XPath parser, based on XPath v1 grammar defined by W3C.
http://www.w3.org/TR/xpath

*/

grammar JXPath;

options { 
output = AST; 
//ASTLabelType=XPathASTNode;
}

tokens {
	FUNCTION_CALL; FILTER_PREDICATE_EXPR; QUALIFIED_NAME; VAR_REF; SIMPLE_AXIS_STEP;
	ABBREVIATED_STEP; NAMED_AXIS_STEP; PATH_EXPR; CONSTANT_EXPR; AXIS_SPECIFIER; PREDICATES;
	ARGUMENTS;
}

/**/
@header {
	package com.tibco.jxpath.compiler;

	import com.tibco.jxpath.compiler.*;
}

@lexer::header {
	package com.tibco.jxpath.compiler;

	import com.tibco.jxpath.compiler.*;
}

// ---------------- Parser Rules ---------------- //
main  :  expr
  ;

locationPath 
  :  relativeLocationPath
  |  absoluteLocationPath
  ;

absoluteLocationPath
  :  pathSep relativeLocationPath
  |  abbreviatedAbsoluteLocationPath
  ;

abbreviatedAbsoluteLocationPath
  : abrPath relativeLocationPath
  ;
  
relativeLocationPath
  :  step (simpleAxisStep step)*
  ;

simpleAxisStep
    : ( pathSep
      | abrPath
      )
    ;
    
pathSep
	: PATHSEP    -> ^(SIMPLE_AXIS_STEP PATHSEP)
	;
	
abrPath
	: ABRPATH -> ^(SIMPLE_AXIS_STEP ABRPATH)
	;
	
step  :  namedAxisStep
  |  abbreviatedStep
  ;

namedAxisStep
    : axisSpecifier? nodeTest predicates? -> ^(NAMED_AXIS_STEP axisSpecifier? nodeTest ^(PREDICATES predicates)?);
    
predicates
	: predicate+
	;
	
axisSpecifier
  :  (AxisName CC
  |  AT) -> ^(AXIS_SPECIFIER AxisName? CC? AT?)
  ;

nodeTest:  nameTest
  |  NodeType LPAR RPAR
  |  'processing-instruction' LPAR Literal RPAR
  ;

predicate
  :  LBRAC! expr RBRAC!
  ;

abbreviatedStep
  :  (DOT
  |  DOTDOT)
  	-> ^(ABBREVIATED_STEP)
  ;

expr  :  orExpr
  ;

primaryExpr
  :  (variableReference -> ^(variableReference)
  |  LPAR expr RPAR -> ^(expr)
  |  Literal -> ^(CONSTANT_EXPR Literal)
  |  Number  -> ^(CONSTANT_EXPR Number) 
  |  functionCall -> ^(functionCall))
  ;

functionCall
  :  functionName LPAR ( expr ( COMMA expr )* )? RPAR
  	-> ^(FUNCTION_CALL functionName ^(ARGUMENTS expr*))
  ;

unionExpr
  :  pathExpr (UNION^ unionExpr)?
  |  pathSep UNION^ unionExpr
  ;

pathExpr
  :  locationPath -> ^(PATH_EXPR locationPath)
  |  f=filterExpr (s=simpleAxisStep r=relativeLocationPath)? 
  		-> { ((CommonTree)((filterExpr_return)f).getTree()).getType() == CONSTANT_EXPR && s==null && r==null }? ^($f)
  		-> ^(PATH_EXPR $f $s? $r?)
  ;

filterExpr
  :  primaryExpr p+=predicate* -> { list_p != null && list_p.size() > 0 }? ^(FILTER_PREDICATE_EXPR primaryExpr ^(PREDICATES $p*))
 							  -> primaryExpr
  ;

orExpr  :  andExpr (OR^ andExpr)*
  ;

andExpr  :  equalityExpr (AND^ equalityExpr)*
  ;

equalityExpr
  :  relationalExpr ((EQUAL|NOT_EQUAL)^ relationalExpr)*
  ;

relationalExpr
  :  additiveExpr ((LESS_THAN|GREATER_THAN|LESS_THAN_EQUALS|GREATER_THAN_EQUALS)^ additiveExpr)*
  ;

additiveExpr
  :  multiplicativeExpr ((PLUS|MINUS)^ multiplicativeExpr)*
  ;

multiplicativeExpr
  :  unaryExpr ((STAR|DIV|MOD)^ unaryExpr)*
  |  PATHSEP ((DIV|MOD)^ multiplicativeExpr)?
  ;

unaryExpr
  :  MINUS* unionExpr
  ;

qName  :  nCName (COLON nCName)?
	-> ^(QUALIFIED_NAME nCName+)
  ;

functionName
  :  qName  // Does not match nodeType, as per spec.
  ;

variableReference
  :  VAR_START qName
  	-> ^(VAR_REF qName)
  ;

nameTest:  STAR
  |  nCName COLON STAR
  |  qName
  ;

nCName  :  NCName
  |  AxisName
  ;

// Lexer rules
UNION : '|';
OR : 'or';
AND : 'and';
EQUAL : '=';
NOT_EQUAL : '!=';
LESS_THAN : '<';
GREATER_THAN : '>';
LESS_THAN_EQUALS : '<=';
GREATER_THAN_EQUALS : '>=';
PLUS : '+';
MINUS : '-';
STAR : '*';
DIV : 'div';
MOD : 'mod';
COLON : ':';
VAR_START : '$';
PATHSEP  :  '/';
ABRPATH  :  '//';
LPAR  :  '(';
RPAR  :  ')';
LBRAC  :  '[';
RBRAC  :  ']';
DOT  :  '.';
DOTDOT  :  '..';
AT  :  '@';
COMMA  :  ',';
CC  :  '::';

NodeType:  'comment'
  |  'text'
  |  'processing-instruction'
  |  'node'
  ;
  
Number  :  Digits ('.' Digits?)?
  |  '.' Digits
  ;

fragment
Digits  :  ('0'..'9')+
  ;

AxisName:  'ancestor'
  |  'ancestor-or-self'
  |  'attribute'
  |  'child'
  |  'descendant'
  |  'descendant-or-self'
  |  'following'
  |  'following-sibling'
  |  'namespace'
  |  'parent'
  |  'preceding'
  |  'preceding-sibling'
  |  'self'
  ;

Literal  :  '"' ~'"'* '"'
  |  '\'' ~'\''* '\''
  ;

Whitespace
  :  (' '|'\t'|'\n'|'\r')+ {$channel = HIDDEN;}
  ;

NCName  :  NCNameStartChar NCNameChar*
  ;

fragment
NCNameStartChar
  :  'A'..'Z'
  |   '_'
  |  'a'..'z'
  |  '\u00C0'..'\u00D6'
  |  '\u00D8'..'\u00F6'
  |  '\u00F8'..'\u02FF'
  |  '\u0370'..'\u037D'
  |  '\u037F'..'\u1FFF'
  |  '\u200C'..'\u200D'
  |  '\u2070'..'\u218F'
  |  '\u2C00'..'\u2FEF'
  |  '\u3001'..'\uD7FF'
  |  '\uF900'..'\uFDCF'
  |  '\uFDF0'..'\uFFFD'
// Unfortunately, java escapes can't handle this conveniently,
// as they're limited to 4 hex digits. TODO.
//  |  '\U010000'..'\U0EFFFF'
  ;

fragment
NCNameChar
  :  NCNameStartChar | '-' | '.' | '0'..'9'
  |  '\u00B7' | '\u0300'..'\u036F'
  |  '\u203F'..'\u2040'
  ;
