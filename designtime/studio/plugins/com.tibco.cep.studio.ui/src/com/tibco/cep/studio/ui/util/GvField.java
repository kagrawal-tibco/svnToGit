package com.tibco.cep.studio.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.GvUtil;

/*
@author ssailapp
@date Feb 24, 2010 8:12:20 PM
 */

public class GvField {

	private Text tGv;
	private Button bGvToggle;
	private DropTarget dropTarget;
	private String gvVal;
	private String fieldVal;
	private Composite masterComp, fieldComp;
	private StackLayout fieldTabLayout;
	private Control fieldTabs[];
	private Label label;
	
	private int type;
	private Control field;
	
	public static int FIELD_TYPE_TEXT = 0;
	public static int FIELD_TYPE_PASSWORD = 1;
	public static int FIELD_TYPE_CHECKBOX = 2;
	public static int FIELD_TYPE_COMBO = 3;
	
	public GvField(Composite parent, String initVal, int type) {
		masterComp = new Composite(parent, SWT.NONE);
		masterComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		masterComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		
		this.type = type;
		createFieldTabs(masterComp);
		
		setValueWithoutInit(initVal);

		bGvToggle = PanelUiUtil.createGlobalVarPushButton(masterComp, tGv);
		bGvToggle.addListener(SWT.Selection, getGvToggleListener());
		//bGvToggle.notifyListeners(SWT.Selection, new Event());
		
		initializeDropTarget();
		masterComp.pack();
	}
	
	public GvField(Composite parent, String initVal, int type, Label label) {
		this(parent, initVal, type);
		this.label = label;
	}

	private void createFieldTabs(Composite parent) {
		Composite fieldStackComp = new Composite(parent, SWT.NONE);
		fieldStackComp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		fieldStackComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		fieldComp = new Composite(fieldStackComp, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		//gd.widthHint = 400;
		fieldComp.setLayoutData(gd);
		fieldTabLayout = new StackLayout();
		fieldComp.setLayout(fieldTabLayout);
	    
	    fieldTabs = new Control[2];
	    fieldTabs[0] = createFieldTab();
	    fieldTabs[1] = createGvTab();
        fieldTabLayout.topControl = fieldTabs[0]; 
        
		fieldComp.pack();
		fieldStackComp.pack();
	}
	
	private Control createFieldTab() {
		Composite fieldWidgetComp = new Composite(fieldComp, SWT.NONE);
		GridLayout gl = new GridLayout(1, false);
		gl.marginWidth = 0;
		fieldWidgetComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		fieldWidgetComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (type == FIELD_TYPE_TEXT)
			field = PanelUiUtil.createText(fieldWidgetComp);
		else if (type == FIELD_TYPE_PASSWORD)
			field = PanelUiUtil.createTextPassword(fieldWidgetComp);
		else if (type == FIELD_TYPE_CHECKBOX)
			field = PanelUiUtil.createCheckBox(fieldWidgetComp, "");
		else if (type == FIELD_TYPE_COMBO)
			field = PanelUiUtil.createComboBox(fieldWidgetComp, new String[0]);
		
		fieldWidgetComp.pack();
		return fieldWidgetComp;
	}
	
	private Control createGvTab() {
		Composite gvComp = new Composite(fieldComp, SWT.NONE);
		GridLayout gl = new GridLayout(1, false);
		gl.marginWidth = 0;
		gvComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		gvComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tGv = PanelUiUtil.createText(gvComp);
		gvComp.pack();
		return gvComp;
	}
	
	public void setFieldListener(int eventType, Listener listener) {
		field.addListener(eventType, listener);
	}

	public void setGvListener(Listener listener) {
		tGv.addListener(SWT.Modify, listener);
	}
	
	public Control getField() {
		return field;
	}
	
	public Composite getMasterComposite() {
		return masterComp;
	}

	public boolean isPasswordField() {
		return (type==FIELD_TYPE_PASSWORD);
	}
	
	public String getFieldValue() {
		if (field instanceof Text) {
			return ((Text)field).getText();
		} else if (field instanceof Combo) {
			return ((Combo)field).getText();
		} else if (field instanceof Button) {
			boolean isChecked = ((Button)field).getSelection();
			return new Boolean(isChecked).toString();
		}
		return ("");
	}
	
	private void setFieldValue(String value) {
		fieldVal = value;
		if (field instanceof Text) {
			((Text)field).setText(value);
			field.notifyListeners(SWT.Modify, new Event());
		} else if (field instanceof Combo) {
			Combo combo = (Combo) field;
			if (!value.equals(""))
				combo.setText(value);
			else if (combo.getItemCount()>0)
				combo.setText(combo.getItem(0));
			field.notifyListeners(SWT.Selection, new Event());
		} else if (field instanceof Button) {
			boolean isChecked = new Boolean(value).booleanValue();
			((Button)field).setSelection(isChecked);
			field.notifyListeners(SWT.Selection, new Event());
		}
	}
	
	public boolean isGvMode() {
		return field.getDragDetect();
	}

	public Text getGvText() {
		return tGv;
	}
	
	public Button getGvToggleButton() {
		return bGvToggle;
	}

	public String getGvValue() {
		return tGv.getText();
	}
	
	public void initializeDropTarget() {
		//bGvToggle.notifyListeners(SWT.Selection, new Event());
		if (field.getDragDetect()) {
			onSetGvMode();
		} else {
			onSetFieldMode();
		}
	}
	
	public void initializeGvToggleButtonUI() {
		if (field.getDragDetect()) {
			setGvMode();
		} else {
			setFieldMode();
		}
	}
	
	public void setFieldModeValue(String value) {
		fieldVal = value;
		setFieldValue(value);
		field.setDragDetect(false);
	}
	
	public void setGvModeValue(String value) {
		gvVal = value;
		tGv.setText(value);
		field.setDragDetect(true);
	}
	
	private Listener getGvToggleListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean proceed = getFieldModeChangeConfirmation();
				if (!proceed)
					return;
				if (field.getDragDetect()) {
					onSetFieldMode();
				} else {
					onSetGvMode();
				}
			}

