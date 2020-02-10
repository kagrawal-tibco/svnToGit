/**
 * 
 */
package com.tibco.cep.studio.ui.forms.components;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;

/**
 * @author aathalye/sasahoo
 *
 */
public class DestinationSelector extends AbstractResourceElementSelector implements	ISelectionStatusValidator {
	
	private Destination destination;
	private Destination baseDestination;
	private Channel channel;
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseDestination
	 */
	public DestinationSelector(Shell parent, String currentProjectName, Destination baseDestination) {
        super(parent);
        setTitle(Messages.getString("event.destination.selector.title"));
        setMessage(Messages.getString("declration_selector_select_resource"));
        this.baseDestination = baseDestination;
        addFilter(new StudioProjectsOnly(currentProjectName));
        Set<String> extensions = new HashSet<String>();
        extensions.add("channel");
        addFilter(new DestinationFilter(extensions));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.CHANNEL));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        if(baseDestination != null){
        	channel = (Channel)baseDestination.eContainer().eContainer();
        	setInitialSelection(IndexUtils.getFile(channel.getOwnerProjectName(), channel));
        }
        updateOKStatus();
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.BaseObjectSelector#create()
	 */
	public void create() {
		super.create();
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				if(baseDestination != null) {
					try{
						treeViewer.expandToLevel( ((StructuredSelection)treeViewer.getSelection()).getFirstElement(), 2);
						TreeItem[] items = treeViewer.getTree().getSelection();
						if (items.length > 0) {
							TreeItem channelTree = items[0];
							selectDestination(channelTree.getItems(), baseDestination.getName());
//							String statusMessage = MessageFormat.format("Selected Destination: {0}", new Object[] { baseDestination.getName() }); 
							IStatus status = new Status(IStatus.ERROR,StudioUIPlugin.PLUGIN_ID,IStatus.ERROR, null, null);
							updateStatus(status);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
					/* (non-Javadoc)
					 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
					 */
					public void selectionChanged(SelectionChangedEvent event) {
						setResult(((IStructuredSelection) event.getSelection()).toList());
						updateOKStatus();
					}
				});
			}});
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector#setOKStatusAfterElementSelection()
	 */
	protected void setOKStatusAfterElementSelection(){
		//TODO:Nothing 
    }
	 
	 protected void addTreeViewerSelectionListener(){
		 //TODO:Nothing 
	 }
	
	/**
	 * @param treeItems
	 * @param destinationName
	 */
	 private void selectDestination(final TreeItem[] treeItems, final String destinationName){
		 Display.getDefault().asyncExec(new Runnable(){
			 @Override
			 public void run() {
				 ChannelDestinationNode  destNode = null;
				 for(TreeItem i:treeItems){
					 String name = ((ChannelDestinationNode)i.getData()).getEntity().getName();
					 if(name.equalsIgnoreCase(destinationName)){
						 destNode =  (ChannelDestinationNode)i.getData();
						 break;
					 }
				 }
				 if (destNode != null) {
					 treeViewer.setSelection(new StructuredSelection(destNode),true);
				 }
			 }});
	 }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
            if (selection[0] instanceof ChannelDestinationNode) {
            	destination = (Destination)((ChannelDestinationNode)selection[0]).getEntity();       	
                String statusMessage = MessageFormat.format(
                        "Selected Destination: {0}",
                        new Object[] { (destination != null ? destination.getName() : "") }); //$NON-NLS-1$
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, null, null);
    }

    @Override
    public Object getFirstResult() {
    	return destination;
    }
}