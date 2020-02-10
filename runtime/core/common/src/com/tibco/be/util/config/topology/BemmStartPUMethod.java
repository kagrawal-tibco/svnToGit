/**
 * 
 */
package com.tibco.be.util.config.topology;

/**
 * @author Nick
 *
 */
public class BemmStartPUMethod {
	private String name;
	
	public BemmStartPUMethod(String name){
		this.name = name == null ? null : name.trim().toLowerCase();
	}

	public String getName() {
		return name;
	}

}
