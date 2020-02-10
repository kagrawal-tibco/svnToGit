package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

/**
 * 
 * @author sasahoo
 *
 */
public class ArchiveAction extends Action implements IArchiveConstants{

	private EnterpriseArchiveEditor editor;
	private TableViewer viewer;
	
	/**
	 * @param editor
	 * @param viewer
	 */
	public ArchiveAction(EnterpriseArchiveEditor editor,TableViewer viewer){
		this.editor = editor;
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = viewer.getSelection();
		Object object = ((IStructuredSelection) selection).getFirstElement();
		if (object != null) {
			if(object instanceof BusinessEventsArchiveResource){
				editor.activePage(BUSINNESS_EVENTS_ARCHIVE);
//				editor.getBusinessEventsArchiveFormViewer().getViewer().setSelection(new StructuredSelection(((BusinessEventsArchiveResource)object)));
			}
			if(object instanceof SharedArchive){
				editor.activePage(SHARED_ARCHIVE);
//				editor.getSharedArchiveFormViewer().getViewer().setSelection(new StructuredSelection(((SharedArchive)object)));
			}
		}
	}
}