			private boolean getFieldModeChangeConfirmation() {
				if ( (isGvMode() && !tGv.getText().equals("")) ||
					 (!isGvMode() && (field instanceof Text) && !getFieldValue().equals("")) )	{
					String message = "Changing field mode will discard its current value. Really discard the current value?";
					boolean proceed = MessageDialog.openQuestion(masterComp.getShell(), "Discard value", message);
					return proceed;
				}
				return true;
			}
		};
		return listener;
	}
	
	public void onSetFieldMode() {
		setFieldCompStackTopControl(0);
		bGvToggle.setImage(GvUiUtil.getGvImage(GvUiUtil.ICON_GLOBAL_VAR_OFF));
		field.setDragDetect(false);
		gvVal = tGv.getText();
		//tGv.setText("");
		setFieldValue(fieldVal);
		if (dropTarget != null) 
			dropTarget.dispose();
		field.setFocus();
	}
	
	public void onSetGvMode() {
		setFieldCompStackTopControl(1);
		bGvToggle.setImage(GvUiUtil.getGvImage(GvUiUtil.ICON_GLOBAL_VAR_ON));
		field.setDragDetect(true);
		fieldVal = getFieldValue();
		tGv.setText(gvVal);
		if (dropTarget != null) 
			dropTarget.dispose();
		dropTarget = PanelUiUtil.setDropTarget(tGv);
		tGv.setFocus();
	}
	
	public void setFieldMode() {
		setFieldCompStackTopControl(0);
		bGvToggle.setImage(GvUiUtil.getGvImage(GvUiUtil.ICON_GLOBAL_VAR_OFF));
		field.setDragDetect(false);
		//gvVal = tGv.getText();
		//tGv.setText("");
		setFieldValue(fieldVal);
		if (dropTarget != null) 
			dropTarget.dispose();
		field.setFocus();
	}
	
	public void setGvMode() {
		setFieldCompStackTopControl(1);
		bGvToggle.setImage(GvUiUtil.getGvImage(GvUiUtil.ICON_GLOBAL_VAR_ON));
		field.setDragDetect(true);
		//fieldVal = getFieldValue();
		tGv.setText(gvVal);
		if (dropTarget != null) 
			dropTarget.dispose();
		dropTarget = PanelUiUtil.setDropTarget(tGv);
		tGv.setFocus();
	}
	
	private void setFieldCompStackTopControl(int top) {
		fieldTabLayout.topControl = fieldTabs[top];
		fieldComp.layout();
	}

	public void setVisible(boolean enabled) {
		masterComp.setVisible(enabled);
	}
	
	public String getGvDefinedValue(IProject project) {
		String variable = isGvMode() ? getGvValue() : getFieldValue();
		return (GvUtil.getGvDefinedValue(project, variable));
	}
	
	public void setValue(String value) {
		setValueOnly(value);
		initializeDropTarget();
	}
	
	public void setValueOnly(String value) {
		if (GvUtil.isGlobalVar(value)) {
			gvVal = value;
			fieldVal = "";
			field.setDragDetect(true);
		} else {
			gvVal = "";
			fieldVal = value;
			field.setDragDetect(false);	
		}
	}
	
	public void setValueWithoutInit(String value) {
		setValueOnly(value);
	}
	
	public String getValue() {
		return (isGvMode() ? getGvValue(): getFieldValue());
	}
	
	public void setBackgroundColor(Color color){
		masterComp.setBackground(color);
		fieldComp.setBackground(color);
		fieldComp.getParent().setBackground(color);
		field.getParent().setBackground(color);
		tGv.getParent().setBackground(color);
	}
	
	public void setEnabled(boolean enabled){
		setEnabled(masterComp,enabled);
		if (label != null) {
			label.setEnabled(enabled);
		}
	}
	
	private void setEnabled(Composite comp, boolean enabled){
		for (Control child : comp.getChildren()){
			if(child instanceof Composite){
				setEnabled((Composite)child,enabled);
			}
			child.setEnabled(enabled);
		}
	}

}
