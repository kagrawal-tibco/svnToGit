/**
 * 
 */
package com.tibco.be.util.config.topology;

/**
 * User: hlouro
 * Date: Nov 1 2011
 * Time: 08:33 AM
 */

public class BemmBEHome {
	private static final String DEFAULT_BE_ENGINE_TRA_FILE = "/bin/be-engine.tra";
	private String version;
	private String traPath;
	private String beHome;
	
	public BemmBEHome(String version, String traPath, String beHome){
		this.version = version == null ? null : version.trim();
        this.beHome = beHome == null ? null : beHome.trim();
        this.traPath = traPath == null ? null : traPath.trim();

		if(this.traPath == null || this.traPath.isEmpty()){
			this.traPath = beHome + DEFAULT_BE_ENGINE_TRA_FILE;
		}
	}
	
	public String getVersion(){
		return version;
	}
	public String getTra(){
		return traPath;
	}
	public String getHome(){
		return beHome;
	}
}
