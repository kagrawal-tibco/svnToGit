package com.tibco.cep.studio.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;

/**
 * 
 * @author sasahoo
 *
 */
public class ProjectDiagramTools {

	private Action conceptFilterAction;
	private Action metricFilterAction;
	private Action eventFilterAction;
	private Action channelFilterAction;
	private Action processFilterAction;
	private Action stateMachineFilterAction;
	private Action rulesFilterAction;
	private Action ruleFunctionFilterAction;
	private Action scorecardFilterAction;
	private Action dtFilterAction;
	private Action archiveFilterAction;
	private Action scopelinksFilterAction;
	private Action usagelinksFilterAction;
	private Action processlinksFilterAction;
	private Action expandprocessFilterAction;
	private Action tooltipsFilterAction;
	private Action rulesInFoldersFilterAction;
	private Action groupConceptFilterAction;
	private Action groupEventsFilterAction;
	private Action groupRulesFilterAction;
	private Action groupRuleFunctionsFilterAction;
	private Action domainModelAction;
	private ProjectDiagramManager projectDiagramManager;
	private IWorkbenchPage page;

	/**
	 * @param page
	 */
	public ProjectDiagramTools(IWorkbenchPage page){
		this.page = page;
	}

	/**
	 * 
	 * @param projectDiagramManager
	 * @return
	 */
	public List<Action> getToolItems(){
		List<Action> actions =  new ArrayList<Action>(); 
		//Concept
		conceptFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_CONCEPTS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				
				try{
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(conceptFilterAction.isChecked()){
						projectDiagramManager.setShowConcepts(true);
					}else{
						projectDiagramManager.setShowConcepts(false);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		conceptFilterAction.setChecked(true);
		conceptFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_CONCEPTS_TOOLTIP"));
		conceptFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.CONCEPT_PALETTE_CONCEPT));
		actions.add(conceptFilterAction);

		//Metric
		
		metricFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_METRICS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				
				try{
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(metricFilterAction.isChecked()){
						projectDiagramManager.setShowConcepts(true);
					}else{
						projectDiagramManager.setShowConcepts(false);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		metricFilterAction.setChecked(true);
		metricFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_METRICS_TOOLTIP"));
		metricFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.METRIC));
		if(AddonUtil.isViewsAddonInstalled()){
		actions.add(metricFilterAction);
		}

		//Event
		eventFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_EVENTS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(eventFilterAction.isChecked()){
						projectDiagramManager.setShowEvents(true);
					}else{
						projectDiagramManager.setShowEvents(false);
					}
			}
		};
		eventFilterAction.setChecked(true);
		eventFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_EVENTS_TOOLTIP"));
		eventFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.EVENT));
		actions.add(eventFilterAction);
		
		//State Machines
		stateMachineFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_STATEMACHIES_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(stateMachineFilterAction.isChecked()){
						projectDiagramManager.setShowStateMachines(true);
					}else{
						projectDiagramManager.setShowStateMachines(false);
					}
			}
		};
		stateMachineFilterAction.setChecked(true);
		stateMachineFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_STATEMACHIES_TOOLTIP"));
		stateMachineFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_STATEMACHINE));
		actions.add(stateMachineFilterAction);

		//Channel
		channelFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_CHANNELS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(channelFilterAction.isChecked()){
						projectDiagramManager.setShowChannels(true);
					}else{
						projectDiagramManager.setShowChannels(false);
					}
			}
		};
		channelFilterAction.setChecked(true);
		channelFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_CHANNELS_TOOLTIP"));
		channelFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.CHANNEL));
		actions.add(channelFilterAction);
		
		// Process
		processFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_PROCESSES_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(processFilterAction.isChecked()){
						projectDiagramManager.setShowProcesses(true);
					}else{
						projectDiagramManager.setShowProcesses(false);
					}
			}
		};
		processFilterAction.setChecked(true);
		processFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_PROCESSES_TOOLTIP"));
		processFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PROCESS));
		actions.add(processFilterAction);
		
		//Rules
		rulesFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_RULES_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(rulesFilterAction.isChecked()){
						projectDiagramManager.setShowRules(true);
					}else{
						projectDiagramManager.setShowRules(false);
					}
			}
		};
		rulesFilterAction.setChecked(true);
		rulesFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_RULES_TOOLTIP"));
		rulesFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_RULE));
		actions.add(rulesFilterAction);

		//Rule functions
		ruleFunctionFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_RULES_FUNCTIONS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(ruleFunctionFilterAction.isChecked()){
						projectDiagramManager.setShowRuleFunctions(true);
					}else{
						projectDiagramManager.setShowRuleFunctions(false);
					}
			}
		};
		ruleFunctionFilterAction.setChecked(true);
		ruleFunctionFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_RULES_FUNCTIONS_TOOLTIP"));
		ruleFunctionFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_RULE_FUNCTION));	
		actions.add(ruleFunctionFilterAction);

		//Scorecard
		scorecardFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_SCORECARDS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(scorecardFilterAction.isChecked()){
						projectDiagramManager.setShowScoreCards(true);
					}else{
						projectDiagramManager.setShowScoreCards(false);
					}
			}
		};
		scorecardFilterAction.setChecked(true);
		scorecardFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_SCORECARDS_TOOLTIP"));
		scorecardFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.SCORECARD));	
		actions.add(scorecardFilterAction);

		//Decision Table
		dtFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_DECISIONTABLES_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(dtFilterAction.isChecked()){
						projectDiagramManager.setShowDecisionTables(true);
					}else{
						projectDiagramManager.setShowDecisionTables(false);
					}
			}
		};
		dtFilterAction.setChecked(true);
		dtFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_DECISIONTABLES_TOOLTIP"));
		dtFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_DECISIONTABLE));	
		actions.add(dtFilterAction);

		//Domain Model 
		domainModelAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_DOMAIN_MODEL_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(domainModelAction.isChecked()){
						projectDiagramManager.setShowDomainModels(true);
					}else{
						projectDiagramManager.setShowDomainModels(false);
					}
			}
		};
		domainModelAction.setChecked(true);
		domainModelAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_DOMAIN_MODEL_TITLE"));
		domainModelAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_DOMAINMODEL));
		actions.add(domainModelAction);
		
		//Archive
		archiveFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_ARCHIVES_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(archiveFilterAction.isChecked()){
						projectDiagramManager.setShowArchives(true);
					}else{
						projectDiagramManager.setShowArchives(false);
					}
			}
		};
		archiveFilterAction.setChecked(true);
		archiveFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_ARCHIVES_TOOLTIP"));
		archiveFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_ARCHIVE));	
		actions.add(archiveFilterAction);

		//Scope Links
		scopelinksFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_SCOPE_LINKS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(scopelinksFilterAction.isChecked()){
						projectDiagramManager.setShowScopeLinks(true);
					}else{
						projectDiagramManager.setShowScopeLinks(false);
					}
			}
		};
		scopelinksFilterAction.setChecked(true);
		scopelinksFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_SCOPE_LINKS_TOOLTIP"));
		scopelinksFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_LINK));	
		actions.add(scopelinksFilterAction);

		//Usage Links
		usagelinksFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_USAGE_LINKS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(usagelinksFilterAction.isChecked()){
						projectDiagramManager.setShowUsageLinks(true);
					}else{
						projectDiagramManager.setShowUsageLinks(false);
					}
			}
		};
		usagelinksFilterAction.setChecked(true);
		usagelinksFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_USAGE_LINKS_TOOLTIP"));
		usagelinksFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_LINK));	
		actions.add(usagelinksFilterAction);

		// Process Links
		processlinksFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_PROCESS_LINKS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(processlinksFilterAction.isChecked()){
						projectDiagramManager.setShowProcessLinks(true);
					}else{
						projectDiagramManager.setShowProcessLinks(false);
					}
			}
		};
		processlinksFilterAction.setChecked(true);
		processlinksFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_PROCESS_LINKS_TOOLTIP"));
		processlinksFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_LINK));	
		actions.add(processlinksFilterAction);

		// Expand Processes
		expandprocessFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_PROCESS_EXPANDED_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(expandprocessFilterAction.isChecked()){
						projectDiagramManager.setShowProcessExpanded(true);
					}else{
						projectDiagramManager.setShowProcessExpanded(false);
					}
			}
		};
		expandprocessFilterAction.setChecked(true);
		expandprocessFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_PROCESS_EXPANDED_TOOLTIP"));
		expandprocessFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PROCESS));	
		actions.add(expandprocessFilterAction);
		
		//Tooltips
		tooltipsFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_TOOLTIPS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(tooltipsFilterAction.isChecked()){
						projectDiagramManager.setShowTooltips(true);
					}else{
						projectDiagramManager.setShowTooltips(false);
					}
			}
		};
		tooltipsFilterAction.setChecked(true);
		tooltipsFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_TOOLTIPS_TOOLTIP"));
		tooltipsFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_TOOLTIP));	
		actions.add(tooltipsFilterAction);


		//Rules in folders
		rulesInFoldersFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_RULES_FOLDERS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				//TODO
			}
		};
		rulesInFoldersFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_SHOW_RULES_FOLDERS_TOOLTIP"));
		rulesInFoldersFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_NODES));	
		actions.add(rulesInFoldersFilterAction);


		//Group Concepts
		groupConceptFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_CONCEPTS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				projectDiagramManager = getProjectDiagramManager(page);
				if(projectDiagramManager!=null)
					if(groupConceptFilterAction.isChecked()){
						projectDiagramManager.setGroupConceptsInSameArea(true);
					}else{
						projectDiagramManager.setGroupConceptsInSameArea(false);
					}
			}
		};
		groupConceptFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_CONCEPTS_TOOLTIP"));
		groupConceptFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_NODES));	
		actions.add(groupConceptFilterAction);

		//Group Concepts
		groupEventsFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_EVENTS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				//TODO
			}
		};
		groupEventsFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_EVENTS_TOOLTIP"));
		groupEventsFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_NODES));	
		actions.add(groupEventsFilterAction);

		//Group rules
		groupRulesFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_RULES_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				//TODO
			}
		};
		groupRulesFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_RULES_TOOLTIP"));
		groupRulesFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_NODES));	
		actions.add(groupRulesFilterAction);

		//Group rule functions
		groupRuleFunctionsFilterAction = new org.eclipse.jface.action.Action(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_RULE_FUNCTIONS_TITLE"), org.eclipse.jface.action.Action.AS_CHECK_BOX) {
			public void run() {
				//TODO
			}
		};
		groupRuleFunctionsFilterAction.setToolTipText(com.tibco.cep.diagramming.utils.Messages.getString("PALETTE_GROUP_RULE_FUNCTIONS_TOOLTIP"));
		groupRuleFunctionsFilterAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.PALETTE_NODES));	
		actions.add(groupRuleFunctionsFilterAction);

		return actions;
	}

	/**
	 * @return
	 */
	private ProjectDiagramManager getProjectDiagramManager(IWorkbenchPage page){
		DiagramManager diagramManager = DiagramUtils.getDiagramManager(page);
		if(diagramManager !=null && diagramManager instanceof ProjectDiagramManager){
			return (ProjectDiagramManager)diagramManager;
		}
		return null;
	}
}
