package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 14, 2010 2:50:13 PM
 */

public abstract class NodeGroupsListPage extends ClusterNodeDetailsPage {

	public NodeGroupsListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		GridData gd;
		Composite propsComp = toolkit.createComposite(client);
		propsComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.heightHint = 200;
		propsComp.setLayoutData(gd);
		createSectionContents(propsComp);
		propsComp.pack();
	}
	
	protected abstract void createSectionContents(Composite parent);
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		update();
	}
	
	protected abstract void update();
}
