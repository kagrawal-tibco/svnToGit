package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

/*
@author ssailapp
@date Mar 2, 2010 6:38:00 PM
 */

public class PreprocessorSelectionDialog extends ConfigElementSelectionDialog {

	private String selRuleFunction;

	public PreprocessorSelectionDialog(Shell parent) {
		super(parent);
		selRuleFunction = "";
	}

	public void open(ArrayList<?> functions, ArrayList<?> filterGrp) {
		initDialog("Preprocessor Selector", false);

		for (Object function: functions) {
			if (!ifCurrentFunction((String)function, filterGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_FUNCTION));
				item.setText((String)function);
				item.setData(function);
			}
		}

		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selRuleFunction = item.getText(0);
				}
				dialog.dispose();
			}
		});

		openDialog();
	}

	private boolean ifCurrentFunction(String function, ArrayList<?> filterGrp) {
		String curFunction = (String)filterGrp.get(0);
		if (function.equals(curFunction.trim()))
			return true;
		return false;
	}
	
	public String getSelectedFunction() {
		return selRuleFunction;
	}
}
