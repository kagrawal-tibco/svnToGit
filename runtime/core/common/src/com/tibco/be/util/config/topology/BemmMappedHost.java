package com.tibco.be.util.config.topology;

import java.util.HashMap;

import com.tibco.cep.kernel.service.logging.Logger;

public class BemmMappedHost {
                                            // TODO: Cleanup this file
	private String hostFqName;
	private BemmHostResource hostResource;   //describes this host resource
    private String runBeVersion;

    private HashMap<String, BemmDeploymentUnit> mappedDuIdToDeployUnit; //Key: DUID of the DU's that are mapped to this host
    private HashMap<String, String> duNameToDuId;
    private Logger logger;

    public BemmMappedHost(String clusterFqn,
                          String runBeVersion,
                          BemmHostResource hostResource,
                          Logger logger)  throws Exception {

        this.runBeVersion = runBeVersion;
        this.hostResource = hostResource;
        this.logger = logger;
        this.hostFqName = getHostFqName(clusterFqn);

        this.mappedDuIdToDeployUnit = new HashMap<String, BemmDeploymentUnit>();
        this.duNameToDuId= new HashMap<String, String>();

	}

    private String getHostFqName(String clusterFqn) {
        final String hostName = hostResource.getHostFqName();

        if(hostName != null && !hostName.isEmpty())
			return clusterFqn + "/" + hostName;

        return hostFqName = clusterFqn + "/" + hostResource.getIpAddress();
    }

	public void addDu(BemmDeploymentUnit du) {
        mappedDuIdToDeployUnit.put(du.getId(), du);
        duNameToDuId.put(du.getName(), du.getId());
	}

	public String getHostFqName() {
		return hostFqName;
	}

	public String getRunBeVersion() {
		return runBeVersion;
	}

	public BemmHostResource getHostResource() {
		return hostResource;
	}

	public BemmBEHome getBeHome() {
		return hostResource.getBEHome(runBeVersion);
	}

    public String getHostName() {
        return hostResource.getHostFqName();
    }

    public BemmDeploymentUnit getDeployUnitFromId(String duId) {
        duId = duId != null ? duId.trim() : null;
        return mappedDuIdToDeployUnit.get(duId);
    }

    public BemmDeploymentUnit getDeployUnit(String duName) {
         duName = duName != null ? duName.trim() : null;
         return mappedDuIdToDeployUnit.get(duNameToDuId.get(duName));
    }

    public Object[] getMappedDUs() {
        return mappedDuIdToDeployUnit.values().toArray();
    }

}
