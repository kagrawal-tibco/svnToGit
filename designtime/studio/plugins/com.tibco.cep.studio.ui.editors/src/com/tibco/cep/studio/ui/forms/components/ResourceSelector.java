package com.tibco.cep.studio.ui.forms.components;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;

/**
 * 
 * @author sasahoo
 *
 */
public class ResourceSelector extends AbstractResourceElementSelector implements ISelectionStatusValidator {

	private IFile resource;
	
	
	public ResourceSelector(Shell parent,IProject currentProject, String resourcePath) {
        super(parent);
        setTitle("Select Resource");
        setMessage("Select resource to be associated with");

        // Only include Designer nature projects
        addFilter(new StudioProjectsOnly(currentProject.getName()));
        
        // exclude all files except BE core resources
        Set<String> extensions = new HashSet<String>();
        extensions.add("concept");
        extensions.add("scorecard");
        extensions.add("statemachine");
        extensions.add("event");
        extensions.add(CommonIndexUtils.TIME_EXTENSION);
        extensions.add("channel");
        extensions.add("rule");
        extensions.add("rulefunction");
        extensions.add("rulefunctionimpl");
        extensions.add(IndexUtils.EAR_EXTENSION);
        extensions.add("conceptview");
        extensions.add("eventview");
        extensions.add("projectview");
        extensions.add("project");
         
        addFilter(new ResourceFileInclusionFilter(extensions));
        
        addFilter(new EObjectFilter());
        setValidator(this);
        
        setInput(ResourcesPlugin.getWorkspace().getRoot());

        if(resourcePath!=null && !resourcePath.equalsIgnoreCase("")){
        	final IPath path = Path.fromOSString(resourcePath);
        	IFile file = currentProject.getFile(path);
        	setInitialSelection(file);
        }
    }
	
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
            if (selection[0] instanceof IFile) {
            	resource =  (IFile)selection[0];
                String statusMessage = MessageFormat.format("Selected Resource: {0}", new Object[] { (resource != null ? resource .getFullPath() : "") }); 
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID,
                        Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
                Status.ERROR, "Select Resource",
                null);
    }

    @Override
    public int open() {
        return super.open();
    }

    @Override
    public Object getFirstResult() {
    	return resource;
    }
 }
