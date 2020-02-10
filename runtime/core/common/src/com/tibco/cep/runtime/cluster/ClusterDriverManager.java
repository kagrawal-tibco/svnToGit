package com.tibco.cep.runtime.cluster;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.driver.AbstractDriverManager;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author ssinghal
 *
 */
public class ClusterDriverManager extends AbstractDriverManager{
	
	protected static final ExpandedName DRIVER_FILE_NODE_NAME_ROOT = ExpandedName.makeName("clusters");
	protected static final ExpandedName DRIVER_FILE_NODE_NAME_DRIVER = ExpandedName.makeName("cluster");
	
	
	@Override
	public void initialize() throws Exception {
		loadDrivers("clusters.xml", DRIVER_FILE_NODE_NAME_ROOT, DRIVER_FILE_NODE_NAME_DRIVER);
    	initializeRegisteredDrivers();
    }
	
	@Override
	 protected void register(String type, String label, String version, String className, String description) {
	    	final ClusterDriverManager.ClusterDriverRegistration registration = new ClusterDriverManager.ClusterDriverRegistration(type, label, version, className, description);
	    	if (m_registrations.get(type) == null) {
	    		m_registrations.put(type, registration);
	    	} else {
	    		ClusterDriverManager.ClusterDriverRegistration registered = (ClusterDriverRegistration) m_registrations.get(type);
	    		if (registered != null && registered.getClassName() != null
	    				&& !registered.getClassName().equals(className)) {
	    			//If the driver type is already registered with a different class, log a warning.
	    			LogManagerFactory.getLogManager().getLogger(ClusterDriverManager.class).log(Level.WARN,
	    					"Cluster '" + type + "' is already registered using class - " + className);
	    		}
	    	}
	    }
	
	
	class ClusterDriverRegistration extends AbstractDriverRegistration {
		
		public ClusterDriverRegistration(String type, String label, String version, String className, String description) {
			super(type, label, version, className, description);
		}
	}

}
