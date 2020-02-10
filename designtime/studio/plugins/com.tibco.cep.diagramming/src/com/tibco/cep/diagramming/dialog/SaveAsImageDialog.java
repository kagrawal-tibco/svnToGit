package com.tibco.cep.diagramming.dialog;

import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import com.tomsawyer.application.swing.export.TSESaveAsImageDialog;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

public class SaveAsImageDialog extends TSESaveAsImageDialog {

	private static final long serialVersionUID = 1L;

	public SaveAsImageDialog(Frame owner, String title, TSSwingCanvas canvas) {
//		super(owner, title, canvas, TSESaveAsImageDialog.JPEG_FORMAT
//				| TSESaveAsImageDialog.PDF_FORMAT
//				| TSESaveAsImageDialog.PNG_FORMAT);
		super(owner, title, canvas, TSESaveAsImageDialog.JPEG_FORMAT
				| TSESaveAsImageDialog.PDF_FORMAT
				| TSESaveAsImageDialog.PNG_FORMAT|TSESaveAsImageDialog.SVG_FORMAT|TSESaveAsImageDialog.GIF_FORMAT);
	}

	/**
	 * This method is extended so the checkbox for "export selected objects only"
	 * is selected automatically depending on whether there are any selected
	 * objects in our rule analyzer diagram.
	 */

	public void adjustCheckboxes() {
		super.adjustCheckboxes();

		if (this.getCanvas() != null) {
			this.selected.setSelected(((TSEGraph) this.getCanvas()
					.getGraphManager().getMainDisplayGraph()).hasSelected());
		}
	}

