package com.tibco.cep.studio.ui.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

/**
 * @author pdhar
 *
 */
public class TreeViewerFilter extends ViewerFilter {
	ELEMENT_TYPES[] types;
	IProject project;
	
	public TreeViewerFilter(IProject project,ELEMENT_TYPES[] types) {
		setProject(project);
		setTypes(types);
	}
	

	/**
	 * @return the types
	 */
	public ELEMENT_TYPES[] getTypes() {
		return types;
	}


	/**
	 * @param types the types to set
	 */
	public void setTypes(ELEMENT_TYPES[] types) {
		this.types = types;
	}


	/**
	 * @return the project
	 */
	public IProject getProject() {
		return project;
	}


	/**
	 * @param project the project to set
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	
	protected boolean hasChildren(Object parent,ELEMENT_TYPES[] elTypes) throws CoreException {
		List<Object> children = new ArrayList<Object>();
		boolean hasChildren = false;
		if(parent instanceof IProject){
			IResource[] members = ((IProject) parent).members();
			for (IResource resource : members) {
				hasChildren = hasChildren || hasChildren(resource, elTypes);
			}
		} else if (parent instanceof IContainer) {
			IResource[] members = ((IContainer)parent).members();
			for (IResource resource : members) {
				hasChildren = hasChildren || hasChildren(resource, elTypes);
			}
		} else if(parent instanceof IFile) {
			DesignerElement res = IndexUtils
			.getElement((IResource) parent);
			if (res != null){
					
					if(Arrays.asList(getTypes()).contains(
							res.getElementType())) {
						hasChildren = true;
					} else if(res.getElementType() == ELEMENT_TYPES.CHANNEL && 
							Arrays.asList(getTypes()).contains(ELEMENT_TYPES.DESTINATION)) {
						hasChildren = true;
					}
			}
		} else if(parent instanceof StudioNavigatorNode) {
			StudioNavigatorNode node = (StudioNavigatorNode) parent;
			Entity e = node.getEntity();
			 if(e != null && Arrays.asList(getTypes()).contains(IndexUtils.getElementType(e))){
				 hasChildren = true;
			 }
		} else if(parent instanceof SharedEntityElement) {
			SharedEntityElement se = (SharedEntityElement) parent;
			Entity e = se.getEntity();
			if(e != null && Arrays.asList(getTypes()).contains(IndexUtils.getElementType(e))){
				 hasChildren = true;
			}
		}
		return hasChildren;
	}



	@Override
	public boolean select(Viewer viewer, Object parentElement,
			Object element) {
		if(element instanceof IResource) {
			if(element instanceof IContainer) {
				try {
					return hasChildren(element, getTypes());
				} catch (CoreException e) {
					return false;
				}
			} 
			
			DesignerElement res = IndexUtils
			.getElement((IResource) element);
			if(res != null){
				if(Arrays.asList(getTypes()).contains(
						res.getElementType())) {
					return true;
				} else if(res.getElementType() == ELEMENT_TYPES.CHANNEL && 
						Arrays.asList(getTypes()).contains(ELEMENT_TYPES.DESTINATION)) {
					return true;
				}
			}
		} else if(element instanceof StudioNavigatorNode) {
			StudioNavigatorNode node = (StudioNavigatorNode) element;
			 Entity e = node.getEntity();
			 if(e != null && Arrays.asList(getTypes()).contains(IndexUtils.getElementType(e))){
				 return true;
			 }
		} else if( element instanceof SharedElementRootNode) {
			SharedElementRootNode seroot = (SharedElementRootNode) element;
			return !seroot.getChildren().isEmpty();
		} else if (element instanceof Folder) {
        	return checkFolder((Folder)element);
        } else if (element instanceof SharedElement) {
        	SharedElement sharedElement = (SharedElement)element;
        	if(getTypes() != null){
        		ELEMENT_TYPES elementType = sharedElement.getElementType();
        		if((elementType == ELEMENT_TYPES.CHANNEL) &&
						Arrays.binarySearch(getTypes(),ELEMENT_TYPES.DESTINATION) >= 0) {
					return true;
				}
        		if (Arrays.binarySearch(getTypes(), elementType) >= 0) {
        			return true;
        		} else {
        			return false;
        		}
        	}	        	
        } else if(element instanceof DesignerProject) {
        	return true;
        }
		return false;
	}
	
	/**
	 * @param folder
	 * @return
	 */
	private boolean checkFolder(ElementContainer folder) {
		EList<DesignerElement> entries = folder.getEntries();
		for (DesignerElement element : entries) {
			if (element instanceof ElementContainer) {
				if (checkFolder((ElementContainer) element)) {
					return true;
				}
			} else {
				if(getTypes() != null){
					ELEMENT_TYPES elementType = element.getElementType();
					if((elementType == ELEMENT_TYPES.CHANNEL) &&
							Arrays.binarySearch(getTypes(),ELEMENT_TYPES.DESTINATION) >= 0) {
						return true;
					}
					if (Arrays.binarySearch(getTypes(), elementType) >= 0) {
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
}