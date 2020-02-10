package com.tibco.cep.studio.core.clusterconfig;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.core.builder.AbstractBPMNProcessor;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyGroup;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringContext;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.DependencyUtils;

/**
 * @author sasahoo, ssailapp
 *
 */
public class ClusterConfigRefactoringParticipant extends StudioRefactoringParticipant {

	public static final String CDD_EXTENSION  = "cdd";

	public ClusterConfigRefactoringParticipant() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
															  OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			return null;
		}
		IResource resource = getResource();

		if (resource instanceof IFolder) {
			Object elementToRefactor = getElementToRefactor();
			// folder refactorings need to be done pre-change, as elements could have moved and therefore post-changes cannot be computed
			return processClusterConfiguration(elementToRefactor, fProjectName, pm);
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;

		if (!(CDD_EXTENSION.equalsIgnoreCase(file.getFileExtension()))) {
			return null;//new NullChange();
		}
		Object elementToRefactor = getElementToRefactor();

		String name = resource.getName().replace(".cdd", "");
		ClusterConfigModelMgr modelmgr = new ClusterConfigModelMgr(resource);
		
		modelmgr.parseModel();

		CompositeChange change = new CompositeChange("Changes to "+ name);
		processClusterConfigurationElement(fProjectName, change, file, modelmgr, elementToRefactor);
		return change;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			                                 CheckConditionsContext context) throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return super.checkConditions(pm, context);
		}
		RefactoringStatus status = super.checkConditions(pm, context);
		IResource resource = getResource();
		if (!(resource instanceof IFile)) {
			return status;
		}
		IFile file = (IFile) resource;
		if (isDeleteRefactor()
				|| CDD_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			return status;
		}

		return status;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		return processClusterConfiguration(elementToRefactor, fProjectName, pm);
	}

	/**
	 * @param elementToRefactor
	 * @param projectName
	 * @param pm
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	private Change processClusterConfiguration(Object elementToRefactor,
			                                  String projectName,
			                                  IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		if (!shouldUpdateReferences()) {
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Cluster Deployment Descriptor changes:");
		List<IFile> cddFilesList = new ArrayList<IFile>();
		CommonUtil.getResourcesByExtension(getResource().getProject(), CDD_EXTENSION, cddFilesList);
		for (IFile element: cddFilesList) {
			if (isFileProcessed(elementToRefactor, element)){
				// already processed in the pre-change
				continue;
			}
			if (isCddFile(elementToRefactor))
				return null;
			ClusterConfigModelMgr modelmgr = new ClusterConfigModelMgr(element);
			modelmgr.parseModel();
			processClusterConfigurationElement(fProjectName, compositeChange, (IFile)element, modelmgr, elementToRefactor);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	private boolean isCddFile(Object elementToRefactor) {
		if (elementToRefactor instanceof StudioRefactoringContext) {
	    	StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
	    	if (context.getElement() instanceof IFile) {
	    		IFile file = (IFile)context.getElement();
	    		if (file.getFileExtension().equalsIgnoreCase(CDD_EXTENSION))
	    			return true;
	    	}
		}
		return false;
	}

	/**
	 * @param elementToRefactor
	 * @param element
	 * @return
	 */
	protected boolean isFileProcessed(Object elementToRefactor, IResource element){
		if (elementToRefactor instanceof StudioRefactoringContext) {
	    	StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
	    	if (context.getElement() instanceof IFile) {
	    		IFile file = (IFile)context.getElement();
	    		if(file.getFullPath().toString().equals(element.getFullPath().toString())) {
	    			return true;
	    		}
	    	}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		return "Cluster Configuration Refactoring participant";
	}


	/**
	 * @param projectName
	 * @param compositeChange
	 * @param clusterFile
	 * @param modelmgr
	 * @param elementToRefactor
	 * @throws CoreException
	 */
	private void processClusterConfigurationElement(String projectName, CompositeChange compositeChange, IFile clusterFile,
			                                 ClusterConfigModelMgr modelmgr, Object elementToRefactor) throws CoreException {
		boolean changed = false;

		//Handling Cluster Configuration rename
		if (elementToRefactor instanceof StudioRefactoringContext){
			StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
			if (context.getElement() instanceof IFile){
				IFile file = (IFile)context.getElement();
				if (file.getFileExtension().equals(CDD_EXTENSION)){
					if (isRenameRefactor()) {
						//modelmgr.getModel().clusterInfo.name = getNewElementName(); // Cluster name needn't be the same as file name
						//We don't need to do further checks
						Change change = createTextFileChange(clusterFile, modelmgr);
						compositeChange.add(change);
						return;
					}
					if (isMoveRefactor()) {
						return;
					}
				}
			}
		}

		// Handle Cluster General
		String omType = modelmgr.getOmMgr();
		if (omType.equals(ClusterConfigModel.ObjectManagement.CACHE_MGR)) {
			for (DomainObject domainObj: modelmgr.getModel().om.cacheOm.domainObjects.domainObjOverrides.overrides) {
				if (processDomainObject(elementToRefactor, domainObj)) {
					changed = true;
				}
				if (processRuleFunctions(elementToRefactor, domainObj.values, Elements.PRE_PROCESSOR.localName)) {
					changed = true;
				}
			}
		}

		// Handle Collections
		ArrayList<RuleElement> rulesGrpList = modelmgr.getRulesGroup();
		for(RuleElement ruleElement:rulesGrpList){
			if (! (ruleElement instanceof RulesGrp))
				continue;
			RulesGrp rulesGrp = (RulesGrp)ruleElement;
			if (processRuleGroups(elementToRefactor, rulesGrp.rules)) {
				changed = true;
			}
		}
		ArrayList<DestinationElement> destinationsGrpList = modelmgr.getDestinationsGroup();
		for(DestinationElement destinationElement:destinationsGrpList){
			if (! (destinationElement instanceof DestinationsGrp))
				continue;
			DestinationsGrp destinationsGrp = (DestinationsGrp)destinationElement;
			if (processDestinationGroups(elementToRefactor, destinationsGrp.destinations)) {
				changed = true;
			}
		}
		ArrayList<FunctionElement> functionsGrpList = modelmgr.getFunctionsGroup(false);
		for(FunctionElement functionElement:functionsGrpList){
			if (! (functionElement instanceof FunctionsGrp))
				continue;
			FunctionsGrp functionsGrp = (FunctionsGrp)functionElement;
			if (processFunctionGroups(elementToRefactor, functionsGrp.functions)) {
				changed = true;
			}
		}

		for(ProcessElement processElement: modelmgr.getProcessesGroupList().processElements){
			if (! (processElement instanceof ProcessesGrp))
				continue;
			ProcessesGrp processGrp = (ProcessesGrp)processElement;
			if (processBPMNProcessGroups(elementToRefactor, processGrp.processes)) {
				changed = true;
			}
		}

		//Handling Agent Classes
		ArrayList<AgentClass> agents = modelmgr.getAgentClasses();
		for (AgentClass agent: agents) {
			if(agent.type.equals(AgentClass.AGENT_TYPE_INFERENCE)){
				InfAgent inferenceAgent = (InfAgent)agent ;

				ArrayList<RuleElement> agentRules = inferenceAgent.agentRulesGrpObj.agentRules;
				if (processRuleGroups(elementToRefactor, agentRules)){
					changed  = true;
				}
				ArrayList<DestinationElement> agentDestinations = inferenceAgent.agentDestinationsGrpObj.agentDestinations;
				if (processDestinationGroups(elementToRefactor, agentDestinations)){
					changed  = true;
				}
				ArrayList<FunctionElement> startupAgentFunctions = inferenceAgent.agentStartupFunctionsGrpObj.agentFunctions;
				if (processFunctionGroups(elementToRefactor, startupAgentFunctions)){
					changed  = true;
				}
				ArrayList<FunctionElement> shutdownAgentFunctions = inferenceAgent.agentShutdownFunctionsGrpObj.agentFunctions;
				if (processFunctionGroups(elementToRefactor, shutdownAgentFunctions)){
					changed  = true;
				}
			}
			else if(agent.type.equals(AgentClass.AGENT_TYPE_QUERY)){
				QueryAgent queryAgent = (QueryAgent)agent ;
				ArrayList<DestinationElement> agentDestinations = queryAgent.agentDestinationsGrpObj.agentDestinations;
				if(processDestinationGroups(elementToRefactor, agentDestinations)){
					changed  = true;
				}
				ArrayList<FunctionElement> startupAgentFunctions = queryAgent.agentStartupFunctionsGrpObj.agentFunctions;
				if(processFunctionGroups(elementToRefactor, startupAgentFunctions)){
					changed  = true;
				}
				ArrayList<FunctionElement> shutdownAgentFunctions = queryAgent.agentShutdownFunctionsGrpObj.agentFunctions;
				if(processFunctionGroups(elementToRefactor, shutdownAgentFunctions)){
					changed  = true;
				}
			}
			else if(agent.type.equals(AgentClass.AGENT_TYPE_PROCESS)){
				ProcessAgent processAgent = (ProcessAgent) agent;
				ArrayList<ProcessElement> agentProcesses = processAgent.agentProcessesGrpObj.agentProcesses;
				if (processBPMNProcessGroups(elementToRefactor, agentProcesses)) {
					changed = true;
				}
				ArrayList<FunctionElement> startupAgentFunctions = processAgent.agentStartupFunctionsGrpObj.agentFunctions;
				if(processFunctionGroups(elementToRefactor, startupAgentFunctions)){
					changed  = true;
				}
				ArrayList<FunctionElement> shutdownAgentFunctions = processAgent.agentShutdownFunctionsGrpObj.agentFunctions;
				if(processFunctionGroups(elementToRefactor, shutdownAgentFunctions)){
					changed  = true;
				}

			} else if(agent.type.equals(AgentClass.AGENT_TYPE_DASHBOARD)){
				DashboardAgent dashboardAgent = (DashboardAgent)agent ;
				ArrayList<RuleElement> agentRules = dashboardAgent.agentRulesGrpObj.agentRules;
				if(processRuleGroups(elementToRefactor, agentRules)){
					changed  = true;
				}
				ArrayList<DestinationElement> agentDestinations = dashboardAgent.agentDestinationsGrpObj.agentDestinations;
				if(processDestinationGroups(elementToRefactor, agentDestinations)){
					changed  = true;
				}
				ArrayList<FunctionElement> startupAgentFunctions = dashboardAgent.agentStartupFunctionsGrpObj.agentFunctions;
				if(processFunctionGroups(elementToRefactor, startupAgentFunctions)){
					changed  = true;
				}
				ArrayList<FunctionElement> shutdownAgentFunctions = dashboardAgent.agentShutdownFunctionsGrpObj.agentFunctions;
				if(processFunctionGroups(elementToRefactor, shutdownAgentFunctions)){
					changed  = true;
				}
				if (processProperties(elementToRefactor, dashboardAgent.properties.propertyList)){
					changed = true;
				}
			}
		}

		if (changed) {
			Change change = createTextFileChange(clusterFile, modelmgr);
			compositeChange.add(change);
		}
	}

	private boolean processProperties(Object elementToRefactor, List<PropertyElement> propertyElements) {
		boolean changed = false;
		List<PropertyElement> propertiesToBeDeleted = new ArrayList<ClusterConfigModel.PropertyElement>();
		if (isFolderRefactor() == true || elementToRefactor instanceof IFile) {
			for (PropertyElement propertyElement : propertyElements) {
				if (propertyElement instanceof PropertyGroup) {
					PropertyGroup propertyGroup = (PropertyGroup) propertyElement;
					changed = processProperties(elementToRefactor, propertyGroup.propertyList);
				}
				else if (propertyElement instanceof Property) {
					Property property = (Property) propertyElement;
					for (Entry<String, String> entry : property.fieldMap.entrySet()) {
						if (isFolderRefactor() == true) {
							IFolder folder = (IFolder) getResource();
							if (IndexUtils.startsWithPath(entry.getValue(), folder)) {
								entry.setValue(getNewPath(entry.getValue(), folder));
								changed = true;
							}
						}
						else {
							IFile file = (IFile) elementToRefactor;
							if (isRenameRefactor() == true) {
								if (entry.getValue().endsWith(getResource().getName()) == true) {
									entry.setValue("/" + file.getParent().getProjectRelativePath().toPortableString() + "/" + getNewElementName() + "." + file.getFileExtension());
									changed = true;
								}
							}
							else if (isMoveRefactor() == true) {
								if (entry.getValue().endsWith(getResource().getName()) == true) {
									entry.setValue(getNewElementPath() + file.getName());
									changed = true;
								}
							}
							else if (isDeleteRefactor() == true) {
								if (entry.getValue().endsWith(getResource().getName()) == true) {
									propertiesToBeDeleted.add(property);
								}
							}
						}
					}
				}
			}
			if (propertiesToBeDeleted.isEmpty() == false) {
				changed = propertyElements.removeAll(propertiesToBeDeleted);
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param ruleElements
	 * @return
	 */
	private boolean processRuleGroups(Object elementToRefactor, ArrayList<RuleElement> ruleElements){
		boolean changed = false;
		for(RuleElement ruleElement:ruleElements){
			if (! (ruleElement instanceof Rule))
				continue;
			Rule rule = (Rule)ruleElement;
//			String ruleURI = rule.uri;
			if (processRules(elementToRefactor, ruleElements, rule)){
				changed  = true;
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param destinationElements
	 * @return
	 */
	private boolean processDestinationGroups(Object elementToRefactor, ArrayList<DestinationElement> destinationElements){
		boolean changed = false;
		for(DestinationElement destinationElement:destinationElements){
			if (! (destinationElement instanceof Destination))
				continue;
			Destination destination = (Destination)destinationElement;
//			String destinationURI = destination.destinationVal.get(Elements.URI.localName);
			if (processDestinations(elementToRefactor, destinationElements, destination)){
				changed  = true;
			}
			if (processRuleFunctions(elementToRefactor, destination.destinationVal, Elements.PRE_PROCESSOR.localName)) {
				changed = true;
			}
			if (processRuleFunctions(elementToRefactor, destination.destinationVal, Elements.THREAD_AFFINITY_RULE_FUNCTION.localName)) {
				changed = true;
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param functionElements
	 * @return
	 */
	private boolean processFunctionGroups(Object elementToRefactor, ArrayList<FunctionElement> functionElements){
		boolean changed = false;
		for(FunctionElement fnElement:functionElements){
			if (! (fnElement instanceof Function))
				continue;
			Function fn = (Function)fnElement;
//			String functionURI = fn.uri;
			if (processRuleFunctions(elementToRefactor, functionElements, fn)){
				changed  = true;
			}
		}
		return changed;
	}


	/**
	 * @param elementToRefactor
	 * @param processElements
	 * @return
	 */
	private boolean processBPMNProcessGroups(Object elementToRefactor,
			ArrayList<ProcessElement> processElements) {
		boolean changed = false;
		for (AbstractBPMNProcessor processor : DependencyUtils
				.getBPMNProcessors()) {
			if (processor.refactor(elementToRefactor, processElements,
					this)) {
				changed = true;
				break;
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param agentDestinations
	 * @param destination
	 * @return
	 */
	private boolean processDestinations(Object elementToRefactor, ArrayList<DestinationElement> agentDestinations, Destination destination){
		boolean changed = false;
		String destinationURI = destination.destinationVal.get(Elements.URI.localName);
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (elementToRefactor instanceof com.tibco.cep.designtime.core.model.service.channel.Destination) {
			com.tibco.cep.designtime.core.model.service.channel.Destination dest = (com.tibco.cep.designtime.core.model.service.channel.Destination) elementToRefactor;
			Channel channel = (Channel) dest.eContainer().eContainer();
			String destURI = channel.getFullPath() +"/"+ dest.getName();
			if(destinationURI.equals(destURI)){
				if (isDeleteRefactor()) {
					agentDestinations.remove(destination);
				}
				else{
					String newDestURI = channel.getFullPath() +"/"+ getNewElementName();
					destination.destinationVal.put(Elements.URI.localName, newDestURI);
				}
				changed = true;
			}
		}

		if (elementToRefactor instanceof Channel || isFolderRefactor()) {
			String newFullPath = null;
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(destinationURI, folder)) {
					newFullPath = getNewPath(destinationURI, folder);
				}
			} else {
				Channel channel = (Channel) elementToRefactor;
				if (isRenameRefactor()) {
					newFullPath = channel.getFolder() + getNewElementName();
					newFullPath = replace(destinationURI,channel.getFullPath(),newFullPath);
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + channel.getName();
					newFullPath = replace(destinationURI,channel.getFullPath(),newFullPath);
				}else if (isDeleteRefactor()) {
					agentDestinations.remove(destination);
					changed = true;
				}
			}
			if (newFullPath != null && !newFullPath.equals(destinationURI)) {
				destination.destinationVal.put(Elements.URI.localName, newFullPath);
				changed = true;
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param agentRules
	 * @param rule
	 * @return
	 */
	private boolean processRules(Object elementToRefactor,
			                          ArrayList<RuleElement> agentRules,
			                          Rule rule){
		boolean changed = false;
		String ruleURI = rule.uri;
		if(elementToRefactor instanceof com.tibco.cep.studio.core.index.model.RuleElement || isFolderRefactor()){
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(ruleURI, folder)) {
					rule.uri = getNewPath(ruleURI, folder);
					changed = true;
				}
			} else{
				com.tibco.cep.studio.core.index.model.RuleElement relement = (com.tibco.cep.studio.core.index.model.RuleElement) elementToRefactor;
				if(ruleURI.equals(relement.getFolder()+relement.getName())){
					if (isRenameRefactor()) {
						rule.uri = relement.getFolder() + getNewElementName();
					} else if(isMoveRefactor()) {
						rule.uri = getNewElementPath() + relement.getName();
					}
					changed = true;
				}
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param agentFunctions
	 * @param fn
	 * @return
	 */
	private boolean processRuleFunctions(Object elementToRefactor,
			ArrayList<FunctionElement> agentFunctions ,
			Function fn){
		boolean changed = false;
		String fnURI = fn.uri;
		if (elementToRefactor instanceof com.tibco.cep.studio.core.index.model.RuleElement || isFolderRefactor()){
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(fnURI, folder)) {
					fn.uri = getNewPath(fnURI, folder);
					changed = true;
				}
			} else{
				com.tibco.cep.studio.core.index.model.RuleElement relement = (com.tibco.cep.studio.core.index.model.RuleElement) elementToRefactor;
				if(fnURI.equals(relement.getFolder()+relement.getName())){
					if(isRenameRefactor()){
						fn.uri = relement.getFolder() + getNewElementName();
					}else if(isMoveRefactor()){
						fn.uri = getNewElementPath() + relement.getName();
					}
					changed = true;
				}
			}
		} else if (elementToRefactor != null && elementToRefactor instanceof IFile) {
			IFile file = (IFile) elementToRefactor;
			if (file.getFileExtension().equals(
					CommonIndexUtils.PROCESS_EXTENSION)) {
				IPath path = file.getProjectRelativePath().removeFileExtension();
				String folder = path.removeLastSegments(1).toPortableString();
				String name = path.lastSegment();
				if(!folder.startsWith("/")) {
					folder = "/"+folder;
				}
				if(!folder.endsWith("/")) {
					folder = folder+"/";
				}
				if(fnURI.equals(folder+name)){
					if(isRenameRefactor()){
						fn.uri = folder + getNewElementName();
					}else if(isMoveRefactor()){
						fn.uri = getNewElementPath() + name;
					}
					changed = true;
				}

			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param domainObj
	 * @return
	 */
	private boolean processDomainObject(Object elementToRefactor, DomainObject domainObj) {
		boolean changed = processDomainObjectValues(elementToRefactor, domainObj.values, Elements.URI.localName);
		changed |= processDomainObjectProperties(elementToRefactor, domainObj);
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param map
	 * @param key
	 * @return
	 */
	private boolean processDomainObjectValues(Object elementToRefactor, Map<String, String> map, String key) {
		boolean changed = false;
		String entityURI = map.get(key);
		String newUri = "";
		if (elementToRefactor instanceof EntityElement || isFolderRefactor()) {
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(entityURI, folder)) {
					newUri = getNewPath(entityURI, folder);
					map.put(key, newUri);
					changed = true;
				}
			} else {
				EntityElement eelement = (EntityElement) elementToRefactor;
				if (entityURI.equals(eelement.getFolder()+eelement.getName())) {
					if (isRenameRefactor()) {
						newUri = eelement.getFolder() + getNewElementName();
						map.put(key, newUri);
					} else if (isMoveRefactor()) {
						newUri = getNewElementPath() + eelement.getName();
						map.put(key, newUri);
					}
					changed = true;
				}
			}
		}
		return changed;
	}

	/**
	 * @param elementToRefactor
	 * @param domainObj
	 * @return
	 */
	private boolean processDomainObjectProperties(Object elementToRefactor, DomainObject domainObj) {
		boolean changed = false;
		if (!(elementToRefactor instanceof PropertyDefinition))
			return changed;
		PropertyDefinition prop = (PropertyDefinition) elementToRefactor;
		if (!domainObj.values.get(Elements.URI.localName).equals(prop.getOwnerPath()))
			return changed;

		LinkedHashMap<String, DomainObjectProperty> propsMap = domainObj.props;
		LinkedHashMap<String, DomainObjectProperty> newPropsMap = new LinkedHashMap<String, DomainObjectProperty>();
		for (Map.Entry<String, DomainObjectProperty> entry: propsMap.entrySet()) {
			if (entry.getKey().equals(prop.getName())) {
				newPropsMap.put(getNewElementName(), entry.getValue());
				changed = true;
			} else {
				newPropsMap.put(entry.getKey(), entry.getValue());
			}
		}

		if (changed) {
			domainObj.props = newPropsMap;
		}
		return changed;
	}


	/**
	 * @param elementToRefactor
	 * @param map
	 * @param key
	 * @return
	 */
	private boolean processRuleFunctions(Object elementToRefactor, Map<String, String> map, String key) {
		boolean changed = false;
		String fnURI = map.get(key);
		String newUri = "";
		if (elementToRefactor instanceof com.tibco.cep.studio.core.index.model.RuleElement || isFolderRefactor()) {
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(fnURI, folder)) {
					newUri = getNewPath(fnURI, folder);
					map.put(key, newUri);
					changed = true;
				}
			} else {
				com.tibco.cep.studio.core.index.model.RuleElement relement = (com.tibco.cep.studio.core.index.model.RuleElement) elementToRefactor;
				if (fnURI.equals(relement.getFolder()+relement.getName())) {
					if (isRenameRefactor()) {
						newUri = relement.getFolder() + getNewElementName();
						map.put(key, newUri);
					} else if (isMoveRefactor()) {
						newUri = getNewElementPath() + relement.getName();
						map.put(key, newUri);
					}
					changed = true;
				}
			}
		}
		return changed;
	}

	/**
	 * @param elementPath
	 * @param folder
	 * @return
	 */
	protected String getNewPath(String elementPath, IFolder folder) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toString();
				return initChar + parentPath + "/" + getNewElementName() + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + getNewElementName() + elementPath.substring(oldPath.length()+offset);
		}
		return elementPath;
	}

	/**
	 * @param file
	 * @param modelmgr
	 * @return
	 * @throws CoreException
	 */
	protected Change createTextFileChange(IFile file, ClusterConfigModelMgr modelmgr) throws CoreException {
		TextFileChange change = null;
		InputStream fis = null;
		try {
			String clusterConfigText = modelmgr.saveModel(true);
			String contents = new String(clusterConfigText.getBytes(file.getCharset()));
			change = new TextFileChange("New name change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return change;
	}
}
