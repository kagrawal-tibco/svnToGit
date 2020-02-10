      /* ====================================================================
       * OQL Sample Grammar for Object Data Management Group (ODMG)
       *
       * Copyright (c) 1999 Micro Data Base Systems, Inc. All rights reserved.
       *
       * Redistribution and use in source and binary forms, with or without
       * modification, are permitted provided that the following conditions
       * are met:
       *
       * 1. Redistributions of source code must retain the above copyright
       *    notice, this list of conditions and the following disclaimer.
       *
       * 2. Redistributions in binary form must reproduce the above copyright
       *    notice, this list of conditions and the following disclaimer in
       *    the documentation and/or other materials provided with the
       *    distribution.
       *
       * 3. All advertising materials mentioning features or use of this
       *    software must display the following acknowledgment:
       *    "This product includes software developed by Micro Data Base Systems, Inc.
       *    (http://www.mdbs.com) for the use of the Object Data Management Group
       *    (http://www.odmg.org/)."
       *
       * 4. The names "mdbs" and "Micro Data Base Systems" must not be used to
       *    endorse or promote products derived from this software without
       *    prior written permission. For written permission, please contact
       *    info@mdbs.com.
       *
       * 5. Products derived from this software may not be called "mdbs"
       *    nor may "mdbs" appear in their names without prior written
       *    permission of Micro Data Base Systems, Inc.
       *
       * 6. Redistributions of any form whatsoever must retain the following
       *    acknowledgment:
       *    "This product includes software developed by Micro Data Base Systems, Inc.
       *    (http://www.mdbs.com) for the use of the Object Data Management Group
       *    (http://www.odmg.org/)."
       *
       * THIS SOFTWARE IS PROVIDED BY MICRO DATA BASE SYSTEMS, INC. ``AS IS'' AND ANY
       * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
       * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
       * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL MICRO DATA BASE SYSTEMS, INC. OR
       * ITS ASSOCIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
       * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
       * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
       * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
       * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
       * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
       * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
       * OF THE POSSIBILITY OF SUCH DAMAGE.
       *
       */

      /*
      **  oql.g
      **
      ** Built with Antlr 3.0.0
      **   java antlr.Tool ojb-oql.g
      */
      grammar BEOql;

      options {
      	language=Java;
          output=AST;
          backtrack=true;
          k=2;
          memoize=true;
          ASTLabelType=CommonTree;
      }
      tokens {
      	SELECT_EXPR;
      	FROM_CLAUSE;
      	DELETE_EXPR;
      	ORDER_CLAUSE;
      	WHERE_CLAUSE;
      	ITERATOR_DEF;
      	PROJECTION_ATTRIBUTES;
      	PROJECTION_LIST;
      	PROJECTION;
      	GROUP_CLAUSE;
      	HAVING_CLAUSE;
      	GROUP_COLUMN;
      	ARRAY_INDEX;
      	ARG_LIST;
      	FIELD_LIST;
      	CAST_OP;
      	ALIAS_CLAUSE;
      	ALIAS;
      	IN_CLAUSE;
      	ASTERISK_CLAUSE;
      	FOR_ALL_CLAUSE;
      	EXISTS_CLAUSE;
      	LIKE_CLAUSE;
      	BETWEEN_CLAUSE;
      	PATH_EXPRESSION;
      	CAST_EXPRESSION;
      	OR_EXPRESSION;
      	AND_EXPRESSION;
      	PATHFUNCTION_EXPRESSION;
      	FUNCTION_EXPRESSION;
      	BIND_VARIABLE_EXPRESSION;
      	BLOCK_EXPRESSION;
      	SUBSELECT_EXPRESSION;
      	UNARY_EXPRESSION;
      	UNARY_OP;
      	INDEX_SINGLE;
      	INDEX_MULTIPLE;
      	RANGE_EXPRESSION;
      	STREAM_DEF;
      	WINDOW_DEF;
        ACCEPT_CLAUSE;
      	ACCEPT_NEW;
      	ACCEPT_ALL;
      	EMIT_CLAUSE;
      	EMIT_NEW;
      	EMIT_DEAD;
      	POLICY_CLAUSE;
      	POLICY_BY_CLAUSE;
        TIME_WINDOW;
        SLIDING_WINDOW;
        TUMBLING_WINDOW;
        USING_CLAUSE;
      	PURGE_CLAUSE;
        PURGE_FIRST_CLAUSE;
      	PURGE_WHEN_CLAUSE;
        LIMIT_CLAUSE;
        LITERALS;
        SORT_CRITERION;
        SORT_DIRECTION;
        SCOPE_IDENTIFIER;
        NUMBER_LITERAL;
        NULL_LITERAL;
        BOOLEAN_LITERAL;
        CHAR_LITERAL;
        STRING_LITERAL;
        DATETIME_LITERAL;
        UNARY_MINUS;
        IDENTIFIER;
      }

      @header {
      	package com.tibco.cep.query.ast.parser;
      }
      @lexer::header{package com.tibco.cep.query.ast.parser;}

      @parser::members {
        boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
        private void debugOut(String msg) {
            if(debug) {
                System.out.println(msg);
            }
        }


        public String getErrorMessage(RecognitionException e, String[] tokenNames)
        {
            return super.getErrorMessage(e, BEOqlParser.tokenNames);
        }

        protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException
        {
            throw new MismatchedTokenException(ttype, input);
        }

        public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
             throw e;
        }

        public void recover(IntStream input, RecognitionException re) {
             throw new RuntimeException(re);
        }

        public void displayRecognitionError(String[] tokenNames,RecognitionException e) {
          throw new RuntimeException(e);
        }

      }

      @rulecatch {
            catch (RecognitionException rece) {
	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
               if(debug) {
                    System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                }
                throw new RuntimeException(rece);
            }
        }

        @lexer::members {

          public String getErrorMessage(RecognitionException e, String[] tokenNames) {
              return super.getErrorMessage(e, BEOqlParser.tokenNames);
          }

          protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException {
              throw new MismatchedTokenException(ttype, input);
          }

          public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
             throw e;
          }

	  @Override
	  public void reportError(RecognitionException e) {
	    throw new RuntimeException(e);
	  }

	  public void displayRecognitionError(String[] tokenNames,RecognitionException e) {
            throw new RuntimeException(e);
          }

	  public void recover(org.antlr.runtime.RecognitionException re) {
  	    throw new RuntimeException(re);
	  }

	  /**
	   * Refer to http://www.antlr.org/wiki/pages/viewpage.action?pageId=5341217 for more information.
	   */
	  public Token nextToken() {
		while (true) {
			state.token = null;
			state.channel = Token.DEFAULT_CHANNEL;
			state.tokenStartCharIndex = input.index();
			state.tokenStartCharPositionInLine = input.getCharPositionInLine();
			state.tokenStartLine = input.getLine();
			state.text = null;
			if ( input.LA(1)==CharStream.EOF ) {
				return Token.EOF_TOKEN;
			}
			try {
				mTokens();
				if ( state.token==null ) {
					emit();
				}
				else if ( state.token==Token.SKIP_TOKEN ) {
					continue;
				}
				return state.token;
			}
			catch (RecognitionException re) {
				throw new RuntimeException(re); // or throw Error
			}
		}
	  }

	}


              /* punctuation */
      TOK_RPAREN                :       	')';
      TOK_LPAREN                :       	'(';
      TOK_COMMA                 :       	',';
      TOK_SEMIC                 :       	';';
      TOK_DOT		        :       	'.';
      TOK_DOTDOT    		:               TOK_DOT TOK_DOT;
      TOK_COLON                 :       	':';
      TOK_INDIRECT              :       	'-' '>' { $type = TOK_DOT;} ;
      TOK_AT			:       	'@';
      TOK_CONCAT                :       	'|' '|';
      TOK_EQ		        :       	'=';
      TOK_PLUS                  :       	'+';
      TOK_MINUS                 :       	'-';
      TOK_SLASH                 :       	'/';
      TOK_STAR                  :       	'*';
      TOK_LE                    :       	'<=';
      TOK_GE                    :       	'>=';
      TOK_NE                    :       	'<>'|'!=';
      //TOK_NE2                 :       	'!=';
      TOK_LT                    :       	'<';
      TOK_GT                    :       	'>';
      TOK_LBRACK                :       	'[';
      TOK_RBRACK                :       	']';
      TOK_DOLLAR		:       	'$';
      TOK_AMPERSAND             :               '&';
      TOK_HASH                  :               '#';
      TOK_DOUBLE_QUOTE		:		'"';
      TOK_LCURLY		:               '{';
      TOK_RCURLY		:               '}';
      TOK_DISTINCT		:		('D'|'d')('I'|'i')('S'|'s')('T'|'t')('I'|'i')('N'|'n')('C'|'c')('T'|'t') ;
      TOK_FROM			:		('F'|'f')('R'|'r')('O'|'o')('M'|'m') ;
      TOK_SELECT		:		('S'|'s')('E'|'e')('L'|'l')('E'|'e')('C'|'c')('T'|'t') ;
      TOK_DELETE		:		('D'|'d')('E'|'e')('L'|'l')('E'|'e')('T'|'t')('E'|'e');
      TOK_IN			:		('I'|'i')('N'|'n') ;
      TOK_AS			:		('A'|'a')('S'|'s') ;
      TOK_WHERE			:		('W'|'w')('H'|'h')('E'|'e')('R'|'r')('E'|'e') ;
      TOK_GROUP			:		('G'|'g')('R'|'r')('O'|'o')('U'|'u')('P'|'p') ;
      TOK_BY			:		('B'|'b')('Y'|'y') ;
      TOK_HAVING		:		('H'|'h')('A'|'a')('V'|'v')('I'|'i')('N'|'n')('G'|'g') ;
      TOK_ORDER			:		('O'|'o')('R'|'r')('D'|'d')('E'|'e')('R'|'r') ;
      TOK_ASC			:		('A'|'a')('S'|'s')('C'|'c') ;
      TOK_DESC			:		('D'|'d')('E'|'e')('S'|'s')('C'|'c') ;
      TOK_OR			:		('O'|'o')('R'|'r') ;
      TOK_AND			:		('A'|'a')('N'|'n')('D'|'d') ;
      TOK_FOR			:		('F'|'f')('O'|'o')('R'|'r') ;
      TOK_ALL			:		('A'|'a')('L'|'l')('L'|'l') ;
      TOK_EXISTS		:		('E'|'e')('X'|'x')('I'|'i')('S'|'s')('T'|'t')('S'|'s') ;
      TOK_LIKE			:		('L'|'l')('I'|'i')('K'|'k')('E'|'e') ;
      TOK_BETWEEN		:		('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n') ;
      TOK_UNION			:		('U'|'u')('N'|'n')('I'|'i')('O'|'o')('N'|'n') ;
      TOK_EXCEPT		:		('E'|'e')('X'|'x')('C'|'c')('E'|'e')('P'|'p')('T'|'t') ;
      TOK_INTERSECT		:		('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('S'|'s')('E'|'e')('C'|'c')('T'|'t') ;
      TOK_MOD			:		('M'|'m')('O'|'o')('D'|'d') ;
      TOK_ABS			:		('A'|'a')('B'|'b')('S'|'s') ;
      TOK_NOT			:		('N'|'n')('O'|'o')('T'|'t') ;
      TOK_FIRST			:		('F'|'f')('I'|'i')('R'|'r')('S'|'s')('T'|'t') ;
      TOK_LAST			:		('L'|'l')('A'|'a')('S'|'s')('T'|'t') ;
      TOK_SLIDING		:		('S'|'s')('L'|'l')('I'|'i')('D'|'d')('I'|'i')('N'|'n')('G'|'g') ;
      TOK_TUMBLING		:		('T'|'t')('U'|'u')('M'|'m')('B'|'b')('L'|'l')('I'|'i')('N'|'n')('G'|'g') ;
      TOK_UNIQUE		:		('U'|'u')('N'|'n')('I'|'i')('Q'|'q')('U'|'u')('E'|'e') ;
      TOK_SUM			:		('S'|'s')('U'|'u')('M'|'m') ;
      TOK_MIN			:		('M'|'m')('I'|'i')('N'|'n') ;
      TOK_MAX			:		('M'|'m')('A'|'a')('X'|'x') ;
      TOK_AVG			:		('A'|'a')('V'|'v')('G'|'g') ;
      TOK_COUNT			:		('C'|'c')('O'|'o')('U'|'u')('N'|'n')('T'|'t') ;
      TOK_PENDING_COUNT		:		('P'|'p')('E'|'e')('N'|'n')('D'|'d')('I'|'i')('N'|'n')('G'|'g')('_')('C'|'c')('O'|'o')('U'|'u')('N'|'n')('T'|'t') ;
      TOK_UNDEFINED		:		('I'|'i')('S'|'s')('_')('U'|'u')('N'|'n')('D'|'d')('E'|'e')('F'|'f')('I'|'i')('N'|'n')('E'|'e')('D'|'d') ;
      TOK_DEFINED		:		('I'|'i')('S'|'s')('_')('D'|'d')('E'|'e')('F'|'f')('I'|'i')('N'|'n')('E'|'e')('D'|'d') ;
      TOK_NULL			:		('N'|'n')('U'|'u')('L'|'l')('L'|'l') ;
      TOK_TRUE 			:       	('T'|'t')('R'|'r') ('U'|'u') ('E'|'e')  { setText("TRUE"); };
      TOK_FALSE 		:		('F'|'f')('A'|'a')('L'|'l')('S'|'s')('E'|'e')   { setText("FALSE"); };
      TOK_CONCEPT		:		('C'|'c')('O'|'o')('N'|'n')('C'|'c')('E'|'e')('P'|'p')('T'|'t') ;
      TOK_ENTITY		:		('E'|'e')('N'|'n')('T'|'t')('I'|'i')('T'|'t')('Y'|'y') ;
      TOK_EVENT			:		('E'|'e')('V'|'v')('E'|'e')('N'|'n')('T'|'t') ;
      TOK_OBJECT		:		('O'|'o')('B'|'b')('J'|'j')('E'|'e')('C'|'c')('T'|'t') ;
      TOK_INT			:		('I'|'i')('N'|'n')('T'|'t') ;
      TOK_LONG			:		('L'|'l')('O'|'o')('N'|'n')('G'|'g') ;
      TOK_DOUBLE		:		('D'|'d')('O'|'o')('U'|'u')('B'|'b')('L'|'l')('E'|'e') ;
      TOK_CHAR			:		('C'|'c')('H'|'h')('A'|'a')('R'|'r') ;
      TOK_STRING		:		('S'|'s')('T'|'t')('R'|'r')('I'|'i')('N'|'n')('G'|'g') ;
      TOK_BOOLEAN		:		('B'|'b')('O'|'o')('O'|'o')('L'|'l')('E'|'e')('A'|'a')('N'|'n') ;
      TOK_DATETIME		:		('D'|'d')('A'|'a')('T'|'t')('E'|'e')('T'|'t')('I'|'i')('M'|'m')('E'|'e') ;
      TOK_DATE			:		('D'|'d')('A'|'a')('T'|'t')('E'|'e') ;
      TOK_TIME			:		('T'|'t')('I'|'i')('M'|'m')('E'|'e') ;
      TOK_TIMESTAMP		:		('T'|'t')('I'|'i')('M'|'m')('E'|'e')('S'|'s')('T'|'t')('A'|'a')('M'|'m')('P'|'p') ;
      //TOK_THIS		:		('T'|'t')('H'|'h')('I'|'i')('S'|'s') ;
      TOK_LIMIT			:		('L'|'l')('I'|'i')('M'|'m')('I'|'i')('T'|'t') ;
      TOK_ACCEPT		:       ('A'|'a')('C'|'c')('C'|'c')('E'|'e')('P'|'p')('T'|'t') ;
      TOK_EMIT			:               ('E'|'e')('M'|'m')('I'|'i')('T'|'t') ;
      TOK_POLICY		:               ('P'|'p')('O'|'o')('L'|'l')('I'|'i')('C'|'c')('Y'|'y') ;
      TOK_MAINTAIN		:               ('M'|'m')('A'|'a')('I'|'i')('N'|'n')('T'|'t')('A'|'a')('I'|'i')('N'|'n') ;
      TOK_USING			:               ('U'|'u')('S'|'s')('I'|'i')('N'|'n')('G'|'g') ;
      TOK_PURGE			:               ('P'|'p')('U'|'u')('R'|'r')('G'|'g')('E'|'e') ;
      TOK_WHEN			:               ('W'|'w')('H'|'h')('E'|'e')('N'|'n') ;
      TOK_OFFSET		:	        ('O'|'o')('F'|'f')('F'|'f')('S'|'s')('E'|'e')('T'|'t') ;
      TOK_NEW			:               ('N'|'n')('E'|'e')('W'|'w') ;
      TOK_DEAD			:               ('D'|'d')('E'|'e')('A'|'a')('D'|'d') ;
      TOK_TIME_MILLISECONDS	:		('M'|'m')('I'|'i')('L'|'l')('L'|'l')('I'|'i')('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d')('S'|'s') ;
      TOK_TIME_SECONDS		:		('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d')('S'|'s') ;
      TOK_TIME_MINUTES		:		('M'|'m')('I'|'i')('N'|'n')('U'|'u')('T'|'t')('E'|'e')('S'|'s') ;
      TOK_TIME_HOURS		:		('H'|'h')('O'|'o')('U'|'u')('R'|'r')('S'|'s') ;
      TOK_TIME_DAYS		:		('D'|'d')('A'|'a')('Y'|'y')('S'|'s') ;

      /*
       * Names
       */
      fragment
      HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

      /* BRJ: support -> and $ */
      fragment
      NameFirstCharacter:
/*
              ( 'A'..'Z' | 'a'..'z' | '_' );
*/
/* This is the same definition as Rules.g - Letter */
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
           '\uf900'..'\ufaff' |
           '\uff61'..'\uff9f'
        ;


      /* BRJ: use NameFirstCharacter as base */
      fragment
      NameCharacter:
/*
              ( NameFirstCharacter | '0'..'9' | TOK_DOLLAR );
*/
/* This is the same definition as Rules.g - JavaIDDigit */
           '\u0030'..'\u0039' |
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
           '\u1040'..'\u1049' |
           TOK_DOLLAR
       ;

      fragment
      OctalEscape
          :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
          |   '\\' ('0'..'7') ('0'..'7')
          |   '\\' ('0'..'7')
          ;

      fragment
      UnicodeEscape
          :   ('\\' 'u' HexDigit HexDigit HexDigit HexDigit)
          ;

	  fragment
      EscapeSequence
          :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
          |   '\\' 'Q' ( options {greedy=false;} : . )* '\\' 'E'
          |   UnicodeEscape
          |   OctalEscape
          ;

      fragment
        IntegerTypeSuffix : ('l'|'L') ;

      fragment
        Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

      fragment
        FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

      //FloatingPointLiteral
      //  :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
      //  |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
      //  |   ('0'..'9')+ Exponent FloatTypeSuffix?
      //  |   ('0'..'9')+ Exponent? FloatTypeSuffix
      //  ;

      HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

      fragment
      DIGIT_ZERO : '0';

      fragment
      DIGIT_OCTAL_RANGE : '1'..'7';

      fragment
      DIGIT_DECIMAL_RANGE : DIGIT_OCTAL_RANGE | '8'..'9' ;

      fragment
      DIGIT_FULL_RANGE : DIGIT_ZERO | DIGIT_DECIMAL_RANGE ;

      fragment
      OCTALDIGIT : DIGIT_ZERO | DIGIT_OCTAL_RANGE ;

      fragment
      DIGIT : DIGIT_OCTAL_RANGE | '8' | '9' ;

      DIGITS : DIGIT_ZERO
                | (DIGIT_DECIMAL_RANGE DIGIT_FULL_RANGE*) ;

      DecimalLiteral : DIGIT('0' | DIGIT)* IntegerTypeSuffix? ;

      OctalLiteral : '0' OCTALDIGIT+ IntegerTypeSuffix? ;

      DateTimeLiteral :
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_MINUS
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_MINUS
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         'T'
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_COLON
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_COLON
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_DOT
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         ( TOK_PLUS | TOK_MINUS )
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
      ;

      CharLiteral
        :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\'' { setText(getText().substring(1, getText().length()-1)); }
        ;

      StringLiteral
        :  '"' ( EscapeSequence | ~('\\'|'"') )* '"' { setText(getText().substring(1, getText().length()-1)); }
        ;

      /* Numbers */
      //fragment
      //DIGIT
      //	:
      //	 '0'..'9'
      //	;

      //DIGITS
      //	:
      //	 DIGIT DIGIT*
      //	;

      /* a couple protected methods to assist in matching floating point numbers */
      fragment
      TOK_APPROXIMATE_NUMERIC_LITERAL
              :       ('e'|'E') ('+'|'-')? DIGITS
      	;


      /* a numeric literal */
      TOK_EXACT_NUMERIC_LITERAL :

                  '.'
                  DIGITS
                  { $type = TOK_EXACT_NUMERIC_LITERAL; }

                  (
                      TOK_APPROXIMATE_NUMERIC_LITERAL
                      { $type = TOK_APPROXIMATE_NUMERIC_LITERAL; }
                  )?

              |   DIGITS
                  { $type = DIGIT; }

                  /* only check to see if it's a float if looks like decimal so far */
                  (
                      '.'
                      (DIGITS)*
                      { $type = TOK_EXACT_NUMERIC_LITERAL; }
                      (TOK_APPROXIMATE_NUMERIC_LITERAL { $type = TOK_APPROXIMATE_NUMERIC_LITERAL; } )?

                  |    TOK_APPROXIMATE_NUMERIC_LITERAL { $type = TOK_APPROXIMATE_NUMERIC_LITERAL; }
                  )? /* cristi, 20001027, ? was missing */
      	;


    /*
    ** Define a lexical rule to handle strings.
    */


    //  CharLiteral
    //                        :
    //          ( '\''
    //          (
    //              '\'' '\''           { setText("'"); }
    //          |   '\n'                { skip();/* newline(); */ }
    //          |   ~( '\'' | '\n' )
    //          )*
    //          '\'' ) { setText(getText().substring(1, getText().length()-1)); }
    //      ;


    // StringLiteral
    //                         : (
    //          '"'!
    //          (
    //              '\\' '"'            { setText("\""); }
    //          |   '\n'                { skip();/* newline(); */ }
    //          |   ~( '\"' | '\n' )   /* cristi, 20001028, was '\'' instead of '\"' */
    //          )*
    //          '"'
    //          ) { setText(getText().substring(1, getText().length()-1)); }
    //      ;


    Identifier
                  :

              NameFirstCharacter (NameFirstCharacter | NameCharacter)*
          ;


      /*
      ** Define white space so that we can throw out blanks
      */
      WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
          ;


      NewLine :
              (
              	'\n'   /* Unix */
              |   '\r\n' /* Dos/Windows */
              |   '\r'   /* Macintosh  */
              )   { /* newline();*/ $channel=HIDDEN; }
          ;

      /*
      ** Define a lexical rule to handle line comments.
      */
      CommentLine :
              '/' '/'
              (
                  ~'\n'
              )*
              ('\n' | EOF)   { $channel=HIDDEN; setText(getText().substring(2, getText().length())); }
          ;

      /*
       Single-line comments
      */
      SLComment :		'--'
                      (~('\n'|'\r'))* ('\n'|'\r'('\n')?|EOF)
              { $channel=HIDDEN;setText(getText().substring(2, getText().length())); }
              ;

      /*
      ** Define a lexical rule to handle block comments.
      */


      MultiLineComment
          :   '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;}
          ;


      query :
              selectExpr ( TOK_SEMIC)? EOF  -> selectExpr
              | deleteExpr ( TOK_SEMIC)? EOF  -> deleteExpr
              | EOF -> EOF
          ;

      selectExpr :
              lc=TOK_SELECT

              (limitClause)?

              (TOK_DISTINCT)?

              projectionAttributes

              fromClause
              ( whereClause )?
              ( groupClause )?
              ( orderClause )?
      	-> ^(SELECT_EXPR[$lc,$selectExpr.text] (limitClause)? (TOK_DISTINCT)? projectionAttributes fromClause (whereClause)? (groupClause)? (orderClause)? )
          ;

      deleteExpr :
              lc=TOK_DELETE {}
             (TOK_STAR)?
              fc = fromClause
              whereClause -> ^(DELETE_EXPR[$lc,$deleteExpr.text] ^(PROJECTION_ATTRIBUTES[$lc,"id,extId"]  ^(PROJECTION[$lc,"id,extId"] IDENTIFIER[$lc,"id"] IDENTIFIER[$lc,"extId"])) fromClause whereClause)
              //whereClause -> ^(DELETE_EXPR[$lc,$deleteExpr.text] fromClause whereClause)
              ;

      fromClause :
              lc=TOK_FROM iteratorDef
              (
                  TOK_COMMA
                  iteratorDef
              )*
              -> ^(FROM_CLAUSE[$lc,$fromClause.text]  iteratorDef+ )
          ;


      iteratorDef :
      /*
                  (
                  	labelIdentifier TOK_IN expr
                  ) -> ^(ITERATOR_DEF ^(IN_CLAUSE labelIdentifier  expr))
                  |
      */
      		( pathExpr (streamDef | aliasDef)? )
      		    -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] ^(PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr) streamDef? aliasDef? )
      		|
      		( TOK_LPAREN selectExpr TOK_RPAREN aliasDef?)
      		    -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] selectExpr aliasDef )

          	;

      aliasDef
      	:
      		TOK_AS? labelIdentifier
               -> ^(ALIAS_CLAUSE[$aliasDef.start,$aliasDef.text] labelIdentifier )
      	;

      streamDef
      	:
      		TOK_LCURLY streamDefItems TOK_RCURLY aliasDef
              	-> ^( STREAM_DEF[$streamDef.start,$streamDef.text] aliasDef streamDefItems )
      	;

      streamDefItems
      	:
      		(streamDefItemEmit (TOK_SEMIC! streamDefItemPolicy)? (TOK_SEMIC! streamDefItemAccept)?)
      		|
      		(streamDefItemEmit (TOK_SEMIC! streamDefItemAccept)? (TOK_SEMIC! streamDefItemPolicy)?)
            |
      		(streamDefItemAccept (TOK_SEMIC! streamDefItemEmit)? (TOK_SEMIC! streamDefItemPolicy)?)
            |
      		(streamDefItemAccept (TOK_SEMIC! streamDefItemPolicy)? (TOK_SEMIC! streamDefItemEmit)?)
            |
      		(streamDefItemPolicy (TOK_SEMIC! streamDefItemAccept)? (TOK_SEMIC! streamDefItemEmit)?)
            |
      		(streamDefItemPolicy (TOK_SEMIC! streamDefItemEmit)? (TOK_SEMIC! streamDefItemAccept)?)
      	;

      streamDefItemEmit
      	:
      		TOK_EMIT TOK_COLON
      		(
      		    TOK_NEW -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text])
      		    | TOK_DEAD -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text])
      		)
      	;

      streamDefItemAccept
      	:
      		TOK_ACCEPT TOK_COLON
      		(
      		    TOK_NEW -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text])
      		    | TOK_ALL -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text])
      		)
      	;

      streamDefItemPolicy
      	:
      		TOK_POLICY TOK_COLON TOK_MAINTAIN
      		windowDef
      		whereClause?
      		policyBy?
      		-> ^( POLICY_CLAUSE[$streamDefItemPolicy.start,$streamDefItemPolicy.text] policyBy? whereClause?
      		      ^( WINDOW_DEF[$windowDef.start,$windowDef.text] windowDef)
      		    )
      	;

      policyBy
      	:
      		TOK_BY expr (TOK_COMMA expr)*
      		-> ^( POLICY_BY_CLAUSE[$policyBy.start,$policyBy.text] expr+)
        ;

      windowDef
      	:
      		timeWindowDef
      		|
      		tumblingWindowDef
      		|
      		slidingWindowDef
      	;

      timeWindowDef
      	:
      		( TOK_LAST DIGITS timeUnit
      		  (usingClause)?
      		  (windowPurgeExpr)?
      		)
      		=>
      		TOK_LAST DIGITS timeUnit
      		(usingClause)?
      		(windowPurgeExpr)?

      		-> ^( TIME_WINDOW[$timeWindowDef.start,$timeWindowDef.text] DIGITS timeUnit
      		      usingClause?
      		      windowPurgeExpr?)
      	;

      timeUnit
      	:
      		TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS
      	;

	usingClause :
		TOK_USING expr
	      	-> ^(USING_CLAUSE[$usingClause.start,$usingClause.text] expr)
	;


      slidingWindowDef
      	:
      	    (TOK_LAST DIGITS TOK_SLIDING)
      		=>
      		TOK_LAST DIGITS TOK_SLIDING
      		(windowPurgeExpr)?
      		-> ^( SLIDING_WINDOW[$slidingWindowDef.start,$slidingWindowDef.text] DIGITS windowPurgeExpr?)
      	;

      tumblingWindowDef
      	:
      		(TOK_LAST DIGITS TOK_TUMBLING)
      		=>
      		TOK_LAST DIGITS TOK_TUMBLING
      		-> ^( TUMBLING_WINDOW[$tumblingWindowDef.start,$tumblingWindowDef.text] DIGITS)
      	;

      windowPurgeExpr
      	:
      		//todo Special Functions here
      		//todo expr does not allow "count(1) >= 100" etc
      		TOK_PURGE TOK_FIRST first=expr TOK_WHEN when=expr
      		-> ^( PURGE_CLAUSE[$windowPurgeExpr.start,$windowPurgeExpr.text]
      		      ^(PURGE_FIRST_CLAUSE[$TOK_FIRST,$first.text] $first)
      		      ^(PURGE_WHEN_CLAUSE[$TOK_WHEN,$when.text] $when)
      		     )
      	;

      pathExpr :
        labelIdentifier //-> ^(PATH_EXPRESSION[$pathExpr.start,$pathExpr.text]  labelIdentifier )
      	| TOK_SLASH labelIdentifier
      	(
      		TOK_SLASH labelIdentifier
      	)*
      	//-> ^(PATH_EXPRESSION[$pathExpr.start,$pathExpr.text]  labelIdentifier+ )

      	  //s=stringLiteral -> ^(PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] $s)
      ;

      whereClause :

              TOK_WHERE expr
              -> ^(WHERE_CLAUSE[$whereClause.start,$whereClause.text] expr)
          ;


      projectionAttributes :
      	projectionList	-> ^(PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text]  projectionList)
      	|   scopeIdentifier -> ^(PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] ^(PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier))
          ;



      projectionList :
      		lc+=projection
              (
                  TOK_COMMA
                  lc+=projection
              )*
              -> $lc+
      	//-> ^(PROJECTION[$lc.start,"PROJECTION"] projection)+
      	;

      projection :
              (
                  a=expr
                  (
                      (
                          ( TOK_AS )?
                          li=labelIdentifier
                      )?
                      -> ^(PROJECTION[$expr.start,$projection.text] $a ^(ALIAS_CLAUSE[$li.start,$li.text]  labelIdentifier? )? )
                     /* |
                      (
                          TOK_DOT lc=TOK_STAR
                      )
                      -> ^(SCOPED_PROJECTION ^(TOK_DOT expr ASTERISK_CLAUSE)) */
                     //|  TOK_DOT e=extExpr ->^(PROJECTION[$expr.start,"PROJECTION"] $projection $e? )
                  )
               )
          ;

       extExpr	:
           (
              expr
              (
                 lc=TOK_DOT a=expr -> ^($extExpr $a )
              )*
           )
       ;

      groupClause :

              TOK_GROUP TOK_BY groupColumn
              (
                  TOK_COMMA groupColumn
              )*
              havingClause?
      		-> ^(GROUP_CLAUSE[$groupClause.start,$groupClause.text] groupColumn+ havingClause?)
          ;

      havingClause:
      	(
      		TOK_HAVING  expr
      	)
      	-> ^(HAVING_CLAUSE[$havingClause.start,$havingClause.text] expr)
      ;

      groupColumn :

      	fieldList    -> fieldList
          ;

      orderClause :

              TOK_ORDER TOK_BY
              sortCriterion
              (
                  TOK_COMMA sortCriterion
              )*
              -> ^(ORDER_CLAUSE[$orderClause.start,$orderClause.text] sortCriterion+ )
          ;

      sortCriterion :
          (
              expr
              sortDirection?
              limitClause?
          ) -> ^(SORT_CRITERION[$sortCriterion.start,$sortCriterion.text] expr ^(SORT_DIRECTION sortDirection? ) limitClause? )
          ;

      sortDirection :
                  TOK_ASC -> TOK_ASC
              |   TOK_DESC -> TOK_DESC
              |   /* Nothing */ -> TOK_ASC
      ;

      limitClause:
              ( TOK_LCURLY TOK_LIMIT TOK_COLON limitDef  TOK_RCURLY )
                -> limitDef
          ;

      limitDef
      	:
      		first=limitFirst (offset=limitOffset)?
      		-> ^(LIMIT_CLAUSE[$limitDef.start,$limitDef.text] $first $offset?)
      	;

      limitFirst :
      		TOK_FIRST firstAsDigits=DIGITS -> ^($firstAsDigits)
      		| TOK_FIRST bindVar -> ^(bindVar)
      	;

      limitOffset :
      		TOK_OFFSET offsetAsDigits=DIGITS -> ^($offsetAsDigits)
      		| TOK_OFFSET bindVar -> ^(bindVar)
      	;

      expr :

      /*
              (
                  TOK_LPAREN
                  type
                  TOK_RPAREN
              )*
              orExpr
      */
      	//orExpr -> orExpr
      	castExpr -> castExpr
          ;


      castExpr :
      	  (TOK_LPAREN type (TOK_LBRACK TOK_RBRACK)* TOK_RPAREN expr)
	=> TOK_LPAREN type (TOK_LBRACK TOK_RBRACK)* TOK_RPAREN expr
	-> ^(CAST_EXPRESSION[$castExpr.start,$castExpr.text] expr type TOK_LBRACK*)
	| (orExpr -> orExpr)
      ;

      orExpr :
              ( andExpr -> andExpr )
              (
                  lc=TOK_OR
                  a=andExpr -> ^(OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a )
              )*
          ;

      andExpr :
      	(
              ( quantifierExpr -> quantifierExpr)
              (
                  lc=TOK_AND
                  a=quantifierExpr -> ^(AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a)
              )*
           )
          ;

      quantifierExpr :
      	(
              (
              ( equalityExpr -> equalityExpr )
//
//              |   ( lc=TOK_FOR TOK_ALL labelIdentifier)
//                  /* "in"  expr */bindVar
//                  ( inClause
//                    //TOK_COLON equalityExpr
//                   )
//      			-> ^(FOR_ALL_CLAUSE[$lc,"FOR_ALL_CLAUSE"] labelIdentifier inClause )
//      			//TOK_COLON equalityExpr )
//              |   lc=TOK_EXISTS labelIdentifier
//                  /* "in" expr */
//                  inClause
//                  //TOK_COLON equalityExpr
//                  -> ^( EXISTS_CLAUSE[$lc,"EXISTS_CLAUSE"] labelIdentifier inClause )
//                 //TOK_COLON equalityExpr )
              )
      	)
          ;

