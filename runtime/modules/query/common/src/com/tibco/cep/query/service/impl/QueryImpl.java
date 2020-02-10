/**
 *
 */
package com.tibco.cep.query.service.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.be.util.idgenerators.serial.PrefixedNumericGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.impl.local.DeletePlanGeneratorImpl;
import com.tibco.cep.query.api.impl.local.PlanGenerator;
import com.tibco.cep.query.api.impl.local.PlanGeneratorImpl;
import com.tibco.cep.query.ast.parser.ParserUtil;
import com.tibco.cep.query.exec.QueryExecutionPlanFactory;
import com.tibco.cep.query.exec.codegen.CodeGenerationQueryExecutionPlanFactory;
import com.tibco.cep.query.exec.codegen.QueryExecutionClassLoader;
import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.model.DeleteContext;
import com.tibco.cep.query.model.QueryModel;
import com.tibco.cep.query.model.visitor.impl.BindResolutionVisitor;
import com.tibco.cep.query.model.visitor.impl.ContextResolutionVisitor;
import com.tibco.cep.query.remote.ComponentDeSerializer;
import com.tibco.cep.query.remote.ComponentSerializer;
import com.tibco.cep.query.remote.RemoteQueryDeSerializer;
import com.tibco.cep.query.remote.RemoteQuerySerializer;
import com.tibco.cep.query.remote.impl.DefaultQueryDeSerializerImpl;
import com.tibco.cep.query.remote.impl.DefaultQuerySerializerImpl;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryFeatures;
import com.tibco.cep.query.service.QueryPreparedStatement;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.QueryStatement;
import com.tibco.cep.query.utils.BaseObjectTreeAdaptor;
import com.tibco.cep.query.utils.DotTreeWalker;
import com.tibco.cep.runtime.service.loader.BEClassLoader;

/**
 * @author pdhar
 */
public class QueryImpl implements Query {
    protected Logger logger;
    protected String oql;
    protected QuerySession qs;
    protected String queryName;
    protected int queryState;
    private QueryModel model;
    protected QueryExecutionPlanFactory executionPlanFactory; // Always access it with getExecutionPlanFactory()
    protected Map<String, QueryStatement> statements;
    protected QueryExecutionClassLoader executionClassLoader;
    protected IdentifierGenerator idGenerator;
    protected List<StateChangeListener> stateListeners;
    private static final String QUERY_MODEL_GRAPH_FILE_EXTN = "_Model.dot";
    private static final String CLASS_FILE_EXTN = ".class";
    protected static final String PROP_CEP_QUERY_LOG_ENABLE = "cep.query.log.enable";
    protected static final String PROP_CEP_QUERY_LOG_DELETE_LOG_FILES = "cep.query.log.deleteLogFiles";
    protected static final String QUERY_JAR_FILE_EXTN = ".jar";
    protected PlanGenerator planGenerator = null;
    protected QueryFeatures queryFeatures;

    /**
     *
     */
    public QueryImpl() {
        super();
    }

    /**
     * @param oql
     */
    public QueryImpl(String oql, QuerySession qs) throws QueryException {
        this.logger = qs.getQueryServiceProvider().getLogger(this.getClass());
        this.oql = oql;
        this.qs = qs;
        this.stateListeners = new ArrayList<StateChangeListener>();
        this.statements = new HashMap<String, QueryStatement>();
        this.executionClassLoader = new QueryExecutionClassLoader((BEClassLoader)
                qs.getRuleSession().getRuleServiceProvider().getClassLoader(), this);

        this.queryFeatures = new QueryFeaturesImpl();

        try {
            this.queryName = ((QueryRuleSessionImpl) qs).nextIdentifier().toString();
            this.idGenerator = new PrefixedNumericGenerator(queryName, true, 1);

            // Model generation:
            this.parseOql();
            setQueryState(Query.QUERY_STATE_PARSED);
            this.resolveOql();
            setQueryState(Query.QUERY_STATE_RESOLVED);
            this.resolveBinding();
            setQueryState(Query.QUERY_STATE_BINDING_RESOLVED);
            this.validateOql();
            setQueryState(Query.QUERY_STATE_VALIDATED);

            // Code generation:
            // Delayed, done when accessing the QueryExecutionPlanFactory for the first time.
            // Can be forced by calling getExecutionPlanFactory().

        } catch (Exception e) {
            throw new QueryException(e);
        }
        if (null != model && !(model instanceof DeleteContext)) {
            this.planGenerator = new PlanGeneratorImpl(this, this.qs.getRegionName());
        } else if (model instanceof DeleteContext) {
            this.planGenerator = new DeletePlanGeneratorImpl(this, this.qs.getRegionName());
        }

        setQueryFeatures();
    }

