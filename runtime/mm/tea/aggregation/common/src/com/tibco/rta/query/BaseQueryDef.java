package com.tibco.rta.query;

import java.io.Serializable;
import java.util.Calendar;

/**
 * An interface that allows to build a query.
 */
public interface BaseQueryDef extends Serializable {
	
	/**
	 * Get the query name.
	 * @return
	 */
	
	String getName();
	
	/**
	 * Get the schema name.
	 * @return
	 */
	String getSchemaName();


	
	/**
	 * Get the user name.
	 * @return
	 */
	String getUserName();
	
	/**
	 * Get the batch size for query optimization.
	 * @return the batch size.
	 */
	int getBatchSize();
	
	/**
	 * Get the type of query.
	 * @return the type of query.
	 */
	QueryType getQueryType();

	/**
	 * Get the version.
	 * @return the version.
	 */
	String getVersion();
	
	/**
	 * Get the date/time of creation.
	 * @return the date/time of creation.
	 */
	Calendar getCreatedDate();
	
	/**
	 * Get the date/time of modification.
	 * @return the date/time of modification.
	 */
	Calendar getModifiedDate();
	
	/**
	 * Gets the sort order.
	 * @return the sort order.
	 */
	SortOrder getSortOrder();
	
	/**
	 * The query name to set.
	 * @param name query name to set.
	 */
	void setName(String name);
	
	/**
	 * The batch size to set for optimizations.
	 * 	@param size the batch size to set for optimizations.
 	*/
	void setBatchSize(int size);
	
	/**
	 * The type of query
	 * @param queryType the type of query.
	 */
	void setQueryType(QueryType queryType);
	
	/**
	 * Set the user name associated with this query.
	 * @param userName the user name associated with this query.
	 */
	void setUserName(String userName);
	
	/**
	 * Set the version associated with this query.
	 * @param version version associated with this query.
	 */
	void setVersion(String version);
	
	/**
	 * Set the creation date/time.
	 * @param createdDateTime the creation date/time.
	 */
	void setCreatedDate(Calendar createdDateTime);
	
	/**
	 * Set the modification date/time.
	 * @param modifiedDateTime the modification date/time.
	 */
	void setModifiedDate(Calendar modifiedDateTime);
	
	/**
	 * Sets the sort order.
	 * @param sortOrder the sort order to set.
	 */
	void setSortOrder(SortOrder sortOrder);
   
}