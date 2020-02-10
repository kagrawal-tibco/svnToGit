/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/**
 * @author shivkumarchelwa
 *
 */
public class TopologyEditor {

	protected Section topologySection;
	Composite sectionClient;
	protected TableViewer tableViewer;
	protected Table tableReference;
	public ToolBarProvider toolBarProvider;
	protected AbstractSaveableEntityEditorPart editor;
	protected List<String> columnNames = new ArrayList<String>();

	public static TopologyEditor newInstance(ChannelFormEditor editor) {
		return new TopologyEditor(editor);
	}

	public TopologyEditor(ChannelFormEditor editor) {
		this.editor = editor;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void createTopologyPart(FormToolkit toolkit, Composite parent, final HashMap<Object, Object> controls) {
		topologySection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		topologySection.setLayoutData(gd);

		sectionClient = toolkit.createComposite(topologySection, SWT.BORDER);
		topologySection.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout());

		sectionClient.setLayoutData(gd);

		createPropertiesTable(sectionClient, controls);

		toolkit.paintBordersFor(sectionClient);
	}

	protected void createPropertiesTable(Composite parent, final HashMap<Object, Object> controls) {
		createPropertiesColumnList();
		createTable(parent, controls);
	}

	public void createCellEditor(List<String> tableColumnsWithType) {
		int editorCount = 0;
		CellEditor[] editors = new CellEditor[tableColumnsWithType.size()];
		int iterator = 0;
		while (iterator < tableColumnsWithType.size()) {
			String columnName = tableColumnsWithType.get(iterator);
			if (columnName.equals(KafkaStreamsEditorContants.INPUTS)) {
				TextCellEditor textCellEditor = new TextCellEditor(tableReference, SWT.WRAP);
				editors[editorCount++] = textCellEditor;
			}
			if (columnName.equals(KafkaStreamsEditorContants.TRANSFORMATION)) {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			iterator++;
		}
		tableViewer.setCellEditors(editors);
	}

	protected Table createTable(Composite composite, final HashMap<Object, Object> controls) {

		TopologyContentProvider contentProvider = new TopologyContentProvider();
		TopologyLabelProvider labelProvider = new TopologyLabelProvider();

		createToolBar(composite);
		controls.put(DriverManagerConstants.DRIVER_KAFKA_STREAMS + CommonIndexUtils.DOT + "Topology.Toolbar.AddItem", toolBarProvider.getAddItem());
		controls.put(DriverManagerConstants.DRIVER_KAFKA_STREAMS + CommonIndexUtils.DOT + "Topology.Toolbar.DeleteItem", toolBarProvider.getDeleteItem());

		Composite tableComp = new Composite(composite, GridData.BEGINNING);
		GridData gdtable = new GridData(GridData.FILL_BOTH);
		tableComp.setLayoutData(gdtable);
		GridLayout gridtablelayout = new GridLayout(1, false);
		tableComp.setLayout(gridtablelayout);

		tableViewer = new TableViewer(tableComp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableViewer.setContentProvider(contentProvider);
		tableViewer.setLabelProvider(labelProvider);
		toolBarProvider.getDeleteItem().setEnabled(false);

		// Set up the table
		Table table = tableViewer.getTable();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 500;
		table.setLayoutData(gd);
		for (int i = 0; i < columnNames.size(); i++) {
			new TableColumn(table, SWT.NONE).setText(columnNames.get(i));
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).pack();
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setColumnProperties(columnNames.toArray(new String[columnNames.size()]));
		tableReference = table;
		autoTableLayout(table);

		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = tableViewer.getTable().getSelectionIndex();
				if (selectionIndex < 0)
					return;
				Transformation selectedTransformation = (Transformation) tableViewer.getTable().getItem(selectionIndex)
						.getData();
				if (getEditor().isEnabled() && isRemovableTransformation(selectedTransformation)) {
					toolBarProvider.getDeleteItem().setEnabled(true);
				} else {
					toolBarProvider.getDeleteItem().setEnabled(false);
				}
			}
		});

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent e) {

				IStructuredSelection selection = (IStructuredSelection) e.getSelection();
				Transformation clickedTransformtion = (Transformation) selection.getFirstElement();

				String precedingType = null;
				int selectionIndex = tableViewer.getTable().getSelectionIndex();
				if (selectionIndex > 0) {
					TableItem precedingItem = tableViewer.getTable().getItem(selectionIndex - 1);
					precedingType = ((Transformation) precedingItem.getData()).getType();
				}

				TransformationDialog diag = new TransformationDialog(editor.getSite().getShell(),
						editor.getProject().getName(), precedingType, clickedTransformtion);
				int ret = diag.open();
				if (ret != Dialog.OK) {
					return;
				}

				Transformation t = diag.getTransformation();

				TopologyContentProvider contentProvider = (TopologyContentProvider) tableViewer.getContentProvider();
				contentProvider.replaceTransformation(selectionIndex, t);

				editor.modified();
				tableViewer.refresh();
			}
		});

		tableViewer.refresh();
		table.pack();

		return table;
	}

	protected AbstractSaveableEntityEditorPart getEditor() {
		return editor;
	}

	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
	}

	private void createToolBar(Composite parent) {
		toolBarProvider = new ToolBarProvider(parent);
		toolBarProvider.setShowBackgroundColor(false);
		toolBarProvider.setShowButtonText(true);
		toolBarProvider.createToolbar(false, false, false, false);

		Listener listener = new Listener() {

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				ToolItem toolItem = (ToolItem) arg0.widget;
				if (toolItem.getText().equalsIgnoreCase(toolBarProvider.getAddItem().getText())) {
					addAction();
				} else if (toolItem.getText().equalsIgnoreCase(toolBarProvider.getDeleteItem().getText())) {
					removeAction();
				}
			}
		};

		toolBarProvider.setAllItemSelectionListener(listener);

	}

	public void addAction() {

		String lastTransformation = null;
		TableItem[] tableItems = tableViewer.getTable().getItems();
		if (tableItems.length > 0) {
			lastTransformation = tableItems[tableItems.length - 1].getText(0);
		}

		TransformationDialog diag = new TransformationDialog(editor.getSite().getShell(), editor.getProject().getName(),
				lastTransformation);
		int ret = diag.open();
		if (ret != Dialog.OK) {
			return;
		}
		Transformation t = diag.getTransformation();

		TopologyContentProvider contentProvider = (TopologyContentProvider) tableViewer.getContentProvider();
		contentProvider.addTransformation(t);

		tableViewer.add(t);
		editor.modified();
		tableViewer.refresh();
	}

	public void removeAction() {
		int selectionIndex = tableViewer.getTable().getSelectionIndex();
		Transformation t = (Transformation) tableViewer.getTable().getItem(selectionIndex).getData();
		TopologyContentProvider contentProvider = (TopologyContentProvider) tableViewer.getContentProvider();
		contentProvider.removeTransformation(t);

		tableViewer.remove(t);
		editor.modified();
		tableViewer.refresh();
	}

	/*
	 * A transformation is removable if: 1. It's the last transformation is the last
	 * transformation in the table 2. It's the first transformation & succeeding
	 * transformation compatible(from same group i.e. kstream, ktable, globalktable)
	 * 2. Both preceding & succeeding transformation are compatible
	 */
	public boolean isRemovableTransformation(Transformation transformation) {
		TopologyContentProvider contentProvider = (TopologyContentProvider) tableViewer.getContentProvider();
		List<Transformation> list = contentProvider.getStreamsTopology().getTransformations();
		int currentIndex = list.indexOf(transformation);
		if (currentIndex == list.size() - 1) {
			return true;
		} else {
			boolean succeedingTypeCompatible = false;
			boolean precedingTypeCompatible = false;
			String currentType = transformation.getType();
			if (currentIndex >= 1) {
				Transformation precedingTransformation = list.get(currentIndex - 1);
				String precedingType = precedingTransformation.getType();
				if ((KSTREAM_TYPES.contains(currentType) && KSTREAM_TYPES.contains(precedingType))
						|| (KTABLE_TYPES.contains(currentType) && KTABLE_TYPES.contains(precedingType))
						|| (KGROUPEDSTREAM_TYPES.contains(currentType)
								&& KGROUPEDSTREAM_TYPES.contains(precedingType))) {
					precedingTypeCompatible = true;
				}
			} else {
				precedingTypeCompatible = true;
			}
			Transformation succeedingTransformation = list.get(currentIndex + 1);
			String succeedingType = succeedingTransformation.getType();
			if ((KSTREAM_TYPES.contains(currentType) && KSTREAM_TYPES.contains(succeedingType))
					|| (KTABLE_TYPES.contains(currentType) && KTABLE_TYPES.contains(succeedingType))
					|| (KGROUPEDSTREAM_TYPES.contains(currentType) && KGROUPEDSTREAM_TYPES.contains(succeedingType))) {
				succeedingTypeCompatible = true;
			}
			return precedingTypeCompatible && succeedingTypeCompatible;
		}
	}

	protected void createPropertiesColumnList() {
		columnNames.add("Transformation");
		columnNames.add("Inputs");
	}

	static class KafkaStreamsEditorContants {
		public static final String TRANSFORMATION = "Transformation";
		public static final String INPUTS = "Inputs";
	}
}
