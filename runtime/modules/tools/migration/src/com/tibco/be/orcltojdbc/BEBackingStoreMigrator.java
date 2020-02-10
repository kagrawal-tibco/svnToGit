/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.orcltojdbc;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.be.migration.expimp.MigrationRuleServiceProvider;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Migration utility : Migrates from Oracle Type-based backing store to a JDBC
 * compliant database. It reads entities from OracleTypes database and
 * simultaneously writes into a relational schema (through jdbc).
 *
 * Requires both database resources to be defined in Designer, and configured in
 * the be-migration.tra file.
 */
public class BEBackingStoreMigrator {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    private String repoUrl;
    private BEProperties beProperties;
    private Properties env = new Properties();

    private RuleServiceProvider rsp;
    private com.tibco.cep.kernel.service.logging.Logger logger;

    private BEStore destStore;
    private BEStore orclStore;
    private ClassRegistry classRegistry;
    private int maxBatchSize = 1;
    private int maxWorkerSize = 1;
    private long entityTotal = 0L;

    private TreeSet workerProcs = new TreeSet();
    private TreeMap workerStats = new TreeMap();

    public static void main(String[] args) {
        try {
            BEBackingStoreMigrator migrator = new BEBackingStoreMigrator();
            migrator.init(args);
            migrator.migrate();
            migrator.shutdown();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public BEBackingStoreMigrator() {

    }

    /**
     * This method is used only during local testing.
     */
    private void init(String[] args) throws Exception {
        parseArguments(args);
        String propertyFile = System.getProperty("wrapper.tra.file", env.getProperty("be-migration", "be-migration.tra"));
        beProperties = BEProperties.loadDefault();
        File f = new File(propertyFile);
        if (f.exists()) {
            beProperties.load(new FileInputStream(f));
            Properties p = beProperties.getBranch("tibco.env");
            System.getProperties().putAll(p);
        }

        repoUrl = beProperties.getProperty("tibco.repourl", env.getProperty("tibco.repourl"));
        rsp = new MigrationRuleServiceProvider("cep-migration", repoUrl, beProperties);

        // Initialize JDBC Migration
        this.init(rsp, beProperties);
    }

    public void init(RuleServiceProvider rsp, BEProperties beProperties) throws Exception {
        this.rsp = rsp;
        this.beProperties = beProperties;
        this.logger = rsp.getLogger(BEBackingStoreMigrator.class);
        String target = beProperties.getProperty("be.migration.target.type", "jdbc").trim();
        if (target.equalsIgnoreCase("oracle")) {
            this.logger.log(Level.INFO, "Oracle to Oracle Backing store migration initializing.");
            orclStore = new BEOracleStore(rsp);
            destStore = new BEOracleStore(rsp);
        } else {
            this.logger.log(Level.INFO, "Oracle to JDBC Backing store migration initializing.");
            orclStore = new BEOracleStore(rsp);
            destStore = new BEJdbcStore(rsp);
        }
        classRegistry = new ClassRegistry(rsp, orclStore);
        orclStore.setClassRegistry(classRegistry);
        destStore.setClassRegistry(classRegistry);

        maxBatchSize = Integer.parseInt(beProperties.getProperty("be.migration.batchsize", "200").trim());
        this.logger.log(Level.INFO, "Migration batch size is set to %s", this.maxBatchSize);
        maxWorkerSize = Integer.parseInt(beProperties.getProperty("be.migration.workersize", "10").trim());
        this.logger.log(Level.INFO, "Migration worker size is set to %s", this.maxWorkerSize);
    }

    public synchronized void updateMigrationStats(String worker, long type, long count, long time) {
        entityTotal = entityTotal + count;
        long[] stats = (long[])workerStats.get(worker);
        if (stats == null) {
            stats = new long[] { type, count, time };
        } else {
            stats[0] += type;
            stats[1] += count;
            stats[2] += time;
        }
        workerStats.put(worker, stats);
    }

    public void migrate() {
        try {
            this.logger.log(Level.DEBUG, "Pool (src): %s", this.orclStore.getConnectionPoolState());
            this.logger.log(Level.DEBUG, "Pool (dst): %s", this.destStore.getConnectionPoolState());
            // Create pooled executor
            BlockingQueue<Runnable> workList = new LinkedBlockingQueue();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(maxWorkerSize, maxWorkerSize, 5, TimeUnit.SECONDS, workList);
            // Build work queue, sort by high-to-low count
            Iterator itr1 = destStore.getMigrationProgress().values().iterator();
            while (itr1.hasNext()) {
                MigrationProgress mp = (MigrationProgress) itr1.next();
                mp.totalEntityCnt = orclStore.countObjects(mp.typeId, mp.maxEntityId);
                workerProcs.add(mp);            
            }
            workerProcs.add(MigrationProgress.WORKITEMS);
            // Queue all the work, as sorted by count
            Iterator itr2 = workerProcs.iterator();
            while (itr2.hasNext()) {
                MigrationProgress mp = (MigrationProgress) itr2.next();
                executor.execute(new MigrationWorker(mp));
            }
            // Wait for all queued work to start
            long migrationStart = System.currentTimeMillis();
            while (executor.getQueue().size() > 0) {
                this.logger.log(Level.DEBUG, "Waiting for completion of remaining %s tasks...", executor.getQueue().size());
                this.logger.log(Level.DEBUG, "Pool (src): %s", this.orclStore.getConnectionPoolState());
                this.logger.log(Level.DEBUG, "Pool (dst): %s", this.destStore.getConnectionPoolState());
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
            }
            // Wait for all work to complete
            executor.shutdown();
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

            // Print individual thread workloads
            for (Object workerName : workerStats.keySet()) {
                long[] stats = (long[])workerStats.get(workerName);
                this.logger.log(Level.INFO, "Thread %s:  types=%s, count=%s,  time=%s",
                        workerName, stats[0], stats[1], stats[2]);
            }
            // Print overall stats
            long migrationDuration = (System.currentTimeMillis() - migrationStart) / 1000L;
            long migrationProcessingRate = ((migrationDuration == 0) ? 0 : (entityTotal / migrationDuration));
            if (entityTotal > 0) {
                this.logger.log(Level.INFO, "Migration is completed with %s entities in %s secs (%s eps).",
                        this.entityTotal, migrationDuration, migrationProcessingRate);
            } else {
                this.logger.log(Level.INFO,
                        "Migration is completed without any entity processing. Please check for warnings.");
            }
            this.logger.log(Level.DEBUG, "Pool (src): " + orclStore.getConnectionPoolState());
            this.logger.log(Level.DEBUG, "Pool (dst): " + destStore.getConnectionPoolState());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.log(Level.ERROR, e.getMessage());
        }
    }

    private int insertConcepts(int typeId, Iterator objs) throws Exception {
        int currBatchSize = 0;
        int currentTotalSize = 0;
        long currentGroupStart = 0L;

        Map typeIdToInstances = new HashMap();
        Map instanceMap = new TreeMap();
        typeIdToInstances.put(typeId, instanceMap);
        currentGroupStart = System.currentTimeMillis();
        while (objs.hasNext()) {
            Entity entity = (Entity) objs.next();
            instanceMap.put(entity.getId(), entity);
            currBatchSize++;
            currentTotalSize++;
            if (currBatchSize == maxBatchSize) {
                destStore.insertConcepts(typeIdToInstances);
                instanceMap.clear();
                currBatchSize = 0;
            }
            if ((currentTotalSize % 1000) == 0) {
                long processTime = (System.currentTimeMillis() - currentGroupStart) / 1000L;
                this.logger.log(Level.DEBUG, " Processed %s concepts in %s secs.", currentTotalSize, processTime);
                currentGroupStart = System.currentTimeMillis();
            }
        }
        if (instanceMap.size() > 0) {
            destStore.insertConcepts(typeIdToInstances);
        }
        return currentTotalSize;
    }

    private int insertEvents(int typeId, Iterator objs) throws Exception {
        int currBatchSize = 0;
        int currentTotalSize = 0;
        long currentGroupStart = 0L;

        Map typeIdToInstances = new HashMap();
        Map instanceMap = new TreeMap();
        typeIdToInstances.put(typeId, instanceMap);
        currentGroupStart = System.currentTimeMillis();
        while (objs.hasNext()) {
            Entity entity = (Entity) objs.next();
            instanceMap.put(entity.getId(), entity);
            currBatchSize++;
            currentTotalSize++;
            if (currBatchSize == maxBatchSize) {
                destStore.insertEvents(typeIdToInstances);
                instanceMap.clear();
                currBatchSize = 0;
            }
            if ((currentTotalSize % 1000) == 0) {
                long processTime = (System.currentTimeMillis() - currentGroupStart) / 1000L;
                this.logger.log(Level.DEBUG, " Processed %s events in %s secs.", currentTotalSize, processTime);
                currentGroupStart = System.currentTimeMillis();
            }
        }
        if (instanceMap.size() > 0) {
            destStore.insertEvents(typeIdToInstances);
        }
        return currentTotalSize;
    }

    //TODO: Implement batch inserts.
    private int insertWorkItems(int typeId, Iterator objs) throws Exception {
        int currentTotalSize = 0;
        while (objs.hasNext()) {
            currentTotalSize++;
            WorkTuple item = (WorkTuple) objs.next();
            destStore.saveWorkItem(item);
        }
        return currentTotalSize;
    }
    
    public void shutdown() {
        rsp.shutdown();
    }

    private void parseArguments(String argv[]) {
        if (argv.length == 0 || argv.length < 3) {
            // printUsage();
            System.exit(0);
        }
        for (int i = 0; i < argv.length; i++) {
            String key = argv[i];
            // -h or /h or -help or /help
            if (key.matches("-h|/h|-help|/help|--help|/--help")) {
                // printUsage();
                System.exit(0);
            } else if (key.matches("-p|/p|-property|/property|--propFile|/--propFile")) {
                ++i;
                env.put("be-migration", argv[i]);
            } else {
                env.put("tibco.repourl", argv[i]);
            }
        }
    }

    class MigrationWorker implements Runnable {
        private String workerName;
        private MigrationProgress mpw;

        MigrationWorker(MigrationProgress mp) {
            this.mpw = mp;
        }

        public void run() {
            try {
                long entityCnt = 0L;
                long entityStart = 0L;
                long entityDuration = 0L;
                Class entityClz = null;
                Iterator objs = null;
                workerName = "main-" + Thread.currentThread().getId();
                Thread.currentThread().setName(workerName);
                updateMigrationStats(workerName, 0, 0, 0);
                // Process all entries of this type
                if (mpw.typeId < 0) {
                    logger.log(Level.DEBUG, "Migrating %s ...", mpw.className);
                    entityStart = System.currentTimeMillis();
                    if (mpw.className.equals("WorkItems")) {
                        objs = orclStore.getWorkItems();
                        entityCnt = insertWorkItems(mpw.typeId, objs);
                    }
                    entityDuration = (System.currentTimeMillis() - entityStart) / 1000L;
                    logger.log(Level.INFO, "Completed %s: total %d entities migrated in %d secs (%d eps).",
                            mpw.className, entityCnt, entityDuration,
                            ((entityDuration == 0) ? 0 : (entityCnt / entityDuration)));                 
                } else {
                    entityClz = classRegistry.getClass(mpw.typeId);
                    objs = orclStore.getObjects(mpw.typeId, mpw.maxEntityId);
                    if (objs == null) {
                        // Skip those without any backing-store
                        logger.log(Level.DEBUG, "Skipping %s with no entities ...", entityClz.getName());
                        return;
                    }
                    logger.log(Level.INFO, "Migrating %s with %s entities ...",
                            entityClz.getName(), mpw.totalEntityCnt);
                    entityStart = System.currentTimeMillis();
                    if (Concept.class.isAssignableFrom(entityClz)) {
                        entityCnt = insertConcepts(mpw.typeId, objs);
                    } else if (SimpleEvent.class.isAssignableFrom(entityClz)) {
                        entityCnt = insertEvents(mpw.typeId, objs);
                    } else if (TimeEvent.class.isAssignableFrom(entityClz)) {
                        entityCnt = insertEvents(mpw.typeId, objs);
                    } else {
                        throw new Exception("Unknown type: " + entityClz.getName());
                    }
                    // Mark the type as done, so the next time, it is skipped
                    destStore.markTypeAsDone(mpw.typeId);
                    entityDuration = (System.currentTimeMillis() - entityStart) / 1000L;
                    logger.log(Level.INFO, "Completed %s : total %d entities migrated in %d secs (%s eps).",
                            entityClz.getName(), entityCnt, entityDuration,
                            ((entityDuration == 0) ? 0 : (entityCnt / entityDuration)));
                }
                updateMigrationStats(workerName, 1, entityCnt, entityDuration);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
