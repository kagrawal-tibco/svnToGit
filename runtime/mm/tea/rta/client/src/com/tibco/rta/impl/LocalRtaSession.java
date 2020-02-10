package com.tibco.rta.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.naming.OperationNotSupportedException;

import com.tibco.cep.bemm.monitoring.metric.rule.RuleExistsException;
import com.tibco.rta.ConfigProperty;
import com.tibco.rta.ConnectionRefusedException;
import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaSession;
import com.tibco.rta.client.SessionInitFailedException;
import com.tibco.rta.client.taskdefs.ConnectionTask;
import com.tibco.rta.client.util.RtaClientUtils;
import com.tibco.rta.client.utils.ModelValidationUtils;
import com.tibco.rta.client.utils.SessionUtils;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.transport.http.DefaultMessageContext;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.metric.MetricChildrenBrowserProxy;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.runtime.ServerConfigurationCollection;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FactResultTuple;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryDefEx;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.query.impl.QueryExImpl;
import com.tibco.rta.query.impl.QueryImpl;
import com.tibco.rta.query.impl.SnapshotBrowserProxy;

/**
 * @author vdhumal
 *
 */
public class LocalRtaSession extends DefaultRtaSession implements RtaSession {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

	private ConcurrentHashMap<String, RtaSchema> namesToSchemasMap2;
	private ConcurrentHashMap<String, RtaSchema> systemSchemas2;
	private AtomicBoolean hasLoadedFunctions2;
	private RtaCommandListener commandListener = null; 
	
	public LocalRtaSession(RtaConnectionEx ownerConnection, String name, Map<ConfigProperty, PropertyAtom<?>> configuration) {
		super(ownerConnection, name, configuration);

		this.namesToSchemasMap2 = new ConcurrentHashMap<>();
		this.systemSchemas2 = new ConcurrentHashMap<>();
		this.hasLoadedFunctions2 = new AtomicBoolean(false);		
	}
	
	@Override
	public void init() throws ConnectionRefusedException, SessionInitFailedException {
		//No-Op		
	}

	@Override
	public void init(long timeout, TimeUnit units) throws ConnectionRefusedException, SessionInitFailedException {
		//No-Op
	}

	public LocalRtaSession(RtaConnectionEx ownerConnection, Map<ConfigProperty, PropertyAtom<?>> configuration) {
		this(ownerConnection, null, configuration);
	}

//	public Map<ConfigProperty, PropertyAtom<?>> getConfiguration() { return null; }

	@Override
	public void close() {
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Attempting to close session");
		}
		//No-op
	}
	
//	public String getClientId() { return null;}

//	public String getName() {return null;}

//	public MessageTransmissionStrategy getTransmissionStrategy() {return null;}

