package com.tibco.cep.bpmn.ui.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.refactoring.BpmnProcessRefactiongForXsltHelper;
import com.tibco.cep.bpmn.core.refactoring.BpmnProcessRefactoringParticipant;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.properties.ProcessVariables;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.studio.core.refactoring.EntityRenameProcessor;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;


public class BpmnProcessRenameProcessor extends EntityRenameProcessor {

	private boolean processVarRef =false;
	private boolean changeBool = false;
	private IResource resource = null ;
	private IFile file = null ;
	public ProcessVariables prvar = null ;
	List<EObjectWrapper<EClass, EObject>> propDefs = new ArrayList<EObjectWrapper<EClass,EObject>>();
	public BpmnProcessRenameProcessor(IRefactoringContext context) {
		super(context);
		Object element = context.getElement();
		if ( element instanceof ProcessVariables ){
			ProcessVariables prVar = (ProcessVariables) element ;
			this.setNewName(prVar.getName());
		}
	}
	
	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		List<RefactoringParticipant> participants = new ArrayList<RefactoringParticipant>();
		return participants.toArray(new RefactoringParticipant[participants.size()]);
	}
	@Override
	public IResource getResource() {
		resource = super.getResource();
		if (resource != null) {
			return resource;
		}
		EObjectWrapper<EClass, EObject> processWrap = null;
		if (getfContext().getElement() instanceof ProcessVariables) {
			prvar = (ProcessVariables) this.getfContext().getElement();
			processWrap = (EObjectWrapper<EClass, EObject>) prvar.getProcess();
			file = BpmnIndexUtils.getFile(prvar.getProjectName(),
					processWrap.getEInstance());
			resource = file;
		}
		return resource;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		// create the actual file name change here
		EObjectWrapper<EClass, EObject> processWrap = null ;
		processWrap  = ( EObjectWrapper<EClass, EObject> )prvar.getProcess() ;
		if (resource instanceof IFolder || resource instanceof IProject) {
			return null; // these changes are done in the pre-change
		}
		CompositeChange compositeChange = new CompositeChange("Process changes:");
		handleProcessVarXsltRefactor(compositeChange , file , prvar , resource);
		Change change = null;
		if(compositeChange.getChildren().length > 0)
			change = compositeChange;
		
		return change;
	}	
	
	public void handleProcessVarXsltRefactor(CompositeChange compositeChange,
			IFile file , ProcessVariables propDef , IResource resource) {
		processVarRef = false;
		List<EObject> allProcesses  = BpmnIndexUtils.getAllProcesses(propDef.getProjectName());
		EObject prTobeRefactored = null;
		IFile filePr = null;
		for (EObject process : allProcesses) {
			 filePr = BpmnIndexUtils.getFile(propDef.getProjectName(), process);
			if (!resource.getFullPath().equals(filePr.getFullPath())) 
				continue;
			prTobeRefactored = process;
			break;
			}
//			refactorProcessVariable( compositeChange,filePr ,  prTobeRefactored ,  propDef) ;
//			BpmnProcessRefactoringParticipant refact = new BpmnProcessRefactoringParticipant() ;
			
			String oldPath = "job"+ "/" + propDef.getName();
			String newPath = "job"+ "/" + this.getfArguments().getNewName();
			handleProcessVariableRefactor(filePr , compositeChange ,prTobeRefactored, oldPath , newPath , propDef );
//			if( processVarRef && ! changeBool ) {
//			try {
//				EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(prTobeRefactored);
//				Change change = BpmnProcessRefactoringParticipant.createTextFileModification(filePr, processWrapper, null,false,null);
//				compositeChange.add(change);
//			} catch ( Exception e )  {
//				
//			}
//		 }
		
	}
	
	public  void refactorProcessVariable(CompositeChange compositeChange,
			IFile file , EObject eobj , ProcessVariables procvar) {
		String oldName = procvar.getName() ;
		EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(eobj); 
		List<EObjectWrapper<EClass, EObject>> propDefs = new ArrayList<EObjectWrapper<EClass,EObject>>();
		List<EObject> listAttribute = useInstance.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
		for (EObject eObject : listAttribute) {
			propDefs.add(EObjectWrapper.wrap(eObject));
		}
		listAttribute.clear();
		for(EObjectWrapper<EClass, EObject> propDef:propDefs){
			if(oldName.equals(propDef.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME))){
				if(propDef.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)){
					propDef.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, this.getfArguments().getNewName());
					useInstance.addToListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES, propDef) ;
					processVarRef = true ;
				} else
					continue;
			}
			useInstance.addToListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES, propDef) ;
	  }
		
	}
	
	public  boolean handleProcessVariableRefactor (IFile file ,CompositeChange Compchange ,EObject processToBeRefactor, String oldPath, String newPath , ProcessVariables procvar ) {
		List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(processToBeRefactor);
		allFlowNodes.add(0, processToBeRefactor);
		EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(processToBeRefactor); 
		refactorProcessVariable( Compchange,file ,  processToBeRefactor ,  procvar) ;
		boolean inputChange = false;
		boolean outputChange = false;
		for (EObject eObject : allFlowNodes) {
		
			String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(eObject);
			if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
				inputChange = BpmnProcessRefactiongForXsltHelper.processXSLTFunctionForProcessVariableRefactor(eObject, newPath, oldPath, inputMapperXslt, false, null,true);
				
			}
			String outputMapperXslt = BpmnModelUtils.getOutputMapperXslt(eObject);
			if(outputMapperXslt != null && !outputMapperXslt.isEmpty()){
				String namespace = RDFTnsFlavor.BE_NAMESPACE + "/" +file.getProjectRelativePath().removeFileExtension().toString();
				String ns = getNamespace( outputMapperXslt , namespace);
				if(ns == null) {
					continue;
				}
				String oldopPath = ns + ":" + procvar.getName();
				String newopPath = ns + ":" + this.getfArguments().getNewName();
				System.out.println(oldopPath + newopPath );
				outputChange = BpmnProcessRefactiongForXsltHelper.processOpXSLTFunctionForProcessVarrefactor(eObject, newopPath, oldopPath, outputMapperXslt);
				
			}
			
		}
		if(inputChange || outputChange || processVarRef){
			changeBool = true;
		}
		try {
			if ( changeBool  ) {
				EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(processToBeRefactor);
				Change change = BpmnProcessRefactoringParticipant.createTextFileModification(file, processWrapper, null,false,null);
				Compchange.add(change);
			}
		} catch ( Exception e ){
			System.out.println("Exception while creating tex file changes");
		}
		
		
		return changeBool ;
	}
	
	public static String getNamespace( String xslt , String ns ){
		String prefix = null ;
		TemplateBinding binding = MapperCoreUtils.getBinding(xslt, new ArrayList());
		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding)binding.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
		for (int i=0; i<nd.length; i++) {
//			nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
			if (ns.equals(nd[i].getNamespace())) {
				prefix = nd[i].getPrefix() ;
				break ;
			}
		}
//		ArrayList<?> receivingParams = XSTemplateSerializer.getReceivingParms(xslt);
		return prefix ;
	}
	
	
}
