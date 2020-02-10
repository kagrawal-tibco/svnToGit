package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
/**
 * 
 * @author mgoel
 *
 */
public class PaletteItemDragListener implements DragSourceListener {

	TreeViewer viewer;
	BpmnPaletteModel model;
	@SuppressWarnings("unused")
	private BpmnPaletteConfigurationModelMgr mdlmgr;
	
	public PaletteItemDragListener(TreeViewer viewer, BpmnPaletteModel bpmnPaletteModel, BpmnPaletteConfigurationModelMgr modelmgr) {
		this.viewer=viewer;// TODO Auto-generated constructor stub
		this.model = bpmnPaletteModel;
		this.mdlmgr = modelmgr;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		StructuredSelection selection = (StructuredSelection)viewer.getSelection();
        Object firstElement = selection.getFirstElement();
		if(!(firstElement instanceof BpmnPaletteGroupItem))
			event.doit = false;
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		StructuredSelection selection = (StructuredSelection)viewer.getSelection();
        event.data = selection.getFirstElement();
        LocalSelectionTransfer.getTransfer().setSelection(selection);
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		if(event.detail == DND.DROP_MOVE && event.doit)
        {
          StructuredSelection selection = (StructuredSelection)LocalSelectionTransfer.getTransfer().getSelection();
          @SuppressWarnings("unused")
		BpmnPaletteGroupItem item = ((BpmnPaletteGroupItem)selection.getFirstElement());
          viewer.refresh();
        }

	}

}
