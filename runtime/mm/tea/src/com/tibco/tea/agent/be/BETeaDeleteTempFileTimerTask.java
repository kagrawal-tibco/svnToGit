
package com.tibco.tea.agent.be;

import java.io.File;
import java.util.TimerTask;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.tea.agent.server.TeaAgentServer;

/**
 * This task is used to ping the tea server and check whether tea server is
 * running or not
 * 
 * @author dijadhav
 *
 */
public class BETeaDeleteTempFileTimerTask extends TimerTask {
	/**
	 * Logger object
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaDeleteTempFileTimerTask.class);
	private File beTeaTempFolderPath;

	/**
	 * Constructor to set Temporary file
	 * 
	 * @param beTeaTempFolderPath
	 *            - BE TEA agent temporary file
	 */
	public BETeaDeleteTempFileTimerTask(File beTeaTempFolderPath) {
		super();
		this.beTeaTempFolderPath = beTeaTempFolderPath;
	}

	@Override
	public void run() {
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DELETE_OLD_FILES_TASK));

			try {
				File[] files = beTeaTempFolderPath.listFiles();
				if (null != files) {
					for (File file : files) {
						if (null != file) {
							long fileCreationTime = file.lastModified();
							long currentTime = System.currentTimeMillis();
							// Delete files which are created one hour before
							if (currentTime - fileCreationTime >= 3600000) {
								LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.FILE_ELIGIBLE_DELETE, file.getName()));
								file.delete();
							}
						}
					}
				}
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DELETE_OLD_FILES_TASK_ERROR) + e);
			}
		} catch (Exception e) {
			try {
				LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DELETE_OLD_FILES_TASK_ERROR), e);
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
		}
	}

}
