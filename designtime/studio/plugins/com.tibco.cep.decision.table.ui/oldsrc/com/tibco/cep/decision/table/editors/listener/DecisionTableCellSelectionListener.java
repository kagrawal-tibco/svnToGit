/**
 * 
 */
package com.tibco.cep.decision.table.editors.listener;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author aathalye
 *
 */
public class DecisionTableCellSelectionListener implements MouseListener, KeyListener, FocusListener {
	
	private JTextField textField;
	
	private TableScrollPane tableScrollPane;
	
	private DecisionTableEditor editor;
	
	private TableTypes tableType;
	
	/**
	 * 	
	 * @param functionTextField The fx text area to show cell contents
	 */
	public DecisionTableCellSelectionListener(JTextField functionTextField, TableScrollPane tableScrollPane, DecisionTableEditor editor, TableTypes tableType) {
		this.textField = functionTextField;
		this.tableScrollPane = tableScrollPane;
		this.tableType = tableType;
		this.editor = editor;
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			
		}
		
		if (source instanceof JTable) {
			
			TableScrollPane otherTableScrollPane = null;
			if (TableTypes.DECISION_TABLE.equals(tableType)) {
				otherTableScrollPane = 
						editor.getDecisionTableDesignViewer().getExceptionTablePane().getTableScrollPane();
			} else {
				otherTableScrollPane = 
						editor.getDecisionTableDesignViewer().getDecisionTablePane().getTableScrollPane();
			}
			JTable table = otherTableScrollPane.getMainTable();
			if (table.getSelectedRow() >= 0) {
				table.clearSelection();
			}

			JTable mainTable = (JTable)source;
			int column = mainTable.getSelectedColumn();
			int row = mainTable.getSelectedRow();
			
			// check for row/column bounds
			if (row < 0 || row > mainTable.getRowCount()) {
				return;
			}
			if (column < 0 || column > mainTable.getColumnCount()) {
				return;
			}
			mainTable.getCellEditor(row, column).isCellEditable(e);
			mainTable.editCellAt(row, column);
			Object value = mainTable.getValueAt(row, column);
			if (value instanceof TableRuleVariable) {
				TableRuleVariable text = (TableRuleVariable)value;
				if (text != null) {
					textField.setText(text.getExpr().trim());
				}
				setWorkbenchSelectionforCell(mainTable, row, column);
			} else {
				setWorkbenchSelectionforCell(mainTable, row, column);
				textField.setText("");
			}
		} 
	}
	
	

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		Object source = e.getSource();
		if (source instanceof JTable) {
			JTable mainTable = (JTable)source;
			int column = mainTable.getSelectedColumn();
			int row = mainTable.getSelectedRow();
			mainTable.getCellEditor(row, column).isCellEditable(e);
			mainTable.editCellAt(row, column);
			setWorkbenchSelectionforCell(mainTable, row, column);
		}
	}



	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 9) {
			Object source = e.getSource();
			if (source instanceof JTable) {
				JTable editingTable = (JTable)source;
				JTable mainTable = tableScrollPane.getMainTable();
				if (editingTable == mainTable) {
					int column = editingTable.getSelectedColumn();
					int row = editingTable.getSelectedRow();
					int columns = editingTable.getColumnCount();
					int rows = editingTable.getRowCount();
					if (columns >= column && rows >= row && column > -1 && row > -1) {
						TableCellEditor tableCellEditor = editingTable.getCellEditor(row, column);
						if (tableCellEditor instanceof DefaultRuleVariableCellEditor) {
							editingTable.editCellAt(row, column);
							TableRuleVariable text = (TableRuleVariable)editingTable.getValueAt(row, column);
							if (text != null) {
								textField.setText(text.getExpr().trim());
							} else {
								textField.setText("");
							}
						} 
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Delegating workbench selection on JTable Cell selection
	 * @param mainTable
	 * @param row
	 * @param column
	 */
	private void setWorkbenchSelectionforCell(JTable mainTable, int row, int column) {
		final Object value = mainTable.getValueAt(row, column);
		if (value != null && value instanceof TableRuleVariable) {
			final TableRuleVariable drc = (TableRuleVariable) value;
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run() {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					setWorkbenchSelection(drc, editor);
				}});
		} else if(value != null && value instanceof Integer){
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run() {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					setWorkbenchSelection(value, editor);
				}});
		}
		else {
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run() {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					setWorkbenchSelection(new Object(), editor);
				}});
		}
	}
}