    //********* Setting Feature for Self Join as of Now ****************

    public void setQueryFeatures() {
        QueryExecutionPlanDescriptor qepd = planGenerator.getQueryExecutionPlanDescriptor();
        this.queryFeatures.setSelfJoin(qepd.isSelfJoin());
    }

    public QueryFeatures getQueryFeatures() {
        return queryFeatures;
    }

    //******************************************************************

    public String getSourceText() {
        return this.oql;
    }

    public PlanGenerator getPlanGenerator() {
        return planGenerator;
    }

    private QueryModel transformAST(CommonTree ast) throws Exception {
        QueryModel model;
        model = qs.getQueryServiceProvider().getModelFactory().createModel(ast);
        setQueryState(Query.QUERY_STATE_MODELED);
        return model;
    }

    protected void resolveOql() throws Exception {
        if (null != model && !(model instanceof DeleteContext)) {
            this.model.accept(new ContextResolutionVisitor());
        }
    }

    protected void resolveBinding() throws Exception {
        if (null != model && !(model instanceof DeleteContext)) {
            this.model.accept(new BindResolutionVisitor());
        }
    }

    protected void validateOql() throws Exception {
        //this.model.accept(new ContextValidationVisitor());
    }

    protected void parseOql() throws Exception {
        ParserUtil pu = new ParserUtil(oql);
        Tree ast = (Tree) pu.getAST();
        this.logger.log(Level.DEBUG, this.oql);
        model = transformAST((CommonTree) ast);
        if (null != model) {
            if (this.logger.isEnabledFor(Level.DEBUG)) {
                DotTreeWalker dtree = new DotTreeWalker();
                String dot = dtree.toDOT(model, new BaseObjectTreeAdaptor()).toString();
                this.logger.log(Level.DEBUG, "\n" + dot);
            }
        } else {
            this.logger.log(Level.INFO, "Query model could not be generated for query: %s", this.oql);
            throw new QueryException("Query model could not be generated for query :" + oql);
        }
    }

    public QuerySession getQuerySession() {
        return qs;
    }

    /**
     * Return the query name identifier
     *
     * @return the query name
     */
    public String getName() throws QueryException {
        return queryName;
    }

