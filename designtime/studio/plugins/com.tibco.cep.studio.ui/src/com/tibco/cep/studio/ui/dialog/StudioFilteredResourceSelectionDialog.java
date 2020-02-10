package com.tibco.cep.studio.ui.dialog;

import java.text.MessageFormat;
import java.util.Collection;
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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SearchPattern;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.RuleFunctionOnlyFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.filter.VirtualRuleFunctionOnlyFilter;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioFilteredResourceSelectionDialog extends AbstractFilteredResourceElementSelector implements  ISelectionStatusValidator {

	protected Set<String> extensions = new HashSet<String>();
    private Object object;
    private SelectFileFilter filter;
    private IProject project;
   
    /**
     * @param parent
     * @param currentProjectName
     * @param existingPath
     * @param displayTypes
     * @param isRuleFunction
     * @param onlyVRF
     * @param onlyRF
     */
    public StudioFilteredResourceSelectionDialog(Shell parent, 
    		                                     String currentProjectName, 
    		                                     String existingPath, 
    		                                     ELEMENT_TYPES[] displayTypes, 
    		                                     boolean isRuleFunction, 
    		                                     boolean onlyVRF, 
    		                                     boolean onlyRF) {
    	super(parent);
    	setTitle("Select Resource");
    	setMessage("Select Resource");
    	addFilter(new StudioProjectsOnly(currentProjectName));
    	boolean isRuleType = false;
    	if(displayTypes != null){
    		for (ELEMENT_TYPES type : displayTypes) {
    			if (type == ELEMENT_TYPES.RULE_FUNCTION || type == ELEMENT_TYPES.RULE) {
    				isRuleType = true;
    			}
    			extensions.add(IndexUtils.getFileExtension(type));
    		}		
    		addFilter(new OnlyFileInclusionFilter(extensions));
    		addFilter(new ProjectLibraryFilter(displayTypes));
    		if (onlyRF) {
    			addFilter(new RuleFunctionOnlyFilter(extensions));
    		}
    		if (onlyVRF) {
        		addFilter(new VirtualRuleFunctionOnlyFilter(extensions));
        	}
    	}
    	setValidator(this);
    	setInput(ResourcesPlugin.getWorkspace().getRoot());
    	project =  ResourcesPlugin.getWorkspace().getRoot().getProject(currentProjectName);
    	if(isRuleType){
    		if (existingPath != null && !existingPath.isEmpty()) {
    			RuleElement ruleElement = IndexUtils.getRuleElement(currentProjectName, existingPath, isRuleFunction ? ELEMENT_TYPES.RULE_FUNCTION : ELEMENT_TYPES.RULE);
    			if(ruleElement != null){
    				IFile file = IndexUtils.getFile(currentProjectName, ruleElement);
    				setInitialSelection(file);
    			}
    		}
    	} else {
    		setInitialEntitySelection(currentProjectName, existingPath, displayTypes);
    	}
    }
    
    /**
     * @param parent
     * @param currentProjectName
     * @param existingPath
     * @param extensionList
     */
    public StudioFilteredResourceSelectionDialog(Shell parent, 
										    	 String currentProjectName, 
										    	 String existingPath, 
										    	 Collection<String> extensionList, ViewerFilter[] filters) {
    	super(parent);
    	setTitle("Select Resource");
    	setMessage("Select Resource");
    	addFilter(new StudioProjectsOnly(currentProjectName));
     	
    	extensions.addAll(extensionList);
    	addFilter(new OnlyFileInclusionFilter(extensions));
    	
    	for (ViewerFilter filter: filters) {
    		addFilter(filter);
    	}
		
    	setValidator(this);
    	setInput(ResourcesPlugin.getWorkspace().getRoot());
    	project =  ResourcesPlugin.getWorkspace().getRoot().getProject(currentProjectName);
    	
    	IFile file = project.getFile(new Path(existingPath));
    	if (file != null) {
    		setInitialSelection(file);
    	}
    	
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
    	
    	/**
    	 * @param element
    	 * @return
    	 */
    	protected boolean showSubNode(Object element){
    		if(!(element instanceof IResource)){
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
    		String name = file.getName();
			if (matches(name)) {
				return true;
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
}