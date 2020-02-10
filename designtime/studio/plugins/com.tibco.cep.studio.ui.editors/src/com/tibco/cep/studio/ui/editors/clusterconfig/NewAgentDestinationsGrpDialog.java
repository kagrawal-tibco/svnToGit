package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;

/*
@author ssailapp
@date Dec 15, 2009 5:06:43 PM
 */

public class NewAgentDestinationsGrpDialog extends ConfigElementSelectionDialog {

	private ArrayList<DestinationsGrp> selDestinationsGrp;
	
	public NewAgentDestinationsGrpDialog(Shell parent) {
		super(parent);
		selDestinationsGrp = new ArrayList<DestinationsGrp>();
	}
	
	public void open(ArrayList<?> destinationsGrps, ArrayList<?> filterGrp) {
		initDialog("Input Destinations Groups Selector", true);
		
		for (Object destinationsGrp: destinationsGrps) {
			if (!filterGrp.contains(destinationsGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
				item.setText(((DestinationsGrp)destinationsGrp).id);
				item.setData(destinationsGrp);
			}
		}
		
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selDestinationsGrp.add((DestinationsGrp)item.getData());
				}
				dialog.dispose();
			}
		});

		openDialog();
	}
	
	public ArrayList<DestinationsGrp> getSelectedDestinations() {
		return selDestinationsGrp;
	}


}
