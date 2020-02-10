package com.tibco.cep.dashboard.integration.be;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginFinder;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConfig;
import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.impl.BEArchiveResourceImpl;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.impl.ChannelConfigurationImpl;
import com.tibco.cep.runtime.channel.impl.ChannelManagerImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionConfig.ThreadingModel;
import com.tibco.cep.studio.core.adapters.ChannelAdapter;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class HTTPDestinationDeployer implements TransportDeployer {

	private Logger logger;

	private String uri;

	private Class<? extends SimpleEvent> eventClass;

	private ChannelManagerImpl channelManagerImpl;

	private com.tibco.cep.runtime.channel.Channel runTimeChannel;

	private boolean sslEnabled;

	private ArchiveInputDestinationConfig[] destinationConfigs;

	@Override
	public <T extends SimpleEvent> ArchiveInputDestinationConfig[] deploy(DashboardSession dashboardSession, Logger logger, Properties properties, Class<T> event) throws ManagementException {
		this.logger = logger;
		this.eventClass = event;
		RuleServiceProvider ruleServiceProvider = dashboardSession.getRuleServiceProvider();
		//get common configurations
		uri = (String) ConfigurationProperties.PULL_REQUEST_BASE_URL.getValue(properties);
		Channel designTimeChannelModel = null;
		//find out the method of configuration
		String sharedResourceRef = (String) BEIntegrationProperties.HTTP_SHARED_RESOURCE_REF.getValue(properties);
		if (StringUtil.isEmptyOrBlank(sharedResourceRef) == false) {
			designTimeChannelModel = createChannelUsingSharedResource(ruleServiceProvider, properties, sharedResourceRef);
		}
		if (designTimeChannelModel == null) {
			if (StringUtil.isEmptyOrBlank(sharedResourceRef) == false) {
				logger.log(Level.WARN, "Could not configure HTTP channel using shared resource %s, defaulting to properties driven configuration...", sharedResourceRef);
			}
			designTimeChannelModel = createChannelUsingProperties(ruleServiceProvider, properties);
		}
		ChannelConfigurationImpl runTimeChannelConfig = new ChannelConfigurationImpl(ruleServiceProvider.getGlobalVariables(), new ChannelAdapter(designTimeChannelModel, ruleServiceProvider.getProject().getOntology()));
		channelManagerImpl = (ChannelManagerImpl) ruleServiceProvider.getChannelManager();
		try {
			runTimeChannel = channelManagerImpl.registerChannel(runTimeChannelConfig);
			//set the channel to be synchronous , by default it is asynchronous
			((HttpChannel) runTimeChannel).setAsync(false);
			//add all the destinations
			Map<String, com.tibco.cep.runtime.channel.Channel.Destination> destinations = runTimeChannel.getDestinations();
			destinationConfigs = new BEArchiveResourceImpl.InputDestinationConfigImpl[destinations.size()];
			int i = 0;
			for (com.tibco.cep.runtime.channel.Channel.Destination destination : destinations.values()) {
				destination.bind(dashboardSession);
				destinationConfigs[i] = new BEArchiveResourceImpl.InputDestinationConfigImpl(destination.getURI(), null, ThreadingModel.CALLER.toString(), -1, 0, 0);
				i++;
			}
		} catch (Exception e) {
			throw new ManagementException("could not create http channel", e);
		}
		return destinationConfigs;
	}

	private Channel createChannelUsingSharedResource(RuleServiceProvider ruleServiceProvider, Properties properties, String sharedResourceRef) {
		logger.log(Level.INFO, "Configuring HTTP Channel using shared resource %s...", sharedResourceRef);
		XiNode resourceAsXiNode = null;
		try {
			resourceAsXiNode = ruleServiceProvider.getProject().getSharedArchiveResourceProvider().getResourceAsXiNode(sharedResourceRef);
			if (resourceAsXiNode == null) {
				logger.log(Level.WARN, "Could not find shared resource %s...", sharedResourceRef);
				return null;
			}
			if (resourceAsXiNode.getName().equals(HttpChannelConfig.HTTP_ROOT)) {
				XiNode configNode = XiChild.getChild(resourceAsXiNode, HttpChannelConfig.XML_NODE_CONFIG);
				sslEnabled = XiChild.getBoolean(configNode, HttpChannelConfig.DEST_PROPERTY_USE_SSL);
				String hostName = getSubstitutedStringValue(ruleServiceProvider.getGlobalVariables(), configNode, HttpChannelConfig.DEST_PROPERTY_HOST);
				String portString = getSubstitutedStringValue(ruleServiceProvider.getGlobalVariables(), configNode, HttpChannelConfig.DEST_PROPERTY_PORT);
				int port = Integer.parseInt(portString);
				logger.log(Level.INFO, "Configuring HTTP Channel with base URL as http://%1s:%2s with destination as %3s...", hostName, port, uri);
				Channel channel = loadChannelFromTemplate("dashboard.channel.by.reference.template");
				//update channel
				updateChannelTemplate(uri, channel, properties);
				//set configuration method and configuration reference
				channel.getDriver().setConfigMethod(CONFIG_METHOD.REFERENCE);
				channel.getDriver().setReference(sharedResourceRef);
				return channel;
			}
			logger.log(Level.WARN, "Invalid shared resource %s...", sharedResourceRef);
		} catch (Exception e) {
			logger.log(Level.WARN, "Could not load shared resource %s...", e, sharedResourceRef);
		}
		return null;
	}

    private String getSubstitutedStringValue(GlobalVariables gv, XiNode node, ExpandedName name) {
        final CharSequence cs = gv.substituteVariables(XiChild.getString(node, name));
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }//else
    }

	private Channel createChannelUsingProperties(RuleServiceProvider ruleServiceProvider, Properties properties) {
		logger.log(Level.INFO, "Configuring HTTP Channel using properties...");
		String hostName = (String) ConfigurationProperties.HOST_NAME.getValue(properties);
		Integer port = (Integer) ConfigurationProperties.PULL_REQUEST_PORT.getValue(properties);
		logger.log(Level.INFO, "Configuring HTTP Channel with base URL as http://%1s:%2s with destination as %3s...", hostName, port, uri);
		Channel channel = loadChannelFromTemplate("dashboard.channel.template");
		updateChannelTemplate(uri, channel, properties);
		//set host and port
		List<Entity> channelDriverproperties = channel.getDriver().getProperties().getProperties();
		for (Entity property : channelDriverproperties) {
			if (property.getName().equals("host") == true) {
				SimpleProperty hostProperty = (SimpleProperty) property;
				hostProperty.setValue(hostName);
			} else if (property.getName().equals("port") == true) {
				SimpleProperty portProperty = (SimpleProperty) property;
				portProperty.setValue(Integer.toString(port));
			}
		}
		return channel;
	}

	private Channel loadChannelFromTemplate(String resourceName) {
		//force register channel URI against the EMF channel factory
		EPackage.Registry.INSTANCE.put("http:///com/tibco/cep/designtime/core/model/service/channel", ChannelPackage.eINSTANCE);
		Resource resource = new XMIResourceImpl();
		URL channelTemplateURL = this.getClass().getResource(resourceName);
		if (channelTemplateURL == null) {
			throw new IllegalStateException("could not find dashboard channel template");
		}
		try {
			resource.load(channelTemplateURL.openStream(), null);
			return (Channel) resource.getContents().get(0);
		} catch (IOException e) {
			throw new IllegalStateException("could not read dashboard channel template", e);
		}
	}

	private void updateChannelTemplate(String uri, Channel channel, Properties properties) {
		if (uri.startsWith("/") == true) {
			uri = uri.substring(1);
		}
		String[] uriParts = uri.split("/");
		if (uriParts.length != 2) {
			throw new IllegalArgumentException("Invalid value[" + uri + "] specified for uri");
		}
		//set the channel name
		channel.setName(uriParts[0]);
		//update destination
		Destination destination = channel.getDriver().getDestinations().get(0);
		destination.setName(uriParts[1]);
		destination.setEventURI(eventClass.getName());
		destination.setSerializerDeserializerClass(StaticEventSerializer.class.getName());
		//get server type
		String serverType = null;
		List<SimpleProperty> extendedConfigProperties = channel.getDriver().getExtendedConfiguration().getProperties();
		for (SimpleProperty property : extendedConfigProperties) {
			if ("serverType".equals(property.getName()) == true) {
				serverType = property.getValue();
			}
		}
		//validate server type
		if (StringUtil.isEmptyOrBlank(serverType) == true) {
			throw new IllegalArgumentException("Invalid server type specified");
		}
		if ("TOMCAT".equals(serverType) == false) {
			throw new IllegalArgumentException("Invalid server type[" + serverType + "] specified");
		}
		//set doc root and doc page and other advance properties
		String docRoot = ((BEViewsPlugIn) PluginFinder.getInstance().getPluginById(BEViewsPlugIn.PLUGIN_ID)).getDeployedWebRoot().getLocation();
		String docPage = (String) ConfigurationProperties.PROP_DOC_PAGE.getValue(properties);
		logger.log(Level.INFO, "Using %1s as be.http.docRoot and %2s as be.http.docPage for HTTP Channel...", docRoot, docPage);
		Choice choice = channel.getDriver().getChoice();
		if (serverType.equalsIgnoreCase(choice.getValue()) == false) {
			throw new IllegalArgumentException("Missing extended configuration for [" + serverType + "]");
		}
		List<ExtendedConfiguration> choiceExtendedConfigurations = choice.getExtendedConfiguration();
		if (choiceExtendedConfigurations.size() != 1) {
			throw new IllegalArgumentException("Invalid extended configuration for 'TOMCAT' server type");
		}
		List<SimpleProperty> choiceExtendedConfigProperties = choiceExtendedConfigurations.get(0).getProperties();
		for (SimpleProperty property : choiceExtendedConfigProperties) {
			String name = property.getName();
			if (name.equals(HttpChannelConstants.DOC_ROOT_PROPERTY) == true) {
				property.setValue(docRoot);
			} else if (property.getName().equals(HttpChannelConstants.DEFAULT_DOC_PAGE_PROPERTY) == true) {
				property.setValue(docPage);
			} else {
				name = name.toLowerCase();
				if (name.startsWith("be.") == true) {
					name = name.substring(3);
				}
				String value = properties.getProperty(name);
				if (StringUtil.isEmptyOrBlank(value) == false) {
					property.setValue(value);
				}
			}
		}
	}

	boolean isSSLEnabled(){
		return sslEnabled;
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void start(DashboardSession dashboardSession) throws ManagementException {
		try {
			runTimeChannel.start(ChannelManager.ACTIVE_MODE);
			logger.log(Level.INFO, "Started %1s with destination as %s...", runTimeChannel.toString(), uri);
		} catch (Exception e) {
			throw new ManagementException("could not start/connect http channel for " + runTimeChannel.getURI(), e);
		}
	}

	@Override
	public void pause() {
		runTimeChannel.suspend();
	}

	@Override
	public void resume() {
		runTimeChannel.resume();
	}

	@Override
	public void stop() {
	}

	public static void main(String[] args) {
		try {
			HTTPDestinationDeployer deployer = new HTTPDestinationDeployer();
			EPackage.Registry.INSTANCE.put("http:///com/tibco/cep/designtime/core/model/service/channel", ChannelPackage.eINSTANCE);
			Resource resource = new XMIResourceImpl();
			resource.load(deployer.getClass().getResourceAsStream("dashboard.channel.template"), null);
			EObject object = resource.getContents().get(0);
			System.out.println(object);
			String[] parts = "/dashboard/controller".split("/");
			System.out.println(Arrays.toString(parts));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
