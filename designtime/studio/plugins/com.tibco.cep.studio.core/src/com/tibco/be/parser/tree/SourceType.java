package com.tibco.be.parser.tree;

/**
     * This class is a flag that tells where the 
 * node came from, for example an action or a condition
 */ 
public enum SourceType {
    RULE_CONDITION,
    RULE_ACTION,
    CONDITION_EXPRESSION,
    DT_ACTION_CELL,
    //used when validating individual DT cells
    DT_CONDITION_CELL,
    //dummy used for range
    Functions_Start,
    RULE_FUNCTION_CONDITION_OK,
    RULE_FUNCTION_ACTION_ONLY,
    RULE_FUNCTION_QUERY_OK,
    //DT_RF_CONDITION_OK,
    //used when code generating a RuleFuction that was generated from a Decision Table
    //at this point, everything is in rule function, and the parser can't tell
    //the condition and action cells apart
    DT_RF_ACTION_ONLY,
    //dummy used for range
    Functions_End,
    RT_ATTRIBUTES,
    RT_VIEWS,
    RT_BINDINGS,
    RT_DECLARE,
    RT_WHEN,
    RT_ACTION_CONTEXT;
    
    public boolean isFunction() {
        return Functions_Start.ordinal() < this.ordinal() && this.ordinal() < Functions_End.ordinal(); 
    }
    
    public boolean isDT() {
        return this == DT_CONDITION_CELL || this == DT_ACTION_CELL
                || this == DT_RF_ACTION_ONLY ;
        //|| srcTyp == DT_RF_CONDITION_OK
    }
    
    public boolean isNonActionOnlyRuleFunction() {
        return this==RULE_FUNCTION_CONDITION_OK || this==RULE_FUNCTION_QUERY_OK; 
    }

//    public boolean isRT() {
//        switch (this) {
//            case RT_ACTION_CONTEXT:
//            case RT_ATTRIBUTES:
//            case RT_BINDINGS:
//            case RT_DECLARE:
//            case RT_VIEWS:
//            case RT_WHEN:
//                return true;
//            default:
//                return false;
//        }
//    }
}
