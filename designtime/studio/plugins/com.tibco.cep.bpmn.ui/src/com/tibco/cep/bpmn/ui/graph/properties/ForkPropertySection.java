package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;

public class ForkPropertySection extends MapperPropertySection {
	private static String MAPPER_OPTION = "Mapper";
	private static String COPY_OPTION = "Copy";
	
	private CCombo outGoingEdgesCombo;
	private CCombo mappingOptionCombo;
	private String currentSelectedSeqId;
	
	public ForkPropertySection() {
		super();
		currentSelectedSeqId = "";
	}

	//TODO: Manish
	//The fork Mapper UI will show the follow
	// A combo box with the following options
	// Copy
	// Mapper
	// if the copy option is selected it means the LHS job data
	// will be copied to each one of the fork paths i.e each
	// transform xslt:// will copy all the Job attributes from LHS to RHS 
	// The mapper can show a pre configured xslt:// which basically copies the job and
	// the mapper stays in read only mode. i.e the user does not have to do anything.
	// if Mapper is selected then the mapper will be shown allowing the user
	// to specify the mapping.
	// The LHS shows the process job structure with the "job" variable
	// The RHS shows one root for each seq flow with the process job structure
	

	@Override
	public void refresh() {
		try {
			mctx.clearDefinitions();
			EObject userObject = null;
			if (fTSENode != null) {
				userObject = (EObject)fTSENode.getUserObject();
			}
			
			if (fTSEGraph != null) {
				userObject = (EObject)fTSEGraph.getUserObject();
			} 
			if (userObject != null	&& (BpmnModelClass.PARALLEL_GATEWAY
					.isSuperTypeOf(userObject.eClass()))) {
				//TODO:Manish
				// the LHS of the mapper will show the following roots
				// GlobalVars
				// the process job root
				// 
				// The RHS one root for each sequence flow going out with the 
				// seq flow id as the variable name (use BpmnModelUtils.generatedFlowElementName())
				// which should show something like P1.SequenceFlow_02 as P1_SequenceFlow_02
				// each seq flow root has the same structure as the process job structure
				EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
				EObject bpmnIndex = BpmnCorePlugin.getDefault()
						.getBpmnModelManager().getBpmnIndex(fProject);
				ProcessAdapter createAdapter = new ProcessAdapter(
						process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
				mctx.addDefinition("job", createAdapter, false);
				
				outGoingEdgesCombo.removeAll();
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
						.wrap(userObject);
				EList<EObject> listAttribute = wrap
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
				for (EObject eObject : listAttribute) {
					EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper
							.wrap(eObject);
					String attribute = seqWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					outGoingEdgesCombo.add(attribute);
				}
				
				currentSelectedSeqId = outGoingEdgesCombo.getText();
				
				outGoingEdgesCombo.select(0);
				outGoingEdgesCombo.setEnabled(true);
				
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(wrap);
				EEnumLiteral option =(EEnumLiteral)valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_MAPPING_OPTION);
				if(option!= null && option.equals(BpmnModelClass.ENUM_MAPPING_OPTION_COPY)){
					mappingOptionCombo.setText(COPY_OPTION);
					currentMapper.getBindingEditor().setEnabled(false);
				}
				else{
					mappingOptionCombo.setText(MAPPER_OPTION);
					currentMapper.getBindingEditor().setEnabled(true);
				}
				
				refreshMapper();
			} else {
				setNullMapper();
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
	}
	
	protected void createNodeTypeSpecificControl(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label createLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("forkPropertySection_mappingOptionCombo_label"),  SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.RIGHT;
		createLabel.setLayoutData(gd);
		mappingOptionCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		gd = new GridData(SWT.LEFT);
		gd.horizontalAlignment = SWT.LEFT;
		gd.widthHint = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		mappingOptionCombo.setLayoutData(gd);
		mappingOptionCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(mappingOptionCombo.getText().equals(COPY_OPTION)){
					copyActionSelected();
				}else{
					mapperActionSelected();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		mappingOptionCombo.add(MAPPER_OPTION);
		mappingOptionCombo.add(COPY_OPTION);
		mappingOptionCombo.setText(MAPPER_OPTION);
		
		createLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("forkPropertySection_outGoingEdgesCombo_label"),  SWT.NONE);
		gd = new GridData();
		gd.horizontalAlignment = SWT.RIGHT;
		createLabel.setLayoutData(gd);
		outGoingEdgesCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		gd = new GridData();
		gd.horizontalAlignment = SWT.LEFT;
		gd.widthHint = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		outGoingEdgesCombo.setLayoutData(gd);
		outGoingEdgesCombo.setEnabled(false);
		outGoingEdgesCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshMapper();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		composite.pack();
	}
	
	private void refreshMapper() {
		try {
			String text = outGoingEdgesCombo.getText();
			currentSelectedSeqId = text;
			String xslt = "";
			EObject userObject = getUserObject();
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrap);
			EList<EObject> listAttribute = valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_EXPRESSIONS);
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> expressionWrap = EObjectWrapper.wrap(eObject);
				String seqId = expressionWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SEQUENCE_FLOW_ID);
				if(seqId.equals(text)){
					xslt = (String)expressionWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION);
					break;
				}
			}
			

			EObjectWrapper<EClass, EObject> process = getDiagramManager()
					.getModelController().getModelRoot();
			EObject bpmnIndex = BpmnCorePlugin.getDefault().getBpmnModelManager()
					.getBpmnIndex(fProject);
			ProcessAdapter createAdapter = new ProcessAdapter(
					process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
			updateMapper(createAdapter, xslt);
			
//			if(!xslt.trim().isEmpty()){
//				currentMapper.updateMapperPanel(xslt);
//			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	private void copyActionSelected(){
		EObject userObject = getUserObject();
		if(userObject != null){
			Map<String, Object> updateList = new HashMap<String, Object>();
			updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_FORK_MAPPING_OPTION, BpmnModelClass.ENUM_MAPPING_OPTION_COPY);
			String createNewXSLT = currentMapper.createNewXSLT(getCopyXsltTemplate());
			currentMapper.getBindingEditor().setEnabled(false);
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(userObject);
			EList<EObject> listAttribute = wrap
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			ArrayList<EObject> forkTransformations = new ArrayList<EObject>();
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper
						.wrap(eObject);
				String attribute = seqWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				EObjectWrapper<EClass, EObject> createGatewayExpressionData = getDiagramManager().getModelController().createGatewayForkJoinExpressionData(attribute, createNewXSLT);
				forkTransformations.add(createGatewayExpressionData.getEInstance());
			}
			
			updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_FORK_EXPRESSIONS, forkTransformations);
			updatePropertySection(updateList);
		}
		refreshMapper();
	}
	
	private void mapperActionSelected(){
		EObject userObject = getUserObject();
		if(userObject != null){
			currentMapper.getBindingEditor().setEnabled(true);
			Map<String, Object> updateList = new HashMap<String, Object>();
			updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_FORK_MAPPING_OPTION, BpmnModelClass.ENUM_MAPPING_OPTION_MAPPER);
			ArrayList<EObject> forkTransformations = new ArrayList<EObject>();
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(userObject);
			EList<EObject> listAttribute = wrap
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper
						.wrap(eObject);
				String attribute = seqWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				EObjectWrapper<EClass, EObject> createGatewayExpressionData = getDiagramManager().getModelController().createGatewayForkJoinExpressionData(attribute, "");
				forkTransformations.add(createGatewayExpressionData.getEInstance());
			}
			updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_FORK_EXPRESSIONS, forkTransformations);
			updatePropertySection(updateList);
		}
		
		refreshMapper();
	}
	
	private String getCopyXsltTemplate(){
		return MapperControl.getCopyXsltTemplate();
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
	
	private class WidgetListener implements ChangeListener, FocusListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			updateModel();
		}
		
		private void updateModel(){
			EObject userObject = getUserObject();
			if(userObject != null){
				String text = currentSelectedSeqId;
				String newXSLT = currentMapper.getNewXSLT();
				String oldxslt = "";
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(wrap);
				EList<EObject> listAttribute = valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_EXPRESSIONS);
				ArrayList<EObject> forkExpressions = new ArrayList<EObject>(listAttribute);
				Map<String, Object> updateList = new HashMap<String, Object>();
				boolean changed = false;
				for (EObject eObject : listAttribute) {
					EObjectWrapper<EClass, EObject> expressionWrap = EObjectWrapper.wrap(eObject);
					String seqId = expressionWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SEQUENCE_FLOW_ID);
					if(seqId.equals(text)){
						oldxslt = (String)expressionWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION);
						if(newXSLT != null  && !newXSLT.equals(oldxslt)){
							expressionWrap.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION, newXSLT);
							updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_FORK_EXPRESSIONS, forkExpressions);
							changed = true;
							break;
						}

					}
				}
				if(changed){
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
