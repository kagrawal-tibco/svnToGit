package com.tibco.cep.studio.ui.diagrams;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEventData;

public class ProjectDiagramSelectionChangeListener extends SelectionChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditorPart editor;
	
	public ProjectDiagramSelectionChangeListener(DiagramManager manager) {
		super(manager);
		this.editor = manager.getEditor();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#selectionChanged(com.tomsawyer.graphicaldrawing.event.TSESelectionChangeEventData)
	 */
	@Override
	public void selectionChanged(TSESelectionChangeEventData eventData)
    {
		try{
			if (manager.isSelectedMultipleGraph() || eventData.getSource() == null) {
				return;
			}
			if (eventData.getType() == TSESelectionChangeEvent.GRAPH_SELECTION_CHANGED)
			{
				TSEGraph graph = (TSEGraph) eventData.getSource();
				if (graph.isSelected() && !manager.getGraphManager().hasSelected(true)) {
					onGraphSelected(graph);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    protected void onGraphSelected(TSEGraph tsGraph)
    {
     	setWorkbenchSelection(tsGraph);
    }
    
    /**
     * This method is added for testing purpose.
     * @param selection
     */
    public void setStatusLineManager(ISelection selection) {
// 		IStatusLineManager statusLineManager = editor.getEditorSite().getActionBars().getStatusLineManager();
//		if (selection instanceof IStructuredSelection) {
//			Object object = ((IStructuredSelection)selection).getFirstElement();
//			if(object instanceof TSEObject){
//				statusLineManager.setMessage("Selected Graph!");
//		  }
//		} else {
//			statusLineManager.setMessage("Selected unknown node!");
//		}
	}
    
    private void setWorkbenchSelection(final TSEObject object){
    	Display.getDefault().asyncExec(new Runnable(){
    		public void run() {
    			try {
    				ISelection selection = new StructuredSelection(object);
    				editor.getSite().getWorkbenchWindow().getSelectionService().getSelection();
    				ISelectionProvider selectionProvider = editor.getEditorSite().getSelectionProvider();
    				if(selectionProvider != null){
    					((MultiPageSelectionProvider)selectionProvider).firePostSelectionChanged(
    							new SelectionChangedEvent(selectionProvider, selection));
    				}
    				setStatusLineManager(selection);
    			}
    			catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    	});
    }
}