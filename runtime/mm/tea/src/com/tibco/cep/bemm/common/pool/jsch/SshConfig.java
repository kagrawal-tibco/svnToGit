package com.tibco.cep.bemm.common.pool.jsch;

/**
 * Class has the detaills of SSH
 * 
 * @author dijadhav
 *
 */
public class SshConfig {
	/**
	 * Host IP address
	 */
	private String hostIp;
	/**
	 * Name of the SSH user
	 */
	private String userName;
	/**
	 * SSH port
	 */
	private int port;
	/**
	 * SSH password
	 */
	private String password;

	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * @param hostIp
	 *            the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
