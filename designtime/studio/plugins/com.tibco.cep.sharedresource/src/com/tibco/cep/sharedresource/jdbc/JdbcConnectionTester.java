package com.tibco.cep.sharedresource.jdbc;

import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.impl.DBHelper;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.sharedresource.SharedResourcePlugin;
import com.tibco.cep.sharedresource.ssl.SslConfigJdbcModel;
import com.tibco.cep.studio.core.util.GvUtil;

/*
@author ssailapp
@date Aug 15, 2011
 */
@SuppressWarnings({"unused"})
public class JdbcConnectionTester {
	
	private Shell shell;
	private JdbcConfigModelMgr modelmgr;
	
	public JdbcConnectionTester(Shell shell, JdbcConfigModelMgr modelmgr) {
		this.shell = shell;
		this.modelmgr = modelmgr;
	}
	
	public boolean testConnection() {
		//String jdbcdriver = tDriver.getText(); 
		String jdbcdriver = modelmgr.getStringValue("driver");
		jdbcdriver = GvUtil.getGvDefinedValue(modelmgr.getProject(), jdbcdriver);
		if ( jdbcdriver.startsWith("oracle.jdbc.driver.OracleDriver") )
			jdbcdriver = "oracle.jdbc.OracleDriver";
		try {
			//String url = tgDbUrlJdbc.getGvDefinedValue(modelmgr.getProject());
			String url = modelmgr.getStringValue("location");
			url = GvUtil.getGvDefinedValue(modelmgr.getProject(), url);
			//String username = tgUsernameJdbc.getGvDefinedValue(modelmgr.getProject());
			String username = modelmgr.getStringValue("user");
			username = GvUtil.getGvDefinedValue(modelmgr.getProject(), username);
			//String passwd = tgPasswordJdbc.getGvDefinedValue(modelmgr.getProject());
			String passwd = modelmgr.getStringValue("password");
			passwd = GvUtil.getGvDefinedValue(modelmgr.getProject(), passwd);
			
			String useSsl = modelmgr.getStringValue("useSsl");
			useSsl = GvUtil.getGvDefinedValue(modelmgr.getProject(), useSsl);
			
			Driver driver = (Driver) Class.forName(jdbcdriver).newInstance();
			
			Connection conn;
			
			if ("true".equalsIgnoreCase(useSsl)) {
				JdbcSSLConnectionInfo sslConnInfo = JdbcSSLSharedresourceHelper.getSSLConnectionInfo(username, passwd, url, jdbcdriver, modelmgr);
				sslConnInfo.loadSecurityProvider();
				conn = driver.connect(url, sslConnInfo.getProperties());//DriverManager.getConnection(url, sslConnInfo.getProperties());
			}
			else {
				Properties props = new Properties();
				props.put("user", username);
				props.put("password", passwd);
                if (jdbcdriver.toLowerCase().contains("mysql")) {
                    props.put("useSSL", "false");
                }
				conn = driver.connect(url, props);//DriverManager.getConnection(url, username, passwd);
			}
			ConnectionPool.unlockDDConnection(conn);
			
			if (conn == null) {
				/* java.sql.Driver.connect() documentation :
				 * 'The driver should return "null" if it realizes it is the wrong kind of driver to connect to the given URL.'*/
				throw new Exception("Given URL is not compatible with the selected driver.");
			}
			
			StringBuffer message = new StringBuffer();
			DatabaseMetaData metadata = conn.getMetaData();
			message.append("Database Product Name: " + metadata.getDatabaseProductName() + "\n");
			message.append("Database Product Version: " + metadata.getDatabaseProductVersion() + "\n");
			message.append("Driver Name: " + metadata.getDriverName() + "\n");
			message.append("Driver Version: " + metadata.getDriverVersion() + "\n");
			MessageDialog.openInformation(shell, "JDBC Connection", "JDBC connection test successful. \n\n" + message);
		} catch (InstantiationException e) {
			String message = "JDBC connection test failed. \n\n" + e.toString();
			SharedResourcePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SharedResourcePlugin.PLUGIN_ID, message, e));
			MessageDialog.openError(shell, "JDBC Connection", message);	
			return false;
		} catch (IllegalAccessException e) {
			String message = "JDBC connection test failed. \n\n" + e.toString();
			SharedResourcePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SharedResourcePlugin.PLUGIN_ID, message, e));
			MessageDialog.openError(shell, "JDBC Connection", message);	
			return false;
		} catch (ClassNotFoundException e) {
			String message = "JDBC connection test failed. \n\nClass not found: " + e.getMessage();
			SharedResourcePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SharedResourcePlugin.PLUGIN_ID, message, e));
			MessageDialog.openError(shell, "JDBC Connection", message);	
			return false;
		} catch (SQLException e) {
			String message = "JDBC connection test failed. \n\n" + e.toString();
			SharedResourcePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SharedResourcePlugin.PLUGIN_ID, message, e));
			MessageDialog.openError(shell, "JDBC Connection", message);
			return false;
		} catch (UnsatisfiedLinkError e) {
			String message = "JDBC connection test failed. \n\n" + e.toString();
			SharedResourcePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SharedResourcePlugin.PLUGIN_ID, message, e));
			MessageDialog.openError(shell, "JDBC Connection", message);
			return false;
		}
		catch (Exception e) {
			String message = "JDBC connection test failed. \n\n" + e.toString();
			SharedResourcePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, SharedResourcePlugin.PLUGIN_ID, message, e));
			MessageDialog.openError(shell, "JDBC Connection", message);
			return false;
		}
		return true;
	}
}
