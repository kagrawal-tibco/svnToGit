package com.tibco.cep.runtime.service.cluster.gmp;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/**
 * Utility to suspend agents
 *
 */
public class SuspendAgents implements Invocable {

	private static final long serialVersionUID = -4926771631373624577L;

	public SuspendAgents() {
        super();
    }

    /**
     * invoked on each node via invocation service
     * 
     */
    @Override
    public Object invoke(Map.Entry entry) throws Exception {
    	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
    	if (cluster != null) {
    		Thread.currentThread().setContextClassLoader(cluster.getRuleServiceProvider().getClassLoader());
    		cluster.getAgentManager().suspendAgents();
        }
		return true;
    }

    /**
     * Suspend all agents in the cluster -- using the invocation service
     * 
     * @return
     * @throws Exception
     */
    public final static boolean suspendAgents() throws Exception {
        boolean done = true;
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            InvocationService invoker = cluster.getDaoProvider().getInvocationService();
            Map<String, Invocable.Result> resultSet = invoker.invoke(new SuspendAgents(), null);
            final Logger logger = cluster.getRuleServiceProvider().getLogger(SuspendAgents.class);
            java.util.Iterator<Map.Entry<String,Invocable.Result>> iter = resultSet.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String,Invocable.Result> entry = iter.next();
                Invocable.Result ret = entry.getValue();
                String uid = entry.getKey();
                if (ret.getStatus() == Invocable.Status.ERROR) {
                	logger.log(Level.ERROR, "%s: OnSuspend: Member=%s has an exception: %s", cluster.getClusterName(), uid, ret.getException());
                    done = false;
                }
            }
        }
        return done;
    }
}
