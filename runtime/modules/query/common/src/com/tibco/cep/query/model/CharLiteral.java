package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 4:12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CharLiteral extends Literal {

    /**
     * Returns the primitive literal value
     * @return String
     */
    String charValue();

    /**
     * Returns the Boxed value of the literal
     * @return Character
     */
    String getValue();
}
