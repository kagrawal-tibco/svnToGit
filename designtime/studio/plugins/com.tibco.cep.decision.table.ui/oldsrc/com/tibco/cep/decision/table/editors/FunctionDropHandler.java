package com.tibco.cep.decision.table.editors;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

/**
 * 
 * @author sasahoo
 * 
 */
public class FunctionDropHandler implements DropTargetListener {
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
	}

	public synchronized void drop(DropTargetDropEvent dropTargetDropEvent) {
		Transferable tr = dropTargetDropEvent.getTransferable();
		if (tr != null) {
			if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				dropTargetDropEvent
						.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			} else {
				dropTargetDropEvent.rejectDrop();
			}
		}
	}

	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
	}
}
