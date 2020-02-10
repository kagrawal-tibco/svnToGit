package com.tibco.cep.studio.ui.dialog;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.Messages;

public class StudioProjectSelector extends ResourceSelector implements  ISelectionStatusValidator {

	private IProject project;
	
	public StudioProjectSelector(Shell parent, String currentProjectName) {
		this(parent, currentProjectName, true);
	}
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param showProjectContents
	 */
	public StudioProjectSelector(Shell parent, String currentProjectName, boolean showProjectContents) {
        super(parent);
        setTitle(Messages.getString("Studio.project.selector.title"));
        setMessage(Messages.getString("Studio.project.selector.message"));
        addFilter(new StudioProjectsOnly());
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        if(currentProjectName!=null){
        	setInitialSelection(ResourcesPlugin.getWorkspace().getRoot().getProject(currentProjectName.trim()));
        }
        if (!showProjectContents) {
        	addFilter(new ViewerFilter() {
				
				@Override
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return element instanceof IProject;
				}
			});
        }
     }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
            if (selection[0] instanceof IProject) {
            	project = (IProject)selection[0];
                String statusMessage = Messages.getString("Studio.project.selector.status.message", project.getName());
                return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR,Messages.getString("Studio.project.selector.status.error"), null);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return project;
    }
 }
