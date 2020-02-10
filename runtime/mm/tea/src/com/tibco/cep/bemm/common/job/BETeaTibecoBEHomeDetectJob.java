package com.tibco.cep.bemm.common.job;

import java.util.ArrayList;
import java.util.List;

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
import com.tibco.tea.agent.be.util.BEAgentUtil;

/**
 * @author dijadhav
 *
 */
public class BETeaTibecoBEHomeDetectJob implements GroupJob<Object> {
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
	private List<String> tibcoHomes;
	private MessageService messageService;
	private String startPuMethod;
	private boolean isSolaris;

	public BETeaTibecoBEHomeDetectJob(MasterHost masterHost, int timeout, List<String> tibcoHomes, String startPuMethod,
			boolean isSolaris) {
		this.masterHost = masterHost;
		this.timeout = timeout;
		this.tibcoHomes = tibcoHomes;
		this.startPuMethod = startPuMethod;
		this.isSolaris = isSolaris;
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
		BETeaOperationResult operationResult = new BETeaOperationResult();
		int maxRetryCount = 1;
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance()
				.getJSchConnectionPool(null);
		PooledConnection<Session> connection = null;
		do {

			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();
				if (null != tibcoHomes && !tibcoHomes.isEmpty()) {
					int i = 0;
					String command = "";
					StringBuilder builder = new StringBuilder();
					for (String tibcoHome : tibcoHomes) {
						if (startPuMethod.startsWith("windows")) {
							tibcoHome = BEAgentUtil.getWinSshPath(tibcoHome);
						}

						command = "ls " + tibcoHome + "/_installInfo/assembly_registry/be_server";
						String beHomeDetails = ManagementUtil.getBEHome(command, session, timeout);

						if (null != beHomeDetails && !beHomeDetails.trim().isEmpty()) {
							beHomeDetails = beHomeDetails.replace("\n", " ").replace("\r", " ");
							builder.append(beHomeDetails);
							builder.append("," + tibcoHome + "/be/\n");
							
						}
						i++;
					}
					maxRetryCount = -1;
					if (!builder.toString().isEmpty())
						operationResult.setResult(builder.toString());
				}
			} catch (Exception e) {
				operationResult.setResult(e);
				LOGGER.log(Level.DEBUG, "Failed to get OS details for %s host");
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, "Retrying to get OS details for %s host");
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
