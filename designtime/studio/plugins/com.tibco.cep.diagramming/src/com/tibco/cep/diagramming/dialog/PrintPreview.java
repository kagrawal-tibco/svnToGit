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

public class PrintPreview extends Action {

	private IWorkbenchPage page;
	private IGraphDrawing editor;
	private Object diagramManager;
	private IWorkbenchWindow window;

	public PrintPreview (String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
        setId(ICommandIds.CMD_PRINT_PREVIEW);
        // Associate the action with a pre-defined command, to allow key bindings.
        setActionDefinitionId(ICommandIds.CMD_PRINT_PREVIEW);
        setImageDescriptor(TSImages.getImageDescriptor(TSImages.TS_IMAGES_PRINT_PREVIEW));
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
				swingCanvas, ICommandIds.CMD_PRINT_PREVIEW);
		CustomDialog.open();

		// JButton Button1, Button2, Button3, Button4;
		// Button1 = new JButton("Button1");
		// Button2 = new JButton("Button2");
		// Button3 = new JButton("Button3");
		// Button4 = new JButton("Button4");

		// JFrame frame = new JFrame("");
		// frame.setSize(600, 400);
		// frame.setLayout(new FlowLayout());
		// frame.getContentPane().add( Button1);
		// frame.getContentPane().add( Button2);
		// frame.getContentPane().add( Button3);
		// frame.getContentPane().add( Button4);
		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// frame.pack();
		// frame.setVisible(true);
		//
		// JDialog printPreviewWindow = new TSEPrintPreviewWindow(frame,
		// "Print Preview", stateMachineDiagramViewer.getSwingCanvas());
		// printPreviewWindow.setSize(600, 400);
		// printPreviewWindow.setModal(false);
		// printPreviewWindow.setVisible(true);

	}
}
