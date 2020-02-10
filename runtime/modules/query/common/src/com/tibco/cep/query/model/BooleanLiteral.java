package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 4:17:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BooleanLiteral extends Literal {

    /**
     * Returns the primitive literal value
     * @return boolean
     */
    boolean booleanValue();

    /**
     * Returns the boxed literal value
     * @return Boolean
     */
    Boolean getValue();
}
