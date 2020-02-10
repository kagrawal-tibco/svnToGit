package com.tibco.cep.studio.cluster.topology.handler;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.resetCanvasAndPalette;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.palette.ClusterTopologyPaletteEntry;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.views.PaletteView;

import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * 
 * @author ggrigore
 * 
 */
public class ClusterTopologyEditorPartListener extends AbstractEditorPartPaletteHandler implements IPartListener {
	
	private ClusterTopologyEditor editor;
	
	public ClusterTopologyEditorPartListener(ClusterTopologyEditor editor){
		this.editor = editor;
	}
	
	public void partActivated(IWorkbenchPart part) {

		IWorkbenchPage activePage = part.getSite().getPage();
		if (activePage == null)
			return;
		if (part instanceof ClusterTopologyEditor) {

			//Selection Input for Property Page Display
			ClusterTopologyDiagramManager diagramManager =(ClusterTopologyDiagramManager ) editor.getDiagramManager();
			if((diagramManager.getSelectedNodes().size() == 1 && diagramManager.getSelectedEdges().size() == 0)
					|| (diagramManager.getSelectedEdges().size() == 1 && diagramManager.getSelectedNodes().size() == 0)){
				if(diagramManager.getSelectedNodes().size() == 1){
					setWorkbenchSelection(diagramManager.getSelectedNodes().get(0), editor);
				}
				if(diagramManager.getSelectedEdges().size() == 1){
					setWorkbenchSelection(diagramManager.getSelectedEdges().get(0), editor);
				}
			}
			//If no Node or Edges are selected, then Root State Machine selected by default. 
			if(diagramManager.getSelectedNodes().size()==0){
				setWorkbenchSelection((TSEGraph)diagramManager.getGraphManager().getMainDisplayGraph(), editor);
				((ClusterTopologyEditor)part).enableEdit(false);
			}
			doWhenDiagramEditorActive(part, activePage, (IEditorSite)((ClusterTopologyEditor) part).getSite(),PALETTE.CLUSTER);
		}

	}

	public void partBroughtToTop(IWorkbenchPart part) {
		if (part instanceof ClusterTopologyEditor) {
			IEditorSite site = (IEditorSite) ((ClusterTopologyEditor) part).getSite();
			if(site.getPage().getActivePart() instanceof IEditorPart){
				/**
				 * Resetting palette and default canvas selection when editor deactivates
				 */
				resetCanvasAndPalette(((ClusterTopologyDiagramManager)((ClusterTopologyEditor)part).getDiagramManager()).getDrawingCanvas());
			}
		}
	}

	public void partClosed(IWorkbenchPart part) {
		try {
			if (part instanceof ClusterTopologyEditor) {
				final ClusterTopologyEditor editor = (ClusterTopologyEditor) part;
				IEditorSite site = (IEditorSite) editor.getSite();
				if (site.getPage() != null	&& !(site.getPage().getActiveEditor() instanceof IGraphDrawing)
						&& !(site.getPage().getActiveEditor() instanceof AbstractDecisionTableEditor)) {
					doWhenDiagramEditorCloseOrDeactivate(part, site);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void partDeactivated(IWorkbenchPart part) {
		try {
			if (part instanceof ClusterTopologyEditor) {
				IEditorSite site = (IEditorSite) ((ClusterTopologyEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof ClusterTopologyEditor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	public void updatePaletteView(IWorkbenchPart part, IEditorSite site,
			boolean isActivate) {
		if (site.getPage() != null) {
			IViewPart view = site.getPage().findView(PaletteView.ID);
			if (view != null) {
				PaletteView paletteView = (PaletteView) view;
				Palette palette = paletteView.getPalette();
				if (isActivate) {
					if (palette.getPaletteDrawers().size() == 0) {
						if (part instanceof ClusterTopologyEditor) {
							if (paletteView.getType() != PALETTE.CLUSTER) {
								ClusterTopologyPaletteEntry clusterPaletteEntry = new ClusterTopologyPaletteEntry();
//								PaletteDrawer graphNodesDrawer = processPaletteEntry.createNodeGroup(site.getPage(),palette);
								PaletteDrawer graphLinksDrawer = clusterPaletteEntry.createLinkGroup(site.getPage(),palette);
								PaletteDrawer graphBEDrawer = clusterPaletteEntry.createDeploymentGroup(site.getPage(),palette);

//								palette.addPaletteDrawer(graphNodesDrawer);
								palette.addPaletteDrawer(graphLinksDrawer);
								palette.addPaletteDrawer(graphBEDrawer);
								paletteView.setType(PALETTE.CLUSTER);

								clusterPaletteEntry.expandBar(site.getWorkbenchWindow(), paletteView,paletteView.getRootExpandBar(),palette, paletteView.getListener());
							}
						}
					}
				} else {
					StudioPaletteUI.resetPalette(paletteView);
				}
			}
		}

	}

	@Override
	public void resetPaletteSelection(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}
}