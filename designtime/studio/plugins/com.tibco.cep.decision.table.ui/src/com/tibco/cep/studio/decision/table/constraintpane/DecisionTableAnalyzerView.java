package com.tibco.cep.studio.decision.table.constraintpane;

import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.DONT_CARE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.decision.table.calendar.DateTimeCalendar;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableCommandStackConstraintUpdateListener;
import com.tibco.cep.studio.decision.table.constraintpane.event.IConstraintsTableListener;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.extension.IDecisionTableAnalyzerActionProvider;
import com.tibco.cep.studio.decision.table.extension.IDecisionTableAnalyzerExtension;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.job.ConstraintsTableCreationJob;

/**
 * 
 * @author sasahoo
 * @author aathalye
 *
 */
public class DecisionTableAnalyzerView extends ViewPart {

	public static final String ID = "com.tibco.cep.studio.decision.table.constraintpane.decisionTableAnalyzerView";

	private static final String EXTENSION_ID = "tableAnalyzerExtension";
	private static final String EXTENSION_ATTR_ID = "extension";

	private Composite composite;
	private ExpandBar collapsiblePanes;

	private IDecisionTableEditor fCurrentEditor;
	private DecisionTable fCurrentOptimizedTable;
	private HashMap<String, Widget> fComponents = new HashMap<String, Widget>();
	private Object testDataModel = null;
	private Map<Integer, List<String>> testDataCoverageResult = null;

	private IAction genTestDataAction;
	private IAction showCoverageAction;
	private IAction showTestDataCoverageAction;
	private IAction analyzeAction;
	private RefreshAnalyzerAction refreshAnalyzerAction;
	private IPreferenceStore prefStore;
	private boolean tableAnalyzer = false;

	private TableAnalyzerListener analyzerListener;

	private IDecisionTableAnalyzerExtension analyzerExtension;

	private boolean hasCheckedForExtension = false;

	private static final String CLASS = DecisionTableAnalyzerView.class
			.getName();
	private Map<String, Boolean> columnSubstituionMap = new HashMap<String, Boolean>();

	private ICommandStackListener<IExecutableCommand> commandStackListener = null;

    private SimpleDateFormat sdf;

	private Color bgColor;

	private Font font;

	public Map<String, Boolean> getColumnSubstituionMap() {
		return columnSubstituionMap;
	}

	public Object getTestDataModel() {
		return testDataModel;
	}

	public void setTestDataModel(Object testDataModel) {
		this.testDataModel = testDataModel;
	}

	public Map<Integer, List<String>> getTestDataCoverageResult() {
		return testDataCoverageResult;
	}

	public void setTestDataCoverageResult(
			Map<Integer, List<String>> testDataCoverageResult) {
		this.testDataCoverageResult = testDataCoverageResult;
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		sdf = this.getAnalyzerExtension().getDateFormat();
		analyzerListener = new TableAnalyzerListener();
		getSite().getPage().addPartListener(analyzerListener);
	}

	@Override
	public void dispose() {
		if (analyzerListener != null) {
			getSite().getPage().removePartListener(analyzerListener);
		}
		if (this.bgColor != null) {
			this.bgColor.dispose();
		}
		if (this.font != null) {
			this.font.dispose();
		}
		clearTestDataCoverageView();
		super.dispose();
	}

