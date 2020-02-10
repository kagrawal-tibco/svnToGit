package com.tibco.cep.bemm.model.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.common.audit.AuditRecord;
import com.tibco.cep.bemm.common.audit.AuditRecords;
import com.tibco.cep.bemm.common.enums.BETEAAgentAction;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceSaveException;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschConnectionException;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.GroupOperationUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.AbstractApplicationBuilder;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.persistence.service.exception.BEHostTRASaveException;
import com.tibco.cep.bemm.persistence.topology.model.Cluster;
import com.tibco.cep.bemm.persistence.topology.model.Clusters;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentMapping;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentMappings;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentUnit;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentUnits;
import com.tibco.cep.bemm.persistence.topology.model.HostResource;
import com.tibco.cep.bemm.persistence.topology.model.HostResources;
import com.tibco.cep.bemm.persistence.topology.model.ProcessingUnitConfig;
import com.tibco.cep.bemm.persistence.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.bemm.persistence.topology.model.Site;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class ApplicationBuilderImpl extends AbstractApplicationBuilder<Site> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ApplicationBuilderImpl.class);

	@Override
	public Application newApplication() throws ObjectCreationException {
		return beMMModelFactory.getApplication();
	}

	@Override
	public void enrichTopologyAndClusterConfigData(Application application, Site site, ClusterConfig clusterConfig,
			String loggedInUser, boolean isImportApplication) throws Exception {
		enrichClusterConfigData(application, clusterConfig);
		enrichTopologyData(application, site, loggedInUser, isImportApplication);
	}

	@Override
	public Application enrichTopologyData(Application application, Site site, String loggedInUser,
			boolean isImportApplication) throws Exception {
		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
				.getMessage(MessageKey.INSIDE_ENRICHTOPOLOGYDATA));

		if (null != site) {
			application.setName(site.getName());
			Set<String> profiles = dataStoreService.loadProfiles(application.getName());
			for (String profile : profiles) {
				application.addProfile(profile);
			}
			DeploymentVariables deploymentVariables = dataStoreService.fetchApplicationConfig(application.getName(),
					DeploymentVariableType.APPLICATION_CONFIG);
			if (null != deploymentVariables) {
				NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
				if (null != nameValuePairs) {
					List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();
					if (null != nameValues) {
						for (NameValuePair nameValuePair : nameValues) {
							if (null != nameValuePair && "defaultProfile".equals(nameValuePair.getName())) {
								application.setDefaultProfile(nameValuePair.getValue());
								break;
							}
						}
					}
				}
			}
			if (null != application.getDefaultProfile() && !application.getDefaultProfile().trim().isEmpty()) {
				Map<String, Map<String, String>> props = dataStoreService.loadProfile(application.getName(),
						application.getDefaultProfile());
				application.setBEProperties(props.get("BEPROPS"));
				application.setGlobalVariables(props.get("GV"));
				application.setSystemProperties(props.get("SYSTEMPROPS"));
			}

			HostResources hostResources = site.getHostResources();
			if (null != hostResources) {
				List<HostResource> hostResourceList = hostResources.getHostResource();
				if (null != hostResourceList && !hostResourceList.isEmpty()) {

					for (HostResource hostResource : hostResourceList) {
						if (null != hostResource) {
							Host host = addApplicationHost(application, isImportApplication, hostResource);
							Clusters clusters = site.getClusters();
							if (clusters != null) {
								for (Cluster cluster : clusters.getCluster()) {
									if (null == application.getClusterName()
											|| application.getClusterName().trim().isEmpty()) {
										application.setClusterName(cluster.getName());
									}
									DeploymentMappings mappings = cluster.getDeploymentMappings();
									for (DeploymentMapping deploymentMapping : mappings.getDeploymentMapping()) {
										if (deploymentMapping != null) {
											HostResource hostRef = (HostResource) deploymentMapping.getHostRef();
											if (hostRef != null && hostRef.getId().equals(host.getHostId())) {
												DeploymentUnit deploymentUnitRef = (DeploymentUnit) deploymentMapping
														.getDeploymentUnitRef();
												DeploymentUnit deploymentUnit = getDeploymentUnit(cluster,
														(DeploymentUnit) deploymentUnitRef);
												if (null != deploymentUnit) {
													ProcessingUnitsConfig processingUnitsConfig = deploymentUnit
															.getProcessingUnitsConfig();
													for (ProcessingUnitConfig processingUnitConfig : processingUnitsConfig
															.getProcessingUnitConfig()) {
														if (processingUnitConfig.getBeId() == null
																|| processingUnitConfig.getBeId().trim().isEmpty()) {
															if (null != host.getBE() && host.getBE().size() == 1) {
																processingUnitConfig
																		.setBeId(host.getBE().get(0).getId());
															}
														}
														addServiceInstance(application, host, deploymentUnitRef,
																deploymentUnit, processingUnitConfig, loggedInUser);
													}

												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		boolean appNeedsDeploy = false;
		boolean appNeedsReDeploy = false;

		for (Host host : application.getHosts()) {
			boolean hostNeedsDeploy = false;
			boolean hostNeedsReDeploy = false;
			for (ServiceInstance instance : host.getInstances()) {
				if (instance.getNeedReDeployment()) {
					hostNeedsReDeploy = true;
				}
				if (instance.getNeedsDeployment()) {
					hostNeedsDeploy = true;
					break;
				}
			}
			if (hostNeedsReDeploy) {
				appNeedsReDeploy = true;
			}
			if (hostNeedsDeploy) {
				appNeedsDeploy = true;
			}

			if (!hostNeedsReDeploy && !hostNeedsDeploy) {
				host.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
				host.setDeployed(true);
			}

			if (hostNeedsReDeploy || hostNeedsDeploy)
				host.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
		}
		if (appNeedsReDeploy || appNeedsDeploy) {
			application.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());
		}

		if (!appNeedsReDeploy && !appNeedsDeploy) {
			application.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
			application.setDeployed(true);
			application.setStatus(BETeaAgentStatus.STOPPED.getStatus());
		}

		return application;
	}

	/**
	 * Create and Add the Service instance in host
	 * 
	 * @param application
	 * @param host
	 * @param deploymentUnitRef
	 * @param deploymentUnit
	 * @param processingUnitConfig
	 * @param loggedInUser
	 * @param globalVariableDescriptors
	 * @param cddProperties
	 * @param dataStoreService
	 * @throws Exception
	 */
	private void addServiceInstance(Application application, Host host, DeploymentUnit deploymentUnitRef,
			DeploymentUnit deploymentUnit, ProcessingUnitConfig processingUnitConfig, String loggedInUser)
			throws Exception {
		String deploymentPath = ManagementUtil.getDir(deploymentUnit.getDeployedFiles().getCddDeployed());
		ServiceInstance instance = beMMModelFactory.getServiceInstance();
		instance.setDeploymentPath(deploymentPath);
		instance.setDuId(application.getNextDeploymentUnitId());
		instance.setHost(host);
		instance.setJmxPort(Integer.parseInt(processingUnitConfig.getJmxport()));
		instance.setName(processingUnitConfig.getId());
		instance.setPredefined(true);
		instance.setBeId(processingUnitConfig.getBeId());
		if (!application.isMonitorableOnly())
			instance.setPuId(processingUnitConfig.getPuid());
		instance.setUseAsEngineName(processingUnitConfig.isUseAsEngineName());
		instance.setDefaultProfile(application.getDefaultProfile());
		host.addInstance(instance);
		if (!application.isMonitorableOnly())
			host.getMasterHost().addJMXPort(instance.getJmxPort(), true);
		instance.setJmxUserName(processingUnitConfig.getJmxUserName());
		instance.setJmxPassword(processingUnitConfig.getJmxPassword());
		ProcessingUnit procUnit = application.getProcessingUnit(processingUnitConfig.getPuid());
		if (!application.isMonitorableOnly()) {
			if (procUnit != null) {

				boolean hasInferneceAgent = false;
				for (com.tibco.cep.bemm.model.AgentConfig agentConfig : procUnit.getAgents()) {
					Agent agent = beMMModelFactory.getAgent();
					agent.setAgentName(agentConfig.getAgentName());
					agent.setAgentType(agentConfig.getAgentType());
					agent.setInstance(instance);
					instance.addAgent(agent);
					if (!hasInferneceAgent
							&& AgentType.INFERENCE.getType().equalsIgnoreCase(agentConfig.getAgentType().getType())) {
						hasInferneceAgent = true;
					}
				}
				instance.setHotDeployable(procUnit.isHotDeploy());
				if (null != application.getRuleTemplateDeployDir()
						&& !application.getRuleTemplateDeployDir().trim().isEmpty()) {
					instance.setRuleTemplateDeployDir(application.getRuleTemplateDeployDir().trim());
				}
				ProcessingUnit processingUnit = application.getProcessingUnit(instance.getPuId());
				if (null != processingUnit) {
					Map<String, String> properties = processingUnit.getProperties();
					if (null != properties && !properties.isEmpty()) {
						Object ruleTempleDeployDir = properties
								.get(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
						if (null != ruleTempleDeployDir && !ruleTempleDeployDir.toString().isEmpty()) {
							instance.setRuleTemplateDeployDir(ruleTempleDeployDir.toString());
						}
					}
				}


				loadDeploymentVariables(instance, DeploymentVariableType.BE_PROPERTIES);
				DeploymentVariables beProps = instance.getBEProperties();
				if (beProps != null) {
					NameValuePairs bePropsPairs = beProps.getNameValuePairs();
					List<NameValuePair> bePropsNvList = bePropsPairs.getNameValuePair();
					for (NameValuePair bePropsNvPair : bePropsNvList) {
						String effectiveValue = null;
						if (bePropsNvPair.getName()
								.equals(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName())) {
							effectiveValue = GroupOperationUtil.getEffectiveValue(bePropsNvPair);
							if (null != effectiveValue && !effectiveValue.toString().isEmpty()) {
								instance.setRuleTemplateDeployDir(effectiveValue.toString());
							}
						} else if (bePropsNvPair.getName()
								.equals(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName())) {
							effectiveValue = GroupOperationUtil.getEffectiveValue(bePropsNvPair);
							if (null != effectiveValue && !effectiveValue.toString().isEmpty()) {
								instance.setExternalClassesPath(effectiveValue.toString());
							}
						}
					}
				}			

				if (hasInferneceAgent) {
					instance.setRuleTemplateDeploy(null != instance.getRuleTemplateDeployDir()
							&& !instance.getRuleTemplateDeployDir().trim().isEmpty() ? true : false);

					instance.setDeployClasses(((null != application.getExternalClassesPath()
							&& !application.getExternalClassesPath().trim().isEmpty())
							|| (null != instance.getExternalClassesPath()
									&& !instance.getExternalClassesPath().trim().isEmpty())) ? true : false);
				}
			} else {
				String message = BEMMServiceProviderManager.getInstance().getMessageService().getMessage(
						MessageKey.INSTANCE_PU_UNDEFINED_ERROR, processingUnitConfig.getPuid(), instance.getName());
				throw new BEServiceInstanceSaveException(message);
			}
			loadDeploymentVariables(instance, DeploymentVariableType.INSTANCE_CONFIG);
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecord auditRecord = getAuditRecord(BETEAAgentAction.CREATE_INSTANCE, BEMMServiceProviderManager
						.getInstance().getMessageService().getMessage(MessageKey.INSTANCE_CREATED_MESSAGE),
						loggedInUser);
				loadAuditRecords(instance, application, auditRecord);
			}
		}
	}

	/**
	 * Get Audit records
	 * 
	 * @param agentAction
	 * @param description
	 * @param performedBy
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private AuditRecord getAuditRecord(BETEAAgentAction agentAction, String description, String performedBy)
			throws DatatypeConfigurationException {
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setAction(agentAction.getAction());
		auditRecord.setDescription(description);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		auditRecord.setPerformedOn(calendar);
		auditRecord.setPeformedBy(performedBy);
		auditRecord.setEntity("Instance");
		return auditRecord;
	}

	/**
	 * Load the audit action
	 * 
	 * @param instance
	 * @param application
	 * @param auditRecord
	 */
	private void loadAuditRecords(ServiceInstance instance, Application application, AuditRecord auditRecord) {
		try {
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				AuditRecords auditRecords = dataStoreService.fetchAuditRecords(application.getName(),
						instance.getName());
				if (null == auditRecords) {
					auditRecords = new AuditRecords();
					auditRecords.getAuditRecord().add(auditRecord);
					storeAuditRecords(instance, application, auditRecords);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void storeAuditRecords(ServiceInstance instance, Application application, AuditRecords auditRecords) {
		try {
			if (Boolean.valueOf(ConfigProperty.BE_TEA_AGENT_AUDIT_ENABLED.getDefaultValue())) {
				dataStoreService.storeAuditRecords(application.getName(), instance.getName(), auditRecords);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadDeploymentVariables(ServiceInstance instance, DeploymentVariableType deploymentVariableType)
			throws Exception {

		if (deploymentVariableType.equals(DeploymentVariableType.INSTANCE_CONFIG)) {
			DeploymentVariables deploymentVariables = dataStoreService.fetchDeploymentVaribles(
					instance.getHost().getApplication().getName(), instance, deploymentVariableType);
			if (null != deploymentVariables) {
				NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
				if (null != nameValuePairs) {
					List<NameValuePair> valuePairs = nameValuePairs.getNameValuePair();
					if (null != valuePairs) {
						for (NameValuePair nameValuePair : valuePairs) {
							if (Constants.DEPLOYMENT_STATUS.equals(nameValuePair.getName())) {
								for (BETeaAgentStatus agentStatus : BETeaAgentStatus.values()) {
									if (agentStatus.getStatus().equals(nameValuePair.getValue())) {
										instance.setDeploymentStatus(agentStatus.getStatus());
										if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
												.equals(instance.getDeploymentStatus()))
											instance.setStatus(BETeaAgentStatus.STOPPED.getStatus());
										break;
									}
								}
							} else if (Constants.DESCRIPTION.equals(nameValuePair.getName())) {
								instance.setDeploymentDescription(nameValuePair.getValue());
							} else if (Constants.DEPLOYMENT.equals(nameValuePair.getName())) {
								instance.setDeployed(Boolean.parseBoolean(nameValuePair.getValue()));
							} else if (Constants.LAST_DEPLOYEMNT_TIME.equals(nameValuePair.getName())) {
								try {
									instance.setLastDeploymentTime(Long.valueOf(nameValuePair.getValue()));
								} catch (NumberFormatException e) {
									// do nothing - This would occur for 5.3
									// agent repo used with 5.4 agent. Can be
									// ignored.
								}
							} else if (Constants.HOT_DEPLOYABLE.equals(nameValuePair.getName())) {
								instance.setHotDeployable(Boolean.valueOf(nameValuePair.getValue()));
							} else if (Constants.INSTANCE_START_TIME.equals(nameValuePair.getName())) {
								instance.setStartingTime(Long.valueOf(nameValuePair.getValue()));
							} else if (Constants.DEFAULT_PROFILE.equals(nameValuePair.getName())) {
								String value = nameValuePair.getValue();
								if (null != value && !value.trim().isEmpty()) {
									instance.setDefaultProfile(value);
								}
							}
						}
					}
				}
			} else {
				deploymentVariables = new DeploymentVariables();
				deploymentVariables.setName(instance.getName());
				deploymentVariables.setType(DeploymentVariableType.INSTANCE_CONFIG);
				NameValuePairs nameValuePairs = new NameValuePairs();

				NameValuePair deployed = new NameValuePair();
				deployed.setName(Constants.DEPLOYMENT);
				deployed.setValue(String.valueOf(instance.getDeployed()));
				nameValuePairs.getNameValuePair().add(deployed);

				NameValuePair deploymentStatus = new NameValuePair();
				deploymentStatus.setName(Constants.DEPLOYMENT_STATUS);
				deploymentStatus.setValue(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
				nameValuePairs.getNameValuePair().add(deploymentStatus);

				NameValuePair description = new NameValuePair();
				description.setName(Constants.DESCRIPTION);
				description.setValue(BEMMServiceProviderManager.getInstance().getMessageService()
						.getMessage(MessageKey.INSTANCE_CREATED_MESSAGE));
				nameValuePairs.getNameValuePair().add(description);
				if (null != instance.getDefaultProfile() && !instance.getDefaultProfile().isEmpty()) {
					NameValuePair defaultProfile = new NameValuePair();
					defaultProfile.setName(Constants.DEFAULT_PROFILE);
					defaultProfile.setValue(instance.getDefaultProfile());
					nameValuePairs.getNameValuePair().add(defaultProfile);
				}

				deploymentVariables.setNameValuePairs(nameValuePairs);
				instance.setDeployed(false);
				instance.setDeploymentDescription(BEMMServiceProviderManager.getInstance().getMessageService()
						.getMessage(MessageKey.INSTANCE_CREATED_MESSAGE));
				instance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
				dataStoreService.storeDeploymentVariables(instance.getHost().getApplication().getName(),
						instance.getName(), deploymentVariables);

			}
		} else if (deploymentVariableType.equals(DeploymentVariableType.BE_PROPERTIES)) {
			DeploymentVariables deploymentVariables = dataStoreService.fetchDeploymentVaribles(
					instance.getHost().getApplication().getName(), instance, deploymentVariableType);
			if (null != deploymentVariables) {
				instance.setBEProperties(deploymentVariables);
			}

		}

	}

	/**
	 * This method is used to get the deployment unit of given id from cluster.
	 * 
	 * @param cluster
	 *            - Cluster object
	 * @param deploymentUnitRef
	 *            -Deployment unit id.
	 * @return DeployementUnit object.
	 */
	private DeploymentUnit getDeploymentUnit(Cluster cluster, DeploymentUnit deploymentUnitRef) {
		DeploymentUnit deploymentUnit = null;
		DeploymentUnits deploymentUnits = cluster.getDeploymentUnits();
		if (null != deploymentUnits) {
			for (DeploymentUnit du : deploymentUnits.getDeploymentUnit()) {
				if (null != du && null != deploymentUnitRef) {
					if (deploymentUnitRef.getId().equals(du.getId())) {
						deploymentUnit = du;
						break;
					}
				}
			}
		}
		return deploymentUnit;
	}

	/**
	 * Create the master host object
	 * 
	 * @return
	 * @throws ObjectCreationException
	 */
	private Host createApplicationHost() throws ObjectCreationException {
		return beMMModelFactory.getHost();
	}

	/**
	 * Resolve master host
	 * 
	 * @param host
	 *            - Application host
	 * @param hostResource
	 *            - Site topology host
	 * @param isImportApplication
	 *            - Application import or not
	 * @throws ObjectCreationException
	 */
	private void resolveMasterHost(Host host, HostResource hostResource, boolean isImportApplication)
			throws ObjectCreationException {
		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
				.getMessage(MessageKey.RESOLVING_MASTER_HOST, hostResource.getHostname()));

		BEMasterHostManagementService masterHostManagementService = BEMMServiceProviderManager.getInstance()
				.getMasterHostManagementService();

		MasterHost masterHost = null;
		if (isImportApplication) { // Import application flow
			masterHost = masterHostManagementService.getMasterHostByHostResource(hostResource);
		} else { // Load application flow
			masterHost = masterHostManagementService.getMasterHostByHostId(hostResource.getId());
		}

		// Create host if not exist
		try {
			if (null == masterHost) {
				masterHost = masterHostManagementService.createMasterHost(hostResource);
			} else
				masterHostManagementService.storeTRA(masterHost);
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
					.getMessage(MessageKey.RESOLVED_MASTER_HOST, hostResource.getHostname()));
		} catch (JschAuthenticationException | JschConnectionException | BEHostTRASaveException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
		host.setMasterHost(masterHost);

	}

	/**
	 * Add host to the application and return the host
	 * 
	 * @param application
	 * @param isImportApplication
	 * @param hostResource
	 * @return
	 * @throws ObjectCreationException
	 */
	private Host addApplicationHost(Application application, boolean isImportApplication, HostResource hostResource)
			throws ObjectCreationException {
		Host host = createApplicationHost();
		resolveMasterHost(host, hostResource, isImportApplication);
		hostResource.setHostname(host.getHostName());
		hostResource.setIp(host.getHostIp());
		hostResource.setId(host.getHostId());
		host.setApplication(application);
		application.addHost(host);
		return host;
	}

}
