package com.tibco.cep.studio.dbconcept.conceptgen;


public class JDBCResourceGen {
	
	private static String DRIVER = "driver";
	private static String URL = "url";
	private static String MAX_CONNS = "maxCons";
	private static String USERID = "userid";
	private static String PWD = "password";
	private static String TIMEOUT = "timeout";
	private static String CONN_TYPE = "JDBC";

	private static String GV_DRIVER = "%%" + DRIVER + "%%";
	private static String GV_URL = "%%" + URL + "%%";
	private static String GV_MAX_CONNS = "%%" + MAX_CONNS + "%%";
	private static String GV_USERID = "%%" + USERID + "%%";
	private static String GV_PWD = "%%" + PWD + "%%";
	private static String GV_TIMEOUT = "%%" + TIMEOUT + "%%";
	private static String GV_CONN_TYPE = "%%" + CONN_TYPE + "%%";
	
	private static String BWJDBC_TEMPLATE;
	private static String GLOBAL_VAR_TEMPLATE;
	
	static {
	
		BWJDBC_TEMPLATE = 
		
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<BWSharedResource>\n" + 
		"		<name>OracleJDBCConnection</name>\n" +
		"		<resourceType>ae.shared.JDBCSharedResource</resourceType>\n" +
		"		<config>\n" +
		"		        <driver>" + GV_DRIVER + "</driver>\n" +
		"				<maxConnections>" + GV_MAX_CONNS + "</maxConnections>\n" +
		"       		<loginTimeout>" + GV_TIMEOUT + "</loginTimeout>\n" +
		"       		<connectionType>" + GV_CONN_TYPE + "</connectionType>\n" +
		"		       	<UseSharedJndiConfig>false</UseSharedJndiConfig>\n" +
		"       		<location>" + GV_URL + "</location>\n" +
		"       		<user>" + GV_USERID + "</user>\n" +
		"       		<password>" + GV_PWD + "</password>\n" +
		"   	</config>\n" +
		"</BWSharedResource>\n";
	
		GLOBAL_VAR_TEMPLATE = 
		"<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n" +
		"<repository xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\" xmlns = \"http://www.tibco.com/xmlns/repo/types/2002\">\n" +
		"	<globalVariables>\n" +
		"		<globalVariable>\n" +
		"			<name>maxCons</name>\n" +
		"			<value>" + GV_MAX_CONNS + "</value>\n" +
		"			<deploymentSettable>true</deploymentSettable>\n" +
		"			<serviceSettable>true</serviceSettable>\n" +
		"			<type>String</type>\n" +
		"			<modTime>1190364818105</modTime>\n" +
		"		</globalVariable>\n" +
		"		<globalVariable>\n" +
		"			<name>timeout</name>\n" +
		"			<value>" + GV_TIMEOUT + "</value>\n" +
		"			<deploymentSettable>true</deploymentSettable>\n" +
		"			<serviceSettable>true</serviceSettable>\n" +
		"			<type>Integer</type>\n" +
		"			<modTime>1190364818105</modTime>\n" +
		"		</globalVariable>\n" +
		"		<globalVariable>\n" +
		"			<name>password</name>\n" +
		"			<value>" + GV_PWD + "</value>\n" +
		"			<deploymentSettable>true</deploymentSettable>\n" +
		"			<serviceSettable>true</serviceSettable>\n" +
		"			<type>String</type>\n" +
		"			<modTime>1190364818105</modTime>\n" +
		"		</globalVariable>\n" +
		"		<globalVariable>\n" +
		"			<name>url</name>\n" +
		"			<value>" + GV_URL + "</value>\n" +
		"			<deploymentSettable>true</deploymentSettable>\n" +
		"			<serviceSettable>true</serviceSettable>\n" +
		"			<type>String</type>\n" +
		"			<modTime>1190364818089</modTime>\n" +
		"		</globalVariable>\n" +
		"		<globalVariable>\n" +
		"			<name>userid</name>\n" +
		"			<value>" + GV_USERID + "</value>\n" +
		"			<deploymentSettable>true</deploymentSettable>\n" +
		"			<serviceSettable>true</serviceSettable>\n" +
		"			<type>String</type>\n" +
		"			<modTime>1190364818121</modTime>\n" +
		"		</globalVariable>\n" +
		"	</globalVariables>\n" +
		"</repository>\n";
	}
	
	/*private void addGlobalVars (DBDataSource dbs) throws Exception {
		
		String jdbcGlobalVarPath = "/defaultVars" + sigProps.getJdbcResourceRoot() + "/" + dbs.getName() + "/defaultVars.substvar";
		
		String globalVarsStr = GLOBAL_VAR_TEMPLATE.replaceAll(MAX_CONNS, "10");
		globalVarsStr = globalVarsStr.replaceAll(TIMEOUT, "10");
		globalVarsStr = globalVarsStr.replaceAll(PWD, dbs.getPassword());
		globalVarsStr = globalVarsStr.replaceAll(URL, dbs.getConnectionUrl());
		globalVarsStr = globalVarsStr.replaceAll(USERID, dbs.getUserId());

		globalVars.put(jdbcGlobalVarPath, globalVarsStr.getBytes());
	}*/
	
