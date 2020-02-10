package com.tibco.cep.modules.db.model.designtime;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 29, 2005
 * Time: 11:07:52 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DBConstants {
	/** 
	 * These are used in the XML for storing corresponding values
	 */
	public String SCHEMA_NAME = "SCHEMA_NAME";
	public String OBJECT_NAME = "OBJECT_NAME";
	public String OBJECT_TYPE = "OBJECT_TYPE";
	public String PRIMARY_KEY_PROPS = "PRIMARY_KEY_PROPS";
	public String EXTID_PROPS = "EXTID_PROPS";
	public String EXTID_PREFIX = "EXTID_PREFIX";
	public String VERSION_PROP = "VERSION_PROP";
	public String VERSION_POLICY = "VERSION_POLICY";
	public String JDBC_RESOURCE = "JDBC_RESOURCE";
	
	/**
	 * These are used in the XML for storing corresponding values
	 */
	public static String COLUMN_NAME = "COLUMN_NAME";
	public static String DATA_TYPE = "DATA_TYPE";
	public static String LENGTH = "LENGTH";
	public static String PRECISION = "PRECISION";
	public static String REL_TYPE = "REL_TYPE";
	public static String REL_KEYS = "REL_KEYS";
	
	public static final String SQL_SERVER_IDENTITY_COLUMN = "identity";
	public static final String SQL_SERVER_GENERATED_KEYS_COLUMN = "GENERATED_KEYS";
	public static final String SQL_SERVER_DB_NAME = "Microsoft SQL Server";
}
