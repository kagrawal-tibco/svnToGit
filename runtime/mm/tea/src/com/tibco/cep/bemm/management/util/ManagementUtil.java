package com.tibco.cep.bemm.management.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.h2.store.fs.FileUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitsConfig;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.job.BETeaDownloadLogJob;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.GroupJobExecutorService;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfig;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfigs;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceAddException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceValidationException;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;
import com.tibco.cep.bemm.management.service.impl.BETeaDownloadLogEntry;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.tea.agent.be.util.BEAgentUtil;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class ManagementUtil {
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ManagementUtil.class);

	public static void streamCopy(InputStream inputstream, File file) throws IOException {
		ReadableByteChannel readablebytechannel = Channels.newChannel(inputstream);
		WritableByteChannel writablebytechannel = Channels.newChannel(new FileOutputStream(file));
		ByteBuffer bytebuffer;
		for (bytebuffer = ByteBuffer.allocateDirect(1024); readablebytechannel.read(bytebuffer) != -1; bytebuffer
				.compact()) {
			((Buffer)bytebuffer).flip();
			writablebytechannel.write(bytebuffer);
		}
		((Buffer)bytebuffer).flip();
		for (; bytebuffer.hasRemaining(); writablebytechannel.write(bytebuffer))
			;
		inputstream.close();
		writablebytechannel.close();
		readablebytechannel.close();
	}

	/**
	 * This method is used to get the decoded password.
	 * 
	 * @param encodedPwd
	 * @return
	 */
	public static String getDecodedPwd(String encodedPwd) {
		if (encodedPwd == null || encodedPwd.trim().isEmpty())
			return "";

		String pwd = encodedPwd;
		try {
			if (ObfuscationEngine.hasEncryptionPrefix(encodedPwd)) {
				pwd = new String(ObfuscationEngine.decrypt(encodedPwd));
			}
		} catch (AXSecurityException e) {
			// logger.log(Level.ERROR,
			// "Exception occurred while decoding password for host");
		}
		return pwd;
	}

	/**
	 * This method is used to get directory path from file path
	 * 
	 * @param fileFqPath
	 *            - Path of the file.
	 * @return - Directory file path
	 */
	public static String getDir(String fileFqPath) {
		fileFqPath = fileFqPath.replace("\\", "/");
		int idx = fileFqPath.lastIndexOf("/");
		int idx1 = fileFqPath.lastIndexOf('\\');

		if (idx == -1 && idx1 == -1)
			return null;

		if (idx != -1) {
			return fileFqPath.substring(0, idx);
		}

		return fileFqPath.substring(0, idx1);
	}

	/**
	 * This method is used to determine the targeted host.
	 * 
	 * @param targetOs
	 *            - Host details.
	 * @return Targeted host
	 */
	public static String determineMethod(final String targetOs) {

		if (targetOs == null || targetOs.isEmpty()) {
			return null;
		}
		if (targetOs.toLowerCase().startsWith("windows"))
			return "windows";
		else
			return "sftp";
	}

	/**
	 * Get the path on windows when ssh is used.
	 * 
	 * @param dir
	 *            -Path on Windows
	 * @return Path on windows when ssh used.
	 */
	public static String getWinSshPath(String dir) {
		String path = "";
		path = dir.replaceAll("\\\\", "/");
		path = path.replaceAll(":/", "/");
		path = "/cygdrive/" + path;
		return path;
	}

	/**
	 * @return
	 * @throws ObjectCreationException
	 */
	public static Object getInstance(String implPath, Class... params) throws ObjectCreationException {
		try {
			Class agentClass = Class.forName(implPath);
			Object object = agentClass.newInstance();
			return object;
		} catch (ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException e) {
			throw new ObjectCreationException(e);
		}
	}

	public static Map<String, String> getLogLevels() {
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("Off", "off");
		logMap.put("Fatal", "fatal");
		logMap.put("Error", "error");
		logMap.put("Warn", "warn");
		logMap.put("Info", "info");
		logMap.put("Debug", "debug");
		logMap.put("Trace", "trace");
		logMap.put("All", "all");
		return logMap;
	}

	/**
	 * Iterates over the exception stack until it finds a cause matching the
	 * class specified.
	 * 
	 * @return Exception message if exception is instance of exceptClass.
	 *         Returns null otherwise
	 */
	public static String getClassExceptionMsg(Throwable exception, Class exceptClass) {
		if (exception == null)
			return null;

		if (exceptClass.isInstance(exception)) {
			return exception.getLocalizedMessage();
		}

		return getClassExceptionMsg(exception.getCause(), exceptClass);
	}

	public static String getEncodedPwd(String password) {
		if (password == null || password.trim().isEmpty())
			return "";

		String pwd = password;
		try {
			if (!ObfuscationEngine.hasEncryptionPrefix(password))
				pwd = new String(ObfuscationEngine.encrypt(password.toCharArray()));
		} catch (AXSecurityException e) {
			// logger.log(Level.ERROR,
			// "Exception occurred while decoding password for host");
		}
		return pwd;
	}

	public static String formatTime(long time) {
		Object[][] DURATIONS = new Object[][] { new Object[] { 365 * 24 * 60 * 60 * 1000L, "y" }, // a
																									// year
				new Object[] { 30 * 24 * 60 * 60 * 1000L, "m" }, // a month
				new Object[] { 24 * 60 * 60 * 1000L, "d" }, // a day
				new Object[] { 60 * 60 * 1000L, "h" }, // a hour
				new Object[] { 60 * 1000L, "m" }, // a minute
				new Object[] { 1000L, "s" }, // a second
				new Object[] { 1L, "ms" }, // a millisecond
		};
		if (time == 0) {
			return "0ms";
		}
		StringBuilder sb = new StringBuilder();
		int limit = 2;
		for (int i = 0; i < DURATIONS.length; i++) {
			long durationMSecs = (Long) DURATIONS[i][0];
			int t = (int) (time / durationMSecs);
			if (t > 0) {
				time = time % durationMSecs;
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(t + "" + DURATIONS[i][1]);
				limit--;
				if (limit == 0) {
					break;
				}
			}
		}
		return sb.toString();
	}

	public static boolean isLocalhost(String targetHostName, String targetHostAddress) {
		if (com.tibco.cep.bemm.management.util.Constants.LOCALHOST.equalsIgnoreCase(targetHostName)
				|| com.tibco.cep.bemm.management.util.Constants.LOCALHOST.equalsIgnoreCase(targetHostAddress)) {
			return true;
		}
		String localhost = null;
		String localhostAddress = null;
		try {
			localhost = InetAddress.getLocalHost().getHostName();
			localhostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException ex) {
			return false;
		}
		return (targetHostName != null && targetHostName.equalsIgnoreCase(localhost))
				|| (targetHostAddress != null && targetHostAddress.equalsIgnoreCase(localhostAddress));
	}

	/**
	 * Get name for undefined host
	 * 
	 * @param application
	 * @return
	 */
	public static String nextUndefinedHostName(Application application) {
		String nextName = "Undefined_Host_0";

		Collection<Host> hosts = application.getHosts();
		if (null != hosts && !hosts.isEmpty()) {
			List<Integer> noList = new ArrayList<Integer>();
			Iterator<Host> intances = hosts.iterator();
			while (intances.hasNext()) {
				String instanceName = intances.next().getHostName();
				if (null != instanceName && instanceName.startsWith("Undefined_Host_")) {
					String numStr = instanceName.substring(instanceName.lastIndexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			if (noList.size() > 0) {
				Object[] noArray = noList.toArray();
				java.util.Arrays.sort(noArray);
				int num = ((Integer) noArray[noArray.length - 1]).intValue();
				num++;
				nextName = "Undefined_Host_" + num;
			}
		}
		return nextName;
	}

	/**
	 * Get next host id
	 * 
	 * @param application
	 * @return
	 */
	public static String getNextApplicationHostId(Application application) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		Collection<Host> hosts = application.getHosts();
		Iterator<Host> iterator = hosts.iterator();
		String hrId;
		while (iterator.hasNext()) {
			hrId = iterator.next().getHostId();
			if (hrId.startsWith("HR_")) {
				numStr = hrId.substring(hrId.indexOf("_") + 1);
				noList.add(getValidNo(numStr));
			}
		}
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer) noArray[noArray.length - 1]).intValue();
			num++;
			return "HR_" + num;
		}
		return "HR_0";
	}

	/**
	 * Get DU id for undefined instance
	 * 
	 * @param application
	 * @return
	 */
	public static String nextUndefinedDUId(Application application) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		Collection<Host> hosts = application.getHosts();
		for (Host host : hosts) {
			Iterator<ServiceInstance> iterator = host.getInstances().iterator();

			String duId;
			while (iterator.hasNext()) {
				duId = iterator.next().getDuId();
				if (null != duId && duId.startsWith("Undefined_DU_")) {
					numStr = duId.substring(duId.lastIndexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
		}

		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer) noArray[noArray.length - 1]).intValue();
			num++;
			return "Undefined_DU_" + num;
		}
		return "Undefined_DU_0";
	}

	/**
	 * Get name for undefined insatnce
	 * 
	 * @param application
	 * @return
	 */
	public static String nextUndefinedInstanceName(Application application) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		Collection<Host> hosts = application.getHosts();
		for (Host host : hosts) {
			Iterator<ServiceInstance> iterator = host.getInstances().iterator();

			String duId;
			while (iterator.hasNext()) {
				duId = iterator.next().getName();
				if (duId.startsWith("Undefined_Instance_")) {
					numStr = duId.substring(duId.lastIndexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
		}

		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer) noArray[noArray.length - 1]).intValue();
			num++;
			return "Undefined_Instance_" + num;
		}

		return "Undefined_Instance_0";
	}

	private static int getValidNo(String no) {
		int n;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return 0;
		}
		return n;
	}

	public static String getNextMasterHostId(Map<String, MasterHost> masterHosts) {
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.GENERATING_NEXT_HOST_ID));
			List<Integer> noList = new ArrayList<Integer>();
			String numStr;
			for (String hostId : masterHosts.keySet()) {

				if (hostId.startsWith("HR_")) {
					numStr = hostId.substring(hostId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			if (noList.size() > 0) {
				Object[] noArray = noList.toArray();
				java.util.Arrays.sort(noArray);
				int num = ((Integer) noArray[noArray.length - 1]).intValue();
				num++;
				return "HR_" + num;
			}

		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		return "HR_0";
	}

	/**
	 * Get next master host name
	 * 
	 * @param masterHosts
	 * @param hostname
	 * @return
	 */
	public static String getNextMasterHostName(Map<String, MasterHost> masterHosts, String hostname) {

		Set<Integer> noS = new HashSet<Integer>();
		String numStr;
		for (MasterHost masterHost : masterHosts.values()) {
			if (masterHost.getHostName().trim().startsWith(hostname.trim())) {
				numStr = masterHost.getHostName().substring(masterHost.getHostName().lastIndexOf("_") + 1);
				noS.add(getValidNo(numStr));
			}

		}
		if (noS.size() > 0) {
			Object[] noArray = noS.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer) noArray[noArray.length - 1]).intValue();
			num++;
			return hostname + "_" + num;
		}
		return hostname + "_0";

	}

	/**
	 * Set application Running status
	 * 
	 * @param application
	 */
	public static void setRunningStatus(Application application) {

		boolean isAllRunnning = false;
		boolean isAllStopped = false;
		Collection<Host> hosts = application.getHosts();

		for (Host host : hosts) {
			if (null != host) {
				for (ServiceInstance serviceInstance : host.getInstances()) {
					if (serviceInstance.isRunning()) {
						isAllRunnning = true;
					} else {
						isAllRunnning = false;
						break;
					}
				}
			}
			if (!isAllRunnning) {
				break;
			}
		}
		if (!isAllRunnning) {
			for (Host host : hosts) {
				if (null != host) {
					for (ServiceInstance serviceInstance : host.getInstances()) {
						if (serviceInstance.isStopped()) {
							isAllStopped = true;
						} else {
							if (!serviceInstance.isRunning()) {
								isAllStopped = false;
								break;
							}
						}
					}
				}
				if (!isAllStopped) {
					break;
				}
			}
			if (!isAllStopped) {
				application.setStatus(BETeaAgentStatus.OTHER.getStatus());
			} else {
				application.setStatus(BETeaAgentStatus.STOPPED.getStatus());
			}
		} else {
			application.setStatus(BETeaAgentStatus.RUNNING.getStatus());
		}

	}

	public static DataSource createZip(Map<String, String> threaddumps, String name) {

		String tempFile = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String threadDumpFilename = name + "_" + currentTimeStamp;
		try {
			tempFile = FileUtils.createTempFile(threadDumpFilename, "_Thread_Dump.zip", true, true);
			fos = new FileOutputStream(tempFile);
			zos = new ZipOutputStream(fos);
			for (Entry<String, String> entry : threaddumps.entrySet()) {
				if (null != entry) {
					String fileName = entry.getKey().split("/")[2] + ".txt";
					ZipEntry zipEntry = new ZipEntry(fileName);
					zos.putNextEntry(zipEntry);
					zos.write(entry.getValue().getBytes());
					zos.closeEntry();
				}
			}
		} catch (IOException e1) {
		} finally {
			if (null != zos) {
				try {
					zos.close();
				} catch (IOException e) {

				}
			}
		}
		if (null != tempFile) {
			return new FileDataSource(tempFile) {

				/*
				 * (non-Javadoc)
				 * 
				 * @see javax.activation.FileDataSource#getName()
				 */
				@Override
				public String getName() {
					return threadDumpFilename + "_Thread_Dump.zip";
				}

			};
		}
		return null;
	}

	/**
	 * Delete the directory structure
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void delete(File file) throws FileNotFoundException {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				if (null != files && files.length > 0) {
					for (File file2 : files) {
						delete(file2);
					}
				}
			}
			file.delete();
		} else {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
	}

	/**
	 * Load Properties from CDD
	 * 
	 * @param application
	 *            - Application Instance
	 * @param properties
	 *            - Map of properties
	 */
	public static void loadCDDProps(Application application, Map<Object, Object> properties) {
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.APPLICATION_LOADING_BE_PROPERTIES_FROM_CDD, application.getName()));
			ClusterConfig clusterConfig = BEMMServiceProviderManager.getInstance()
					.getBEApplicationsManagementDataStoreService().fetchApplicationCDD(application.getName());
			
			ProcessingUnitsConfig processingUnitsConfig = clusterConfig.getProcessingUnits();
			List<ProcessingUnitConfig> processingUnitConfigs = processingUnitsConfig.getProcessingUnit();
			for (ProcessingUnitConfig processingUnitConfig : processingUnitConfigs) {
				if(processingUnitConfig.getPropertyGroup() != null){
					properties.putAll(processingUnitConfig.getPropertyGroup().toProperties());
				}
			}
			if (null != clusterConfig) {
				properties.putAll(clusterConfig.getPropertyGroup().toProperties());
			}
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.APPLICATION_LOADING_BE_PROPERTIES_FROM_CDD, application.getName()));
		
		} catch (Exception e) {
		}
	}

	/**
	 * Get default deployment path
	 * 
	 * @param deploymentPath
	 * @param origInstance
	 * @param host
	 * @return
	 * @throws BEServiceInstanceAddException
	 */
	public static String getDefaultDeploymentPath(String deploymentPath, ServiceInstance instance, Host host)
			throws BEServiceInstanceValidationException {
		// If user gives the empty deployment path then take deployment path
		// from host
		if (null == deploymentPath || deploymentPath.trim().isEmpty()) {
			deploymentPath = host.getMasterHost().getDeploymentPath();
		}
		// If deployment path from host is empty then take path from original
		// instance
		if (null == deploymentPath || deploymentPath.trim().isEmpty()) {
			if (null != instance)
				deploymentPath = instance.getDeploymentPath();
		}

		// If original instance deployment path is empty then throw exception.
		if (null == deploymentPath || deploymentPath.trim().isEmpty()) {
			try {
				throw new BEServiceInstanceValidationException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DEPLOYMENT_PATH_NOT_EMPTY));
			} catch (ObjectCreationException e) {
				e.printStackTrace();
			}
		}
		String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
		if (startPuMethod.startsWith("windows")) {
			deploymentPath = deploymentPath.replace("/", "\\");
		}
		return deploymentPath;
	}

	/**
	 * Check path exist or not
	 * 
	 * @param deploymentPath
	 * @param config
	 * @param host
	 * @return
	 */
	public static boolean isPathExist(String deploymentPath, ServiceInstance config, Host host) {
		String password = host.getPassword();
		String username = host.getUserName();

		// Create the JSCH client.
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		JschClient jschClient = new JschClient(username, decodedPassword, host.getHostIp(), host.getSshPort());

		String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
		String path = deploymentPath;
		if (startPuMethod.startsWith("windows")) {
			path = BEAgentUtil.getWinSshPath(deploymentPath);
		}

		return jschClient.isPathExits(path);
	}

	/**
	 * Get the byte array from passed stream.
	 * 
	 * @param stFileInputStream
	 *            -Input Stream
	 * @return Byte Array
	 */
	public static byte[] getByteArrayFromStream(InputStream stFileInputStream) {
		ByteArrayOutputStream buffer = null;
		try {
			buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = stFileInputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			return buffer.toByteArray();
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
		} finally {
			if (null != buffer) {
				try {
					buffer.flush();
					buffer.close();

				} catch (IOException e) {
				}
			}
			if (null != stFileInputStream)
				try {
					stFileInputStream.close();
				} catch (IOException e) {
				}
		}

		return null;
	}

	/**
	 * Get overridden properties
	 * 
	 * @param traPath
	 * @param jschClient
	 * @param properties
	 * @param instanceName
	 */
	public static void loadTraProperties(Map<String, String> properties, InputStream hostTraFileStream) {
		if (null != hostTraFileStream) {
			try {
				Properties traProperties = new Properties();
				traProperties.load(hostTraFileStream);
				for (Entry<Object, Object> entry : traProperties.entrySet()) {
					properties.put(entry.getKey().toString(), entry.getValue().toString());
				}

			} catch (IOException e) {
				LOGGER.log(Level.DEBUG, e.getMessage());
			} finally {
				if (null != hostTraFileStream)
					try {
						hostTraFileStream.close();
					} catch (IOException e) {
					}
			}
		}
	}

	/**
	 * Check host TRA exist or not
	 * 
	 * @param hostId
	 * @param hasExist
	 * @param application
	 * @return
	 */
	public static boolean isHostTraConfigExist(String hostId, Application application) {
		HostTraConfigs configs = application.getHostTraConfigs();
		boolean hasExist = false;
		if (null != configs) {
			List<HostTraConfig> hostTraConfigs = configs.getHostTraConfig();
			if (null != hostTraConfigs && !hostTraConfigs.isEmpty()) {
				for (HostTraConfig hostTraConfig : hostTraConfigs) {

					if (null != hostTraConfig && hostTraConfig.getHostId().equals(hostId)) {
						try {
							LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.HOST_OVERRIDDEN_TRA_CONFIG_EXIST));
							hasExist = true;
							break;
						} catch (ObjectCreationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return hasExist;
	}

	public static boolean isPathExist(ChannelSftp sftpChannel, String path) throws ObjectCreationException {
		boolean isExits = false;
		try {
			if (null != sftpChannel) {
				SftpATTRS attrs = sftpChannel.stat(path);
				if (null != attrs) {
					isExits = true;
				}
			}
		} catch (SftpException e) {
			LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.NOT_EXIST, path));
		}
		return isExits;
	}

	/**
	 * Create Zip File
	 * 
	 * @param filteredInstance
	 * @param tempFile
	 * @param poolService
	 * @param timeout
	 * @throws BEDownloadLogException
	 */
	public static void createLogZip(List<ServiceInstance> filteredInstance, Map<String, String> instanceLogLocation, String tempFile,
			GroupJobExecutorService poolService, File beTeaTempFileLocation, int timeout, boolean isASLog)
			throws BEDownloadLogException {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		int errorsCnt = 0;
		int fnfCnt = 0;
		try {
			fos = new FileOutputStream(tempFile);
			zos = new ZipOutputStream(fos);
			List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

			for (ServiceInstance instance : filteredInstance) {
				SshConfig sshConfig = new SshConfig();
				Host host = instance.getHost();
				sshConfig.setHostIp(host.getHostIp());
				sshConfig.setPassword(host.getPassword());
				sshConfig.setPort(host.getSshPort());
				sshConfig.setUserName(host.getUserName());

				jobExecutionContexts.add(new JSchGroupJobExecutionContext(instance, sshConfig));
			}
			List<Object> results = poolService
					.submitJobs(new BETeaDownloadLogJob(beTeaTempFileLocation, instanceLogLocation, timeout, isASLog), jobExecutionContexts);
			for (Object result : results) {

				if (null != result) {
					if (result instanceof BETeaDownloadLogEntry) {
						BETeaDownloadLogEntry downloadLogEntry = (BETeaDownloadLogEntry) result;
						if (downloadLogEntry.getErrorCode() == 0) {
							InputStream inputStream = new FileInputStream(downloadLogEntry.getLogFilePath());
							if (null != inputStream) {
								ZipEntry zipEntry = new ZipEntry(downloadLogEntry.getLogfilename());
								zos.putNextEntry(zipEntry);
								byte[] bytes = new byte[1024];
								int length;
								while ((length = inputStream.read(bytes)) >= 0) {
									zos.write(bytes, 0, length);
								}
								inputStream.close();
								zos.closeEntry();
							}
						} else {

							if (downloadLogEntry.getErrorCode() == 404)
								fnfCnt++;
							else
								errorsCnt++;

						}
					} else {
						if (result instanceof FileNotFoundException)
							fnfCnt++;
						else
							errorsCnt++;
					}
				} else
					errorsCnt++;

			}
		} catch (IOException e) {
			errorsCnt++;
		} finally {
			if (null != zos) {
				try {
					zos.close();
				} catch (IOException e) {
				}
			}
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

		try {
			if (fnfCnt > 0 && filteredInstance.size() == fnfCnt) {
				throw new BEDownloadLogException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INSTANCE_LOG_FILE_NOT_EXIST_ERROR));
			}
			if (errorsCnt > 0 && filteredInstance.size() == errorsCnt) {
					throw new BEDownloadLogException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DOWNLOAD_LOG_FILE_FOR_SELECTED_INSTANCE_ERROR));
			}
			if (fnfCnt > 0 && errorsCnt > 0) {
				if (fnfCnt > errorsCnt) {
						throw new BEDownloadLogException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INSTANCE_LOG_FILE_NOT_EXIST_ERROR));
				} else {
						throw new BEDownloadLogException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.DOWNLOAD_LOG_FILE_FOR_SELECTED_INSTANCE_ERROR));
				}
			}
		} catch (ObjectCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to execute the given command using exec channel
	 * 
	 * @param command
	 *            - Command to be executed.
	 * @param timout-
	 *            JSCH channel timeout
	 * @throws JschCommandFailException
	 *             - This class is used to throw exception when JSCH command
	 *             execution fails.
	 */
	public static void executeCommand(String command, Session session, boolean waitForCompletion, int timeout,
			int maxRetry, int threadSleep) throws JschCommandFailException {
		ChannelExec channelExec = null;
		try {

			LOGGER.log(Level.DEBUG, "Executing " + command + " command");
			channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(command);
			channelExec.connect(timeout);
			if (waitForCompletion)
				if (channelExec != null) {
					for (int loop = 0; loop < maxRetry; loop++) {
						try {
							if (channelExec.isClosed())
								break;
							Thread.sleep(threadSleep);
						} catch (InterruptedException ex) {
							LOGGER.log(Level.ERROR, ex, ex.getMessage());
						}
					}
				}

		} catch (JSchException e) {
			throw new JschCommandFailException(e);
		} finally {
			if (null != channelExec)
				channelExec.disconnect();
		}
	}

	/**
	 * This method is used to execute the given command using exec channel
	 * 
	 * @param command
	 *            - Command to be executed.
	 * @param timeout
	 * @param timout-
	 *            JSCH channel timeout
	 * @throws JschCommandFailException
	 *             - This class is used to throw exception when JSCH command
	 *             execution fails.
	 */
	public static String getOSDetails(String command, Session session, int timeout) throws JschCommandFailException {

		StringBuilder builder = new StringBuilder();
		Channel channel = null;
		InputStream in = null;
		try {

			// exec 'scp -t rfile' remotely
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			in = channel.getInputStream();
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect(timeout);
			
			byte[] buf = new byte[1024];
			int b = in.read(buf);
			if (b == 1 || b == 2) {
				StringBuffer sb = new StringBuffer();
				int c;
				do {
					c = in.read();
					sb.append((char) c);
				} while (c != '\n');
				if (b == 1) {
					if (sb.toString().contains("No such file or directory")) {
						throw new FileNotFoundException(sb.toString());
					}
					throw new IOException(sb.toString());
				}
				if (b == 2) { // fatal error
					throw new IOException(sb.toString());
				}
			}

			command += "\n";
			out.write(command.getBytes());
			out.flush();
			if (b > 0)
				builder.append(new String(buf, 0, b));
			// send a content of lfile
			while (true) {
				int len = in.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				builder.append(new String(buf, 0, len));
			}
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();
			if (checkAck(in) != 0) {
			}
			out.close();

		} catch (Exception e) {
			throw new JschCommandFailException(e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
			if (null != channel) {
				channel.disconnect();
			}

		}
		return builder.toString();
	}

	public static void executeShellScript(List<String> shellScripts, String remoteTempShellFileName, Session session,
			boolean waitForCompletion, int timeout, int maxRetry, int threadSleep) throws JschCommandFailException {
		Channel channel = null;
		File shellScript = null;
		FileInputStream fin = null;
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.EXECUTING_SCRIPT, shellScripts.toString()));

			channel = session.openChannel("shell");

			shellScript = createShellScript(shellScripts, remoteTempShellFileName);
			fin = new FileInputStream(shellScript);
			byte fileContent[] = new byte[(int) shellScript.length()];
			fin.read(fileContent);
			InputStream in = new ByteArrayInputStream(fileContent);
			channel.setInputStream(in);
			channel.connect(timeout + timeout);
			if (waitForCompletion) {
				if (channel != null) {
					for (int loop = 0; loop < maxRetry; loop++) {
						try {
							if (channel.isClosed())
								break;
							Thread.sleep(threadSleep);
						} catch (InterruptedException ex) {
							LOGGER.log(Level.ERROR, ex, ex.getMessage());
						}
					}
				}
			}

		} catch (JSchException e) {
			throw new JschCommandFailException(e);
		} catch (FileNotFoundException e) {
			throw new JschCommandFailException(e);
		} catch (IOException e) {
			throw new JschCommandFailException(e);
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		} finally {
			if (null != channel)
				channel.disconnect();
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static File createShellScript(List<String> shellScripts, String tempShellFileName) {
		File fileStream = new File(tempShellFileName);

		try {
			PrintStream outStream = new PrintStream(new FileOutputStream(fileStream));
			for (String ss : shellScripts) {
				outStream.println(ss);
			}
			outStream.close();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
		}
		return fileStream;
	}

	/**
	 * Get the service instances matched with key from passed list of keys
	 * 
	 * @param instanceKeys
	 *            - List of instance keys
	 * @param serviceInstances
	 *            - List of instance
	 * @return
	 */
	public static List<ServiceInstance> getFilteredServiceInstances(List<String> instanceKeys,
			List<ServiceInstance> serviceInstances, BETeaOperationEnum operation) {
		List<ServiceInstance> filteredInstance = new ArrayList<ServiceInstance>();

		if (null != serviceInstances) {
			if (null == operation) {
				for (ServiceInstance serviceInstance : serviceInstances) {
					if (null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())
							&& !BETeaAgentStatus.DEPLOYED.getStatus().equals(serviceInstance.getDeploymentStatus())) {
						filteredInstance.add(serviceInstance);
					}
				}
			} else {
				switch (operation) {
				case DEPLOY:
					for (ServiceInstance serviceInstance : serviceInstances) {
						if (null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())
								&& !BETeaAgentStatus.DEPLOYED.getStatus()
										.equals(serviceInstance.getDeploymentStatus())) {
							filteredInstance.add(serviceInstance);
						}
					}
					break;
				case UNDEPLOY:
					for (ServiceInstance serviceInstance : serviceInstances) {
						if (null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())
								&& !(serviceInstance.isRunning() || serviceInstance.isStarting()
										|| serviceInstance.isStopping())
								&& serviceInstance.getDeployed()
								&& (BETeaAgentStatus.DEPLOYED.getStatus().equals(serviceInstance.getDeploymentStatus())
										|| BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus()
												.equals(serviceInstance.getDeploymentStatus())
										|| BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus()
												.equals(serviceInstance.getDeploymentStatus())
										|| BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
												.equals(serviceInstance.getDeploymentStatus()))) {
							filteredInstance.add(serviceInstance);
						}
					}
					break;
				case START:
					for (ServiceInstance serviceInstance : serviceInstances) {
						if (null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())
								&& BETeaAgentStatus.STOPPED.getStatus().equals(serviceInstance.getStatus()) && !BETeaAgentStatus.UNDEPLOYED.getStatus().equals(serviceInstance.getDeploymentStatus()) ) {
							filteredInstance.add(serviceInstance);
						}
					}
					break;
				case STOP:

				case THREAD_DUMP:
				case INVOKE_MM_OP:
					for (ServiceInstance serviceInstance : serviceInstances) {
						if (null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())
								&& BETeaAgentStatus.RUNNING.getStatus().equals(serviceInstance.getStatus())) {
							filteredInstance.add(serviceInstance);
						}
					}
					break;
				case KILL:
					for (ServiceInstance serviceInstance : serviceInstances) {
						if (null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())) {
							filteredInstance.add(serviceInstance);
						}
					}
					break;
				case DELETE:
					for (ServiceInstance serviceInstance : serviceInstances) {
						boolean condition = null != serviceInstance && instanceKeys.contains(serviceInstance.getKey())
								&& BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
										.equals(serviceInstance.getDeploymentStatus())
								&& !(BETeaAgentStatus.STOPPING.getStatus().equals(serviceInstance.getStatus())
										|| BETeaAgentStatus.STARTING.getStatus().equals(serviceInstance.getStatus())
										|| BETeaAgentStatus.RUNNING.getStatus().equals(serviceInstance.getStatus()))
												? true : false;
						if (condition) {
							filteredInstance.add(serviceInstance);
						}
					}

				default:
					break;
				}
			}

		}
		return filteredInstance;
	}

	/**
	 * Start a new Jsch session
	 * 
	 * @param hostIp
	 * @param username
	 * @param password
	 * @param port
	 * @param connectionTimeout
	 * @return
	 * @throws Exception
	 */
	public static Session connectJSchSession(String hostIp, String username, String password, int port,
			int connectionTimeout) throws Exception {
		JSch jsch = new JSch();

		String privateKeyFileLoc = System
				.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_PRIVATEKEY_FILE.getPropertyName());
		final String passPhrase = System
				.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_PRIVATEKEY_PASSPHRASE.getPropertyName());

		Session session = null;

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
		if (privateKeyFileLoc != null) {// attempt authentication using
										// private/public keys.
			LOGGER.log(Level.DEBUG, "Attempting authentication to %s@%s:%s using PRIVATE KEY '%s'", username, hostIp,
					port, privateKeyFileLoc);

			if (passPhrase == null) {
				jsch.addIdentity(privateKeyFileLoc);
			} else {
				jsch.addIdentity(privateKeyFileLoc, passPhrase);
			}

			try {
				session = jsch.getSession(username, hostIp, port);
				session.setTimeout(connectionTimeout);
				session.setConfig(config);
				session.connect();
			} catch (Exception e) {
				LOGGER.log(Level.DEBUG, "Authentication to %s@%s:%s using PRIVATE KEY '%s' FAILED. %s", username,
						hostIp, port, privateKeyFileLoc, e.getMessage());
				jsch.removeAllIdentity();
				privateKeyFileLoc = null;
			}
		}

		// if private key authentication not set or failed proceed with password
		// authentication
		if (privateKeyFileLoc == null) {

			LOGGER.log(Level.DEBUG, "Attempting PASSWORD authentication to %s@%s:%s", username, hostIp, port);

			session = jsch.getSession(username, hostIp, port);
			session.setTimeout(connectionTimeout);
			session.setConfig(config);
			session.setPassword(password);
			session.connect();
			LOGGER.log(Level.DEBUG, "Connected to %s@%s:%s", username, hostIp, port);
		}

		return session;
	}

	public static void downloadRemoteFile(String cdPath, String remoteFile, Session session, String lfile, int timeout)
			throws JschCommandFailException {

		FileOutputStream fos = null;
		String prefix = null;
		if (new File(lfile).isDirectory()) {
			prefix = lfile + File.separator;
		}
		Channel channel = null;
		try {

			// exec 'scp -f rfile' remotely
			channel = session.openChannel("exec");

			String command = "";
			if(cdPath!=null){
				command = cdPath + ";";
			}
			command = command + "scp -f " + remoteFile;
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect(timeout);

			byte[] buf = new byte[1024];

			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			while (true) {
				int c = checkAck(in);
				if (c != 'C') {
					break;
				}

				// read '0644 '
				in.read(buf, 0, 5);

				long filesize = 0L;
				while (true) {
					if (in.read(buf, 0, 1) < 0) {
						// error
						break;
					}
					if (buf[0] == ' ')
						break;
					filesize = filesize * 10L + (long) (buf[0] - '0');
				}

				String file = null;
				for (int i = 0;; i++) {
					in.read(buf, i, 1);
					if (buf[i] == (byte) 0x0a) {
						file = new String(buf, 0, i);
						break;
					}
				}

				// System.out.println("filesize="+filesize+", file="+file);

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();

				// read a content of lfile
				fos = new FileOutputStream(prefix == null ? lfile : prefix + file);
				int foo;
				while (true) {
					if (buf.length < filesize)
						foo = buf.length;
					else
						foo = (int) filesize;
					foo = in.read(buf, 0, foo);
					if (foo < 0) {
						// error
						break;
					}
					fos.write(buf, 0, foo);
					filesize -= foo;
					if (filesize == 0L)
						break;
				}
				fos.flush();
				fos.close();
				fos = null;

				if (checkAck(in) != 0) {
				}

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();
			}

		} catch (Exception e) {
			throw new JschCommandFailException(e);
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (Exception ee) {
			}

			if (null != channel)
				channel.disconnect();
		}
	}

	public static void uploadToRemoteMahine(String remoteFile, Session session, String localFile, int timeout)
			throws JschCommandFailException {
		FileInputStream fis = null;
		Channel channel = null;
		try {

			// exec 'scp -t rfile' remotely
			channel = session.openChannel("exec");
			String command = "scp -t " + remoteFile;
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect(timeout);

			if (checkAck(in) != 0) {
			}

			File _lfile = new File(localFile);

			// send "C0644 filesize filename", where filename should not include
			// '/'
			long filesize = _lfile.length();
			command = "C0644 " + filesize + " ";
			if (localFile.lastIndexOf('/') > 0) {
				command += localFile.substring(localFile.lastIndexOf('/') + 1);
			} else {
				command += localFile;
			}
			command += "\n";
			out.write(command.getBytes());
			out.flush();

			if (checkAck(in) != 0) {
			}

			// send a content of lfile
			fis = new FileInputStream(localFile);
			byte[] buf = new byte[1024];
			while (true) {
				int len = fis.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				out.write(buf, 0, len); // out.flush();
			}
			fis.close();
			fis = null;
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			if (checkAck(in) != 0) {
			}
			out.close();

		} catch (Exception e) {
			throw new JschCommandFailException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception ee) {
			}
			if (null != channel) {
				channel.disconnect();
			}

		}
	}

	private static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) {
				if (sb.toString().contains("No such file or directory")) {
					throw new FileNotFoundException(sb.toString());
				}
				throw new IOException(sb.toString());
			}
			if (b == 2) { // fatal error
				throw new IOException(sb.toString());
			}
		}

		return b;
	}

	public static Object readRemoteFile(String remoteFile, Session session, int timeout)
			throws JschCommandFailException {

		ChannelSftp channel = null;
		InputStream stream = null;
		String line = null;
		List<String> lines = new ArrayList<String>();
		try {
			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			stream = channel.get(remoteFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));

			while ((line = br.readLine()) != null) {
				if (!line.endsWith("prodInfo")) {
					String version = line.substring(line.lastIndexOf("/") + 1, line.length());
					int v1 = Integer.valueOf(version.split("\\.")[0].trim());
					int v2 = Integer.valueOf(version.split("\\.")[1].trim());
					if (v1 >= 5 && v2 >= 3) {
						lines.add(line);
					}
				}
				LOGGER.log(Level.DEBUG, line);
			}
			return lines;
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new JschCommandFailException(e);
		} finally {

			if (null != channel) {
				channel.disconnect();
			}
			try {
				stream.close();
			} catch (IOException e) {
			}

		}
	}

	public static String fireTailCommand(Session session, String command, int timeout) throws JschCommandFailException {

		StringBuilder builder = new StringBuilder();
		Channel channel = null;
		try {

			// exec 'scp -t rfile' remotely
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect(timeout);

			//if (!channel.isConnected())
				//return builder.toString();
			byte[] buf = new byte[1024];
			int b = in.read(buf);
			if (b == 1 || b == 2) {
				StringBuffer sb = new StringBuffer();
				int c;
				do {
					c = in.read();
					sb.append((char) c);
				} while (c != '\n');
				if (b == 1) {
					if (sb.toString().contains("No such file or directory")) {
						throw new FileNotFoundException(sb.toString());
					}
					throw new IOException(sb.toString());
				}
				if (b == 2) { // fatal error
					throw new IOException(sb.toString());
				}
			}

			command += "\n";
			out.write(command.getBytes());
			out.flush();
			if (b > 0)
				builder.append(new String(buf, 0, b));
			// send a content of lfile
			while (true) {
				int len = in.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				builder.append(new String(buf, 0, len));
			}
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();
			if (checkAck(in) != 0) {
			}
			out.close();

		} catch (Exception e) {
			throw new JschCommandFailException(e);
		} finally {

			if (null != channel) {
				channel.disconnect();
			}

		}
		return builder.toString();
	}

	/**
	 * This method is used to execute the given command using exec channel
	 * 
	 * @param command
	 *            - Command to be executed.
	 * @param timeout
	 * @param timout-
	 *            JSCH channel timeout
	 * @throws JschCommandFailException
	 *             - This class is used to throw exception when JSCH command
	 *             execution fails.
	 */
	public static String getBEHome(String command, Session session, int timeout) throws JschCommandFailException {

		StringBuilder builder = new StringBuilder();
		Channel channel = null;
		try {

			// exec 'scp -t rfile' remotely
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();
			((ChannelExec)channel).setErrStream(System.err);
			channel.connect(timeout);

			
			byte[] buf = new byte[1024];
			int b = in.read(buf);
			if (b == 1 || b == 2) {
				StringBuffer sb = new StringBuffer();
				int c;
				do {
					c = in.read();
					sb.append((char) c);
				} while (c != '\n');
				if (b == 1) {
					if (sb.toString().contains("No such file or directory")) {
						throw new FileNotFoundException(sb.toString());
					}
					throw new IOException(sb.toString());
				}
				if (b == 2) { // fatal error
					throw new IOException(sb.toString());
				}
			}

			command += "\n";
			out.write(command.getBytes());
			out.flush();
			if (b > 0)
				builder.append(new String(buf, 0, b));
			// send a content of lfile
			while (true) {
				int len = in.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				builder.append(new String(buf, 0, len));
			}
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();
			if (checkAck(in) != 0) {
			}
			out.close();

		} catch (Exception e) {
			throw new JschCommandFailException(e);
		} finally {

			if (null != channel) {
				channel.disconnect();
			}

		}
		return builder.toString();
	}

	public static String resolveGV(String name, BEApplicationsManagementDataStoreService<?> dataStoreService,String applicationName) {
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = dataStoreService.fetchApplicationArchive(applicationName);
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, "Failed to get global variables =");
		}
		if(null!=globalVariableDescriptors && !globalVariableDescriptors.isEmpty()){
			for (GlobalVariableDescriptor globalVariableDescriptor : globalVariableDescriptors) {
				if(null!=globalVariableDescriptor && globalVariableDescriptor.getName().equals(name.replaceAll("%", ""))){
					name=globalVariableDescriptor.getValueAsString();
					break;
				}
			}
		}
		return name;
	}
}
