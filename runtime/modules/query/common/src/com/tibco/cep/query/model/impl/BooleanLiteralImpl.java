package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.BooleanLiteral;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 4:19:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BooleanLiteralImpl extends AbstractExpression implements BooleanLiteral {

    private boolean boolValue;

    public BooleanLiteralImpl(ModelContext parentContext, CommonTree tree, String boolValue) throws Exception {
        super(parentContext, tree);
        this.boolValue = Boolean.parseBoolean(boolValue);
        this.typeInfo = new TypeInfoImpl(boolean.class);
    }

    /**
     * Returns the primitive literal value
     *
     * @return boolean
     */
    public boolean booleanValue() {
        return this.boolValue;
    }

    /**
     * Returns the boxed literal value
     *
     * @return Boolean
     */
    public Boolean getValue() {
        return Boolean.valueOf(this.boolValue);
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_BOOLEAN_LITERAL;
    }


    public boolean isResolved() {
        return true;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof BooleanLiteral)) {
            return false;
        }
         if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final BooleanLiteralImpl that = (BooleanLiteralImpl) o;
        return (this.boolValue == that.boolValue);
    }


    public int hashCode() {
        return (this.boolValue ? 1 : 0);
    }

}
