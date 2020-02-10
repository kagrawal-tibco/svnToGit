package com.tibco.cep.studio.ui.diagrams.tools;

import java.awt.Point;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author hitesh
 */

public class ProjectSelectTool extends EntitySelectTool {

	public static ProjectSelectTool tool;
	protected ProjectDiagramPopupMenuController projectDiagramPopupMenuController;


	public ProjectSelectTool(DiagramManager diagramManager) {
		super(diagramManager);
	}

	/**
	 * This variable stores all menus created and shared by all instances of
	 * select tool.
	 */
	public static Hashtable menus;

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#showPopup(java.lang.String, java.awt.Point)
	 */
	@Override
	public void showPopup(String menuName, Point point) {
		JPopupMenu menu = (JPopupMenu) ProjectSelectTool.menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}

	//Opens the associated editor on double click
	protected void handleNodeDoubleClick(final TSENode node) {
		StudioUIUtils.invokeOnDisplayThread(new Runnable(){
			public void run(){
				StudioEditorDiagramUIUtils.openFormEditor(page, node, diagramManager);
			}
		}, false);
	}	

	protected ContextMenuController getPopupMenuController() {
		if (this.projectDiagramPopupMenuController == null) {
			this.projectDiagramPopupMenuController = new ProjectDiagramPopupMenuController();
		}
		return (this.projectDiagramPopupMenuController);
	}

	@Override
	protected void showDependencyGraph() {
		StudioUIUtils.invokeOnDisplayThread(new Runnable(){
			public void run(){
				StudioEditorDiagramUIUtils.showDependencyDiagram(page, diagramManager);
			}
		}, false);
	}
	
	protected void showSelectedItemsDiagram() {
		ProjectDiagramManager projectDiagramManager = ((ProjectDiagramManager) this.diagramManager);
		List<TSENode> selectedNodes = projectDiagramManager.getGraphManager().selectedNodes();
		if(selectedNodes.size() > 0){
			if (this.diagramManager instanceof ProjectDiagramManager) {
				projectDiagramManager.hideUnSelectedItems(this.diagramManager.getSelectedNodes());
				projectDiagramManager.layoutAndRefresh();
			}
		}
	}
}
