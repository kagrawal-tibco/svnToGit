package com.tibco.cep.diagramming.dialog;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.utils.ICommandIds;
import com.tibco.cep.diagramming.utils.TSImages;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

public class PageSetup extends Action {

	private IWorkbenchPage page;
	private IGraphDrawing editor;
	private Object diagramManager;

	private IWorkbenchWindow window;

	public PageSetup (String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_PAGE_SETUP);
		// Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_PAGE_SETUP);
		setImageDescriptor(TSImages.getImageDescriptor(TSImages.TS_IMAGES_PAGE_SETUP));
	}
	
	
	public void run() {
		page = window.getActivePage();
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart instanceof IGraphDrawing) {
			editor = (IGraphDrawing) activeEditorPart;
		}
		diagramManager = editor.getDiagramManager();

		TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();

		IWorkbenchPart part = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		CustomDialog CustomDialog = new CustomDialog(part.getSite().getShell(),
				swingCanvas, ICommandIds.CMD_PAGE_SETUP);
		CustomDialog.open();
		//
		// JFrame frame = new JFrame("");
		// frame.setSize(600, 400);
		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// frame.pack();
		// frame.setVisible(true);
		//
		// TSEPrintSetupDialog printSetupDialog =
		// new TSEPrintSetupDialog(frame,
		// "Print Setup",
		// stateMachineDiagramViewer.getSwingCanvas());
		// printSetupDialog.setModal(false);
		// printSetupDialog.setVisible(true);
	}
}
