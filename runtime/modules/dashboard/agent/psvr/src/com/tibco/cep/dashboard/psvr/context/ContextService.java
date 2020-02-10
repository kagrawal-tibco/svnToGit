package com.tibco.cep.dashboard.psvr.context;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public class ContextService extends Service {
	
	private Properties properties;
	
	private ContextCache contextCache;
    
    public ContextService(){
    	super("contextservice","Context Service");
    }
    
	@Override
	protected void doInit() throws ManagementException {
    	contextCache = ContextCache.getInstance();	
	}  

	@Override
	protected void doStart() throws ManagementException {
		contextCache.init(logger,properties);
	}
	
	@Override
	protected boolean doStop() {
		boolean success = true;
		if (contextCache != null) {
			success = contextCache.shutdown();
			contextCache = null;
		}
		if (success == false){
			logger.log(Level.WARN, getDescriptiveName()+" did not shutdown cleanly");
		}
		return success;
	}	

}