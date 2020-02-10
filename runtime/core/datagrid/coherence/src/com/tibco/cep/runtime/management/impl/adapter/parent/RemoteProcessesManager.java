package com.tibco.cep.runtime.management.impl.adapter.parent;

import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.topology.CddConfigUtil;
import com.tibco.be.util.config.topology.MMStMasterCddPathFinder;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 2/1/12
 * Time: 12:31 PM
 */
public class RemoteProcessesManager implements Service {

    private volatile ConcurrentHashMap<String, RemoteProcessMaster> clustNameToRemProcMaster;

    private Logger logger;
    private Properties props;


    public RemoteProcessesManager(Properties props, Logger logger) {
        this.logger = logger;
        this.props = props;
    }

    @Override
    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        //reset each time init is called
        clustNameToRemProcMaster = new ConcurrentHashMap<String, RemoteProcessMaster>();

        List<String> masterCddPaths = MMStMasterCddPathFinder.getMasterCddsPathInAllStFiles(logger);

        for (String masterCddPath : masterCddPaths) {
            if (masterCddPath != null && !masterCddPath.trim().isEmpty()) {

                logger.log(Level.DEBUG, "Loading master CDD file '%s'", masterCddPath);

                final String clustName = getClustNameFromCdd(masterCddPath);

                if (clustNameToRemProcMaster.get(clustName) == null) {
                    logger.log(Level.DEBUG, "Creating Remote Process Manager class for cluster %s ", clustName);

                    clustNameToRemProcMaster.put(clustName, new RemoteProcessMaster(clustName, masterCddPath));
                } else {
                    logger.log(Level.WARN, "Duplicated cluster name '%s' found in CDD file '%s'" +
                            ". Check if a cluster with the same name was already defined in a " +
                            "different CDD file.", clustName, masterCddPath );
                }
            }
        }
    }

    @Override
    public void start() throws Exception {
        //Nothing is to be started at this point - For MM only.
    }

    private String getClustNameFromCdd(String masterCddPath) throws Exception {
        final CddConfigUtil cddConfigUtil = CddConfigUtil.getInstance(masterCddPath);
        final ClusterConfig config = cddConfigUtil.getClusterConfig();
        return CddTools.getValueFromMixed(config.getName());
    }

    /** Iterates over all the remote processes objects and connects each one individually 
     * @throws Exception*/
    public Collection<RemoteProcessMaster> connectAll() throws Exception {
        int n=0;
        final int NUM_PROCS = clustNameToRemProcMaster.size();

        for (String cName : clustNameToRemProcMaster.keySet()) {
            try {
                clustNameToRemProcMaster.get(cName).connect(props, logger);

                logger.log(Level.INFO, "Remote (broker) Process for cluster '%s' SUCCESSFULLY connected. " +
                        "%d of %d processes connected", cName, ++n, NUM_PROCS);
            } catch (Exception e) {
                logger.log(Level.ERROR, e, "FAILED to connect Remote (broker) Process for cluster '%s'. " +
                        "%d of %d processes connected", cName, n, NUM_PROCS);
                
                clustNameToRemProcMaster.remove(cName);
            }
        }

        if(n!= NUM_PROCS)
            logger.log(Level.WARN, "FAILED to connect %d of %d Remote (broker) Processes", NUM_PROCS - n, NUM_PROCS);
        
        return Collections.unmodifiableCollection(clustNameToRemProcMaster.values());
    }

    @Override
    public void stop() throws Exception {
        for (String cName : clustNameToRemProcMaster.keySet()) {
            try {
                clustNameToRemProcMaster.get(cName).stop();
            } catch (Exception e) {
                //IF it's SocketException means that Broker was successfully shutdown so ignore
                if (e.getCause() != null && !(e.getCause() instanceof SocketException)) {
                    logger.log(Level.WARN, e, "Exception occurred while Stopping Remote (broker) " +
                            "Process for cluster '%s'. Check if process was successfully terminated.", cName);
                    logger.log(Level.DEBUG,e,"");
                }
            }
        }
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

    public RemoteProcessMaster getRemoteProcess(String clusterName) {
        return clustNameToRemProcMaster.get(clusterName);
    }

    public Collection<RemoteProcessMaster> getRemoteProcesses() {
        return Collections.unmodifiableCollection(clustNameToRemProcMaster.values());
    }

}
