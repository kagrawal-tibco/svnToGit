package com.tibco.cep.studio.core.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;

/**
 * 
 * @author smarathe
 *
 */

public class ImportDomainUtils {

	
	
	public static Connection getConnection(String driver, String connURL, String username, String password, JdbcSSLConnectionInfo sslConnectionInfo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Driver driverClass = null;
		driverClass = (Driver) Class.forName(driver).newInstance();
		DriverManager.registerDriver(driverClass);
		Connection connection;
		if (sslConnectionInfo == null) {
			connection = DriverManager.getConnection(connURL, username, password);
		}
		else {
			sslConnectionInfo.setUser(username);
			sslConnectionInfo.setPassword(password);
			sslConnectionInfo.loadSecurityProvider();
			connection = DriverManager.getConnection(connURL, sslConnectionInfo.getProperties());
		}
		ConnectionPool.unlockDDConnection(connection);
		connection.setAutoCommit(false);
		return connection;
	}
	
	public static Map<String, List<String>> getConnectionMetaData(HashMap<String, Object> connectionMap) {
		
		String driver = (String)connectionMap.get(Messages.getString("import.domain.databse.table.driver"));
		String username = (String)connectionMap.get(Messages.getString("import.domain.databse.table.username"));
		String password = (String)connectionMap.get(Messages.getString("import.domain.databse.table.password"));
		String connURL = (String)connectionMap.get(Messages.getString("import.domain.databse.table.url"));
		JdbcSSLConnectionInfo sslConnInfo = (JdbcSSLConnectionInfo)connectionMap.get(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO);
		
		Map<String, List<String>> metaDataMap = new HashMap<String, List<String>>();
		try {
			Connection connection = getConnection(driver, connURL, username, password, sslConnInfo);
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null, "%", null);
			while (rs.next()) {
			      String tableName = rs.getString(3);
			      ResultSetMetaData tableMetaData = rs.getMetaData();
			      int columnCount = tableMetaData.getColumnCount();
			      List<String> columnList = new ArrayList<String>();
			      for (int i = 1; i <= columnCount; i++){
			          String columnName = tableMetaData.getColumnName(i);
			          columnList.add(columnName);
			      }
			      metaDataMap.put(tableName,columnList);
			}
		} catch (Exception e) {
		}
		return metaDataMap;
			
	}
	
	public static Connection getConnectionFromMap(Map<String, Object> connectionMap) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		String driver = (String)connectionMap.get(Messages.getString("import.domain.databse.table.driver"));
		String username = (String)connectionMap.get(Messages.getString("import.domain.databse.table.username"));
		String password = (String)connectionMap.get(Messages.getString("import.domain.databse.table.password"));
		String connURL = (String)connectionMap.get(Messages.getString("import.domain.databse.table.url"));
		JdbcSSLConnectionInfo sslConnInfo = (JdbcSSLConnectionInfo)connectionMap.get(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO);
		
		return getConnection(driver, connURL, username, password, sslConnInfo );
	}
	
	public static String getDatabaseName(Map<String, Object> connectionMap) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Connection connection = getConnectionFromMap(connectionMap);
		DatabaseMetaData meta = connection.getMetaData();
		String dataBaseName = meta.getDatabaseProductName();
		return dataBaseName;
		
	}
	
	public static List<String> getTableList (Map<String, Object> connectionMap, String databaseName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<String> tableList = new ArrayList<String>();
		Connection connection = getConnectionFromMap(connectionMap);
		Statement stmt = connection.createStatement();
		ResultSet res = null;
		if(databaseName.equalsIgnoreCase("Oracle")) {
			res = stmt.executeQuery("select object_name from user_objects where object_type = 'TABLE'");
			while(res.next()) {
				tableList.add(res.getString(1));
			}
		} else {
			res = connection.getMetaData().getTables(null, null, "%", new String[] {"TABLE"});
			while(res.next()) {
				tableList.add(res.getString(3));
			}
		}
		return tableList;
	}

	public static List<String> getColumnList(Map<String, Object> connectionMap, String tableName, String domainDataType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<String> columnList = new ArrayList<String>();
		Connection connection = getConnectionFromMap(connectionMap);
		DatabaseMetaData meta = connection.getMetaData();
		String databaseName = meta.getDatabaseProductName();
		String schemaName = null;
		if("Oracle".equalsIgnoreCase(databaseName)) {
			 //In Oracle Connection.getMetaData() returns meta-data for the entire database, not just the current schema.
			 //So pass the schema name to get the meta-data for the current schema only. 
			String userName = meta.getUserName();
			if (userName != null) {
				//In Oracle schema name is same as user name.
				schemaName = userName.toUpperCase();
			}	
		}
		ResultSet res = meta.getColumns(null, schemaName, tableName, null);
	    while (res.next()) {
	    	String columnDataType = res.getString("DATA_TYPE");
	    	if ("Oracle".equalsIgnoreCase(databaseName)) {
	    		int dataPrecision = res.getInt("COLUMN_SIZE");// Precision is maximum number of digits (Including digits to the right and left of decimal).
	    		int dataScale = res.getInt("DECIMAL_DIGITS"); // Scale is the number of digits to the right of decimal.
	    		Integer oraDataType = getOracleColumnDataType(res.getString("TYPE_NAME"), dataPrecision, dataScale);
	    		if (oraDataType != null) {
	    			columnDataType = oraDataType.toString();
	    		}
	    	}
	    	List<Integer> sqlDataType = getValidSQLTypes(domainDataType);
	    	if(isValidDataType(sqlDataType, columnDataType)) {
	    		columnList.add(res.getString("COLUMN_NAME"));
	    	}
	    }
	    return columnList;
	}
	
	/**
	 * Returns a proper java.sql.Types type for the Oracle column considering columnType, precision and scale.
	 * @param columnType
	 * @param precision
	 * @param scale
	 * @return
	 */
	private static Integer getOracleColumnDataType(String columnType, int precision, int scale) {
		Integer datatype = null;
		if ("DOUBLE".equalsIgnoreCase(columnType)) {
			datatype = Types.DOUBLE;
		} else if ("INTEGER".equalsIgnoreCase(columnType)
				|| "INT".equalsIgnoreCase(columnType)) {
			datatype = Types.INTEGER;
		} else if ("NUMBER".equalsIgnoreCase(columnType)) {
			if (scale != 0) {
				datatype = Types.DOUBLE;
			} else if(precision > 0) { 
				
				//valid int number is in range -2,147,483,648 to 2,147,483,647 (10 digit number)
				//valid long number is in range -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 (19 digit number)
				
				if(precision > 18) {
					datatype = Types.DOUBLE;
				} else if(precision > 9){ 
					datatype = Types.BIGINT;
				} else {
					datatype = Types.INTEGER;
				}
			} else {
				datatype = Types.DOUBLE;
			}
		}
		return datatype;
	}
	
	public static boolean isValidDataType(List<Integer> sqlDataType, String columnDataType) {
		for(Integer dataType : sqlDataType) {
			if(dataType.toString().equalsIgnoreCase(columnDataType)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Integer> getValidSQLTypes(String domainDataType) {
		List<Integer> sqlDataTypes = new ArrayList<Integer>();
		if(domainDataType.equals("String")) {
			sqlDataTypes.add(Types.CHAR);
			sqlDataTypes.add(Types.VARCHAR);
			sqlDataTypes.add(Types.LONGVARCHAR);
		} else if(domainDataType.equals("int")) {
			sqlDataTypes.add(Types.INTEGER);
		} else if(domainDataType.equals("long")) {
			sqlDataTypes.add(Types.BIGINT);
		} else if(domainDataType.equals("double")) {
			sqlDataTypes.add(Types.FLOAT);
			sqlDataTypes.add(Types.DOUBLE);
			sqlDataTypes.add(Types.DECIMAL);
		}else if(domainDataType.equals("boolean")) {
			sqlDataTypes.add(Types.BIT);
		} else if(domainDataType.equals("DateTime")) {
			sqlDataTypes.add(Types.TIMESTAMP);
		}
		return sqlDataTypes;
	}
	
	public static List<String> getValuesList(Map<String, Object> connectionMap, String columnName, String tableName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		List<String> valuesList = new ArrayList<String>();
		Connection connection = getConnectionFromMap(connectionMap);
		String query = "Select " + columnName + " from " + tableName;
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery(query);
	    while (res.next()) {
	    	valuesList.add(res.getObject(columnName).toString());
	    }
	    return valuesList;
	}
	
	
	
}
