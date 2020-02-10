package com.tibco.cep.studio.decision.table.ui.wizard;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.RecordFormatException;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelImportListener;
import com.tibco.cep.decision.table.spi.ExcelConversionOps;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.decisionproject.util.RuleIdGenerator;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.actions.CompareTableAction;
import com.tibco.cep.studio.decision.table.ui.actions.MergeTableAction;
import com.tibco.cep.studio.decision.table.ui.editors.DecisionTableEditor;
import com.tibco.cep.studio.decision.table.ui.editors.DefaultDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.ui.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;

public class DTImportWizard extends AbstractNewEntityWizard<NewDecisionTableWizardPage<DTImportWizard>> implements IVRFSelectionWizard {

	private String virtualRFPath = null;
	private IFile vrfFile;
	private IWorkbenchWindow window;
	private int mergeStatus = 0;
	private boolean useExistingId;
	
	public static final int OPTION_OVERWRITE=0;
	public static final int OPTION_MERGE_MANUAL=1;
	public static final int OPTION_MERGE_AUTO=2;

	private String folderPath = "";
	
	private long startTime;
	private static final String Excel_VALIDATION_ERROR = "ExcelValidation";
	private static final String CLASS_NAME = DTImportWizard.class.getName();
	private ExcelImportListener excelImportListener = null;
	private Table tableEModel;
	private DecisionTableEditor editor;
	private boolean isImportError = false;
	private String fileName;
	private DecisionTableColumnIdGenerator columnIdGenerator;
	private RuleIdGenerator ruleIdGenerator;
	private Implementation impl;
	private boolean isMerge = false;
	private long xlParseTime;
	
	public DTImportWizard(){
		setWindowTitle(Messages.getString("import.decision.table.wizard.title"));
	}
	
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void closeDTEditor(IWorkbenchPage workBenchPage, String dtName) {
		IEditorReference[] editorReferences = workBenchPage.getEditorReferences();
		for (IEditorReference editorRef : editorReferences) {
			if (editorRef.getId().equals(DecisionTableEditor.ID) && 
					(editorRef.getName().equals(dtName + ".rulefunctionimpl")
							|| editorRef.getName().equals(dtName))) {
				DecisionTableEditor dtEditor = (DecisionTableEditor) editorRef.getEditor(true);
				workBenchPage.closeEditor(dtEditor, true);
			}
		}
	}
	
	private int getStatus() {
		OverwriteOrMergeDialog dialog = new OverwriteOrMergeDialog(this.window.getShell());
		dialog.open();
		int ret = dialog.getReturnCode();
		return ret;
	}
	
