package com.tibco.cep.sharedresource.ui.editors;

/*
 @author ssailapp
 @date Dec 12, 2009 8:58:15 AM
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractFormEditor;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public abstract class AbstractSharedResourceEditor extends AbstractFormEditor {
	protected boolean isEditorDirty;
	//private TextEditor sourceEditor;
	protected ArrayList<Control> controls;
//	private static final int SOURCE_PAGE_INDEX = 1;
	private IEditorSite site;
	protected IProject project;
	protected boolean fEnabled = true;
	protected InputStream inputStream = null;
	protected Document document;
	private Image fTitleImage;
    protected int currentPage = -1;

	public AbstractSharedResourceEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		isEditorDirty = false;
		controls = new ArrayList<Control>();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		boolean linked = false;
		if (input instanceof FileEditorInput) {
			linked = ((FileEditorInput) input).getFile().isLinked(IResource.CHECK_ANCESTORS);
		}
		if (input instanceof IStorageEditorInput && (!(input instanceof FileEditorInput) || linked)) {
			fEnabled = false;
			if (this.site == null) {
				try {
					IStorageEditorInput storageInput = (IStorageEditorInput)input;
					IStorage store = storageInput.getStorage();
					try {
						inputStream = store.getContents();
						DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
						fact.setNamespaceAware(true);
						DocumentBuilder builder = fact.newDocumentBuilder();
						document = builder.parse(inputStream);
					} finally {
						try {	
							if (inputStream != null){
								inputStream.close();
							}
							if (store instanceof JarEntryFile) {
								((JarEntryFile) store).closeJarFile();
							}
						} catch (Exception e){
							e.printStackTrace();
						}

					}
				} catch (Exception e) {
					StudioUIPlugin.log(e);
				}
			} 
		}
		
		super.init(site, input);
		this.site = site;
		StudioUIManager.getInstance();	
		if (getEditorFile() != null) {
			project = getEditorFile().getProject();
		}
		if (input != null) {
			setPartName(getEditorFileName());
			setTitleToolTip(input.getToolTipText());
		}
		site.getPage().addPartListener(partListener);	
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					String contents = saveModel();
					saveFile(contents);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			if (isDirty()) {
				isEditorDirty = false;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	/*
	@Override
	public void doSave_Old(IProgressMonitor monitor) {
		// Check if we are currently in the source editor
		if (getActivePage() != controls.size() && isEditorDirty) {
			updateSourceEditorFromConfigPages();
		}
		isEditorDirty = false;
		sourceEditor.doSave(monitor);
	}
	*/

	@Override
	public void doSaveAs() {
	}

	/*
	@Override
	public void doSaveAs_Old() {
		if (getActivePage() != controls.size() && isEditorDirty) {
			updateSourceEditorFromConfigPages();
		}
		isEditorDirty = false;
		sourceEditor.doSaveAs();
		setInput(sourceEditor.getEditorInput());
		updateTitle();
	}
	*/

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// modified();
	}

	public void modified() {
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

	protected abstract void createGeneralPage() throws PartInitException;

	/*
	protected void createSourcePage() {
		try {
			sourceEditor = new TextEditor();
			int index = addPage(sourceEditor, getEditorInput());
			setPageText(index, "Source");
		} catch (PartInitException pie) {
			pie.printStackTrace();
		}
	}
	*/

//	private void updateTitle() {
//		IEditorInput input = getEditorInput();
//		setTitle(input.getName());
//		setTitleToolTip(input.getToolTipText());
//	}

	
	public void setFocus() {
		super.setFocus();
		/*
		int index = getActivePage();
		if (index < controls.size()) {
			if (controls.get(index) != null)
				(controls.get(index)).setFocus();
		} else {
			//sourceEditor.setFocus();
		}
		*/
	}

	/*
	public void goToMarker(IMarker marker) {
		setActivePage(SOURCE_PAGE_INDEX);
		sourceEditor.gotoMarker(marker);
	}
	*/

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "", "");
		return (displayName);
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
		if (editorFile != null && !editorFile.isLinked(IResource.CHECK_ANCESTORS) && editorFile.getLocation() != null) {
			return editorFile.getLocation().toString();
		}
		return null;
	}

	protected abstract FormPage[] getEditorPages();
	
	protected abstract AbstractSharedResourceEditorPage[] getSchemaEditorPages();
	
	protected abstract void loadModel();

	protected abstract String saveModel();
	
	private void saveFile(String contents) {
		try {
			IFile file = getEditorFile();
			ByteArrayInputStream is = new ByteArrayInputStream(contents.getBytes(ModelUtils.DEFAULT_ENCODING));
			file.setContents(is, true, true, null);
		} catch (Exception e) {
		}
	}
	
