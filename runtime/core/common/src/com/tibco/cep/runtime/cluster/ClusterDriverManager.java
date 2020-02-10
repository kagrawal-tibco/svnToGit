package com.tibco.cep.runtime.cluster;

import java.net.URL;
import java.util.List;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.driver.AbstractDriverManager;
import com.tibco.cep.runtime.driver.DriverPojo;
import com.tibco.cep.runtime.driver.DriverXmlParser;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author ssinghal
 *
 */
public class ClusterDriverManager extends AbstractDriverManager{
	
	protected static final String LOCAL_DRIVERS_XML_NAME = "cluster.xml";
	protected static final ExpandedName DRIVER_FILE_NODE_NAME_ROOT = ExpandedName.makeName("cluster");
	protected static final String DRIVER_CATEGORY = "cluster";
	
	
	@Override
	public void initialize() throws Exception {
		loadDrivers(LOCAL_DRIVERS_XML_NAME, DRIVER_FILE_NODE_NAME_ROOT);
    }
	
	@Override
	protected void register(URL url) {
		final ClusterDriverManager.ClusterDriverRegistration registration = new ClusterDriverManager.ClusterDriverRegistration(url);
		d_registrations.add(registration);
	}

	public List<DriverPojo> getAllDrivers() throws Exception{
		DriverXmlParser xmlParser = new ClusterXmlParser();
		return xmlParser.loadDrivers(getDriverUrls());
	}
	
	class ClusterDriverRegistration extends AbstractDriverRegistration {
		
		public ClusterDriverRegistration(URL url) {
			super(url, DRIVER_CATEGORY);
		}
	}


	

}
