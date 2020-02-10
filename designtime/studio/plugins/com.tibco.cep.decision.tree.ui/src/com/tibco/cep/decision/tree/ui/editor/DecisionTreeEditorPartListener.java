package com.tibco.cep.decision.tree.ui.editor;

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
import com.tomsawyer.graphicaldrawing.TSEGraph;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeEditorPartListener extends AbstractEditorPartPaletteHandler implements IPartListener {
	
	public void partActivated(IWorkbenchPart part) {
		IWorkbenchPage activePage = part.getSite().getPage();
		if (activePage == null){
			return;
		}
		if (part instanceof DecisionTreeEditor) {
			DecisionTreeDiagramManager diagramManager = (DecisionTreeDiagramManager)((DecisionTreeEditor)part).getDecisionTreeDiagramManager();
			if((diagramManager.getSelectedNodes().size() == 1 && diagramManager.getSelectedEdges().size() == 0)
					|| (diagramManager.getSelectedEdges().size() == 1 && diagramManager.getSelectedNodes().size() == 0)){
				if(diagramManager.getSelectedNodes().size() == 1){
					setWorkbenchSelection(diagramManager.getSelectedNodes().get(0), (DecisionTreeEditor)part);
				}
				if(diagramManager.getSelectedEdges().size() == 1){
					setWorkbenchSelection(diagramManager.getSelectedEdges().get(0), (DecisionTreeEditor)part);
				}
			}
			//If no Node or Edges are selected, then main Decision Tree selected by default. 
			if (diagramManager.getSelectedNodes().size() == 0) {
				setWorkbenchSelection((TSEGraph)diagramManager.getGraphManager().getMainDisplayGraph(), (DecisionTreeEditor)part);
				
				((DecisionTreeEditor)part).enableEdit(false);
			}
			doWhenDiagramEditorActive(part, activePage, (IEditorSite)((DecisionTreeEditor) part).getSite(), PALETTE.TREE);	
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partBroughtToTop(IWorkbenchPart part) {
		if (part instanceof DecisionTreeEditor) {
			IEditorSite site = (IEditorSite) ((DecisionTreeEditor) part).getSite();
			if(site.getPage().getActivePart() instanceof IEditorPart){
				/**
				 * Resetting palette and default canvas selection when editor deactivates
				 */
				resetCanvasAndPalette(((DecisionTreeDiagramManager)((DecisionTreeEditor)part).getDecisionTreeDiagramManager()).getDrawingCanvas());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		try {
			if (part instanceof DecisionTreeEditor) {
				final DecisionTreeEditor editor = (DecisionTreeEditor) part;
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
			if (part instanceof DecisionTreeEditor) {
				IEditorSite site = (IEditorSite) ((DecisionTreeEditor) part).getSite();
				if(site.getPage().getActivePart() instanceof IEditorPart){
					/**
					 * Resetting palette and default canvas selection when editor deactivates
					 */
					resetCanvasAndPalette(((DecisionTreeDiagramManager)((DecisionTreeEditor)part).getDecisionTreeDiagramManager()).getDrawingCanvas());
				}
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof DecisionTreeEditor);
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
						if (part instanceof DecisionTreeEditor) {
							//if (paletteView.getType() !=  PALETTE.TREE) {
								updateDecisionTreePalette(palette, paletteView, site);
							//}
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
	private void updateDecisionTreePalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		DecisionTreePaletteEntry decisionTreePaletteEntry = new DecisionTreePaletteEntry(site.getPage());
		PaletteDrawer conditionDrawer = decisionTreePaletteEntry.createConditionGroup(site.getPage(), palette);
		PaletteDrawer actionDrawer = decisionTreePaletteEntry.createActionGroup(site.getPage(), palette);
		PaletteDrawer terminalDrawer = decisionTreePaletteEntry.createTerminalGroup(site.getPage(), palette);
		PaletteDrawer otherDrawer = decisionTreePaletteEntry.createOtherGroup(site.getPage(), palette);
		PaletteDrawer edgesDrawer = decisionTreePaletteEntry.createLinkGroup(site.getPage(), palette);
		palette.addPaletteDrawer(conditionDrawer);
		palette.addPaletteDrawer(actionDrawer);
		palette.addPaletteDrawer(terminalDrawer);
		palette.addPaletteDrawer(otherDrawer);
		palette.addPaletteDrawer(edgesDrawer);
		paletteView.setType(PALETTE.TREE);
		decisionTreePaletteEntry.expandBar(site.getWorkbenchWindow(),paletteView, paletteView.getRootExpandBar(), palette, paletteView.getListener());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler#resetPaletteSelection(org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void resetPaletteSelection(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}
}