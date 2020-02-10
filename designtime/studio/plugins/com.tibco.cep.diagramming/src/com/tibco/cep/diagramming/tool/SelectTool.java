package com.tibco.cep.diagramming.tool;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.dialog.SaveAsImageDialog;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tomsawyer.application.swing.export.TSEPrintPreviewWindow;
import com.tomsawyer.application.swing.export.TSEPrintSetupDialog;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSFindChildParent;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.complexity.TSEFoldingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSETextLabelUI;
import com.tomsawyer.interactive.command.editing.TSEAddNodeLabelCommand;
import com.tomsawyer.interactive.command.editing.TSECollapseCommand;
import com.tomsawyer.interactive.command.editing.TSEExpandCommand;
import com.tomsawyer.interactive.command.editing.TSEUnfoldCommand;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.tool.TSEWindowTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;

/**
 * 
 * @author ggrigore
 *
 */
@SuppressWarnings("all")
public class SelectTool extends TSESelectTool {

	public TSEObject hitObject;
	public IWorkbenchPage page;
	
	/**
	 * @return the page
	 */
	public IWorkbenchPage getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(IWorkbenchPage page) {
		this.page = page;
	}

	// TODO: a hack for now
	public static boolean wasCollapsed = true;
	public static SelectTool tool;

	/**
	 * This variable stores all menus created and shared by all instances of
	 * select tool.
	 */
	public static Hashtable menus;

	/**
	 * This variable stores the name of the active menu. It is null if no menu
	 * is active.
	 */
	public String activeMenuName;

	/**
	 * This variable stores the position in device coordinates that was hit when
	 * the popup was triggered.
	 */
	public Point clickPoint;

	/**
	 * This variable stores the graph that was hit when the popup was triggered
	 */
	public TSEGraph hitGraph;

	/**
	 * This variable stores the position in world coordinates that was hit when
	 * the popup was triggered.
	 */
	public TSConstPoint hitPosition;

	/**
	 * This constant specifies that an operation is to be performed on an node's
	 * parents.
	 */
	protected static final int PARENTS = 1;

	/**
	 * This constant specifies that an operation is to be performed on an node's
	 * children.
	 */
	protected static final int CHILDREN = 2;

	/**
	 * This constant specifies that an operation is to be performed on an node's
	 * neighbors.
	 */
	protected static final int NEIGHBORS = 3;
	/**
	* This method returns whether or not the input event has grid alignment enabled.
	*/
	public boolean isAlignmentEnabled(InputEvent event) {
		return false;
	}

	/**
	 * This variables stores the controller that listens and passes all popup
	 * menu actions to the appropriate instance of select tool
	 */
	protected static ContextMenuController popupMenuController;

	/**
	 * This method retrieves the popup menu according to the given menu name and
	 * shows it.
	 */
	public void showPopup(String menuName, Point point) {
		JPopupMenu menu = (JPopupMenu) menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}
	
	public void showPopup(JPopupMenu menu, Point point) {
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
	}

	/**
	 * This method hides the given popup menu and all its submenus.
	 */
	public void hidePopupMenu(JPopupMenu menu) {
		MenuElement[] subElements = menu.getSubElements();
		menu.setVisible(false);
		for (int i = 0; i < subElements.length; i++) {
			if (subElements[i] instanceof JMenu) {
				this.hidePopupMenu(((JMenu) subElements[i]).getPopupMenu());
			}
		}
	}

	/**
	 * This method sets the enabled or disabled state of the popup menu items.
	 * It is called before the popup menu is displayed.
	 */
	public void setPopupState(JPopupMenu popup) {
		for (int i = popup.getComponentCount() - 1; i >= 0; --i) {
			Component element = popup.getComponent(i);

			if (element instanceof JMenu) {
				this.setMenuState((JMenu) element);
			} else if (element instanceof JMenuItem) {
				SelectToolHandler.chooseState((JMenuItem) element, this.getGraph(), this);
			}
		}
	}

	/**
	 * This method sets the state of all items in the input popup menu based on
	 * their action commands.
	 */
	public void setMenuState(JMenu menu) {
		for (int i = menu.getMenuComponentCount() - 1; i >= 0; --i) {
			JMenuItem item = menu.getItem(i);

			// With Swing 1.1.1, iterating through the items of a
			// submenu appears to return the separators as well as the
			// menu items. So we skip the null items.

			if (item != null) {
				if ((item != menu) && (item instanceof JMenu)) {
					this.setMenuState((JMenu) item);
				} else {
					SelectToolHandler.chooseState(item, this.getGraph(), this);
				}
			}
		}
	}

