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

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Nov 4, 2010 12:03:48 AM
 */


public class NodeProcessesGrpPage extends NodeGroupsListPage {

	private ProcessesGrp processesGrp;
	private ListProviderUi processesGrpListUi;
	private GroupsListModel listmodel;
	private Text tProcessesGrp;
	
	public NodeProcessesGrpPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));

		Composite nameComp = new Composite(comp, SWT.NONE);
		nameComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		nameComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		PanelUiUtil.createLabel(nameComp, "Process Definition: ");
		tProcessesGrp = PanelUiUtil.createText(nameComp);
		tProcessesGrp.addListener(SWT.Modify, getNameModifyListener());
		
		listmodel = new GroupsListModel(modelmgr, viewer);
		processesGrpListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = processesGrpListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	private Listener getNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = tProcessesGrp.getText();
				String updated = modelmgr.updateProcessesGroupName(processesGrp, newName);
				if (updated.equals("true"))
					BlockUtil.refreshViewerForError(viewer,0,tProcessesGrp);
				if (updated.equals("trueError")){
					BlockUtil.refreshViewerForError(viewer,1,tProcessesGrp);
				}
			}
		};
		return listener;
	}	
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		tProcessesGrp.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		if (ssel.size() == 1) { 
			Object selObj = ssel.getFirstElement();
			if (selObj instanceof ProcessesGrp) {
				processesGrp = (ProcessesGrp) selObj;
			} else if (selObj instanceof AgentProcessesGrpElement) {
				processesGrp = ((AgentProcessesGrpElement)selObj).processesGrp;
				tProcessesGrp.setEnabled(false);
			}
		} else {
			processesGrp = null;
		}
		update();
	}

	protected void update() {
		if (processesGrp == null)
			return;
		String processes[] = modelmgr.getProcessNames(processesGrp, false).toArray(new String[0]);
		processesGrpListUi.setItems(processes);
		tProcessesGrp.setText(processesGrp.id);
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