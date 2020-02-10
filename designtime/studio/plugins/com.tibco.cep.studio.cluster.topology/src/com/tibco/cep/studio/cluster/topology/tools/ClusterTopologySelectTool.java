package com.tibco.cep.studio.cluster.topology.tools;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.ui.ImageNodeUI;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.handler.ClusterTopologySelectToolHandler;
import com.tibco.cep.studio.cluster.topology.ui.MachineNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;

/**
 * @author hitesh
 *
 **/

public class ClusterTopologySelectTool extends SelectTool {
	
	private ClusterTopologyDiagramManager clusterDiagramManager;
	protected ClusterTopologyPopupMenuController clusterTopologyPopupMenuController;
	public static ClusterTopologySelectTool tool;
	public MachineNodeUI subprocessUI; 
	private ImageNodeUI imageNodeUI;
	/**
	 * This constructor creates a new tool
	 */
	
	public ClusterTopologySelectTool(ClusterTopologyDiagramManager clusterDiagramManager) {		
		this.clusterDiagramManager = clusterDiagramManager;
		this.page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		this.imageNodeUI = new ImageNodeUI(new TSEImage(this.getClass(),"/icons/host_48x48.png"));
		subprocessUI = new MachineNodeUI();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				getPopupMenuController();
			}
		});
		tool = this;
	}
	
	/**
	 * This variable stores all menus created and shared by all instances of
	 * select tool.
	 */
	public static Hashtable<String, JPopupMenu> menus;

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#showPopup(java.lang.String, java.awt.Point)
	 */
	@Override
	public void showPopup(String menuName, Point point) {
		JPopupMenu menu = (JPopupMenu) ClusterTopologySelectTool.menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		try{
			tool = this; //resetting Active Select Tool when mouse pressed.
			
			// if there is a popup trigger handle it, ignore it if not
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				ClusterTopologySelectToolHandler.popupTriggered(event, this.getGraph(), this);
			} else {
				TSEHitTesting hitTesting = this.getHitTesting();

				// get the point where the mouse is pressed.
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());
				TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());

				TSENode node = null;
				TSENodeUI nodeUI = null;
				TSENodeLabel nodeLabel = null;

				if (object instanceof TSENode) {
					node = (TSENode) object;
					nodeUI = (TSENodeUI) node.getNodeUI();
				} else if (object instanceof TSENodeLabel) {
					nodeLabel = (TSENodeLabel) object;
				}

				// if we double-clicked on a node, don't enable tag editing
				if (node != null && event.getClickCount() == 2) {
					return;
				}
				
				// if we click on a node label do nothing
				if (nodeLabel != null && event.getClickCount() >= 1) {
					return;
				}

				boolean clickedOnChildGraphMark = (node != null) && (node.getUI() != null)
				&& (((TSENodeUI) node.getNodeUI()).hasChildGraphMark())
				&& (((TSENodeUI) node.getNodeUI()).getChildGraphMarkBounds().contains(point));
				if (event.getClickCount() == 1 && clickedOnChildGraphMark ) {
					boolean clickedOnMark = nodeUI.getChildGraphMarkBounds().contains(point);
					if (clickedOnMark) {
						if (TSENestingManager.isCollapsed((TSENode)node)) {
							TSENestingManager.expand(node);
							node.discardAllLabels();
							node.setUI((TSEObjectUI)subprocessUI.clone());
						}
						else {
							TSENestingManager.collapse(node);
							TSENodeLabel label = (TSENodeLabel) node.addLabel();
							label.setDefaultOffset();
							((TSEAnnotatedUI) label.getUI()).setTextAntiAliasingEnabled(true);
							label.setName(node.getName().toString());
							node.setUI((TSEObjectUI)imageNodeUI.clone());
							node.setSize(48,48);
						}
						clusterDiagramManager.getLayoutManager().callBatchIncrementalLayout();
						
						refreshDiagram(clusterDiagramManager);
						refreshOverview(clusterDiagramManager.getEditor().getEditorSite(), true, true);
					}
					else {
						super.onMousePressed(event);
					}
				}

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
				ClusterTopologySelectToolHandler.chooseState((JMenuItem) element, this.getGraph(), this);
			}
		}
	}

	/**
	 * This method sets the state of all items in the input popup menu based on
	 * their action commands.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#setMenuState(javax.swing.JMenu)
	 */
	public void setMenuState(JMenu menu) {
		for (int i = menu.getMenuComponentCount() - 1; i >= 0; --i) {
			JMenuItem item = menu.getItem(i);

			// With Swing 1.1.1, iterating through the items of a submenu appears to return the separators as well as the
			// menu items. So we skip the null items.

			if (item != null) {
				if ((item != menu) && (item instanceof JMenu)) {
					this.setMenuState((JMenu) item);
				} else {
					ClusterTopologySelectToolHandler.chooseState(item, this.getGraph(), this);
				}
			}
		}
	}
	
	protected ContextMenuController getPopupMenuController() {
		if (this.clusterTopologyPopupMenuController == null) {
			this.clusterTopologyPopupMenuController = new ClusterTopologyPopupMenuController();
		}
		return (this.clusterTopologyPopupMenuController);
	}
	
	public static ClusterTopologySelectTool getTool() {
		return tool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#delete()
	 */
	@Override
	protected void deleteDiagramComponents(){	
	   editGraph(EDIT_TYPES.DELETE, clusterDiagramManager.getEditor().getEditorSite(), clusterDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#cut()
	 */
	protected void cutGraph(){
		clusterDiagramManager.setCutGraph(true);
		editGraph(EDIT_TYPES.CUT, clusterDiagramManager.getEditor().getEditorSite(), clusterDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#copy()
	 */
	protected void copyGraph(){
		clusterDiagramManager.setCopyGraph(true);
		editGraph(EDIT_TYPES.COPY, clusterDiagramManager.getEditor().getEditorSite(), clusterDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#paste()
	 */
	protected void pasteGraph(){
		clusterDiagramManager.setPasteGraph(true);
		editGraph(EDIT_TYPES.PASTE, clusterDiagramManager.getEditor().getEditorSite(), clusterDiagramManager);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPage()
	 */
	public IWorkbenchPage getPage() {
		return clusterDiagramManager.getEditor().getSite().getPage();
	}

	public ClusterTopologyDiagramManager getClusterTopologyDiagramManager() {
		return clusterDiagramManager;
	}
}
