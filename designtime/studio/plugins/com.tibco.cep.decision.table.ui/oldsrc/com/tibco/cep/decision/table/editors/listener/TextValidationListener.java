package com.tibco.cep.decision.table.editors.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.eclipse.swt.widgets.Display;

public class TextValidationListener implements DocumentListener {

	
    public void insertUpdate(DocumentEvent e) { 
        this.validate(e.getDocument()); 
    } 

    public void removeUpdate(DocumentEvent e) { 
        this.validate(e.getDocument()); 
    } 

    public void changedUpdate(DocumentEvent e) { 
        this.validate(e.getDocument()); 
    } 	

	// TODO, obviously...    
	public void validate(final Document doc) {
		// Restart the timer which starts the calls back after the specified delay. 
//		try {
//			Reader reader = new StringReader(doc.getText(0, doc.getLength()));
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 

		boolean valid = false;

		if (!valid) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
//					try {
//						DecisionTablePlugin.getInstance().getLog().log(
//							new Status(
//								Status.ERROR,
//								DecisionTablePlugin.PLUGIN_ID,
//								Status.OK,
//								"Syntax Error: " + doc.getText(0, doc.getLength()),
//								null));
//					} catch (BadLocationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			});		
		} 	
	}		
}
