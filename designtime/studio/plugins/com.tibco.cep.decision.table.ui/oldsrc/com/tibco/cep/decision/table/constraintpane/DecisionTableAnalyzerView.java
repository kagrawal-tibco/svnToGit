package com.tibco.cep.decision.table.constraintpane;

import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_FIXABLE_MARKER_NAME;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_RULECOMB_MARKER_NAME;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.DONT_CARE;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_COLUMN_NAME;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_CONFLICTING_ACTIONS_RULEID;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_DUPLICATE_RULEID;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RANGE_MAX_VALUE;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RANGE_MIN_VALUE;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RANGE_PROBLEM;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RULE_COMBINATION_PROBLEM;
import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
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

import com.jidesoft.decision.DecisionRule;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.listeners.DecisionTableCommandStackConstraintUpdateListener;
import com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.extension.IDecisionTableAnalyzerExtension;
import com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.jobs.ConstraintsTableCreationJob;
import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.IAutofixableProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RuleCombinationProblemEvent;
import com.tibco.cep.studio.util.logger.problems.UncoveredDomainEntryProblemEvent;

/**
 * 
 * @author sasahoo
 * @author aathalye
 *
 */
public class DecisionTableAnalyzerView extends ViewPart {

	private class DefaultDecisionTableAnalyzerExtension implements IDecisionTableAnalyzerExtension {

		@Override
		public boolean canAnalyze() {
			return true;
		}

		@Override
		public boolean canGenerateTestData() {
			return true;
		}

		@Override
		public boolean canShowCoverage() {
			return true;
		}

		@Override
		public void setCurrentDecisionTable(DecisionTableEditor editor) {

		}

		@Override
		public void processAnalyzerProblemEvent(ProblemEvent problemEvent, Table table) throws Exception {
			IFile resource = getTableFile();
			if (resource == null) {
				return;
			}
			String message = problemEvent.getMessage();
			int location = problemEvent.getLocation();
			int severity = problemEvent.getSeverity();
			//Else ignore it
			if (problemEvent instanceof IAutofixableProblemEvent) {
				IMarker marker = resource.createMarker(ANALYZE_FIXABLE_MARKER_NAME);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put(IMarker.MESSAGE, message);
				attributes.put(IMarker.SEVERITY, severity);
				if (location >= 0) {
					attributes.put(IMarker.LINE_NUMBER, location);
				}
				if (problemEvent instanceof RangeProblemEvent) {
					RangeProblemEvent rangeProblemEvent = (RangeProblemEvent)problemEvent;

					attributes.put(MARKER_ATTR_COLUMN_NAME, 
							rangeProblemEvent.getGuiltyColumnName());
					attributes.put(MARKER_ATTR_RANGE_MIN_VALUE,
							rangeProblemEvent.getMin());
					attributes.put(MARKER_ATTR_RANGE_MAX_VALUE,
							rangeProblemEvent.getMax());
					attributes.put(MARKER_ATTR_RANGE_PROBLEM,
							rangeProblemEvent.getRangeTypeProblem().toString());

				}
				if (problemEvent instanceof DuplicateRuleProblemEvent) {
					DuplicateRuleProblemEvent duplicateRulePE = 
						(DuplicateRuleProblemEvent)problemEvent;
					attributes.put(MARKER_ATTR_DUPLICATE_RULEID,
							duplicateRulePE.getDuplicateRuleID());

				}
				if (problemEvent instanceof ConflictingActionsProblemEvent) {
					ConflictingActionsProblemEvent conflictingRulesPE = 
						(ConflictingActionsProblemEvent)problemEvent;
					attributes.put(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID,
							conflictingRulesPE.getProblemRuleId());

				}
				if (problemEvent instanceof UncoveredDomainEntryProblemEvent) {
					UncoveredDomainEntryProblemEvent domainEntryPE = 
						(UncoveredDomainEntryProblemEvent)problemEvent;
					attributes.put(MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY,
							domainEntryPE.getUncoveredDomainValue());
					attributes.put(MARKER_ATTR_COLUMN_NAME, 
							domainEntryPE.getColumnName());
				}
				marker.setAttributes(attributes);
			} else {
				if (problemEvent instanceof RuleCombinationProblemEvent) {
					IMarker marker = resource.createMarker(ANALYZE_RULECOMB_MARKER_NAME);
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put(IMarker.MESSAGE, message);
					attributes.put(IMarker.SEVERITY, severity);
					if (location >= 0) {
						attributes.put(IMarker.LINE_NUMBER, location);
					}
					attributes.put(MARKER_ATTR_RULE_COMBINATION_PROBLEM, true);
					marker.setAttributes(attributes);
				} else if (problemEvent instanceof MissingEqualsCriterionProblemEvent) {
					IMarker marker = resource.createMarker(ANALYZE_RULECOMB_MARKER_NAME);
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put(IMarker.MESSAGE, message);
					attributes.put(IMarker.SEVERITY, severity);
					if (location >= 0) {
						attributes.put(IMarker.LINE_NUMBER, location);
					}
					attributes.put(MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM, true);
					marker.setAttributes(attributes);
				} else if (problemEvent instanceof OverlappingRangeProblemEvent) {
					IMarker marker = resource.createMarker(ANALYZE_RULECOMB_MARKER_NAME);
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put(IMarker.MESSAGE, message);
					attributes.put(IMarker.SEVERITY, severity);
					OverlappingRangeProblemEvent overlappingRulesPE = 
							(OverlappingRangeProblemEvent)problemEvent;
						attributes.put(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID,
								overlappingRulesPE.getProblemRuleId());
						attributes.put(IMarker.LINE_NUMBER, overlappingRulesPE.getProblemRuleId());
						marker.setAttributes(attributes);
				}
				
			}
		}

