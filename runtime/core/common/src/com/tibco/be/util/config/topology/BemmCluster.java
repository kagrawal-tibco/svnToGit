/**
 *
 */
package com.tibco.be.util.config.topology;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.io.File;
import java.net.UnknownHostException;
import java.util.*;


public class BemmCluster {
	private String fqname;
	private String name;
	private XiNode clusterXiNode;
    private XiNode hrXiNode;
    private Logger logger;
    private String stFilePath;
    private File stFile;

    private String masterCddPath;
    private String masterEarPath;
    private String runBeVersion;
    private HashMap<String,BemmMappedHost> hostIdToMappedHost;      //Key: id of the host.
                                            //value: HostResources whose id's are used in the deployment mappings

    public BemmCluster(String siteFqn, String clusterName, XiNode clusterXiNode,
            XiNode hrXiNode, Logger logger, File stFile) throws Exception {
        this.name = clusterName;
        this.fqname = siteFqn + "/" + clusterName;
        this.clusterXiNode = clusterXiNode;
        this.hrXiNode = hrXiNode;
        this.logger = logger;
        this.stFile = stFile;
        this.stFilePath = stFile.getAbsolutePath();
        this.hostIdToMappedHost = new HashMap<String,BemmMappedHost>();

        parseAndCreateObjs();
    }

    private void parseAndCreateObjs() throws Exception {
        final HashMap<String,BemmDeploymentUnit> duIdToDeployUnit = new HashMap<String,BemmDeploymentUnit>();
        final Map<String, BemmHostResource> hostIdToHostRsrc = new HashMap<String, BemmHostResource>();


        //Parse Master files info
        final XiNode masterFilesNode = XiChild.getChild(clusterXiNode, TopologyNS.Elements.MASTER_FILES);
        final XiNode masterEarNode = XiChild.getChild(masterFilesNode, TopologyNS.Elements.EAR_MASTER);
        final XiNode masterCddNode = XiChild.getChild(masterFilesNode, TopologyNS.Elements.CDD_MASTER);

        if (masterEarNode == null || masterCddNode == null) {
            throw new RuntimeException("Master EAR and/or CDD node not configured in site topology file: " + stFilePath);
        }

        masterEarPath = masterEarNode.getStringValue().trim();
        masterCddPath = masterCddNode.getStringValue().trim();

        if (masterEarPath == null || masterEarPath.isEmpty() ||
            masterCddPath == null || masterCddPath.isEmpty()) {
            throw new RuntimeException("Master EAR and/or CDD path not specified in site topology file: " + stFilePath);
        }

        //Parse Run BE version info
        final XiNode runVersionNode = XiChild.getChild(clusterXiNode, TopologyNS.Elements.RUN_VERSION);
		runBeVersion = XiChild.getChild(runVersionNode, TopologyNS.Elements.BE_RUNTIME)
                              .getAttributeStringValue(TopologyNS.Attributes.VERSION).trim();

        //Parse DU's Info
        final XiNode dusNode = XiChild.getChild(clusterXiNode, TopologyNS.Elements.DEPLOY_UNITS);
        if (dusNode != null) {
            for (Iterator i = XiChild.getIterator(dusNode, TopologyNS.Elements.DEPLOY_UNIT); i.hasNext();) {
                final XiNode du = (XiNode) i.next();
                final String duid = du.getAttributeStringValue(TopologyNS.Attributes.ID).trim();

                //this is where the deployment unit objects are created. They are created here because it is convenient
                //to do it in sequence when parsing the site topology file
                duIdToDeployUnit.put(duid, new BemmDeploymentUnit(du, masterCddPath, masterEarPath, logger));
            }
        } else {
            logger.log(Level.WARN, "No deployment units configured in site topology file: %s.", stFilePath);
        }

        //Parse Host Resource Info. It's convenient to do it here because
        //the host resource ID is needed to resolve the deployment mappings
        for (Iterator i = XiChild.getIterator(hrXiNode, TopologyNS.Elements.HOST_RESOURCE); i.hasNext();) {
            final XiNode hostNode = (XiNode) i.next();
            final String hostID = hostNode.getAttributeStringValue(TopologyNS.Attributes.ID).trim();
            hostIdToHostRsrc.put(hostID, new BemmHostResource(hostNode, logger));
        }

        //Parse Deployment Mappings Info
        final XiNode depMappingsNode = XiChild.getChild(clusterXiNode, TopologyNS.Elements.DEPLOY_MAPPINGS);
        if (depMappingsNode != null) {
            for (Iterator i = XiChild.getIterator(depMappingsNode, TopologyNS.Elements.DEPLOY_MAPPING); i.hasNext();) {
                final XiNode depMapping = (XiNode) i.next();
                final String duIdRef = depMapping.getAttributeStringValue(TopologyNS.Attributes.DU_REF);
                final String hostIdRef = depMapping.getAttributeStringValue(TopologyNS.Attributes.HOST_REF);

                BemmMappedHost mh = hostIdToMappedHost.get(hostIdRef);
                final BemmDeploymentUnit du = duIdToDeployUnit.get(duIdRef);
                final BemmHostResource hr = hostIdToHostRsrc.get(hostIdRef);

                if(du != null && hr != null) {
                    if(mh == null) {
                        mh = new BemmMappedHost(fqname, runBeVersion, hr,logger);
                        hostIdToMappedHost.put(hostIdRef, mh);
                    }
                    du.setFqName(mh.getHostFqName());         //Needs to be done here because the DU objects are created before the HostFqName is known
                    mh.addDu(du);
                }
                else{
                    logger.log(Level.ERROR, "Cannot find host resource ID '%s' or deployment unit ID '%s'  " +
                            " in site topology file: '%s'.", hostIdRef, duIdRef, stFilePath);
                }
            }
        } else {
            logger.log(Level.WARN, "Invalid deployment mapping in site topology: %s", stFilePath);
        }
	}

	public BemmMappedHost getMappedHost(String hostNameOrIp) throws UnknownHostException {
		if(hostNameOrIp == null || (hostNameOrIp=hostNameOrIp.trim()).isEmpty()){
			return null;
		}

		for (String key : hostIdToMappedHost.keySet()) {
			BemmMappedHost bm = hostIdToMappedHost.get(key);
            BemmHostResource hr = bm.getHostResource();
			ProcessInfo.ensureFQDN(hostNameOrIp, hr.getIpAddress());

            if (hr.getHostFqName().equals(hostNameOrIp) ||
                hr.getIpAddress().equals(hostNameOrIp)) {
            	return bm;
            }
		}
		return null;
	}

	public Collection<BemmMappedHost> getClusterHosts() {
        return Collections.unmodifiableCollection(hostIdToMappedHost.values());
	}

	public String getName(){
		return name;
	}

	public String getFqname() {
		return fqname;
	}

	public String getMasterCddPath() {
		return masterCddPath;
	}

	public String getMasterEarPath() {
		return masterEarPath;
	}

	public String getRunBeVersion() {
		return runBeVersion;
	}
}
