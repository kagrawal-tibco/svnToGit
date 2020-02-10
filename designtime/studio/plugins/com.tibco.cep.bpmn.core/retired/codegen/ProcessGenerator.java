package com.tibco.cep.bpmn.core.codegen;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class ProcessGenerator extends AbstractGenerator  {
	
	private EObject process;
	private EObject processSymbol;
//	private EObject jobSymbol;

	public ProcessGenerator(EObject process,BaseGenerator parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
		this.process = process;
	}
	
	public EObject getProcess() {
		return process;
	}
	
//	public EObject getJobSymbol() {
//		return jobSymbol;
//	}
	
	public EObject getProcessSymbol() {
		return processSymbol;
	}

	@Override
	public void generate() throws Exception {
		BpmnIndex ontology = getBpmnOntology();
		// create Job data structures
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		final String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String processURI = BpmnIndexUtils.getSerializableURI(getProject().getName(), processWrapper.getEInstance());
		this.processSymbol = getSymbolMap(process).addSymbol(processName,processURI);
//		String jobDataConceptURI = ExtensionHelper.getExtensionAttributeValue(processWrapper, BpmnMetaModelExtensionConstants.E_ATTR_JOB_DATA_CONCEPT);
//		Concept jConcept = createJobConcept(process,jobDataConceptURI);
//		this.jobSymbol = getSymbolMap(process).addSymbol("jobDataConcept", jConcept.getFullPath());
		
		// generate the flow nodes
		// generate activity nodes
		Collection<EObject> activities = ontology.getFlowNodes(processWrapper.getEInstance(),true,true,BpmnModelClass.ACTIVITY);
		for(EObject activity: activities) {
			BaseGenerator bg = GeneratorFactory.createGenerator(activity, this, getContext(),getMonitor(),canOverwrite());
			bg.generate();
		}
		// generate gateway nodes
		Collection<EObject> gateways = ontology.getFlowNodes(processWrapper.getEInstance(),true,true,BpmnModelClass.GATEWAY);
		for(EObject gateway: gateways) {
			BaseGenerator bg = GeneratorFactory.createGenerator(gateway, this, getContext(),getMonitor(),canOverwrite());
			bg.generate();
		}
		// generate catch event nodes( start event)
		Collection<EObject> catchEvents = ontology.getFlowNodes(processWrapper.getEInstance(),true,true,BpmnModelClass.CATCH_EVENT);
		for(EObject catchEvent: catchEvents) {
			BaseGenerator bg = GeneratorFactory.createGenerator(catchEvent, this, getContext(),getMonitor(),canOverwrite());
			bg.generate();
		}
		// generate throw event nodes ( end event)
		Collection<EObject> throwEvents = ontology.getFlowNodes(processWrapper.getEInstance(),true,true,BpmnModelClass.THROW_EVENT);
		for(EObject throwEvent: throwEvents) {
			BaseGenerator bg = GeneratorFactory.createGenerator(throwEvent, this, getContext(),getMonitor(),canOverwrite());
			bg.generate();
		}
		// generate the sequence flows
		Collection<EObject> sequenceFlows = ontology.getSequenceFlows(processWrapper.getEInstance());
		for(EObject sequenceFlow: sequenceFlows) {
			BaseGenerator bg = GeneratorFactory.createGenerator(sequenceFlow, this, getContext(),getMonitor(),canOverwrite());
			bg.generate();
		}
		
		// generate the process launcher
	}

	


}
