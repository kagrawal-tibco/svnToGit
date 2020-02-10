package com.tibco.be.model.functions;

import com.tibco.xml.schema.SmElement;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 4:49:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PredicateWithXSLT extends Predicate{

    //Todo this should go in some resource, so we can do it for JDBC, ASN.1 ... etc
    String XSLT_TYPE = "xslt";
    String XPATH_TYPE = "xpath";



    /**
     *
     * @return
     */
    SmElement getInputType();

    /**
     *
     * @return
     */
    SmElement getOutputType();

    /**
     *
     * @return the type element within the mapper Node of the function element in the function's catalog
     */
    String getMapperType();

    /**
     *
     * @return if the type of function uses XPath Evaluator
     */
    boolean isXPathFunction();

    /**
     *
     * @return if the type of the function uses a XSLT Transformation
     */
    boolean isXsltFunction();


}
