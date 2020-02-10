package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.exception.SynValidationInfoMessage;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.SortingOrderComparator;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.ui.viewers.TableToolbar;
import com.tibco.cep.studio.dashboard.ui.viewers.TempPreference;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

//FIXME as of now I am making status column's width as 0, we should consider removing it from the system post 4.0 - Anand see 1-ANZ4FC 3/18/2010 3:40 PM
public class AttributeViewer {

//	private Composite content;
	protected Composite content;

	private Table table;

	private TableViewer tableViewer;

	protected TableToolbar toolBar;

	// Set the table column property names
	public static final String STATUS_COLUMN = "!";

	public static final String NAME_COLUMN = "Name";

	public static final String DATATYPE_COLUMN = "Data Type";

	public static final String DESCRIPTION_COLUMN = "Description";

	// Set column names
	private String[] columnNames;

	private boolean isValid = true;

	private IBaseLabelProvider labelProvider;

	protected MultiStateVerifier nameVerifier = new MultiStateVerifier(MultiStateVerifier.VERIFY_NAME_BODY);

	protected MultiStateVerifier valueVerifier = new MultiStateVerifier();

	protected MultiStateVerifier datasizeVerifier = new MultiStateVerifier(MultiStateVerifier.VERIFY_INTEGER);

	private Map<String, MultiStateVerifier> verifierMap = new HashMap<String, MultiStateVerifier>();

	private LocalElement localElement;

	//private LocalFilterBySystemFields localFilterBySystemFields = new LocalFilterBySystemFields();
	protected LocalFilterBySystemFields localFilterBySystemFields = new LocalFilterBySystemFields();

	private ICellModifier cellModifier;

	public boolean editToolbarSupported = false;

	private boolean isReadOnly = false;

	private boolean fieldReorderAllowed = true;

	protected String childType;

	protected FormToolkit toolKit;

	/**
	 * @param parent
	 */
	public AttributeViewer(FormToolkit toolKit, Composite parent, boolean editToolbarSupported, String childType) {
		this.childType = childType;
		this.toolKit = toolKit;
		verifierMap.put(NAME_COLUMN, nameVerifier);
		setEditToolbarSupported(editToolbarSupported);
		addChildControls(parent);
	}

