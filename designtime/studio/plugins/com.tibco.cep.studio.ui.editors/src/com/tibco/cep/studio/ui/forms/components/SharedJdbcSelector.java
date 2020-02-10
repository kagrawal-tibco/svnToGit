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
 * 
 * @author smarathe
 *
 */
public class SharedJdbcSelector extends AbstractResourceElementSelector implements ISelectionStatusValidator {

	String projectName;
	IFile sharedJdbcFile;
	public SharedJdbcSelector(Shell parent, String projectName) {
		super(parent);
		this.projectName = projectName;
		setTitle(Messages.getString("Shared_Resource_Selector_Dialog_Title"));
	    setMessage(Messages.getString("Shared_Resource_Selector_Dialog_Message"));
	    setInput(ResourcesPlugin.getWorkspace().getRoot());
	    addFilter(new StudioProjectsOnly(projectName));
	    Set<String> extensions = new HashSet<String>();
	    extensions.add("sharedjdbc");
	    addFilter(new OnlyFileInclusionFilter(extensions));
	    setValidator(this);
	}
	@Override
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
        	Object obj = selection[0];
            if (obj instanceof IFile) {
            	
            	String sharedJdbcFileName = ((IFile) obj).getName();
                this.sharedJdbcFile = (IFile) obj;            	
                String statusMessage = MessageFormat.format(
                		Messages.getString("Shared_Resource_Selector_Message_format"),
                        new Object[] { sharedJdbcFileName }); //$NON-NLS-1$
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            } 
		}
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("Shared_Resource_Selector_Error_Message"), null);
	}
	
	 public Object getFirstResult() {
	    	return sharedJdbcFile;
	    }

}
