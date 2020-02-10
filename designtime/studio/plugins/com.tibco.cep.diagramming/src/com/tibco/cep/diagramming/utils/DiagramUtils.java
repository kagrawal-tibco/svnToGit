package com.tibco.cep.diagramming.utils;

import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.actions.DiagramLayoutActions;
import com.tibco.cep.diagramming.dialog.CustomDialog;
import com.tibco.cep.diagramming.dialog.SaveAsImageDialog;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.IZoomConstants;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.IDisplayFrameProvider;
import com.tomsawyer.application.swing.export.TSEPrintPreviewWindow;
import com.tomsawyer.application.swing.export.TSEPrintSetupDialog;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.grid.TSGridPreferenceTailor;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;
import com.tomsawyer.util.preference.TSPreferenceData;

/**
 * 
 * @author sasahoo
 * @author hitesh
 *
 */
public class DiagramUtils implements IZoomConstants{

	private static final String SWT_AWT_DISPLAY_FRAME_PROVIDER_EXTENSION_POINT = "com.tibco.cep.studio.core.displayFrameProvider";
	private static final String SWT_AWT_DISPLAY_FRAME = "frame";
	public static final String INHERITANCE_EDGE_TYPE = "Inherits From";
	public static final String CONTAINEMT_EDGE_TYPE = "Containment";
	public static final String REFERENCE_EDGE_TYPE = "Reference";

