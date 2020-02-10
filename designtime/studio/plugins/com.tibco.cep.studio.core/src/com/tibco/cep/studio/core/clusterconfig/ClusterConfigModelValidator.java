package com.tibco.cep.studio.core.clusterconfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.core.builder.AbstractBPMNProcessor;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfigs;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfigs;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ObjectManagement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyGroup;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.DependencyUtils;
import com.tibco.cep.studio.core.validation.DefaultSharedResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;

/*
@author ssailapp
@date Jan 29, 2010 6:02:00 PM
 */

public class ClusterConfigModelValidator extends DefaultSharedResourceValidator {
	
	Map<String, GlobalVariableDescriptor> glbVars;
	AbstractBPMNProcessor[] processors;	
	// TODO - Need to use Cdd's own marker (or add marker attributes), if we need to identify "tab" etc
//	private void reportError(IFile file, String message) {
//		reportProblem(file, message);
//	}
	
	@Override
	public boolean canContinue() {
		return true;
	}
	
	@Override
	protected String getMessageString(String key, Object... arguments) {
		return Messages.getString(key, arguments);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		
		glbVars = getGlobalVariableNameValues(resource.getProject().getName());

		ClusterConfigModelMgr modelmgr = new ClusterConfigModelMgr(resource);
		modelmgr.parseModel();
		boolean valid = validateModel(modelmgr, resource);
		ValidationUtils.validateResourceByExtension(resource.getProject(), "st");
		return valid;
	}
	
	public boolean validateModel(ClusterConfigModelMgr modelmgr, IResource resource) {
		boolean valid = true;
		processors = DependencyUtils.getBPMNProcessors();
		validateObjectManagement(modelmgr, resource);
		validateDBConcepts(modelmgr, resource);
		validateGroups(modelmgr, resource);
		validateLogConfigurations(modelmgr, resource);
		validateAgentClasses(modelmgr, resource);
		validateProcessingUnits(modelmgr, resource);
		validateIds(modelmgr, resource);
		validateLoadBalancer(modelmgr, resource);
		validateProperties(modelmgr, resource, modelmgr.getPropertyElementList(), "cluster properties");
		return valid;
	}
	
	private void validateObjectManagement(ClusterConfigModelMgr modelmgr, IResource resource) {
//		if (modelmgr.getModel().om.activeOm.equals(ObjectManagement.BDB_MGR)) {
//			LinkedHashMap<String, String> map = modelmgr.getBdbManagerProperties();
//			if (map != null) {
//				validateNumericField(resource, null, map.get(Elements.CHECKPOINT_INTERVAL.localName), "bdb.chkpointinterval.empty", "bdb.chkpointinterval.invalid", SEVERITY_ERROR, true, true);
//				validateNumericField(resource, null, map.get(Elements.CHECKPOINT_OPS_LIMIT.localName), "bdb.chkpointopslimit.empty", "bdb.chkpointopslimit.invalid", SEVERITY_ERROR, true, true);
//				validateNumericField(resource, null, map.get(Elements.PROPERTY_CACHE_SIZE.localName), "bdb.propcachesize.empty", "bdb.propcachesize.invalid", SEVERITY_ERROR, true, true);
//			}
//		} else
	   if (modelmgr.getModel().om.activeOm.equals(ObjectManagement.CACHE_MGR)) {
			//validateProperties(modelmgr, resource, modelmgr.getModel().om.cacheOm.cacheProps, "cache properties");
			CacheOm cacheOm = modelmgr.getModel().om.cacheOm;
			if (!cacheOm.provider.equalsIgnoreCase(CacheOm.PROVIDER_TIBCO) && !cacheOm.provider.equalsIgnoreCase(CacheOm.PROVIDER_COHERENCE))
				reportProblem(resource, getMessageString("cache.provider.invalid", cacheOm.provider), SEVERITY_ERROR);
			
			validateNumericField(resource, glbVars, cacheOm.values.get(Elements.CACHE_AGENT_QUORUM.localName), "cache.agentquorum.invalid", SEVERITY_ERROR, true, false);
			validateNumericField(resource, glbVars, cacheOm.values.get(Elements.BACKUP_COPIES.localName), "cache.backupcopies.invalid", SEVERITY_ERROR, true, false);
			validateNumericField(resource, glbVars, cacheOm.values.get(Elements.ENTITY_CACHE_SIZE.localName), "cache.entitycachesize.invalid", SEVERITY_ERROR, true, false);
			
			if(cacheOm.provider.equalsIgnoreCase(CacheOm.PROVIDER_TIBCO)){
				validateStringField(resource,glbVars,cacheOm.values.get(Elements.DISCOVERY_URL.localName),"cache.discoveryurl.invalid",SEVERITY_ERROR);
				validateStringField(resource,glbVars,cacheOm.values.get(Elements.LISTEN_URL.localName),"cache.listenurl.invalid",SEVERITY_ERROR);
				validateStringField(resource,glbVars,cacheOm.values.get(Elements.REMOTE_LISTEN_URL.localName),"cache.remotelistenurl.invalid",SEVERITY_ERROR);
				validateNumericField(resource, glbVars, cacheOm.values.get(Elements.PROTOCOL_TIMEOUT.localName), "cache.protocoltimeout.invalid", SEVERITY_ERROR, false, false);
				validateNumericField(resource, glbVars, cacheOm.values.get(Elements.READ_TIMEOUT.localName), "cache.readtimeout.invalid", SEVERITY_ERROR, false, false);
				validateNumericField(resource, glbVars, cacheOm.values.get(Elements.WRITE_TIMEOUT.localName), "cache.writetimeout.invalid", SEVERITY_ERROR, false, false);
				validateNumericField(resource, glbVars, cacheOm.values.get(Elements.LOCK_TIMEOUT.localName), "cache.locktimeout.invalid", SEVERITY_ERROR, false, false);
				validateNumericField(resource, glbVars, cacheOm.values.get(Elements.SHUTDOWN_WAIT.localName), "cache.shutdownwait.invalid", SEVERITY_ERROR, false, false);
				validateNumericField(resource, glbVars, cacheOm.values.get(Elements.WORKERTHREADS_COUNT.localName), "cache.workerthreadscount.invalid", SEVERITY_ERROR, true, false);
			}
			
			boolean isBsEnabled = Boolean.valueOf(cacheOm.bs.values.get(Elements.ENABLED.localName));
			//if (isBsEnabled) {
			//BE-25036 BE-25112 @ rjain validation
			if(!cacheOm.bs.equals(null)){
				LinkedHashMap<String, String> map = cacheOm.bs.primaryConnection.values;
				String minSize = map.get(Elements.MIN_SIZE.localName);
				String maxSize = map.get(Elements.MAX_SIZE.localName);
				String initialSize = map.get(Elements.INITIAL_SIZE.localName);
				validateNumericField(resource, null, minSize, "cache.bs.minsize.invalid", SEVERITY_ERROR, true, true);
				validateNumericField(resource, null, maxSize, "cache.bs.maxsize.invalid", SEVERITY_ERROR, true, true);
				validateNumericField(resource, null, initialSize, "cache.bs.initsize.invalid", SEVERITY_ERROR, true, true);
				
				try {
					if (Integer.parseInt(maxSize) < Integer.parseInt(minSize)) {
						reportProblem(resource, getMessageString("cache.bs.sizes.maxlessthanmin", maxSize, minSize), SEVERITY_WARNING);
					}
				} catch (NumberFormatException nfe) {
				}
			}
			validateDomainObject(modelmgr, resource, cacheOm);
		}
	}
	
