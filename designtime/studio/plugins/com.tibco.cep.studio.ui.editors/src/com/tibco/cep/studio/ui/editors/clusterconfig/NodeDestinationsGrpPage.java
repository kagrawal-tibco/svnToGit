package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 16, 2009 4:01:00 PM
 */

public class NodeDestinationsGrpPage extends NodeGroupsListPage {

	private DestinationsGrp destinationsGrp;
	private ListProviderUi destinationsGrpListUi;
	private GroupsListModel listmodel;
	private Text tDestinationsGrp;
	
	public NodeDestinationsGrpPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));

		Composite nameComp = new Composite(comp, SWT.NONE);
		nameComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		nameComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		PanelUiUtil.createLabel(nameComp, "Input Destinations Collection: ");
		tDestinationsGrp = PanelUiUtil.createText(nameComp);
		tDestinationsGrp.addListener(SWT.Modify, getNameModifyListener());
		
		listmodel = new GroupsListModel(modelmgr, viewer);
		destinationsGrpListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = destinationsGrpListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}

	private Listener getNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = tDestinationsGrp.getText();
				String updated = modelmgr.updateDestinationsGroupName(destinationsGrp, newName);
				if (updated.equals("true"))
					BlockUtil.refreshViewerForError(viewer,0,tDestinationsGrp);
				if (updated.equals("trueError")){
					BlockUtil.refreshViewerForError(viewer,1,tDestinationsGrp);
				}
			}
		};
		return listener;
	}	
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		tDestinationsGrp.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		if (ssel.size() == 1) { 
			Object selObj = ssel.getFirstElement();
			if (selObj instanceof DestinationsGrp) {
				destinationsGrp = (DestinationsGrp) selObj;
			} else if (selObj instanceof AgentDestinationsGrpElement) {
				destinationsGrp = ((AgentDestinationsGrpElement)selObj).destinationsGrp;
				tDestinationsGrp.setEnabled(false);
			}
		} else {
			destinationsGrp = null;
		}
		update();
	}

	protected void update() {
		if (destinationsGrp == null)
			return;
		String destinations[] = modelmgr.getDestinationNames(destinationsGrp, false).toArray(new String[0]);
		destinationsGrpListUi.setItems(destinations);
		tDestinationsGrp.setText(destinationsGrp.id);
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
