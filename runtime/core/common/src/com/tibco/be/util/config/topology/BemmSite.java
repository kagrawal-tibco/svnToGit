package com.tibco.be.util.config.topology;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class BemmSite {
	private String name;
    private Logger logger;
    private String fqname;
    private HashMap<String, BemmCluster> clusterNameToCluster;
    private File stFile;

    public BemmSite(File stFile, String siteName)throws Exception {
        this.stFile = stFile;
        clusterNameToCluster = new HashMap<String, BemmCluster>();
        logger = LogManagerFactory.getLogManager().getLogger(this.getClass());

        if ((siteName == null) || siteName.isEmpty()){
			fqname = "/site";
			this.name = "Site";
		} else{
			fqname = "/" + siteName;
			this.name = siteName;
		}

	}

	public void addCluster(XiNode siteXiNode) throws Exception {
        //Host Resources
        final XiNode hrNode = XiChild.getChild(siteXiNode, TopologyNS.Elements.HOST_RESOURCES);

		//Clusters Node
		final XiNode clustersNode = XiChild.getChild(siteXiNode, TopologyNS.Elements.CLUSTERS);

        for (Iterator i = XiChild.getIterator(clustersNode, TopologyNS.Elements.CLUSTER); i.hasNext();) {
            final XiNode clusterNode = (XiNode)i.next();
            final XiNode masterNode = XiChild.getChild(clusterNode, TopologyNS.Elements.MASTER_FILES);
            final String masterCddPath = XiChild.getChild(masterNode, TopologyNS.Elements.CDD_MASTER).getStringValue().trim();


            String clustNameFromMasterCdd = "";
            try {
                final ClusterConfig clustConf = CddConfigUtil.getInstance(masterCddPath).getClusterConfig();
                clustNameFromMasterCdd = CddTools.getValueFromMixed(clustConf.getName());

                if(clustNameFromMasterCdd == null ||
                   clustNameFromMasterCdd.trim().isEmpty()) {
                    throw new RuntimeException("Could not obtain cluster name " +
                                               "from master CDD file: " + masterCddPath);
                }

                String clustNameFromSt =
                        clusterNode.getAttributeStringValue(TopologyNS.Attributes.NAME);

                if(clustNameFromSt == null ||
                   clustNameFromSt.trim().isEmpty()) {
                   throw new RuntimeException("Could not obtain cluster name from site " +
                                              "topology file: " + stFile.getAbsolutePath());
                }

                clustNameFromMasterCdd = clustNameFromMasterCdd.trim();
                clustNameFromSt = clustNameFromSt.trim();

                if (!clustNameFromSt.equals(clustNameFromMasterCdd)) {
                    throw new RuntimeException(
                        String.format("Cluster name '%s' specified in the site " +
                                      "topology file '%s' does not match the " +
                                      "cluster name '%s' specified in the master " +
                                      "CDD file '%s'. The names must match!",
                                      clustNameFromSt,
                                      stFile.getAbsolutePath(),
                                      clustNameFromMasterCdd,
                                      masterCddPath ) );
                }

                if (clusterNameToCluster.containsKey(clustNameFromMasterCdd)) {
                    throw new RuntimeException(
                      String.format("Duplicated cluster name '%s' " +
                                    "found in site topology file '%s' " +
                                    "and master cdd file '%s'. %s",
                                    clustNameFromMasterCdd,
                                    stFile.getAbsolutePath(),
                                    masterCddPath,
                                    "Cluster with the same name already defined!")
                    );
                }

                clusterNameToCluster.put(clustNameFromMasterCdd,
                                        new BemmCluster(fqname,
                                            clustNameFromMasterCdd,
                                            clusterNode,
                                            hrNode,
                                            logger,
                                            stFile) );

            } catch (Exception e)  {
                logger.log(Level.ERROR, "", e);
                //if I don't create a new thread, somehow the shutdown process
                //halts while attempting to close the HTTP channel.
                new Thread(){
                    @Override
                    public void run() {
                        Runtime.getRuntime().exit(-1);  //it calls the shutdown hook
                    }
                }.start();
            }
        }
	}

    public String getName() {
        return name;
    }

    public Collection<BemmCluster> getClusters(){
        return Collections.unmodifiableCollection(clusterNameToCluster.values());
	}

	public BemmCluster getCluster(String clusterName){
        clusterName = clusterName != null ? clusterName.trim() : null;
        return clusterNameToCluster.get(clusterName);
	}
}
