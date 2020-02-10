package com.tibco.cep.studio.core.search;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class DomainModelSearchParticipant extends EntitySearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		List<Entity> allEntities = IndexUtils.getAllEntities(index.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.DOMAIN });
		for (Entity entity : allEntities) {
			Domain domain = (Domain) entity;
			if (resolvedElement instanceof EntityElement && ((EntityElement) resolvedElement).getEntity() instanceof Domain) {
				Domain resolvedDomain = (Domain) ((EntityElement) resolvedElement).getEntity();
				if (isEqual(resolvedDomain, domain)) {
					result.addExactMatch(createElementMatch(DEFINITION_FEATURE, domain.eClass(), resolvedDomain));
				}
				if (domain.getSuperDomainPath() != null && domain.getSuperDomainPath().equalsIgnoreCase(resolvedDomain.getFullPath())) {
					result.addExactMatch(createElementMatch(DomainPackage.DOMAIN__SUPER_DOMAIN_PATH, domain.eClass(), domain));
				}
			}
		}
	}

}
