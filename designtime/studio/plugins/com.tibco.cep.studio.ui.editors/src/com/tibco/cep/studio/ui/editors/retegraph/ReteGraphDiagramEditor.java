package com.tibco.cep.studio.ui.editors.retegraph;

import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramEditorPartListener;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.diagrams.ReteGraphDiagramManager;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditor;

/**
 * 
 * @author ggrigore
 *
 */
public class ReteGraphDiagramEditor extends EntityDiagramEditor implements ITabbedPropertySheetPageContributor{
	
	public final static String ID = "com.tibco.cep.studio.retegraph.editors.ReteGraphDiagram";
	private EntityDiagramManager mgr;
	private ReteGraphDiagramPropertySheetPage propertySheetPage;
	
	private ReteGraphDiagramEditorPartListener projectDiagramEditorPartListener;
	
	public IDiagramManager getDiagramManager() {
		super.getDiagramManager();
		if (this.mgr == null) {
			this.mgr = new ReteGraphDiagramManager(this);
		}
		return this.mgr;
	}

	@Override
	public void dispose() {
		super.dispose();
		this.mgr.dispose();
	}
	
	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class)
			return new ReteGraphDiagramPropertySheetPage(this);
		return super.getAdapter(adapter);
	}

	/**
	 * Get the new property sheet page for this editor.
	 * 
	 * @return the new property sheet page.
	 */
	public TabbedPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null || propertySheetPage.getControl() == null) {
			propertySheetPage = new ReteGraphDiagramPropertySheetPage(this);
		}
		return propertySheetPage;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.EntityDiagramEditor#getEnityEditorpartListener()
	 */
	public EntityDiagramEditorPartListener getEnityEditorpartListener(){
		if(projectDiagramEditorPartListener ==  null){
			projectDiagramEditorPartListener = new ReteGraphDiagramEditorPartListener(this);
		}
		return projectDiagramEditorPartListener;
	}
	
	@Override
	public String getContributorId() {
		return "com.tibco.cep.studio.ui.editors.retegraph.diagram.propertyContributor";
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.IGraphDrawing#getPalette()
	 */
	@Override
	public PALETTE getPalette() {
		return PALETTE.PROJECT;
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.diagramming.diagram.perspective";
	}
}