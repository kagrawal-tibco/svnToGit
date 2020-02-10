/**
 * 
 */
package com.tibco.cep.modules.db.service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author vpatil
 *
 */
public interface OracleConnectionPool {
	
	public void activate() throws Exception;
	
	public Connection getConnection() throws SQLException;
	
	public void refresh() throws SQLException;
	
	public void close();
	
	public String getName();
	
	public int getCacheSize();
	
	public int getNumberOfActiveConnections();
	
	public int getNumberOfAvailableConnections();
}
