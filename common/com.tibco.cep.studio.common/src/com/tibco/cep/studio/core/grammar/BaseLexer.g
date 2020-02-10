lexer grammar BaseLexer;

options {
superClass=BaseRulesLexer;
//backtrack=true;
}

@lexer::header { 
//package com.tibco.cep.studio.core.grammar.concept; 

import com.tibco.cep.studio.core.rules.*; 
import com.tibco.cep.studio.core.rules.ast.*; 
import com.tibco.cep.studio.core.rules.grammar.*;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
}

@lexer::members {
private static final int HEADER_CHANNEL = 1;

private RulesASTNode headerTree = null;
private IProblemHandler fHandler = null;

public RulesASTNode getHeaderNode() {
	return headerTree;
}

public void setProblemHandler(IProblemHandler handler) {
	this.fHandler = handler;
}

public IProblemHandler getProblemHandler() {
	return fHandler;
}

@Override
public void reportError(RecognitionException exception) {
	if (exception instanceof MismatchedTokenException) {
		if (exception.getUnexpectedType() == EOF) {
			return;
		}
	}
	super.reportError(exception);
	RulesSyntaxProblem problem = new RulesSyntaxProblem(
			getErrorMessage(exception, getTokenNames()),
			exception);
	if (fHandler != null) {
		fHandler.handleProblem(problem);
	}
}

}

/***********************************************
 * THE Business Event Rule Grammar Begins here *
 ***********************************************/

/*
 * Program structuring syntax follows.
 */

HeaderSection
	: HEADER_START
	   {
	   /*
	   	create a separate lexer/parser for 'javadoc-like' comments,
	   	so we can parse the description/author/etc for a rule, but
	   	not have to worry about mixing keywords, identifiers, etc
	   */
            HeaderLexer j = new HeaderLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(j);
            //tokens.discardTokenType(HeaderLexer.WS);
            // tokens.discardTokenType(HeaderLexer.WS_STAR); // I turn these into NEWLINES for rewrite purposes
            
            HeaderParser p = new HeaderParser(tokens);
            p.setTreeAdaptor(new HeaderTreeAdaptor());
            HeaderParser.header_return header = p.header();
            if (headerTree == null) {
            	// the first 'Header' section wins
            	headerTree = (RulesASTNode) header.getTree();
            	headerTree.setHidden(true);
            }
            $channel = HEADER_CHANNEL;
          }
	;

MappingEnd
	: '</#mapping>'
	;

// LEXER
INSTANCE_OF : 'instanceof';
TRUE	:	'true';
FALSE	:	'false';
PLUS	: '+';
MINUS	: '-';
MULT	: '*';
DIVIDE	: '/';
MOD		: '%';
POUND	: '!';
ASSIGN 	: '=';
DOT		: '.';
SEMICOLON 	: ';';
LBRACE	:	'{';
RBRACE	:	'}';
LPAREN	:	'(';
RPAREN	:	')';
LBRACK	:	'[';
RBRACK	:	']';
ANNOTATE :  '@';
AND 	:	'&&';
OR	:	'||';
EQ	:	'==';
NE	:	'!=' | 'not' 'equal';
LT	:	'<' | 'less' 'than';
GT	:	'>' | 'greater' 'than';
LE	:	'<=' | 'less' 'than' 'or' 'equal';
GE	:	'>=' | 'greater' 'than' 'or' 'equal';
PLUS_EQUAL 	: '+=';
MINUS_EQUAL 	: '-=';
MULT_EQUAL 	: '*=';
DIV_EQUAL 	: '/=';
MOD_EQUAL 	: '%=';
INCR 	:	'++';
DECR	:	'--';
NullLiteral	: 'null';

HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DecimalLiteral : ('0'..'9')+ IntegerTypeSuffix? ;

//OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
IntegerTypeSuffix : ('l'|'L') ;

FloatingPointLiteral
    :   ('0'..'9')+ DOT ('0'..'9')* Exponent? FloatTypeSuffix?
    |   DOT ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix
    ;

fragment
Exponent : ('e'|'E') (PLUS|MINUS)? ('0'..'9')+ ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

CharacterLiteral
    :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"'|'\n'|'\r') )* '"'
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

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

fragment HEADER_START 
	:	'/**'
	;
	
