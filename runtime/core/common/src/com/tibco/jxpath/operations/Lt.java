package com.tibco.jxpath.operations;

import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:15 PM
*/
public class Lt extends BinaryOperation {

    @Override
    public XObject operate(XObject leftResult, XObject rightResult) {
        return new XBoolean(leftResult.num() < rightResult.num());
    }
}
