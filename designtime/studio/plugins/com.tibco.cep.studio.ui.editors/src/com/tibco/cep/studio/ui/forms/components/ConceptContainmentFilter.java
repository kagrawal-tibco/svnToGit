package com.tibco.cep.studio.ui.forms.components;

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
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ConceptContainmentFilter extends FileInclusionFilter{

	private String projectName;
//	private String currentPath;
	private Set<String> pathSet = new HashSet<String>();

	/**
	 * @param inclusions
	 * @param baseAbsolutePath
	 * @param projectName
	 */
	public ConceptContainmentFilter(Set<String> inclusions, 
			                        String baseAbsolutePath, 
			                        String currentPath, 
			                        String projectName) {
		
		super(inclusions,baseAbsolutePath);
		this.projectName = projectName;
//		this.currentPath = currentPath;
		
		getContainedPathSet();
		containedParentLoopPathSet(baseAbsolutePath);
	}
	public ConceptContainmentFilter(Set<String> inclusions, 
            String baseAbsolutePath, 
            String currentPath, 
            String projectName,Set<String> excludedContainedConcept) {

		super(inclusions,baseAbsolutePath);
	this.projectName = projectName;
	//this.currentPath = currentPath;

	//getContainedPathSet();
	getContainedProcessPathSet(excludedContainedConcept);
	containedParentLoopPathSet(baseAbsolutePath);
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
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				String path = IndexUtils.getFullPath(file);
				return isEntityFile(file) && !isBaseEntityFile(baseAbsolutePath, path)
				                          && !pathSet.contains(path);
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
		return true;
	}
	
	private void getContainedPathSet(){
		List<Entity> list = IndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		for(Entity entity:list){
			Concept concept = (Concept)entity;
			for(PropertyDefinition propertyDefinition:concept.getAllPropertyDefinitions()){
				if(propertyDefinition.getType()== PROPERTY_TYPES.CONCEPT){
						pathSet.add(propertyDefinition.getConceptTypePath());
				}
			}
		}
	}
	
	/*
	 * for getting contained concept for process
	 */
	private void getContainedProcessPathSet(Set<String> excludedConcept){
		List<Entity> list = IndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		for(Entity entity:list){
			Concept concept = (Concept)entity;	
			if(concept.getFullPath()!=null){
				if(excludedConcept.contains(concept.getFullPath()))
					pathSet.add(concept.getFullPath());
			}
			
			for(PropertyDefinition propertyDefinition:concept.getAllPropertyDefinitions()){
				if(propertyDefinition.getType()== PROPERTY_TYPES.CONCEPT){
					pathSet.add(propertyDefinition.getConceptTypePath());
				}
			}
		}
	}
	
	
	
	
	
	
	/**
	 * @param path
	 */
	private void containedParentLoopPathSet(String path){
		List<Entity> list = IndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		for(Entity entity:list){
			Concept concept = (Concept)entity;
			for(PropertyDefinition propertyDefinition:concept.getAllPropertyDefinitions()){
				if(propertyDefinition.getType()== PROPERTY_TYPES.CONCEPT){
					if(propertyDefinition.getConceptTypePath().equals(path)){
						pathSet.add(concept.getFullPath());
						containedParentLoopPathSet(concept.getFullPath());
					}
				}
			}
		}
	}
}