//	protected void pageChange(int newPageIndex) {
//		/*
//		switch (newPageIndex) {
//		case SOURCE_PAGE_INDEX:
//			if (isEditorDirty) {
//				updateSourceEditorFromConfigPages();
//				isEditorDirty = false;
//			}
//			break;
//		default:
//			if (isDirty())
//				updateConfigPagesFromSourceEditor();
//			break;
//		}
//		*/
//		// isEditorDirty = false;
//		super.pageChange(newPageIndex);
//	}

	@Override
	protected void addPages() {
		loadModel();
		try {
			createGeneralPage();
			//createSourcePage();
			
			emptyBottomTab();
			
			autoSave();
			
			boolean linked = false;
			IFile file = getEditorFile();
			if (file != null) {
				linked = file.isLinked(IResource.CHECK_ANCESTORS);
			}
			setEnabled(!linked);
			
			// Workaround for Linux Platforms
			// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
			// user needs to close/re-open the editors
			StudioUIUtils.resetPerspective();
			
		} catch (PartInitException pie) {
			// TODO 
		}
	}
	
	protected void autoSave() {
		// Auto-save if it either an empty file or if something wasn't initialized correctly and default values were assigned
		String filePath = getEditorFilePath();
		if (isDirty() || filePath == null || new File(filePath).length() == 0) {
			doSave(null);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor#dispose()
	 */
	public void dispose() {
		/* [Sridhar] - Explicit calls to dispose the pages is posing 2 problems:
			1. NPE when invoking the superclass' dispose method (FormEditor#dispose)
			2. Introduces SWT leaks. Each invocation of the editor is causing a leak of 7 Color objects.
			
			We will need to revisit this.
		*/				
		/*
		FormPage[] editorPages = getEditorPages();
		if (editorPages != null) {
			for (FormPage page : editorPages) {
				if (page != null) {
					page.dispose();
				}
			}
		}

		AbstractSchemaEditorPage[] schemaEditorpages = getSchemaEditorPages();
		if (schemaEditorpages != null) {
			for (AbstractSchemaEditorPage page : schemaEditorpages) {
				if (page != null) {
					page.dispose();
				}
			}
		}
		*/
		if (fTitleImage != null) {
			fTitleImage.dispose();
		}
		super.dispose();
		getSite().getPage().removePartListener(partListener);
	}
	

	public Document getDocument() {
		return document;
	}

	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == AbstractSharedResourceEditor.this) {
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
	
	protected void emptyBottomTab() {
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractFormEditor#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		if (getContainer() != null) {
			getContainer().setEnabled(enabled);
			if(getEditorInput() instanceof JarEntryEditorInput){
				setTitleReadOnly();
			}
			fEnabled = enabled;
		}
	}
	
	public void setTitleReadOnly() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName().concat(Messages.getString("studio.navigator.label.read.only")));
		Image oldImage = fTitleImage;
		fTitleImage = new Image(Display.getDefault(), getTitleImage(), SWT.IMAGE_DISABLE);
		setTitleImage(fTitleImage);
		if (oldImage != null) {
			oldImage.dispose();
		}
	}
	
	
	public boolean isEnabled() {
		return fEnabled;
	}
	
	@Override
	protected int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * @param callSuperFormEditor
	 * @param newPageIndex
	 */
	protected void pageChange(boolean callSuperFormEditor, int newPageIndex) {
		super.pageChange(newPageIndex);
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		// fix for windows handles
		int oldPageIndex = getCurrentPage();
		if (oldPageIndex != -1 && pages.size() > oldPageIndex
				&& pages.get(oldPageIndex) instanceof IFormPage
				&& oldPageIndex != newPageIndex) {
			// Check the old page
			IFormPage oldFormPage = (IFormPage) pages.get(oldPageIndex);
			if (oldFormPage.canLeaveThePage() == false) {
				setActivePage(oldPageIndex);
				return;
			}
		}
		// Now is the absolute last moment to create the page control.
		if (pages.contains(newPageIndex)) {
			Object page = pages.get(newPageIndex);
			if (page instanceof IFormPage) {
				IFormPage fpage = (IFormPage) page;
				if (fpage.getPartControl() == null) {
					fpage.createPartControl(getContainer());
					setControl(newPageIndex, fpage.getPartControl());
					fpage.getPartControl().setMenu(getContainer().getMenu());
				}
			}
		}
		if (oldPageIndex != -1 && pages.size() > oldPageIndex
				&& pages.get(oldPageIndex) instanceof IFormPage) {
			// Commit old page before activating the new one
			IFormPage oldFormPage = (IFormPage) pages.get(oldPageIndex);
			IManagedForm mform = oldFormPage.getManagedForm();
			if (mform != null)
				mform.commit(false);
		}
		if (pages.size() > newPageIndex
				&& pages.get(newPageIndex) instanceof IFormPage)
			((IFormPage) pages.get(newPageIndex)).setActive(true);
		if (oldPageIndex != -1 && pages.size() > oldPageIndex
				&& newPageIndex != oldPageIndex && 
				pages.get(oldPageIndex) instanceof IFormPage)
			((IFormPage) pages.get(oldPageIndex)).setActive(false);
		// Call super - this will cause pages to switch
		if (pages.contains(newPageIndex)) {
			super.pageChange(newPageIndex);
		}
		this.currentPage = newPageIndex;
	}


}