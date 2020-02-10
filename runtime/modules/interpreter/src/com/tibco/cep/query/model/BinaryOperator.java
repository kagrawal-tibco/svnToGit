/**
 * 
 */
package com.tibco.cep.query.model;


/**
 * @author pdhar
 *
 */
public interface BinaryOperator extends Operator {
    public static final int NUM_OPERANDS = 2;
    public static final int LEFT_OPERAND = 0;
    public static final int RIGHT_OPERAND = 1;

    int validMask = Operator.OP_MASK_BINARY;

}
