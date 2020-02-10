/**
 *
 */
package com.tibco.cep.query.service;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.impl.local.PlanGenerator;
import com.tibco.cep.query.exec.codegen.QueryExecutionClassLoader;
import com.tibco.cep.query.model.QueryModel;


/**
 * @author pdhar
 */
public interface Query {
    /**
     * A constant indicating that transactions are not supported.
     */
    public static int TRANSACTION_NONE = 0;
    /**
     * A constant indicating that dirty reads are prevented; non-repeatable reads and phantom reads can occur.
     */
    public static int TRANSACTION_READ_COMMITTED = 1;
    /**
     * A constant indicating that dirty reads, non-repeatable reads and phantom reads can occur.
     */
    public static int TRANSACTION_READ_UNCOMMITTED = 2;
    /**
     * A constant indicating that dirty reads and non-repeatable reads are prevented; phantom reads can occur.
     */
    public static int TRANSACTION_REPEATABLE_READ = 3;
    /**
     * A constant indicating that dirty reads, non-repeatable reads and phantom reads are prevented.
     */
    public static int TRANSACTION_SERIALIZABLE = 4;

    /**
     * Query state
     */
    public static int QUERY_STATE_PARSED = 10;
    public static int QUERY_STATE_MODELED = 20;
    public static int QUERY_STATE_RESOLVED = 30;
    public static int QUERY_STATE_BINDING_RESOLVED = 35;
    public static int QUERY_STATE_VALIDATED = 40;
    public static int QUERY_STATE_COMPILED = 50;
    public static int QUERY_STATE_OPEN = 60;
    public static int QUERY_STATE_CLOSED = 70;


    String QUERY_OQL_FILE_EXTN = ".oql";


    /**
     * Creates a Statement object for sending OQL statements to the engine.
     * OQL statements without parameters are normally executed using Statement objects.
     * If the same OQL statement is executed many times, it may be more efficient to use a PreparedStatement object.
     * Result sets created using the returned QueryStatement object will by default be type TYPE_FORWARD_ONLY
     * and have a concurrency level of CONCUR_READ_ONLY.
     *
     * @return QueryStatement the default statement object
     * @throws QueryException
     */
    public QueryStatement createStatement() throws QueryException;

    /**
     * Creates a PreparedStatement object for sending parameterized OQL statements to the engine.
     * A OQL statement with or without IN parameters can be pre-compiled and stored in a QueryPreparedStatement object.
     * This object can then be used to efficiently execute this statement multiple times.
     * <p/>
     * Note: This method is optimized for handling parametric SQL statements that benefit from precompilation.
     * If the engine supports precompilation, the method prepareStatement will send the statement to the engine for precompilation.
     * <p/>
     * Result sets created using the returned PreparedStatement object will by default be type TYPE_FORWARD_ONLY and
     * have a concurrency level of CONCUR_READ_ONLY.
     *
     * @return PreparedQueryStatement
     * @throws QueryException
     */
    public QueryPreparedStatement prepareStatement() throws QueryException;

    /**
     * @return QueryService the related QueryService
     */
    public QuerySession getQuerySession();

    /**
     * Retrieve the Query Name identifier
     *
     * @return String the name identifying each unique query
     */
    public String getName() throws QueryException;

    /**
     * @return QueryModel the parsed Query Model
     * @throws QueryException
     */
    public QueryModel getModel() throws QueryException;

    /**
     * Releases this Query object's Session and Provider resources immediately instead of waiting for them to be automatically released.
     * Calling the method close on a Query object that is already closed is a no-op.
     */
    public void close() throws QueryException;


    /**
     * @return QueryExecutionClassLoader
     */
    public QueryExecutionClassLoader getExecutionClassLoader() throws QueryException;

    /**
     * Gets the IdentifierGenerator attached to this Query.
     *
     * @return IdentifierGenerator
     */
    public IdentifierGenerator getIdGenerator();


    String getSourceText();


    PlanGenerator getPlanGenerator();

    public void setQueryFeatures();

    public QueryFeatures getQueryFeatures();


    public interface StateChangeListener {

        void notify(Query.StateChangeEvent e);


    }

    public interface StateChangeEvent {
        /**
         * Returns the Query object
         *
         * @return Query
         */
        Query getQuery();

        /**
         * Return the state of the query
         *
         * @return int
         */
        int getState();

        /**
         * Return true if the query state is QUERY_STATE_OPEN
         *
         * @return boolean true or false
         */
        boolean isOpen();

        /**
         * Return true if the query state is QUERY_STATE_PARSED
         *
         * @return boolean true or false
         */
        boolean isParsed();

        /**
         * Return true if the query state is QUERY_STATE_MODELED
         *
         * @return boolean true or false
         */
        boolean isModeled();

        /**
         * Return true if the query state is QUERY_STATE_RESOLVED
         *
         * @return boolean true or false
         */
        boolean isResolved();

        /**
         * Return true if the query state is QUERY_STATE_VALIDATED
         *
         * @return boolean true or false
         */
        boolean isValidated();

        /**
         * Return true if the query state is QUERY_STATE_COMPILED
         *
         * @return boolean true or false
         */
        boolean isCompiled();

        /**
         * Return true if the query state is QUERY_STATE_CLOSED
         *
         * @return boolean true or false
         */
        boolean isClosed();
    }


}
