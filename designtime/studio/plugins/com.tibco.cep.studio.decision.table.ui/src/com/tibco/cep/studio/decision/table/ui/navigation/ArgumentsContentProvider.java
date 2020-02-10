package com.tibco.cep.studio.decision.table.ui.navigation;

import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.ParentResource;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.decision.table.utils.LegacyDecisionTableUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentsContentProvider extends AbstractResourceContentProvider {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object [] getChildren(Object object)
	{
		if (object instanceof ParentResource) {
			try {
				
				if(object instanceof com.tibco.cep.decisionproject.ontology.impl.ConceptImpl){
					
					Concept old = (Concept) object;
					
					com.tibco.cep.designtime.core.model.element.Concept newConcept = IndexUtils.getConcept(old.getOwnerProjectName(), old.getFolder() + old.getName());
					
					LegacyDecisionTableUtil.buildArgumentResource(old.getOwnerProjectName(), old, newConcept.getAllProperties(), null);
					
				}
				
				return 	getAdapterFactoryContentProvider().getChildren((ParentResource)object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return EMPTY_CHILDREN;
	}
}
