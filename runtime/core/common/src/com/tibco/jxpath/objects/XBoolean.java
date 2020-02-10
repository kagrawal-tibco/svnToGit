package com.tibco.jxpath.objects;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 4:48 PM
*/
public class XBoolean extends XObjectWrapper {

    private boolean val;
    public static final XBoolean FALSE = new XBoolean(false);
    public static final XBoolean TRUE = new XBoolean(true);

    public XBoolean (boolean val)
    {
        super(val);
        this.val = val;
    }

    public boolean bool() {
        return val;
    }

    public double num() {
        return (val ? 1 : 0);
    }

    public String str() {
        return (val ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    }




    public boolean equals(XObject other) {

        XBoolean otherBoolean = XBoolean.class.cast(other);

        if (otherBoolean == null) return false;

        if (otherBoolean.bool() == this.bool()) return true;

        return false;
    }
}
