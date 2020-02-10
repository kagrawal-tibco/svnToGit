package com.tibco.cep.decision.tree.ui.tool;

import static com.tibco.cep.studio.core.index.utils.IndexUtils.getFullPath;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.decision.tree.common.DecisionTreeModelConstants;
import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.studio.ui.forms.components.FileInclusionFilter;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;

public class DecisionTreeFileInclusionFilter extends FileInclusionFilter{

	private boolean isCallDecisionTree = false;
	@SuppressWarnings("unused")
	private String ownerConcept = null;


	/**
	 * @param inclusions
	 * @param baseAbsolutePath
	 * @param ownerConcept
	 * @param isCallDecisionTree
	 */
	@SuppressWarnings("rawtypes")
	public DecisionTreeFileInclusionFilter(Set inclusions,
										   String baseAbsolutePath,
										   String ownerConcept, 
										   boolean isCallDecisionTree) {
		super(inclusions, baseAbsolutePath);
		this.isCallDecisionTree = isCallDecisionTree;
		this.ownerConcept = ownerConcept;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				String path = getFullPath(file);
				if(isCallDecisionTree){
					return isValidTreeModel(file, path);
				}else{
					return isEntityFile(file) && isBaseEntityFile(baseAbsolutePath, path);
				}
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(isCallDecisionTree){
			if(element instanceof StateEntityNode){
				return false;
			}
		}
		return true;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#isValidResource(java.lang.Object)
	 */
	protected boolean isValidResource(Object res){
		IFile file = (IFile) res;
		String path = getFullPath(file);
		if(isCallDecisionTree){
			return isValidTreeModel(file, path);
		}else{
			return isEntityFile(file) && isBaseEntityFile(baseAbsolutePath, path);
		}
	}
	
	/**
	 * @param file
	 * @param path
	 * @return
	 */
	private boolean isValidTreeModel(IFile file, String path){
		DecisionTree decisionTree = null;
		String fileExtension = file.getFileExtension();
		if (DecisionTreeModelConstants.DTREE_EXTENSION.equals(fileExtension)) {
			//TODO: decisionTree = (DecisionTree)getEntity(file.getProject().getName(), path, ELEMENT_TYPES.DTREE);
		}
		return isEntityFile(file) && !isBaseEntityFile(baseAbsolutePath, path) 
		                          && (isSameOwnerConcept(decisionTree) || isOwnerDecisionTreeInherit(decisionTree));
	}
	
	/**
	 * @param file
	 * @return
	 */
	private boolean isSameOwnerConcept(DecisionTree decisionTree ){
//		if( (decisionTree!= null) && decisionTree.getOwnerConceptPath().equalsIgnoreCase(ownerConcept)){
//			return true;
//		}
		return false;
	}
	
	/**
	 * Check for Owner State Model 
	 * @param file
	 * @return
	 */
	protected boolean isOwnerTreeModel(DecisionTree decisionTree){
/*		Set<String> ownerTreeModels = new HashSet<String>();
		//TODO getCallTreeModelOwners(decisionTree, decisionTree.getOwnerProjectName(), ownerTreeModels);
		for(String ownerTreeModelPath:ownerTreeModels){
			ownerTreeModelPath = ownerTreeModelPath + "." +IndexUtils.getFileExtension(decisionTree);
			if(ownerTreeModelPath.equalsIgnoreCase(baseAbsolutePath)){
				return true;
			}
		}
*/		return false;
	}
	
	/**
	 * @param decisionTree
	 * @return
	 */
	private boolean isOwnerDecisionTreeInherit(DecisionTree decisionTree){
/*		try{
			if(decisionTree!= null && decisionTree.getOwnerConcept() != null){
				Concept concept = decisionTree.getOwnerConcept();
				List<String> subConceptsPaths = getSubConcepts(concept.getFullPath(), concept.getOwnerProjectName());
				if(subConceptsPaths.contains(ownerConcept)){
					return true;
				}
			}}
		catch(Exception e){
			e.printStackTrace();
		}
*/		return false;
	}
	
}