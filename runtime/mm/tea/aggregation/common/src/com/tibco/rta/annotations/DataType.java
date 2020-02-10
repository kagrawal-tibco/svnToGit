package com.tibco.rta.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate {@link com.tibco.rta.model.DataType} of the value to used on a property.
 * @see com.tibco.rta.ConfigProperty
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
public @interface DataType {

    /**
     *
     * @return the data type associated with this property.
     */
    com.tibco.rta.model.DataType dataType();

    /**
     * Default value for the field.
     * @return
     */
    String defaultValue();
}
