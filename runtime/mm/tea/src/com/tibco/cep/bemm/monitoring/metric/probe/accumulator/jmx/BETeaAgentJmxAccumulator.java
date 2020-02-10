package com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.AccumulatorPlugin;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Mapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.MultiMapping;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
/**
 * @author vasharma
 *
 */
public class BETeaAgentJmxAccumulator extends AbstractJmxAccumulator{
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaAgentJmxAccumulator.class);

	protected WorkItemService collectorThreadPool;

	@Override
	public void init(AccumulatorPlugin accumulatorConfig) throws Exception {
		LOGGER.log(Level.INFO, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INITIALIZING_SERVICE, "collector threadpool"));

		collectorThreadPool = ServiceProviderManager.getInstance().newWorkItemService("be-teagent-collector");
		collectorThreadPool.init(ServiceProviderManager.getInstance().getConfiguration());
		
		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INITIALIZED_SUCCESS, "BETeaAgentJmxAccumulator"));
	}

	@Override
	public void stop() {
		try {
			LOGGER.log(Level.INFO, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.STOPPING_COLLECTOR_THREADPOOL));
			if (collectorThreadPool != null) {
				collectorThreadPool.stop();
			}
		} catch (Exception e) {
			try {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.STOPPING_COLLECTOR_THREADPOOL_ERROR));
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void publishFacts(List<Mapping> outputMapperList, List<MultiMapping> multiMappingList,
			Map<Monitorable, Object> monitorableEntitiesRegistry,String schemaName){
		for (Map.Entry<Monitorable,Object> entity : monitorableEntitiesRegistry.entrySet()) {	
			if(((BeTeaAgentMonitorable)entity.getKey()).getStatus().equals("running")){
				WorkItem job = new InvokeMbeanAndPublishFactJob(outputMapperList,multiMappingList, entity, schemaName);
				collectorThreadPool.addWorkItem(job);
			}
		}
	}

	class InvokeMbeanAndPublishFactJob implements WorkItem {

		List<Mapping> outputMapperList;
		List<MultiMapping> multiMappingList;
		Map.Entry<Monitorable, Object> entity;
		String schemaName;

		public InvokeMbeanAndPublishFactJob (List<Mapping> outputMapperList, List<MultiMapping> multiMappingList, Map.Entry<Monitorable, Object> entity, String schemaName) {
			this.outputMapperList = outputMapperList;
			this.entity = entity;
			this.multiMappingList=multiMappingList;
			this.schemaName=schemaName;
		}
		@Override
		public Object call() throws Exception {
			try {
				invokeMbeanAndPublishFact(outputMapperList,multiMappingList, entity);
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVOKING_MBEAN_ENTITY_ERROR, entity.getKey().getName()));
			}
			return null;
		}

		@Override
		public Object get() {
			return null;
		}

		private void invokeMbeanAndPublishFact(List<Mapping> outputMapperList, List<MultiMapping> multiMappingList, Map.Entry<Monitorable, Object> entity) throws JmxException {
			JmxBeanManager beanManager= null;
			try {
				beanManager = getBeanManager(entity.getKey());

				publishSingleMapFact(outputMapperList,entity,beanManager,schemaName);

				publishMultiMapFacts(multiMappingList,entity,beanManager,schemaName);

				beanManager.close(); //Returning the clean connection
			} catch (IOException ioe) {
				beanManager.close(true);
			}
		}
	}

}
