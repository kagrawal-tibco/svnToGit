package com.tibco.cep.bpmn.core.codegen;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
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

public class BpmnMsgProcGenerator extends AbstractGenerator {

	private static final String MSG_PROC = "MsgProc";
	EObject process = null;
	private List<Symbol> symbols;
	
	public BpmnMsgProcGenerator(EObject process, BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite,List<Symbol> procSymbols) {
		super(parent, ctx, monitor, overwrite);
		this.process = process;
		this.symbols = procSymbols;
	}

	@Override
	public void generate() throws Exception {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		Collection<EObject> flowElements = getBpmnOntology().getFlowNodes(process);
		
//		Event msgProcEvent = createMsgProcEvent(processName+MSG_PROC+"Event");		
//		Symbol msgSymbol = MutableUtils.createSymbol("msgEvent", msgProcEvent.getFullPath());
		
//		Symbol processNameSymbol = MutableUtils.createSymbol("processName", "String");
		final String ruleFunctionName = "msgProc"+BpmnModelUtils.generatedRulefunctionName(getContext(), processName);
		final String ruleFnfolderPath = getRulefunctionFolderPath();
		
		RuleFunction msgRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
		
		
		for(Symbol s:symbols) {
			msgRuleFunction.getSymbols().getSymbolMap().put(s.getIdName(),s);
		}
//		msgRuleFunction.getSymbols().getSymbolMap().put(msgSymbol.getIdName(),msgSymbol);
//		msgRuleFunction.getSymbols().getSymbolMap().put(processNameSymbol.getIdName(),processNameSymbol);
		Scorecard processConstants = getRootGenerator().getProcessConstants();
		String scQualifiedName = ModelUtilsCore.convertPathToPackage(processConstants.getFullPath());
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(EObject element: flowElements) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			
//			String id = BpmnModelUtils.getFlowElementId(element);
//			String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			String flowElementConstant = scQualifiedName+"."+BpmnModelUtils.generatedFlowElementName(element).toUpperCase();
			String id = BpmnModelUtils.getFlowElementId(element);
			if(i==0) {
				sb.append("\t\tif(msgId == "+flowElementConstant+") {\n");
			} else {
				sb.append("\telse if(msgId == "+flowElementConstant+") {\n");
			}
			RuleFunction rf = getRootGenerator().getFlowElementHandlerMap().get(id);
			ModelFunction emf = BpmnModelUtils.findModelFunction(rf);
			String signatureFormat = emf.signatureFormat();
			String usage = MessageFormat.format(signatureFormat, "context");
			sb.append("\t\t"+usage+";\n");
			sb.append("\t\tSystem.debugOut(\""+id+"\");\n");
			sb.append("\t}\n");
			i++;
		}
//		String rfnURI = ExtensionHelper.getExtensionAttributeValue(ruleFnTaskWrapper, BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
//		DesignerElement element = IndexUtils.getElement(getProject().getName(), rfnURI);
//		if(element != null && element instanceof RuleElement) {
//			RuleElement relement = (RuleElement) element;
//			ModelFunction emf = BpmnModelUtils.findModelFunction(relement.getRule());
//			String signatureFormat = emf.signatureFormat();
//			sb.append(signatureFormat+";");
//		}		
		msgRuleFunction.setActionText(sb.toString());
		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),msgRuleFunction);
		MutableUtils.createRuleFunctionFile(msgRuleFunction, suri);

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
