package com.tibco.rta;

import com.tibco.rta.annotations.Idempotent;
import com.tibco.rta.annotations.RetryOnRejection;
import com.tibco.rta.client.SessionInitFailedException;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.runtime.ServerConfigurationCollection;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A user session to communicate with the metric engine.
 * The main class used for saving and retrieving model changes to the engine,
 * querying the metrics as well as for submitting facts to the engine.
 * <p/>
 * {@code RtaSession}  is thread-safe
 */
public interface RtaSession {

    /**
     * Synchronous session initialization which attempts to connect
     * to metric server. Blocks forever for init to complete.
     * <p>
     * If session is already inited, this call does not do anything.
     * </p>
     *
     * @throws ConnectionRefusedException if connection could not be established.
     * @throws SessionInitFailedException Any failure in init phase
     */
    void init() throws ConnectionRefusedException, SessionInitFailedException;

    /**
     * Synchronous session initialization which attempts to connect
     * to metric server.
     * <p>
     * If session is already inited, this call does not do anything.
     * </p>
     * <p>
     *     This call blocks only for specified timeout period.
     *     If session init could not complete during this time,
     *     {@link SessionInitFailedException} is thrown.
     * </p>
     *
     * @param timeout time to wait for init to complete
     * @param units Timeunits.
     * @throws ConnectionRefusedException if connection could not be established.
     * @throws SessionInitFailedException Any failure in init phase.
     */
    void init(long timeout, TimeUnit units) throws ConnectionRefusedException, SessionInitFailedException;

    /**
     * Return session id.
     * This will be unique for every session.
     *
     * @return
     */
    String getClientId();

    /**
     * Gets a schema with this name available with the metric engine.
     *
     * @param name the name
     * @return the schema
     * @throws RtaException if session was closed or the remote operation failed.
     */
    @Idempotent
    @RetryOnRejection
    <T extends RtaSchema> T getSchema(String name) throws RtaException;

     /**
     * Register schema with this session locally.
     * If already registered, return the same instance.
     * <p>
     * This operation is a local operation and does not
     * communicate with the metric engine.
     * </p>
     *
     * @param schema the schema
     * @return An instance of {@code RtaSchema}
     * @throws RtaException
     */
    RtaSchema registerSchema(RtaSchema schema) throws RtaException;


    /**
     * Publish a fact to the metric/aggregation engine.
     * <p>
     * Facts will be buffered and asynchronously
     * asserted to the metric engine in a manner driven by a buffer criterion
     * </p>
     * <p>
     * The {@link Future} returned by this API may return a null when its <code>get</code>
     * is called since facts are buffered and another <code>get</code> call on another future
     * may return more than one such ids separated by a ,
     * </p>
     *
     * @param fact The fact instance to be published.
     * @return a Future task wrapping transaction id upon successful submission of fact.
     * @throws RtaException if session was closed or the remote operation failed.
     */
    @Idempotent
    Future<Object> publishFact(Fact fact) throws RtaException;


    /**
     * Close the session and cleanup any underlying resources.
     * Clients should call this API once they are done with using a session.
     *
     * @throws RtaException
     */
    void close() throws RtaException;

    /**
     * Register a transient query on the server.
     *
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    Query createQuery() throws RtaException;

    /**
     * Get this metrics child metrics (for drilldown)
     *
     * @param metric      The metric
     * @param orderByList Optional orderBy
     * @return A metric browser
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    <S, T> Browser<T> getChildMetrics(Metric<S> metric, List<MetricFieldTuple> orderByList) throws RtaException;

    /**
     * Get this metrics constituent facts (for drilldown and analysis)
     *
     * @param metric      The metric
     * @param orderByList Optional orderby
     * @return A fact browser
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    <S, T> Browser<T> getConstituentFacts(Metric<S> metric, List<MetricFieldTuple> orderByList) throws RtaException;


    /**
     * Set a listener interested in server notifications for all possible server notifications
     *
     * @param listener       the lister to notify.
     * @see NotificationListenerKey
     */
    void setNotificationListener(RtaNotificationListener listener);

    /**
     * Set a listener interested in server notifications like transaction success, failure etc.
     *
     * @param listener       the lister to notify.
     * @param interestEvents Set preference for receiving notifications from metric engine
     * @see NotificationListenerKey
     */
    void setNotificationListener(RtaNotificationListener listener, int interestEvents);


    /**
     * Listener for asynchronous command notifications.
     * <p>
     * For example if API caller wants to take some action
     * based on rule evaluation like stopping a process on machine,
     * the metric engine will send the command in a manner that can
     * be received by the caller of this API to execute appropriate
     * action.
     * </p>
     *
     * @param listener
     */
    void setCommandListener(RtaCommandListener listener);

    /**
     * Get all the rules configured on the server.
     *
     * @return a list of configured rules.
     * @throws Exception
     */
    @Idempotent
    @RetryOnRejection
    List<String> getRules() throws Exception;

    /**
     * Get all the rule definitions configured on the server.
     *
     * @return a list of configured rules.
     * @throws Exception
     */
    @Idempotent
    @RetryOnRejection
    List<RuleDef> getAllRuleDefs() throws Exception;


    /**
     * Get the rule with the specified name. Names are unique across all rules.
     *
     * @param name the rule name
     * @return the matching rule.
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    RuleDef getRule(String name) throws RtaException;

    /**
     * Create a new rule with the specified descriptor.
     *
     * @param rule the rule descriptor.
     * @throws RtaException
     * @throws Exception 
     */
    @Idempotent
    @RetryOnRejection
    void createRule(RuleDef rule) throws Exception;

    /**
     * Update an existing rule with the specified descriptor.
     * Rule with the same name as in the descriptor will be updated.
     *
     * @param rule the rule descriptor to update with.
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    void updateRule(RuleDef rule) throws RtaException;

    /**
     * Delete the rule with the given name.
     *
     * @param name
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    void deleteRule(String name) throws RtaException;


    /*
     * Get all metric function descriptor 
     * @return list of metric function descriptors
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    List<MetricFunctionDescriptor> getAllFunctionDescriptors() throws RtaException;

    /**
     * Get all action function descriptor 
     * @return list of action function descriptors
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    List<ActionFunctionDescriptor> getAllActionFunctionDescriptors() throws RtaException;
    
    /**
     * Gets the server side runtime configuration.
     */
    public ServerConfigurationCollection getServerConfiguration() throws RtaException;
    
    /**
     * Clear All alerts specified by ids
     * @param alert_ids to clear
     * @throws RtaException
     */
    @Idempotent
    @RetryOnRejection
    public void clearAlerts(Collection<String> alert_ids) throws RtaException;
}