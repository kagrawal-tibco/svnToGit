package com.tibco.cep.decision.tree.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.core.util.DecisionTreeCoreUtil;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSRoutingInputTailor;

public abstract class AbstractDecisionTreeEditorPart extends AbstractSaveableEntityEditorPart implements IGraphDrawing, ITabbedPropertySheetPageContributor {
	public final static String ID = "com.tibco.cep.decision.tree.ui.editor";
	protected DecisionTreeDiagramManager decisionTreeDiagramManager = null;
	protected DecisionTreeEditorPartListener dtListener = new DecisionTreeEditorPartListener();
	protected int SWUIPageIndex;
	protected IFile file = null;
	protected DecisionTree decisionTree = null;
    protected boolean isMainChanged = false;
    
    public IDiagramModelAdapter getDiagramModelAdapter() {
    	return decisionTreeDiagramManager.getDiagramModelAdapter();
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
			//createModel(input);
			file = ((FileEditorInput) input).getFile();
			decisionTree = DecisionTreeCoreUtil.parseFile(file);
			setDecisionTree(decisionTree);
			DecisionTreeEditorInput decisionTreeEditorInput = new DecisionTreeEditorInput(file, decisionTree);
			super.init(site, decisionTreeEditorInput);
		} else {
			super.init(site, input);
		}
		site.getPage().addPartListener(dtListener);
	}

	
	
	public DecisionTree getDecisionTree() {
		return decisionTree;
	}

	public DecisionTreeDiagramManager getDecisionTreeDiagramManager() {
		return decisionTreeDiagramManager;
	}

	public void setDecisionTreeDiagramManager(DecisionTreeDiagramManager decisionTreeDiagramManager) {
		this.decisionTreeDiagramManager = decisionTreeDiagramManager;
	}

	public int getSWUIPageIndex() {
		return SWUIPageIndex;
	}

	public void setSWUIPageIndex(int pageIndex) {
		SWUIPageIndex = pageIndex;
	}
	
	public IDiagramManager getDiagramManager() {
		return decisionTreeDiagramManager;
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
		return "com.tibco.cep.decision.treePropertyContributor";
	}

	public TSRoutingInputTailor getRoutingInputTailor() {
		return null;
	}

	public LayoutManager getLayoutManager() {
		if (this.decisionTreeDiagramManager != null) {
			return ((DecisionTreeDiagramManager) this.decisionTreeDiagramManager).getLayoutManager();
		} else {
			return null;
		}
	}

	public void setLayoutManager(LayoutManager mgr) {
		// TODO Auto-generated method stub

	}

	public void setDecisionTree(DecisionTree decisionTree) {
		this.decisionTree = decisionTree;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#dispose()
	 */
	@Override
	public void dispose() {
		try{
			if (decisionTreeDiagramManager != null) {
				decisionTreeDiagramManager.dispose();
				decisionTreeDiagramManager = null;
			}
			super.dispose();
			if(getSite()!= null){
			getSite().getPage().removePartListener(dtListener);
			
			dtListener = null;
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