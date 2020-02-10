package com.tibco.cep.studio.decision.table.ui.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.validators.DecisionTableResourceSyntaxValidator;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.TableRuleInfo;
import com.tibco.cep.studio.core.validation.dt.IMarkerErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.TableModelErrorsRecorder;
import com.tibco.cep.studio.decision.table.action.handler.DecisionTableActionHandler;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableCellModificationCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableCommandStackEditorOpsListener;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTable;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.editor.AbstractDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.providers.DomainValueDataConverter;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.dnd.DTDropListener;
import com.tibco.cep.studio.decision.table.ui.handler.DecisionTableEditorPartListener;
import com.tibco.cep.studio.decision.table.ui.job.ConstraintsTableCreationJob;
import com.tibco.cep.studio.decision.table.ui.perspective.DecisionTablePerspective;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.decision.table.utils.RuleIDGeneratorManager;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

//TODO Also implement the IGotoMarker interface so as to resolve the goto from problems view to highlight the problematic row
public class DecisionTableEditor extends AbstractDecisionTableEditor implements ITabbedPropertySheetPageContributor, ISelectionProvider, IGotoMarker, IDecisionTableEditor  {

	public final static String ID = "com.tibco.cep.decision.table.editor";
	
	private Table table;
	private DecisionTableDesignViewer decisionTableDesignViewer;

	private ISelection editorSelection;	
	private boolean jar = false;
	private IEditorInput input;
	private HashMap<String, Action> selectionProvider;
	private DecisionTable decisionTable;
	public final ReentrantLock lock = new ReentrantLock();
	private List<String> rowsToHighlight = null;
	
	private DecisionTableEditorPartListener decisionTableEditorPartListener;
	
	private boolean invalidPart = false;
	
	protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

	private IContextActivation contextToken;

