package com.tibco.cep.bemm.persistence.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.common.audit.AuditRecords;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfigs;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.exception.BEMasterHostSaveException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceSaveException;
import com.tibco.cep.bemm.management.exception.FileSaveException;
import com.tibco.cep.bemm.management.service.BEApplicationCDDCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.GroupOperationUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.persistence.service.BEApplicationExportImportSerializer;
import com.tibco.cep.bemm.persistence.service.BEApplicationSerializer;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.service.BEMasterHostSerializer;
import com.tibco.cep.bemm.persistence.service.exception.BEApplicationProfileNotExistException;
import com.tibco.cep.bemm.persistence.service.exception.BEHostTRASaveException;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

/**
 * File based Persistent Store Impl for the managed Applications
 * 
 * @author vdhumal
 * 
 */
public class BEApplicationsManagementDataFileStoreServiceImpl extends AbstractStartStopServiceImpl
		implements BEApplicationsManagementDataStoreService<File> {

	private static Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(BEApplicationsManagementDataFileStoreServiceImpl.class);

	private static final String DEPLOYMENT_VARIABLES_JAXB_PACKAGE = "com.tibco.cep.bemm.model.impl";
	private static final String AUDIT_RECORDS_JAXB_PACKAGE = "com.tibco.cep.bemm.common.audit";
	private static final String APPLICATION_TRA_CONFIGS_JAXB_PACKAGE = "com.tibco.cep.bemm.common.tra.config.model";
	
	private File applicationDataStoreLocation = null;
	private File tempFileLocation = null;
	private BEApplicationSerializer beApplicationSerializer = null;
	private BEApplicationExportImportSerializer applicationExportImportSerializer = null;
	private BEMasterHostSerializer masterSerializer = null;
	private Set<String> excludedProperties = new HashSet<String>();
	private BEApplicationGVCacheService<?> applicationGVCacheService;
	private BEApplicationCDDCacheService<?> applicationCDDCacheService;
	private MessageService messageService;

	@Override
	public void init(Properties configuration) throws ServiceInitializationException {
		String topologyRepoDir = (String) ConfigProperty.BE_TEA_AGENT_APPLICATION_DATASTORE.getValue(configuration);
		this.applicationDataStoreLocation = new File(topologyRepoDir);

		if (!this.applicationDataStoreLocation.exists()) {
			try {
				Files.createDirectories(this.applicationDataStoreLocation.toPath());
			} catch (IOException ioex) {
				throw new ServiceInitializationException(ioex);
			}
		}
		this.tempFileLocation = new File(applicationDataStoreLocation, Constants.BE_TEA_AGENT_TEMP_DIR);
		if (!this.tempFileLocation.exists()) {
			try {
				Files.createDirectories(this.tempFileLocation.toPath());
			} catch (IOException ioex) {
				throw new ServiceInitializationException(ioex);
			}
		}
		this.beApplicationSerializer = new BEApplicationSiteTopologySerializer(
				this.applicationDataStoreLocation.getAbsolutePath());
		this.applicationExportImportSerializer = new BEApplicationExportImportSerializerImpl();
		this.masterSerializer = new BEMasterHostSerializerImpl(this.applicationDataStoreLocation.getAbsolutePath());
		try {
			applicationCDDCacheService = BEMMServiceProviderManager.getInstance().getBEApplicationCDDCacheService();
			applicationGVCacheService = BEMMServiceProviderManager.getInstance().getBEApplicationGVCacheService();
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			throw new ServiceInitializationException(e);
		}
	}

	@Override
	public File getApplicationManagementDataStore() {
		return applicationDataStoreLocation;
	}

	@Override
	public Collection<Application> fetchAllApplicationTopologies() throws Exception {
		Collection<Application> applicationTopologies = new ArrayList<>();
		File[] appdirs = applicationDataStoreLocation.listFiles();
		if (null != appdirs) {
			for (File file : appdirs) {
				if (file.isDirectory()) {
					// Get the ST files
					File[] stFiles = file.listFiles(new FilenameFilter() {

						@Override
						public boolean accept(File paramFile, String fileName) {
							return fileName.endsWith(beApplicationSerializer.getContentType()) ? true : false;
						}
					});
					for (File stFile : stFiles) {
						InputStream fileInputStream = null;
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						try {
							fileInputStream = new FileInputStream(stFile);

							int nRead;
							byte[] data = new byte[16384];
							while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
								buffer.write(data, 0, nRead);
							}

							Application application = beApplicationSerializer.deserialize(buffer.toByteArray(), this);
							applicationTopologies.add(application);
						} finally {
							buffer.flush();
							buffer.close();
							if (fileInputStream != null) {
								fileInputStream.close();
							}
						}
					}
				}
			}
		}

		return applicationTopologies;
	}

	@Override
	public Application fetchApplicationTopology(String applicationName) {
		FileInputStream fileInputStream = null;
		Application application = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			File stFile = new File(applicationDataStoreLocation,
					applicationName + File.separatorChar + applicationName + ".st");
			fileInputStream = new FileInputStream(stFile);
			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			application = beApplicationSerializer.deserialize(buffer.toByteArray(), this);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} finally {
			try {
				buffer.flush();
				buffer.close();
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException ex) {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}
		return application;
	}
	
	@Override
	public void copyApplicationProfile(Application application, String newProfileName, String oldProfileName) {
		String name = application.getName();
		File oldProfileFile = new File(applicationDataStoreLocation, name + "/" + oldProfileName + ".properties");
		File newProfileFile = new File(applicationDataStoreLocation, name + "/" + newProfileName + ".properties");
		try {
			Files.copy(oldProfileFile.toPath(), newProfileFile.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void storeApplicationTopology(Application application) throws BEApplicationSaveException {
		ByteArrayInputStream bais = null;
		try {
			byte[] siteTopologyContents = beApplicationSerializer.serialize(application);
			bais = new ByteArrayInputStream(siteTopologyContents);
			saveConfigResource(application.getName(), bais,
					application.getName() + beApplicationSerializer.getContentType());
		} catch (Exception ex) {
			throw new BEApplicationSaveException(ex);
		} finally {

			if (null != bais) {
				try {
					bais.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}
		}
	}

	@Override
	public ClusterConfig fetchApplicationCDD(String applicationName) {
		return applicationCDDCacheService.fetchClusterConfigDetails(applicationName,
				applicationDataStoreLocation.getAbsolutePath());
	}

	@Override
	public void storeApplicationCDD(String applicationName, byte[] applicationCDD) throws BEApplicationSaveException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(applicationCDD);
			saveConfigResource(applicationName, bais, applicationName + ".cdd");
			String cddPath = applicationDataStoreLocation + File.separator + applicationName + File.separator
					+ applicationName + ".cdd";
			applicationCDDCacheService.cacheClusterConfigDetails(applicationName, cddPath);
		} catch (IOException | FileSaveException ex) {
			throw new BEApplicationSaveException(ex);
		} finally {
			if (null != bais) {
				try {
					bais.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}
		}
	}

	@Override
	public Collection<GlobalVariableDescriptor> fetchApplicationArchive(String applicationName) throws Exception {
		return applicationGVCacheService.getServiceSettableGlobalVariables(applicationName, applicationDataStoreLocation.getAbsolutePath());
	}

	@Override
	public void storeApplicationArchive(String applicationName, byte[] applicationArchive)
			throws BEApplicationSaveException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(applicationArchive);
			saveConfigResource(applicationName, bais, applicationName + ".ear");
			String earPath = applicationDataStoreLocation + File.separator + applicationName + File.separator
					+ applicationName + ".ear";
			applicationGVCacheService.cacheGlobalDescriptorDetails(applicationName, earPath);
		} catch (IOException | FileSaveException ex) {
			throw new BEApplicationSaveException(ex);
		} catch (Exception e) {
			throw new BEApplicationSaveException(e);
		} finally {

			if (null != bais) {
				try {
					bais.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}

		}
	}

	@Override
	public void storeDeploymentVariables(String applicationName, String serviceInstanceName,
			DeploymentVariables deploymentVariables) throws BEServiceInstanceSaveException {
		FileOutputStream fileOutputStream = null;
		try {
			File serviceInstanceDir = new File(applicationDataStoreLocation + File.separator + applicationName
					+ File.separator + serviceInstanceName);
			if (!serviceInstanceDir.exists()) {
				Files.createDirectories(serviceInstanceDir.toPath(), new FileAttribute[0]);
			} else if (!serviceInstanceDir.isDirectory()) {
				throw new BEServiceInstanceSaveException(
						messageService.getMessage(MessageKey.EXISTS_BUT_NOT_DIRECTORY, serviceInstanceDir.getAbsolutePath()));
			} else if (!serviceInstanceDir.canWrite()) {
				throw new BEServiceInstanceSaveException(
						messageService.getMessage(MessageKey.CAN_NOT_WRITE_TO_DIRECTORY, serviceInstanceDir.getAbsolutePath()));
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(DEPLOYMENT_VARIABLES_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			File deployVariablesFilepath = new File(serviceInstanceDir, deploymentVariables.getType().value() + ".xml");
			fileOutputStream = new FileOutputStream(deployVariablesFilepath);
			marshaller.marshal(deploymentVariables, fileOutputStream);
		} catch (Exception ex) {
			throw new BEServiceInstanceSaveException(ex.getMessage(), ex);
		} finally {
			if (null != fileOutputStream) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}
		}
	}

	@Override
	public DeploymentVariables fetchDeploymentVaribles(String applicationName, ServiceInstance serviceInstance,
			DeploymentVariableType variableType) throws Exception {
		DeploymentVariables deploymentVariables = null;
		InputStream fileInputStream = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream DeployVariablesFileInStream = null;
		try {
			File serviceInstanceDir = new File(applicationDataStoreLocation + File.separator + applicationName
					+ File.separator + serviceInstance.getName());
			File deployVariablesFilepath = new File(serviceInstanceDir, variableType.value() + ".xml");

			if (deployVariablesFilepath.exists()) {
				fileInputStream = new FileInputStream(deployVariablesFilepath);

				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				DeployVariablesFileInStream = new ByteArrayInputStream(buffer.toByteArray());
				JAXBContext jaxbContext = JAXBContext.newInstance(DEPLOYMENT_VARIABLES_JAXB_PACKAGE,
						this.getClass().getClassLoader());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				deploymentVariables = (DeploymentVariables) unmarshaller.unmarshal(DeployVariablesFileInStream);
				if (deploymentVariables != null)
					deploymentVariables.setKey(serviceInstance.getKey() + "/" + variableType.name());
			}
		} catch (IOException | JAXBException ex) {
			throw new Exception(ex);
		} finally {
			buffer.flush();
			buffer.close();
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (DeployVariablesFileInStream != null) {
				DeployVariablesFileInStream.close();
			}
		}
		return deploymentVariables;
	}

	@Override
	public AuditRecords fetchAuditRecords(String applicationName, String serviceInstanceName) throws Exception {
		AuditRecords auditRecords = null;
		InputStream fileInputStream = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream auditRecordstream = null;
		try {
			File serviceInstanceDir = new File(applicationDataStoreLocation + File.separator + applicationName
					+ File.separator + serviceInstanceName);
			File deployVariablesFilepath = new File(serviceInstanceDir, serviceInstanceName + "_Audit.xml");
			if (deployVariablesFilepath.exists()) {
				fileInputStream = new FileInputStream(deployVariablesFilepath);

				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				auditRecordstream = new ByteArrayInputStream(buffer.toByteArray());
				JAXBContext jaxbContext = JAXBContext.newInstance(AUDIT_RECORDS_JAXB_PACKAGE,
						this.getClass().getClassLoader());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				auditRecords = (AuditRecords) unmarshaller.unmarshal(auditRecordstream);
			}
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		} catch (IOException | JAXBException ex) {
			throw new Exception(ex);
		} finally {
			buffer.flush();
			buffer.close();
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (auditRecordstream != null) {
				auditRecordstream.close();
			}
		}
		return auditRecords;
	}

	@Override
	public void storeAuditRecords(String applicationName, String serviceInstanceName, AuditRecords auditRecords)
			throws Exception {

		FileOutputStream fileOutputStream = null;
		try {
			File applicationDir = new File(applicationDataStoreLocation + File.separator + applicationName
					+ File.separator + serviceInstanceName);
			if (!applicationDir.exists()) {
				Files.createDirectories(applicationDir.toPath(), new FileAttribute[0]);
			} else if (!applicationDir.isDirectory()) {
				throw new BEServiceInstanceSaveException(
						messageService.getMessage(MessageKey.EXISTS_BUT_NOT_DIRECTORY, applicationDir.getAbsolutePath()));
			} else if (!applicationDir.canWrite()) {
				throw new BEServiceInstanceSaveException(
						messageService.getMessage(MessageKey.CAN_NOT_WRITE_TO_DIRECTORY,applicationDir.getAbsolutePath()));
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(AUDIT_RECORDS_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			File deployVariablesFilepath = new File(applicationDir, serviceInstanceName + "_Audit.xml");
			fileOutputStream = new FileOutputStream(deployVariablesFilepath);
			marshaller.marshal(auditRecords, fileOutputStream);
		} catch (Exception ex) {
			throw new BEServiceInstanceSaveException(ex.getMessage(), ex);
		} finally {
			if (null != fileOutputStream) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}
		}
	}

	private void saveConfigResource(String folderName, InputStream in, String targetFileName)
			throws IOException, FileSaveException {
		File applicationDir = new File(applicationDataStoreLocation, folderName);
		if (!applicationDir.exists()) {
			Files.createDirectory(applicationDir.toPath(), new FileAttribute[0]);
		} else if (!applicationDir.isDirectory()) {
			throw new FileSaveException(messageService.getMessage(MessageKey.NOT_DIRECTORY, applicationDir.getAbsolutePath()));
		} else if (!applicationDir.canWrite()) {
			throw new FileSaveException(messageService.getMessage(MessageKey.NOT_HAVE_PERMISSION_WRITE, applicationDir.getAbsolutePath()));
		}
		File file = new File(applicationDir, targetFileName);
		ManagementUtil.streamCopy(in, file);
	}

	@Override
	public String loadApplicationCDD(String applicationName) throws Exception {
		String cddPath = applicationDataStoreLocation + File.separator + applicationName + File.separator
				+ applicationName + ".cdd";
		File applicationCDD = new File(cddPath);
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			if (applicationCDD.exists()) {
				reader = new BufferedReader(new FileReader(applicationCDD));
				String line = null;
				while (null != (line = reader.readLine())) {
					builder.append(line);
				}
			}
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		return builder.toString();
	}

	@Override
	public DeploymentVariables fetchApplicationConfig(String applicationName, DeploymentVariableType variableType)
			throws Exception {
		DeploymentVariables application = null;
		InputStream fileInputStream = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream DeployVariablesFileInStream = null;
		try {
			File applicationDir = new File(applicationDataStoreLocation + File.separator + applicationName);
			File deployVariablesFilepath = new File(applicationDir, variableType.value() + ".xml");
			if (deployVariablesFilepath.exists()) {
				fileInputStream = new FileInputStream(deployVariablesFilepath);

				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				DeployVariablesFileInStream = new ByteArrayInputStream(buffer.toByteArray());
				JAXBContext jaxbContext = JAXBContext.newInstance(DEPLOYMENT_VARIABLES_JAXB_PACKAGE,
						this.getClass().getClassLoader());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				application = (DeploymentVariables) unmarshaller.unmarshal(DeployVariablesFileInStream);
			}
		} catch (IOException | JAXBException ex) {
			throw new Exception(ex);
		} finally {
			buffer.flush();
			buffer.close();
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (DeployVariablesFileInStream != null) {
				DeployVariablesFileInStream.close();
			}
		}
		return application;

	}

	@Override
	public void storeApplicationConfig(String applicationName, DeploymentVariables deploymentVariables)
			throws Exception {

		FileOutputStream fileOutputStream = null;
		try {
			File applicationDir = new File(applicationDataStoreLocation + File.separator + applicationName);
			if (!applicationDir.exists()) {
				Files.createDirectories(applicationDir.toPath(), new FileAttribute[0]);
			} else if (!applicationDir.isDirectory()) {
				throw new BEServiceInstanceSaveException(
						applicationDir.getAbsolutePath() + " exists but is not a directory");
			} else if (!applicationDir.canWrite()) {
				throw new BEServiceInstanceSaveException(
						"Access Denied. Cannot write to directory " + applicationDir.getAbsolutePath());
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(DEPLOYMENT_VARIABLES_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			File deployVariablesFilepath = new File(applicationDir, deploymentVariables.getType().value() + ".xml");
			fileOutputStream = new FileOutputStream(deployVariablesFilepath);
			marshaller.marshal(deploymentVariables, fileOutputStream);
		} catch (Exception ex) {
			throw new BEServiceInstanceSaveException(ex.getLocalizedMessage(), ex);
		} finally {
			if (null != fileOutputStream) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}

			}

		}

	}

	@Override
	public void deleteConfigData(String entityPath) throws FileNotFoundException {
		File dir = new File(applicationDataStoreLocation + File.separator + entityPath);
		ManagementUtil.delete(dir);
	}

	@Override
	public void storeMasterHostTopology(Map<String, MasterHost> masterHotsMap) throws BEMasterHostSaveException {

		ByteArrayInputStream bais = null;
		try {
			byte[] siteTopologyContents = masterSerializer.serialize(masterHotsMap);
			bais = new ByteArrayInputStream(siteTopologyContents);

			File file = new File(applicationDataStoreLocation, "hostrepo" + masterSerializer.getContentType());
			if (!file.exists()) {
				file.createNewFile();
			}
			ManagementUtil.streamCopy(bais, file);

		} catch (Exception ex) {
			throw new BEMasterHostSaveException(ex);
		} finally {
			if (null != bais) {
				try {
					bais.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}
		}

	}

	@Override
	public Map<String, MasterHost> fetchAllMasterHost() {
		InputStream fileInputStream = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			File[] appdirs = applicationDataStoreLocation.listFiles();
			if (null != appdirs) {
				for (File file : appdirs) {
					try {
						if (!file.isDirectory() && "hostrepo.xml".equals(file.getName())) {
							fileInputStream = new FileInputStream(file);

							int nRead;
							byte[] data = new byte[16384];
							while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
								buffer.write(data, 0, nRead);
							}

							return masterSerializer.deserialize(buffer.toByteArray(), this);
						}
					} catch (Exception ex) {
						LOGGER.log(Level.ERROR, ex, ex.getMessage());
					}
				}
			}

		} finally {
			try {
				buffer.flush();
				buffer.close();
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException ex) {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public void storeApplicationTraConfigs(String applicationName, HostTraConfigs hostTraConfigs) throws Exception {

		FileOutputStream fileOutputStream = null;
		try {
			File applicationDir = new File(applicationDataStoreLocation + File.separator + applicationName);
			if (!applicationDir.exists()) {
				Files.createDirectories(applicationDir.toPath(), new FileAttribute[0]);
			} else if (!applicationDir.isDirectory()) {
				throw new BEServiceInstanceSaveException(
						applicationDir.getAbsolutePath() + " exists but is not a directory");
			} else if (!applicationDir.canWrite()) {
				throw new BEServiceInstanceSaveException(
						"Access Denied. Cannot write to directory " + applicationDir.getAbsolutePath());
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(APPLICATION_TRA_CONFIGS_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			File traConfigFilepath = new File(applicationDir, applicationName + "_TRA_CONFIG.xml");
			if (!traConfigFilepath.exists()) {
				traConfigFilepath.createNewFile();
			}
			fileOutputStream = new FileOutputStream(traConfigFilepath);
			marshaller.marshal(hostTraConfigs, fileOutputStream);
		} catch (Exception ex) {
			throw new BEServiceInstanceSaveException(ex.getLocalizedMessage(), ex);
		} finally {
			if (null != fileOutputStream) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}

			}

		}

	}

	@Override
	public HostTraConfigs loadpplicationTraConfigs(String applicationName) throws Exception {
		HostTraConfigs hostTraConfigs = null;
		InputStream fileInputStream = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream applicationTraFileInputStream = null;
		try {
			File applicationDir = new File(applicationDataStoreLocation + File.separator + applicationName);
			File deployVariablesFilepath = new File(applicationDir, applicationName + "_TRA_CONFIG.xml");
			if (deployVariablesFilepath.exists()) {
				fileInputStream = new FileInputStream(deployVariablesFilepath);

				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				applicationTraFileInputStream = new ByteArrayInputStream(buffer.toByteArray());
				JAXBContext jaxbContext = JAXBContext.newInstance(APPLICATION_TRA_CONFIGS_JAXB_PACKAGE,
						this.getClass().getClassLoader());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				hostTraConfigs = (HostTraConfigs) unmarshaller.unmarshal(applicationTraFileInputStream);
			}
		} catch (IOException | JAXBException ex) {
			LOGGER.log(Level.ERROR, "Exception while loadpplicationTraConfigs()", ex);
			throw new Exception(ex);
		} finally {
			buffer.flush();
			buffer.close();
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (applicationTraFileInputStream != null) {
				applicationTraFileInputStream.close();
			}
		}
		return hostTraConfigs;

	}

	@Override
	public Set<String> getExcludedProperties() {
		if (excludedProperties.isEmpty()) {
			BufferedReader br = null;

			try {
				InputStream is = this.getClass().getClassLoader().getResourceAsStream("excluded_properties.txt");
				if (null != is) {
					br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					String line = null;
					while (null != (line = br.readLine())) {
						excludedProperties.add(line);
					}
				}
			} catch (IOException e) {
			} finally {
				if (null != br) {
					try {
						br.close();
					} catch (IOException e) {
					}
				}

			}
		}
		return excludedProperties;
	}

	@Override
	public void storeHostTra(String applicationName, String hostId, InputStream traFileStream)
			throws BEHostTRASaveException {
		String dirPath = applicationDataStoreLocation.getAbsolutePath();
		if (null != applicationName && !applicationName.trim().isEmpty())
			dirPath = applicationDataStoreLocation.getAbsolutePath() + File.separator + applicationName;

		File hostDir = new File(dirPath);
		if (!hostDir.exists()) {
			try {
				Files.createDirectories(hostDir.toPath(), new FileAttribute[0]);
			} catch (IOException e) {
				throw new BEHostTRASaveException(e);
			} finally {

				if (null != traFileStream) {
					try {
						traFileStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
		try {
			File file = new File(hostDir.getAbsolutePath(), hostId + ".tra");
			if (!file.exists())
				file.createNewFile();
			ManagementUtil.streamCopy(traFileStream, file);
		} catch (IOException ex) {
			throw new BEHostTRASaveException(ex);
		}
	}

	@Override
	public InputStream fetchHostTraFile(String applicationName, String hostId) {
		String dirPath = applicationDataStoreLocation.getAbsolutePath();
		if (null != applicationName && !applicationName.trim().isEmpty())
			dirPath = dirPath + File.separator + applicationName;

		File hostDir = new File(dirPath);
		if (hostDir.exists()) {
			try {
				return new FileInputStream(hostDir + File.separator + hostId + ".tra");
			} catch (FileNotFoundException e) {
			}
		}
		return null;
	}

	/**
	 * @return the tempFileLocation
	 */
	@Override
	public File getTempFileLocation() {
		return tempFileLocation;
	}

	@Override
	public String createApplicationXML(com.tibco.cep.bemm.management.export.model.Application exportedApplication)
			throws Exception {
		byte[] bs = applicationExportImportSerializer.serialize(exportedApplication);
		String fileName = null;
		FileOutputStream fileOutputStream = null;
		try {
			File applicationExportFile = File.createTempFile(exportedApplication.getName()+"_"+System.currentTimeMillis(), ".xml", tempFileLocation);
			fileName = applicationExportFile.getAbsolutePath();
			fileOutputStream = new FileOutputStream(applicationExportFile);
			fileOutputStream.write(bs);

		} catch (Exception e) {

		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
		return fileName;

	}

	@Override
	public com.tibco.cep.bemm.management.export.model.Application createApplicationFromXML(String name)
			throws Exception {
		FileInputStream fileInputStream = null;
		com.tibco.cep.bemm.management.export.model.Application application = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try {
			File applicationFolder = new File(tempFileLocation, name.replace(".zip", "").trim());
			File[] files = applicationFolder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith("xml");
				}
			});
			if (null != files && files.length > 0) {
				fileInputStream = new FileInputStream(files[0]);
				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				application = applicationExportImportSerializer.deserialize(buffer.toByteArray(), this);
			}

		} finally {
			try {
				buffer.flush();
				buffer.close();
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException ex) {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}
		return application;

	}

	@Override
	public void storeApplicationProfile(Application application, String profileName, Map<String, String> globalVariables,
			Map<String, String> systemProperties, Map<String, String> beProperties) {
		String name = application.getName();
		File profileFile = new File(applicationDataStoreLocation, name + "/" + profileName + ".properties");
		BufferedWriter bw = null;
		try {
			profileFile.createNewFile();
			bw = new BufferedWriter(new FileWriter(profileFile));
			if (null != globalVariables) {
				for (Entry<String, String> entry : globalVariables.entrySet()) {
					if (null != entry) {
						String key = entry.getKey();
						String value = entry.getValue();
						if(null!=value && value.indexOf("\\") > 0)
						    value = value.replace("\\", "\\\\");
						if (null != key && !key.trim().isEmpty()) {
							if(null==value)
								value="";
							if (!key.startsWith(Constants.GLOBAL_VARIABL_PREFIX)) {
								key = Constants.GLOBAL_VARIABL_PREFIX + key.trim();
							}
							if(chkGlobalVarPassType(application, key)){
								value = ManagementUtil.getEncodedPwd(value);
							}
							bw.write(key + "=" + value + "\n");
						}
					}
				}
			}
			if (null != systemProperties) {
				for (Entry<String, String> entry : systemProperties.entrySet()) {
					if (null != entry) {
						String key = entry.getKey();
						String value = entry.getValue();
						if(null!=value &&value.indexOf("\\") > 0)
						    value = value.replace("\\", "\\\\");
						if (null != key && !key.trim().isEmpty()) {
							if(null==value)
								value="";
							if (!key.startsWith(Constants.SYSTEM_PROPERTY_PREFIX)) {
								key = Constants.SYSTEM_PROPERTY_PREFIX + key.trim();
							}
							bw.write(key + "=" + value + "\n");
						}
					}
				}
			}
			if (null != beProperties) {
				for (Entry<String, String> entry : beProperties.entrySet()) {
					if (null != entry) {
						String key = entry.getKey();
						String value = entry.getValue();
						if(null!=value &&value.indexOf("\\") > 0)
						    value = value.replace("\\", "\\\\");
						if (null != key && !key.trim().isEmpty()) {
							if(null==value)
								value="";
							bw.write(key + "=" + value + "\n");
						}
					}
				}
			}
		} catch (IOException e) {
			profileFile.delete();
		} finally {
			if (null != bw) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private boolean chkGlobalVarPassType(Application application, String key) {
		
		//Just any host instance will do, as we have to check just the type
		for(Host host : application.getHosts()){
			for(ServiceInstance instance : host.getInstances()){
				if (key.startsWith(Constants.GLOBAL_VARIABL_PREFIX)) {
					key = key.replace(Constants.GLOBAL_VARIABL_PREFIX, "");
				}
				NameValuePair pairToMatch = new NameValuePair(); pairToMatch.setName(key);
				NameValuePair matchedNameValuePair = GroupOperationUtil.getInstanceMatchedNameValuePair(instance.getGlobalVariables(), pairToMatch);
				if (matchedNameValuePair!=null && "Password".equals(matchedNameValuePair.getType())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Set<String> loadProfiles(String name) {
		File applicationFolter = new File(applicationDataStoreLocation, name);
		Set<String> profiles = new HashSet<String>();
		File[] propFiles = applicationFolter.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".properties");
			}
		});
		if (null != propFiles) {
			for (File file : propFiles) {
				if (null != file) {
					profiles.add(file.getName().replace(".properties", ""));
				}
			}
		}
		return profiles;

	}

	@Override
	public Map<String, Map<String, String>> loadProfile(String applicationName, String profileName)
			throws BEApplicationProfileNotExistException {
		File profileFile = new File(applicationDataStoreLocation, applicationName + "/" + profileName + ".properties");
		if (!profileFile.exists())
			throw new BEApplicationProfileNotExistException(profileName + " data not exist");
		Properties properties = new Properties();
		Map<String, Map<String, String>> propMap = new HashMap<>();
		try {
			Map<String, String> gvs = new HashMap<>();
			Map<String, String> systemProps = new HashMap<>();
			Map<String, String> beProps = new HashMap<>();
			properties.load(new FileInputStream(profileFile));
			for (Entry<Object, Object> entry : properties.entrySet()) {
				if (null != entry) {
					String key = entry.getKey().toString();
					if (key.startsWith(Constants.GLOBAL_VARIABL_PREFIX)) {
						gvs.put(key.replace(Constants.GLOBAL_VARIABL_PREFIX, ""), entry.getValue().toString());
					} else if (key.startsWith(Constants.SYSTEM_PROPERTY_PREFIX)) {
						systemProps.put(key.replace(Constants.SYSTEM_PROPERTY_PREFIX, ""), entry.getValue().toString());
					} else {
						beProps.put(key, entry.getValue().toString());
					}

				}
			}
			propMap.put("BEPROPS", beProps);
			propMap.put("SYSTEMPROPS", systemProps);
			propMap.put("GV", gvs);

		} catch (IOException e) {
		}
		return propMap;

	}

	@Override
	public void rollback(String name) {

		try {

			ManagementUtil.delete(new File(applicationDataStoreLocation, name));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
