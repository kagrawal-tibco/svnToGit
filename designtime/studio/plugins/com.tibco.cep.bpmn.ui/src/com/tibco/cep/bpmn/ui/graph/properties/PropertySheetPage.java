package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class PropertySheetPage extends TabbedPropertySheetPage {

	protected BpmnEditor editor;
	private ISelection currentSelection;
	private IWorkbenchPart currentPart;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public PropertySheetPage(BpmnEditor editor) {
		super(editor);
		this.editor = editor;
	}

	/**
	 * Get the EMF AdapterFactory for this editor.
	 * 
	 * @return the EMF AdapterFactory for this editor.
	 */
	public BpmnEditor getEditor() {
		return editor;
	}

	/**
	 * Update the title bar.
	 */
	@SuppressWarnings("restriction")
	public void doRefreshTitleBar(TSEObject object, String text) {
		org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite tabbedPropertyComposite = (org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite)getControl();
		org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle title = tabbedPropertyComposite.getTitle();
		if (getCurrentTab() == null) {
			title.setTitle(null, null);
		} else {
			Image image = null;
			if (object instanceof TSENode) {
				image = BpmnUIPlugin.getDefault().getImage("icons/bpmn/Activity.gif");
			} else if (object instanceof TSEdge) {   
				image = BpmnUIPlugin.getDefault().getImage("icons/bpmn/SequenceFlow.gif");
			} else if (object instanceof TSEGraph) {
				image = BpmnUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
			}else if (object instanceof TSEConnector) {
				image = BpmnUIPlugin.getDefault().getImage("icons/bpmn/Activity.gif");
			}
			title.setTitle(text, image);
		}
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		this.currentSelection = selection;
		this.currentPart = part;
		super.selectionChanged(part, selection);
	}
	
	public ISelection getCurrentSelection() {
		return currentSelection;
	}
	
	public IWorkbenchPart getCurrentPart() {
		return currentPart;
	}

}