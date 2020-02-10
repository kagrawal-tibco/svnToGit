package com.tibco.rta.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This means that the annotated target should handle repetitive calls gracefully by ignoring subsequent invocations.
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Idempotent {
}
