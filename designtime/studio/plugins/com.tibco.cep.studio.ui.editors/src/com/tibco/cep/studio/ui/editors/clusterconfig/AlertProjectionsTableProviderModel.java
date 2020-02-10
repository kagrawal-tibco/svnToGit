package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList.AlertConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.TableProviderModel;

/*
@author ssailapp
@date Apr 29, 2010 10:57:20 AM
 */

public class AlertProjectionsTableProviderModel implements TableProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private AlertConfig alertConfig;

	public AlertProjectionsTableProviderModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	public void setAlertConfig(AlertConfig alertConfig) {
		this.alertConfig = alertConfig;
	}
	
	@Override
	public void addItem(Table table) {
		if (alertConfig != null) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "");
			item.setText(1, "");
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
