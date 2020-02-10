package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 29, 2010 12:03:48 AM
 */

public class NodeClusterMemberPage extends NodeGroupsListPage {

	private ClusterMember clusterMember;
	private ListProviderUi clusterMemberListUi;
	private GroupsListModel listmodel;
	private Text tId, tPath;
	
	public NodeClusterMemberPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {

		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(2, false));
		
		PanelUiUtil.createLabel(comp, "Cluster Member ID: ");
		tId = PanelUiUtil.createText(comp);
		tId.addListener(SWT.Modify, getIdModifyListener());
		
		PanelUiUtil.createLabel(comp, "Path: ");
		tPath = PanelUiUtil.createText(comp);
		tPath.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateClusterMemberPath(clusterMember, tPath.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		});
		
		Composite comp2 = new Composite(comp, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp2.setLayoutData(gd);
		comp2.setLayout(new GridLayout(1, false));

		PanelUiUtil.createLabel(comp2, "Cluster Members: ");
		listmodel = new GroupsListModel(modelmgr, viewer);
		clusterMemberListUi = new ListProviderUi(comp2, null, listmodel); 
		Composite listComp = clusterMemberListUi.createListPanel(true, false);
		
		comp2.pack();
		comp.pack();
		parent.pack();
	}
	
	private Listener getIdModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateClusterMemberId(clusterMember, tId.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1) { 
			Object selObj = ssel.getFirstElement();
			if (selObj instanceof ClusterMember) {
				clusterMember = (ClusterMember) selObj;
			} else {
				clusterMember = null;
			}
		}
		update();
	}

	protected void update() {
		if (clusterMember == null)
			return;
		tId.setText(clusterMember.id);
		String names[] = modelmgr.getClusterMemberIds(clusterMember).toArray(new String[0]);
		clusterMemberListUi.setItems(names);
		tPath.setText(clusterMember.path);
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
