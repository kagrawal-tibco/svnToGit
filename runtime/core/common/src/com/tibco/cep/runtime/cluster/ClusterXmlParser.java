package com.tibco.cep.runtime.cluster;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.runtime.driver.DriverPojo;
import com.tibco.cep.runtime.driver.DriverXmlParser;

/**
 * @author ssinghal
 *
 */
public class ClusterXmlParser extends DriverXmlParser{
	
	@Override
	public List<DriverPojo> loadDrivers(List<URL> urls) throws Exception {
		
		List<DriverPojo> driverPojos = new ArrayList<DriverPojo>();
		
		for(URL url:urls){
			
			DriverPojo cdp = new ClusterDriverPojo();
			initRoot(url);
			populateData(cdp);
		}
		
		
		return null;
	}

}
