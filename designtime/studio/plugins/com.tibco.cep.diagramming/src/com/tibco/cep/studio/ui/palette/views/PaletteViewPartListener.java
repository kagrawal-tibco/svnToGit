package com.tibco.cep.studio.ui.palette.views;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.AbstractResourceEditorPart;
import com.tibco.cep.diagramming.drawing.BaseDiagramManager;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.PALETTE;

/**
 * 
 * @author sasahoo
 *
 */
public class PaletteViewPartListener implements IPartListener {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void partActivated(IWorkbenchPart part) {
		if(part instanceof PaletteView){
			PaletteView view = (PaletteView)part;
			if(view.getViewSite() != null && view.getViewSite().getPage()!= null && view.getViewSite().getPage().getActiveEditor()!= null
					&& view.getViewSite().getPage().getActiveEditor() instanceof IGraphDrawing){
				
				IEditorPart e = view.getViewSite().getPage().getActiveEditor();
				IGraphDrawing editor = (IGraphDrawing)view.getViewSite().getPage().getActiveEditor();
				((BaseDiagramManager)editor.getDiagramManager()).waitForInitComplete();
				if (!((BaseDiagramManager)editor.getDiagramManager()).isInitialized()) {
					return;
				}
				if(e instanceof AbstractResourceEditorPart){
					AbstractResourceEditorPart resourceEditor = (AbstractResourceEditorPart)e;
					if(editor.getPalette() != view.getType()){
						if(resourceEditor.getPartListener()!= null){
							resourceEditor.getPartListener().updatePaletteView(e,  e.getEditorSite(), true);
						}
					}
				}
				
			}
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if(part instanceof PaletteView){
			PaletteView view = (PaletteView)part;
			view.setType(PALETTE.NONE);
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}
}
