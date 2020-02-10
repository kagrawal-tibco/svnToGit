package com.tibco.cep.studio.cluster.topology.wizards;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.topology.CddConfigUtil;
import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.model.topology.BeRuntime;
import com.tibco.cep.studio.core.model.topology.Cluster;
import com.tibco.cep.studio.core.model.topology.Clusters;
import com.tibco.cep.studio.core.model.topology.DeploymentUnits;
import com.tibco.cep.studio.core.model.topology.HostResources;
import com.tibco.cep.studio.core.model.topology.MasterFiles;
import com.tibco.cep.studio.core.model.topology.RunVersion;
import com.tibco.cep.studio.core.model.topology.Site;
import com.tibco.cep.studio.core.model.topology.TopologyFactory;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;

/**
 * 
 * @author ggrigore
 *
 */
public class NewClusterTopologyWizard extends AbstractNewEntityWizard<NewClusterTopologyWizardPage> {
	
	protected final static String DEFAULT_CLUSTER_NAME = "Default Cluster";

	public NewClusterTopologyWizard(){
		setWindowTitle(Messages.getString("new.deployment.wizard.title"));
	}
	
	@Override
	protected void createEntity(String name, String baseURI, IProgressMonitor monitor) throws Exception {
		Site site = TopologyFactory.eINSTANCE.createSite();
		site.setName(name);
		site.setDescription(page.getTypeDesc());
		createClusterTopology(site);
		StudioResourceUtils.persistEntity(site, name, StudioResourceUtils.getFolder(getModelFile()),
				CommonIndexUtils.SITE_TOPOLOGY_EXTENSION, baseURI, project.getName(), monitor);	
	}

	@Override
	public void addPages() {
		try {
			if(_selection != null && !_selection.isEmpty()){
				project =  StudioResourceUtils.getProjectForWizard(_selection);
			}
			page = new NewClusterTopologyWizardPage(getWizardTitle(),_selection, project, getEntityType());
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);
       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createClusterTopology(Site site) {
		
		NewClusterTopologyWizardPage clusterPage = (NewClusterTopologyWizardPage)page;
		IFile cddFile = clusterPage.getCDDFile();
		
		// host-resources
		HostResources hrs =TopologyFactory.eINSTANCE.createHostResources();
		
		// clusters
		Clusters clusters= TopologyFactory.eINSTANCE.createClusters();
		List<Cluster> clustersList = clusters.getCluster();
		
		Cluster cluster= TopologyFactory.eINSTANCE.createCluster();
		cluster.setName(DEFAULT_CLUSTER_NAME);
		
		CddConfigUtil ccUtil = null;
		if (cddFile != null && cddFile.getLocation() != null) {
			String cddProject = cddFile.getLocation().toOSString();
			cluster.setProjectCdd(cddProject);
			if (cddProject != null && cddProject.trim().length() > 0) {
				try {
					ccUtil = CddConfigUtil.getInstance(cddProject);
					String clusterName = CddTools.getValueFromMixed(ccUtil.getClusterConfig().getName());
					cluster.setName(clusterName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			cluster.setProjectCdd("");
		}
		
		MasterFiles masterfiles = TopologyFactory.eINSTANCE.createMasterFiles();
		masterfiles.setCddMaster("");
		masterfiles.setEarMaster("");		
		cluster.setMasterFiles(masterfiles);
		
		RunVersion runVersion = TopologyFactory.eINSTANCE.createRunVersion();
		BeRuntime runTime = TopologyFactory.eINSTANCE.createBeRuntime();
		runTime.setVersion(cep_containerVersion.version);
		runVersion.setBeRuntime(runTime);
		cluster.setRunVersion(runVersion);
		
		DeploymentUnits dus = TopologyFactory.eINSTANCE.createDeploymentUnits();
		cluster.setDeploymentUnits(dus);
		
		clustersList.add(cluster);
		
		site.setClusters(clusters);
		site.setHostResources(hrs);
	}
	
	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_CLUSTER_TOPOLOGY;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.deployment.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.deployment.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/clusterWizard.png");
	}

}