	/**
	 * This method allows the user to edit the text of a node, a node label or
	 * an edge label. It does so by switching to the text editing tool.
	 */
	public void onEditText() {
		String activeMenu = this.getActiveMenu();
		if ((activeMenu == EntityResourceConstants.NODE_POPUP)
				|| (activeMenu == EntityResourceConstants.EDGE_LABEL_POPUP)
				|| (activeMenu == EntityResourceConstants.NODE_LABEL_POPUP)
				|| (activeMenu == EntityResourceConstants.CONNECTOR_LABEL_POPUP)) {
			this.editText((TSESolidObject) this.hitObject);
		}
	}

	public void onExpand() {
		TSEExpandCommand command = new TSEExpandCommand(this.getSwingCanvas(), (TSENode) this.hitObject);
		this.getSwingCanvas().getCommandManager().transmit(command);
	}

	/**
	 * This method collapses the selected object or the selected graph.
	 */
	public void onCollapse() {
		TSECollapseCommand command = null;
		if (this.hitObject != null) {
			command = new TSECollapseCommand(this.getSwingCanvas(),(TSENode) this.hitObject);
		} else {
			command = new TSECollapseCommand(this.getSwingCanvas(), (TSENode) this.hitGraph.getParent());
		}
		this.getSwingCanvas().getCommandManager().transmit(command);
	}

	/**
	 * This method sets the name of the currently shown menu.
	 */
	public void setActiveMenu(String name) {
		this.activeMenuName = name;
	}

	/**
	 * This method folds one level neighbors or children or parents of the
	 * selected node according to the <code>type</code> parameter.
	 */
	protected void onFoldOneLevel(int type) {
		this.foldNeighbors(type, 1);
	}

	/**
	 * This method folds N level neighbors or children or parents of the
	 * selected node according to the <code>type</code> parameter.
	 */
	protected void onFoldNLevel(int type, String message) {
		int depth = this.getDepth("Fold Levels:", message);
		if (depth > 0) {
			this.foldNeighbors(type, depth);
		}
	}

	/**
	 * This method folds all neighbors or children or parents of the selected
	 * node according to the <code>type</code> parameter.
	 */
	protected void onFoldAllLevel(int type) {
		this.foldNeighbors(type, TSFindChildParent.FIND_DEPTH_ALL);
	}

	/**
	 * This method folds neighbors, children or parents of selected node
	 * according to the <code>type</code> parameter and specified depth.
	 */
	void foldNeighbors(int type, int depth) {
		LinkedList neighborsToFold = new LinkedList();

		if (type == CHILDREN) {
			((TSENode) this.hitObject).findChildren(neighborsToFold, null, depth);
		} else if (type == PARENTS) {
			((TSENode) this.hitObject).findParents(neighborsToFold, null, depth);

		} else if (type == NEIGHBORS) {
			((TSENode) this.hitObject).findNeighbors(neighborsToFold, null, depth);
		}

		// if (!neighborsToFold.isEmpty())
		// {
		// this.provider.foldNodes((TSENode) this.hitObject);
		// TSEFoldCommand command =
		// new TSEFoldCommand(
		// this.getSwingCanvas(),
		// neighborsToFold,
		// this.inputData);
		// this.getSwingCanvas().getCommandManager().transmit(command);
		//
		// this.provider.incrementalLayout();
		// }
	}

	/**
	 * This method unfolds the selected node.
	 */
	public void onUnfold() {
		TSEUnfoldCommand command = new TSEUnfoldCommand(this.getSwingCanvas(), (TSENode) this.hitObject);
		this.getSwingCanvas().getCommandManager().transmit(command);
		// this.provider.incrementalLayout();
	}

	public void onUnfoldAll() {
		TSEFoldingManager foldingManager = (TSEFoldingManager) TSEFoldingManager.getManager(this.getGraphManager());
		foldingManager.unfoldAll();
		// this.provider.globalLayout();
	}

