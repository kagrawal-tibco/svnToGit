package com.tibco.cep.decision.table.editors.listener;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.decision.UserObject;
import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.editors.DateTimeFieldCalendar;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.ImageIconsFactory;
import com.tibco.cep.decisionproject.util.DTConstants;

/**
 * 
 * @author sasahoo
 * 
 */
public class DecisionTableModelSelectionListener implements ListSelectionListener {

	private JTable mainTable;
	private JTextField textField;
	private TableRuleVariable drc;
	private int tableType;
	private int oldSelectedRow = -1 ;
	private int oldSelectedColumn = -1 ;
	private DecisionTableEditor editor;
	//private DecisionTablePane tablePane;
	private String cellValue = null;

	/**
	 * @param editor
	 * @param mainTable
	 * @param textField
	 * @param tableType
	 */
	public DecisionTableModelSelectionListener(DecisionTableEditor editor,
											   JTable mainTable, 
			                                   JTextField textField,
			                                   int tableType) {
		this.mainTable = mainTable;
		this.textField = textField;		
		this.tableType = tableType;
		this.editor = editor;
	}
	
	public void valueChanged(ListSelectionEvent event) {
		final int firstRow = mainTable.getSelectedRow();
		final int column = mainTable.getSelectedColumn();
		if (oldSelectedRow == firstRow && oldSelectedColumn == column) {
			// if old selection and current selection is same then rturn instead of 
			// populating property view again 
			return ;
		}
		else {
			oldSelectedRow = firstRow;
			oldSelectedColumn = column;
		}

		if (firstRow != -1 && column != -1) {

			Object value = mainTable.getValueAt(firstRow, column);
			if (value instanceof TableRuleVariable) {
				drc = (TableRuleVariable) value;
			
				//EList<String> textList = drc.getExpression().getBodies();
				String expr = drc.getExpr();
				if (expr != null) {
					// this is the fn field
					DefaultRuleVariableCellEditor cellEditor = 
						(DefaultRuleVariableCellEditor)mainTable.getCellEditor();
					final EditorContext editorContext = cellEditor == null ? null : cellEditor.getEditorContext();
					String body = expr;
					if (editorContext != null) {
						Column col = ((UserObject)editorContext.getUserObject()).getColumn();
						if (col.isSubstitution()) {
							//TODO Fix This
							//body = drc.getExpression().getVarString();
						}
					}
					textField.setText(body);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
//							IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//							if(window!=null){
//								IWorkbenchPage page = window.getActivePage();
//								IEditorPart activeEditor = page.getActiveEditor();
								TableRuleSet decisionTableRuleSet = null;
								@SuppressWarnings("unused")
								Table tableEModel = null;
								
								TableScrollPane otherTableScrollPane = null;
								if (DTConstants.DECISION_TABLE == tableType) {
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
								
//								if (activeEditor instanceof DecisionTableEditor){
									setWorkbenchSelection(drc, editor);
									
									IDecisionTableEditorInput dtEditorInput = (IDecisionTableEditorInput)editor.getEditorInput();
									if (DTConstants.DECISION_TABLE == tableType){
										decisionTableRuleSet = dtEditorInput.getTableEModel().getDecisionTable();
									} else {
										decisionTableRuleSet = dtEditorInput.getTableEModel().getExceptionTable();
									}
									tableEModel = dtEditorInput.getTableEModel();
									if (editorContext == null) {
										Columns columns = decisionTableRuleSet.getColumns();
										Column column = columns.search(drc.getColId());
										if (column != null && column.isSubstitution()) {
											//TODO Fix this
											//textField.setText(drc.getExpression().getVarString());
										}
									} else {
										// the value was already set above, do nothing
									}
//								} else {
//									return ;
//								}
								
//								PropertyView ruleMetaView = (PropertyView) page	.findView(PropertyView.ID);
//								if (ruleMetaView != null) {
//									ruleMetaView.getEntityScrollPageBook().showPage(PropertyView.RULE_META_DATA_PAGE);
//									ruleMetaView.setRuleMetaData(drc, mainTable, decisionTableRuleSet, tableType);
//									ruleMetaView.setRuleTableMetaData(tableEModel);
//								}
//							}
						}
					});
				}
			} else {
				// if table rule variable is null then set fx field with blank value
				textField.setText("");
			}


		}

		//Showing "Show Calendar" popup menu
//		RuleVariableCellEditor cellEditor = (RuleVariableCellEditor)table.getCellEditor();
//		if (cellEditor == null) return;
//		EditorContext editorContext = cellEditor.getEditorContext();
//		if (editorContext == null) return;
//		UserObject userObject = (UserObject)editorContext.getUserObject();
//		if (userObject == null) return;

//		if(DecisionTableUtil.checkDateType(userObject.getColumn().getPropertyType())){
//		Object value = table.getValueAt(firstRow, column);
//		Component component = cellEditor.getTableCellEditorComponent(table,value , true, firstRow, column);
//		com.jidesoft.combobox.CheckBoxListComboBox combo = (com.jidesoft.combobox.CheckBoxListComboBox)component;
//		createPopupMenu(firstRow,column,_pivotTablePane,table).show(combo, (int)combo.getMousePosition().getX(),(int)combo.getMousePosition().getY());
//		}

	}

	public JPopupMenu createPopupMenu(final int row,final int col, final DecisionTablePane _pivotTablePane, final JideTable table) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Show Calendar");
		menuItem.setIcon(ImageIconsFactory.createImageIcon("calendar.gif"));
		menuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try{
							IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
							new DateTimeFieldCalendar(window.getShell(),
									_pivotTablePane,
									table,
									row,
									col).open();
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}});
			}});
		popupMenu.add(menuItem);
		return popupMenu;
	}

	/**
	 * @param decisionTableRuleSet
	 * @param tableRuleVariable
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	private boolean isDateTimePropertyType(TableRuleSet decisionTableRuleSet,TableRuleVariable tableRuleVariable){
		String ID = tableRuleVariable.getColId();
		EList<Column> columns = decisionTableRuleSet.getColumns().getColumn();
		for(Column col:columns){
			if(col.getId().equalsIgnoreCase(ID)){
				return DecisionTableUtil.checkDateType(col.getPropertyType());
			}
		}
		return false;
	}


	public String getCellValue() {
		return cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
}
