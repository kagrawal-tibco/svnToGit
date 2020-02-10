package com.tibco.cep.studio.dashboard.ui.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.Assertion;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class ElementSelector extends TableViewer {

	private static String[] columnNames = new String[] { "Name", "Folder", LocalConfig.PROP_KEY_DISPLAY_NAME, "Description", "ParentElement" };

	private LocalElement[] elementChoices;

	private Action selectAll;

	private Action unselectAll;

	private Action invertSelection;

	public ElementSelector(Composite parent, LocalElement[] elementChoices, boolean multiSelect, boolean showContextMenu) {
		super(createTable(parent, false, multiSelect));
		Assertion.isNull(elementChoices);
		initViewer(elementChoices, multiSelect, showContextMenu);
	}

	public ElementSelector(IManagedForm mform, Composite parent, LocalElement[] elementChoices, boolean multiSelect, boolean showContextMenu) {
		super(createTable(mform, parent, false, multiSelect));
		Assertion.isNull(elementChoices);
		initViewer(elementChoices, multiSelect, showContextMenu);
	}

	public ElementSelector(Composite parent, LocalElement[] elementChoices, boolean showContainer, boolean multiSelect, boolean showContextMenu) {
		super(createTable(parent, showContainer, multiSelect));
		Assertion.isNull(elementChoices);
		initViewer(elementChoices, multiSelect, showContextMenu);
	}

	public ElementSelector(IManagedForm mform, Composite parent, LocalElement[] elementChoices, boolean showContainer, boolean multiSelect, boolean showContextMenu) {
		super(createTable(mform, parent, showContainer, multiSelect));
		Assertion.isNull(elementChoices);
		initViewer(elementChoices, multiSelect, showContextMenu);
	}

	private void initViewer(LocalElement[] input, boolean multiSelect, boolean showContextMenu) {
		setContentProvider(new ArrayContentProvider());
		setLabelProvider(new ElementLabelProvider());
		setSorter(new ElementNameSorter());
		setInput(input);
		if (multiSelect == true) {
			//create all actions
			createActions();
			if (showContextMenu == true) {
				//hook up the actions
				MenuManager menuMgr = new MenuManager("#PopupMenu");
				menuMgr.add(selectAll);
				menuMgr.add(unselectAll);
				menuMgr.add(new Separator());
				menuMgr.add(invertSelection);
				getTable().setMenu(menuMgr.createContextMenu(getTable()));
			}
		}
	}

	private void createActions() {
		selectAll = new Action("Select All", SWT.NONE) {

            public void run() {
            	setSelection(new StructuredSelection(elementChoices));
            	refresh();
            }

        };
        selectAll.setToolTipText("Select all");

        unselectAll = new Action("Unselect All", SWT.NONE) {

            public void run() {
            	setSelection(StructuredSelection.EMPTY);
                refresh();
            }
        };
        unselectAll.setToolTipText("Unselect all");

        invertSelection = new Action("Invert Selection", SWT.NONE) {
            public void run() {
                List<LocalElement> newSelections = new LinkedList<LocalElement>(Arrays.asList(elementChoices));
                newSelections.removeAll(getSelectedElements());
                setSelection(new StructuredSelection(newSelections));
                refresh();
            }
        };
        invertSelection.setToolTipText("Invert Selection");
	}

	public Action getSelectAllAction() {
		return selectAll;
	}

	public Action getUnselectAllAction() {
		return unselectAll;
	}

	public Action getInvertSelectionAction() {
		return invertSelection;
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if (input != elementChoices) {
			if (input instanceof Object[]) {
				Object[] inputArray = (Object[]) input;
				elementChoices = new LocalElement[inputArray.length];
				for (int i = 0; i < inputArray.length; i++) {
					elementChoices[i] = (LocalElement) inputArray[i];
				}
			}
		}
	}

	private static Table createTable(Composite parent, boolean showContainer, boolean multiSelect) {
		Assertion.isNull(parent);
		int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION;
		if (multiSelect == true) {
			style = style | SWT.MULTI;
		}
		Table table = new Table(parent, style);
		internalCreateTable(table, showContainer);
		return table;
	}

	private static Table createTable(IManagedForm mform, Composite parent, boolean showContainer, boolean multiSelect) {
		Assertion.isNull(mform);
		Assertion.isNull(parent);
		int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION;
		if (multiSelect == true) {
			style = style | SWT.MULTI;
		}
		Table table = mform.getToolkit().createTable(parent, style);
		internalCreateTable(table, showContainer);
		return table;
	}

	private static void internalCreateTable(Table table, boolean showContainer) {
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 100;
		table.setLayoutData(gd);

		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(table);

		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText(columnNames[0]);
		tableLayout.addColumnData(new ColumnWeightData(1,true));

		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(columnNames[1]);
		tableLayout.addColumnData(new ColumnWeightData(1,true));

		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText(columnNames[2]);
		tableLayout.addColumnData(new ColumnWeightData(1,true));

		column = new TableColumn(table, SWT.LEFT, 3);
		column.setText(columnNames[3]);
		tableLayout.addColumnData(new ColumnWeightData(1,true));

		if (true == showContainer) {
			column = new TableColumn(table, SWT.LEFT, 4);
			column.setText(columnNames[3]);
			tableLayout.addColumnData(new ColumnWeightData(1,true));
		}
	}

//	public void setLayoutData(Object layoutData) {
//		getTable().setLayoutData(layoutData);
//		getTable().layout();
//	}

	public LocalElement getSelectedElement() {
		List<LocalElement> selectedElements = getSelectedElements();
		if (selectedElements.isEmpty() == true) {
			return null;
		}
		return selectedElements.get(0);
	}

	public List<LocalElement> getSelectedElements() {
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		if (selection.isEmpty() == true) {
			return Collections.emptyList();
		}
		List<LocalElement> selectedLocalElements = new ArrayList<LocalElement>(selection.size());
		for (Object selectedObj : selection.toArray()) {
			selectedLocalElements.add((LocalElement) selectedObj);
		}
		return selectedLocalElements;
	}

}