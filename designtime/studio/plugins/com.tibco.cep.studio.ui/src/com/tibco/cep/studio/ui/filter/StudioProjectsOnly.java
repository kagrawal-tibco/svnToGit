package com.tibco.cep.studio.ui.filter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.util.CommonUtil;

public class StudioProjectsOnly extends ViewerFilter {
	
	private String currentProject;
	
	public StudioProjectsOnly(){
	}
	
	public StudioProjectsOnly(String currentProject){
		this.currentProject = currentProject;
	}

    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean ret = true;
        
        if (parentElement instanceof IWorkspaceRoot) {
            IProject project = null;

            // Check if the element is a project
            if (element instanceof IProject) {
                project = (IProject) element;

            } else if (element instanceof IAdaptable) {
                project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
            }

            if (project != null) {
                // Check the nature of the project
                try {
                    if(checkProjectNature(project)){
                    	ret = true;
                    }else{
                    	ret = false;
                    }
                    
                } catch (CoreException e) {
                   e.printStackTrace();
                }
            } else {
                ret = false;
            }
        }

        return ret;
    }
    
    /**
     * @param project
     * @return
     * @throws CoreException
     */
    private boolean checkProjectNature(IProject project) throws CoreException{
    	if(currentProject == null){
    		return checkStudioProjectNature(project);
    	}else{
    		return checkStudioProjectNature(project) && project.getName().equalsIgnoreCase(currentProject);
    	}
    }
    
    /**
     * @param project
     * @return
     * @throws CoreException
     */
    private boolean checkStudioProjectNature(IProject project) throws CoreException{
    	return project.isOpen() && CommonUtil.isStudioProject(project);
    }
}
