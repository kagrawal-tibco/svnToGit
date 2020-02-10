package com.tibco.cep.studio.dbconcept.component;

import java.text.MessageFormat;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
/**
 * 
 * @author majha
 *
 */
public abstract class FileSelector extends BaseObjectSelector implements  ISelectionStatusValidator {

	private String locationURI;
	private Set<String> extensions;
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseConcept
	 * @param superConceptPath
	 */
	public FileSelector(Shell parent,
						   String currentProjectName, Set<String> ext) {
        super(parent);
        setTitle(getTitle());
        setMessage(getMessage());
        addFilter(new StudioProjectsOnly(currentProjectName));
        extensions = ext;
        addFilter(new FileInclusionFilter(extensions, isFileSerachInSubDirectoryAllowed()));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        setExpandLevel(TreeViewer.ALL_LEVELS);
     }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
        	locationURI= getLocationURI(selection[0]);
            if (locationURI != null) {
                String statusMessage = MessageFormat.format(getSelectionMsgFormat(),
                        new Object[] { (locationURI != null ? locationURI: "") });

                return new Status(Status.OK, ModulePlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, ModulePlugin.PLUGIN_ID,
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
    protected abstract boolean isFileSerachInSubDirectoryAllowed();
    protected abstract String getLocationURI(Object selction);
 }
