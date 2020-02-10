package com.tibco.cep.studio.cluster.topology.editors;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.cluster.topology.handler.ClusterTopologyEditHandler;
import com.tibco.cep.studio.cluster.topology.handler.ClusterTopologyEditorPartListener;
import com.tibco.cep.studio.cluster.topology.properties.ClusterDiagramPropertySheetPage;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;
import com.tomsawyer.service.layout.TSRoutingInputTailor;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyEditor extends AbstractSaveableEntityEditorPart implements IGraphDrawing,ITabbedPropertySheetPageContributor {
	
	public final static String ID = "com.tibco.cep.studio.cluster.topology.editors.clusterEditor";
	protected ClusterTopologyDiagramManager clusterTopologyDiagramManager;
	protected int SWUIPageIndex;
	
	private ClusterTopologyEditorPartListener siteListener = null;
	private ClusterDiagramPropertySheetPage propertySheetPage;
	private HashMap<String, Action> handler;

	private IFile file;
	
    public IDiagramModelAdapter getDiagramModelAdapter() {
    	return clusterTopologyDiagramManager.getDiagramModelAdapter();
    }
	
	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}		
		updateTitle();
		setForm(clusterTopologyDiagramManager.getForm());
		setCatalogFunctionDrag(true);
		
		//Intitially make main Graph as workbench selection
		if(clusterTopologyDiagramManager !=null){
			if(clusterTopologyDiagramManager.getGraphManager()!=null){
				if(clusterTopologyDiagramManager.getGraphManager().getMainDisplayGraph()!=null){
					setWorkbenchSelection(clusterTopologyDiagramManager.getGraphManager().getMainDisplayGraph(), this);
				}
			}
		}

	}

	@Override
	public void dispose() {
		if(siteListener != null){
			getSite().getPage().removePartListener(siteListener);
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		}
		this.clusterTopologyDiagramManager.dispose();
		super.dispose();
	}

	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	protected void addFormPage() throws PartInitException {
		clusterTopologyDiagramManager = new ClusterTopologyDiagramManager(this);
		clusterTopologyDiagramManager.waitForInitComplete();
		clusterTopologyDiagramManager.createPartControl(getContainer());
		SWUIPageIndex = addPage((clusterTopologyDiagramManager).getControl());
		setPageText(SWUIPageIndex, "Site Topology");
		this.setActivePage(0);
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
		saving = true;
		try {
			clusterTopologyDiagramManager.save();
			if (isDirty()) {
				isModified = false;
				firePropertyChange(IEditorPart.PROP_DIRTY);
				updateSave();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saving = false;
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public IDiagramManager getDiagramManager() {
		return this.clusterTopologyDiagramManager;
	}

	public void setClusterTopologyDiagramManager(ClusterTopologyDiagramManager clusterTopologyDiagramManager) {
		this.clusterTopologyDiagramManager = clusterTopologyDiagramManager;
	}

	public int getSWUIPageIndex() {
		return SWUIPageIndex;
	}

	public void setSWUIPageIndex(int pageIndex) {
		SWUIPageIndex = pageIndex;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput) input).getFile();
			ClusterTopologyEditorInput clusterTopologyEditorInput = new ClusterTopologyEditorInput(file);
			super.init(site, clusterTopologyEditorInput);
		} else {
			super.init(site, input);
		}		
		if(siteListener == null){
			siteListener = new ClusterTopologyEditorPartListener(this);
		}
		site.setSelectionProvider(this);
		site.getPage().addPartListener(siteListener);
	}

	public void inputChanged() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	
	public void enableEdit(boolean enabled) {
		updateSave();
		this.getActionHandler(ActionFactory.COPY.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.CUT.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.DELETE.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.UNDO.getId(), this).setEnabled(	clusterTopologyDiagramManager.canUndo());
		this.getActionHandler(ActionFactory.REDO.getId(), this).setEnabled(	clusterTopologyDiagramManager.canRedo());
	
		//enableUndoRedoContext();
	}

	@Override
	public void modified() {
		super.modified();
		updateSave();
	}
	
	public void updateSave() {
		this.getActionHandler(ActionFactory.SAVE.getId(), this).setEnabled(isDirty());
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
		if (this.clusterTopologyDiagramManager != null) {
			return ((ClusterTopologyDiagramManager) this.clusterTopologyDiagramManager).getLayoutManager();
		} else {
			return null;
		}
	}

	@Override
	public TSEGraphManager getGraphManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TSEOverviewComponent getOverviewComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLayoutManager(LayoutManager mgr) {
		// TODO Auto-generated method stub
		
	}

	public IFile getFile() {
		return file;
	}

	@Override
	public String getContributorId() {
		return "com.tibco.cep.studio.cluster.topology.diagram.propertyContributor";
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class)
			return new ClusterDiagramPropertySheetPage(this);
		return super.getAdapter(adapter);
	}

	/**
	 * Get the new property sheet page for this editor.
	 * 
	 * @return the new property sheet page.
	 */
	public TabbedPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null || propertySheetPage.getControl() == null) {
			propertySheetPage = new ClusterDiagramPropertySheetPage(this);
		}
		return propertySheetPage;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.IGraphDrawing#getPalette()
	 */
	@Override
	public PALETTE getPalette() {
		return PALETTE.CLUSTER;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#getPartListener()
	 */
	@Override
	public AbstractEditorPartPaletteHandler getPartListener(){
		return siteListener;
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param id
	 * @param editor
	 * @return
	 */
	public Action getActionHandler(String id, ClusterTopologyEditor editor) {
		if (handler == null) {
			handler = new HashMap<String, Action>();
		}
		if (handler.get(id) == null) {
			handler.put(id, new ClusterTopologyEditHandler(id,editor));
		}
		return handler.get(id);
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.diagramming.diagram.perspective";
	}
	
	public boolean isDisplayFullName() {
		return false;
	}
	
	public boolean isfillTaskIcons() {
		return false;
	}

}
