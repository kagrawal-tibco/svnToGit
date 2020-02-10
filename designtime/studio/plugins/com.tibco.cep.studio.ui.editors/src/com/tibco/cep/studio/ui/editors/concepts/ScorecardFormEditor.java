package com.tibco.cep.studio.ui.editors.concepts;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.refreshPropertyTable;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.collectPropertyDomainTypeMismatch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.forms.AbstractScorecardSaveableEditorPart;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.notification.ConceptAdapterImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class ScorecardFormEditor extends AbstractScorecardSaveableEditorPart {

	public final static String ID = "com.tibco.cep.scorecard.editors.formeditor";
	
	public ScorecardFormViewer scorecardFromViewer;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage()  {
		scorecardFromViewer = new ScorecardFormViewer(this);
		scorecardFromViewer.createPartControl(getContainer());
		pageIndex = addPage(scorecardFromViewer.getControl());
		this.setActivePage(0);
		this.setForm(scorecardFromViewer.getForm());
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
			Scorecard scorecard = getScorecard();
            scorecard.eAdapters().add(getAdapter());
 			setScorecard(scorecard);
			setProject(file.getProject());
			scorecardFormEditorInput = new ScorecardFormEditorInput(file, scorecard);
			super.init(site, scorecardFormEditorInput);
			site.getPage().addPartListener(partListener);
		} else {
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
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
	
	/*	if(scorecardFromViewer.getPropTable().getCellEditor()!=null){
			scorecardFromViewer.getPropTable().getCellEditor().stopCellEditing();
		}
	*/	
		//Check for all Property-Domain Type Mismatch and warn user. If no validate to show errors
		Map<PropertyDefinition, String> map = new HashMap<PropertyDefinition, String>();
		collectPropertyDomainTypeMismatch(getScorecard().getOwnerProjectName(), getScorecard().getProperties(), map);
		if(map.size() > 0){
			boolean status = MessageDialog.openQuestion(getSite().getShell(),
					"Remove Domain Model Association", "Do you want to remove all domains as property type changed?");
			if(status){
			  for(PropertyDefinition pd:map.keySet()){
				  pd.getDomainInstances().clear();
				  int index = getScorecard().getProperties().indexOf(pd);
				  refreshPropertyTable(this, pd, entity, index);
			  }
			}
		}
		
		super.doSave();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose(){
		try{
			if (scorecardFromViewer != null) {
				scorecardFromViewer.dispose();
			}
			removeAdapter();
			if(isDirty() ){
				Scorecard scorecard = (Scorecard)IndexUtils.getEntity(getProject().getName(), getEntity().getFullPath());	
				saveDomainAssociation(scorecard.getProperties());
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

			}else{
				super.dispose();
			}
			getSite().getPage().removePartListener(partListener);
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
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
			if (p == ScorecardFormEditor.this) {
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
	
	public ScorecardFormViewer getScorecardFromViewer() {
		return scorecardFromViewer;
	}	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		createModel(file);
		setEntity(null);
		Scorecard scorecard = getScorecard();		
        scorecard.eAdapters().add(new ConceptAdapterImpl(this));
        ((ScorecardFormEditorInput)getEditorInput()).setScorecard(scorecard);
        scorecardFromViewer.doRefresh(scorecard);
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}