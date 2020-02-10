grammar SPMCLI;

options {
    output=AST;
    language=Java;
    ASTLabelType=CommonTree; // type of $stat.tree ref etc...
}


tokens {
    CREATE='create';
    ADD='add';
    REMOVE='remove';
    COMMIT='commit';
    CONNECT='connect';
    SHOW='show';
    LINK='link';
    EXPORT='export';
    IMPORT='import';
    SCHEMA='schema';
    CUBE='cube';
    MEASUREMENT='measurement';
    HIERARCHY='hierarchy';
    ATTRIBUTE='attribute';
    DIMENSION='dimension';
    AGGREGATOR='aggregator';
    USERNAME='username';
    PASSWORD='password';
    IN='in';
    TO='to';
    FROM='from';
    FOR='for';
    DATATYPE='datatype';
    EQUALS='=';
    LPAREN='(';
    RPAREN=')';
    QUOTES='"';

    COMMAND;
    MODEL_BLOCK;
    TARGET_BLOCK;
    TARGET_OBJECTS;
    PREPOSITIONS;
    PREPOSITION;
    BLOCK;
    ATTRIBUTES_BLOCK;
}

@header {
package com.tibco.rta.model.command.ast;
}

@lexer::header {
package com.tibco.rta.model.command.ast;
}

attribute_values
    :   IDENTIFIER;


IDENTIFIER
    :    LETTER (LETTER | JAVAIDDIGIT)*
    ;

fragment
LETTER
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
       '\uf900'..'\ufaff' |
       '\uff61'..'\uff9f'
    ;

fragment
JAVAIDDIGIT
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

model_attribute
    :    DATATYPE
    |    AGGREGATOR;

verb
    :    CREATE
    |    ADD
    |    REMOVE
    |    CONNECT
    |    SHOW
    |    LINK
    |    IMPORT
    |    EXPORT
    |    COMMIT
    ;

model_object
    :    SCHEMA
    |    CUBE
    |    MEASUREMENT
    |    ATTRIBUTE
    |    DIMENSION
    |    HIERARCHY
    |    AGGREGATOR
    ;

creds_object
    :    USERNAME
    |    PASSWORD
    ;

preposition
    :    FROM
    |    IN
    |    TO
    |    FOR
    ;

target_object
    :    model_object
    ;

NEWLINE
    : '\r'? '\n'
    ;


WS
    : (' '|'\t'|'\n'|'\r')+ {skip();}
    ;

verb_expr
    :    WS? verb WS?
    ;

target_object_expr
    :    WS? target_object WS?
    ;

identifier_expr
    :    WS? IDENTIFIER WS?
    ;

datatype_expr
    :    WS? IDENTIFIER WS?
    ;

/**
A single attribute expression attributename="test"
*/
attribute_expr
    :    WS* model_attribute WS* EQUALS WS* QUOTES WS* attribute_values WS* QUOTES WS*
    -> ^(model_attribute ^(IDENTIFIER attribute_values))
    ;

/**
All attributes following an identifier (attr1="test" attr2="hello")
*/
all_attributes_expr
    :  (LPAREN)? (attribute_expr)*  (RPAREN)?
    -> ^(ATTRIBUTES_BLOCK (attribute_expr)*)
    ;

prep_expr
    :    WS? preposition WS?
    ;

model_object_expr
    :    WS? model_object WS?
   ->   model_object
    ;

/**
Identifier followed by all its attributes and repeating it a (attr1="test" attr2="hello") b (attr1="abc")
*/
attrs_with_identifiers_expr
    :    (identifier_expr all_attributes_expr)*
    -> ^(BLOCK ^(IDENTIFIER identifier_expr) all_attributes_expr)*
    ;

/**
Preposition and model objects e.g : in cube C in schema S
*/
preps_with_model_objects_expr
    :    (prep_expr model_object_expr identifier_expr)*
    -> ^(PREPOSITIONS ^(PREPOSITION prep_expr ^(MODEL_BLOCK ^(model_object_expr ^(IDENTIFIER identifier_expr))))*)
    ;

/**
The entire command e.g : create attribute A (dataype="string" aggregator="count") B (dataype="long" aggregator="count")
*/
command_expr
    :    verb_expr target_object_expr attrs_with_identifiers_expr preps_with_model_objects_expr
    -> ^(COMMAND ^(verb_expr ^(TARGET_BLOCK ^(target_object_expr attrs_with_identifiers_expr)) preps_with_model_objects_expr))
    ;
