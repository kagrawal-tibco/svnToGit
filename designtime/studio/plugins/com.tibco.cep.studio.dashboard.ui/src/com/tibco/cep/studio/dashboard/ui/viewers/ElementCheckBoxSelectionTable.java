package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;

/**
 * @ *
 */
public class ElementCheckBoxSelectionTable {

	private Composite clientComposite;

	private TableColumnInfo[] columnInfos;

	private Table table;

	public ElementCheckBoxSelectionTable(Composite parent, TableColumnInfo[] columnInfos) {
		clientComposite = new Composite(parent, SWT.NONE);
		table = new Table(clientComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
		this.columnInfos = columnInfos;
		initTable();
	}

	public ElementCheckBoxSelectionTable(IManagedForm managedForm, Composite parent, TableColumnInfo[] columnInfos) {
		clientComposite = managedForm.getToolkit().createComposite(parent, SWT.NONE);
		table = managedForm.getToolkit().createTable(clientComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
		this.columnInfos = columnInfos;
		initTable();
	}

	protected void initTable() {
		TableColumnLayout tableLayout = new TableColumnLayout();
		clientComposite.setLayout(tableLayout);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 100;
		clientComposite.setLayoutData(gd);

//		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(table);

		for (int i = 0; i < columnInfos.length; i++) {
			TableColumnInfo columnInfo = columnInfos[i];
			TableColumn column = new TableColumn(table, SWT.LEFT, i);
			column.setText(columnInfo.getTitle());
			column.setResizable(columnInfo.getLayoutData().resizable);
			tableLayout.setColumnData(column, columnInfo.getLayoutData());
		}

	}

	public Control getControl(){
		return clientComposite;
	}

	public TableColumnInfo[] getColumnInfos() {
		return columnInfos;
	}

	Table getTable() {
		return table;
	}

}
