package com.tibco.cep.bpmn.ui.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Refactoring;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.properties.VariablePropertyTableModelListener;
import com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringPage;

class ProcessVarRenameElementRefactoringPage extends RenameElementRefactoringPage{
	Refactoring refactoring;
	protected ProcessVarRenameElementRefactoringPage(String name,Refactoring refactoring) {
		super(name);
		this.refactoring = refactoring;
	}
	
	@Override
	protected boolean validateName(String text) {
		boolean validateName = false;
		validateName = super.validateName(text);
		if(!validateName){
			return false;
		}
		IResource res = getResource();
//		ProcessVariables propDef = ((BpmnProcessRenameProcessor)((EntityElementRefactoring )refactoring).getProcessor()).prvar;
		List<EObject> allProcesses  = BpmnIndexUtils.getAllProcesses(res.getProject().getName());
		EObject prTobeRefactored = null;
		IFile filePr = null;
		for (EObject process : allProcesses) {
			 filePr = BpmnIndexUtils.getFile(res.getProject().getName(), process);
			if (!res.getFullPath().equals(filePr.getFullPath())) 
				continue;
			prTobeRefactored = process;
			break;
			}
		
		EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(prTobeRefactored); 
		List<EObjectWrapper<EClass, EObject>> propDefs = new ArrayList<EObjectWrapper<EClass,EObject>>();
		List<EObject> listAttribute = useInstance.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
		for (EObject eObject : listAttribute) {
			propDefs.add(EObjectWrapper.wrap(eObject));
		}
		boolean isInvalidPropDef = VariablePropertyTableModelListener.isDuplicatePropertyDefinitionName(propDefs, text);
		if(isInvalidPropDef){
			setErrorMessage(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_DupFilenameExists", text));
			setPageComplete(false);
		}
		return validateName;
	}
//	@Override
//	protected IResource getResource() {
//		if(getRefactoring() instanceof EntityElementRefactoring){
//			EntityElementRefactoring entityRenameRefactoring = (EntityElementRefactoring)getRefactoring();
//			EntityRenameProcessor processor = (EntityRenameProcessor)entityRenameRefactoring.getProcessor();
//			IResource resource = processor.getResource();
//			return resource;
//		}
//		return null;
//	}
//	
	protected boolean isNonEntityResourceRefactor() {
		boolean valid = false ;
		valid = super.isNonEntityResourceRefactor();
		if(valid)
			return true;
		IResource resource = getResource();
		if(resource instanceof IFile){
			IFile file = (IFile)resource;
			if("bpmn".equals(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}
}