//	public MessageReceptionStrategy getReceptionStrategy() {return null;}

	@Override
	public <C extends ConnectionTask<?, ?>> void performExchange(
			C connectionTask) {
		//No-op
	}
	
	@Override
	public Object getExchanged() {
		return null; //No-op
	}
		
	@Override
	public void exchange(Object toExchange) throws InterruptedException {
		//No-op
	}
	
	@Override
	public void informServerStatus(int serverStatus) {
		//No-op
	}

	@Override
	public <C extends ConnectionTask<?, ?>> void triggerRetry(C retryTask) {
		//No-op
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends RtaSchema> T getSchema(String name) throws RtaException {
        try {
            if (!hasLoadedFunctions2.get()) {
                getAllFunctionDescriptors();
                hasLoadedFunctions2.compareAndSet(false, true);
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Metric Function Descriptors are taken from session");
                }
            }

            RtaSchema localSchema = namesToSchemasMap2.get(name);
            if (localSchema != null) {
                return (T) localSchema;
            }
            RtaSchema schema = ServiceProviderManager.getInstance().getAdminService().getSchema(name);
            if (schema != null) {
                namesToSchemasMap2.put(name, schema);
            }
            return (T) schema;
        } catch (Throwable t) {
            LOGGER.log(Level.ERROR, "getSchema failed", t);
            throw new RtaException(t);
        }
	}

	@Override
	public void saveSchema(RtaSchema schema) throws RtaException {
		throw new RtaException(new OperationNotSupportedException());
	}
	
	@Override
	public RtaSchema registerSchema(RtaSchema schema) throws RtaException {
        // Check if this schema exists already. If yes do nothing
        boolean schemaExists = namesToSchemasMap2.containsValue(schema);
        if (!schemaExists) {
            namesToSchemasMap2.put(schema.getName(), schema);
            return schema;
        } else {
            return schema;
        }
	}

	@Override
	public void deleteSchema(String schemaName) throws RtaException {
		throw new RtaException(new OperationNotSupportedException());
	}
	
	@Override
	public Future<Object> publishFact(Fact fact) throws RtaException {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "In Publish Fact");
        }
        if (fact == null) {
            throw new RtaException("Invalid Argument, fact is null ");
        }
        try {
        	ArrayList<Fact> list=new ArrayList<Fact>();
        	list.add(fact);
        	ServiceProviderManager.getInstance().getMetricsService().assertFact(new DefaultMessageContext(System.currentTimeMillis()),list);
        } catch (Throwable t) {
            LOGGER.log(Level.ERROR, "Facts publish failed", t);
            throw new RtaException(t);        	
        }
        return null;
	}

	@Override
	public Query createQuery() throws RtaException {
        return new QueryExImpl(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends MetricResultTuple> Browser<T> registerQuery(QueryImpl query) throws Exception {
        try {
            ModelValidationUtils.validateQuery(query, this);
    		QueryDef queryDef = null;
    		if (query.getQueryByKeyDef() != null) {
    			queryDef = query.getQueryByKeyDef();
            } else if (query.getQueryByFilterDef() != null) {
                queryDef = query.getQueryByFilterDef();
            }

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.DEBUG, "Registering query [%s]", query.getName());
            }
    		if (queryDef instanceof QueryDefEx) {
    			((QueryDefEx)queryDef).setResultHandler(query.getResultHandler());
    		}	
    		String browserCorId = ServiceProviderManager.getInstance().getQueryService().registerQuery(queryDef);    		
            SnapshotBrowserProxy proxy = null;
            if (browserCorId != null) {
                proxy = new SnapshotBrowserProxy();
                proxy.setId(browserCorId);
                query.setId(browserCorId);
            }
            
            if (proxy != null) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Query registered [%s] with browser id [%s]", query.getName(), proxy.getId());
                }
                proxy.setSession(this);
            }
            return proxy;
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to create query", e);
            throw new RtaException(e);
        }

	}

	@Override
	public void unregisterQuery(QueryImpl query) throws Exception {
        try {
            ServiceProviderManager.getInstance().getQueryService().unregisterQuery(query.getId());
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to unregister query", e);
            throw new RtaException(e);
        }
	}

	@Override
	public boolean hasNextResult(String browserCorId) throws Exception {
        try {
        	return ServiceProviderManager.getInstance().getQueryService().hasNext(browserCorId);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to perform remote op", e);
            throw new RtaException(e);
        }	
	}

	@Override
	public Collection<QueryResultTuple> nextResult(String browserCorId) throws Exception {
        try {
        	return ServiceProviderManager.getInstance().getQueryService().getNext(browserCorId);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to fetch result", e);
            throw new RtaException(e);
        }
	}

	@Override
	public void stopBrowser(String browserCorId) throws Exception {
        try {
        	ServiceProviderManager.getInstance().getQueryService().removeBrowserMapping(browserCorId);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to stop browser", e);
            throw new RtaException(e);
        }
	}

	@Override
	public void setNotificationListener(RtaNotificationListener listener) {	}

	@Override
	public void setNotificationListener(RtaNotificationListener notificationListener, int interestEvents) { }

