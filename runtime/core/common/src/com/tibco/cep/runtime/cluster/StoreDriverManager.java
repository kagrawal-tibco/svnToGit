package com.tibco.cep.runtime.cluster;

import java.net.URL;
import java.util.List;

import com.tibco.cep.runtime.driver.AbstractDriverManager;
import com.tibco.cep.runtime.driver.DriverPojo;
import com.tibco.cep.runtime.driver.DriverXmlParser;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author ssinghal
 *
 */
public class StoreDriverManager extends AbstractDriverManager{
	
	protected static final String LOCAL_DRIVERS_XML_NAME = "store.xml";
	protected static final ExpandedName DRIVER_FILE_NODE_NAME_ROOT = ExpandedName.makeName("store");
	protected static final String DRIVER_CATEGORY = "store";
	
	
	@Override
	public void initialize() throws Exception {
		loadDrivers(LOCAL_DRIVERS_XML_NAME, DRIVER_FILE_NODE_NAME_ROOT);
    }
	
	@Override
	protected void register(URL url) {
		final StoreDriverManager.StoreDriverRegistration registration = new StoreDriverManager.StoreDriverRegistration(url);
		d_registrations.add(registration);
	}

	public List<DriverPojo> getAllDrivers() throws Exception{
		DriverXmlParser xmlParser = new ClusterXmlParser();
		return xmlParser.loadDrivers(getDriverUrls());
	}
	
	class StoreDriverRegistration extends AbstractDriverRegistration {
		
		public StoreDriverRegistration(URL url) {
			super(url, DRIVER_CATEGORY);
		}
	}

}
