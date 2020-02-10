package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.handler.ClusterTopologyModelChangeListener;
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
import com.tibco.cep.studio.cluster.topology.model.ObjectFactory;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.studio.cluster.topology.model.Pstools;
import com.tibco.cep.studio.cluster.topology.model.RunVersion;
import com.tibco.cep.studio.cluster.topology.model.Site;
import com.tibco.cep.studio.cluster.topology.model.Software;
import com.tibco.cep.studio.cluster.topology.model.Ssh;
import com.tibco.cep.studio.cluster.topology.model.StartPuMethod;
import com.tibco.cep.studio.cluster.topology.model.UserCredentials;
import com.tibco.pof.admindomain.impl.MachineImpl;

public class ClusterTopologyFactory{
	
	private ClusterTopologyModelChangeListener o;
	private ObjectFactory objectFactory;
	
	public ClusterTopologyFactory(ClusterTopologyModelChangeListener o){
		this.o = o;
		this.objectFactory = new ObjectFactory();
	}

    /**
     * Create an instance of {@link ProcessingUnitsConfigImpl }
     * 
     */
    public ProcessingUnitsConfigImpl createProcessingUnitsConfigImpl(ProcessingUnitsConfig pusc,DeploymentUnit du) {
    	if(pusc == null){
    		pusc = objectFactory.createProcessingUnitsConfig();
    	}
    	ProcessingUnitsConfigImpl processingUnitsConfigImpl = new ProcessingUnitsConfigImpl(pusc);
    	processingUnitsConfigImpl.addObserver(o);
        return processingUnitsConfigImpl;
    }

    /**
     * Create an instance of {@link RunVersionImpl }
     * 
     */
    public RunVersionImpl createRunVersionImpl(RunVersion rv) {
    	if(rv == null){
    		rv = objectFactory.createRunVersion();
    	}
    	RunVersionImpl runVersionImpl = new RunVersionImpl(rv);
    	if(rv.getBeRuntime() != null){
    		runVersionImpl.setBeRuntime(createBeRuntimeImpl(rv.getBeRuntime()));
    	}
    	runVersionImpl.addObserver(o);
        return runVersionImpl;
    }

    /**
     * Create an instance of {@link MasterFilesImpl }
     * 
     */
    public MasterFilesImpl createMasterFilesImpl(MasterFiles md) {
    	if(md == null){
    		md = objectFactory.createMasterFiles();
    	}
    	MasterFilesImpl masterFilesImpl = new MasterFilesImpl(md);
    	masterFilesImpl.addObserver(o);
        return masterFilesImpl;
    }

    /**
     * Create an instance of {@link ProcessingUnitImpl }
     * 
     */
    public ProcessingUnitConfigImpl createProcessingUnitConfigImpl(ProcessingUnitConfig pu , DeploymentUnitImpl duImpl) {
    	if(pu == null){
    		pu = objectFactory.createProcessingUnitConfig();
    	}
    	ProcessingUnitConfigImpl processingUnitConfigImpl = new ProcessingUnitConfigImpl(pu,duImpl);
    	processingUnitConfigImpl.addObserver(o);
        return processingUnitConfigImpl;
    }

    /**
     * Create an instance of {@link MachineInfoImpl }
     * 
     */
    public HostResourceImpl createHostResourceImpl(HostResource hr) {
    	if(hr == null){
    		hr = objectFactory.createHostResource();
    	}
    	HostResourceImpl hostResourceImpl = new HostResourceImpl(hr);
    	if(hr.getUserCredentials() != null){
    		hostResourceImpl.setUserCredentials(createUserCredentialsImpl(hr.getUserCredentials()));
    	}
    	if(hr.getStartPuMethod() != null){
    		hostResourceImpl.setStartPuMethod(createStartPuMethodImpl(hr.getStartPuMethod()));
    	}
    	if(hr.getSoftware() != null){
    		hostResourceImpl.setSoftware(createSoftwareImpl(hr.getSoftware()));
    	}
    	hostResourceImpl.addObserver(o);
        return hostResourceImpl;
    }