    public int hashCode() {
        String[] content = oql.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            if (content[i].length() < 1)
                continue;
            sb.append(content[i]);
        }
        return sb.toString().hashCode();
    }

    /**
     * Retrieves the QueryModel used to build the Query
     *
     * @return the QueryModel
     * @throws com.tibco.cep.query.api.QueryException
     *
     */
    public QueryModel getModel() throws QueryException {
        return model;
    }

    /**
     * Creates a PreparedStatement object for sending parameterized OQL statements to the engine.
     * A OQL statement with or without IN parameters can be pre-compiled and stored in a QueryPreparedStatement object.
     * This object can then be used to efficiently execute this statement multiple times.
     * <p/>
     * Note: This method is optimized for handling parametric SQL statements that benefit from pre-compilation.
     * If the engine supports pre-compilation, the method prepareStatement will send the statement to the engine for pre-compilation.
     * <p/>
     * Result sets created using the returned PreparedStatement object will by default be type TYPE_FORWARD_ONLY and
     * have a concurrency level of CONCUR_READ_ONLY.
     *
     * @return PreparedQueryStatement
     * @throws UnsupportedOperationException
     */
    public QueryPreparedStatement prepareStatement() throws QueryException {
        throw new UnsupportedOperationException();
    }

    public QueryStatement createStatement() throws QueryException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the QueryExecutionPlanFactory for this Query, creating it if necessary.
     *
     * @return QueryExecutionPlanFactory
     * @throws QueryException
     */
    protected QueryExecutionPlanFactory getExecutionPlanFactory() throws Exception {
        if (null == this.executionPlanFactory) {
            this.executionPlanFactory = new CodeGenerationQueryExecutionPlanFactory(this, this.qs.getRegionName());
        }
        return this.executionPlanFactory;
    }

    public int getQueryState() {
        return queryState;
    }

    public void setQueryState(int queryState) {
        this.queryState = queryState;
        notifyObserver(queryState);
    }

    public void notifyObserver(int state) {
        for (Iterator it = this.stateListeners.iterator(); it.hasNext(); ) {
            final StateChangeListener l = (StateChangeListener) it.next();
            l.notify(new StateChangeEventImpl(this, this.queryState));
        }
    }

    /**
     * Releases this Query object's Session and Provider resources immediately instead of waiting for them to be automatically released.
     * Calling the method close on a Query object that is already closed is a no-op.
     */
    public void close() throws QueryException {
        for (QueryStatement qs : this.statements.values()) {
            if (((QueryStatementImpl) qs).getState() != QueryStatement.QUERYSTATEMENT_STATE_OPEN) {
                throw new QueryException("Query cannot be closed while QueryStatement is still running");
            }
        }
        setQueryState(Query.QUERY_STATE_CLOSED);
    }

    /**
     * @return QueryExecutionClassLoader
     */
    public QueryExecutionClassLoader getExecutionClassLoader() {
        return this.executionClassLoader;
    }

    void removeStatement(QueryStatement statement) {
        this.statements.remove(statement.getId());
        if (this.statements.size() > 0) {
            setQueryState(Query.QUERY_STATE_OPEN);
        } else {
            setQueryState(Query.QUERY_STATE_CLOSED);
        }
    }

    /**
     * Gets the identifier generator.
     *
     * @return IdentifierGenerator
     */
    public IdentifierGenerator getIdGenerator() {
        return this.idGenerator;
    }

    /**
     * @param deserializer
     */
    public void deserialize(ComponentDeSerializer deserializer) {
        this.deserializeQuery((RemoteQueryDeSerializer) deserializer);
    }

    /**
     * @param serializer
     */
    public void serialize(ComponentSerializer serializer) {
        this.serializeQuery((RemoteQuerySerializer) serializer);
    }

    /**
     * @param deserializer
     */
    public void deserializeQuery(RemoteQueryDeSerializer deserializer) {
        deserializer.startComponentDeSerialization();

        deserializer.endComponentDeSerialization();
    }

    /**
     * @param serializer
     */
    public void serializeQuery(RemoteQuerySerializer serializer) {
        serializer.startComponentSerialization();

        serializer.endComponentSerialization();
    }

    public void readExternal(DataInput dataInput) throws IOException {
        try {
            RemoteQueryDeSerializer deser = new DefaultQueryDeSerializerImpl(this, dataInput);
            this.deserialize(deser);
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new java.lang.RuntimeException(ex);
        }
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        try {
            RemoteQuerySerializer ser = new DefaultQuerySerializerImpl(this, dataOutput);
            this.serialize(ser);
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new java.lang.RuntimeException(ex);
        }
    }

    /**
     * adds at  //To change body of implemented methods use File | Settings | File Templates.e observer
     *
     * @param qso
     */
    public void addQueryStateListener(StateChangeListener qso) throws QueryException {
        this.stateListeners.add(qso);
    }

    /**
     * removes a query state observer
     *
     * @param qso
     */
    public void removeQueryStateListener(StateChangeListener qso) throws QueryException {
        this.stateListeners.remove(qso);
    }

    public class StateChangeEventImpl implements Query.StateChangeEvent {
        int state = 0;
        Query query;

        public StateChangeEventImpl(Query query, int state) {
            this.state = state;
            this.query = query;
        }

        public Query getQuery() {
            return this.query;
        }

        public int getState() {
            return this.state;
        }

        public boolean isCompiled() {
            return state == Query.QUERY_STATE_COMPILED;
        }

        public boolean isClosed() {
            return state == Query.QUERY_STATE_CLOSED;
        }

        public boolean isModeled() {
            return state == Query.QUERY_STATE_MODELED;
        }

        public boolean isParsed() {
            return state == Query.QUERY_STATE_PARSED;
        }

        public boolean isOpen() {
            return state == Query.QUERY_STATE_OPEN;
        }

        public boolean isResolved() {
            return state == Query.QUERY_STATE_RESOLVED;
        }

        public boolean isValidated() {
            return state == Query.QUERY_STATE_VALIDATED;
        }
    }
}
