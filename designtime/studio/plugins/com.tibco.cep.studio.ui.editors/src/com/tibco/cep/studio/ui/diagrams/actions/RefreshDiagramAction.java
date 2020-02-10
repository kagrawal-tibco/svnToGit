package com.tibco.cep.studio.ui.diagrams.actions;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.utils.Messages;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.project.ProjectDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
/**
 * 
 * @author sasahoo
 *
 */
public class RefreshDiagramAction  extends Action {

	private IWorkbenchPage page;

	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public RefreshDiagramAction( IWorkbenchPage page) {
		super("");
		this.page = page;
		this.setText(Messages.getString("refresh"));
		this.setToolTipText(Messages.getString("refresh.tooltip"));
		setImageDescriptor(EditorsUIPlugin.getDefault().getImageDescriptor("icons/diagram_refresh.png"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		
		if(page.getActiveEditor() instanceof  ProjectDiagramEditor){
			ProjectDiagramEditor editor = (ProjectDiagramEditor)page.getActiveEditor();
			ProjectDiagramManager manager = (ProjectDiagramManager)editor.getDiagramManager();
			manager.setRefresh(true);
			EditorUtils.refreshDiagramEditor(page, ((EntityDiagramEditorInput)editor.getEditorInput()).getFile());
		}
		
		if(page.getActiveEditor() instanceof  ConceptDiagramEditor){
			ConceptDiagramEditor editor = (ConceptDiagramEditor)page.getActiveEditor();
			EditorUtils.refreshDiagramEditor(page, ((EntityDiagramEditorInput)editor.getEditorInput()).getFile());
		}

		if(page.getActiveEditor() instanceof  EventDiagramEditor){
			EventDiagramEditor editor = (EventDiagramEditor)page.getActiveEditor();
			EditorUtils.refreshDiagramEditor(page, ((EntityDiagramEditorInput)editor.getEditorInput()).getFile());
		}
		
		refreshOverview(page.getActiveEditor().getEditorSite(), true, true);
		
	}
}
