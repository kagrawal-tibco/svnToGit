package com.tibco.cep.bemm.persistence.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.ApplicationBuilderImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.persistence.service.BEApplicationSerializer;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.topology.model.Be;
import com.tibco.cep.bemm.persistence.topology.model.BeRuntime;
import com.tibco.cep.bemm.persistence.topology.model.Cluster;
import com.tibco.cep.bemm.persistence.topology.model.Clusters;
import com.tibco.cep.bemm.persistence.topology.model.DeployedFiles;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentMapping;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentMappings;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentUnit;
import com.tibco.cep.bemm.persistence.topology.model.DeploymentUnits;
import com.tibco.cep.bemm.persistence.topology.model.HostResource;
import com.tibco.cep.bemm.persistence.topology.model.HostResources;
import com.tibco.cep.bemm.persistence.topology.model.MasterFiles;
import com.tibco.cep.bemm.persistence.topology.model.ObjectFactory;
import com.tibco.cep.bemm.persistence.topology.model.ProcessingUnitConfig;
import com.tibco.cep.bemm.persistence.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.bemm.persistence.topology.model.RunVersion;
import com.tibco.cep.bemm.persistence.topology.model.Site;
import com.tibco.cep.bemm.persistence.topology.model.Software;
import com.tibco.cep.bemm.persistence.topology.model.Ssh;
import com.tibco.cep.bemm.persistence.topology.model.StartPuMethod;
import com.tibco.cep.bemm.persistence.topology.model.UserCredentials;

public class BEApplicationSiteTopologySerializer implements BEApplicationSerializer {

	private static final String CLUSTER_TOPOLOGY_JAXB_PACKAGE = "com.tibco.cep.bemm.persistence.topology.model";

	private String deployTopologiesRepoDirLocation = null;
	private ObjectFactory siteTopologyObjectFactory = null;

	public BEApplicationSiteTopologySerializer(String deployTopologiesRepoDirLocation) {
		this.siteTopologyObjectFactory = new ObjectFactory();
		this.deployTopologiesRepoDirLocation = deployTopologiesRepoDirLocation;
	}

	@Override
	public String getContentType() {
		return ".st";
	}

	@Override
	public Application deserialize(byte[] siteTopologyContents,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws Exception {
		Application application = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(siteTopologyContents);
			JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Site site = (Site) unmarshaller.unmarshal(bais);

			ApplicationBuilderImpl applicationBuilder = new ApplicationBuilderImpl();
			application = applicationBuilder.newApplication();
			application.setName(site.getName());
			DeploymentVariables appDeploymentVariables = dataStoreService.fetchApplicationConfig(application.getName(),
					DeploymentVariableType.APPLICATION_CONFIG);
			applicationBuilder.enrichApplicationConfig(application, appDeploymentVariables);
			ClusterConfig clusterConfig =null;
			if (!application.isMonitorableOnly()) {
				clusterConfig= dataStoreService.fetchApplicationCDD(application.getName());
				if (null == clusterConfig && !application.isMonitorableOnly()) {
				dataStoreService.rollback(application.getName());
				throw new Exception("Incompatible CDD version");
			}
			
			}
			applicationBuilder.enrichTopologyAndClusterConfigData(application, site, clusterConfig, null, false);
		} catch (JAXBException jbex) {
			throw new Exception(jbex);
		}
		return application;
	}

