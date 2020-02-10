package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;


/*
@author ssailapp
@date Nov 28, 2009 1:45:08 PM
 */

public class ListProviderUi {

	private Composite parent;
	private Composite listParent;
	private Composite listComp;
	private Composite configParent;	// Parent container for config
	private Composite configComp;	// container for config (created by listmodel client)
	private String items[];	//TODO: Remove this 
	private ToolBarProvider toolBarProvider;
	private List list;
	private ListProviderModel listmodel = null;
	
	public ListProviderUi(Composite parent, String items[], ListProviderModel listmodel) {
		this.parent = parent;
		this.items = items;
		this.listmodel = listmodel;
	}
	
	public Composite getParent() {
		return listParent;
	}
	
	public Composite getConfigurationParent() {
		return configParent;
	}
	
	public void setItems(String items[]) {
		if (items!=null && items.length >= 0) {
			list.setItems(items);
			list.setSelection(0);
			this.items = items;
		}
	}

	public Composite createListPanel() {
		return (createListPanel(false, true));
	}
		
	public Composite createListPanel(boolean isListOnly) {
		return (createListPanel(isListOnly, true));
	}
	
	public Composite createListPanel(boolean isListOnly, boolean hasToolBar) {
		listParent = new Composite(parent, SWT.NONE);
		listParent.setLayoutData(new GridData(GridData.FILL_BOTH));
        listParent.setLayout(PanelUiUtil.getCompactGridLayout(1, true));
		
        if (hasToolBar)
        	initializeToolBar();
		
		listComp = new Composite(listParent, SWT.NONE);
        if (isListOnly) {
        	listComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
        } else {
        	listComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
        }
		listComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		list = new List(listComp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 50;
		list.setLayoutData(gd);
		setItems(items);
		list.addListener(SWT.Selection, getListSelectionListener(this));
		
		if (!isListOnly) {
			configParent = new Composite(listComp, SWT.NONE);
			configParent.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
			configParent.setLayoutData(new GridData(GridData.FILL_BOTH));
			if (listmodel != null) {
				configComp = listmodel.createConfigPanel(this);
				/*
				if (configComp != null)
					configComp.setBackground(new Color(null, 225, 225, 250));
				*/	
			}
			configParent.pack();
		}
		listComp.pack();
		listParent.pack();		
		return listParent;
	}

	private void initializeToolBar() {
		toolBarProvider = new ToolBarProvider(listParent);
		ToolBar toolBar = toolBarProvider.createToolbar(false, false);

		Listener addItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				listmodel.addItem(getList());
				list.setSelection(list.getItemCount()-1);
				list.notifyListeners(SWT.Selection, new Event());
			}
		};
		toolBarProvider.setAddItemListener(addItemListener);
		
		Listener delItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int index = list.getSelectionIndex();
				listmodel.deleteItem(getList(), getSelectedItem());
				if (list.getItemCount() > index)
					list.setSelection(index);
				else if (list.getItemCount() == index)
					list.setSelection(index-1);
				else if (list.getItemCount() > 0)
					list.setSelection(0);
				list.notifyListeners(SWT.Selection, new Event());
			}
		};
		toolBarProvider.setDelItemListener(delItemListener);
	}
	
	private Listener getListSelectionListener(final ListProviderUi listProvider) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				listmodel.onListItemSelectionChanged(getSelectedItem());
			}
		};
		return listener;
	}

	public List getList() {
		return list;
	}

	public String getSelectedItem() {
		if (list.getItemCount()==0) {
			return ("");
		}
		if (list.getSelectionIndex() != -1) {
			return (list.getSelection()[0]);
		}
		return list.getItem(0);
	}
	
	public ListProviderModel getListModel() {
		return listmodel;
	}

	public void updateItem(String oldName, String newName) {
		PanelUiUtil.replaceListItem(list, oldName, newName);
		/*
		String items[] = list.getItems();
		for (int i=0; i<items.length; i++) {
			if (items[i].equalsIgnoreCase(oldName)) {
				items[i] = newName;
				setItems(items);
				list.setSelection(i);
				break;
			}
		}
		*/
	}
}
