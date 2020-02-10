package com.tibco.be.parser;

import com.tibco.be.model.functions.PredicateWithXSLT;

/**
 * @author ishaan
 * @version Nov 14, 2004, 8:40:35 PM
 */
public interface MapperFunctionValidator {
    public String validateXSLT(PredicateWithXSLT function, String xslt);

    boolean hasErrors();
}