	@Override
	public byte[] serialize(Application application) throws Exception {
		ByteArrayOutputStream baos = null;
		try {
			Site site = createSiteTopologyHierarchy(application);
			JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			baos = new ByteArrayOutputStream();
			marshaller.marshal(site, baos);
			baos.flush();
			return baos.toByteArray();
		} catch (PropertyException pe) {
			throw new BEApplicationSaveException(pe.getLocalizedMessage(), pe);
		} catch (JAXBException jbe) {
			throw new BEApplicationSaveException(jbe.getLocalizedMessage(), jbe);
		} catch (Exception e) {
			throw new BEApplicationSaveException(e.getLocalizedMessage(), e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	private Site createSiteTopologyHierarchy(Application application) {
		// Create the Site
		Site site = siteTopologyObjectFactory.createSite();
		site.setName(application.getName());
		// Create HostResources
		HostResources hostResources = siteTopologyObjectFactory.createHostResources();
		Map<String, HostResource> hostResourcesMap = new HashMap<>();

		for (Host host : application.getHosts()) {
			if (host.isPredefined()) {
				HostResource hostResource = siteTopologyObjectFactory.createHostResource();
				hostResource.setHostname(host.getHostName());
				hostResource.setId(host.getHostId());
				hostResource.setIp(host.getHostIp());
				hostResource.setOsType(host.getOs());
				Software software = siteTopologyObjectFactory.createSoftware();

				List<BE> bes = host.getBE();

				if (null != bes && !bes.isEmpty()) {
					for (BE be : bes) {
						if (null != be) {
							Be beModel = siteTopologyObjectFactory.createBe();
							String beHome = be.getBeHome();
							String beTRA = be.getBeTra();
							if (null != beHome && !beHome.trim().isEmpty() && null != beTRA
									&& !beTRA.trim().isEmpty()) {
								String version = "0";
								if (beHome.endsWith("/")) {
									beHome = beHome.substring(0, beHome.length() - 1);
									version = beHome.substring(beHome.lastIndexOf("/") + 1, beHome.length());
								} else {
									version = beHome.substring(beHome.lastIndexOf("/") + 1, beHome.length());
								}
								if (beHome.endsWith("\\")) {
									beHome = beHome.substring(0, beHome.length() - 1);
									version = beHome.substring(beHome.lastIndexOf("\\") + 1, beHome.length());
								} else {
									version = beHome.substring(beHome.lastIndexOf("\\") + 1, beHome.length());
								}
								beModel.setTra(beTRA);
								beHome = beHome.replace("\\", "/");
								beModel.setHome(beHome);
								beModel.setVersion(version);
								software.getBe().add(beModel);
							}
						}
					}
				}
			
				hostResource.setSoftware(software);
				UserCredentials userCredentials = siteTopologyObjectFactory.createUserCredentials();
				userCredentials.setUsername(host.getUserName());
				userCredentials.setPassword(host.getPassword());
				hostResource.setUserCredentials(userCredentials);
				StartPuMethod startPuMethod = siteTopologyObjectFactory.createStartPuMethod();
				Ssh ssh = siteTopologyObjectFactory.createSsh();
				int sshPort = host.getSshPort();

				ssh.setPort(String.valueOf(sshPort));
				startPuMethod.setSsh(ssh);
				hostResource.setStartPuMethod(startPuMethod);
				hostResources.getHostResource().add(hostResource);
				hostResourcesMap.put(host.getKey(), hostResource);
			}
		}
		site.setHostResources(hostResources);

		// Create the Cluster
		Clusters clusters = siteTopologyObjectFactory.createClusters();
		Cluster cluster = siteTopologyObjectFactory.createCluster();
		cluster.setName(application.getClusterName());
		// Create Master Files
		MasterFiles masterFiles = siteTopologyObjectFactory.createMasterFiles();
		String basePath = deployTopologiesRepoDirLocation + "/" + application.getName() + "/" + application.getName();
		masterFiles.setCddMaster(basePath + ".cdd");
		masterFiles.setEarMaster(basePath + ".ear");
		cluster.setMasterFiles(masterFiles);
		RunVersion runVersion = siteTopologyObjectFactory.createRunVersion();
		BeRuntime beRuntime = new BeRuntime();
		runVersion.setBeRuntime(beRuntime);
		cluster.setRunVersion(runVersion);

		// Create Deployments Units
		DeploymentUnits deploymentUnits = siteTopologyObjectFactory.createDeploymentUnits();
		DeploymentMappings deploymentMappings = siteTopologyObjectFactory.createDeploymentMappings();
		for (Host host : application.getHosts()) {
			if (host.isPredefined()) {
				for (ServiceInstance instance : host.getInstances()) {

					// Create DeploymentUnit
					DeploymentUnit deploymentUnit = siteTopologyObjectFactory.createDeploymentUnit();
					deploymentUnit.setName("DU_" + instance.getName());
					deploymentUnit.setId(instance.getDuId());

					// Deployed Files Path
					DeployedFiles deployedFiles = siteTopologyObjectFactory.createDeployedFiles();
					// String os = host.getOs();
					String deploymentPath = instance.getDeploymentPath();
					deploymentPath = deploymentPath.replace("\\", "/");
					String cddDeploymentPath = deploymentPath + "/" + application.getName() + ".cdd";
					String earDeploymentPath = deploymentPath + "/" + application.getName() + ".ear";
					/*
					 * if (os.toLowerCase().startsWith("windows")) {
					 * cddDeploymentPath = cddDeploymentPath.replaceAll("/",
					 * "\\\\"); earDeploymentPath =
					 * earDeploymentPath.replaceAll("/", "\\\\");
					 * cddDeploymentPath = instance.getDeploymentPath() +
					 * "\\" + application.getName() + ".cdd"; earDeploymentPath
					 * = instance.getDeploymentPath() +
					 * "\\" + application.getName() + ".ear"; }
					 */
					deployedFiles.setCddDeployed(cddDeploymentPath);
					deployedFiles.setEarDeployed(earDeploymentPath);

					deploymentUnit.setDeployedFiles(deployedFiles);

					// ProcessingUnitConfig
					ProcessingUnitsConfig processingUnitsConfig = siteTopologyObjectFactory
							.createProcessingUnitsConfig();
					ProcessingUnitConfig processingUnitConfig = siteTopologyObjectFactory.createProcessingUnitConfig();
					processingUnitConfig.setId(instance.getName());
					processingUnitConfig.setPuid(instance.getPuId());
					processingUnitConfig.setJmxUserName(instance.getJmxUserName());
					processingUnitConfig.setJmxPassword(instance.getJmxPassword());
					processingUnitConfig.setJmxport(String.valueOf(instance.getJmxPort()));
					if (!application.isMonitorableOnly()) {
					if(null==instance.getBeId() || instance.getBeId().trim().isEmpty()){
						processingUnitConfig.setBeId(host.getBE().get(0).getId());
						instance.setBeId(host.getBE().get(0).getId());
					}else{
						processingUnitConfig.setBeId(instance.getBeId());
					}
					}
					processingUnitsConfig.getProcessingUnitConfig().add(processingUnitConfig);
					deploymentUnit.setProcessingUnitsConfig(processingUnitsConfig);
					deploymentUnits.getDeploymentUnit().add(deploymentUnit);

					// Deployment mappings
					DeploymentMapping deploymentMapping = siteTopologyObjectFactory.createDeploymentMapping();
					deploymentMapping.setDeploymentUnitRef(deploymentUnit);
					HostResource hostResource = hostResourcesMap.get(host.getKey());
					deploymentMapping.setHostRef(hostResource);
					deploymentMappings.getDeploymentMapping().add(deploymentMapping);
				}
			}
		}

		cluster.setDeploymentUnits(deploymentUnits);
		cluster.setDeploymentMappings(deploymentMappings);
		clusters.getCluster().add(cluster);
		site.setClusters(clusters);
		return site;
	}
}
