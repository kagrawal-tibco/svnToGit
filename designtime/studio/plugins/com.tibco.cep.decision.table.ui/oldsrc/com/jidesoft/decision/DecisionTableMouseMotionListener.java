/**
 * 
 */
package com.jidesoft.decision;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JTable;

/**
 * @author aathalye
 *
 */
public class DecisionTableMouseMotionListener implements MouseMotionListener {
	
	private DecisionTablePane decisionTablePane;
	
	private int row;
	
	private int column;
	
	/**
	 * @param decisionTablePane
	 */
	public DecisionTableMouseMotionListener(DecisionTablePane decisionTablePane) {
		this.decisionTablePane = decisionTablePane;
		this.row = -1;
		this.column = -1;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		Object source = e.getSource();
		if (source instanceof JTable) {
			JTable mainTable = (JTable)source;
			Point point = e.getPoint();
			int row = mainTable.rowAtPoint(point);
			int column = mainTable.columnAtPoint(point);
			
			if (row == this.row && column == this.column) {
				return;
			} else {
				this.row = row;
				this.column = column;
			}
			Rectangle rectangle = mainTable.getCellRect(row, column, false);
			decisionTablePane.showCalendarPopup(row, column, rectangle, mainTable);
		}
	}
}
