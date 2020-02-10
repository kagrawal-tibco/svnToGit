package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;

public class JoinPropertySection extends MapperPropertySection {

	public JoinPropertySection() {
		super();
	}

	@Override
	public void refresh() {
		try {
			mctx.clearDefinitions();
			EObject userObject = null;
			if (fTSENode != null) {
				userObject = (EObject) fTSENode.getUserObject();
			}

			if (fTSEGraph != null) {
				userObject = (EObject) fTSEGraph.getUserObject();
			}
			if (userObject != null
					&& (BpmnModelClass.PARALLEL_GATEWAY
							.isSuperTypeOf(userObject.eClass()))) {
				// TODO:Manish
				// the LHS of the mapper will show the following roots
				// GlobalVars
				// one root for each sequence flow coming in with the
				// seq flow id as the variable name (use
				// BpmnModelUtils.generatedFlowElementName())
				// which should show something like P1.SequenceFlow_02 as
				// P1_SequenceFlow_02
				// each seq flow root has the same structure as the process job
				// structure
				// The RHS side of the mapper should have the process job root
				EObjectWrapper<EClass, EObject> process = getDiagramManager()
						.getModelController().getModelRoot();
				EObject bpmnIndex = BpmnCorePlugin.getDefault()
						.getBpmnModelManager().getBpmnIndex(fProject);
				ProcessAdapter createAdapter = new ProcessAdapter(
						process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
						.wrap(userObject);
				EList<EObject> listAttribute = wrap
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
				for (EObject eObject : listAttribute) {
					EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper
							.wrap(eObject);
					String attribute = seqWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					String replace = attribute.replace(".", "_");
					mctx.addDefinition(replace, createAdapter, false);
				}

				// mctx.addDefinition(createAdapter.getName(), createAdapter,
				// false);
				refreshMapper();
			} else {
				setNullMapper();
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

	}

	private void refreshMapper() {
		EObject userObject = getUserObject();
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
				.wrap(userObject);
		if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper,
				BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSION)) {
			EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(userObjWrapper);
			if (valWrapper != null){
				EObject expression = valWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSION);
				if (expression != null)
					currentXslt = EObjectWrapper.wrap(expression).getAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION);
			}

//			if (currentXslt != null && !currentXslt.trim().isEmpty())
//				currentMapper.updateMapperPanel(currentXslt);
		}
		
		EObjectWrapper<EClass, EObject> process = getDiagramManager()
				.getModelController().getModelRoot();
		EObject bpmnIndex = BpmnCorePlugin.getDefault().getBpmnModelManager()
				.getBpmnIndex(fProject);
		ProcessAdapter createAdapter = new ProcessAdapter(
				process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
		updateMapper(createAdapter, currentXslt);
		
	}

	ChangeListener getMapperChangeListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}
	
	@Override
	FocusListener getTextAreaFocusListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}

	private class WidgetListener implements ChangeListener, FocusListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			updateModel();
		}
		
		private void updateModel(){
			EObject userObject = getUserObject();
			if (userObject != null) {
				String newXSLT = currentMapper.getNewXSLT();
				if (newXSLT != null && !newXSLT.equals(currentXslt)) {
					Map<String, Object> updateList = new HashMap<String, Object>();
					currentXslt = newXSLT;
					EObjectWrapper<EClass, EObject> createParallelGatewayJoinExpressionData = getDiagramManager().getModelController().createParallelGatewayJoinExpressionData(currentXslt);
					updateList
							.put(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSION,
									createParallelGatewayJoinExpressionData.getEInstance());
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

}
