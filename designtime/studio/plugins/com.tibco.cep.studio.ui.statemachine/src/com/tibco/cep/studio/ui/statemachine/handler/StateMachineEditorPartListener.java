package com.tibco.cep.studio.ui.statemachine.handler;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.resetCanvasAndPalette;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.StateMachinePaletteEntry;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * 
 * @author sasahoo
 * 
 */
public class StateMachineEditorPartListener extends AbstractEditorPartPaletteHandler implements IPartListener {
	
	protected StateMachinePaletteEntry stateModelPaletteEntry;
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		IWorkbenchPage activePage = part.getSite().getPage();
		if (activePage == null){
			return;
		}
		if (part instanceof StateMachineEditor) {
			StateMachineDiagramManager diagramManager = (StateMachineDiagramManager)((StateMachineEditor)part).getStateMachineDiagramManager();
			if (!diagramManager.isInitialized()) {
				return;
			}
			if((diagramManager.getSelectedNodes().size() == 1 && diagramManager.getSelectedEdges().size() == 0)
					|| (diagramManager.getSelectedEdges().size() == 1 && diagramManager.getSelectedNodes().size() == 0)){
				if(diagramManager.getSelectedNodes().size() == 1){
					setWorkbenchSelection(diagramManager.getSelectedNodes().get(0), (StateMachineEditor)part);
				}
				if(diagramManager.getSelectedEdges().size() == 1){
					setWorkbenchSelection(diagramManager.getSelectedEdges().get(0), (StateMachineEditor)part);
				}
			}
			//If no Node or Edges are selected, then Root State Machine selected by default. 
			if (diagramManager.getSelectedNodes().size() == 0 && diagramManager.getSelectedEdges().size() == 0) {
				setWorkbenchSelection((TSEGraph)diagramManager.getGraphManager().getMainDisplayGraph(), (StateMachineEditor)part);
				
				((StateMachineEditor)part).enableEdit(false);
			}
			doWhenDiagramEditorActive(part, activePage, (IEditorSite)((StateMachineEditor) part).getSite(), PALETTE.STATE_MACHINE);	
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partBroughtToTop(IWorkbenchPart part) {
		if (part instanceof StateMachineEditor) {
			IEditorSite site = (IEditorSite) ((StateMachineEditor) part).getSite();
			if(site.getPage().getActivePart() instanceof IEditorPart){
				/**
				 * Resetting palette and default canvas selection when editor deactivates
				 */
				resetCanvasAndPalette(((StateMachineDiagramManager)((StateMachineEditor)part).getStateMachineDiagramManager()).getDrawingCanvas());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		try {
			if (part instanceof StateMachineEditor) {
				final StateMachineEditor editor = (StateMachineEditor) part;
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

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partDeactivated(IWorkbenchPart part) {
		try {
			if (part instanceof StateMachineEditor) {
				IEditorSite site = (IEditorSite) ((StateMachineEditor) part).getSite();
				if(site.getPage().getActivePart() instanceof IEditorPart){
					/**
					 * Resetting palette and default canvas selection when editor deactivates
					 */
					resetCanvasAndPalette(((StateMachineDiagramManager)((StateMachineEditor)part).getStateMachineDiagramManager()).getDrawingCanvas());
				}
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof StateMachineEditor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @param site
	 * @param isActivate
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler#updatePaletteView(org.eclipse.ui.IWorkbenchPart, org.eclipse.ui.IEditorSite, boolean)
	 */
	public void updatePaletteView(IWorkbenchPart part, IEditorSite site, boolean isActivate) {
		if (site.getPage() != null) {
			IViewPart view = site.getPage().findView(PaletteView.ID);
			if (view != null) {
				PaletteView paletteView = (PaletteView) view;
				Palette palette = paletteView.getPalette();
				if (isActivate) {
					if (palette.getPaletteDrawers().size() == 0) {
						if (part instanceof StateMachineEditor) {
							if(paletteView.getType() !=  PALETTE.STATE_MACHINE){
								updateStateModelPalette(palette, paletteView, site);
							}
						}
					}
				} else {
					StudioPaletteUI.resetPalette(paletteView);
				}
			}
		}
	}
	
	/**
	 * @param palette
	 * @param paletteView
	 * @param site
	 */
	private void updateStateModelPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		stateModelPaletteEntry = new StateMachinePaletteEntry();
		
		PaletteDrawer genNodesDrawer = StudioPaletteUI.createGlobalDrawer(site.getPage(), palette);
//		genNodesDrawer.addPaletteEntry(stateModelPaletteEntry.createPaletteEntry(site.getPage(),
//										genNodesDrawer, null,
//										Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TITLE"),
//										Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TOOLTIP"),
//										StateMachineImages.SM_PALETTE_NOTE, new NoteNodeCreator(),
//										new NoteNodeUI(), false, Tool.NONE, false));
		
		PaletteDrawer smNodesDrawer = stateModelPaletteEntry.createNodeGroup(site.getPage(), palette);
		PaletteDrawer smLinksDrawer = stateModelPaletteEntry.createLinkGroup(site.getPage(), palette);
		palette.addPaletteDrawer(genNodesDrawer);
		palette.addPaletteDrawer(smNodesDrawer);
		palette.addPaletteDrawer(smLinksDrawer);
		paletteView.setType(PALETTE.STATE_MACHINE);
		stateModelPaletteEntry.expandBar(site.getWorkbenchWindow(),paletteView, paletteView.getRootExpandBar(),	palette, paletteView.getListener());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler#resetPaletteSelection(org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void resetPaletteSelection(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}
}