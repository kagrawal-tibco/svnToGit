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

import com.tibco.cep.studio.tester.ui.editor.data.TestDataEditor;
import com.tibco.cep.studio.tester.ui.tools.PayloadDialog;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class PayLoadCellEditor extends DialogCellEditor {

	protected Label textField;
	/*private String dialogMessage;
	private String dialogTitle;*/
	private Composite parent;
	private String columnName;
	TestDataEditor editor;
	//	private IWorkbenchPage page;
	public static String ICON_TOOLBAR_LIST_BROWSE = "/icons/browse_file_system.gif";
	public static String ICON_TOOLBAR_STRING = "iconString16.gif";

	public PayLoadCellEditor(Composite comp,String columnName,TestDataEditor editor){
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

	/*public void setDialogMessage(String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}
	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}
	 */
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
		
	}

	protected void doSetFocus() {
		// Overridden to set focus to the Text widget instead of the Button.
		textField.setFocus();
		//textField.selectAll();
	}
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {


		if(columnName.equals("payload")){
			Shell shell = parent.getShell();
			PayloadDialog payloadDialog = new PayloadDialog(shell,"Payload", (String) this.getValue());
			payloadDialog.open();
			return payloadDialog.passValue();
		}

		return null;
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
