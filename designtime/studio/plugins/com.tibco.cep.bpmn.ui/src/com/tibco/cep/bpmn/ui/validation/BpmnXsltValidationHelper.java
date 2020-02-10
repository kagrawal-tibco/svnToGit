package com.tibco.cep.bpmn.ui.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.properties.AbstractMapperControl;
import com.tibco.cep.bpmn.ui.graph.properties.BpmnMapperControl;
import com.tibco.cep.bpmn.ui.graph.properties.MapperControl;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection.EntityMapperContext;
import com.tibco.cep.bpmn.ui.graph.properties.WebServiceMapperHelper;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo.NamespaceDeclaration;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportGenerationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChangeList;
import com.tibco.cep.mapper.xml.xdata.bind.fix.FormulaErrorBindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.XMLReadException;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingDisplayUtils;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * 
 * @author majha
 * 
 */
public class BpmnXsltValidationHelper {
	private static final String INPUT_ERROR_PREFIX = BpmnMessages.getString("bpmnXsltValidationHelper_INPUT_ERROR_PREFIX");
	private static final String OUTPUT_ERROR_PREFIX =  BpmnMessages.getString("bpmnXsltValidationHelper_OUTPUT_ERROR_PREFIX");

	private IProject project;
	private EObjectWrapper<EClass, EObject> process;
	private CommonOntologyAdapter commonOntologyAdaptor;

	public BpmnXsltValidationHelper(IProject project,
			EObjectWrapper<EClass, EObject> process) throws Exception {
		this.project = project;
		this.process = process;
		this.commonOntologyAdaptor = new CommonOntologyAdapter(
				project.getName());
	}

