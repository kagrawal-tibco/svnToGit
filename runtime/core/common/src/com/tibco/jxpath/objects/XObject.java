package com.tibco.jxpath.objects;


import com.tibco.jxpath.Expression;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 4:42 PM
*/
public interface XObject extends Expression {

    /**
     *
     * @return the boolean representation of the value. See XPath spec 1.0 for detailed conversion of XNumber to boolean
     * and XString to boolean.
     */
    boolean  bool();

    /**
     *
     * @return the IEEE754 Number format for this object if convertable.
     */
    double num();

    /**
     *
     * @return the string form for the XObject. See XPath spec 1.0 for detailed conversions for XNumber, XBoolean
     */
    String str();

    /**
     *
     * @return the underlying object
     */
    Object object();

    /** re-incarnate this method later
    Object castToType(Class classType);
     */

    /**
     *
     * @param other   check if this object is equal to the other XObject passed in as parameter
     * @return  boolean true or false upon comparision
     */
    boolean equals(XObject other);



}
