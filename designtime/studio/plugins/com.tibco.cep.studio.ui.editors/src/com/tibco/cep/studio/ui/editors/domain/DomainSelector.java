package com.tibco.cep.studio.ui.editors.domain;

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
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private Domain domain;
	private IFile domainFile;

	/**
	 * @param parent
	 * @param currentProjectName
	 * @param domain
	 * @param superDomainPath
	 */
	public DomainSelector(Shell parent,String currentProjectName, Domain domain, String superDomainPath) {
        super(parent);
        setTitle(Messages.getString("Domain_Selector_Dialog_Title"));
        setMessage(Messages.getString("Domain_Selector_Dialog_Message"));
        
        addFilter(new StudioProjectsOnly(currentProjectName));// Only include Designer nature projects
        Set<String> extensions = new HashSet<String>(); // Include only domain files
        extensions.add("domain");
        addFilter(new DomainFileInclusionFilter(extensions,currentProjectName, domain.getDataType(),domain.getFullPath()));
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.DOMAIN));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        if(superDomainPath !=null && !superDomainPath.trim().equalsIgnoreCase("")){
        	Entity entity = IndexUtils.getEntity(currentProjectName, superDomainPath, ELEMENT_TYPES.DOMAIN);
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
				domainFile = (IFile)selection[0];
				EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
				domain = (Domain) entityObj;
				String statusMessage = MessageFormat.format( Messages.getString("Domain_Selector_Message_format"), 
						new Object[] { (domain != null ? domain.getName() : "") });
				return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}else if (selection[0] instanceof SharedEntityElement && ((SharedEntityElement) selection[0]).getSharedEntity() instanceof Domain) {
            	domain = (Domain) ((SharedEntityElement) selection[0]).getSharedEntity();
            	String statusMessage = MessageFormat.format( Messages.getString("Domain_Selector_Message_format"),  
            			new Object[] { (domain != null ? domain.getName() : "") });
				return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
           }
		}
		return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("Domain_Selector_Error_Message"), null);
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	if (domainFile != null) {
    		return IndexUtils.getFullPath(domainFile);
    	} else if (domain != null) {
    		return domain.getFullPath();
    	}
    	return null;
    }
 }