	public List<String> validateMapperFunctions(
			EObjectWrapper<EClass, EObject> flowElement) {
		List<String> errors = new ArrayList<String>();
		EntityMapperContext mctx = new MapperPropertySection.EntityMapperContext(
				project);
		AbstractMapperControl mc = null;
		if (BpmnModelUtils.isSWTMapper()) {
			mc = new BpmnMapperControl(mctx, null);
		} else{
			mc = new MapperControl(mctx, null);
		}
		mctx.setMapper(mc);
		EObject eInstance = flowElement.getEInstance();
		String xslt = getInputMapperXslt(eInstance);
		SmElement outputType = null;
		if (xslt != null && !xslt.trim().isEmpty()) {
			
			EObjectWrapper<EClass, EObject> objectWrapper = EObjectWrapper
					.wrap(eInstance);
			
			if (eInstance.eClass().equals(BpmnModelClass.END_EVENT)
					|| eInstance.eClass().equals(
							BpmnModelClass.RULE_FUNCTION_TASK)
					|| eInstance.eClass().equals(
							BpmnModelClass.BUSINESS_RULE_TASK)
					|| eInstance.eClass().equals(BpmnModelClass.SEND_TASK)
					|| eInstance.eClass().equals(BpmnModelClass.INFERENCE_TASK)
					|| eInstance.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
				EObjectWrapper<EClass, EObject> subProcess = null;
				EObject eContainer = eInstance.eContainer();
				if (eContainer != null) {
					if (eContainer.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
						subProcess = EObjectWrapper.wrap(eContainer);
					}
				}

				ProcessAdapter createAdapter = new ProcessAdapter(
						process.getEInstance(), commonOntologyAdaptor);
				if (subProcess == null) {
					mctx.addDefinition(MapperConstants.JOB, createAdapter,
							false);
				} else {
					mctx.addDefinitionForSubprocess(MapperConstants.JOB,
							subProcess, createAdapter,
							commonOntologyAdaptor, false);
				}
				
				outputType = getOutputTypeForInputMapper(flowElement, mc, xslt);

			} else if (eInstance != null
					&& eInstance.eClass().equals(BpmnModelClass.CALL_ACTIVITY)) {
				EObject attachedResource = (EObject) BpmnModelUtils
						.getAttachedResource(eInstance);
				if (attachedResource != null
						&& attachedResource.eClass().equals(
								BpmnModelClass.PROCESS)) {
					EObject bpmnIndex = BpmnCorePlugin.getDefault()
							.getBpmnModelManager().getBpmnIndex(project);
					ProcessAdapter createAdapter = new ProcessAdapter(
							process.getEInstance(), new ProcessOntologyAdapter(
									bpmnIndex));
					mctx.addDefinition(MapperConstants.JOB, createAdapter,
							false);
					outputType = getOutputForCallActivityForInputMapping(EObjectWrapper.wrap(attachedResource), mc, xslt);
				}

			}else if(eInstance.eClass().equals(BpmnModelClass.SERVICE_TASK)){
				EObjectWrapper<EClass, EObject> subProcess = null;
				EObject eContainer = eInstance.eContainer();
				if (eContainer != null) {
					if (eContainer.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
						subProcess = EObjectWrapper.wrap(eContainer);
					}
				}
				
				ProcessAdapter createAdapter = new ProcessAdapter(
						process.getEInstance(), commonOntologyAdaptor);
				if(subProcess == null){
					mctx.addDefinition("job", createAdapter, false);
				}else{
					mctx.addDefinitionForSubprocess("job", subProcess, createAdapter,commonOntologyAdaptor, false);
				}
				
				outputType = getOutputTypeForWsInputMapping(flowElement.getEInstance(), mc, xslt, mctx);
			}  else if(eInstance.eClass().equals(BpmnModelClass.JAVA_TASK)) {
				EObjectWrapper<EClass, EObject> subProcess = null;
				EObject eContainer = eInstance.eContainer();
				if (eContainer != null) {
					if (eContainer.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
						subProcess = EObjectWrapper.wrap(eContainer);
					}
				}
				
				ProcessAdapter createAdapter = new ProcessAdapter(
						process.getEInstance(), commonOntologyAdaptor);
				if(subProcess == null){
					mctx.addDefinition("job", createAdapter, false);
				}else{
					mctx.addDefinitionForSubprocess("job", subProcess, createAdapter,commonOntologyAdaptor, false);
				}
				getOutputTypeForJavaTaskInputMapping(flowElement.getEInstance(), mc, xslt, mctx);
			}
			
			mctx.addDefinitionForLoop(objectWrapper, process, true);
			VariableDefinitionList vdl = makeInputVariableDefinitions(mctx);
			if(outputType != null){
				List<String> e = validateXsltFunction(xslt, vdl, outputType, true);
				if (e != null && e.size()>0)
					errors.add(e.get(0));
			}
			
			
		}

		mctx = new MapperPropertySection.EntityMapperContext(project);
		if (BpmnModelUtils.isSWTMapper()) {
			mc = new BpmnMapperControl(mctx, null);
		} else{
			mc = new MapperControl(mctx, null);
		}
		mctx.setMapper(mc);
		outputType = null;
		String outputMapperXslt = getOutputMapperXslt(eInstance);
		if (outputMapperXslt != null && !outputMapperXslt.trim().isEmpty()) {
			if (eInstance.eClass().equals(BpmnModelClass.START_EVENT)
					|| eInstance.eClass().equals(BpmnModelClass.BOUNDARY_EVENT)
					|| eInstance.eClass().equals(BpmnModelClass.SUB_PROCESS)
					|| eInstance.eClass().equals(BpmnModelClass.RULE_FUNCTION_TASK)
					|| eInstance.eClass().equals(BpmnModelClass.JAVA_TASK)			
					|| eInstance.eClass().equals(BpmnModelClass.RECEIVE_TASK)) {
				String attachedResource = (String) BpmnModelUtils
						.getAttachedResource(eInstance);
				if (attachedResource != null
						&& !attachedResource.trim().isEmpty()) {
					DesignerElement element = IndexUtils.getElement(
							project.getName(), attachedResource);
					String variable = "";
					if (element != null
							&& !(variable = getVariable(element)).trim()
									.isEmpty()) {
						if (eInstance.eClass().equals(
								BpmnModelClass.START_EVENT)) {
							mctx.addDefinition(variable, attachedResource,
									false);

						} else if (eInstance.eClass().equals(
								BpmnModelClass.BOUNDARY_EVENT))
							mctx.addDefinition(variable, attachedResource,
									false);
						else if (eInstance.eClass().equals(
								BpmnModelClass.RECEIVE_TASK))
							mctx.addDefinition(variable, attachedResource,
									false);
						else if (eInstance.eClass().equals(
								BpmnModelClass.RULE_FUNCTION_TASK)
								&& element instanceof RuleElement) {
							addDefinitionForRuleFunctionReturnType(
									(RuleElement) element, mctx);
						} else if (eInstance.eClass().equals(
								BpmnModelClass.JAVA_TASK)) {
							addDefinitionForJavaResourceReturnType(
									BpmnModelUtils
											.getMethodReturnType(eInstance),
									project, mctx,(JavaElement) element);
						}
						outputType = getOutputTypeForOutputMapper(process, flowElement, mc,
								outputMapperXslt);
					}
				} else if (eInstance.eClass()
						.equals(BpmnModelClass.SUB_PROCESS)) {
					ProcessAdapter createAdapter = new ProcessAdapter(
							process.getEInstance(), commonOntologyAdaptor);
					EObjectWrapper<EClass, EObject> subProcess = EObjectWrapper
							.wrap(eInstance);
					String lname = MapperConstants.RETURN;
					mctx.addDefinitionForSubprocess(lname, subProcess,
							createAdapter, commonOntologyAdaptor, false);
					outputType = getOutputTypeForOutputMapper(process, flowElement, mc,
							outputMapperXslt);
				}

			} else if (eInstance != null
					&& eInstance.eClass().equals(BpmnModelClass.CALL_ACTIVITY)) {
				EObject attachedResource = (EObject) BpmnModelUtils
						.getAttachedResource(eInstance);
				if (attachedResource != null
						&& attachedResource.eClass().equals(
								BpmnModelClass.PROCESS)) {
					EObject bpmnIndex = BpmnCorePlugin.getDefault()
							.getBpmnModelManager().getBpmnIndex(project);
					ProcessAdapter createAdapter = new ProcessAdapter(
							attachedResource, new ProcessOntologyAdapter(
									bpmnIndex));
					String lname = EObjectWrapper
							.wrap(attachedResource)
							.getAttribute(
									BpmnMetaModelExtensionConstants.E_ATTR_NAME);
					lname = MapperConstants.RETURN;// lname.replace(".", "$");
					mctx.addDefinition(lname, createAdapter, false);
					outputType = getOutputForProcess(process, mc,
							outputMapperXslt);
				}

			}else if(eInstance.eClass().equals(BpmnModelClass.SERVICE_TASK)){
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(flowElement);
				String attribute = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL);
				WebServiceMapperHelper webServicePropertySection = new WebServiceMapperHelper(project, mctx, mc);

				if (attribute != null && !attribute.isEmpty()) {
					SmElement element = webServicePropertySection
							.buildOutputSoapSchema(flowElement);
					if (element != null)
						mctx.addDefinition(element);
					
					outputType = getOutputTypeForOutputMapper(process, flowElement, mc,
							outputMapperXslt);
				}
			} 
			mctx.addDefinitionForLoop(EObjectWrapper.wrap(eInstance), process, false);
			VariableDefinitionList vdl = makeInputVariableDefinitions(mctx);

			if (outputType != null) {
				List<String> e = validateXsltFunction(outputMapperXslt, vdl,
						outputType, false);
				if (e != null && e.size()>0)
					errors.add(e.get(0));
			}
			
			
		}

