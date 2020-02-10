package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 23, 2010 1:19:07 PM
 */

public class NodePropertyElementListPage extends ClusterNodeDetailsPage {
	
	private Tree trProps;
	private TreeProviderUi trPropsProviderUi;
	
	public NodePropertyElementListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		Composite grProps = new Composite(client, SWT.NONE);
		grProps.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 500;
		gd.horizontalSpan = 2;
		grProps.setLayoutData(gd);
		//grProps.setText("Properties");
		
		PropertyTreeProviderModel treeModel = new PropertyTreeProviderModel(modelmgr);
		trPropsProviderUi = new TreeProviderUi(grProps, treeModel.columns, true, treeModel, treeModel.keys);
		trProps = trPropsProviderUi.createTree();
	    trPropsProviderUi.setTreeData(modelmgr.getPropertyElementList());
        trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 0));
	    trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 1));
        //trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 2, 0, TreeProviderUi.TYPE_COMBO, treeModel.types));	    
	    trProps.addListener(SWT.Modify, treeModel.getTreeModifyListener(trProps));
	    
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		update();
	}
	
	private void update() {
	    trPropsProviderUi.setTreeData(modelmgr.getPropertyElementList());
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