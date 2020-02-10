package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.resetCanvasAndPalette;

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
import com.tibco.cep.studio.ui.palette.views.PaletteView;

public class StateMachineComponentEditorPartListener extends AbstractEditorPartPaletteHandler implements IPartListener {

	public void partActivated(IWorkbenchPart part) {
		IWorkbenchPage activePage = part.getSite().getPage();
		if (activePage == null) {
			return;
		}
		if (part instanceof StateMachineComponentEditor) {

			StateMachineComponentEditor stateMachineComponentEditor = (StateMachineComponentEditor) part;
			doWhenDiagramEditorActive(part, activePage, (IEditorSite) stateMachineComponentEditor.getSite(), PALETTE.NONE);

			stateMachineComponentEditor.openPerspective();
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
		if (part instanceof StateMachineComponentEditor) {
			IEditorSite site = (IEditorSite) ((StateMachineComponentEditor) part).getSite();
			if (site.getPage().getActivePart() instanceof IEditorPart) {
				/**
				 * Resetting palette and default canvas selection when editor deactivates
				 */
				resetCanvasAndPalette(((ReadOnlyStateMachineDiagramManager) ((StateMachineComponentEditor) part).getDiagramManager()).getDrawingCanvas());
			}
		}
	}

	public void partClosed(IWorkbenchPart part) {
		try {
			if (part instanceof StateMachineComponentEditor) {
				final StateMachineComponentEditor editor = (StateMachineComponentEditor) part;
				IEditorSite site = (IEditorSite) editor.getSite();
				if (site.getPage() != null && !(site.getPage().getActiveEditor() instanceof IGraphDrawing) && !(site.getPage().getActiveEditor() instanceof AbstractDecisionTableEditor)) {
					doWhenDiagramEditorCloseOrDeactivate(part, site);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void partDeactivated(IWorkbenchPart part) {
		try {
			if (part instanceof StateMachineComponentEditor) {
				IEditorSite site = (IEditorSite) ((StateMachineComponentEditor) part).getSite();
				if (site.getPage().getActivePart() instanceof IEditorPart) {
					/**
					 * Resetting palette and default canvas selection when editor deactivates
					 */
					resetCanvasAndPalette(((ReadOnlyStateMachineDiagramManager) ((StateMachineComponentEditor) part).getDiagramManager()).getDrawingCanvas());
				}
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), site.getPage().getActiveEditor() instanceof StateMachineComponentEditor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void partOpened(IWorkbenchPart part) {
	}

	@Override
	public void updatePaletteView(IWorkbenchPart part, IEditorSite site, boolean isActivate) {
		if (site.getPage() != null) {
			IViewPart view = site.getPage().findView(PaletteView.ID);
			if (view != null) {
				PaletteView paletteView = (PaletteView) view;
				Palette palette = paletteView.getPalette();
				if (isActivate) {
					if (palette.getPaletteDrawers().size() == 0) {
						if (part instanceof StateMachineComponentEditor) {
							updateStateModelPalette(palette, paletteView, site);
						}
					}
				} else {
					StudioPaletteUI.resetPalette(paletteView);
				}
			}
		}
	}

	private void updateStateModelPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site) {
		paletteView.setType(PALETTE.NONE);
	}

	@Override
	public void resetPaletteSelection(IWorkbenchPart part) {
	}
}