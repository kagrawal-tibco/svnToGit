package com.tibco.cep.studio.ui.statemachine.diagram.editors;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.updateProperties;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.handler.StateMachineEditorPartListener;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSRoutingInputTailor;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractStateMachineEditorPart extends AbstractSaveableEntityEditorPart implements IGraphDrawing, ITabbedPropertySheetPageContributor {

	public final static String ID = "com.tibco.cep.studio.ui.statemachine.editor";
	protected StateMachineDiagramManager stateMachineDiagramManager = null;
	protected StateMachineEditorPartListener smListener = new StateMachineEditorPartListener();
	protected int SWUIPageIndex;
	protected IFile file = null;
	protected StateMachine stateMachine;
	
	protected String oldOwnerConceptPath = null;
	protected String newOwnerConceptPath = null;
    protected Text ownerConceptTextField; 
    protected boolean isMainChanged = false;

    
    public IDiagramModelAdapter getDiagramModelAdapter() {
    	return stateMachineDiagramManager.getDiagramModelAdapter();
    }
    
	public Text getOwnerConceptTextField() {
		return ownerConceptTextField;
	}

	public void setOwnerConceptTextField(Text ownerConceptTextField) {
		this.ownerConceptTextField = ownerConceptTextField;
	}

	public String getOldOwnerConceptPath() {
		return oldOwnerConceptPath;
	}

	public void setOldOwnerConceptPath(String oldOwnerConceptPath) {
		this.oldOwnerConceptPath = oldOwnerConceptPath;
	}

	public String getNewOwnerConceptPath() {
		return newOwnerConceptPath;
	}

	public void setNewOwnerConceptPath(String newOwnerConceptPath) {
		this.newOwnerConceptPath = newOwnerConceptPath;
	}
	
	/**
	 * @param mainChanged
	 */
	public void mainChanged(boolean mainChanged){
		this.isMainChanged = mainChanged;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (input instanceof FileEditorInput) {
			createModel(input,true);
			stateMachine = getStateMachine();
			updateExtendedProperties();
			file = ((FileEditorInput) input).getFile();
			
			String linkFilePath = file.getProjectRelativePath().removeFileExtension().toString();
			if (linkFilePath != null  &&  !linkFilePath.startsWith("/")) {
				linkFilePath = "/" + linkFilePath ;
			}
			IFile fileTemp = IndexUtils.getLinkedResource(file.getProject().getName(), linkFilePath);
			if ( fileTemp!= null) {
				fEnabled = false ;
			}
			setStateMachine(stateMachine);
			StateMachineEditorInput stateMachineEditorInput = new StateMachineEditorInput(file, stateMachine);
			super.init(site, stateMachineEditorInput);
		} else {
			super.init(site, input);
		}
		site.setSelectionProvider(this);
		site.getPage().addPartListener(smListener);
	}

	protected void updateExtendedProperties() {
		if (stateMachine == null) {
			return;
		}
		if (updateProperties(stateMachine.getExtendedProperties())) {
			try {
				ModelUtils.saveEObject(stateMachine);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param editorInput
	 * @return
	 */
	public StateMachine getStateMachine() {
		return (StateMachine)getEntity();
	}

	public Object getStateMachineDiagramManager() {
		return stateMachineDiagramManager;
	}

	/**
	 * @param stateMachineDiagramManager
	 */
	public void setStateMachineDiagramManager(StateMachineDiagramManager stateMachineDiagramManager) {
		this.stateMachineDiagramManager = stateMachineDiagramManager;
	}

	public int getSWUIPageIndex() {
		return SWUIPageIndex;
	}

	public void setSWUIPageIndex(int pageIndex) {
		SWUIPageIndex = pageIndex;
	}
	
	public TSEGraph getCompositeGraph() {
		return null;
	}

	public TSEGraph getConcurrentGraph() {
		return null;
	}

	public IDiagramManager getDiagramManager() {
		return stateMachineDiagramManager;
	}

	public TSEGraphManager getGraphManager() {
		return null;
	}

	public TSHierarchicalLayoutInputTailor getHierarchicalLayoutInputTailor() {
		return null;
	}

	public TSEAllOptionsServiceInputData getInputData() {
		return null;
	}

	public TSEOverviewComponent getOverviewComponent() {
		return null;
	}

	public TSSwingCanvas getDrawingCanvas() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 */
	public String getContributorId() {
		return "com.tibco.cep.statemachine.smPropertyContributor";
	}

	public TSRoutingInputTailor getRoutingInputTailor() {
		return null;
	}

	public LayoutManager getLayoutManager() {
		if (this.stateMachineDiagramManager != null) {
			return ((StateMachineDiagramManager) this.stateMachineDiagramManager).getLayoutManager();
		} else {
			return null;
		}
	}

	public void setLayoutManager(LayoutManager mgr) {
		// TODO Auto-generated method stub

	}
	public void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose() {
		try{
			if (stateMachineDiagramManager != null) {
				stateMachineDiagramManager.dispose();
				stateMachineDiagramManager = null;
			}
			super.dispose();
			if(getSite()!= null){
			getSite().getPage().removePartListener(smListener);
			
			smListener = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isDisplayFullName() {
		return false;
	}
	
	public boolean isfillTaskIcons() {
		return false;
	}
}