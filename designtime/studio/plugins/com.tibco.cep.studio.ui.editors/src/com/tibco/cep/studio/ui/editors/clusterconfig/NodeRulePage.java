package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 16, 2009 2:58:58 PM
 */

public class NodeRulePage extends ClusterNodeDetailsPage {

	private Label lName;
	private Text tName;
	private Rule rule; 
	
	public NodeRulePage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		lName = PanelUiUtil.createLabel(client, "URI: ");
		tName = PanelUiUtil.createText(client);
		tName.addListener(SWT.Modify, getNameModifyListener());
		
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Listener getNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = tName.getText();
				if (!newName.equalsIgnoreCase(rule.uri)) {
					boolean updated = modelmgr.updateRuleName(rule, newName);
					if (updated)
						BlockUtil.refreshViewer(viewer);
				}
			}
		};
		return listener;
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			rule = ((Rule) ssel.getFirstElement());
		else
			rule = null;
		update();
	}
	
	private void update() {
		if (rule != null) {
			lName.setText(rule.isRef? "Ref: " : "URI: ");
			tName.setText(rule.uri);
			tName.setEnabled(!rule.isRef);
		}
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
