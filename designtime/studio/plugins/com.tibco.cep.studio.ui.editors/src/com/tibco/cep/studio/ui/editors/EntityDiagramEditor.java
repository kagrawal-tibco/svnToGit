package com.tibco.cep.studio.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramEditorPartListener;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditorInput;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSRoutingInputTailor;

abstract public class EntityDiagramEditor<E extends Entity, T extends EntityDiagramManager<E>> extends AbstractStudioResourceEditorPart implements IGraphDrawing {
	
	// public final static String ID = "com.tibco.cep.entities.editors.EntityDiagramEditor";
	
	/**
	 * A reference to the generic {@link DiagramManager} which will be shadowed by superclass.
	 */
	protected T entityDiagramManager;
	
	protected int entityPageIndex;
	
	private IFile file = null;
	
	private EntityDiagramEditorPartListener entityListener = new EntityDiagramEditorPartListener();

	
    public IDiagramModelAdapter getDiagramModelAdapter() {
    	return entityDiagramManager.getDiagramModelAdapter();
    }

	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
	}

	@Override
	public void dispose() {
		try {
			// remove editor part listener to page
			getSite().getPage().removePartListener(getEnityEditorpartListener());
			super.dispose();
			if (entityDiagramManager != null) {
				entityDiagramManager.dispose();
			}
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
	}

	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
	}

	private void addFormPage() throws PartInitException {

		entityDiagramManager = (T) this.getDiagramManager();
		T eDiagramManager = entityDiagramManager;
		eDiagramManager.createPartControl(getContainer());

//		if(eDiagramManager instanceof ProjectDiagramManager){
//			new ProjectDiagramTools((ProjectDiagramManager)eDiagramManager);
//		}
		entityPageIndex = addPage((entityDiagramManager).getControl());
		this.setActivePage(entityPageIndex);
	}

	/**
	 * Update the editor's title based upon the content being edited.
	 */
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		entityDiagramManager.save();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getStateMachineDiagramViewer() {
		return entityDiagramManager;
	}

	public int getSWUIPageIndex() {
		return entityPageIndex;
	}

	public void setSWUIPageIndex(int pageIndex) {
		entityPageIndex = pageIndex;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (input instanceof EntityDiagramEditorInput) {
			super.init(site, (EntityDiagramEditorInput)input);
		} else if(input instanceof FileEditorInput) {
			IProject dproject = (IProject)getProject(input);
			if (dproject == null) {
				super.init(site, input);
				return;
			}
			file = ((FileEditorInput)input).getFile();
			EntityDiagramEditorInput coninput = new EntityDiagramEditorInput(file, (IProject) dproject);
			super.init(site, coninput);
		} else if(input instanceof SequenceDiagramEditorInput) {
			super.init(site, (SequenceDiagramEditorInput)input);
		} else {
			super.init(site, input);
		}
		// TODO: this should either be renamed to "EntityEditorPartListener" or
		// abstracted out. - renamed for now
		site.getPage().addPartListener(getEnityEditorpartListener());
	}
	
	public IDiagramManager getDiagramManager() {
		return entityDiagramManager;
	}

	public TSEGraphManager getGraphManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSHierarchicalLayoutInputTailor getHierarchicalLayoutInputTailor() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSEAllOptionsServiceInputData getInputData() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSEOverviewComponent getOverviewComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSSwingCanvas getDrawingCanvas() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSRoutingInputTailor getRoutingInputTailor() {
		// TODO Auto-generated method stub
		return null;
	}

	public LayoutManager getLayoutManager() {
		if (entityDiagramManager != null) {
//			return ((EntityDiagramManager) this.entityDiagramManager).
//				getLayoutManager();
			return entityDiagramManager.getLayoutManager();
		}
		else {
			return null;
		}
	}

	/**
	 * @return
	 */
	public EntityDiagramEditorPartListener getEnityEditorpartListener(){
		if (entityListener == null) {
			entityListener = new EntityDiagramEditorPartListener();
		}
		return entityListener;
	}
	
	public void setLayoutManager(LayoutManager mgr) {
		// TODO Auto-generated method stub
	}

	private IResource getProject(IEditorInput editorInput) {
		IFile file = null;
		if (editorInput instanceof FileEditorInput) {
			file = ((FileEditorInput)editorInput).getFile();
		}
		if (file == null) {
			return null;
		}
		return file.getProject();
		
	}
	
	public IFile getFile() {
		return file;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#getPartListener()
	 */
	@Override
	public AbstractEditorPartPaletteHandler getPartListener(){
		return entityListener;
	}
	
	public boolean isDisplayFullName() {
		return false;
	}
	
	public boolean isfillTaskIcons() {
		return false;
	}
}