package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Clusters;
import com.tibco.cep.studio.cluster.topology.model.HostResources;
import com.tibco.cep.studio.cluster.topology.model.Site;


public class SiteImpl extends ClusterTopology{

	public SiteImpl(Site site){
		this.site = site;
	}

	public Clusters getClusters() {
		return site.getClusters();
	}

	public void setClusters(ClustersImpl value) {
		site.setClusters(value.getClusters());
		notifyObservers();
	}

	public HostResources getHostResources() {
		return site.getHostResources();
	}

	public void setHostResources(HostResourcesImpl value) {
		site.setHostResources(value.getHostResources());
		notifyObservers();
	}

	public String getName() {
		return site.getName();
	}

	public void setName(String value) {
		site.setName(value);
		notifyObservers();
	}

	public String getDescription() {
		return site.getDescription();
	}

	public void setDescription(String value) {
		site.setDescription(value);
		notifyObservers();
	}
	
	public Site getSite() {
		return site;
	}
}
