package com.tibco.cep.studio.tester.ui.view;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SearchPattern;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.AbstractFilteredResourceElementSelector;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * @see DestinationSelector
 * This Selector will allow filtering Destinations based on entry
 * @author sasahoo
 *
 */
public class TesterFilteredDestinationSelector extends AbstractFilteredResourceElementSelector implements ISelectionStatusValidator {
	
	private Destination destination;
	private Destination baseDestination;
	private Channel channel;
	private SelectFileFilter filter;
	private IProject project;
	 
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseDestination
	 */
	public TesterFilteredDestinationSelector(Shell parent, String currentProjectName, Destination baseDestination) {
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
        project =  ResourcesPlugin.getWorkspace().getRoot().getProject(currentProjectName);
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
                return new Status(Status.OK, StudioTesterUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, StudioTesterUIPlugin.PLUGIN_ID, Status.ERROR, null, null);
    }

    @Override
    public Object getFirstResult() {
    	return destination;
    }
    
    protected void applyFilter() {
    	String pattern = getPattern();
    	if (pattern != null) {
    		if (filter != null) {
    			treeViewer.removeFilter(filter);
    		}
    		filter = new SelectFileFilter();
    		treeViewer.addFilter(filter);
    		treeViewer.refresh();
    		treeViewer.expandAll();
    	}
	}
	
    class SelectFileFilter extends ViewerFilter {

    	private SearchPattern namePattern;
    	protected boolean visible = false;
    	
    	/**
    	 * @param inclusions
    	 * @param showSubNode
    	 */
    	public SelectFileFilter() {
    		addResourceElementMatcher(project);
    	}
    	
    	/**
    	 * @param viewer
    	 * @param parentElement
    	 * @param element
    	 */
    	public boolean select(Viewer viewer, Object parentElement, Object element) {
    		if (element instanceof IAdaptable) {
    			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
    			if (res instanceof IFile) {
    				IFile file = (IFile) res;
    				return matchElement(file);
    			}
    			if (res instanceof IFolder){
    			    IFolder folder = (IFolder)res;
    			    visible = false;
                    return isVisible(folder);
    			}
    		}
    		if(element instanceof SharedElementRootNode){
    			return true;
    		}else if(element instanceof DesignerElement){
    			return true;
    		}else{
    			return showSubNode(element);
    		}
    	}
    	
    	/* (non-Javadoc)
    	 * @see com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter#showSubNode(java.lang.Object)
    	 */
    	protected boolean showSubNode(Object element){
    		if(!(element instanceof IResource)){
    			if (element instanceof StudioNavigatorNode) {
    				element = ((StudioNavigatorNode) element).getEntity();
    			}
    			if (element instanceof Destination) {
    				if (matches(((Destination)element).getName())){
    					return true;
    				}
    			}
    			return false;
    		}
    		return true;
    	}
    	
    	/**
    	 * 
    	 * @param element
    	 * @return
    	 */
    	protected boolean isVisible(Object element) {
    		Object[] object = CommonUtil.getResources((IFolder) element);
    		for(Object obj : object){
    			if(obj instanceof IFolder){
    				isVisible(obj);
    			}
    			if(obj instanceof IFile){
    				IFile file = (IFile)obj;
    				if(matchElement(file)){
    					visible = true;
    					break;
    				}
    			}
    		}
    		return visible;
    	}
   	
    	/**
    	 * 
    	 * @param element
    	 * @return
    	 */
    	protected  boolean exists(Object element) {
    		if (element == null) {
    			return false;
    		}
    		if (element instanceof IResource) {
    			return ((IResource) element).exists();
    		}
    		
    		return true;
    	}
    	
    	protected boolean matchElement(Object obj) {
    		IFile file = (IFile)obj;
    		Channel channel = (Channel)CommonIndexUtils.getEntity(project.getName(), IndexUtils.getFullPath(file));
    		for (Destination destination : channel.getDriver().getDestinations()) {
    			if (matches(destination.getName())) {
    				return true;
    			}
    		}
    		return false;
    	}

    	protected void addResourceElementMatcher(IProject searchContainer) {
        	String stringPattern = getPattern();
        	if (stringPattern == null) {
        		stringPattern = "";
        	}
    		String filenamePattern;
    		namePattern = new SearchPattern();
    		namePattern.setPattern(stringPattern);
    		int sep = stringPattern.lastIndexOf(IPath.SEPARATOR);
    		if (sep != -1) {
    			filenamePattern = stringPattern.substring(sep + 1, stringPattern.length());
    			if ("*".equals(filenamePattern)) //$NON-NLS-1$
    				filenamePattern= "**"; //$NON-NLS-1$
    			
    			if (sep > 0) {
    				if (filenamePattern.length() == 0) // relative patterns don't need a file name
    					filenamePattern= "**"; //$NON-NLS-1$
    			}
    		} else {
    			filenamePattern= stringPattern;
    		}
    		
    		int lastPatternDot = filenamePattern.lastIndexOf('.');
    		if (lastPatternDot != -1) {
    			char last = filenamePattern.charAt(filenamePattern.length() - 1);
    			if (last != ' ' && last != '<' && getMatchRule() != SearchPattern.RULE_EXACT_MATCH) {
    				namePattern.setPattern(filenamePattern.substring(0, lastPatternDot));
    			}
    		} else {
    			namePattern.setPattern(filenamePattern);
    		}
        }
        
    	public int getMatchRule() {
    		return namePattern.getMatchRule();
    	}
    	protected boolean matches(String text) {
    		return namePattern.matches(text);
    	}
    }
    
    class DestinationFilter extends OnlyFileInclusionFilter {
    	
    	public DestinationFilter(Set<String> inclusions) {
    		super(inclusions);
    	}
    	
    	/* (non-Javadoc)
    	 * @see com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter#showSubNode(java.lang.Object)
    	 */
    	protected boolean showSubNode(Object element){
    		if(!(element instanceof IResource)){
    			if (element instanceof StudioNavigatorNode) {
    				element = ((StudioNavigatorNode) element).getEntity();
    			}
    			if (element instanceof Destination) {
    				return true;
    			}
    			return false;
    		}
    		return true;
    	}
    }
}