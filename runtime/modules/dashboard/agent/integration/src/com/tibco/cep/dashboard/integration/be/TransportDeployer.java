package com.tibco.cep.dashboard.integration.be;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.runtime.model.event.SimpleEvent;

public interface TransportDeployer {

	public <T extends SimpleEvent> ArchiveInputDestinationConfig[] deploy(DashboardSession dashboardSession, Logger logger, Properties properties, Class<T> event) throws ManagementException;

	public void start(DashboardSession dashboardSession) throws ManagementException;

	public void pause();

	public void resume();

	public void stop();

	public void shutdown();

}
