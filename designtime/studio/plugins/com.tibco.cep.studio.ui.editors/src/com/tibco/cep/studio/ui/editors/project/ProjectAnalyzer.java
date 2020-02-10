/**
 * 
 */
package com.tibco.cep.studio.ui.editors.project;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_LOCAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.RuleElementResolutionContext;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.MethodArgumentASTVisitor;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;


/**
 * @author ggrigore
 * @author aathalye
 *
 */
public class ProjectAnalyzer implements IResolutionContextProvider {
	
	private static final String PROJECT_ANALYZER_MARKER = StudioUIPlugin.PLUGIN_ID + ".projectAnalyzeProblem";
	private IProject project;
	private IProgressMonitor monitor;
	private RuleElement fRuleElement;
	private LinkedList<Event> fUnusedEvents; // cache the result, rather than calculating multiple times
	private HashMap<IFile, RulesASTNode> fCachedASTNodes; // cache AST nodes, so we don't reparse multiple times
	
	public ProjectAnalyzer(IProject project, IProgressMonitor monitor) {
		this.project = project;
		this.monitor = monitor;
	}

	public boolean analyzeProject(List<String> projectAnalyzerWarningsList) {
		if (monitor == null) {
			this.monitor = new NullProgressMonitor();
		}
		fUnusedEvents = null; // clear cache
		fCachedASTNodes = null;
		Date start = new Date();
		monitor.beginTask("Analyzing project", 8);
		this.clearMarkers();
		if (monitor.isCanceled()) {
			return true;
		}
		if (this.reportUnusedEvents(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		if (this.reportUnusedDestinations(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		if (this.reportUnusedDomainModels(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		if (this.reportUnusedStateMachines(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		if (this.reportUnimplementedVRFs(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		if (this.reportUnusedRules(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		if (this.reportUnusedRuleFunctions(monitor, projectAnalyzerWarningsList)) {
			return true;
		}
		monitor.done();
		Date end = new Date();
		System.out.println("Analysis took "+(end.getTime() - start.getTime()) + " seconds.");
		return false;
	}

	private void createMarker(Entity entity, String messageKey, Object...arguments) {
		IFile resource = IndexUtils.getFile(project.getName(), entity);
		this.createMarker(resource, messageKey, arguments);
	}
	
	private void createMarker(IFile entityFile, String messageKey, Object...arguments) {
		String message = Messages.getString(messageKey, arguments);
		try {
			IMarker marker = entityFile.createMarker(PROJECT_ANALYZER_MARKER);
			Map<String, Object> attributes = new HashMap<String, Object>(2);
			attributes.put(IMarker.MESSAGE, message);
			attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			marker.setAttributes(attributes);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public void clearMarkers() {
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.subTask("Clearing project markers");
		try {
			project.deleteMarkers(PROJECT_ANALYZER_MARKER, true, IFile.DEPTH_INFINITE);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		subMonitor.done();
	}
	
	public boolean reportUnusedEvents(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		List<Event> unusedEvents = this.getUnusedEvents(monitor, projectAnalyzerWarningsList);
		for (Event event : unusedEvents) {
			createMarker(event,"Project.Analyze.NoDestinationEvent.message", event.getFullPath());
			projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.NoDestinationEvent.message", event.getFullPath()));
		}
		return false;
	}
	
	public List<Event> getUnusedEvents(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (fUnusedEvents == null) {
			fUnusedEvents = new LinkedList<Event>();
		} else {
			return fUnusedEvents;
		}
		monitor.setTaskName("Finding unused events");
		
		List<Event> allEvents = IndexUtils.getAllEvents(project.getName());
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused events", 2);
		
		
		String projectName = this.project.getName();
		List<EObject> potentialUnusedEvents = new ArrayList<EObject>();
		for (Event event : allEvents) {
			if (monitor.isCanceled()) {
				return allEvents;
			}
			subMonitor.subTask("Analyzing "+event.getName());
			if (event instanceof SimpleEvent) {
				SimpleEvent simpleEvent = (SimpleEvent)event;
				if (simpleEvent.getChannelURI() == null) {
					potentialUnusedEvents.add(simpleEvent);
				}
			}
		}
		monitor.worked(1);
		if (potentialUnusedEvents.size() > 0) {
			String[] targetMethodNames = new String[] { "Event.assertEvent", "Event.routeTo", "Event.sendEvent", "Event.replyEvent", "Event.createEvent" };
			// this code will loop over *all* rules/rule functions, rather than searching for each method call.  
			// Tests show this is much slower, and gives the same result, though it is cleaner code...
			//			StudioModelManager mgr = StudioCorePlugin.getDesignerModelManager();
			//			DesignerProject studioProject = mgr.getIndex(this.project);
			//			Iterator<RuleElement> iter = studioProject.getRuleElements().iterator();
			//			ruleSize = studioProject.getRuleElements().size();
			//			// try to find any potentially unused events in rules/rule functions (not just routeTo calls)
			//			while (iter.hasNext()) {
			//				if (potentialUnusedEvents.size() == 0) {
			//					// no longer need to search for any unused events -- we've found them all
			//					break;
			//				}
			//				RuleElement obj = iter.next();
			//				IFile ruleFile = IndexUtils.getFile(projectName, obj);

			if (processPotentialUnusedEntities(projectName, potentialUnusedEvents,
					targetMethodNames, subMonitor)) {
				return fUnusedEvents;
			}

			for (EObject entity : potentialUnusedEvents) {
				if (!fUnusedEvents.contains(entity)) {
					fUnusedEvents.add((Event) entity);
				}
			}
		}
		System.out.println("number of unused events: "+fUnusedEvents.size());
		subMonitor.done();

		return fUnusedEvents;
	}

	private boolean processPotentialUnusedEntities(String projectName,
			List<EObject> potentialUnusedEntities, String[] targetMethodNames,
			SubProgressMonitor subMonitor) {
		SearchResult sResult = new SearchResult();
		FunctionsCatalogLookup lookup = new FunctionsCatalogLookup(projectName);
		for (String methodName : targetMethodNames) {
			FunctionRec function = lookup.lookupFunction(methodName, null);
			if (function != null) {
				sResult.merge(SearchUtils.searchIndex(function.function, projectName, methodName.substring(methodName.lastIndexOf('.')+1)));
			}
		}
		SubProgressMonitor subProcMonitor = new SubProgressMonitor(subMonitor, 1);
		subProcMonitor.beginTask("Searching rules for references", sResult.getExactMatches().size());
		List<Object> processedFiles = new ArrayList<Object>();
		Iterator<EObject> iter = sResult.getExactMatches().iterator();
		while (iter.hasNext()) {
			if (monitor.isCanceled()) {
				return true;
			}
			Object obj = iter.next();
			if (obj instanceof ElementReference) {
				if (((ElementReference) obj).isMethod()) {
					EObject rootContainer = ElementReferenceResolver.getRootOfScope((ElementReference)obj);
					if (rootContainer instanceof TypeElement) {
						IFile ruleFile = IndexUtils.getFile(projectName, (TypeElement) rootContainer);
						if (ruleFile == null || processedFiles.contains(ruleFile)) {
							subProcMonitor.worked(1);
							continue;
						}
						subProcMonitor.subTask("Searching "+ruleFile.getName());
						processedFiles.add(ruleFile);
						
						RulesASTNode tree = getCachedAST(ruleFile);
						if (tree == null) {
							continue;
						}
						MethodArgumentASTVisitor visitor = new MethodArgumentASTVisitor(project.getName(), this, potentialUnusedEntities, targetMethodNames);
						tree.accept(visitor);
						List<EObject> foundEntities = visitor.getFoundEntities();
						for (EObject entity : foundEntities) {
							potentialUnusedEntities.remove(entity);
						}
						subProcMonitor.worked(1);
					}
				}
			}
		}
		subProcMonitor.done();
		return false;
	}
	
	private RulesASTNode getCachedAST(IFile ruleFile) {
		if (fCachedASTNodes == null) {
			fCachedASTNodes = new HashMap<IFile, RulesASTNode>();
		}
		RulesASTNode node = fCachedASTNodes.get(ruleFile);
		if (node == null) {
			node = (RulesASTNode) RulesParserManager.parseRuleFile(ruleFile);
			fCachedASTNodes.put(ruleFile, node);
		}
		return node;
	}

	public boolean reportUnusedDestinations(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unused destinations");
		
		List<Entity> allChannels = 
			IndexUtils.getAllEntities(project.getName(), ELEMENT_TYPES.CHANNEL);
		
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused destinations", allChannels.size());

		List<EObject> potentialUnusedDestinations = new ArrayList<EObject>();
		for (Entity entity : allChannels) {
			if (monitor.isCanceled()) {
				return true;
			}
			Channel channel = (Channel)entity;
			if (DRIVER_LOCAL.equals(channel.getDriver().getDriverType().getName())) {
				subMonitor.subTask("Analyzing "+channel.getName());
				//Get its destinations
				List<Destination> destinations = channel.getDriver().getDestinations();
				for (Destination destination : destinations) {
					String eventURI = destination.getEventURI();
					if (eventURI == null || eventURI.length() == 0) {
						potentialUnusedDestinations.add(destination);
					}
				}
			}
			subMonitor.worked(1);
		}
		
		if (potentialUnusedDestinations.size() > 0) {
			// Event.routeTo() can also "use" an apparently unused destination
			String[] targetMethodNames = new String[] { "Event.routeTo" };
			processPotentialUnusedEntities(project.getName(), potentialUnusedDestinations,
					targetMethodNames, subMonitor);
		}
		
		for (EObject dest : potentialUnusedDestinations) {
			Destination destination = (Destination) dest;
			// Report potentially unused destination
			createMarker(((Destination)destination).getDriverConfig().getChannel(),"Project.Analyze.NoDefaultEventDestination.message", 
					destination.getName(), 
					((Destination)destination).getDriverConfig().getChannel().getFullPath());
			projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.NoDefaultEventDestination.message", 
					destination.getName(), 
					((Destination)destination).getDriverConfig().getChannel().getFullPath()));
		}
		subMonitor.done();
		return false;
	}

	public boolean reportUnusedDomainModels(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unused domains");
		
		List<Entity> allDomains = 
			IndexUtils.getAllEntities(project.getName(), ELEMENT_TYPES.DOMAIN);
		
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused domains", allDomains.size());
		
		for (Entity entity : allDomains) {
			if (monitor.isCanceled()) {
				return true;
			}
			Domain domain = (Domain)entity;
			subMonitor.subTask("Analyzing "+domain.getName());
			List<DomainInstance> domainInstances = domain.getInstances();
			if (domainInstances.isEmpty()) {
				//Report it
				//Create marker
				createMarker(domain, "Project.Analyze.UnusedDomain.message", domain.getFullPath());
				projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.UnusedDomain.message", domain.getFullPath()));
			}
			subMonitor.worked(1);
		}
		subMonitor.done();
		return false;
	}
	
	public boolean reportUnusedStateMachines(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unused state models");
		
		List<Entity> allStateMachines = 
			IndexUtils.getAllEntities(project.getName(), ELEMENT_TYPES.STATE_MACHINE);
		
		
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused state models", allStateMachines.size());
		
		for (Entity entity : allStateMachines) {
			if (monitor.isCanceled()) {
				return true;
			}
			StateMachine stateMachine = (StateMachine)entity;
			subMonitor.subTask("Analyzing "+stateMachine.getName());
			if (stateMachine.getOwnerConcept() == null) {
				//Report unused SM
				createMarker(stateMachine, "Project.Analyze.UnusedStateMachine.message", 
					stateMachine.getFullPath());
				projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.UnusedStateMachine.message", 
						stateMachine.getFullPath()));
			}
			subMonitor.worked(1);
		}
		subMonitor.done();
		return false;
	}

	public boolean reportUnimplementedVRFs(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unimplemented VRFs");
		
		List<DesignerElement> allRFs = 
			IndexUtils.getAllElements(project.getName(), ELEMENT_TYPES.RULE_FUNCTION);
		
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unimplemented VRFs", allRFs.size());
		
		outer:
		for (DesignerElement ruleElement : allRFs) {
			if (monitor.isCanceled()) {
				return true;
			}
			RuleFunction rf = (RuleFunction)((RuleElement)ruleElement).getRule();
			subMonitor.subTask("Analyzing "+rf.getName());
			if (rf.isVirtual()) {
				IFile vrfFile = IndexUtils.getFile(project.getName(), rf);
				if (vrfFile == null) {
					continue; // possibly from project library, skip it
				}
				String vrfFullPath = rf.getFullPath();
				DesignerProject index = 
					StudioCorePlugin.getDesignerModelManager().getIndex(project);
				ElementContainer folder = 
					IndexUtils.getFolderForFile(index, vrfFile);
				List<DesignerElement> childEntries = folder.getEntries();
				for (DesignerElement designerElement : childEntries) {
					if (monitor.isCanceled()) {
						return true;
					}
					if (designerElement instanceof DecisionTableElement) {
						DecisionTableElement decisionTableElement = (DecisionTableElement)designerElement;
						Implementation implementation = 
							(Implementation)decisionTableElement.getImplementation();
						// Get implemented resource path
						String vrfPath = implementation.getImplements();
						if (vrfFullPath.equals(vrfPath)) {
							continue outer;
						}
					}
				}
				// No match was found. Report it.
				createMarker(rf, "Project.Analyze.UnimplementedVRF.message", vrfFullPath);
				projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.UnimplementedVRF.message", vrfFullPath));
			}
			subMonitor.worked(1);
		}
		subMonitor.done();
		return false;
	}
	
	public boolean reportUnusedRules(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unused rules");

		// first get all unused events
		List<Event> unusedEvents = this.getUnusedEvents(monitor, projectAnalyzerWarningsList);
		// then get Studio project so we can get all rule elements
		StudioModelManager mgr = StudioCorePlugin.getDesignerModelManager();
		DesignerProject studioProject = mgr.getIndex(this.project);
		Iterator<RuleElement> iter = studioProject.getRuleElements().iterator();
		String projectName = this.project.getName();

		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused rules", IProgressMonitor.UNKNOWN);
		
		while (iter.hasNext()) {
			if (monitor.isCanceled()) {
				return true;
			}
			Compilable rule = iter.next().getRule();
			subMonitor.subTask("Analyzing "+rule.getName());
			if (rule instanceof Rule) {
				boolean foundUnusedEvent = false;
				boolean foundUsedEntity = false;
				for (Symbol symbol : rule.getSymbols().getSymbolList()) {
					Entity entity = null;
					DesignerElement element =
						IndexUtils.getElement(projectName, symbol.getType());
					if (element instanceof EntityElement) {
						entity = ((EntityElement) element).getEntity();
						if (entity instanceof Event) {
							if (unusedEvents.contains(entity)) {
								foundUnusedEvent = true;
								break;
							} else {
								foundUsedEntity = true;
							}
						} else if (entity != null) {
							foundUsedEntity = true; // should be a concept
						} 
					} else {
						System.out.println("Could not resolve symbol "+symbol.getType());
					}
				}
				if (foundUnusedEvent || !foundUsedEntity) {
					this.createMarker(rule, "Project.Analyze.UnusedRule.message", rule.getFullPath());
					projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.UnusedRule.message", rule.getFullPath()));
				}
			}
			subMonitor.worked(1);
		}
		subMonitor.done();
		return false;
	}

	public boolean reportUnusedRuleFunctions(IProgressMonitor monitor, List<String> projectAnalyzerWarningsList) {
		if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unused rule functions");

		Date start = new Date();
		List<DesignerElement> allRFs = 
			IndexUtils.getAllElements(project.getName(), ELEMENT_TYPES.RULE_FUNCTION);
		
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused rule functions", allRFs.size());
		
		RuleFunction rf;
		String projectName = this.project.getName();
		SearchResult sResult;
		boolean found = false;
		for (DesignerElement ruleElement : allRFs) {
			if (monitor.isCanceled()) {
				return true;
			}
			rf = (RuleFunction)((RuleElement)ruleElement).getRule();
			subMonitor.subTask("Analyzing "+rf.getName());
			sResult = SearchUtils.searchIndex(ruleElement, projectName, rf.getName(), false);
			Iterator<EObject> iter = sResult.getExactMatches().iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof ElementReference) {
					if (((ElementReference) obj).isMethod()) {
						found = true;
						break;
					}
				} else if (obj instanceof ElementMatch) {
					EObject matchedElement = ((ElementMatch) obj).getMatchedElement();
					if (matchedElement instanceof NonEntityMatch) {
						Object path = ((NonEntityMatch) matchedElement).getMatch();
						if (rf.getFullPath().equals(path)) {
							found = true;
							break;
						}
					}
				}
			}
			
			if (found == false) {
				createMarker(rf, "Project.Analyze.UnusedRF.message", rf.getFullPath());
				projectAnalyzerWarningsList.add(Messages.getString("Project.Analyze.UnusedRF.message", rf.getFullPath()));
			}
			else {
				found = false;
			}
			subMonitor.worked(1);
		}
		subMonitor.done();
		Date end = new Date();
		System.out.println("unused RFs took "+(end.getTime() - start.getTime()));
		return false;
	}
	
	
	public boolean reportUnusedProcesses(IProgressMonitor monitor) {
        if (monitor.isCanceled()) {
			return true;
		}
		monitor.setTaskName("Finding unused processes");

		Date start = new Date();
		List<EObject> allProcesses = new LinkedList();
		
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Finding unused processes", allProcesses.size());

		boolean found = false;
		for (EObject processObj : allProcesses) {
			if (monitor.isCanceled()) {
				return true;
			}
			
			if (found == false) {
				IFile processFile = null;
				this.createMarker(processFile,
					"Project.Analyze.UnusedProcess.message",
					"/processes/myProcess.beprocess");
				
			}
			else {
				found = false;
			}
			subMonitor.worked(1);
		}
		subMonitor.done();
		Date end = new Date();
		System.out.println("unused processes took "+(end.getTime() - start.getTime()));
        return found;
	}	
	

	@Override
	public IResolutionContext getResolutionContext(
			ElementReference elementReference) {
		if (true) {
			return ElementReferenceResolver.createResolutionContext((ScopeBlock) elementReference.eContainer());
		}
		ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
		SimpleResolutionContext context = new SimpleResolutionContext(scope);
		List<GlobalVariableDef> globalDefs = fRuleElement.getGlobalVariables();
		for (GlobalVariableDef globalVariableDef : globalDefs) {
			context.addGlobalVariable(globalVariableDef);
		}
		return context;
//		if (fRuleElement != null) {
//			return new RuleElementResolutionContext(fRuleElement.getScope(), fRuleElement);
//		}
//		return null;
	}

	@Override
	public IResolutionContext getResolutionContext(ElementReference reference,
			ScopeBlock scope) {
		if (true) {
			return ElementReferenceResolver.createResolutionContext(scope);
		}
		if (fRuleElement != null) {
			return new RuleElementResolutionContext(scope, fRuleElement);
		}
		return null;
	}

}