//	@Override
//	public RtaNotificationListener getNotificationListener() { return null;}

	@Override
	public void setCommandListener(RtaCommandListener commandListener) { 
		this.commandListener = commandListener;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <S, T> Browser<T> getChildMetrics(Metric<S> metric, List<MetricFieldTuple> orderByList) throws RtaException {
		try {
			if (orderByList != null && !orderByList.isEmpty()) {
                ModelValidationUtils.validateDimensionOrderedList(orderByList, metric, this);
	        }
	        String browserCorId = ServiceProviderManager.getInstance().getMetricIntrospectionService().getChildMetricBrowser(metric, null);
	        MetricChildrenBrowserProxy proxy = null;
	        if (browserCorId != null) {
	            proxy = new MetricChildrenBrowserProxy();
	            proxy.setId(browserCorId);
                proxy.setSession(this);
	        }
	        return proxy;
	    } catch (Throwable e) {
	        LOGGER.log(Level.ERROR, "Failed to get browser for metric children", e);
	        throw new RtaException(e);
	    }        
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <S, T> Browser<T> getConstituentFacts(Metric<S> metric, List<MetricFieldTuple> orderByList) throws RtaException {
		try {
			if (orderByList != null && !orderByList.isEmpty()) {
	        	ModelValidationUtils.validateAttributeOrderedList(orderByList, metric, this);
	        }	
			String browserCorId = ServiceProviderManager.getInstance().getMetricIntrospectionService().getConstituentFactsBrowser(metric, orderByList);
	        MetricChildrenBrowserProxy proxy = null;
	        if (browserCorId != null) {
	            proxy = new MetricChildrenBrowserProxy();
	            proxy.setId(browserCorId);
                proxy.setSession(this);
	        }
	        return proxy;
	    } catch (Throwable e) {
	        LOGGER.log(Level.ERROR, "Failed to get browser for metric children", e);
	        throw new RtaException(e);
	    }
	}

	@Override
	public boolean hasNextChild(String browserCorId) throws Exception {
        try {
        	return ServiceProviderManager.getInstance().getMetricIntrospectionService().hasNext(browserCorId);        	
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to fetch result", e);
            throw new RtaException(e);
        }
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T nextChild(String browserCorId) throws Exception {
        try {
    		Object nextChild = ServiceProviderManager.getInstance().getMetricIntrospectionService().next(browserCorId);
            QueryResultTupleCollection resultTupleCollection = null;
            if (nextChild instanceof MetricResultTuple) {
                MetricResultTuple metricResultTuple = (MetricResultTuple) nextChild;
                QueryResultTuple resultTuple = new QueryResultTuple();
                resultTuple.setMetricResultTuple(metricResultTuple);

                resultTupleCollection = new QueryResultTupleCollection<QueryResultTuple>(resultTuple);
            } else if (nextChild instanceof FactImpl) {
                FactImpl fact = (FactImpl) nextChild;
                FactResultTuple resultTuple = new FactResultTuple((FactKeyImpl) fact.getKey(), fact.getAttributes());
                resultTupleCollection = new QueryResultTupleCollection<FactResultTuple>(resultTuple);
            }
            return (T) resultTupleCollection.get(0);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "Failed to fetch result", e);
            throw new RtaException(e);
        }
	}

	@Override
	public List<ActionFunctionDescriptor> getAllActionFunctionDescriptors() throws RtaException {
        try {
            Collection<ActionFunctionDescriptor> actionFunctionDescriptors = ActionFunctionsRepository.INSTANCE.getActionFunctionDescriptors();
            return new ArrayList<ActionFunctionDescriptor>(actionFunctionDescriptors);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getAllMetricFunctionDescriptorsTask failed", e);
            throw new RtaException(e);
        }
	}

	@Override
	public List<MetricFunctionDescriptor> getAllFunctionDescriptors() throws RtaException {
        try {
            return MetricFunctionsRepository.INSTANCE.getMetricFunctionDescriptors();
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getAllMetricFunctionDescriptorsTask failed", e);
            throw new RtaException(e);
        }
	}

	@Override
	public List<String> getRules() throws RtaException {
    	List<String> ruleNames = new ArrayList<>();
		try {
        	List<RuleDef> ruleDefs = ServiceProviderManager.getInstance().getRuleService().getRules();
        	for (RuleDef ruleDef : ruleDefs) {
        		ruleNames.add(ruleDef.getName());
        	}
        } catch (Throwable t) {
            LOGGER.log(Level.ERROR, "getRule failed", t);
            throw new RtaException(t);
        }
		return ruleNames;
	}

	@Override
	public List<RuleDef> getAllRuleDefs() throws Exception {
		try {
        	return ServiceProviderManager.getInstance().getRuleService().getRules();
        } catch (Throwable t) {
            LOGGER.log(Level.ERROR, "getRule failed", t);
            throw new RtaException(t);
        }
	}

	@Override
	public RuleDef getRule(String name) throws RtaException {
        try {
        	return ServiceProviderManager.getInstance().getRuleService().getRuleDef(name);
        } catch (Throwable t) {
            LOGGER.log(Level.ERROR, "getRule failed", t);
            throw new RtaException(t);
        }
	}

	@Override
	public void createRule(RuleDef rule) throws Exception {
        try {
       		//((MutableRuleDef) rule).setCommandListener(commandListener);
            ((MutableRuleDef) rule).setCreatedDate(Calendar.getInstance());
            ((MutableRuleDef) rule).setModifiedDate(Calendar.getInstance());        	
            ModelValidationUtils.validateRule(rule);
            
            ServiceProviderManager.getInstance().getRuleService().createRule((RuleDef)RtaClientUtils.cloneOf (rule));
        }catch(RuleExistsException e){
        	throw new RuleExistsException(rule.getName()+" already exists.");
        } 
        catch (Throwable e) {
            LOGGER.log(Level.ERROR, "createRule failed", e);
            throw new RtaException(e);
        }
	}

	@Override
	public void updateRule(RuleDef rule) throws RtaException {
        try {
            if (rule == null) {
                LOGGER.log(Level.ERROR, "Rule is null");
                throw new Exception("Rule is null");
            }
            Double updatedVersion = SessionUtils.getUpdatedVersion(rule.getVersion());
            ((MutableRuleDef) rule).setVersion("" + updatedVersion);
            ((MutableRuleDef) rule).setModifiedDate(Calendar.getInstance());
            ServiceProviderManager.getInstance().getRuleService().updateRule((RuleDef)RtaClientUtils.cloneOf (rule));
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "updateRule failed", e);
            throw new RtaException(e);
        }
	}

	@Override
	public void deleteRule(String ruleName) throws RtaException {
        try {
            if (ruleName == null || ruleName.isEmpty()) {
                throw new Exception("Rule name is null");
            }
            ServiceProviderManager.getInstance().getRuleService().deleteRule(ruleName);
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "deleteRule failed", e);
            throw new RtaException(e);
        }
	}

	@Override
	public ServerConfigurationCollection getServerConfiguration() throws RtaException {
        try {
            return new ServerConfigurationCollection(ServiceProviderManager.getInstance().getRuntimeService().getRuntimeConfiguration());
        } catch (Throwable e) {
            LOGGER.log(Level.ERROR, "getServerConfiguration failed", e);
            throw new RtaException(e);
        }
	}

