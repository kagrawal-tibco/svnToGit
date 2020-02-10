package com.tibco.cep.studio.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



public class DelayedDocumentListener implements DocumentListener, ActionListener { 

	private Timer timer = null; 
	private DocumentListener docListenerDelegate; 
	private DocumentEvent event; 


	public DelayedDocumentListener(DocumentListener docListener) { 
		this(docListener, 250); 
	} 

	public DelayedDocumentListener(DocumentListener docListener, int delay){ 
		this.docListenerDelegate = docListener; 
		this.timer = new Timer(delay, this); 
	} 
	
	private void documentChanged(DocumentEvent de) {
		this.timer.restart();
		this.event = de;
	}

	// DocumentListener interfaces

	public void changedUpdate(DocumentEvent e){ 
		this.documentChanged(e); 
	} 

	public void insertUpdate(DocumentEvent e){ 
		this.documentChanged(e); 
	} 

	public void removeUpdate(DocumentEvent e){ 
		this.documentChanged(e); 
	} 

	// ActionListener interfaces we can register for a "Validate" button 

	public void actionPerformed(ActionEvent e) { 

		if (this.timer.isRunning()) { 
			this.timer.stop(); 
		}

		DocumentEvent.EventType eventType = this.event.getType(); 

		if (eventType == DocumentEvent.EventType.INSERT) { 
			this.docListenerDelegate.insertUpdate(event); 
		}
		else if (eventType == DocumentEvent.EventType.REMOVE) { 
			this.docListenerDelegate.removeUpdate(event); 
		}
		else if (eventType == DocumentEvent.EventType.CHANGE) { 
			this.docListenerDelegate.changedUpdate(event); 
		}
	}
}