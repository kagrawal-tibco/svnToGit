package com.tibco.cep.studio.cluster.topology.ui;


import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.Be;
import com.tibco.cep.studio.cluster.topology.model.DeployedFiles;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.HostResource;
import com.tibco.cep.studio.cluster.topology.model.ObjectFactory;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.studio.cluster.topology.model.Software;
import com.tibco.cep.studio.cluster.topology.model.StartPuMethod;
import com.tibco.cep.studio.cluster.topology.model.UserCredentials;
import com.tibco.cep.studio.cluster.topology.model.impl.BeImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeployedFilesImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HawkImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitsConfigImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.PstoolsImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SoftwareImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SshImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.StartPuMethodImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.UserCredentialsImpl;

/**
* @author mgoel
*
**/
public class SiteEntityFactory {
	private ObjectFactory objectFactory;
	
	public SiteEntityFactory()
	{
		this.objectFactory=new ObjectFactory();
	}
	
	 public ProcessingUnitConfigImpl newProcessingUnitConfigImpl(ProcessingUnitConfigImpl puImpl , DeploymentUnitImpl duImpl) {
		 ProcessingUnitConfig pu =null;	
		// if(puImpl != null){
	    		pu = objectFactory.createProcessingUnitConfig();
	    	//}
	    	ProcessingUnitConfigImpl processingUnitConfigImpl = new ProcessingUnitConfigImpl(pu,duImpl);
	    	processingUnitConfigImpl.setId(puImpl.getId());
	    	
	    	processingUnitConfigImpl.setJmxport(puImpl.getJmxport());
	    	processingUnitConfigImpl.setPuid(puImpl.getPuid());
	    	processingUnitConfigImpl.setUseAsEngineName(puImpl.isUseAsEngineName());
	    	
	    	return processingUnitConfigImpl;
	    }
	 
	 public HostResourceImpl newHostResourceImpl(HostResourceImpl hrImpl) {
	    	//if(hr == null){
		
		 HostResource hr=null;
	    		hr = objectFactory.createHostResource();
	    	//}
	    	HostResourceImpl hostResourceImpl = new HostResourceImpl(hr);
	    	 if(hrImpl!=null){
	    			
	 	    	if(hrImpl.getId()!=null)
	 	    	{
	 	    		String getId=new String();
	 	    		getId=hrImpl.getId();
	 	    	hostResourceImpl.setId(getId);
	 	    	}else{
	 	    		hostResourceImpl.setId("");
	 	    	}
	    		
	    	if(hrImpl.getHostname()!=null)
	    	{
	    		String gethostname=new String();
	    		gethostname=hrImpl.getHostname();
	    	hostResourceImpl.setHostname(gethostname);
	    	}else{
	    		hostResourceImpl.setHostname("");
	    	}
	    	if(hrImpl.getIp()!=null)
	    	{
	    		String ip;
	    		ip = new String(hrImpl.getIp());
	    		/*System.out.println(ip);
	    		System.out.println(hrImpl.getIp());*/
	    		
	    	hostResourceImpl.setIp(ip);
	    	} else {
	    		hostResourceImpl.setIp("");
	    	}
	    	if(hrImpl.getOsType()!=null){
	    		String getos=new String();
	    		getos=hrImpl.getOsType();
	    	hostResourceImpl.setOsType(getos);
	    	}else{
	    	
	    		hostResourceImpl.setOsType("");
	    	}
	    	///USER CREDENTIALS
	    	UserCredentials	uc = objectFactory.createUserCredentials();
	    	UserCredentialsImpl userCredentialsImpl = new UserCredentialsImpl(uc);
	    	
	    	if(hrImpl.getUserCredentials() != null){
	    		if(hrImpl.getUserCredentials().getPassword()!=null){
	    		userCredentialsImpl.setPassword(hrImpl.getUserCredentials().getPassword());
	    		}else{
	    			userCredentialsImpl.setPassword("");
	    		}
	    		if(hrImpl.getUserCredentials().getUsername()!=null)
	    		{
	    		userCredentialsImpl.setUsername(hrImpl.getUserCredentials().getUsername());
	    		}else{
	    			userCredentialsImpl.setUsername("");
	    		}
	    		hostResourceImpl.setUserCredentials(userCredentialsImpl);
	    	}
	    	
	    	///START PU
	    	StartPuMethod	spm = null;
	    	spm = objectFactory.createStartPuMethod();
	    	
	    	//spm=hr.getStartPuMethod();*/
	    	StartPuMethodImpl startPuMethodImpl = new StartPuMethodImpl(spm);
	    	if(hrImpl.getStartPuMethod() != null){
	    		
	    		startPuMethodImpl.setHawk(new HawkImpl(hrImpl.getStartPuMethod().getHawk()));
	    		startPuMethodImpl.setPstools(new PstoolsImpl(hrImpl.getStartPuMethod().getPstools()));
	    		//(new SshImpl(hrImpl.getStartPuMethod().getSsh()).getPort());
	    		SshImpl sshimpl=new SshImpl(objectFactory.createSsh());
	    		if(hrImpl.getStartPuMethod().getSsh().getPort()!=null){
	    		sshimpl.setPort(new String(hrImpl.getStartPuMethod().getSsh().getPort()));
	    		}else
	    			sshimpl.setPort("22");
	    		startPuMethodImpl.setSsh(sshimpl);
	    		hostResourceImpl.setStartPuMethod(startPuMethodImpl);
	    	}
	    	
	    	///SOFTWARE
	    	
	    	Software sf = objectFactory.createSoftware();
	    	SoftwareImpl softwareImpl = new SoftwareImpl(sf);
	    	List<Be> beList= softwareImpl.getBe();
	    	Be be=null;
	    	if(be == null){
	    		be = objectFactory.createBe();
	    	}
	    	BeImpl beImpl = new BeImpl(be);
	    	if(hrImpl.getSoftware() != null){
	    	
	    	int size=hrImpl.getSoftware().getBe().size();
	    	int i=0;
	    	while(i<size){
	    		if (hrImpl.getSoftware().getBe() != null) {	
	    			SoftwareImpl sofImpl=hrImpl.getSoftware();
	    			BeImpl bimpl=new BeImpl(sofImpl.getBe().get(i));
	    			if(bimpl.getVersion()!=null){
	    				beImpl.setVersion(new String(bimpl.getVersion()));
	    			}else{
	    				beImpl.setVersion("");
	    			}
	    			if(bimpl.getTra()!=null){
	    			beImpl.setTra(new String(bimpl.getTra()));
	    			}else{
	    				beImpl.setTra("");
	    			}
	    			if(bimpl.getHome()!=null){
	    			 beImpl.setHome(new String(bimpl.getHome()));
	    			}else{
	    				 beImpl.setHome("");
	    			}
	    				
	    			beList.add(be);
	    		}
	    	
	    	/*else{
	    		beImpl.setHome("");
    			beImpl.setTra("");
    			beImpl.setVersion("5.0.0");
    			beList.add(be);
	    	}*/
	    		i++;
	    	}
	      		hostResourceImpl.setSoftware(softwareImpl);
	    	}
		 }
	        return hostResourceImpl;
	    }

