package com.tibco.cep.studio.dbconcept.conceptgen;

import java.util.Set;

/**
 * 
 * @author bgokhale
 * Defines a database entity to the framework.
 *
 */
public interface DBEntity extends BaseEntity {

	 /**
	  * Types of database objects
	  */
    public static final int TABLE = 0;
    public static final int VIEW = 1;

    /**
     * 
     * @return Name of the database (MySql and SQLServer -> database name, for oracle -> schema name)
     */
    public String getDBName();

    /**
     * 
     * @return Name of the database version like "10.2.0"
     */
    public String getDBVersion();

    /**
     * 
     * @return Name of the schema to which this entity belongs
     */
    public String getSchema();

    /**
     * 
     * @return Type of entity TABLE or VIEW
     */
    public int getEntityType();

    /**
     * 
     * @return Name of the underlying database table or view
     */
    public String getObjName();
    
    /**
     * 
     * @return Primary key as list of columns in the key
     */
    public Set<?> getPK();
    
    /**
     * 
     * @return Return a string URI that points to a JDBC resource
     */
    public String getJdbcResourceURI();
    
}
