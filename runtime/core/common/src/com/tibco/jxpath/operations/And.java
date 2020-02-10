package com.tibco.jxpath.operations;

import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 3:04 PM
*/
public class And extends BinaryOperation {

    public And()
    {
    }

    @Override
    public XObject operate(XObject leftResult, XObject rightResult) {
        return new XBoolean(leftResult.bool() && rightResult.bool());
    }
}
