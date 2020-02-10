package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.Observable;

import com.tibco.cep.studio.cluster.topology.model.Be;
import com.tibco.cep.studio.cluster.topology.model.BeRuntime;
import com.tibco.cep.studio.cluster.topology.model.Cluster;
import com.tibco.cep.studio.cluster.topology.model.Clusters;
import com.tibco.cep.studio.cluster.topology.model.DeployedFiles;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMapping;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMappings;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnits;
import com.tibco.cep.studio.cluster.topology.model.Hawk;
import com.tibco.cep.studio.cluster.topology.model.HostResource;
import com.tibco.cep.studio.cluster.topology.model.HostResources;
import com.tibco.cep.studio.cluster.topology.model.MasterFiles;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.studio.cluster.topology.model.Pstools;
import com.tibco.cep.studio.cluster.topology.model.RunVersion;
import com.tibco.cep.studio.cluster.topology.model.Site;
import com.tibco.cep.studio.cluster.topology.model.Software;
import com.tibco.cep.studio.cluster.topology.model.Ssh;
import com.tibco.cep.studio.cluster.topology.model.StartPuMethod;
import com.tibco.cep.studio.cluster.topology.model.UserCredentials;

public class ClusterTopology extends Observable {

	protected Be be;
	protected BeRuntime beRuntime;
	protected Cluster cluster;
	protected Clusters clusters;
	protected DeployedFiles deployedFiles;
	protected DeploymentMapping deploymentMapping;
	protected DeploymentMappings deploymentMappings;
	protected DeploymentUnit deploymentUnit;
	protected DeploymentUnitImpl deploymentUnitImpl;
	protected DeploymentUnits deploymentUnits;
	protected Hawk hawk;
	protected HostResource hostResource;
	protected HostResources hostResources;
	protected MasterFiles masterFiles;
	protected ProcessingUnitConfig processingUnitConfig;
	protected ProcessingUnitsConfig processingUnitsConfig;
	protected Pstools pstools;
	protected RunVersion runVersion;
	protected Site site;
	protected Software software;
	protected Ssh ssh;
	protected StartPuMethod startPuMethod;
	protected UserCredentials userCredentials;

	 /* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers()
	 */
	public void notifyObservers() {
		 super.setChanged();
		 super.notifyObservers();
	 }

	
}