	/**
	 * Remove the component from the {@link ExpandBar}
	 * 
	 * @param component
	 */
	protected void removeColumnPanel(final Widget component, String columnName) {
		try {
			if (collapsiblePanes != null && !collapsiblePanes.isDisposed()) {
				for (ExpandItem item : collapsiblePanes.getItems()) {
					if (component instanceof Control) {
						Control control = (Control) component;
						if (!control.isDisposed()
								&& (item.getControl() == control.getParent())) {
							control.dispose(); // Widget
							item.getControl().dispose(); // Composite
							item.dispose(); // ExpandItem
							collapsiblePanes.layout();
							break;
						}
					}
				}
			}
			fComponents.remove(columnName);
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}

	/**
	 * Add the component to the {@link ExpandBar}
	 * 
	 * @param component
	 */
	protected void addColumnPanel(String columnName,
			DecisionTable constraintsTable) {
		List<Filter> allFilters = constraintsTable.getAllFilters(columnName);
		if (allFilters.size() > 0) { // donot create a component for column
										// having all filters empty
			// Create a component
			createColumnPanel(columnName, constraintsTable);
		}
	}

	/**
	 * Listens to switches in decision table editors and updates the analyzer
	 * 
	 * @author rektare
	 */
	class TableAnalyzerListener implements IPartListener {

		@Override
		public void partActivated(IWorkbenchPart part) {
			IWorkbenchPage page = part.getSite().getPage();
			if (page != null) {
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart != null) {
					if (activeEditorPart instanceof IDecisionTableEditor) {
						IDecisionTableEditor editor = (IDecisionTableEditor) activeEditorPart;
						resetContextActions(editor);
						// This is not found to be very expensive but if this
						// check is added
						// it does not populate the constraints table upon
						// switching from non-DT editor.
						if (fCurrentEditor != editor) {
							refillComponents(editor, editor.getDecisionTable());
							clearTestDataCoverageView();
						}
					} else {
						return;
					}
				}
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
			// TODO Auto-generated method stub

		}

		@Override
		public void partClosed(IWorkbenchPart part) {
			refreshAnalyzerView(part);
			clearTestDataCoverageView();
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
			refreshAnalyzerView(part);
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * @param part
	 */
	public void refreshAnalyzerView(IWorkbenchPart part) {
		IWorkbenchPage page = part.getSite().getPage();
		if (page != null) {
			IEditorPart activeEditorPart = page.getActiveEditor();
			if (!(activeEditorPart instanceof IDecisionTableEditor)) {
				try {
					if (Thread.currentThread() != Display.getDefault()
							.getThread()) {
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								try {
									clear();
								} catch (Exception e) {
									DecisionTableUIPlugin.log(e);
								}
							}
						});
					} else {
						try {
							clear();
						} catch (Exception e) {
							DecisionTableUIPlugin.log(e);
						}
					}
					enableContextTools(false);
				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */

	public void createPartControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		createActions();
		contributeToActionBars();
		prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		tableAnalyzer = prefStore.getBoolean(PreferenceConstants.RUN_ANALYZER);
		if (tableAnalyzer) {
			// create constraint tables which were not created by their decision
			// table editors
			// when analyzer view was closed
			createMissingConstraintTables();
			IEditorPart editor = getSite().getPage().getActiveEditor();
			if (editor instanceof IDecisionTableEditor) {
				IDecisionTableEditor dtEditor = (IDecisionTableEditor) editor;
				// refresh the analyzer per the active editor
				try {
					DecisionTable decisionTable = dtEditor.getDecisionTable();
					refillComponents(dtEditor, decisionTable);
				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		}
	}

	/**
	 * Creates the decision tables for open editors which have not already
	 * created one at initialization
	 */
	private void createMissingConstraintTables() {
		IEditorReference[] references = getSite().getPage()
				.getEditorReferences();
		for (IEditorReference ref : references) {
			if (ref.getEditor(false) instanceof IDecisionTableEditor) {
				IDecisionTableEditor editor = (IDecisionTableEditor) ref
						.getEditor(false);
				try {
					ConstraintsTableCreationJob job = new ConstraintsTableCreationJob(
							"Constraints Table Creation", this, editor);
					job.setPriority(Job.SHORT);
					job.setUser(true);
					job.schedule();
					// setDecisionTable(editor);
				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		}
	}

	/**
	 * Update an existing range slider component for any updates.
	 * 
	 * @param slider
	 * @param filters
	 */
	private void updateRangeSliderComponent(RangeSlider slider,
			List<Filter> filters) {
		Object[] minmax;
		if (filters.size() > 1) { 
			if(slider.getUpperValue() instanceof Double || slider.getLowerValue() instanceof Double){
				minmax = DecisionTableAnalyzerUtils.getMinMax(filters,true,sdf);
			}else{
				minmax = DecisionTableAnalyzerUtils.getMinMax(filters,false,sdf);
			}
			
			Object[] range = null;
			for (Filter f : filters) {
				if (f instanceof RangeFilter) {
					range = ((RangeFilter) f).getMinMax(true);
				}
			}
			if(range[0] instanceof Double && range[1] instanceof Double){
				minmax[0] = Math.min((double) range[0], Double.valueOf(minmax[0].toString()));
				minmax[1] = Math.max((double) range[1], Double.valueOf(minmax[1].toString()));
			}else{
				minmax[0] = Math.min((long) range[0], (long) minmax[0]);
				minmax[1] = Math.max((long) range[1], (long) minmax[1]);
			}
		} else if (filters.size() == 1) {
			RangeFilter filter = (RangeFilter) filters.get(0);
			minmax = filter.getMinMax(true);
		} else {
			return;
		}
		if(minmax[0] instanceof Double && minmax[1] instanceof Double){
			if ((double) minmax[0] == Double.MAX_VALUE
					&& (double) minmax[1] == Double.MIN_VALUE) {
				minmax[0] = 0d;
				minmax[1] = 0d;
			}
		}else{
			if ((long) minmax[0] == Long.MAX_VALUE
					&& (long) minmax[1] == Long.MIN_VALUE) {
				minmax[0] = 0;
				minmax[1] = 0;
			}
		}

		slider.setMinimum(minmax[0]);
		slider.setMaximum(minmax[1]);

	}

	/**
	 * Update an existing combo box component for value
	 * updates/additions/removals etc.
	 * 
	 * @param combo
	 * @param columnName
	 * @param filter
	 */
	private void updateComboBox(MultiSelectComboBox combo, String columnName,
			Filter filter) {
		if (filter.size() == 0) {
			// Remove it
			removeColumnPanel(combo, columnName);
		}
		if (filter instanceof EqualsFilter) {
			EqualsFilter equalsFilter = (EqualsFilter) filter;
			Set<Object> keySet = equalsFilter.getKeySet();
			NavigableSet<Object> keys = new TreeSet<Object>();
			for (Object key : keySet) {
				HashSet<Cell> cells = equalsFilter.get(key);
				if (false == cells.isEmpty()) {
					for (Cell cell : cells) {
						if (cell.isEnabled()) {
							if (!DONT_CARE.equalsIgnoreCase(key.toString())) {
								keys.add(key);
							}
							continue;
						}
					}
				}
			}
			combo.setElements(new ArrayList<Object>(keys));
		}
	}

		/**
	 * Handle UI updates to the analyzer pane here.
	 * <p>
	 * <b> Do not handle this anywhere outside this class. </b>
	 * </p>
	 * 
	 * @param columnName
	 * @param constraintsTable
	 * @param allColumnFilters
	 *            -> To avoid recomputation in many cases.
	 */
	protected void updateUIComponents(final String columnName,
			final DecisionTable constraintsTable,
			final List<Filter> allColumnFilters) {

		// Keep the filters runnable, causes race conditions when dt is modified
		// elsewhere
		final List<Filter> allFilters = (allColumnFilters == null || allColumnFilters
				.size() == 0) ? constraintsTable.getAllFilters(columnName)
				: allColumnFilters;

		Runnable doRun = new Runnable() {
			public void run() {
				Widget component = fComponents.get(columnName);
				if (component == null) {
					addColumnPanel(columnName, constraintsTable);
				} else {
					// If component is present;
					// check following.
					/**
					 * 1.) If there is any range filter, create/update range
					 * slider. 2.) If there is only equals filter(s),
					 * create/update combobox. 3.) If not filters and component
					 * exists remove it.
					 */

					if (allFilters.size() == 0 && component != null) {
						// Remove it
						removeColumnPanel(component, columnName);
					}
					for (Filter filter : allFilters) {
						if (filter instanceof RangeFilter) {
							if (!(component instanceof RangeSlider)) {
								// Probably newly added range
								// Remove this component
								// Replace with range
								removeColumnPanel(component, columnName);
								addColumnPanel(columnName, constraintsTable);
							} else {
								if (filter.size() == 0) {
									// Remove the component
									removeColumnPanel(component, columnName);
									continue;
								}

								// Update existing slider
								updateRangeSliderComponent(
										(RangeSlider) component, allFilters);
								// Modify the text fields when range slider is
								// modified

								((Text) fComponents.get(columnName + "_lower"))
										.setText(String
												.valueOf(((RangeSlider) component)
														.getLowerValue()));
								((Text) fComponents.get(columnName + "_upper"))
										.setText(String
												.valueOf(((RangeSlider) component)
														.getUpperValue()));
							}
							// Break here as other filters need not be tested.
							break;
						} else {
							if (component instanceof MultiSelectComboBox) {
								updateComboBox((MultiSelectComboBox) component,
										columnName, filter);
							} else {
								// Probably removed range
								// Remove this component
								// Replace with combo
								removeColumnPanel(component, columnName);
								addColumnPanel(columnName, constraintsTable);
							}
						}
					}
				}
			}
		};
		if (Thread.currentThread() == Display.getDefault().getThread()) {
			doRun.run();
		} else {
			Display.getDefault().syncExec(doRun);
		}
	}

	/**
	 * Call this method only on the swing thread.
	 * 
	 * @throws Exception
	 */
	public void clear() throws Exception {
		if (collapsiblePanes != null) {
			if (Thread.currentThread() == Display.getDefault().getThread()) {
				collapsiblePanes.dispose();
			} else {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						collapsiblePanes.dispose();
					}
				});
			}
		}
		if (fCurrentEditor != null) {
			Table tableEModel = fCurrentEditor.getTable();
			String tablePath = tableEModel.getFolder() + tableEModel.getName();
			CommandFacade.getInstance().removeCommandStackListener(
					fCurrentEditor.getProjectName(), tablePath,
					commandStackListener);
		}
		fComponents.clear();
		fCurrentEditor = null;
		fCurrentOptimizedTable = null;
	}

	/**
	 * @param editor
	 * @param decisionTable
	 */
	@SuppressWarnings("unchecked")
	public void setDecisionTable(IDecisionTableEditor editor,
			SubMonitor progressMonitor) throws Exception {
		/*
		 * DecisionTableModelManager decisionTableModelManager =
		 * editor.getDecisionTableModelManager(); if (decisionTableModelManager
		 * == null) { return; }
		 */

		Table tableEModel = editor.getTable();

		if (!editor.isEditorInputReadOnly() && tableEModel != null) {
			final DecisionTable decisionTable = DecisionTableEMFFactory
					.createOptimizedDecisionTable(tableEModel, sdf, progressMonitor);

			String tablePath = tableEModel.getFolder() + tableEModel.getName();

			commandStackListener = new DecisionTableCommandStackConstraintUpdateListener(
					decisionTable, tableEModel);

			CommandFacade.getInstance().addCommandStackListeners(
					editor.getProjectName(), tablePath,
					commandStackListener);

			decisionTable.setProjectName(editor.getProjectName());
			decisionTable.printStats();

			editor.setDecisionTable(decisionTable);
		}
	}

	public void resetContextActions(IDecisionTableEditor editor) {
		// TODO tableanalyser with nattable
		if (editor != null && editor.getTable() != null
				&& editor.getTable().getDecisionTable() != null
				&& editor.getTable().getDecisionTable().getRule() != null
				&& !editor.isEditorInputReadOnly()) {
			EList<TableRule> rules = editor.getTable().getDecisionTable()
					.getRule();
			if (rules.size() > 0) {
				enableContextTools(true);
			}
		} else {
			enableContextTools(false);
		}
	}

	private void enableContextTools(boolean enable) {
		if (genTestDataAction != null) {
			genTestDataAction.setEnabled(enable);
		}
		if (showCoverageAction != null) {
			showCoverageAction.setEnabled(enable);
		}
		if (showTestDataCoverageAction != null) {
			showTestDataCoverageAction.setEnabled(enable);
		}
		if (analyzeAction != null) {
			analyzeAction.setEnabled(enable);
		}
		if (refreshAnalyzerAction != null) {
			refreshAnalyzerAction.setEnabled(enable);
		}
	}

	/**
	 * Refill the table analyzer with the decision table contents
	 * 
	 * @param editor
	 *            The decision table editor for which the analyzer is to be set
	 * @param existingValues
	 *            The contents of the decision table if any
	 * @param decisionTable
	 *            The decision table for which
	 */
	public void refillComponents(IDecisionTableEditor editor,
			final DecisionTable decisionTable) {

		if (decisionTable == null) {
			try {
				clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		final HashMap<String, Object> existingValues;

		// fCurrentEditor might be null if part is being created afresh
		if (fCurrentEditor == null) {
			existingValues = new HashMap<String, Object>();
		} else if (editor.equals(fCurrentEditor)) {
			// record existing values so that they can be re-selected
			existingValues = getExistingValues();
		} else {
			existingValues = null;
		}

		columnSubstituionMap.clear();
		final Columns columns = editor.getTable().getDecisionTable().getColumns();
		if (columns != null) {
			for (com.tibco.cep.decision.table.model.dtmodel.Column column : columns
					.getColumn()) {
				if (column.getColumnType() == ColumnType.CONDITION) {
					if (column.isSubstitution()) {
						columnSubstituionMap.put(column.getName(),
								column.isSubstitution());
					}
				}
			}
		}

		// check if the view created is new, then replace old listener with new
		// one
		IConstraintsTableListener listener = decisionTable.getListener();
		if (listener != null) {
			if (listener.hashCode() != DecisionTableAnalyzerView.this
					.hashCode()) {
				decisionTable.addListener(new ConstraintsTableListener(this));
			}
		} else {
			decisionTable.addListener(new ConstraintsTableListener(this));
		}

		Runnable doRun = new Runnable() {
			public void run() {
				if (collapsiblePanes != null)
					collapsiblePanes.dispose();
				fComponents.clear();
				collapsiblePanes = new ExpandBar(composite, SWT.V_SCROLL
						| SWT.H_SCROLL);
				collapsiblePanes.setBackground(getBGColor());
				collapsiblePanes.setFont(getFont());
				Set<String> columnNames = decisionTable.getColumns();
				for (String name : columnNames) {
					createColumnPanel(name, decisionTable);
				}
				if (existingValues != null) {
					selectValues(existingValues);
				}
				DecisionTableAnalyzerView.this.composite.layout();
			}
		};

		if (Thread.currentThread() != Display.getDefault().getThread()) {
			DecisionTableUIPlugin.debug(CLASS,
					"Executing thread is not an SWT..Wrapping into SWT");
			Display.getDefault().syncExec(doRun);
		} else {
			doRun.run();
		}
		setCurrentTable(editor, decisionTable);
	}

	protected Font getFont() {
		if (this.font == null) {
			this.font = new Font(Display.getDefault(),
					"Arial", 8, SWT.BOLD);
		}
		return this.font;
	}

	protected Color getBGColor() {
		if (this.bgColor == null) {
			this.bgColor = new Color(Display.getDefault(),
					198, 226, 255);
		}
		return this.bgColor;
	}

	public void resetContextActions() {
		if (fCurrentEditor != null) {
			resetContextActions(fCurrentEditor);
		}
	}

	private void selectValues(final HashMap<String, Object> existingValues) {
		Set<String> keySet = existingValues.keySet();
		for (String columnName : keySet) {
			Object object = existingValues.get(columnName);
			if (object == null) {
				continue;
			}
			// find the new component for this column, and select the proper
			// values
			Widget component = fComponents.get(columnName);
			if (component == null) {
				continue;
			}
			setComponentValue(component, object);
		}
	}

	private void setComponentValue(Widget component, Object object) {
		if (component instanceof MultiSelectComboBox
				&& object instanceof Object[]) {
			Object[] values = (Object[]) object;
			MultiSelectComboBox combo = (MultiSelectComboBox) component;
			// TODO : handle cases in which a selected item is no longer in the
			// combo
			// Currently, this clears the selection completely
			combo.setSelectedObjects(values);
		} else if (component instanceof RangeSlider && object instanceof int[]) {
			int[] values = (int[]) object;
			RangeSlider slider = (RangeSlider) component;
			// check whether values are within the range
			int oldLowValue = values[0];
			int oldHighValue = values[1];
		}
	}

	private HashMap<String, Object> getExistingValues() {
		HashMap<String, Object> existingValuesMap = new HashMap<String, Object>();
		for (String columnName : fComponents.keySet()) {
			Widget comp = fComponents.get(columnName);
			Object value = getComponentValue(comp);
			existingValuesMap.put(columnName, value);
		}
		return existingValuesMap;
	}

	private Object getComponentValue(Widget comp) {
		if (comp instanceof MultiSelectComboBox) {
			Object selectedItem = ((MultiSelectComboBox) comp)
					.getSelectedObjects();
			if (selectedItem instanceof Object[]) {
				return selectedItem;
			} else if (selectedItem == null) {
			}
		}
		// else if (comp instanceof RangeSlider) {
		// int lowValue = ((RangeSlider)comp).getLowValue();
		// int highValue = ((RangeSlider)comp).getHighValue();
		// return new int[] { lowValue, highValue };
		// }
		return null;
	}

	private void setCurrentTable(IDecisionTableEditor editor,
			DecisionTable decisionTable) {
		if (fCurrentEditor != null) {
			// dispose some stuff?
		}
		setCurrentDecisionTable(editor);
		this.fCurrentOptimizedTable = decisionTable;
	}

	/**
	 * @param name
	 * @return
	 */
	private void createColumnPanel(String name, DecisionTable decisionTable) {
		ExpandItem pane = new ExpandItem(collapsiblePanes, SWT.NONE,
				collapsiblePanes.getItemCount());
		Composite composite = new Composite(collapsiblePanes, SWT.NONE);
		pane.setText(name);
		composite.setLayout(new GridLayout());

		// composite.setBackground(new Color(Display.getDefault(), 159, 182,
		// 205));
		Widget comp = createTypedPanel(composite, name, decisionTable);
		if (comp != null) {
			pane.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			pane.setControl(composite);
			pane.setExpanded(true);
		}
	}

	/**
	 * 
	 * @param columnName
	 * @param equalsFilter
	 * @return
	 */
	private Widget componentizeEqualsFilter(Composite composite,
			String columnName, EqualsFilter equalsFilter) {
		// filter disabled cells
		NavigableSet<Object> keys = new TreeSet<Object>();
		Set<Object> keySet = equalsFilter.getKeySet();
		for (Object key : keySet) {
			HashSet<Cell> cells = equalsFilter.get(key);
			if (null == cells)
				continue;
			for (Cell cell : cells) {
				if (cell.isEnabled()) {
					if (!DONT_CARE.equalsIgnoreCase(key.toString())) {
						keys.add(key);
					}
					continue;
				}
			}
		}
		
		if (keys.size() == 1) {
			Object first = keys.first();
			if ("true".equals(first)) {
				keys.add("false");
			} else if ("false".equals(first)) {
				keys.add("true");
			}
		}
		MultiSelectComboBox cbl = new MultiSelectComboBox(composite, SWT.NONE,
				new ArrayList<Object>(keys));
		fComponents.put(columnName, cbl);
		return cbl;
	}

	/**
	 * @param columnName
	 * @return
	 */
	private Widget createTypedPanel(Composite composite, String columnName,
			DecisionTable decisionTable) {
		List<Filter> allFilters = decisionTable.getAllFilters(columnName);
		if (allFilters.size() == 0)
			return null; // return null component if filters are empty
		if (allFilters.size() == 1) {
			Filter filter = allFilters.get(0);
			if (filter instanceof EqualsFilter) {
				return componentizeEqualsFilter(composite, columnName,
						(EqualsFilter) filter);
			} else if (filter instanceof RangeFilter) {
				RangeFilter rf = (RangeFilter) filter;
				Object[] minmax = rf.getMinMax(true);
				if (minmax[0] instanceof Date && minmax[1] instanceof Date) {
					return addDateTextBoxComponent(composite, columnName,
							minmax, minmax);
				}
				return createRangeComponent(composite, columnName, minmax);
			}
		} else if (allFilters.size() == 2) {
			boolean hasRangeFilter = false;
			EqualsFilter combinationFilter = null;
			for (Filter temp : allFilters) {
				if (temp instanceof RangeFilter) {
					hasRangeFilter = true;
				}
				if (temp instanceof EqualsFilter) {
					combinationFilter = (EqualsFilter) temp;
				}
			}
			// Should be one ComboFilter and one RangeFilter,
			// Signifying a combination of ranges and equality checks
			if (hasRangeFilter) {
				// Do this only if there is a range filter.
				Object[] minMax;
				if(decisionTable.getCellInfo(columnName).getType() == PROPERTY_TYPES.DOUBLE_VALUE){
					minMax = DecisionTableAnalyzerUtils.getMinMax(allFilters,true,sdf);
				}else{
					minMax = DecisionTableAnalyzerUtils.getMinMax(allFilters,false,sdf);
				}
				if (minMax == null) {
					// could not find range
					Text textField = new Text(composite, SWT.SINGLE);
					// SelectAllUtils.install(textField);
					fComponents.put(columnName, textField);
					return textField;
				}
				Filter filter1 = allFilters.get(0);
				Filter filter2 = allFilters.get(1);
				RangeFilter rangeFilter = null;

				if (filter1 instanceof RangeFilter) {
					rangeFilter = (RangeFilter) filter1;
				} else if (filter2 instanceof RangeFilter) {
					rangeFilter = (RangeFilter) filter2;
				}

				final Object[] range = rangeFilter.getMinMax(true);
				if (range[0] instanceof Long && range[1] instanceof Long) {
					if(minMax[0] instanceof Double && minMax[1] instanceof Double){
						range[0] = Double.valueOf(range[0].toString());
						range[1] = Double.valueOf(range[1].toString());
						range[0] = Math.min((double)range[0], (double)minMax[0]);
						range[1] = Math.max((double)range[1], (double)minMax[1]);
						if ((double) range[0] == Double.MAX_VALUE
								&& (double) range[1] == Double.MIN_VALUE) {
							range[0] = 0d;
							range[1] = 0d;
						}
					}else{
						range[0] = Math.min((long) range[0], (long) minMax[0]);
						range[1] = Math.max((long) range[1], (long) minMax[1]);
						if ((long) range[0] == Long.MAX_VALUE
								&& (long) range[1] == Long.MIN_VALUE) {
							range[0] = 0;
							range[1] = 0;
						}
					}
					return createRangeComponent(composite, columnName, range);
				}else if(range[0] instanceof Double && range[1] instanceof Double){
					if(minMax[0] instanceof Long && minMax[1] instanceof Long){
						range[0] = Math.min((double) range[0], Double.valueOf(minMax[0].toString()));
						range[1] = Math.max((double) range[1], Double.valueOf(minMax[1].toString()));
					}else{
						range[0] = Math.min((double) range[0], (double) minMax[0]);
						range[1] = Math.max((double) range[1], (double) minMax[1]);
					}
					if ((double) range[0] == Double.MAX_VALUE
							&& (double) range[1] == Double.MIN_VALUE) {
						range[0] = 0d;
						range[1] = 0d;
					}
					return createRangeComponent(composite, columnName, range);
				}else if (range[0] instanceof Date && range[1] instanceof Date) {
					return addDateTextBoxComponent(composite, columnName,
							minMax, range);
				}
			} else {
				// Resort to equals case
				return componentizeEqualsFilter(composite, columnName,
						combinationFilter);
			}
		}
		Text textField = new Text(composite, SWT.SINGLE);
		// SelectAllUtils.install(textField);
		fComponents.put(columnName, textField);
		return textField;
	}

	private Widget addDateTextBoxComponent(Composite composite,
			String columnName, Object[] minMax, final Object[] range) {
		long rangeMinTime = ((Date) range[0]).getTime();
		long rangeMaxTime = ((Date) range[1]).getTime();
		long minMaxMinTime = ((Date) minMax[0]).getTime();
		long minMaxMaxTime = ((Date) minMax[1]).getTime();

		range[0] = new Date(Math.min(rangeMinTime, minMaxMinTime));
		range[1] = new Date(Math.max(rangeMaxTime, minMaxMaxTime));

		// if()
		// could not find range
		final Text textField = new Text(composite, SWT.SINGLE);
//		textField.setLayoutData(new GridData(GridData.FILL_BOTH));
	
		GridData gridData = new GridData(
				GridData.FILL_BOTH);
		gridData.heightHint = 25;
//		gridData.widthHint = 200;
		textField.setLayoutData(gridData);
		textField.setEditable(false);
		textField.setBackground(new Color(null, 255, 255, 255));
		textField.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				DateTimeCalendar calendar = new DatePickerDialog(
						textField, range);
				calendar.open();

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

			}
		});
		// SelectAllUtils.install(textField);
		fComponents.put(columnName, textField);
		return textField;
	}

	private Widget createRangeComponent(Composite composite, String name,
			Object[] range) {
		if (range == null)
			return null;
		if ((range[0] instanceof Integer && range[1] instanceof Integer )&& ((int) range[0] == Integer.MIN_VALUE
				|| (int) range[1] == Integer.MAX_VALUE)) {
			return null;

		}else if ((range[0] instanceof Long && range[1] instanceof Long )&& ((long) range[0] == Long.MIN_VALUE
				|| (long) range[1] == Long.MAX_VALUE)) {
			return null;

		}else {
			try {
				final RangeSlider hRangeSlider = new RangeSlider(composite,
						SWT.HORIZONTAL,range);
				final Object[] rangeFinal = range;
				final GridData gd = new GridData(GridData.FILL,
						GridData.CENTER, true, false, 1, 2);
				gd.widthHint = 250;
				hRangeSlider.setLayoutData(gd);

				hRangeSlider.setMaximum(adjustRange(range[1], 1));
				hRangeSlider.setMinimum(adjustRange(range[0], -1));
				hRangeSlider.setUpperValue(adjustRange(range[1], 1));
				hRangeSlider.setLowerValue(adjustRange(range[0], -1));

				Composite boundComposite = new Composite(composite, SWT.NONE);
				boundComposite.setLayout(new GridLayout(2, true));
				final Label hLabelLower = new Label(boundComposite, SWT.NONE);
				hLabelLower.setLayoutData(new GridData(GridData.BEGINNING,
						GridData.CENTER, false, false, 1, 1));
				hLabelLower.setText("Lower Value");

				final Text hTextLower = new Text(boundComposite, SWT.BORDER);
				hTextLower.setLayoutData(new GridData(GridData.FILL,
						GridData.BEGINNING, false, false, 1, 1));
				hTextLower
						.setText(String.valueOf(hRangeSlider.getLowerValue()));
				hTextLower.setEnabled(true);
				hTextLower.addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e) {
						if (e.getSource() == hTextLower) {
							String s = ((Text) (e.getSource())).getText();
							if (s != null && !s.trim().isEmpty()) {
								try {
									if(s.contains(".") || rangeFinal[0] instanceof Double){
										hRangeSlider.setLowerValue(Double.parseDouble(s));
									}else{
										hRangeSlider.setLowerValue(Long
												.parseLong(s));
									}
								} catch (NumberFormatException nfe) {
									DecisionTableUIPlugin.log(nfe);
								}
							}
						}

					}
				});

				final Label hLabelUpper = new Label(boundComposite, SWT.NULL);
				hLabelUpper.setLayoutData(new GridData(GridData.BEGINNING,
						GridData.CENTER, false, false, 1, 1));
				hLabelUpper.setText("Upper Value");

				final Text hTextUpper = new Text(boundComposite, SWT.BORDER);
				hTextUpper.setLayoutData(new GridData(GridData.FILL,
						GridData.BEGINNING, false, false, 1, 1));
				hTextUpper
						.setText(String.valueOf(hRangeSlider.getUpperValue()));
				hTextUpper.setEnabled(true);
				hTextUpper.addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e) {
						if (e.getSource() == hTextUpper) {
							String s = ((Text) (e.getSource())).getText();
							if (s != null && !s.trim().isEmpty()) {
								try {
									if(s.contains(".") || rangeFinal[0] instanceof Double){
										hRangeSlider.setUpperValue(Double.parseDouble(s));
									}else{
										hRangeSlider.setUpperValue(Long.parseLong(s));
									}
								} catch (NumberFormatException nfe) {
									DecisionTableUIPlugin.log(nfe);
								}
							}
						}

					}
				});