	public DeploymentUnitImpl newDeploymentUnitImpl (DeploymentUnitImpl duimpl){
		
		 DeploymentUnit du=objectFactory.createDeploymentUnit();
		 DeploymentUnitImpl newduimpl= new DeploymentUnitImpl(du);
		 if(duimpl!=null){
			 if(duimpl.getId()!=null){
				 newduimpl.setId(new String(duimpl.getId()));
			 }else{
				 newduimpl.setId("");
			 }
		 if(duimpl.getName()!=null){
			 newduimpl.setName(new String(duimpl.getName()));
		 }else{
			 newduimpl.setName("");
		 }
		 DeployedFiles depFiles= objectFactory.createDeployedFiles();
		 DeployedFilesImpl depFilesImpl=new DeployedFilesImpl(depFiles);
		 if(duimpl.getDeployedFiles()!=null){
			 depFilesImpl.setCddDeployed(new String(duimpl.getDeployedFiles().getCddDeployed()));
			 depFilesImpl.setEarDeployed(new String(duimpl.getDeployedFiles().getEarDeployed()));
				 newduimpl.setDeployedFiles(depFilesImpl);
		 }
		 
		 int i=0;
		 ProcessingUnitsConfig puc=objectFactory.createProcessingUnitsConfig();
		 ProcessingUnitsConfigImpl pucImpl=new ProcessingUnitsConfigImpl(puc);
		 if(duimpl.getProcessingUnitsConfig()!=null){
			 ProcessingUnitsConfigImpl oldPucImpl= new ProcessingUnitsConfigImpl(duimpl.getProcessingUnitsConfig());
			 List<ProcessingUnitConfig> oldpuList=oldPucImpl.getProcessingUnitConfig();
			 int size=(oldpuList).size();
			 ProcessingUnitConfigImpl puImpl;
			  while(i<size){
				 puImpl=newProcessingUnitConfigImpl(
						 new ProcessingUnitConfigImpl(oldpuList.get(i++),duimpl),
						 newduimpl);
				// ClusterTopologyUtils.getPUIDSet().add(puImpl.getProcessingUnitConfig().getId());
				 //puList.add(puImpl.getProcessingUnitConfig());
				 pucImpl.addProcessingUnitConfig(puImpl);
			  }
			
			 newduimpl.setProcessingUnitsConfig(pucImpl);
		 }
		 }
		 return newduimpl;
		 
	 }

}
