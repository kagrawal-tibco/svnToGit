package com.tibco.cep.studio.ui.dialog;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

public class StudioResourceSelectionDialog extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	protected Set<String> extensions = new HashSet<String>();
    private Object object;
    
	/**
	 * Resource selection dialog that presents workspace resources, showing
	 * <i>only</i> resources with extensions that match displayTypes
	 * @param parent
	 * @param currentProjectName
	 * @param existingPath
	 */
	public StudioResourceSelectionDialog(Shell parent,String currentProjectName, String existingPath, ELEMENT_TYPES type) {
		this(parent, currentProjectName, existingPath, new ELEMENT_TYPES[] { type });
	}
	/**
	 * Resource selection dialog that presents workspace resources, showing
	 * <i>only</i> resources with extensions that match displayTypes
	 * @param parent
	 * @param currentProjectName
	 * @param existingPath
	 */
	public StudioResourceSelectionDialog(Shell parent,String currentProjectName, String existingPath, ELEMENT_TYPES[] element_TYPESs) {
        super(parent);
        setTitle("Select Resource");
        setMessage("Select Resource");
        addFilter(new StudioProjectsOnly(currentProjectName));
        if(element_TYPESs != null){
        	for (ELEMENT_TYPES type : element_TYPESs) {
        		extensions.add(IndexUtils.getFileExtension(type));
			}
        	addFilter(new OnlyFileInclusionFilter(extensions));
        	addFilter(new ProjectLibraryFilter(element_TYPESs));
        }
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        setInitialEntitySelection(currentProjectName, existingPath, element_TYPESs);
    }
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
	 */
	@Override
	public IStatus validate(Object[] selection) {
		 if (selection != null && selection.length == 1) {
	            if (selection[0] instanceof IFile) {
	            	object = selection[0];
	            	IFile file = (IFile)object;
	                String statusMessage = Messages.getString("Resource_Selector_Message_format", IndexUtils.getFullPath(file));
	                return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
	            }else if (selection[0] instanceof SharedEntityElement) {
	            	object = selection[0];
	                Entity sharedEntity = ((SharedEntityElement) object).getSharedEntity();
	                String statusMessage = MessageFormat.format(
	                		Messages.getString("Resource_Selector_Message_format"),
	                        new Object[] { (sharedEntity != null ? sharedEntity.getFullPath() : "") });
	                return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
	            }
	        }
	        return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID,Status.ERROR, "Select Resource", null);
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return object;
    }
}
