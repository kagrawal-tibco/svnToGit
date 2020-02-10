package com.tibco.cep.studio.ui.diagrams.tools;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.EventFormEditor;
import com.tibco.cep.studio.ui.editors.events.EventFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditor;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditorInput;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;

/**
 * @author ggrigore
 * 
 *         This class defines a tool for handling user specified actions. In
 *         this class, popup menus for nodes, edges, labels, and the graph are
 *         provided although commented out on top of the original selection tool
 *         handling mechanisms.
 */

public class EntitySelectTool extends SelectTool {

	protected DrawingCanvas drawingCanvas;
	protected DiagramManager diagramManager;

	private Cursor actionCursor;
	private Cursor defaultCursor;
	public IWorkbenchPage page;
	public static EntitySelectTool tool;
	private Object userObject;
	private IProject project;
	protected EntityPopupMenuController entityPopupMenuController;
	
	/**
	 * This variable stores all menus created and shared by all instances of
	 * select tool.
	 */
	public static Hashtable menus;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#showPopup(java.lang.String, java.awt.Point)
	 */
	public void showPopup(String menuName, Point point) {
		JPopupMenu menu = (JPopupMenu) EntitySelectTool.menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}

	/**
	 * This constructor creates a new tool
	 */
	
	public EntitySelectTool(DiagramManager diagramManager) {
		this.diagramManager = diagramManager;
		this.drawingCanvas = diagramManager.getDrawingCanvas();

		this.actionCursor = this.getActionCursor();
		this.defaultCursor = this.getDefaultCursor();

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				getPopupMenuController();
			}
		});
		this.page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		tool = this;
	}