//      inClause :
//      		lc=TOK_IN expr  -> ^(IN_CLAUSE[$lc,"IN_CLAUSE"] expr)
//      	;

      equalityOp :
      			TOK_EQ
      			| TOK_NE
      	;
      equalityExpr :
              ( relationalExpr -> relationalExpr )
              (
                  e=equalityOp
                  /* Simple comparison of expressions */
                  r1=relationalExpr -> ^($e $equalityExpr $r1 )

                  /* Like comparison */
                  | likeOp = TOK_LIKE pattern = unaryExpr -> ^(LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern)
//              	|   lc=TOK_LIKE r2=relationalExpr  -> ^(LIKE_CLAUSE[$lc,$lc.text] $equalityExpr $r2 )
              )*
          ;

      relationalOp:
          TOK_LT
          |   TOK_GT
          |   TOK_LE
          |   TOK_GE
      ;

      relationalExpr :
              ( additiveExpr -> additiveExpr )
              (
                  r=relationalOp a=additiveExpr -> ^($r $relationalExpr $a)
      				/* Simple comparison of expressions */
                  | (lc=TOK_BETWEEN  a5=additiveExpr a1=TOK_AND a6=additiveExpr)
                      -> ^(BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^(RANGE_EXPRESSION[$a1,"RANGE_EXPRESSION"] $a5 $a6) )
              )*
          ;
      additiveOp:
              TOK_PLUS
          |   TOK_MINUS
          |   TOK_CONCAT
          //|   TOK_UNION
          //|   TOK_EXCEPT
      ;
      additiveExpr :
              ( multiplicativeExpr -> multiplicativeExpr )
              (
                  a=additiveOp m=multiplicativeExpr -> ^($a $additiveExpr $m)
              )*
          ;
      multiplicativeOp :
          TOK_STAR
      |   TOK_SLASH
      |   TOK_MOD
      //|   TOK_INTERSECT
      ;
      multiplicativeExpr :
              ( inExpr -> inExpr )
              (
                  m=multiplicativeOp i=inExpr -> ^($m $multiplicativeExpr $i)
              )*
          ;

      inExpr :
              (
               unaryExpr -> unaryExpr
              )
              (
                  lc=TOK_IN
                  (
                   u=unaryExpr  -> ^(IN_CLAUSE[$lc,$inExpr.text] $inExpr $u )
                  |
                   TOK_LPAREN fieldList TOK_RPAREN -> ^(IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList )
                  )
              )?
          ;

      unaryOp:
                  TOK_PLUS
              |   TOK_MINUS -> UNARY_MINUS[$unaryOp.start,$unaryOp.text]
              |   TOK_ABS
              |   TOK_NOT
      ;

      xunaryExpr:

              (
                  //u+=unaryOp (u+=unaryOp)*

                  TOK_PLUS^
                  |   TOK_MINUS^
                  |   TOK_ABS^
                  |   TOK_NOT^

              )* ( p=postfixExpr )
              //-> ^(UNARY_EXPRESSION[$unaryExpr.start,"UNARY_EXPRESSION"] ^(UNARY_OP $u+) $p)
              //)

            //| postfixExpr -> postfixExpr


          ;

      unaryExpr :
           unaryOp x=unaryExpr -> ^(unaryOp $x)
           | postfixExpr -> postfixExpr
       ;

      postfixOp:
        TOK_DOT
      	| TOK_AT
      ;


      postfixExpr:
      	(
      	    primaryExpr -> primaryExpr
      	)
        ( a=arrayIndexExpr -> ^(ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a ) )?
        (
            (
                (p=postfixOp  li=labelIdentifier)
                     -> ^($p $postfixExpr $li )
                     //(
                     //    al=argList	-> ^(FUNCTION_EXPRESSION[$li.start,"FUNCTION_EXPRESSION-pfE"] ^($p $postfixExpr $li) $al )
                     //
                     //    |   /* NOTHING */  -> ^($p $postfixExpr $li)
                     //)
             )
            ( a=arrayIndexExpr -> ^(ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a ) )?
          )*
           (  p=postfixOp  si=scopeIdentifier -> ^($p $postfixExpr $si ) )?
          ;


      arrayIndexExpr:
            (TOK_LBRACK index TOK_RBRACK) -> index
        ;


      index :
              /* single element */
              ( e=expr -> expr )
              (
                //    /* multiple elements */
                //
                //    (
                //        TOK_COMMA
                //        e=expr
                //    )+    -> ^(INDEX_MULTIPLE $e+)

              //    /* Range of elements */
              // |   TOK_COLON r=expr -> ^(RANGE_EXPRESSION $index $r )

              |   /* NOTHING */ -> ^(INDEX_SINGLE $index)

              )
          ;


      pathFunctionExpr:
      	pathExpr argList -> ^(PATHFUNCTION_EXPRESSION[$pathFunctionExpr.start,$pathFunctionExpr.text] ^(PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr) argList)
      	;

      blockExpr:
      (lc=TOK_LPAREN expr TOK_RPAREN ) -> ^(BLOCK_EXPRESSION[$lc,$blockExpr.text] expr)
      ;

      primaryExpr :
              (
              blockExpr -> blockExpr
              /* | conversionExpr -> conversionExpr */
              |   collectionExpr -> collectionExpr
              |   aggregateExpr  ->  aggregateExpr
              |   pathFunctionExpr ->pathFunctionExpr
              //|   pathExpr -> pathExpr
              /* |   undefinedExpr */
              |   labelIdentifier
                  (
                      argList -> ^(FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList )
                  |   /* NOTHING */ -> labelIdentifier
                  )
              |   bindVar -> bindVar
              |   literal -> literal
              |   TOK_LPAREN selectExpr TOK_RPAREN -> selectExpr


              )
          ;

      bindVar :
      	 (
      	 lc=TOK_DOLLAR labelIdentifier -> ^(BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier)
      	 );


      argList :

              TOK_LPAREN
              (
                  expr
                  (
                      TOK_COMMA
                      expr
                  )*
              )?

              TOK_RPAREN
              -> ^(ARG_LIST[$argList.start,$argList.text] expr* )
          ;

      //conversionExpr :
      //
      //       (  /* "element" | */
      //           TOK_DISTINCT
      //          /* |   "flatten" */
      //        )
      //        TOK_LPAREN selectExpr TOK_RPAREN
      //        -> ^( FUNCTION_EXPRESSION[$conversionExpr.start,"FUNCTION_EXPRESSION-cvE"] TOK_DISTINCT ^(SUBSELECT_EXPRESSION[selectExpr.start,"SUBSELECT_EXPRESSION"] selectExpr))
      //    ;



      collectionExpr :
      //        (
      //            TOK_FIRST
      //        |   TOK_LAST
      //        |   TOK_UNIQUE
      //        |   TOK_EXISTS
      //        )
                TOK_EXISTS TOK_LPAREN selectExpr TOK_RPAREN
                  -> ^( EXISTS_CLAUSE[$collectionExpr.start,$collectionExpr.text] selectExpr )
          ;


      literals :
           literal (TOK_COMMA literal)*
           -> ^(LITERALS[$literals.start,$literals.text] literal+)
      ;

      aggregateOp :
          TOK_SUM
          |   TOK_MIN
          |   TOK_MAX
          |   TOK_AVG
      ;

      aggregateExpr :
      //        aggregateOp
      //        TOK_LPAREN selectExpr TOK_RPAREN
      //        -> ^(FUNCTION_EXPRESSION[$aggregateExpr.start,"FUNCTION_EXPRESSION-agEq"] aggregateOp ^(ARG_LIST[$selectExpr.start,"ARG_LIST"] ^(SUBSELECT_EXPRESSION[$selectExpr.start,"SUBSELECT_EXPRESSION"] selectExpr)))
      //    |
              aggregateOp
              TOK_LPAREN (TOK_DISTINCT)? expr TOK_RPAREN
              -> ^(FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] aggregateOp (TOK_DISTINCT)? ^(ARG_LIST[$expr.start,$expr.text] expr ))
          |   TOK_PENDING_COUNT
              TOK_LPAREN (TOK_DISTINCT)? TOK_STAR TOK_RPAREN
              -> ^(FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_PENDING_COUNT (TOK_DISTINCT)?)
          |   TOK_COUNT
              TOK_LPAREN
              (
      //          selectExpr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,"FUNCTION_EXPRESSION-cntq"] TOK_COUNT ^(ARG_LIST[$selectExpr.start,"ARG_LIST"] ^(SUBSELECT_EXPRESSION[$selectExpr.start,"SUBSELECT_EXPRESSION"] selectExpr)))
      //          lc=TOK_STAR -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,"FUNCTION_EXPRESSION-cnt*"] TOK_COUNT ^(ARG_LIST[$lc,"ARG_LIST"] ^(ASTERISK_CLAUSE[$lc,"ASTERISK_CLAUSE"])))
      //          | expr TOK_DOT lc=TOK_STAR -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,"FUNCTION_EXPRESSION-cnte.*"] TOK_COUNT ^(ARG_LIST[$expr.start,"ARG_LIST"] ^( TOK_DOT expr ASTERISK_CLAUSE[$lc,"ASTERISK_CLAUSE"] )))
                (TOK_DISTINCT)?
                a=expr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT (TOK_DISTINCT)? ^(ARG_LIST[$expr.start,$expr.text]  expr ))
              | scopeIdentifier -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^(ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier))
              )
              TOK_RPAREN
          ;

      undefinedOp :
      	    TOK_UNDEFINED
      	|   TOK_DEFINED

      ;
      undefinedExpr :
              undefinedOp
              TOK_LPAREN
              selectExpr
              TOK_RPAREN
              -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp ^(SUBSELECT_EXPRESSION[$selectExpr.start,$selectExpr.text] selectExpr))
           |  undefinedOp
              TOK_LPAREN
              expr
              TOK_RPAREN
              -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp expr)
          ;


      fieldList :
           (
              field
              (
                  TOK_COMMA
                  field
              )*
           )	-> ^(FIELD_LIST[$fieldList.start,$fieldList.text] field+)

          ;

      field :
      	(
      /*
      	  labelIdentifier TOK_COLON^ expr
      	  | expr
      */
      		expr
      	)
      	;


      type :

              TOK_LONG
              |   TOK_DOUBLE
              |   TOK_CHAR
              |   TOK_STRING
              |   TOK_BOOLEAN
              |   TOK_DATETIME
              |   TOK_INT
              |   TOK_CONCEPT
              |   TOK_ENTITY
              |   TOK_EVENT
              |   TOK_OBJECT
              |   pathExpr -> ^(PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr)

          ;

      literal :

                  objectLiteral { debugOut( "Object:"+$objectLiteral.text); } -> ^(NULL_LITERAL[$literal.start,$literal.text])
              |   booleanLiteral { debugOut( "Boolean:"+$booleanLiteral.text); } -> ^(BOOLEAN_LITERAL[$literal.start,$literal.text] booleanLiteral)
              |   DIGITS { debugOut( "Int:"+ $DIGITS.text); } -> ^(NUMBER_LITERAL[$literal.start,$literal.text] DIGITS)
              |   decimalLiteral { debugOut( "Long:"+ $decimalLiteral.text); } -> ^(NUMBER_LITERAL[$literal.start,$literal.text] decimalLiteral)
              |   doubleLiteral { debugOut( "Double:"+$doubleLiteral.text); } -> ^(NUMBER_LITERAL[$literal.start,$literal.text] doubleLiteral)
              |   charLiteral { debugOut( "Char:"+ $charLiteral.text); } -> ^(CHAR_LITERAL[$literal.start,$literal.text] charLiteral)
              |   stringLiteral { debugOut( "String:"+ $stringLiteral.text); } -> ^(STRING_LITERAL[$literal.start,$literal.text] stringLiteral)
              |   datetimeLiteral { debugOut( "Date:" + $datetimeLiteral.text); } -> ^(DATETIME_LITERAL[$literal.start,$literal.text] datetimeLiteral)
              |   hexLiteral  { debugOut( "Hex:"+ $hexLiteral.text); } -> ^(NUMBER_LITERAL[$literal.start,$literal.text] hexLiteral)
              |   octalLiteral { debugOut("Octal:" + $octalLiteral.text); } -> ^(NUMBER_LITERAL[$literal.start,$literal.text] octalLiteral)
              //|   timeLiteral { debugOut("Time:"+ $timeLiteral.text); } -> ^(DATETIME_LITERAL[$literal.start,$literal.text] timeLiteral)
              //|   timestampLiteral { debugOut( "Timestamp"+ $timestampLiteral.text); } -> ^(DATETIME_LITERAL[$literal.start,$literal.text] timestampLiteral)
          ;

      objectLiteral :

              TOK_NULL
          ;

      booleanLiteral :

              (
                  TOK_TRUE
              |   TOK_FALSE
              )
          ;

      decimalLiteral :
            DecimalLiteral
      ;

      hexLiteral :
          HexLiteral
      ;

      octalLiteral :
         OctalLiteral
      ;

      doubleLiteral :
              (
                  TOK_APPROXIMATE_NUMERIC_LITERAL
              |   TOK_EXACT_NUMERIC_LITERAL
              )
          ;

      charLiteral :

              CharLiteral
          ;


      stringLiteral :
               StringLiteral
          ;

      datetimeLiteral :
              DateTimeLiteral
          ;

      //timeLiteral :
      //
      //        TOK_TIME StringLiteral
      //    ;

      //timestampLiteral :
      //
      //        TOK_TIMESTAMP StringLiteral
      //    ;

      keyWords:
        TOK_DISTINCT
        | TOK_FROM
        | TOK_SELECT
        | TOK_IN
        | TOK_AS
        | TOK_WHERE
        | TOK_GROUP
        | TOK_BY
        | TOK_HAVING
        | TOK_ORDER
        | TOK_ASC
        | TOK_DESC
        | TOK_OR
        | TOK_AND
        | TOK_FOR
        | TOK_ALL
        | TOK_EXISTS
        | TOK_LIKE
        | TOK_BETWEEN
        | TOK_UNION
        | TOK_EXCEPT
        | TOK_INTERSECT
        | TOK_MOD
        | TOK_ABS
        | TOK_NOT
        | TOK_FIRST
        | TOK_LAST
        | TOK_UNIQUE
        | TOK_SUM
        | TOK_MIN
        | TOK_MAX
        | TOK_AVG
        | TOK_COUNT
        | TOK_UNDEFINED
        | TOK_DEFINED
        | TOK_NULL
        | TOK_TRUE
        | TOK_FALSE
        | TOK_LIMIT
        | TOK_EMIT
        | TOK_POLICY
        | TOK_MAINTAIN
        | TOK_USING
        | TOK_PURGE
        | TOK_WHEN
        | TOK_OFFSET
        | TOK_NEW
        | TOK_DEAD
        | TOK_TIME_MILLISECONDS
        | TOK_TIME_SECONDS
        | TOK_TIME_MINUTES
        | TOK_TIME_HOURS
        | TOK_TIME_DAYS
        | TOK_LONG
        | TOK_DOUBLE
        | TOK_CHAR
        | TOK_STRING
        | TOK_BOOLEAN
        | TOK_DATETIME
        | TOK_DATE
        | TOK_TIME
        | TOK_INT
        | TOK_CONCEPT
        | TOK_EVENT
        | TOK_OBJECT
      ;

      labelIdentifier :
        (
            (
                TOK_HASH keyWords ->IDENTIFIER[$labelIdentifier.start,$keyWords.text]
            )
            | Identifier  ->IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text]
        )
        ;


      scopeIdentifier :
		TOK_STAR -> ^(SCOPE_IDENTIFIER[$scopeIdentifier.start,$scopeIdentifier.text] TOK_STAR)
	;
