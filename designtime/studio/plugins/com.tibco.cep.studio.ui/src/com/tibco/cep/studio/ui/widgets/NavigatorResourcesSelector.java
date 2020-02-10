package com.tibco.cep.studio.ui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author sasahoo
 *
 */
public class NavigatorResourcesSelector extends SelectionStatusDialog {

    protected String emptyListMessage = "No entries available.";
    protected String emptySelectionMessage = "Select Resource";
    
    protected CommonCheckBoxTreeViewer treeViewer = null;
	private Object input = null;
    @SuppressWarnings("unused")
	private int expandLevel = 0;
    @SuppressWarnings("unused")
	private boolean noSelectionIsOK = false;
    private ISelectionStatusValidator validator = null;
    private List<ViewerFilter> viewerFilters = null;
    private ViewerSorter viewerSorter = null;
    private INavigatorContentService navigatorService = null;
    private boolean treeIsEmpty = false;
    public static final int CLEAR = IDialogConstants.CLIENT_ID + 1;
    private Button clearBtn;
    private boolean clearEnabled = false;
    private NavigatorTreeGroup resourceGroup;
    protected IFile[] existingInstances = null;
    protected Set<Object> checkedElements = null;
    protected Set<Object> grayededElements = null;

    /**
     * @param parent
     */
    @SuppressWarnings("rawtypes")
	public NavigatorResourcesSelector(Shell parent) {
        super(parent);
        setHelpAvailable(false);
        setResult(new ArrayList(0));
        setStatusLineAboveButtons(true);
    }

    /**
     * @param input
     */
    public void setInput(Object input) {
    	this.input = input;
    }

    /**
     * @param noSelectionIsOK
     */
    public void setNoSelectionIsOK(boolean noSelectionIsOK) {
        this.noSelectionIsOK = noSelectionIsOK;
    }

     /**
     * @param expandLevel
     */
    public void setExpandLevel(int expandLevel) {
        this.expandLevel = expandLevel;
    }

    /**
     * @param selection
     */
    public void setInitialSelection(Object selection) {
        setInitialSelections(new Object[] { selection });
    }

    /**
     * @param filter
     */
    public void addFilter(ViewerFilter filter) {
        if (viewerFilters == null) {
            viewerFilters = new ArrayList<ViewerFilter>();
        }
        viewerFilters.add(filter);
    }
    
    /**
     * @param sorter
     */
    public void setSorter(ViewerSorter sorter) {
        this.viewerSorter = sorter;
    }

    /**
     * @param validator
     */
    public void setValidator(ISelectionStatusValidator validator) {
        this.validator = validator;
    }

    /**
     * @return
     */
    public ISelectionStatusValidator getValidator() {
        return validator;
    }