	/**
	 * Method overriden so we can handle SVG in a special way, although this is no
	 * longer necessary now because we have unexposed SVG. Still, PDF export required
	 * special handling to turn off anti-aliasing, so we turn that off, export to PDF,
	 * then we turn it back on. 
	 * @return
	 */
	public boolean onOK() {
		boolean returnValue = false;
		boolean aliasingWasEnabled = false;

		 List<?> edges = this.getCanvas().getGraphManager()
				.getMainDisplayGraph().edges();

		 //Commenting out svg code as it is not being used 
		 
		// SVG requires special handling. Default super code is not working for some reason...
//		if (super.type.equals("svg")) {
//
//			if (!edges.isEmpty()
//					&& (((TSEEdgeUI) ((TSEEdge) edges.get(0)).getUI())
//							.isAntiAliasingEnabled())) {
//				aliasingWasEnabled = true;
//				Iterator<?> edgeIter = edges.iterator();
//				while (edgeIter.hasNext()) {
//					((TSEEdgeUI) ((TSEEdge) edgeIter.next()).getUI())
//							.setAntiAliasingEnabled(false);
//				}
//			}
//
//			FileOutputStream stream = null;
//			File file = new File(this.fileName.getText());
//
//			try {
//				stream = new FileOutputStream(file);
//				TSSVGImageCanvas imageCanvas = new TSSVGImageCanvas(this
//						.getCanvas().getGraphManager(), stream);
//				imageCanvas.setDisplayCanvas(this.getCanvas());
//
////				if (this.drawGrid.isSelected()) {
////					imageCanvas.setGrid(this.getCanvas().getGrid());
////				}
//
//				imageCanvas.setPreferenceData(this.getCanvas()
//						.getPreferenceData());
//				imageCanvas.paint();
//
//				if (aliasingWasEnabled) {
//					Iterator<?> edgeIter = edges.iterator();
//					while (edgeIter.hasNext()) {
//						((TSEEdgeUI) ((TSEEdge) edgeIter.next()).getUI())
//								.setAntiAliasingEnabled(true);
//					}
//				}
//
//				returnValue = true;
//			} catch (Exception e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(this, "Cannot write file: "
//						+ file.getAbsolutePath(), "Error writing file",
//						JOptionPane.ERROR_MESSAGE);
//			}
//			try {
//				if (stream != null) {
//					stream.close();
//				}
//			} catch (Exception e) {
//			}
//
//			return returnValue;
//		}
// end special case for SVG

// PDF and SVG do not support anti-aliasing, so we have to turn that off.
// they do this themselves automatically.
		 
		 
		  /*
		  *@author : sshekhar 
		  *For Pdf to display Japanese text #BE-7362
		  */
			if(super.type.equals("pdf")){
				try{
				
				if (!edges.isEmpty()
						&& (((TSEEdgeUI) ((TSEEdge) edges.get(0)).getUI())
								.isAntiAliasingEnabled())) {
				aliasingWasEnabled = true;
				Iterator<?> edgeIter = edges.iterator();
				while (edgeIter.hasNext()) {
					((TSEEdgeUI) ((TSEEdge) edgeIter.next()).getUI())
							.setAntiAliasingEnabled(false);
					}
				}
				
				renderJapaneseText();
				returnValue=true;
				if (aliasingWasEnabled) {
				Iterator<?> edgeIter = edges.iterator();
				while (edgeIter.hasNext()) {
					((TSEEdgeUI) ((TSEEdge) edgeIter.next()).getUI())
							.setAntiAliasingEnabled(true);
					}
				}
				}catch(Exception e){
					e.printStackTrace();
					}
				}
		
		aliasingWasEnabled = false;

		if ((super.type.equals("pdf") || super.type.equals("svg"))
				&& !edges.isEmpty()
				&& (((TSEEdgeUI) ((TSEEdge) edges.get(0)).getUI())
						.isAntiAliasingEnabled())) {
			// first turn off anti-aliasing drawing in oval nodes which
			// turn on anti-aliasing
			//	            OvalNodeUI.setAntiAliasingSettingEnabled(false);

			// now process all the edges, because they don't have such a
			// global setting
			aliasingWasEnabled = true;
			Iterator<?> edgeIter = edges.iterator();
			while (edgeIter.hasNext()) {
				((TSEEdgeUI) ((TSEEdge) edgeIter.next()).getUI())
						.setAntiAliasingEnabled(false);
			}
		} else {
			return super.onOK();
		}

		boolean status = super.onOK();

		if (aliasingWasEnabled) {
			// turn nodes back on
			//	            OvalNodeUI.setAntiAliasingSettingEnabled(true);

			// now turn the edges back on
			Iterator<?> edgeIter = edges.iterator();
			while (edgeIter.hasNext()) {
				((TSEEdgeUI) ((TSEEdge) edgeIter.next()).getUI())
						.setAntiAliasingEnabled(true);
			}
		}

		return status;
	}
	//For Pdf to display Japanese text #BE-7362
		public void renderJapaneseText(){
			FileOutputStream stream = null;
			File file = new File(this.fileName.getText());
				try{
				
				stream = new FileOutputStream(file);
				com.tomsawyer.canvas.image.pdf.TSPDFImageCanvasPreferenceTailor imagecanvaspreferencetailor = new com.tomsawyer.canvas.image.pdf.TSPDFImageCanvasPreferenceTailor(preferenceData);
				imagecanvaspreferencetailor.setWidth(width.getTextAsInteger());
				imagecanvaspreferencetailor.setHeight(height.getTextAsInteger());
				//For Pdf to display Japanese text #BE-7362
				imagecanvaspreferencetailor.setRenderTextAsShapes(true);
	            com.tomsawyer.canvas.image.pdf.TSPDFImageCanvas imagecanvas = new com.tomsawyer.canvas.image.pdf.TSPDFImageCanvas(getCanvas().getGraphManager(), stream);
	             imagecanvas.setDisplayCanvas(this.getCanvas());
	             imagecanvas.setPreferenceData(this.getCanvas().getPreferenceData());
	             imagecanvas.setTitle(getCanvas().getGraph().getText());
	             imagecanvas.paint();
	             try {
						if (stream != null) {
							stream.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Cannot write file: "
					+ file.getAbsolutePath(), "Error writing file",
					JOptionPane.ERROR_MESSAGE);
			}
		}
		
}
