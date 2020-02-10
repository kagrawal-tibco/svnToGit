package com.tibco.cep.runtime.service.cluster.deploy;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/**
 * Undeploy a class from hotDeployer.delEntityCache
 * 
 * 
 */
public class ClassUnloadService implements Invocable {

	private static final long serialVersionUID = -4387931304079197958L;

	public ClassUnloadService() {
    }

    /**
     * Remote invocation -- this is executed on each node -- undeploy the class in delEntityCache
     * 
     */
    @Override
    public Object invoke(Map.Entry entry) throws Exception {
    	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
    	if (cluster != null) {
    		Thread.currentThread().setContextClassLoader(cluster.getRuleServiceProvider().getClassLoader());
    		cluster.getHotDeployer().undeployExternalClass();
        }
		return true;
    }

    /**
     * Unload an external class loaded into each cluster member using the
     * <b>externalClasses.Path</b> property.
     * @return
     * @throws Exception
     */
    //TODO Rollback operation on each node if it fails even on a single node.
    public final static boolean unloadExternalClass() throws Exception{
        boolean done=true;
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            InvocationService invoker=cluster.getDaoProvider().getInvocationService();
            Map<String, Invocable.Result> resultSet=invoker.invoke(new ClassUnloadService(), null);
            final Logger logger = cluster.getRuleServiceProvider().getLogger(ClassUnloadService.class);
            java.util.Iterator<Map.Entry<String,Invocable.Result>> iter = resultSet.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String,Invocable.Result> entry = iter.next();
                Invocable.Result ret= entry.getValue();
                String uid = entry.getKey();
                if (ret.getStatus() == Invocable.Status.ERROR) {
                	if(ret.getResult() == null) {
                		logger.log(Level.ERROR, "%s: OnSuspend: Member=%s has an exception: %s", cluster.getClusterName(), uid, new RuntimeException("The class is not available for unload operation or is already been unloaded.", ret.getException()));
                	} else{
                		logger.log(Level.ERROR, "%s: OnSuspend: Member=%s has an exception: %s", cluster.getClusterName(), uid, ret.getException());
                	}
                    done=false;
                }
            }
        }
        return done;
    }
}
