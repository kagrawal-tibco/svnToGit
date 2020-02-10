/**
 * 
 */
package com.tibco.cep.query.model;

import java.util.List;

/**
 * @author pdhar
 *
 */
public interface Operator{

    //Operator masks
    public static final int OP_MASK_NONE		= 0x0;
    public static final int OP_MASK_UNARY		= 0x1;   //Unary operator mask
    public static final int OP_MASK_BINARY		= 0x2;   //Binary Operator mask
    public static final int OP_MASK_LOGICAL		= 0x4;   //Logical Operator mask
    public static final int OP_MASK_RELATIONAL  = 0x8;   //Relational Operator mask
    public static final int OP_MASK_SIMPLE      = 0x10;  //Op.toString() gives the text
    public static final int OP_MASK_COMPLEX     = 0x20;  //Op.translate().toString gives the text
    public static final int OP_MASK_JAVA		= 0x40;
    public static final int OP_MASK_OQL			= 0x80;

    // Operator types
    public static final int OP_VOID 			= 1;
    public static final int OP_PLUS 			= 2;
    public static final int OP_MINUS			= 3;
    public static final int OP_ABS  			= 4;
    public static final int OP_NOT 				= 5;
    public static final int OP_OR				= 6;
    public static final int OP_AND				= 7;
    public static final int OP_EQ				= 8;
    public static final int OP_NE				= 9;
    public static final int OP_GT				= 10;
    public static final int OP_LT				= 11;
    public static final int OP_GE				= 12;
    public static final int OP_LE				= 13;
    public static final int OP_CONCAT			= 14;
    public static final int OP_MOD				= 15;
    public static final int OP_LIKE				= 16;
    public static final int OP_BETWEEN			= 17;
    public static final int OP_EXISTS			= 18;
    public static final int OP_IN				= 19;
    public static final int OP_DOT              = 20;
    public static final int OP_AT				= 21;
    public static final int OP_MULTIPLY         = 22;
    public static final int OP_DIVIDE           = 23;
    public static final int OP_ARRAY			= 24;
    public static final int OP_RANGE            = 25;
    public static final int OP_UMINUS           = 26;
    public static final int OP_GROUP            = 27;
    public static final int OP_CAST             = 28;


    public static final int[] UNARY_OP_TYPES = {
                                                OP_PLUS,
                                                OP_UMINUS,
                                                OP_NOT,
                                                OP_ABS,
                                                OP_GROUP,
                                                OP_EXISTS,
    };
    public static final int[] BINARY_OP_TYPES = {
                                                OP_PLUS,
                                                OP_MINUS,
                                                OP_MULTIPLY,
                                                OP_DIVIDE,
                                                OP_AND,
                                                OP_OR,
                                                OP_EQ,
                                                OP_NE,
                                                OP_GT,
                                                OP_LT,
                                                OP_GE,
                                                OP_LE,
                                                OP_CONCAT,
                                                OP_IN,
                                                OP_LIKE,
                                                OP_BETWEEN,
                                                OP_DOT,
                                                OP_AT,
                                                OP_ARRAY,
                                                OP_RANGE,
                                                OP_CAST,
    };

    public static final int[] LOGICAL_OP_TYPES = {
                                                OP_AND,
                                                OP_OR,
                                                OP_NOT,
    };

    public static final int[] RELATIONAL_OP_TYPES =  {
                                                OP_EQ,
                                                OP_NE,
                                                OP_GT,
                                                OP_LT,
                                                OP_GE,
                                                OP_LE,
    };

    /**
     *  Simple op types probably dont need a function to help achieve the purpose
     */
    public static final int[] SIMPLE_OP_TYPES = {
                                                OP_PLUS,
                                                OP_MINUS,
                                                OP_MULTIPLY,
                                                OP_DIVIDE,
                                                OP_MOD,
                                                OP_NOT,
                                                OP_AND,
                                                OP_OR,
                                                OP_EQ,
                                                OP_NE,
                                                OP_GT,
                                                OP_LT,
                                                OP_GE,
                                                OP_LE,
                                                OP_DOT,
                                                OP_ARRAY,
                                                OP_UMINUS,
                                                OP_GROUP,
    };
    /**
     * complex op types probably need a function to help achieve the purpose
     */
    public static final int[] COMPLEX_OP_TYPES = {
                                                OP_CAST,
                                                OP_CONCAT,
                                                OP_BETWEEN,
                                                OP_IN,
                                                OP_LIKE,
                                                OP_EXISTS,
                                                OP_ABS,
                                                OP_RANGE,
    };

    /**
     * @return int the operator context mask for unary,binary,nary,none
     */
    public int getOpMask();




    /**
     * @param mask int the Operator mask
     * @return int[] the array of valid operators for the given context;
     */
    public int[] getValidOps(int mask);


    /**
     * @return int the number of operands for the given operator
     */
    public int getOperandCount();

     /**
     * set the Operator Mask
     * @param opMask
     */
    public void setOpMask(int opMask);

    /**
     *
     * @return int the Operator type
     */
    public int getOpType();

    /**
     *
     * @param opType set the Operator Type
     */
    public void setOpType(int opType);

    /**
     *
     * @return String the Java Operator for the given operator
     * null if there is no corresponding java operator
     */
    public String getJavaOperator();

    /**
     * @return boolean true if this operator has a Java language operator
     */
    public boolean hasJavaOperator();

    /**
     * @return String the OQL operator for the given operator
     * null if there is no corresponding oql operator
     */
    public String getOqlOperator();

    /**
     * @return boolean true if this operator has Oql language operator
     */
    public boolean hasOqlOperator();


    TypeInfo getResultType(List<Expression> operands) throws Exception;

}
