package com.tibco.cep.decision.table.constraintpane;

import java.util.HashMap;

/**
 * User: ssubrama
 * Creation Date: Aug 2, 2008
 * Creation Time: 8:19:58 AM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public interface Operator {

    public static byte DONT_CARE_OPERATOR = 0;
    public static byte EQUALS_OPERATOR = 1; //Exact Match
    public static byte NOTEQ_OPERATOR = 2;
    public static byte RANGE_OPERATOR = 3; //All less than, greater than equals
    public static byte BOOLEAN_OPERATOR =4; //Any OR,XOR,AND
    public static byte FUNCTION_OPERATOR=5;//

    public static byte RANGE_BOUNDED = 0; //less than equal a
    public static byte RANGE_MIN_EXCL_BOUNDED = 1;
    public static byte RANGE_MAX_EXCL_BOUNDED = 2;
    public static byte RANGE_MIN_MAX_EXCL_BOUNDED = 3;
    
//    public static byte RANGE_MIN_STRICT_UNBOUNDED = 1; //less than a
//    public static byte RANGE_MAX_UNBOUNDED = 2; //greater than equal a
//    public static byte RANGE_MAX_STRICT_UNBOUNDED = 3; //greater than a
//    public static byte RANGE_MIN_MAX_BOUNDED = 4; //[a..b] both inclusive
//    public static byte RANGE_MIN_STRICT_MAX_BOUNDED = 5; //]a..b] => foo > a && foo <= b  in the range of a..b
//    public static byte RANGE_MIN_MAX_STRICT_BOUNDED = 6; //foo >= a && foo < b in the range of a..b
//    public static byte RANGE_MIN_STRICT_MAX_STRICT_BOUNDED = 7; //foo > a && foo < b

    public static String[] OperatorTypes = new String[] {"DONT_CARE", "EQUALS", "NOTEQ", "RANGE", "BOOLEAN", "FUNCTION"};
    
    public static final String AND_OP = "&&";
    public static final String OR_OP = "||";
    
    @SuppressWarnings("rawtypes")
	boolean evaluate(HashMap args);

}


