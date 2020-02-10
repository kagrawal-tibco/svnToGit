package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import java.util.Arrays;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class DataTypeSelector extends SelectionStatusDialog {

	private TableViewer tblViewer_dataTypes;

	private String[] dataTypes;

	private String dataType;

	public DataTypeSelector(Shell parent, String[] dataTypes, String dataType) {
		super(parent);
		this.dataTypes = dataTypes;
		this.dataType = dataType;
		setTitle(Messages.getString("metric.datatype.wizard.title"));
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);

		// create the layout with standard margins and spacing
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);

		// set layout data on the composite
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		// create label
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("metric.data.types"));

		// create the aggregation selection table
		tblViewer_dataTypes = new TableViewer(composite, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tblViewer_dataTypes.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(tblViewer_dataTypes.getTable());
		TableColumn column = new TableColumn(tblViewer_dataTypes.getTable(), SWT.LEFT, 0);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(1, false));

		// populate the table
		tblViewer_dataTypes.setContentProvider(new ArrayContentProvider());
		tblViewer_dataTypes.setLabelProvider(new DataTypeLabelProvider());
		// convert the incoming datatypes to PROPERTY_TYPES
		PROPERTY_TYPES[] dataTypeObjs = new PROPERTY_TYPES[dataTypes.length];
		for (int i = 0; i < dataTypeObjs.length; i++) {
			dataTypeObjs[i] = PROPERTY_TYPES.getByName(dataTypes[i]);
		}
		tblViewer_dataTypes.setInput(dataTypeObjs);

		// add listener to table
		tblViewer_dataTypes.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				dataTypeSelectionChanged();
			}

		});

		tblViewer_dataTypes.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				dataTypeSelectionChanged();
				// if (getButton(OK) != null && getButton(OK).isEnabled() == true) {
				// okPressed();
				// }
			}

		});

		// update the table to incoming selection
		// tblViewer_dataTypes.setSelection(new StructuredSelection(dataType));
		tblViewer_dataTypes.getTable().select(getIndex(dataType));

		return composite;
	}

	private int getIndex(String type) {
		int k = 0;
		for (TableItem item : tblViewer_dataTypes.getTable().getItems()) {
			if (item.getText().equals(type)) {
				return k;
			}
			k++;
		}
		return -1;
	}

	@Override
	protected void computeResult() {
		if (dataType == null) {
			setResult(null);
		}
		setResult(Arrays.asList(dataType));
	}

	@Override
	protected void cancelPressed() {
		setResult(null);
		super.cancelPressed();
	}

	private void dataTypeSelectionChanged() {
		IStructuredSelection selection = (IStructuredSelection) tblViewer_dataTypes.getSelection();
		if (selection.isEmpty() == true) {
			dataType = null;
		} else {
			dataType = ((PROPERTY_TYPES) selection.getFirstElement()).getName();
		}
	}

	private class DataTypeLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if (element == null) {
				return "";
			}
			if (element instanceof PROPERTY_TYPES) {
				return ((PROPERTY_TYPES) element).getName();
			}
			return element.toString();
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof PROPERTY_TYPES) {
				return EditorUtils.getPropertyImage((PROPERTY_TYPES) element);
			}
			return null;
		}
	}
}