		@Override
		public IFile getUnderlyingFile() {
			return getTableFile();
		}

	}

	public static final String ID = "com.tibco.cep.decision.table.constraintpane.decisionTableAnalyzerView"; 

	private static final String EXTENSION_ID = "tableAnalyzerExtension";
	private static final String EXTENSION_ATTR_ID = "extension";
	
	private Composite composite;
	private ExpandBar collapsiblePanes;
	
	private DecisionTableEditor fCurrentEditor;
	private DecisionTable fCurrentOptimizedTable;
	private HashMap<String, Widget> fComponents = new HashMap<String, Widget>();

//	private GenerateTestDataAction genTestDataAction;
	private ShowCoverageAction showCoverageAction;
	private AnalyzeAction analyzeAction;
	private RefreshAnalyzerAction refreshAnalyzerAction;
	private IPreferenceStore prefStore;
	private boolean tableAnalyzer = false;
	
	private TableAnalyzerListener analyzerListener;

	private IDecisionTableAnalyzerExtension analyzerExtension;

	private boolean hasCheckedForExtension = false;
	
	private static final String CLASS = DecisionTableAnalyzerView.class.getName();
	private Map<String, Boolean> columnSubstituionMap = new HashMap<String, Boolean>();
	

	public Map<String, Boolean> getColumnSubstituionMap() {
		return columnSubstituionMap;
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		analyzerListener = new TableAnalyzerListener();
		getSite().getPage().addPartListener(analyzerListener);
	}
	
	@Override
	public void dispose() {
		if (analyzerListener != null) {
			getSite().getPage().removePartListener(analyzerListener);
		}
		super.dispose();
	}

