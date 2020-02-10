package com.tibco.cep.bpmn.ui.editor;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension2;
import org.eclipse.ui.texteditor.IEditorStatusLine;

import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.util.Messages;

public abstract class AbstractSaveableGraphEditorPart extends /* AbstractGraphicalPaletteEditor*/ AbstractStudioResourceEditorPart implements IGraphEditor,IResourceChangeListener {
	protected boolean isModified;
	protected IProject project;
	protected IFile file;
	protected boolean saving;
	protected boolean fEnabled = true;
	protected AdapterFactoryEditingDomain editingDomain;
	private Action saveAction;
	private Object fEditorStatusLine;
	private ISelectionProvider fSelectionProvider;
	protected GraphDocumentProvider fGraphDocumentProvider;
	private Image fTitleImage;
	
	
	/**
	 * @return
	 */
	public IEditorPart getEditor(){
		return this;
	}
	
	/**
	 * 
	 */
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}
	
	/**
	 * 
	 */
	public void setTitleReadOnly(){
		setPartName(getPartName().concat(Messages.getString("studio.navigator.label.read.only")));
		setTitleImage(new Image(Display.getDefault(), getTitleImage(), SWT.IMAGE_DISABLE));
	}
	
	
	
	/**
	 * @return
	 */
	public IFile getFile() {
		if (file == null) {
			if (getEditorInput() instanceof FileEditorInput) {
				file = ((FileEditorInput)getEditorInput()).getFile();
			}else if (getEditorInput() instanceof BpmnEditorInput) {
				file = ((BpmnEditorInput)getEditorInput()).getFile();
			}
		}
		return file;
	}
	
	public GraphDocumentProvider getDocumentProvider() {
		return fGraphDocumentProvider;
	}
	
	public void setDocumentProvider(GraphDocumentProvider fGraphDocumentProvider) {
		this.fGraphDocumentProvider = fGraphDocumentProvider;
	}

	

	/**
	 * @return
	 */
	public IProject getProject() {
		if (project == null) {
			project = (IProject) getEditorInput().getAdapter(IProject.class);
		}
		return project;
	}
	
	


	public AdapterFactoryEditingDomain getEditingDomain() {
		return editingDomain;
	}


	public void setEditingDomain(AdapterFactoryEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	
	
	
	public boolean isDirty() {
		BpmnEditor gEditor=(BpmnEditor) this.getEditor();
		if(gEditor!=null && !gEditor.isEditable())
			return false;
		boolean isDirty = ((super.isDirty()) || isModified );
    	if (isDirty){    		
    		if (saveAction !=  null){    			
    			saveAction.setEnabled(true);
    		}
    	}
        return isDirty;
	}

	public void modified() {
		isModified = true;
		if (!super.isDirty()) {
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	@Override
	public void firePropertyChange(final int propertyId){
		BpmnEditor gEditor=(BpmnEditor) this.getEditor();
		if(gEditor!=null && gEditor.isEditable())
		super.firePropertyChange(IEditorPart.PROP_DIRTY);
	}
	
	public void setModified(boolean isModified) {
		this.isModified = isModified;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
	
	@Override
	protected void handlePropertyChange(int propertyId) {
		if (propertyId == IEditorPart.PROP_DIRTY) {
			isModified = isDirty();
		}
		super.handlePropertyChange(propertyId);
	}
	
	
	public void setSaveAction(Action saveAction) {
		this.saveAction = saveAction;
	}
	

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
//		super.init(site, input);
		setSite(site);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
		StudioUIManager.getInstance();
		IDocumentProvider provider= getDocumentProvider();
		if (provider == null) {
			IStatus s= new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.OK,BpmnMessages.getString("graphEditor_status_message"), null); //$NON-NLS-1$
			throw new PartInitException(s);
		}

		try {
			internalInit(site.getWorkbenchWindow(), site, input);
//			if (getFile().exists()) {
//				provider.connect(input);
//			}
		} catch (CoreException e) {
			throw new PartInitException("Failed to connect provider", e);
		}
		
	}
	
	private void internalInit(final IWorkbenchWindow workbenchWindow, final IEditorSite site, final IEditorInput input) throws PartInitException {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {

					if (getDocumentProvider() instanceof IDocumentProviderExtension2) {
						IDocumentProviderExtension2 extension = (IDocumentProviderExtension2) getDocumentProvider();
						extension.setProgressMonitor(monitor);
					}

					doSetInput(input);

				} catch (CoreException x) {
					throw new InvocationTargetException(x);
				} finally {
					if (getDocumentProvider() instanceof IDocumentProviderExtension2) {
						IDocumentProviderExtension2 extension = (IDocumentProviderExtension2) getDocumentProvider();
						extension.setProgressMonitor(null);
					}
				}
			}
		};

		try {
			// When using the progress service always a modal dialog pops up.
			// The site should be asked for a runnable context
			// which could be the workbench window or the progress service,
			// depending on what the site represents.
			// getSite().getWorkbenchWindow().getWorkbench().getProgressService().run(false,
			// true, runnable);

			getSite().getWorkbenchWindow().run(false, true, runnable);

		} catch (InterruptedException x) {
		} catch (InvocationTargetException x) {
			Throwable t = x.getTargetException();
			if (t instanceof CoreException) {
				/*
				 * /* XXX: Remove unpacking of CoreException once the following
				 * bug is fixed:
				 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=81640
				 */
				CoreException e = (CoreException) t;
				IStatus status = e.getStatus();
				if (status.getException() != null)
					throw new PartInitException(status);
				throw new PartInitException(new Status(status.getSeverity(), status.getPlugin(), status.getCode(), status.getMessage(), t));
			}
			throw new PartInitException(new Status(IStatus.ERROR, "org.eclipse.ui.workbench.texteditor", IStatus.OK, 	BpmnMessages.getString("graphEditor_BpmnEditor_status_message"), t));
		}

	}
	
	
	protected void doSetInput(IEditorInput input) throws CoreException {
		if(input != null) {
			IEditorInput oldInput= getEditorInput();
			if (oldInput != null)
				getDocumentProvider().disconnect(oldInput);

			super.setInput(input);

			IDocumentProvider provider= getDocumentProvider();
			if (provider == null) {
				IStatus s= new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.OK, BpmnMessages.getString("graphEditor_BpmnEditor_noDocProv_status_message"), null);
				throw new CoreException(s);
			}
			
			if(getFile().exists())
				provider.connect(input);

			initializeTitle(input);
		}
		
	}
	
	
	
	private void initializeTitle(IEditorInput input) {

		Image oldImage= fTitleImage;
		fTitleImage= null;
		String title= ""; //$NON-NLS-1$

		if (input != null) {
			IEditorRegistry editorRegistry= PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor editorDesc= editorRegistry.findEditor(getSite().getId());
			ImageDescriptor imageDesc= editorDesc != null ? editorDesc.getImageDescriptor() : null;

			fTitleImage= imageDesc != null ? imageDesc.createImage() : null;
			title= input.getName();
		}

		setTitleImage(fTitleImage);
		setPartName(title);

		firePropertyChange(PROP_DIRTY);

		if (oldImage != null && !oldImage.isDisposed())
			oldImage.dispose();
	}
		
		
	

	@Override
	public void dispose() {
		super.dispose();
		disposeDocumentProvider();
	}
	
	
	
	private void disposeDocumentProvider() {
		IDocumentProvider provider= getDocumentProvider();
		if (provider != null) {

			IEditorInput input= getEditorInput();
			if (input != null)
				provider.disconnect(input);


		}
		fGraphDocumentProvider= null;
		
	}

	/**
	 * @param file2 
	 * @param file
	 * @throws Exception 
	 */
	public AdapterFactoryEditingDomain createEditingDomain(IFile file) throws Exception {
		return ECoreHelper.getEditingDomain(file.getLocation().toOSString());
	}

	@Override
	protected void createPages() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		saving = true;
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					long start = System.currentTimeMillis();
					saveResource(monitor);
					long end = System.currentTimeMillis();
					long diff = end - start;
					if(diff < 500){
						Thread.sleep(500-diff);
					}
					
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
		};
		
		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			postSaveResource();
			if (saveAction !=  null){    			
    			saveAction.setEnabled(false);
			}
    			BpmnEditor gEditor=(BpmnEditor) this.getEditor();
    			IAction saveHandler= gEditor.getEditorSite().getActionBars().getGlobalActionHandler(ActionFactory.SAVE.getId());
    			if(saveHandler!=null)
    				saveHandler.setEnabled(false);
    		
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		} finally {
			saving = false;
		}

	}
	
	public void firePropertyChange(){
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	abstract protected void postSaveResource() ;

	abstract protected void saveResource(IProgressMonitor monitor) throws Exception;

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			IResourceDelta rootDelta = event.getDelta();
			if(rootDelta == null)
				return;
			if(!(getEditorInput() instanceof BpmnEditorInput)){
				return;
			}
			final IFile file = ((BpmnEditorInput)getEditorInput()).getFile();
			if(file == null)
				return;
			
			IResourceDelta resourceDelta = rootDelta.findMember(file.getFullPath());
			if (resourceDelta == null) {
				return;
			}
			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				/* (non-Javadoc)
				 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
				 */
				public boolean visit(IResourceDelta delta) {
					if (delta.getKind() == IResourceDelta.CHANGED){
						int flags = delta.getFlags();
						if (flags == IResourceDelta.MARKERS) {
							return false;
						}
						if (saving) {
							return false;
						}
						//Handling editor dirty 
//						if(getEditor().isDirty()){
//							//check the editor is active
//							if(getSite().getPage().getActiveEditor() ==  getEditor()){
//								Display.getDefault().asyncExec(new Runnable(){
//									@Override
//									public void run() {
//										boolean confirm = MessageDialog.openConfirm(getSite().getShell(), 
//												"File Changed", "Do you want to replace the editor contents with these changes?");
//										if(!confirm){
//											doEditorRefresh = false;                        	   
//										}
//									}});
//							}
//						}
//						if(doEditorRefresh){
							doRefresh(file);
//						}
					}
					return true;
				}
			};
			resourceDelta.accept(visitor);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
	}
	
	public void doRefresh(IFile file){
		//Override this
	}
	
	/**
	 * @return
	 */
	public boolean isEnabled(){
		return fEnabled;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (IProject.class.equals(adapter)) {
			if (file != null) {
				return file.getProject();
			}
		} else if (IEditorStatusLine.class.equals(adapter)) {
			if (fEditorStatusLine == null) {
				IStatusLineManager statusLineManager= getStatusLineManager();
				ISelectionProvider selectionProvider= getSelectionProvider();
				if (statusLineManager != null && selectionProvider != null)
					fEditorStatusLine= new GraphEditorStatusLine(statusLineManager, selectionProvider);
			}
			return fEditorStatusLine;
		}
		return super.getAdapter(adapter);
	}
	
	protected IStatusLineManager getStatusLineManager() {
		return getEditorSite().getActionBars().getStatusLineManager();
	}
	
	public ISelectionProvider getSelectionProvider() {
		return fSelectionProvider;
	}
	
	public void setSelectionProvider(ISelectionProvider fSelectionProvider) {
		this.fSelectionProvider = fSelectionProvider;
		getSite().setSelectionProvider(fSelectionProvider);
	}
	


	@Override
	public boolean isCatalogFunctionDrag() {
		return false;
	}


	@Override
	public void setCatalogFunctionDrag(boolean catalogFunctionDrag) {
		// TODO Auto-generated method stub
	}
	
	public boolean isDisplayFullName() {
		return false;
	}
	
	public boolean isfillTaskIcons() {
		return false;
	}
}