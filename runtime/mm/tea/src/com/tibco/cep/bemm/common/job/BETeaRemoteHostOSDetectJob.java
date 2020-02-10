package com.tibco.cep.bemm.common.job;

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
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.MasterHost;

/**
 * @author dijadhav
 *
 */
public class BETeaRemoteHostOSDetectJob implements GroupJob<Object> {
	private MessageService messageService;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * @param integer
	 * @param masterHost
	 * 
	 */
	private MasterHost masterHost;
	private int timeout;
	private String host;

	public BETeaRemoteHostOSDetectJob(MasterHost masterHost, int timeout) {
		this.masterHost = masterHost;
		this.timeout = timeout;
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
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;
		host = context.getSshConfig().getHostIp();
		BETeaOperationResult operationResult = new BETeaOperationResult();
		int maxRetryCount = 1;
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance()
				.getJSchConnectionPool(null);
		PooledConnection<Session> connection = null;
		do {
			try {

				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				String command = "uname -s";
				String osDetails = ManagementUtil.getOSDetails(command, session, timeout);
				if (osDetails.contains("CYGWIN_NT")) {
					// command = "systeminfo | findstr /B /C:\"OS \" ";
					command = "wmic os get Caption /value";
					String os = ManagementUtil.getOSDetails(command, session, timeout);
					if (null != os && !os.trim().isEmpty()) {
						os = os.trim().replace("Caption=", "");
					} else {
						os = "Microsoft Windows";
					}
					osDetails = os;
				} else if (osDetails.contains("Linux")) {
					command = "cat /etc/redhat-release";
					String os = ManagementUtil.getOSDetails(command, session, timeout);
					if (null == os || os.trim().isEmpty()) {
						command = "lsb_release -d";
						os = ManagementUtil.getOSDetails(command, session, timeout);
						if (null != os && !os.trim().isEmpty()) {
							os = os.replace("Description", "").trim();
							osDetails = os;
						}
					} else {
						os = os.replace("Description", "").trim();
						osDetails = os;
					}
					osDetails = osDetails.replace(":", "").trim();
				} else if (osDetails.contains("Darwin")) {
					command = "sw_vers -productName";
					osDetails = ManagementUtil.getOSDetails(command, session, timeout);
				}
				maxRetryCount = -1;
				operationResult.setResult(osDetails);
			} catch (Exception e) {
				operationResult.setResult(e);
				if (null != masterHost)
					LOGGER.log(Level.DEBUG,
							messageService.getMessage(MessageKey.HOST_GET_OS_DETAILS_ERROR, masterHost.getHostName()));
				else
					LOGGER.log(Level.DEBUG,
							messageService.getMessage(MessageKey.HOST_GET_OS_DETAILS_ERROR, host));

				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					if (null != masterHost)
						LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.HOST_RETRYING_GET_OS_DETAILS,
								masterHost.getHostName()));
					else 
						LOGGER.log(Level.DEBUG,
								messageService.getMessage(MessageKey.HOST_GET_OS_DETAILS_ERROR, host));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}

			}
		} while (maxRetryCount >= 0);
		return operationResult;
	}

}
