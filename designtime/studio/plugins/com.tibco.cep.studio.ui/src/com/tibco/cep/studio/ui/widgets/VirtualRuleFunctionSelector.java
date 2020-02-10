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
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
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

public class VirtualRuleFunctionSelector  extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private RuleFunction vruleFunction;
	private String projectName;
	private String vrfPath = "";

	public VirtualRuleFunctionSelector(Shell parent, 
			                           String studioProjectName) {
        super(parent);
        setTitle("Select Virtual Rule Function");
        setMessage("Select virtual rule function to be associated with");
        
        this.projectName = studioProjectName;
        
        // Only include Designer nature projects
        addFilter(new StudioProjectsOnly(this.projectName));
        
        // Include only rule function files
        Set<String> extensions = new HashSet<String>();
        extensions.add("rulefunction");
        addFilter(new OnlyFileInclusionFilter(extensions));
        
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.RULE_FUNCTION));
        setValidator(this);
        
        setInput(ResourcesPlugin.getWorkspace().getRoot());
    }

    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
			if (selection[0] instanceof IFile) {
				IFile file = (IFile)selection[0];
				if (file.getFileExtension().equals("rulefunction")) {
					IPath relativePath = file.getFullPath().removeFirstSegments(1);
					if (file.isLinked(IFile.CHECK_ANCESTORS)) {
						// remove the .projlib segment
						relativePath = relativePath.removeFirstSegments(1);						
					}
					relativePath = relativePath.removeFileExtension();
					vrfPath = "/" + relativePath.toString();
					RuleElement vrfElement = IndexUtils.getRuleElement(this.projectName, vrfPath, ELEMENT_TYPES.RULE_FUNCTION);
					vruleFunction = (com.tibco.cep.designtime.core.model.rule.RuleFunction)vrfElement.getRule();
					vruleFunction.setOwnerProjectName(this.projectName);
				}
				
				String statusMessage = MessageFormat.format("Selected virtual rule function: {0}",
						new Object[] { (vruleFunction != null ? vruleFunction.getName() : "") });
				
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
				if (rule instanceof com.tibco.cep.designtime.core.model.rule.RuleFunction) {
					vruleFunction = (com.tibco.cep.designtime.core.model.rule.RuleFunction) rule;
					vruleFunction.setOwnerProjectName(this.projectName);
					vrfPath = vruleFunction.getFullPath();
				}
				
				String statusMessage = MessageFormat.format("Selected virtual rule function: {0}",
						new Object[] { (vruleFunction != null ? vruleFunction.getName(): "") });
				
				return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}
		}
        
		return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, "Select virtual rule function", null);
		
    }

    @Override
    public int open() {
        return super.open();
    }

    @Override
    public Object getFirstResult() {
    	return vruleFunction;
    }
    
 }
