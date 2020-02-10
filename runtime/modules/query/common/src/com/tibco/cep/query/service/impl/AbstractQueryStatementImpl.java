package com.tibco.cep.query.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.be.util.idgenerators.serial.LongGenerator;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryResultSet;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.QueryStatement;
import com.tibco.cep.query.service.Tuple;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 2, 2007
 * Time: 12:38:54 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractQueryStatementImpl implements QueryStatement {

    protected static IdentifierGenerator idGenerator = new LongGenerator(true, 1);


    protected QueryResultSet resultSet;
    protected Query query;
    protected QueryExecutionPlan executionPlan;
    private String id;
    protected int state;
    protected List<QueryStatement.StateChangeListener> stateListeners = new ArrayList<StateChangeListener>();


    public AbstractQueryStatementImpl(Query parent, QueryExecutionPlan executionPlan) throws Exception {
        this.query = parent;
        this.executionPlan = executionPlan;
        this.resultSet = new QueryResultSetImpl(this);
        this.id = String.valueOf(AbstractQueryStatementImpl.idGenerator.nextIdentifier());
    }


    public QueryExecutionPlan getExecutionPlan() {
        return this.executionPlan;
    }



    public void setExecutionPlan(QueryExecutionPlan executionPlan) {
        this.executionPlan = executionPlan;
    }


    /**
     * Releases this QueryStatement object's resources.
     */
    public void close() throws QueryException {
        this.setState(QueryStatement.QUERYSTATEMENT_STATE_CLOSED);
        ((QueryImpl) this.getQuery()).removeStatement(this);
    }

    /**
     * Cancels this statement if the engine allows it.
     */
    public void cancel() throws QueryException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the Query object associated with the QueryStatement
     *
     * @return Query
     */
    public Query getQuery() throws QueryException {
        return this.query;
    }

    /**
     * Retrieves the QuerySession under which this statement is executing
     *
     * @return QuerySession
     */
    public QuerySession getQuerySession() {
        return query.getQuerySession();
    }

    /**
     * Add tuples to the resultset
     * @param t
      */
    public void addTuple2ResultSet(Tuple t) {
        ((QueryResultSetImpl)resultSet).addTuple(t);
    }

    /**
     * Retrieves the direction for fetching rows that is the default for result sets generated from this QueryStatement object.
     *
     * @return int the default fetch direction for result sets generated from this QueryStatement object
     */
    public int getFetchDirection() throws QueryException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the number of result set rows that is the default fetch size for ResultSet objects generated from this
     * QueryStatement object. If this QueryStatement object has not set a fetch size by calling the method setFetchSize,
     * the return value is implementation-specific.
     *
     * @return int the default fetch size for result sets generated from this Statement object
     */
    public int getFetchSize() throws QueryException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the maximum number of rows that a QueryResultSet object produced by this QueryStatement object can contain.
     * If this limit is exceeded, the excess rows are silently dropped.
     *
     * @return int the current maximum number of rows for a ResultSet  object produced by this QueryStatement object; zero means there is no limit
     */
    public int getMaxRows() throws QueryException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Moves to this QueryStatement object's next result, returns true if it is a QueryResultSet object,
     * and implicitly closes any current QueryResultSet  object(s) obtained with the method getQueryResultSet.
     * There are no more results when the following is true:
     * // stmt is a QueryStatement object
     * (stmt.getMoreResults() == false)
     *
     * @return boolean true if the next result is a QueryResultSet  object; false if there are no more results
     */
    public boolean getMoreResults() throws QueryException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Moves to this QueryStatement object's next result, returns true if it is a QueryResultSet object,
     * and implicitly closes any current QueryResultSet  object(s) obtained with the method getQueryResultSet.
     * There are no more results when the following is true:
     * // stmt is a QueryStatement object
     * (stmt.getMoreResults() == false)
     *
     * @param current one of the following Statement  constants indicating what should happen to current ResultSet objects
     *                obtained using the method getResultSet: Statement.CLOSE_CURRENT_RESULT, Statement.KEEP_CURRENT_RESULT,
     *                or Statement.CLOSE_ALL_RESULTS
     * @return boolean true if the next result is a QueryResultSet  object; false if there are no more results
     */
    public boolean getMoreResults(int current) throws QueryException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the current result as a QueryResultSet object. This method should be called only once per result.
     *
     * @return the current result as a QueryResultSet object or null if the result is an update count or there are no more results
     */
    public QueryResultSet getResultSet() throws QueryException {
        return this.resultSet;
    }

    /**
     * Retrieves the result set concurrency for ResultSet objects generated by this Statement object.
     *
     * @return either QueryResultSet.CONCUR_READ_ONLY or QueryResultSet.CONCUR_UPDATABLE
     */
    public int getQueryResultSetConcurrency() throws QueryException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the result set holdability for ResultSet objects generated by this Statement object.
     *
     * @return either ResultSet.HOLD_CURSORS_OVER_COMMIT or ResultSet.CLOSE_CURSORS_AT_COMMIT
     */
    public int getQueryResultSetHoldability() throws QueryException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Returns the ID of this object.
     *
     * @return String
     */
    public String getId() {
        return this.id;
    }

    public int getState() {
        return this.state;
    }


    void setState(int querystatementStateOpen) {
        this.state = querystatementStateOpen;
        for(QueryStatement.StateChangeListener l : this.stateListeners) {
            l.notify(new StateChangeEventImpl(this,this.state));
        }
    }

    /**
     * adds at  //To change body of implemented methods use File | Settings | File Templates.e observer
     *
     * @param qso
     */
    public void addStateListener(QueryStatement.StateChangeListener qso) throws QueryException {
        this.stateListeners.add(qso);
    }

    /**
     * removes a query state observer
     *
     * @param qso
     */
    public void removeStateListener(QueryStatement.StateChangeListener qso) throws QueryException {
        this.stateListeners.remove(qso);
    }

    public class StateChangeEvent implements QueryStatement.StateChangeEvent {
        private QueryStatement statement;
        private int state;

        public QueryStatement getQueryStatement() {
            return this.statement;
        }

        public int getState() {
            return this.state;
        }

        public boolean isClosed() {
            return this.state == QueryStatement.QUERYSTATEMENT_STATE_OPEN;
        }

        public boolean isOpen() {
            return this.state == QueryStatement.QUERYSTATEMENT_STATE_OPEN;
        }
    }

    public class StateChangeEventImpl implements QueryStatement.StateChangeEvent {
        private QueryStatement statement;
        private int state;

        public StateChangeEventImpl(QueryStatement statement, int state) {
            this.state = state;
            this.statement = statement;
        }

        public QueryStatement getQueryStatement() {
            return this.statement;
        }

        public int getState() {
            return this.state;
        }

        public boolean isClosed() {
            return this.state == QueryStatement.QUERYSTATEMENT_STATE_CLOSED;
        }

        public boolean isOpen() {
            return this.state == QueryStatement.QUERYSTATEMENT_STATE_OPEN;
        }
    }


}
