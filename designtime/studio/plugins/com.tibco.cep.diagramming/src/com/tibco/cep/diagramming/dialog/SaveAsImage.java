package com.tibco.cep.diagramming.dialog;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tomsawyer.canvas.image.jpeg.TSJPEGImageCanvasPreferenceTailor;
import com.tomsawyer.canvas.image.pdf.TSPDFImageCanvasPreferenceTailor;
import com.tomsawyer.canvas.image.png.TSPNGImageCanvasPreferenceTailor;
import com.tomsawyer.canvas.image.svg.TSSVGImageCanvasPreferenceTailor;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

public class SaveAsImage implements IWorkbenchWindowActionDelegate {

	private IWorkbenchPage page;
	private IGraphDrawing editor;
	private Object diagramManager;

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void init(IWorkbenchWindow window) {
		page = window.getActivePage();
	}

	public void run(IAction action) {
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart instanceof IGraphDrawing) {
			editor = (IGraphDrawing) activeEditorPart;
		}
		diagramManager = editor.getDiagramManager();

		TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();
		IWorkbenchPart part = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		CustomDialog CustomDialog = new CustomDialog(part.getSite().getShell(),
				swingCanvas, action.getId());
		CustomDialog.open();

		//		JFrame frame = new JFrame("");
		//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//		frame.setSize(600, 400);
		//		frame.pack();
		//		frame.setVisible(true);

		if (((TSEGraph) ((IGraphDrawing) diagramManager).getGraphManager().getMainDisplayGraph())
				.hasSelected()) {
			TSJPEGImageCanvasPreferenceTailor jpegOptions = new TSJPEGImageCanvasPreferenceTailor(
					((IGraphDrawing) diagramManager).getDrawingCanvas().getPreferenceData());
			jpegOptions.setExportSelectedOnly();

			TSPDFImageCanvasPreferenceTailor pdfOptions = new TSPDFImageCanvasPreferenceTailor(
					((IGraphDrawing) diagramManager).getDrawingCanvas().getPreferenceData());
			pdfOptions.setExportSelectedOnly();

			TSPNGImageCanvasPreferenceTailor pngOptions = new TSPNGImageCanvasPreferenceTailor(
					((IGraphDrawing) diagramManager).getDrawingCanvas().getPreferenceData());
			pngOptions.setExportSelectedOnly();

			TSSVGImageCanvasPreferenceTailor svgOptions = new TSSVGImageCanvasPreferenceTailor(
					((IGraphDrawing) diagramManager).getDrawingCanvas().getPreferenceData());
			svgOptions.setExportSelectedOnly();
		}

		//        SaveAsImageDialog saveAsImageDialog =
		//            new SaveAsImageDialog(frame,
		//                "Export Drawing To Image",
		//                stateMachineDiagramViewer.getSwingCanvas());
		//        saveAsImageDialog.setModal(false);
		//        saveAsImageDialog.setVisible(true);

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
