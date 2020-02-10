/**
 * 
 */
package com.tibco.be.ws.scs;

/**
 * ENUM for list of default supported SCS types. Currently only File and SVN are supported.
 * 
 * @author vpatil
 */
public enum SCSType {
	
	FILE("file"), SVN("svn"), CUSTOM("custom");
	
	private String type;
	
	SCSType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static SCSType getValue(String type) {
		for (SCSType scsType : SCSType.values()) {
			if (scsType.getType().equals(type)) return scsType;
		}
		
		return SCSType.CUSTOM;
	}
}
