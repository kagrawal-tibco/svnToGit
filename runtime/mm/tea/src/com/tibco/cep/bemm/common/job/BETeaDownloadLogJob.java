
package com.tibco.cep.bemm.common.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
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
import com.tibco.cep.bemm.management.service.impl.BETeaDownloadLogEntry;
import com.tibco.cep.bemm.management.util.FetchCddDataUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.tea.agent.be.util.BEAgentUtil;

/**
 * Job to download log file
 * 
 * @author dijadhav
 *
 */
public class BETeaDownloadLogJob implements GroupJob<Object> {
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);
	private File beTeaTempFileLocation;
	Map<String, String> instanceLogLocation;
	private int timeout;
	private boolean isASLog;
	private MessageService messageService;

	public BETeaDownloadLogJob(File beTeaTempFileLocation, Map<String, String> instanceLogLocation,  int timeout, boolean isASLog) {
		this.beTeaTempFileLocation = beTeaTempFileLocation;
		this.instanceLogLocation = instanceLogLocation;
		this.timeout = timeout;
		this.isASLog = isASLog;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(
				((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs());

		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;

		ServiceInstance serviceInstance = context.getServiceInstance();
		String startPuMethod = BEAgentUtil.determineMethod(serviceInstance.getHost().getOs());

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

		if (startPuMethod.startsWith("windows")) {
			if(path.contains(":")){
				path = BEAgentUtil.getWinSshPath(path);
			}
			cdPath = BEAgentUtil.getWinSshPath(cdPath);
		}
		cdPath = "cd " + cdPath;
		
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_STARTED_DOWNLOAD_LOG_FILE, serviceInstance.getName()));

		File tempFile = File.createTempFile(name + "_" + System.currentTimeMillis(), ".log", beTeaTempFileLocation);
		BETeaDownloadLogEntry downloadLogEntry = new BETeaDownloadLogEntry();
		downloadLogEntry.setLogFilePath(tempFile.getAbsolutePath());
		downloadLogEntry.setLogfilename(logFileName);
		PooledConnection<Session> connection = null;
		int maxRetryCount = 1;

		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				ManagementUtil.downloadRemoteFile(cdPath, path, session, tempFile.getAbsolutePath(), timeout);
				maxRetryCount = -1;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_DOWNLOAD_LOG_FILE_ERROR,
						serviceInstance.getName(), e.getMessage()));
				downloadLogEntry.setErrorCode(500);
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (e.getCause() instanceof FileNotFoundException)
						downloadLogEntry.setErrorCode(404);
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_RETRYING_DOWNLOAD_LOG_FILE,
							serviceInstance.getName(), e.getMessage()));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_JOB_COMPLETED_DOWNLOAD_LOG_FILE, serviceInstance.getName()));

		return downloadLogEntry;
	}

}
