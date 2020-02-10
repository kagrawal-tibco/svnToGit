package com.tibco.cep.dashboard.integration.be;

import java.util.Properties;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementProperties;
import com.tibco.cep.dashboard.psvr.streaming.StreamingProperties;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.runtime.model.event.SimpleEvent;

class AutoTransportDeployer implements TransportDeployer {

	private HTTPDestinationDeployer httpDestinationDeployer;
	private TCPDestinationDeployer tcpDestinationDeployer;

	AutoTransportDeployer() {
		httpDestinationDeployer = new HTTPDestinationDeployer();
		tcpDestinationDeployer = new TCPDestinationDeployer();
	}

	@Override
	public <T extends SimpleEvent> ArchiveInputDestinationConfig[] deploy(DashboardSession dashboardSession, Logger logger, Properties properties, Class<T> event) throws ManagementException {
		ArchiveInputDestinationConfig[] channels = httpDestinationDeployer.deploy(dashboardSession, LoggingService.getChildLogger(logger, "httpchannelcreator"), properties, event);
		if (httpDestinationDeployer.isSSLEnabled() == true) {
			if (properties instanceof ManagementProperties) {
				ManagementProperties managementProperties = (ManagementProperties) properties;
				managementProperties.updateProperty(StreamingProperties.STREAMING_ENABLED.getName(), "false");
			}
		}
		boolean streamingEnabled = (Boolean) StreamingProperties.STREAMING_ENABLED.getValue(properties);
		if (streamingEnabled == true && httpDestinationDeployer.isSSLEnabled() == false) {
			ArchiveInputDestinationConfig[] tcpChannelConfigs = tcpDestinationDeployer.deploy(dashboardSession, LoggingService.getChildLogger(logger, "tcpchannelcreator"), properties, event);
			ArchiveInputDestinationConfig[] httpChannelConfigs = channels;
			channels = new ArchiveInputDestinationConfig[httpChannelConfigs.length+tcpChannelConfigs.length];
			System.arraycopy(httpChannelConfigs, 0, channels, 0, httpChannelConfigs.length);
			System.arraycopy(tcpChannelConfigs, 0, channels, httpChannelConfigs.length, tcpChannelConfigs.length);
		}
		else {
			tcpDestinationDeployer = null;
		}
		return channels;
	}

	@Override
	public void start(DashboardSession dashboardSession) throws ManagementException {
		httpDestinationDeployer.start(dashboardSession);
		if (tcpDestinationDeployer != null) {
			tcpDestinationDeployer.start(dashboardSession);
		}
	}

	@Override
	public void pause() {
		httpDestinationDeployer.pause();
		if (tcpDestinationDeployer != null) {
			tcpDestinationDeployer.pause();
		}
	}

	@Override
	public void resume() {
		httpDestinationDeployer.resume();
		if (tcpDestinationDeployer != null) {
			tcpDestinationDeployer.resume();
		}
	}

	@Override
	public void stop() {
		httpDestinationDeployer.stop();
		if (tcpDestinationDeployer != null) {
			tcpDestinationDeployer.stop();
		}
	}

	@Override
	public void shutdown() {
		httpDestinationDeployer.shutdown();
		if (tcpDestinationDeployer != null) {
			tcpDestinationDeployer.shutdown();
		}
	}

}
