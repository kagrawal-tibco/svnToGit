package com.tibco.rta.service.recovery.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipListener;
import com.tibco.rta.service.metric.MetricService;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.recovery.RecoveryService;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecoveryServiceImpl extends AbstractStartStopServiceImpl implements RecoveryService, GroupMembershipListener, GMPActivationListener {
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
            LoggerCategory.RTA_SERVICES_RECOVERY.getCategory());
    private PersistenceService pService;
    private MetricService mService;

    @Override
    public void init(Properties configuration) throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Recovery service..");
        }
        super.init(configuration);

        pService = ServiceProviderManager.getInstance().getPersistenceService();
        mService = ServiceProviderManager.getInstance().getMetricsService();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Recovery Complete..");
        }

        ServiceProviderManager.getInstance().getGroupMembershipService().addMembershipListener(this);
        ServiceProviderManager.getInstance().getGroupMembershipService().addActivationListener(this);

    }

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.INFO, "Starting Recovery Service ...");
        }

        boolean storeProcessedFacts = false;
        try {
            storeProcessedFacts = Boolean.parseBoolean((String) ConfigProperty.RTA_STORE_PROCESSED_FACTS.getValue(configuration));
        } catch (Exception e) {
        }
        //only perform recovery if the processed facts are stored.
        
        super.start();
        
        if (storeProcessedFacts) {
            fetchAndResubmitUnprocessedFact();
        } else {
            LOGGER.log(Level.INFO, "Engine configured to run without storing processed facts. Nothing to recover.");
        }

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Recovery Service Complete.");
        }
    }

    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
    	super.stop();
    }

    @Override
    public void suspend() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

//    @Override
//    public boolean isStarted() {
//        // TODO Auto-generated method stub
//        return false;
//    }

    private void fetchAndResubmitUnprocessedFact() {
        List<RtaSchema> schemas = ModelRegistry.INSTANCE.getAllRegistryEntries();
        boolean isMultipleTablePerDh = System.getProperty("insert_processed_multiple", "true").equals("true");
        if (!isMultipleTablePerDh) {
            boolean isUnprocessed = false;
            for (RtaSchema schema : schemas) {
                try {
                    Browser<Fact> browser = pService.getUnProcessedFact(schema.getName());
                    while (browser.hasNext()) {
                        isUnprocessed = true;
                        Fact fact = browser.next();
                        ((FactImpl) fact).setNew(false);
                        mService.assertFact(fact);
                    }
                    if (isUnprocessed) {
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "Found unprocessed Fact ...");
                        }
                    } else {
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "No Unprocessed Fact Found!");
                        }
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Can't get Unprocessed Fact for SchemaName: %s", e, schema.getName());
                }
            }
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Initializing Recovery ThreadPool ...");
            }
            ExecutorService executerService = Executors.newCachedThreadPool();
            for (RtaSchema schema : schemas) {
                for (Cube cube : schema.getCubes()) {
                    for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                    	if (!dh.isEnabled()) {
                    		continue;
                    	}
                        executerService.execute(new FetchUnprocessedFactJob(schema.getName(), cube.getName(), dh
                                .getName()));
                    }
                }
            }
        }
    }

    @Override
    public void resubmitFact(Fact facts) {
        // TODO Auto-generated method stub

    }

    private class FetchUnprocessedFactJob implements Runnable {

        private String schemaName;
        private String cubeName;
        private String dhName;

        FetchUnprocessedFactJob(String schemaName, String cubeName, String dhName) {
            this.schemaName = schemaName;
            this.cubeName = cubeName;
            this.dhName = dhName;
        }

        @Override
        public void run() {
            try {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Processing Recovery for schema = %s Cube = %s DhName = %s", schemaName, cubeName, dhName);
                }
                Browser<Fact> browser = pService.getUnProcessedFact(schemaName, cubeName, dhName);
                while (browser.hasNext()) {
                    Fact fact = browser.next();
                    ((FactImpl) fact).setNew(false);
                    mService.assertFact(fact);
                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Recovery completed for schema = %s Cube = %s DhName = %s", schemaName, cubeName, dhName);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR,
                        "Can't get Unprocessed Fact for schemaName: %s cubeName: %s dhName: %s", e,
                        schemaName, cubeName, dhName);
            }
        }
    }

    @Override
    public <G extends GroupMember> void memberJoined(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void memberLeft(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onPrimary(G member) {
        try {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Data Recovery Service.");
            }
			if (!isStarted()) {
				start();
			}
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while starting Data Recovery Service", e);
        }
    }

    @Override
    public <G extends GroupMember> void onSecondary(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void networkFailed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void networkEstablished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onActivate() {
        try {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Data Recovery Service.");
            }
			if (!isStarted()) {
				start();
			}
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while starting Data Recovery Service", e);
        }
    }

    @Override
    public void quorumComplete(GroupMember... groupMembers) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onFenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onUnfenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onConflict(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public void onDeactivate() {
		// TODO Auto-generated method stub
		try {
			stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
