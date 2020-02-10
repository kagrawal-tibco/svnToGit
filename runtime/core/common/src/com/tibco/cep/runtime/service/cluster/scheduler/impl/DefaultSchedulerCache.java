/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.cluster.scheduler.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.LifecycleListener;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkManagerEntry;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
//import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultCachePoller.ScheduledTimeComparator;
//import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultCachePoller.WorkEntryFilter;
import com.tibco.cep.runtime.service.cluster.system.mm.ScheduleCacheMBeanImpl;
import com.tibco.cep.runtime.service.cluster.system.mm.SchedulerCacheMBean;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

public class DefaultSchedulerCache implements ControlDao.ChangeListener<String, WorkManagerEntry>, SchedulerCache, GroupMemberServiceListener {
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    Cluster cluster;

    private ControlDao<String, WorkManagerEntry> workManagerDao;
    private Map<String, ControlDao<String, WorkTuple>> schedulerDaos = new ConcurrentHashMap<String, ControlDao<String, WorkTuple>>();
    private Map<String, DefaultTaskScheduler> taskSchedulers = new HashMap<String, DefaultTaskScheduler>();
    private final ReentrantLock schedulelock = new ReentrantLock();
    private final ReentrantLock schedulelock2 = new ReentrantLock();

    public int QUEUE_THRESHOLD;
    private boolean USE_SCHEDULER_PROPERTY_ON_STARTUP;

    Map<String, WorkManagerEntry> allSchedulers = new HashMap<String, WorkManagerEntry>();

    private boolean started = false;
    private Logger logger;
    private SchedulerCacheMBean mbean;

    WorkManager schedulerManager;

    public DefaultSchedulerCache() {

    }
    
    public void init(Cluster cluster) throws Exception {
        this.cluster = cluster;
        this.logger = cluster.getRuleServiceProvider().getLogger(DefaultSchedulerCache.class);
        QUEUE_THRESHOLD = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getInt("be.engine.cluster.scheduler.maxLimit", Integer.MAX_VALUE);
        USE_SCHEDULER_PROPERTY_ON_STARTUP = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean("be.engine.cluster.scheduler.useflagonstartup", true);

        logger.log(Level.DEBUG, "Use be.engine.cluster.scheduler property during startup : " + USE_SCHEDULER_PROPERTY_ON_STARTUP);

        workManagerDao = cluster.getDaoProvider().createControlDao(String.class, WorkManagerEntry.class, ControlDaoType.WorkManager);
        mbean = new ScheduleCacheMBeanImpl(this);
        schedulerManager = WorkManagerFactory.create("DefaultScheduler", null, null, getClass().getSimpleName(), 1, cluster.getRuleServiceProvider());
    }

    public ControlDao<String, WorkManagerEntry> getWorkManagerDao() {
        return workManagerDao;
    }

    // MultiAgentCluster calls this during startup
    public void start() {
        workManagerDao.start();
        workManagerDao.registerListener(this);
        loadSchedulers();
        schedulerManager.start();
        
        try {
            schedulelock.lock();
            if ((isNodeConfiguredAsScheduler() && USE_SCHEDULER_PROPERTY_ON_STARTUP) || !USE_SCHEDULER_PROPERTY_ON_STARTUP) {
                started = true;
                browseAndAcquire();
            }
        }
        finally {
            schedulelock.unlock();
        }
        
        // Register to the GMP callbacks so that WorkManager allocations can transfered
        // in case of node failures
        cluster.getGroupMembershipService().addGroupMemberServiceListener(this);
    }

    private void browseAndAcquire() {
        schedulelock.lock();
        try {
            Iterator<String> allSchedulers = workManagerDao.keySet().iterator();
            while (allSchedulers.hasNext()) {
                String schedulerId = allSchedulers.next();
                acquireWorkManager(schedulerId);
            }
        }
        finally {
            schedulelock.unlock();
        }
    }

    private Object canSchedule(byte mode) {
        if ((mode == MODE_ALL) || ((mode & MODE_CACHEAGENT) != 0)) {
            return cluster;
        }

        switch (mode) {
            case MODE_INFERENCEAGENT:
                return getAgentToSchedule(CacheAgent.Type.INFERENCE);
            case MODE_QUERYAGENT:
                return getAgentToSchedule(CacheAgent.Type.QUERY);
            default:
                return null;
        }
    }

    private CacheAgent getAgentToSchedule(CacheAgent.Type type) {
        CacheAgent[] allAgents = cluster.getAgentManager().getLocalAgents();
        for (int i = 0; i < allAgents.length; i++) {
            CacheAgent agent = allAgents[i];
            if (type == CacheAgent.Type.INFERENCE && agent.getAgentType() == CacheAgent.Type.INFERENCE) {
                return agent;
            } else if (type == CacheAgent.Type.QUERY && agent.getAgentType() == CacheAgent.Type.QUERY) {
                return agent;
            } else if (type == CacheAgent.Type.CACHESERVER && agent.getAgentType() == CacheAgent.Type.CACHESERVER) {
                return agent;
            }
        }
        return null;
    }

