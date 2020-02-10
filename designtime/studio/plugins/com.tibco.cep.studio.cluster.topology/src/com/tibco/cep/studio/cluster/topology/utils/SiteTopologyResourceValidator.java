package com.tibco.cep.studio.cluster.topology.utils;

import static com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils.CLUSTER_TOPOLOGY_JAXB_PACKAGE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.cluster.topology.model.Be;
import com.tibco.cep.studio.cluster.topology.model.Cluster;
import com.tibco.cep.studio.cluster.topology.model.Clusters;
import com.tibco.cep.studio.cluster.topology.model.DeployedFiles;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMapping;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMappings;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnits;
import com.tibco.cep.studio.cluster.topology.model.HostResource;
import com.tibco.cep.studio.cluster.topology.model.HostResources;
import com.tibco.cep.studio.cluster.topology.model.MasterFiles;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.studio.cluster.topology.model.Site;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;

/**
 * 
 * @author sasahoo
 *
 */
public class SiteTopologyResourceValidator extends DefaultResourceValidator {
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#canContinue()
	 */
	@Override
	public boolean canContinue() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {		
		if (validationContext == null) { 
			return true;
		}
		IResource resource = validationContext.getResource();	
		if (resource == null) {
			return true;
		}
//		int modificationType = validationContext.getModificationType();
//		int buildType = validationContext.getBuildType();
		deleteMarkers(resource);
		super.validate(validationContext);
		String path = resource.getLocation().toOSString();
		File siteTopologyFile = new File(path);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE);
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		    Site site = (Site)unmarshaller.unmarshal(siteTopologyFile);
		    parseHostResources(site, resource);
		    parseClusters(site, resource);
		} catch (JAXBException je) {
			reportProblem(resource, je.getMessage(), IMarker.SEVERITY_WARNING);
//	    	je.printStackTrace();
	    }catch (Exception e) {
//	    	e.printStackTrace();
	    	reportProblem(resource, e.getMessage(), IMarker.SEVERITY_WARNING);
	    }
		return true;
	}
	

	/**
	 * @param site
	 * @param resource
	 */
	private void parseHostResources(Site site, IResource resource) {
		HostResources hostResources = site.getHostResources();
		if (hostResources != null) {
			List<HostResource> hostResourceList = hostResources.getHostResource();
			for (HostResource hostResource : hostResourceList) {
				if (hostResource.getHostname().isEmpty()) {
					reportProblem(resource, Messages.getString("host.name.empty"),resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
				} if(hostResource.getOsType().isEmpty()) {
					if(!hostResource.getHostname().isEmpty()) {
						reportProblem(resource, Messages.getString("host.osType.empty", hostResource.getHostname()),resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
					}
				}
			}
		}
	}

	/**
	 * @param site
	 * @param resource
	 */
	private void parseClusters(Site site, IResource resource) {
		Clusters clusters = site.getClusters();
		List<Cluster> clusterList = clusters.getCluster();
		processClusters(clusterList, resource);
		processHosts(site, resource);
		
	}
	
	private void processHosts(Site site, IResource resource) {
		for (HostResource host: site.getHostResources().getHostResource()) {
			if (host.getSoftware() != null ) {
				if (host.getSoftware().getBe() != null) {
					String version = host.getSoftware().getBe().get(0).getVersion();
					if (version.isEmpty()) {
						reportProblem(resource, Messages.getString("host.BE.version.empty", host.getHostname()), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
					} else if (!version.equals(cep_containerVersion.version)) {
						reportProblem(resource, Messages.getString("host.BE.version.invalid", 
								version, host.getHostname()), resource.getFullPath().makeAbsolute().toPortableString(),
								IMarker.SEVERITY_ERROR);
					}
					Be be = host.getSoftware().getBe().get(0);	
					String traFileName = be.getTra();
					if(traFileName.equals("")) {
						reportProblem(resource, Messages.getString("tra_empty",host.getHostname()), resource.getFullPath().makeAbsolute().toPortableString(),
								IMarker.SEVERITY_ERROR);
					}
					else if(!traFileName.endsWith(".tra") ) {
						reportProblem(resource, Messages.getString("invalid_tra", traFileName), resource.getFullPath().makeAbsolute().toPortableString(),
								IMarker.SEVERITY_ERROR);
					} /*else if(!new File(traFileName).exists()) {
						reportProblem(resource, Messages.getString("tra_doesnot_exist", traFileName), resource.getFullPath().makeAbsolute().toPortableString(),
								IMarker.SEVERITY_ERROR);
					}*/

				}
			}
		}
	}
	
	/**
	 * @param clusterList
	 * @param resource
	 */
	@SuppressWarnings("unused")
	private void processClusters(List<Cluster> clusterList, IResource resource) {
		DeploymentUnits deploymentUnits;
		List<DeploymentUnit> deploymentUnitList;
		DeployedFiles dfs;
		ProcessingUnitsConfig pus;
		List<ProcessingUnitConfig> puList;
		for (Cluster cluster : clusterList) {
			if (cluster.getName().isEmpty()) {
				reportProblem(resource, Messages.getString("cluster.name.empty"), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
			}
			if(cluster.getProjectCdd().isEmpty()){
				reportProblem(resource, Messages.getString("empty_cdd","Default",cluster.getName()), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
				
			}else if(!cluster.getProjectCdd().endsWith(".cdd") || !new File(cluster.getProjectCdd()).exists()) {
				reportProblem(resource, Messages.getString("cluster.project.cdd.invalid","", cluster.getName()), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
				
			}
			if (cluster.getRunVersion().getBeRuntime().getVersion().isEmpty()) {
				reportProblem(resource, Messages.getString("cluster.BE.version.empty", cluster.getName()), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
			} else if (!cluster.getRunVersion().getBeRuntime().getVersion().equals(cep_containerVersion.version)) {
				reportProblem(resource, Messages.getString("cluster.BE.version.invalid", cluster.getRunVersion().getBeRuntime().getVersion(), cluster.getName()), 
						IMarker.SEVERITY_ERROR);
			}
			if(!cluster.getProjectCdd().equalsIgnoreCase("")){
		//	IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(cluster.getProjectCdd()));
			File file = new File (cluster.getProjectCdd());
			if (!file.exists()){
				reportProblem(resource, Messages.getString("cluster.project.path.cdd.invalid", cluster.getRunVersion().getBeRuntime().getVersion(), cluster.getName()), 
						IMarker.SEVERITY_ERROR);	
			}else if(!this.isObjectCacheCDDFile(file)) {
				reportProblem(resource, Messages.getString("cluster.project.cdd.invalid.object.management", cluster.getRunVersion().getBeRuntime().getVersion(), cluster.getName()), 
						IMarker.SEVERITY_ERROR);
			}
			}
			MasterFiles masterfiles = cluster.getMasterFiles();
			String cddMaster = masterfiles.getCddMaster();
			String earMaster = masterfiles.getEarMaster();
			if(cluster.getMasterFiles().getEarMaster().isEmpty()){
				reportProblem(resource, Messages.getString("empty_ear", cluster.getName()), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
				
			}else if (!earMaster.endsWith(".ear") || !new File (earMaster).exists()) {
				String problemMessage = Messages.getString("invalid_ear", earMaster);
				reportProblem(resource, problemMessage, resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_WARNING);
				
			}
			if(cddMaster.isEmpty()){
				reportProblem(resource, Messages.getString("empty_cdd","Master", cluster.getName()), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
				
			}else if (!cddMaster.endsWith(".cdd") || !new File (cddMaster).exists()) {
				String problemMessage = Messages.getString("invalid_cdd","Master", cddMaster);
				reportProblem(resource, problemMessage, resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_WARNING);
				
			}
		
			deploymentUnits = cluster.getDeploymentUnits();
			if (deploymentUnits != null){
				deploymentUnitList = deploymentUnits.getDeploymentUnit();
				for (DeploymentUnit du : deploymentUnitList) {
					if (du.getName().isEmpty()) {
						reportProblem(resource, Messages.getString("deployment.name.empty"), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
					}
					String name = du.getName();
					if (!du.getName().isEmpty() && du.getDeployedFiles() == null) {
						reportProblem(resource, Messages.getString("deployed.cdd.empty", name), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
						reportProblem(resource, Messages.getString("deployed.ear.empty", name), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
					}
					if (!du.getName().isEmpty() && du.getDeployedFiles() != null) {
						if (du.getDeployedFiles().getCddDeployed().isEmpty()) {
							reportProblem(resource, Messages.getString("deployed.cdd.empty", name), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
						}else if(!du.getDeployedFiles().getCddDeployed().endsWith(".cdd")){
							reportProblem(resource, Messages.getString("deployed.cdd.invalid", name), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
							
						}
						if (du.getDeployedFiles().getEarDeployed().isEmpty()) {
							reportProblem(resource, Messages.getString("deployed.ear.empty", name), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
						}else if(!du.getDeployedFiles().getEarDeployed().endsWith(".ear")){
							reportProblem(resource, Messages.getString("deployed.ear.invalid", name), resource.getFullPath().makeAbsolute().toPortableString(), IMarker.SEVERITY_ERROR);
							
						}
					}
					dfs = du.getDeployedFiles();
					pus = du.getProcessingUnitsConfig();
					puList = pus.getProcessingUnitConfig();
					for(ProcessingUnitConfig pu : puList){
						//TODO: put validation condition here and use this
//						reportProblem(resource, message, IMarker.SEVERITY_WARNING);
					}
				}
			}
			processDeploymentMappings(cluster.getDeploymentMappings());
			
			
		}
	}
	
	/**
	 * @param deploymentMappings
	 */
	@SuppressWarnings("unused")
	private void processDeploymentMappings(DeploymentMappings deploymentMappings) {
		List<DeploymentMapping> deploymentMappingList;
		DeploymentUnit deploymentUnit;
		HostResource hostResource;
		String duIdRef = null;
		String hrIdRef = null;
		if (deploymentMappings != null){
			ArrayList<String> hrIdList;
			// get id mappings
			deploymentMappingList = deploymentMappings.getDeploymentMapping();				
			for (DeploymentMapping dm : deploymentMappingList) {
				
				if(dm.getDeploymentUnitRef() != null){
					deploymentUnit = (DeploymentUnit) dm.getDeploymentUnitRef();
					duIdRef = deploymentUnit.getId();
				}
				if(dm.getHostRef() != null){
					hostResource = (HostResource) dm.getHostRef();
					hrIdRef = hostResource.getId();
				}
				if (duIdRef != null && duIdRef.trim().length() > 0
						&& hrIdRef != null && hrIdRef.trim().length() > 0) {
					//TODO: put validation condition here and use this
//					reportProblem(resource, message, IMarker.SEVERITY_WARNING);
				}
			}
		}
	}
	
	 private boolean isObjectCacheCDDFile(File file) {
			/*initially we were passing an IFile but for BE-15503 had to change it to File
		    as IFile was null for a cdd not in the workspace.*/
		//	String filePath = file.getLocation().toOSString();
		 	String filePath =  file.getAbsolutePath().toString();
			if (filePath == null || new File(filePath).length() == 0)
				return false;
			try {
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				fact.setNamespaceAware(true);
				DocumentBuilder builder = fact.newDocumentBuilder();
				FileInputStream fis = new FileInputStream(filePath);
				Document doc = builder.parse(fis);
				Element root = doc.getDocumentElement();
				NodeList fileNodeList = root.getChildNodes();
				for (int n = 0; n < fileNodeList.getLength() ; n++) {
					Node fileNode = fileNodeList.item(n);
					if (fileNode == null || !isValidFileNode(fileNode)) {
						continue;
					}
					String fileNodeName = fileNode.getLocalName();
					if(fileNodeName.equals("object-management")) {
						Node objectManagementTypeNode = fileNode.getFirstChild();
						while(!isValidFileNode(objectManagementTypeNode)) {
							objectManagementTypeNode = objectManagementTypeNode.getNextSibling();
						}
						String objectManagementType = objectManagementTypeNode.getLocalName();
						if(objectManagementType.equals("cache-manager")|| objectManagementType.equals("memory-manager")) {
							return true;
						} else {
							return false;
						}
					}
				}
				return false;
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				return false;
			} catch (SAXException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
	 
	 private static boolean isValidFileNode(Node node) {
			if (node != null)
				return (isValidFileNode(node.getLocalName()));
			return false;
		}
		
		private static boolean isValidFileNode(String name) {
			if (name==null)
				return false;
			String ignList[] = { "#text", "#comment" };
			for (String ign: ignList) {
				if (ign.equalsIgnoreCase(name)) {
					return false;
				}
			}
			return true;
		}
}