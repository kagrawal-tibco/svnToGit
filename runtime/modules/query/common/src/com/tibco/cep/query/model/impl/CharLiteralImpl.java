package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.CharLiteral;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 4:14:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharLiteralImpl extends AbstractExpression implements CharLiteral {
    private String charValue;


    public CharLiteralImpl(ModelContext parentContext, CommonTree tree, String charValue) throws Exception {
        super(parentContext, tree);
        this.charValue = charValue;
        this.typeInfo = new TypeInfoImpl(char.class);
    }


    /**
     * Returns the primitive literal value
     *
     * @return char
     */
    public String charValue() {
        return this.charValue;
    }


    /**
     * Returns the Boxed value of the literal
     *
     * @return Character
     */
    public String getValue() {
        return this.charValue;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_CHAR_LITERAL;
    }


    public boolean isResolved() {
        return true;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof CharLiteralImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final CharLiteralImpl that = (CharLiteralImpl) o;
        return (this.charValue == that.charValue);
    }


    public int hashCode() {
        return this.charValue.hashCode();
    }


}
