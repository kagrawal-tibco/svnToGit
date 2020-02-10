package com.tibco.rta.service.purge.impl;

import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.RetentionPolicy.Qualifier;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipListener;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.purge.PurgeService;

public class PurgeServiceImpl extends AbstractStartStopServiceImpl implements PurgeService, GroupMembershipListener, GMPActivationListener, PurgeServiceImplMBean {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
            LoggerCategory.RTA_SERVICES_PURGE.getCategory());

    PersistenceService pServ;
    private Map<String, Timer> allTimers = new LinkedHashMap<String, Timer>();
    private Map<String, Long> timerFrequencies = new LinkedHashMap<String, Long>();
    private Map<String, PurgeTask> allTimerTask = new LinkedHashMap<String, PurgeTask>();

    private boolean storeFacts;
    private boolean storeProcessedFacts;


    private static String[] itemNames = { "PurgeTimerName", "Frequency", "LastTimeDeleted",
            "DeletedInLastJob" , "TotalDeleted", "DeleteJobCount" , "AvgPurgeJobTimeInMilis" };
    private static String[] itemDescriptions = { "Purge Timer Name", "Purge Timer Frequency",
            "Last Time Purge job Fired", "Deleted in last Purge Job", "Total Deleted  by now",
            "Number of Purge jobs fired till now", "Average Time For Purge Job" };
    private static OpenType[] itemTypes = { SimpleType.STRING, SimpleType.STRING, SimpleType.DATE, SimpleType.LONG,
            SimpleType.LONG, SimpleType.LONG, SimpleType.DOUBLE };
    private static String[] indexNames = { "PurgeTimerName" };

    private static CompositeType compositeType = null;
    private static TabularType tabularType = null;
    static {
        try {
            compositeType = new CompositeType("Timer", "Purge Timer info", itemNames, itemDescriptions, itemTypes);
            tabularType = new TabularType("Timers", "List of Purge Timers", compositeType, indexNames);
        } catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(Properties configuration) throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Data Purge service..");
        }
        super.init(configuration);
        pServ = ServiceProviderManager.getInstance().getPersistenceService();
        try {
            storeProcessedFacts = Boolean.parseBoolean((String) ConfigProperty.RTA_STORE_PROCESSED_FACTS
                    .getValue(configuration));
            storeFacts = Boolean.parseBoolean((String) ConfigProperty.RTA_STORE_FACTS.getValue(configuration));
        } catch (Exception e) {
        }
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Initializing Data Purge service Complete.");
        }

        registerMBean(configuration);

        ServiceProviderManager.getInstance().getGroupMembershipService().addMembershipListener(this);
        ServiceProviderManager.getInstance().getGroupMembershipService().addActivationListener(this);
    }

    private void registerMBean(Properties configuration) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
        ObjectName name = new ObjectName(mbeanPrefix + ".purge:type=PurgeService");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(this, name);
        }
    }

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Data Purge service..");
        }
        super.start();
        purge();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Data Purge service Complete.");
        }
    }

    @Override
    public void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Data Purge service..");
        }
        for (Map.Entry<String, Timer> entry : allTimers.entrySet()) {
            String hrNm = entry.getKey();
            Timer timer = entry.getValue();
            try {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Cancelling timer for hierarchy [%s] ..", hrNm);
                }
                timer.cancel();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Cancelling timer for hierarchy [%s] complete.", hrNm);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while cancelling purge timer for hierarchy [%s]", e, hrNm);
            }
        }
        allTimers.clear();
        allTimerTask.clear();
        super.stop();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Data Purge service Complete.");
        }

    }

    @Override
    public void purge() throws Exception {

        List<RtaSchema> allSchemas = ModelRegistry.INSTANCE.getAllRegistryEntries();
        for (RtaSchema schema : allSchemas) {
            for (Cube cube : schema.getCubes()) {
                for (DimensionHierarchy hierarchy : cube.getDimensionHierarchies()) {
                    if (!hierarchy.isEnabled()) {
                        continue;
                    }
                    if (hierarchy.getRetentionPolicy() != null) {
                        String schemaName = hierarchy.getOwnerSchema().getName();
                        String cubeName = hierarchy.getOwnerCube().getName();
                        String hierarchyName = hierarchy.getName();

                        String hrNm = schemaName + "/" + cubeName + "/" + hierarchyName;
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Setting a purge timer for hiearchy [%s] ..", hrNm);
                        }
                        startPurgeTimerForDimension(hierarchy, hrNm);

                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Setting a purge timer for hiearchy [%s] complete.", hrNm);
                        }
                    }
                }
            }

            for (RetentionPolicy rp : schema.getRetentionPolicies()) {
                if (rp.getQualifier().equals(Qualifier.FACT)) {
                    if (storeFacts || storeProcessedFacts) {
                        int rawPurgeTime = Integer.parseInt(rp.getPurgeTimeOfDay());
                        long purgeFrequencyPeriod = rp.getPurgeFrequencyPeriod();

                        if (purgeFrequencyPeriod < 1) {
                            if (LOGGER.isEnabledFor(Level.WARN)) {
                                LOGGER.log(Level.WARN,
                                        "purge-frequency-period not valid for facts, using 24 hours as default...");
                            }
                            purgeFrequencyPeriod = 24 * 60 * 60 * 1000;
                        }

                        PurgeTask purgeTask = new PurgeTask(schema.getName(), rp.getQualifier(),
                                rp.getRetentionPeriod());
                        Calendar cal = Calendar.getInstance();

                        String purgeTimerName = "PurgeTimerFacts/"+schema.getName();

                        Timer timer = new Timer(purgeTimerName, true);

                        if (rawPurgeTime != -1) {
                            String dailyPurgeScheduledTime = String.format("%04d", rawPurgeTime);

                            int hr = Integer.parseInt(dailyPurgeScheduledTime.substring(0, 2));
                            int mi = Integer.parseInt(dailyPurgeScheduledTime.substring(2, 4));

                            Calendar currentCal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, hr);
                            cal.set(Calendar.MINUTE, mi);
                            cal.set(Calendar.SECOND, 0);

                            if (cal.getTime().before(currentCal.getTime())) {
                                currentCal.add(Calendar.DAY_OF_MONTH, 1);
                                cal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
                            }

                        } else {
                            cal.add(Calendar.MILLISECOND, (int) purgeFrequencyPeriod);
                        }
                        allTimers.put(purgeTimerName, timer);
                        allTimerTask.put(purgeTimerName, purgeTask);
                        timerFrequencies.put(purgeTimerName, purgeFrequencyPeriod);

                        timer.schedule(purgeTask, cal.getTime(), purgeFrequencyPeriod);
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "Starting fact purge timer for [%s] ", schema.getName());
                        }
                    }
                }
            }
        }
    }

    private void startPurgeTimerForDimension(DimensionHierarchy hierarchy, String hrNm) throws Exception {
//		TreeMap<Long, TimeDimension> sortedTd = new TreeMap<Long, TimeDimension>();
//		List<String> tdNames = new ArrayList<String>();

//		for (Dimension d : hierarchy.getDimensions()) {
//			if (d instanceof TimeDimension) {
//				TimeDimension td = (TimeDimension) d;
//				TimeUnits tu = td.getTimeUnit();
//				long timestamp = TimeUnitsImpl.getTimePeriod(tu.getTimeUnit(), td.getMultiplier());
//				sortedTd.put(timestamp, td);
//			}
//		}

//		Iterator iterator = sortedTd.descendingKeySet().iterator();
//		for (int i = 0; i < sortedTd.descendingKeySet().size(); i++) {
//			Long largest = (Long) iterator.next();
//			TimeDimension td = sortedTd.get(largest);
//			tdNames.add(td.getName());
//		}

//		if (tdNames.size() > 0) {
        RetentionPolicy rPolicy = hierarchy.getRetentionPolicy();
        int retentionCount = (int) rPolicy.getRetentionUnitCount();

        String purgeTime = rPolicy.getPurgeTimeOfDay();
        if (purgeTime == null || purgeTime.isEmpty()) {
            return;
        }

        if (retentionCount == 0) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Retention count is 0 for hierarchy [%s]. Will skip", hrNm);
            }
            return;
        }

        long purgeFrequencyPeriod = rPolicy.getPurgeFrequencyPeriod();

        if (purgeFrequencyPeriod < 1) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "purge-frequency-period not valid for [%s], using 24 hours as default...", hrNm);
            }
            purgeFrequencyPeriod = 24 * 60 * 60 * 1000;
        }

        long purgeOlderThan = hierarchy.getRetentionPolicy().getRetentionPeriod();
        setTimer(hierarchy, Integer.parseInt(purgeTime), purgeOlderThan, purgeFrequencyPeriod);
