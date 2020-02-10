package com.tibco.cep.studio.tester.utilities;

import java.text.MessageFormat;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.ui.editor.data.TestDataEditor;
import com.tibco.cep.studio.tester.ui.tools.MultipleValuesCellDialog;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author aasingh
 *
 */
public class MultipleValuesCellDialogEditor extends DialogCellEditor {

	protected Label textField;
	private Composite parent;
	private String columnType;
	private String columnName;
	TestDataEditor editor;
	TestDataModel model;
	String [][] domainEntries;
	String[] items;
	
	public static String ICON_TOOLBAR_LIST_BROWSE_MULTIPLE = "/icons/multipleEntryButton.gif";
	public static String ICON_TOOLBAR_STRING = "iconString16.gif";

	public MultipleValuesCellDialogEditor(Composite comp,String columnType,String columnName, TestDataEditor editor,TestDataModel model,  String [][] domainEntries, String[] items){
		super(comp);
		this.parent=comp;
		this.model=model;
		this.columnType=columnType;
		this.columnName = columnName;
		this.editor=editor;
		this.domainEntries = domainEntries;
		this.items=items;
	
	}
	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.DOWN);
		result.setImage(StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_BROWSE_MULTIPLE));
		//result.setText("..."); //$NON-NLS-1$
		return result;
	}

	protected Control createContents(Composite cell) {

		textField = new Label(cell, SWT.LEFT);
		textField.setFont(cell.getFont());
		textField.setBackground(cell.getBackground());
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
		if(value!=null)
			textField.setText(value.toString());
	}

	protected void doSetFocus() {
		// Overridden to set focus to the Text widget instead of the Button.
		textField.setFocus();
	}
	
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
			Shell shell = parent.getShell();
			MultipleValuesCellDialog multipleValuesCellDialog = new MultipleValuesCellDialog(editor,shell,columnName,columnType,"Multiple",getDialogInitialValue(),domainEntries,items);
			multipleValuesCellDialog.open();
			return  multipleValuesCellDialog.passValue();
		
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
