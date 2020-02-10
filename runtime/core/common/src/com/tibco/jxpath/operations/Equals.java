package com.tibco.jxpath.operations;

import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:13 PM
*/
public class Equals extends BinaryOperation {

    @Override
    public XObject operate(XObject leftResult, XObject rightResult) {
    	if (leftResult == null) {
    		return new XBoolean(rightResult == null);
    	}
        return new XBoolean(leftResult.equals(rightResult));
    }
}
