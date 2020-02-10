/**
 * 
 */
package com.tibco.cep.studio.ui.forms.components;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * @author mgujrath
 *
 */
public class TestDataSelector extends AbstractResourceElementSelector implements ISelectionStatusValidator {

	String projectName;
	IFile testDataFile;
	public TestDataSelector(Shell parent, String projectName,String type) {
		super(parent);
		this.projectName = projectName;
		setTitle("Test Data Selector");
	    setMessage("Select Test Data");
	    setInput(ResourcesPlugin.getWorkspace().getRoot());
	    addFilter(new StudioProjectsOnly(projectName));
	    Set<String> extensions = new HashSet<String>();
	    if("concept".equalsIgnoreCase(type)){
	    	extensions.add("concepttestdata");
	    }else if("event".equalsIgnoreCase(type)){
	    	extensions.add("eventtestdata");
	    }
	    addFilter(new OnlyFileInclusionFilter(extensions));
	    setValidator(this);
	}
	@Override
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
        	Object obj = selection[0];
            if (obj instanceof IFile) {
            	
            	String testDataFileName = ((IFile) obj).getName();
                this.testDataFile = (IFile) obj;            	
                String statusMessage = MessageFormat.format(Messages.getString("Shared_Resource_Selector_Message_format"),
                        new Object[] { testDataFileName }); //$NON-NLS-1$
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            } 
		}
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("Shared_Resource_Selector_Error_Message"), null);
	}
	
	 public Object getFirstResult() {
	    	return testDataFile;
	    }

}
