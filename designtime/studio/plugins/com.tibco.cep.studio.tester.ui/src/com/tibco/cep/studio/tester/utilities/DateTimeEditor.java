package com.tibco.cep.studio.tester.utilities;

import java.util.ArrayList;

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

import com.tibco.cep.studio.ui.StudioUIPlugin;
/**
 * 
 * @author aasingh
 *
 */
public class DateTimeEditor extends DialogCellEditor{

	private Composite parent;
	protected Label textField;
	private String[][] domainEntries;
	ArrayList items = null; 
	public static String ICON_TOOLBAR_LIST_DATE_TIME = "/icons/iconDate16.gif";
	public DateTimeEditor(Composite composite , String[][] domainEntries){
		super(composite);
		parent = composite;
		this.domainEntries = domainEntries;
	  
	}
	
	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.DOWN);
		result.setImage(StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_DATE_TIME));
		//result.setText("..."); //$NON-NLS-1$
		return result;
	}
	
	protected Control createContents(Composite cell) {
		textField = new Label(cell, SWT.LEFT);
		textField.setFont(cell.getFont());
		textField.setBackground(cell.getBackground());
		textField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent event) {
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
		//	setValueToModel();
		}
		super.keyReleaseOccured(keyEvent);
	}
	
	protected void updateContents(Object value) {
		if (textField == null) {
			return;
		}
		String property=null;
			property=(String)value;
        String text = "";
        if (value != null) {
			text = property.toString();
		}
        textField.setText(text);
		
	}

	protected void doSetFocus() {
		// Overridden to set focus to the Text widget instead of the Button.
		textField.setFocus();
	}
	
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		// TODO Auto-generated method stub
		 Shell shell = parent.getShell();
		
		 if(domainEntries != null){
			 	 items = new ArrayList();
				 for(int i =0; i < domainEntries.length ; i++){
				 items.add(domainEntries[i][0]);
			 }
		}
		DateTimeDialog dateTime = new DateTimeDialog(shell,items);
		dateTime.open();
		return dateTime.ReturnCurrentDate();
	}

}
