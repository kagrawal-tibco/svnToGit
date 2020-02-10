package com.tibco.cep.studio.ui.build;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;

public final class JDBCDeployJobManifest {

	private IProject project;

	private String cdd;

	private String outputFolder;

	private String outputFilePrefix;

	private String baseTypeFile;

	private String databaseType;

	private boolean ansi;

	private boolean optimize;
	
	private boolean expandMaxStringSize;

	private String dbURL;

	private String dbUserName;

	private String dbPassword;

	private boolean populateObjectTable;

	private String databaseDriver;

	private JdbcSSLConnectionInfo sslConnectionInfo;

	public JDBCDeployJobManifest(IProject project) {
		super();
		this.project = project;
	}

	public IProject getProject() {
		return project;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getDatabaseDriver() {
		return databaseDriver;
	}

	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	public boolean isAnsi() {
		return ansi;
	}

	public void setAnsi(boolean ansi) {
		this.ansi = ansi;
	}

	public boolean isOptimize() {
		return optimize;
	}

	public void setExpandMaxStringSize(boolean expandMaxStringSize) {
		this.expandMaxStringSize = expandMaxStringSize;
	}

	public boolean isExpandMaxStringSize() {
		return expandMaxStringSize;
	}

	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	public String getCdd() {
		return cdd;
	}

	public void setCdd(String cdd) {
		this.cdd = cdd;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public String getOutputFilePrefix() {
		return outputFilePrefix;
	}

	public void setOutputFilePrefix(String outputFilePrefix) {
		this.outputFilePrefix = outputFilePrefix;
	}

	public String getBaseTypeFile() {
		return baseTypeFile;
	}

	public void setBaseTypeFile(String baseTypeFile) {
		this.baseTypeFile = baseTypeFile;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public JdbcSSLConnectionInfo getSSLConnectionInfo() {
		return this.sslConnectionInfo;
	}

	public void setSSLConnectionInfo(JdbcSSLConnectionInfo sslConnectionInfo) {
		this.sslConnectionInfo = sslConnectionInfo;
	}

	public boolean isPopulateObjectTable() {
		return populateObjectTable;
	}

	public void setPopulateObjectTable(boolean populateObjectTable) {
		this.populateObjectTable = populateObjectTable;
	}

	public String[] buildArguments(){
		List<String> arguments = new LinkedList<String>();
		//cdd
		if (isNullOrBlank(cdd) == false) {
			arguments.add("-c");
			arguments.add(project.findMember(cdd).getLocation().toString());
		}
		//out file prefix
		if (isNullOrBlank(outputFilePrefix) == false) {
			arguments.add("-o");
			arguments.add(outputFolder+File.separator+outputFilePrefix);
		}
		//schema
		if (isNullOrBlank(baseTypeFile) == false) {
			arguments.add("-s");
			arguments.add(baseTypeFile);
		}
		//database
		if (isNullOrBlank(databaseType) == false) {
			arguments.add("-d");
			arguments.add(databaseType);
		}
		//ansi
		arguments.add("-ansi");
		arguments.add(String.valueOf(ansi));
		//optimize
		if (optimize == true) {
			arguments.add("-optimize");
		}
		//maxstringsize
		if (expandMaxStringSize == true) {
			arguments.add("-maxstringsize");
			arguments.add("extended");
		}
		return arguments.toArray(new String[arguments.size()]);
	}

	private boolean isNullOrBlank(String str) {
		return str == null || str.trim().length() == 0;
	}


}
