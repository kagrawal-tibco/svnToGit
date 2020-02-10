package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.xml.schema.SmElement;

public class WebServiceOutputPropertySection extends WebServicePropertySection {

	public WebServiceOutputPropertySection() {
		super();
	}
	
//	private String loopVartype ;
//	private String loopVarpath ;
	public static String mapcontainsLoop = "containsLoop";
	public static String maploopVartype = "loopVartype";
	public static String maploopVarpath = "loopVarpath";


	public void refresh() {
		try {
			mctx.clearDefinitions();
			EObject userObject = null;
			mctx.setMapperPropertySection(this) ;
			mctx.setInputMapper(false) ;
			if (fTSENode != null) {
				userObject = (EObject) fTSENode.getUserObject();
			}

			if (fTSEConnector != null) {
				userObject = (EObject) fTSEConnector.getUserObject();
			}

			if (fTSEGraph != null) {
				userObject = (EObject) fTSEGraph.getUserObject();
			}
			EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
			if (userObject != null
					&& (userObject.eClass().equals(BpmnModelClass.SERVICE_TASK))) {
					addWsdlSchama();
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, false);
					refreshMapper();

			} else {
				if(userObject != null){
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process,  false);
				}
				refreshMapper();
			}

		} catch (Exception e) {
			// TODO
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

		if(subProcess == null)
			refreshMapper(process);
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
	
	
	
	
//	private String getVariable(DesignerElement element) {
//		String variable = element.getName();
//
//		return variable;
//	}
//	
//	private void addDefinitionForRuleFunctionReturnType(RuleElement element){
//		boolean isArray = false;
//		String returnType = element.getRule().getReturnType();
//		if(returnType == null || returnType.trim().isEmpty())
//			return;
//		if(returnType.endsWith("[]")){
//			returnType=returnType.replace("[]", "");
//			isArray = true;
//		}
//		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(fProject.getName(), returnType);
//		String fullPath = returnType;
//		String name = "return";
//		if (entity != null) {
//			fullPath = CommonIndexUtils.getEntity(fProject.getName(),
//					returnType).getFullPath();
//
//			mctx.addDefinition(name, fullPath, isArray);
//		} else {
//			mctx.addSimpleTypeDefinition(name, returnType, isArray);
//		}
//	}
	

	
	
	
	private void addWsdlSchama() throws Exception {
		EObject userObject = getUserObject();
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put(mapcontainsLoop,false);
		map.put(maploopVartype, "");
		map.put(maploopVarpath, "");
//		InputMapperPropertySection.checkForIteratorCondition( map ,  userObject ) ;
		containsLoop = (Boolean) map.get(mapcontainsLoop);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
		String attribute = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL);
		if(attribute != null && !attribute.isEmpty()){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
					SmElement element = buildOutputSoapSchema(wrap);
					if (element != null && !mctx.getDefinitions().contains(element))
						mctx.addDefinition(element);
		}
	}
	
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
