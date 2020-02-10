package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.TableProviderModel;

/*
@author ssailapp
@date Jan 18, 2011
 */

public class DomainObjectPropertiesProviderModel implements TableProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private DomainObject domainObject;

	public DomainObjectPropertiesProviderModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}
	
	@Override
	public void addItem(Table table) {
		if (domainObject != null) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "");
			item.setText(1, "");
			item.setText(2, "");
			item.setText(3, "");
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
