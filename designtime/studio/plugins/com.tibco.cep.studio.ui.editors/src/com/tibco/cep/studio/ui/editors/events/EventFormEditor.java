package com.tibco.cep.studio.ui.editors.events;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.refreshPropertyTable;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.collectPropertyDomainTypeMismatch;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubEvents;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractEventSaveableEditorPart;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.notification.EventAdapterImpl;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * @author sasahoo
 */
public class EventFormEditor extends AbstractEventSaveableEditorPart {

	public final static String ID = "com.tibco.cep.event.editors.formeditor";
	public EventFormDesignViewer simplEventFormViewer;
	public AdvancedEventFormDesignViewer advancedEventFormDesignViewer;
	public AdvancedEventMasterDetailFormDesignViewer advancedEventMasterDetailsFormDesignViewer;
	
	public boolean isNewPayloadEditor = false;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		createUIEditorPage();
		updateTitle();
	}
	
	protected void addFormPage() throws PartInitException {
		simplEventFormViewer = new EventFormDesignViewer(this);
		simplEventFormViewer.createPartControl(getContainer());
		pageIndex = addPage(simplEventFormViewer.getControl());
		setPageText(pageIndex, Messages.getString("event.form.page.standard"));
		
		advancedEventFormDesignViewer = new AdvancedEventFormDesignViewer(this);
		advancedEventFormDesignViewer.createPartControl(getContainer());
		pageIndex = addPage(advancedEventFormDesignViewer.getControl());
		setPageText(pageIndex, Messages.getString("event.form.page.advanced"));
		
		if (isNewPayloadEditor) {
			advancedEventMasterDetailsFormDesignViewer = new AdvancedEventMasterDetailFormDesignViewer(this);
			advancedEventMasterDetailsFormDesignViewer.createPartControl(getContainer());
			pageIndex = addPage(advancedEventMasterDetailsFormDesignViewer.getControl());
			setPageText(pageIndex, Messages.getString("event.form.page.advanced"));
		}
		this.setActivePage(0);
		this.setForm(simplEventFormViewer.getForm());
		
		setCatalogFunctionDrag(true);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			SimpleEvent event = getSimpleEvent();
			event.eAdapters().add(getAdapter());
 			setSimpleEvent(event);
			setProject(file.getProject());
			simpleEventFormEditorInput = new EventFormEditorInput(file, (SimpleEvent)entity);
			super.init(site, simpleEventFormEditorInput);
			site.getPage().addPartListener(partListener);
		} else {
			super.init(site, input);
		}
	}
	
	@Override
	protected Adapter getAdapter() {
		if (adapter == null) {
			adapter = new EventAdapterImpl(this);
		}
		return adapter;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		//Check for all Property-Domain Type Mismatch and warn user. If no validate to show errors
		Map<PropertyDefinition, String> map = new HashMap<PropertyDefinition, String>();
		collectPropertyDomainTypeMismatch(getSimpleEvent().getOwnerProjectName(), getSimpleEvent().getProperties(), map);
		if(map.size() > 0){
			boolean status = MessageDialog.openQuestion(getSite().getShell(),
					"Remove Domain Model Association", "Do you want to remove all domains as property type changed?");
			if(status){
			  for(PropertyDefinition pd:map.keySet()){
				  pd.getDomainInstances().clear();
				  int index = getSimpleEvent().getProperties().indexOf(pd);
				  refreshPropertyTable(this, pd, entity, index);
			  }
			}
		}
		
//		check for unused imports here, as too many ChangeEvents are fired to perform check in paramEditor's ChangeListener
		getAdvancedEventFormDesignViewer().checkUnusedImports();
		if(getAdvancedEventMasterDetailsFormDesignViewer()!=null)
		getAdvancedEventMasterDetailsFormDesignViewer().checkUnusedImports();
		
		super.doSave(progressMonitor);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose(){
		try{
			if (simplEventFormViewer != null) {
				simplEventFormViewer.dispose();
			}
			if (advancedEventFormDesignViewer != null) {
				advancedEventFormDesignViewer.dispose();
			}
			if (advancedEventMasterDetailsFormDesignViewer != null) {
				advancedEventFormDesignViewer.dispose();
			}
			removeAdapter();
			if(isDirty() ){
				Event event = (Event)IndexUtils.getEntity(getProject().getName(), getEntity().getFullPath());	
				saveDomainAssociation(event.getProperties());
				if(isDmAssociationFlag()){
					try {
						ModelUtils.saveEObject(getEntity());
//						getEntity().eResource().save(ModelUtils.getPersistenceOptions());
						
						//Refreshing the Project Explorer
						IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.ID);
						if(view!=null){
							((ProjectExplorer)view).getCommonViewer().refresh();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			if(getSite() != null && getSite().getPage() != null && partListener != null){
				getSite().getPage().removePartListener(partListener);
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			super.dispose();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == EventFormEditor.this) {
				handleActivate();
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
	
	public EventFormDesignViewer getSimplEventFormViewer() {
		return simplEventFormViewer;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		createModel(file);
		setEntity(null);
		SimpleEvent event = getSimpleEvent();
		entity.eAdapters().add(new EventAdapterImpl(this));
        ((EventFormEditorInput)getEditorInput()).setSimpleEvent(event);
        simplEventFormViewer.doRefresh(event);
	}

	public AdvancedEventFormDesignViewer getAdvancedEventFormDesignViewer() {
		return advancedEventFormDesignViewer;
	}
	
	public AdvancedEventMasterDetailFormDesignViewer getAdvancedEventMasterDetailsFormDesignViewer() {
		return advancedEventMasterDetailsFormDesignViewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#setFocus()
	 */
	@Override
	public void setFocus() {
		super.setFocus();
		simplEventFormViewer.refreshFieldOnFocus();
		Event event = simplEventFormViewer.getEvent() ;
		
		/// Handling proj lib  -- Bugfix for BE - 18918 
		String projectName = getProject().toString() ;
		projectName =  projectName.substring(projectName.lastIndexOf("/") + 1 );
		IFile file = IndexUtils.getLinkedResource(projectName, event.getFullPath()) ;
		if (file != null && IndexUtils.isProjectLibType( file ) ) {
			advancedEventFormDesignViewer.getPayloadEditor().setfProjectName( projectName ) ;
		}
		//till here 
		
		advancedEventFormDesignViewer.refreshFormEditorSourceViewerConfiguration(); // re-reconcile expiry action editor
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				ParameterEditor parameterEditor = advancedEventFormDesignViewer.getPayloadEditor().getEditorPanel();
				if(isSuperEvent()){
					if(parameterEditor.isEnabled()){
						parameterEditor.setEnabled(false);
					}
				}else{
					if(isEnabled() && !parameterEditor.isEnabled()){
						parameterEditor.setEnabled(true);
					}
				}
			}
		});
		if (isNewPayloadEditor) {
			advancedEventMasterDetailsFormDesignViewer.refreshFormEditorSourceViewerConfiguration(); // re-reconcile expiry action editor
		}
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		// Workaround for Linux Platforms
		// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
		// user needs to close/re-open the editors
		if (newPageIndex == 1 ) {
			StudioUIUtils.resetPerspective();
		} 
	}
	
	/**
	 * @return
	 */
	public boolean isSuperEvent(){
		if(entity != null){
			List<String> subEventsPaths = getSubEvents(entity.getFullPath(), entity.getOwnerProjectName());
			if(subEventsPaths.size() > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}