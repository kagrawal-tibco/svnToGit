package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 3:27:48 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class XSDBuiltInConverter implements TypeConverter, TypeRenderer{

    Class xsdClass, javaClass;

    /**
     *
     * @param xsdClass
     * @param javaClass
     */
    public XSDBuiltInConverter(Class xsdClass,
                               Class javaClass) {
        this.xsdClass= xsdClass;
        this.javaClass= javaClass;
    }

    /**
     *
     * @return
     */
    public Class getNativeClass() {
        return xsdClass;
    }

    /**
     *
     * @return
     */
    public Class getForeignClass() {
        return javaClass;
    }


}
