package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.TableProviderModel;

/*
@author ssailapp
@date Feb 19, 2010 6:24:26 PM
 */

public class ProcUnitAgentsTableProviderModel implements TableProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private ProcessingUnit procUnit;

	public ProcUnitAgentsTableProviderModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	public void setProcessingUnit(ProcessingUnit procUnit) {
		this.procUnit = procUnit;
	}
	
	@Override
	public void addItem(Table table) {
		if (procUnit != null) {
			ProcUnitAgentSelectionDialog dialog = new ProcUnitAgentSelectionDialog(table.getShell(), modelmgr);
			dialog.open(modelmgr.getAgentClasses(), procUnit.agentClasses);
			ArrayList<AgentClass> selAgentClass = dialog.getSelectedAgentClass();
			for (AgentClass agentClass: selAgentClass) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, agentClass.name);
			}
			if (selAgentClass.size() > 0) {
				updateModel(table);
			}
		}
	}
	
	@Override
	public void deleteItem(Table table, int index) {
		table.remove(index);
		updateModel(table);
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

		updateModel(table);	
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
		
		updateModel(table);
	}

	private void updateModel(Table table) {
		table.notifyListeners(SWT.Modify, new Event());
	}
}
