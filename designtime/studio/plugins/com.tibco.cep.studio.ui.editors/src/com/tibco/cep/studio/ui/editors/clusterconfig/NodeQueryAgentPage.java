package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date Dec 11, 2009 11:20:31 AM
 */

public class NodeQueryAgentPage extends NodeInfQueryAgentPage {

	private QueryAgent queryAgent;
	
	public NodeQueryAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		/*
		Composite queryComp = new Composite(client, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		queryComp.setLayoutData(gd);
		queryComp.setLayout(new GridLayout(2, false));
		queryComp.pack();
		*/
		
		createPropertiesGroup();
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		queryAgent = (QueryAgent) infQueryAgent;
	}
}
