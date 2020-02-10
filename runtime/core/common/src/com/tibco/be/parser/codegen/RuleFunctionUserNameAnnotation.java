package com.tibco.be.parser.codegen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})

/*
 * For generated RuleFuncton implementations to store their user friendly names and URIs.
 * A typical user name would be RuleFunctions.myFunction and URI would be /RuleFunction/myFunction
 * whereas the generated class name would be something like be.gen.RuleFunctions.nullmyFunctionnull
 */
@RuleFunctionUserNameAnnotation(userName = "asdf", userURI = "asdf")
public @interface RuleFunctionUserNameAnnotation {
	public static final String userName = "userName";
	public static final String userURI = "userURI";
	String userName() default "";
	String userURI() default "";
}