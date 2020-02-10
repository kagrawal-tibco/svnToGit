package com.tibco.cep.tpcl;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface IDriverXmlProvider {
	
	public static final String DRIVERS_XML="drivers.xml";//$NON-NLS-1$
	
	List<URL> getDriverXML() throws IOException;
}
