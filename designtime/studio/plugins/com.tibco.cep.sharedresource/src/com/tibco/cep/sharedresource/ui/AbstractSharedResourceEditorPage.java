package com.tibco.cep.sharedresource.ui;

import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.StudioImages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/*
@author ssailapp
@date Nov 23, 2009 4:17:23 PM
 */
@SuppressWarnings("unused")
public abstract class AbstractSharedResourceEditorPage {

	protected ManagedForm managedForm;
    protected SashForm sashForm;
	protected Composite editorParent;
	protected MultiPageEditorPart editor;
	protected FormToolkit toolkit;
	
	protected Image fImage;
	
	
	protected void createPartControl(Composite parent, String title, Image image) {
		managedForm = new ManagedForm(parent);
		final ScrolledForm form = managedForm.getForm();
		toolkit = managedForm.getToolkit();
		form.setText(title);
		form.setImage(image);
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		
		sashForm = new MDSashForm(form.getBody(), SWT.VERTICAL);
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, true, true);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayout(new GridLayout(1, true));
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		hookResizeListener();
		managedForm.initialize();
		form.reflow(true);
	}
	
	public void dispose() {
		if (managedForm != null) {
			managedForm.dispose();
		}
		if (sashForm != null) {
			sashForm.dispose();
		}
	}
	
    protected void hookResizeListener() {
		Listener listener = ((MDSashForm) sashForm).listener;
		Control[] children = sashForm.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Sash)
				continue;
			children[i].addListener(SWT.Resize, listener);
		}
	}
    
    public Control getControl() {
    	return (managedForm.getForm());
    }

    public GvField createGvTextField(Composite parent, String label, SharedResModelMgr modelmgr, String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	return createGvTextField(parent, modelmgr, modelId);
    }
    
    public GvField createGvTextField(Composite parent, SharedResModelMgr modelmgr, String modelId) {
    	GvField gvField = GvUiUtil.createTextGv(parent, modelmgr.getStringValue(modelId));
		setGvFieldListeners(gvField, SWT.Modify, modelId);
		return gvField;
    }

    public GvField createGvPasswordField(Composite parent, String label, SharedResModelMgr modelmgr, String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createPasswordGv(parent, modelmgr.getStringValue(modelId));
		setGvFieldListeners(gvField, SWT.Modify, modelId);
		return gvField;
    }
    
    public GvField createGvCheckboxField(Composite parent, String label, SharedResModelMgr modelmgr, String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createCheckBoxGv(parent, modelmgr.getStringValue(modelId));
		setGvFieldListeners(gvField, SWT.Selection, modelId);
		return gvField;
    }
    
    public GvField createGvComboField(Composite parent, String label, SharedResModelMgr modelmgr, String items[], String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createComboGv(parent, modelmgr.getStringValue(modelId));
		Combo combo = (Combo) gvField.getField();
		combo.setItems(items);
		String selItem = modelmgr.getStringValue(modelId);
		if (!selItem.equals(""))
			combo.setText(selItem);
		else if (combo.getItemCount()>0)
			combo.setText(combo.getItem(0));
		setGvFieldListeners(gvField, SWT.Selection, modelId);
		return gvField;
    }
    
    protected void setGvFieldListeners(GvField gvField, int eventType, String modelId) {
		gvField.setFieldListener(eventType, getListener(gvField.getField(), modelId));
		gvField.setGvListener(getListener(gvField.getGvText(), modelId));
    }
    
    public boolean validateFields(){
    	return true;
    }
    public abstract String getName();
    public abstract Listener getListener(final Control field, final String key);
    
	/**
	 * @param textField
	 * @param type
	 * @param deafultValue
	 * @param displayName
	 * @return
	 */
	protected static boolean validateField(Text textField, String type, String deafultValue,
			String displayName, String prjName) {
		final String problemMessage = validatePropertyValue(type, textField,
				deafultValue, displayName,prjName);
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
	
	/**
	 * @param type
	 * @param fieldName
	 * @param deafultValue
	 * @param propertyName
	 * @param propertyInstance
	 * @return
	 */
	protected static String validatePropertyValue(String type, Text textField,
			String deafultValue, String propertyName, String projectName) {
		final String message = com.tibco.cep.studio.ui.util.Messages.getString(
				"invalid.property.entry", textField.getText(), propertyName,
				type);
		String text = textField.getText();
		boolean globalVar = false;
		if (text.length() > 4) {
			globalVar = GvUtil.isGlobalVar(text.trim());
		}
		if (globalVar) {
			// check if global var defined
			Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator
					.getGlobalVariableNameValues(projectName);

			GlobalVariableDescriptor gvd = glbVars.get(stripGvMarkers(text));
			if (gvd == null) {
				return text + " is not defined";
			}
			if(type!=null){
				if (!gvd.getType().equalsIgnoreCase(type.intern())) {
				/*if (type.intern().equals("int")
						&& gvd.getType().equals("Integer")) {
					return null;
				}*/
					return "Type of "+ text +" is not "+ type.intern();
				}
			}
			return null;
		}
		return null;
	}
	
	private static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		return stripVal;
	}
    
}
