package com.tibco.jxpath.objects;




/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 4:55 PM
*/
public class XNumber extends XObjectWrapper {

    private double val;

    public XNumber(double val) {
        super(val);
        this.val = val;
    }

    public XNumber(Number num) {
        super(num);
        this.val = num.doubleValue();
    }

    public XNumber(String num) {
        super(Double.parseDouble(num));
        this.val = (Double)object();
    }

    public boolean bool() {
        if ((Math.abs(val)  == 0) || val == Double.NaN || val == Double.NEGATIVE_INFINITY) return false;
        return val > 0;

    }

    public double num() {
        return this.val;
    }

    public String str() {

        String s = Double.toString(this.val);
        //SS:XPath spec for Number-to-String is bit ridiculous on not wanting .0 for integers/long.
        if (s.endsWith(".0")) return s.substring(0, s.length()-2);
        return s;
    }





    public boolean equals(XObject other) {
        if (other instanceof XNumber) {
            XNumber otherNumber = (XNumber) other;
            if (otherNumber.val == this.val) return true;
        }

        return false;
    }

    public XNumber ceil() {
        double ceiled = Math.ceil(this.val);
        return new XNumber(ceiled);
    }

    public XNumber round() {
        return new XNumber(Math.round(this.val));
    }

    public XNumber floor() {
        return new XNumber(Math.floor(this.val));
    }
}
