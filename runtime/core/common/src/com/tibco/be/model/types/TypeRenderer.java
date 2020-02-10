package com.tibco.be.model.types;

import com.tibco.xml.data.primitive.XmlTypedValue;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 6:00:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TypeRenderer {
    /**
     *
     * @param value
     * @return
     * @throws ConversionException
     */
    XmlTypedValue convertToTypedValue(Object value) throws ConversionException;


}
