package com.tibco.cep.studio.ui.editors.domain.data;

import java.util.List;

/**
 * 
 * @author smarathe
 *
 */

public class DBTreeRoot {

	public DBTreeRoot() {
		
	}
	
	public DBTreeRoot(String rootName) {
		this.rootName = rootName;
	}
	
	public DBTreeRoot(String rootName, List<DBTreeTables> tableList) {
		this.rootName = rootName;
		this.tableList = tableList;
	}
	
	private List<DBTreeTables> tableList;
	String rootName;

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public List<DBTreeTables> getTableList() {
		return tableList;
	}

	public void setTableList(List<DBTreeTables> tableList) {
		this.tableList = tableList;
	}
	
}
