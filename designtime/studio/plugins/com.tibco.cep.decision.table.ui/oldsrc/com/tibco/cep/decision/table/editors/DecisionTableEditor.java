package com.tibco.cep.decision.table.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
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
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionDataModelListener;
import com.jidesoft.decision.DecisionTableModel;
import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.SortableTableModel;
import com.jidesoft.grid.TableScrollPane;
import com.jidesoft.grid.TableUtils;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.listeners.DecisionTableCommandStackEditorOpsListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableCommandStackListener;
import com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.decision.table.handler.DecisionTableEditorPartListener;
import com.tibco.cep.decision.table.handler.DecisionTableFrontEndModelLoader;
import com.tibco.cep.decision.table.listener.DefaultDecisionTableModelListener;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.perspective.DecisionTablePerspective;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.properties.DecisionTablePropertySheetPage;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.jobs.ConstraintsTableCreationJob;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.RuleIDGeneratorManager;
import com.tibco.cep.decision.table.validators.DecisionTableResourceSyntaxValidator;
import com.tibco.cep.decision.table.wizard.SaveAsDecisionTableWizard;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.TableXMLLoad;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.TableRuleInfo;
import com.tibco.cep.studio.core.validation.dt.IMarkerErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.TableModelErrorsRecorder;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tibco.cep.studio.ui.overview.CommonOverview;
import com.tibco.cep.studio.ui.overview.DecisionTableOverviewUtils;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.util.StudioConfig;

/**
 * 
 * @author gabe,
 * @author sasahoo
 * @author rmishra
 * @author aathalye 
 * @author ssailapp
 * @since 3.0
 */

public class DecisionTableEditor extends AbstractDecisionTableEditor implements IGotoMarker, ITabbedPropertySheetPageContributor, ISelectionProvider {
	
	public final static String ID = "com.tibco.cep.studio.ui.editors.decisiontableeditor";
	
	protected DecisionTableDesignViewer decisionTableDesignViewer;
	
	private DecisionTableModelManager decisionTableModelManager;
	
	/**
	 * For events on regular decision table model. Takes care of sorting case also.
	 */
	private DefaultDecisionTableModelListener decisionTableModelListener;
	
	/**
	 * For events on regular exception table model. Takes care of sorting case also.
	 */
	private DefaultDecisionTableModelListener exceptionTableModelListener;
	
	protected int SWUIPageIndex;
	
	private boolean isEditorContentModified;
	
	private HashMap<String, Action> selectionProvider;
	
	private boolean invalidPart = false;
	
	protected com.tibco.cep.decision.table.constraintpane.DecisionTable decisionTable;
	
	private IDecisionTableEditorInput dei;
	
	private IPreferenceStore prefStore;
	
	private boolean tableAnalyzer = false;
	
	public final ReentrantLock lock = new ReentrantLock();
	
	private DecisionTableEditorPartListener decisionTableEditorPartListener;
	
	private boolean jar = false;
	
	private IEditorInput input;
	
	/**
	 * To load UI model from EMF model during editor initialization.
	 */
	private DecisionTableFrontEndModelLoader decisionTableFrontEndModelLoader;

	private ISelection editorSelection;
	
	protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

