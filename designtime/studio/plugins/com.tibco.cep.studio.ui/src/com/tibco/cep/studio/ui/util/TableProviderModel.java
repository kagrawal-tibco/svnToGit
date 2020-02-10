package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.widgets.Table;

/*
@author ssailapp
@date Jan 22, 2010 12:13:39 AM
 */

public interface TableProviderModel {
	//public boolean hasToolbar();
	//public boolean hasOrdering();
	//public boolean hasCheck();
	
	public void addItem(Table table);
	public void deleteItem(Table table, int index);
	public void moveUpItem(Table table, int index);
	public void moveDownItem(Table table, int index);
}
