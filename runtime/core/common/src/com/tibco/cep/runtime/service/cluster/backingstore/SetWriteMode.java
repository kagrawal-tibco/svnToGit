package com.tibco.cep.runtime.service.cluster.backingstore;

import java.util.Map.Entry;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/**
 * @author bgokhale
 * Ported from 4.x
 * 
 * Used by backing store pre-load process to prevent write-behind to database during cache.putAll()
 * 
 */
public class SetWriteMode implements Invocable {

    protected final static Logger logger = LogManagerFactory.getLogManager().getLogger(SetWriteMode.class);
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4040265965791175768L;
	
	boolean writeMode = false;

    public SetWriteMode(boolean writeMode) {
        this.writeMode = writeMode;
    }

    public SetWriteMode() {
    }

    public final static void setWriteMode (boolean writeMode) throws Exception {
        InvocationService invoker=CacheClusterProvider.getInstance().getCacheCluster().getDaoProvider().getInvocationService();
        invoker.invoke(new SetWriteMode(writeMode), null);
    }

    // Gets executed remotely.
	@Override
	public Object invoke(Entry entry) throws Exception {
        try {
        	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null && cluster.getBackingStore() != null) {
                logger.log(Level.DEBUG, "Local node write mode turned (%s)", (writeMode ? "ON" : "OFF"));
                GenericBackingStore genericBackingStore = cluster.getBackingStore();
                if ((genericBackingStore != null) && (genericBackingStore instanceof BackingStore)) {
                    ((BackingStore)genericBackingStore).setWriteMode(writeMode);
                }
            }
            return true;
        } catch (Exception ex) {
            logger.log(Level.WARN, "Setting %s write mode to (%s) failed.", ex, "entity", (writeMode ? "ON" : "OFF"));
        }
        return false;
	}
}
