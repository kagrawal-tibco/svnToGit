/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.deploy.ClassUnloadService;
import com.tibco.cep.runtime.service.cluster.deploy.HotDeployer;
import com.tibco.cep.runtime.service.cluster.deploy.LoadExternalClasses;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.SuspendAgents;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;
import com.tibco.cep.runtime.service.om.api.MapChangeListener;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Hot deployment related
 * 
 */
public class DefaultHotDeployer implements HotDeployer, MapChangeListener<Integer, Object> {
    Cluster cluster;

    ControlDao<Integer, Object> hotDeployer;

    SingleKeyCache delEntityCache;

    Map<String, byte[]> classes = new HashMap<String, byte[]>();

    WorkManager hotDeployWM;

    private final static int ID = -1;

    private final static byte START = 0x01;

    private final static byte INPROGRESS = 0x02;

    private final static byte DONE = 0x03;

    private final static byte ERROR = 0x05;

    final static Logger logger = LogManagerFactory.getLogManager().getLogger(HotDeployer.class);

    /**
     * @param cluster
     * @throws Exception
     */
    public DefaultHotDeployer() {
    	
    }

    public void init (Cluster cluster) throws Exception {
        this.cluster = cluster;
        hotDeployWM = WorkManagerFactory.create(cluster.getClusterName(), null, null, getClass().getSimpleName(), 1, cluster.getRuleServiceProvider());
        hotDeployer = cluster.getClusterProvider().createControlDao(Integer.class, Object.class, ControlDaoType.HotDeployer, cluster);
        hotDeployer.registerListener(this);
    }


	@Override
	public void start() throws Exception {
		hotDeployer.start();
        delEntityCache = new SingleKeyCache(cluster);
        hotDeployWM.start();
	}

