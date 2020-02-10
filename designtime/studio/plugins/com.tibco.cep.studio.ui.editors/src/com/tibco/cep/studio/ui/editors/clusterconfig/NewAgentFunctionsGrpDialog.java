package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;

/*
@author ssailapp
@date Jan 15, 2010 10:27:31 PM
 */

public class NewAgentFunctionsGrpDialog extends ConfigElementSelectionDialog {

	private ArrayList<FunctionsGrp> selFunctionsGrp;
	
	public NewAgentFunctionsGrpDialog(Shell parent) {
		super(parent);
		selFunctionsGrp = new ArrayList<FunctionsGrp>();
	}
	
	public void open(ArrayList<?> functionsGrps, ArrayList<?> filterGrp) {
		initDialog("Function Groups Selector", true);
		
		for (Object functionsGrp: functionsGrps) {
			if (!filterGrp.contains(functionsGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
				item.setText(((FunctionsGrp)functionsGrp).id);
				item.setData(functionsGrp);
			}
		}
		
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selFunctionsGrp.add((FunctionsGrp)item.getData());
				}
				dialog.dispose();
			}
		});
		
		openDialog();
	}
	
	public ArrayList<FunctionsGrp> getSelectedFunctionsGrp() {
		return selFunctionsGrp;
	}
}
