package com.tibco.cep.studio.dashboard.ui.editors.views.queryparams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.LocalSorterByPosition;
import com.tibco.cep.studio.dashboard.ui.viewers.queryparam.QueryParamLabelProvider;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * @
 *
 */
public class QueryParamEditor extends AttributeViewer implements ISynElementChangeListener {

	public static final Logger LOGGER = Logger.getLogger(QueryParamEditor.class.getName());

	private static final String VALUE_COLUMN = "Value";

	/**
	 * Used to record internal status of <code>LocalElement</code>s.
	 */
	private Map<LocalElement, InternalStatusEnum> _fieldStates = new HashMap<LocalElement, InternalStatusEnum>();

	/**
	 *
	 * @param mform
	 * @param parent
	 */
	public QueryParamEditor(FormToolkit toolKit, Composite parent) {
		super(toolKit, parent, false, LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
	}

	@Override
	public void setLocalElement(LocalElement localElement) throws Exception {
		LocalElement oldElement = getLocalElement();
		super.setLocalElement(localElement);

		// Clean old stuff
		if (oldElement != null) {
			oldElement.unsubscribeAll(this);
		}
		_fieldStates.clear();

		// listen to know when LocalQueryParams are added (see handleAdd)
		localElement.subscribeToAll(this);
		// record initial status of all field elements
		// TODO dchesney Mar 2, 2006 ideally this wouldn't be necessary and
		// element status change notifications would include the old status
		try {
			List<LocalElement> list = localElement.getElementList(childType);
			for (Iterator<LocalElement> it = list.iterator(); it.hasNext();) {
				LocalElement elem = (LocalElement) it.next();
				recordStatus(elem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Remembers the current internal status of <code>field</code> for later use when status change notifications are received.
	 *
	 * @param field
	 *            element whose internal status is recorded
	 */
	private void recordStatus(LocalElement field) {
		_fieldStates.put(field, field.getInternalStatus());
	}

	/**
	 * Returns the last known status of <code>field</code>.
	 *
	 * @param field
	 *            element whose last known status is returned
	 * @return last known status
	 */
	private InternalStatusEnum getLastKnownStatus(LocalElement field) {
		return _fieldStates.get(field);
	}

	// ----------------
	// AttributeViewer
	// ----------------

	/**
	 * Create the Table
	 */
	public void createTable(Composite parent) {

		setColumnNames(new String[] { STATUS_COLUMN, NAME_COLUMN, DATATYPE_COLUMN, VALUE_COLUMN });

		Table table = toolKit.createTable(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		setTable(table);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(table);

		int colIdx = 0;
		// 2nd column with task Description
		TableColumn column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(STATUS_COLUMN);
		column.setToolTipText(STATUS_COLUMN);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(0, false));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(NAME_COLUMN);
		column.setToolTipText(NAME_COLUMN);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(1, false));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(DATATYPE_COLUMN);
		column.setToolTipText(DATATYPE_COLUMN);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(1, false));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(VALUE_COLUMN);
		column.setToolTipText(VALUE_COLUMN);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(2, false));

	}

	/**
	 * Create the TableViewer
	 */
	public void createTableViewer() {

		TableViewer tableViewer = new TableViewer(getTable());
		tableViewer.setUseHashlookup(true);

		tableViewer.setColumnProperties(getColumnNames());

		int idx = 0;

		// Create the cell editors
		CellEditor[] editors = new CellEditor[getColumnNames().length];

		/*
		 * Status
		 */
		editors[idx++] = null;

		/*
		 * Parameter Name
		 */
		editors[idx++] = null;

		/*
		 * Parameter Data Type
		 */
		editors[idx++] = null;

		/*
		 * Value
		 */
		editors[idx++] = new TextCellEditor(getTable());

		// Assign the cell editors to the viewer
		tableViewer.setCellEditors(editors);
		tableViewer.setCellModifier(new QueryParamCellModifier(this));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new QueryParamLabelProvider());
		tableViewer.setSorter(new LocalSorterByPosition());
		setTableViewer(tableViewer);
	}

	public boolean isFieldReorderAllowed() {
		return true;
	}

	/**
	 * Schedules a <code>Runnable</code> to safely add <code>element</code> to the table.
	 *
	 * @param element
	 *            <code>LocalElement</code> to add
	 */
	public void add(LocalElement element) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new AddRunnable(element));
	}

	/**
	 * Schedules a <code>Runnable</code> to safely remove <code>element</code> from the table.
	 *
	 * @param element
	 *            <code>LocalElement</code> to remove
	 */
	public void remove(LocalElement element) {
		recordStatus(element);
		PlatformUI.getWorkbench().getDisplay().asyncExec(new RemoveRunnable(element));
	}

	/**
	 * AttributeViewer doesn't listen to the LocalMetric to know when fields have been added/removed - it uses special logic to refresh the table from handleAdd(). Temporarily stop listening while the add is performed
	 * otherwise we'll get double entries in the table.
	 */
	protected void handleAdd() {
		LocalElement elem = getLocalElement();
		if (elem != null) {
			elem.unsubscribeAll(this);
		}
		super.handleAdd();
		if (elem != null) {
			// start listening again
			elem.subscribeToAll(this);
		}
	}

	// --------------------------
	// ISynElementChangeListener
	// --------------------------

	public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
		// if (newElement != null && newElement instanceof LocalMetricField) {
		// NOTE : AttributeViewer should be doing this, but it currently
		// replaces the entire input to the underlying TableViewer when
		// told. Don't do add() otherwise duplicate entries will result.
		// add((LocalElement)newElement);
		// }
	}

	public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
		// if (removedElement != null && removedElement instanceof LocalMetricField) {
		// NOTE : AttributeViewer should be doing this, but it currently
		// replaces the input to the TableViewer when told.
		// remove((LocalElement)removedElement);
		// }
	}

	public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
		// Empty
	}

	public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
		if (InternalStatusEnum.StatusExisting.equals(status)) {
			if (parent != null && parent instanceof LocalQueryParam) {
				// field state changes to existing when it is "re-added" (i.e.,
				// goes from removed to existing state)
				InternalStatusEnum oldStatus = getLastKnownStatus((LocalQueryParam) parent);
				if (oldStatus != null && InternalStatusEnum.StatusRemove.equals(oldStatus)) {
					if (LOGGER.isLoggable(Level.FINE)) {
						LOGGER.log(Level.FINE, "QueryParamEditor#elementStatusChanged: element=" + parent + ", status=" + status);
					}
					add((LocalElement) parent);
				}
			}
		}
	}

	public String getName() {
		return null;
	}

	public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
		// Empty
	}

	/**
	 * Used to safely add an element to the table.
	 */
	class AddRunnable implements Runnable {
		LocalElement _elem;

		AddRunnable(LocalElement elem) {
			_elem = elem;
		}

		public void run() {
			if (!getTable().isDisposed()) {
				getTableViewer().add(_elem);
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.log(Level.FINE, "LocalMetricFieldViewer#Added: " + _elem);
				}
			}
		}
	}

	/**
	 * Used to safely add an remove an element from the table.
	 */
	class RemoveRunnable implements Runnable {
		LocalElement _elem;

		RemoveRunnable(LocalElement elem) {
			_elem = elem;
		}

		public void run() {
			if (!getTable().isDisposed()) {
				getTableViewer().remove(_elem);
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.log(Level.FINE, "LocalMetricFieldViewer#Removed: " + _elem);
				}
			}
		}
	}
}
