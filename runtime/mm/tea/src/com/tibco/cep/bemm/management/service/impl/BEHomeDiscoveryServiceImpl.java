package com.tibco.cep.bemm.management.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.job.BETeaDownloadRemoteFileJob;
import com.tibco.cep.bemm.common.job.BETeaRemoteHostOSDetectJob;
import com.tibco.cep.bemm.common.job.BETeaTibecoBEHomeDetectJob;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.GroupJobExecutorService;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschConnectionException;
import com.tibco.cep.bemm.management.service.BEHomeDiscoveryService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.impl.BEImpl;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.be.util.BEAgentUtil;

/**
 * The implementation of BEHomeDiscoveryService which
 * 
 * @author dijadhav
 *
 */
public class BEHomeDiscoveryServiceImpl extends AbstractStartStopServiceImpl implements BEHomeDiscoveryService {
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEHomeDiscoveryService.class);
	private Properties configuration;
	private BEApplicationsManagementDataStoreService<?> dataStoreService;
	protected GroupJobExecutorService poolService;
	private MessageService messageService;

	/**
	 * Default Constructor
	 */
	public BEHomeDiscoveryServiceImpl() {
	}

	@Override
	public void init(Properties configuration) throws ServiceInitializationException, ObjectCreationException {
		LOGGER.log(Level.DEBUG, "Initializing BE home discovery service");

		this.configuration = configuration;

		dataStoreService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		LOGGER.log(Level.DEBUG, "Initializing BE home discovery service");
		this.poolService = BEMMServiceProviderManager.getInstance().getGroupOpExecutorService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.management.service.BEHomeDiscoveryService#getBEHomes(
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int)
	 */
	@Override
	public List<BE> getBEHomes(String ipAddress, String hostOs, String userName, String password, int sshPort,
			String hostName) throws JschAuthenticationException, JschConnectionException {
		if (null == hostOs || hostOs.trim().isEmpty())
			hostOs = getHostOS(ipAddress, userName, password, sshPort);
		else
			if ("Windows Based".equals(hostOs)
					|| "OS/X,Unix/Linux Based".equals(hostOs)) {
				hostOs = getHostOS(ipAddress, userName, password, sshPort);
			}
		if (null == hostOs || hostOs.trim().isEmpty())
			return null;
		
		try {
			
			File envFile = getEnvFile(hostName, ipAddress, hostOs, userName, password, sshPort);
			return validBeHomes(getTibcoHomes(envFile), hostName, ipAddress, hostOs, userName, password, sshPort);

		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
		return null;

	}

	private List<BE> validBeHomes(List<String> tibcoHomes, String hostName, String ipAddress, String hostOs,
			String userName, String password, int sshPort) throws JschAuthenticationException {
		String startPuMethod = BEAgentUtil.determineMethod(hostOs);

		return getScriptForBeHomes(tibcoHomes, startPuMethod, ipAddress, password, sshPort, userName, hostOs);
	}

	private List<BE> getScriptForBeHomes(List<String> tibcoHomes, String startPuMethod, String ipAddress,
			String password, int sshPort, String username, String hostOs) throws JschAuthenticationException {
		List<BE> beHomes = new ArrayList<BE>();

		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		SshConfig sshConfig = new SshConfig();
		sshConfig.setHostIp(ipAddress);
		sshConfig.setPassword(password);
		sshConfig.setPort(sshPort);
		sshConfig.setUserName(username);
		boolean isSolaris = false;
		if (hostOs.contains("SunOS")) {
			isSolaris = true;
		}
		// Get OS
		jobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
		List<Object> results = poolService.submitJobs(
				new BETeaTibecoBEHomeDetectJob(null, timeout(), tibcoHomes, startPuMethod, isSolaris),
				jobExecutionContexts);
		if (null != results) {
			for (Object object : results) {
				if (!(object instanceof Exception)) {
					BETeaOperationResult operationResult = (BETeaOperationResult) object;
					if (!(operationResult.getResult() instanceof Exception)) {
						String homes = (String) operationResult.getResult();
						if (null != homes && !homes.trim().isEmpty()) {
							String[] beHomeDetaisl = homes.split("\n");
							for (String home : beHomeDetaisl) {
								if (null != home && !home.trim().isEmpty()) {

									String version = home.split(",")[0].trim();
									String beHome = home.split(",")[1].trim();
									if (null != version && !version.trim().isEmpty() && null != beHome
											&& !beHome.trim().isEmpty()) {
										Map<String, String> versionMap = getVersion(version);
										if (null != versionMap && !versionMap.isEmpty()) {
											for (Entry<String, String> entry : versionMap.entrySet()) {
												String tempBEHome = beHome + entry.getKey();
												String tra = "";
												if (tempBEHome.endsWith("\\") || tempBEHome.endsWith("/")) {
													tempBEHome = tempBEHome.substring(0, tempBEHome.length() - 1);
												}
												if (startPuMethod.startsWith("windows")) {

													tempBEHome = tempBEHome.replace("/cygdrive/", "").replaceFirst("/",
															":/");

													if (beHome.indexOf("\\") > -1) {
														tra = tempBEHome + "\\bin\\be-engine.tra";
													} else if (tempBEHome.indexOf("/") > -1){
														tra = tempBEHome + "/bin/be-engine.tra";
														tempBEHome = tempBEHome.replaceAll("/", "\\\\");
													}	
												} else {
													if (beHome.indexOf("\\") > -1) {
														tra = tempBEHome + "\\bin\\be-engine.tra";
													} else if (tempBEHome.indexOf("/") > -1){
														tra = tempBEHome + "/bin/be-engine.tra";
													}	
												}

												BE be = new BEImpl();
												be.setBeHome(tempBEHome);
												be.setBeTra(tra);
												be.setVersion(entry.getValue());
												beHomes.add(be);
											}
										}
									}

								}

							}

						}

					} else {
						LOGGER.log(Level.DEBUG, ((Throwable) operationResult.getResult()).getMessage());
					}
				} else {
					String message = ((Exception) object).getMessage();
					if (message.contains("Auth fail") || message.contains("Auth cancel")) {
						throw new JschAuthenticationException(
								"Failed to authenticate on " + ipAddress + "using given credentials");
					}
				}
			}
		}

		return beHomes;

	}

	private List<String> getTibcoHomes(File envFile) throws Exception {
		List<String> tibcoHomes = new ArrayList<String>();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(envFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("environment");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String location = eElement.getAttribute("location");
				tibcoHomes.add(location);
			}
		}
		return tibcoHomes;
	}

	private File getEnvFile(String hostName, String ipAddress, String hostOs, String userName, String password,
			int sshPort) throws JschConnectionException, JschAuthenticationException {
		File hostEnvFile = null;
		try {
			File repoLocation = (File) dataStoreService.getTempFileLocation();
			if (null == hostOs || hostOs.isEmpty())
				hostOs = getHostOS(ipAddress, userName, password, sshPort);

			String startPuMethod = BEAgentUtil.determineMethod(hostOs);

			String envFilePath = null;
			if (startPuMethod.startsWith("windows")) {
				envFilePath = "$USERPROFILE/.TIBCOEnvInfo/_envInfo.xml";
			} else {
				envFilePath = "$HOME/.TIBCOEnvInfo/_envInfo.xml";
			}

			hostEnvFile = new File(repoLocation, hostName + "_envInfo.xml");
			hostEnvFile.createNewFile();

			List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
			SshConfig sshConfig = new SshConfig();
			sshConfig.setHostIp(ipAddress);
			sshConfig.setPassword(password);
			sshConfig.setPort(sshPort);
			sshConfig.setUserName(userName);
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
			List<Object> results = poolService.submitJobs(
					new BETeaDownloadRemoteFileJob(envFilePath, hostEnvFile.getAbsolutePath(), hostOs, timeout()),
					jobExecutionContexts);
			if (null != results) {
				for (Object object : results) {
					if (!(object instanceof Exception)) {
						boolean result = (Boolean) object;
						if (result)
							try {
								LOGGER.log(Level.DEBUG, "Env file download from %s host", hostName);
							} catch (Exception e) {
								LOGGER.log(Level.ERROR, "Env file download from host failed", e.getMessage());
							}

					}
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		} finally {

		}
		return hostEnvFile;
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
					}else{
						String message = ((Exception) os.getResult()).getMessage();
						if (message.contains("Auth fail") || message.contains("Auth cancel")) {
							throw new JschAuthenticationException(
									"Failed to authenticate on " + ipAddress + " using given credentials");
						}else  if (os.getResult() instanceof ConnectException
								|| ((Exception) os.getResult()).getCause() instanceof ConnectException) {
							throw new JschConnectionException("Failed to connect :" + ipAddress);
						}
					}
				} else {
					String message = ((Exception) object).getMessage();
					if (message.contains("Auth fail") || message.contains("Auth cancel")) {
						throw new JschAuthenticationException(
								"Failed to authenticate on " + ipAddress + " using given credentials");
					}else if (object instanceof ConnectException
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
	private Integer timeout() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getDefaultValue()));
	}

	private Map<String, String> getVersion(String version) {
		version = version.trim();
		boolean checkOldVersion = Boolean.valueOf(
				configuration.getProperty(ConfigProperty.BE_TEA_AGENT_DISPLAY_BE_HOME_ENABLED.getPropertyName(),
						ConfigProperty.BE_TEA_AGENT_DISPLAY_BE_HOME_ENABLED.getDefaultValue()));
		String[] versions = version.split(" ");
		Map<String, String> versionMap = new HashMap<String, String>();
		if (versions.length != 1) {
			int majorVersion = 0;
			int minorVersion = 0;
			int updateVersion = 0;
			int buildVersion = 0;
			boolean isValid = false;

			for (int i = 0; i < versions.length; i++) {
				String v = versions[i];
				String[] parts = v.split("\\.");
				String key = parts[0] + "." + parts[1];
				if (!checkOldVersion) {

					if (Integer.parseInt(parts[0]) >= 5 && Integer.parseInt(parts[0]) >= majorVersion) {
						majorVersion = Integer.parseInt(parts[0]);
						if (Integer.parseInt(parts[1]) >= 3 && Integer.parseInt(parts[1]) >= minorVersion) {
							if (versionMap.containsKey(key)) {
								String versionStr = versionMap.get(key);
								String maxVersion = getMaxVersion(versionStr, v);
								versionMap.put(key, maxVersion.trim());
							} else {
								versionMap.put(key, v.trim());
							}
						}
					}
				} else {
					if (Integer.parseInt(parts[0]) >= majorVersion) {
						majorVersion = Integer.parseInt(parts[0]);
						if (Integer.parseInt(parts[1]) >= minorVersion) {
							if (versionMap.containsKey(key)) {
								String versionStr = versionMap.get(key);
								String maxVersion = getMaxVersion(versionStr, v);
								versionMap.put(key, maxVersion.trim());
							} else {
								versionMap.put(key, v.trim());
							}
						}
					}
				}
			}

		} else {
			versions = version.split("\\.");
			if (checkOldVersion) {
				String key = versions[0] + "." + versions[1];
				versionMap.put(key, version);
			} else {
				String key = versions[0] + "." + versions[1];
				if (Integer.parseInt(versions[0]) >= 5 && Integer.parseInt(versions[1]) >= 3) {
					versionMap.put(key, version);
				}
			}

		}
		return versionMap;
	}

	/**
	 * Get max version
	 * 
	 * @param versionStr
	 * @param v
	 * @return
	 */
	private String getMaxVersion(String versionStr, String v) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern("###");

		String[] part = versionStr.split("\\.");
		String[] part1 = v.split("\\.");

		StringBuilder s1 = new StringBuilder();
		s1.append(part[0]);
		s1.append(part[1]);
		s1.append(part[2]);
		if (part[3].trim().length() != 3)
			s1.append(df.format(part[3]));
		else
			s1.append(part[3]);
		StringBuilder s2 = new StringBuilder();
		s2.append(part1[0]);
		s2.append(part1[1]);
		s2.append(part1[2]);
		if (part1[3].trim().length() != 3)
			s2.append(df.format(part1[3]));
		else
			s2.append(part1[3]);

		int val = (new Integer(s1.toString()).compareTo(new Integer(s2.toString())));
		if (val < 0) {
			return v;
		} else if (val > 0) {
			return versionStr;
		}
		return versionStr;
	}
}
