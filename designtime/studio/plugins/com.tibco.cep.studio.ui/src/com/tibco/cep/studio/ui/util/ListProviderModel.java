package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

/*
@author ssailapp
@date Nov 30, 2009 6:10:03 PM
 */

public interface ListProviderModel {

	public void addItem(List list);
	public void deleteItem(List list, String item);
	
	public Composite createConfigPanel(ListProviderUi listProvider);
	public void onListItemSelectionChanged(String item);
	
}
