package com.tibco.cep.query.model.impl;

import java.util.List;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.UnaryOperator;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 13, 2007
 * Time: 2:02:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnaryOperatorImpl extends AbstractOperator implements UnaryOperator {



    public UnaryOperatorImpl(int opMask, int opType) {
        super(opMask, opType);
    }


    public TypeInfo getResultType(List<Expression> operands) throws Exception {
        return operands.get(0).getTypeInfo();
    }


    /**
     * @return int the number of operands for the given operator
     */
    public int getOperandCount() {
        return NUM_OPERANDS;
    }

}
