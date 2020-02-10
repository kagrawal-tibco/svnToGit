package com.tibco.cep.bpmn.ui.graph.properties;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * 
 * @author ggrigore
 *
 */
public class OutputMapperPropertySection extends MapperPropertySection {
	
	
//	private boolean containsLoop ;
//	private String loopVartype ;
//	private String loopVarpath ;

	public OutputMapperPropertySection(){
		super();
	}
	
	@Override
	protected EObject getUserObject() {
		EObject userObject = null;
		if (fTSENode != null) {
			userObject = (EObject) fTSENode.getUserObject();
		}
		
		if (fTSEConnector != null) {
			userObject = (EObject) fTSEConnector.getUserObject();
		}

		if (fTSEGraph != null) {
			userObject = (EObject) fTSEGraph.getUserObject();
		}
		
		return userObject;
	}
	
	@Override
	protected void createMapperContextComponent() {
		mctx = new EntityMapperContext(fProject);
		mctx.setInputMapper(false) ;
		if (BpmnModelUtils.isSWTMapper()) {
			mc = new BpmnMapperControl(this, ctx, mctx, getMapperComposite(),getOutputMapperXslt());
		} else {
			mc = new MapperControl(mctx,getMapperComposite());
			mc.getBindingEditor().getBindingTree().enableIgnoreInlineEditChange(true);
		}
		setMapper(mc);
		mctx.setMapper(mc);
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		try {
			mctx.clearDefinitions();
			EObject userObject = null;
			if (fTSENode != null) {
				userObject = (EObject) fTSENode.getUserObject();
			}
			
			if (fTSEConnector != null) {
				userObject = (EObject) fTSEConnector.getUserObject();
			}

			if (fTSEGraph != null) {
				userObject = (EObject) fTSEGraph.getUserObject();
			}
			//Used Swt mapper 
			mctx.setMapperPropertySection(this) ;
			mctx.setInputMapper(false) ;
			getMapper().fproject = fProject ;
			
		    updateCustomFunctions(getMapper().fproject.getName());
			
			EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();

			if (userObject != null
					&& (userObject.eClass().equals(BpmnModelClass.START_EVENT) || userObject.eClass().equals(BpmnModelClass.BOUNDARY_EVENT)
							 || userObject.eClass().equals(BpmnModelClass.SUB_PROCESS)
							|| userObject.eClass().equals(
									BpmnModelClass.RULE_FUNCTION_TASK)  || userObject.eClass().equals(
									BpmnModelClass.RECEIVE_TASK) || userObject.eClass().equals(
											BpmnModelClass.JAVA_TASK))) {
				String attachedResource = (String) BpmnModelUtils
						.getAttachedResource(userObject);
				com.tibco.cep.designtime.core.model.Entity entity = IndexUtils.getEntity(fProject.getProject().getName(),attachedResource);
				if(entity!= null){
					mc.setCoreModelEntity(entity);
				} else {
					mc.setCoreModelEntity(null);
				}
				if (attachedResource != null
						&& !attachedResource.trim().isEmpty()) {
					DesignerElement element = IndexUtils.getElement(
							fProject.getName(), attachedResource);
					String variable = "";
					if (element != null
							&& !(variable = getVariable(element)).trim()
									.isEmpty()) {
						if (userObject.eClass().equals(
								BpmnModelClass.START_EVENT))
							mctx.addDefinition(variable, attachedResource,
									false);
						else if (userObject.eClass().equals(
								BpmnModelClass.BOUNDARY_EVENT))
							mctx.addDefinition(variable, attachedResource,
									false);
						else if (userObject.eClass().equals(
								BpmnModelClass.RECEIVE_TASK))
							mctx.addDefinition(variable, attachedResource,
									false);
						else if (userObject.eClass().equals(BpmnModelClass.JAVA_TASK)) {
							mctx.addJavaTaskDefinition(BpmnModelUtils.getMethodReturnType(userObject), fProject.getName(),(JavaElement)element);
						} else if (userObject.eClass().equals(
								BpmnModelClass.RULE_FUNCTION_TASK)
								&& element instanceof RuleElement) {
							addDefinitionForRuleFunctionReturnType((RuleElement) element);
						}
						mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, false);
						refreshMapper();
					}
				}else if (userObject.eClass().equals(
						BpmnModelClass.SUB_PROCESS)) {
					CommonOntologyAdapter adaptor = new CommonOntologyAdapter(fProject.getName());
					ProcessAdapter createAdapter = new ProcessAdapter(
							process.getEInstance(), adaptor);
					EObjectWrapper<EClass, EObject> subProcess = EObjectWrapper.wrap(userObject);
//					String  lname = subProcess.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//					lname =lname.replace(".", "$");
					String lname = MapperConstants.RETURN;
					mctx.addDefinitionForSubprocess(lname, subProcess, createAdapter,adaptor, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, false);
					refreshMapper();
				}else{
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, false);
					refreshMapper();
				}
			} else if(userObject != null
					&& userObject.eClass().equals(
					BpmnModelClass.CALL_ACTIVITY)){
				EObject attachedResource = (EObject)BpmnModelUtils.getAttachedResource(userObject);
				if(attachedResource != null && attachedResource.eClass().equals(BpmnModelClass.PROCESS)){
					EObject bpmnIndex = BpmnCorePlugin.getDefault()
							.getBpmnModelManager().getBpmnIndex(fProject);
					ProcessAdapter createAdapter = new ProcessAdapter(
							attachedResource, new ProcessOntologyAdapter(bpmnIndex));
					String  lname = EObjectWrapper.wrap(attachedResource).getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
					lname = MapperConstants.RETURN;//lname.replace(".", "$");
					mctx.addDefinition(lname, createAdapter, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject),process,  false);
					
					refreshMapper(process);
				}else{
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, false);
					refreshMapper();
				}
				
			}else {
				if(userObject != null){
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process,  false);
				}
				refreshMapper();
			}
			
		} catch (Exception e) {
			System.out.println("exception while creatng defintion");
		}
		
	}

	private void refreshMapper() {
		EObjectWrapper<EClass, EObject> process = getDiagramManager()
				.getModelController().getModelRoot();
		EObjectWrapper<EClass, EObject> subProcess = null;
		EObject userObject = getUserObject();
		EObject eContainer = userObject.eContainer();
		if (eContainer != null) {
			if (eContainer.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
				subProcess = EObjectWrapper.wrap(eContainer);
			}
		}

		if(subProcess == null ){
			refreshMapper(process);
		}
		else
			refreshMapperForSubprocess(process, subProcess);
		
		
	}
	
	protected void refreshMapperForSubprocess(
			EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> subProcess) {

		try {
			 CommonOntologyAdapter commonOntologyAdapter = new CommonOntologyAdapter(fProject.getName());
			ProcessAdapter processAdaptor = new ProcessAdapter(
					process.getEInstance(), commonOntologyAdapter);

			currentXslt = getOutputMapperXslt();

			getMapper().setMapperInputForSubprocess(subProcess,
					processAdaptor, commonOntologyAdapter, currentXslt);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}
	

	
	private void refreshMapper(EObjectWrapper<EClass, EObject> process){
		ProcessAdapter createAdapter = null;
		try {
			createAdapter = new ProcessAdapter(process.getEInstance(), new CommonOntologyAdapter(fProject.getName()));
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
		currentXslt = getOutputMapperXslt();
		
		updateMapper(createAdapter, currentXslt);
		
	}
	
	
	private String getVariable(DesignerElement element) {
		String variable = element.getName();

		return variable;
	}
	
	private void addDefinitionForRuleFunctionReturnType(RuleElement element){
		boolean isArray = false;
		String returnType = element.getRule().getReturnType();
		if(returnType == null || returnType.trim().isEmpty())
			return;
		if(returnType.endsWith("[]")){
			returnType=returnType.replace("[]", "");
			isArray = true;
		}
		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(fProject.getName(), returnType);
		String fullPath = returnType;
		String name = MapperConstants.RETURN;
		if (entity != null) {
			fullPath = CommonIndexUtils.getEntity(fProject.getName(),
					returnType).getFullPath();
//			if(returnEnt == null)
			returnEnt=entity;
			mctx.addDefinition(name, fullPath, isArray);
		} else {
//			if (BpmnModelUtils.isSWTMapper()){
//				mctx.createRfReturnSchema(element,returnType,isArray);
//			} else
				mctx.addSimpleTypeDefinition(name, returnType, isArray,element);
			
		}
	}
	

	
//	private void addDefinitionForWSdlInput(String wsdlFilePath){
//		try {
//			WsdlWrapper wsdl = WsdlWrapperFactory.getWsdl(fProject, wsdlFilePath);
//			addWsdlSchama(wsdl);
//		} catch (Exception e) {
////			BpmnUIPlugin.log(e);
//		}
//	}
	
//	private void addWsdlSchama(WsdlWrapper wsdl) throws Exception {
//		EObject userObject = getUserObject();
//		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
//		String attribute = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL);;
//		if(attribute != null && !attribute.isEmpty()){
//			
//			if(wsdl != null){
//				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
//				EObject attr= (EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_OPERATION_REF);
//				if (attr != null) {
//					String name = EObjectWrapper.wrap(attr).getAttribute(
//							BpmnMetaModelConstants.E_ATTR_NAME);
//					SmElement element = wsdl.getOutMessgeElement(name);
//					if (element != null)
//						mctx.addDefinition(name, element);
//				}
//			}
//		}
//	}
	
	@Override
	ChangeListener getMapperChangeListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}
	
	private class WidgetListener implements ChangeListener, FocusListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			updateModel();
		}
		
		private void updateModel(){
			EObject userObject = getUserObject();
			if(userObject != null){
				String newXSLT = currentMapper.getNewXSLT();
				if(newXSLT != null  && !newXSLT.equals(currentXslt)){
					Map<String, Object> updateList = new HashMap<String, Object>();
					currentXslt = newXSLT;
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT, newXSLT);
					updatePropertySection(updateList);
				}
			}
		}


		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
				updateModel();
			
		}
	}

	@Override
	FocusListener getTextAreaFocusListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}

}