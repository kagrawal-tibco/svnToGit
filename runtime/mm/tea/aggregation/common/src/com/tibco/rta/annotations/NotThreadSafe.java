package com.tibco.rta.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class to which this annotation is applied is NOT thread-safe.  This means that
 * sequences of accesses (reads and writes to public fields, calls to public methods)
 * may put the object into an invalid state, regardless of the interleaving of those actions
 * by the runtime, and without proper locking on the part of the caller the annotated item
 * may be put in an inconsistent state.
 *
 **/
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface NotThreadSafe {
}
