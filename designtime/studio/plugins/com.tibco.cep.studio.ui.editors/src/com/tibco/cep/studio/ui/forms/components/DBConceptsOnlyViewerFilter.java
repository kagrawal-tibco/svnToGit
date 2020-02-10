package com.tibco.cep.studio.ui.forms.components;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * A ViewFilter to show only DBConcepts, also show only those folders which contain a DBConcept (hides other folders)
 * 
 * @author moshaikh
 *
 */
public class DBConceptsOnlyViewerFilter extends ViewerFilter {
	
	private final String projectName;
	
	public DBConceptsOnlyViewerFilter(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFolder) {
			return selectFolder((IFolder) element);
		} else if (element instanceof IFile) {
			String path = IndexUtils.getFullPath((IResource) element);
			return isDBConcept(path, this.projectName);
		}
		return true;
	}
	
	/**
	 * Whether or not the specified concept path represents a DBConcept.
	 * @param path
	 * @param projectName
	 * @return
	 */
	public static boolean isDBConcept(String path, String projectName) {
		Concept concept = IndexUtils.getConcept(projectName, path);
		if (concept != null && concept.getExtendedProperties() != null) {
			for (Entity plist : concept.getExtendedProperties().getProperties()) {
				if ("OBJECT_NAME".equals(plist.getName())) {
					return true;
				}
			}
		}
		return false; 
	}
	
	/**
	 * Select the folder to be shown only if it has a DBConcept under its hierarchy.
	 * @param folder
	 * @return
	 */
	private boolean selectFolder(IFolder folder) {
		boolean selectFolder = false;
		for (String path : CommonUtil.getResourcesByExtension(folder, "concept")) {
			if(isDBConcept("/" + path.replaceAll(".concept",  ""), this.projectName)) {
				selectFolder = true;
				break;
			}
		}
		return selectFolder;
	}
} 
