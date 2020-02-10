package com.tibco.cep.studio.ui.editors.project;

import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramEditorPartListener;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditor;

public class ProjectDiagramEditor extends EntityDiagramEditor<Entity, ProjectDiagramManager> implements ITabbedPropertySheetPageContributor{
	
	public final static String ID = "com.tibco.cep.studio.project.editors.ProjectDiagramEditor";
	
	private ProjectDiagramPropertySheetPage propertySheetPage;
	
	private ProjectDiagramEditorPartListener projectDiagramEditorPartListener;
	
	public IDiagramManager getDiagramManager() {
		if (entityDiagramManager == null) {
			entityDiagramManager = new ProjectDiagramManager(this);
		}
		return entityDiagramManager;
	}
	
	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class)
			return new ProjectDiagramPropertySheetPage(this);
		return super.getAdapter(adapter);
	}

	/**
	 * Get the new property sheet page for this editor.
	 * 
	 * @return the new property sheet page.
	 */
	public TabbedPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null || propertySheetPage.getControl() == null) {
			propertySheetPage = new ProjectDiagramPropertySheetPage(this);
		}
		return propertySheetPage;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.EntityDiagramEditor#getEnityEditorpartListener()
	 */
	public EntityDiagramEditorPartListener getEnityEditorpartListener(){
		if (projectDiagramEditorPartListener == null) {
			projectDiagramEditorPartListener = new ProjectDiagramEditorPartListener(this);
		}
		return projectDiagramEditorPartListener;
	}
	
	@Override
	public String getContributorId() {
		return "com.tibco.cep.studio.ui.editors.project.diagram.propertyContributor";
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