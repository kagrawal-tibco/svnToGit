package com.tibco.cep.studio.dbconcept.component;

import java.text.MessageFormat;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.ui.filter.FolderInclusionFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
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
    
    
	/**
	 * create "Create folder"
	 * @param parent
	 */
	public void createMkDirButtonForButtonBar (Composite parent) {
		createMkDirButton = createButton(parent, 1200, "Create New Folder...",true);
		createMkDirButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				createFolder(e);
			}
		});
	}
	
	
	/**
	 * 
	 * @param e
	 * 
	 * User is allowed to create folder while importing db concpets...
	 * 
	 */
	public void  createFolder(SelectionEvent e){
		
		IStructuredSelection selection= (IStructuredSelection) getTreeViewer().getSelection();
		IContainer selectedContainer= null;
		if (selection.size() == 1) {
			Object first= selection.getFirstElement();
			if (first instanceof IContainer) {
				selectedContainer= (IContainer) first;
			}
		}
		
		// New folder dialog
		ExtendedNewFolderDialog newFolderDialog = new ExtendedNewFolderDialog(getShell(),
				selectedContainer);
		
		if (newFolderDialog.open() == Window.OK) {
			TreeViewer treeViewer = getTreeViewer();
			treeViewer.refresh(selectedContainer);
			Object createdFolder = newFolderDialog.getResult()[0];
			treeViewer.reveal(createdFolder);
			treeViewer.setSelection(new StructuredSelection(createdFolder));
		}
	}    
    
    protected abstract String getTitle();
    protected abstract String getMessage();
    protected abstract String getSelectionMsgFormat();
    protected abstract String getSelectionErrorMsg();
    protected abstract String getLocationURI(Object selction);
 }