		return errors;
	}

	private String getOutputMapperXslt(EObject userObject) {
		String xslt = "";
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			} else if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			}
		}
		return xslt;
	}

	private String getVariable(DesignerElement element) {
		String variable = element.getName();

		return variable;
	}

	private void addDefinitionForRuleFunctionReturnType(RuleElement element,
			EntityMapperContext mctx) {
		boolean isArray = false;
		String returnType = element.getRule().getReturnType();
		if (returnType == null || returnType.trim().isEmpty())
			return;
		if (returnType.endsWith("[]")) {
			returnType = returnType.replace("[]", "");
			isArray = true;
		}
		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils
				.getEntity(project.getName(), returnType);
		String fullPath = returnType;
		String name = MapperConstants.RETURN;
		if (entity != null) {
			fullPath = CommonIndexUtils
					.getEntity(project.getName(), returnType).getFullPath();

			mctx.addDefinition(name, fullPath, isArray);
		} else {
			mctx.addSimpleTypeDefinition(name, returnType, isArray,element);
		}
	}
	
	private void addDefinitionForJavaResourceReturnType(SymbolEntryImpl symbol,IProject fProject,EntityMapperContext mctx,JavaElement javaEnt) {
		
		EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(symbol.getType());
    	String returnType = typeWrapper.toString();
    	boolean isArray = symbol.isArray();
    	String name = MapperConstants.RETURN;
    	SmType smType= MapperUtils.getSimpleTypeForReturnType(returnType);
    	if (smType == XSDL.ANY_TYPE/*!symbol.isPrimitive()*/) {
    		String path = symbol.getPath();
    		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(fProject.getName(), path);
    		if (entity != null) {
    			String fullPath = CommonIndexUtils.getEntity(fProject.getName(),	path).getFullPath();
    			mctx.addDefinition(name, fullPath, isArray);
    		}
    	} else {
    		mctx.addSimpleTypeDefinition(name, returnType, isArray,javaEnt);
//    		mctx.addSimpleJavaTaskDefinition(name, MapperUtils.getSimpleTypeForReturnType(returnType), isArray,javaEnt);
    	}
	}
	
	private SmElement getOutputTypeForJavaTaskInputMapping(EObject flowElement, AbstractMapperControl mc, String xslt, EntityMapperContext ctx){
		String attachedResource = (String) BpmnModelUtils.getAttachedResource(flowElement);
		if (attachedResource != null && !attachedResource.trim().isEmpty()) {
			if(flowElement.eClass().equals(BpmnModelClass.JAVA_TASK)){
				
				//TODO: build schema
				
//				buildInputSoapSchema(EObjectWrapper.wrap(flowElement), mc, xslt, attachedResource, ctx);
				return mc.getEntitySchema();
			}
		}
		
		return null;
	}

	private String getInputMapperXslt(EObject userObject) {
		String xslt = "";
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			} else if (BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			}
		}
		return xslt;
	}

	private List<String> validateXsltFunction(String xsltString,
			VariableDefinitionList vdl, SmElement outputType,
			boolean inputMapper) {
		List<String> errors = new ArrayList<String>();
		try {
			TemplateReportArguments args = new TemplateReportArguments();
			args.setSkippingTemplateParams(true); // these aren't shown in the
													// tree, so we don't want
													// them in the report.
			args.setCancelChecker(new DefaultCancelChecker());

			args.setRecordingMissing(true);
			EMFTnsCache cache = BpmnCorePlugin.getCache(project.getName());
			MapperInvocationContext context = new MapperInvocationContext(
					project.getName(),
					new ArrayList<com.tibco.cep.studio.core.index.model.VariableDefinition>(),
					xsltString, null, null, null);
			TemplateEditorConfiguration tec = null;
			try {
				if (outputType == null)
					tec = MapperCoreUtils.createTemplateEditorConfiguration(
							context, vdl, cache,
							SmSequenceTypeFactory.createElement(XSDL.STRING));
				else
					tec = MapperCoreUtils.createTemplateEditorConfiguration(
							context, vdl, cache,
							SmSequenceTypeFactory.create(outputType));

			} catch (XMLReadException e) {
				String error;
				if (inputMapper)
					error = INPUT_ERROR_PREFIX
							+ BpmnMessages.getString("bpmnXsltValidationHelper_error_readXsltStr_message")
							+ e.getMessage();
				else
					error = OUTPUT_ERROR_PREFIX
							+ BpmnMessages.getString("bpmnXsltValidationHelper_error_readXsltStr_message")
							+ e.getMessage();

				errors.add(error);
				return errors;
			}
			Binding parent = tec.getBinding() != null ? tec.getBinding()
					.getParent() : null;
			if (parent instanceof StylesheetBinding) {
				Set<?> prefixesAsSet = ((StylesheetBinding) parent)
						.getExcludedPrefixesAsSet();
				NamespaceDeclaration[] namespaceDeclarations = parent
						.getElementInfo().getNamespaceDeclarations();
				for (Object object : prefixesAsSet) {
					String pfx = (String) object;
					if (!findNamespaceByPrefix(pfx, namespaceDeclarations)) {
						String error;
						if (inputMapper)
							error = INPUT_ERROR_PREFIX
									+ BpmnMessages.getString("bpmnXsltValidationHelper_error_unknownPref_message")+"'"
									+ pfx
									+ "'"+BpmnMessages.getString("bpmnXsltValidationHelper_error_excludeResultPref");
						else
							error = OUTPUT_ERROR_PREFIX
									+ BpmnMessages.getString("bpmnXsltValidationHelper_error_unknownPref_message")+"'"
									+ pfx
									+ "'"+BpmnMessages.getString("bpmnXsltValidationHelper_error_excludeResultPref");

						errors.add(error);
					}
				}
			}
			BindingEditorPanel editorPanel = new BindingEditorPanel(
					StudioCorePlugin.getUIAgent(context.getProjectName()));

			editorPanel.getEditor().setTemplateEditorConfiguration(tec);
			BindingEditor editor = runLoad(editorPanel, tec);
			Object obj = editor.getBindingTree().getRootNode();
			if (obj instanceof BindingNode) {
				TemplateReport tr = ((BindingNode) obj).getTemplateReport();
				errors.addAll(checkTemplateReport(tr, inputMapper));
			} else {
				TemplateReport tr = TemplateReport.create(tec,
						new TemplateReportFormulaCache(), args);
				errors.addAll(checkTemplateReport(tr, inputMapper));
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

		return errors;
	}

	private boolean findNamespaceByPrefix(String pfx,
			NamespaceDeclaration[] namespaceDeclarations) {
		for (int i = 0; i < namespaceDeclarations.length; i++) {
			NamespaceDeclaration declaration = namespaceDeclarations[i];
			if (pfx.equals(declaration.getPrefix())) {
				return true;
			}
		}
		return false;
	}

	private BindingEditor runLoad(BindingEditorPanel editorPanel,
			TemplateEditorConfiguration tec) {
		DefaultCancelChecker cancelChecker = new DefaultCancelChecker();
		NamespaceContextRegistry ni = tec.getExprContext().getNamespaceMapper();
		TemplateEditorConfiguration tec2 = TemplateReportGenerationUtils
				.createConfiguration(tec, ni, cancelChecker);
		if (cancelChecker.hasBeenCancelled()) {
			return null;
		}

		// Now insert marker comments:
		editorPanel.getEditor().getBindingTree().getFormulaCache().clear();
		BindingDisplayUtils.addMarkers(tec2, editorPanel.getEditor()
				.getBindingTree().getFormulaCache());
		if (cancelChecker.hasBeenCancelled()) {
			return null;
		}

		editorPanel.getEditor().setTemplateEditorConfiguration(tec2);
		if (cancelChecker.hasBeenCancelled()) {
			return null;
		}
		editorPanel.getEditor().getBindingTree().waitForReport(cancelChecker);
		if (cancelChecker.hasBeenCancelled()) {
			return null;
		}
		// if (state!=null)
		// {
		// m_bindingEditor.setInputTreeState(state.m_leftState);
		// m_bindingEditor.setBindingTreeState(state.m_rightState);
		// }
		if (cancelChecker.hasBeenCancelled()) {
			return null;
		}

		// m_bindingEditor.setIsDebuggerActive(isDebuggerActive);

		editorPanel.getEditor().loadPreferences();
		if (cancelChecker.hasBeenCancelled()) {
			return null;
		}

		return editorPanel.getEditor();
	}

	private List<String> checkTemplateReport(TemplateReport tr,
			boolean inputMapper) {
		List<String> errors = new ArrayList<String>();
		if (tr.isRecursivelyErrorFree()) {
			return errors;
		}
		BindingFixerChangeList list = new BindingFixerChangeList();
		list.setIncludeOtherwiseCheck(true); // Always want these on here (they
												// are off elsewhere because of
												// the expense)
		list.setIncludeWarnings(true);
		list.run(tr);
		for (int i = 0; i < list.size(); i++) {
			BindingFixerChange change = list.getChange(i);
			String message = getErrorMessage(change, inputMapper);

			if (change.getCategory() == BindingFixerChange.CATEGORY_ERROR) {
				errors.add(message);
			} else if (change.getCategory() == BindingFixerChange.CATEGORY_WARNING) {
				// ignore
			}
		}

		return errors;
	}

	private String getErrorMessage(BindingFixerChange change,
			boolean inputMapper) {
		String message;
		if (inputMapper)
			message = INPUT_ERROR_PREFIX + change.getMessage();
		else
			message = OUTPUT_ERROR_PREFIX + change.getMessage();

		if (!(change instanceof FormulaErrorBindingFixerChange)) {
			return message;
		}
		FormulaErrorBindingFixerChange fe = (FormulaErrorBindingFixerChange) change;
		TextRange textRange = fe.getErrorMessage().getTextRange();
		String details = null;
		if (textRange != null) {
			try {
				int start = textRange.getStartPosition();
				int end = textRange.getEndPosition();
				if (start >= 0 && start < end)
					;
				details = fe.getTemplateReport().getXPathExpression()
						.getExprValue().trim().substring(start, end);
			} catch (Exception e) {
			}
		}
		if (details != null) {
			message += " (" + details + ")";
		}
		return message;
	}
	
	private SmElement getOutputTypeForInputMapper(EObjectWrapper<EClass, EObject> flowElement, AbstractMapperControl mc,
			String xslt) {
		if(flowElement.getEInstance().eClass().equals(BpmnModelClass.INFERENCE_TASK)){
			mc.setEntityForBusinessRule(flowElement, xslt);
			return mc.getEntitySchema();
		}else{
			String attachedResource = (String) BpmnModelUtils
					.getAttachedResource(flowElement.getEInstance());
			if (attachedResource != null && !attachedResource.trim().isEmpty()) {
				mc.setEntityURI(attachedResource, xslt);
				return mc.getEntitySchema();
			}else if(flowElement.getEInstance().eClass().equals(BpmnModelClass.SUB_PROCESS)){
				return getOutputForSubprocess(process, flowElement, mc, xslt);
			}
		}
		return null;
	}

	private SmElement getOutputTypeForOutputMapper(EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> flowElement, AbstractMapperControl mc,
			String xslt) {
		EObjectWrapper<EClass, EObject> subProcess = null;
		EObject eContainer = flowElement.getEInstance().eContainer();
		if (eContainer != null) {
			if (eContainer.eClass().equals(BpmnModelClass.SUB_PROCESS)) {
				subProcess = EObjectWrapper.wrap(eContainer);
			}
		}

		if (subProcess == null) {
			return getOutputForProcess(process, mc, xslt);
		} else
			return getOutputForSubprocess(process, subProcess, mc, xslt);
	}

	private SmElement getOutputForProcess(
			EObjectWrapper<EClass, EObject> process, AbstractMapperControl mc,
			String xslt) {
		ProcessAdapter createAdapter = null;
		createAdapter = new ProcessAdapter(process.getEInstance(),
				commonOntologyAdaptor);
		mc.setMapperInput(createAdapter, xslt);
		return mc.getEntitySchema();
	}

	private SmElement getOutputForSubprocess(
			EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> subProcess, AbstractMapperControl mc,
			String xslt) {

		ProcessAdapter processAdaptor = new ProcessAdapter(
				process.getEInstance(), commonOntologyAdaptor);

		mc.setMapperInputForSubprocess(subProcess, processAdaptor,
				commonOntologyAdaptor, xslt);
		return mc.getEntitySchema();
	}
	
	private SmElement getOutputForCallActivityForInputMapping(EObjectWrapper<EClass, EObject> calledProcess, AbstractMapperControl mc,
			String xslt){
		ProcessAdapter createAdapter = new ProcessAdapter(calledProcess.getEInstance(), commonOntologyAdaptor);

		String  lname = MapperConstants.PROCESS;
		mc.setEntityForCallActivity(createAdapter, xslt, lname);
		return mc.getEntitySchema();
	}
	

	private SmElement getOutputTypeForWsInputMapping(EObject flowElement, AbstractMapperControl mc, String xslt, EntityMapperContext ctx){
		String attachedResource = (String) BpmnModelUtils
				.getAttachedResource(flowElement);

		if (attachedResource != null && !attachedResource.trim().isEmpty()) {
			if(flowElement.eClass().equals(BpmnModelClass.SERVICE_TASK)){
				buildInputSoapSchema(EObjectWrapper.wrap(flowElement), mc, xslt, attachedResource, ctx);
				if (BpmnModelUtils.isSWTMapper()) {
					return ctx.getSmElement();
				} else
					return mc.getEntitySchema();
			}
		}
		
		return null;
	}
	
	protected void buildInputSoapSchema(EObjectWrapper<EClass, EObject> webServiceTask, AbstractMapperControl mc,
			String xslt, String attachedResource, EntityMapperContext ctx) {
		
		WebServiceMapperHelper helper = new WebServiceMapperHelper(project, ctx, mc);
		helper.buildInputSoapSchema(webServiceTask, xslt);
	
		return;
	}
	
	private VariableDefinitionList makeInputVariableDefinitions(EntityMapperContext mctx) {

		VariableDefinitionList vdlist = new VariableDefinitionList();
		if (mctx != null) {
			
			vdlist.add(new VariableDefinition(
					ExpandedName.makeName(GlobalVariablesProvider.NAME), 
					SmSequenceTypeFactory.create(mctx.getGlobalVariables().toSmElement(false))));
			
			List<VariableDefinition> definitions = mctx.getDefinitions();
			for (VariableDefinition variableDefinition : definitions) {
				vdlist.add(variableDefinition);
			}
			
		}
		
		return vdlist;
	}
	
}
