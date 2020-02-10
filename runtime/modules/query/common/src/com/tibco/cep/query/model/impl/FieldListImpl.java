package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 18, 2007
 * Time: 3:51:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldListImpl extends AbstractExpression implements FieldList {


    public FieldListImpl(ModelContext parentContext, CommonTree tree) throws Exception {
        super(parentContext, tree);
        this.typeInfo = new TypeInfoImpl(this);
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_FIELD_LIST;
    }


    public Expression getExpression(int index) {
        return (Expression) getChild(index);
    }


    public int getSize() {
        return this.childrenCount();
    }                               


    public boolean isResolved() {
        return null != this.typeInfo;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTypedContext)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final AbstractTypedContext that = (AbstractTypedContext) o;
        return this.typeInfo.equals(that.typeInfo)
                && this.childContext.equals(that.childContext);
    }


    public int hashCode() {
        long longHash = this.typeInfo.hashCode();
        longHash = longHash * 29 + this.childContext.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
