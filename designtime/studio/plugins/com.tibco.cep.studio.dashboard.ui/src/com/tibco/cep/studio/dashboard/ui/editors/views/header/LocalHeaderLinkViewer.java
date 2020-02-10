package com.tibco.cep.studio.dashboard.ui.editors.views.header;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.viewers.TableToolbar;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.LocalSorterByPosition;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class LocalHeaderLinkViewer extends AttributeViewer {

	private static final String URL_NAME = "Name";

	private static final String URL_LINK = "URL";

	public LocalHeaderLinkViewer(FormToolkit toolKit, Composite parent, IProject project) {
		super(toolKit, parent, true, LocalHeader.ELEMENT_KEY_HEADER_LINK);
	}
	
	@Override
	public void createTable(Composite parent) {
		setColumnNames(new String[] { URL_NAME, URL_LINK});
		Table table = toolKit.createTable(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
		setTable(table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(table);
		int colIdx = 0;
		TableColumn column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(URL_NAME);
		column.setToolTipText(URL_NAME);
		tableLayout.addColumnData(new ColumnWeightData(1,true));
		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(URL_LINK);
		column.setToolTipText(URL_LINK);
		tableLayout.addColumnData(new ColumnWeightData(3,true));
		
		//set the layout for the table 
		GridData gridData = new GridData(SWT.FILL,SWT.FILL,true,false);
		gridData.heightHint = 105;
		table.setLayoutData(gridData);
	}
	
	@Override
	public void createTableViewer() {
		TableViewer tableViewer = new TableViewer(getTable());
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(getColumnNames());
		int idx = 0;
		// Create the cell editors
		CellEditor[] editors = new CellEditor[getColumnNames().length];
		/*
		 * Column 0 URL Name
		 */
		editors[idx++] = new TextCellEditor(getTable());
		/*
		 * Column 1 URL Link
		 */
		editors[idx++] = new TextCellEditor(getTable());
		// Assign the cell editors to the viewer
		tableViewer.setCellEditors(editors);
		tableViewer.setCellModifier(new LocalHeaderLinkCellModifier(this));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LocalHeaderLinkLabelProvider());
		tableViewer.setSorter(new LocalSorterByPosition());
		setTableViewer(tableViewer);
	}
	
	@Override
	public void setLocalElement(LocalElement localElement) throws Exception {
		super.setLocalElement(localElement);
		//update the add button 
		updateAddButtonState();
	}
	
	@Override
	protected void handleAdd() {
		if (isLinkLimiteReached() == true) {
			MessageDialog.openError(getTable().getShell(), "Add new link", "Max limit for header links reached");
			return ;
		}
		super.handleAdd();
		updateAddButtonState();
	}
	
	@Override
	protected void handleDelete() {
		super.handleDelete();
		updateAddButtonState();
	}

	private void updateAddButtonState(){
		if (false == toolBar.isLocked(TableToolbar.TOOL_ITEM_ADD)) {
			toolBar.getToolItem(TableToolbar.TOOL_ITEM_ADD).setEnabled(!isLinkLimiteReached());
		}
	}
	
	private boolean isLinkLimiteReached(){
		try {
			LocalParticle headerLinksParticle = getLocalElement().getParticle(LocalHeader.ELEMENT_KEY_HEADER_LINK);
			return getLocalElement().getChildren(LocalHeader.ELEMENT_KEY_HEADER_LINK).size() >= headerLinksParticle.getMaxOccurs();
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING,DashboardUIPlugin.PLUGIN_ID,"could not determine if link limit has reached for "+getLocalElement(),e));
			return true;
		}
	}
	
}