	public String getGlobalVars (DBDataSource dbs) throws Exception {
		
		//String jdbcGlobalVarPath = "/defaultVars" + resPath + "/" + resPath + "/defaultVars.substvar";
		String globalVarsStr = GLOBAL_VAR_TEMPLATE;
		globalVarsStr = globalVarsStr.replaceAll(GV_MAX_CONNS, "10");
		globalVarsStr = globalVarsStr.replaceAll(GV_TIMEOUT, "10");
		globalVarsStr = globalVarsStr.replaceAll(GV_PWD, dbs.getPassword());
		globalVarsStr = globalVarsStr.replaceAll(GV_URL, dbs.getConnectionUrl());
		globalVarsStr = globalVarsStr.replaceAll(GV_USERID, dbs.getUserId());

		//globalVars.put(jdbcGlobalVarPath, globalVarsStr.getBytes());
		return globalVarsStr;
	}
	
	public String getGlobalVars (String url, String userId, String passwd, String maxCons, String timeout) throws Exception {
		
		//String jdbcGlobalVarPath = "/defaultVars" + resPath + "/" + resPath + "/defaultVars.substvar";
		String globalVarsStr = GLOBAL_VAR_TEMPLATE;
		globalVarsStr = globalVarsStr.replaceAll(GV_MAX_CONNS, maxCons);
		globalVarsStr = globalVarsStr.replaceAll(GV_TIMEOUT, timeout);
		globalVarsStr = globalVarsStr.replaceAll(GV_PWD, passwd);
		globalVarsStr = globalVarsStr.replaceAll(GV_URL, url);
		globalVarsStr = globalVarsStr.replaceAll(GV_USERID, userId);

		//globalVars.put(jdbcGlobalVarPath, globalVarsStr.getBytes());
		return globalVarsStr;
	}
	
	public String getJDBCResource (DBDataSource dbs, String jdbcGlobalVarPath) throws Exception {
		
		//String jdbcResourceRoot = sigProps.getJdbcResourceRoot();
		//String jdbcGlobalVarPath = "%%" + jdbcResourceRoot.substring(1) + "/" + dbs.getName() + "/";
		//String jdbcResourcePath = jdbcResourceRoot + "/" + dbs.getName() + ".sharedjdbc";

		String driver = dbs.getDriver();
		String timeout = jdbcGlobalVarPath + TIMEOUT + "%%";
		String connectionType = "JDBC";
		String location = jdbcGlobalVarPath + URL + "%%";
		String userid = jdbcGlobalVarPath + USERID + "%%";
		String pwd = jdbcGlobalVarPath + PWD + "%%";
		String maxcons = jdbcGlobalVarPath + MAX_CONNS + "%%";
		
		String jdbcResourceStr = BWJDBC_TEMPLATE.replaceAll(GV_DRIVER, driver);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_TIMEOUT, timeout);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_CONN_TYPE, connectionType);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_USERID, userid);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_PWD, pwd);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_MAX_CONNS, maxcons);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_URL, location);
		
		return jdbcResourceStr;
		
		//sharedRes.put(jdbcResourcePath, jdbcResourceStr.getBytes());
	}
	
	public String getJDBCResource (String driver, String jdbcGlobalVarPath) throws Exception {
		
		//String jdbcResourceRoot = sigProps.getJdbcResourceRoot();
		//String jdbcGlobalVarPath = "%%" + jdbcResourceRoot.substring(1) + "/" + dbs.getName() + "/";
		//String jdbcResourcePath = jdbcResourceRoot + "/" + dbs.getName() + ".sharedjdbc";

		String timeout = jdbcGlobalVarPath + TIMEOUT + "%%";
		String connectionType = "JDBC";
		String location = jdbcGlobalVarPath + URL + "%%";
		String userid = jdbcGlobalVarPath + USERID + "%%";
		String pwd = jdbcGlobalVarPath + PWD + "%%";
		String maxcons = jdbcGlobalVarPath + MAX_CONNS + "%%";
		
		String jdbcResourceStr = BWJDBC_TEMPLATE.replaceAll(GV_DRIVER, driver);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_TIMEOUT, timeout);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_CONN_TYPE, connectionType);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_USERID, userid);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_PWD, pwd);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_MAX_CONNS, maxcons);
		jdbcResourceStr = jdbcResourceStr.replaceAll(GV_URL, location);
		
		return jdbcResourceStr;
		
		//sharedRes.put(jdbcResourcePath, jdbcResourceStr.getBytes());
	}
}
