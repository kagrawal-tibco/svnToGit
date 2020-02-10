package com.tibco.cep.ws.component;

import java.text.MessageFormat;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ws.WsPlugin;
/**
 * 
 * @author majha
 *
 */
public abstract class FileSelector extends BaseObjectSelector implements  ISelectionStatusValidator {

	private String locationURI;
	private Set<String> extensions;
	private boolean showProjectLib = true;
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param ext
	 * @param showProjectLib
	 */
	public FileSelector(Shell parent, 
			            String currentProjectName, 
			            Set<String> ext, 
			            boolean showProjectLib) {
		super(parent);
		this.showProjectLib = showProjectLib;
		init(currentProjectName, ext);
	}
	
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseConcept
	 * @param superConceptPath
	 */
	public FileSelector(Shell parent,
			String currentProjectName, Set<String> ext) {
		super(parent);
		init(currentProjectName, ext);
	}

	public void init(String currentProjectName, Set<String> ext) {
		setTitle(getTitle());
		setMessage(getMessage());
		addFilter(new StudioProjectsOnly(currentProjectName));
		extensions = ext;
		addFilter(new FileInclusionFilter(extensions, isFileSerachInSubDirectoryAllowed(), showProjectLib));
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
    protected abstract boolean isFileSerachInSubDirectoryAllowed();
    protected abstract String getLocationURI(Object selction);
 }