//	// TODO: a hack for now
//	private static boolean wasCollapsed = true;
//
//	/**
//	 * This variable stores all menus created and shared by all instances of
//	 * select tool.
//	 */
//	static Hashtable menus;
//
//	/**
//	 * This variable stores the name of the active menu. It is null if no menu
//	 * is active.
//	 */
//	String activeMenuName;
//
//	/**
//	 * This variable stores the position in device coordinates that was hit when
//	 * the popup was triggered.
//	 */
//	Point clickPoint;
//
//	/**
//	 * This variable stores the graph that was hit when the popup was triggered
//	 */
//	TSEGraph hitGraph;
//
//	/**
//	 * This variable stores the position in world coordinates that was hit when
//	 * the popup was triggered.
//	 */
//	TSConstPoint hitPosition;
//
//	/**
//	 * This constant specifies that an operation is to be performed on an node's
//	 * parents.
//	 */
//	protected static final int PARENTS = 1;
//
//	/**
//	 * This constant specifies that an operation is to be performed on an node's
//	 * children.
//	 */
//	protected static final int CHILDREN = 2;
//
//	/**
//	 * This constant specifies that an operation is to be performed on an node's
//	 * neighbors.
//	 */
//	protected static final int NEIGHBORS = 3;
	
	@Override
	public void dispose() {
		super.dispose();
		this.diagramManager = null;
		this.drawingCanvas = null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPopupMenuController()
	 */
	protected ContextMenuController getPopupMenuController() {
		if (this.entityPopupMenuController == null) {
			this.entityPopupMenuController = new EntityPopupMenuController();
		}
		return (this.entityPopupMenuController);
	}

	/**
	 * This method responds to the mouse button being pressed in the client area
	 * of the swing canvas.
	 */
	public void onMousePressed(MouseEvent event) {
		try{
			tool = this; //resetting Active Select Tool when mouse pressed.
			
			// if there is a popup trigger handle it, ignore it if not
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				if(getToolManager().getActiveTool() instanceof SelectTool) {
					SelectToolHandler.popupTriggered(event, this.getGraph(), this);
				} else {
					//this.getToolManager().getActiveTool().finalizeTool();
					this.getToolManager().setActiveTool(this);
				}
			} else {
				TSEHitTesting hitTesting = this.getHitTesting();

				// get the point where the mouse is pressed.
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());
				TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());
				//To open BPMN Editor if user clicks on a bpmn process
				TSEGraph graph = hitTesting.getGraphAt(point, this.getGraph());
				if(graph != null) {
					Object tsProcess = graph.getUserObject();
					if(tsProcess instanceof DynamicEObjectImpl && event.getClickCount() == 2)	{
						DynamicEObjectImpl tsProcessObject = (DynamicEObjectImpl) tsProcess;
					    if(tsProcessObject.eClass() instanceof EClassImpl && tsProcessObject.eClass() != null ) {
					    	try {
					    		String folder = (String)tsProcessObject.eGet(tsProcessObject.eClass().getEStructuralFeature("folder"),true);
					    		String name = (String)tsProcessObject.eGet(tsProcessObject.eClass().getEStructuralFeature("name"),true);
					    		String processPath =folder+IPath.SEPARATOR+name+"."+"beprocess";
					    		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(((EntityDiagramManager<Entity>)diagramManager).getProject().getName()).getFile(processPath);
					    		openEditor(page, file);
					    		return;
					    	} catch(Exception e) {
					    		return;
					    	}
					    }
					} else {
						
					}
				}
				TSENode node = null;
				TSENodeUI nodeUI = null;

				if (object instanceof TSENode) {
					node = (TSENode) object;
					nodeUI = (TSENodeUI) node.getNodeUI();
				}

				// if we double-clicked on a node
				if (node != null && event.getClickCount() == 2) {
					
					this.handleNodeDoubleClick(node);
				}

				if (node != null && event.isControlDown()) {
					this.showNodeData(node);
				}

				if (event.getClickCount() == 2 && node == null) {
					this.handleGraphDoubleClick();
				}

				boolean clickedOnChildGraphMark = (node != null) && (node.getUI() != null)
						&& (((TSENodeUI) node.getNodeUI()).hasChildGraphMark())
						&& (((TSENodeUI) node.getNodeUI()).getChildGraphMarkBounds().contains(point));

				if (event.getClickCount() == 2
						&& !clickedOnChildGraphMark
						&& node != null
						&& node.isFolderNode()
						&& nodeUI != null
						&& !(nodeUI.hasHideMark() && nodeUI.getHideMarkBounds().contains(point))) {
					this.hitObject = node;
				} else {
					// Disable node tag editing for now.
					super.onMousePressed(event);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			// / this.drawingManager.enableMenuItems(DrawingManager.ANY_OPERATION);
	}
	
	private void showNodeData(TSENode node) {
		
	}
	
	protected void handleGraphDoubleClick() {
		// System.out.println("Changing collapsed/expanded state of all nodes.");
//		List<TSENode> list = new LinkedList<TSENode>();
//		Iterator iter = this.drawingCanvas.getGraphManager().getMainDisplayGraph().nodes().iterator();
//		TSENode n;
//		while (iter.hasNext()) {
//			n = (TSENode) iter.next();
//			if (n.getUserObject() instanceof EntityNodeData) {
//				EntityNodeData nodeData = (EntityNodeData) n.getUserObject();
//				nodeData.setCollapsed(!this.wasCollapsed);
//				list.add(n);
//			}
//		}
//		wasCollapsed = !wasCollapsed;
//		EntityNodeData.calculateConceptNodeSizes(list, this.getSwingCanvas());
		/*
		// this.drawingCanvas.callBatchIncrementalLayout();
		// this.getSwingCanvas().centerPointInCanvas(node.getCenter(), true);
		this.diagramManager.getLayoutManager().callBatchGlobalLayout();
		this.getSwingCanvas().drawGraph();
		this.getSwingCanvas().repaint();
		*/
//		this.callIncrementalLayout();
	}
	
//	protected void callIncrementalLayout() {
//		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
//        TSInteractivePreferenceTailor intrTailor = new TSInteractivePreferenceTailor(prefData);
//        boolean keepFocus = intrTailor.getLayoutPreserveFocus();
//        if (!keepFocus) {
//        	intrTailor.setLayoutPreserveFocus(true);
//        }
//		// this.diagramManager.getLayoutManager().callBatchIncrementalLayout();
//		this.diagramManager.getLayoutManager().callInteractiveIncrementalLayout();
//		this.diagramManager.postPopulateDrawingCanvas();
//		if (!keepFocus) {
//        	intrTailor.setLayoutPreserveFocus(false);
//		}
//	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#delete()
	 */
	protected void deleteDiagramComponents(){
		editGraph(EDIT_TYPES.DELETE, ((EntityDiagramManager<Entity>)diagramManager).getEditor().getEditorSite(), ((EntityDiagramManager<Entity>)diagramManager));
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#cut()
	 */
	protected void cutGraph(){
		editGraph(EDIT_TYPES.CUT, ((EntityDiagramManager<Entity>)diagramManager).getEditor().getEditorSite(), ((EntityDiagramManager<Entity>)diagramManager));
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#copy()
	 */
	protected void copyGraph(){
		editGraph(EDIT_TYPES.COPY, ((EntityDiagramManager<Entity>)diagramManager).getEditor().getEditorSite(), ((EntityDiagramManager<Entity>)diagramManager));
	}

	/*
	 * public void onMouseMoved(MouseEvent event) { TSEHitTesting hitTesting =
	 * this.getHitTesting();
	 * 
	 * // get the point where the mouse is pressed. TSConstPoint point =
	 * this.getNonalignedWorldPoint(event);
	 * 
	 * TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(
	 * this.getSwingCanvas().getPreferenceData());
	 * 
	 * TSEObject object = hitTesting.getGraphObjectAt( point, this.getGraph(),
	 * tailor.isNestedGraphInteractionEnabled());
	 * 
	 * TSENode node = null;
	 * 
	 * if (object instanceof TSENode) { node = (TSENode) object; }
	 * 
	 * if (node != null && event.isControlDown()) { // add component here? //
	 * System.out.println("just moved over node: " + node.getTag()); } }
	 */
	
	/**
	 * This method responds to the mouse button being released in the client
	 * area of the swing canvas.
	 */
	public void onMouseReleased(MouseEvent event) {
		// if there is a popup trigger handle it, ignore it if not

		if (event.isPopupTrigger()) {
			// this.popupTriggered(event);
		} else {
			super.onMouseReleased(event);
		}

		// / this.drawingManager.enableMenuItems(DrawingManager.ANY_OPERATION);
	}
	
	/**
	 * This method resets the tool. It must be called before performing
	 * user-specific actions.
	 */
	public void resetTool() {
		// throw away the hit object.
		this.hitObject = null;

		this.restoreCursors();

		// hide all menus if any are visible, etc...

		// call resetTool of the super class
		super.resetTool();

		// / this.drawingManager.enableMenuItems(DrawingManager.ANY_OPERATION);
	}

	public void showBusyCursor() {
		this.setActionCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setDefaultCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void restoreCursors() {
		this.setActionCursor(this.actionCursor);
		this.setDefaultCursor(this.defaultCursor);
	}

	public void editText(TSESolidObject object) {
		// we disable node tag editing
		// super.editText(object);
	}

	/**
	 * This method should be called to nullify the tool's reference to the old
	 * graph when a new graph is created or opened.
	 */
	public void nullifyReferences() {
		this.hitObject = null;
	}
	
	protected void handleNodeDoubleClick(TSENode node) {
		try {
			userObject = node.getUserObject();
			EntityDiagramManager entityDiagramManager = (EntityDiagramManager) diagramManager;
			project = entityDiagramManager.getProject();

			if (userObject instanceof EntityNodeData) {
				EntityNodeData nodeData = (EntityNodeData) userObject;
				Entity entity = (Entity) nodeData.getEntity().getUserObject();
				String path = entity.getFullPath();
				if (entity instanceof Concept) {
					openEditor(page, new ConceptFormEditorInput(project.getFile(path + ".concept"), (Concept) entity), ConceptFormEditor.ID);
				} else if (entity instanceof SimpleEvent) {
					openEditor(page, new EventFormEditorInput(project.getFile(path + ".event"), (SimpleEvent) entity), EventFormEditor.ID);
				} else if (entity instanceof TimeEvent) {
					openEditor(page, new TimeEventFormEditorInput(project.getFile(path + "."+CommonIndexUtils.TIME_EXTENSION), (TimeEvent) entity), TimeEventFormEditor.ID);
				}
			}
			
//			if (userObject instanceof EntityNodeData) {
//				EntityNodeData nodeData = (EntityNodeData) userObject;
////				Entity entity = (Entity) nodeData.getEntity().getUserObject();
////				String path = entity.getFullPath();
//
//				if (nodeData.isCollapsed()) {
//					// / ExpandRuleNodeCommand command = new ExpandRuleNodeCommand(drawingManager, node);
//					//this.getSwingCanvas().getCommandManager().transmit(command);
//				} else {
//					// / CollapseRuleNodeCommand command = new CollapseRuleNodeCommand(drawingManager, node);
//					//this.getSwingCanvas().getCommandManager().transmit(command);
//				}
//
//				// USE this instead of the above if/else if you do not want the operation to be undoable:
//				nodeData.setCollapsed(!nodeData.isCollapsed());
//				LinkedList<TSENode> list = new LinkedList<TSENode>();
//				list.add(node);
//				EntityNodeData.calculateConceptNodeSizes(list, this.getSwingCanvas());
//				this.callIncrementalLayout();
//				// this.getSwingCanvas().centerPointInCanvas(node.getCenter(), true);				
//				// this.getSwingCanvas().repaint();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
//	/**
//	 * This method responds to the actions caused by the user selecting one of
//	 * the menu items on a popup menu.
//	 */
//	public void actionPerformed(ActionEvent action) {
//		String command = action.getActionCommand();
//
//		if (command.startsWith(EntityResourceConstants.ADD_EDGE_LABEL)) {
//			// this.onAddEdgeLabel(command);
//		} else if (EntityResourceConstants.EXPAND.equals(command)) {
//			this.onExpand();
//		} else if (EntityResourceConstants.COLLAPSE.equals(command)) {
//			this.onCollapse();
//		} else if (EntityResourceConstants.FOLD_CHILDREN_ONE_LEVEL
//				.equals(command)) {
//			this.onFoldOneLevel(CHILDREN);
//		} else if (EntityResourceConstants.FOLD_CHILDREN_N_LEVEL
//				.equals(command)) {
//			this.onFoldNLevel(CHILDREN, "Number of Children Levels to Fold");
//		} else if (EntityResourceConstants.FOLD_CHILDREN_ALL_LEVEL
//				.equals(command)) {
//			this.onFoldAllLevel(CHILDREN);
//		} else if (EntityResourceConstants.FOLD_PARENTS_ONE_LEVEL
//				.equals(command)) {
//			this.onFoldOneLevel(PARENTS);
//		} else if (EntityResourceConstants.FOLD_PARENTS_N_LEVEL.equals(command)) {
//			this.onFoldNLevel(PARENTS, "Number of Parent Levels to Fold");
//		} else if (EntityResourceConstants.FOLD_PARENTS_ALL_LEVEL
//				.equals(command)) {
//			this.onFoldAllLevel(PARENTS);
//		} else if (EntityResourceConstants.FOLD_NEIGHBORS_ONE_LEVEL
//				.equals(command)) {
//			this.onFoldOneLevel(NEIGHBORS);
//		} else if (EntityResourceConstants.FOLD_NEIGHBORS_N_LEVEL
//				.equals(command)) {
//			this.onFoldNLevel(NEIGHBORS, "Number of Neighbor Levels to Fold");
//		} else if (EntityResourceConstants.FOLD_NEIGHBORS_ALL_LEVEL
//				.equals(command)) {
//			this.onFoldAllLevel(NEIGHBORS);
//		} else if (EntityResourceConstants.UNFOLD.equals(command)) {
//			this.onUnfold();
//		} else if (EntityResourceConstants.ADD_NODE_LABEL.equals(command)) {
//
//			this.onAddNodeLabel();
//		} else if (command.startsWith(EntityResourceConstants.ZOOM)) {
//			this.onZoom(command);
//		}
//		// else if (TreeResourceConstants.FOLD_CUSTOM.equals(command)) {
//		//			
//		// int depth = this.getDepth(
//		// "Visible levels from root:",
//		// "Simplify Decision Tree");
//		//
//		// if (depth > 0)
//		// {
//		// this.provider.foldTree(depth);
//		// }
//		// }
//		else if (EntityResourceConstants.EXPORT_IMAGE.equals(command)) {
//			TSESaveAsImageDialog saveAsImageDialog = new TSESaveAsImageDialog(
//					new JFrame("ts"), "Export Drawing to Image", this
//							.getSwingCanvas());
//			saveAsImageDialog.setVisible(true);
//		} else if (EntityResourceConstants.PRINT_SETUP.equals(command)) {
//			TSEPrintSetupDialog printSetup = new TSEPrintSetupDialog(
//					new JFrame("ts"), "Print Setup", this.getSwingCanvas());
//			printSetup.setVisible(true);
//		} else if (EntityResourceConstants.PRINT_PREVIEW.equals(command)) {
//			JDialog printPreviewWindow = new TSEPrintPreviewWindow(new JFrame(
//					"ts"), "Print Preview", this.getSwingCanvas());
//			printPreviewWindow.setSize(800, 600);
//			printPreviewWindow.setVisible(true);
//		} else if (EntityResourceConstants.UNFOLDALL.equals(command)) {
//			this.onUnfoldAll();
//		}
//
//		// Finally, pass unhandled commands to TomahawkActions.
//		else {
//			// Tomahawk.getActions().actionPerformed(action);
//		}
//	}
//
//	/**
//	 * This method returns the popup menu controller.
//	 */
//	PopupMenuController getPopupMenuController() {
//		if (ProjectSelectTool.popupMenuController == null) {
//			ProjectSelectTool.popupMenuController = new PopupMenuController();
//		}
//
//		return (ProjectSelectTool.popupMenuController);
//	}

	public static EntitySelectTool getTool() {
		return tool;
	}
//
//	/**
//	 * This variables stores the controller that listens and passes all popup
//	 * menu actions to the appropriate instance of select tool
//	 */
//	static PopupMenuController popupMenuController;
//
//	/**
//	 * This method retrieves the popup menu according to the given menu name and
//	 * shows it.
//	 */
//	public void showPopup(String menuName, Point point) {
//		JPopupMenu menu = (JPopupMenu) ProjectSelectTool.menus.get(menuName);
//		if (menu != null) {
//			this.setPopupState(menu);
//			menu.show(this.getSwingCanvas(), point.x, point.y);
//		}
//		this.setActiveMenu(menuName);
//	}
//
//	/**
//	 * This method hides the given popup menu and all its submenus.
//	 */
//	public void hidePopupMenu(JPopupMenu menu) {
//		MenuElement[] subElements = menu.getSubElements();
//		menu.setVisible(false);
//		for (int i = 0; i < subElements.length; i++) {
//			if (subElements[i] instanceof JMenu) {
//				this.hidePopupMenu(((JMenu) subElements[i]).getPopupMenu());
//			}
//		}
//	}
//
//	/**
//	 * This method sets the enabled or disabled state of the popup menu items.
//	 * It is called before the popup menu is displayed.
//	 */
//	public void setPopupState(JPopupMenu popup) {
//		for (int i = popup.getComponentCount() - 1; i >= 0; --i) {
//			Component element = popup.getComponent(i);
//
//			if (element instanceof JMenu) {
//				this.setMenuState((JMenu) element);
//			} else if (element instanceof JMenuItem) {
//				SelectToolHandler.chooseState((JMenuItem) element, this
//						.getGraph(), this);
//			}
//		}
//	}
//
//	/**
//	 * This method sets the state of all items in the input popup menu based on
//	 * their action commands.
//	 */
//	public void setMenuState(JMenu menu) {
//		for (int i = menu.getMenuComponentCount() - 1; i >= 0; --i) {
//			JMenuItem item = menu.getItem(i);
//
//			// With Swing 1.1.1, iterating through the items of a
//			// submenu appears to return the separators as well as the
//			// menu items. So we skip the null items.
//
//			if (item != null) {
//				if ((item != menu) && (item instanceof JMenu)) {
//					this.setMenuState((JMenu) item);
//				} else {
//					SelectToolHandler.chooseState(item, this.getGraph(), this);
//				}
//			}
//		}
//	}

//	/**
//	 * This method allows the user to edit the text of a node, a node label or
//	 * an edge label. It does so by switching to the text editing tool.
//	 */
//	public void onEditText() {
//		String activeMenu = this.getActiveMenu();
//
//		if ((activeMenu == EntityResourceConstants.NODE_POPUP)
//				|| (activeMenu == EntityResourceConstants.EDGE_LABEL_POPUP)
//				|| (activeMenu == EntityResourceConstants.NODE_LABEL_POPUP)
//				|| (activeMenu == EntityResourceConstants.CONNECTOR_LABEL_POPUP)) {
//			this.editText((TSESolidObject) this.hitObject);
//		}
//	}
//
//	public void onExpand() {
//		TSEExpandCommand command = new TSEExpandCommand(this.getSwingCanvas(),
//				(TSENode) this.hitObject);
//
//		this.getSwingCanvas().getCommandManager().transmit(command);
//	}
//
//	/**
//	 * This method collapses the selected object or the selected graph.
//	 */
//	public void onCollapse() {
//		TSECollapseCommand command = null;
//
//		if (this.hitObject != null) {
//			command = new TSECollapseCommand(this.getSwingCanvas(),
//					(TSENode) this.hitObject);
//		} else {
//			command = new TSECollapseCommand(this.getSwingCanvas(),
//					(TSENode) this.hitGraph.getParent());
//		}
//
//		this.getSwingCanvas().getCommandManager().transmit(command);
//	}

//	/**
//	 * This method sets the name of the currently shown menu.
//	 */
//	public void setActiveMenu(String name) {
//		this.activeMenuName = name;
//	}
//
//	/**
//	 * This method folds one level neighbors or children or parents of the
//	 * selected node according to the <code>type</code> parameter.
//	 */
//	protected void onFoldOneLevel(int type) {
//		this.foldNeighbors(type, 1);
//	}
//
//	/**
//	 * This method folds N level neighbors or children or parents of the
//	 * selected node according to the <code>type</code> parameter.
//	 */
//	protected void onFoldNLevel(int type, String message) {
//		int depth = this.getDepth("Fold Levels:", message);
//
//		if (depth > 0) {
//			this.foldNeighbors(type, depth);
//		}
//	}
//
//	/**
//	 * This method folds all neighbors or children or parents of the selected
//	 * node according to the <code>type</code> parameter.
//	 */
//	protected void onFoldAllLevel(int type) {
//		this.foldNeighbors(type, TSFindChildParent.FIND_DEPTH_ALL);
//	}
//
//	/**
//	 * This method folds neighbors, children or parents of selected node
//	 * according to the <code>type</code> parameter and specified depth.
//	 */
//	void foldNeighbors(int type, int depth) {
//		LinkedList neighborsToFold = new LinkedList();
//
//		if (type == CHILDREN) {
//			((TSENode) this.hitObject).findChildren(neighborsToFold, null,
//					depth);
//		} else if (type == PARENTS) {
//			((TSENode) this.hitObject)
//					.findParents(neighborsToFold, null, depth);
//
//		} else if (type == NEIGHBORS) {
//			((TSENode) this.hitObject).findNeighbors(neighborsToFold, null,
//					depth);
//		}
//
//		// if (!neighborsToFold.isEmpty())
//		// {
//		// this.provider.foldNodes((TSENode) this.hitObject);
//		// TSEFoldCommand command =
//		// new TSEFoldCommand(
//		// this.getSwingCanvas(),
//		// neighborsToFold,
//		// this.inputData);
//		// this.getSwingCanvas().getCommandManager().transmit(command);
//		//
//		// this.provider.incrementalLayout();
//		// }
//	}
//
//	/**
//	 * This method unfolds the selected node.
//	 */
//	public void onUnfold() {
//		TSEUnfoldCommand command = new TSEUnfoldCommand(this.getSwingCanvas(),
//				(TSENode) this.hitObject);
//
//		this.getSwingCanvas().getCommandManager().transmit(command);
//
//		// this.provider.incrementalLayout();
//	}
//
//	public void onUnfoldAll() {
//		TSEFoldingManager foldingManager = (TSEFoldingManager) TSEFoldingManager
//				.getManager(this.getGraphManager());
//		foldingManager.unfoldAll();
//		// this.provider.globalLayout();
//	}
//
//	/**
//	 * This method adds a node label to the selected node. It can only be
//	 * invoked on a node.
//	 */
//	public void onAddNodeLabel() {
//		try {
//			if (this.getActiveMenu() == EntityResourceConstants.NODE_POPUP) {
//				TSETextLabelUI labelUI = new TSETextLabelUI();
//				labelUI.setBorderDrawn(true);
//				labelUI.setBorderColor(new TSEColor(0, 0, 255));
//				labelUI.setFillColor(new TSEColor(224, 255, 239));
//				labelUI.setTransparent(false);
//
//				TSEAddNodeLabelCommand com = new TSEAddNodeLabelCommand(
//						(TSENode) this.hitObject, "bottom", 10, null, labelUI /*
//																			 * this.
//																			 * getSwingCanvas
//																			 * (
//																			 * )
//																			 * .
//																			 * getToolManager
//																			 * (
//																			 * )
//																			 * .
//																			 * getCurrentLabelUI
//																			 * (
//																			 * )
//																			 */);
//
//				this.getSwingCanvas().getCommandManager().transmit(com);
//
//				TSENodeLabel label = com.getNodeLabel();
//
//				// select the newly created label and allow the user to
//				// edit its text
//
//				if (label.isOwned()) {
//					TSEEditTextTool tool = (TSEEditTextTool) TSEditingToolHelper
//							.getEditTextTool(this.getToolManager());
//
//					if (tool != null) {
//						TSEWindowTool activeTool = (TSEWindowTool) this
//								.getToolManager().getActiveTool();
//						tool.setParentTool(activeTool);
//						tool.setChangedGraphObject(label);
//
//						activeTool.setTool(tool);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method sets the given zoom level. The zoom level is expressed in
//	 * percent, thus for example passing 134 means setting zoom level to 134%.
//	 */
//	public void onZoom(String command) {
//		double level = 1.0;
//		int dotIndex = command.indexOf('.');
//
//		if (dotIndex > 0) {
//			try {
//				level = Double.valueOf(command.substring(dotIndex + 1))
//						.doubleValue();
//			} catch (NumberFormatException ignored) {
//			}
//		}
//
//		this.getSwingCanvas().setZoomLevel(level / 100, true);
//
//		// correlateButtonSelection(command, true);
//	}
//
//	/**
//	 * This method returns the name of the currently shown menu.
//	 */
//	public String getActiveMenu() {
//		return (this.activeMenuName);
//	}
//
//	/**
//	 * This method asks the user for desired depth and returns the user's input.
//	 */
//	int getDepth(String message, String title) {
//		String input = "";
//		int depth = -1;
//
//		while (depth <= 0) {
//			try {
//				input = JOptionPane.showInputDialog(this.getSwingCanvas(),
//						message, title, JOptionPane.PLAIN_MESSAGE);
//
//				// returns null if cancelled.
//
//				if (input == null) {
//					break;
//				}
//
//				depth = Integer.parseInt(input);
//
//				if (depth <= 0) {
//					JOptionPane.showMessageDialog(this.getSwingCanvas(),
//							"Please enter a positive integer");
//				}
//			} catch (NumberFormatException e) {
//				if (input == null) {
//					depth = 0;
//				} else {
//					JOptionPane.showMessageDialog(this.getSwingCanvas(),
//							"Please enter a positive integer");
//				}
//			}
//		}
//
//		return depth;
//	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPage()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IWorkbenchPage getPage() {
		return ((EntityDiagramManager<Entity>)diagramManager).getEditor().getSite().getPage();
	}
	
}
