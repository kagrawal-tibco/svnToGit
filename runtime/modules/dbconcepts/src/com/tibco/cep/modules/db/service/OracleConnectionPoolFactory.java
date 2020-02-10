/**
 * 
 */
package com.tibco.cep.modules.db.service;


/**
 * @author vpatil
 *
 */
public class OracleConnectionPoolFactory {
	
	public static OracleConnectionPool getConnectionPool(String key, OracleConnectionInfo oracleConnectionInfo) throws Exception {
		OracleConnectionPool oracleConnectionPool = null;

		if (oracleConnectionInfo.isOracle12Driver()) {
			oracleConnectionPool = new Oracle12vConnectionPool(key, oracleConnectionInfo);
		} else {
			oracleConnectionPool = new OraclePre12vConnectionPool(key, oracleConnectionInfo);
		}
		
		return oracleConnectionPool;
	}

}
