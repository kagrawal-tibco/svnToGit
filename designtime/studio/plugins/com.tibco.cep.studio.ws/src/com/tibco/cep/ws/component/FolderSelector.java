package com.tibco.cep.ws.component;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ws.WsPlugin;
/**
 * 
 * @author majha
 *
 */
public abstract class FolderSelector extends BaseObjectSelector implements  ISelectionStatusValidator {

	private String locationURI;
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseConcept
	 * @param superConceptPath
	 */
	public FolderSelector(Shell parent,
						   String currentProjectName) {
        super(parent);
        setTitle(getTitle());
        setMessage(getMessage());
        addFilter(new StudioProjectsOnly(currentProjectName));
        addFilter(new FolderInclusionFilter());
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        setExpandLevel(2);
     }
	

	
	
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
            if (selection[0] instanceof IFolder) {
                locationURI = getLocationURI(selection[0]);
                String statusMessage = MessageFormat.format(getSelectionMsgFormat(),
                        new Object[] { (locationURI != null ? locationURI: "") });

                return new Status(Status.OK, WsPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, WsPlugin.PLUGIN_ID,
        				  Status.ERROR, getSelectionErrorMsg(), null);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return locationURI;
    }
    
    protected abstract String getTitle();
    protected abstract String getMessage();
    protected abstract String getSelectionMsgFormat();
    protected abstract String getSelectionErrorMsg();
    protected abstract String getLocationURI(Object selction);
 }
