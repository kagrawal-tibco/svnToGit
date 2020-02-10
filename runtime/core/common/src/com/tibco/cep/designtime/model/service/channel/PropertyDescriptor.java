package com.tibco.cep.designtime.model.service.channel;


import java.util.regex.Pattern;

/*
* User: Nicolas Prade
* Date: Oct 1, 2009
* Time: 5:00:50 PM
*/


public interface PropertyDescriptor {


    /**
     * Returns String default value associated with this property.
     *
     * @return String default value associated with this property.
     */
    String getDefaultValue();


    /**
     * Returns String name of this property.
     *
     * @return String name of this property.
     */
    String getName();


    /**
     * Gets the pattern that the property value must match, or null is there is no Pattern.
     *
     * @return Pattern or null;
     */
    Pattern getPattern();


    /**
     * Returns int flag indicating the type of the property.
     *
     * @return int flag indicating the type of the property.
     */
    int getType();

}
