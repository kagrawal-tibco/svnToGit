package com.tibco.be.oracle;

import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.util.Properties;

public class MockRuleServiceProvider implements RuleServiceProvider {

	public void configure(int mode) throws Exception {
		// TODO Auto-generated method stub

	}

	public ChannelManager getChannelManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	public GlobalVariables getGlobalVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	public IdGenerator getIdGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Logger getLogger() {
		return m_logger;
	}
	
	public void setLogger(Logger logger) {
		m_logger = logger;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public DeployedProject getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getProperties() {

		return m_props;
	}

	public void setProperties(Properties props) {

		m_props = props;
	}
	
	public RuleAdministrator getRuleAdministrator() {
		// TODO Auto-generated method stub
		return null;
	}

	public RuleRuntime getRuleRuntime() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeManager getTypeManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}
	
	private Properties m_props = null;
	private Logger m_logger = null;

}
