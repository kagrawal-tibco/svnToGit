package com.tibco.cep.studio.ui.diagrams.tools;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;

import java.awt.Component;
import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.events.EventFormEditor;
import com.tibco.cep.studio.ui.editors.events.EventFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditor;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditorInput;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author hitesh
 *
 */
public class EventSelectTool extends EntitySelectTool {

	private IProject project;
	private Object userObject;
	public static EventSelectTool tool;
	protected EventDiagramPopupMenuController eventDiagramPopupMenuController;
	
	public EventSelectTool(DiagramManager diagramManager) {
		super(diagramManager);
		tool = this;
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
		JPopupMenu menu = (JPopupMenu) EventSelectTool.menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}
	
	
	
	public void setPopupState(JPopupMenu popup) {
		for (int i = popup.getComponentCount() - 1; i >= 0; --i) {
			Component element = popup.getComponent(i);

			if (element instanceof JMenu) {
				this.setMenuState((JMenu) element);
			} else if (element instanceof JMenuItem) {
				EventDiagramSelectToolHandler.chooseState((JMenuItem) element, 
						                                  this.getGraph(), 
						                                  this, 
						                                  ((EventDiagramEditor)diagramManager.getEditor()).isEnabled());
			}
		}
	}
	public void deleteViaKeyBoard() {
		this.deleteDiagramComponents();
	}
	
	protected ContextMenuController getPopupMenuController() {
		if (this.eventDiagramPopupMenuController == null) {
			this.eventDiagramPopupMenuController = new EventDiagramPopupMenuController();
		}
		return (this.eventDiagramPopupMenuController);
	}
	
	//Opens simple event or time event form editor
	protected void handleNodeDoubleClick(TSENode node) {
			openEventFormEditor(node);
	}
	
	public void openEventFormEditor(TSENode node){
		try {
			EntityDiagramManager<Concept> entityDiagramManager = (EntityDiagramManager) diagramManager;
			userObject = node.getUserObject();
			project = entityDiagramManager.getProject();
			if (userObject instanceof EntityNodeData) {
				EntityNodeData nodeData = (EntityNodeData) userObject;
				Entity entity = (Entity) nodeData.getEntity().getUserObject();
				String path = entity.getFullPath();
				if (entity instanceof SimpleEvent) {
					openEditor(page, new EventFormEditorInput(project.getFile(path + ".event"), (SimpleEvent) entity), EventFormEditor.ID);
				} else if (entity instanceof TimeEvent) {
					openEditor(page, new TimeEventFormEditorInput(project.getFile(path + "."+CommonIndexUtils.TIME_EXTENSION), (TimeEvent) entity), TimeEventFormEditor.ID);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static EventSelectTool getTool() {
		return tool;
	}

	
	
	
	protected void deleteDiagramComponents()  {
		  editGraph(EDIT_TYPES.DELETE, diagramManager.getEditor().getEditorSite(), diagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#editNode()
	 */
	@Override
	protected void editNode() {
		TSEObject object = SelectToolHandler.tseObject;
		if(object instanceof TSENode){
			TSENode selectedNode = (TSENode)object;
			openEventFormEditor(selectedNode);
		}
	}
}
