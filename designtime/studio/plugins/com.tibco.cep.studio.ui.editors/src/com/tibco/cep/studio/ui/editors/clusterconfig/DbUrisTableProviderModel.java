package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderModel;

/*
@author ssailapp
@date Apr 28, 2010 11:17:11 AM
 */

public class DbUrisTableProviderModel implements TableProviderModel {

	private ClusterConfigModelMgr modelmgr;

	public DbUrisTableProviderModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	@Override
	public void addItem(Table table) {
		TableItem item = new TableItem(table, SWT.NONE);
		String name = PanelUiUtil.getFileResourceSelectionListener(table.getParent(), modelmgr.project, new String[]{"sharedjdbc"});
		if(name!=null){
			item.setText(0, name);
		}
		updateModel(table);
	}
	
	@Override
	public void deleteItem(Table table, int index) {
		table.remove(index);
		updateModel(table);
	}

	@Override
	public void moveDownItem(Table table, int index) {
	}

	@Override
	public void moveUpItem(Table table, int index) {
	}

	private void updateModel(Table table) {
		table.notifyListeners(SWT.Modify, new Event());
	}
}