	public void addChildControls(Composite composite) {
		//create table holding composite
		content = new Composite(composite, SWT.NONE);
		if (toolKit != null) {
			toolKit.adapt(content);
		}
		//set layout on the table holding composite
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		content.setLayout(layout);
		// Create the table
		createTable(content);

		//set a layout data if one has not been set in the createTable() API
		if (getTable().getLayoutData() == null) {
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			//gridData.heightHint = 100;
			content.setLayoutData(gridData);
			getTable().setLayoutData(gridData);
		}

		// Create and setup the TableViewer
		try {
			createTableViewer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (true == isEditToolbarSupported()) {
			createToolbar(content);
		}

		if (false == TempPreference.KEY_SHOW_SYSTEM_FIELDS_boolean) {
			getTableViewer().addFilter(localFilterBySystemFields);
		}
//		toolKit.paintBordersFor(content);
		hookListeners();
	}

	protected void hookListeners() {

		getTableViewer().addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {

				IStructuredSelection currentSelection = (IStructuredSelection) tableViewer.getSelection();

				try {
					if (currentSelection.getFirstElement() instanceof LocalElement) {
						LocalElement localElement = (LocalElement) currentSelection.getFirstElement();
						if (false == localElement.isValid() || null != localElement.getValidationMessage()) {
							String message = localElement.getValidationMessage().getMessage(true);
							if (localElement.getValidationMessage() instanceof SynValidationInfoMessage) {
								MessageDialog.openWarning(null, "Field Validation Detail", message);
							} else {
								MessageDialog.openError(null, "Field Validation Detail", message);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				try {
					if (true == isEditToolbarSupported() && false == isReadOnly() /*&& null != getTableViewer().getSelection()*/) {
//						if (false == toolBar.isLocked(TableToolbar.TOOL_ITEM_ADD)) {
//							toolBar.getToolItem(TableToolbar.TOOL_ITEM_ADD).setEnabled(true);
//						}
//						if (false == toolBar.isLocked(TableToolbar.TOOL_ITEM_UP)) {
//							toolBar.getToolItem(TableToolbar.TOOL_ITEM_UP).setEnabled(isFieldReorderAllowed());
//						}
//						if (false == toolBar.isLocked(TableToolbar.TOOL_ITEM_DOWN)) {
//							toolBar.getToolItem(TableToolbar.TOOL_ITEM_DOWN).setEnabled(isFieldReorderAllowed());
//						}
						/*
						 * First enables the delete button
						 */
						if (false == toolBar.isLocked(TableToolbar.TOOL_ITEM_DELETE)) {
							toolBar.getToolItem(TableToolbar.TOOL_ITEM_DELETE).setEnabled(!getTableViewer().getSelection().isEmpty());
							/*
							 * Then disable it only if 1 of the selected element
							 * is readonly
							 *
							 * for (Iterator iter =
							 * structureSelection.iterator(); iter.hasNext();) {
							 * if (false == ((LocalElement)
							 * iter.next()).isEditable()) {
							 * toolBar.getToolItem(TableToolbar
							 * .TOOL_ITEM_DELETE).setEnabled(false); break; } }
							 */
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the Table
	 */
	public void createTable(Composite parent) {

		columnNames = new String[] { STATUS_COLUMN, NAME_COLUMN, DATATYPE_COLUMN, DESCRIPTION_COLUMN };

		int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI;

		table = toolKit.createTable(parent, style);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(table);

		int colIdx = 0;
		// 2nd column with task Description
		TableColumn column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(STATUS_COLUMN);
		column.setToolTipText(STATUS_COLUMN);
		tableLayout.addColumnData(new ColumnWeightData(0,false));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(NAME_COLUMN);
		column.setToolTipText(NAME_COLUMN);
		tableLayout.addColumnData(new ColumnWeightData(1,true));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(DATATYPE_COLUMN);
		column.setToolTipText(DATATYPE_COLUMN);
		tableLayout.addColumnData(new ColumnWeightData(1,true));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(DESCRIPTION_COLUMN);
		column.setToolTipText(DESCRIPTION_COLUMN);
		tableLayout.addColumnData(new ColumnWeightData(3,true));

	}

	/*
	 * This a hack to correctly position/size the cell editors There is a bug in
	 * Eclipse bugzilla for this already
	 *
	 * TODO: Remove this as soon as the fix is in because this really slows down
	 * the refresh of the table
	 */
	public void HackResetTable() {

		// int startColumn = isKeyColumnEnabled() ? 2 : 1;

		TableColumn[] columns = table.getColumns();
		for (int nItem = 0; nItem < table.getItemCount(); nItem++) {
			TableItem item = table.getItem(nItem);
			for (int nCol = 1; nCol < columns.length; nCol++) {
				item.setImage(nCol, null);
			}
		}
	}

	/**
	 * Create the TableViewer
	 *
	 * @throws Exception
	 */
	public void createTableViewer() throws Exception {
		TableLayout layout = new TableLayout();
		table.setLayout(layout);

		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);

		tableViewer.setColumnProperties(columnNames);

		int idx = 0;
		// Create the cell editors
		CellEditor[] editors = new CellEditor[columnNames.length];

		editors[idx++] = null;
		/*
		 * Column 1 property name
		 */
		TextCellEditor textEditor = new TextCellEditor(table);

		((Text) textEditor.getControl()).addVerifyListener(nameVerifier);
		editors[idx++] = textEditor;

		/*
		 * Column 2 datatype combo
		 */
		editors[idx++] = new ComboBoxCellEditor(table, getDataTypeOptions(), SWT.READ_ONLY);

		/*
		 * Column 3 Description
		 */
		textEditor = new TextCellEditor(table);
		editors[idx++] = textEditor;

		// Assign the cell editors to the viewer
		tableViewer.setCellEditors(editors);

		tableViewer.setCellModifier(new AttributeCellModifier(this));

		tableViewer.setContentProvider(new ArrayContentProvider());

		tableViewer.setLabelProvider(labelProvider == null ? new AttributeLabelProvider() : labelProvider);
		tableViewer.addFilter(new LocalFilterByStatus_Removed());

		tableViewer.setSorter(new LocalSorterByPosition());
	}

	protected String[] getDataTypeOptions() {
		return LocalAttribute.SUPPORTED_DATA_TYPES;
	}

	protected void createToolbar(Composite parent) {
		toolBar = new TableToolbar(parent, SWT.VERTICAL);

		/*
		 * add
		 */

		ToolItem item = toolBar.getToolItem(TableToolbar.TOOL_ITEM_ADD);

		if (null != item) {
			boolean lock = false;
			if (isReadOnly() == true || isAddAllowed() == false){
				lock = true;
			}
			toolBar.setLock(TableToolbar.TOOL_ITEM_ADD, lock);
			item.setEnabled(!lock);
			item.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {

					if (isAddAllowed()) {
						handleAdd();
					}

				}
			});
		}

		/*
		 * delete
		 */
		item = toolBar.getToolItem(TableToolbar.TOOL_ITEM_DELETE);

		if (null != item) {
			boolean lock = false;
			if (isReadOnly() == true || isDeleteAllowed() == false){
				lock = true;
			}
			toolBar.setLock(TableToolbar.TOOL_ITEM_DELETE, lock);
			item.setEnabled(false); /* delete should always be false*/
			item.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {

					if (isDeleteAllowed()) {
						handleDelete();
					}
				}
			});
		}

		/*
		 * up
		 */
//		item = toolBar.getToolItem(TableToolbar.TOOL_ITEM_UP);
//
//		if (null != item) {
//
//			item.setEnabled(isReadOnly() ? false : isFieldReorderAllowed());
//			item.addSelectionListener(new SelectionAdapter() {
//
//				// Remove the selection and refresh the view
//				public void widgetSelected(SelectionEvent e) {
//
//					if (null != getTableViewer().getSelection()) {
//						IStructuredSelection structureSelection = (IStructuredSelection) getTableViewer().getSelection();
//						handleMoveUp(structureSelection);
//					}
//
//				}
//
//			});
//		}

		/*
		 * down
		 */
//		item = toolBar.getToolItem(TableToolbar.TOOL_ITEM_DOWN);
//
//		if (null != item) {
//
//			item.setEnabled(isReadOnly() ? false : isFieldReorderAllowed());
//			item.addSelectionListener(new SelectionAdapter() {
//
//				// Remove the selection and refresh the view
//				public void widgetSelected(SelectionEvent e) {
//
//					if (null != getTableViewer().getSelection()) {
//						IStructuredSelection structureSelection = (IStructuredSelection) getTableViewer().getSelection();
//						handleMoveDown(structureSelection);
//					}
//
//				}
//
//			});
//		}

		/*
		 * Show/hide system fields
		 */
		// item =
		// toolBar.getToolItem(TableToolbar.TOOL_ITEM_SHOW_SYSTEM_FIELDS);
		//
		// item.setSelection(TempPreference.KEY_SHOW_SYSTEM_FIELDS_boolean);
		//
		// if (null != item) {
		//
		// item.addSelectionListener(new SelectionAdapter() {
		//
		// public void widgetSelected(SelectionEvent e) {
		//
		// boolean selectionStat = ((ToolItem) e.widget).getSelection();
		// if (true == selectionStat) {
		// getTableViewer().removeFilter(localFilterBySystemFields);
		// }
		// else {
		// getTableViewer().addFilter(localFilterBySystemFields);
		// }
		//
		// TempPreference.KEY_SHOW_SYSTEM_FIELDS_boolean = selectionStat;
		// getTableViewer().refresh();
		//
		// }
		//
		// });
		// }
	}

	/**
	 * @param structureSelection
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private void handleMoveUp(IStructuredSelection structureSelection) {
		for (Iterator iter = structureSelection.iterator(); iter.hasNext();) {
			((LocalElement) iter.next()).moveUp();
		}
		refresh();
	}

	/**
	 * @param structureSelection
	 */
	@SuppressWarnings("unused")
	private void handleMoveDown(IStructuredSelection structureSelection) {
		/*
		 * when multiple elements are selected the move down must execute in
		 * reverse so that it does not overlap itself
		 */
		Object[] selection = structureSelection.toArray();

		for (int i = selection.length - 1; i > -1; i--) {
			((LocalElement) selection[i]).moveDown();

		}
		refresh();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void handleAdd() {
		try {
			LocalElement localElementBase = getLocalElement().createLocalElement(childType);
			/*
			 * If there is a selection in the table get the selection and
			 * determine the hihest ranked element in the selection by sorting
			 * order then move this element below it
			 */
			if (null != getTableViewer().getSelection() && false == getTableViewer().getSelection().isEmpty()) {

				List selectedElements = ((IStructuredSelection) getTableViewer().getSelection()).toList();

				Collections.sort(selectedElements, new SortingOrderComparator());
				LocalElement highestRanked = ((LocalElement) selectedElements.get(selectedElements.size() - 1));

				localElementBase.moveToBelow(highestRanked);
			}

			setInput();

			getTableViewer().setSelection(new StructuredSelection(localElementBase));
			getTableViewer().refresh();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	protected void handleDelete() {

		try {
			if (null != getTableViewer().getSelection()) {

				IStructuredSelection structureSelection = (IStructuredSelection) getTableViewer().getSelection();

				int minSelectedPosition = getMinPosition(structureSelection);

				for (Iterator iter = structureSelection.iterator(); iter.hasNext();) {
					LocalElement attr = (LocalElement) iter.next();
					getLocalElement().removeElementByID(childType, attr.getID(), LocalElement.FOLDER_NOT_APPLICABLE);
				}

				setInput();

				/*
				 * After the deletion try to move the selection to an
				 * appropriate element
				 */
				LocalElement nextSelection = getLocalElement().getNextActiveChild(minSelectedPosition);

				if (null != nextSelection) {
					getTableViewer().setSelection(new StructuredSelection(nextSelection));
				} else {
					nextSelection = getLocalElement().getLastActiveChild();
					if (null != nextSelection) {
						getTableViewer().setSelection(new StructuredSelection(nextSelection));
					}
				}

				getTableViewer().refresh();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	protected int getMinPosition(IStructuredSelection structureSelection) {

		int leastSelectedPosition = Integer.MAX_VALUE;

		try {
			if (null != structureSelection) {
				for (Iterator iter = structureSelection.iterator(); iter.hasNext();) {
					LocalElement L = (LocalElement) iter.next();
					if (L.getSortingOrder() < leastSelectedPosition) {
						leastSelectedPosition = L.getSortingOrder();
					}
				}

			}

			else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return leastSelectedPosition;
	}

	// private int getMaxPosition(IStructuredSelection structureSelection) {
	//
	// int maxSelectedPosition = -1;
	//
	// try {
	// if (null != structureSelection) {
	// for (Iterator iter = structureSelection.iterator(); iter.hasNext();) {
	// LocalAttribute L = (LocalAttribute) iter.next();
	// if (L.getSortingOrder() > maxSelectedPosition) {
	// maxSelectedPosition = L.getSortingOrder();
	// }
	// }
	//
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return maxSelectedPosition;
	// }

	/**
	 * Sets (or resets) the input to the underlying <code>TableViewer</code> to
	 * an array containing the children of the <code>LocalElement</code>
	 * returned by {@link #getLocalElement()}. The children in the array are
	 * those children returned by {@link LocalElement#getChildren(String)} when
	 * passed the argument "Field".
	 *
	 */
	public void setInput() {
		try {
 			if (getLocalElement() != null) {
				List<LocalElement> children = getLocalElement().getChildren(childType);
				getTableViewer().setInput(children.toArray(new LocalElement[children.size()]));
			} else {
				getTableViewer().setInput(new LocalElement[0]);
			}
			validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HackResetTable();

		getTableViewer().refresh();

	}

	public void validate() throws Exception {

		// if (attributeContainer != null) {
		//
		// if (!attributeContainer.isValid()) {
		// setValid(false);
		// }
		// else {
		// setValid(true);
		// }
		//
		// }
	}

	/**
	 * Return the column names in a collection
	 *
	 * @return List containing column names
	 */
	public List<String> getColumnNamesList() {
		return Arrays.asList(columnNames);
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setCellModifier(ICellModifier modifier) {
		cellModifier = modifier;
		tableViewer.setCellModifier(modifier);
	}

	public ICellModifier getCellModifier() {
		return cellModifier;
	}

	public void refresh() {
		tableViewer.refresh();
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		tableViewer.setLabelProvider(labelProvider);
	}

	public MultiStateVerifier getVerifier(String columnName) {
		return (MultiStateVerifier) verifierMap.get(columnName);
	}

	public LocalElement getLocalElement() {
		return localElement;
	}

	/**
	 * The input to the underlying <code>TableViewer</code> will be reset by
	 * invoking {@link #setInput()}.
	 *
	 * @param localElement
	 * @throws Exception
	 */
	public void setLocalElement(LocalElement localElement) throws Exception {
		this.localElement = localElement;
		setInput();
	}

	/**
	 * The default behavior is to allow addition. Implementor may override this
	 * to not allow addition
	 *
	 * @return
	 */
	public boolean isAddAllowed() {
		return true;
	}

	/**
	 * The default behavior is to allow deletion. Implementor may override this
	 * to not allow deletion
	 *
	 * @return
	 */
	public boolean isDeleteAllowed() {
		return true;
	}

	/**
	 * The default behavior deny field re-order because most subclasses do not
	 * support it Implementor may override this to not allow addition
	 *
	 * @return
	 */
	public boolean isFieldReorderAllowed() {
		return fieldReorderAllowed;
	}

	public void setFieldReorderAllowed(boolean fieldReorderAllowed) {
		this.fieldReorderAllowed = fieldReorderAllowed;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
		toolBar.setReadOnly(isReadOnly);
		enableCellModifier(!isReadOnly);
	}

	public void enableCellModifier(boolean flag) {
		if (true == flag) {
			tableViewer.setCellModifier(cellModifier);
		} else {
			tableViewer.setCellModifier(null);
		}
	}

	public boolean isValid() {
		if (false == isValid) {
			return false;
		}

		boolean tryValidate = false;
		try {
			tryValidate = getLocalElement().isValid();
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return tryValidate;
	}

	public void setLayout(Layout layout) {
		content.setLayout(layout);
		content.layout();

	}

	public void setLayoutData(Object layoutData) {
		content.setLayoutData(layoutData);
		content.layout();
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Map<String, MultiStateVerifier> getVerifierMap() {
		return verifierMap;
	}

	public void setVerifierMap(Map<String, MultiStateVerifier> verifierMap) {
		this.verifierMap = verifierMap;
	}

	public boolean isEditToolbarSupported() {

		return editToolbarSupported;
	}

	public void setEditToolbarSupported(boolean keyColumnEnabled) {
		this.editToolbarSupported = keyColumnEnabled;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	public TableToolbar getToolBar() {
		return toolBar;
	}

	public void setToolBar(TableToolbar toolBar) {
		this.toolBar = toolBar;
	}

	public Composite getContent() {
		return content;
	}

	public void setContent(Composite content) {
		this.content = content;
	}

}