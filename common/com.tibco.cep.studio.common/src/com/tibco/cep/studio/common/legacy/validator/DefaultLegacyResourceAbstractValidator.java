/**
 * 
 */
package com.tibco.cep.studio.common.legacy.validator;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;

/**
 * @author aathalye
 *
 */
public abstract class DefaultLegacyResourceAbstractValidator<A extends AbstractResource, 
                                                             E extends Entity> implements
                        ILegacyResourceExistenceValidator<A> {
	
	protected String rootPath;//The base path of the project
	
	protected String dpPath;//The full path of the Decision project
	
	protected DefaultLegacyResourceAbstractValidator(String dpPath, String rootPath) {
		this.rootPath = rootPath;
		this.dpPath = dpPath;
	}
	
	@SuppressWarnings("unchecked")
	protected E validateExistence(URI uri) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(uri, true);
		if (resource == null) {
			return null;
		}
		EObject eobject = resource.getContents().get(0);
		if (eobject instanceof Entity) {
			return (E)eobject;
		}
		return null;
	}
	
	/**
	 * Validate children of an {@link AbstractResource} with those of
	 * an {@link Entity}.
	 * <p>
	 * The 2 parameters to be compared should be equivalent.
	 * e.g: Concept, Concept
	 * </p>
	 * @param oldResource
	 * @param newResource
	 * @return
	 */
	protected abstract void validateChildren(A oldResource, E newResource, List<LegacyOntCompliance> compliances);
	
	protected String buildFilePath(String entityPath, String extension) {
		return new StringBuilder(rootPath)
		            .append(entityPath)
		            .append(".")
		            .append(extension)
		            .toString();
	}
}