	/**
	 * This method adds a node label to the selected node. It can only be
	 * invoked on a node.
	 */
	public void onAddNodeLabel() {
		try {
			if (this.getActiveMenu() == EntityResourceConstants.NODE_POPUP) {
				TSETextLabelUI labelUI = new TSETextLabelUI();
				labelUI.setBorderDrawn(true);
				labelUI.setBorderColor(new TSEColor(0, 0, 255));
				labelUI.setFillColor(new TSEColor(224, 255, 239));
				labelUI.setTransparent(false);

				//TODO - the UI should be set through a builder instead.
				TSEAddNodeLabelCommand com = new TSEAddNodeLabelCommand((TSENode) this.hitObject, "bottom", 10, null);//labelUI);

				this.getSwingCanvas().getCommandManager().transmit(com);

				TSENodeLabel label = com.getNodeLabel();

				// select the newly created label and allow the user to edit its text

				if (label.isOwned()) {
					TSEEditTextTool tool = (TSEEditTextTool) TSEditingToolHelper.getEditTextTool(this.getToolManager());
					if (tool != null) {
						TSEWindowTool activeTool = (TSEWindowTool) this.getToolManager().getActiveTool();
						tool.setParentTool(activeTool);
						tool.setChangedGraphObject(label);
						activeTool.setToChildTool(tool);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method sets the given zoom level. The zoom level is expressed in
	 * percent, thus for example passing 134 means setting zoom level to 134%.
	 */
	public void onZoom(String command) {
		double level = 1.0;
		int dotIndex = command.indexOf('.');
		if (dotIndex > 0) {
			try {
				level = Double.valueOf(command.substring(dotIndex + 1)).doubleValue();
			} catch (NumberFormatException ignored) {
			}
		}
		this.getSwingCanvas().setZoomLevel(level / 100, true);
		// correlateButtonSelection(command, true);
	}
	
	/**
	 * This method returns the name of the currently shown menu.
	 */
	public String getActiveMenu() {
		return (this.activeMenuName);
	}

	/**
	 * This method asks the user for desired depth and returns the user's input.
	 */
	int getDepth(String message, String title) {
		String input = "";
		int depth = -1;

		while (depth <= 0) {
			try {
				input = JOptionPane.showInputDialog(this.getSwingCanvas(), message, title, JOptionPane.PLAIN_MESSAGE);

				// returns null if cancelled.

				if (input == null) {
					break;
				}

				depth = Integer.parseInt(input);

				if (depth <= 0) {
					JOptionPane.showMessageDialog(this.getSwingCanvas(), "Please enter a positive integer");
				}
			} catch (NumberFormatException e) {
				if (input == null) {
					depth = 0;
				} else {
					JOptionPane.showMessageDialog(this.getSwingCanvas(), "Please enter a positive integer");
				}
			}
		}

		return depth;
	}
	
	/**
	 * This method responds to the actions caused by the user selecting one of
	 * the menu items on a popup menu.
	 */
	public void actionPerformed(ActionEvent action) {
		String command = action.getActionCommand();

		if (command.startsWith(EntityResourceConstants.ADD_EDGE_LABEL)) {
			// this.onAddEdgeLabel(command);
		} else if (EntityResourceConstants.EXPAND.equals(command)) {
			this.onExpand();
		} else if (EntityResourceConstants.COLLAPSE.equals(command)) {
			this.onCollapse();
		} else if (EntityResourceConstants.FOLD_CHILDREN_ONE_LEVEL.equals(command)) {
			this.onFoldOneLevel(CHILDREN);
		} else if (EntityResourceConstants.FOLD_CHILDREN_N_LEVEL.equals(command)) {
			this.onFoldNLevel(CHILDREN, "Number of Children Levels to Fold");
		} else if (EntityResourceConstants.FOLD_CHILDREN_ALL_LEVEL.equals(command)) {
			this.onFoldAllLevel(CHILDREN);
		} else if (EntityResourceConstants.FOLD_PARENTS_ONE_LEVEL.equals(command)) {
			this.onFoldOneLevel(PARENTS);
		} else if (EntityResourceConstants.FOLD_PARENTS_N_LEVEL.equals(command)) {
			this.onFoldNLevel(PARENTS, "Number of Parent Levels to Fold");
		} else if (EntityResourceConstants.FOLD_PARENTS_ALL_LEVEL.equals(command)) {
			this.onFoldAllLevel(PARENTS);
		} else if (EntityResourceConstants.FOLD_NEIGHBORS_ONE_LEVEL.equals(command)) {
			this.onFoldOneLevel(NEIGHBORS);
		} else if (EntityResourceConstants.FOLD_NEIGHBORS_N_LEVEL.equals(command)) {
			this.onFoldNLevel(NEIGHBORS, "Number of Neighbor Levels to Fold");
		} else if (EntityResourceConstants.FOLD_NEIGHBORS_ALL_LEVEL.equals(command)) {
			this.onFoldAllLevel(NEIGHBORS);
		} else if (EntityResourceConstants.UNFOLD.equals(command)) {
			this.onUnfold();
		} else if (EntityResourceConstants.ADD_NODE_LABEL.equals(command)) {
			this.onAddNodeLabel();
		} else if (EntityResourceConstants.FIT_IN_WINDOW.equals(command)) {
			DiagramUtils.fitWindowAction(getPage());
		} else if (command.startsWith(EntityResourceConstants.ZOOM)) {
			this.onZoom(command);
		} else if (command.startsWith(EntityResourceConstants.EDIT_NODE)) {
			this.editNode();
		}
		// else if (TreeResourceConstants.FOLD_CUSTOM.equals(command)) {
		//			
		// int depth = this.getDepth(
		// "Visible levels from root:",
		// "Simplify Decision Tree");
		//
		// if (depth > 0)
		// {
		// this.provider.foldTree(depth);
		// }
		// }
		else if (EntityResourceConstants.ELEMENTDIAGRAM.equals(command)) {
			this.showSelectedItemsDiagram();
		}
		else if (EntityResourceConstants.EXPORT_IMAGE.equals(command)) {
//			ActionUtils.exportToImageAction(getPage());
//			TSESaveAsImageDialog saveAsImageDialog = new TSESaveAsImageDialog(
//					new JFrame("ts"), "Export Drawing to Image", this.getSwingCanvas());
			SaveAsImageDialog saveAsImageDialog = new SaveAsImageDialog(new JFrame("ts"), "Export Drawing to Image", this.getSwingCanvas());
			saveAsImageDialog.setGraphPath(System.getProperty("user.home") +
			File.separator +"Untitled1");
			saveAsImageDialog.setVisible(true);
		} else if (EntityResourceConstants.PRINT_SETUP.equals(command)) {
			TSEPrintSetupDialog printSetup = new TSEPrintSetupDialog(
					new JFrame("ts"), "Print Setup", this.getSwingCanvas());
			printSetup.setVisible(true);
		} else if (EntityResourceConstants.PRINT_PREVIEW.equals(command)) {
			JDialog printPreviewWindow = new TSEPrintPreviewWindow(
					new JFrame("ts"), "Print Preview", this.getSwingCanvas());
			printPreviewWindow.setSize(800, 600);
			printPreviewWindow.setVisible(true);
		} else if (EntityResourceConstants.UNFOLDALL.equals(command)) {
			this.onUnfoldAll();
		}else if (EntityResourceConstants.DELETE_SELECTED.equals(command)) {
			this.deleteDiagramComponents();
		} else if(EntityResourceConstants.CUT_GRAPH.equals(command)) {
			this.cutGraph();
		} else if(EntityResourceConstants.COPY_GRAPH.equals(command)) {
			this.copyGraph();
		} else if(EntityResourceConstants.PASTE_GRAPH.equals(command)) {
			this.pasteGraph();
		} else if(EntityResourceConstants.SHOW_DEPENDENCY_GRAPH.equals(command)) {
			this.showDependencyGraph();
		}

		// Finally, pass unhandled commands to TomahawkActions.
		else {
			// Tomahawk.getActions().actionPerformed(action);
		}
	}

	//Override
	protected void deleteDiagramComponents(){}
	
	//Override
	protected void cutGraph() { }
	
	//Override
	protected void copyGraph() {}
	
	//Override
	protected void pasteGraph() {}
	
	//Override
	protected void showDependencyGraph() {}
	
	//Override
	protected void showSelectedItemsDiagram() {}
	
	//Override
	protected void editNode() { }
	
	/**
	 * This method returns the popup menu controller.
	 */
	protected ContextMenuController getPopupMenuController() {
		if (SelectTool.popupMenuController == null) {
			SelectTool.popupMenuController = new ContextMenuController();
		}
		return (SelectTool.popupMenuController);
	}
	
	public static SelectTool getTool() {
		return SelectTool.tool;
	}
	
	//Override
	protected void setActiveDiagramTool(String command) {
		
	}
	
	public void dispose() {
	}

}