	private void validateDomainObject(ClusterConfigModelMgr modelmgr, IResource resource, CacheOm cacheOm) {
		validateNumericField(resource, null, cacheOm.domainObjects.domainObjDefault.values.get(Elements.PRE_LOAD_FETCH_SIZE.localName), "cache.do.default.preloadfetchsize.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, cacheOm.domainObjects.domainObjDefault.values.get(Elements.CONCEPT_TTL_VALUE.localName), "cache.do.default.conceptttl.invalid", SEVERITY_ERROR, false, false);
		for (DomainObject domainObj: cacheOm.domainObjects.domainObjOverrides.overrides) {
			String uri = domainObj.values.get(Elements.URI.localName);
			EntityElement ee = ClusterConfigProjectUtils.getEntityElementForPath(resource.getProject(), uri);
			if (ee == null) {
				reportProblem(resource, getMessageString("cache.do.overrides.entity.invalid", domainObj.values.get(Elements.URI.localName)));
				continue;
			}
			String mode = domainObj.values.get(Elements.MODE.localName);
			if (modelmgr.isCacheMode(mode, false)) {
				checkIfProjectRuleFunctions(resource, uri, domainObj.values.get(Elements.PRE_PROCESSOR.localName));
				
				if (!validateNumericField(domainObj.values.get(Elements.PRE_LOAD_FETCH_SIZE.localName), glbVars, true, false)) {
					reportProblem(resource, getMessageString("cache.do.overrides.preloadfetchsize.invalid", uri, domainObj.values.get(Elements.PRE_LOAD_FETCH_SIZE.localName)));
				}
				
				if (!validateNumericField(domainObj.values.get(Elements.CONCEPT_TTL_VALUE.localName), glbVars, false, false)) {
					reportProblem(resource, getMessageString("cache.do.overrides.conceptttl.invalid", uri, domainObj.values.get(Elements.CONCEPT_TTL_VALUE.localName)));
				}
			
				ArrayList<String> entityProps = ClusterConfigProjectUtils.getEntityElementProperties(ee);
				for (String propName: domainObj.props.keySet()) {
					if (!entityProps.contains(propName))
						reportProblem(resource, getMessageString("cache.do.overrides.property.invalid", uri, propName));
				}
			}
			
			// Check if it is a DB concept
			if (ClusterConfigProjectUtils.isDbConcept(ee)) {
				if (modelmgr.isCacheMode(mode, false)) {
					reportProblem(resource, getMessageString("cache.do.overrides.dbconcept.cachemode", uri), SEVERITY_WARNING);
					boolean bsEnabled = modelmgr.getBooleanValue(domainObj.bs.values.get(Elements.ENABLED.localName));
					if (bsEnabled)
						reportProblem(resource, getMessageString("cache.do.overrides.dbconcept.bsenabled", uri), SEVERITY_WARNING);
				}
			}
		}
	}
	
	private void validateDBConcepts(ClusterConfigModelMgr modelmgr, IResource resource) {
		LinkedHashMap<String, String> dbMap = modelmgr.getModel().om.dbConcepts.values;
		validateNumericField(resource, null, dbMap.get(Elements.CHECK_INTERVAL.localName), "db.checkinterval.empty", "db.checkinterval.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, dbMap.get(Elements.INACTIVITY_TIMEOUT.localName), "db.inactivitytimeout.empty", "db.inactivitytimeout.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, dbMap.get(Elements.INITIAL_SIZE.localName), "db.initialsize.empty", "db.initialsize.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, dbMap.get(Elements.MAX_SIZE.localName), "db.maxsize.empty", "db.maxsize.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, dbMap.get(Elements.MIN_SIZE.localName), "db.minsize.empty", "db.minsize.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, dbMap.get(Elements.PROPERTY_CHECK_INTERVAL.localName), "db.propertycheckinterval.empty", "db.propertycheckinterval.invalid", SEVERITY_ERROR, true, false);
		validateNumericField(resource, null, dbMap.get(Elements.RETRY_COUNT.localName), "db.propertyretrycount.empty","db.propertyretrycount.invalid", SEVERITY_ERROR, false, false);
		validateNumericField(resource, null, dbMap.get(Elements.WAIT_TIMEOUT.localName), "db.waittimeout.empty", "db.waittimeout.invalid", SEVERITY_ERROR, true, false);
	}
	
	private void validateLoadBalancer(ClusterConfigModelMgr modelmgr, IResource resource) {
		LoadBalancerAdhocConfigs adhocConfigs = modelmgr.getModel().loadBalancerAdhocConfigs;
		ArrayList<String> configNames = new ArrayList<String>();
		for(LoadBalancerAdhocConfig c:adhocConfigs.configs ){
				if(isEmpty(c.values.get("name"))){
					reportProblem(resource, getMessageString("loadbalancer.empty.adhoc.configname"));
				}else{
					configNames.add(c.values.get("name"));
				}
				checkForDuplicates(resource, configNames, "id.duplicate", c.values.get("name"), IMarker.SEVERITY_WARNING);
				validateProperties(modelmgr, resource, c.properties, "load balancer adhoc configuration.");
		}
		
		LoadBalancerPairConfigs pairConfigs = modelmgr.getModel().loadBalancerPairConfigs;
		ArrayList<String> pairConfigNames = new ArrayList<String>();
		for(LoadBalancerPairConfig c:pairConfigs.configs ){
				if(isEmpty(c.values.get("name"))){
					reportProblem(resource, getMessageString("loadbalancer.empty.pair.configname"));
				}
				else{
					pairConfigNames.add(c.values.get("name"));
				}
				checkForDuplicates(resource, pairConfigNames, "id.duplicate", c.values.get("name"), IMarker.SEVERITY_WARNING);
				validateProperties(modelmgr, resource, c.properties, "load balancer pair configuration.");				
		}
		
		
	}

	private void validateGroups(ClusterConfigModelMgr modelmgr, IResource resource) {
		ArrayList<RuleElement> ruleElements = modelmgr.getRulesGroup();
		List<String> duplicates = new ArrayList<>();
		for (RuleElement ruleElement: ruleElements) {
			if (!(ruleElement instanceof RulesGrp))
				continue;
			RulesGrp rulesGrp = (RulesGrp) ruleElement;
			if (isEmpty(rulesGrp.id))
				reportProblem(resource, getMessageString("groups.rulesgrp.name.empty"));
			checkGroupName(rulesGrp.id, resource);
			ArrayList<String> ruleNames = modelmgr.getRuleNames(rulesGrp, true);
			checkIfProjectRules(resource, ruleNames, rulesGrp.id);
			checkForDuplicates(resource, ruleNames, "groups.rulesgrp.rules.duplicate", rulesGrp.id, IMarker.SEVERITY_WARNING);
			String[] rulesGroupNames = modelmgr.getRulesGroupNames();
			List<String> ruleGrpArr = Arrays.asList(rulesGroupNames);
			if (ruleGrpArr.indexOf(rulesGrp.id) < ruleGrpArr.lastIndexOf(rulesGrp.id)) {
				if (!duplicates.contains(rulesGrp.id)) {
					duplicates.add(rulesGrp.id);
					reportProblem(resource, getMessageString("groups.rulesgrp.duplicate", rulesGrp.id));
				}
			}
		}
		duplicates.clear();
		ArrayList<DestinationElement> destinationElements = modelmgr.getDestinationsGroup();
		String[] destNames = modelmgr.getDestinationsGroupNames();
		List<String> destNameArr = Arrays.asList(destNames);
		for (DestinationElement destinationElement: destinationElements) {
			if (destinationElement instanceof DestinationsGrp) {
				DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
				if (isEmpty(destinationsGrp.id))
					reportProblem(resource, getMessageString("groups.destinationsgrp.name.empty"));
				checkGroupName(destinationsGrp.id, resource);
				if (destNameArr.indexOf(destinationsGrp.id) < destNameArr.lastIndexOf(destinationsGrp.id)) {
					if (!duplicates.contains(destinationsGrp.id)) {
						duplicates.add(destinationsGrp.id);
						reportProblem(resource, getMessageString("groups.destinationsgrp.duplicate", destinationsGrp.id));
					}
				}
				ArrayList<String> destinationNames = modelmgr.getDestinationUris(destinationsGrp, true);
				checkIfProjectDestinations(resource, destinationNames, destinationsGrp.id);
				for (DestinationElement grpDestinationElement: destinationsGrp.destinations) {
					if (grpDestinationElement instanceof Destination) {
						Destination destination = (Destination) grpDestinationElement;
						checkIfProjectRuleFunctions(resource, destinationsGrp, destination, destination.destinationVal.get(Elements.PRE_PROCESSOR.localName));
						checkIfProjectThreadAffinityRuleFunctions(resource, destinationsGrp, destination, destination.destinationVal.get(Elements.THREAD_AFFINITY_RULE_FUNCTION.localName));
						if (!validateNumericField(destination.destinationVal.get(Elements.THREAD_COUNT.localName), glbVars, true, false))
							reportProblem(resource, getMessageString("groups.destination.threadcount.invalid", destination.destinationVal.get(Elements.THREAD_COUNT.localName), destination.id, destinationsGrp.id));
						if (!validateNumericField(destination.destinationVal.get(Elements.QUEUE_SIZE.localName), glbVars, true, false))
							reportProblem(resource, getMessageString("groups.destination.queuesize.invalid", destination.destinationVal.get(Elements.QUEUE_SIZE.localName), destination.id, destinationsGrp.id));
					}
				}
			} 
		}	
		duplicates.clear();

		ArrayList<FunctionElement> funcElements = modelmgr.getFunctionsGroup(true);
		for (FunctionElement funcElement: funcElements) {
			if (!(funcElement instanceof FunctionsGrp))
				continue;
			FunctionsGrp functionsGrp = (FunctionsGrp) funcElement;
			if (isEmpty(functionsGrp.id))
				reportProblem(resource, getMessageString("groups.functionsgrp.name.empty"));
			checkGroupName(functionsGrp.id, resource);

			ArrayList<String> funcNames = modelmgr.getFunctionNames((FunctionsGrp)funcElement, true);
			checkIfProjectRuleFunctions(resource, funcNames, ((FunctionsGrp)funcElement).id);
			String[] functionsGroupNames = modelmgr.getFunctionsGroupNames();
			List<String> funcGrpNameArr = Arrays.asList(functionsGroupNames);
			if (funcGrpNameArr.indexOf(functionsGrp.id) < funcGrpNameArr.lastIndexOf(functionsGrp.id)) {
				if (!duplicates.contains(functionsGrp.id)) {
					duplicates.add(functionsGrp.id);
					reportProblem(resource, getMessageString("groups.functionsgrp.duplicate", functionsGrp.id));
				}
			}
		}
		duplicates.clear();

		ArrayList<ProcessElement> processElements = modelmgr.getProcessesGroupList().processElements;
		for (ProcessElement procElement: processElements) {
			if (!(procElement instanceof ProcessesGrp))
				continue;
			ProcessesGrp processGrp = (ProcessesGrp) procElement;
			if (isEmpty(processGrp.id))
				reportProblem(resource, getMessageString("groups.processgrp.name.empty"));
			checkGroupName(processGrp.id, resource);
			String[] processesGroupNames = modelmgr.getProcessesGroupNames();
			List<String> procGrpNameArr = Arrays.asList(processesGroupNames);
			if (procGrpNameArr.indexOf(processGrp.id) < procGrpNameArr.lastIndexOf(processGrp.id)) {
				if (!duplicates.contains(processGrp.id)) {
					duplicates.add(processGrp.id);
					reportProblem(resource, getMessageString("groups.processgrp.duplicate", processGrp.id));
				}
			}
		}
		List<String> uris = getAgentProcesses(modelmgr, resource, processElements);
		for (AbstractBPMNProcessor processor : processors) {
			processor.validate(uris, resource, false, this);
		}
	}
	
	private void checkGroupName(String id, IResource resource) {
		// not sure if there are other restrictions that should be put in place for groups,
		// but names with spaces cause xml parsing errors when referenced
		if (id.indexOf(' ') != -1) {
			reportProblem(resource, getMessageString("groups.name.invalid", id));
		}
	}

	private void validateLogConfigurations(ClusterConfigModelMgr modelmgr, IResource resource) {
		ArrayList<LogConfig> logConfigs = modelmgr.getLogConfigs();
		for (LogConfig logConfig: logConfigs) {
			if (isEmpty(logConfig.id))
				reportProblem(resource, getMessageString("logconfig.name.empty", logConfig.id));
			if (!validateNumericField(logConfig.files.get(Elements.MAX_NUMBER.localName), glbVars, true, true))
				reportProblem(resource, getMessageString("logconfig.files.maxnumber.invalid", logConfig.id, logConfig.files.get(Elements.MAX_NUMBER.localName)));
			if (!validateNumericField(logConfig.files.get(Elements.MAX_SIZE.localName), glbVars, true, true))
				reportProblem(resource, getMessageString("logconfig.files.maxsize.invalid", logConfig.id, logConfig.files.get(Elements.MAX_SIZE.localName)));
		}
	}
	
	private void validateAgentClasses(ClusterConfigModelMgr modelmgr, IResource resource) {
		ArrayList<AgentClass> agents = modelmgr.getAgentClasses();
		for (AgentClass agent: agents) {
			if (isEmpty(agent.name)) {
				reportProblem(resource, getMessageString("agentclass.name.empty", agent.name));
			}
			
			if (agent instanceof DashInfProcQueryAgent) {
				DashInfProcQueryAgent fAgent = (DashInfProcQueryAgent) agent;
				ArrayList<String> destinationNames = modelmgr.getAgentDestinationsGrpUris(fAgent.agentDestinationsGrpObj, true);
				checkIfProjectDestinations(resource, destinationNames, agent);
				for (DestinationElement destinationElement: fAgent.agentDestinationsGrpObj.agentDestinations) {
					if (destinationElement instanceof Destination) {
						Destination destination = (Destination) destinationElement;
						checkIfProjectRuleFunctions(resource, fAgent, destination, destination.destinationVal.get(Elements.PRE_PROCESSOR.localName));

						if (!validateNumericField(destination.destinationVal.get(Elements.THREAD_COUNT.localName), glbVars, true, false))
							reportProblem(resource, getMessageString("agentclass.destination.threadcount.invalid", destination.destinationVal.get(Elements.THREAD_COUNT.localName), destination.id, fAgent.name));
						if (!validateNumericField(destination.destinationVal.get(Elements.QUEUE_SIZE.localName), glbVars, true, false))
							reportProblem(resource, getMessageString("agentclass.destination.queuesize.invalid", destination.destinationVal.get(Elements.QUEUE_SIZE.localName), destination.id, fAgent.name));
					}
				}
				
				if(!(agent instanceof ProcessAgent)){
					ArrayList<String> startupFuncNames = modelmgr.getAgentFunctionNames(fAgent.agentStartupFunctionsGrpObj, true);
					checkIfProjectRuleFunctions(resource, startupFuncNames, agent, "Startup");
					ArrayList<String> shutdownFuncNames = modelmgr.getAgentFunctionNames(fAgent.agentShutdownFunctionsGrpObj, true);
					checkIfProjectRuleFunctions(resource, shutdownFuncNames, agent, "Shutdown");
				}
				
				if (agent instanceof DashInfProcAgent) {
					DashInfProcAgent fInfDashAgent = (DashInfProcAgent) agent;
					ArrayList<String> ruleNames = modelmgr.getAgentRulesGrpNames(fInfDashAgent, true);
					checkIfProjectRules(resource, ruleNames, agent);
					checkForDuplicates(resource, ruleNames, "agentclass.rule.duplicate", agent.name, IMarker.SEVERITY_WARNING);
					if (agent instanceof InfAgent) {
						validateLocalCache(resource, fAgent.name, ((InfAgent)fAgent).localCacheMaxSize, ((InfAgent)fAgent).localCacheEvictionTime);
						validateSharedQueueAndMaxActive(resource, fAgent.name, ((InfAgent)fAgent).sharedQueueSize, ((InfAgent)fAgent).sharedQueueWorkers,((InfAgent)fAgent).maxActive);
					}
				}
				
				if (agent instanceof ProcessAgent) {
					ProcessAgent fProAgent = (ProcessAgent)agent;
					List<String> uris = new ArrayList<String>();
					uris.addAll(getAgentProcesses(modelmgr, resource, fProAgent.agentProcessesGrpObj.agentProcesses));
					for (AbstractBPMNProcessor processor : processors) {
						processor.validate(uris, resource, true, this);
					}
					uris.addAll(getAgentFunctions(resource, fProAgent.agentStartupFunctionsGrpObj.agentFunctions));
					uris.addAll(getAgentFunctions(resource, fProAgent.agentShutdownFunctionsGrpObj.agentFunctions));
					for (AbstractBPMNProcessor processor : processors) {
						processor.validate(uris, resource, false, this);
					}
				}
				
				if (agent instanceof QueryAgent) {
					validateLocalCache(resource, fAgent.name, ((QueryAgent)fAgent).localCacheMaxSize, ((QueryAgent)fAgent).localCacheEvictionTime);
					validateSharedQueueAndMaxActive(resource, fAgent.name, ((QueryAgent)fAgent).sharedQueueSize, ((QueryAgent)fAgent).sharedQueueWorkers,((QueryAgent)fAgent).maxActive);
				}
			} else if (agent instanceof MMAgent) {
				MMAgent mAgent = (MMAgent) agent;

				// Check Inf agent
				String infAgentName = mAgent.infAgentRef;
				if (isEmpty(infAgentName))
					reportProblem(resource, getMessageString("agentClass.mmagent.infagent.name.empty", mAgent.name));
				else if (!modelmgr.getAgentClassNames().contains(infAgentName))
					reportProblem(resource, getMessageString("agentClass.mmagent.infagent.name.notfound", mAgent.name, infAgentName));
				
				// Check Query agent
				String queryAgentName = mAgent.queryAgentRef;
				if (isEmpty(queryAgentName))
					reportProblem(resource, getMessageString("agentClass.mmagent.queryagent.name.empty", mAgent.name));
				else if (!modelmgr.getAgentClassNames().contains(queryAgentName))
					reportProblem(resource, getMessageString("agentClass.mmagent.queryagent.name.notfound", mAgent.name, queryAgentName));
			}
			validateProperties(modelmgr, resource, agent.properties, "Agent Class [" + agent.name + "]");
		}
	}
	
	/**
	 * @param resource
	 * @param processors
	 * @param proElements
	 */
	private List<String> getAgentProcesses(ClusterConfigModelMgr modelmgr, IResource resource, ArrayList<ProcessElement> proElements) {
		List<String> uris = new ArrayList<String>();
		for (ProcessElement processElement : proElements) {
			if (processElement instanceof ProcessesGrp) {
				ProcessesGrp proGrp = (ProcessesGrp)processElement;
				uris.addAll(modelmgr.getProcessNames(proGrp, false));
			} else if (processElement instanceof AgentProcessesGrpElement) {
				AgentProcessesGrpElement agpElement = (AgentProcessesGrpElement)processElement;
				uris.addAll(modelmgr.getProcessNames(agpElement.processesGrp, false));
			} else if (processElement instanceof Process) {
				Process process = (Process)processElement;
				uris.add(process.uri);
			}
		}
		return uris;
	}

	/**
	 * @param resource
	 * @param processors
	 * @param proElements
	 */
	private List<String> getAgentFunctions(IResource resource, ArrayList<FunctionElement> proElements) {
		List<String> uris = new ArrayList<String>();
		for (FunctionElement processElement : proElements) {
			if (! (processElement instanceof Function))
				continue;
			Function process = (Function)processElement;
			uris.add(process.uri);
		}
		return uris;
	}
	
	private void validateProcessingUnits(ClusterConfigModelMgr modelmgr, IResource resource) {
		ArrayList<ProcessingUnit> procUnits = modelmgr.getProcessingUnits();
		for (ProcessingUnit procUnit: procUnits) {
			if (isEmpty(procUnit.name))
				reportProblem(resource, getMessageString("procunit.name.empty", procUnit.name));
			if (procUnit.logConfig == null || isEmpty(procUnit.logConfig.id))
				reportProblem(resource, getMessageString("procunit.logconfig.empty", procUnit.name));
			if (!procUnit.enableCacheStorage && modelmgr.isCachePU(procUnit)) {
				reportProblem(resource, getMessageString("procunit.cachestorage.cachepu", procUnit.name), SEVERITY_WARNING);
			} else if (procUnit.enableCacheStorage && modelmgr.isDashboardPU(procUnit)) {
				reportProblem(resource, getMessageString("procunit.cachestorage.dashboardpu", procUnit.name), SEVERITY_WARNING);
			} 
			ArrayList<LinkedHashMap<String,String>> agentClassesMap = procUnit.agentClasses;
			HashSet<String> agentSetNames = new HashSet<String>();
			for (LinkedHashMap<String,String> agentClassMap: agentClassesMap) {
				String agentName = agentClassMap.get(ProcessingUnit.AGENT_REF);
				if (isEmpty(agentName))
					reportProblem(resource, getMessageString("procunit.agentclass.name.empty", procUnit.name));
				else if (!modelmgr.getAgentClassNames().contains(agentName))
					reportProblem(resource, getMessageString("procunit.agentclass.name.notfound", procUnit.name, agentName));
				AgentClass agent = modelmgr.getAgentClass(agentName);
				if ((agent instanceof CacheAgent) && agentClassesMap.size()>1) {
					reportProblem(resource, getMessageString("procunit.agentclass.cache.alone", procUnit.name, agentName));
				}
				agentSetNames.add(agentName);
				
				if (!validateNumericField(agentClassMap.get(ProcessingUnit.AGENT_PRIORITY), glbVars, true, false))
					reportProblem(resource, getMessageString("procunit.agentclass.priority", procUnit.name, agentName, agentClassMap.get(ProcessingUnit.AGENT_PRIORITY)));
			}
			
			for (String agentSetName: agentSetNames) {
				ArrayList<String> agentKeys = new ArrayList<String>();
				for (LinkedHashMap<String,String> agentClassMap: agentClassesMap) {
					if (!agentClassMap.get(ProcessingUnit.AGENT_REF).equals(agentSetName))
						continue;
					agentKeys.add(agentClassMap.get(ProcessingUnit.AGENT_KEY));
				}
				checkForDuplicates(resource, agentKeys, "procunit.agentclass.key.duplicate", procUnit.name);
			}

			validateProperties(modelmgr, resource, procUnit.properties, "Processing Unit [" + procUnit.name + "]");
		}
		
		//Check for duplicates
		/*
		ArrayList<String> names = modelmgr.getProcessingUnitsName();
		checkForDuplicates(resource, names, "procunit.name.duplicate");
		*/
	}

	private void validateIds(ClusterConfigModelMgr modelmgr, IResource resource) {
		ArrayList<String> names = new ArrayList<String>();
		for (DestinationElement destinationElement: modelmgr.getDestinationsGroup()) {
			if (destinationElement instanceof DestinationsGrp) {
				names.addAll(modelmgr.getDestinationNames((DestinationsGrp)destinationElement, true));
			}
		}
		
		ArrayList<AgentClass> agents = modelmgr.getAgentClasses();
		for (AgentClass agent: agents) {
			if (agent instanceof DashInfProcQueryAgent) {
				names.addAll(modelmgr.getAgentDestinationsGrpNames(((DashInfProcQueryAgent)agent).agentDestinationsGrpObj, true));
			}
		}
		
		names.addAll(modelmgr.getLogConfigsName());
		names.addAll(modelmgr.getAgentClassNames());
		names.addAll(modelmgr.getProcessingUnitsName());
		checkForDuplicates(resource, names, "id.duplicate", "");
		checkForNumerics(resource, names);
	}

	private void validateProperties(ClusterConfigModelMgr modelmgr, IResource resource, PropertyElementList propList, String idpath) {
		if (propList == null)
			return;
		ArrayList<String> names = new ArrayList<String>();
		for (PropertyElement propElement: propList.propertyList) {
			validateProperties(modelmgr, resource, propElement, names, idpath);
		}
		checkForDuplicates(resource, names, "property.name.duplicate", idpath);
	}
	
	private void validateProperties(ClusterConfigModelMgr modelmgr, IResource resource, PropertyGroup propGrp, String idpath) {
		ArrayList<String> names = new ArrayList<String>();
		for (PropertyElement propElement: propGrp.propertyList) {
			validateProperties(modelmgr, resource, propElement, names, idpath);
		}		
		checkForDuplicates(resource, names, "property.name.duplicate", idpath);
	}
	
	private void validateProperties(ClusterConfigModelMgr modelmgr, IResource resource, PropertyElement propElement, ArrayList<String> names, String idpath) {
		if (propElement instanceof PropertyGroup) {
			String name = ((PropertyGroup)propElement).name; 
			if (isEmpty(name)) {
				reportProblem(resource, getMessageString("property.group.name.empty", idpath));
			} else {
				names.add(name);
			}
			validateProperties(modelmgr, resource, (PropertyGroup) propElement, idpath); 
		} else if ((propElement instanceof Property)) {
			String name = ((Property)propElement).fieldMap.get("name");
			if (isEmpty(name)) {
				reportProblem(resource, getMessageString("property.name.empty", idpath));
			} else {
				names.add(name);
			}
		}
	}

	private void validateLocalCache(IResource resource, String agentName, String maxSize, String evictTime) {
		boolean valid = validateNumericField(maxSize, glbVars, true, false);
		if (!valid)
			reportProblem(resource, getMessageString("agentclass.localcache.maxsize.invalid", maxSize, agentName));
		valid = validateNumericField(evictTime, glbVars, true, false);
		if (!valid)
			reportProblem(resource, getMessageString("agentclass.localcache.evictiontime.invalid", evictTime, agentName));
	}
	
	private void validateSharedQueueAndMaxActive(IResource resource, String agentName, String queueSize, String workerThreads,String maxActive) {
		boolean valid = validateNumericField(queueSize, glbVars, true, false);
		if (!valid)
			reportProblem(resource, getMessageString("agentclass.sharedqueue.queuesize.invalid", queueSize, agentName));
		valid = validateNumericField(workerThreads, glbVars, true, false);
		if (!valid)
			reportProblem(resource, getMessageString("agentclass.sharedqueue.workerthreads.invalid", workerThreads, agentName));
		valid = validateNumericField(maxActive, glbVars, true, false);
		if (!valid)
			reportProblem(resource, getMessageString("agentclass.maxactive.invalid", maxActive, agentName));
	}
	
	private void checkIfProjectRules(IResource resource, ArrayList<String> ruleNames, String rulesGrpName) {
		IProject project = resource.getProject();
		List<String> projRuleNames = new ArrayList<String>();
		projRuleNames.addAll(ClusterConfigProjectUtils.getProjectRuleNames(project));
		projRuleNames.addAll(ClusterConfigProjectUtils.getProjectRuleTemplateNames(project));
		for (String ruleName: ruleNames) {
			if (ruleName.equals("/") || isFolder(project, ruleName))
				continue;
			if (!projRuleNames.contains(ruleName)) {
				reportProblem(resource, getMessageString("groups.rule.invalid", ruleName, rulesGrpName), IMarker.SEVERITY_ERROR);
			}
		}
	}
	
	private void checkIfProjectRules(IResource resource, ArrayList<String> ruleNames, AgentClass agent) {
		IProject project = resource.getProject();
		List<String> projRuleNames = new ArrayList<String>();
		projRuleNames.addAll(ClusterConfigProjectUtils.getProjectRuleNames(project));
		projRuleNames.addAll(ClusterConfigProjectUtils.getProjectRuleTemplateNames(project));
		for (String ruleName: ruleNames) {
			if (ruleName.equals("/") || isFolder(project, ruleName))
				continue;
			if (!projRuleNames.contains(ruleName)) {
				reportProblem(resource, getMessageString("agentclass.rule.invalid", ruleName, agent.name), IMarker.SEVERITY_ERROR);
			}
		}
	}
	
	private void checkIfProjectDestinations(IResource resource, ArrayList<String> destinationNames, String destinationGrpName) {
		ArrayList<String> projDestinationNames = ClusterConfigProjectUtils.getProjectDestinationNames(resource.getProject());
		for (String destinationName: destinationNames) {
			if (destinationName.equals("/") || isFolder(resource.getProject(), destinationName))
				continue;
			if (!projDestinationNames.contains(destinationName)) {
				reportProblem(resource, getMessageString("groups.destination.invalid", destinationName, destinationGrpName), IMarker.SEVERITY_ERROR);
			}
		}
	}
	
	private void checkIfProjectDestinations(IResource resource, ArrayList<String> destinationNames, AgentClass agent) {
		ArrayList<String> projDestinationNames = ClusterConfigProjectUtils.getProjectDestinationNames(resource.getProject());
		for (String destinationName: destinationNames) {
			if (destinationName.equals("/") || isFolder(resource.getProject(), destinationName))
				continue;
			if (!projDestinationNames.contains(destinationName)) {
				reportProblem(resource, getMessageString("agentclass.destination.invalid", destinationName, agent.name), IMarker.SEVERITY_ERROR);
			}
		}
	}
	
	private void checkIfProjectRuleFunctions(IResource resource, String domainObjUri, String funcName) {
		ArrayList<String> projFuncNames = ClusterConfigProjectUtils.getProjectRuleFunctionNames(resource.getProject(), ClusterConfigProjectUtils.RF_ARGS_TYPE_SUBSCRIPTION_PREPROCESSOR);
		if (isEmpty(funcName) || funcName.equals("/")) //preprocessor can be empty
			return;
		if (!projFuncNames.contains(funcName)) {
			reportProblem(resource, getMessageString("cache.do.overrides.preprocessor.invalid", domainObjUri, funcName), IMarker.SEVERITY_ERROR);
		}
	}
	
	private void checkIfProjectRuleFunctions(IResource resource, DestinationsGrp destinationsGrp, Destination destination, String funcName) {
		ArrayList<String> projFuncNames = ClusterConfigProjectUtils.getProjectRuleFunctionNames(resource.getProject(), ClusterConfigProjectUtils.RF_ARGS_TYPE_PREPROCESSOR);
		if (isEmpty(funcName) || funcName.equals("/")) //preprocessor can be empty
			return;
		if (!projFuncNames.contains(funcName)) {
			reportProblem(resource, getMessageString("groups.destination.preprocessor.invalid", funcName, destination.id, destinationsGrp.id), IMarker.SEVERITY_ERROR);
		}
	}

	private void checkIfProjectThreadAffinityRuleFunctions(IResource resource, DestinationsGrp destinationsGrp, Destination destination, String funcName) {
		ArrayList<String> projFuncNames = ClusterConfigProjectUtils.getProjectRuleFunctionNames(resource.getProject(), ClusterConfigProjectUtils.RF_ARGS_TYPE_THREAD_AFFINITY_RULEFUNCTION);
		if (isEmpty(funcName))
			return;
		if (!projFuncNames.contains(funcName)) {
			reportProblem(resource, getMessageString("groups.destination.thread.affinity.rulefunction.invalid", funcName, destination.id, destinationsGrp.id), IMarker.SEVERITY_ERROR);
		}
	}

	private void checkIfProjectRuleFunctions(IResource resource, AgentClass agent, Destination destination, String funcName) {
		ArrayList<String> projFuncNames = ClusterConfigProjectUtils.getProjectRuleFunctionNames(resource.getProject(), ClusterConfigProjectUtils.RF_ARGS_TYPE_PREPROCESSOR);
		if (isEmpty(funcName) || funcName.equals("/")) //preprocessor can be empty
			return;
		if (!projFuncNames.contains(funcName)) {
			reportProblem(resource, getMessageString("agentclass.destination.preprocessor.invalid", funcName, destination.id, agent.name), IMarker.SEVERITY_ERROR);
		}
	}
	
	private void checkIfProjectRuleFunctions(IResource resource, ArrayList<String> funcNames, String funcGrpName) {
		ArrayList<String> projFuncNames = ClusterConfigProjectUtils.getProjectRuleFunctionNames(resource.getProject(), ClusterConfigProjectUtils.RF_ARGS_TYPE_STARTUP);
		for (String funcName: funcNames) {
			if (funcName.equals("/"))
				continue;
			if (!projFuncNames.contains(funcName)) {
				reportProblem(resource, getMessageString("groups.function.invalid", funcName, funcGrpName), IMarker.SEVERITY_ERROR);
			}
		}
	}
	
	private void checkIfProjectRuleFunctions(IResource resource, ArrayList<String> funcNames, AgentClass agent, String functionType) {
		ArrayList<String> projFuncNames = ClusterConfigProjectUtils.getProjectRuleFunctionNames(resource.getProject(), ClusterConfigProjectUtils.RF_ARGS_TYPE_STARTUP);
		for (String funcName: funcNames) {
			if (funcName.equals("/"))
				continue;
			if (!projFuncNames.contains(funcName)) {
				reportProblem(resource, getMessageString("agentclass.function.invalid", functionType, funcName, agent.name), IMarker.SEVERITY_ERROR);
			}
		}
	}

	private boolean isFolder(IProject project, String name) {
		if (!isEmpty(name)) {
			IFolder folder = project.getFolder(name);
			if (folder.exists()){
				return true;
			} else {
                ElementContainer ec = IndexUtils.getFolderForFile(IndexUtils.getIndex(project.getName()), name, false,true);
                if (ec != null) {
                	return true;
                } else {
                	return false;
                }
			}
		}
		return false;
	}
	
	private boolean isEmpty(String name) {
		if (name==null || name.trim().equals(""))
			return true;
		return false;
	}
	
	private void checkForDuplicates(IResource resource, ArrayList<String> names, String msgId, String idpath) {
		checkForDuplicates(resource, names, msgId, idpath, IMarker.SEVERITY_ERROR);
	}
	
	private void checkForDuplicates(IResource resource, ArrayList<String> names, String msgId, String idpath, int severity) {
		Set<String> dupNames = getDuplicateNames(names);
		for (String dupName: dupNames) {
			reportProblem(resource, getMessageString(msgId, dupName, idpath), severity);
		}
	}
	
	private void checkForNumerics(IResource resource, ArrayList<String> names) {
		for (String name: names) {
			try { 
				int num = Integer.parseInt(name);
				reportProblem(resource, getMessageString("id.invalid.numeric", name));
			} catch (Exception e) {
			}
		}
	}

}