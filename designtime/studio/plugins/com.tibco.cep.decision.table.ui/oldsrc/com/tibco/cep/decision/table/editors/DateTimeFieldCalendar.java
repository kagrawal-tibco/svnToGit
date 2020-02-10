package com.tibco.cep.decision.table.editors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelFactoryImpl;
import com.tibco.cep.decisionproject.util.DTConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class DateTimeFieldCalendar extends Dialog {

	private DateTime calendar;
	private DateTime time;
	public static final String DATE_TIME_PATTERN = DTConstants.DATE_TIME_PATTERN;
	final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
	private Calendar dcalendar;
	private JideTable table; 
	private int selectedRow;
	private int selectedColumn;
	private DecisionTablePane pane;
	private Shell shell;
	public DateTimeFieldCalendar(Shell parentShell, DecisionTablePane pane,JideTable table , int selectedRow , int selectedColumn) {
		super(parentShell);
		this.shell = parentShell;
		this.table = table;
		this.selectedRow = selectedRow;
		this.selectedColumn = selectedColumn;
		this.pane = pane;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		newShell.setText("DateTime Field Calendar"); //$NON-NLS-1$
		super.configureShell(newShell);
	}
	private Text hrtext;
	private Text mintext;
	private Text sectext;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		parent.setLayout(new GridLayout(1, false));
		parent.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
		GridData gd = new GridData();
		gd = new GridData();
		calendar = new DateTime(parent, SWT.CALENDAR | SWT.BORDER);

		gd = new GridData();
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.LEFT;
		gd.widthHint = 225;
		calendar.setLayoutData(gd);
		calendar.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

		time = new DateTime(parent, SWT.TIME | SWT.LONG);
		gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
		gd.heightHint = 25;
		gd.widthHint = 225;
		time.setLayoutData(gd);
		String date  = "";
		if (table.getValueAt(selectedRow, selectedColumn) instanceof TableRuleVariable) {
			TableRuleVariable drc = (TableRuleVariable) table.getValueAt(selectedRow, selectedColumn);
			if (drc != null) {
				//EList<String> textList = drc.getExpression().getBodies();
				String text = drc.getExpr();
				if (text != null) {
					date  = text;
				}
			}
		}
		
		Group diffGroup = new Group(parent,SWT.NONE);
		Color color = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
		diffGroup.setBackground(color);
		diffGroup.setText("Difference");
		diffGroup.setLayout(new GridLayout(3,false));
		gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
		gd.widthHint = 225;
		diffGroup.setLayoutData(gd);
		Label label = new Label(diffGroup,SWT.NONE);
		label.setText("Hour");
		label.setBackground(color);
		label = new Label(diffGroup,SWT.NONE);
		label.setText("Minute");
		label.setBackground(color);
		label = new Label(diffGroup,SWT.NONE);
		label.setText("Second");
		label.setBackground(color);
		hrtext = new Text(diffGroup,SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 55;
		hrtext.setLayoutData(gd);
		mintext = new Text(diffGroup,SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 55;
		mintext.setLayoutData(gd);
		sectext = new Text(diffGroup,SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 55;
		sectext.setLayoutData(gd);
		
		if(date.equalsIgnoreCase("")){
			date = getFormattedDateTime();
		}
		try {
			dcalendar = new GregorianCalendar();
			dcalendar.setTime(sdf.parse(date));
			calendar.setDay(dcalendar.get(Calendar.DAY_OF_MONTH));
			calendar.setMonth(dcalendar.get(Calendar.MONTH));
			calendar.setYear(dcalendar.get(Calendar.YEAR));
			time.setHours(dcalendar.get(Calendar.HOUR));
			time.setMinutes(dcalendar.get(Calendar.MINUTE));
			time.setSeconds(dcalendar.get(Calendar.SECOND));
	
		} catch (ParseException e1) {
			MessageDialog.openError(shell, "Error", "Enter valid date time");
		}
		return parent;
	}

	private String getFormattedDateTime() {
		return sdf.format(new Date());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(2, false);
		parent.setLayout(glayout);
		parent.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
		Button ok = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		ok.setText("Ok"); //$NON-NLS-1$
		ok.setLayoutData(gd);

		Button cancel = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		cancel.setText("Cancel"); //$NON-NLS-1$
		cancel.setLayoutData(gd);

		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});

		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dcalendar = new GregorianCalendar(calendar.getYear(), calendar
						.getMonth(), calendar.getDay(), time.getHours(), time
						.getMinutes(), time.getSeconds());
				
				try{
					dcalendar.add(Calendar.HOUR, Integer.parseInt(hrtext.getText()));
					dcalendar.add(Calendar.MINUTE, Integer.parseInt(mintext.getText()));
					dcalendar.add(Calendar.SECOND, Integer.parseInt(sectext.getText()));
				setDateTime(pane, selectedRow, selectedColumn,sdf.format(dcalendar.getTime()));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				close();
			}
		});

	}
	private TableRuleVariable ruleVariable;
	/**
	 * @param pane
	 * @param selectedRow
	 * @param selectedColumn
	 * @param value
	 */
	protected void setDateTime(DecisionTablePane pane ,final int selectedRow , final int selectedColumn, String value) {
		ruleVariable = DtmodelFactoryImpl.eINSTANCE.createTableRuleVariable();
		//Expression expression = ruleVariable.getExpression();
		String expr = ruleVariable.getExpr();
		if (expr == null) {
			//expression = DtmodelFactoryImpl.eINSTANCE.createExpression();
			ruleVariable.setExpr("");
		}
		ruleVariable.setExpr(value);
		if (pane instanceof DecisionTablePane) {
			
			if (selectedRow != -1 && selectedColumn != -1){
					TableCellEditor cellEditor = 
						table.getCellEditor(selectedRow, selectedColumn);
				if (cellEditor instanceof DefaultRuleVariableCellEditor) {
//					if (RuleVariableCellEditor.isValueAllowed(ruleVariable, (RuleVariableCellEditor)cellEditor)){
//													
//								
//						//pane.getTableScrollPane()							
//						
//					} 
					((DefaultRuleVariableCellEditor)cellEditor).setEditorValue(ruleVariable);
					if (table.isEditing()) {
						table.getCellEditor().cancelCellEditing();
					}								
					table.setValueAt(ruleVariable, selectedRow, selectedColumn);
				}
			}
		} else {
			TableScrollPane tableScrollPane = pane.getTableScrollPane();
					
			JTable rowHeaderTable = tableScrollPane.getRowHeaderTable();
			//JTable columnHeaderTable = tableScrollPane.
			if (tableScrollPane.getSelectedRow() != -1) {
				tableScrollPane.setValueAt(ruleVariable,
						tableScrollPane.getSelectedRow(),
						tableScrollPane.getSelectedColumn());
			}
			if (rowHeaderTable.getSelectedRow() != -1) {
				rowHeaderTable.setValueAt(
								ruleVariable,
								rowHeaderTable.getSelectedRow(),
								rowHeaderTable.getSelectedColumn());

			}
			//FIXME
			/*if (pane.getColumnHeaderTable().getSelectedRow() != -1) {
				pane.getColumnHeaderTable()
						.setValueAt(
								ruleVariable,
								pane.getColumnHeaderTable()
										.getSelectedRow(),
										pane.getColumnHeaderTable()
										.getSelectedColumn());
			}*/
		}
	}

}
