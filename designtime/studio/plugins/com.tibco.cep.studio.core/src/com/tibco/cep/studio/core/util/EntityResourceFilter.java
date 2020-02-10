package com.tibco.cep.studio.core.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;


public class EntityResourceFilter implements IResourceVisitor {

//	private List<IFile> fEntities;
//	private ENTITY_TYPES[] fResourceTypes;
//
//	public EntityResourceFilter(List<IFile> entities, ENTITY_TYPES resourceType) {
//		this(entities, new ENTITY_TYPES[] { resourceType });
//	}
//
//	public EntityResourceFilter(List<IFile> entities, ENTITY_TYPES[] resourceType) {
//		this.fEntities = entities;
//		this.fResourceTypes = resourceType;
//	}
	
	public boolean visit(IResource resource) throws CoreException {
//		if (resource instanceof IFile) {
//			IFile file = (IFile) resource;
//			String extension = file.getFileExtension();
//			if (extension != null) {
//				for (ENTITY_TYPES type : fResourceTypes) {
//					String fileExtension = OntologyUtils.getFileExtension(type);
//					if (extension.equalsIgnoreCase(fileExtension)) {
//						fEntities.add(file);
//						break;
//					}
//					// special case TimeEvents
////					if (type == ENTITY_TYPES.SIMPLE_EVENT 
////							&& extension.equalsIgnoreCase(OntologyUtils.getFileExtension(ENTITY_TYPES.TIME_EVENT))) {
////						fEntities.add(file);
////						break;
////					}
//				}
//			}
//		}
		return true;
	}

}
