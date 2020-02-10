package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;

public class ClusterConfigSearchParticipant implements ISearchParticipant {

	public static final String CDD_EXTENSION  = "cdd";

	class ResourceCollector implements IResourceVisitor {

		private List<IFile> fCddFiles = new ArrayList<IFile>();
		
		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (!(resource instanceof IFile)) {
				return true;
			}
			IFile file = (IFile) resource;
			if (CDD_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
				fCddFiles.add(file);
			}
			return false;
		}

		public List<IFile> getCddFiles() {
			return fCddFiles;
		}
		
	}
	
	@Override
	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		SearchResult result = new SearchResult();
		if (project == null || !project.isAccessible()) {
			return result;
		}
		ResourceCollector collector = new ResourceCollector();
		try {
			project.accept(collector);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		List<IFile> cddFiles = collector.getCddFiles();
		for (IFile file : cddFiles) {
			processCddFile(file, resolvedElement, projectName, nameToFind, monitor, result);
		}
		
		return result;
	}

	private void processCddFile(IFile file, Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		
		ClusterConfigModelMgr modelmgr = new ClusterConfigModelMgr(file);
		if (modelmgr == null) {
			return;
		}
		modelmgr.parseModel();

		ArrayList<AgentClass> agents = modelmgr.getAgentClasses();
		for (AgentClass agent: agents) {
			if (agent instanceof DashInfProcQueryAgent) {
				String fullPath = null;
				if (resolvedElement instanceof RuleElement) {
					fullPath = ((RuleElement) resolvedElement).getFolder()+((RuleElement) resolvedElement).getName();
				}
				if (fullPath == null) {
					continue;
				}
				DashInfProcQueryAgent fAgent = (DashInfProcQueryAgent) agent;

				searchAgentFunctions(file, projectName, result, modelmgr,
						agent, fullPath, fAgent);

				searchFunctionsGroup(file, projectName, result, modelmgr,
						fullPath);
				
				searchRulesGroup(file, projectName, result, modelmgr, fullPath);

				for (DestinationElement destinationElement: fAgent.agentDestinationsGrpObj.agentDestinations) {
					if (destinationElement instanceof Destination) {
						Destination destination = (Destination) destinationElement;
						Map<String, String> destinationval = destination.destinationVal;
						Set<String> keySet = destinationval.keySet();
						for (String key : keySet) {
							String val = destinationval.get(key);
							if (val.equals(fullPath)) {
								createAndAddMatch(file, projectName, "'"+key+"' in Destination Group '"+destination.id+"', agent '"+agent.name+"'", fullPath, result);
							}
						}
					}
				}
			}
		}
		
	}

	private void searchAgentFunctions(IFile file, String projectName,
			SearchResult result, ClusterConfigModelMgr modelmgr, AgentClass agent,
			String fullPath, DashInfProcQueryAgent fAgent) {
		List<String> startupFuncNames = modelmgr.getAgentFunctionNames(fAgent.agentStartupFunctionsGrpObj, true);
		for (String fnName : startupFuncNames) {
			if (fnName.equals(fullPath)) {
				createAndAddMatch(file, projectName, "Startup function for agent '"+agent.name+"'", fullPath, result);
			}
		}
		List<String> shutdownFuncNames = modelmgr.getAgentFunctionNames(fAgent.agentShutdownFunctionsGrpObj, true);
		for (String fnName : shutdownFuncNames) {
			if (fnName.equals(fullPath)) {
				createAndAddMatch(file, projectName, "Shutdown function for agent '"+agent.name+"'", fullPath, result);
			}
		}
	}

	private void searchFunctionsGroup(IFile file, String projectName,
			SearchResult result, ClusterConfigModelMgr modelmgr, String fullPath) {
		ArrayList<FunctionElement> functionsGroup = modelmgr.getFunctionsGroup(false);
		for (FunctionElement functionElement : functionsGroup) {
			if (functionElement instanceof FunctionsGrp) {
				ArrayList<FunctionElement> functions = ((FunctionsGrp) functionElement).functions;
				for (FunctionElement func : functions) {
					if (func instanceof ClusterConfigModel.Function) {
						String ruleURI = ((ClusterConfigModel.Function) func).uri;
						if (fullPath.equals(ruleURI)) {
							createAndAddMatch(file, projectName, "Rule in Functions group '"+((FunctionsGrp) functionElement).id+"'", fullPath, result);
						}
					}
				}
			}
		}
	}

	private void searchRulesGroup(IFile file, String projectName,
			SearchResult result, ClusterConfigModelMgr modelmgr, String fullPath) {
		ArrayList<ClusterConfigModel.RuleElement> rulesGrpList = modelmgr.getRulesGroup();
		for(ClusterConfigModel.RuleElement ruleElement:rulesGrpList){
			if (!(ruleElement instanceof RulesGrp))
				continue;
			RulesGrp rulesGrp = (RulesGrp)ruleElement;
			ArrayList<com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement> rules = rulesGrp.rules;
			for (ClusterConfigModel.RuleElement element : rules) {
				if (!(element instanceof Rule)) {
					continue;
				}
				Rule rule = (Rule)element;
				String ruleURI = rule.uri;
				if (fullPath.equals(ruleURI)) {
					createAndAddMatch(file, projectName, "Rule in Rules group '"+rulesGrp.id+"'", fullPath, result);
				}
			}
		}
	}

	private void createAndAddMatch(IFile file, String projectName,
			String label, String pathToEntity, SearchResult result) {
		NonEntityMatch match = SearchFactory.eINSTANCE.createNonEntityMatch();
		match.setProjectName(projectName);
		IPath path = file.getFullPath().removeFirstSegments(1);
		match.setFilePath(path.toString());
		match.setMatch(pathToEntity);
		ElementMatch elementMatch = SearchFactory.eINSTANCE.createElementMatch();
		elementMatch.setMatchedElement(match);
		elementMatch.setLabel(label);
		result.addExactMatch(elementMatch);
	}

}
