package com.tibco.rta.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This means that the annotated target may need to handle retries
 * in case it was rejected.
 * <p>
 *     This is not covered by {@link Idempotent} which implies
 *     automatic retry when the task itself is not rejected.
 * </p>
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface RetryOnRejection {
}
