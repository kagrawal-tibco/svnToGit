package com.tibco.cep.studio.ui.forms.components;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubConcepts;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubEvents;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class EntityFileInclusionFilter extends FileInclusionFilter {

	private String projectName;
	private Entity entity;
	private String superPath;
	
	/**
	 * @param inclusions
	 * @param baseAbsolutePath
	 * @param projectName
	 */
	@SuppressWarnings("unchecked")
	public EntityFileInclusionFilter(Set inclusions,String baseAbsolutePath, String superPath, String projectName) {
		super(inclusions,baseAbsolutePath);
		this.projectName = projectName;
		entity = IndexUtils.getEntity(projectName, baseAbsolutePath);
		this.superPath= superPath ;
	}
	
	/**
	 * @param viewer
	 * @param parentElement
	 * @param element
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		try{
			if (element instanceof IAdaptable) {
				IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
				if (res instanceof IFile) {
					IFile file = (IFile) res;
					String path = IndexUtils.getFullPath(file);
					return ((isEntityFile(file)&& !isBaseEntityFile(baseAbsolutePath, path) 
							&& !isSuperFile(path, entity) 
							&& !isDuplicatePropertiesAvailable(path, entity)
							&& !ifEventPayloadExists(file,path))
							|| (superPath != null && superPath.equalsIgnoreCase(path))) 
							
							&& !isContained(path);
				}
				if (res instanceof IFolder){
					IFolder folder = (IFolder)res;
					visible = false;
					return isVisible(folder);
				}
			}
			if(element instanceof SharedElementRootNode){
				return true;
			}else if(element instanceof DesignerElement){
				return true;
			}else{
				if(!(element instanceof IResource)){
					return false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * @param path
	 * @param entity
	 * @return
	 */
	private boolean isDuplicatePropertiesAvailable(String path, Entity entity){
		Set<String> propertyNameSet = new HashSet<String>();
		if(entity instanceof Concept){
			Concept inheritConcept = IndexUtils.getConcept(projectName, path);
			Concept concept = (Concept) entity;
			collectAllInheritConceptPropertyDef(propertyNameSet, inheritConcept);
			for(PropertyDefinition propDef:concept.getAllPropertyDefinitions()){
			     if(propertyNameSet.contains(propDef.getName())){
			    	 return true;
			     }
			}
		}
		if(entity instanceof Event){
			Event inheritEvent = IndexUtils.getSimpleEvent(projectName, path);
			Event event = (Event) entity;
			collectAllInheritEventPropertyDef(propertyNameSet, inheritEvent);
			for(PropertyDefinition propDef:event.getAllUserProperties()){
			     if(propertyNameSet.contains(propDef.getName())){
			    	 return true;
			     }
			}
		}
		return false;
	}
	
	/**
	 * @param path
	 * @return
	 */
	private boolean isContained(String path){
		if(entity instanceof Concept){
			Concept concept = (Concept)entity;
			for(PropertyDefinition propertyDefinition:concept.getPropertyDefinitions(true)){
				if(propertyDefinition.getType()== PROPERTY_TYPES.CONCEPT){
					if(propertyDefinition.getConceptTypePath().equals(path)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param propertyNameSet
	 * @param concept
	 */
	private void collectAllInheritConceptPropertyDef(Set<String> propertyNameSet, Concept concept){
		for(PropertyDefinition propDef:concept.getProperties()){
			propertyNameSet.add(propDef.getName());
		}
		Concept inheritConcept = concept.getParentConcept();
		if(inheritConcept != null){
			collectAllInheritConceptPropertyDef(propertyNameSet, inheritConcept);
		}
	}

	/**
	 * @param propertyNameSet
	 * @param event
	 */
	private void collectAllInheritEventPropertyDef(Set<String> propertyNameSet, Event event){
		if(event != null){
			for(PropertyDefinition propDef:event.getProperties()){
				propertyNameSet.add(propDef.getName());
			}
/*			Event inheritEvent = event.getSuperEvent();
			if(inheritEvent != null){
				collectAllInheritEventPropertyDef(propertyNameSet, inheritEvent);
			}
*/		}
	}

	
	/**
	 * @param entity
	 * @return
	 */
	private boolean ifEventPayloadExists(IFile file, String path){
		if(!file.getFileExtension().equals(CommonIndexUtils.EVENT_EXTENSION))
			return false;
		Entity entity = IndexUtils.getEntity(projectName, path);
		if(entity instanceof Event){
			Event event = (Event) entity;
			if(event.getPayloadString() != null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param path
	 * @param entity
	 * @return
	 */
	public boolean isSuperFile(String path, Entity entity ){
		if(entity instanceof Concept){
			Concept concept = (Concept)entity;
			List<String> subConceptsPaths = getSubConcepts(concept.getFullPath(), projectName);
			if(subConceptsPaths.contains(path)){
				return true;
			}
		}
		if(entity instanceof Event){
			Event event = (Event)entity;
			List<String> subEventsPaths = getSubEvents(event.getFullPath(), projectName);
			if(subEventsPaths.contains(path)){
				return true;
			}
		}
		return false;
	}
}
