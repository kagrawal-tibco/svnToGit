package com.tibco.cep.query.model.impl;

import java.util.Iterator;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.PurgeClause;
import com.tibco.cep.query.model.Window;
import com.tibco.cep.query.model.validation.validators.PurgeClauseValidator;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 4, 2007 Time: 9:12:53 PM To
 * change this template use File | Settings | File Templates.
 */
public class PurgeClauseImpl extends AbstractQueryContext implements PurgeClause {

    public PurgeClauseImpl(Window w, CommonTree tree) {
        super(w, tree);
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_PURGE_CLAUSE;
    }

    /**
     * @return Expression
     */
    public Expression getFirst() {
        return (Expression) this.getChildrenIterator().next();
    }

    /**
     * @return Expression
     */
    public Expression getWhen() {
        final Iterator it = this.getChildrenIterator();
        it.next();
        if (it.hasNext()) {
            return (Expression) it.next();
        }
        return null;
    }

    public void validate() throws Exception {
        new PurgeClauseValidator().validate(this);
    }
}
