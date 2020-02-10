package com.tibco.cep.studio.ui.forms.components;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
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
public class ConceptSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private Concept selectedConcept;
	protected Set<String> extensions = new HashSet<String>();
	private String projectName;
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseConcept
	 * @param superConceptPath
	 */
	public ConceptSelector(Shell parent,
						   String currentProjectName, 
			               String baseConceptPath, 
			               String superConceptPath) {
        super(parent);
        setTitle(Messages.getString("Concept_Selector_Dialog_Title"));
        setMessage(Messages.getString("Concept_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(currentProjectName));
        extensions.add("concept");
        this.projectName = currentProjectName;
        addFilter(new EntityFileInclusionFilter(extensions,
		        		baseConceptPath,superConceptPath, 
		        		currentProjectName));
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.CONCEPT));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        setInitialEntitySelection(currentProjectName, superConceptPath, ELEMENT_TYPES.CONCEPT);
     }
	
	/**
	 * TODO: for Initial Shared Entity Element Selection
	 * @param path
	 */
	protected SharedElement getInitialSharedElement(String path){
		DesignerProject project = IndexUtils.getIndex(projectName);
		for(DesignerProject refProject : project.getReferencedProjects()){
			EList<EntityElement> entityElements = refProject.getEntityElements();
			for (EntityElement entityElement : entityElements) {
				if (entityElement instanceof SharedEntityElement) {
					SharedEntityElement sharedElement = (SharedEntityElement)entityElement;
					if(sharedElement.getEntity().getFullPath().equals(path)){
						return sharedElement;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param conceptPath
	 */
	public ConceptSelector(Shell parent,String currentProjectName, String conceptPath) {
        super(parent);
        setTitle(Messages.getString("Concept_Selector_Dialog_Title"));
        setMessage(Messages.getString("Concept_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(currentProjectName));
        extensions.add("concept");
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        setInitialEntitySelection(currentProjectName, conceptPath, ELEMENT_TYPES.CONCEPT);
    }
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param conceptPath
	 */
	public ConceptSelector(Shell parent,String currentProjectName, String conceptPath, String propertyPath, boolean isReference) {
        super(parent);
        setTitle(Messages.getString("Concept_Selector_Dialog_Title"));
        setMessage(Messages.getString("Concept_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(currentProjectName));
        extensions.add("concept");
        if(isReference){
        	addFilter(new OnlyFileInclusionFilter(extensions));
        }else{
        	addFilter(new ConceptContainmentFilter(extensions,conceptPath,propertyPath, currentProjectName));
        }
        addFilter(new EObjectFilter());
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        if(IndexUtils.getEntity(currentProjectName, propertyPath)!= null){
        	setInitialEntitySelection(currentProjectName, propertyPath, ELEMENT_TYPES.CONCEPT);
        }
    }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
        	Object obj = selection[0];
            if (obj instanceof IFile) {
            	
            	EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
                selectedConcept = (Concept) entityObj;
                
                String statusMessage = MessageFormat.format(
                		Messages.getString("Concept_Selector_Message_format"),
                        new Object[] { (selectedConcept != null ? selectedConcept
                                .getName() : "") });
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            } else if (obj instanceof SharedEntityElement && ((SharedEntityElement) obj).getSharedEntity() instanceof Concept) {
            	selectedConcept = (Concept) ((SharedEntityElement) obj).getSharedEntity();

                String statusMessage = MessageFormat.format(
                		Messages.getString("Concept_Selector_Message_format"),
                        new Object[] { (selectedConcept != null ? selectedConcept
                                .getName() : "") });
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
        }
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("Concept_Selector_Error_Message"), null);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return selectedConcept;
    }
 }
