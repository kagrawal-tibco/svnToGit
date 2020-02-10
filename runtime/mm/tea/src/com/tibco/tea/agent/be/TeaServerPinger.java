
package com.tibco.tea.agent.be;

import java.util.TimerTask;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.tea.agent.server.TeaAgentServer;

/**
 * This task is used to ping the tea server and check whether tea server is
 * running or not
 * 
 * @author dijadhav
 *
 */
public class TeaServerPinger extends TimerTask {
	/**
	 * Logger object
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(TeaServerPinger.class);
	private String serverURL;
	private TeaAgentServer server;
	private MessageService messageService;
	private String agentName;
	/**
	 * Constructor to set the server url and server
	 * 
	 * @param serverURL
	 * @param server
	 * @param agentName 
	 */
	public TeaServerPinger(String serverURL, TeaAgentServer server, String agentName) {
		super();
		this.serverURL = serverURL;
		this.server = server;
		this.agentName=agentName;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.PINGING_TEA_SERVER));

			try {
				server.autoRegisterAgent(agentName, serverURL);
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.TEA_SERVER_RUNNING));
			} catch (Exception e) {
				String message = e.getMessage();
				if(null!=e.getCause()){
					message+=" "+e.getCause().getMessage();
				}
				if(null!=e.getCause().getCause()){
					message+=" "+e.getCause().getCause().getMessage();
				}
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.TEA_SERVER_NOT_RUNNING,message));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.SERVER_PINGER_TASK_ERROR, e.getMessage()));
		}
	}

}
