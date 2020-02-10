/**
 * 
 */
package com.tibco.cep.studio.tester.ui.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.tester.ui.editor.data.TestDataEditor;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * @author mgujrath
 * 
 */
public class DomainValuesCellDialog extends Dialog implements SelectionListener {

	protected String title;
	private String valueToBePassed, initializationString, columnName,columnType;
	private LinkedHashMap<Integer, Object> model;
	private TableViewer tableViewer;
	private TestDataEditor editor;
	private int tableCounter = 0, counter = 0;
	private String[][] domainEntries;

	public DomainValuesCellDialog(TestDataEditor editor, Shell parentShell,
			String columnName, String columnType, String dialogTitle,
			String initializationString, String[][] domainEntries) {
		super(parentShell);
		this.columnName = columnName;
		this.columnType = columnType;
		this.title = dialogTitle == null ? JFaceResources
				.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		model = new LinkedHashMap<Integer, Object>();
		this.editor = editor;
		this.initializationString = initializationString;
		this.domainEntries = domainEntries;

	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

		
	}

	abstract class EntityLabelProvider implements ITableLabelProvider {
	}

	protected Control createDialogArea(Composite parent) {

		Table tableReference;
		List<HashMap<String, String>> domainInputList = new ArrayList<HashMap<String, String>>();
		for (String[] map : domainEntries) {
			HashMap<String, String> domainMap = new HashMap<String, String>();
			domainMap.put(map[0], map[1]);
			domainInputList.add(domainMap);
		}
		Composite composite = new Composite(parent, 0);
		composite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 300;
		data.widthHint = 300;
		composite.setLayoutData(data);
		tableViewer = new TableViewer(composite, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);

		tableViewer.setContentProvider(new EntityContentProviderSwt());
		tableViewer.setLabelProvider(new EntityLabelProvider() {

			@SuppressWarnings("rawtypes")
			@Override
			public String getColumnText(Object element, int columnIndex) {
				@SuppressWarnings("rawtypes")
				HashMap<String, String> map = (HashMap) element;
				String key = map.keySet().iterator().next();
				switch (columnIndex) {
				case 0:
					return key;
				case 1:
					return map.get(key);

				}
				return null;

			}

			@Override
			public void addListener(ILabelProviderListener listener) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {
			}

			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		// Set up the table
		Table table = tableViewer.getTable();
		String[] tableColumnNames = { "Value", "Description" };
		table.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

		for (int i = 0; i < 2; i++) {
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			tableColumn.setText(tableColumnNames[i]);

		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).pack();
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setColumnProperties(tableColumnNames);
		tableReference = table;
		autoTableLayout(table);
		// table.getColumn(0).setResizable(false);
		// initializeTable();
		tableViewer.setInput(domainInputList);
		tableViewer.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {

				TableItem item = (TableItem) event.item;
				valueToBePassed = item.getText();
			}
		});

		table.pack();
		// createCellEditor(tableReference,tableViewer);
		return null;
	}

	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			if (loop == 0)
				autoTableLayout.addColumnData(new ColumnWeightData(100));
			else
				autoTableLayout.addColumnData(new ColumnWeightData(80));
		}
		table.setLayout(autoTableLayout);
	}

	class EntityContentProviderSwt implements IStructuredContentProvider {
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}

		@Override
		public Object[] getElements(Object element) {
			return ((List<?>) element).toArray();
		}
	}

	public String passValue() {
		return valueToBePassed;
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}
   
	@Override
	protected void cancelPressed() {
		// TODO Auto-generated method stub
		super.cancelPressed();
		valueToBePassed="";
	}
	
}