	public com.tibco.cep.decision.table.constraintpane.DecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void setDecisionTable(com.tibco.cep.decision.table.constraintpane.DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
		setCatalogFunctionDrag(true);
		
		if (isJar()) {
			this.setInput(input);
		}
		
		if (invalidPart) {
			MessageDialog.openError(getSite().getShell(), "Opening Decision Table Editor Error", "Decision Table Editor can't be opened");
		}
	}

	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}
	
	private <T extends TableModelListener & DecisionDataModelListener> void registerUIModelListeners(JTable mainTable, 
			                              DecisionDataModel decisionDataModel,
			                              T tableModelListener,
			                              TableTypes tableType) {
		FilterableTableModel filterableTableModel = (FilterableTableModel)mainTable.getModel();
		SortableTableModel sortableTableModel = (SortableTableModel)filterableTableModel.getActualModel();
		sortableTableModel.getActualModel().addTableModelListener(tableModelListener);
		decisionDataModel.addDecisionDataModelListener(tableModelListener);
	}
	
	@SuppressWarnings("unchecked")
	private void addFormPage() throws PartInitException {
		decisionTableDesignViewer = new DecisionTableDesignViewer(this, decisionTableModelManager, invalidPart);
		decisionTableDesignViewer.createPartControl(getContainer());
		SWUIPageIndex = addPage(decisionTableDesignViewer.getControl());
		if (!invalidPart) {
			// add preference listener to pivot table pane
			DecisionTablePane dtPane = decisionTableDesignViewer.getDecisionTablePane();
			DecisionTablePane etPane = decisionTableDesignViewer.getExceptionTablePane();

			TableScrollPane tscDTPane = dtPane.getTableScrollPane();
			JTable dtMainTable = tscDTPane.getMainTable();

			TableScrollPane tscETPane = etPane.getTableScrollPane();
			JTable etMainTable = tscETPane.getMainTable();

			DecisionDataModel decisionDataModel = dtPane.getDecisionDataModel();
			DecisionDataModel exceptionDataModel = etPane.getDecisionDataModel();
			// add listeners to DecisionTable as well as ExceptionTable
			decisionTableModelListener = new DefaultDecisionTableModelListener(this, TableTypes.DECISION_TABLE);
			exceptionTableModelListener = new DefaultDecisionTableModelListener(this, TableTypes.EXCEPTION_TABLE);
			
			registerUIModelListeners(dtMainTable, decisionDataModel, decisionTableModelListener, TableTypes.DECISION_TABLE);
			registerUIModelListeners(etMainTable, exceptionDataModel, exceptionTableModelListener, TableTypes.EXCEPTION_TABLE);
			
			IDecisionTableEditorInput dei = (IDecisionTableEditorInput)getEditorInput();
			
			if (!dei.isReadOnly()) {
				//decision table
				DecisionTableModel decisionTableUIModel = dtPane.getDecisionTableModel();		
				ICommandStackListener<IExecutableCommand> commandStackModificationListener =
					new DecisionTableCommandStackListener(decisionTableUIModel, decisionTableModelManager.getTabelEModel());
				ICommandStackListener<IExecutableCommand> commandStackEditorOpsListener =
					new DecisionTableCommandStackEditorOpsListener(this);
				CommandFacade.getInstance().addCommandStackListeners(dei.getProjectName(), 
						dei.getResourcePath(), 
						commandStackModificationListener, 
						commandStackEditorOpsListener);
	
	
				//exception table
				DecisionTableModel exceptionTableUIModel = etPane.getDecisionTableModel();
				ICommandStackListener<IExecutableCommand> exceptionCommandStackModificationListener =
					new DecisionTableCommandStackListener(exceptionTableUIModel, decisionTableModelManager.getTabelEModel());
				//ICommandStackListener<IExecutableCommand> commandStackEditorOpsListener =
				//new DecisionTableCommandStackEditorOpsListener(this);
				CommandFacade.getInstance().addCommandStackListeners(dei.getProjectName(), 
						dei.getResourcePath(), 
						exceptionCommandStackModificationListener, 
						commandStackEditorOpsListener);
			}
			
			IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
            boolean autoResizeColumn = prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN);
            
            if (true == autoResizeColumn) {
            	TableUtils.autoResizeAllColumns(dtMainTable, true);
            	TableUtils.autoResizeAllColumns(etMainTable, true);
            }
		}
			
		this.setActivePage(0);
		this.setForm(decisionTableDesignViewer.getForm());
	}

	/**
	 * Update the editor's title based upon the content being edited.
	 */
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

	@Override
	public void doSave(IProgressMonitor monitor) {
		//Stop editing on the open editor
		decisionTableDesignViewer.stopAllCellEditing();
		if (monitor instanceof NullProgressMonitor || monitor instanceof SubProgressMonitor) {
			//From Import or new creation
			try {
				saving = true;
				IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput) getEditorInput();
				RuleFunction virtualRuleFunction = editorInput.getVirtualRuleFunction();
				String dtName = editorInput.getName();
				if (virtualRuleFunction != null && dtName != null) {
					saveModel(virtualRuleFunction, dtName);
				}
				//Create a check point command here if save is successful
				CommandFacade.getInstance().
				createCheckpoint(editorInput.getProjectName(),
						         editorInput.getTableEModel(),
						         null);
				editorInput.refresh(monitor);
				//editorInput.getFile().refreshLocal(IFile.DEPTH_ONE, monitor);
			} catch (Exception e) {
				DecisionTableUIPlugin.log(e);
			}
			finally {
				saving = false;
			}
		} else {// If save button or Cntrl+S is pressed
		
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
						IDecisionTableEditorInput editorInput =  (IDecisionTableEditorInput) getEditorInput();
						RuleFunction virtualRuleFunction = editorInput.getVirtualRuleFunction();
						String dtName = editorInput.getName();
						/**
						 * Rule Function null check not required
						 * since if it is null as in 4.0, the check
						 * has been made.
						 */
						if (dtName != null) {
							saveModel(virtualRuleFunction, dtName);
							monitor.worked(95);
						}
						monitor.setTaskName("Decision Table is saved");
						monitor.done();
						//Create a check point command here if save is successful
						CommandFacade.getInstance().
						createCheckpoint(editorInput.getProjectName(),
								         editorInput.getTableEModel(),
								         null);
						editorInput.refresh(monitor);
						//editorInput.getFile().refreshLocal(IFile.DEPTH_ONE, monitor);

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
		}
		
		updateConstraintActionItem(this.getEditorSite().getPage());
		DecisionTableOverviewUtils.updateTableOverview( this.getEditorSite().getPage(), this);
	}

	public void explorerRefresh(AbstractResource template) {}

	@Override
	public void doSaveAs() {
		DecisionTableUtil.hideAllEditorPopups(getSite().getWorkbenchWindow().getActivePage().getActiveEditor(), true);
		IDecisionTableEditorInput dtEditorInput = (IDecisionTableEditorInput) getEditorInput();
		WizardDialog dialog = new WizardDialog(getSite().getShell(),
				new SaveAsDecisionTableWizard(getSite().getWorkbenchWindow(),
						dtEditorInput)) {
			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				super.createButtonsForButtonBar(parent);
				Button finishButton = getButton(IDialogConstants.FINISH_ID);
				finishButton.setText(IDialogConstants.OK_LABEL);
			}
		};
		dialog.setMinimumPageSize(320, 5);
		dialog.create();
		dialog.open();
	}

	public boolean isEditorContentModified() {
		return isEditorContentModified;
	}

	public void inputChanged(IEditorInput input) {
		setInputWithNotify(input);
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());

	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void dispose() {
		if (decisionTableDesignViewer != null) {
			decisionTableDesignViewer.dispose();
		}
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			final IViewPart view = page.findView(CommonOverview.ID);

			IEditorPart editor = (IEditorPart) page.getActiveEditor();

			if (!(editor instanceof DecisionTableEditor)) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if(view != null){
							com.tibco.cep.studio.ui.overview.Overview overview = ((CommonOverview) view).getOverview();
							if(overview != null && overview.getScrollPane() != null){
								overview.setScrollPane(null);
								((CommonOverview) view).getOverview().updateUI();
								((CommonOverview) view).getOverview().validate();
							}
						}
						// ((TableOverview) view).dispose();
					}
				});

			}
		}
		if (decisionTableModelListener != null) {
			decisionTableModelListener.dispose();
			decisionTableModelListener = null;
		}
		if (exceptionTableModelListener != null) {
			exceptionTableModelListener.dispose();
			exceptionTableModelListener = null;
		}
        if (getEditorInput() instanceof IDecisionTableEditorInput) {
        	// clear the command stack and remove it from the map of command stacks
    		CommandFacade.getInstance().clearCommandStack(decisionTableModelManager.getTabelEModel(), ((IDecisionTableEditorInput)getEditorInput()).getProjectName());
    		// set decision table exception table reference null if demand load
    		// option is true
    		Table eModel = decisionTableModelManager.getTabelEModel();
    		String project = ((IDecisionTableEditorInput)getEditorInput()).getProjectName();
    		String demandLoad = StudioConfig.getInstance().getProperty(TableXMLLoad.PROP_DEMAND_LOAD,
    				TableXMLLoad.DEFAULT_VALUE);
    		if (demandLoad == null || "".equals(demandLoad)) {
    			demandLoad = TableXMLLoad.DEFAULT_VALUE;
    		}
    		if (demandLoad != null
    				&& ("true".equalsIgnoreCase(demandLoad) || "on"
    						.equalsIgnoreCase(demandLoad))) {
    			
    			eModel.setDecisionTable(null);
    			eModel.setExceptionTable(null);
    		}
    		decisionTableDesignViewer = null;
    		decisionTableModelManager = null;
    		//Reset id generator
    		RuleIDGeneratorManager.INSTANCE.resetIDGenerator(project, eModel.getFolder() + eModel.getName());
        }
        
        // dispose the dt structures
        if (decisionTable != null) {
        	decisionTable.clear();
        }
        
        DecisionTableAnalyzerView view = (DecisionTableAnalyzerView)page.findView(DecisionTableAnalyzerView.ID);
        
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
	
	
	public DecisionTableDesignViewer getDesignViewer() {
		return decisionTableDesignViewer;
	}
	
	private static class EditorInputFactory {
		
		static IDecisionTableEditorInput getEditorInput(IEditorInput inputEditorInput) {
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
	}

	@Override
	public void init(IEditorSite site, final IEditorInput input)	throws PartInitException {
		DecisionDataModel decisionTableModel = null;
		TableModel declarationTableModel = null;
		
		DecisionDataModel exceptionTableModel = null;
		Table tableEModel = null;
		
		super.init(site, input);
		site.setSelectionProvider(this);
		if (input instanceof JarEntryEditorInput) {
			this.input = input;
			setJar(true);	
		}
		
		//Check whether valid Decision Table Editor
		if (isInvalidEditor(input)) {
			return;
		}
		
		final IWorkbenchPage page = initPerspective(site);

		// time the loading:
		long startTime = System.currentTimeMillis();
		
		
		/**
		 * This check has been introduce since 4.0
		 */
		try {
			dei = EditorInputFactory.getEditorInput(input);
			if (dei == null) {
				throw new IllegalArgumentException("No suitable decision table editor input could be instantiated");
			}
			//Disable editor based on editor input
			setEnabled(!dei.isReadOnly());
			//Load model
			dei.loadModel();
			//Load front end model.
			decisionTableFrontEndModelLoader = new DecisionTableFrontEndModelLoader(dei.getProjectName(), dei.getTableEModel());
			this.setInput(dei);
			tableEModel = dei.getTableEModel();
			if (tableEModel != null) {
				decisionTableModel = decisionTableFrontEndModelLoader.loadFrontEndModel(TableTypes.DECISION_TABLE);
				exceptionTableModel = decisionTableFrontEndModelLoader.loadFrontEndModel(TableTypes.EXCEPTION_TABLE);
				RuleFunction virtualRuleFunction = dei.getVirtualRuleFunction();
				if (virtualRuleFunction != null) {
					RuleFunction rf = (RuleFunction) virtualRuleFunction;
					declarationTableModel = 
						DecisionTableUtil.getDeclarationTableModel(tableEModel, rf);
				} else {
					declarationTableModel = DecisionTableUtil.getDeclarationTableModel(tableEModel);
				}
			} 
			if (tableEModel.getMd() == null) {
				MetaData metaData = DtmodelFactory.eINSTANCE.createMetaData();
				EList<Property> property = metaData.getProp();
				Property effDateProperty = DtmodelFactory.eINSTANCE
				.createProperty();
				effDateProperty.setName("EffectiveDate");
				effDateProperty.setType("DateTime");
				effDateProperty.setValue("");
				Property expDateProperty = DtmodelFactory.eINSTANCE.createProperty();
				expDateProperty.setName("ExpiryDate");
				expDateProperty.setType("DateTime");
				expDateProperty.setValue("");
				property.add(effDateProperty);
				property.add(expDateProperty);

				//Added SingleRowExecution boolean property
				Property singleRowExecutionProperty = DtmodelFactory.eINSTANCE.createProperty();
				singleRowExecutionProperty.setName("SingleRowExecution");
				singleRowExecutionProperty.setType("Boolean");
				singleRowExecutionProperty.setValue("false");
				property.add(singleRowExecutionProperty);

				tableEModel.setMd(metaData);
			}

			long endTime = System.currentTimeMillis();
			DecisionTableUIPlugin.debug(getClass().getName(), "Imported EMF Model From XMI in: {0} sec."
					, (endTime - startTime) / 1000.0);
			
			decisionTableModelManager = new DecisionTableModelManager(tableEModel,
																	  decisionTableModel,
																	  declarationTableModel,
																	  exceptionTableModel);
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				public void run() {
					try {
						prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
						tableAnalyzer = prefStore.getBoolean(PreferenceConstants.RUN_ANALYZER);
						if (tableAnalyzer) {
							//Populating Decision Table Constraint Editor
							populateDecisionTableConstraintEditor(page);
						}
					} catch (Exception e) {
						DecisionTableUIPlugin.log(e);
					}
				}
			}, false);

			decisionTableEditorPartListener = new DecisionTableEditorPartListener(this);
			site.getPage().addPartListener(decisionTableEditorPartListener);
		} catch(Exception e) {
			final String msg = e.getLocalizedMessage();
			getSite().getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {					
					try {
//						IFile file = ((FileEditorInput) input).getFile();						
							MessageDialog.openError(getSite().getShell(), "Error",
									msg + "");
							((FileEditorInput) input).getFile().refreshLocal(
									IFile.DEPTH_INFINITE, null);
							getSite().getPage().closeEditor(getEditor(), false);
							
							// Commented as it is recursively opening the file and going in the infinite loop
							//IDE.openEditor(getSite().getPage(), file);
					
					} catch (CoreException coreExpt) {
						DecisionTableUIPlugin.log(coreExpt);
					}
				}
			});					
		}
	}

	/**
	 * @param site
	 * @return
	 */
	public IWorkbenchPage initPerspective(IEditorSite site) {
		final IWorkbenchPage page = site.getPage();
		IPerspectiveDescriptor descriptor = page.getPerspective();
		
		//BE-14160 DT : Open DT from projectview, table analyzer does not get updated. 
		// There will be a problem opening analyser if the perspective is not a decision table
		if (descriptor == null || !(DecisionTablePerspective.ID).equals(descriptor.getId())) {
			IWorkbenchWindow window = site.getWorkbenchWindow();
			IWorkbench bench = window.getWorkbench();
			try {
				return bench.showPerspective(DecisionTablePerspective.ID, window);
			} catch (WorkbenchException e) {
				DecisionTableUIPlugin.log(e);
			}
		}
		return page;
	}
	
	public IEditorPart getEditor() {
		return this;
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

	/**
	 * @param page
	 * @param editor
	 */
	private void populateDecisionTableConstraintEditor(IWorkbenchPage page) {
		IViewPart dtConstraintsView = page.findView(DecisionTableAnalyzerView.ID);
		
		if (dtConstraintsView != null) {
			DecisionTableAnalyzerView analyzerView = (DecisionTableAnalyzerView) dtConstraintsView;
		
			try {
				ConstraintsTableCreationJob job = new ConstraintsTableCreationJob("Constraints Table Creation", analyzerView, this);
				job.setPriority(Job.SHORT);
				job.setUser(true);
				job.schedule();
			} catch (Exception e) {
				DecisionTableUIPlugin.log(e);
			}
		} 
	}
	
	private void updateConstraintActionItem(IWorkbenchPage page) {
		IViewPart dtConstraintsView = page.findView(DecisionTableAnalyzerView.ID);
		
		if (dtConstraintsView != null) {
			DecisionTableAnalyzerView analyzerView = (DecisionTableAnalyzerView) dtConstraintsView;
			analyzerView.resetContextActions();
		}
	}


	public void editorContentModified() {
		if (!isDirty()) {
			isEditorContentModified = true;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} else {
			isEditorContentModified = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#modified()
	 */
	public void modified() {
		editorContentModified();
	}
	/**
	 * Restore editor state by making it non-dirty 
	 */
	public void editorContentRestored() {
		Display display = Display.getDefault();
		if (display.getThread() != Thread.currentThread()) {
			display.syncExec(new Runnable() {
				public void run() {
					isEditorContentModified = false;
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			});
		} else {
			isEditorContentModified = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	

	@Override
	public boolean isDirty() {
		return isEditorContentModified;
	}

	private void saveModel(RuleFunction virtualRuleFunction, String dtName) throws Exception {
		long startTime = System.currentTimeMillis();
		
		Table tableEModel = decisionTableModelManager.getTabelEModel();
		//Save the since part
		tableEModel.setSince(DTConstants.SINCE_BE_40);
		
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)getEditorInput();
		IStatus status = editorInput.saveModel(virtualRuleFunction, tableEModel);
		
		if (status.getCode() == IStatus.ERROR) {
			throw new Exception(status.getException());
		}
		long endTime = System.currentTimeMillis();
		DecisionTableUIPlugin.debug(getClass().getName(), "Exported EMF Model to XMI in:{0} sec. " , (endTime - startTime) / 1000.0);

	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapterClass) {
		if (IGotoMarker.class.equals(adapterClass)) {
			return this;
		}
		if (adapterClass == IPropertySheetPage.class) {
			return new DecisionTablePropertySheetPage(this);
		}
		return super.getAdapter(adapterClass);
	}
	
	

	/**
	 * Implemented this method so as to resolve the goto from problems view
	 * to highlight the problematic row.
	 */
	public void gotoMarker(IMarker marker) {
		//Check category
		try {
			if (!TableModelErrorsRecorder.TABLE_SYNTAX_ERROR_MARKER.
					equals(marker.getType())) { return; }
		
			//Get location
			String location = marker.getAttribute(IMarker.LOCATION, "-1");
			//Location form [rowid, Column:<columnName>]
			//Split
			String rowId = 
				location.substring(location.indexOf('[') + 1, location.indexOf(','));
			String columnName = 
				location.substring(location.indexOf(':') + 1, location.indexOf(']'));
			String trvId = marker.getAttribute(IMarkerErrorRecorder.MARKER_TRV_ATTR, "1_1");
			Table tableEModel = decisionTableModelManager.getTabelEModel();
			//DT or ET?
			TableRuleInfo tableRuleInfo = 
				ModelUtils.DecisionTableUtils.getTableRuleInfoFromId(rowId, tableEModel);
			
			DecisionTablePane pane = null;
			if(tableRuleInfo == null){
				MessageDialog.openError(getSite().getShell(), "Error",
						location + " in " + marker.getResource().getName() + " does not exist.");
				return;
			}
			
			TableTypes tableType = tableRuleInfo.getTableTypes();
			switch (tableType) {
			case DECISION_TABLE:
				pane = decisionTableDesignViewer.getDecisionTablePane();
				break;
			case EXCEPTION_TABLE:
				pane = decisionTableDesignViewer.getExceptionTablePane();
				break;
			}
			int problemColPosition = 
				DecisionTableUtil.getColumnPosition(getProject().getName(), 
						                            tableEModel, 
						                            tableType, 
						                            columnName,
						                            trvId);
			//This is the id from model
			//Get the row index
			DecisionDataModel decisionDataModel = pane.getDecisionDataModel();
			int rowIndex = decisionDataModel.getRowIndex(Integer.parseInt(rowId));
			
			JTable mainTable = pane.getTableScrollPane().getMainTable();

			mainTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
			mainTable.getColumnModel().getSelectionModel().setSelectionInterval(problemColPosition, problemColPosition);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	public DecisionTableModelManager getDecisionTableModelManager() {
		return decisionTableModelManager;
	}

		
	public DecisionTableDesignViewer getDecisionTableDesignViewer() {
		return decisionTableDesignViewer;
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

	public void showVersion(boolean showVersionNumber) {
		Table table = ((IDecisionTableEditorInput) getEditorInput()).getTableEModel();
		String partName = table.getName();
		if (showVersionNumber) {
			partName += " [v:" + table.getVersion() + "]";
		}
		setPartName(partName);
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
		
	public boolean isInvalidPart() {
		return invalidPart;
	}
	@Override
	public String getContributorId() {
		return "com.tibco.cep.decision.table.editors.propertyContributor";
	}
	
	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractDecisionTableEditor#getDecisionTablePane()
	 */
	public JPanel getDecisionTablePane() {
		if (decisionTableDesignViewer != null) {
			return decisionTableDesignViewer.getDecisionTablePane();
		}
		return null;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractDecisionTableEditor#getExceptionTablePane()
	 */
	@Override
	public JPanel getExceptionTablePane() {
		if (decisionTableDesignViewer != null) {
			return decisionTableDesignViewer.getExceptionTablePane();
		}
		return null;
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.decision.table.perspective";
	}

	@Override
	public boolean isJar() {
		return jar;
	}

	@Override
	public void setJar(boolean jar) {
		this.jar = jar;
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (!selectionChangedListeners.contains(listener)) {
			selectionChangedListeners.add(listener);
		}
	}

	@Override
	public ISelection getSelection() {
		return editorSelection;
	}

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
}