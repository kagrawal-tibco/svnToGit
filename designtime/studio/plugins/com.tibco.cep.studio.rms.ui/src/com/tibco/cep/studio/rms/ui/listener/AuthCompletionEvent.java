/**
 * 
 */
package com.tibco.cep.studio.rms.ui.listener;

import java.util.EventObject;

/**
 * @author aathalye
 *
 */
public class AuthCompletionEvent extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4260103267873569781L;

	private int authEventType;
	
	private String username;
	
	/**
	 * The clear text password
	 */
	private String password;
	
	/**
	 * 
	 * @param repoURL -> The repo URL
	 * @param username
	 * @param authEventType
	 */
	public AuthCompletionEvent(String repoURL, String username, int authEventType) {
		super(repoURL);
		this.username = username;
		this.authEventType = authEventType;
	}
	
	/**
	 * 
	 * @param repoURL -> The repo URL
	 * @param username
	 * @param password
	 * @param authEventType
	 */
	public AuthCompletionEvent(String repoURL, String username, String password, int authEventType) {
		this(repoURL, username, authEventType);
		this.password = password;
	}
	
	/**
	 * 
	 * @param repoURL -> The repo URL
	 */
	public AuthCompletionEvent(String repoURL) {
		super(repoURL);
	}
	
	public static final int AUTH_SUCCESS_EVENT = 1 << 1;
	
	public static final int AUTH_FAILURE_EVENT = 1 << 2;

	public int getAuthEventType() {
		return authEventType;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}
}
