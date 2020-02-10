/**
 * 
 */
package com.tibco.cep.store;

/**
 * @author vpatil
 *
 */
public interface StoreMetadata {
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	String getVersion() throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	String[] getContainerNames() throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	String getName() throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @return
	 * @throws Exception
	 */
	String[] getContainerFieldNames(String containerName) throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	String getContainerFieldType(String containerName, String fieldName) throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @return
	 * @throws Exception
	 */
	String getContainerPrimaryIndex(String containerName) throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @return
	 * @throws Exception
	 */
	String[] getContainerIndexNames(String containerName) throws Exception;
	
	/**
	 * 
	 * @param containerName
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	String[] getContainerIndexFieldNames(String containerName, String indexName) throws Exception;
	
}