    /**
     * Create an instance of {@link BeRuntimeImpl }
     * 
     */
    public BeRuntimeImpl createBeRuntimeImpl(BeRuntime br) {
    	if(br == null){
    		br = objectFactory.createBeRuntime();
    	}
    	BeRuntimeImpl beRuntimeImpl = new BeRuntimeImpl(br);
    	beRuntimeImpl.addObserver(o);
        return beRuntimeImpl;
    }

    /**
     * Create an instance of {@link StartPuMethodImpl }
     * 
     */
    public StartPuMethodImpl createStartPuMethodImpl(StartPuMethod spm) {
    	if(spm == null){
    		spm = objectFactory.createStartPuMethod();
    	}
    	StartPuMethodImpl startPuMethodImpl = new StartPuMethodImpl(spm);
    	startPuMethodImpl.addObserver(o);
        return startPuMethodImpl;
    }

    /**
     * Create an instance of {@link HawkImpl }
     * 
     */
    public HawkImpl createHawkImpl(Hawk hawk) {
    	if(hawk == null){
    		hawk = objectFactory.createHawk();
    	}
    	HawkImpl hawkImpl = new HawkImpl(hawk);
    	hawkImpl.addObserver(o);
        return hawkImpl;
    }
    
    /**
     * Create an instance of {@link PstoolsImpl }
     * 
     */
    public PstoolsImpl createPstoolsImpl(Pstools pstools) {
    	if(pstools == null){
    		pstools = objectFactory.createPstools();
    	}
    	PstoolsImpl pstoolsImpl = new PstoolsImpl(pstools);
    	pstoolsImpl.addObserver(o);
        return pstoolsImpl;
    }
    
    /**
     * Create an instance of {@link SshImpl }
     * 
     */
    public SshImpl createSshImpl(Ssh ssh) {
    	if(ssh == null){
    		ssh = objectFactory.createSsh();
    	}
    	SshImpl sshImpl = new SshImpl(ssh);
    	sshImpl.addObserver(o);
        return sshImpl;
    }
    
    /**
     * Create an instance of {@link SoftwareImpl }
     * 
     */
    public SoftwareImpl createSoftwareImpl(Software sf) {
    	if(sf == null){
    		sf = objectFactory.createSoftware();
    	}
    	SoftwareImpl softwareImpl = new SoftwareImpl(sf);
    	softwareImpl.addObserver(o);
        return softwareImpl;
    }

    /**
     * Create an instance of {@link ClusterImpl }
     * 
     */
    public ClusterImpl createClusterImpl(Cluster cl) {
    	if(cl == null){
    		cl = objectFactory.createCluster();
    	}
    	ClusterImpl clusterImpl = new ClusterImpl(cl);
    	if(cl.getMasterFiles() != null){
    		clusterImpl.setMasterFiles(createMasterFilesImpl(cl.getMasterFiles()));
    	}
    	if(cl.getRunVersion() != null){
    		clusterImpl.setRunVersion(createRunVersionImpl(cl.getRunVersion()));
    	}
    	if(cl.getDeploymentUnits() != null){
    		clusterImpl.setDeploymentUnits(createDeploymentUnitsImpl(cl.getDeploymentUnits()));
    	}
    	if(cl.getDeploymentMappings() != null){
    		clusterImpl.setDeploymentMappings(createDeploymentMappingsImpl(cl.getDeploymentMappings()));
    	}
    	clusterImpl.addObserver(o);
        return clusterImpl;
    }

    /**
     * Create an instance of {@link DeploymentUnitsImpl }
     * 
     */
    public DeploymentUnitsImpl createDeploymentUnitsImpl(DeploymentUnits dus) {
    	if(dus == null){
    		dus = objectFactory.createDeploymentUnits();
    	}
    	DeploymentUnitsImpl deploymentUnitsImpl = new DeploymentUnitsImpl(dus);
    	deploymentUnitsImpl.addObserver(o);
        return deploymentUnitsImpl;
    }

