package com.tibco.cep.bpmn.ui.editors.bpmnPalette;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.util.StudioUIUtils;



/**
 * 
 * @author majha
 *
 */
public class BpmnPaletteConfigurationEditor extends FormEditor implements IResourceChangeListener  {

	public final static String BPMN_PALETTE_RESOURCE_TYPE = "BPMN Palette Configuration";
	
	protected boolean isEditorDirty;
	//private TextEditor sourceEditor;
	protected ArrayList<Control> controls;
	private IEditorSite site;
	protected IProject project;
	private BpmnPaletteConfigurationModelMgr modelmgr;
    private BpmnPalettePage page;
    protected IFile file = null;
    
    
    public BpmnPaletteConfigurationEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		isEditorDirty = false;
		controls = new ArrayList<Control>();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		this.site = site;
		StudioUIManager.getInstance();	
		if (getEditorFile()!=null) {
			project = getEditorFile().getProject();
			/*BpmnPaletteEditorInput bpmnPaletteEditorInput=new BpmnPaletteEditorInput(this,file);
			super.init(site, bpmnPaletteEditorInput);*/
			site.getPage().addPartListener(partListener);	
			
		}
		if (input != null) {
			setPartName(getEditorFileName());
			setTitleToolTip(input.getToolTipText());
		}
			
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if(!isDirty())
			return;
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				saveModel();
				if (isDirty()) {
					isEditorDirty = false;
				}
			}
		};

		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		} finally {
		}
	}
	
	
	protected void createPage() throws PartInitException {
		//TODO add page
		BpmnPaletteConfigurationModel model = modelmgr.getModel();
		
		if(model != null){// in case of new editor model is null.
			page = new BpmnPalettePage(this, modelmgr);
			addPage(page);
//			setPageText(index, page.getName());
			controls.add(page.getPartControl());
			
		}
	}

	public String getTitle() {
//		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), BPMN_PALETTE_RESOURCE_TYPE, BPMN_PALETTE_CONFIG_EXTENSION);
//		return (displayName);
		return "BPMN Palette";
	}

	protected void loadModel() {
		modelmgr = new BpmnPaletteConfigurationModelMgr(project, this);
		try {
			modelmgr.parseModel();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}

	protected void saveModel() {
		try {
			modelmgr.saveModel();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}

	protected BpmnPalettePage[] getPaletteConfigPage() {
		return new BpmnPalettePage[] { page };
	}


	
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			IResourceDelta rootDelta = event.getDelta();
			if (rootDelta == null)
				return;
			if (!(getEditorInput() instanceof FileEditorInput)){
				return;
			}
			final IFile file = ((FileEditorInput)getEditorInput()).getFile();
			if (file == null)
				return;
			
			IResourceDelta resourceDelta = rootDelta.findMember(file.getFullPath());
			if (resourceDelta == null) {
				return;
			}
			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) {
					if (delta.getKind() == IResourceDelta.CHANGED){
						int flags = delta.getFlags();
						if (flags == IResourceDelta.MARKERS) {
							return false;
						}
						doRefresh(file);
					}
					return true;
				}
			};
			resourceDelta.accept(visitor);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}
	
	public void doRefresh(final IFile file) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IFile oldFile = ((FileEditorInput) getEditorInput()).getFile();
				if (oldFile.getFullPath().equals(file.getFullPath())) {
					updateEditorPages();
				}
			}
		});
	}
	
	private void updateEditorPages() {
		page.update();
	}

	public void modelChanged() {
		
		if (!validateEdit()) {
			doRevert();
			return;
		}
		isEditorDirty = true;
		if (!super.isDirty()) {
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	protected void doRevert() {
		// TODO : revert file if edit validation fails
	}

	private boolean validateEdit() {
		IFile file = getEditorFile();
		if (file == null) {
			return true;
		}
		final IStatus validateEdit = ResourcesPlugin.getWorkspace().validateEdit(new IFile[] { file }, getSite().getShell());
		if (!validateEdit.isOK()) {
			getSite().getShell().getDisplay().syncExec(new Runnable() {
			
				@Override
				public void run() {
					MessageDialog.openError(getSite().getShell(), "Unable to execute command", validateEdit.getMessage());
				}
			});
			return false;
		}

		return true;
	}

	public boolean isDirty() {
		return (isEditorDirty || super.isDirty());
	}

	protected void handlePropertyChange(int propertyId) {
		if (propertyId == IEditorPart.PROP_DIRTY) {
			isEditorDirty = isDirty();
		}
		super.handlePropertyChange(propertyId);
	}
	

	@SuppressWarnings("unused")
	private void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	
	public void setFocus() {
		super.setFocus();
	}



	public IFile getEditorFile() {
		if (getEditorInput() instanceof FileEditorInput) {
			return ((FileEditorInput) getEditorInput()).getFile();
		}
		return null;
	}

	public String getEditorFileName() {
		if (getEditorInput() != null)
			return (getEditorInput().getName());
		return null;
	}

	public String getEditorFilePath() {
		IFile editorFile = getEditorFile();
		if (editorFile != null && editorFile.getLocation() != null) {
			return editorFile.getLocation().toString();
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private void saveFile(String contents) {
		try {
			IFile file = getEditorFile();
			ByteArrayInputStream is = new ByteArrayInputStream(contents.getBytes(ModelUtils.DEFAULT_ENCODING));
			file.setContents(is, true, true, null);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}
	
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}

	@Override
	protected void addPages() {
		loadModel();
		try {
			createPage();
		} catch (PartInitException e) {
			BpmnUIPlugin.log(e);
		}
		autoSave();

	}
	
	private void autoSave() {
		// Auto-save if it either an empty file or if something wasn't initialized correctly and default values were assigned
		String filePath = getEditorFilePath();
		if (isDirty() || filePath == null || new File(filePath).length() == 0) {
			isEditorDirty = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor#dispose()
	 */
	public void dispose() {		
		super.dispose();
		getSite().getPage().removePartListener(partListener);
	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == BpmnPaletteConfigurationEditor.this) {
				StudioUIUtils.refreshPaletteAndOverview(site);
			} 
		}

		public void partBroughtToTop(IWorkbenchPart p) {
		}

		public void partClosed(IWorkbenchPart p) {
		}

		public void partDeactivated(IWorkbenchPart p) {
		}

		public void partOpened(IWorkbenchPart p) {
		}
	};

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}
	
	public BpmnPaletteModel getBpmnPaletteModel(){
		BpmnPaletteModel model = null;
		if(modelmgr != null){
			model = modelmgr.getModel().getBpmnPaletteModel();	
		}
		return model;
	}
}
