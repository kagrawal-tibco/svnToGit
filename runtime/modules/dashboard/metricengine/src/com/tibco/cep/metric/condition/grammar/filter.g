
grammar filter;
      options {
      	language=Java;
          output=AST;
          backtrack=true;
          k=2;
          memoize=true;
      }

      tokens {
      	TOK_SELECTOR;
      	OR_EXPRESSION;
      	AND_EXPRESSION;
      	PARENTHESES_EXPRESSION;
      	RANGE_EXPRESSION;
	BIND_VARIABLE_EXPRESSION;
	IDENTIFIER;
	PROPERTY;
      	UNARY_MINUS; 
      	TOK_NOTLIKE='notlike';
      	TOK_NOR='nor';
      	TOK_NOTIN='notin';
      	TOK_NOTBETWEEN='notbetween';
      	TOK_ISNOT='isnot';
      	
      }
      
      @header {
	package com.tibco.cep.metric.condition.ast;
      }
      @lexer::header{
      	package com.tibco.cep.metric.condition.ast;
      }


      @parser::members {
        protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException
        {
            throw new MismatchedTokenException(ttype, input);
        }

        public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
             System.out.println(e.getMessage());
             throw e;
        }

      }

      @rulecatch {
            catch (RecognitionException rece) {
                throw rece;
            }
        }
      
      
	TOK_RPAREN              :       	')';
	TOK_LPAREN              :       	'(';
	TOK_COMMA               :       	',';
	TOK_DOT		        :       	'.';
	TOK_COLON               :       	':';
	TOK_CONCAT              :       	'|' '|';
      	TOK_EQ		        :       	'=';
	TOK_PLUS                :       	'+';
	TOK_MINUS               :       	'-';
	TOK_SLASH               :       	'/';
	TOK_STAR                :       	'*';
      	TOK_LE                  :       	'<=';
      	TOK_GE                  :       	'>=';
	TOK_NE                  :       	'<>'|'!=';
      	TOK_LT                  :       	'<';
      	TOK_GT                  :       	'>';
      	TOK_DOLLAR		: 		'$';
      	TOK_HASH		:		'#';
      	TOK_AT			:		'@';
	TOK_IN			:		('I'|'i')('N'|'n') ;
	TOK_OR			:		('O'|'o')('R'|'r') ;
	TOK_AND			:		('A'|'a')('N'|'n')('D'|'d') ;
	TOK_LIKE		:		('L'|'l')('I'|'i')('K'|'k')('E'|'e') ;
	TOK_BETWEEN		:		('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n') ;
	TOK_MOD			:		('M'|'m')('O'|'o')('D'|'d') | '%';
	TOK_ABS			:		('A'|'a')('B'|'b')('S'|'s') ;
	TOK_NOT			:		('N'|'n')('O'|'o')('T'|'t') ;
	TOK_NULL		:		('N'|'n')('U'|'u')('L'|'l')('L'|'l') ;
	TOK_TRUE 		:       	('T'|'t')('R'|'r') ('U'|'u') ('E'|'e')  {  };
	TOK_FALSE 		:		('F'|'f')('A'|'a')('L'|'l')('S'|'s')('E'|'e')   {  };
	TOK_DOUBLE		:		('D'|'d')('O'|'o')('U'|'u')('B'|'b')('L'|'l')('E'|'e') ;
      	TOK_IS          	:               ('I'|'i')('S'|'s');

      /*
       * Names
       */
      fragment
      HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

      /* BRJ: support -> and $ */
      fragment
      NameFirstCharacter:
              ( 'A'..'Z' | 'a'..'z' | '_' );

      /* BRJ: use NameFirstCharacter as base */
      fragment
      NameCharacter:
              ( NameFirstCharacter | '0'..'9' );

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
          |   UnicodeEscape
          |   OctalEscape
          ;
      fragment
        IntegerTypeSuffix : ('l'|'L') ;

      fragment
        Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

      fragment
        FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

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
      
      fragment
      SUBDATE
      	:  DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_MINUS
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_MINUS
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE;

      fragment
      SUBTIME
      	:  DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_COLON
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE
         TOK_COLON
         DIGIT_FULL_RANGE DIGIT_FULL_RANGE;
	
	fragment
	SUBTIMEMILLIS : DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE;
	
	fragment
	GMT : DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE;
	
	DateLiteral : '\'' SUBDATE '\'';

	TimeLiteral : '\'' SUBTIME (TOK_DOT SUBTIMEMILLIS (( TOK_PLUS | TOK_MINUS ) GMT)?)? '\'' ;

	DateTimeLiteral : '\'' SUBDATE 'T' SUBTIME (TOK_DOT SUBTIMEMILLIS (( TOK_PLUS | TOK_MINUS ) GMT)?)? '\'';

      StringLiteral
        :  '"' ( EscapeSequence | ~('\\'|'"') )* '"' { }
        | '\'' ( EscapeSequence | ~('\''|'\\') )* '\'' { }
        ;

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

    Identifier
                  :

              NameFirstCharacter
              ( NameCharacter )*
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
              ('\n' | EOF)   { $channel=HIDDEN; }
          ;

      /*
       Single-line comments
      */
      SLComment :		'--'
                      (~('\n'|'\r'))* ('\n'|'\r'('\n')?|EOF)
              { $channel=HIDDEN; }
              ;

      /*
      ** Define a lexical rule to handle block comments.
      */


      MultiLineComment
          :   '/*' (options {greedy=false;} : .)* '*/' { $channel=HIDDEN;}
          ;



