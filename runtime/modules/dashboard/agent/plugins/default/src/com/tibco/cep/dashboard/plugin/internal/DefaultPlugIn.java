package com.tibco.cep.dashboard.plugin.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.plugin.Builder;
import com.tibco.cep.dashboard.psvr.plugin.IResolver;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.ShutDown;
import com.tibco.cep.dashboard.psvr.plugin.StartUp;

public class DefaultPlugIn extends PlugIn {
	
	private static final int START_ORDER = -1;
	
	public static final String PLUGIN_ID = Integer.toString(START_ORDER);
	
	private static final String NAME = "default";
	
	private static final String DESCRIPTIVE_NAME = "Default PlugIn";
	
	private Map<ResolverType, IResolver> resolvers;

	private Builder builder;
	
	public DefaultPlugIn() {
		super();
		resolvers = new HashMap<ResolverType, IResolver>();
	}
	
	@Override
	public String getId() {
		return PLUGIN_ID;
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDescriptiveName() {
		return DESCRIPTIVE_NAME;
	}
	
	@Override
	public int getStartOrder() {
		return START_ORDER;
	}	
	
	@Override
	protected void init() throws ManagementException {
		loadResolvers();
		builder = new DefaultBuilder(this);
	}
	
	private void loadResolvers() throws ManagementException {
		ResolverType resolverType = null;
		String className = null;
		try {
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream("/defaultresolvers.map"));
			Enumeration<Object> keys = props.keys();
			while (keys.hasMoreElements()) {
				String resolverTypeAsStr = (String) keys.nextElement();
				resolverType = ResolverType.valueOf(resolverTypeAsStr);
				className = props.getProperty(resolverTypeAsStr);
				if (StringUtil.isEmptyOrBlank(className) == false){
					Class<?> clazz = Class.forName(className);
					IResolver instance = (IResolver) clazz.newInstance();
					instance.init(logger, exceptionHandler, messageGenerator);
					resolvers.put(resolverType, instance);
				}
			}
		} catch (IOException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.mappingfile.loading.failure",new MessageGeneratorArgs(e)));
		} catch (ClassNotFoundException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.class.notfound.failure",new MessageGeneratorArgs(e,resolverType,className)));
		} catch (InstantiationException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.class.instantiation.failure",new MessageGeneratorArgs(e,resolverType,className)));
		} catch (IllegalAccessException e) {
			throw new ManagementException(messageGenerator.getMessage("resolver.class.access.failure",new MessageGeneratorArgs(e,resolverType,className)));
		} catch (PluginException e){
			throw new ManagementException(messageGenerator.getMessage("resolver.initialization.failure",new MessageGeneratorArgs(e,resolverType,className,e.getCause())));
		}
	}	
	
	@Override
	protected void start() throws ManagementException {

	}	

	@Override
	protected void pause() throws ManagementException {
	}

	@Override
	protected boolean ping() {
		return true;
	}

	@Override
	protected boolean stop() {
		return true;
	}

	@Override
	protected void unpause() throws ManagementException {
	}
	
	@Override
	public Builder getBuilder() {
		return builder;
	}

	@Override
	public IResolver getResolver(ResolverType type) {
		return resolvers.get(type);
	}	

	@Override
	public boolean registerMBeans(MBeanServer server, String namePrefix) {
		return true;
	}
	
	@Override
	public boolean unregisterMBeans(MBeanServer server) {
		return true;
	}

	@Override
	public URL getActionConfigURL() {
		return null;
	}

	@Override
	public ShutDown getShutdown() {
		return null;
	}

	@Override
	public StartUp getStartUp() {
		return null;
	}

}
