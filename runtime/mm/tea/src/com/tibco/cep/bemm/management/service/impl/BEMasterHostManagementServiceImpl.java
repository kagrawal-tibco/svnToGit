package com.tibco.cep.bemm.management.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataSource;

import org.apache.commons.lang3.RandomStringUtils;

import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.job.BETeaAuthenticateMasterHostJob;
import com.tibco.cep.bemm.common.job.BETeaDeleteMasterHostJob;
import com.tibco.cep.bemm.common.job.BETeaDownloadRemoteFileJob;
import com.tibco.cep.bemm.common.job.BETeaRemoteHostOSDetectJob;
import com.tibco.cep.bemm.common.job.BETeaUploadExternalJarsJob;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.MasterHostGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.exception.BEMasterHostAddException;
import com.tibco.cep.bemm.management.exception.BEMasterHostDeleteException;
import com.tibco.cep.bemm.management.exception.BEMasterHostEditException;
import com.tibco.cep.bemm.management.exception.BEMasterHostSaveException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEUploadFileException;
import com.tibco.cep.bemm.management.exception.InvalidParameterException;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;
import com.tibco.cep.bemm.management.exception.JschConnectionException;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEHomeDiscoveryService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.management.util.MasterHostConvertor;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.MasterHostImpl;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.service.exception.BEHostTRASaveException;
import com.tibco.cep.bemm.persistence.topology.model.HostResource;
import com.tibco.cep.bemm.persistence.topology.model.Ssh;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.tea.agent.be.util.BEAgentUtil;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * The service implementation for master host related functionality
 * 
 * @author dijadhav
 *
 */
