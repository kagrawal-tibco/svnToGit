package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.CddFactory;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.cdd.impl.CddFactoryImpl;
import com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.utils.UUIDGen;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.service.impl.QueryRuleSessionImpl;
import com.tibco.cep.query.stream.impl.rete.service.QueryAgent;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AgentBuilder;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ColocatedQueryAgent {

	private static ColocatedQueryAgent instance;

	static final synchronized ColocatedQueryAgent getInstance() {
		if (instance == null) {
			instance = new ColocatedQueryAgent();
		}
		return instance;
	}

	private Properties properties;

	private Logger logger;

	private ExceptionHandler exceptionHandler;

	private MessageGenerator messageGenerator;

	private ServiceContext serviceContext;

	private QueryExecutor fallBackQueryExecutor;

	private QueryRuleSessionImpl querySession;

	private ColocatedQueryAgent() {
	}

	void init(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, ServiceContext serviceContext){
		try {
			this.properties = properties;
			this.logger = logger;
			this.exceptionHandler = exceptionHandler;
			this.messageGenerator = messageGenerator;
			this.serviceContext = serviceContext;
			startQueryAgent();
		} catch (Exception e) {
			String message = this.messageGenerator.getMessage("colocated.query.agent.ex");
			this.exceptionHandler.handleException(message, e, Level.WARN, Level.WARN);
			fallBackQueryExecutor = new FallBackQueryExecutor();
		}
	}

	private void startQueryAgent() throws Exception{
		logger.log(Level.INFO, "Building Co-located Query Agent Configuration...");
        //create a dynamic query agent
        CddFactory cddFactory = new CddFactoryImpl();

        QueryAgentClassConfig agentClassConfig = cddFactory.createQueryAgentClassConfig();
        agentClassConfig.setId(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName());

        AgentConfig agentConfig = cddFactory.createAgentConfig();
        agentConfig.setId(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName());
        QueryAgentClassConfigImpl qac = new QueryAgentClassConfigImpl() {
        };
        qac.setId(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName());
        agentConfig.setRef(qac);

        RuleServiceProvider ruleServiceProvider = serviceContext.getRuleServiceProvider();
		Cluster cluster = ruleServiceProvider.getCluster();
		QueryAgent queryAgent = (QueryAgent) AgentBuilder.getInstance().build(ruleServiceProvider, cluster.getClusterName(), agentConfig);

		logger.log(Level.INFO, "Initializing Co-located Query Agent...");
        queryAgent.init(cluster.getAgentManager());

        logger.log(Level.INFO, "Starting Co-located Query Agent...");
        queryAgent.start(CacheAgent.AgentState.REGISTERED);

        querySession = (QueryRuleSessionImpl) queryAgent.getRuleSession();

        logger.log(Level.INFO, "Co-located Query Agent Started...");
	}

	QueryExecutor createQueryExecutor(){
		if (fallBackQueryExecutor != null) {
			return fallBackQueryExecutor;
		}
		return new QueryAgentQueryExecutorImpl(querySession, logger, exceptionHandler, properties);
	}

	private class FallBackQueryExecutor implements QueryExecutor {

		private ResultSet resultSet;

		FallBackQueryExecutor() {
			resultSet = new FallBackResultSet();
		}

		@Override
		public int countQuery(Query query) throws QueryException {
			return 0;
		}

		@Override
		public ResultSet executeQuery(Query query) throws QueryException {
			return resultSet;
		}

		@Override
		public void close() {
		}

		private class FallBackResultSet implements ResultSet {

			private String id;

			FallBackResultSet() {
				id = new UUIDGen().createId().toString();
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public boolean next() throws QueryException {
				return false;
			}

			@Override
			public Tuple getTuple() throws QueryException {
				return null;
			}

			@Override
			public List<Tuple> getTuples(int count) throws QueryException {
				return new ArrayList<Tuple>(0);
			}

			@Override
			public void close() throws QueryException {
				//do nothing
			}

		}

	}

}
