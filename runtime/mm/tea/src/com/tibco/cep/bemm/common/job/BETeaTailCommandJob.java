package com.tibco.cep.bemm.common.job;

import java.util.Map;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.ConnectionPool;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.PooledConnection;
import com.tibco.cep.bemm.common.pool.PooledConnectionConfig;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.util.FetchCddDataUtil;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.tea.agent.be.util.BEAgentUtil;

/**
 * Job used to execute tail command
 * 
 * @author dijadhav
 *
 */
public class BETeaTailCommandJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Instance Service
	 */
	private BEServiceInstancesManagementService instanceService;

	/**
	 * Number of lines to retrieve
	 */
	private String numberOfLines;
	
	private boolean isASLog;
	private Map<String, String> instanceLogLocation;
	
	/**
	 * Logger instance
	 */
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param instanceService
	 * @param numberofLines2
	 */
	public BETeaTailCommandJob(BEServiceInstancesManagementService instanceService, String numberofLines,
			boolean isASLog, Map<String, String> instanceLogLocation) {
		super();
		this.instanceService = instanceService;
		this.numberOfLines = numberofLines;
		this.isASLog = isASLog;
		this.instanceLogLocation = instanceLogLocation;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.common.pool.GroupJob#callUsingExecutionContext(com.
	 * tibco.cep.bemm.common.pool.GroupJobExecutionContext)
	 */
	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(
				((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs());
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;
		ServiceInstance serviceInstance = context.getServiceInstance();
		PooledConnection<Session> connection = null;
		BETeaOperationResult operationResult = new BETeaOperationResult();
		operationResult.setName(serviceInstance.getName());
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_STARTED_FIRE_TAIL_COMMAND));
		int maxRetryCount = 1;

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				String path = serviceInstance.getDeploymentPath();
				String applicationName = serviceInstance.getHost().getApplication().getName();
				String name = serviceInstance.getName();
				
				String logLocation = null;
				String logFileName = null;
				
				if(isASLog){
					logLocation = instanceLogLocation.get(name + FetchCddDataUtil.LOG_LOCATION_DIR +FetchCddDataUtil.AS_EXTENSION_KEY)!=null? 
							instanceLogLocation.get(name + FetchCddDataUtil.LOG_LOCATION_DIR + FetchCddDataUtil.AS_EXTENSION_KEY) : instanceLogLocation.get(name+FetchCddDataUtil.LOG_LOCATION_DIR);
					logFileName = instanceLogLocation.get(name + FetchCddDataUtil.LOG_FILE_NAME + FetchCddDataUtil.AS_EXTENSION_KEY);
					if(logFileName==null){
						if(instanceLogLocation.get(name+FetchCddDataUtil.LOG_FILE_NAME)!=null){
							logFileName = instanceLogLocation.get(name+FetchCddDataUtil.LOG_FILE_NAME) + "-as.log";
						}
					}
				}else{
					logLocation = instanceLogLocation.get(name+FetchCddDataUtil.LOG_LOCATION_DIR);
					logFileName = instanceLogLocation.get(name+FetchCddDataUtil.LOG_FILE_NAME);
				}
				
				if(logFileName==null){
					logFileName =  isASLog ? name + "-as.log" : name + ".log";
				}
				
				if(logLocation!=null && !logLocation.equals("")){
					path = logLocation + "/";
				}else{
					path = path + "/" + applicationName + "/" + name + "/" + "logs" + "/";
				}
				
				path = path + logFileName;
				
				String cdPath = serviceInstance.getDeploymentPath() + "/" + applicationName + "/" + name ;

				String startPuMethod = BEAgentUtil.determineMethod(serviceInstance.getHost().getOs());

				if (startPuMethod.startsWith("windows")) {
					if(path.contains(":")){
						path = BEAgentUtil.getWinSshPath(path);
					}
					cdPath = BEAgentUtil.getWinSshPath(cdPath);
				}
				cdPath = "cd " + cdPath;
				
				String command = "";
				if(cdPath!=null){
					command = cdPath + ";";
				}
				command = command + "tail -"+numberOfLines + " " + path;
				Object result = instanceService.fireTailCommand(command, session);
				operationResult.setResult(result);
				maxRetryCount = -1;
				LOGGER.log(Level.DEBUG, result.toString());
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.FIRE_TAIL_COMMAND_ERROR));
				operationResult.setResult(e);
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					maxRetryCount--;
					if (null != connection)
						connection.setMarkedUnusable(true);
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_FIRE_TAIL_COMMAND));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}

		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_DEPLOY_INSTANCE_ON_HOST, serviceInstance.getName(),
				serviceInstance.getHost().getName()));
		return operationResult;
	}

}
