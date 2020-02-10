package com.tibco.cep.studio.ui.forms.components;

import java.text.MessageFormat;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.concepts.ConceptPropertySelector;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;

/**
 * @author mgujrath
 *
 */
public class TextAndDialogCellEditor extends DialogCellEditor {
	
	protected Label textField;
	protected String dialogMessage;
	protected String dialogTitle;
	protected Composite parent;
	protected String columnName;
	protected AbstractSaveableEntityEditorPart editor;
	//private EditorPart editor;
	protected IWorkbenchPage page;
	public static String ICON_TOOLBAR_LIST_BROWSE = "/icons/browse_file_system.gif";
	public static String ICON_TOOLBAR_STRING = "iconString16.gif";
	
	public TextAndDialogCellEditor(Composite comp,String columnName,AbstractSaveableEntityEditorPart editor){
		super(comp);
		this.parent=comp;
		this.columnName=columnName;
		this.editor=editor;
		
	}
	protected Button createButton(Composite parent) {
        Button result = new Button(parent, SWT.DOWN);
        result.setImage(StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_BROWSE));
        //result.setText("..."); //$NON-NLS-1$
        return result;
    }
    
	public void setDialogMessage(String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}


	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	protected Control createContents(Composite cell) {
		
		textField = new Label(cell, SWT.LEFT);
		textField.setFont(cell.getFont());
		textField.setBackground(cell.getBackground());
	    //addImg = /*StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_ADD);*/new Image(cell.getDisplay(),ICON_TOOLBAR_STRING);
		//textField.setImage(addImg);
		textField.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent event) {
					 setValueToModel();
				}
			});

		textField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent event) {
					keyReleaseOccured(event);
				}
			});

		return textField;
	/*	Control contenst=super.createContents(cell);
		setDialogMessage("hello user!!");
		setDialogTitle("USers");
		return contenst;*/
	}
    
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.keyCode == SWT.CR || keyEvent.keyCode == SWT.KEYPAD_CR) { // Enter key
			setValueToModel();
		}
		super.keyReleaseOccured(keyEvent);
	}

	protected void setValueToModel() {
	 	String newValue = textField.getText();
	 	//textField.setImage(addImg);
        boolean newValidState = isCorrect(newValue);
        if (newValidState) {
            markDirty();
            doSetValue(newValue);
        } else {
            // try to insert the current value into the error message.
            setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { newValue.toString() }));
        }
	
	
	}
	
	protected void updateContents(Object value) {
		if (textField == null) {
			return;
		}
		
		String property=null;
		try{
		String[] val=value.toString().split("-");
		property= val[0].toString();
		}catch(Exception e){
			property=(String)value;
		}
		
        String text = "";
        if (value != null) {
			text = property.toString();
		}
        textField.setText(text);
       // textField.setImage(addImg);
	}
	
	protected void doSetFocus() {
		// Overridden to set focus to the Text widget instead of the Button.
		textField.setFocus();
		//textField.selectAll();
	}
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		
		Table table=(Table)cellEditorWindow.getParent();
		TableItem[] selectedProperty=table.getSelection();
		PropertyDefinition prop=(PropertyDefinition)selectedProperty[0].getData();
		
		if(columnName.equals("Type")){
	
			ConceptPropertySelector cp=new ConceptPropertySelector(parent.getShell(),editor.getProject().getName(), editor.getEntity().getFullPath(),prop.getDefaultValue() ,prop.getType().toString());
			if (cp.open() == Window.OK) {
				editor.modified();
				String propertyType = cp.getPropertyType();
				String val=cp.getValue();
				if (propertyType == val)
					return propertyType;			// for basic data types
				else
					return val+"-"+propertyType;	//concept reference and contained concept
			
			}
		}
		if(columnName.equals("Domain")){
			
			try{
					String val= prop.getType().toString();
					return setValuesFromDomainResourcesPicker(prop, val);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		return null;
	}

	private String setValuesFromDomainResourcesPicker(PropertyDefinition propertyDefinition, String oldValue){
		page = editor.getEditorSite().getPage();
		EditorUtils.runDomainResourceSelector(propertyDefinition, page, editor.getProject().getName(), true, true);
		String domainValues = EditorUtils.getDomainValues(propertyDefinition.getDomainInstances());
		if(!domainValues.equalsIgnoreCase(oldValue)){
			editor.modified();
		}
		return domainValues;
	}
	
	
	protected String getDialogInitialValue() {
		Object value = getValue();
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}
	
}	
