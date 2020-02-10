grammar Header;

options {
language=Java;
output=AST;
ASTLabelType=RulesASTNode;
superClass=BaseRulesParser;
backtrack=false;
}

tokens {
	DESCRIPTION_STATEMENT; AUTHOR_STATEMENT; GUID_STATEMENT; LITERALS; NAME; HEADER_BLOCK; STATEMENTS;
}

@members {
	public RulesASTNode getHeaderNode() {
		return null; // root grammars will return the proper value
	}
}

@header { 
package com.tibco.cep.studio.core.rules.grammar; 

import com.tibco.cep.studio.core.rules.*; 
import com.tibco.cep.studio.core.rules.ast.*; 
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

@lexer::header {package com.tibco.cep.studio.core.rules.grammar;}
@lexer::members {
public void reportError(RecognitionException e) {
	// ignore, don't care for header sections, and we don't want to display a lexer error to user
	return;
}
}

header : ( aSt+=authorStatement | dSt+=descriptionStatement | gSt+=guidStatement
		| idOrChar /* for chars outside of the 'description' or 'author' statements */)+
	-> ^(HEADER_BLOCK ^(STATEMENTS $aSt* $dSt* $gSt*)) ;

authorStatement
	: '@author' (whiteSpace (id+=idOrChar )* | EOF)
		-> ^(AUTHOR_STATEMENT ^(NAME $id*))
	;

descriptionStatement
	: '@description' (whiteSpace (id+=idOrChar )* | EOF)
		-> ^(DESCRIPTION_STATEMENT ^(LITERALS $id*))
	;

guidStatement
	: '@GUID' (whiteSpace (id+=idOrChar )* | EOF)
		-> ^(GUID_STATEMENT ^(LITERALS $id*))
	;

idOrChar 
	: ID | CHARACTER | whiteSpace
	;

whiteSpace
	: WS | NEWLINE
	;

ID      : ('a'..'z'|'A'..'Z')+
        ;

END     : '*/' {emit(Token.EOF_TOKEN);}
        ;

WS_STAR 
options {k=3;}
: NEWLINE WS? ('*/' {$type=END; emit(Token.EOF_TOKEN);} | '*' {$type=NEWLINE;}) WS?
		;

NEWLINE : ('\n' | '\r')+
	;
			
WS      : (' ' | '\t')+ 
        ;
        
CHARACTER : // catch everything else
			.
		;
