package com.tibco.cep.bemm.common.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.ASException;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.listener.SpaceDefListener;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMapCreator;
import com.tibco.cep.as.kit.map.SpaceMapCreator.Parameters;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.as.kit.tuple.SerializableTupleCodec;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.taskdefs.IdempotentRunnableRetryTask;
import com.tibco.cep.bemm.common.taskdefs.Task;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.cep.bemm.model.impl.MasterHostImpl;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.management.impl.cluster.ASManagementTable;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * This service is used to get the instance data from AS
 * 
 * @author dijadhav
 *
 */
public class ApplicationASDataFeedHandlerImpl implements ApplicationDataFeedHandler<Application> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ApplicationASDataFeedHandlerImpl.class);
	
	public static final String MEMBER_SPACE2 = "tibco-be-internal-member-cache"; //For Pre-5.3 BE member space name
	public static final long EXPIRY_MILLIS = 15 * 60 * 1000;
	private static String TUPLE_COLUMN_NAME_VALUE = "v";
	private static String TUPLE_COLUMN_NAME_KEY = PortablePojoConstants.PROPERTY_NAME_KEY;

	private Space domainSpace;
	private Space memberSpace;
	private Metaspace metaspace;
	private ManagementTableSpaceDefListener managementTableSpaceDefListener;
	private MessageService messageService;
	
	/**
	 * SpaceDefListener for ManagementTable AS spaces
	 */
	class ManagementTableSpaceDefListener implements SpaceDefListener {
		@Override
		public void onDrop(SpaceDef paramSpaceDef) {
			if (ASManagementTable.DATA_SPACE.equals(paramSpaceDef.getName())) {
				domainSpace = null;
			}	
			if (ASManagementTable.MEMBER_SPACE.equals(paramSpaceDef.getName()) 
									|| MEMBER_SPACE2.equals(paramSpaceDef.getName())) {
				memberSpace = null;
			}
		}

		@Override
		public void onDefine(SpaceDef paramSpaceDef) {
			try {
				if (ASManagementTable.MEMBER_SPACE.equals(paramSpaceDef.getName()) 
									|| MEMBER_SPACE2.equals(paramSpaceDef.getName())) {
					memberSpace = metaspace.getSpace(paramSpaceDef.getName(),
							DistributionRole.LEECH);

				} else if (ASManagementTable.DATA_SPACE.equals(paramSpaceDef.getName())) {
					domainSpace = metaspace.getSpace(paramSpaceDef.getName(),
							DistributionRole.LEECH);
				}
			} catch (ASException ex) {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}

		@Override
		public void onAlter(SpaceDef paramSpaceDef1, SpaceDef paramSpaceDef2) {

		}		
	}
	
	@Override
	public void init(Properties propertyParams) {
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.REGISTERED_APPLICATIONASDATAFEEDHANDLERIMPL));
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	synchronized public void connect(final Application application) {
		//Start in a separate Thread with Retry task, so that the main thread is not blocked
		Properties configuration = BEMMServiceProviderManager.getInstance().getConfiguration();
		int retryInterval = Integer.parseInt(ConfigProperty.BE_TEA_AGENT_CLUSTER_AS_CONN_RETRY_INTERVAL.getValue(configuration).toString());  
		Runnable runnableRetryTask = new IdempotentRunnableRetryTask(new ASClusterConnectionTask(application), Integer.MAX_VALUE, retryInterval);
		Thread connThread = new Thread(runnableRetryTask, "teagent-cluster-connection-" + application.getName());
		connThread.setDaemon(true);
		connThread.start();
	}

	@Override
	synchronized public Application getTopologyData(Application application) {

		Application applicationTopology = null;
		Browser browser = null;
		try {
			if (null != memberSpace) {
				try {
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_BROWSING_DOMAIN_MEMBER_METASPACE, application.getClusterName(), application.getName()));
					browser = memberSpace.browse(BrowserDef.BrowserType.GET,
							BrowserDef.create().setTimeScope(BrowserDef.TimeScope.SNAPSHOT));
				} catch (ASException e) {
					LOGGER.log(Level.WARN, e.getLocalizedMessage());
				}
				if (null != browser) {
					applicationTopology = BEMMModelFactoryImpl.getInstance().getApplication();
					Map<String, String> memberSpace = new HashMap<String, String>();

					// Create the map of domain key-value
					while (0 != browser.size()) {
						Tuple tuple = browser.next();
						memberSpace.put(tuple.getString("id"), tuple.getString("v"));
					}
					for (Entry<String, String> entry : memberSpace.entrySet()) {
						String engineName = null;
						String key = entry.getKey();
						String value = entry.getValue();

						String processId = null;
						String hostName = null;
						if (null != value && !value.trim().isEmpty()) {
							processId = value.split(Constants.ATR)[0].trim();
							hostName = value.split(Constants.ATR)[1].trim();
						}
						if (null != key && !key.trim().isEmpty()) {
							engineName = key.replaceFirst(hostName, "").replaceFirst(Constants.DOT, "").trim();
						}
						List<Domain> domains = getDomain(value);
						setClustername(applicationTopology, domains.iterator().next());
						for (Domain domain : domains) {							
							FQName fqName = domain.safeGet(DomainKey.FQ_NAME);
							String[] componentNames = fqName.getComponentNames();
							if (null != componentNames) {
								switch (componentNames.length) {
								case 4: {
									String hostAddress = domain.safeGet(DomainKey.HOST_IP_ADDRESS).toString().trim();
									Host host = getHost(hostAddress.trim(), applicationTopology);
									boolean addHost = false;
									if (null == host) {										
										String hostFqName = componentNames[1].substring(componentNames[1].indexOf(Constants.ATR) + 1);
										host = BEMMModelFactoryImpl.getInstance().getHost();
										MasterHost masterHost= new MasterHostImpl();
										masterHost.setHostIp(hostAddress);
										masterHost.setHostName(hostFqName);
										masterHost.setMachineName(hostFqName);
										host.setMasterHost(masterHost);
										addHost = true;
									}
									ServiceInstance instance = getServiceInstance(host, processId);
									boolean addInstance = false;
									if (null == instance) {
										addInstance = true;
										instance = BEMMModelFactoryImpl.getInstance().getServiceInstance();
										String jmxProps = domain.safeGet(DomainKey.JMX_PROPS_CSV);
										if (null != jmxProps && !jmxProps.trim().isEmpty() && jmxProps.contains("port")) {
											String[] parts = jmxProps.split(",");
											for (String part : parts) {
												if (null != part && part.contains("port")) {
													String port = part.replace("port=", "").trim();
													instance.setJmxPort(Integer.parseInt(port));
												}
											}
										}
										instance.setProcessId(processId);
										instance.setName(engineName);

									}

									String agentName = componentNames[2].trim();
									String agentId = componentNames[3].trim();
									boolean addAgent = false;
									Agent agent = getAgent(instance, agentName);
									if (null == agent) {
										agent = BEMMModelFactoryImpl.getInstance().getAgent();
										agent.setAgentId(Integer.parseInt(agentId));
										String description = domain.safeGet(DomainKey.DESCRIPTION_CSV);
										if (null != description && !description.trim().isEmpty()) {
											if (description.contains("nodeType")) {
												String nodeType = description.replace("nodeType=", "").trim();
												if ("CACHESERVER".equals(nodeType)) {
													agent.setAgentType(AgentType.CACHE);
												} else if ("INFERENCE".equals(nodeType)) {
													agent.setAgentType(AgentType.INFERENCE);
												} else if ("QUERY".equals(nodeType)) {
													agent.setAgentType(AgentType.QUERY);
												} else if ("PROCESS".equals(nodeType)) {
													agent.setAgentType(AgentType.PROCESS);
												} else {
													agent.setAgentType(AgentType.DASHBOARD);
												}
											}
										}

										agent.setAgentName(agentName);
										addAgent = true;
									}
									if (addAgent) {
										agent.setInstance(instance);
										instance.addAgent(agent);

									}
									if (addInstance) {
										instance.setHost(host);
										host.addInstance(instance);
									}
									if (addHost) {
										host.setApplication(applicationTopology);
										applicationTopology.addHost(host);
									}
								}
									break;
								default:
									break;
								}
							}
						}
					}
				}
			}

		} catch (ObjectCreationException ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} catch (ASException ex) {
			LOGGER.log(Level.WARN, ex, ex.getMessage());
		} finally {
			if (null != browser)
				try {
					browser.stop();
				} catch (ASException ex) {
					LOGGER.log(Level.WARN, ex, ex.getMessage());
				}
		}
		return applicationTopology;

	}

	@Override
	synchronized public void disconnect(Application application) {
		try {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DISCONNECTING_METASPACE, application.getClusterName()));
			if (memberSpace != null) {
				memberSpace.close();
				memberSpace = null;
			}	
			if (domainSpace != null) {
				domainSpace.close();
				domainSpace = null;
			}
			if (managementTableSpaceDefListener != null) {
				metaspace.stopListener(managementTableSpaceDefListener);
				managementTableSpaceDefListener = null;
			}
			if (metaspace != null) {
				metaspace.close();
				metaspace = null;
			}
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.DISCONNECTED_METASPACE, application.getClusterName()));
		} catch (ASException ex) {
			LOGGER.log(Level.WARN, ex, ex.getMessage());
		}
	}

	/**
	 * @param spaceName
	 * @return
	 */
	private KeyValueTupleAdaptor<FQName, Domain> getTupleAdaptor(String spaceName) {
		Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
		String backupCountStr = "1";
		if (cluster != null) {
			GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
			backupCountStr = gVs.substituteVariables(
					System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1")).toString();
		} else {
			backupCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
		}
		SpaceMapCreator.Parameters<FQName, Domain> memberParameters = new SpaceMapCreator.Parameters<FQName, Domain>()
				.setSpaceName(spaceName).setRole(DistributionRole.LEECH)
				.setDistributionPolicy(DistributionPolicy.DISTRIBUTED).setKeyClass(FQName.class)
				.setValueClass(Domain.class).setTupleCodec(new SerializableTupleCodec()).setTtl(EXPIRY_MILLIS)
				.setReplicationCount(Integer.parseInt(backupCountStr));
		KeyValueTupleAdaptor<FQName, Domain> tupleAdaptor = prepareFieldsAndCodec(memberParameters);
		return tupleAdaptor;
	}

	/**
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <K, V> KeyValueTupleAdaptor<K, V> prepareFieldsAndCodec(Parameters<K, V> parameters) {
		String keyName = parameters.getKeyColumnName() == null ? TUPLE_COLUMN_NAME_KEY : parameters.getKeyColumnName();
		DataType keyType = DataTypeRefMap.mapToDataType(parameters.getKeyClass());
		FieldDef nameField = FieldDef.create(keyName, keyType.getFieldType());

		TupleCodec tupleCodec = parameters.getTupleCodec();
		KeyValueTupleAdaptor<K, V> tupleAdaptor = null;
		List<FieldDef> fieldDefs = parameters.getExplicitFieldDefs();
		if (fieldDefs == null) {
			String valueName = parameters.getValueColumnName() == null ? TUPLE_COLUMN_NAME_VALUE : parameters
					.getValueColumnName();

			DataType valueType = DataTypeRefMap.mapToDataType(parameters.getValueClass());
			FieldDef valueField = FieldDef.create(valueName, valueType.getFieldType());
			valueField.setNullable(true);
			// spacedef.putFieldDef(valueField);

			if (tupleCodec instanceof KeyValueTupleAdaptor) {
				tupleAdaptor = (KeyValueTupleAdaptor<K, V>) tupleCodec;
			} else {
				tupleAdaptor = new KeyValueTupleAdaptor<K, V>(nameField.getName(), keyType, valueField.getName(),
						valueType, tupleCodec);
			}
		} else {
			tupleAdaptor = (KeyValueTupleAdaptor<K, V>) tupleCodec;
		}
		return tupleAdaptor;
	}

	/**
	 * @param instance
	 * @param agentName
	 * @return
	 */
	private Agent getAgent(ServiceInstance instance, String agentName) {
		Agent agent = null;
		for (Agent agent2 : instance.getAgents()) {
			if (null != agent2 && agentName.equals(agent2.getAgentName())) {
				agent = agent2;
				break;
			}
		}
		return agent;
	}

	/**
	 * @param host
	 * @param processId
	 * @return
	 */
	private ServiceInstance getServiceInstance(Host host, String processId) {
		ServiceInstance instance = null;
		for (ServiceInstance serviceInstance : host.getInstances()) {
			if (null != serviceInstance && processId.equals(serviceInstance.getProcessId())) {
				instance = serviceInstance;
				break;
			}
		}
		return instance;
	}

	private Host getHost(String hostAddress, Application applicationTopology) {
		Host host = null;
		List<Host> applicationHosts = applicationTopology.getHosts();
		if (null != applicationHosts && !applicationHosts.isEmpty()) {
			for (Host applicationHost : applicationHosts) {
				if (null != applicationHost) {
					if (null != hostAddress && hostAddress.equals(applicationHost.getHostIp())) {
						host = applicationHost;
						break;
					}
				}
			}
		}
		return host;
	}

	private List<Domain> getDomain(String value) {
		KeyValueTupleAdaptor<FQName, Domain> tupleAdaptor = getTupleAdaptor(memberSpace.getName());
		List<Domain> domains = new ArrayList<Domain>();
		Browser browser = null;
		try {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.BROWSING_DOMAIN_DATA_METASPACE, memberSpace.getMetaspaceName()));
			browser = domainSpace.browse(BrowserDef.BrowserType.GET,
					BrowserDef.create().setTimeScope(BrowserDef.TimeScope.SNAPSHOT));
			while (0 != browser.size()) {
				Tuple tuple = browser.next();
				Domain domain = tupleAdaptor.extractValue(tuple);
				if (value.trim().equals(domain.safeGet(DomainKey.HOST_PROCESS_ID))) {
					domains.add(domain);
				}
			}
		} catch (ASException ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			if (null != browser)
				try {
					browser.stop();
				} catch (ASException ex) {
					LOGGER.log(Level.WARN, ex, ex.getMessage());
				}
		}
		return domains;
	}

	/**
	 * Set the cluster name
	 * 
	 * @param applicationTopology
	 * @param domain
	 */
	private void setClustername(Application applicationTopology, Domain domain) {
		FQName fqName = domain.safeGet(DomainKey.FQ_NAME);
		String[] componentNames = fqName.getComponentNames();
		if (null != componentNames) {
			applicationTopology.setClusterName(componentNames[0].trim());
		}
	}

	
	private class ASClusterConnectionTask implements Task {

		private Application application = null;
		
		ASClusterConnectionTask(Application application) {
			this.application = application;
		}
		
		@Override
		public Object perform() throws Throwable {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_CONNECTING_AS_CLUSTER, application.getName(), application.getClusterName()));
			metaspace = ASCommon.getMetaspace(application.getClusterName());			
			if (null == metaspace) {
				MemberDef memberDef = MemberDef.create().setDiscovery(application.getDiscoveryURL()).setListen(application.getListenURL());
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_CONNECTING_METASPACE, application.getClusterName(), application.getName()));
				metaspace = Metaspace.connect(application.getClusterName(), memberDef);
			}
	
			if (null != metaspace) {
				managementTableSpaceDefListener = new ManagementTableSpaceDefListener();
				metaspace.listenSpaceDefs(managementTableSpaceDefListener);
			}
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.APPLICATION_CONNECTED_AS_CLUSTER_SUCCESS, application.getName(), application.getClusterName()));
			return true;
		}

		@Override
		public String getTaskName() {
			return "AS-Cluster-Connection";
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			// place holder to revisit
			
		}
		
	}
}
