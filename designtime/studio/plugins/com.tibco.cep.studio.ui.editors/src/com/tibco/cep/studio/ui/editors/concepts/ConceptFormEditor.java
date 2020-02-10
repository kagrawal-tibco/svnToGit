package com.tibco.cep.studio.ui.editors.concepts;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.checkDomainEditorReference;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.updateStateMachineRuleSymbols;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.updateStateTransitionRulesSymbols;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.forms.AbstractConceptSaveableEditorPart;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.notification.ConceptAdapterImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class ConceptFormEditor extends AbstractConceptSaveableEditorPart{

	public final static String ID = "com.tibco.cep.concept.editors.formeditor";
	
	public ConceptFormDesignViewer conceptFromDesignViewer;
	private ConceptFormEditorInput conceptFormEditorInput;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage()  {
		conceptFromDesignViewer = new ConceptFormDesignViewer(this);
		conceptFromDesignViewer.createPartControl(getContainer());
		pageIndex = addPage(conceptFromDesignViewer.getControl());
		this.setActivePage(0);
		this.setForm(conceptFromDesignViewer.getForm());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#updateTitle()
	 */
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
		if(conceptFormEditorInput!=null)
			setTitleImage(EntityImages.getImage(EntityImages.CONCEPT_PALETTE_CONCEPT));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			Concept concept = null;
			if(input instanceof ConceptFormEditorInput){
				concept = ((ConceptFormEditorInput)input).getConcept();
			}else{
				concept = getConcept();
			}
	        concept.eAdapters().add(getAdapter());
			setProject(file.getProject());
			conceptFormEditorInput = new ConceptFormEditorInput(file, concept);
			super.init(site, conceptFormEditorInput);
			site.getPage().addPartListener(partListener);
		} 
		 else {
			super.init(site, input);
		}
	}

	@Override
	protected Adapter getAdapter() {
		if (adapter == null) {
			adapter = new ConceptAdapterImpl(this);
		}
		return adapter;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose(){
		try{
			if (conceptFromDesignViewer != null) {
				conceptFromDesignViewer.dispose();
			}
			removeAdapter();

			if(isDirty() ){
				Concept concept = (Concept)IndexUtils.getEntity(getProject().getName(), getEntity().getFullPath());	
				if(isSmAssociationFlag() || isDmAssociationFlag()){
					try {
						saveStateMachineAssociation(concept);
						saveDomainAssociation(concept.getProperties());
						ModelUtils.saveEObject(getEntity());
//						getEntity().eResource().save(ModelUtils.getPersistenceOptions());
						//Refreshing the Project Explorer
						IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.ID);
						if(view!=null){
							((ProjectExplorer)view).getCommonViewer().refresh();
						}
						checkDomainAssocitionReferences();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				super.dispose();
			}
			getSite().getPage().removePartListener(partListener);
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			conceptFromDesignViewer = null;
			conceptFormEditorInput = null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
	
//		if(conceptFromDesignViewer.getPropTable().getCellEditor()!=null){
//			conceptFromDesignViewer.getPropTable().getCellEditor().stopCellEditing();
//		}
		checkDomainAssocitionReferences();
		super.doSave();
		setSmAssociationFlag(false);
		getStateMachinePathSet().clear();
		
		setPropertyTableModifictionType(-1);
		
		for(String stateMachinePath: statemachinePathSetFromConceptEditor){
			changeStateMachineEditorReference(stateMachinePath);
		}
		getStateMachinePathSetFromConceptEditor().clear();
	}
	
	/**
	 * Check for open domain editors 
	 */
	private void checkDomainAssocitionReferences(){
		if(isDmAssociationFlag()){
			Set<String> modifiedSet = new HashSet<String>();
			if(concept!=null){
				for(DomainInstance instance: concept.getAllDomainInstances()){
					modifiedSet.add(instance.getResourcePath());
				}
				checkDomainEditorReference(modifiedSet, getSite().getPage());
			}
		}
	}
	
	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == ConceptFormEditor.this) {
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
	
	public ConceptFormDesignViewer getConceptFromDesignViewer() {
		return conceptFromDesignViewer;
	}
	
	/**
	 * @param file
	 */
	private void changeStateMachineEditorReference(String stateMachinePath) {
		try{
			AbstractSaveableEntityEditorPart stateMachineEditor = null;
			for (IEditorReference reference : getSite().getPage().getEditorReferences()) {
				try {
					if (reference.getEditorInput() instanceof FileEditorInput) {
						FileEditorInput stateMachineditorInput = 
							(FileEditorInput)reference.getEditorInput();
						if (IndexUtils.getFullPath(stateMachineditorInput.getFile()).equals(stateMachinePath)) {
							stateMachineEditor =  (AbstractSaveableEntityEditorPart)reference.getEditor(true);
							stateMachineEditor.setOwnerConceptDefined(false);
							updateStateMachineRuleSymbols((StateMachine)stateMachineEditor.getEntity());
							updateStateTransitionRulesSymbols((StateMachine)stateMachineEditor.getEntity(), getConcept().getFullPath());
							stateMachineEditor.modified();
							break;
						}
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
			if(stateMachineEditor == null){
				StateMachine stateMachine =  (StateMachine)IndexUtils.getEntity(getConcept().getOwnerProjectName(), stateMachinePath, ELEMENT_TYPES.STATE_MACHINE);
				if(stateMachine != null){
					updateStateMachineRuleSymbols(stateMachine);
					updateStateTransitionRulesSymbols(stateMachine, getConcept().getFullPath());
					ModelUtils.saveEObject(stateMachine);
//					stateMachine.eResource().save(ModelUtils.getPersistenceOptions());
					
					try {
						IndexUtils.getFile(stateMachine.getOwnerProjectName(), stateMachine).refreshLocal(0, null);
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				getSite().getPage().saveEditor(stateMachineEditor, false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		createModel(file);
		setEntity(null);
		Concept concept = getConcept();			
        concept.eAdapters().add(new ConceptAdapterImpl(this));
        ((ConceptFormEditorInput)getEditorInput()).setConcept(concept);
        conceptFromDesignViewer.doRefresh(concept);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#refreshSMAssociationViewer()
	 */
	public void refreshSMAssociationViewer(){
		conceptFromDesignViewer.getSmAssociationViewer().refresh();
		if (conceptFromDesignViewer.getSmAssociationViewer().getSelection().isEmpty()) {
			conceptFromDesignViewer.getRemoveSMAssociationButton().setEnabled(false);
		}
	}
	
	@Override
	public void setFocus() {
		super.setFocus();
		conceptFromDesignViewer.refreshFieldOnFocus();
		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			refreshSMAssociationViewer();
		}
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}