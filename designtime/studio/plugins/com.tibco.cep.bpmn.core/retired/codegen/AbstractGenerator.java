package com.tibco.cep.bpmn.core.codegen;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.RootSymbolMap;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolMap;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.HISTORY_POLICY;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public abstract class AbstractGenerator implements BaseGenerator {
	
	public static final String LAUNCHER_SUFFIX = "Launcher";
	protected IProgressMonitor monitor;
	protected boolean overwrite;
	protected CodeGenContext context;
	protected BaseGenerator parent;
	
	
	/**
	 * constructor
	 * @param parent TODO
	 * @param ctx
	 * @param overwrite
	 * @param monitor2
	 */
	protected AbstractGenerator(BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		this.context = ctx;		
		this.overwrite = overwrite;
		this.parent = parent;
		if(monitor != null) {
			this.monitor = monitor;
		} else {
			this.monitor = new NullProgressMonitor();
		}
	}
	
	/**
	 * @return the parent
	 */
	public BaseGenerator getParent() {
		return parent;
	}


	/**
	 * Returns the progress monitor
	 * @return
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}


	/**
	 * Returns true if the current generator will overwrite the previously generated code
	 * @return
	 */
	public boolean canOverwrite() {
		return overwrite;
	}

	/**
	 * Returns the codegen context
	 * @return
	 */
	public CodeGenContext getContext() {
		return context;
	}
	
	/**
	 * @return the project
	 */
	public IProject getProject() {
		return (IProject) getContext().get(BpmnCoreConstants.BPMN_CODEGEN_PROJECT);
	}
	
	/**
	 * Returns the Rule folder uri
	 * @return
	 */
	public URI getRuleFolderURI() {
		IFolder folder =  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_RULE_FOLDER);
		return ECoreHelper.getPlatformResourceURI(folder);
	}
	
	/**
	 * Returns the Rulefunction folder URI
	 * @return
	 */
	public URI getRuleFunctionFolderURI() {
		IFolder folder =  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_RULEFUNCTION_FOLDER);
		return ECoreHelper.getPlatformResourceURI(folder);
	}
	
	/**
	 * Returns the Concept folder URI
	 * @return
	 */
	public URI getConceptFolderURI() {
		IFolder folder =  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_CONCEPT_FOLDER);
		return ECoreHelper.getPlatformResourceURI(folder);
	}
	
	/**
	 * Returns the Event folder URI
	 * @return
	 */
	public URI getEventFolderURI() {
		IFolder folder =  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_EVENT_FOLDER);
		return ECoreHelper.getPlatformResourceURI(folder);
	}
	
	/**
	 * Returns the Scorecard folder URI
	 * @return
	 */
	public URI getScorecardFolderURI() {
		IFolder folder =  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_SCORECARD_FOLDER);
		return ECoreHelper.getPlatformResourceURI(folder);
	}
	
	/**
	 * Returns the TimeEvent folder URI
	 * @return
	 */
	public URI getTimeEventFolderURI() {
		IFolder folder =  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_TIME_EVENT_FOLDER);
		return ECoreHelper.getPlatformResourceURI(folder);
	}
	
	/**
	 * @return list of codegen errors
	 */
	@SuppressWarnings("unchecked")
	public List<CodegenError> getErrorList() {
		return (List<CodegenError>) context.get(BpmnCoreConstants.BPMN_CODEGEN_ERROR_LIST);
	}
	
	/**
	 * @return the rule folder
	 */
	public IFolder getRuleFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_RULE_FOLDER);
	}
	
	/**
	 * @return the rulefunction folder
	 */
	public IFolder getRulefunctionFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_RULEFUNCTION_FOLDER);
	}
	
	/**
	 * @return the concept folder
	 */
	public IFolder getConceptFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_CONCEPT_FOLDER);
	}
	
	/**
	 * @return the event folder
	 */
	public IFolder getEventFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_EVENT_FOLDER);
	}
	
	/**
	 * @return the timeevent folder
	 */
	public IFolder getTimeEventFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_TIME_EVENT_FOLDER);
	}
	
	/**
	 * @return the scorecard folder
	 */
	public IFolder getScorecardFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_SCORECARD_FOLDER);
	}
	
	
	/**
	 * @return the scorecard folder
	 */
	public IFolder getConstantsFolder() {
		return  (IFolder) context.get(BpmnCoreConstants.BPMN_CODEGEN_CONSTANTS_FOLDER);
	}
	
	/**
	 * @return the constants folder Path
	 */
	public String getConstantsFolderPath() {
		return getConstantsFolder().getProjectRelativePath().makeAbsolute()
				.addTrailingSeparator().toPortableString();
	}
	
	
	/**
	 * @return the rule folder Path
	 */
	public String getRuleFolderPath() {
		return getRuleFolder().getProjectRelativePath().makeAbsolute()
				.addTrailingSeparator().toPortableString();
	}
	
	/**
	 * @return the rulefunction folder Path
	 */
	public String getRulefunctionFolderPath() {
		return getRulefunctionFolder().getProjectRelativePath().makeAbsolute()
		.addTrailingSeparator().toPortableString();
	}
	
	/**
	 * @return the concept folder Path
	 */
	public String getConceptFolderPath() {
		return getConceptFolder().getProjectRelativePath().makeAbsolute()
		.addTrailingSeparator().toPortableString();
	}
	
	/**
	 * @return the event folder Path
	 */
	public String getEventFolderPath() {
		return getEventFolder().getProjectRelativePath().makeAbsolute()
		.addTrailingSeparator().toPortableString();
	}
	
	/**
	 * @return the timeevent folder Path
	 */
	public String getTimeEventFolderPath() {
		return getTimeEventFolder().getProjectRelativePath().makeAbsolute()
		.addTrailingSeparator().toPortableString();
	}
	
	/**
	 * @return the scorecard folder Path
	 */
	public String getScorecardFolderPath() {
		return getScorecardFolder().getProjectRelativePath().makeAbsolute()
		.addTrailingSeparator().toPortableString();
	}
	
	
	
	
	/**
	 * @param err
	 */
	@SuppressWarnings("unchecked")
	public void reportError(CodegenError err) {
		List<CodegenError> errList = (List<CodegenError>) context.get(BpmnCoreConstants.BPMN_CODEGEN_ERROR_LIST);
		if(err != null) {
			errList.add(err);
		}
	}	
	
	SymbolMap getSymbolMap(EObject eObj) {
		RootSymbolMap rootSymbolMap 
		= (RootSymbolMap) getContext().get(BpmnCoreConstants.BPMN_CODEGEN_PROJECT_SYMBOL_MAP);
		SymbolMap processSymbolMap = rootSymbolMap.getProcessSymbolMap(getRootGenerator().getProcess());
		return processSymbolMap.findSymbolMap(eObj);
	}
	
	
	
	
	
	
	protected RootGenerator getRootGenerator() {
		BaseGenerator gen = this;
		while(gen != null) {
			if(gen instanceof RootGenerator) {
				return  (RootGenerator) gen;
			}
			gen = gen.getParent();
		}
		return null;
	}
	
	BpmnIndex getBpmnOntology() {
		return (BpmnIndex) context.get(BpmnCoreConstants.BPMN_CODEGEN_ONTOLOGY);
	}
	
	
	
