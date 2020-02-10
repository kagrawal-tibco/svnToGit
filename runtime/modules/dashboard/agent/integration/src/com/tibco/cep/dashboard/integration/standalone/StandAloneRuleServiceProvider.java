package com.tibco.cep.dashboard.integration.standalone;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.logging.impl.LogManagerImpl;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

public class StandAloneRuleServiceProvider implements RuleServiceProvider {

	private Properties props;

	private LogManager logManager;

	public StandAloneRuleServiceProvider(File configurationFile) throws IOException {
		props = new Properties();
		Properties tempProps = new Properties();
		FileInputStream fInStream = new FileInputStream(configurationFile);
		tempProps.load(fInStream);
		Enumeration<Object> keys = tempProps.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith("java.property.") == true) {
				String propName = key.substring("java.property.".length());
				String propValue = tempProps.getProperty(key);
				props.put(propName, propValue);
				System.setProperty(propName, propValue);
			}
			else {
				props.put(key, tempProps.getProperty(key));
			}
		}
		System.setProperty("wrapper.tra.file", configurationFile.getAbsolutePath());
		logManager = new LogManagerImpl(tempProps);
	}

	@Override
	public void configure(int mode) throws Exception {
	}

	@Override
	public ChannelManager getChannelManager() {
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		return null;
	}

	@Override
	public GlobalVariables getGlobalVariables() {
		return null;
	}

	@Override
	public IdGenerator getIdGenerator() {
		return null;
	}

	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

	@Override
	public String getName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "StandAloneRuleServiceProvider";
		}
	}

	@Override
	public DeployedProject getProject() {
		return null;
	}

	@Override
	public Properties getProperties() {
		return new BEProperties(props);
	}

	@Override
	public RuleAdministrator getRuleAdministrator() {
		return null;
	}

	@Override
	public RuleRuntime getRuleRuntime() {
		return null;
	}

	@Override
	public TypeManager getTypeManager() {
		return null;
	}

	@Override
	public void initProject() throws Exception {
	}

	@Override
	public boolean isMultiEngineMode() {
		return false;
	}

	@Override
	public void shutdown() {
	}

	@Override
	public com.tibco.cep.kernel.service.logging.Logger getLogger(String name) {
		return logManager.getLogger(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public com.tibco.cep.kernel.service.logging.Logger getLogger(Class clazz) {
		return logManager.getLogger(clazz);
	}

    @Override
    public Cluster getCluster() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCacheServerMode() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}