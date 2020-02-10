package com.tibco.cep.studio.decision.table.ui.constraintpane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.ui.forms.components.FileInclusionFilter;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;
import com.tibco.cep.studio.ui.navigator.model.StateMachineAssociationNode;

/**
 * @author vdhumal
 *
 */
public class EntityTestDataFilter extends FileInclusionFilter {

	private List<String> entityPaths = null;
	private Map<String, String> testDataFilesEntityMap = null;
	
	public EntityTestDataFilter(Set<?> inclusions, List<String> entityPaths) {
		super(inclusions);
		this.entityPaths = entityPaths;
		this.testDataFilesEntityMap = new HashMap<String, String>();
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
				return isEntityFile(file) && isTestDataForSelectedEntity(file);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(element instanceof StateMachineAssociationNode){
			return false;
		}
		
		if(element instanceof PropertyNode){
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param testDataFile
	 * @return
	 */
	public boolean isTestDataForSelectedEntity(IFile testDataFile) {
		String entityPath = TesterCoreUtils.getEntityInfo(testDataFile.getLocation().toString());
		if (this.entityPaths.contains(entityPath)) {
			testDataFilesEntityMap.put(testDataFile.getProjectRelativePath().toString(), entityPath);
			return true;
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public Map<String, String> getTestDataFilesEntityMap() {
		return testDataFilesEntityMap;
	}
}