selector
    :   return_or = orExpr EOF -> ^(TOK_SELECTOR $return_or )
    
    ;
    
orExpr :
              ( andExpr -> andExpr )
              (
                  lc=TOK_OR
                  a=andExpr -> ^($lc $orExpr $a )
              )*
          ;    

andExpr :
      	(
              ( equalityExpr -> equalityExpr)
              (
                  lc=TOK_AND
                  a=equalityExpr -> ^($lc $andExpr $a)
              )*
           )
          ;    

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
              	|   lc=TOK_LIKE r2=relationalExpr  -> ^($lc $equalityExpr $r2)
              	|   TOK_NOT lc2=TOK_LIKE r2=relationalExpr  -> ^(TOK_NOTLIKE $equalityExpr $r2)
              	|   is=TOK_IS nul=TOK_NULL -> ^($is $equalityExpr $nul) 
              	|   is=TOK_IS return_not=TOK_NOT nul=TOK_NULL -> ^(TOK_ISNOT $equalityExpr $nul) 
       )*
       ; 

relationalOp:
          TOK_LT
          |   TOK_GT
          |   TOK_LE
          |   TOK_GE
      ;

relationalExpr :
       ( ae=additiveExpr -> additiveExpr )
       (
           r=relationalOp a=additiveExpr -> ^($r $relationalExpr $a)
          | bc=( lc=TOK_BETWEEN  a5=additiveExpr a1=TOK_AND a6=additiveExpr ) -> ^($lc ^(TOK_GE $ae $a5) ^(TOK_LE $ae $a6) )
          | bc=( TOK_NOT TOK_BETWEEN  a5=additiveExpr a1=TOK_AND a6=additiveExpr ) -> ^(TOK_NOTBETWEEN ^(TOK_LT $ae $a5) ^(TOK_GT $ae $a6) )
       )*
          ;
      additiveOp:
              TOK_PLUS
          |   TOK_MINUS
          |   TOK_CONCAT
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
      ;
      multiplicativeExpr :
              ( inExpr -> inExpr )
              (
                  m=multiplicativeOp i=inExpr -> ^($m $multiplicativeExpr $i)
              )*
          ;

      inExpr :
              (
               e=unaryExpr -> $e
              )
              (
                  lc=TOK_IN
                  (
                  u=unaryExpr  -> ^(TOK_OR $inExpr $u )
                  | TOK_LPAREN field (TOK_COMMA field)* TOK_RPAREN -> ^(TOK_OR ^(TOK_EQ $e field)+ )
                  )
                
                  | TOK_NOT lc=TOK_IN
                  
                  (
                  u=unaryExpr  -> ^(TOK_NOR $inExpr $u )
                  | TOK_LPAREN field (TOK_COMMA field)* TOK_RPAREN -> ^(TOK_NOR ^(TOK_EQ $e field)+ )
                  )
              )?
              
          ;
       
        

      unaryOp:
                  TOK_PLUS
              |   TOK_MINUS -> UNARY_MINUS[$unaryOp.start,$unaryOp.text]
              |   TOK_ABS
              |   TOK_NOT
      ;
      
      unaryExpr :
           unaryOp x=unaryExpr -> ^(unaryOp $x)
           | term -> term
       ; 

term
    :   TOK_LPAREN o=orExpr TOK_RPAREN -> ^(PARENTHESES_EXPRESSION $o)
    |   literal -> literal
    |   labelIdentifier -> labelIdentifier
    |   bindVar -> bindVar
    ;

bindVar :
	(
 	 lc=TOK_DOLLAR labelIdentifier -> ^(BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier)
 	 );
 	 
labelIdentifier :
        (
            (
                TOK_HASH keyWords ->IDENTIFIER[$labelIdentifier.start,$keyWords.text]
            )
            | Identifier  ->IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text]
            | TOK_AT Identifier -> PROPERTY[$labelIdentifier.start,$labelIdentifier.text]
        )
        ;

valueList :
           (
              f=field
              (
                  TOK_COMMA 
                  f=field
              )*   -> (field)+
           ) 

          ;

field :
      	(
      		orExpr 
      	) 
      	;


 literal :

                  objectLiteral 
              |   booleanLiteral
              |   DIGITS 
              |   decimalLiteral 
              |   doubleLiteral 
              |   dateLiteral
              |   timeLiteral 
              |   datetimeLiteral
              |   stringLiteral 
              |   hexLiteral  
              |   octalLiteral
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


      stringLiteral :
               StringLiteral
          ;

      dateLiteral :
              DateLiteral
          ;

      timeLiteral :
              TimeLiteral
          ;

      datetimeLiteral :
              DateTimeLiteral
          ;


      keyWords:
        TOK_IN
        | TOK_OR
        | TOK_AND
        | TOK_LIKE
        | TOK_BETWEEN
        | TOK_MOD
        | TOK_ABS
        | TOK_NOT
        | TOK_NULL
        | TOK_TRUE
        | TOK_FALSE
        | TOK_DOUBLE
        | TOK_IS
      ;
     
