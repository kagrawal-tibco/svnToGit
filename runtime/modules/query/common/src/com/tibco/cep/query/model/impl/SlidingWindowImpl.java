package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.PurgeClause;
import com.tibco.cep.query.model.SlidingWindow;
import com.tibco.cep.query.model.StreamPolicy;
import com.tibco.cep.query.model.validation.validators.SlidingWindowValidator;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 4, 2007 Time: 8:36:24 PM To
 * change this template use File | Settings | File Templates.
 */
public class SlidingWindowImpl extends TumblingWindowImpl implements SlidingWindow {

    public SlidingWindowImpl(StreamPolicy policy, CommonTree tree, int size) {
        super(policy, tree, size);
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_SLIDING_WINDOW;
    }


    /**
     * @return PurgeClause or null.
     */
    public PurgeClause getPurgeClause() {
        ModelContext[] children = this.getDescendantContextsByType(this,
                ModelContext.CTX_TYPE_PURGE_CLAUSE);
        if (children.length > 0) {
            return (PurgeClause) children[0];
        }
        return null;
    }


    @Override
    public void validate() throws Exception {
        new SlidingWindowValidator().validate(this);
    }
}
