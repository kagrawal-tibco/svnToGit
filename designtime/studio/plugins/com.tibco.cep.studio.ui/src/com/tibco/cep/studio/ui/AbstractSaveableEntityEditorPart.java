package com.tibco.cep.studio.ui;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openPerspective;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.refreshPaletteAndOverview;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.ui.persistence.ManageResourceUtils;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractSaveableEntityEditorPart extends AbstractStudioResourceEditorPart implements IEditingDomainProvider,ISelectionProvider {
 
	protected Entity entity;
	protected IEditorSite site;
	protected int pageIndex;
	protected ISelectionChangedListener selectionChangedListener;
	protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();
	protected ISelection editorSelection = StructuredSelection.EMPTY;
	protected AdapterFactoryEditingDomain editingDomain;
	protected Collection<Resource> removedResources = new ArrayList<Resource>();
	protected Collection<Resource> changedResources = new ArrayList<Resource>();
	protected Collection<Resource> savedResources = new ArrayList<Resource>();
	protected boolean dmAssociationFlag = false;
	protected Set<String> domainPathSet= new HashSet<String>();
	protected boolean ownerConceptDefined = true;
	protected IFile file = null;
	protected String selectedPropertyDefinitionName;
	protected Adapter adapter;
	protected IEditorInput newInput;
	
	public int propertyTableModifictionType = -1;
	protected CommandStackListener commandStackListener; 
	
	@Override
	public void dispose() {
		super.dispose();
		this.entity = null;
		this.site = null;
		this.selectionChangedListener = null;
		if (this.selectionChangedListeners != null) {
			this.selectionChangedListeners.clear();
			this.selectionChangedListeners = null;
		}
		this.editorSelection = null;
		if (this.editingDomain != null) {
			this.editingDomain.getCommandStack().removeCommandStackListener(commandStackListener);
			this.commandStackListener = null;
			this.editingDomain = null;
		}
	}
	
	protected void removeAdapter() {
		if (adapter != null) {
			Entity e = getEntity();
			e.eAdapters().remove(adapter);
		}
	}

    protected Adapter getAdapter() {
    	return adapter;
    }

	public boolean isOwnerConceptDefined() {
		return ownerConceptDefined;
	}

	public void setOwnerConceptDefined(boolean ownerConceptDefined) {
		this.ownerConceptDefined = ownerConceptDefined;
	}
	
	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model
	 * file.
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		saving = true;
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					saveResource();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			postSaveResource();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saving = false;
		}
	}
	
	/**
	 * For No Progress monitor Resource Save
	 */
	public void doSave(){
		// do this upon the first change, rather than upon save.
//		if(getEditorInput() instanceof FileEditorInput){
//			final IFile file = ((FileEditorInput)getEditorInput()).getFile();
//			if(file != null) {
//				IStatus validateEdit = ResourcesPlugin.getWorkspace().validateEdit(new IFile[] { file }, form.getShell());
//				if (!validateEdit.isOK()) {
//					MessageDialog.openError(form.getShell(), "Save Failed", validateEdit.getMessage());
//					return;
//				}
//			}
//		}

		try {
			saving = true;
			saveResource();
			postSaveResource();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			saving = false;
		}
	}
	
	/**
	 * @throws Exception
	 */
	protected void saveResource()throws Exception {
		StudioUIPlugin.getDefault().getManageResource().saveResource(editingDomain.getResourceSet());
	}
	
	/**
	 * @throws Exception
	 */
	protected void postSaveResource()throws Exception {
		((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
		if (isDirty()) {
			isModified = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		setDmAssociationFlag(false);
		getDomainPathSet().clear();
	}
	

	public void firePropertyChange() {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
	
	/**
	 * create the model
	 */
	public void createModel(IEditorInput input) {
		IFile file = null; 
			if (input instanceof IFileEditorInput) {
				file = ((IFileEditorInput) input).getFile(); 
				file.getFullPath(); 
				createModel(file);
			}
	}
	
	public void createModel(IEditorInput input, boolean b) {
		IFile file = null; IFile file1; newInput = input;
		if(b){
			if (input instanceof IFileEditorInput) {
				file = ((IFileEditorInput) input).getFile(); 
				file.getFullPath(); 
				createModel(file);
			}
		}else{
			file = ((IFileEditorInput) newInput).getFile();
		    String linkFilePath = file.getProjectRelativePath().removeFileExtension().toString();
			if (linkFilePath != null  &&  !linkFilePath.startsWith("/")) {
				linkFilePath = "/" + linkFilePath ;
			}
			file1 = IndexUtils.getLinkedResource(file.getProject().getName(),linkFilePath);
			createModel(file1);
		}
		
	}
	
	public void createModel(IFile file) {
		if (file != null) {
			editingDomain = ManageResourceUtils.eINSTANCE.getEditingDomain(file);
			editingDomain.getCommandStack().addCommandStackListener(getCommandStackListener());
		}
	}
	
	protected CommandStackListener getCommandStackListener() {
		if (commandStackListener == null) {
			commandStackListener = new CommandStackListener() {
				public void commandStackChanged(final EventObject event) {
					getContainer().getDisplay().asyncExec(
							new Runnable() {
								public void run() {
									modified();
									Command mostRecentCommand = ((CommandStack) event.getSource()).getMostRecentCommand();
									if (mostRecentCommand != null) {
										//TODO
									}
								}
							});
				}
			};
		}
		return commandStackListener;
	}

	@Override
	protected void createPages() {
		createUIEditorPage();
		updateTab();
		updateTitle();
	}
	
	protected void updateTab() {
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}
	
	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Handles activation of the editor or it's associated views.
	 */
	protected void handleActivate() {
		if (editingDomain.getResourceToReadOnlyMap() != null) {
			editingDomain.getResourceToReadOnlyMap().clear();
			setSelection(getSelection());
		}
		if (!removedResources.isEmpty()) {
			if (handleDirtyConflict()) {
				getSite().getPage().closeEditor(this, false);
				this.dispose();
			} else {
				removedResources.clear();
				changedResources.clear();
				savedResources.clear();
			}
		} else if (!changedResources.isEmpty()) {
			changedResources.removeAll(savedResources);
			handleChangedResources();
			changedResources.clear();
			savedResources.clear();
		}
		openPerspective(site, getPerspectiveId());
	}
	
	protected void handleRuleActivate() {
		openPerspective(site, getPerspectiveId());
		refreshPaletteAndOverview(site);
	}
	
	/**
	 * Handles what to do with changed resources on activation.
	 */
	protected void handleChangedResources() {
		if (!changedResources.isEmpty()
				&& (!isDirty() || handleDirtyConflict())) {
			editingDomain.getCommandStack().flush();

			for (Iterator<Resource> i = changedResources.iterator(); i
					.hasNext();) {
				Resource resource = i.next();
				if (resource.isLoaded()) {
					resource.unload();
					try {
						resource.load(Collections.EMPTY_MAP);
					} catch (IOException exception) {
						// TODO
					}
				}
			}
		}
	}
	/**
	 * Shows a dialog that asks if conflicting changes should be discarded.
	 */
	protected boolean handleDirtyConflict() {
		return MessageDialog
				.openQuestion(
						getSite().getShell(),
						"Conflicted",
						"do you want to merge?");
	}
	
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if(listener !=null){
			if(selectionChangedListeners != null){
				selectionChangedListeners.add(listener);
			}
		}
		
	}

	public ISelection getSelection() {
		return editorSelection;
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		if (listener != null) {
			if(selectionChangedListeners!=null)
			selectionChangedListeners.remove(listener);
		}
	}
	
	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to
	 * set this editor's overall selection. Calling this result will notify the
	 * listeners.
	 */
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
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	
	protected abstract void addFormPage() throws PartInitException;
	
	
	/**
	 * page switch
	 * @param pageIndex
	 */
	public void activePage(int pageIndex){
		this.setActivePage(pageIndex);
	}
	
	/**
	 * @return
	 */
	public Entity getEntity() {
		if (entity == null) {
			EditingDomain editingDomain = getEditingDomain();
			ResourceSet resourceSet = editingDomain.getResourceSet(); 
			if(!resourceSet.getResources().isEmpty()){
			
				Resource fResource = (Resource) resourceSet.getResources().get(0);			
				for (Iterator<?> iterator = fResource.getContents().iterator(); iterator.hasNext();) {
					Object object = iterator.next();
					if (object instanceof Entity) {
						entity = (Entity) object;
						break;
					}
				}	
			}
			else{
				createModel(newInput,false);
				EditingDomain editingDomain1 = getEditingDomain();
				ResourceSet resourceSet1 = editingDomain1.getResourceSet(); 
				Resource fResource1 = (Resource) resourceSet1.getResources().get(0);			
				for (Iterator<?> iterator = fResource1.getContents().iterator(); iterator.hasNext();) {
					Object object = iterator.next();
					if (object instanceof Entity) {
						entity = (Entity) object;
						break;
					}
				}
			}
		} 
		return entity;
	}
	
	/**
	 * If editor closes when there are removal in Domain Association externally
	 * @param propList
	 */
	
	protected void saveDomainAssociation(EList<PropertyDefinition> propList){
		//Handling Domain Association
		if(isDmAssociationFlag() && domainPathSet.size()>0){
			for(String s:domainPathSet){
				for(PropertyDefinition def:propList){
					int index = 0;
					for(DomainInstance instance:def.getDomainInstances()){
						if(instance.getResourcePath().equals(s)){
							break;	
						}
						index++;
					}
					if(def.getDomainInstances().size()>0)
						def.getDomainInstances().remove(index);
				}
			}
		}
	}
	
	
	public boolean isDmAssociationFlag() {
		return dmAssociationFlag;
	}

	public void setDmAssociationFlag(boolean dmAssociationFlag) {
		this.dmAssociationFlag = dmAssociationFlag;
	}
	
	public Set<String> getDomainPathSet() {
		return domainPathSet;
	}

	public void setDomainPathSet(Set<String> domainPathSet) {
		this.domainPathSet = domainPathSet;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput)input).getFile();
			if (IndexUtils.isProjectLibType(file)) {
				fEnabled = false;
			}
		}
		
		if (input instanceof IStorageEditorInput && !(input instanceof FileEditorInput)) {
			fEnabled = false;
			if (this.site == null || !(this.site.getId().equals("com.tibco.cep.editors.ui.rulesEditor"))
					&& !(this.site.getId().equals("com.tibco.cep.editors.ui.rulesTemplateEditor"))
					&& !(this.site.getId().equals("com.tibco.cep.editors.ui.rulefunctionEditor"))) {
				try {
					IStorageEditorInput storageInput = (IStorageEditorInput)input;
					IStorage store = storageInput.getStorage();
					if (store instanceof JarEntryFile) {
						JarEntryFile storeRes = (JarEntryFile)store;			
						String ownerProjectName = storeRes.getProjectName();
						InputStream stream = null;
						try {
							stream = store.getContents();		
							Resource resource;
							String uriPath = storeRes.getName();
							URI uri = URI.createURI(uriPath);
							Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(uri);
							if (factory != null) {
								resource = factory.createResource(uri);
							} else {
								resource = new XMIResourceImpl(uri);
							}
							resource.load(stream, null);
							for (EObject eobject : resource.getContents()){
								if (eobject instanceof Entity) {
									entity = (Entity)eobject;
									break;
								}
							}
						} finally {
							try {	
								if (stream != null){
									stream.close();
								}
								((JarEntryFile) store).closeJarFile();
							} catch (Exception e){
								e.printStackTrace();
							}
						}
						if (entity != null){
							entity.setOwnerProjectName(ownerProjectName);
							setProject(ResourcesPlugin.getWorkspace().getRoot().getProject(ownerProjectName));
							super.init(site, input);
						}
					}
				} catch (Exception e) {
					StudioUIPlugin.log(e);
				}
			} 
		}
		super.init(site, input);
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getSelectedPropertyDefinitionName() {
		return selectedPropertyDefinitionName;
	}

	public void setSelectedPropertyDefinitionName(
			String selectedPropertyDefinitionName) {
		this.selectedPropertyDefinitionName = selectedPropertyDefinitionName;
	}

	public int getPropertyTableModifictionType() {
		return propertyTableModifictionType;
	}

	public void setPropertyTableModifictionType(int propertyTableModifictionType) {
		this.propertyTableModifictionType = propertyTableModifictionType;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (IProject.class.equals(adapter)) {
			if (file != null) {
				return file.getProject();
			} 
			IEditorInput editorInput  = (IEditorInput) getEditorInput();
			if(editorInput != null && editorInput instanceof IFileEditorInput) {
				IFileEditorInput fei = (IFileEditorInput) editorInput;
				return fei.getFile().getProject();
			}
			return project;
		}
		return super.getAdapter(adapter);
	}

	/**
	 * Revert the editor to the contents saved on disk.<br>
	 * Note: This method may be called from a non-UI thread, so any UI work
	 * should be wrapped in a syncExec/asyncExec
	 */
	public void doRevert() {
		// TODO revert the editor (useful when the file is not editable, due to user permissions or source code control)
		System.out.println("reverting");
	}
}