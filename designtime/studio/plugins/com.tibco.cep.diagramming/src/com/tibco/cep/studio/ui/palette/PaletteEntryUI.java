package com.tibco.cep.studio.ui.palette;

import javax.swing.SwingUtilities;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.builder.TSObjectBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.TSObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;

/**
 * This abstract class provides the skeleton of Diagram palette Entry items
 * 
 * @author sasahoo, hitesh
 * 
 */
public abstract class PaletteEntryUI {

	public enum Tool {
		NONE,
		SELECTION
	}
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	protected abstract PaletteDrawer createNodeGroup(final IWorkbenchPage page,
			Palette palette);


	public PaletteEntry createPaletteEntry(final IWorkbenchPage page,
			PaletteDrawer drawer, String id, final String title, String tooltip,
			String image, final TSObjectBuilder objectBuilder,
			final TSObjectUI objectUI, final boolean isEdge, final Tool tool, boolean custom) {
		return this.createPaletteEntry(page, drawer, id, title, null, tooltip, image, objectBuilder, objectUI, isEdge, tool, custom);
	}
	
	/**
	 * 
	 * @param page
	 * @param drawer
	 * @param id
	 * @param title
	 * @param tooltip
	 * @param image
	 * @param isEdge
	 * @param tool
	 * @param custom TODO
	 * @param nodeCreator
	 * @param nodeUI
	 * @return
	 */
	public PaletteEntry createPaletteEntry(final IWorkbenchPage page,
			PaletteDrawer drawer, String id, final String title, final String label, String tooltip,
			String image, final TSObjectBuilder objectBuilder,
			final TSObjectUI objectUI, final boolean isEdge, final Tool tool, boolean custom){
		
		return new PaletteEntry(id, title, label, tooltip, image, drawer, custom) {

			@Override
			protected void stateChanged() {
				if ((tool == Tool.NONE) &&
					((objectBuilder == null) || (objectUI == null))) {
					return;
				}
				IEditorPart activeEditorPart = page.getActiveEditor();
				IGraphDrawing editor = null;;
				if (activeEditorPart instanceof IGraphDrawing) {
					editor = (IGraphDrawing) activeEditorPart;
				}
				if (editor != null) {
					Object diagramManager = editor.getDiagramManager();
					
					((DiagramManager) diagramManager).setCopyGraph(false);
					((DiagramManager) diagramManager).setCutGraph(false);
					((DiagramManager) diagramManager).setPasteGraph(false);
					
					//For Event Model diagram view
					if (getState() == STATE_SELECTED) {
						((DiagramManager) diagramManager).setSelectedPaletteEntry(title);
					}
					
					DrawingCanvas canvas = (DrawingCanvas)(((DiagramManager) diagramManager).getDrawingCanvas());
					final TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
					final TSEGraphManager graphManager = ((DiagramManager) diagramManager).getGraphManager();
					
					if (getState() == STATE_NOT_SELECTED) {
						toolManager.setActiveTool(null);
						graphManager.setNodeBuilder(null);
						updatePaletteItem(getControl());
						return;
					}
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							if (isEdge) {
								TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
								graphManager.setEdgeBuilder((TSEdgeBuilder) objectBuilder);
								((TSEEdgeUI)objectUI).setAntiAliasingEnabled(true);
								graphManager.getEdgeBuilder().setEdgeUI((TSEdgeUI) objectUI);
								toolManager.setActiveTool(createEdgeTool);
							}
							else if (objectBuilder == null && objectUI == null) {
								TSESelectTool selectTool = TSViewingToolHelper.getSelectTool(toolManager);
								toolManager.setActiveTool(selectTool);
							}
							else {
								TSECreateNodeTool createTool = TSEditingToolHelper.getCreateNodeTool(toolManager);
								graphManager.setNodeBuilder((TSNodeBuilder) objectBuilder);
								graphManager.getNodeBuilder().setNodeUI((TSNodeUI) objectUI);	
								toolManager.setActiveTool(createTool);
							}
							
						}
					});
				}
			}
		};
	}
	
	public void updatePaletteItem(Control control) {
		//Override this
	}
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	protected abstract PaletteDrawer createLinkGroup(final IWorkbenchPage page,
			Palette palette);

	/**
	 * 
	 * @param window
	 * @param paletteView
	 * @param rootExpandBar
	 * @param palette
	 * @param listener
	 */
	protected abstract void expandBar(final IWorkbenchWindow window,
			PaletteView paletteView, ExpandBar rootExpandBar, Palette palette,
			PalettePresentationUpdater listener);

	/**
	 * 
	 * @param window
	 * @param rootExpandBar
	 * @param drawer
	 * @param listener
	 */
	protected abstract void createDrawer(final IWorkbenchWindow window,
			ExpandBar rootExpandBar, PaletteDrawer drawer,
			PalettePresentationUpdater listener);

	/**
	 * 
	 * @param rootExpandBar
	 * @param drawer
	 * @param listener
	 */
	protected void createCheckboxDrawer(ExpandBar rootExpandBar,
			PaletteDrawer drawer, PalettePresentationUpdater listener) {
		// TODO
	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param paletteEntry
	 * @param parent
	 * @param listener
	 */
	protected abstract void createPaletteEntry(final IWorkbenchWindow window,
			boolean isGlobalPalette, PaletteEntry paletteEntry,
			Composite parent, PalettePresentationUpdater listener);

	/**
	 * 
	 * @param paletteEntry
	 * @param parent
	 * @param listener
	 */
	protected void createCheckButtonEntry(PaletteEntry paletteEntry,
			Composite parent, PalettePresentationUpdater listener) {
		// TODO
	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @param custom TODO
	 * @return
	 */
	protected abstract CLabel createRectangleButton(
			final IWorkbenchWindow window, boolean isGlobalPalette,
			Composite buttonParent, String label, String toolTipText,
			String imageName, PalettePresentationUpdater listener, boolean custom);

	/**
	 * 
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @return
	 */
	protected Control createCheckboxButton(Composite buttonParent,
			String label, String toolTipText) {
		return null;
	}

}