//	@Override
//	public void clearAlerts(Collection<String> alert_ids) throws RtaException {
//		throw new RtaException(new OperationNotSupportedException());
//
//	}

	@Override
	public Collection<RtaSchema> getAllSchemas() throws RtaException {
		try {
			Collection<RtaSchema> schemas = ServiceProviderManager.getInstance().getAdminService().getAllSchemas();
			for (RtaSchema schema : schemas) {
				this.namesToSchemasMap2
						.putIfAbsent(schema.getName(), schema);
				if (schema.isSystemSchema()) {
					this.systemSchemas2
							.putIfAbsent(schema.getName(), schema);
				}
			}
			return schemas;
		} catch (Throwable e) {
			LOGGER.log(Level.ERROR, "getAllSchemas failed", e, new Object[0]);
			throw new RtaException(e);
		}		
	}
	
	@Override
    public void clearAlerts(Collection<String> alert_ids) throws RtaException {

        if (systemSchemas2.size() == 0) {
            getAllSchemas();
        }

        for (QueryDef queryDef : SessionUtils.createClearAlertsQuerys(systemSchemas2.values(), alert_ids)) {
        	clearAlerts(queryDef);
        }
    }
	
	private void clearAlerts(QueryDef queryDef) throws RtaException {
		try {
			ServiceProviderManager.getInstance().getRuleService().clearAlerts(queryDef);
		} catch (Throwable e) {
			LOGGER.log(Level.ERROR, "clear alerts failed", e);
			throw new RtaException(e);
		}
	}

}
