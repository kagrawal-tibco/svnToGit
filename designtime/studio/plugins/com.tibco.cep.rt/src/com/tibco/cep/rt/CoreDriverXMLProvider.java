package com.tibco.cep.rt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.tibco.cep.tpcl.IDriverXmlProvider;

public class CoreDriverXMLProvider implements IDriverXmlProvider {

	public CoreDriverXMLProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.tpcl.IDriverXmlProvider#getDriverXML()
	 */
	@Override
	public List<URL> getDriverXML() throws IOException {
		List<URL> urls = new ArrayList<URL>();
		Enumeration<URL> res = Activator.class.getClassLoader().getResources(DRIVERS_XML);
		while(res.hasMoreElements()){
			urls.add(res.nextElement());
		}
		return urls;
	}

}
