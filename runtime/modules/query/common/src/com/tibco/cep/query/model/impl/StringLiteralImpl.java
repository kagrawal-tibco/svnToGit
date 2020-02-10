package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.StringLiteral;
import com.tibco.cep.query.model.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 15, 2007
 * Time: 11:08:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringLiteralImpl extends AbstractExpression implements StringLiteral {

    private String literal = "";

    /**
     * Constructor
     * @param parentContext
     * @param tree
     * @param literal
     * @throws Exception
     */
    public StringLiteralImpl(ModelContext parentContext, CommonTree tree, String literal) throws Exception {
        super(parentContext, tree);
        this.literal = literal;
        this.typeInfo = new TypeInfoImpl(this.literal);

    }

    /**
     * Returns the Literal value
     *
     * @return String
     */
    public String getValue() {
        return this.literal;
    }

    /**
     * Returns the TypeInfo information for the typed context
     *
     * @return TypeInfo the type information
     */
    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }



    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_STRING_LITERAL;
    }


    public boolean isResolved() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof StringLiteralImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final StringLiteralImpl that = (StringLiteralImpl) o;
        return this.literal.equals(that.literal);
    }

    public int hashCode() {
        return this.literal.hashCode();
    }


}