    /**
     * Create an instance of {@link DeploymentMappingsImpl }
     * 
     */
    public DeploymentMappingsImpl createDeploymentMappingsImpl(DeploymentMappings dms) {
    	if(dms == null){
    		dms = objectFactory.createDeploymentMappings();
    	}
    	DeploymentMappingsImpl deploymentMappingsImpl = new DeploymentMappingsImpl(dms);
    	deploymentMappingsImpl.addObserver(o);
        return deploymentMappingsImpl;
    }

    /**
     * Create an instance of {@link MachineImpl }
     * 
     */
    public DeploymentUnitImpl createDeploymentUnitImpl(DeploymentUnit du) {
    	if(du == null){
    		du = objectFactory.createDeploymentUnit();
    	}
    	DeploymentUnitImpl deploymentUnitImpl = new DeploymentUnitImpl(du);
    	if(du.getDeployedFiles() != null){
    		deploymentUnitImpl.setDeployedFiles(createDeployedFilesImpl(du.getDeployedFiles()));
    	}
    	deploymentUnitImpl.addObserver(o);
        return deploymentUnitImpl;
    }

    /**
     * Create an instance of {@link SiteImpl }
     * 
     */
    public SiteImpl createSiteImpl(Site site) {
    	if(site == null){
    		site = objectFactory.createSite();
    	}
    	SiteImpl siteImpl = new SiteImpl(site);
    	siteImpl.addObserver(o);
        return siteImpl;
    }

    /**
     * Create an instance of {@link DeployedFilesImpl }
     * 
     */
    public DeployedFilesImpl createDeployedFilesImpl(DeployedFiles df) {
    	if(df == null){
    		df = objectFactory.createDeployedFiles();
    	}
    	DeployedFilesImpl deployedFilesImpl = new DeployedFilesImpl(df);
    	if(df.getCddDeployed() != null){
    		deployedFilesImpl.setCddDeployed(df.getCddDeployed());
    	}
    	if(df.getEarDeployed() != null){
    		deployedFilesImpl.setEarDeployed(df.getEarDeployed());
    	}
    	deployedFilesImpl.addObserver(o);
        return deployedFilesImpl;
    }

    /**
     * Create an instance of {@link ClustersImpl }
     * 
     */
    public ClustersImpl createClustersImpl(Clusters clusters) {
    	if(clusters == null){
    		clusters = objectFactory.createClusters();
    	}
    	ClustersImpl clustersImpl = new ClustersImpl(clusters);
    	clustersImpl.addObserver(o);
        return clustersImpl;
    }

    /**
     * Create an instance of {@link MachineInfoRefImpl }
     * 
     */
    public DeploymentMappingImpl createDeploymentMappingImpl(DeploymentMapping dm) {
    	if(dm == null){
    		dm = objectFactory.createDeploymentMapping();
    	}
    	DeploymentMappingImpl deploymentMappingImpl = new DeploymentMappingImpl(dm);
    	deploymentMappingImpl.addObserver(o);
        return deploymentMappingImpl;
    }

    /**
     * Create an instance of {@link UserCredentialsImpl }
     * 
     */
    public UserCredentialsImpl createUserCredentialsImpl(UserCredentials uc) {
    	if(uc == null){
    		uc = objectFactory.createUserCredentials();
    	}
    	UserCredentialsImpl userCredentialsImpl = new UserCredentialsImpl(uc);
    	userCredentialsImpl.addObserver(o);
        return userCredentialsImpl;
    }

    /**
     * Create an instance of {@link ParkOfMachinesImpl }
     * 
     */
    public HostResourcesImpl createHostResourcesImpl(HostResources hrs) {
    	if(hrs == null){
    		hrs = objectFactory.createHostResources();
    	}
    	HostResourcesImpl hostResourcesImpl = new HostResourcesImpl(hrs);
    	hostResourcesImpl.addObserver(o);
        return hostResourcesImpl;
    }

    /**
     * Create an instance of {@link BeImpl }
     * 
     */
    public BeImpl createBeImpl(Be be) {
    	if(be == null){
    		be = objectFactory.createBe();
    	}
    	BeImpl beImpl = new BeImpl(be);
    	beImpl.addObserver(o);
        return beImpl;
    }
}