//		}
    }

    private void setTimer(DimensionHierarchy hierarchy, int rawPurgeTime,
                          long purgeOlderThan, long purgeFrequencyPeriod) throws Exception {
        String schemaName = hierarchy.getOwnerSchema().getName();
        String cubeName = hierarchy.getOwnerCube().getName();
        String hierarchyName = hierarchy.getName();

        String hrNm = schemaName + "/" + cubeName + "/" + hierarchyName;

        Calendar cal = Calendar.getInstance();
        if (rawPurgeTime != -1) {

            String dailyPurgeScheduledTime = String.format("%04d", rawPurgeTime);
            int hr = Integer.parseInt(dailyPurgeScheduledTime.substring(0, 2));
            int mi = Integer.parseInt(dailyPurgeScheduledTime.substring(2, 4));

            Calendar currentCal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hr);
            cal.set(Calendar.MINUTE, mi);
            cal.set(Calendar.SECOND, 0);

            if (cal.getTime().before(currentCal.getTime())) {
                currentCal.add(Calendar.DAY_OF_MONTH,1);
                cal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
            }
        } else {
            cal.add(Calendar.MILLISECOND, (int) purgeFrequencyPeriod);
        }
        Timer timer = new Timer("PurgeTaskTimer-" + hrNm);
        allTimers.put(hrNm, timer);
        timerFrequencies.put(hrNm, purgeFrequencyPeriod);

        PurgeTask purgeTask = new PurgeTask(schemaName, cubeName, hierarchyName, purgeOlderThan);

        timer.schedule(purgeTask, cal.getTime(), purgeFrequencyPeriod);
        allTimerTask.put(hrNm, purgeTask);

    }

    class PurgeTask extends TimerTask {
        String schemaName;
        String cubeName;
        String hierarchyName;
        String hierarchyFqn;
        long purgeOlderThan;
        Qualifier qualifier;

        AtomicLong lastTimePurged = new AtomicLong(0);
        AtomicLong lastTimePurgedCount = new AtomicLong(0);
        AtomicLong totalPurgedCount = new AtomicLong(0);
        AtomicLong totalPurgeTime = new AtomicLong(0);
        AtomicLong purgeJobsCount = new AtomicLong(0);


        PurgeTask(String schemaName, String cubeName, String hierarchyName, long purgeOlderThan) {
            this.schemaName = schemaName;
            this.cubeName = cubeName;
            this.hierarchyName = hierarchyName;
            this.purgeOlderThan = purgeOlderThan;
            this.hierarchyFqn = schemaName + "/" + cubeName + "/" + hierarchyName;
        }

        public PurgeTask(String schemaName, Qualifier qualifier, long retentionPeriod) {
            this.schemaName = schemaName;
            this.qualifier = qualifier;
            this.purgeOlderThan = retentionPeriod;
        }

        @Override
        public void run() {
            if (qualifier == null) {
                try {
                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Purge job for [%s] fired", hierarchyFqn);
                    }
                    long t1 = System.currentTimeMillis();
                    long purgedCount= pServ.purgeMetricsForHierarchy(schemaName, cubeName, hierarchyName,
                            purgeOlderThan);
                    lastTimePurgedCount.set(purgedCount);
                    totalPurgedCount.addAndGet(lastTimePurgedCount.get());
                    totalPurgeTime.addAndGet(System.currentTimeMillis() - t1);
                    purgeJobsCount.incrementAndGet();
                    lastTimePurged.set(System.currentTimeMillis());

                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Purge job for [%s] completed", hierarchyFqn);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while executing purge task for hierarchy [%s]", e, hierarchyFqn);
                }
            } else {
                try {
                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Purge Facts Called");
                    }
                    long t1= System.currentTimeMillis();
                    long purgedCount = pServ.purgeMetricsForQualifier(schemaName, qualifier, purgeOlderThan, storeFacts,
                            storeProcessedFacts);
                    lastTimePurgedCount.set(purgedCount);
                    totalPurgedCount.addAndGet(lastTimePurgedCount.get());
                    totalPurgeTime.addAndGet(System.currentTimeMillis() - t1);
                    purgeJobsCount.incrementAndGet();
                    lastTimePurged.set(System.currentTimeMillis());

                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Purge Facts Completed");
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while executing purge task for qualifier [%s]", e,
                            qualifier.toString());
                }
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
                LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Data Purge Service.");
            }
            if (!isStarted()) {
                start();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while starting Data Purge Service", e);
        }
    }

    @Override
    public <G extends GroupMember> void onSecondary(G member) {
//        for ()
    }

    @Override
    public void quorumComplete(GroupMember... groupMembers) {
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
    public void onActivate() {
        try {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Data Purge Service.");
            }
            if (!isStarted()) {
                start();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while starting Data Purge Service", e);
        }
    }


    @Override
    public TabularDataSupport getPurgeJobDetails() {
        // TODO Auto-generated method stub
        TabularDataSupport tableData = new TabularDataSupport(tabularType);
        for (String key : allTimerTask.keySet()) {
            Map<String, Object> items = new HashMap<String, Object>();

            long purgeFrequency = timerFrequencies.get(key);
            String frequency = String.format(
                    "%d days, %02d hours, %02d minutes, %02d secs",
                    TimeUnit.MILLISECONDS.toDays(purgeFrequency),
                    TimeUnit.MILLISECONDS.toHours(purgeFrequency)
                            - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(purgeFrequency)),
                    TimeUnit.MILLISECONDS.toMinutes(purgeFrequency)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(purgeFrequency)),
                    TimeUnit.MILLISECONDS.toSeconds(purgeFrequency)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(purgeFrequency)));

            PurgeTask purgeTask = allTimerTask.get(key);

            items.put(itemNames[0], key);
            items.put(itemNames[1], frequency);
            if (purgeTask.lastTimePurged.get() != 0) {
                items.put(itemNames[2], new Date(purgeTask.lastTimePurged.get()));
            } else {
                items.put(itemNames[2], null);
            }
            items.put(itemNames[3], purgeTask.lastTimePurgedCount.get());
            items.put(itemNames[4], purgeTask.totalPurgedCount.get());
            items.put(itemNames[5], purgeTask.purgeJobsCount.get());
            if (purgeTask.purgeJobsCount.get() != 0) {
                items.put(itemNames[6], (double)(purgeTask.totalPurgeTime.get()/purgeTask.purgeJobsCount.get()));
            } else {
                items.put(itemNames[6], 0D);
            }

            CompositeDataSupport value = null;
            try {
                value = new CompositeDataSupport(compositeType, items);
            } catch (OpenDataException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            tableData.put(value);
        }
        return tableData;
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
