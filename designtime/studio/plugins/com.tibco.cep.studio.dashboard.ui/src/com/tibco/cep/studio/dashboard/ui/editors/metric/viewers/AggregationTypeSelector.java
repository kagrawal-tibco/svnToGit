package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import java.util.Arrays;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class AggregationTypeSelector extends SelectionStatusDialog {

	private TableViewer tblViewer_AggrTypes;

	private Text txt_Description;

	private String[] aggregationTypes;

	private String aggregationType;

	public AggregationTypeSelector(Shell parent, String[] aggregationTypes, String aggregationType) {
		super(parent);
		this.aggregationTypes = aggregationTypes;
		this.aggregationType = aggregationType;
		setTitle(Messages.getString("metric.aggregationtype.wizard.title"));
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
		label.setText(Messages.getString("metric.aggr.types"));

		// create the aggregation selection table
		tblViewer_AggrTypes = new TableViewer(composite, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tblViewer_AggrTypes.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(tblViewer_AggrTypes.getTable());
		TableColumn column = new TableColumn(tblViewer_AggrTypes.getTable(), SWT.LEFT, 0);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(1, false));

		// create description composite
		Group grp_description = new Group(composite, SWT.NONE);
		grp_description.setText("Description");

		GridLayout grpDescriptionLayout = new GridLayout(1, false);
		grpDescriptionLayout.marginHeight = 10;
		grpDescriptionLayout.marginWidth = 10;
		grp_description.setLayout(grpDescriptionLayout);
		grp_description.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		txt_Description = new Text(grp_description, SWT.WRAP | SWT.MULTI | SWT.READ_ONLY);
		GridData txtDescriptionLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		txtDescriptionLayoutData.heightHint = 50;
		txt_Description.setLayoutData(txtDescriptionLayoutData);

		// populate the table
		tblViewer_AggrTypes.setContentProvider(new ArrayContentProvider());
		tblViewer_AggrTypes.setInput(aggregationTypes);

		// add listener to table
		tblViewer_AggrTypes.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				aggregateTypeSelectionChanged();

			}

		});

		tblViewer_AggrTypes.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				aggregateTypeSelectionChanged();
				// if (getButton(OK) != null && getButton(OK).isEnabled() == true) {
				// okPressed();
				// }
			}

		});

		// update the table to incoming selection
		tblViewer_AggrTypes.setSelection(new StructuredSelection(aggregationType));

		return composite;
	}

	@Override
	protected void computeResult() {
		if (aggregationType == null) {
			setResult(null);
		}
		setResult(Arrays.asList(aggregationType));
	}

	@Override
	protected void cancelPressed() {
		setResult(null);
		super.cancelPressed();
	}

	private void aggregateTypeSelectionChanged() {
		IStructuredSelection selection = (IStructuredSelection) tblViewer_AggrTypes.getSelection();
		if (selection.isEmpty() == true) {
			aggregationType = null;
			txt_Description.setText("");
		} else {
			aggregationType = (String) selection.getFirstElement();
			txt_Description.setText(Messages.getString("metric.aggregationtype." + aggregationType.toLowerCase() + ".description"));
		}
	}

}