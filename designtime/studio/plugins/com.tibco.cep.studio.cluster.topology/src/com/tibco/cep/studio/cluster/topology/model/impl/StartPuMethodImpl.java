package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Hawk;
import com.tibco.cep.studio.cluster.topology.model.Pstools;
import com.tibco.cep.studio.cluster.topology.model.Ssh;
import com.tibco.cep.studio.cluster.topology.model.StartPuMethod;

public class StartPuMethodImpl extends ClusterTopology{

	HawkImpl hawk;
	PstoolsImpl pstools;
	SshImpl ssh;
	
	public StartPuMethodImpl(StartPuMethod startPuMethod){
		this.startPuMethod = startPuMethod;
	}

	public Hawk getHawk() {
		if(startPuMethod!=null)
		{
		return startPuMethod.getHawk();
		}else
			return ((new HawkImpl(null)).getHawk());
	}

	public void setHawk(HawkImpl value) {
		hawk = value;
		if(startPuMethod!=null)
		{
		startPuMethod.setHawk(value == null ? null : value.getHawk());
		}
		
		notifyObservers();
	}

	public Pstools getPstools() {
		if(startPuMethod!=null){
		return startPuMethod.getPstools();
		}else{
			return((new PstoolsImpl(null)).getPstools());
		}
	}

	public void setPstools(PstoolsImpl value) {
		pstools = value;
		if(startPuMethod!=null)
		{
		startPuMethod.setPstools(value == null ? null : value.getPstools());
		}
		notifyObservers();
	}

	public Ssh getSsh() {
		if(startPuMethod!=null)
		{
		return startPuMethod.getSsh();
		}else{
			return ((new SshImpl(null)).getSsh());
		}
			
	}

	public void setSsh(SshImpl value) {
		ssh = value;
		if(startPuMethod!=null)
		{
		startPuMethod.setSsh(value == null ? null : value.getSsh());
		}
		notifyObservers();
	}

	public StartPuMethod getStartPuMethod() {
		return startPuMethod;
	}
}
