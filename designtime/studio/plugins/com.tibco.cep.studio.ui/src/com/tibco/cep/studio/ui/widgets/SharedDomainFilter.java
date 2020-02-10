package com.tibco.cep.studio.ui.widgets;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;

/**
 * 
 * @author sasahoo
 *
 */
public class SharedDomainFilter extends ViewerFilter {

	protected boolean sharedFolderVisible = false;
	private PROPERTY_TYPES type;
	
	public SharedDomainFilter(PROPERTY_TYPES type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof DesignerElement){
			if(element instanceof Folder){
				sharedFolderVisible = false;
				return isVisibleSharedFolder((Folder)element);
			}
			if(element instanceof SharedElement){
				return isValidSharedEntity((SharedElement)element);
			}
		}
		return true;
	}
	
	/**
	 * @param folder
	 * @return
	 */
	protected boolean isVisibleSharedFolder(Folder folder) {
		
		for(DesignerElement element:folder.getEntries()){
			if(element instanceof SharedElement){
				if(isValidSharedEntity((SharedElement)element)){
					sharedFolderVisible = true;
				}
			}
			if(element instanceof Folder){
				isVisibleSharedFolder((Folder)element);
			}
		}
		if (sharedFolderVisible == true) {
			return true;
		}
		return false;
	}

	
	/**
	 * @param element
	 * @return
	 */
	protected boolean isValidSharedEntity(SharedElement element){
		if(element instanceof SharedEntityElement){
			SharedEntityElement sharedEntityElement = (SharedEntityElement)element;
			if(sharedEntityElement.getEntity() instanceof Domain){
				Domain domain = (Domain)sharedEntityElement.getEntity();
				return isDomainTypeMatch(domain.getDataType().getLiteral());
			}
		}
		return true;
	}
	
	/**
	 * @param domainType
	 * @param type
	 * @return
	 */
	private boolean isDomainTypeMatch(String domainType){
		if(domainType.equalsIgnoreCase(type.getLiteral())){
			return true;
		}
		return false;
	}
}