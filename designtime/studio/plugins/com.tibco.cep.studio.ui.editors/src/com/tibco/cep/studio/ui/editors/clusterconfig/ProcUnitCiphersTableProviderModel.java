package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.ui.util.TableProviderModel;

/*
@author ssailapp
@date Apr 22, 2010 2:51:58 PM
 */

public class ProcUnitCiphersTableProviderModel implements TableProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private ProcessingUnit procUnit;

	public ProcUnitCiphersTableProviderModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	public void setProcessingUnit(ProcessingUnit procUnit) {
		this.procUnit = procUnit;
	}
	
	@Override
	public void addItem(Table table) {
		if (procUnit != null) {
			TableItem item = new TableItem(table, SWT.NONE);
			String name = IdUtil.generateSequenceId("cipher", procUnit.httpProperties.ssl.ciphers);
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
