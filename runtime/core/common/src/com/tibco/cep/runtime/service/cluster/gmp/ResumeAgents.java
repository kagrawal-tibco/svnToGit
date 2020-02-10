package com.tibco.cep.runtime.service.cluster.gmp;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/**
 * Utility to resume agents
 * 
 */
public class ResumeAgents implements Invocable {

	private static final long serialVersionUID = 2742735042118205651L;

	public ResumeAgents() {
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
    		cluster.getAgentManager().resumeAgents();
        }
        return true;
    }

    public final static boolean resumeAgents () throws Exception{
        boolean done=true;
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            InvocationService invoker=cluster.getDaoProvider().getInvocationService();
            Map<String, Invocable.Result> resultSet=invoker.invoke(new ResumeAgents(), null);
            final Logger logger = cluster.getRuleServiceProvider().getLogger(ResumeAgents.class);
            java.util.Iterator<Map.Entry<String,Invocable.Result>> iter = resultSet.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String,Invocable.Result> entry = iter.next();
                Invocable.Result ret= entry.getValue();
                String uid = entry.getKey();
                if (ret.getStatus() == Invocable.Status.ERROR) {
                	logger.log(Level.ERROR, "%s: OnSuspend: Member=%s has an exception: %s", cluster.getClusterName(), uid, ret.getException());
                    done=false;
                }
            }
        }
        return done;
    }

}