				hRangeSlider.addMouseMoveListener(new MouseMoveListener() {
					@Override
					public void mouseMove(MouseEvent e) {

						if (e.getSource() instanceof RangeSlider) {
							RangeSlider rs = (RangeSlider) e.getSource();
							hTextUpper.setText(String.valueOf(rs.getSelection()[1]));
							hTextLower.setText(String.valueOf(rs.getSelection()[0]));
						}
					}
				});

				fComponents.put(name, hRangeSlider);
				fComponents.put(name + "_lower", hTextLower);
				fComponents.put(name + "_upper", hTextUpper);
				return hRangeSlider;
			} catch (RuntimeException re) {
				DecisionTableUIPlugin.log(re);
			}
		}
		return null;
	}

	private Object adjustRange(Object object, int i) {
		if (object instanceof Double) {
			return (Double) object + i;
		}
		if (object instanceof Long) {
			return (Long) object + i;
		}
		if (object instanceof Integer) {
			return (Integer) object + i;
		}
		return object;
	}

	public IFile getTableFile() {
		IDecisionTableEditor editor = getCurrentEditor();
		if (editor != null
				&& editor.getEditorInput() instanceof FileEditorInput) {
			return ((FileEditorInput) editor.getEditorInput()).getFile();
		}
		return null;
	}

	private void createActions() {
		refreshAnalyzerAction = new RefreshAnalyzerAction(this);
		if (canAnalyze()) {
			analyzeAction = getAnalyzeAction();
		}
		if (canShowCoverage()) {
			showCoverageAction = getShowCoverageAction();
		}
		if (canGenerateTestData()) {
			genTestDataAction = getGenerateTestDataAction();
		}
		if (canShowTestDataCoverage()) {
			showTestDataCoverageAction = getShowTestDataCoverageAction();
		}
	}

	private IAction getShowTestDataCoverageAction() {
		IDecisionTableAnalyzerExtension ext = getAnalyzerExtension();
		if (ext instanceof IDecisionTableAnalyzerActionProvider) {
			return ((IDecisionTableAnalyzerActionProvider) ext).getShowTestDataCoverageAction(this);
		}
		return null;
	}

	private IAction getGenerateTestDataAction() {
		IDecisionTableAnalyzerExtension ext = getAnalyzerExtension();
		if (ext instanceof IDecisionTableAnalyzerActionProvider) {
			return ((IDecisionTableAnalyzerActionProvider) ext).getGenerateTestDataAction(this);
		}
		return null;
	}

	private IAction getAnalyzeAction() {
		IDecisionTableAnalyzerExtension ext = getAnalyzerExtension();
		if (ext instanceof IDecisionTableAnalyzerActionProvider) {
			return ((IDecisionTableAnalyzerActionProvider) ext).getAnalyzeAction(this);
		}
		return new AnalyzeAction(this);
	}
	
	private IAction getShowCoverageAction() {
		IDecisionTableAnalyzerExtension ext = getAnalyzerExtension();
		if (ext instanceof IDecisionTableAnalyzerActionProvider) {
			return ((IDecisionTableAnalyzerActionProvider) ext).getShowCoverageAction(this);
		}
		return new ShowCoverageAction(this);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
		enableContextTools(false);
	}

	/**
	 * @param manager
	 */
	private void fillLocalPullDown(IMenuManager manager) {
		if (canAnalyze()) {
			manager.add(analyzeAction);
		}
		if (canShowCoverage()) {
			manager.add(showCoverageAction);
		}
		if (canShowTestDataCoverage()) {
			manager.add(showTestDataCoverageAction);
		}
		if (canGenerateTestData()) {
			manager.add(new Separator());
			manager.add(genTestDataAction);
		}
		manager.add(refreshAnalyzerAction);
	}

	private boolean canGenerateTestData() {
		if (getAnalyzerExtension() != null) {
			return getAnalyzerExtension().canGenerateTestData();
		}
		return true;
	}

	private boolean canShowTestDataCoverage() {
		if (getAnalyzerExtension() != null) {
			return getAnalyzerExtension().canShowTestDataCoverage();
		}
		return true;
	}
	
	private boolean canShowCoverage() {
		if (getAnalyzerExtension() != null) {
			return getAnalyzerExtension().canShowCoverage();
		}
		return true;
	}

	private boolean canAnalyze() {
		if (getAnalyzerExtension() != null) {
			return getAnalyzerExtension().canAnalyze();
		}
		return true;
	}

	/**
	 * @param manager
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAnalyzerAction);
		manager.add(new Separator());

		if (canAnalyze()) {
			manager.add(analyzeAction);
		}
		if (canShowCoverage()) {
			manager.add(showCoverageAction);
		}
		if (canShowTestDataCoverage()) {
			manager.add(showTestDataCoverageAction);
		}
		if (canGenerateTestData()) {
			manager.add(new Separator());
			manager.add(genTestDataAction);
		}
	}

	public ExpandBar getCollapsiblePanes() {
		return collapsiblePanes;
	}

	public void setFocus() {
		this.composite.setFocus();
	}

	public DecisionTable getCurrentOptimizedTable() {
		return fCurrentOptimizedTable;
	}

	public void setCurrentOptimizedTable(DecisionTable table) {
		fCurrentOptimizedTable = table;
	}

	public IDecisionTableEditor getCurrentEditor() {
		return fCurrentEditor;
	}

	public void setCurrentDecisionTable(IDecisionTableEditor editor) {
		fCurrentEditor = editor;
		IDecisionTableAnalyzerExtension ext = getAnalyzerExtension();
		if (ext != null) {
			ext.setCurrentDecisionTable(editor);
		}
	}

	public Map<String, Widget> getComponents() {
		return Collections.unmodifiableMap(fComponents);
	}

	private void clearTestDataCoverageView() {
		IDecisionTableTestDataCoverageView testDataCoverageResultsView = (IDecisionTableTestDataCoverageView) getSite()
				.getPage()
				.findView(
						IDecisionTableTestDataCoverageView.TESTDATA_COVERAGE_RESULTS_VIEW_ID);
		if (testDataCoverageResultsView != null)
			testDataCoverageResultsView.clear();
	}

	public IDecisionTableAnalyzerExtension getAnalyzerExtension() {
		if (hasCheckedForExtension) {
			return analyzerExtension;
		}
		if (analyzerExtension == null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(
							DecisionTableUIPlugin.PLUGIN_ID, EXTENSION_ID);
			if (elements != null && elements.length > 0) {
				try {
					Object ext = elements[0]
							.createExecutableExtension(EXTENSION_ATTR_ID);
					if (ext instanceof IDecisionTableAnalyzerExtension) {
						analyzerExtension = (IDecisionTableAnalyzerExtension) ext;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		if (analyzerExtension == null) {
			analyzerExtension = new SimpleDecisionTableAnalyzerExtension();
		}
		hasCheckedForExtension = true;
		return analyzerExtension;
	}

	class RefreshAnalyzerAction extends Action {

		private IViewPart part;

		public RefreshAnalyzerAction(IViewPart part) {
			this.part = part;
			setImageDescriptor(DecisionTableUIPlugin
					.getImageDescriptor("icons/refresh_16x16.png"));
			setText(Messages.getString("DecisionTableAnalyzerView.Refresh"));
			setToolTipText(Messages.getString("DecisionTableAnalyzerView.Refresh"));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			if (getComponents().keySet().size() != 0) {
				try {
					IWorkbenchPage page = part.getSite().getPage();
					if (page != null) {
						IEditorPart activeEditorPart = page.getActiveEditor();
						if (activeEditorPart != null) {
							if (activeEditorPart instanceof IDecisionTableEditor) {
								IDecisionTableEditor editor = (IDecisionTableEditor) activeEditorPart;
								ConstraintsTableCreationJob job = new ConstraintsTableCreationJob(
										"Constraints Table Creation",
										(DecisionTableAnalyzerView) part,
										editor);
								job.setPriority(Job.SHORT);
								job.setUser(true);
								job.schedule();
							}
						}
					}

				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		}

	}

	/**
	 * Open the dialog
	 * 
	 * @author dijadhav
	 *
	 */
	private class DatePickerDialog extends DateTimeCalendar {
		private final Text text;
		private Object[] range;

		private DatePickerDialog(Text text, Object[] range) {
			super(text.getShell(), DecisionTableAnalyzerView.this.sdf);
			this.text = text;
			this.range = range;
		}

		@Override
		protected Control createContents(Composite parent) {
			Control composite = super.createContents(parent);
//			dateOpCombo.select(3);
			initializeDateTime(text.getText());
//			Date startDate = (Date) range[0];
//			Date endDate = (Date) range[1];
//			startDateCalendar.setTime(startDate);
//			endDateCalendar.setTime(endDate);
//			setCalendar(startDateCalWidget, startDateTimeWidget, startDateCalendar, valText);
//			setCalendar(endDateCalendarWidget, endDateTimeWidget, endDateCalendar,
//					endDateValText);
//			resetConfiguration(startDateCalendar);
//			setRangeDateTime(sdf.format(range[0]), sdf.format(range[1]));

			return composite;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org
		 * .eclipse.swt.widgets.Composite)
		 */
		protected void createButtonsForButtonBar(Composite parent) {
			createButtonLayout(parent);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org
		 * .eclipse.swt.widgets.Composite)
		 */
		protected void createButtonsForCalendarBetweenOrRangeButtonBar(
				Composite parent) {
			createButtonLayout(parent);
		}

		/**
		 * Create the button layout in calendar dialog.
		 * 
		 * @param parent
		 */
		private void createButtonLayout(Composite parent) {
			GridLayout glayout = new GridLayout(2, false);
			parent.setLayout(glayout);
			parent.setBackground(COLOR_WHITE);

			Button ok = new Button(parent, SWT.FLAT);
			GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
			gd.widthHint = 100;
			gd.heightHint = 25;
			ok.setText("Ok"); //$NON-NLS-1$
			ok.setLayoutData(gd);

			Button cancel = new Button(parent, SWT.FLAT);
			gd = new GridData(SWT.END, SWT.CENTER, false, false);
			gd.widthHint = 100;
			gd.heightHint = 25;
			cancel.setText("Cancel"); //$NON-NLS-1$
			cancel.setLayoutData(gd);

			cancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					close();
				}
			});
			ok.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					okButtonActionListener();
				}

			});
		}

		/**
		 * Action listener of ok button.
		 */
		private void okButtonActionListener() {
			Calendar gcalendar = new GregorianCalendar(startDateCalWidget.getYear(),
					startDateCalWidget.getMonth(), startDateCalWidget.getDay(), startDateTimeWidget.getHours(),
					startDateTimeWidget.getMinutes(), startDateTimeWidget.getSeconds());
			List<String> errorMessages = new LinkedList<String>();
			Date startDate = (Date) range[0];
			Date endDate = (Date) range[1];
			try {
				if (isBefore()) {
//					if (gcalendar.getTime().before(startDate)) {
//						errorMessages.add("Date "
//								+ sdf.format(gcalendar.getTime())
//								+ " is beyond the date range [ "
//								+ sdf.format(startDate) + ","
//								+ sdf.format(endDate) + " ]");
//					} else {
						setBeforeDateTime(sdf.format(gcalendar.getTime()));
//					}
				} else if (isAfter()) {
//					if (gcalendar.getTime().after(endDate)) {
//						errorMessages.add("Date "
//								+ sdf.format(gcalendar.getTime())
//								+ " is beyond the date range [ "
//								+ sdf.format(startDate) + ","
//								+ sdf.format(endDate) + " ]");
//					} else {
						setAfterDateTime(sdf.format(gcalendar.getTime()));
//					}
				} else if (isBetweenOrRange()) {

					Calendar startGCalendar = null;
					Calendar endGCalendar = null;
					startGCalendar = new GregorianCalendar(startDateCalWidget.getYear(),
							startDateCalWidget.getMonth(), startDateCalWidget.getDay(),
							startDateTimeWidget.getHours(), startDateTimeWidget.getMinutes(),
							startDateTimeWidget.getSeconds());

					endGCalendar = new GregorianCalendar(
							endDateCalendarWidget.getYear(),
							endDateCalendarWidget.getMonth(),
							endDateCalendarWidget.getDay(), endDateTimeWidget.getHours(),
							endDateTimeWidget.getMinutes(), endDateTimeWidget.getSeconds());

//					if (startGCalendar.getTime().before(startDate)) {
//						errorMessages.add("Start Date "
//								+ sdf.format(startGCalendar.getTime())
//								+ " is beyond the date range [ "
//								+ sdf.format(startDate) + ","
//								+ sdf.format(endDate) + " ]");
//					}
//					if (endGCalendar.getTime().after(endDate)) {
//						errorMessages.add("End Date "
//								+ sdf.format(endGCalendar.getTime())
//								+ " is beyond the date range [ "
//								+ sdf.format(startDate) + ","
//								+ sdf.format(endDate) + " ]");
//					}
					setRangeDateTime(sdf.format(startGCalendar.getTime()),
							sdf.format(endGCalendar.getTime()));

				} else {
//					if (gcalendar.getTime().before(startDate)
//							|| gcalendar.getTime().after(endDate)) {
//						errorMessages.add("Date "
//								+ sdf.format(gcalendar.getTime())
//								+ " is beyond the date range [ "
//								+ sdf.format(startDate) + ","
//								+ sdf.format(endDate) + " ]");
//					} else {
						setFormattedDateTimeExpr(sdf.format(gcalendar.getTime()));
//					}
				}
				if (!errorMessages.isEmpty()) {
					int style = SWT.ICON_ERROR;
					MessageBox messageBox = new MessageBox(this.getShell(),
							style);
					StringBuilder messageBuilder = new StringBuilder();
					for (String message : errorMessages) {
						messageBuilder.append(message + "\r\n");
					}
					messageBox.setText("Error Message");
					messageBox.setMessage(messageBuilder.toString());
					messageBox.open();
				} else {
					setWidgetDateTime(getFormattedDateTimeExpr());
					text.setText(getFormattedDateTimeExpr());
					text.redraw();
				}
			} catch (Exception ex) {
				DecisionTableUIPlugin.log(ex);
			}
			if (errorMessages.isEmpty()) {
				close();
			}
		}

	}
}
