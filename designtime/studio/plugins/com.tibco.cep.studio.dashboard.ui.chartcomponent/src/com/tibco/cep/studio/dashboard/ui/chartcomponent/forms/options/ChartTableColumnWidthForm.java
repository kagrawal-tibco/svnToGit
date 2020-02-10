package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.table.TableColumnSizer;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartTableColumnWidthForm extends BaseForm {
	
	private Button btn_MakeAllEqual;
	private SelectionListener btn_MakeAllEqualSelectionListener;

	private Button btn_MakeValueEqual;
	private SelectionListener btn_MakeValueEqualSelectionListener;

	private Table tbl_ColumnSizes;
	private TableViewer tblViewer_ColumnSizes;
	private ControlListener tableSizeListener;
	private TableColumnControlListener tableColumnSizeListener;
	
	private TableColumnSizer tableColumnSizer;
	
	private boolean multiModeOn;
	
	public ChartTableColumnWidthForm(FormToolkit formToolKit, Composite parent) {
		super("Column Widths", formToolKit, parent, false);
		tableColumnSizeListener = new TableColumnControlListener();
	}
	
	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, true));

		btn_MakeAllEqual = createButton(formComposite, "Reset All Columns", SWT.PUSH);
		btn_MakeAllEqual.setToolTipText("Resets all the columns to be equal width");
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		btn_MakeAllEqual.setLayoutData(layoutData);

		btn_MakeValueEqual = createButton(formComposite, "Reset All Value Columns", SWT.PUSH);
		btn_MakeValueEqual.setToolTipText("Reset all the value columns to be equal width");
		btn_MakeValueEqual.setLayoutData(layoutData);

		tbl_ColumnSizes = createTable(formComposite, SWT.SINGLE | SWT.FULL_SELECTION);
		GridData tbl_ColumnSizesLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		tbl_ColumnSizesLayoutData.heightHint = 50;
		tbl_ColumnSizes.setLayoutData(tbl_ColumnSizesLayoutData);

		tbl_ColumnSizes.setHeaderVisible(true);
		tbl_ColumnSizes.setLinesVisible(true);

		tblViewer_ColumnSizes = new TableViewer(tbl_ColumnSizes);
		tblViewer_ColumnSizes.setContentProvider(new ArrayContentProvider());
		tblViewer_ColumnSizes.setLabelProvider(new ColumnSizeLabelProvider());
		tblViewer_ColumnSizes.setCellModifier(new ColumnSizeModifier());
		tblViewer_ColumnSizes.setSorter(null);
	}
	
	@Override
	protected void doEnableListeners() {
		if (tableSizeListener == null) {
			tableSizeListener = new TableSizeListener();
		}
		tbl_ColumnSizes.addControlListener(tableSizeListener);
		tableColumnSizeListener.enable = true;
		
		if (btn_MakeAllEqualSelectionListener == null) {
			btn_MakeAllEqualSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					allEqualClicked();
				}

			};
		}
		btn_MakeAllEqual.addSelectionListener(btn_MakeAllEqualSelectionListener);

		if (btn_MakeValueEqualSelectionListener == null) {
			btn_MakeValueEqualSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					valueEqualClicked();
				}

			};
		}
		btn_MakeValueEqual.addSelectionListener(btn_MakeValueEqualSelectionListener);
	}

	@Override
	protected void doDisableListeners() {
		tbl_ColumnSizes.removeControlListener(tableSizeListener);
		tableColumnSizeListener.enable = false;
		btn_MakeAllEqual.removeSelectionListener(btn_MakeAllEqualSelectionListener);
		btn_MakeValueEqual.removeSelectionListener(btn_MakeValueEqualSelectionListener);
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		tableColumnSizer = null;
		if (newLocalElement != null) {
			tableColumnSizer = new TableColumnSizer(newLocalElement);
			multiModeOn = "MultiMeasure".equalsIgnoreCase(newLocalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_SUBTYPES));
		}
	}
	
	

	@Override
	public void refreshEnumerations() {
		try {
			// remove any existing columns
			TableColumn[] columns = tbl_ColumnSizes.getColumns();
			for (TableColumn tableColumn : columns) {
				tableColumn.removeControlListener(tableColumnSizeListener);
				tableColumn.dispose();
			}
			tblViewer_ColumnSizes.setColumnProperties(new String[] {});
			tblViewer_ColumnSizes.setCellEditors(null);
			if (tableColumnSizer != null) {
				// set columns
				// Windows has a bug which makes first color right aligned
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=39568
				//always add the fake column 
				TableColumn fakeColumn = new TableColumn(tbl_ColumnSizes, SWT.CENTER);
				fakeColumn.setText("Fake");
				fakeColumn.setResizable(false);
				fakeColumn.setMoveable(false);
				//now get the actual table columns 
				columns = getTableColumns();
				//initialize the column properties and editors 
				int totalColumnCnt = columns.length + 1;
				String[] columnProperties = new String[totalColumnCnt];
				CellEditor[] editors = new CellEditor[totalColumnCnt];
				//set first for the fake
				int i = 0;
				columnProperties[i] = fakeColumn.getText();
				editors[i] = null;
				i++;
				while (i < totalColumnCnt) {
					columnProperties[i] = String.valueOf(i);//columns[i - 1].getText();
					editors[i] = new TextCellEditor(tbl_ColumnSizes);
					i++;
				}
//				int columnCnt = seriesConfigs.size() + 2;
//				String[] columnNames = new String[columnCnt];
//				CellEditor[] editors = new CellEditor[columnNames.length];
//				for (int i = 0; i < columnCnt; i++) {
//					TableColumn column = new TableColumn(tbl_ColumnSizes, SWT.CENTER);
//					if (i == 0) {
//						column.setText("Fake");
//					} else if (i == 1) {
//						column.setText(visualization.getPropertyValue("CategoryColumnHeaderName"));
//					} else {
//						boolean hasIndicatorColumn = !seriesConfigs.get(i - 2).isPropertyValueSameAsDefault("IndicatorValueColumnField");
//						if (hasIndicatorColumn == true) {
//							column.setText(seriesConfigs.get(i - 2).getPropertyValue("IndicatorValueColumnFieldHeaderName"));
//						} else {
//							column.setText(seriesConfigs.get(i - 2).getPropertyValue("TextValueColumnFieldHeaderName"));
//						}
//					}
//					column.setResizable(false);
//					column.setMoveable(false);
//					columnNames[i] = column.getText();
//					if (i == 0) {
//						editors[i] = null;
//					} else {
//						editors[i] = new TextCellEditor(tbl_ColumnSizes);
//					}
//				}
				tblViewer_ColumnSizes.setColumnProperties(columnProperties);
				tblViewer_ColumnSizes.setCellEditors(editors);
			}
			resizeColumns();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not read widths from " + localElement, e));
		}
	}
	
	private TableColumn[] getTableColumns() throws Exception {
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		//if multi mode then we have 2 column array else we have series cnt array 
		TableColumn[] columns = new TableColumn[multiModeOn == true ? 2 : seriesConfigs.size() + 1];
		columns[0] = new TableColumn(tbl_ColumnSizes, SWT.CENTER);
		columns[0].setText(visualization.getPropertyValue("CategoryColumnHeaderName"));
		columns[0].setToolTipText(columns[0].getText());
		columns[0].setResizable(true);
		columns[0].setMoveable(false);
		columns[0].addControlListener(tableColumnSizeListener);
		for (int i = 1; i < columns.length; i++) {
			columns[i] = new TableColumn(tbl_ColumnSizes, SWT.CENTER);
			boolean hasIndicatorColumn = !seriesConfigs.get(i-1).isPropertyValueSameAsDefault("IndicatorValueColumnField");
			if (hasIndicatorColumn == true) {
				columns[i].setText(seriesConfigs.get(i-1).getPropertyValue("IndicatorValueColumnFieldHeaderName"));
			} else {
				columns[i].setText(seriesConfigs.get(i-1).getPropertyValue("TextValueColumnFieldHeaderName"));
			}
			columns[i].setToolTipText(columns[i].getText());
			columns[i].setMoveable(false);
			if (i+1 < columns.length) {
				columns[i].setResizable(true);
				columns[i].addControlListener(tableColumnSizeListener);
			}
			else {
				columns[i].setResizable(false);
			}
		}
		return columns;
	}
	
	@Override
	public void refreshSelections() {
		tblViewer_ColumnSizes.setInput(new LocalElement[] { localElement });
		if (multiModeOn == true) {
			btn_MakeValueEqual.setEnabled(false);
		}
		else {
			try {
				LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
				int seriesCnt = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE).size();
				if (seriesCnt < 2) {
					btn_MakeValueEqual.setEnabled(false);
				}
				else {
					btn_MakeValueEqual.setEnabled(true);
				}
			} catch (Exception e) {
				btn_MakeValueEqual.setEnabled(true);
			}
		}
	}



	private void allEqualClicked() {
		try {
			int[] widths = tableColumnSizer.getWidths();
			if (multiModeOn == false) {
				int baseWidth = 100 / widths.length;
				widths[0] = 100;
				for (int i = 1; i < widths.length; i++) {
					widths[i] = baseWidth;
					widths[0] = widths[0] - baseWidth;
				}
			}
			else {
				for (int i = 0; i < widths.length; i++) {
					widths[i] = 50;
				}
			}
			tableColumnSizer.updateWidths(widths);
			resizeColumns();
			tblViewer_ColumnSizes.refresh(true);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update widths for " + localElement, e));
		}
	}

	private void valueEqualClicked() {
		try {
			if (multiModeOn == false) {
				int[] widths = tableColumnSizer.getWidths();
				// get space available for value columns
				int widthForValueCols = 100 - widths[0];
				// how many value columns do we have?
				int valueColCnt = widths.length - 1;
				// how much will the new value column width be ?
				int baseWidth = widthForValueCols / valueColCnt;
				// updath the value column width
				for (int i = 1; i <= valueColCnt; i++) {
					widths[i] = baseWidth;
				}
				// adjust the category column width to handle round off
				widths[0] = 100 - baseWidth * valueColCnt;
				tableColumnSizer.updateWidths(widths);
				resizeColumns();
				tblViewer_ColumnSizes.refresh(true);
			}
			else {
				//do nothing for safety reasons
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update widths for " + localElement, e));
		}
	}

	private void resizeColumns() {
		disableListeners();
		try {
			Rectangle clientArea = tbl_ColumnSizes.getClientArea();
			// actual width available for columns
			int actualWidth = clientArea.width - 2 * tbl_ColumnSizes.getBorderWidth();
			TableColumn[] columns = tbl_ColumnSizes.getColumns();
			int[] widths = tableColumnSizer.getWidths();
			for (int i = 1; i < columns.length; i++) {
				TableColumn tableColumn = columns[i];
				tableColumn.setWidth((int) (actualWidth * (widths[i - 1] / 100.00)));
			}
			// tbl_ColumnSizes.setSize(clientArea.width, clientArea.height);
		} catch (Exception ex) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not read widths from " + localElement, ex));
		} finally {
			enableListeners();
		}
	}
	
	private void updateWidth(int colIdx, int width){
		try {
			int[] widths = tableColumnSizer.getWidths();
			int delta = widths[colIdx] - width;
			widths[colIdx] = width;
			//update the next width to compensate for this width's change 
			colIdx++;
			if (colIdx < widths.length) {
				widths[colIdx] = widths[colIdx] + delta;
			}
			//update the widths 
			tableColumnSizer.updateWidths(widths);
		} catch (Exception ex) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update width", ex));
		}
	}

	class ColumnSizeLabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return "";
			}
			if (element instanceof LocalElement) {
				try {
					return String.valueOf(tableColumnSizer.getWidths()[columnIndex - 1]);
				} catch (Exception e) {
					log(new Status(IStatus.ERROR, getPluginId(), "could not read column width property from " + element, e));
					return "ERROR";
				}
			}
			return String.valueOf(element);
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
//			System.out.println("ColumnSizeLabelProvider.isLabelProperty(" + element + "," + property + ")");
			return false;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

	}

	class TableSizeListener implements ControlListener {

		@Override
		public void controlMoved(ControlEvent e) {
		}

		@Override
		public void controlResized(ControlEvent e) {
			resizeColumns();
		}

	}

	class ColumnSizeModifier implements ICellModifier {

		@Override
		public boolean canModify(Object element, String property) {
			return true;
		}

		@Override
		public Object getValue(Object element, String property) {
			try {
				int columnIndex = Integer.parseInt(property);
				if (columnIndex == 0) {
					return "";
				}
				return String.valueOf(tableColumnSizer.getWidths()[columnIndex - 1]);
			} catch (Exception e) {
				log(new Status(IStatus.ERROR, getPluginId(), "could not read column width property from " + element, e));
				return "ERROR";
			}
		}

		@Override
		public void modify(Object element, String property, Object value) {
			try {
				int columnIndex = Integer.parseInt(property);
				if (columnIndex != 0) {
					updateWidth(columnIndex-1, Integer.parseInt(String.valueOf(value)));
					resizeColumns();
					tblViewer_ColumnSizes.refresh(true);
				}
			} catch (NumberFormatException e) {
				// don't update the value
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update width", e));
			}
		}

	}
	
	class TableColumnControlListener implements ControlListener {
		
		boolean enable;
		
		TableColumnControlListener() {
			enable = false;
		}

		@Override
		public void controlMoved(ControlEvent e) {
			//do nothing
		}

		@Override
		public void controlResized(ControlEvent e) {
			if (enable == false) {
				return;
			}
			enable = false;
			try {
				double totalTableWidth = tbl_ColumnSizes.getClientArea().width;
				int colCnt = tbl_ColumnSizes.getColumnCount() - 1;
				for (int i = 0; i < colCnt; i++) {
					TableColumn column = tbl_ColumnSizes.getColumn(i+1);
					if (column == e.getSource()) {
						int percentageWidth = (int)((column.getWidth()/totalTableWidth)*100);
						updateWidth(i, percentageWidth);
						break;
					}
				}
				resizeColumns();
				tblViewer_ColumnSizes.refresh(true);
			} catch (Exception ex) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update width", ex));
			} finally {
				enable = true;
			}
		}
		
	}

}
