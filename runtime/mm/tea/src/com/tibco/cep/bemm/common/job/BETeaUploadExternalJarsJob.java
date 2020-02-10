package com.tibco.cep.bemm.common.job;

import javax.activation.DataSource;

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
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.MasterHost;

/**
 * Job is used to upload the external jars to remote location
 * 
 * @author dijadhav
 *
 */
public class BETeaUploadExternalJarsJob implements GroupJob<Object> {
	/**
	 * Master host management service
	 */
	private BEMasterHostManagementService beMasterHostManagementService;
	/**
	 * Uploaded files
	 */
	private DataSource jarFiles;
	private MasterHost masterHost;
	private MessageService messageService;
	private String beId;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GroupJob.class);

	/**
	 * Constructor to set remote and local file
	 * 
	 * @param beMasterHostManagementServiceImpl
	 *            - Remote file
	 * @param jarFiles
	 *            - Local file
	 */
	public BETeaUploadExternalJarsJob(BEMasterHostManagementService beMasterHostManagementServiceImpl,
			DataSource jarFiles, MasterHost masterHost, String beId) {
		super();
		this.beMasterHostManagementService = beMasterHostManagementServiceImpl;
		this.jarFiles = jarFiles;
		this.masterHost = masterHost;
		this.beId = beId;
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
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPLOADING_FILE, jarFiles.getName()));
		int maxRetryCount = 1;
		boolean uploaded = false;
		String os = this.masterHost.getOs();
		if (null == os)
			os = ((JSchGroupJobExecutionContext) executionContext).getServiceInstance().getHost().getOs();
		final ConnectionPool<Session> sessionPool = BEMMServiceProviderManager.getInstance().getJSchConnectionPool(os);
		PooledConnection<Session> connection = null;
		// Retry if there is JSCH exception
		do {
			try {
				connection = sessionPool.getConnection((PooledConnectionConfig) executionContext);
				Session session = connection.getUnderlyingConnection();

				beMasterHostManagementService.uploadExternalJars(masterHost, jarFiles, beId, session);
				maxRetryCount = -1;
				uploaded = true;
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.UPLOAD_FILE_ERROR, jarFiles.getName()));
				if (e instanceof JschCommandFailException || e instanceof JSchException) {
					if (null != connection)
						connection.setMarkedUnusable(true);
					maxRetryCount--;
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRYING_UPLOAD_FILE, jarFiles.getName()));
				} else
					maxRetryCount = -1;
			} finally {
				if (null != connection) {
					sessionPool.returnConnection(connection);
				}
			}
		} while (maxRetryCount >= 0);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.JOB_COMPLETED_UPLOAD_FILE, jarFiles.getName()));
		return uploaded;
	}

}
