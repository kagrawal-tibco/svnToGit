package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.StreamPolicy;
import com.tibco.cep.query.model.TumblingWindow;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.validation.validators.TumblingWindowValidator;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 4, 2007
 * Time: 8:22:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TumblingWindowImpl
    extends AbstractQueryContext
    implements TumblingWindow {

    private int maxSize;


    public TumblingWindowImpl(StreamPolicy policy, CommonTree tree, int maxSize) {
        super(policy, tree);
        this.maxSize = maxSize;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_TUMBLING_WINDOW;
    }


    /**
     * @return the maximum size of the window.
     */
    public int getMaxSize() {
        return this.maxSize;
    }


    public WhereClause getWhereClause() {
        ModelContext[] children = this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_WHERE);
        if (children.length > 0) {
            return (WhereClause) children[0];
        }
        return null;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof TumblingWindowImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final TumblingWindowImpl that = (TumblingWindowImpl) o;

        return this.maxSize == that.maxSize;
    }


    public int hashCode() {
        return this.maxSize;
    }


    public void validate() throws Exception {
        new TumblingWindowValidator().validate(this);
    }
}
