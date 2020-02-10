package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;

/*
@author ssailapp
@date Feb 18, 2010 3:25:23 PM
 */

public class ProcUnitLogConfigSelectionDialog extends ConfigElementSelectionDialog {

	private String selLogConfig;

	public ProcUnitLogConfigSelectionDialog(Shell parent) {
		super(parent);
		selLogConfig = "";
	}

	public void open(ArrayList<?> logConfigs, ArrayList<?> filterGrp) {
		initDialog("Log Configuration Selector", false);

		for (Object logConfig: logConfigs) {
			if (!ifCurrentLogConfig(((LogConfig)logConfig).id, filterGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
				item.setText(((LogConfig)logConfig).id);
				item.setData(logConfig);
			}
		}

		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selLogConfig = item.getText(0);
				}
				dialog.dispose();
			}
		});

		openDialog();
	}

	private boolean ifCurrentLogConfig(String logConfigName, ArrayList<?> filterGrp) {
		String curConfig = (String)filterGrp.get(0);
		if (logConfigName.equals(curConfig.trim()))
			return true;
		return false;
	}
	
	public String getSelectedLogConfig() {
		return selLogConfig;
	}
}
