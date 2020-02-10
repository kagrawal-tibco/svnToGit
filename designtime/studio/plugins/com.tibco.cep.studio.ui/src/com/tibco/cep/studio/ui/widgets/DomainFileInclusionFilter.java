package com.tibco.cep.studio.ui.widgets;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.filter.FileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainFileInclusionFilter extends FileInclusionFilter {
	
	private PROPERTY_TYPES type;
	private String projectName;

	/**
	 * @param inclusions
	 * @param projectName
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	public DomainFileInclusionFilter(Set inclusions, String projectName, PROPERTY_TYPES type) {
		super(inclusions);
		this.type = type;
		this.projectName = projectName;
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
				return isValidResource(res);
			}
			if (res instanceof IFolder){
				IFolder folder = (IFolder)res;
				visible = false;
				return isVisible(folder);
			}
		}
		if(element instanceof SharedElementRootNode){
			return true;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#isValidResource(java.lang.Object)
	 */
	protected boolean isValidResource(Object obj){
		try{
			IFile file = (IFile) obj;
			if(file.getFileExtension().equals(CommonIndexUtils.DOMAIN_EXTENSION)){
				Domain domain = IndexUtils.getDomain(projectName, IndexUtils.getFullPath(file));
				return isEntityFile(file) && (domain != null && isDomainTypeMatch(domain.getDataType().getLiteral(), type.getLiteral()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param domainType
	 * @param type
	 * @return
	 */
	private boolean isDomainTypeMatch(String domainType,String type){
		if(domainType.equalsIgnoreCase(type)){
			return true;
		}
		return false;
	}
}
