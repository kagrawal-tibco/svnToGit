package com.tibco.cep.dashboard.security;

public class UserRuntimeInfo {
	
	private String userid;
	
	private long time;
	
	public UserRuntimeInfo(String userid, long time) {
		super();
		this.userid = userid;
		this.time = time;
	}

	public final String getUserid() {
		return userid;
	}

	public final void setUserid(String userid) {
		this.userid = userid;
	}

	public final long getTime() {
		return time;
	}

	public final void setTime(long time) {
		this.time = time;
	}
	
}