COMMENT
    :   '/*' ~'*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
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
       '\u3400'..'\u4DB5' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff' |
       '\uff61'..'\uff9f' |
	   '\uAC00'..'\uD7A3' |
	   // experimentally added the following by comparing Character.isJavaIdentifierStart with the above range
	   '\u0000' |
	   '\u00a2'..'\u00a5' |
	   '\u00aa' |
	   '\u00b5' |
	   '\u00ba' |
	   '\u203f'..'\u2040' |
	   '\u2054' |
	   '\u2071' |
	   '\u207f' |
	   '\u2090'..'\u209c' |
	   '\u20a0'..'\u20b9' |
	   '\u2102' |
	   '\u2107' |
	   '\u210a'..'\u2113' |
	   '\u2115' |
	   '\u2119'..'\u211d' |
	   '\u2124' |
	   '\u2126' |
	   '\u2128' |
	   '\u212a'..'\u212d' |
	   '\u212f'..'\u2139' |
	   '\u213c'..'\u213f' |
	   '\u2145'..'\u2149' |
	   '\u214e' |
	   '\u2160'..'\u2188' |
	   '\u2c00'..'\u2c2e' |
	   '\u2c30'..'\u2c5e' |
	   '\u2c60'..'\u2ce4' |
	   '\u2ceb'..'\u2cee' |
	   '\u2d00'..'\u2d25' |
	   '\u2d30'..'\u2d65' |
	   '\u2d6f' |
	   '\u2d80'..'\u2d96' |
	   '\u2da0'..'\u2da6' |
	   '\u2da8'..'\u2dae' |
	   '\u2db0'..'\u2db6' |
	   '\u2db8'..'\u2dbe' |
	   '\u2dc0'..'\u2dc6' |
	   '\u2dc8'..'\u2dce' |
	   '\u2dd0'..'\u2dd6' |
	   '\u2dd8'..'\u2dde' |
	   '\u2e2f' |
	   '\u3005'..'\u3007' |
	   '\u3021'..'\u3029' |
	   '\u3031'..'\u3035' |
	   '\u3038'..'\u303c' |
	   '\u31a0'..'\u31ba' |
	   '\u31f0'..'\u31ff' |
	   '\ua000'..'\ua48c' |
	   '\ua4d0'..'\ua4fd' |
	   '\ua500'..'\ua60c' |
	   '\ua610'..'\ua61f' |
	   '\ua62a'..'\ua62b' |
	   '\ua640'..'\ua66e' |
	   '\ua67f'..'\ua697' |
	   '\ua6a0'..'\ua6ef' |
	   '\ua717'..'\ua71f' |
	   '\ua722'..'\ua788' |
	   '\ua78b'..'\ua78e' |
	   '\ua790'..'\ua791' |
	   '\ua7a0'..'\ua7a9' |
	   '\ua7fa'..'\ua801' |
	   '\ua803'..'\ua805' |
	   '\ua807'..'\ua80a' |
	   '\ua80c'..'\ua822' |
	   '\ua838' |
	   '\ua840'..'\ua873' |
	   '\ua882'..'\ua8b3' |
	   '\ua8f2'..'\ua8f7' |
	   '\ua8fb' |
	   '\ua90a'..'\ua925' |
	   '\ua930'..'\ua946' |
	   '\ua960'..'\ua97c' |
	   '\ua984'..'\ua9b2' |
	   '\ua9cf' |
	   '\uaa00'..'\uaa28' |
	   '\uaa40'..'\uaa42' |
	   '\uaa44'..'\uaa4b' |
	   '\uaa60'..'\uaa76' |
	   '\uaa7a' |
	   '\uaa80'..'\uaaaf' |
	   '\uaab1' |
	   '\uaab5'..'\uaab6' |
	   '\uaab9'..'\uaabd' |
	   '\uaac0' |
	   '\uaac2' |
	   '\uaadb'..'\uaadd' |
	   '\uab01'..'\uab06' |
	   '\uab09'..'\uab0e' |
	   '\uab11'..'\uab16' |
	   '\uab20'..'\uab26' |
	   '\uab28'..'\uab2e' |
	   '\uabc0'..'\uabe2' |
	   '\ud7b0'..'\ud7c6' |
	   '\ud7cb'..'\ud7fb' |
	   '\ufb00'..'\ufb06' |
	   '\ufb13'..'\ufb17' |
	   '\ufb1d' |
	   '\ufb1f'..'\ufb28' |
	   '\ufb2a'..'\ufb36' |
	   '\ufb38'..'\ufb3c' |
	   '\ufb3e' |
	   '\ufb40'..'\ufb41' |
	   '\ufb43'..'\ufb44' |
	   '\ufb46'..'\ufbb1' |
	   '\ufbd3'..'\ufd3d' |
	   '\ufd50'..'\ufd8f' |
	   '\ufd92'..'\ufdc7' |
	   '\ufdf0'..'\ufdfc' |
	   '\ufe33'..'\ufe34' |
	   '\ufe4d'..'\ufe4f' |
	   '\ufe69' |
	   '\ufe70'..'\ufe74' |
	   '\ufe76'..'\ufefc' |
	   '\uff04' |
	   '\uff21'..'\uff3a' |
	   '\uff3f' |
	   '\uff41'..'\uff5a' |
	   '\uffa0'..'\uffbe' |
	   '\uffc2'..'\uffc7' |
	   '\uffca'..'\uffcf' |
	   '\uffd2'..'\uffd7' |
	   '\uffda'..'\uffdc' |
	   '\uffe0'..'\uffe1' |
	   '\uffe5'..'\uffe6'
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

