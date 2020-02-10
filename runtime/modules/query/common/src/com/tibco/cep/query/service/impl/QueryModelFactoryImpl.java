package com.tibco.cep.query.service.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.ast.ASTWalker;
import com.tibco.cep.query.ast.parser.BEOqlParser;
import com.tibco.cep.query.model.NamedDeleteContext;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.QueryModel;
import com.tibco.cep.query.model.impl.DistinctClauseImpl;
import com.tibco.cep.query.model.impl.NamedDeleteContextImpl;
import com.tibco.cep.query.model.impl.NamedSelectContextImpl;
import com.tibco.cep.query.service.QueryServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 8, 2007
 * Time: 12:25:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryModelFactoryImpl extends AbstractQueryModelFactory {

    public QueryModelFactoryImpl(QueryServiceProvider qsp) {
        super(qsp);
    }

    public QueryModel createModel(CommonTree ast) throws Exception {
        QueryModel model = null;
        String alias = getQueryServiceProvider().getProjectContext().getIdGenerator().nextIdentifier().toString();
        if (ast.getChildCount() > 0) {
            // Check if the query is a delete query.
            switch(ast.getType()) {
                case BEOqlParser.DELETE_EXPR:
                    model = new NamedDeleteContextImpl(getQueryServiceProvider().getProjectContext(),
                        alias, ast);
                    model.initialize();
                    ASTWalker.walkDeleteClause(ast, (NamedDeleteContext)model);
                    break;
                case BEOqlParser.SELECT_EXPR:
                    // Query doesn't have delete clause. Process query for select clause.
                    model = new NamedSelectContextImpl(getQueryServiceProvider().getProjectContext(),
                            alias, ast);
                    final CommonTree distinctTree = (CommonTree) (ast.getFirstChildWithType(BEOqlParser.TOK_DISTINCT));
                    if (null != distinctTree) {
                        new DistinctClauseImpl(model, distinctTree);
                    }
                    // initialize the model to set the function registry
                    model.initialize();
                    ASTWalker.walkSelectClause(ast, (NamedSelectContext)model);
                    break;
            }
        }
        return model;
    }

}
