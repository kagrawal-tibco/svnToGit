package com.tibco.cep.studio.ui.wizards;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleFunctionSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private String selectedRuleFunctionPath;
	protected Set<String> extensions = new HashSet<String>();
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param ruleFunctionPath
	 */
	public RuleFunctionSelector(Shell parent,
			                    String currentProjectName, 
			                    String ruleFunctionPath, 
								boolean isRank) {
        super(parent);
        setTitle(Messages.getString("RuleFunction_Selector_Dialog_Title"));
        setMessage(Messages.getString("RuleFunction_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(currentProjectName));
        extensions.add("rulefunction");
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.RULE_FUNCTION));
        if (isRank) {
			 addFilter(new RankRuleFunctionFileFilter());
		}
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        if(ruleFunctionPath != null && !ruleFunctionPath.equalsIgnoreCase("")){
        	RuleElement ruleElement = IndexUtils.getRuleElement(currentProjectName, ruleFunctionPath, ELEMENT_TYPES.RULE_FUNCTION);
        	if(ruleElement != null){
        		IFile file = IndexUtils.getFile(currentProjectName, ruleElement);
        		setInitialSelection(file);
        	}
        }
    }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
	public IStatus validate(Object[] selection) {
		if (selection.length == 0) {
			return Status.OK_STATUS;
		}
		Object object = selection[0];
		if (selection != null && selection.length == 1) {
			if (object instanceof IFile) {
				selectedRuleFunctionPath = IndexUtils.getFullPath((IFile)selection[0]);
				String statusMessage = MessageFormat.format(
						Messages.getString("RuleFunction_Selector_Message_format"),
						new Object[] { (selectedRuleFunctionPath != null ? selectedRuleFunctionPath: "") });
				return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}
			else if (object instanceof SharedRuleElement) {
				SharedRuleElement sharedRuleElement = (SharedRuleElement) object;
				selectedRuleFunctionPath = sharedRuleElement.getFolder() + sharedRuleElement.getName();
				String statusMessage = MessageFormat.format(
						Messages.getString("RuleFunction_Selector_Message_format"),
						new Object[] { (selectedRuleFunctionPath != null ? selectedRuleFunctionPath: "") });
				return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}
		}
        return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("RuleFunction_Selector_Error_Message"), null);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return selectedRuleFunctionPath;
    }
 }