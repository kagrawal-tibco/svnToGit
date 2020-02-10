package com.tibco.cep.studio.ui;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openPerspective;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.refreshPaletteAndOverview;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.diagramming.AbstractResourceEditorPart;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public abstract class AbstractStudioResourceEditorPart extends AbstractResourceEditorPart {

	protected ScrolledForm form;
	protected boolean catalogFunctionDrag = false;
	protected boolean isModified;
	protected IProject project;
	protected IFile file;
	protected boolean saving;
	protected boolean fEnabled = true;
	private Image fTitleImage;

	public boolean isCatalogFunctionDrag() {
		return catalogFunctionDrag;
	}

	public void setCatalogFunctionDrag(boolean catalogFunctionDrag) {
		this.catalogFunctionDrag = catalogFunctionDrag;
	}

	public IEditorPart getEditor(){
		return this;
	}
	
	/**
	 * @param form
	 */
	public void setForm(ScrolledForm form) {
		this.form = form;
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled){
		//This one is commented due to Rules Editor's different tab disable 
//		getContainer().setEnabled(enabled);
		if(form !=null){
//			if (fEnabled  != enabled) {
				// disable only the children so that scrolling is still enabled
//				Control[] children = form.getChildren();
//				for (Control control : children) {
//					control.setEnabled(enabled);
//				}
				setTitleReadOnly();
				fEnabled = enabled;
//			}
		}
	}
	
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}
	
	public void setTitleReadOnly(){
		IEditorInput input = getEditorInput();
		setPartName(input.getName().concat(Messages.getString("studio.navigator.label.read.only")));
		Image oldImage = fTitleImage;
		fTitleImage = new Image(Display.getDefault(), getTitleImage(), SWT.IMAGE_DISABLE);
		setTitleImage(fTitleImage);
		if (oldImage != null) {
			oldImage.dispose();
		}
	}
	
	public IProject getProject() {
		if (project == null) {
			if (getEditorInput() instanceof FileEditorInput) {
				project = ((FileEditorInput)getEditorInput()).getFile().getProject();
			} else if (getEditorInput() instanceof JarEntryEditorInput) {
				String projName = ((JarEntryEditorInput)getEditorInput()).getProjectName();
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			}
		}
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}
	
	@Override
	public boolean isDirty() {
		return isModified;
	}

	public void modified() {
		if (!isDirty()) {
			isModified = true;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} else {
			isModified = true;
		}
	}
	
	/**
	 * @return
	 */
	public AbstractEditorPartPaletteHandler getPartListener(){
		return null;
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);

		//refreshing palette view when form editor open 
		refreshPaletteAndOverview(site);

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
		StudioUIManager.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			IResourceDelta rootDelta = event.getDelta();
			if(rootDelta == null)
				return;
			if(!(getEditorInput() instanceof FileEditorInput)){
				return;
			}
			final IFile file = ((FileEditorInput)getEditorInput()).getFile();
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
			e.printStackTrace();
		}
	}
	
	public void doRefresh(IFile file){
		//Override this
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
	
	public void refreshSMAssociationViewer(){
	   //Override this for concept editor SMAssociation Viewer for Concept/State Model Validate	
	}
	
	/**
	 * @return
	 */
	public boolean isEnabled(){
		if (Utils.isStandaloneDecisionManger()) {
			if (this.getEditorInput() instanceof FileEditorInput) {
				FileEditorInput input = (FileEditorInput)this.getEditorInput();
				if (StudioResourceUtils.getDecisionManagerDisabledFileExtensions().contains(input.getFile().getFileExtension())){
					return false;
				}
			}
		}
		return fEnabled;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (form != null) {
			form.dispose();
		}
		if (fTitleImage != null) {
			fTitleImage.dispose();
		}
		project = null;
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	@Override
	public void openEditorPerspective(final IEditorSite site) {
		openPerspective(site, getPerspectiveId());
	}

	
}