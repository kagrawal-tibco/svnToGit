package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

/*
@author vbarot
*/

public class LoadBalancerChannelSelectionDialog extends ConfigElementSelectionDialog {

	private String selDestination;

	public LoadBalancerChannelSelectionDialog(Shell parent) {
		super(parent);
		selDestination = "";
	}

	public void open(ArrayList<?> functions, ArrayList<?> filterGrp) {
		initDialog("Destination Selector", false);

		for (Object function: functions) {
			if (!ifCurrentFunction((String)function, filterGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DESTINATION));
				item.setText((String)function);
				item.setData(function);
			}
		}

		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selDestination = item.getText(0);
				}
				dialog.dispose();
			}
		});

		openDialog();
	}

	private boolean ifCurrentFunction(String function, ArrayList<?> filterGrp) {
		//try{
		if(filterGrp.size()>0){
		String curFunction = (String)filterGrp.get(0);
		if (function.equals(curFunction.trim()))
			return true;
		else
			return false;
		}
		else{
		return false;
		}
		//}
		//catch(Exception e){
		//	System.out.println(e.getMessage());
		//	return false;
		//}
	}
	
	public String getSelectedDestination() {
		return selDestination;
	}
}
