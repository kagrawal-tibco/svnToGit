package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTablePersistenceManager.getInstance;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableDataItem implements IRequestDataItem {
	
	private Table table; 
	private DecisionTableUpdateProvider dtupdateprovider;
	private boolean isSyncMerge = false;
	boolean incremental;
	
	/**
	 * @param table
	 * @param dtupdateprovider
	 * @param incremental
	 */
	public DecisionTableDataItem(Table table, 
								 DecisionTableUpdateProvider dtupdateprovider, 
								 boolean incremental,
								 boolean isSyncMerge) {
		this.table = table;
		this.dtupdateprovider = dtupdateprovider;
		this.incremental = incremental;
		this.isSyncMerge = isSyncMerge;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.ISerializableObject#serialize(com.google.gwt.xml.client.Document, com.google.gwt.xml.client.Node)
	 */
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		getInstance(rootDocument, rootNode, table, dtupdateprovider, incremental, this.isSyncMerge);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public DecisionTableUpdateProvider getDtupdateprovider() {
		return dtupdateprovider;
	}

	public void setDtupdateprovider(DecisionTableUpdateProvider dtupdateprovider) {
		this.dtupdateprovider = dtupdateprovider;
	}

	public boolean isIncremental() {
		return incremental;
	}

	public void setIncremental(boolean incremental) {
		this.incremental = incremental;
	}

	public boolean isSyncMerge() {
		return isSyncMerge;
	}

	public void setSyncMerge(boolean isSyncMerge) {
		this.isSyncMerge = isSyncMerge;
	}
	
	
}