package com.tibco.cep.dashboard.integration.be;

import java.io.IOException;
import java.util.Properties;

import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.psvr.streaming.StreamingProperties;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager.SessionListener;
import com.tibco.cep.driver.ancillary.tcp.server.TCPServer;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.impl.BEArchiveResourceImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;

class TCPDestinationDeployer implements TransportDeployer {

	private Logger logger;

	private String serverNickName;

	private String hostName;

	private int port;

	private TCPServer tcpServer;

	private TCPServerSessionListener sessionListener;

	private Class<? extends SimpleEvent> eventClass;

	@Override
	public <T extends SimpleEvent> ArchiveInputDestinationConfig[] deploy(DashboardSession dashboardSession, Logger logger, Properties properties, Class<T> event) throws ManagementException {
		this.logger = logger;
		hostName = (String) ConfigurationProperties.HOST_NAME.getValue(properties);
		serverNickName = hostName+"-streaming-server";
		hostName = (String) ConfigurationProperties.HOST_NAME.getValue(properties);
		port = (Integer) StreamingProperties.STREAMING_PORT.getValue(properties);
		logger.log(Level.INFO, "Configuring TCP Channel with %1s as hostname and %2s as port...", hostName, port);
		tcpServer = new TCPServer();
		TCPServer.Parameters parameters = new TCPServer.Parameters(serverNickName, hostName, port);
		try {
			tcpServer.init(null, parameters, LoggingService.getChildLogger(LoggingService.getRootLogger(), "streaming.channel"));
			sessionListener = new TCPServerSessionListener();
			tcpServer.setListener(sessionListener);
		} catch (IOException e) {
			throw new ManagementException("could not initialize streaming server", e);
		}
		this.eventClass = event;
		return new BEArchiveResourceImpl.InputDestinationConfigImpl[0];
	}

	@Override
	public void start(DashboardSession dashboardSession) throws ManagementException {
		try {
			tcpServer.start();
			logger.log(Level.INFO, "Started TCP Channel with using %1s as hostname and %2s as port...", hostName, port);
		} catch (Exception e) {
			throw new ManagementException("could not start streaming server", e);
		}
	}

	@Override
	public void pause() {
		try {
			tcpServer.pause();
		} catch (Exception e) {
			logger.log(Level.WARN, e, "could not pause streaming server");
		}
	}

	@Override
	public void resume() {
		try {
			tcpServer.resume();
		} catch (Exception e) {
			logger.log(Level.WARN, e, "could not resume streaming server");
		}
	}

	@Override
	public void stop() {
		try {
			tcpServer.stop();
		} catch (Exception e) {
			logger.log(Level.WARN, e, "could not stop streaming server");
		}
	}

	@Override
	public void shutdown() {
		stop();
	}

	private class TCPServerSessionListener implements SessionListener {

		@Override
		public void onNewSession(Session session) {
			try {
				Reader reader = session.getReader();
				StringBuilder sb = new StringBuilder();
				// lets read 1K bytes @ a time
				byte[] batch = new byte[1024];
				int numRead = reader.read(batch, 0, 1024);
				while (numRead != -1) {
					sb.append(new String(batch,0,numRead));
					if (numRead < 1024){
						break;
					}
					numRead = reader.read(batch, 0, 1024);
				}
				DashboardAgentFunctions.startStreamingUsingEventClass(session, eventClass, sb.toString());
				session.getReader().stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void removeSession(Session session){
		}
	}
}
