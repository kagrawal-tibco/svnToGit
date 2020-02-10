package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.Cluster;
import com.tibco.cep.studio.cluster.topology.model.Clusters;

public class ClustersImpl extends ClusterTopology{

	public ClustersImpl(Clusters clusters){
		this.clusters = clusters;
	}

	public List<Cluster> getCluster() {
		return clusters.getCluster();
	}

	public void addCluster(ClusterImpl cluster){
		getCluster().add(cluster.getCluster());
		notifyObservers();
	}

	public void addCluster(int index, ClusterImpl cluster){
		getCluster().add(index, cluster.getCluster());
		notifyObservers();
	}

	public void removeCluster(ClusterImpl cluster){
		getCluster().remove(cluster.getCluster());
		notifyObservers();
	}

	public void removeCluster(int index){
		getCluster().remove(index);
		notifyObservers();
	}

	public Clusters getClusters() {
		return clusters;
	}
}