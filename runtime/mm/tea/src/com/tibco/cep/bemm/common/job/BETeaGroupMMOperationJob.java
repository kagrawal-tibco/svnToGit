package com.tibco.cep.bemm.common.job;

import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.context.BETeaMMOpertaionJobContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * Job used to stop the service instance
 * 
 * @author dijadhav
 *
 */
public class BETeaGroupMMOperationJob implements GroupJob<Object> {

	private MessageService messageService;
	/**
	 * Instance Service
	 */
	private BEServiceInstancesManagementService instanceService;
	/**
	 * Name of logged in user
	 */
	private String loggedInUser;
	private String methodGroup;
	private String entityName;
	private Map<String, String> params;
	private String methodName;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param instanceService
	 * @param loggedInUser
	 * @param methodGroup
	 * @param entityName
	 * @param params
	 * @param methodName
	 */
	public BETeaGroupMMOperationJob(BEServiceInstancesManagementService instanceService, String loggedInUser,
			String methodGroup, String entityName, Map<String, String> params, String methodName) {
		super();
		this.instanceService = instanceService;
		this.loggedInUser = loggedInUser;
		this.methodGroup = methodGroup;
		this.entityName = entityName;
		this.params = params;
		this.methodName = methodName;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		BETeaMMOpertaionJobContext context = (BETeaMMOpertaionJobContext) executionContext;
		BETeaOperationResult operationResult = new BETeaOperationResult();
		ServiceInstance serviceInstance = context.getServiceInstance();
		operationResult.setName(serviceInstance.getName());
		int agentId = context.getAgentId();
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.STARTED_INVOKE_METHOD, serviceInstance.getName()));

		try {
			Host host = serviceInstance.getHost();
			int jmxPort = serviceInstance.getJmxPort();
			String username = serviceInstance.getJmxUserName();
			String hostIp = host.getHostIp();
			String password = serviceInstance.getJmxPassword();
			OperationResult result = instanceService.invoke(entityName, methodGroup, methodName, params, username,
					password, hostIp, jmxPort, agentId, serviceInstance);
			operationResult.setResult(result);
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKE_METHOD_ERROR, serviceInstance.getName()));
		}
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.COMPLETED_INVOKE_METHOD, serviceInstance.getName()));

		return operationResult;
	}

}
