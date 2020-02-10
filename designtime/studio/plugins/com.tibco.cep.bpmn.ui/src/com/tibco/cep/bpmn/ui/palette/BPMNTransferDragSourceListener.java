package com.tibco.cep.bpmn.ui.palette;


/**
 * 
 * @author sasahoo
 *
 */
public class BPMNTransferDragSourceListener /*extends TemplateTransferDragSourceListener*/ {

	/**
	 * @param viewer
	 * @param xfer
	 */
//	@SuppressWarnings("deprecation")
//	public BPMNTransferDragSourceListener(EditPartViewer viewer, Transfer xfer) {
//		super(viewer, xfer);
//	}

	/**
	 * @param viewer
	 */
//	public BPMNTransferDragSourceListener(EditPartViewer viewer) {
//		super(viewer);
//	}

	/**
	 * Cancels the drag if the selected item does not represent a
	 * PaletteTemplateEntry.
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragStart(DragSourceEvent)
	 */
//	public void dragStart(DragSourceEvent event) {
//		Object template = getTemplate();
//		if (template == null)
//			event.doit = false;
//		TemplateTransfer.getInstance().setTemplate(template);
//	}

	/**
	 * A helper method that returns <code>null</code> or the <i>template</i>
	 * Object from the currently selected EditPart.
	 * 
	 * @return the template
	 */
//	@SuppressWarnings("rawtypes")
//	protected Object getTemplate() {
//		List selection = getViewer().getSelectedEditParts();
//		if (selection.size() == 1) {
//			EditPart editpart = (EditPart) getViewer().getSelectedEditParts().get(0);
//			Object model = editpart.getModel();
//			if (model instanceof PaletteTemplateEntry) {
//				return ((PaletteTemplateEntry) model).getTemplate();
//			}
//			if (model instanceof CombinedTemplateCreationEntry) {
//				Object template = ((CombinedTemplateCreationEntry) model).getTemplate();
//				
//				BpmnPaletteGroupItem item = (BpmnPaletteGroupItem)template;
//				item.getEntry().setState(PaletteEntry.STATE_SELECTED);
//				
//				return template;
//			}
//		}
//		return null;
//	}
}