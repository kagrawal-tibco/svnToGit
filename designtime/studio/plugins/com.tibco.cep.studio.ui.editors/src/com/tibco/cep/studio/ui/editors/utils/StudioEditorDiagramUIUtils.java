package com.tibco.cep.studio.ui.editors.utils;

import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_CONCEPTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_EVENTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_RULES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_RULE_FUNCTIONS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_ARCHIVES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_CHANNELS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_CONCEPTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_DECISIONTABLES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_DOMAIN_MODEL_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_EVENTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_METRICS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_PROCESSES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_PROCESS_EXPANDED_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_PROCESS_LINKS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_RULES_FOLDERS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_RULES_FUNCTIONS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_RULES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_SCOPE_LINKS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_SCORECARDS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_STATEMACHIES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_TOOLTIPS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_USAGE_LINKS_TITLE;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditor;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditorInput;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author hnembhwa
 * 
 */
public class StudioEditorDiagramUIUtils {

	private static Object userObject;
	private static IProject project;
	
	
	@SuppressWarnings("rawtypes")
	public static void openFormEditor(IWorkbenchPage page, TSENode node, DiagramManager diagramManager){
		try {
			userObject = node.getUserObject();
			EntityDiagramManager entityDiagramManager = (EntityDiagramManager) diagramManager;
			project = entityDiagramManager.getProject();
			String sharedResourcePath = (String) node.getAttributeValue(ProjectDiagramManager.getSharedResourceNodeAtrribute());
			if(sharedResourcePath != null ) {
				IFile file = project.getFile(new Path(sharedResourcePath));
				if (file.exists()) {
					IDE.openEditor(page, file);
				}
			}
			if(userObject instanceof SharedElement){
				StudioUIUtils.openElement(userObject);
			} else if (userObject instanceof EntityNodeData) {
				EntityNodeData nodeData = (EntityNodeData) userObject;
				Entity entity = (Entity) nodeData.getEntity().getUserObject();
				StudioUIUtils.openEditor(entity);
			} else if (userObject instanceof Channel) {
				Channel channelData = (Channel) userObject;
				StudioUIUtils.openEditor(channelData);
			} else if (userObject instanceof Scorecard) {
				Scorecard scoreCardData = (Scorecard) userObject;
				StudioUIUtils.openEditor(scoreCardData);
			} else if (userObject instanceof StateMachine) {
				StateMachine stateMachine = (StateMachine) userObject;
				StudioUIUtils.openEditor(stateMachine);
			}
			else if (userObject instanceof Table) {
				Table table = (Table) userObject;
				IDE.openEditor(page, project.getFile(table.getFolder()+ table.getName()+ ".rulefunctionimpl"));
			}
			else if (userObject instanceof RuleFunction) {
				RuleFunction ruleFunction = (RuleFunction) userObject;
				StudioUIUtils.openEditor(ruleFunction);
			}
			else if (userObject instanceof Rule) {
				Rule rule = (Rule) userObject;
				StudioUIUtils.openEditor(rule);
			}
			else if (userObject instanceof Domain) {
				Domain domain = (Domain) userObject;
				StudioUIUtils.openEditor(domain);
			} 
			else if(userObject instanceof Destination){
				Destination destination = (Destination) userObject;
				if (destination.getDriverConfig().eContainer() instanceof Channel) {
					Channel channelData = ((Channel) (destination.getDriverConfig().eContainer()));
					StudioUIUtils.openEditor(channelData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void showDependencyDiagram(IWorkbenchPage page, DiagramManager diagramManager){
		try{
			TSEObject object = SelectToolHandler.tseObject;
			if(object instanceof TSENode){
				Object userObject = object.getUserObject();
				if(userObject!=null){
					project =  ((EntityDiagramManager) diagramManager).getProject();
					if(userObject instanceof EntityNodeData){
						EntityNodeData nodeData = (EntityNodeData)userObject;
						Entity entity = (Entity) nodeData.getEntity().getUserObject();
						IFile file = IndexUtils.getFile(entity.getOwnerProjectName(), entity);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);
					} else if (userObject instanceof Channel) {
						Channel channelData = (Channel) userObject;
						IFile file = IndexUtils.getFile(channelData.getOwnerProjectName(), channelData);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);	
					} else if (userObject instanceof Scorecard) {
						Scorecard scoreCardData = (Scorecard) userObject;
						IFile file = IndexUtils.getFile(scoreCardData.getOwnerProjectName(), scoreCardData);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);	
					} else if (userObject instanceof StateMachine) {
						StateMachine stateMachine = (StateMachine) userObject;
						IFile file = IndexUtils.getFile(stateMachine.getOwnerProjectName(), stateMachine);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);	
					} else if (userObject instanceof RuleFunction) {
						RuleFunction ruleFunction = (RuleFunction) userObject;
						IFile file = IndexUtils.getFile(ruleFunction.getOwnerProjectName(), ruleFunction);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);	
					} 
					else if (userObject instanceof Table) {
						Table table = (Table) userObject;
						IFile file=project.getFile(table.getFolder()+ table.getName()+ ".rulefunctionimpl");
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);	
					}else if (userObject instanceof Rule) {
						Rule rule = (Rule) userObject;
						IFile file = IndexUtils.getFile(rule.getOwnerProjectName(), rule);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);
					} else if (userObject instanceof Destination){
						Destination destination = (Destination)userObject;
						if(destination.getDriverConfig().eContainer() instanceof Channel){
							Channel channelData = ((Channel) (destination.getDriverConfig().eContainer()));
							IFile file = IndexUtils.getFile(channelData.getOwnerProjectName(), channelData);
							if(page ==  null){
								page = getPage(diagramManager);
							}
							createDependencyDiagram(page , file, project);
						}
					} else if(userObject instanceof Domain){
						Domain domain= (Domain)userObject;
						IFile file = IndexUtils.getFile(domain.getOwnerProjectName(), domain);
						if(page ==  null){
							page = getPage(diagramManager);
						}
						createDependencyDiagram(page , file, project);	
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPage()
	 */
	@SuppressWarnings("unchecked")
	public static IWorkbenchPage getPage(DiagramManager diagramManager) {
		return ((EntityDiagramManager<Entity>)diagramManager).getEditor().getSite().getPage();
	}
	
	/**
	 * @param page
	 * @param file
	 * @param project
	 */
	public static void createDependencyDiagram(final IWorkbenchPage page, IFile file, IProject project){
		final IEditorPart editor = getDependencyDiagramEditorOpen(page, IndexUtils.getFullPath(file));
		if(editor == null){
			DependencyDiagramEditorInput input = new DependencyDiagramEditorInput(file, project);
			openEditor(page, input, DependencyDiagramEditor.ID);
		}else{
			page.activate(editor);	
			} 
		}
	
	/**
	 * 
	 * @param page
	 * @param ID
	 * @param input
	 */
	public static void openEditor(final IWorkbenchPage page, final IEditorInput input,final String ID){
			try {
				page.openEditor(input, ID);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * @param page
	 * @param path
	 * @return
	 */
	public static IEditorPart getDependencyDiagramEditorOpen(IWorkbenchPage page,String path) {

		for (IEditorReference reference : page.getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof DependencyDiagramEditorInput) {
					DependencyDiagramEditorInput depDiagramEditorInput = (DependencyDiagramEditorInput)reference.getEditorInput();
					if (IndexUtils.getFullPath(depDiagramEditorInput.getFile()).equals(path)) {
						return reference.getEditor(true);
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * @param projectDiagramManager
	 * @param projectDiagramMap
	 * @param paletteMap
	 * @param entryMap
	 */
	public static void doDefault(final ProjectDiagramManager projectDiagramManager,
			                     final Map<String, Boolean> projectDiagramMap, 
			                     final Map<String, Boolean> defaultMap, 
			                     final Map<Button, Boolean> entryMap){
		try{
			projectDiagramMap.clear();
			projectDiagramMap.putAll(defaultMap);
			//On Default button checks are restored
			for(Button entry:entryMap.keySet()){
				entry.setEnabled(true);
				entry.setSelection(defaultMap.get(entry.getText().intern()));
			}
			doApply(projectDiagramManager, defaultMap);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param projectDiagramManager
	 * @param projectDiagramMap
	 * @param paletteMap
	 */
	public static void doApply(final ProjectDiagramManager projectDiagramManager,
			                   final Map<String, Boolean> projectDiagramMap, 
			                   final Map<String, Boolean> paletteMap){
		try{
			projectDiagramMap.clear();
			projectDiagramMap.putAll(paletteMap);
			doApply(projectDiagramManager, paletteMap);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param projectDiagramManager
	 * @param paletteMap
	 */
	public static void doApply(final ProjectDiagramManager projectDiagramManager, 
								final Map<String, Boolean> paletteMap){

		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				setFiltersInProjectPalette(projectDiagramManager, paletteMap);
			}
		}, false);
	}
	
	/**
	 * @param projectDiagramManager
	 * @param paletteMap
	 */
	public static void setFiltersInProjectPalette(final ProjectDiagramManager projectDiagramManager, 
			final Map<String, Boolean> paletteMap) {
		try{
		projectDiagramManager.setGroupConceptsInSameArea(paletteMap.get(PALETTE_GROUP_CONCEPTS_TITLE));
		projectDiagramManager.setShowConcepts(paletteMap.get(PALETTE_SHOW_CONCEPTS_TITLE));
		if(AddonUtil.isViewsAddonInstalled()){
		projectDiagramManager.setShowMetrics(paletteMap.get(PALETTE_SHOW_METRICS_TITLE));
		}
		projectDiagramManager.setGroupEventsInSameArea(paletteMap.get(PALETTE_GROUP_EVENTS_TITLE));
		projectDiagramManager.setShowEvents(paletteMap.get(PALETTE_SHOW_EVENTS_TITLE));
		
		projectDiagramManager.setGroupRulesInSameArea(paletteMap.get(PALETTE_GROUP_RULES_TITLE));
		projectDiagramManager.setShowRules(paletteMap.get(PALETTE_SHOW_RULES_TITLE));
		
		projectDiagramManager.setGroupRuleFunctionsInSameArea(paletteMap.get(PALETTE_GROUP_RULE_FUNCTIONS_TITLE));
		projectDiagramManager.setShowRuleFunctions(paletteMap.get(PALETTE_SHOW_RULES_FUNCTIONS_TITLE));
		
		projectDiagramManager.setShowDecisionTables(paletteMap.get(PALETTE_SHOW_DECISIONTABLES_TITLE));
		projectDiagramManager.setShowDomainModels(paletteMap.get(PALETTE_SHOW_DOMAIN_MODEL_TITLE));
		projectDiagramManager.setShowStateMachines(paletteMap.get(PALETTE_SHOW_STATEMACHIES_TITLE));
		//projectDiagramManager.setShowArchives(paletteMap.get(PALETTE_SHOW_ARCHIVES_TITLE));
		
		projectDiagramManager.setShowScoreCards(paletteMap.get(PALETTE_SHOW_SCORECARDS_TITLE));
		projectDiagramManager.setShowChannels(paletteMap.get(PALETTE_SHOW_CHANNELS_TITLE));
		projectDiagramManager.setShowProcesses(paletteMap.get(PALETTE_SHOW_PROCESSES_TITLE));
		projectDiagramManager.setShowScopeLinks(paletteMap.get(PALETTE_SHOW_SCOPE_LINKS_TITLE));
		projectDiagramManager.setShowUsageLinks(paletteMap.get(PALETTE_SHOW_USAGE_LINKS_TITLE));
		projectDiagramManager.setShowProcessLinks(paletteMap.get(PALETTE_SHOW_PROCESS_LINKS_TITLE));
		projectDiagramManager.setShowProcessExpanded(paletteMap.get(PALETTE_SHOW_PROCESS_EXPANDED_TITLE));
		/*projectDiagramManager.setShowArchivedDestinations(paletteMap.get(PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE));
		projectDiagramManager.setShowArchiveRulesLinks(paletteMap.get(PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE));
		projectDiagramManager.setShowArchiveAllRulesLinks(paletteMap.get(PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE));
		projectDiagramManager.setShowRulesInFolders(paletteMap.get(PALETTE_SHOW_RULES_FOLDERS_TITLE));*/
//		projectDiagramManager.setShowTooltips(paletteMap.get(PALETTE_SHOW_TOOLTIPS_TITLE));

		projectDiagramManager.layoutAndRefresh();
		projectDiagramManager.setRefresh(false);
		}
		catch(Throwable e){
			System.err.println();
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param paletteMap
	 */
	public static void loadDefaultPreferencesMap(Map<String, Boolean> paletteMap){
		IPreferenceStore prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
		paletteMap.put(PALETTE_SHOW_CONCEPTS_TITLE, prefStore.getBoolean(PALETTE_SHOW_CONCEPTS_TITLE));
		paletteMap.put(PALETTE_SHOW_METRICS_TITLE, prefStore.getBoolean(PALETTE_SHOW_METRICS_TITLE));
		paletteMap.put(PALETTE_SHOW_EVENTS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_EVENTS_TITLE));
		paletteMap.put(PALETTE_SHOW_DECISIONTABLES_TITLE,  prefStore.getBoolean(PALETTE_SHOW_DECISIONTABLES_TITLE));
		paletteMap.put(PALETTE_SHOW_DOMAIN_MODEL_TITLE,  prefStore.getBoolean(PALETTE_SHOW_DOMAIN_MODEL_TITLE));
		paletteMap.put(PALETTE_SHOW_STATEMACHIES_TITLE,  prefStore.getBoolean(PALETTE_SHOW_STATEMACHIES_TITLE));
		paletteMap.put(PALETTE_SHOW_ARCHIVES_TITLE,  prefStore.getBoolean(PALETTE_SHOW_ARCHIVES_TITLE));
		paletteMap.put(PALETTE_SHOW_RULES_TITLE,  prefStore.getBoolean(PALETTE_SHOW_RULES_TITLE));
		paletteMap.put(PALETTE_SHOW_RULES_FUNCTIONS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_RULES_FUNCTIONS_TITLE));
		paletteMap.put(PALETTE_SHOW_SCORECARDS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_SCORECARDS_TITLE));
		paletteMap.put(PALETTE_SHOW_CHANNELS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_CHANNELS_TITLE));
		paletteMap.put(PALETTE_SHOW_PROCESSES_TITLE,  prefStore.getBoolean(PALETTE_SHOW_PROCESSES_TITLE));
		paletteMap.put(PALETTE_SHOW_SCOPE_LINKS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_SCOPE_LINKS_TITLE));
		paletteMap.put(PALETTE_SHOW_USAGE_LINKS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_USAGE_LINKS_TITLE));
		paletteMap.put(PALETTE_SHOW_PROCESS_LINKS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_PROCESS_LINKS_TITLE));
		paletteMap.put(PALETTE_SHOW_PROCESS_EXPANDED_TITLE,  prefStore.getBoolean(PALETTE_SHOW_PROCESS_EXPANDED_TITLE));
		paletteMap.put(PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE));
		paletteMap.put(PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE));
		paletteMap.put(PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE));
		paletteMap.put(PALETTE_SHOW_RULES_FOLDERS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_RULES_FOLDERS_TITLE));
		paletteMap.put(PALETTE_SHOW_TOOLTIPS_TITLE,  prefStore.getBoolean(PALETTE_SHOW_TOOLTIPS_TITLE));
		paletteMap.put(PALETTE_GROUP_CONCEPTS_TITLE,  prefStore.getBoolean(PALETTE_GROUP_CONCEPTS_TITLE));
		paletteMap.put(PALETTE_GROUP_EVENTS_TITLE,  prefStore.getBoolean(PALETTE_GROUP_EVENTS_TITLE));
		paletteMap.put(PALETTE_GROUP_RULES_TITLE,  prefStore.getBoolean(PALETTE_GROUP_RULES_TITLE));
		paletteMap.put(PALETTE_GROUP_RULE_FUNCTIONS_TITLE,  prefStore.getBoolean(PALETTE_GROUP_RULE_FUNCTIONS_TITLE));
	}
	
	/**
	 * @param entry
	 * @param entryButtonMap
	 * @param id
	 */
	public static void handleEntry(Button entry, Map<String, Button> entryButtonMap, Map<String, Boolean> paletteMap, String id, String groupId){
		if(entry.getText().intern() == id){
			if(entry.getSelection()== false){
				entryButtonMap.get(groupId).setSelection(false);
				entryButtonMap.get(groupId).setEnabled(false);
				paletteMap.put(groupId, false);
			}else{
				entryButtonMap.get(groupId).setEnabled(true);
			}
		}
	}
	
	public static void refresh(DiagramManager diagramManager){
		try{
			((IGraphDrawing)diagramManager).getLayoutManager().callBatchIncrementalLayout();
			((IGraphDrawing)diagramManager).getDrawingCanvas().drawGraph();
			((IGraphDrawing)diagramManager).getDrawingCanvas().repaint();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
