package com.tibco.cep.studio.ui.filter;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedElement;

/**
 * The filter shows only elements that match the type of the ELEMENT_TYPES array
 * 
 */
public class ProjectLibraryExclusionFilter extends ViewerFilter {

    private ELEMENT_TYPES[] fTypes = null;
    private Set<String> sharedResourceExtensions = null;

    /**
     * @param displayType
     */
    public ProjectLibraryExclusionFilter(ELEMENT_TYPES displayType) {
    	this(new ELEMENT_TYPES[] { displayType });
    }
    
    /**
     * @param sharedResourceExtensions
     */
    public ProjectLibraryExclusionFilter(Set<String> sharedResourceExtensions) {
    	this.sharedResourceExtensions = sharedResourceExtensions;
    }
    
    /**
     * @param displayTypes
     */
    public ProjectLibraryExclusionFilter(ELEMENT_TYPES[] displayTypes) {
    	super();
    	this.fTypes = displayTypes;
    	Arrays.sort(fTypes);
    }

	public boolean select(Viewer viewer, Object parentElement, Object element) {
//        if (fTypes == null) {
//        	return true;
//        }
        if (element instanceof Folder) {
        	return checkFolder((Folder)element);
        }
        if (element instanceof SharedElement) {
        	SharedElement sharedElement = (SharedElement)element;
        	if(fTypes != null){
        		ELEMENT_TYPES elementType = sharedElement.getElementType();
        		if (Arrays.binarySearch(fTypes, elementType) >= 0) {
        			return false;
        		} else {
        			return true;
        		}
        	}
        	//Filtering Shared resources from Project Library
        	if(sharedResourceExtensions != null){
        		String extension = sharedElement.getFileName().substring(sharedElement.getFileName().lastIndexOf(".")+1);
        		if(sharedResourceExtensions.contains(extension)){
        			return true;
        		}else{
        			return false;
        		}
        	}
        }
        return true;
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
				if(fTypes != null){
					ELEMENT_TYPES elementType = element.getElementType();
					if (Arrays.binarySearch(fTypes, elementType) >= 0) {
						return true;
					}
				}

				//Filtering Shared resources from Project Library
				if(sharedResourceExtensions != null){
					if(element instanceof SharedElement){
						SharedElement sharedElement = (SharedElement)element;
						String extension = sharedElement.getFileName().substring(sharedElement.getFileName().lastIndexOf(".")+1);
						if(sharedResourceExtensions.contains(extension)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}