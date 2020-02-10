package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubDomains;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.forms.components.FileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainFileInclusionFilter extends FileInclusionFilter {

	private DOMAIN_DATA_TYPES type;
	private Entity entity;
	private String projectName;

	/**
	 * @param inclusions
	 * @param projectName
	 * @param type
	 * @param baseAbsolutePath
	 */
	public DomainFileInclusionFilter(Set<String> inclusions, 
			                         String projectName,
			                         DOMAIN_DATA_TYPES type, 
			                         String baseAbsolutePath) {
		super(inclusions, baseAbsolutePath);
		this.type = type;
		entity = IndexUtils.getEntity(projectName, baseAbsolutePath);
		this.projectName = projectName;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				String path = IndexUtils.getFullPath(file);
				return isEntityFile(file) && !isBaseEntityFile(baseAbsolutePath, path) 
										  && isTypeMatch(file)
				                          && !isSuperFile(path, entity) ;
			}
			if (res instanceof IFolder) {
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(element instanceof SharedElementRootNode){
			return true;
		} else if (element instanceof SharedEntityElement) {
			if (((SharedEntityElement) element).getEntity() instanceof Domain) {
				Domain d = (Domain) ((SharedEntityElement) element).getEntity();
				return d.getDataType() == type;
			}
		}
		return true;
	}
	
	/**
	 * @param domainFile
	 * @return
	 */
	private boolean isTypeMatch(IFile domainFile) {
		if(IndexUtils.getDomain(projectName, IndexUtils.getFullPath(domainFile)).getDataType() == type)
			return true;
		return false;
	}
	
	/**
	 * @param path
	 * @param entity
	 * @return
	 */
	public boolean isSuperFile(String path, Entity entity ){
		if(entity instanceof Domain){
			Domain domain = (Domain)entity;
			List<String> subDomainPaths = getSubDomains(domain.getFullPath(), projectName);
			if(subDomainPaths.contains(path)){
				return true;
			}
		}
		return false;
	}
}
