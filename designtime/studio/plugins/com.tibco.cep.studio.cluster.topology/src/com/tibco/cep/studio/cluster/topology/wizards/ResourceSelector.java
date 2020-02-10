package com.tibco.cep.studio.cluster.topology.wizards;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

public class ResourceSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private String selectedResourcePath;
	protected Set<String> extensions = new HashSet<String>();
	private IFile file;
	
	/**
	 * @param parent
	 * @param title
	 * @param message
	 * @param project
	 * @param sharedResPath
	 * @param extension
	 */
	public ResourceSelector(Shell parent,
			                String title, 
			                String message, 
			                IProject project, 
			                String sharedResPath, 
			                Set<String> extension) {
        super(parent);
        setTitle(title);
        setMessage(message);
        addFilter(new StudioProjectsOnly(project.getName()));
        extensions.addAll(extension);
        addFilter(new OnlyFileInclusionFilter(extensions));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        
        if(sharedResPath != null && !sharedResPath.trim().equals("")){
        	IPath path = Path.fromOSString("/"+sharedResPath);
        	IFile file = project.getFile(path);
        	setInitialSelection(file);
        }
    }
	
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
            if (selection[0] instanceof IFile) {
            	file = (IFile)selection[0];
            	String projectName = file.getProject().getName();
                selectedResourcePath = file.getFullPath().makeRelative().toOSString();
                selectedResourcePath = selectedResourcePath.substring(selectedResourcePath.indexOf(projectName)+projectName.length());
                selectedResourcePath = replace(selectedResourcePath,"\\","/");
                String statusMessage = MessageFormat.format(
                		Messages.getString("Resource_Selector_Message_format"),
                        new Object[] { (selectedResourcePath != null ? selectedResourcePath: "") });
                return new Status(Status.OK, ClusterTopologyPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, ClusterTopologyPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("Resource_Selector_Error_Message"), null);
    }

    public IFile getSelectResource(){
    	return file;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	//return selectedResourcePath;
    	return file.getLocation().toOSString();
    }
 }
