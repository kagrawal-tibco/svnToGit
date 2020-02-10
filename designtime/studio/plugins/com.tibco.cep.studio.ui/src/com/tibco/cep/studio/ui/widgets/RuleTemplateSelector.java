package com.tibco.cep.studio.ui.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.index.utils.RuleCreator;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

public class RuleTemplateSelector  extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private RuleTemplate ruleTemplate;
	private String projectName;
	private String templatePath = "";

	public RuleTemplateSelector(Shell parent, 
			                           String studioProjectName) {
        super(parent);
        setTitle("Select Rule Template");
        setMessage("Select rule template to be associated with");
        
        this.projectName = studioProjectName;
        
        // Only include Designer nature projects
        addFilter(new StudioProjectsOnly(this.projectName));
        
        // Include only rule function files
        Set<String> extensions = new HashSet<String>();
        extensions.add("ruletemplate");
        addFilter(new OnlyFileInclusionFilter(extensions));
        
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.RULE_TEMPLATE));
        setValidator(this);
        
        setInput(ResourcesPlugin.getWorkspace().getRoot());
    }

    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
			if (selection[0] instanceof IFile) {
				IFile file = (IFile)selection[0];
				if (file.getFileExtension().equals("ruletemplate")) {
					IPath relativePath = file.getFullPath().removeFirstSegments(1);
					relativePath = relativePath.removeFileExtension();
					templatePath = "/" + relativePath.toString();
					RuleElement vrfElement = IndexUtils.getRuleElement(this.projectName, templatePath, ELEMENT_TYPES.RULE_TEMPLATE);
					ruleTemplate = (RuleTemplate) vrfElement.getRule();
					ruleTemplate.setOwnerProjectName(this.projectName);
				}
				
				String statusMessage = MessageFormat.format("Selected rule template: {0}",
						new Object[] { (ruleTemplate != null ? ruleTemplate.getName() : "") });
				
				return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
				
			} else if(selection[0] instanceof SharedRuleElement){
				Compilable rule = ((SharedRuleElement) selection[0]).getRule();
				if (rule == null) {
					InputStream is = null;
					try {
						is = IndexUtils.getInputStream((SharedRuleElement)selection[0]);
						RuleCreator creator = new RuleCreator();
						rule = creator.createRule(is, this.projectName);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (is != null) {
								is.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (rule instanceof RuleTemplate) {
					ruleTemplate =  (RuleTemplate) rule;
					ruleTemplate.setOwnerProjectName(this.projectName);
					templatePath = ruleTemplate.getFullPath();
				}
				
				String statusMessage = MessageFormat.format("Selected rule template: {0}",
						new Object[] { (ruleTemplate != null ? ruleTemplate.getName(): "") });
				
				return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}
		}
        
		return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, "Select rule template", null);
		
    }

    @Override
    public int open() {
        return super.open();
    }

    @Override
    public Object getFirstResult() {
    	return ruleTemplate;
    }
    
 }
