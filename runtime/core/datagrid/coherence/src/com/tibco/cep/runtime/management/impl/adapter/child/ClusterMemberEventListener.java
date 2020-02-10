/**
 * 
 */
package com.tibco.cep.runtime.management.impl.adapter.child;

import java.util.Collection;

import com.tangosol.net.Member;
import com.tangosol.net.MemberEvent;
import com.tangosol.net.MemberListener;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.util.FQName;

/**
 * @author Nick Xu
 *
 */
class ClusterMemberEventListener implements MemberListener{
    protected RemoteProcessManagerImpl processManager;
    
    public ClusterMemberEventListener(RemoteProcessManagerImpl processManager){
    	this.processManager = processManager;
    }
    
    public void memberJoined(MemberEvent evt){
    	//System.out.println("____________some member just joined the cluster: " + evt);
    }

    public void memberLeaving(MemberEvent evt){
    	
    }

    public void memberLeft(MemberEvent evt){
    	Member member = evt.getMember();
    	String hostname = member.getMachineName();
    	if(hostname == null)hostname = "";
    	String processID = member.getProcessName();
    	String processWithHost = processID + "@" + hostname;
    	System.out.println("---->Received Member Left Event for: "+processWithHost);
        RemoteCoherenceManagementTableImpl managementTable =
            processManager.getManagementTableLocal();
        if(managementTable.target == null){
        	System.out.println("The managementTable has not been initialized");
        	return;
        }
        try{
			Collection<Domain> domains = managementTable.getDomains();
			for (Domain domain : domains) {
				if (domain == null){
					continue;
				}			
				FQName domainName = domain.safeGet(DomainKey.NAME);
				String[] componentNames = domainName.getComponentNames();
				if(componentNames[1] == null || componentNames[1] == "") break;
				String nameInTable = componentNames[1].toLowerCase();
				processWithHost = processWithHost.toLowerCase();
				//System.out.println("----->Domain Name: "+ componentNames[0] + componentNames[1]);
				if(nameInTable.equalsIgnoreCase(processWithHost) || nameInTable.startsWith(processWithHost) || processWithHost.startsWith(nameInTable)){
					managementTable.removeDomain(domainName);
					System.out.println(domainName.toString()+" has been removed from Management Table.");
				}
			}
        }
        catch(RuntimeException rte){
        	System.out.println("Could not obtain management domain information.");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }        
}