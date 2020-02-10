package com.tibco.jxpath.operations;

import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:16 PM
*/
public class Neg extends UnaryOperation {

    @Override
    public XObject operate(XObject result) {
        return new XNumber(-result.num());
    }
}
