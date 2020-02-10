package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 9, 2007
 * Time: 12:33:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryModel extends RootContext {

    public static final NamedContextId SELECT_CLAUSE = NamedSelectContext.CTX_ID;
    public static final NamedContextId DELETE_CLAUSE = NamedDeleteContext.CTX_ID;
    public static final NamedContextId FROM_CLAUSE = FromClause.CTX_ID;
    public static final NamedContextId WHERE_CLAUSE = WhereClause.CTX_ID;
    public static final NamedContextId GROUP_CLAUSE = GroupClause.CTX_ID;
    public static final NamedContextId ORDER_CLAUSE = OrderClause.CTX_ID;
    public static final NamedContextId PROJECTION_CLAUSE = ProjectionAttributes.CTX_ID;


    void initialize() throws Exception;


    ProjectContext getProjectContext();

    /**
     * Returns named model contexts by their id
     * @param Id {@link NamedContextId}
     * @return
     */
    ModelContext getNameContextById(NamedContextId Id);

    /**
     * Returns the context of the query
     * @return {@link QueryContext}
     */
    QueryContext getContext();

    ModelContext getModelContextByAlias(String alias);

}
