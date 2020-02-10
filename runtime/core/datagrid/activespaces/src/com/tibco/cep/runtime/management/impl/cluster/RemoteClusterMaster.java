package com.tibco.cep.runtime.management.impl.cluster;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.tibco.as.space.MemberDef;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.topology.CddConfigUtil;
import com.tibco.be.util.config.topology.MMStMasterCddPathFinder;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;

/**
 * @author Nick
 *
 */
public class RemoteClusterMaster implements Service {

	private Logger logger;
	private Properties props;
	private HashMap<String, RemoteClusterHandler> clusterUrlToRemClustHand;
	private static final String MM_LISTEN_URL = "be.mm.cluster.as.listen.url";

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.Service#getId()
	 */
	@Override
	public String getId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.Service#init(com.tibco.cep.runtime.config.Configuration, java.lang.Object[])
	 */
	@Override
	public void init(Configuration configuration, Object... otherArgs)
			throws Exception {
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.Service#start()
	 */
	@Override
	public void start() throws Exception {
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.Service#stop()
	 */
	@Override
	public void stop() throws Exception {
	}
	
	public HashMap<String, RemoteClusterHandler> connect(Properties props, Logger logger) throws Exception{
		this.props = props;
		this.logger = logger;
		this.clusterUrlToRemClustHand = new HashMap<String, RemoteClusterHandler>();
		List<String> masterCddPaths = MMStMasterCddPathFinder.getMasterCddsPathInAllStFiles(logger);

        for (String masterCddPath : masterCddPaths) {
            if (masterCddPath != null && !masterCddPath.trim().isEmpty()) {

                logger.log(Level.DEBUG, "Loading master CDD file '%s'", masterCddPath);

                final CddConfigUtil cddConfigUtil = CddConfigUtil.getInstance(masterCddPath);
                ClusterConfig config = cddConfigUtil.getClusterConfig();
                Properties tempProp = (Properties) config.toProperties();
                String clusterUrl = CddTools.getValueFromMixed(config.getName());

                if (clusterUrlToRemClustHand.get(clusterUrl) == null) {
                    String listenUrl = tempProp.getProperty(MM_LISTEN_URL);
                    
                    Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
                	String discoverUrl;
                	if (cluster != null) {
                		GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
                		discoverUrl = gVs.substituteVariables(tempProp.getProperty(ASConstants.PROP_DISCOVER_URL)).toString();
                	}
                	else {
	                    discoverUrl = tempProp.getProperty(ASConstants.PROP_DISCOVER_URL);
                	}
                	
                    MemberDef cd = MemberDef.create()
                                            .setListen(listenUrl)
                                            .setDiscovery(discoverUrl);

                    clusterUrlToRemClustHand.put(clusterUrl,
                                                 new RemoteClusterHandler(clusterUrl, cd));
                }
            }
        }
		return clusterUrlToRemClustHand;
	}


	public RemoteClusterHandler getHandler(String clusterName){
		return clusterUrlToRemClustHand.get(clusterName);
	}
}