	/**
	 * Report all validation and other problems here.
	 * @param monitor
	 * @param excelVldErrorCollection
	 */
	private void processProblems(Collection<ValidationError> excelVldErrorCollection) {
		// send all problems to problem View
		BasicDiagnostic diagnostic = new BasicDiagnostic("Excel Import",
				IStatus.ERROR, Messages.getString("ImportExcel_validationerror"), null);
		String problemCode = Excel_VALIDATION_ERROR;

		for (ValidationError ve : excelVldErrorCollection) {
			Object[] details = new Object[] { new StringBuilder(ve
					.getErrorMessage()) };
			BasicDiagnostic d = new BasicDiagnostic(IStatus.ERROR, 
													ve.getErrorSource().toString(), 
													ve.getErrorCode(), 
													ve.getErrorMessage(), 
													details);
			diagnostic.add(d);
		}

		DiagnosticDialog dialog = new DiagnosticDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				"Excel validation error", problemCode, diagnostic,
				Diagnostic.ERROR);
		dialog.create();
		dialog.open();
	}
	
	private void processWorkbookEvents(FileInputStream fin,
			                           POIFSFileSystem poifs, InputStream din, HSSFRequest req) {
		try {
			ExcelConversionOps.processWorkbookEvents(fin, poifs, din, req);
		} catch (Exception e) {
			//Log it
			DecisionTableUIPlugin.log(e);
			//Display it in dialog
			MessageDialog.openError(window.getShell(), 
					                Messages.getString("ImportExcel_title"), 
					                e.getMessage());
		}
	}
	
	
	public void importDT(){
		final boolean showDescription = page.showDescription();
		fileName = page.getFileLocationText().getText().trim();
		final String dtName = page.getResourceContainer().getResourceName();
		final RuleFunction template = (RuleFunction) page.getTemplate();
		final String templatePath = template.getFolder() + template.getName();
		final boolean importDM = page.isImportDM();
		
		IPath containerPath = page.getContainerFullPath();;
		if(containerPath != null){
			IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(containerPath);
				if(resource instanceof IFolder){
					IFolder folder = (IFolder)resource;
					folderPath = folder.getFullPath().removeFirstSegments(1).toString();
				} else if(resource instanceof IProject){
					IProject resourceProject = (IProject)resource;
					folderPath = resourceProject.getFullPath().removeFirstSegments(1).toString();
				} else if (resource instanceof IFile){
					IResource resourceParent = (IResource) resource.getParent();
					folderPath = resourceParent.getFullPath().removeFirstSegments(1).toString();
				}
		}
		
		Permit permit = Permit.ALLOW;
		if (Permit.ALLOW.equals(permit)) {
			if (fileName.endsWith(".prr")) {
				// write code for importing form PRR format
			} else if (fileName.endsWith(".xls")) {
				IRunnableWithProgress op = new IRunnableWithProgress() {

					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						
						IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
						boolean useExistingIDsAtImport = prefStore.getBoolean(PreferenceConstants.EXISTING_ID_IMPORT);
						
						monitor.beginTask("Importing Excel...", 100);
						monitor.worked(10);
						try {
							startTime = System.currentTimeMillis();

							FileInputStream fin = new FileInputStream(fileName);
							POIFSFileSystem poifs = new POIFSFileSystem(fin);
							InputStream din = poifs.createDocumentInputStream("Workbook");
							// Request object is need for event model system
							HSSFRequest req = new HSSFRequest();
							excelImportListener = new ExcelImportListener(getProjectName(), 
									template, importDM, 
									useExistingIDsAtImport,new HSSFWorkbook(poifs));
							// Registered for these types of events
							monitor.worked(40);
							//Register record listeners outside.
							ExcelConversionOps.registerRecordListeners(req, excelImportListener);
							processWorkbookEvents(fin, poifs, din, req);
							monitor.worked(40);
							long xlParseTime = System.currentTimeMillis();
							DecisionTableUIPlugin.debug(getClass().getName(), "Total time taken in parsing Excel file::{0}{1} ms",
											(xlParseTime - startTime));
							// check if there is any validation error
							Collection<ValidationError> errorCollection = excelImportListener.getExcelVldErrorCollection();
							if (!errorCollection.isEmpty()) {
								processProblems(errorCollection);
								return;
							}
							int errorCode = 1;
							errorCode = excelImportListener.getErrorReturnCode();
							if (errorCode == 0) {
								processProblems(errorCollection);
								return;
							}
							tableEModel = excelImportListener.getTableEModel();
							// get Column Id generator
							columnIdGenerator = excelImportListener.getColumnIdGenerator();
							// get current maximum DTRuleId as well as ETRuleId
							int maxCurrentDTRuleId = excelImportListener.getCurrentDTRuleId();
							int maxCurrentETRuleId = excelImportListener.getCurrentETRuleId();
							// create Rule Id Generator
							ruleIdGenerator = new RuleIdGenerator();
							// initialize rule id generators
							if (useExistingId) {
								ruleIdGenerator.intializeIdCounter(maxCurrentDTRuleId, maxCurrentETRuleId);
							} else { 
								ruleIdGenerator.intializeIdCounter(0, 0);
							}
							if (template instanceof RuleFunction) {
								tableEModel.setName(dtName);
								tableEModel.setImplements(templatePath);
								tableEModel.setVersion(0.0);
								//tableEModel.setModified(true);
								
								StringBuilder folder = new StringBuilder(CommonIndexUtils.PATH_SEPARATOR);
								folder.append(folderPath.isEmpty() ? "" : folderPath + CommonIndexUtils.PATH_SEPARATOR);
								tableEModel.setFolder(folder.toString().trim());
								
								tableEModel.setShowDescription(showDescription);
								DesignerElement dtElement = page.getDuplicateElement();
								IFile file = page.getDuplicateResource();
								if (dtElement instanceof DecisionTableElement) {
									impl = (Implementation) ((DecisionTableElement) dtElement).getImplementation();
								}	
								if (impl != null) {
									if (mergeStatus == OPTION_MERGE_MANUAL) {
										doManualMerge(monitor, impl, file, getProjectName());
										return;
									} else if (mergeStatus == OPTION_MERGE_AUTO) {
										Table newTable  = doAutoMerge(monitor, impl, file, getProjectName());
										if(newTable == null){
											return;
										} 										
										// IF table is not null - new table created after merging
										tableEModel = newTable;
									} else if (mergeStatus == -1) {
										// table already exists, but was not already deleted, show error and return
										MessageDialog.openError(window.getShell(),
												Messages.getString("MESSAGE_TITLE_ERROR"),
												Messages.getString("EXCEL_IMPORT_DUPLICATE_TABLE_NAME_ERROR"));
										isImportError = true;
									}
								}
							}
							IWorkbenchPage page = window.getActivePage();
							if (page != null) {
								if (!isImportError) {
									openEditor(dtName, template, page);
								}
							}
							monitor.worked(10);
							monitor.setTaskName("Import completed");
							monitor.done();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				
				try {
					getContainer().run(false, true, op);
					if (isMerge) {
						// Do not save the DT, as the user is required to manually
						// merge the imported table with the existing one and save
						// explicitly when finished
//						return true;
					}
					if (!isImportError) {
						Display.getDefault().asyncExec(new Runnable(){
							public void run(){
								// keep save operation inside progress monitor
								ProgressMonitorDialog dialog = new ProgressMonitorDialog(window.getShell()) {
									@Override
									protected void createDialogAndButtonArea(Composite parent) {
										dialogArea = createDialogArea(parent);
										applyDialogFont(parent);
									}
								};
								IRunnableWithProgress runnable = new IRunnableWithProgress() {
									public void run(IProgressMonitor monitor) {
										monitor.beginTask("Saving Decision Table", 100);
										// monitor.worked(50);
										if (editor != null) {
											editor.doSave(new NullProgressMonitor());
										}
										// editor.explorerRefresh(template);
										monitor.done();
									}
								};
								try {
									dialog.run(false, true, runnable);
									dialog.close();
								} catch (Exception e) {
									DecisionTableUIPlugin.log("DT Save Failed >>", e);
									throw new RuntimeException("DT Save failed >>");
								}
							}
						});
						
					}
					long endTime = System.currentTimeMillis();
					DecisionTableUIPlugin.debug(CLASS_NAME,
									"Total time taken in showing DT on UI from EMF model >>>{0}{1} ms",
									(endTime - xlParseTime));
					DecisionTableUIPlugin.debug(CLASS_NAME,
							"Total time taken in Excel import>>>{0}{1} ms",
							(endTime - startTime));
				} catch (InvocationTargetException e2) {
					e2.printStackTrace();
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				} catch (RecordFormatException rfe) {
					rfe.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
//					return true;
				}
			}
//			return true;
		}
//		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#addPages()
	 */
	public void addPages() {
		try {
			if(_selection != null && !_selection.isEmpty()){
				project =  StudioResourceUtils.getProjectForWizard(_selection);
				if(_selection.getFirstElement() instanceof IFile){
					IFile file = (IFile)_selection.getFirstElement();
					virtualRFPath = IndexUtils.getFullPath(file);
				}
			}
			String projName = this.project == null ? "" : this.project.getName();
			page = new NewDecisionTableWizardPage<DTImportWizard>(getWizardTitle(), _selection, 
					getEntityType(), virtualRFPath, projName, true);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);
       
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}
	
	
	
	private void openEditor(final String dtName,
			final RuleFunction virtualRuleFunction, IWorkbenchPage page)
			throws PartInitException {
		fileName = fileName.substring(0, fileName.indexOf('.'));
		DefaultDecisionTableEditorInput dtEditorInput = null;

		DecisionTableElement decisionTableElement = IndexFactory.eINSTANCE.createDecisionTableElement();
		// Set the eobject in it
		//decisionTableElement.setImplementation(tableEModel);
		decisionTableElement.setName(tableEModel.getName());
		decisionTableElement.setFolder(tableEModel.getFolder());
		decisionTableElement.setElementType(ELEMENT_TYPES.DECISION_TABLE);
		// Get IFile for it
		IFile file = IndexUtils.getFile(getProjectName(), decisionTableElement);

		dtEditorInput = new DefaultDecisionTableEditorInput(file, virtualRuleFunction);
		dtEditorInput.setTableEModel(tableEModel);
		dtEditorInput.setModelSource(DTConstants.MODEL_SOURCE_EXCEL);
		// set column id generator
		dtEditorInput.setColumnIdGenerator(columnIdGenerator);
		// set Rule Id generator with Editor Input
		dtEditorInput.setRuleIdGenerator(ruleIdGenerator);

		editor = (DecisionTableEditor) page.openEditor(dtEditorInput, DecisionTableEditor.ID);
		/*
		 * perform swing operations in AWT thread it's a costly operation so
		 * there is no need to perform this operation until user wants
		 * explicitly
		 */
		final DecisionTableDesignViewer decDesignViewer = editor.getDtDesignViewer();
		final IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		if (prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN)) {
			decDesignViewer.resizeColumns(decDesignViewer.getDecisionTable());
			decDesignViewer.resizeColumns(decDesignViewer.getExceptionTable());
		}
//		editor.getDecisionTableDesignViewer().setToolBarEnabled();
		
	}
	
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return DecisionTableUIPlugin.getImageDescriptor("icons/import_excel_wizard.gif");
	}

	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_DT;
	}

	
	protected String getWizardDescription() {
		return Messages.getString("import.decision.table.wizard.desc");
	}

	
	protected String getWizardTitle() {
		return Messages.getString("import.decision.table.wizard");
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		try{
			this.window = workbench.getActiveWorkbenchWindow();
			this._selection = selection;
			if(selection !=  null && selection.size()==1){
				//If Import Decision Table from Excel on VRF file selection
				if (selection.size()==1 && selection.getFirstElement() instanceof IFile) {
					IFile file = (IFile)selection.getFirstElement();
					if (StudioResourceUtils.isVirtual(file)) {
						vrfFile = file;
					}
				}
			} 
			IPreferenceStore store = DecisionTableUIPlugin.getDefault().getPreferenceStore();
			useExistingId = store.getBoolean(PreferenceConstants.EXISTING_ID_IMPORT);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	protected void doManualMerge(IProgressMonitor monitor, Implementation impl, IFile file, String projectName) {
		CompareTableAction action = new CompareTableAction(tableEModel, file);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("rightIsRemote", "true");
			ExecutionEvent executionEvent = new ExecutionEvent(null, map, null, null);
			action.execute(executionEvent);
			isMerge = true;
			monitor.done();
			return;
		} catch (org.eclipse.core.commands.ExecutionException e) {
			e.printStackTrace();
		}
	}

	protected Table doAutoMerge(IProgressMonitor monitor, Implementation impl, IFile file, String projectName) {
		//TODO
		Table targetTable = (Table)impl;
		MergeTableAction action = new MergeTableAction(page.getTemplate(), impl, tableEModel, file, projectName);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("rightIsRemote", "true");
			ExecutionEvent executionEvent = new ExecutionEvent(null, map, null, null);
			targetTable = (Table)action.execute(executionEvent);
			isMerge = true;
			monitor.done();
			return targetTable;
		} catch (org.eclipse.core.commands.ExecutionException e) {
			e.printStackTrace();
		}
		
		return targetTable;
	}
	
	public IFile getVRFFile() {
		return vrfFile;
	}

	public String getProjectName() {
		return (project != null) ? project.getName() : page.getProject().getName();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		DecisionTableUIPlugin.debug("Import Started ...");
		final String dtName = page.getResourceContainer().getResourceName();
		int status = -1; 
		if (page.isduplicateDT()) {
			IWorkbenchPage workBenchPage = this.window.getActivePage();
			status = getStatus();
			mergeStatus = status;
			switch (status) {
			case OPTION_OVERWRITE:
				closeDTEditor(workBenchPage, dtName);
				importDT();
				page.setduplicateDT(false);
				return true;
			case OPTION_MERGE_MANUAL:
				closeDTEditor(workBenchPage, dtName);
				importDT();
				return true;
			case OPTION_MERGE_AUTO:
				closeDTEditor(workBenchPage, dtName);
				importDT();
				return true;
			default:
				mergeStatus = 0; //Reset to default
				return false;
		}
		} else {
			importDT();
			return true;
		}
	}

}

