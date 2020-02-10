package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.HostResource;
import com.tibco.cep.studio.cluster.topology.model.HostResources;

public class HostResourcesImpl extends ClusterTopology{

	public HostResourcesImpl(HostResources hostResources){
		this.hostResources = hostResources;
	}

	public List<HostResource> getHostResource() {
		return hostResources.getHostResource();
	}
	
	public void addHostResource(HostResourceImpl hostResource){
		getHostResource().add(hostResource.getHostResource());
		notifyObservers();
	}

	public void addHostResource(int index, HostResourceImpl hostResource){
		getHostResource().add(index, hostResource.getHostResource());
		notifyObservers();
	}

	public void removeHostResource(HostResourceImpl hostResource){
		getHostResource().remove(hostResource.getHostResource());
		notifyObservers();
	}

	public void removeHostResource(int index){
		getHostResource().remove(index);
		notifyObservers();
	}

	public HostResources getHostResources() {
		return hostResources;
	}
}