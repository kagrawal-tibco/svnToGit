package com.tibco.cep.sharedresource.jndi;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderModel;

/*
@author ssailapp
@date Jan 22, 2010 1:46:47 PM
 */

public class JndiPropsTableProviderModel implements TableProviderModel {
	
    public String types[] = new String[] { "boolean", "byte", "char", "double", "float", "int", "long", "short", "string" };
	
	public JndiPropsTableProviderModel() {
	}
	
	@Override
	public void addItem(Table table) {
		ArrayList<String> curNames = getPropertyNames(table);
		String newName = PanelUiUtil.generateSequenceId("property", curNames);
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(0, newName);
		item.setText(1, "string");
		item.setText(2, "");
		updateModelProperties(table);
	}
	
	@Override
	public void deleteItem(Table table, int index) {
		table.remove(index);
		updateModelProperties(table);
	}

	@Override
	public void moveDownItem(Table table, int index) {
		if (index == -1)
			return;

		String prop = table.getItem(index).getText(0);
		String type = table.getItem(index).getText(1);
		String cardinality = table.getItem(index).getText(2);
		table.remove(index);
		
		TableItem item = new TableItem(table, SWT.NONE, index+1);
		item.setText(0, prop);
		item.setText(1, type);
		item.setText(2, cardinality);

		updateModelProperties(table);	
	}

	@Override
	public void moveUpItem(Table table, int index) {
		if (index == -1)
			return;
		String prop = table.getItem(index).getText(0);
		String type = table.getItem(index).getText(1);
		String cardinality = table.getItem(index).getText(2);
		table.remove(index);
		
		TableItem item = new TableItem(table, SWT.NONE, index-1);
		item.setText(0, prop);
		item.setText(1, type);
		item.setText(2, cardinality);
		
		updateModelProperties(table);
	}

	private ArrayList<String> getPropertyNames(Table table) {
		ArrayList<String> names = new ArrayList<String>();
		for (TableItem item: table.getItems()) { 
			names.add(item.getText(0));
		}
		return names;
	}
	
	private void updateModelProperties(Table table) {
		table.notifyListeners(SWT.Modify, new Event());
	}
	
}
