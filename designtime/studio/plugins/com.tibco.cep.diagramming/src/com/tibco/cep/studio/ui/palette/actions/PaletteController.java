package com.tibco.cep.studio.ui.palette.actions;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
/**
 * Controls the selection state of the PaletteEntries on the Palette
 * based on mouse clicks
 */
public class PaletteController extends MouseAdapter {

	private IWorkbenchPage page;
	private IWorkbenchWindow window;
	private IGraphDrawing editor;
	public PaletteController(IWorkbenchWindow window){
		this.window = window;
	}
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		super.mouseDoubleClick(e);
	}

	@Override
	public void mouseDown(MouseEvent e) {
		page = window.getActivePage();
		if (page == null) {
			return;
		}
		if (e.getSource() instanceof CLabel) {
			CLabel label = (CLabel) e.getSource();
			if (label.getData() != null && label.getData() instanceof PaletteEntry) {
				((PaletteEntry)label.getData()).setState(PaletteEntry.STATE_SELECTED);
				updatePaletteItem(label);
			}
		}

	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (page == null) {
			return;
		}
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart instanceof IGraphDrawing) {
			editor = (IGraphDrawing) activeEditorPart;
		}
		if(editor != null) {
			handleMouseUp();
		}
	}

	protected void handleMouseUp() {
		// default implementation does nothing
	}
	
	protected void updatePaletteItem(CLabel label) {
		//Override this
	}
}