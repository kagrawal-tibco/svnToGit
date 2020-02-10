/**
 * 
 */
package com.tibco.cep.studio.ui.diagrams.tools;

import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * @author hitesh
 *
 */
public class DependencySelectTool extends EntitySelectTool {

	public static DependencySelectTool tool;
	protected DependencyDiagramPopupMenuController dependencyDiagramPopupMenuController;
	
	public DependencySelectTool(DiagramManager diagramManager) {
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
		JPopupMenu menu = (JPopupMenu) DependencySelectTool.menus.get(menuName);
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
		if (this.dependencyDiagramPopupMenuController == null) {
			this.dependencyDiagramPopupMenuController = new DependencyDiagramPopupMenuController();
		}
		return (this.dependencyDiagramPopupMenuController);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#editNode()
	 */
	@Override
	protected void editNode() {
		StudioUIUtils.invokeOnDisplayThread(new Runnable(){
			public void run(){
				TSEObject object = SelectToolHandler.tseObject;
				if(object instanceof TSENode){
					TSENode selectedNode = (TSENode)object;
					StudioEditorDiagramUIUtils.openFormEditor(page, selectedNode, diagramManager);
				}
			}
		}, false);
	}

}
