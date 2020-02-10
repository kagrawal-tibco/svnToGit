package com.tibco.cep.studio.ui.editors.globalvar;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesGroup;
import com.tibco.cep.studio.ui.editors.utils.NodeAbstractDetailsPage;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 6, 2010 3:40:27 PM
 */

public class GlobalVariablesGroupPage extends NodeAbstractDetailsPage {
	
	private GlobalVariablesModelMgr modelmgr;
	private Text tName;
	private GlobalVariablesGroup gvGrp;
	
	public GlobalVariablesGroupPage(GlobalVariablesModelMgr modelmgr, TreeViewer viewer) {
		super(viewer);
		this.modelmgr = modelmgr;
	}

	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		GridData gd;
		Composite propsComp = toolkit.createComposite(client);
		propsComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		propsComp.setLayoutData(gd);
		createSectionContents(propsComp);
		propsComp.pack();
	}
	
	private void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(2, false));
		
		tName = createConfigTextField(comp, "Group Name: ", GlobalVariablesModel.FIELD_NAME);
	
		comp.pack();
		parent.pack();
	}
	
	private Text createConfigTextField(Composite comp, String label, String fieldId) {
		PanelUiUtil.createLabel(comp, label);
		Text tField = PanelUiUtil.createText(comp);
		if (gvGrp != null)
			tField.setText(gvGrp.name);
		tField.addListener(SWT.Modify, getListener(tField, fieldId));
		return tField;
	}
	
	private Listener getListener(final Text field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = field.getText();
				boolean updated = modelmgr.updateGroupName(gvGrp, newName);
				if (updated)
					viewer.refresh(gvGrp);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			gvGrp = ((GlobalVariablesGroup) ssel.getFirstElement());
		else
			gvGrp = null;
		update();
	}

	private void update() {
		if (gvGrp == null)
			return;
		tName.setText(gvGrp.name);
		
		if (gvGrp.isProjectLib()) {
			tName.setEnabled(false);
		} else {
			tName.setEnabled(true);
		}
	}
}
