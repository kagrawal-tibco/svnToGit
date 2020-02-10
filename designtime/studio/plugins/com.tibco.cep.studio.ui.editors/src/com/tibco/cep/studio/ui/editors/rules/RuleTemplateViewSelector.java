package com.tibco.cep.studio.ui.editors.rules;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.forms.components.EntityFileInclusionFilter;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;

/**
 * 
 */
public class RuleTemplateViewSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private RuleTemplateView ruleTemplateView;
	private IFile ruleTemplateViewFile;

	/**
	 */
	public RuleTemplateViewSelector(Shell parent,String currentProjectName, String initialSelection) {
		super(parent);
		setTitle(Messages.getString("TemplateView_Selector_Dialog_Title"));
		setMessage(Messages.getString("TemplateView_Selector_Dialog_Message"));

		addFilter(new StudioProjectsOnly(currentProjectName));// Only include Designer nature projects
		Set<String> extensions = new HashSet<String>(); // Include only rule template view files
		extensions.add("ruletemplateview");
		addFilter(new EntityFileInclusionFilter(extensions,
		        		initialSelection,null, 
		        		currentProjectName));
		addFilter(new EObjectFilter());
		addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.RULE_TEMPLATE_VIEW));
		setValidator(this);
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (initialSelection != null) {
			Entity entity = IndexUtils.getEntity(currentProjectName, initialSelection, ELEMENT_TYPES.RULE_TEMPLATE_VIEW);
			if(entity!=null){
				IFile file = IndexUtils.getFile(currentProjectName, entity);
				setInitialSelection(file);
			}
		}
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
			if (selection[0] instanceof IFile) {
				ruleTemplateViewFile = (IFile)selection[0];
				EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
				ruleTemplateView = (RuleTemplateView) entityObj;
				String statusMessage = MessageFormat.format( Messages.getString("TemplateView_Selector_Message_format"), 
						new Object[] { (ruleTemplateView != null ? ruleTemplateView.getName() : "") });
				return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}else if (selection[0] instanceof SharedEntityElement && ((SharedEntityElement) selection[0]).getSharedEntity() instanceof Domain) {
            	ruleTemplateView = (RuleTemplateView) ((SharedEntityElement) selection[0]).getSharedEntity();
            	String statusMessage = MessageFormat.format( Messages.getString("TemplateView_Selector_Message_format"),  
            			new Object[] { (ruleTemplateView != null ? ruleTemplateView.getName() : "") });
				return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
           }
		}
		return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("TemplateView_Selector_Error_Message"), null);
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	if (ruleTemplateViewFile != null) {
    		return IndexUtils.getFullPath(ruleTemplateViewFile);
    	} else if (ruleTemplateView != null) {
    		return ruleTemplateView.getFullPath();
    	}
    	return null;
    }
 }