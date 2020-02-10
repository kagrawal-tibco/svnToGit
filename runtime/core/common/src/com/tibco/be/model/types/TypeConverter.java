package com.tibco.be.model.types;

import com.tibco.xml.data.primitive.XmlTypedValue;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 3:35:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TypeConverter {

    /**
     *
     * @return
     */
    Class getNativeClass();
    /**
     *
     * @return
     */
    Class getForeignClass();

    /**
     *
     * @param value
     * @return
     * @throws ConversionException
     */
    Object convertSimpleType(String value) throws ConversionException;

    /**
     *
     * @param typedValue
     * @return
     * @throws ConversionException
     */
    Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException;


}
