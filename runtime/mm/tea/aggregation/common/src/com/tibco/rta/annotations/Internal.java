package com.tibco.rta.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a field though public is meant for internal consumption only.
 * <p>
 *     These should not be documented as well.
 * </p>
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Internal {
}