public class BEMasterHostManagementServiceImpl extends AbstractBETeaManagementServiceImpl
		implements BEMasterHostManagementService {
	private Properties configuration;
	private BEApplicationsManagementDataStoreService<?> dataStoreService;
	private BEApplicationsManagementService applicationsManagementService;
	private MessageService messageService;
	private ValidationService validationService;
	private Map<String, MasterHost> masterHostMap = new ConcurrentHashMap<String, MasterHost>();
	private LockManager lockManager;
	private BEHomeDiscoveryService beHomeDiscoveryService;
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEMasterHostManagementServiceImpl.class);

	@Override
	public void init(Properties configuration) throws ServiceInitializationException, ObjectCreationException {
		LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
				.getMessage(MessageKey.INITIALIZING_MASTER_HOST_REPOSITORY));

		this.configuration = configuration;

		dataStoreService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		validationService = BEMMServiceProviderManager.getInstance().getValidationService();
		beHomeDiscoveryService = BEMMServiceProviderManager.getInstance().getBEHomeDiscoveryService();
		applicationsManagementService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService();
		this.poolService = BEMMServiceProviderManager.getInstance().getGroupOpExecutorService();
		loadAll();
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INITIALIZED_MASTER_HOST_REPOSITORY));

	}

	/**
	 * Load all master host
	 */
	private void loadAll() {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.LOADING_EXISTING_HOSTS_CONFIGURATION));
		masterHostMap = dataStoreService.fetchAllMasterHost();
		if (null == masterHostMap) {
			masterHostMap = new ConcurrentHashMap<String, MasterHost>();
		}
		authenticateHosts();
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.LOADED_EXISTING_HOSTS_CONFIGURATION));
	}

	@Override
	public void setDataStoreService(BEApplicationsManagementDataStoreService<?> dataStoreService) {
		this.dataStoreService = dataStoreService;
	}

	@Override
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

	/**
	 * @param applicationsManagementService
	 *            the applicationsManagementService to set
	 */
	@Override
	public void setApplicationsManagementService(BEApplicationsManagementService applicationsManagementService) {
		this.applicationsManagementService = applicationsManagementService;
	}

	@Override
	public Map<String, MasterHost> getAllMasterHost() {
		return masterHostMap;
	}

	@Override
	public String addMasterHost(String hostName, String ipAddress, String hostOS, List<BE> beServiceModels,
			String userName, String password, int sshPort, String deploymentPath, String loggedInUser)
			throws BEMasterHostAddException {

		// If host with given name already exist then throw exception
		if (isHostExistByName(hostName)) {
			throw new BEMasterHostAddException(
					messageService.getMessage(MessageKey.HOST_DUPLICATE_NAME_ERROR_MESSAGE, hostName));
		}

		boolean isMonitorable = Boolean
				.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));

		if (!isMonitorable && !validationService.isNotNullAndEmpty(userName)) {
			throw new BEMasterHostAddException("User name is required");
		}
		/*
		 * if (null == beServiceModels || beServiceModels.isEmpty()) { throw new
		 * BEMasterHostAddException("BE Home/TRA Path is required"); } boolean
		 * beHomeIsMentioned = false; for (BE be : beServiceModels) { if (null
		 * != be) { if (null != be.getBeHome() &&
		 * !be.getBeHome().trim().isEmpty() && null != be.getBeTra() &&
		 * !be.getBeTra().trim().isEmpty()) { beHomeIsMentioned = true; break; }
		 * } } if (!beHomeIsMentioned) { throw new BEMasterHostAddException(
		 * "BE Home/TRA Path is required"); }
		 */
		// Get next host id.
		String hostId = ManagementUtil.getNextMasterHostId(masterHostMap);
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.HOST_ID, hostId));
		try {

			MasterHost host = createHost(hostName, ipAddress, hostOS, beServiceModels, userName, password, sshPort,
					deploymentPath, hostId, null);
			try {
				if (!isMonitorable)
					storeTRA(host);
				else
					host.setAuthenticated(true);
				masterHostMap.put(hostId, host);
				dataStoreService.storeMasterHostTopology(masterHostMap);
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.NEW_HOST_ADDED_SUCCESS));

				return (messageService.getMessage(MessageKey.HOST_CREATE_SUCCESS_MESSAGE, hostName));
			} catch (BEHostTRASaveException e) {
				LOGGER.log(Level.DEBUG, e.getMessage());
			} catch (JschAuthenticationException e) {
				throw new BEMasterHostAddException(
						messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, hostName), e);
			} catch (JschConnectionException e) {
				throw new BEMasterHostAddException(
						messageService.getMessage(MessageKey.HOST_CONNECTION_ERROR_MESSAGE, hostName), e);
			}

		} catch (BEMasterHostSaveException e) {
			throw new BEMasterHostAddException(
					messageService.getMessage(MessageKey.HOST_CREATE_ERROR_MESSAGE, hostName), e);
		}
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.HOST_CREATE_ERROR_MESSAGE, hostName));

		return messageService.getMessage(MessageKey.HOST_CREATE_ERROR_MESSAGE, hostName);

	}

	@Override
	public String editMasterHost(MasterHost masterHost, String hostName, String ipAddress, String hostOs,
			List<BE> beServiceModels, String userName, String password, int sshPort, String deploymentPath,
			String loggedInUser) throws BEMasterHostEditException {
		try {
			if (null != masterHost) {

				if (isInstancesDeployed(masterHost)) {
					if (isHostExistByName(hostName, masterHost)) {
						throw new BEMasterHostEditException(
								messageService.getMessage(MessageKey.INSTANCES_DEPLOYED_HOST_EDIT_ERROR));
					}
					/*
					 * if (null == beServiceModels || beServiceModels.isEmpty())
					 * { throw new BEMasterHostEditException(
					 * "BE Home/TRA Path is required"); } boolean
					 * beHomeIsMentioned = false; for (BE be : beServiceModels)
					 * { if (null != be) { if (null != be.getBeHome() &&
					 * !be.getBeHome().trim().isEmpty() && null != be.getBeTra()
					 * && !be.getBeTra().trim().isEmpty()) { beHomeIsMentioned =
					 * true; break; } } } if (!beHomeIsMentioned) { throw new
					 * BEMasterHostEditException("BE Home/TRA Path is required"
					 * ); } if
					 * (!masterHost.getOs().trim().equals(hostOs.trim())) {
					 * throw new BEMasterHostEditException(
					 * "Some instances are deployed on this host.Operating system should not be edited"
					 * ); }
					 */
				} else {
					if (isHostExistByName(hostName, masterHost)) {
						throw new BEMasterHostEditException(
								messageService.getMessage(MessageKey.HOST_DUPLICATE_NAME_ERROR_MESSAGE, hostName));
					}
				}
				boolean isMonitorable = Boolean
						.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
								ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));

				if (!isMonitorable && !validationService.isNotNullAndEmpty(userName)) {
					throw new BEMasterHostEditException("User name is required");
				}
				List<BE> beModels = new ArrayList<BE>();
				List<String> ids = new ArrayList<String>();
				if (null != beServiceModels) {
					for (BE be : beServiceModels) {
						if (null != be) {
							if (null != be.getBeHome() && !be.getBeHome().trim().isEmpty() && null != be.getBeTra()
									&& !be.getBeTra().trim().isEmpty()) {
								if (null == be.getId() || be.getId().trim().isEmpty())
									be.setId(RandomStringUtils.randomNumeric(10));
								ids.add(be.getId());
								beModels.add(be);
							}
						}
					}
				}
				masterHost.setBE(beModels);
				masterHost.setHostIp(ipAddress);
				masterHost.setHostName(hostName);
				masterHost.setSshPort(sshPort);
				masterHost.setUserName(userName);
				masterHost.setOs(hostOs);
				masterHost.setDeploymentPath(deploymentPath);
				removeTRA(masterHost, ids);
				if (!ManagementUtil.getDecodedPwd(masterHost.getPassword())
						.equals(ManagementUtil.getDecodedPwd(password)))
					masterHost.setPassword(ManagementUtil.getEncodedPwd(password));

				try {
					storeTRA(masterHost);
				} catch (BEHostTRASaveException e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				} catch (JschAuthenticationException e) {
					throw new BEMasterHostEditException(
							messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, hostName), e);
				} catch (JschConnectionException e) {
					throw new BEMasterHostEditException(
							messageService.getMessage(MessageKey.HOST_CONNECTION_ERROR_MESSAGE, hostName), e);
				}
				masterHostMap.put(masterHost.getHostId(), masterHost);
				dataStoreService.storeMasterHostTopology(masterHostMap);

			}

		} catch (BEMasterHostSaveException e) {
			throw new BEMasterHostEditException(
					(messageService.getMessage(MessageKey.HOST_EDIT_ERROR_MESSAGE, masterHost.getHostName())), e);
		}
		return (messageService.getMessage(MessageKey.HOST_EDIT_SUCCESS_MESSAGE, masterHost.getHostName()));
	}

	/**
	 * @param masterHost
	 * @param ids
	 */
	private void removeTRA(MasterHost masterHost, List<String> ids) {
		List<String> removedBEHomes = new ArrayList<String>();
		if (null != masterHost.getBE())
			for (BE be : masterHost.getBE()) {
				if (null != be && !ids.contains(be.getId())) {
					removedBEHomes.add(be.getId());
				}
			}
		final String hostId = masterHost.getHostId();
		File[] trafiles = ((File) dataStoreService.getApplicationManagementDataStore()).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(hostId + "_");
			}
		});
		if (null != trafiles) {
			for (File file : trafiles) {
				if (null != file) {
					String id = file.getName().replace(".tra", "").replace(masterHost.getHostId() + "_", "").trim();
					if (!removedBEHomes.contains(id))
						file.delete();
				}
			}
		}
	}

	@Override
	public List<BE> getBeHomes(String hostName, String ipAddress, String hostOs, String userName, String password,
			int sshPort) throws Exception {
		return beHomeDiscoveryService.getBEHomes(ipAddress, hostOs, userName, password, sshPort, hostName);
	}

	@Override
	public void storeTRA(MasterHost masterHost)
			throws BEHostTRASaveException, JschAuthenticationException, JschConnectionException {
		boolean isMonitorable = Boolean
				.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
		if(isMonitorable)
		{
			masterHost.setAuthenticated(true);
			return;
		}
		
		if (!isHostTRAConfigExist(masterHost.getHostId())) {
			LOGGER.log(Level.DEBUG,
					messageService.getMessage(MessageKey.OVERRIDDEN_TRA_CONFIG_NOT_EXIST, masterHost.getHostName()));
			if (null != masterHost.getUserName() && !masterHost.getUserName().trim().isEmpty()) {

				try {
					File repoLocation = (File) dataStoreService.getApplicationManagementDataStore();
					List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
					SshConfig sshConfig = new SshConfig();
					sshConfig.setHostIp(masterHost.getHostIp());
					sshConfig.setPassword(masterHost.getPassword());
					sshConfig.setPort(masterHost.getSshPort());
					sshConfig.setUserName(masterHost.getUserName());
					if (null == masterHost.getOs() || masterHost.getOs().trim().isEmpty()) {
						String os = getHostOS(masterHost.getHostIp(), masterHost.getUserName(),
								masterHost.getPassword(), masterHost.getSshPort());
						if (null != os && !os.trim().isEmpty()) {
							masterHost.setAuthenticated(true);
							masterHost.setOs(os);
						}

					} else {
						if ("Windows Based".equals(masterHost.getOs())
								|| "OS/X,Unix/Linux Based".equals(masterHost.getOs())) {
							String os = getHostOS(masterHost.getHostIp(), masterHost.getUserName(),
									masterHost.getPassword(), masterHost.getSshPort());
							if (null != os && !os.trim().isEmpty()) {
								masterHost.setAuthenticated(true);
								masterHost.setOs(os);
							}

						} else {
							authenticateHost(masterHost);
						}

					}
					if (masterHost.isAuthenticated()) {
						// Get OS
						String startPuMethod = BEAgentUtil.determineMethod(masterHost.getOs());
						// Get TRA files of each be home
						for (BE be : masterHost.getBE()) {
							String traPath = be.getBeTra();
							File hostTRAFile = new File(repoLocation,
									masterHost.getHostId() + "_" + be.getId() + ".tra");
							if (!hostTRAFile.exists()) {
								hostTRAFile.createNewFile();
								String remoteFile = traPath;
								if (startPuMethod.startsWith("windows")) {
									remoteFile = BEAgentUtil.getWinSshPath(traPath);
								}
								jobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
								List<Object> results = poolService
										.submitJobs(
												new BETeaDownloadRemoteFileJob(remoteFile,
														hostTRAFile.getAbsolutePath(), masterHost.getOs(), timeout()),
												jobExecutionContexts);
								if (null != results) {
									for (Object object : results) {
										if (!(object instanceof Exception)) {
											boolean result = (Boolean) object;
											if (result)
												LOGGER.log(Level.DEBUG, messageService.getMessage(
														MessageKey.TRA_FILE_DOWNLOAD, masterHost.getHostName()));
											else
												hostTRAFile.delete();
										} else {
											hostTRAFile.delete();
										}
									}
								}

							}
						}
					}

				} catch (IOException e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				}
			}
		}
	}

	private boolean isHostTRAConfigExist(String hostId) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CHECKING_OVERRIDDEN_TRA_CONFIG_FILE_EXISTENCE));

		boolean hasExist = false;
		Map<String, Application> applications = applicationsManagementService.getApplications();

		if (null != applications) {
			for (Entry<String, Application> entry : applications.entrySet()) {
				if (null != entry) {
					Application application = entry.getValue();
					if (null != application) {
						hasExist = ManagementUtil.isHostTraConfigExist(hostId, application);
					}
				}
			}
		}
		return hasExist;
	}

	@Override
	public String deleteMasterHost(MasterHost host, String loggedInUser) throws BEMasterHostDeleteException {
		Map<String, Application> applications = applicationsManagementService.getApplications();

		if (null != applications) {
			for (Entry<String, Application> entry : applications.entrySet()) {
				if (null != entry) {
					Application application = entry.getValue();
					if (null != application) {
						List<Host> hostsTobeDeleted = new ArrayList<Host>();
						List<Host> hosts = application.getHosts();
						Iterator<Host> iterator = hosts.iterator();

						while (iterator.hasNext()) {
							Host existingHost = iterator.next();
							if (null != existingHost
									&& existingHost.getHostId().trim().equals(host.getHostId().trim())) {
								List<ServiceInstance> instances = existingHost.getInstances();
								if (null != instances && !instances.isEmpty()) {
									throw new BEMasterHostDeleteException(messageService
											.getMessage(MessageKey.DELETE_ALL_INSTANCES_TO_BOUND_MACHINE));
								} else {
									hostsTobeDeleted.add(existingHost);
								}
							}

						}
						try {
							if (null != hostsTobeDeleted) {
								hosts.removeAll(hostsTobeDeleted);

								dataStoreService.storeApplicationTopology(application);
							}
						} catch (BEApplicationSaveException e) {
							LOGGER.log(Level.ERROR, e.getMessage());
						}

					}
				}
			}
		}

		try {
			masterHostMap.remove(host.getHostId());

			// If host is deleted the clear cached TRA folder. If there is no
			// host config then remove host repo file
			File repoLocation = (File) dataStoreService.getApplicationManagementDataStore();

			if (!masterHostMap.isEmpty())
				dataStoreService.storeMasterHostTopology(masterHostMap);
			else {
				File hostConfig = new File(repoLocation, "hostrepo.xml");
				hostConfig.delete();
			}

			List<BE> beHomes = host.getBE();
			if (null != beHomes) {
				for (BE be : beHomes) {
					String id = be.getId();
					File traFile = new File((File) dataStoreService.getApplicationManagementDataStore(),
							host.getHostId() + "_" + id + ".tra");
					if (traFile.exists()) {
						traFile.delete();
					}
				}
			}

			return (messageService.getMessage(MessageKey.HOST_DELETE_SUCCESS));
		} catch (BEMasterHostSaveException e) {
			throw new BEMasterHostDeleteException(e);
		}
	}

	@Override
	public MasterHost getMasterHostByName(String hostName) {
		for (MasterHost host : masterHostMap.values()) {
			if (null != host && host.getHostName().trim().equalsIgnoreCase(hostName.trim())) {
				return host;
			}
		}
		return null;
	}

	/**
	 * Get master host from repo matched with passed host resource
	 * 
	 * @param hostResource
	 *            - host resource from site topology
	 * @param masterHosts
	 *            -Map of master host
	 * @return Matched host
	 */
	@Override
	public MasterHost getMasterHostByHostResource(HostResource hostResource) {
		for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
			if (null != entry) {
				MasterHost masterHost = entry.getValue();
				if (null != masterHost) {
					String mHostName = masterHost.getHostName().trim();
					String mHostIp = masterHost.getHostIp().trim();
					String mUserName = masterHost.getUserName().trim();
					String mPassword = masterHost.getPassword().trim();
					String mOs = hostResource.getOsType();
					int msshPort = masterHost.getSshPort();

					String hostName = hostResource.getHostname().trim();
					String hostIP = hostResource.getIp().trim();
					String hostFQN = hostResource.getHostname();
					try {
						if (ManagementUtil.isLocalhost(hostFQN, hostIP)) {
							hostIP = InetAddress.getLocalHost().getHostAddress();
							hostFQN = InetAddress.getLocalHost().getHostName();
						}
						hostFQN = ProcessInfo.ensureFQDN(hostFQN, hostIP);
					} catch (UnknownHostException ex) {
						LOGGER.log(Level.WARN, ex, "Unknown host: " + ex.getMessage());
					}
					String userName = hostResource.getUserCredentials().getUsername().trim();
					String password = hostResource.getUserCredentials().getPassword().trim();
					int sshPort = Integer.parseInt(getPortAndExptMsg(hostResource)[0].toString());
					String os = hostResource.getOsType();
					if (mHostName.equals(hostName)) {
						if (mHostIp.equals(hostIP) && mUserName.equals(userName) && mPassword.equals(password)
								&& mOs.equals(os) && msshPort == sshPort) {
							return masterHost;
						}
					}

				}
			}
		}

		return null;
	}

	@Override
	public MasterHost getMasterHostByHostId(String masterHostId) {
		for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
			if (null != entry) {
				MasterHost masterHost = entry.getValue();
				if (null != masterHost) {
					if (masterHost.getHostId().trim().equals(masterHostId.trim())) {
						return masterHost;
					}
				}
			}
		}
		return null;
	}

	@Override
	public MasterHost createMasterHost(HostResource hostResource)
			throws JschAuthenticationException, JschConnectionException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CREATING_NEW_MASTER_HOST));

		// Get machine name and IP address if user has mentioned localhost
		// as ip address
		String hostIP = hostResource.getIp();
		String hostFQN = hostResource.getHostname();
		try {
			if (ManagementUtil.isLocalhost(hostFQN, hostIP)) {
				hostIP = InetAddress.getLocalHost().getHostAddress();
				hostFQN = InetAddress.getLocalHost().getHostName();
			}
			hostFQN = ProcessInfo.ensureFQDN(hostFQN, hostIP);
		} catch (UnknownHostException ex) {
			LOGGER.log(Level.WARN, ex, messageService.getMessage(MessageKey.UNKNOWN_HOST, ex.getMessage()));
		}

		String hostName = hostResource.getHostname();

		// If name is matched then get name appended with next number
		if (isHostNameMatched(hostName)) {
			hostName = ManagementUtil.getNextMasterHostName(masterHostMap, hostResource.getHostname());
		}

		String userName = hostResource.getUserCredentials().getUsername().trim();
		String password = hostResource.getUserCredentials().getPassword().trim();
		int sshPort = Integer.parseInt(getPortAndExptMsg(hostResource)[0].toString());

		String hostId = ManagementUtil.getNextMasterHostId(masterHostMap);

		// Create master host
		MasterHost newMasterHost = createHost(hostName, hostIP, null,
				MasterHostConvertor.convertBEPeristentModelToBEServiceModel(hostResource.getSoftware().getBe()),
				userName, password, sshPort, "", hostId, hostFQN);

		try {
			storeTRA(newMasterHost);
		} catch (BEHostTRASaveException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
		masterHostMap.put(newMasterHost.getHostId(), newMasterHost);

		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CREATED_NEW_MASTER_HOST));

		return newMasterHost;
	}

	@Override
	public String cloneMasterHost(MasterHost masterHost, String hostName, String ipAddress, String hostOs,
			String beHome, String beTra, String userName, String password, int sshPort, String deploymentPath,
			String loggedInUser) throws BEMasterHostEditException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refereshAllMasterHostStatus() {
		for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
			if (null != entry) {
				MasterHost masterHost = entry.getValue();
				if (null != masterHost) {
					setMasterHostStatus(masterHost);
				}
			}
		}

	}

	@Override
	public void refreshMasterHostStatus(MasterHost masterHost) {
		if (null != masterHost) {
			setMasterHostStatus(masterHost);
		}
	}

	@Override
	public boolean isInstancesDeployed(MasterHost masterHost) {

		boolean isDeployed = false;
		Map<String, Application> applications = applicationsManagementService.getApplications();
		if (null != applications) {
			for (Entry<String, Application> entry : applications.entrySet()) {
				if (null != entry) {
					Application application = entry.getValue();
					if (null != application) {
						List<Host> hosts = application.getHosts();
						Iterator<Host> iterator = hosts.iterator();
						while (iterator.hasNext()) {
							Host existingHost = iterator.next();
							if (null != existingHost
									&& existingHost.getHostId().trim().equals(masterHost.getHostId().trim())) {
								List<ServiceInstance> instances = existingHost.getInstances();
								if (null != instances && !instances.isEmpty()) {
									for (ServiceInstance serviceInstance : instances) {
										if (null != serviceInstance
												&& (serviceInstance.getDeployed() || BETeaAgentStatus.DEPLOYING
														.getStatus().equals(serviceInstance.getDeploymentStatus()))) {
											isDeployed = true;
											break;
										}
									}
								}

							}
						}
					}
				}
			}
		}
		return isDeployed;
	}

	@Override
	public List<String> getApplicationHostToBeDeleted(MasterHost masterHost) {
		List<String> hostsToBeDeleted = new ArrayList<String>();
		Map<String, Application> applications = applicationsManagementService.getApplications();
		if (null != applications) {
			for (Entry<String, Application> entry : applications.entrySet()) {
				if (null != entry) {
					Application application = entry.getValue();
					if (null != application) {
						List<Host> hosts = application.getHosts();
						Iterator<Host> iterator = hosts.iterator();
						while (iterator.hasNext()) {
							Host existingHost = iterator.next();
							if (null != existingHost
									&& existingHost.getHostId().trim().equals(masterHost.getHostId().trim())) {
								List<ServiceInstance> instances = existingHost.getInstances();
								if (null == instances || instances.isEmpty()) {
									hostsToBeDeleted.add(existingHost.getKey());
								}
							}
						}
					}
				}
			}
		}
		return hostsToBeDeleted;
	}

	/**
	 * Check host exist by name or not
	 * 
	 * @param hostName
	 *            - Name of the host
	 * @return
	 */
	private boolean isHostExistByName(String hostName) {
		boolean isExist = false;
		for (MasterHost host : masterHostMap.values()) {
			if (null != host && host.getHostName().trim().equalsIgnoreCase(hostName.trim())) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	/**
	 * Check host exist by name or not skip passed host
	 * 
	 * @param hostName
	 *            - Name of the host
	 * @param masterHost
	 *            -
	 * @return
	 */
	private boolean isHostExistByName(String hostName, MasterHost masterHost) {

		boolean isExist = false;
		for (MasterHost host : masterHostMap.values()) {
			if (null != host)
				if (null != masterHost)
					if (!masterHost.getKey().equals(host.getKey())
							&& host.getHostName().trim().equalsIgnoreCase(hostName.trim())) {
						isExist = true;
						break;
					}

			if (null == masterHost)
				if (host.getHostName().trim().equalsIgnoreCase(hostName.trim())) {
					isExist = true;
					break;
				}
		}
		return isExist;
	}

	/**
	 * This method is used to get the SSH port and message
	 * 
	 * @param hostResource
	 *            - Details of host
	 * @return Object array
	 */
	private Object[] getPortAndExptMsg(HostResource hostResource) {
		int port = -1;
		String msg = null;
		try {
			Ssh ssh = hostResource.getStartPuMethod().getSsh();
			if (ssh == null) {
				port = 22;
				msg = messageService.getMessage(MessageKey.HOST_NO_SSH_PORT_SPECIFIED);
			} else {
				port = Integer.parseInt(ssh.getPort());
			}
		} catch (Exception ex) {
			msg = messageService.getMessage(MessageKey.ILLEGAL_SSH_PORT_ATTEMPTING_CONNECTION_WITH_DEFAULT_PORT, port);

			port = 22;
		}
		return new Object[] { port, msg };
	}

	/**
	 * Check host with name is exists in master host repo or not.
	 * 
	 * @param hostResource
	 *            - Host Resource
	 * @return - True/false
	 */
	private boolean isHostNameMatched(String hostName) {
		boolean isHostNameMaching = false;
		for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
			if (null != entry) {
				MasterHost masterHost = entry.getValue();
				if (null != masterHost) {
					String mHostName = masterHost.getHostName().trim();
					if (mHostName.equals(hostName)) {
						isHostNameMaching = true;

						break;
					}

				}
			}
		}
		return isHostNameMaching;
	}

	/**
	 * Set status of master host
	 * 
	 * @param masterHost
	 *            - Master host
	 */
	private void setMasterHostStatus(MasterHost masterHost) {
		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.HOST_SETTING_RUNNING_STATUS, masterHost.getHostName()));

		// Get machine name and IP address if user has mentioned localhost
		// as ip address
		String machineName = "";
		String ipAddress = masterHost.getHostIp();
		String hostName = masterHost.getHostName();
		try {

			if (ManagementUtil.isLocalhost(hostName.trim(), ipAddress.trim())) {
				ipAddress = InetAddress.getLocalHost().getHostAddress();
				machineName = InetAddress.getLocalHost().getHostName();
			}
			machineName = ProcessInfo.ensureFQDN(hostName.trim(), ipAddress.trim());
		} catch (UnknownHostException ex) {
			LOGGER.log(Level.WARN, ex, messageService.getMessage(MessageKey.UNKNOWN_HOST, ex.getMessage()));
		}
		masterHost.setMachineName(machineName);
		masterHost.setHostIp(ipAddress);
		if (isReachable(masterHost.getHostIp())) {
			masterHost.setStatus(BETeaAgentStatus.REACHABLE.getStatus());
		} else {
			masterHost.setStatus(BETeaAgentStatus.UNREACHABLE.getStatus());
		}
		LOGGER.log(Level.DEBUG, masterHost.getHostName() + " is " + masterHost.getStatus());
	}

	private boolean isReachable(String hostIp) {
		String timeout = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_HOST_REACHABLE_TIMEOUT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_HOST_REACHABLE_TIMEOUT.getDefaultValue());
		boolean isReachable = false;
		try {

			isReachable = InetAddress.getByName(hostIp).isReachable(Integer.valueOf(timeout));
			if (!isReachable) {
				boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
				ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows ? "-n" : "-c", "1", hostIp);
				Process proc = processBuilder.start();

				int returnVal = proc.waitFor();
				isReachable = returnVal == 0;
			}
		} catch (NumberFormatException | IOException | InterruptedException e) {
		}

		return isReachable;
	}

	/**
	 * Create host instance
	 * 
	 * @param hostName
	 *            - Host Name
	 * @param ipAddress
	 *            - IP address
	 * @param hostOS
	 *            - Operation system of host
	 * @param beHome
	 *            - Home of Business Events
	 * @param beTra
	 *            - TRA of business
	 * @param userName
	 *            - User name
	 * @param password
	 *            - Password of the user
	 * @param sshPort
	 *            - SSH port
	 * @param deploymentPath
	 *            - Deployment path of host
	 * @param hostId
	 *            - Host Id
	 * @param machineName
	 *            - Name of machine
	 * @return Master host instance
	 */
	private MasterHost createHost(String hostName, String ipAddress, String hostOS, List<BE> beServiceModels,
			String userName, String password, int sshPort, String deploymentPath, String hostId, String machineName) {
		password = ManagementUtil.getEncodedPwd(password);
		MasterHost host = new MasterHostImpl();
		host.setHostName(hostName);
		List<BE> beModels = new ArrayList<BE>();
		if (null != beServiceModels) {
			for (BE be : beServiceModels) {
				if (null != be) {
					if (null != be.getBeHome() && !be.getBeHome().trim().isEmpty() && null != be.getBeTra()
							&& !be.getBeTra().trim().isEmpty()) {
						if (null == be.getId() || be.getId().trim().isEmpty())
							be.setId(RandomStringUtils.randomNumeric(10));
						beModels.add(be);
					}
				}
			}
		}
		host.setBE(beModels);
		host.setBE(beModels);
		host.setHostId(hostId);
		host.setHostIp(ipAddress);
		host.setMachineName(machineName);
		host.setOs(hostOS);
		host.setUserName(userName);
		host.setPredefined(true);
		host.setSshPort(sshPort);
		host.setPassword(password);
		host.setDeploymentPath(deploymentPath);
		return host;
	}

	@Override
	public String groupDelete(List<String> masterHostsToBeDeleted, String loggedInUser)
			throws BEMasterHostDeleteException {
		if (null != masterHostsToBeDeleted) {
			List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
			// Get Master host to be deleted
			for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
				if (null != entry) {
					if (masterHostsToBeDeleted.contains(entry.getKey())) {
						jobExecutionContexts.add(new MasterHostGroupJobExecutionContext(entry.getValue()));
					}
				}
			}

			if (!jobExecutionContexts.isEmpty()) {

				// Submit the job
				List<Object> results = poolService.submitJobs(new BETeaDeleteMasterHostJob(this, loggedInUser),
						jobExecutionContexts);
				int errorCnt = 0;
				for (Object object : results) {
					BETeaOperationResult operationResult = (BETeaOperationResult) object;
					Object result = operationResult.getResult();
					if (result instanceof Exception) {
						errorCnt++;
					}
				}
				// If error count is same as count of
				if (errorCnt != 0)
					if (errorCnt == jobExecutionContexts.size())
						throw new BEMasterHostDeleteException(
								messageService.getMessage(MessageKey.DELETE_SELECTED_HOST_ERROR));
					else
						throw new BEMasterHostDeleteException(messageService.getMessage(MessageKey.HOST_FAILED));
				return (messageService.getMessage(MessageKey.SELECTED_HOST_DELETE_SUCCESS));
			}
		}
		return (messageService.getMessage(MessageKey.MASTER_HOST_NOT_SELECTED));
	}

	@Override
	public void updateMasterhostStatus(MasterHost masterHost) {
		setMasterHostStatus(masterHost);
	}

	/**
	 * @return
	 */
	private Integer timeout() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getDefaultValue()));
	}

	/**
	 * This method is used to get the service instance from all application for
	 * given host
	 * 
	 * @param host
	 *            - Host where application instance are deployed
	 * @return List of service instance
	 */
	@Override
	public List<ServiceInstance> getHostServiceInstance(MasterHost host) {
		List<ServiceInstance> instances = new ArrayList<>();

		Map<String, Application> applications = applicationsManagementService.getApplications();

		if (null != applications) {
			for (Entry<String, Application> entry : applications.entrySet()) {
				if (null != entry) {
					Application application = entry.getValue();
					if (null != application) {
						List<Host> hosts = application.getHosts();
						Iterator<Host> iterator = hosts.iterator();
						while (iterator.hasNext()) {
							Host existingHost = iterator.next();
							if (null != existingHost
									&& existingHost.getHostId().trim().equals(host.getHostId().trim())) {
								List<ServiceInstance> listOfServiceInstances = existingHost.getInstances();
								Iterator<ServiceInstance> instanceIterator = listOfServiceInstances.iterator();
								while (instanceIterator.hasNext()) {
									try {
										ServiceInstance serviceInstance = instanceIterator.next();
										serviceInstance.setUpTime(BEMMServiceProviderManager.getInstance()
												.getBEMBeanService().getProcessStartTime(serviceInstance));
									} catch (ObjectCreationException e) {
										e.printStackTrace();
									}
								}
								instances.addAll(existingHost.getInstances());
							}
						}
					}
				}
			}
		}
		return instances;
	}

	@Override
	public String start(MasterHost masterHost, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStartException {
		List<ServiceInstance> serviceInstances = getMasterHostInstances(instances);
		try {
			return startServiceInstances(instances, loggedInUser, serviceInstances,
					BEMMServiceProviderManager.getInstance().getBEServiceInstancesManagementService());
		} catch (BEServiceInstanceStartException | ObjectCreationException e) {
			throw new BEServiceInstanceStartException(e);
		}
	}

	@Override
	public String stop(MasterHost masterHost, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStopException {
		List<ServiceInstance> serviceInstances = getMasterHostInstances(instances);
		try {
			return stopServiceInstances(instances, loggedInUser, serviceInstances,
					BEMMServiceProviderManager.getInstance().getBEServiceInstancesManagementService());
		} catch (ObjectCreationException | BEServiceInstanceStopException e) {
			throw new BEServiceInstanceStopException(e);
		}
	}

	@Override
	public String kill(MasterHost masterHost, List<String> instances, String loggedInUser)
			throws BEServiceInstanceKillException {
		List<ServiceInstance> serviceInstances = getMasterHostInstances(instances);
		try {
			return killServiceInstances(null, instances, serviceInstances, loggedInUser, null,
					BEMMServiceProviderManager.getInstance().getBEServiceInstancesManagementService());
		} catch (ObjectCreationException | BEServiceInstanceKillException e) {
			throw new BEServiceInstanceKillException(e);
		}
	}

	/**
	 * Get the list of service instances deployed on the host
	 * 
	 * @param instances
	 *            - List of service instances key on which operation need to be
	 *            performed
	 * @return List of service instances whose key exist in instances
	 */
	private List<ServiceInstance> getMasterHostInstances(List<String> instances) {
		List<ServiceInstance> serviceInstances = new ArrayList<>();

		Map<String, Application> applications = applicationsManagementService.getApplications();

		if (null != applications) {
			for (Entry<String, Application> entry : applications.entrySet()) {
				if (null != entry) {
					Application application = entry.getValue();
					if (null != application) {
						List<Host> hosts = application.getHosts();
						Iterator<Host> iterator = hosts.iterator();
						while (iterator.hasNext()) {
							Host existingHost = iterator.next();
							if (null != existingHost) {
								List<ServiceInstance> hostServiceInstances = existingHost.getInstances();
								if (null != hostServiceInstances && !hostServiceInstances.isEmpty()) {
									for (ServiceInstance serviceInstance : hostServiceInstances) {
										if (null != serviceInstance && instances.contains(serviceInstance.getKey())) {
											serviceInstances.add(serviceInstance);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return serviceInstances;
	}

	@Override
	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}

	@Override
	public LockManager getLockManager() {
		return lockManager;
	}

	@Override
	public String uploadExternalJars(MasterHost masterHost, DataSource jarFiles, String beId, Session session)
			throws BEUploadFileException {
		if (!masterHost.isAuthenticated()) {
			throw new BEUploadFileException(
					messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, masterHost.getHostName()));
		}
		BE beDetails = null;
		if (null != masterHost.getBE() && !masterHost.getBE().isEmpty()) {
			for (BE be : masterHost.getBE()) {
				if (null != be && be.getId().equals(beId)) {
					beDetails = be;
					break;
				}
			}
		}
		if (null != beDetails) {
			String beHome = beDetails.getBeHome();
			beHome = beHome.replace("\\", "/");
			String uploadDir = beHome;
			if (beHome.endsWith("/")) {
				uploadDir = beHome + "lib/ext/tpcl/beTeagentUpload/";
			} else {
				uploadDir = beHome + "/lib/ext/tpcl/beTeagentUpload/";
			}
			String remoteFile = uploadDir + "" + jarFiles.getName();
			String startPUMethod = BEAgentUtil.determineMethod(masterHost.getOs());
			String command = "mkdir -p " + uploadDir;
			if (startPUMethod.startsWith("windows")) {
				command = "mkdir -p '" + BEAgentUtil.getWinSshPath(uploadDir + "'");

			}
			try {
				ManagementUtil.executeCommand(command, session, true, timeout(), maxRetry(), threadSleepTime());
			} catch (JschCommandFailException e) {
				LOGGER.log(Level.DEBUG, e.getMessage());
			}
			if (jarFiles.getName().endsWith("zip")) {
				remoteFile = uploadDir;
				File tempFile = null;
				try {
					tempFile = Files.createTempDirectory("externalJars_" + System.currentTimeMillis()).toFile();
					String filePath = tempFile.getAbsolutePath();
					unzip(jarFiles.getInputStream(), filePath);

					uploadFiles(startPUMethod, tempFile, remoteFile, tempFile.getAbsolutePath(), session, true);
				} catch (IOException e) {
					throw new BEUploadFileException(e);
				} finally {
					if (null != tempFile)
						try {
							ManagementUtil.delete(tempFile);
						} catch (FileNotFoundException e) {
						}
				}

			} else {
				File tempFile = null;
				try {
					tempFile = Files.createTempFile(jarFiles.getName().replace(".jar", ""), ".jar").toFile();
					OutputStream out = new FileOutputStream(tempFile);
					byte[] b = ManagementUtil.getByteArrayFromStream(jarFiles.getInputStream());
					out.write(b);
					out.close();
					uploadFiles(startPUMethod, tempFile, remoteFile, tempFile.getAbsolutePath(), session, false);
				} catch (IOException e) {
					throw new BEUploadFileException(e);
				} finally {
					if (null != tempFile)
						try {
							ManagementUtil.delete(tempFile);
						} catch (FileNotFoundException e) {
						}
				}
			}

		}
		return (messageService.getMessage(MessageKey.FILE_UPLOADED_SUCCESS));
	}

	@Override
	public String uploadExternalJars(MasterHost masterHost, DataSource jarFiles, String beId)
			throws InvalidParameterException, BEUploadFileException {
		if (!masterHost.isAuthenticated()) {
			throw new BEUploadFileException(
					messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, masterHost.getHostName()));
		}
		if (null == beId || beId.trim().isEmpty())
			throw new InvalidParameterException(messageService.getMessage(MessageKey.INVALID_BE_HOME));
		try {
			if (jarFiles.getInputStream().available() <= 0)
				throw new InvalidParameterException(messageService.getMessage(MessageKey.INVALID_FILE));
		} catch (IOException e) {
			throw new BEUploadFileException(e.getMessage());
		}

		SshConfig sshConfig = new SshConfig();
		sshConfig.setHostIp(masterHost.getHostIp());
		sshConfig.setPassword(masterHost.getPassword());
		sshConfig.setPort(masterHost.getSshPort());
		sshConfig.setUserName(masterHost.getUserName());

		List<GroupJobExecutionContext> contexts = new ArrayList<>();
		contexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
		List<Object> objects = poolService.submitJobs(new BETeaUploadExternalJarsJob(this, jarFiles, masterHost, beId),
				contexts);
		if (null != objects) {
			for (Object object : objects) {
				if (object instanceof Boolean) {
					boolean result = (boolean) object;
					if (result)
						return (messageService.getMessage(MessageKey.FILE_UPLOADED_SUCCESS));
				}
			}
		}

		throw new BEUploadFileException(messageService.getMessage(MessageKey.UPLOAD_JAR_FILES_ERROR));
	}

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified
	 * by destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	private void unzip(InputStream inputStream, String destDirectory) {

		ZipInputStream zipIn = new ZipInputStream(inputStream);

		try {
			File destDir = new File(destDirectory);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			ZipEntry entry = zipIn.getNextEntry();

			// iterates over entries in the zip file
			while (entry != null) {
				String filePath = destDirectory + File.separator + entry.getName();
				if (!entry.isDirectory()) {
					// if the entry is a file, extracts it
					try {
						// Defensive code to make dir if not already created
						File destFile = new File(filePath);
						if (!destFile.getParentFile().exists()) {
							destFile.getParentFile().mkdirs();
						}
					} catch (Exception e) {
						// Ignore errors if any while creating directories
					}
					extractFile(zipIn, filePath);
				} else {
					// if the entry is a directory, make the directory
					File dir = new File(filePath);
					dir.mkdirs();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}

		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
		} finally {
			try {
				if (null != zipIn) {
					zipIn.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
			}
		}
	}

	private static final int BUFFER_SIZE = 4096;

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	/**
	 * Upload the files to target host
	 * 
	 * @param startPuMethod
	 * @param file
	 * @param externalClassesPath
	 * @param jschClient
	 * @param tempPath
	 * @param session
	 * @param isZip
	 */
	private void uploadFiles(String startPuMethod, File file, String externalClassesPath, String tempPath,
			Session session, boolean isZip) throws FileNotFoundException {

		if (null != file && file.exists()) {
			if (!file.isDirectory()) {

				String remoteFile = externalClassesPath;
				if (isZip)
					remoteFile = externalClassesPath + file.getName();
				try {
					if (startPuMethod.startsWith("windows")) {
						remoteFile = BEAgentUtil.getWinSshPath(remoteFile);
					}
					remoteFile = "'" + remoteFile + "'";
					ManagementUtil.uploadToRemoteMahine(remoteFile, session, file.getAbsolutePath(), timeout());
				} catch (JschCommandFailException e) {
					LOGGER.log(Level.ERROR,
							messageService.getMessage(MessageKey.UPLOAD_FILE_FAIL, file.getAbsolutePath()), e);
				}

			} else {

				for (File file1 : file.listFiles()) {
					uploadFiles(startPuMethod, file1, externalClassesPath, tempPath, session, isZip);
				}
			}

		}

	}

	private String getHostOS(String ipAddress, String username, String password, int sshPort)
			throws JschAuthenticationException, JschConnectionException {
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		SshConfig sshConfig = new SshConfig();
		sshConfig.setHostIp(ipAddress);
		sshConfig.setPassword(password);
		sshConfig.setPort(sshPort);
		sshConfig.setUserName(username);
		// Get OS
		jobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
		List<Object> results = poolService.submitJobs(new BETeaRemoteHostOSDetectJob(null, timeout()),
				jobExecutionContexts);
		if (null != results) {
			for (Object object : results) {
				if (!(object instanceof Exception)) {
					BETeaOperationResult os = (BETeaOperationResult) object;
					if (!(os.getResult() instanceof Exception)) {
						return os.getResult().toString();
					} else {
						String message = ((Exception) os.getResult()).getMessage();
						if (message.contains("Auth fail") || message.contains("Auth cancel")) {
							throw new JschAuthenticationException(
									messageService.getMessage(MessageKey.AUTHENTICATE_CREDENTIALS_ERROR, ipAddress));
						} else if ((os.getResult()) instanceof ConnectException
								|| ((Exception) os.getResult()).getCause() instanceof ConnectException) {
							throw new JschConnectionException("Failed to connect :" + ipAddress);
						}
					}
				} else {
					String message = ((Exception) object).getMessage();
					if (message.contains("Auth fail") || message.contains("Auth cancel")) {
						throw new JschAuthenticationException(
								messageService.getMessage(MessageKey.AUTHENTICATE_CREDENTIALS_ERROR, ipAddress));
					} else if (object instanceof ConnectException
							|| ((Exception) object).getCause() instanceof ConnectException) {
						throw new JschConnectionException("Failed to connect :" + ipAddress);
					}
				}
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	private Integer maxRetry() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_MAX_RETRY.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_SSH_MAX_RETRY.getDefaultValue()));
	}

	/**
	 * @return
	 */
	private Integer threadSleepTime() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_SLEEP_TIME.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_SSH_SLEEP_TIME.getDefaultValue()));
	}

	/**
	 * Authenticate Host
	 */
	private void authenticateHosts() {
		for (MasterHost masterHost : masterHostMap.values()) {
			try {
				authenticateHost(masterHost);
			} catch (JschAuthenticationException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
			}
		}
	}

	/**
	 * @param masterHost
	 * @throws JschAuthenticationException
	 */
	private void authenticateHost(MasterHost masterHost) throws JschAuthenticationException {
		boolean isMonitorable = Boolean
				.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
		if(isMonitorable)
		{
			masterHost.setAuthenticated(true);
			return;
		}
		
		// Add service instances in job execution context
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		SshConfig sshConfig = new SshConfig();
		sshConfig.setHostIp(masterHost.getHostIp());
		sshConfig.setPassword(masterHost.getPassword());
		sshConfig.setPort(masterHost.getSshPort());
		sshConfig.setUserName(masterHost.getUserName());
		jobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
		List<Object> results = poolService.submitJobs(new BETeaAuthenticateMasterHostJob(masterHost),
				jobExecutionContexts);
		for (Object object : results) {
			if (!(object instanceof Exception)) {
				BETeaOperationResult operationResult = (BETeaOperationResult) object;
				Object result = operationResult.getResult();
				if (!(result instanceof Exception)) {
					masterHost.setAuthenticated((boolean) result);
				} else {
					String message = ((Exception) result).getMessage();
					if (message.contains("Auth fail") || message.contains("Auth cancel")) {
						throw new JschAuthenticationException(messageService
								.getMessage(MessageKey.AUTHENTICATE_CREDENTIALS_ERROR, masterHost.getHostIp()));
					} else if (result instanceof UnknownHostException) {
						throw new JschAuthenticationException(
								messageService.getMessage(MessageKey.INVALID_HOST_IP_ADDRESS));
					} else if (result instanceof ConnectException) {
						throw new JschAuthenticationException("Failed to connect :" + masterHost.getHostIp());
					}

				}
			} else {
				String message = ((Exception) object).getMessage();
				if (message.contains("Auth fail") || message.contains("Auth cancel")) {
					throw new JschAuthenticationException(messageService
							.getMessage(MessageKey.AUTHENTICATE_CREDENTIALS_ERROR, masterHost.getHostIp()));
				} else if (object instanceof UnknownHostException) {
					throw new JschAuthenticationException(
							messageService.getMessage(MessageKey.INVALID_HOST_IP_ADDRESS));
				}

			}

		}
	}

	@Override
	public MasterHost discoverBEHome(List<String> selectedHosts) throws Exception {
		int errorCount = 0;
		List<MasterHost> hosts = new ArrayList<>();
		for (String key : selectedHosts) {
			MasterHost masterHost = masterHostMap.get(key);
			if (null != masterHost && masterHost.isAuthenticated()) {
				hosts.add(masterHost);
			}
		}
		if (hosts.isEmpty()) {
			throw new Exception("Host are not authenticated");
		}
		for (MasterHost masterHost : hosts) {
			if (null != masterHost) {
				try {
					List<BE> beHomes = getBeHomes(masterHost.getHostName(), masterHost.getHostIp(), masterHost.getOs(),
							masterHost.getUserName(), masterHost.getPassword(), masterHost.getSshPort());
					if (null != beHomes && !beHomes.isEmpty()) {

						for (BE be : beHomes) {
							if (null != be) {
								boolean isBEHomeExist = false;
								be.setId(getNextId(masterHost.getBE()));
								if (null != masterHost.getBE() && !masterHost.getBE().isEmpty()) {
									for (BE be1 : masterHost.getBE()) {
										if (null != be1) {
											String beHome = be1.getBeHome().replaceAll("\\\\", "/").trim();
											String beHome1 = be.getBeHome().replaceAll("\\\\", "/").trim();
											if (beHome.equals(beHome1)) {
												isBEHomeExist = true;
												String ver = be.getVersion();
												String ver1 = be1.getVersion();
												if (ver == null) {
													ver = "";
												}
												if (null == ver1) {
													ver1 = "";
												}
												if (ver.split("\\.").length > ver1.split("\\.").length) {
													be1.setVersion(ver);
												} else if (ver1.split("\\.").length == 4
														&& 4 == ver.split("\\.").length) {
													{
														if (Integer.valueOf(ver.split("\\.")[3]) > Integer
																.valueOf(ver1.split("\\.")[3])) {
															be1.setVersion(ver);
														}
													}
												}

											}

										}

									}

								}

								if (masterHost.getBE() == null)
									masterHost.setBE(new ArrayList<>());
								if (!isBEHomeExist)
									masterHost.getBE().add(be);
							}
						}
					}
				} catch (Exception e) {
					errorCount++;
				}
			}
		}
		/**
		 * Download tra files
		 */
		MasterHost selectedHost = null;
		for (MasterHost masterHost : hosts) {
			try {
				storeTRA(masterHost);
				selectedHost = masterHost;
			} catch (BEHostTRASaveException | JschAuthenticationException e) {
				LOGGER.log(Level.DEBUG, e.getMessage());
			}
		}

		if (errorCount == selectedHosts.size())
			throw new Exception("Failed to discover the BE home");
		dataStoreService.storeMasterHostTopology(masterHostMap);
		return selectedHost;
	}

	private String getNextId(List<BE> bes) {

		long nextId = Long.parseLong(RandomStringUtils.randomNumeric(10));
		if (null != bes && !bes.isEmpty()) {
			for (BE be : bes) {
				if (null != be) {
					String id = be.getId();
					if (null != id && !id.trim().isEmpty()) {
						if (Long.parseLong(id) > nextId) {
							nextId = Long.parseLong(id);
						}
					}
				}
			}
		}
		nextId = nextId + 1;
		boolean isUnique = false;
		if (null == bes || bes.isEmpty()) {
			isUnique = true;
		} else {
			for (BE be : bes) {
				if (null != be) {
					if (null != be.getId() && !be.getId().trim().isEmpty()
							&& be.getId().trim().equals(String.valueOf(nextId))) {
						isUnique = false;
						break;
					} else {
						isUnique = true;
					}
				}
			}
		}
		if (!isUnique)
			getNextId(bes);
		return String.valueOf(nextId);
	}

	@Override
	public BE getBEHomeById(MasterHost masterHost, String beId) {
		List<BE> beHomes = masterHost.getBE();
		if (null != beHomes) {
			for (BE be : beHomes) {
				if (null != be) {
					if (be.getId().equals(beId)) {
						return be;
					}
				}
			}
		}
		return null;
	}
}
