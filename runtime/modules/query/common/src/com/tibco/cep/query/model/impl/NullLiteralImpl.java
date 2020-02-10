package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NullLiteral;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 15, 2007
 * Time: 11:43:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class NullLiteralImpl extends AbstractExpression implements NullLiteral {
    /**
     * Constructor
     * @param parentContext
     * @param tree
     */
    public NullLiteralImpl(ModelContext parentContext, CommonTree tree) throws Exception {
        super(parentContext, tree);
        this.typeInfo = new TypeInfoImpl(null);

    }

    /**
     * Returns the Literal value
     *
     * @return Object
     */
    public Object getValue() {
        return NULL_VALUE;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_NULL_LITERAL;
    }

    /**
     * Returns true if the context has been resolved or false
     *
     * @return boolean
     */
    public boolean isResolved() {
        return true;
    }


    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (! (o instanceof NullLiteral)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        return true;
    }


    public int hashCode() {
        return NULL_VALUE.hashCode();
    }


}
