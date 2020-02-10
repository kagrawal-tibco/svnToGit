/**
 * 
 */
package com.tibco.cep.store.as;

import com.tibco.cep.store.StoreContainer;
import com.tibco.datagrid.Row;
import com.tibco.datagrid.Table;
import com.tibco.datagrid.TableMetadata;

/**
 * @author vpatil
 *
 */
public class ASContainer extends StoreContainer<ASItem> {
	private Table table;
	private TableMetadata tableMetadata;
	
	public ASContainer(String name) {
		super(name);
	}
	
	@Override
	public ASItem createItem() throws Exception {
		ASItem dgItem = new ASItem();
		dgItem.create(this);
		return dgItem;
	}
	
	@Override
	public void putItem(ASItem item) throws Exception {
		if (table != null) {
			table.put(item.getRow());
		}
	}

	@Override
	public ASItem getItem(ASItem item) throws Exception {
		ASItem dataGridItem = null;
		if (table != null) {
			Row resultRow = table.get(item.getRow());
			if (resultRow != null) {
				dataGridItem = new ASItem();
				dataGridItem.setRow(resultRow);
				dataGridItem.setContainer(this);
				
				return dataGridItem;
			}			
		}
		return dataGridItem;
	}

	@Override
	public void deleteItem(ASItem item) throws Exception {
		if (table != null) {
			table.delete(item.getRow());
		}
	}
	
	@Override
	public void close() throws Exception {
		if (table != null) {
			this.table.close();
		}
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	
	public Table getTable() {
		return this.table;
	}

	public TableMetadata getTableMetadata() {
		return tableMetadata;
	}

	public void setTableMetadata(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
	}
	
	@Override
	protected String[] getPrimaryKeyNames() throws Exception {
		return tableMetadata.getIndexColumnNames(tableMetadata.getPrimaryIndexName());
	}
}
