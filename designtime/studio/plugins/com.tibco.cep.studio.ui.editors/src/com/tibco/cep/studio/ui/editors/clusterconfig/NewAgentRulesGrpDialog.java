package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;

/*
@author ssailapp
@date Dec 15, 2009 12:17:57 AM
 */

public class NewAgentRulesGrpDialog extends ConfigElementSelectionDialog {

	private ArrayList<RulesGrp> selRulesGrp;
	
	public NewAgentRulesGrpDialog(Shell parent) {
		super(parent);
		selRulesGrp = new ArrayList<RulesGrp>();
	}
	
	public void open(ArrayList<?> rulesGrps, ArrayList<?> filterGrp) {
		initDialog("Rule Groups Selector", true);
		
		for (Object rulesGrp: rulesGrps) {
			if (!filterGrp.contains(rulesGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_RULES));
				item.setText(((RulesGrp)rulesGrp).id);
				item.setData(rulesGrp);
			}
		}
		
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selRulesGrp.add((RulesGrp)item.getData());
				}
				dialog.dispose();
			}
		});

		openDialog();
	}
	
	public ArrayList<RulesGrp> getSelectedRulesGrp() {
		return selRulesGrp;
	}

}
