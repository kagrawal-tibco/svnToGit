package com.tibco.cep.bpmn.core.codegen;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.HISTORY_POLICY;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class BpmnMsgArtifactsGenerator extends AbstractGenerator implements RootGenerator {

	private static final String MSG_PROC = "MsgProc";
	EObject process = null;
	private Event msgProcEvent;
	private Symbol msgProcEventSymbol;
	private Concept jobDataConcept;
	private EObject bpmnJobSymbol;
//	private EObject jobSymbol;
//	private Symbol bpmnContextSymbol;
	private Symbol msgIdSymbol;
	private Map<String,RuleFunction> flowElementHandlerMap = new HashMap<String,RuleFunction>();
	Scorecard processConstants;
	RuleFunction processConstantsInitFunction;	
	public BpmnMsgArtifactsGenerator(EObject process, BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
		this.process = process;
	}

	@Override
	public void generate() throws Exception {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		this.msgProcEvent = createMsgProcEvent(processName+MSG_PROC+"Event");		
		this.msgProcEventSymbol = MutableUtils.createSymbol("msgEvent", msgProcEvent.getFullPath());
		String jobDataConceptURI = ExtensionHelper.getExtensionAttributeValue(processWrapper, BpmnMetaModelExtensionConstants.E_ATTR_JOB_DATA_CONCEPT);
		if(jobDataConceptURI == null) {
			throw new Exception("NULL job data concept.");
		}
		this.jobDataConcept = createJobDataConcept(process,jobDataConceptURI);
		bpmnJobSymbol = getSymbolMap(process).addSymbol("jobDataConcept", jobDataConcept.getFullPath());
		this.processConstants = createProcessConstants(process);
		this.processConstantsInitFunction = createScorecardInitFunction(process, processConstants);
//		this.jobSymbol = BpmnModelUtils.convertBpmnSymbol(jSymbol);
//		this.msgIdSymbol = MutableUtils.createSymbol("msgId", "String");
//		this.bpmnContextSymbol = MutableUtils.createSymbol("context", "Object");
	}
	
	private Scorecard createProcessConstants(EObject process) {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		String projectName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String folderPath = getScorecardFolderPath();
		String name = BpmnModelUtils.generatedScorecardName(getContext(), processName)+"_Constants";
		Scorecard scorecard = MutableUtils.createScoreCard(projectName, folderPath, folderPath, name, "", true);
		Collection<EObject> flowElements = getBpmnOntology().getFlowNodes(process);
		for(EObject flowElement: flowElements) {
			EObjectWrapper<EClass, EObject> flowElementWrapper = EObjectWrapper.wrap(flowElement);
			String flowElementName = BpmnModelUtils.generatedFlowElementName(flowElement).toUpperCase();
			PropertyDefinition msgProp = MutableUtils.createPropertyDefinition(scorecard, 
					flowElementName, 
					PROPERTY_TYPES.STRING, 
					scorecard.getFullPath(), 
					HISTORY_POLICY.CHANGES_ONLY_VALUE, 
					0, 
					false, 
					"");
			scorecard.getProperties().add(msgProp);
		}

		MutableUtils.persistEntity(projectName, scorecard, getMonitor());
		return scorecard;
	}
	
	protected RuleFunction createScorecardInitFunction(EObject process,Scorecard scorecard) throws Exception {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		String projectName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		final String ruleFunctionName = "init"+BpmnModelUtils.generatedRulefunctionName(getContext(), processName);
		final String ruleFnfolderPath = getRulefunctionFolderPath();
		
		RuleFunction launchRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
		Collection<EObject> flowElements = getBpmnOntology().getFlowNodes(process);
		StringBuilder sb = new StringBuilder();
		for(EObject flowElement: flowElements) {
			EObjectWrapper<EClass, EObject> flowElementWrapper = EObjectWrapper.wrap(flowElement);
			String flowElementName = BpmnModelUtils.generatedFlowElementName(flowElement).toUpperCase();
			String scQualifiedName = ModelUtilsCore.convertPathToPackage(scorecard.getFullPath());
			String flowElementId = BpmnModelUtils.getFlowElementId(flowElement);
			sb.append("\t")
				.append(scQualifiedName)
				.append(".")
				.append(flowElementName)
				.append(" = ")
				.append("\"")
				.append(flowElementId)
				.append("\"")
				.append(";\n");
		}
		launchRuleFunction.setActionText(sb.toString());
		
		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),launchRuleFunction);
		MutableUtils.createRuleFunctionFile(launchRuleFunction, suri);
		return launchRuleFunction;
	}

	@Override
	public EObject getProcess() {
		return this.process;
	}
	
	public Scorecard getProcessConstants() {
		return processConstants;
	}
	
	public RuleFunction getProcessConstantsInitFunction() {
		return processConstantsInitFunction;
	}
	
	public Event getMsgProcEvent() {
		return this.msgProcEvent;
	}
	
	public Symbol getMsgProcEventSymbol() {
		return this.msgProcEventSymbol;
	}
	
	public Symbol getBpmnContextSymbol() {
		return MutableUtils.createSymbol("context", "Object");
	}
	
	public Symbol getMsgIdSymbol() {
		return MutableUtils.createSymbol("msgId", "String");
	}
	
	public EObject getJobSymbol() {
		return BpmnModelUtils.convertBpmnSymbol(bpmnJobSymbol);
	}
	
	public Concept getJobDataConcept() {
		return this.jobDataConcept;
	}
	
	public Map<String, RuleFunction> getFlowElementHandlerMap() {
		return flowElementHandlerMap;
	}
	
	protected Event createMsgProcEvent(String eventName) {
		Symbol eventSymbol = null;
		final String startEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
		final String eventfolderPath = getEventFolderPath();
		
		Event event = MutableUtils.createEvent(getProject().getName(), 
				eventfolderPath, 
				eventfolderPath, 
				startEventName, 
				0, TIMEOUT_UNITS.MILLISECONDS, true, true);
		PropertyDefinition id = MutableUtils.createPropertyDefinition(
				event,"id",PROPERTY_TYPES.STRING,event.getFullPath(),"");
		event.getProperties().add(id);
		PropertyDefinition jobId = MutableUtils.createPropertyDefinition(
				event,"jobId",PROPERTY_TYPES.STRING,event.getFullPath(),"");
		event.getProperties().add(jobId);
		
		PropertyDefinition processName = MutableUtils.createPropertyDefinition(
				event,"processName",PROPERTY_TYPES.STRING,event.getFullPath(),"");
		event.getProperties().add(processName);
		MutableUtils.persistEntity(getProject().getName(), event, getMonitor());
		return event;		
	}
	
	public void setDestination(SimpleEvent event,String destinationURI) {
		
		if (destinationURI != null) {
			//get channelPath and destination name from it
			if (destinationURI.lastIndexOf('/') != -1) {
				String channelURI = destinationURI.substring(0, destinationURI
						.lastIndexOf('/'));
				if (channelURI.endsWith(".channel")) {
					channelURI = channelURI.substring(0, channelURI.indexOf(".channel"));
				}
				if (MutableUtils.getChannel(event.getOwnerProjectName(), channelURI)!= null) {
					event.setChannelURI(channelURI);
				}
				String destinationName = destinationURI
				.substring(destinationURI.lastIndexOf('/') + 1);
				event.setDestinationName(destinationName);
			}
		}
	}
	
	public void setTimeEventParameters(TimeEvent event,EVENT_SCHEDULE_TYPE scheduleType,long count,int interval,TIMEOUT_UNITS intervalUnit) {
		event.setScheduleType(scheduleType);
		event.setTimeEventCount(count);
		event.setInterval(interval);
		event.setIntervalUnit(intervalUnit);
	}

}
