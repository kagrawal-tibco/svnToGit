/**
 * 
 */
package com.tibco.cep.dashboard.psvr.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author anpatil
 * @deprecated
 */
public class ResourceProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = -158945833120545057L;
	
	public ResourceProperties(String fileName){
		super();
		URL propertyLocationURL = this.getClass().getResource("/"+fileName);
		if (propertyLocationURL == null){
			throw new IllegalArgumentException(fileName+" could not be located in the classpath");
		}
		try {
			load(propertyLocationURL.openStream());
		} catch (IOException e) {
			throw new RuntimeException("could not load "+fileName,e);
		}
	}
}