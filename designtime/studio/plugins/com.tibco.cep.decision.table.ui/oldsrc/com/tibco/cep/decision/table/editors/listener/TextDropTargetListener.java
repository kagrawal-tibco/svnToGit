package com.tibco.cep.decision.table.editors.listener;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JTextField;

/**
 * 
 * @author sasahoo
 * 
 */
public class TextDropTargetListener implements DropTargetListener {

	private JTextField textField;

	public TextDropTargetListener(JTextField textField) {
		this.textField = textField;
	}

	public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
	}

	public void dragExit(DropTargetEvent dropTargetEvent) {
	}

	public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
	}

	public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
	}

	public synchronized void drop(DropTargetDropEvent dropTargetDropEvent) {
		Transferable tr = dropTargetDropEvent.getTransferable();
		if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			String string;
			try {
				string = (String) tr.getTransferData(DataFlavor.stringFlavor);
				if (textField.getText() != null)
					textField.setText(textField.getText()+string);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dropTargetDropEvent.dropComplete(true);
		} else {
			dropTargetDropEvent.rejectDrop();
		}
	}
}