    public void deploy(String uriPath) throws Exception {
        //Suresh TODO: Work with Aditya
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public synchronized boolean insert() throws Exception {
        try {
            hotDeployer.lock(ID, -1);
            Integer id = (Integer) hotDeployer.get(ID);
            if (id != null) {
                HotDeployRequest request = (HotDeployRequest) hotDeployer.get(id);
                if (request != null) {
                    if ((request.status != DONE) && (request.status != ERROR)) {
                        return false;
                    }
                }
            }
            else {
                id = new Integer(0);
            }
            hotDeployer.put(ID, id + 1);
            hotDeployer.put(id + 1, new HotDeployRequest(cluster.getGroupMembershipService().getLocalMember().getMemberId(), System.currentTimeMillis(), START));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            hotDeployer.unlock(ID);
        }
        return true;
    }

    synchronized protected boolean acquire() throws Exception {
        try {
            hotDeployer.lock(ID, -1);
            Integer id = (Integer) hotDeployer.get(ID);
            HotDeployRequest request = (HotDeployRequest) hotDeployer.get(id);
            if (request != null && request.status == START) {
                request.status = INPROGRESS;
                request.member = cluster.getGroupMembershipService().getLocalMember().getMemberId();
                hotDeployer.put(id, request);
                return true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            hotDeployer.unlock(ID);
        }
        return false;
    }

    synchronized protected boolean release(boolean done, Exception error) throws Exception {
        try {
            hotDeployer.lock(ID,-1);
            Integer id = (Integer) hotDeployer.get(ID);
            HotDeployRequest request = (HotDeployRequest) hotDeployer.get(id);
            if (request != null && request.status == INPROGRESS) {
                if (done) {
                    request.status = DONE;
                }
                else {
                    request.status = ERROR;
                    request.error = error;
                }
                hotDeployer.put(id, request);
                return true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            hotDeployer.unlock(ID);
        }
        return false;
    }

    synchronized protected void memberLeft(UID memberNode) {
        try {
            hotDeployer.lock(ID,-1);
            Integer id = (Integer) hotDeployer.get(ID);
            HotDeployRequest request = (HotDeployRequest) hotDeployer.get(id);
            if (request != null) {
                if (request.member.equals(memberNode) && request.status == INPROGRESS) {
                    request.member = cluster.getGroupMembershipService().getLocalMember().getMemberId();
                    request.status = START;
                    hotDeployer.put(id, request);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            hotDeployer.unlock(ID);
        }
    }

    public void onPut(Integer key, Object value, boolean isLocal) {
        if (value instanceof HotDeployRequest) {
            HotDeployRequest req = (HotDeployRequest) value;
            if (req.status == START) {
                try {
                    hotDeployWM.submitJob(new HotDeployTask(req));
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void onUpdate(Integer key, Object oldValue, Object newValue) {
        if (newValue instanceof HotDeployRequest) {
            HotDeployRequest req = (HotDeployRequest) newValue;
            if (req.status == START) {
                try {
                    hotDeployWM.submitJob(new HotDeployTask(req));
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void onRemove(Integer key, Object value, boolean isLocal) {
    }


    static class HotDeployRequest implements Serializable {
        Id member;
        long time;
        byte status;
        Exception error;

        HotDeployRequest(Id member, long time, byte status) {
            this.member = member;
            this.time = time;
            this.status = status;
        }
    }

    class HotDeployTask implements Runnable {
        HotDeployRequest request;

        HotDeployTask(HotDeployRequest request) {
            this.request = request;
        }

        public void run() {
            boolean acquired = false;
            try {
                if (acquire()) {
                    acquired = true;
                    //Invoke on all members
                    deployExternalClasses(true, null); //TODO: Add suspend
                    release(true, null);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                if (cluster != null) {
                    try {
                        if (acquired) {
                            release(false, ex);
                        }
                    }
                    catch (Exception ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
    }

    
    private boolean deployExternalClasses(boolean suspend, Set<GroupMember> members) throws Exception {
		boolean done = true;

		final String clusterName = cluster.getClusterName();

		// Suspend all the nodes
		if (suspend) {
			logger.log(Level.INFO,"Cluster %s: Initiated Suspend Request to All Nodes",	clusterName);
			if (!cluster.getGroupMembershipService().suspendAll()) {
				logger.log(Level.INFO,"Cluster %s: Failed to suspend all agents. trying to resume everyone", clusterName);
				if (!cluster.getGroupMembershipService().resumeAll()) {
					logger.log(Level.INFO, "Cluster %s: Failed to resume all agents.", clusterName);
				}
				return false;
			}
		}

		logger.log(Level.INFO, "Cluster %s: Initiated LoadExternalClasses to all nodes.",clusterName);
		InvocationService invoker = cluster.getInvocationService();

		Map<String, Invocable.Result> resultSet = invoker.invoke(new LoadExternalClasses(), null);
		java.util.Iterator<Map.Entry<String, Invocable.Result>> iter = resultSet.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Invocable.Result> entry = iter.next();
			Invocable.Result ret = entry.getValue();
			String uid = entry.getKey();
			if (ret.getStatus() == Invocable.Status.ERROR) {
				logger.log(Level.ERROR,	"%s: LoadExternalClasses: Member=%s has an exception: %s",cluster.getClusterName(), uid, ret.getException());
				done = false;
			}
		}

		if (!cluster.getGroupMembershipService().resumeAll()) {
			logger.log(Level.INFO, "Cluster %s: Failed to resume all agents.", clusterName);
		}

		return done;

	}
    
    /**
     * Undeploy an external class loaded into the classloader
     *
     * @throws Exception
     */
    @Override
    public void undeployExternalClass() throws Exception {
    	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
    	final Logger logger = cluster.getRuleServiceProvider().getLogger(SuspendAgents.class);
		String name = cluster.getGroupMembershipService().getLocalMember().getMemberName();
		String mid = cluster.getGroupMembershipService().getLocalMember().getMemberId().getAsString();
		final String agentNameStr = "Agent " + name + "-" + mid;
		if (logger.isEnabledFor(Level.INFO)) {
			logger.log(Level.INFO, agentNameStr	+ ": Issuing deactivate class request");
		}
		// Get the RSP for this agent
		RuleServiceProvider rsp = cluster.getRuleServiceProvider();
		// Get the VRFURI, and impl from deleted entity cache
		String deactivatedEntityURI = delEntityCache.keySet().iterator().next();
		String[] splits = deactivatedEntityURI.split(":");
		String vrfURI = splits[0];
		String implName = splits[1];
		if (logger.isEnabledFor(Level.INFO)) {
			logger.log(Level.INFO, "Clearing registry on " + agentNameStr + " Registry of cached class");
		}
		unloadExternalClass(rsp, vrfURI, implName);
	}
    
    public static void unloadExternalClass(RuleServiceProvider rsp, String vrfURI, String implName) throws Exception {
		// Get the classloader for this
		TypeManager typeManager = rsp.getTypeManager();
		if (typeManager instanceof BEClassLoader) {
			BEClassLoader cl = (BEClassLoader) typeManager;	
			// get the VRFRegistry
			boolean b = cl.directClearVRFImpl(vrfURI, implName);
			if (!b) {
				throw new Exception();
			}
		}
    }

    public void unloadClass(String vrfURI, String implName) throws Exception {
    	final String clusterName = cluster.getClusterName();
    	logger.log(Level.DEBUG, "Cluster %s: Undeploy class started.", clusterName);
        String resolvedClass = resolveClass(vrfURI, implName);
        delEntityCache.put(vrfURI + ":" + implName, resolvedClass);

        if (!cluster.getGroupMembershipService().suspendAll()) {
        	logger.log(Level.ERROR,"Cluster %s: Failed to suspend all nodes. So trying to resume nodes..", clusterName);
			if (!cluster.getGroupMembershipService().resumeAll()) {
				logger.log(Level.ERROR, "Cluster %s: Resume also failed all nodes.. giving up", clusterName);
			}
			return;
        }
        //Issue unload request for all agents in the cluster
        if (!ClassUnloadService.unloadExternalClass()) {
        	logger.log(Level.ERROR, "Cluster %s: Failed to undeploy class on atleast one agent.", clusterName);
        }
        //Resume Channels
        if (!cluster.getGroupMembershipService().resumeAll()) {
        	logger.log(Level.ERROR, "Cluster %s: Failed to resume all agents after undeploying class.", clusterName);
        	return;
        }
    	logger.log(Level.DEBUG, "Cluster %s: Undeploy class complete on all nodes successfully.", clusterName);
        //EXTERNALCATALOG.removeExternalClass(clazz);
    	delEntityCache.remove(vrfURI + ":" + implName);
    }
    
    private String resolveClass(String vrfURI, String implName) throws Exception {
        //Get a Rule function object
        Entity entity = cluster.getRuleServiceProvider().getProject().getOntology().getEntity(vrfURI);
        if (entity == null || !(entity instanceof RuleFunction)) {
            throw new Exception("Rule function not found");
        }
        RuleFunction vrf = (RuleFunction) entity;
        //Get fully qualified class name
        String pkgName =  ModelNameUtil.vrfImplPkg(vrf);

        final boolean isDebug = logger.isEnabledFor(Level.DEBUG);
        if (isDebug) {
            this.logger.log(Level.DEBUG, "Package name for Virtual Rule Function: %s", pkgName);
        }
        String implClass = ModelNameUtil.vrfImplClassShortName(implName);
        if (isDebug) {
            this.logger.log(Level.DEBUG, "Class name for Implementation: %s", implClass);
        }
        return pkgName + "." + implClass;
    }

}