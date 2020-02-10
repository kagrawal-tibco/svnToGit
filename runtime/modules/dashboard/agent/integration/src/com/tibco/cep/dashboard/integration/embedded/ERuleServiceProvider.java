package com.tibco.cep.dashboard.integration.embedded;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ERuleServiceProvider implements RuleServiceProvider {

	private Properties props;

	private Map<String,Logger> loggers;

	public ERuleServiceProvider() {
		props = new Properties();
		loggers = new HashMap<String, Logger>();
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
		return new GlobalVariablesProviderImpl();
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
			return "EmbeddedDashboardSessionRuleServiceProvider";
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
		Logger logger = loggers.get(name);
		if (logger == null)
			synchronized (loggers) {
				logger = loggers.get(name);
				if (logger == null) {
					logger = new ConsoleLogger(name);
					loggers.put(name,logger);
				}
			}
		return logger;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public com.tibco.cep.kernel.service.logging.Logger getLogger(Class clazz) {
		return getLogger(clazz.getSimpleName());
	}

    @Override
    public Cluster getCluster() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCacheServerMode() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

	class ConsoleLogger implements Logger {

		private String name;

		private Level currentLevel;


		public ConsoleLogger(String name) {
			this.name = name;
			setLevel(Level.INFO);
		}

		@Override
		public void setLevel(Level level) {
			currentLevel = level;
		}

		@Override
		public Level getLevel() {
			return currentLevel;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public boolean isEnabledFor(Level level) {
			//System.out.println(level+"["+level.toInt()+"] >= "+currentLevel+"["+currentLevel.toInt()+"] = "+(level.toInt() >= currentLevel.toInt()));
			return (level.toInt() >= currentLevel.toInt());
		}

		@Override
		public void log(Level level, String msg) {
			if (isEnabledFor(level) == true) {
				println(msg);
			}
		}

		@Override
		public void log(Level level, String format, Object... args) {
			if (isEnabledFor(level) == true) {
				println(String.format(format, args));
			}
		}

		@Override
		public void log(Level level, String format, Throwable thrown, Object... args) {
			if (isEnabledFor(level) == true) {
				println(String.format(format, args));
				thrown.printStackTrace(System.out);
			}
		}

		@Override
		public void log(Level level, Throwable thrown, String msg) {
			if (isEnabledFor(level) == true) {
				println(msg);
				thrown.printStackTrace(System.out);
			}
		}

		@Override
		public void log(Level level, Throwable thrown, String format, Object... args) {
			log(level,format,thrown,args);
		}

		@Override
		public void close() {
			//do nothing
		}

		private void println(String msg) {
			StringBuilder sb = new StringBuilder(currentLevel.toString());
			sb.append(" [");
			sb.append(name);
			sb.append("] ");
			sb.append(msg);
			System.out.println(sb);
		}

	}

	public static void main(String[] args) {
		ERuleServiceProvider esp = new ERuleServiceProvider();
		Level[] levels = new Level[]{
			Level.OFF,
			Level.FATAL,
			Level.ERROR,
			Level.WARN,
			Level.INFO,
			Level.DEBUG,
			Level.TRACE,
			Level.ALL
		};
		for (Level level : levels) {
			ConsoleLogger logger = esp.new ConsoleLogger(level.toString());
			logger.setLevel(level);
			for (Level level2 : levels) {
				logger.log(level2, level2.toString());
			}
			System.out.println("---------------------------------------");
		}
	}
}