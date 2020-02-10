package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date Dec 11, 2009 11:20:19 AM
 */

public class NodeCacheAgentPage extends NodeAgentAbstractPage {

	public NodeCacheAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
 	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		createPropertiesGroup();
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
	}
}