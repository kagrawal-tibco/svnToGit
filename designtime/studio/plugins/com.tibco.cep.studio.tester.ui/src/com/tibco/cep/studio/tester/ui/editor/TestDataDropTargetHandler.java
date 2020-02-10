package com.tibco.cep.studio.tester.ui.editor;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JTable;

/**
 * 
 * @author smarathe
 *
 */
public class TestDataDropTargetHandler implements DropTargetListener {

	JTable testDataTable;
	public TestDataDropTargetHandler(JTable testDataTable) {
		this.testDataTable = testDataTable;
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent dropTargetDropEvent) {
		
		int rowNo = testDataTable.rowAtPoint(dropTargetDropEvent.getLocation());
		int columnNo = testDataTable.columnAtPoint(dropTargetDropEvent.getLocation());
		dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable tr = dropTargetDropEvent.getTransferable();
		tr.getTransferDataFlavors();
		try {
			String data = (String)tr.getTransferData(DataFlavor.stringFlavor);
			testDataTable.setValueAt(testDataTable.getValueAt(rowNo, columnNo) + data, rowNo, columnNo);
		} catch (UnsupportedFlavorException e) {
		} catch (IOException e) {
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

}