	/**
	 * Remove the component from the {@link ExpandBar}
	 * @param component
	 */
	protected void removeColumnPanel(final Widget component, String columnName) {
		try {
			if (collapsiblePanes != null && !collapsiblePanes.isDisposed()) {
				for (ExpandItem item : collapsiblePanes.getItems()) {
					if (component instanceof Control) {
						Control control = (Control) component;
						if (!control.isDisposed() && (item.getControl() == control.getParent())) {
							control.dispose();							   //Widget
							item.getControl().dispose();                   //Composite
							item.dispose();								   //ExpandItem	
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
	 * @param component
	 */
	protected void addColumnPanel(String columnName, DecisionTable constraintsTable) {
		List<Filter> allFilters = constraintsTable.getAllFilters(columnName);
		if (allFilters.size() > 0) { // donot create a component for column having all filters empty
			//Create a component
			createColumnPanel(columnName, constraintsTable);
		}	
	}

	/**
	 * Listens to switches in decision table editors and updates the analyzer
	 * @author rektare
	 */
	class TableAnalyzerListener implements IPartListener {

		@Override
		public void partActivated(IWorkbenchPart part) {
			IWorkbenchPage page = part.getSite().getPage();
			if (page != null) {
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart != null) {
					if (activeEditorPart instanceof DecisionTableEditor) {
						DecisionTableEditor editor = (DecisionTableEditor)activeEditorPart;
						resetContextActions(editor);
						//This is not found to be very expensive but if this check is added
						//it does not populate the constraints table upon switching from non-DT editor.
						if (fCurrentEditor != editor) {
							refillComponents(editor, editor.getDecisionTable());
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
			if (!(activeEditorPart instanceof DecisionTableEditor)) {
				try {
					if (Thread.currentThread() != Display.getDefault().getThread()) {
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
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	
	public void createPartControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		createActions();
		contributeToActionBars();
		prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		tableAnalyzer = prefStore.getBoolean(PreferenceConstants.RUN_ANALYZER);
		if (tableAnalyzer) {
			// create constraint tables which were not created by their decision table editors 
			// when analyzer view was closed
			createMissingConstraintTables();
			IEditorPart editor = getSite().getPage().getActiveEditor();
			if (editor instanceof DecisionTableEditor) {
				DecisionTableEditor dtEditor = (DecisionTableEditor)editor;
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
	 * Creates the decision tables for open editors which have not already created one at initialization
	 */
	private void createMissingConstraintTables() {
		IEditorReference[] references = getSite().getPage().getEditorReferences();
		for (IEditorReference ref : references) {
			if (ref.getEditor(false) instanceof DecisionTableEditor) {
				DecisionTableEditor editor = (DecisionTableEditor)ref.getEditor(false);
				try {
					ConstraintsTableCreationJob job = new ConstraintsTableCreationJob("Constraints Table Creation", this, editor);
					job.setPriority(Job.SHORT);
					job.setUser(true);
					job.schedule();
					//setDecisionTable(editor);
				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}		
			}
		}
	}
	
	/**
	 * Update an existing range slider component for any updates.
	 * @param slider
	 * @param filters
	 */
	private void updateRangeSliderComponent(RangeSlider slider, List<Filter> filters) {
		int[] minmax;
		if (filters.size() > 1) {
			minmax = getMinMax(filters);
			int[] range = null;
			for (Filter f : filters) {
				if (f instanceof RangeFilter) {
					range = ((RangeFilter)f).getMinMax(true);
				}
			}
			minmax[0] = Math.min(range[0], minmax[0]);
			minmax[1] = Math.max(range[1], minmax[1]);
		} else if (filters.size() == 1) {				
			RangeFilter filter = (RangeFilter)filters.get(0);
			minmax = filter.getMinMax(true);
		} else {
			return;
		}
		
		if (minmax[0] == Integer.MAX_VALUE && minmax[1] == Integer.MIN_VALUE) {
			minmax[0] = 0;
			minmax[1] = 0;
		}
		
		slider.setMinimum(minmax[0]);
		slider.setMaximum(minmax[1]);
		
	}
	
	/**
	 * Update an existing combo box component for value updates/additions/removals etc.
	 * @param combo
	 * @param columnName
	 * @param filter
	 */
	private void updateComboBox(MultiSelectComboBox combo, String columnName, Filter filter) {
		if (filter.size() == 0) {
			//Remove it
			removeColumnPanel(combo, columnName);
		}
		if (filter instanceof EqualsFilter) {
			EqualsFilter equalsFilter = (EqualsFilter)filter;
			Set<Object> keySet = equalsFilter.getKeySet();
			NavigableSet<Object> keys = new TreeSet<Object>();
			for (Object key : keySet) {
				HashSet<Cell> cells = equalsFilter.get(key);
				if (false == cells.isEmpty()) {
					for (Cell cell : cells) {
						if (cell.isEnabled()) {
							if(!DONT_CARE.equalsIgnoreCase(key.toString())) {
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
	 * <b>
	 * Do not handle this anywhere outside this class.
	 * </b>
	 * </p>
	 * @param columnName
	 * @param constraintsTable
	 * @param allColumnFilters -> To avoid recomputation in many cases.
	 */
	protected void updateUIComponents(final String columnName,
			                          final DecisionTable constraintsTable,
			                          final List<Filter> allColumnFilters) {
		
		// Keep the filters runnable, causes race conditions when dt is modified elsewhere
		final List<Filter> allFilters = (allColumnFilters == null 
				|| allColumnFilters.size() == 0) ? constraintsTable.getAllFilters(columnName) : allColumnFilters;
		
		Runnable doRun = new Runnable() {
			public void run() {
				Widget component = fComponents.get(columnName);
				if (component == null) {
					addColumnPanel(columnName, constraintsTable);
				} else {
					//If component is present;
					//check following.
					/**
					 * 1.) If there is any range filter, create/update range slider.
					 * 2.) If there is only equals filter(s), create/update combobox.
					 * 3.) If not filters and component exists remove it.
					 */
					
					if (allFilters.size() == 0 && component != null) {
						//Remove it
						removeColumnPanel(component, columnName);
					}
					for (Filter filter : allFilters) {
						if (filter instanceof RangeFilter) {
							if (!(component instanceof RangeSlider)) {
								//Probably newly added range
								//Remove this component
								//Replace with range
								removeColumnPanel(component, columnName);
								addColumnPanel(columnName, constraintsTable);
							} else {
								if (filter.size() == 0) {
									//Remove the component
									removeColumnPanel(component, columnName);
									continue;
								}
								
								
								//Update existing slider
								updateRangeSliderComponent((RangeSlider)component, allFilters);
								//Modify the text fields when range slider is modified
								
								((Text)fComponents.get(columnName + "_lower")).setText(String.valueOf(((RangeSlider)component).getLowerValue()));
								((Text)fComponents.get(columnName + "_upper")).setText(String.valueOf(((RangeSlider)component).getUpperValue()));								
							}
							//Break here as other filters need not be tested.
							break;
						} else {
							if (component instanceof MultiSelectComboBox) {
								updateComboBox((MultiSelectComboBox)component, columnName, filter);
							} else {
								//Probably removed range
								//Remove this component
								//Replace with combo
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
		fComponents.clear();
		fCurrentEditor = null;
		fCurrentOptimizedTable = null;
	}
	
	

	/**
	 * @param editor 
	 * @param decisionTable
	 */
	@SuppressWarnings("unchecked")
	public void setDecisionTable(DecisionTableEditor editor, SubMonitor progressMonitor) throws Exception {
		DecisionTableModelManager decisionTableModelManager = editor.getDecisionTableModelManager();
		if (decisionTableModelManager == null) {
			return;
		}
		
		Table tableEModel = decisionTableModelManager.getTabelEModel();
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		
		if (!editorInput.isReadOnly()) {
			final DecisionTable decisionTable = 
				DecisionTableEMFFactory.createOptimizedDecisionTable(tableEModel, progressMonitor);
	
			String tablePath = tableEModel.getFolder() + tableEModel.getName();
	
			ICommandStackListener<IExecutableCommand> commandStackListener = 
				new DecisionTableCommandStackConstraintUpdateListener(decisionTable, tableEModel);
	
			CommandFacade.getInstance().
				addCommandStackListeners(editorInput.getProjectName(), tablePath, commandStackListener);
			
			decisionTable.setProjectName(editorInput.getProjectName());
			decisionTable.printStats();
			
			editor.setDecisionTable(decisionTable);
		}
	}
	
	public void resetContextActions(DecisionTableEditor editor) {
 		if (editor != null && editor.getDecisionTableDesignViewer() != null 
				&& editor.getDecisionTableDesignViewer().getModel() != null &&
				editor.getDecisionTableDesignViewer().getModel().getRules() != null && !editor.isJar()) {
			List<DecisionRule> rules = editor.getDecisionTableDesignViewer().getModel().getRules();
			if (rules.size() > 0) {
				enableContextTools(true);
			} 
		}else {
			enableContextTools(false);
		}
	}
	
	private void enableContextTools(boolean enable) {
//		if (genTestDataAction != null) {
//			genTestDataAction.setEnabled(enable);
//		}
		if (showCoverageAction != null) {
			showCoverageAction.setEnabled(enable);
		}
		if (analyzeAction != null) {
			analyzeAction.setEnabled(enable);
		}
		if(refreshAnalyzerAction != null){
			refreshAnalyzerAction.setEnabled(enable);
		}
	}
	
	/**
	 * Refill the table analyzer with the decision table contents
	 * 
	 * @param editor The decision table editor for which the analyzer is to be set
	 * @param existingValues The contents of the decision table if any
	 * @param decisionTable The decision table for which
	 */
	public void refillComponents(DecisionTableEditor editor, final DecisionTable decisionTable) {
		
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
		}
		else if (editor.equals(fCurrentEditor)) {
			// record existing values so that they can be re-selected
			existingValues = getExistingValues();
		} 		
		else {
			existingValues = null;
		}

		columnSubstituionMap.clear();
		IDecisionTableEditorInput input  = (IDecisionTableEditorInput)editor.getEditorInput();
		final Columns columns = input.getTableEModel().getDecisionTable().getColumns();
		if (columns != null) {
			for (com.tibco.cep.decision.table.model.dtmodel.Column column: columns.getColumn()) {
				if (column.getColumnType() == ColumnType.CONDITION) {
					if (column.isSubstitution()) {
						columnSubstituionMap.put(column.getName(), column.isSubstitution());
					}
				}
			}
		}
		
		// check if the view created is new, then replace old listener with new one
		IConstraintsTableListener listener = decisionTable.getListener();
		if (listener != null) {
			if (listener.hashCode() != DecisionTableAnalyzerView.this.hashCode()) {
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
				collapsiblePanes = new ExpandBar(composite, SWT.V_SCROLL | SWT.H_SCROLL);
				collapsiblePanes.setBackground(new Color(Display.getDefault(), 198, 226, 255));
				collapsiblePanes.setFont(new Font(Display.getDefault(), "Arial", 8 , SWT.BOLD));
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
			DecisionTableUIPlugin.debug(CLASS, "Executing thread is not an SWT..Wrapping into SWT");
			Display.getDefault().syncExec(doRun);
		} else {
			doRun.run();
		}			
		setCurrentTable(editor, decisionTable);
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
			// find the new component for this column, and select the proper values
			Widget component = fComponents.get(columnName);
			if (component == null) {
				continue;
			}
			setComponentValue(component, object);
		}
	}
				
	private void setComponentValue(Widget component, Object object) {
		if (component instanceof MultiSelectComboBox && object instanceof Object[]) {
			Object[] values = (Object[]) object;
			MultiSelectComboBox combo = (MultiSelectComboBox) component;
			// TODO : handle cases in which a selected item is no longer in the combo
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

	private HashMap<String,Object> getExistingValues() {
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
			Object selectedItem = ((MultiSelectComboBox)comp).getSelectedObjects();
			if (selectedItem instanceof Object[]) {
				return selectedItem;
			} else if (selectedItem == null) {
			}
		} 
//		else if (comp instanceof RangeSlider) {
//			int lowValue = ((RangeSlider)comp).getLowValue();
//			int highValue = ((RangeSlider)comp).getHighValue();
//			return new int[] { lowValue, highValue };
//		}
		return null;
	}

	private void setCurrentTable(DecisionTableEditor editor, DecisionTable decisionTable) {
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
		ExpandItem pane = new ExpandItem(collapsiblePanes, SWT.NONE, collapsiblePanes.getItemCount());
		Composite composite = new Composite(collapsiblePanes, SWT.NONE);
		pane.setText(name);
		composite.setLayout(new GridLayout());
		//composite.setBackground(new Color(Display.getDefault(), 159, 182, 205));	
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
	private Widget componentizeEqualsFilter(Composite composite, String columnName, EqualsFilter equalsFilter) {
		// filter disabled cells
		NavigableSet<Object> keys = new TreeSet<Object>();
		Set<Object> keySet = equalsFilter.getKeySet();
		for (Object key : keySet) {
			HashSet<Cell> cells = equalsFilter.get(key);
			if (null == cells) continue;
			for (Cell cell : cells) {
				if (cell.isEnabled()) {
					if(!DONT_CARE.equalsIgnoreCase(key.toString())) {
						keys.add(key);
					}
					continue;
				}
			}
		}
		
		MultiSelectComboBox cbl = new MultiSelectComboBox(composite, SWT.NONE, new ArrayList<Object>(keys));
		fComponents.put(columnName, cbl);
		return cbl;
	}
	

	/**
	 * @param columnName
	 * @return
	 */
	private Widget createTypedPanel(Composite composite, String columnName, DecisionTable decisionTable) {
		List<Filter> allFilters = decisionTable.getAllFilters(columnName);
		if (allFilters.size() == 0)
			return null; // return null component if filters are empty
		if (allFilters.size() == 1) {
			Filter filter = allFilters.get(0);
			if (filter instanceof EqualsFilter) {
				return componentizeEqualsFilter(composite, columnName, (EqualsFilter)filter);
			} else if (filter instanceof RangeFilter) {
				RangeFilter rf = (RangeFilter) filter;
				int[] minmax = rf.getMinMax(true);
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
					combinationFilter = (EqualsFilter)temp;
				}
			}
			//Should be one ComboFilter and one RangeFilter, 
			//Signifying a combination of ranges and equality checks
			if (hasRangeFilter) {
				//Do this only if there is a range filter.
				int[] minMax = getMinMax(allFilters);
				if (minMax == null) {
					// could not find range
					Text textField = new Text(composite, SWT.SINGLE);
					//SelectAllUtils.install(textField);
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
				
				int[] range = rangeFilter.getMinMax(true);
				range[0] = Math.min(range[0], minMax[0]);
				range[1] = Math.max(range[1], minMax[1]);
				if (range[0] == Integer.MAX_VALUE && range[1] == Integer.MIN_VALUE) {
					range[0] = 0;
					range[1] = 0;
				} 
				return createRangeComponent(composite, columnName, range);
			} else {
				//Resort to equals case
				return componentizeEqualsFilter(composite, columnName, combinationFilter);
			}
		} 
		Text textField = new Text(composite, SWT.SINGLE);
		//SelectAllUtils.install(textField);
		fComponents.put(columnName, textField);
		return textField;
	}

	private Widget createRangeComponent(Composite composite, String name, int[] minmax) {
		if (minmax == null)
			return null;
		if (minmax[0] == Integer.MIN_VALUE || minmax[1] == Integer.MAX_VALUE) {
			return null;

		} else {
			try {
				
				final RangeSlider hRangeSlider = new RangeSlider(composite, SWT.HORIZONTAL);
				final GridData gd = new GridData(GridData.FILL, GridData.CENTER, true, false, 1, 2);
				gd.widthHint = 250;
				hRangeSlider.setLayoutData(gd);

				hRangeSlider.setMaximum(minmax[1]);
				hRangeSlider.setMinimum(minmax[0]); 
				hRangeSlider.setUpperValue(minmax[1]);
				hRangeSlider.setLowerValue(minmax[0]);

				Composite boundComposite = new Composite(composite, SWT.NONE);
				boundComposite.setLayout(new GridLayout(2, true));
				final Label hLabelLower = new Label(boundComposite, SWT.NONE);
				hLabelLower.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 1, 1));
				hLabelLower.setText("Lower Value");

				final Text hTextLower = new Text(boundComposite, SWT.BORDER);
				hTextLower.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 1, 1));
				hTextLower.setText(String.valueOf(hRangeSlider.getLowerValue()));
				hTextLower.setEnabled(true);
				hTextLower.addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e) {
						if(e.getSource() == hTextLower){
							String s = ((Text)(e.getSource())).getText();
							if (s != null && !s.trim().isEmpty()) {
								try {
									hRangeSlider.setLowerValue( Integer.parseInt(s));
								} catch (NumberFormatException nfe) {
									DecisionTableUIPlugin.log(nfe);
								}
							}	
						}

					}
				});

				final Label hLabelUpper = new Label(boundComposite, SWT.NULL);
				hLabelUpper.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 1, 1));
				hLabelUpper.setText("Upper Value");

				final Text hTextUpper = new Text(boundComposite, SWT.BORDER);
				hTextUpper.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 1, 1));
				hTextUpper.setText(String.valueOf(hRangeSlider.getUpperValue()));
				hTextUpper.setEnabled(true);
				hTextUpper.addModifyListener(new ModifyListener() {

					@Override
					public void modifyText(ModifyEvent e) {
						if(e.getSource() == hTextUpper){
							String s = ((Text)(e.getSource())).getText();
							if (s != null && !s.trim().isEmpty()) {
								try {
									hRangeSlider.setUpperValue( Integer.parseInt(s));
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
						
						if(e.getSource() instanceof RangeSlider){
							RangeSlider rs = (RangeSlider)e.getSource();
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


	private int[] getMinMax(List<Filter> allFilters) {
		Filter filter1 = allFilters.get(0);
		Filter filter2 = allFilters.get(1);
		if (filter1 instanceof EqualsFilter) {
			// need to find the min/max of the equality to ensure the range covers those values
			EqualsFilter eq = (EqualsFilter) filter1;
			return getMinMax(eq);
		} else if (filter2 instanceof EqualsFilter) {
			// need to find the min/max of the equality to ensure the range covers those values
			EqualsFilter eq = (EqualsFilter) filter2;
			return getMinMax(eq);
		}
		return null;
	}

	private int[] getMinMax(EqualsFilter equalsFilter) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		HashSet<Object> enabledKeys = new LinkedHashSet<Object>();
		Set<Object> keySet = equalsFilter.getKeySet();
		for (Object object : keySet) {
			HashSet<Cell> cells = equalsFilter.get(object);
			for (Cell cell : cells) {
				if (cell.isEnabled()) {
					
			//TO DO: Get the Column type and put a check
			/** Columns allColumns = (Columns) fCurrentOptimizedTable.getColumns();
			  	Int propertyType = allColumns.search(cell.columnId).getPropertyType();
				DecisionTableAnalyzerUtils.getColumnFromId(cell.columnId,fCurrentOptimizedTable.); */
				enabledKeys.add(object);
				continue;
				}
			}
		}
		
		Object[] array = enabledKeys.toArray();
		for (Object object : array) {
			if (object instanceof String) {
				String value = (String)object;
				// check for ANDed cells
				if (value.contains(COND_OPERATORS.OR.opString())) {
					StringTokenizer tokens = new StringTokenizer(value, COND_OPERATORS.OR.opString());
					while (tokens.hasMoreTokens()) {
						String token = tokens.nextToken().trim();
						if (!DONT_CARE.equals(token)) {
							try {
								if(value.contains(".")) {
									Double val = Double.valueOf(token);
									min = (int) Math.min(min, Math.floor(val));
									max = (int) Math.max(max, Math.floor(val));
								} else {
									int val = Integer.valueOf(token);
									min = Math.min(min, val);
									max = Math.max(max, val);
								}
							} catch (NumberFormatException nfe) {
								DecisionTableUIPlugin.log(nfe);
								continue;
							}
						}
					}
				} else {
					if (!DONT_CARE.equals(value)) {
						try {
							if (value.contains(".")) {
								Double val = Double.valueOf(value);
								min = (int) Math.min(min, Math.floor(val));
								max = (int) Math.max(max, Math.floor(val));
							} else {
								int val = Integer.valueOf(value);
								min = Math.min(min, val);
								max = Math.max(max, val);
							}
						} catch (NumberFormatException nfe) {
							DecisionTableUIPlugin.log(nfe);
							continue;
						}
					}
				}
			}
		}
		return new int[] { min, max };
	}

	private IFile getTableFile() {
		DecisionTableEditor editor = getCurrentEditor();
		if (editor != null && editor.getEditorInput() instanceof FileEditorInput) {
			return ((FileEditorInput)editor.getEditorInput()).getFile();
		}
		return null;
	}
	
	private void createActions() {
		refreshAnalyzerAction = new RefreshAnalyzerAction(this);
		analyzeAction = new AnalyzeAction(this);
		showCoverageAction = new ShowCoverageAction (this);
//		genTestDataAction = new GenerateTestDataAction(this);
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
		if (canGenerateTestData()) {
			manager.add(new Separator());
//			manager.add(genTestDataAction);
		}
	}

	private boolean canGenerateTestData() {
		if (getAnalyzerExtension() != null) {
			return getAnalyzerExtension().canGenerateTestData();
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
		if (canGenerateTestData()) {
			manager.add(new Separator());
//			manager.add(genTestDataAction);
		}
	}

	public ExpandBar getCollapsiblePanes() {
		return collapsiblePanes;
	}

	public void setFocus() {
		//this.swtAwtComponent.setFocus();
	}

	public DecisionTable getCurrentOptimizedTable() {
		return fCurrentOptimizedTable;
	}
	
	public void setCurrentOptimizedTable(DecisionTable table) {
		fCurrentOptimizedTable = table;
	}
	
	public DecisionTableEditor getCurrentEditor() {
		return fCurrentEditor;
	}

	public void setCurrentDecisionTable (DecisionTableEditor editor) {
		fCurrentEditor = editor;
	}
	
	public Map<String, Widget> getComponents() {
		return Collections.unmodifiableMap(fComponents);
	}

//	public GenerateTestDataAction getGenTestDataAction() {
//		return genTestDataAction;
//	}
	
	public IDecisionTableAnalyzerExtension getAnalyzerExtension() {
		if (hasCheckedForExtension) {
			return analyzerExtension;
		}
		if (analyzerExtension == null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(DecisionTableUIPlugin.PLUGIN_ID, EXTENSION_ID);
			if (elements != null && elements.length > 0) {
				try {
					Object ext = elements[0].createExecutableExtension(EXTENSION_ATTR_ID);
					if (ext instanceof IDecisionTableAnalyzerExtension) {
						analyzerExtension = (IDecisionTableAnalyzerExtension) ext;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		if (analyzerExtension == null) {
			analyzerExtension = new DefaultDecisionTableAnalyzerExtension();
		}
		hasCheckedForExtension  = true;
		return analyzerExtension;
	}
	
	class RefreshAnalyzerAction extends Action {

		private IViewPart part;
		public RefreshAnalyzerAction(IViewPart part) {
			this.part = part;
			setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/refresh_16x16.png"));
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			if (getComponents().keySet().size() == 0) {
				try {
					IWorkbenchPage page = part.getSite().getPage();
					if (page != null) {
						IEditorPart activeEditorPart = page.getActiveEditor();
						if (activeEditorPart != null) {
							if (activeEditorPart instanceof DecisionTableEditor) {
								DecisionTableEditor editor = (DecisionTableEditor)activeEditorPart;
								ConstraintsTableCreationJob job = new ConstraintsTableCreationJob
										("Constraints Table Creation", (DecisionTableAnalyzerView)part, editor);
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
}