	@Override
	public void doSave(IProgressMonitor monitor) {
		// If save button or Ctrl+S is pressed
		
		final ProgressMonitorDialog pdialog = new ProgressMonitorDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell()) {
				@Override
				protected void createDialogAndButtonArea(Composite parent) {
					dialogArea = createDialogArea(parent);
					applyDialogFont(parent);
				}
			};
			pdialog.setCancelable(false);
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					try {
						saving = true;
						monitor.beginTask("Saving Decision Table...", 100);
						AbstractDecisionTableEditorInput editorInput = (AbstractDecisionTableEditorInput) getEditorInput();
						RuleFunction virtualRuleFunction = editorInput.getVirtualRuleFunction();
						String dtName = editorInput.getName();
						if (virtualRuleFunction != null && dtName != null) {
							saveModel(virtualRuleFunction, dtName);
						}

//						ModelUtils.saveEObject(table);
						setModified(false);
						firePropertyChange(IEditorPart.PROP_DIRTY);
						monitor.worked(95);
						monitor.setTaskName("Decision Table is saved");
						monitor.done();
						//Create a check point command here if save is successful
						CommandFacade.getInstance().
						createCheckpoint(editorInput.getProjectName(),
								         editorInput.getTableEModel(),
								         null);
						FileEditorInput fileEditorInput = (FileEditorInput) getEditorInput();
//						CommonUtil.refresh(fileEditorInput.getFile(), IFile.DEPTH_ONE, true);
						fileEditorInput.getFile().refreshLocal(IFile.DEPTH_ONE, monitor);
					} catch (Exception e) {
						DecisionTableUIPlugin.log(e);
					}
					finally {
						saving = false;
					}
				}
			};
			try {
				pdialog.run(false, true, runnable);
			} catch (InvocationTargetException e) {
			} catch (InterruptedException e) {
				DecisionTableUIPlugin.log(e);
			} catch (Exception e) {
				DecisionTableUIPlugin.log(e);
			}
			pdialog.close();
			
			refreshAnalyzerView();
		
			//updateConstraintActionItem(this.getEditorSite().getPage());
			//	DecisionTableOverviewUtils.updateTableOverview( this.getEditorSite().getPage(), this);
	}

    private void refreshAnalyzerView() {
        populateDecisionTableConstraintEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), false);
	}

	@Override
	public void dispose() {
		if (decisionTableDesignViewer != null){
			decisionTableDesignViewer.dispose();
		}
		//TODO dispose DecisionTableSWTFormViewer properly
		if (getEditorInput() instanceof IDecisionTableEditorInput) {
        	// clear the command stack and remove it from the map of command stacks
    		CommandFacade.getInstance().clearCommandStack(table, table.getOwnerProjectName());
    		//reset Rule ID's
    		RuleIDGeneratorManager.INSTANCE.resetIDGenerator(table.getOwnerProjectName(), table.getPath());
		}
		
		 // dispose the dt structures
        if (decisionTable != null) {
        	decisionTable.clear();
        }
        
        IWorkbenchPage page = getSite().getPage();
        DecisionTableAnalyzerView view = (DecisionTableAnalyzerView) page.findView(DecisionTableAnalyzerView.ID);
        if (view != null) {
        	view.setCurrentDecisionTable(null);
        	view.setCurrentOptimizedTable(null);
        }
        
        // dispose the DT structure
        decisionTable = null;
        
        if (decisionTableEditorPartListener != null) {
        	getSite().getPage().removePartListener(decisionTableEditorPartListener);
        }
        
        super.dispose();
	}

	private void saveModel(RuleFunction virtualRuleFunction, String dtName) throws Exception {
		long startTime = System.currentTimeMillis();
		
		AbstractDecisionTableEditorInput editorInput = (AbstractDecisionTableEditorInput)getEditorInput();
		Table tableEModel = editorInput.getTableEModel();
		//Save the since part
		tableEModel.setSince(DTConstants.SINCE_BE_40);
		
		IStatus status = editorInput.saveModel(virtualRuleFunction, tableEModel);
		
		if (status.getCode() == IStatus.ERROR) {
			throw new Exception(status.getException());
		}
		long endTime = System.currentTimeMillis();
		DecisionTableUIPlugin.debug(getClass().getName(), "Exported EMF Model to XMI in:{0} sec. " , (endTime - startTime) / 1000.0);
	}
	
	@Override
	public void doSaveAs() {
		//Do nothing, not supported
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void createPages() {
		// wait for the index to get loaded
		StudioCorePlugin.getDesignerModelManager().getAllIndexes();
		IndexUtils.waitForUpdate();
		
		addFormPage();
		updateTitle();
		addTableAnalyzerView();
		setCatalogFunctionDrag(true);
		updateTab();
		
		if (getEditorInput() instanceof JarEntryEditorInput) {
			this.setInput(input);
		}
		
		if (invalidPart) {
			MessageDialog.openError(getSite().getShell(), "Opening Decision Table Editor Error", "Decision Table Editor can't be opened");
		}
	}

	public void addTableAnalyzerView() {
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				try {
					final IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
					boolean tableAnalyzer = prefStore.getBoolean(PreferenceConstants.RUN_ANALYZER);
					if (tableAnalyzer) {

						//Populating Decision Table Constraint Editor
						populateDecisionTableConstraintEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), true);
					}
				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		}, false);		
	}
	
	protected void updateTab() {
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}

	@Override
	public void init(final IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		site.setSelectionProvider(this);
		this.setPartName(getEditorInput().getName());
		this.setTitleImage(DecisionTableUIPlugin.getDefault().getImage("icons/decisiontablerulefunctions_16x16.png"));

		decisionTableEditorPartListener = new DecisionTableEditorPartListener(this);
		site.getPage().addPartListener(decisionTableEditorPartListener);

		if (input instanceof JarEntryEditorInput) {
			this.input = input;
		}		

		//Check whether valid Decision Table Editor
		if (isInvalidEditor(input)) {
			return;
		}
		
		activateDTEditorContext();
		//Initialize the Decision Table perspective
		initPerspective(site);		
	}

	/**
	 * Initialize the DT perspective
	 * @param site
	 * 
	 */
	public void initPerspective(IEditorSite site) {
		final IWorkbenchPage page = site.getPage();
		IPerspectiveDescriptor descriptor = page.getPerspective();
		
		if (descriptor == null || !(DecisionTablePerspective.ID).equals(descriptor.getId())) {
			IWorkbenchWindow window = site.getWorkbenchWindow();
			IWorkbench bench = window.getWorkbench();
			try {
				bench.showPerspective(DecisionTablePerspective.ID, window);
			} catch (WorkbenchException e) {
				DecisionTableUIPlugin.log(e);
			}
		}
	}
	
	public void modified() {
		super.modified();
		getDtDesignViewer().updateToolBars();
		getDtDesignViewer().updateEmptyTooltipVisibility();
	}

	/**
	 * Restore editor state by making it non-dirty 
	 */
	public void editorContentRestored() {
		Display display = Display.getDefault();
		if (display.getThread() != Thread.currentThread()) {
			display.syncExec(new Runnable() {
				public void run() {
					isModified = false;
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			});
		} else {
			isModified = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		getDtDesignViewer().updateToolBars();
	}

	@SuppressWarnings("unchecked")
	protected void addFormPage()  {
		
		IDecisionTableEditorInput dei = (IDecisionTableEditorInput)getEditorInput();
		//Disable editor based on editor input
		setEnabled(!dei.isReadOnly());
					//Load model
		dei.loadModel();
		decisionTableDesignViewer = new DecisionTableDesignViewer(getTable(dei), this, invalidPart);
		decisionTableDesignViewer.createPartControl(getContainer());
		addPage(decisionTableDesignViewer.getControl());        
		this.setActivePage(0);
		this.setForm(decisionTableDesignViewer.getForm());
		this.setInput(dei);

		if (!dei.isReadOnly()) {
			ICommandStackListener<IExecutableCommand> commandStackModificationListener =
				new DecisionTableCellModificationCommandListener(decisionTableDesignViewer);
			ICommandStackListener<IExecutableCommand> commandStackEditorOpsListener =
					new DecisionTableCommandStackEditorOpsListener(this);
		
			CommandFacade.getInstance().addCommandStackListeners(table.getOwnerProjectName(), 
					table.getPath(), 
					commandStackModificationListener,
					commandStackEditorOpsListener);
			
		}
	}
	
	private Table getTable(IEditorInput editorInput) {
		if (this.table == null) {
			if (editorInput instanceof IDecisionTableEditorInput){
				this.table =  ((IDecisionTableEditorInput) editorInput).getTableEModel();
				this.table.setOwnerProjectName(((IDecisionTableEditorInput) editorInput).getProjectName());
			}
			else if (editorInput instanceof FileEditorInput) {
				IFile file = ((FileEditorInput) editorInput).getFile();
				EObject loadEObject = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
				if (loadEObject instanceof Table) {
					this.table = (Table) loadEObject;
					this.table.setOwnerProjectName(file.getProject().getName());
				}
			}
		}
		return this.table;
	}
	
	public Action getActionHandler(String id) {
		if (selectionProvider == null) {
			selectionProvider = new HashMap<String, Action>();
		}
		if (selectionProvider.get(id) == null) {
			selectionProvider.put(id, new DecisionTableActionHandler(id, this));
		}
		return selectionProvider.get(id);
	}
	
	public Table getTable(){
		return table;
	}

	public DecisionTableDesignViewer getDtDesignViewer() {
		return getDecisionTableDesignViewer();
	}

	@Override
	public IDecisionTableEditorInput getEditorInput() {
		IEditorInput inputEditorInput =  super.getEditorInput();
		IDecisionTableEditorInput outputEditorInput = null;
		if (inputEditorInput instanceof IDecisionTableEditorInput) {
			outputEditorInput = (IDecisionTableEditorInput)inputEditorInput;
		} else if (inputEditorInput instanceof FileEditorInput) {
			outputEditorInput = new DefaultDecisionTableEditorInput(((FileEditorInput)inputEditorInput).getFile());			
		} else if (inputEditorInput instanceof JarEntryEditorInput) {
			//Also disable editor
			outputEditorInput = new DecisionTableContentEditorInput((JarEntryEditorInput)inputEditorInput);
		}
		return outputEditorInput;
	}
	
	@Override
    public boolean isEditorInputReadOnly() {
	    return getEditorInput().isReadOnly();
	}
	
	@Override
	public String getContributorId() {
		return "com.tibco.cep.studio.decision.table.propertyContributor";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapterClass) {
		if (adapterClass == IPropertySheetPage.class) {
			return new TabbedPropertySheetPage(this);
		}
		return super.getAdapter(adapterClass);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (!selectionChangedListeners.contains(listener)) {
			selectionChangedListeners.add(listener);
		}
	}

	@Override
	public ISelection getSelection() {
		return editorSelection;	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		if (listener != null) {
			selectionChangedListeners.remove(listener);
		}
	}

	@Override
	public void setSelection(ISelection selection) {
		editorSelection = selection;
		if (selection != null) {
			for (Iterator<ISelectionChangedListener> listeners = selectionChangedListeners
					.iterator(); listeners.hasNext();) {
				ISelectionChangedListener listener = listeners.next();
				try {
					listener.selectionChanged(new SelectionChangedEvent(this,
							selection));
				} catch (Exception e) {
				}
			}
		}
	}
	
	public Object getModelDataByPosition(int columnIndex, int rowIndex, NatTable natTable) {
				
		DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
		SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
		if(rowIndex >= 0 && columnIndex >= 0 && dataProvider.getRowCount() > 0) {
			TableRule rule = dataProvider.getRowObject(rowIndex);
			Column column = table.getDecisionTable().getColumns().getColumn().get(columnIndex);
			EList<TableRuleVariable> trvs = null;
			if (column.getColumnType() == ColumnType.CONDITION || column.getColumnType() == ColumnType.CUSTOM_CONDITION) {
				trvs = rule.getCondition();
			} else {
				trvs = rule.getAction();
			}
			for (TableRuleVariable tableRuleVariable : trvs) {
				if (tableRuleVariable.getColId().equals(column.getId())) {
					return tableRuleVariable;
				}
			}
			// if there is no TRV for the selected cell, it doesn't yet have value set.
//			dataProvider.setDataValue(columnIndex, rowIndex, "");
//			return getModelDataByPosition(columnIndex, rowIndex, natTable);
		}
		
		return null;
	}
	
	public void asyncModified(){
	  	  Display.getDefault().asyncExec(new Runnable(){

	  		@Override
	  		public void run() {
	  			modified();
	  	}});
	}
	
	public DecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}
	
	/**
	 * @param page
	 * @param editor
	 */
	private void populateDecisionTableConstraintEditor(IWorkbenchPage page, boolean showView) {
		IViewPart dtConstraintsView = null;
		try {
            dtConstraintsView = showView ? page.showView(DecisionTableAnalyzerView.ID) : page.findView(DecisionTableAnalyzerView.ID);
			if (dtConstraintsView != null) {
				DecisionTableAnalyzerView analyzerView = (DecisionTableAnalyzerView) dtConstraintsView;

				try {
					ConstraintsTableCreationJob job = new ConstraintsTableCreationJob(
							"Constraints Table Creation", analyzerView, this);
					job.setPriority(Job.SHORT);
					job.setUser(true);
					job.schedule();
				} catch (Exception e) {
					DecisionTableUIPlugin.log(e);
				}
			}
		} catch (PartInitException e) {
			DecisionTableUIPlugin.log(e);
		}		
	}
	
	private void updateConstraintActionItem(IWorkbenchPage page) {
		IViewPart dtConstraintsView = page.findView(DecisionTableAnalyzerView.ID);

		if (dtConstraintsView != null) {
			DecisionTableAnalyzerView analyzerView = (DecisionTableAnalyzerView) dtConstraintsView;
				analyzerView.resetContextActions();
		}
	}

	public List<String> getRowsToHighlight() {
		return rowsToHighlight;
	}

	public void setRowsToHighlight(List<String> rowsToHighlight) {
		this.rowsToHighlight = rowsToHighlight;
	}

	@Override
	public void gotoMarker(IMarker marker) {
		//Check category
		try {
			//Get location
			String location = marker.getAttribute(IMarker.LOCATION, "-1");
			if (TableModelErrorsRecorder.TABLE_SYNTAX_ERROR_MARKER.equals(marker.getType())) { 
				//Location form [rowid, Column:<columnName>]
				//Split
				String rowId = 
						location.substring(location.indexOf('[') + 1, location.indexOf(','));
				String columnName = 
						location.substring(location.indexOf(':') + 2, location.indexOf(']'));
				String trvId = marker.getAttribute(IMarkerErrorRecorder.MARKER_TRV_ATTR, "1_1");
				selectRows(rowId, columnName, trvId);

				return; 
			}
			Object lineNumber = marker.getAttribute(IMarker.LINE_NUMBER);
			if (lineNumber instanceof Integer) {
				// if the marker has a line number attribute, just select it
				String ln = String.valueOf(lineNumber);
				selectRows(ln, null, null);
			}
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	private void selectRows(String rowId, String columnName, String trvId) {
		Table tableEModel = getTable();
		//DT or ET?
		TableRuleInfo tableRuleInfo = 
			ModelUtils.DecisionTableUtils.getTableRuleInfoFromId(rowId, tableEModel);
		
		if(tableRuleInfo == null){
			MessageDialog.openError(getSite().getShell(), "Error",
					"Selected location '"+rowId+"' in '" + tableEModel.getName() + "' does not exist.");
			return;
		}
		
		TableTypes tableType = tableRuleInfo.getTableTypes();
		switch (tableType) {
		case DECISION_TABLE:
			break;
		case EXCEPTION_TABLE:
			break;
		}
		int problemColPosition = columnName == null ? 0 : DecisionTableUtil.getColumnPosition(getProject().getName(), 
					                            tableEModel, 
					                            tableType, 
					                            columnName,
					                            trvId);
		int problemRowPosition = DecisionTableUtil.getRowPosition(getProject().getName(), tableEModel, tableType, rowId);
		
		selectRow(problemColPosition, problemRowPosition);
	}

	private void selectRow(int problemColPosition, int problemRowPosition) {
		selectRows(problemColPosition, new int[] { problemRowPosition });
	}
	
	private void selectRows(int problemColPosition, int[] problemRowPosition) {
		if (problemRowPosition.length > 0) {
			decisionTableDesignViewer.getDecisionTable().doCommand(new SelectRowsCommand(decisionTableDesignViewer.getDtBodyLayer().getSelectionLayer(), problemColPosition, problemRowPosition, false, false, problemRowPosition[0]));
		}
	}
	
	private boolean isInvalidEditor(IEditorInput input) {
		if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput)input).getFile();
			DesignerElement e = IndexUtils.getElement(file);
			if (e instanceof DecisionTableElement) {
				DecisionTableElement element = (DecisionTableElement)e;
				if(element.getImplementation() instanceof Table) {
					Table table =(Table)element.getImplementation();
					String implement = table.getImplements();
					if (!DecisionTableResourceSyntaxValidator.isValid(getProject(), implement)) {
						invalidPart = true;
						return invalidPart;
					}
				}
			} else {
				return false;
			}
		}
		return invalidPart;
	}
	
	@Override
	public void updateTitle() {
		IEditorInput input = getEditorInput();
		if (input instanceof IDecisionTableEditorInput) {
			IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)input;
			String name = editorInput.getName();
			if (!name.endsWith(".rulefunctionimpl")) {
				name = name + ".rulefunctionimpl";
			}
			setPartName(name);
			setTitleToolTip(editorInput.getToolTipText());
		} else {
			setPartName(getEditorInput().getName() + " [Invalid]");
			setTitleToolTip(getEditorInput().getToolTipText());
		}
	}
	
	public DecisionTableDesignViewer getDecisionTableDesignViewer() {
		return decisionTableDesignViewer;
	}
	
	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.decision.table.perspective";
	}
	
	@Override
    public DecisionTableColumnIdGenerator getColumnIdGenerator() {
        return getEditorInput().getColumnIdGenerator();
    }
	
    @Override
    public String getProjectName() {
        return getEditorInput().getProjectName();
    }

    public boolean isInvalidPart() {
		return invalidPart;
	}
	
	@Override
	public boolean isJar() {
		return jar;
	}

	@Override
	public void setJar(boolean jar) {
		this.jar = jar;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				getSite().getPage().closeEditor(getEditor(), true);
			}
		});		
	}

	@Override
	public DropTargetListener createDropTargetListener(NatTable natTable,
			TableTypes tableType) {
		return new DTDropListener(natTable, this, tableType);
	}

	@Override
	public List<List<String>> getDomainValues(Column column) {
	//	refactor to allow SB domains
		List<List<String>> values = new ArrayList<>();
		String substitutionFormat = 
				(column.isSubstitution()) ? 
						DTLanguageUtil.canonicalizeExpression(column.getName().substring(column.getName().indexOf(' '))) : "";

		List<DomainInstance> domainInstances = DTDomainUtil.getDomains(column.getPropertyPath(), getProjectName());
		if (domainInstances != null && domainInstances.size() > 0) { 
			List<List<String>> values2 = DTDomainUtil.getDomainEntryDropDownStrings(domainInstances, 
					column.getPropertyPath(), 
					getProjectName(), 
					substitutionFormat,
					column.getColumnType() == ColumnType.ACTION,
					false);
			List<String> valuesList = values2.get(0);
			for (int i = 0; i < valuesList.size(); i++) { //Rows
				List<String> row = new ArrayList<String>();
				for (List<String> c : values2) {
					row.add(c.get(i));
				}	 
				values.add(row);
			}		
		}
		
		return values;
	}

	@Override
	public DomainValueDataConverter getDomainValueDataConverter(boolean multiValue, String separator) {
		return new DomainValueDataConverter(multiValue, separator);
	}
	
    public void activateDTEditorContext() {
        if (contextToken == null) {
            IEditorSite site = getEditorSite();
            if (site != null) {
                IContextService c = (IContextService) site.getService(IContextService.class);
                contextToken = c.activateContext("com.tibco.cep.decision.table.ui.dteditorcontext");
                contextToken.getContextService().getActiveContextIds();
            }
        }
    }

    public void deactivateDTEditorContext() {
        if (contextToken != null) {
        	IEditorSite site = getEditorSite();
            if (site != null) {
                IContextService c = (IContextService) site.getService(IContextService.class);
                c.deactivateContext(contextToken);
                contextToken = null;
            }
        }
    }
    
}
