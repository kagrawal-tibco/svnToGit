/**
 * 
 */
package com.tibco.cep.store.as;

import com.tibco.cep.store.Item;
import com.tibco.cep.store.StoreIterator;
import com.tibco.datagrid.DataGridException;
import com.tibco.datagrid.ResultSet;
import com.tibco.datagrid.Row;
import com.tibco.datagrid.Statement;
import com.tibco.datagrid.TableMetadata;

/**
 * @author vpatil
 *
 */
public class ASIterator extends StoreIterator {

	private ASContainer datagridContainer;
	private Statement statement;
	private ResultSet resultSet;
	
	public ASIterator(ResultSet resultSet, String returnEntityPath, Statement statement) {
		super(resultSet.iterator(), returnEntityPath);
		this.statement = statement;
		this.resultSet = resultSet;
	}
	
	@Override
	protected Item createItem(Object result) throws Exception {
		ASItem dataGridItem = new ASItem();
		dataGridItem.setRow((Row)result);
		dataGridItem.setContainer(datagridContainer);
		
		return dataGridItem;
	}
	
	public void setContainer(String containerName, TableMetadata tableMetadata) {
		datagridContainer = new ASContainer(containerName);
		datagridContainer.setTableMetadata(tableMetadata);
	}
	
	@Override
	public void cleanup() throws Exception {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (DataGridException dge) {}
			resultSet = null;
		}
		if (statement != null)  {
			try {
				statement.close();
			} catch (DataGridException dge) {}
			statement = null;
		}
		datagridContainer = null;
	}
}
