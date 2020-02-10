package com.tibco.cep.studio.ui.editors.channels;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

public class SharedResourcesSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private String selectedSharedResourcePath;
	protected Set<String> extensions = new HashSet<String>();
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param sharedResPath
	 */
	public SharedResourcesSelector(Shell parent,IProject project, String sharedResPath, Set<String> extension) {
        super(parent);
        setTitle(Messages.getString("Shared_Resource_Selector_Dialog_Title"));
        setMessage(Messages.getString("Shared_Resource_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(project.getName()));
        extensions.addAll(extension);
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(extension));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        
        if(sharedResPath != null && !sharedResPath.trim().equals("")){
        	IPath path = Path.fromOSString("/"+sharedResPath);
        	try {
	        	IFile file = project.getFile(path);
	        	setInitialSelection(file);
        	} catch (IllegalArgumentException iae) {
        	}
        }
    }
	
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
            if (selection[0] instanceof IFile) {
            	IFile file = (IFile)selection[0];
//            	String projectName = file.getProject().getName();
            	IPath resPath = file.getFullPath().removeFirstSegments(1);
            	if (file.isLinked(IResource.CHECK_ANCESTORS)) {
            		resPath = resPath.removeFirstSegments(1);
            	}
            	selectedSharedResourcePath = resPath.makeAbsolute().toOSString();
//                selectedSharedResourcePath = file.getFullPath().makeRelative().toOSString();
//                selectedSharedResourcePath = selectedSharedResourcePath.substring(selectedSharedResourcePath.indexOf(projectName)+projectName.length());
                selectedSharedResourcePath = replace(selectedSharedResourcePath,"\\","/");
                String statusMessage = MessageFormat.format(
                		Messages.getString("Shared_Resource_Selector_Message_format"),
                        new Object[] { (selectedSharedResourcePath != null ? selectedSharedResourcePath: "") });
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }else if (selection[0] instanceof SharedElement) {
            	SharedElement sharedElement = (SharedElement) selection[0];
            	String entryPath = sharedElement.getEntryPath();
            	String fileName = sharedElement.getFileName();
            	selectedSharedResourcePath = "/" + entryPath + fileName;
            	selectedSharedResourcePath = replace(selectedSharedResourcePath,"\\","/");
            	String statusMessage = MessageFormat.format(
            			Messages.getString("Shared_Resource_Selector_Message_format"),
            			new Object[] { (selectedSharedResourcePath != null ? selectedSharedResourcePath: "") });
            	return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("Shared_Resource_Selector_Error_Message"), null);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return selectedSharedResourcePath;
    }
 }