//	protected Symbol createEventOld(EObjectWrapper<EClass, EObject> sevWrapper) {
//		Symbol eventSymbol = null;
//		final String eventName = sevWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//		final String startEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
//		EList<EObject> eventDefs = sevWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
//		if(eventDefs.isEmpty()) {
//			// StartEvent.NONE - create our own event to trigger it
//			final String eventfolderPath = getEventFolderPath();
//			
//			Event event = MutableUtils.createEvent(getProject().getName(), 
//									eventfolderPath, 
//									eventfolderPath, 
//									startEventName, 
//									0, TIMEOUT_UNITS.MILLISECONDS, true, true);
//			IndexUtils.waitForUpdateAll();
//			String eventURI = IndexUtils.getFullPath(event);
//			eventSymbol = getSymbolMap(sevWrapper.getEInstance()).addSymbol(startEventName, eventURI);
//		} else  {
//			// use user defined event
//			String eventURI = ExtensionHelper.getExtensionAttributeValue(sevWrapper,BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
//			eventSymbol = getSymbolMap(sevWrapper.getEInstance()).addSymbol(startEventName, eventURI);
//		}
//		return eventSymbol;
//	}
	
	protected Event createEvent(EObjectWrapper<EClass, EObject> sevWrapper) {
		Symbol eventSymbol = null;
		final String eventName = sevWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		final String startEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
		EList<EObject> eventDefs = sevWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
		if(eventDefs.isEmpty()) {
			// StartEvent.NONE - create our own event to trigger it
			final String eventfolderPath = getEventFolderPath();
			
			Event event = MutableUtils.createEvent(getProject().getName(), 
									eventfolderPath, 
									eventfolderPath, 
									startEventName, 
									0, TIMEOUT_UNITS.MILLISECONDS, true, true);
			return event;
		} else  {
			// use user defined event
			String eventURI = ExtensionHelper.getExtensionAttributeValue(sevWrapper,BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
			return (Event) IndexUtils.getEntity(getProject().getName(), eventURI);
		}
	}
	
	
//	protected void createLaunchRuleFunction(EObject process,Symbol jobSymbol,Symbol eventSymbol) throws Exception {
//		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.useInstance(process);
//		final String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//		final String eventURI = eventSymbol.getType();
//		final String jobDataConceptURI = jobSymbol.getType();
//		final EntityElement eventElement = (EntityElement) IndexUtils.getElement(getProject().getName(), eventURI);
//		final Event event = (Event) eventElement.getEntity();
//		final Entity jobDataConcept = IndexUtils.getEntity(getProject().getName(),jobDataConceptURI);
//		final String ruleFunctionName = BpmnModelUtils.generatedRulefunctionName(getContext(), processName)+"_"+event.getName()+"_"+LAUNCHER_SUFFIX;
//		final String ruleFnfolderPath = getRulefunctionFolderPath();
//		RuleFunction launchRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
//				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
//		Symbol objS = getSymbolMap(process).addSymbol("obj","Object");
//		launchRuleFunction.getSymbols().getSymbolMap().put(objS.getIdName(),objS);
//		StringBuilder sb = new StringBuilder();
//		//ModelFunction emf = BpmnModelUtils.findModelFunction(jobDataConcept);
//		sb.append(ModelUtilsCore.convertPathToPackage(jobDataConceptURI)+" jobDataConcept = Instance.newInstance(\""+jobDataConceptURI+"\",String.valueOfLong(System.ID.nextId(\""+processName+"\")));\n");
//		ModelFunction emf = BpmnModelUtils.findModelFunction(event);
//		String signatureFormat = emf.signatureFormat();
//		String usage = MessageFormat.format(signatureFormat, "jobDataConcept@extId","null");
//		sb.append(ModelUtilsCore.convertPathToPackage(eventURI)+" startEvent = "+usage+";\n");
//		sb.append("Event.assertEvent(startEvent);");
//		launchRuleFunction.setActionText(sb.toString());
//		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),launchRuleFunction);
//		MutableUtils.createRuleFunctionFile(launchRuleFunction, suri);
//	}
	
	/**
	 * Create a Job concept for BPMN in which the user concept identified by the jobDataConceptURI
	 * is a contained concept
	 * @param process
	 * @param jobDataConceptURI
	 * @return
	 */
	protected Concept createJobDataConcept(EObject process, String jobDataConceptURI) {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		String projectName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String folderPath = getConceptFolderPath();
		String name = BpmnModelUtils.generatedConceptName(getContext(), processName)+"_"+"Job";
		Concept concept = MutableUtils.createConcept(projectName, folderPath, folderPath, name, "", true);
		int numNodes = getBpmnOntology().getFlowNodes(process).size();
		PropertyDefinition msgProp = MutableUtils.createPropertyDefinition(concept, 
				"msgId", 
				PROPERTY_TYPES.STRING, 
				concept.getFullPath(), 
				HISTORY_POLICY.ALL_VALUES_VALUE, 
				numNodes, 
				false, 
				"");
		concept.getProperties().add(msgProp);
		PropertyDefinition pd = MutableUtils.createPropertyDefinition(concept, 
										"jobData", 
										PROPERTY_TYPES.CONCEPT, 
										concept.getFullPath(), 
										0, 
										0, 
										false, 
										"");
		pd.setConceptTypePath(jobDataConceptURI);
		concept.getProperties().add(pd);
		MutableUtils.persistEntity(projectName, concept, getMonitor());
		return concept;
	}
	

}
