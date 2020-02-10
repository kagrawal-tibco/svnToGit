package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerElement;

public class WebServiceInputPropertySection extends WebServicePropertySection {
	
	public WebServiceInputPropertySection() {
		super();
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		try {
			mctx.clearDefinitions();
			mctx.setInputMapper(true) ;
			mctx.setMapperPropertySection(this) ;
			EObject userObject = null;
			if (fTSENode != null) {
				userObject = (EObject)fTSENode.getUserObject();
			}
			
			if (fTSEGraph != null) {
				userObject = (EObject)fTSEGraph.getUserObject();
			} 
//			Map<Object,Object> map = new HashMap<Object,Object>() ;
//			map.put(InputMapperPropertySection.mapcontainsLoop,false) ;
//			map.put(InputMapperPropertySection.maploopVartype, "") ;
//			map.put(InputMapperPropertySection.maploopVarpath, "") ;
//			InputMapperPropertySection.checkForIteratorCondition( map ,  userObject ) ;
//			mctx.containsLoop = (Boolean) map.get(InputMapperPropertySection.mapcontainsLoop) ;
//			mctx.loopVartype = (String) map.get(InputMapperPropertySection.maploopVartype) ;
//			mctx.loopvarPath = (String) map.get(InputMapperPropertySection.maploopVarpath) ;
//			mctx.isInputMapper = true ;
			if (userObject != null
					&& (userObject.eClass().equals(BpmnModelClass.SERVICE_TASK))) {
				EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
				EObjectWrapper<EClass, EObject> subProcess = null;
				EObject eContainer = userObject.eContainer();
				if (eContainer != null) {
					if (eContainer.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
						subProcess = EObjectWrapper.wrap(eContainer);
					}
				}
				
				CommonOntologyAdapter adaptor = new CommonOntologyAdapter(fProject.getName());
				ProcessAdapter createAdapter = new ProcessAdapter(
						process.getEInstance(), adaptor);
				if(subProcess == null){
					mctx.addDefinition("job", createAdapter, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, true);
				}else{
					mctx.addDefinitionForSubprocess("job", subProcess, createAdapter,adaptor, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process, true);
				}
				
				refreshMapper();

			} else {
				setNullMapper();
			}
		} catch (Exception e) {
//			BpmnUIPlugin.log(e);
		}

		
	}
	
	private void refreshMapper() {
		EObject userObject = getUserObject();
		String attachedResource = (String) BpmnModelUtils
				.getAttachedResource(userObject);
		currentXslt = getInputMapperXslt();
		if (attachedResource != null && !attachedResource.trim().isEmpty()) {
			if(userObject.eClass().equals(BpmnModelClass.SERVICE_TASK)){
				buildInputSoapSchema(EObjectWrapper.wrap(userObject));
			}else
				getMapper().setEntityURI(attachedResource, currentXslt);
			
		}

	}
	

	
	
	
	@SuppressWarnings("unused")
	private String getVariable(DesignerElement element) {
		String variable = element.getName();

		return variable;
	}
	

	ChangeListener getMapperChangeListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}
	
	@Override
	FocusListener getTextAreaFocusListener() {
		// TODO Auto-generated method stub
		return  new WidgetListener();
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
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT, newXSLT);
					updatePropertySection(updateList);
				}
				
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
			
		}

		@Override
		public void focusLost(FocusEvent e) {
				updateModel();
			
		}
	}

}
