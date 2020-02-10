package com.tibco.be.baas.security.authn.saml.metadata.common;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 20/9/11
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(SOURCE)
@Target({ElementType.METHOD})
public @interface MetadataComponent {

    String componentType() default "##ConfigUI";
}
