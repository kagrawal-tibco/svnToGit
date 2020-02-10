/**
 * 
 */
package com.tibco.cep.store.as;

import com.tibco.cep.store.StoreMetadata;
import com.tibco.datagrid.GridMetadata;
import com.tibco.datagrid.TableMetadata;

/**
 * @author vpatil
 *
 */
public class ASMetadata implements StoreMetadata {
	
	private GridMetadata gridMetadata;
	
	public ASMetadata(GridMetadata gridMetadata) {
		this.gridMetadata = gridMetadata;
	}

	@Override
	public String getVersion() throws Exception {
		return this.gridMetadata.getVersion();
	}

	@Override
	public String[] getContainerNames() throws Exception {
		return this.gridMetadata.getTableNames();
	}

	@Override
	public String getName() throws Exception {
		return this.gridMetadata.getGridName();
	}

	@Override
	public String[] getContainerFieldNames(String containerName) throws Exception {
		return getTableMetadata(containerName).getColumnNames();
	}

	@Override
	public String getContainerFieldType(String containerName, String fieldName) throws Exception {
		return getTableMetadata(containerName).getColumnType(fieldName).toString();
	}

	@Override
	public String getContainerPrimaryIndex(String containerName) throws Exception {
		return getTableMetadata(containerName).getPrimaryIndexName();
	}

	@Override
	public String[] getContainerIndexNames(String containerName) throws Exception {
		return getTableMetadata(containerName).getIndexNames();
	}

	@Override
	public String[] getContainerIndexFieldNames(String containerName, String indexName) throws Exception {
		return getTableMetadata(containerName).getIndexColumnNames(indexName);
	}
	
	public TableMetadata getTableMetadata(String containerName) throws Exception {
		TableMetadata tableMetadata = this.gridMetadata.getTableMetadata(containerName);
		if (tableMetadata == null) throw new Exception(String.format("No such container[%s] found. Please check the container name", containerName));
		return tableMetadata;
	}
	
	public void destroy() throws Exception {
		if (this.gridMetadata != null) {
			this.gridMetadata.destroy();
			this.gridMetadata = null;
		}
	}
}
