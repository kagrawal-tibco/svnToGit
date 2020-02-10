package com.tibco.cep.studio.ui.editors.globalvar;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesDescriptor;
import com.tibco.cep.studio.ui.editors.utils.NodeAbstractDetailsPage;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 6, 2010 3:40:18 PM
 */

public class GlobalVariablesDescriptorPage extends NodeAbstractDetailsPage {
	
	private GlobalVariablesModelMgr modelmgr;
	private Text tName, tValue, tDesc, tConstraint, tModTime;
	private Button bDeploy, bService;
	private Combo cType;
	private GlobalVariablesDescriptor gvDesc;
	
	public GlobalVariablesDescriptorPage(GlobalVariablesModelMgr modelmgr, TreeViewer viewer) {
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
		
		tName = createConfigTextField(comp, "Name: ", GlobalVariablesModel.FIELD_NAME);
		tValue = createConfigTextField(comp, "Value: ", GlobalVariablesModel.FIELD_VALUE);

		String gvTypes[] = { GlobalVariablesModel.TYPE_STRING, 
								GlobalVariablesModel.TYPE_INTEGER, 
								GlobalVariablesModel.TYPE_BOOLEAN,
								GlobalVariablesModel.TYPE_PASSWORD };
		Label lType = PanelUiUtil.createLabel(comp, "Type: ");
		cType = PanelUiUtil.createComboBox(comp, gvTypes);
		cType.setText(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_TYPE));
		cType.addListener(SWT.Selection, getListener(cType, GlobalVariablesModel.FIELD_TYPE));
		
		bDeploy = createConfigCheckboxField(comp, "Deployment settable: ", GlobalVariablesModel.FIELD_ISDEPLOY);
		bService = createConfigCheckboxField(comp, "Service settable: ", GlobalVariablesModel.FIELD_ISSERVICE);
		tDesc = createConfigTextField(comp, "Description: ", GlobalVariablesModel.FIELD_DESCRIPTION);
		tConstraint = createConfigTextField(comp, "Constraint: ", GlobalVariablesModel.FIELD_CONSTRAINT);
		tModTime = createConfigTextField(comp, "Last Modified: ", GlobalVariablesModel.FIELD_MODTIME);
		tModTime.setEditable(false);
		tModTime.setEnabled(false);
		
		comp.pack();
		parent.pack();
	}
	
	private Text createConfigTextField(Composite comp, String label, String fieldId) {
		PanelUiUtil.createLabel(comp, label);
		Text tField = PanelUiUtil.createText(comp);
		tField.setText(modelmgr.getStringValue(gvDesc, fieldId));
		tField.addListener(SWT.Modify, getListener(tField, fieldId));
		return tField;
	}
	
	private Button createConfigCheckboxField(Composite comp, String label, String fieldId) {
		PanelUiUtil.createLabel(comp, label);
		Button bField = PanelUiUtil.createCheckBox(comp, "");
		bField.setSelection(modelmgr.getBooleanValue(gvDesc, fieldId));
		bField.addListener(SWT.Selection, getListener(bField, fieldId));
		return bField;
	}
	
	private Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = false;
				if (field instanceof Text) {
					if (!key.equals(GlobalVariablesModel.FIELD_MODTIME)) {
						updated = modelmgr.updateValue(gvDesc, key, ((Text) field).getText());
						if (updated && key.equals(GlobalVariablesModel.FIELD_NAME)) {
							viewer.refresh(gvDesc);
						}
					}
					if(key.equals(GlobalVariablesModel.FIELD_NAME))
					     validateField((Text)field);
				} else if (field instanceof Button) {
					updated = modelmgr.updateValue(gvDesc, key, new Boolean(((Button) field).getSelection()).toString());
				} else if (field instanceof Combo) {
					String text = ((Combo) field).getText();
					String oldType = gvDesc.getType();
					updated = modelmgr.updateValue(gvDesc, key, text);
					if (updated) {
						if (key.equalsIgnoreCase("type") && GlobalVariablesModel.TYPE_PASSWORD.equalsIgnoreCase(oldType)) {
							// the type was changed from password to something else, need to reset the value to '' 
							// so that the hidden password is not displayed to the user
							resetValue();
						}
						viewer.refresh(gvDesc);
					}
					if (key.equals(GlobalVariablesModel.FIELD_TYPE)) {
						setValueFieldEchoChar();
					}
				}
				if (updated) {
					Long modTime = new Long(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_MODTIME)).longValue();
					tModTime.setText(modelmgr.getDateTime(modTime));
				}
			}
		};
		return listener;
	}
	
	protected static boolean validateField(Text textField) {
		String problemMessage=null;
		if(textField.getText().contains("="))
			problemMessage = "Global Valriable name can not contain = operator";
		if (problemMessage != null) {
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_RED));
			textField.setToolTipText(problemMessage);
			return false;
		}else if(problemMessage == null){
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_BLACK));
			textField.setToolTipText("");
			return true;
		}

		return true;
	}
	
	protected void resetValue() {
		tValue.setText("");
	}

	private void setValueFieldEchoChar() {
		if (cType.getText().equals(GlobalVariablesModel.TYPE_PASSWORD)) {
			tValue.setEchoChar('*');
		} else {
			tValue.setEchoChar('\0');
		}
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			gvDesc = ((GlobalVariablesDescriptor) ssel.getFirstElement());
		else
			gvDesc = null;
		update();
	}

	private void update() {
		if (gvDesc==null)
			return;
		String gvName = modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_NAME);
		tName.setText(gvName);
		tValue.setText(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_VALUE));
		cType.setText(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_TYPE));
		bDeploy.setSelection(modelmgr.getBooleanValue(gvDesc, GlobalVariablesModel.FIELD_ISDEPLOY));
		bService.setSelection(modelmgr.getBooleanValue(gvDesc, GlobalVariablesModel.FIELD_ISSERVICE));
		tDesc.setText(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_DESCRIPTION));
		tConstraint.setText(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_CONSTRAINT));
		Long modTime = new Long(modelmgr.getStringValue(gvDesc, GlobalVariablesModel.FIELD_MODTIME)).longValue();
		tModTime.setText(modelmgr.getDateTime(modTime));
		setValueFieldEchoChar();
		
		if (gvDesc.isProjectLib() 
				  || gvName.equalsIgnoreCase("Deployment") 
				  || gvName.equalsIgnoreCase("Domain")) {
			enableEditFields(false);
		} else {
			enableEditFields(true);
		}
		
		

	}
	
	private void enableEditFields(boolean en) {
		tName.setEnabled(en);
		tValue.setEnabled(en);
		cType.setEnabled(en);
		bDeploy.setEnabled(en);
		bService.setEnabled(en);
		tDesc.setEnabled(en);
		tConstraint.setEnabled(en);
	}
}
