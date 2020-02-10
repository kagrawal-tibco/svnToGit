package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.DistinctClause;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 31, 2008
 * Time: 6:19:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DistinctClauseImpl extends AbstractQueryContext implements DistinctClause {


    public DistinctClauseImpl(ModelContext parentContext, CommonTree tree) {
        super(parentContext, tree);
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_DISTINCT_CLAUSE;
    }


}
