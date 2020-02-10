package com.tibco.cep.runtime.management.impl.adapter.child;


import java.util.TimerTask;

import com.tangosol.net.DistributedCacheService;
import com.tibco.cep.runtime.management.impl.adapter.child.RemoteCoherenceMetricTableImpl.ListenerBroker;
import com.tibco.cep.runtime.management.impl.cluster.CoherenceMetricTable;
import com.tibco.cep.runtime.util.FQName;

/**
 * @author Nick Xu
 *
 */
public class RegisterListenerTask extends TimerTask {

	protected CoherenceMetricTable target;
	protected FQName fqnToListenTo;
	protected ListenerBroker broker;
	protected DistributedCacheService dcs;
	
	public RegisterListenerTask(DistributedCacheService dcs,CoherenceMetricTable target,ListenerBroker broker,FQName fqnToListenTo) {
		this.dcs = dcs;
		this.target=target;
		this.fqnToListenTo=fqnToListenTo;
		this.broker=broker;
	}

	@Override
	public void run() {
		int memberSize = 0;
		if(dcs != null){
			memberSize = this.getStorageEnabledMemberSize(dcs);
		}
		if(memberSize > 0){
	        try{ 	
	        	target.registerListener(broker, fqnToListenTo);
	        	broker.addFQNSubscription(fqnToListenTo);
	        	cancel();
	        }  
	        catch(Exception e){
	        	//ignore
	        	System.out.println("RegisterListenerTask: Listener register failed!");
	        }
		}

	}

	private int getStorageEnabledMemberSize(DistributedCacheService ds){
		return ds.getStorageEnabledMembers().size();
	}
	
}
