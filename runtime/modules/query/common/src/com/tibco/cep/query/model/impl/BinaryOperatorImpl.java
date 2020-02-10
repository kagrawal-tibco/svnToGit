package com.tibco.cep.query.model.impl;

import java.util.List;

import com.tibco.cep.query.model.BinaryOperator;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.utils.TypeHelper;

/**
 * User: pdhar
 * Date: Jul 15, 2007
 * Time: 8:48:12 PM
 */
public class BinaryOperatorImpl extends AbstractOperator implements BinaryOperator {

    public BinaryOperatorImpl(int opMask, int opType) {
        super(opMask, opType);
    }


    /**
     * @return int the number of operands for the given operator
     */
    public int getOperandCount() {
        return NUM_OPERANDS;
    }


    public TypeInfo getResultType(List<Expression> operands) throws Exception {
        switch(this.opType) {
            case OP_MOD:
            case OP_PLUS:
            case OP_MINUS:
            case OP_MULTIPLY:{
                final TypeInfo typeInfoLeft = operands.get(0).getTypeInfo();
                final TypeInfo typeInfoRight = operands.get(1).getTypeInfo();
                if (null == typeInfoLeft) {
                    if (null == typeInfoRight) {
                        break;
                    }
                    return new TypeInfoImpl(Double.class);
                } else if (null == typeInfoRight) {
                    return new TypeInfoImpl(Double.class);
                }
                return new TypeInfoImpl(TypeHelper.getNumericCoercionClass(
                    typeInfoLeft.getDesigntimeClass(),
                    typeInfoRight.getDesigntimeClass()));
            }

            case OP_DIVIDE: {
                return new TypeInfoImpl(Double.class);
            }
            case OP_CONCAT:
                return new TypeInfoImpl(String.class);
            case OP_AND:
            case OP_OR:
            case OP_EQ:
            case OP_NE:
            case OP_GT:
            case OP_LT:
            case OP_GE:
            case OP_LE:
            case OP_IN:
            case OP_LIKE:
            case OP_BETWEEN: {
                return new TypeInfoImpl(Boolean.class);
            }
            case OP_AT:
            case OP_DOT: {
                return operands.get(1).getTypeInfo();
            }
            case OP_ARRAY: {
                return operands.get(0).getTypeInfo().getArrayItemType();
            }
            case OP_RANGE:{
                final TypeInfo typeInfoLeft = operands.get(0).getTypeInfo();
                final TypeInfo typeInfoRight = operands.get(1).getTypeInfo();
                if (null == typeInfoLeft) {
                    if (null == typeInfoRight) {
                        break;
                    } else if (typeInfoRight.isDateTime()
                            || typeInfoRight.isNumber()
                            || typeInfoRight.isString()) {
                        return typeInfoRight;
                    } else {
                        break;
                    }
                } else if (null == typeInfoRight) {
                    if (typeInfoLeft.isDateTime()
                            || typeInfoLeft.isNumber()
                            || typeInfoLeft.isString()) {
                        return typeInfoLeft;
                    } else {
                        break;
                    }
                } else if (typeInfoLeft.isDateTime()) {
                    if (typeInfoRight.isDateTime()) {
                        return typeInfoRight;
                    } else {
                        break;
                    }
                } else if (typeInfoLeft.isNumber()) {
                    return new TypeInfoImpl(TypeHelper.getNumericCoercionClass(
                        typeInfoLeft.getDesigntimeClass(),
                        typeInfoRight.getDesigntimeClass()));
                } else if (typeInfoLeft.isString()) {
                    if (typeInfoRight.isString()) {
                        return typeInfoRight;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            case OP_CAST: {
                return ((TypeExpressionImpl) operands.get(1)).getInstancesTypeInfo();
            }

        }
        return null;
    }


}