    /**
     * @param msg
     */
    public void setEmptyListMessage(String msg) {
        emptyListMessage = msg;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.TrayDialog#close()
     */
    @Override
    public boolean close() {
        if (navigatorService != null) {
            navigatorService.dispose();
            navigatorService = null;
        }

        if (treeViewer != null) {
            treeViewer = null;
        }
        return super.close();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        composite.setLayout(layout);
        parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label messageLabel = createMessageArea(composite);
        messageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         
         NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
         navigatorService = fact.createContentService("com.tibco.cep.studio.projectexplorer.view", treeViewer); 

         if (navigatorService != null) {
             INavigatorActivationService service = navigatorService.getActivationService();
             if (service != null) {
                 service.deactivateExtensions(new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false); 
             }
             resourceGroup = new NavigatorTreeGroup(composite, input,
								            		 navigatorService.createCommonContentProvider(),
								            		 navigatorService.createCommonLabelProvider(),
								            		 getResourceProvider(IResource.FILE), 
								            		 WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(), SWT.NONE,
								                     false){
            	    /* (non-Javadoc)
            	     * @see com.tibco.cep.studio.ui.editors.utils.ResourcesTreeGroup#checkStateChanged(org.eclipse.jface.viewers.CheckStateChangedEvent)
            	     */
            	    public void checkStateChanged(final CheckStateChangedEvent event) {
            	    	super.checkStateChanged(event);
            	    	updateOKStatus();
            	    }
             };
             
             treeViewer = resourceGroup.getTreeViewer();
             treeViewer.setContentProvider(navigatorService.createCommonContentProvider());
             treeViewer.setLabelProvider(navigatorService.createCommonLabelProvider());

             if (viewerFilters != null) {
                 for (ViewerFilter filter : viewerFilters) {
                     treeViewer.addFilter(filter);
                 }
             }
             if (viewerSorter != null) {
                 treeViewer.setSorter(viewerSorter);
             }
             treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
             
             if (treeIsEmpty) {
            	 messageLabel.setEnabled(false);
            	 treeViewer.getTree().setEnabled(false);
            	 updateStatus(new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, emptyListMessage, null));
             }else{
            	 if(existingInstances !=null && existingInstances.length >0){
            		 for(IFile element:existingInstances ){
            			 resourceGroup.initialCheckTreeItem(element);
            		 }
            	 }
             }
             updateOKStatus();
         }
         final Button noSelectionCheckButton = new Button(composite, SWT.CHECK);
         noSelectionCheckButton.setText(Messages.getString("Selector_UnSelection_Label"));
         noSelectionCheckButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(noSelectionCheckButton.getSelection() ==  true){
					for(Object element: getTreeViewer().getCheckedElements()){
						getTreeViewer().setChecked(element, false);
					}   	
					treeViewer.getTree().setEnabled(false);
					if(checkedElements != null){
						checkedElements.clear();
					}
					updateStatus(new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, "", null));
				}else{
					treeViewer.getTree().setEnabled(true);
				}
			}});
        return composite;
    }
     
    /**
     * @param resourceType
     * @return
     */
    private ITreeContentProvider getResourceProvider(final int resourceType) {
        return new WorkbenchContentProvider() {
            /* (non-Javadoc)
             * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
             */
            @SuppressWarnings({ "rawtypes", "unchecked" })
			public Object[] getChildren(Object o) {
                if (o instanceof IContainer) {
                    IResource[] members = null;
                    try {
                        members = ((IContainer) o).members();
                    } catch (CoreException e) {
                        return new Object[0];
                    }
                    ArrayList results = new ArrayList();
                    for (int i = 0; i < members.length; i++) {
                        if ((members[i].getType() & resourceType) > 0) {
                            results.add(members[i]);
                        }
                    }
                    return results.toArray();
                } 
                if (o instanceof ArrayList) {
                    return ((ArrayList) o).toArray();
                } 
                return new Object[0];
            }
        };
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#computeResult()
     */
    @Override
    protected void computeResult() {
        setResult(((IStructuredSelection) treeViewer.getSelection()).toList());
    }

    protected void updateOKStatus() {
        IStatus status = null;
        if (!treeIsEmpty) {
        	if(treeViewer.getGrayedElements().length > 0 && treeViewer.getCheckedElements().length == 0){
        		updateStatus(new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, emptySelectionMessage, null));
        		return;
        	}
        	if(treeViewer.getCheckedElements().length == 0){
        		//Check for unselecting the project
        		if(existingInstances != null && existingInstances.length > 0){
        			if(checkedElements != null){
						checkedElements.clear();
					}
        			updateStatus(new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, "", null));
        		}else{
        			updateStatus(new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, emptySelectionMessage, null));
        		}
        	}else{
         		addGrayedElements();
        		addCheckedElements();
        		if(checkedElements == null || checkedElements.size() == 0){
        			//Check for unselecting the project
            		if(existingInstances != null && existingInstances.length > 0){
            			if(checkedElements != null){
    						checkedElements.clear();
    					}
            			updateStatus(new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, "", null));
            		}else{
            			updateStatus(new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, emptySelectionMessage, null));
            		}
        		}
        		else{
        			updateStatus(new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, "", null));
        		}
        	}
        } else {
        	status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.ERROR, emptyListMessage, null);
        }
        if (status != null)
        	updateStatus(status);
    }

    //Override this
    /**
     * Checked elements
     */
    protected void addCheckedElements(){}
    
    //Override this
    /**
     * Grayed elements
     */
    protected void addGrayedElements(){}
    
    protected boolean isDomainAssociationsPresent = false;
    
    /**
     * @return
     */
    protected boolean getOKButtonStatus() {
        Button okButton = getOkButton();
        if (okButton != null && !okButton.isDisposed()) {
            return okButton.isEnabled();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.SelectionDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);

        if (isClearEnabled()) {
            clearBtn = createButton(parent, CLEAR,
                    "Clear", false);
            clearBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    setReturnCode(CLEAR);
                    // Clear the result
                    setResult(Collections.EMPTY_LIST);
                    close();
                }
            });

        }
    }
    
    /**
     * @return
     */
    public boolean isClearEnabled() {
    	return clearEnabled;
    }

    /**
     * @param clearEnabled
     */
    public void setClearEnabled(boolean clearEnabled) {
        this.clearEnabled = clearEnabled;
    }
    
    /**
     * @return
     */
    public NavigatorTreeGroup getResourceGroup() {
		return resourceGroup;
	}
    
    /**
     * @return
     */
    public CommonCheckBoxTreeViewer getTreeViewer() {
		return treeViewer;
	}

}