    public boolean isNodeConfiguredAsScheduler() {
        return ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean("be.engine.cluster.scheduler", true);
    }

    public synchronized void shutdown() {
        Iterator<Map.Entry<String, DefaultTaskScheduler>> allSchedulers = taskSchedulers.entrySet().iterator();
        while (allSchedulers.hasNext()) {
            Map.Entry<String, DefaultTaskScheduler> entry = allSchedulers.next();
            DefaultTaskScheduler scheduler = entry.getValue();
            scheduler.shutdown();
        }
        if (workManagerDao != null) {
            workManagerDao.unregisterListener(this);
            workManagerDao.discard();
        }
        shutdown.set(true);
    }

    protected boolean acquireWorkManager(String id) {
        try {
            schedulelock.lock();
            this.logger.log(Level.DEBUG, "Attempting to Lock Scheduler %s ", workManagerDao.getName());
            workManagerDao.lock(id, -1);
            this.logger.log(Level.DEBUG, "Locked Scheduler %s ", workManagerDao.getName());
            WorkManagerEntry entry = (WorkManagerEntry) workManagerDao.get(id);
            if (entry != null) {
                if (entry.getNodeId() == null) {
                    Object schedulerAgent = this.canSchedule(entry.getMode());
                    if (schedulerAgent != null) {
                        entry.setNodeId(cluster.getGroupMembershipService().getLocalMember().getMemberId());
                        workManagerDao.put(id, entry);
                        DefaultTaskScheduler scheduler = new DefaultTaskScheduler(this, entry, schedulerAgent);
                        taskSchedulers.put(entry.getId(), scheduler);
                        try {
                            scheduler.start();
                            this.logger.log(Level.DEBUG, "Started Scheduler %s", entry.getId());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        finally {
            this.logger.log(Level.DEBUG, "Attempting to Unlock Scheduler %s", workManagerDao.getName());
            workManagerDao.unlock(id);
            this.logger.log(Level.DEBUG, "Unlocked Scheduler %s", workManagerDao.getName());
            schedulelock.unlock();
        }
    }

    public Cluster getCluster() {
        return cluster;
    }

    class AcquireWorkManager implements Runnable {
        String id;

        AcquireWorkManager(String id) {
            this.id = id;
        }

        public void run() {
            try {
                acquireWorkManager(id);
            }
            catch (Exception e) {
                if (!shutdown.get()) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param scheduleId
     * @param pollInterval
     * @param refreshAhead
     */
    @Override
    public void createScheduler(String scheduleId, long pollInterval,
                                long refreshAhead,
                                boolean isCacheLimited, byte mode) {
        schedulelock.lock();
        try {
            workManagerDao.lock(scheduleId, -1);
            WorkManagerEntry entry = (WorkManagerEntry) workManagerDao.get(scheduleId);
            if (entry == null) {
                entry = new WorkManagerEntry();
                entry.setId(scheduleId);
                entry.setPollingInterval(pollInterval);
                entry.setRefreshAhead(refreshAhead);
                entry.setCacheLimited(isCacheLimited);
                entry.setMode(mode);
                workManagerDao.put(scheduleId, entry);
                allSchedulers.put(scheduleId, entry);
            }
        } finally {
            workManagerDao.unlock(scheduleId);
            schedulelock.unlock();
        }
    }

    @Override
    public void schedule(String schedulerId, String workKey, WorkEntry entry) throws Exception {
        WorkManagerEntry wm = (WorkManagerEntry) allSchedulers.get(schedulerId);
        if (wm == null) {
            wm = (WorkManagerEntry) workManagerDao.get(schedulerId);
            if (wm == null) {
                throw new Exception("Scheduler " + schedulerId + " not registered with the cluster");
            } else {
                allSchedulers.put(schedulerId, wm);
            }
        }
        ControlDao<String, WorkTuple> workList = getSchedulerWorkList(schedulerId);
        if (workList != null) {
            WorkTuple work = new WorkTuple();
            work.setWorkId(schedulerId + "." + workKey); // TODO: Why is it 'schedulerId + "." + workKey'?
            work.setWorkQueue(schedulerId);
            work.setScheduledTime(entry.getScheduleTime());
            work.setWork(entry);
            if (cluster.getClusterConfig().isHasBackingStore() && cluster.getClusterConfig().isCacheAside()) {
            	BackingStore backingStore = cluster.getCacheAsideStore();
                if (backingStore != null) {
                    backingStore.saveWorkItem(work);
                }
                workList.put(workKey, work);
            } else {
                workList.put(workKey, work);
            }
            LifecycleListener lifecycleListener = getLifecycleListener();
            if (lifecycleListener != null) {
            	lifecycleListener.workScheduled(entry, schedulerId, workKey);
            }
        } else {
            //TODO: Bala : worklist cache would be null for db-based.
            throw new Exception("Scheduler with id=" + schedulerId + " not registered");
        }
    }

    private LifecycleListener getLifecycleListener() {
    	if (cluster.getRuleServiceProvider() instanceof RuleServiceProviderImpl) {
    		return ((RuleServiceProviderImpl) cluster.getRuleServiceProvider()).getLifecycleListener();
    	}
    	return null;
	}

    protected ControlDao<String, WorkTuple> getSchedulerWorkList(String schedulerId) throws Exception {
        ControlDao<String, WorkTuple> dao = schedulerDaos.get(schedulerId);
        if (dao == null) {
            dao = cluster.getDaoProvider().createControlDao(String.class, WorkTuple.class, ControlDaoType.WorkList$SchedulerId, schedulerId);
            schedulerDaos.put(schedulerId, dao);
        }
        return dao;
    }

	/** BE-22006
    public Object[] listEntries(String schedulerId, long refreshAhead, int limit) throws Exception {
    	long count = 0; 
    	List result = new ArrayList();
        if (cluster.getClusterConfig().isHasBackingStore()) {
            GenericBackingStore backingStore = cluster.getBackingStore();
            if (backingStore != null && (backingStore instanceof BackingStore)) {
                List<WorkTuple> workitems = ((BackingStore)backingStore).getWorkItems(schedulerId, System.currentTimeMillis() + refreshAhead, WorkEntry.STATUS_PENDING);
	            for(WorkTuple tuple : workitems) {
	            	if (tuple != null) {
	                	String key = tuple.getWorkId().replaceFirst(schedulerId+".", "");
                		WorkEntry work = tuple.getWork();
                		long time = work.getScheduleTime();
                		result.add(new Object[]{time, key});
	            	}
	            	if ((++count) > limit) {
	            		break;
	            	}
	    		}                
            }
        }
        else {
	        ControlDao<String, WorkTuple> dao = getSchedulerWorkList(schedulerId);
	        if (dao != null) {
	            Set workset = dao.entrySet(new WorkEntryFilter(refreshAhead), limit);
	            ArrayList<Map.Entry<String, WorkTuple>> workitems = new ArrayList(workset);
	            Collections.sort(workitems, ScheduledTimeComparator.INSTANCE);
	            for(Map.Entry<String, WorkTuple> entry : workitems) {
	            	if (entry != null) {
	                	WorkTuple tuple = entry.getValue();
	                    if (tuple != null) {
		                	String key = tuple.getWorkId().replaceFirst(schedulerId+".", "");
	                		WorkEntry work = tuple.getWork();
	                		long time = work.getScheduleTime();
	                		result.add(new Object[]{time, key});
	                	}
	            	}
	            	if ((++count) > limit) {
	            		break;
	            	}
	    		}
	        }
        }
        return result.toArray(new Object[result.size()]);
    }
    */

    @Override
    public WorkEntry getEntry(String schedulerId, String workKey) throws Exception {
        WorkManagerEntry entry = (WorkManagerEntry) allSchedulers.get(schedulerId);
        if (entry != null) {
            if (cluster.getClusterConfig().isHasBackingStore()) {
                String key = entry.getId() + "." + workKey;
                GenericBackingStore backingStore = cluster.getBackingStore();
                if (backingStore != null && (backingStore instanceof BackingStore)) {
                    WorkTuple tuple = ((BackingStore)backingStore).getWorkItem(key);
                    if (tuple != null) {
                    	return tuple.getWork();
                    }
                }
            } 
            else {
            	ControlDao<String, WorkTuple> worklist = getSchedulerWorkList(schedulerId);
	            if (worklist != null) {
	            	WorkTuple tuple = worklist.get(workKey);
	            	if (tuple != null) {
	            		return tuple.getWork();
	                }
	            }
            }
        }
        return null;
    }
	

    /**
     * @param schedulerId
     * @param workKey
     * @throws Exception
     */
    @Override
    public WorkEntry remove(String schedulerId, String workKey) throws Exception {
        WorkManagerEntry entry = (WorkManagerEntry) allSchedulers.get(schedulerId);
        if (entry != null) {
            if (cluster.getClusterConfig().isHasBackingStore()) {
                String key = entry.getId() + "." + workKey; // TODO: Why is it 'schedulerId + "." + workKey'?
                GenericBackingStore backingStore = cluster.getBackingStore();
                if (backingStore != null && (backingStore instanceof BackingStore)) {
                    ((BackingStore)backingStore).removeWorkItem(key);
                }
            }
            ControlDao<String, WorkTuple> worklist = getSchedulerWorkList(schedulerId);
            if (worklist != null) {
            	WorkTuple ret = worklist.remove(workKey);
            	if (ret != null) {
                    LifecycleListener lifecycleListener = getLifecycleListener();
                    if (lifecycleListener != null) {
                    	lifecycleListener.workRemoved(ret.getWork(), schedulerId, workKey);
                    }
            		return ret.getWork();
                }
            }
        }
        return null;
    }

    /**
     * @param schedulerId
     * @param workKeys
     * @throws Exception
     */
    @Override
    public void remove(String schedulerId, Set<String> workKeys) throws Exception {
        ControlDao<String, WorkTuple> worklist = getSchedulerWorkList(schedulerId);
        if (worklist != null) {
            worklist.removeAll(workKeys);
        }
    }

    /**
     * @param id
     * @param pollInterval
     * @param refreshAhead
     */
    @Override
    public void removeScheduler(String id, long pollInterval, long refreshAhead) {
        try {
            workManagerDao.lock(id, -1);
            workManagerDao.remove(id);
        } finally {
            workManagerDao.unlock(id);
        }
    }

    public boolean schedulerExists(String schedulerKey) {
        return allSchedulers.containsKey(schedulerKey);
    }

    public void onPut(String schedulerKey, WorkManagerEntry entry) {
        handlePutOrUpdate(schedulerKey, null, entry);
    }
    
    public void onUpdate(String schedulerKey, WorkManagerEntry oldValue, WorkManagerEntry entry) {
        handlePutOrUpdate(schedulerKey, oldValue, entry);
    }

    public void onRemove(String key, WorkManagerEntry value) {
        this.logger.log(Level.DEBUG, "Scheduler Cache removed:"+value);
        allSchedulers.remove(key);
    }
    
    private void handlePutOrUpdate(String schedulerKey, WorkManagerEntry oldEntry, WorkManagerEntry entry) {
        if (oldEntry == null) {
            this.logger.log(Level.DEBUG, "onPUT: newValue="+entry);
        } else {
            this.logger.log(Level.DEBUG, "onUPDATE: oldValue="+oldEntry + ", newValue="+entry);
        }
        
        allSchedulers.put(schedulerKey, entry);

        if (isNodeConfiguredAsScheduler()) {
            try {
                schedulelock2.lock();
                if (!started) {
                    return;
                }
                schedulerManager.submitJob(new AcquireWorkManager(schedulerKey));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                schedulelock2.unlock();
            }
        }
    }
    
    private void loadSchedulers() {
        Iterator<String> allSch = workManagerDao.keySet().iterator();
        while (allSch.hasNext()) {
            String schKey = allSch.next();
            if (schKey != null) {
                WorkManagerEntry wm = workManagerDao.get(schKey);
                if (wm != null) {
                    allSchedulers.put(schKey, wm);
                }
            }
        }
    }
    
    @Override
    public void memberJoined(GroupMember member) {
        //TODO: Is there anything needs to be done?
    }
    
    @Override
    public void memberLeft(GroupMember member) {
        try {
            try {
                schedulelock2.lock();
                schedulerManager.submitJob(new MemberService(member));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                schedulelock2.unlock();
            }
        }
        catch (Exception e) {
            if(!shutdown.get()){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void memberStatusChanged(GroupMember member, Status oldStatus,
            Status newStatus) {
        //TODO: Is there anything needs to be done?
    }
    
    protected class MemberService implements Runnable {
        GroupMember member;
        protected MemberService(GroupMember member) {
            this.member = member;
        }
        public void run() {
            try {
                handleMemberLeft();
            } catch (Exception e) {
                if (!shutdown.get()) {
                    e.printStackTrace();
                }
            }
        }
        
        private void handleMemberLeft() {
            try {
                try {
                    schedulelock.lock();
                    Iterator<String> allSchedulers = workManagerDao.keySet().iterator();
                    while (allSchedulers.hasNext()) {
                        String schedulerId = (String) allSchedulers.next();
                        try {
                            workManagerDao.lock(schedulerId, -1);
                            WorkManagerEntry we = (WorkManagerEntry) workManagerDao.get(schedulerId);
                            if ((we != null) && (we.getNodeId() != null)) {
                                if (we.getNodeId().equals(member.getMemberId())) {
                                    we.setNodeId(null);
                                    workManagerDao.put(schedulerId, we);
                                }
                            }
                        } finally {
                            workManagerDao.unlock(schedulerId);
                        }
                    }
                } finally {
                    schedulelock.unlock();
                }
            }
            catch (Exception e) {
                if(!shutdown.get()){
                    e.printStackTrace();
                }
            }
        }
    }
}
