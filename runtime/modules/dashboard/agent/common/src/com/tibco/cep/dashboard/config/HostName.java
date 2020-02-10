package com.tibco.cep.dashboard.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

class HostName {
	
	private String hostName;
	
	HostName(boolean useSimpleName){
		try {
			if (useSimpleName == true){
				hostName = InetAddress.getLocalHost().getHostName();
			}
			else {
				hostName = InetAddress.getLocalHost().getCanonicalHostName();
			}
		} catch (UnknownHostException e) {
			hostName = "localhost";
		}
	}
	
	String getName(){
		return hostName;
	}

}
