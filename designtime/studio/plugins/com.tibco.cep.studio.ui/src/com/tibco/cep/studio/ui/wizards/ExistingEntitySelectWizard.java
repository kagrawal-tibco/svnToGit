/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.EntitySelectFilter;

/**
 * @author mgujrath
 *
 */
public class ExistingEntitySelectWizard extends AbstractResourceElementSelector implements ISelectionStatusValidator{

	public ExistingEntitySelectWizard(Shell shell, String currentProjectName,String basePath, String path) {
		// TODO Auto-generated constructor stub
		
		super(shell);
        setTitle("Select Entity");
        setMessage("Select Resource");
        addFilter(new StudioProjectsOnly(currentProjectName));
        Set<String> extensions = new HashSet<String>();
        extensions.add("concept");
        extensions.add("event");
        extensions.add("scorecard");
        addFilter(new EntitySelectFilter(extensions, basePath, path,currentProjectName));
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
   //     setInitialEntitySelection(currentProjectName, path, ELEMENT_TYPES.SIMPLE_EVENT);
        
		
	}

	@Override
	public IStatus validate(Object[] selection) {
		// TODO Auto-generated method stub
		return null;
	}

}
