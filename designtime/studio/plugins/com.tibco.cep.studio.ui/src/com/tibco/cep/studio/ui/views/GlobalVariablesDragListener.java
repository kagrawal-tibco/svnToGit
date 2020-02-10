package com.tibco.cep.studio.ui.views;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.views.GlobalVariablesView.GlobalVariableContainerNode;

public class GlobalVariablesDragListener extends DragSourceAdapter {

	private StructuredViewer viewer;
	private IWorkbenchPage page;

	/**
	 * @param viewer
	 * @param page
	 */
	public GlobalVariablesDragListener(StructuredViewer viewer, IWorkbenchPage page) {
		this.viewer = viewer;
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragStart(DragSourceEvent event) {
		event.doit = !viewer.getSelection().isEmpty();
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if(selection.getFirstElement() instanceof GlobalVariableContainerNode) {
			event.doit = false;
			return;
		}
		
		if(page != null) {
			IEditorPart editor = page.getActiveEditor();
			if (editor instanceof AbstractStudioResourceEditorPart) {
				if ((((AbstractStudioResourceEditorPart)editor)).isCatalogFunctionDrag()) {
					event.doit = true;
					// Temporary Solution:
					// When Drag starts, the editor comes in focus... It is helpful for the 
					// state machine property view.. 
					// ((AbstractStudioResourceEditorPart)editor).setFocus();
				} else if (editor.getSite() != null) {
					String editorId = editor.getSite().getId();
					if ("com.tibco.cep.concept.editors.formeditor".equals(editorId) || "com.tibco.cep.studio.ui.editors.editor.channel".equals(editorId) || "com.tibco.cep.studio.entities.editor.timeevent".equals(editorId)) {
						event.doit = true;
					} else {	
						event.doit = false;
					}
				} 
				else {
					event.doit = false;
				}
			} else if (editor.getSite() != null) {
				// Wish we compare editor instances, but due to the plugin dependencies we can't
				String editorId = editor.getSite().getId();
				if (editorId.startsWith("com.tibco.cep.sharedresource")) {
					event.doit = true;				
				} else if (editorId.equals("com.tibco.cep.studio.ui.editor.ClusterConfigEditor")) {
					event.doit = true;
				} else {
					event.doit = false;
				}
			} else {	
				event.doit = false;
			}
		} else {
			event.doit = true;
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragFinished(DragSourceEvent event) {
		if (!event.doit)
			return;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@SuppressWarnings("unchecked")
	public void dragSetData(DragSourceEvent event) {
		try {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			// Predicate[] tobject = (Predicate[]) selection.toList().toArray(new Predicate[selection.size()]);
			Object[] tobject = (Object[]) selection.toList().toArray(new Object[selection.size()]);
			Object obj = tobject[0];
			if (obj instanceof GlobalVariableDescriptor) {
				String name = ((GlobalVariableDescriptor) obj).getFullName();
				if (name.startsWith("/")) {
					name = name.substring(1);
				}
				event.data = "%%"+name+"%%";
			} else if (obj instanceof GlobalVariableContainerNode) {
				event.data = "";
			} else {
				event.data = obj.toString();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}