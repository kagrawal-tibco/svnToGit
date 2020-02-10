package com.tibco.cep.studio.ui.editors.retegraph;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tomsawyer.graphicaldrawing.TSEGraph;

public class ReteGraphDiagramPropertyLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = null;
		Object selElement = null;
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			selElement = selection.getFirstElement();
		}
		if (selElement instanceof TSEGraph) {
			ReteGraphDiagramEditor editor = (ReteGraphDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			return Messages.getString("project.diagram.general.property.section.title", 
					((EntityDiagramEditorInput)editor.getEditorInput()).getProject().getName());
		}
		return text != null ? text : ""/*super.getText(element)*/;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image img = null;
		
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			element = selection.getFirstElement();
		}
		if (element instanceof TSEGraph) {
			return EditorsUIPlugin.getDefault().getImage("icons/project_diagram.png");
		}	
		return img != null ? img : super.getImage(element);
	}
}