	/**
	 * @param page
	 * @return
	 */
	public static DiagramManager getDiagramManager(IWorkbenchPage page){
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart instanceof IGraphDrawing) {
			return (DiagramManager) ((IGraphDrawing) activeEditorPart).getDiagramManager();
		}
		return null;
	}

	/**
	 * @param page
	 */
	public static void exportToImageAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		//		java.awt.Frame frame = SWT_AWT_Frame.getInstance().getFrame();
		java.awt.Frame frame = getFrame();
		//final TSESaveAsImageDialog dialog = new TSESaveAsImageDialog(frame,"Export Drawing to Image",((IGraphDrawing)diagramManager).getDrawingCanvas());
		final SaveAsImageDialog dialog = new SaveAsImageDialog(frame,"Export Drawing to Image",((IGraphDrawing)diagramManager).getDrawingCanvas());
		dialog.setGraphPath(System.getProperty("user.home") +
				File.separator +"Untitled1");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialog.setVisible(true);		
			}
		});	
	}

	/**
	 * @param page
	 */
	public static void fitWindowAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			swingCanvas.fitInCanvas(true);

			//Resetting Editor
			((DiagramManager) diagramManager).resetDiagramEditor();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * @param page
	 * @param zoomLevel
	 */
	public static void zoomWindow(IWorkbenchPage page,double zoomLevel){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			swingCanvas.setZoomLevel(zoomLevel, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param editor
	 * @return
	 */
	public static String getCurrentZoomLevel(IEditorPart editor){
		IDiagramManager diagramManager = ((IGraphDrawing)editor).getDiagramManager();
		TSSwingCanvas canvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
		if (canvas == null) {
			return format.format(100);
		}
		String zoom = format.format(canvas.getZoomLevel() * multiplier);
		return zoom;
	}

	/**
	 * @param page
	 */
	public static void incrementalLayoutAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		final Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					LayoutManager layoutManager = ((IGraphDrawing) diagramManager).getLayoutManager();
					// layoutManager.callBatchIncrementalLayout();
					// layoutManager.setIncrementalOptions();
					layoutManager.callInteractiveIncrementalLayout();	
					//Resetting Editor
					((DiagramManager) diagramManager).resetDiagramEditor();
					
				}
			});
			

			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * @param page
	 */
	public static void interactiveZoomAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			TSToolManager toolManager = swingCanvas.getToolManager();
			toolManager.setActiveTool(TSViewingToolHelper.getInteractiveZoomTool(toolManager));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void labelingAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		final Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					LayoutManager layoutManager = ((IGraphDrawing) diagramManager).getLayoutManager();
					layoutManager.callInteractiveLabeling();	

					//Resetting Editor
					((DiagramManager) diagramManager).resetDiagramEditor();
				}
			});
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void layoutAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			LayoutManager layoutManager = ((IGraphDrawing) diagramManager).getLayoutManager();
			layoutManager.setHierarchicalOptions();
			layoutManager.callInteractiveGlobalLayout();
			// ((IGraphDrawing)diagramManager).getDrawingCanvas().fitInCanvas(true);

			//Resetting Editor
			((DiagramManager) diagramManager).resetDiagramEditor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void layoutAction(IWorkbenchPage page, int style) {
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if (diagramManager == null) return;
		try{
			LayoutManager layoutManager = ((IGraphDrawing) diagramManager).getLayoutManager();

			switch (style) {
			case DiagramLayoutActions.CIRCULAR_STYLE:
				layoutManager.setCircularOptions();
				break;
			case DiagramLayoutActions.ORTHOGONAL_STYLE:
				layoutManager.setOrthogonalOptions();
				break;
			case DiagramLayoutActions.SYMMETRIC_STYLE:
				layoutManager.setSymmetricOptions();
				break;
			case DiagramLayoutActions.HIERARCHICAL_STYLE:
				layoutManager.setHierarchicalOptions();
				break;
			case DiagramLayoutActions.HIERARCHICAL_POLYLINE_STYLE:
				layoutManager.setRegularHierarchicalOptions();
				break;
			/* TODO - Decision Tree	
			case DiagramLayoutActions.DECISION_STYLE:
				layoutManager.setDecisionOptions();
				break;
			*/	
			default:
				layoutManager.setHierarchicalOptions();
			}
			layoutManager.callInteractiveGlobalLayout();

			//Resetting Editor
			((DiagramManager) diagramManager).resetDiagramEditor();

			// ((IGraphDrawing)diagramViewer).getDrawingCanvas().fitInCanvas(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * @param page
	 */
	public static void linkNavigatorAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			TSToolManager toolManager = swingCanvas.getToolManager();
			toolManager.setActiveTool(TSViewingToolHelper.getLinkNavigationTool(toolManager));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void panAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			TSToolManager toolManager = swingCanvas.getToolManager();
			toolManager.setActiveTool(TSViewingToolHelper.getPanTool(toolManager));	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void printAction(IWorkbenchPage page){
		//		IEditorPart editorPart = page.getActiveEditor();
		//		if (!(editorPart instanceof IGraphDrawing)) return;
		//		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		//		if(((IGraphDrawing) editorPart).getDiagramManager() == null)return;
		//		final TSPrinterCanvas printerCanvas = new TSPrinterCanvas(((IGraphDrawing)diagramManager).getGraphManager());
		//		printerCanvas.setPreferenceData(((IGraphDrawing)diagramManager).getDrawingCanvas().getPreferenceData());
		//		printerCanvas.print();
		//		
		//		// there is a repainting bug when swing components are mixed
		//		// with a heavy weight component, like the native print
		//		// dialog: the components behind the print dialog will not
		//		// be refreshed when one moves the print dialog around. To
		//		// work around this Swing problem, we have to place the
		//		// printing code on a separate thread, so the Swing thread
		//		// can proceed with the normal repainting.
		//
		//		Runnable runnableImpl = new Runnable()
		//		{
		//			public void run()
		//			{
		//				printerCanvas.print();
		//				// getOwnerFrame().setEnabled(true);
		//				// getOwnerFrame().setVisible(true);
		//			}
		//		};
		//
		//		Thread printThread = new Thread(runnableImpl);
		//
		//		// getOwnerFrame().setEnabled(false);
		//		printThread.start();
	}

	/**
	 * @param page
	 */
	public static void printPreviewAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;			
		//		java.awt.Frame frame = SWT_AWT_Frame.getInstance().getFrame();
		java.awt.Frame frame = getFrame();
		final TSEPrintPreviewWindow dialog = new TSEPrintPreviewWindow(frame,"Print Preview",((IGraphDrawing)diagramManager).getDrawingCanvas());
		dialog.setSize(800,600);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialog.setVisible(true);		
			}
		});
	}

	/**
	 * @param page
	 */
	public static void printSetupAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		//		java.awt.Frame frame = SWT_AWT_Frame.getInstance().getFrame();
		java.awt.Frame frame = getFrame();
		final TSEPrintSetupDialog printSetup = new TSEPrintSetupDialog(frame,"Print Setup",((IGraphDrawing)diagramManager).getDrawingCanvas());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				printSetup.setVisible(true);		
			}
		});
	}

	public static Frame getFrame() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
		.getConfigurationElementsFor(SWT_AWT_DISPLAY_FRAME_PROVIDER_EXTENSION_POINT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			Object o;
			try {
				o = element.createExecutableExtension(SWT_AWT_DISPLAY_FRAME);
				if (o instanceof IDisplayFrameProvider) {
					return ((IDisplayFrameProvider) o).getFrame();
				}
			} catch (CoreException e) {
				StudioCorePlugin.log(e);
			}
		}	
		return null;
	}

	/**
	 * @param page
	 */
	public static void routingAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		final Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;	
		try{
			final LayoutManager layoutManager = ((IGraphDrawing) diagramManager).getLayoutManager();
			// layoutManager.callBatchGlobalLayout();
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// should resetDiagramEditor also be part of this runnable?
					layoutManager.setRoutingOptions();
					layoutManager.callInteractiveRouting();
					((IGraphDrawing) diagramManager).getDrawingCanvas().fitInCanvas(true);	
				}
			});

			//Resetting Editor
			((DiagramManager) diagramManager).resetDiagramEditor();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void selectAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			TSToolManager toolManager = swingCanvas.getToolManager();
			toolManager.setActiveTool(TSViewingToolHelper.getSelectTool(toolManager));

			//Resetting Editor
			((DiagramManager) diagramManager).resetDiagramEditor();

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param page
	 */
	public static void zoomAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			TSToolManager toolManager = swingCanvas.getToolManager();
			toolManager.setActiveTool(TSViewingToolHelper.getZoomTool(toolManager));	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void magnifyAction(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		try{
			TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
			TSToolManager toolManager = swingCanvas.getToolManager();
			toolManager.setActiveTool(toolManager.getTool("magnifyTool"));	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	

	// works but brings up 2 dialogs: for Print set up
	public void run2(IWorkbenchPage page){
		IEditorPart editorPart = page.getActiveEditor();
		if (!(editorPart instanceof IGraphDrawing)) return;
		Object diagramManager = ((IGraphDrawing) editorPart).getDiagramManager();
		if(diagramManager == null)return;
		CustomDialog CustomDialog = new CustomDialog(page.getActivePart().getSite().getShell(),((IGraphDrawing) diagramManager).getDrawingCanvas(),
				ICommandIds.CMD_PAGE_SETUP);
		CustomDialog.open();		
	}

	/**
	 * @param diagramManager
	 */
	public static void refreshDiagram(DiagramManager diagramManager){
		try{
			//causes graph/node jump
			//diagramManager.getLayoutManager().callBatchIncrementalLayout();
			diagramManager.getDrawingCanvas().drawGraph();
			diagramManager.getDrawingCanvas().repaint();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * @param diagramManager
	 * @return
	 */
	public static boolean isClipBoardContentsAvailable(final DiagramManager diagramManager){
		try{
			DataFlavor[] flavors = diagramManager.getDrawingCanvas().getClipboard().getAvailableDataFlavors();
			if(flavors.length > 0){
				return true;
			}
			//						for(DataFlavor fv:flavors){
			//							if(fv.getRepresentationClass() == TSDGraph.class){
			//								try {
			//									Object object = diagramManager.getDrawingCanvas().getClipboard().getData(fv);
			//									TSEGraphManager manager = (TSEGraphManager)object;
			//									TSDGraph graph = (TSDGraph)manager.getAnchorGraph();
			//									for(Object obj: graph.nodes()){
			//										TSENode node = (TSENode)obj;
			//										if(!node.getTagString().trim().equals("")){
			//											System.out.println(node.getUserObject());
			//										}
			//									}
			//								} catch (UnsupportedFlavorException e) {
			//									e.printStackTrace();
			//								} catch (IOException e) {
			//									e.printStackTrace();
			//								}
			//							}
			//						}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return false;
	}

	/**
	 *  This method sets the grid type to Points, Lines or None. 
	 *  Grid type option is configurable through preference page
	 */

	public static void gridType(String gridType, DrawingCanvas drawingCanvas){
		TSPreferenceData prefData = drawingCanvas.getPreferenceData();
		TSGridPreferenceTailor gridTailor = new TSGridPreferenceTailor(prefData);
		if(gridType.contains(Messages.getString("designer.preference.diagram.none"))){
			gridTailor.setNoGrid();
		} else if (gridType.contains(Messages.getString("designer.preference.diagram.lines"))){
			gridTailor.setLineGrid();
			gridTailor.setSpacingX(15);
			gridTailor.setSpacingY(15);
		} else if (gridType.contains(Messages.getString("designer.preference.diagram.points"))){
			gridTailor.setPointGrid();
			gridTailor.setSpacingX(15);
			gridTailor.setSpacingY(15);
		}
	}

	public static void disposePanel(final Container panel) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Container parent = panel;
				parent.removeAll();
				if (parent.getLayout() != null) {
					parent.setLayout(null);
				}
				while (parent.getParent() != null) {
					parent = parent.getParent();
					parent.removeAll();
					if (parent instanceof JRootPane) {
						((JRootPane) parent).setBorder(null);
					} 
					if (parent.getLayout() != null) {
						parent.setLayout(null);
					}
				}
				if (parent instanceof Window) {
					((Window) parent).dispose();
					parent.removeAll();
				}
			}
		});

	}

	public static String getEdgeType(TSEEdge edge) {
		TSEEdgeLabel label = null;
		List<?> labels = edge.labels();
		Iterator<?> iterator = labels.iterator();
		while(iterator.hasNext()) {
			label =  (TSEEdgeLabel) iterator.next();
			if(label.getText() != null) {
				if(label.getText().equals(INHERITANCE_EDGE_TYPE)) {
					return INHERITANCE_EDGE_TYPE;
				} else if (label.getText().equals(CONTAINEMT_EDGE_TYPE)) {
					return CONTAINEMT_EDGE_TYPE;
				} else if(label.getText().equals(REFERENCE_EDGE_TYPE)) {
					return REFERENCE_EDGE_TYPE;
				}
			}
		}
		
		return "";
	}

	/**
	 * @param object
	 */
	public static void display(final TSEObject object, final DiagramManager manager){
			SwingUtilities.invokeLater(new Runnable()
			{
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run()
				{
					TSConstPoint constPoint = null;
					if(object instanceof TSENode){
						((TSENode)object).setSelected(true);
						 constPoint= ((TSENode)object).getCenter();
					}
					if(object instanceof TSEEdge){
						((TSEEdge)object).setSelected(true);
						 constPoint= ((TSEEdge)object).getSourcePoint();
					}
					DrawingCanvas drawingCanvas = manager.getDrawingCanvas();
//					drawingCanvas.drawGraph();
//					drawingCanvas.repaint();
					drawingCanvas.scrollBy(30, 30, true);
					drawingCanvas.centerPointInCanvas(constPoint, true);
//					drawingCanvas.setVisible(true);
				}
			});
	    }
	
	/**
	 * @param graph
	 */
	@SuppressWarnings("unchecked")
	public static void unselect(TSEGraph graph){
		List<TSENode> list = graph.nodes();
		unselectTransitionEdges(graph);
		for(TSENode node:list){
			if(node.isSelected()){
				node.setSelected(false);
			}
			if(node.getChildGraph() != null){
				unselect((TSEGraph)node.getChildGraph());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void unselectTransitionEdges(TSEGraph graph){
		List<TSEEdge> list = graph.edges();
		for(TSEEdge edge:list){
			if(edge.isSelected()){
				edge.setSelected(false);
			}
		}
	}
}
