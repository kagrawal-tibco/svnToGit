package com.tibco.cep.studio.ui.statemachine.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.filter.ProjectLibraryExclusionFilter;
import com.tibco.cep.studio.ui.forms.components.ConceptSelector;

/**
 * 
 * @author sasahoo
 *
 */
public class OwnerConceptSelector extends ConceptSelector{

	/**
	 * @param parent
	 * @param projectName
	 * @param conceptPath
	 */
	public OwnerConceptSelector(Shell parent, String projectName,String conceptPath) {
		super(parent, projectName, conceptPath);
		addFilter(new ConceptFileInclusionFilter(extensions));
		addFilter(new ProjectLibraryExclusionFilter(ELEMENT_TYPES.CONCEPT));
		Concept concept = IndexUtils.getConcept(projectName, conceptPath);
		
		if(concept!=null){
			IFile file = IndexUtils.getFile(projectName, concept);
			setInitialSelection(file);
		}
		
	}
}
