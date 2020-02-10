/******************************************************************************
 * Copyright 1999 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */


package com.tibco.cep.mapper.xml.xdata.xpath;



/*
* Java Error file for
* BW : TIBCO BusinessWorks
*/

public class MessageCode
{
    public static final String NULL_CONTENT  = "XPATH-100001";
    public static final String REPEATING_IN_NON_REPEATING  = "XPATH-100002";
    public static final String EXPECTED_MORE_THAN_ONE  = "XPATH-100003";
    public static final String EXCEEDS_MAXIMUM_OCCURRENCE  = "XPATH-100004";
    public static final String UNEXPECTED_TOKEN  = "XPATH-100005";
    public static final String NO_FUNCTIONS_IN_NAMESPACE  = "XPATH-100006";
    public static final String CANNOT_RESOLVE_PREFIX  = "XPATH-100010";
    public static final String CANNOT_RESOLVE_FUNCTION_WITHOUT_PREFIX  = "XPATH-100011";
    public static final String NO_SUCH_FUNCTION_IN_NS  = "XPATH-100012";
    public static final String EXPECTED_1_ARGUMENT  = "XPATH-100013";
    public static final String EXPECTED_N_ARGUMENTS  = "XPATH-100014";
    public static final String EXPECTED_AT_LEAST_1_ARGUMENT  = "XPATH-100015";
    public static final String EXPECTED_AT_LEAST_N_ARGUMENTS  = "XPATH-100016";
    public static final String EXPECTED_N_MINUS_OR_N_ARGUMENTS  = "XPATH-100017";
    public static final String EXPECTED_NODE_SET  = "XPATH-100020";
    public static final String NO_MATCHING  = "XPATH-100021";
    public static final String VARIABLE_NOT_DEFINED  = "XPATH-100022";
    public static final String LOSS_OF_PRECISION_ROUNDED  = "XPATH-100030";
    public static final String ILLEGAL_CHARACTER_ESCAPE  = "XPATH-100031";
    public static final String XPATH_INDEXES_1_BASED  = "XPATH-100032";
    public static final String VARIABLE_ALREADY_DEFINED_IN_TEMPLATE  = "XPATH-100100";
    public static final String ILLEGAL_VARIABLE_NAME  = "XPATH-100101";
    public static final String BAD_TYPE_SUBSTITUTION  = "XPATH-100110";
    public static final String CANNOT_LOAD_SCHEMA_FOR_TYPE_SUBSTITUTION  = "XPATH-100111";
    public static final String NO_SUCH_TYPE_FOR_TYPE_SUBSTITUTION  = "XPATH-100112";
    public static final String ABSTRACT_ELEMENT  = "XPATH-100113";
    public static final String ABSTRACT_TYPE  = "XPATH-100114";
    public static final String ELEMENT_NOT_NILLABLE  = "XPATH-100115";
    public static final String CANNOT_LOAD_SCHEMA_FOR_ELEMENT  = "XPATH-100116";
    public static final String NO_SUCH_ELEMENT_IN_SCHEMA  = "XPATH-100117";
    public static final String NO_SUCH_ELEMENT_IN_CONTEXT  = "XPATH-100118";
    public static final String AMBIGUOUS_ELEMENT_IN_PROJECT  = "XPATH-100119";
    public static final String NO_FORMULA_EXPECTED_ON_STRUCTURE  = "XPATH-100120";
    public static final String GROUP_BY_REQUIRED_IN_FOR_EACH_GROUP  = "XPATH-100130";
    public static final String GROUP_BY_MUST_BE_FIRST_CHILD  = "XPATH-100131";
    public static final String ONLY_1_GROUP_BY_ALLOWED  = "XPATH-100132";
    public static final String UNEXPECTED_OUTPUT  = "XPATH-100140";
    public static final String UNEXPECTED_OUTPUT_EXCEEDS_MAXIMUM  = "XPATH-100141";
    public static final String UNEXPECTED_OUTPUT_TEXT  = "XPATH-100142";
    public static final String UNEXPECTED_OUTPUT_NS_MISMATCH  = "XPATH-100143";
    public static final String VALUE_REQUIRED_FOR  = "XPATH-100150";
    public static final String ELEMENT_REQUIRED  = "XPATH-100151";
    public static final String COMPONENTS_INCOMPLETE  = "XPATH-100152";
    public static final String OUTPUT_INCOMPLETE  = "XPATH-100153";
    public static final String OUTPUT_INVALID  = "XPATH-100154";
    public static final String MISSING_PRECEDING_TERMS  = "XPATH-100155";
    public static final String COMPONENTS_CONTAIN_ERRORS  = "XPATH-100190";
    public static final String RENAME_NAMESPACE  = "XPATH-100200";
    public static final String RENAME  = "XPATH-100202";
    public static final String MOVE_TO  = "XPATH-100203";
    public static final String MOVE_TO_BEFORE  = "XPATH-100204";
    public static final String BAD_COPY_OF  = "XPATH-100210";
    public static final String FORMULA_HAS_ERRORS  = "XPATH-100300";
    public static final String FORMULA_HAS_MARKERS  = "XPATH-100301";
    public static final String WHEN_AFTER_OTHERWISE  = "XPATH-100400";
    public static final String ONLY_1_OTHERWISE  = "XPATH-100401";
    public static final String ONLY_WHEN_AND_OTHERWISE  = "XPATH-100402";
    public static final String SORT_STATEMENTS_BEFORE_ANYTHING  = "XPATH-100410";
    public static final String CAN_GENERATE_MORE_THAN_EXPECTED  = "XPATH-100420";
    public static final String SORT_IN_FOR_EACH  = "XPATH-100421";
    public static final String LEX_EXPECTED  = "XPATH-100500";
    public static final String LEX_UNEXPECTED  = "XPATH-100501";
    public static final String LEX_UNEXPECTED_END  = "XPATH-100502";
    public static final String LEX_EXPECTED_VAR_NAME  = "XPATH-100503";
    public static final String COERCION_ERROR  = "XPATH-100600";
    public static final String CANNOT_CONVERT_PRIMITIVE_AS_STRING_GOT_EXPECTED  = "XPATH-200001";
    public static final String BOOLEAN_ALWAYS_TRUE  = "XPATH-200002";
    public static final String BOOLEAN_FROM_STRING_ALWAYS_TRUE  = "XPATH-200003";
    public static final String COMPARED_AS_BOOLEAN  = "XPATH-200004";
    public static final String COMPARED_AS_BOOLEAN_NUMBER  = "XPATH-200005";
    public static final String BOOLEAN_ALWAYS_FALSE  = "XPATH-200006";
    public static final String POSITION_INSIDE_FILTER  = "XPATH-200010";
    public static final String STATEMENT_HAS_NO_EFFECT  = "XPATH-200100";
    public static final String OUTPUT_REQUIRED_SURROUNDING_IF_NOT_NEEDED  = "XPATH-200101";
    public static final String INPUT_NOT_OPTIONAL_SURROUNDING_IF_NOT_NEEDED  = "XPATH-200102";
    public static final String INPUT_NOT_NODE_SET_SURROUNDING_IF_NOT_NEEDED  = "XPATH-200103";
    public static final String SIMPLIFY_TO_NIL_CONSTANT  = "XPATH-200110";
    public static final String SET_COPY_NIL  = "XPATH-200120";
    public static final String CLEAR_COPY_NIL_OUTPUT_NOT_NILLABLE  = "XPATH-200121";
    public static final String CLEAR_COPY_NIL_INPUT_NOT_NILLABLE  = "XPATH-200122";
    public static final String REFERENCED_SCHEMA_ERROR  = "XPATH-200200";
    public static final String ADD_OTHERWISE  = "XPATH-200300";
    public static final String AUTOCAST_INFORMATION  = "XPATH-300001";
    public static final String OPTIMIZE_MOVE_OUT_OF_PREDICATE  = "XPATH-300100";
    public static final String COPY_OF_NAMESPACE_MISMATCH  = "XPATH-301000";
    public static final String COPY_OF_NAME_MISMATCH  = "XPATH-301001";
    public static final String COPY_OF_MISMATCH  = "XPATH-301002";
    public static final String STYLESHEET_NOT_ALLOWED_HERE  = "XPATH-400001";
    public static final String EXPECTED_OUTPUT_BEFORE  = "XPATH-400101";
    public static final String EXPECTED_OUTPUT_AT_END  = "XPATH-400102";
}



