package com.tibco.cep.driver.util;

/**
 * User: nicolas
 * Date: 11/14/14
 * Time: 2:12 PM
 */
public enum IncludeEventType
{
    ALWAYS,
    ON_DESERIALIZE,
    ON_SERIALIZE,
    NEVER
    ;


    public boolean isOkOnDeserialize()
    {
        return (this == ALWAYS) || (this == ON_DESERIALIZE);
    }


    public boolean isOkOnSerialize()
    {
        return (this == ALWAYS) || (this == ON_SERIALIZE);
    }

}
