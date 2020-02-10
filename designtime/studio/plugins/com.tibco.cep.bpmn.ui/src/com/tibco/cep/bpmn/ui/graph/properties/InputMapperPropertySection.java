package com.tibco.cep.bpmn.ui.graph.properties;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
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
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author ggrigore
 *
 */
public class InputMapperPropertySection extends MapperPropertySection {

	public Object loopType ;
	
	public InputMapperPropertySection(){
		super();
	}
	
	@Override
	protected void createMapperContextComponent() {
		mctx = new EntityMapperContext(fProject);
		mctx.setInputMapper(true) ;
		if (BpmnModelUtils.isSWTMapper()) {
			mc = new BpmnMapperControl( this,ctx , mctx , getMapperComposite() ,  getInputMapperXslt() );	
		} else {
			mc = new MapperControl(mctx,getMapperComposite());
			mc.getBindingEditor().getBindingTree().enableIgnoreInlineEditChange(true);
		}
		setMapper(mc);
		mctx.setMapper(mc);
	}
	
	public void refresh() {
		try {
			mctx.clearDefinitions();
			
			//Used for SWT mapper 
			getMapper().fproject = fProject ;
			mctx.setMapperPropertySection(this) ;
		    mctx.setInputMapper(true) ;
		    
		    updateCustomFunctions(getMapper().fproject.getName());

			EObject userObject = null;
			if (fTSENode != null) {
				loopType = fTSENode
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_MODE);
				userObject = (EObject)fTSENode.getUserObject();
			}

			if (fTSEGraph != null) {
				userObject = (EObject)fTSEGraph.getUserObject();
			} 
			//			Map<Object,Object> map = new HashMap<Object,Object>();
			//			map.put(mapcontainsLoop,false);
			//			map.put(maploopVartype, "");
			//			map.put(maploopVarpath, "");
			//			map.put(maploopVarname, "");
			//			map.put(maplooptype, "");
			//			map.put(maploopvarisArray, false);
			////			checkForIteratorCondition(map ,  userObject ) ;
			////			if ( loopType != null ) {
			////				map.put(maploopVartype, (String)loopType );
			////			}
			//			BpmnXSLTutils.getloopMapperVar( fProject ,map, userObject) ;
			//			mctx.containsLoop = (Boolean) map.get(mapcontainsLoop) ;
			//			mctx.loopVartype = (String) map.get(maploopVartype) ;
			//			mctx.loopvarPath = (String) map.get(maploopVarpath) ;
			//			mctx.loopVarName = (String) map.get(maplooptype);
			//			mctx.loopVarisArray = (Boolean) map.get(maploopvarisArray);
			if ( userObject != null && userObject.eClass().equals( BpmnModelClass.JAVA_TASK )) {
				getMapper().isJavaTask = true ;
			}
			if (userObject != null
					&& (userObject.eClass().equals(BpmnModelClass.END_EVENT)
							|| userObject.eClass().equals(
									BpmnModelClass.RULE_FUNCTION_TASK) || userObject
									.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)
									|| userObject.eClass().equals(BpmnModelClass.SEND_TASK)
									|| userObject.eClass().equals(BpmnModelClass.INFERENCE_TASK)
									|| userObject.eClass().equals(BpmnModelClass.JAVA_TASK)
									|| userObject.eClass().equals(BpmnModelClass.SUB_PROCESS))) {
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
					mctx.addDefinition(MapperConstants.JOB, createAdapter, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject),process, true);
				}else{
					mctx.addDefinitionForSubprocess(MapperConstants.JOB, subProcess, createAdapter,adaptor, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject),process, true);
				}

				refreshMapper();

			}else if(userObject != null
					&& userObject.eClass().equals(
							BpmnModelClass.CALL_ACTIVITY)){
				EObject attachedResource = (EObject)BpmnModelUtils.getAttachedResource(userObject);
				if(attachedResource != null && attachedResource.eClass().equals(BpmnModelClass.PROCESS)){
					EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
					EObject bpmnIndex = BpmnCorePlugin.getDefault()
							.getBpmnModelManager().getBpmnIndex(fProject);
					ProcessAdapter createAdapter = new ProcessAdapter(
							process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
					mctx.addDefinition(MapperConstants.JOB, createAdapter, false);
					mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process,  true);
					refreshMapperForCallActivity(EObjectWrapper.wrap(attachedResource));
				}else{
					if(userObject != null){
						EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
						mctx.addDefinitionForLoop(EObjectWrapper.wrap(userObject), process,  true);
					}
					refreshMapper();
				}

			} else {
				setNullMapper();
			}
		} catch (Exception e) {
			e.printStackTrace() ; 
			//			BpmnUIPlugin.log(e);
		}


	}

	//	private void refreshMapper(EObjectWrapper<EClass, EObject> process){
	//		ProcessAdapter createAdapter = null;
	//		try {
	//			createAdapter = new ProcessAdapter(process.getEInstance(), new CommonOntologyAdapter(fProject.getName()));
	//		} catch (Exception e) {
	//			BpmnUIPlugin.log(e);
	//		}
	//		
	//		currentXslt = getOutputMapperXslt();
	//
	//		
	//		updateMapper(createAdapter, currentXslt);
	//		
	//	}

	public static void checkForIteratorCondition(Map<Object, Object> map,
			EObject userObject) {
	}
	private void refreshMapperForCallActivity(EObjectWrapper<EClass, EObject> process){
		ProcessAdapter createAdapter = null;
		try {
			createAdapter = new ProcessAdapter(process.getEInstance(), new CommonOntologyAdapter(fProject.getName()));
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

		currentXslt = getInputMapperXslt();

		String  lname = process.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
		lname =MapperConstants.PROCESS;//lname.replace(".", "$");
		updateMapperForCallActivity(createAdapter, currentXslt, lname);

	}

	private void refreshMapper() {

		if (BpmnModelUtils.isSWTMapper()) {
			StudioUIUtils.invokeOnDisplayThread(updateMapper(), false);
			updateMapper();
		} else {
			SwingUtilities.invokeLater(updateMapper());
		}
		
	}

	private Runnable updateMapper() {
		
		return new Runnable() {
			
			@Override
			public void run() {
				EObject userObject = getUserObject();
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
						.wrap(userObject);
				currentXslt = getInputMapperXslt();
				getMapper().setUserObject(userObject);
				if(userObject.eClass().equals(BpmnModelClass.INFERENCE_TASK)){
					getMapper().setEntityForBusinessRule(userObjWrapper, currentXslt);
				}else{
					String attachedResource = (String) BpmnModelUtils
							.getAttachedResource(userObject);
					com.tibco.cep.designtime.core.model.Entity entity   = IndexUtils.getEntity(fProject.getProject().getName(), attachedResource);
					mc.setCoreModelEntity(entity);
					if (attachedResource != null && !attachedResource.trim().isEmpty()) {

						getMapper().setEntityURI(attachedResource, currentXslt);
					}else if(userObject.eClass().equals(BpmnModelClass.SUB_PROCESS)){
						EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
						refreshMapperForSubprocess(process, userObjWrapper);
					}else
						setNullMapper();
				}
				
			}
		};
		
	}
	

	protected void refreshMapperForSubprocess(
			EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> subProcess) {

		try {
			CommonOntologyAdapter commonOntologyAdapter = new CommonOntologyAdapter(fProject.getName());
			ProcessAdapter processAdaptor = new ProcessAdapter(
					process.getEInstance(), commonOntologyAdapter);

			currentXslt = getInputMapperXslt();
			getMapper().setMapperInputForSubprocess(subProcess,
					processAdaptor, commonOntologyAdapter, currentXslt);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
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