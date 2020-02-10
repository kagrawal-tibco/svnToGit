/**
 * 
 */
package com.tibco.be.util.config.topology;

/**
 * @author Nick
 *
 */
public class BemmSshConfig extends BemmStartPUMethod {
	private String port;
	
	public BemmSshConfig(String name, String port){
		super(name);
		this.port = port == null ? null : port.trim();
        //Just to validate if it's a valid integer. In case it is not it will throw NFE
        Integer.valueOf(this.port);
	}

	public String getPort() {
		return port;
	}
}
