package com.tibco.jxpath.operations;

import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:16 PM
*/
public class Mult extends BinaryOperation {

    @Override
    public XObject operate(XObject leftResult, XObject rightResult) {
        return new XNumber(leftResult.num() * rightResult.num());
    }
}
