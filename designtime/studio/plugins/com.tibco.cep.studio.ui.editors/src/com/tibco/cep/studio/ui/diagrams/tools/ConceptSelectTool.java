package com.tibco.cep.studio.ui.diagrams.tools;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.ide.IDE;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditorInput;
import com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
/**
 * 
 * @author hitesh
 *
 */
public class ConceptSelectTool extends EntitySelectTool {

	private IProject project;
	private Object userObject;
	public static ConceptSelectTool tool;
	protected ConceptDiagramPopupMenuController conceptDiagramPopupMenuController;
	ConceptDiagramManager diagramManager;
	private boolean flag = true;

	public ConceptSelectTool(ConceptDiagramManager diagramManager) {
		super(diagramManager);
		this.diagramManager = diagramManager;
		tool = this;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#showPopup(java.lang.String, java.awt.Point)
	 */
	@Override
	public void showPopup(String menuName, Point point) {
		JPopupMenu menu = (JPopupMenu) ConceptSelectTool.menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}

	public void deleteViaKeyBoard() {
		this.deleteDiagramComponents();
	}

	protected ContextMenuController getPopupMenuController() {
		if (this.conceptDiagramPopupMenuController == null) {
			this.conceptDiagramPopupMenuController = new ConceptDiagramPopupMenuController();
		}
		return (this.conceptDiagramPopupMenuController);
	}

	protected void handleNodeDoubleClick(final TSENode node) {
	//	openConceptFormEditor(node);
		StudioUIUtils.invokeOnDisplayThread(new Runnable(){
			public void run(){
				StudioEditorDiagramUIUtils.openFormEditor(page, node, diagramManager);
			}
		}, false);
	}

	public void openConceptFormEditor(TSENode node) {
		try {
			userObject = node.getUserObject();
			EntityDiagramManager<Concept> entityDiagramManager = (EntityDiagramManager) diagramManager;
			project = entityDiagramManager.getProject();
			if (userObject instanceof EntityNodeData) {
				EntityNodeData nodeData = (EntityNodeData) userObject;
				Entity entity = (Entity) nodeData.getEntity().getUserObject();
				String path = entity.getFullPath();
				if (entity instanceof Concept) {
					openEditor(page, new ConceptFormEditorInput(project.getFile(path + ".concept"), (Concept) entity), ConceptFormEditor.ID);
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#editNode()
	 */
	@Override
	protected void editNode() {
		TSEObject object = SelectToolHandler.tseObject;
		if(object instanceof TSENode){
			TSENode selectedNode = (TSENode)object;
			openConceptFormEditor(selectedNode);
		}
	}
	/**
	 * This method sets the enabled or disabled state of the popup menu items.
	 * It is called before the popup menu is displayed.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#setPopupState(javax.swing.JPopupMenu)
	 */
	public void setPopupState(JPopupMenu popup) {
		for (int i = popup.getComponentCount() - 1; i >= 0; --i) {
			Component element = popup.getComponent(i);

			if (element instanceof JMenu) {
				this.setMenuState((JMenu) element);
			} else if (element instanceof JMenuItem) {
				ConceptDiagramSelectToolHandler.chooseState((JMenuItem) element, 
						                                  this.getGraph(), 
						                                  this, 
						                                  ((ConceptDiagramEditor)diagramManager.getEditor()).isEnabled());
			}
		}
	}

	protected void deleteDiagramComponents()  {
 		  editGraph(EDIT_TYPES.DELETE, diagramManager.getEditor().getEditorSite(), diagramManager);
	}

	public static ConceptSelectTool getTool() {
		return tool;
	}
}
