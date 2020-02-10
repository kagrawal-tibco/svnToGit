package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.BackingStore;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.functions.QueryUtilFunctions;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ViewsQueryExecutorFactory {

	private static ViewsQueryExecutorFactory instance;

	public static final synchronized ViewsQueryExecutorFactory getInstance() {
		if (instance == null) {
			instance = new ViewsQueryExecutorFactory();
		}
		return instance;
	}

	private Properties properties;

	private Logger logger;

	private ExceptionHandler exceptionHandler;

	@SuppressWarnings("unused")
	private MessageGenerator messageGenerator;

	private boolean initialized;

	private boolean demoMode;

	private DemoQueryExecutorImpl demoBEViewsQueryExecutorImpl;

	private boolean useDynamicQuerySession;

	private ColocatedQueryAgent queryAgent;

	private ServiceContext serviceContext;

	private ViewsQueryExecutorFactory() {

	}

	public void init(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, ServiceContext serviceContext) throws Exception {
		this.properties = properties;
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		this.serviceContext = serviceContext;
		this.demoMode = (Boolean)BEViewsProperties.DEMO_MODE.getValue(this.properties);
		if (demoMode == true) {
			demoBEViewsQueryExecutorImpl = new DemoQueryExecutorImpl(properties);
		}
		useDynamicQuerySession = false;
		//is backing store enabled and jdbc based ?
		if (BackingStore.isJDBC() == true) {
			//backing store is on, we use cache query only if enforced
			boolean enforceQueryAgent = (Boolean) BEViewsProperties.FORCE_CACHE_QUERY_MODE.getValue(this.properties);
			if (enforceQueryAgent == true) {
				logger.log(Level.INFO, "Backing store is enabled, enforcing cache querying...");
				useDynamicQuerySession = true;
			}
		}
		else {
			//backing store is disabled, we use cache query only if enabled
			logger.log(Level.INFO, "Backing store is disabled, enabling cache querying...");
			useDynamicQuerySession = true;
		}
		if (useDynamicQuerySession == true) {
//			queryAgent = ColocatedQueryAgent.getInstance();
//			queryAgent.init(this.properties, this.logger, this.exceptionHandler, this.messageGenerator, serviceContext);
			RuleServiceProvider ruleServiceProvider = serviceContext.getRuleServiceProvider();
			QueryUtilFunctions.DynamicQueryAgentStarter agentStarter = new QueryUtilFunctions.DynamicQueryAgentStarter(ruleServiceProvider, ruleServiceProvider.getCluster());
			agentStarter.call();
		}
		initialized = true;
	}

	public QueryExecutor createImplementation() throws QueryException {
		if (initialized == false){
			throw new IllegalStateException("Not initialized");
		}
		if (demoMode == true){
			return demoBEViewsQueryExecutorImpl;
		}
		if (useDynamicQuerySession == true) {
			if (queryAgent != null) {
				return queryAgent.createQueryExecutor();
			}
			return new DynamicQuerySessionQueryExecutorImpl(serviceContext, logger, exceptionHandler, properties);
		}
		return new BackingStoreQueryExecutorImpl(logger, exceptionHandler, properties);
	}
}
