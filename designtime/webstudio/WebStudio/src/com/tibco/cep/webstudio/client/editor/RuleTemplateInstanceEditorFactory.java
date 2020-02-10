/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Node;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.IconClickEvent;
import com.smartgwt.client.widgets.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.diff.DiffHelper;
import com.tibco.cep.webstudio.client.diff.MergedDiffHelper;
import com.tibco.cep.webstudio.client.diff.ModificationEntry;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RTIMessages;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.CommandInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.ViewInfo;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.view.BindingViewForm;
import com.tibco.cep.webstudio.client.widgets.CommandForm;
import com.tibco.cep.webstudio.client.widgets.DateTimePicker;
import com.tibco.cep.webstudio.client.widgets.MultiDataSourceFilterBuilder;
import com.tibco.cep.webstudio.client.widgets.ValueForm;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.impl.BuilderSubClauseImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.CommandImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RuleTemplateInstanceImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;

/**
 * This class provides a singleton Factory instance to create Rule Template
 * Instance editors
 * 
 * @author Vikram Patil
 */
public class RuleTemplateInstanceEditorFactory implements IEditorFactory {
	
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(RuleTemplateInstanceEditorFactory.class.getName());
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private static RuleTemplateInstanceEditorFactory instance;
	
	public static final String COMMAND_ACTIONTYPE_CALL = "call";
	public static final String COMMAND_ACTIONTYPE_CREATE = "create";
	public static final String COMMAND_ACTIONTYPE_MODIFY = "modify";
	private static final String RTI_NAME_TOKEN = "%RTIName%";
	
	/**
	 * Create a singleton to create RuleTemplateInstance Editors
	 * 
	 * @return
	 */
	public static RuleTemplateInstanceEditorFactory getInstance() {
		if (instance == null) {
			instance = new RuleTemplateInstanceEditorFactory();
		}
		return instance;
	}

	private RuleTemplateInstanceEditorFactory() {
	}

	/**
	 * @see com.tibco.cep.webstudio.client.editor.IEditorFactory#createEditor(com.tibco.cep.webstudio.client.model.NavigatorResource)
	 */
	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord) {
		return createEditor(selectedRecord, false, null, true, false, null);
	}
	
	/**
	 * Initialise the editor
	 * 
	 * @param selectedRecord
	 * @param loadDataAtStartUp
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean loadDataAtStartUp) {
		return createEditor(selectedRecord, false, null, loadDataAtStartUp, false, null);
	}
	
	/**
	 * @see IEditorFactory#createEditor(NavigatorResource, boolean, String)
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
		return createEditor(selectedRecord, isReadOnly, revisionId, true, false, null);
	}
	
	
	/**
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param artifactVersionDiff
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean artifactVersionDiff, List<RequestParameter> requestParams) {
		return createEditor(selectedRecord, isReadOnly, revisionId, true, artifactVersionDiff, requestParams);
	}
	
	/**
	 * Initialise the editor
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param loadDataAtStartUp
	 * @param artifactVersionDiff
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataAtStartUp, boolean artifactVersionDiff, List<RequestParameter> requestParams) {
		return new RuleTemplateInstanceEditor(selectedRecord, isReadOnly, revisionId, loadDataAtStartUp, artifactVersionDiff, requestParams);
	}

	/**
	 * Editor class for Rule Template Instances. Conditionally renders
	 * view/build panes. Additionally supports handling editor events.
	 */
	public class RuleTemplateInstanceEditor extends AbstractEditor {
		private VLayout then;
		private RuleTemplateInstance selectedRuleTemplateInstance;
		private List<SymbolInfo> symbols;
		private ViewInfo viewInfo;
		private HashMap<String, String> displayProperties;
		private TemplateInstanceChangeHandler changeHandler = new TemplateInstanceChangeHandler();
		private List<CommandForm> commandForms = new ArrayList<CommandForm>();
		private Map<String, List<SymbolInfo>> commandSymbols = new HashMap<String, List<SymbolInfo>>();
		private boolean initializing = false;
		private RTIMessages rtiMsgBundle = (RTIMessages)I18nRegistry.getResourceBundle(I18nRegistry.RTI_MESSAGES);
		private List<Command> commandList;
		private Map<String, ModificationEntry> rtiFilterModifications = new LinkedHashMap<String, ModificationEntry>();
		private Map<BindingInfo, ModificationEntry> rtiBindingModifications = new LinkedHashMap<BindingInfo, ModificationEntry>();
		private Object sourceModel;
		private Object targetModel;
		private Object dndFilter;
		private int dropPosition = -1;
		private boolean isCommandBuilder;
		private boolean showPropertyPane;
		private RuleTemplateInstancePropertyTab propertyTab;
		
		//List of filters to be deleted from model just before save.
		private List<Filter> filtersToBeRemovedBeforeSave = new ArrayList<Filter>();
		
		/**
		 * 
		 */
		public class TemplateInstanceChangeHandler implements IInstanceChangedListener {
			@Override
			public void instanceChanged(IInstanceChangedEvent changeEvent) {
				if (isInitializing()) {
					return;
				}
				if (!RuleTemplateInstanceEditor.this.isDirty()) {
					RuleTemplateInstanceEditor.this.makeDirty();
				}
			}
		}

		/**
		 * Initialises and sets up the rule template instance editor
		 * 
		 * @param selectedRecord
		 * @param loadDataAtStartUp
		 */
		public RuleTemplateInstanceEditor(NavigatorResource selectedRecord, boolean loadDataAtStartUp) {
			this(selectedRecord, false, null, loadDataAtStartUp);
		}
		
		/**
		 * Initialises and sets up the rule template instance editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 * @param loadDataAtStartUp
		 */
		public RuleTemplateInstanceEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataAtStartUp) {
			this(selectedRecord, isReadOnly, revisionId, loadDataAtStartUp, false, null);
		}

		/**
		 * Initialises and sets up the rule template instance editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 * @param loadDataAtStartUp
		 * @param versionDiffContent
		 */
		public RuleTemplateInstanceEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataAtStartUp, boolean versionDiffContent,
				List<RequestParameter> requestParams) {
			super(selectedRecord, true, isReadOnly, revisionId, loadDataAtStartUp, versionDiffContent);
			setAdditionalRequestParams(requestParams);
			this.initialize();
		}
		
		@Override
		protected SectionStackSection[] getSections() {
			return new SectionStackSection[] {};
		}

		public boolean isInitializing() {
			return initializing;
		}

		public void setInitializing(boolean initializing) {
			this.initializing = initializing;
		}

		/**
		 * Widget for when section
		 * 
		 * @return
		 */
		private SectionStackSection createWhenSection() {
			SectionStackSection whenSection = new SectionStackSection(rtiMsgBundle.rtiEditor_when());
			whenSection.setExpanded(true);
			whenSection.setCanCollapse(true);

			VLayout when = new VLayout(5);
			when.setMargin(5);
			when.setOverflow(Overflow.AUTO);
			when.setAnimateMembers(false);
			when.setCanAcceptDrop(false);
			when.setSnapToGrid(true);
			when.setCanDropComponents(false);

			MultiDataSourceFilterBuilder builder = new MultiDataSourceFilterBuilder(this.selectedRuleTemplateInstance.getConditionFilter(),
					this);
			when.addMember(builder);

			whenSection.addItem(when);

			return whenSection;
		}

		/**
		 * Widget for then section
		 * 
		 * @return
		 */
		private SectionStackSection createThenSection() {
			SectionStackSection thenSection = new SectionStackSection(rtiMsgBundle.rtiEditor_then());
			thenSection.setExpanded(true);
			thenSection.setCanCollapse(true);

			thenSection.addItem(this.then);

			this.selectedRuleTemplateInstance.getActions().addChangeListener(new IInstanceChangedListener() {
				@Override
				public void instanceChanged(IInstanceChangedEvent changeEvent) {
					if ((changeEvent.getChangeType() == IInstanceChangedEvent.ADDED)
							&& (changeEvent.getValue() instanceof Command)) {
						Command c = (Command) changeEvent.getValue();
						RuleTemplateInstanceEditor.this.createAndAddCommandForm(c);
						RuleTemplateInstanceEditor.this.makeDirty();
					}
				}
			});

			List<Command> actions = this.selectedRuleTemplateInstance.getActions().getActions();
			if ((actions != null) && (actions.size() > 0)) {
				for (Command command : actions) {
					this.createAndAddCommandForm(command);
				}
			}

			return thenSection;
		}

		/**
		 * Returns the change handler
		 */
		public TemplateInstanceChangeHandler getChangeHandler() {
			return this.changeHandler;
		}

		/**
		 * Get all Symbols
		 * 
		 * @return
		 */
		public List<SymbolInfo> getSymbols() {
			return this.symbols;
		}

		@Override
		protected Canvas getWidget() {
			return null;
		}

		@Override
		public void onDirty() {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSave() {
			if (isSyncMerge()) {
				if (!isMergeComplete()) {
					CustomSC.say("Cannot save partial merge, please take an action on all local changes.");
					return;
				}
				preSaveSyncMergeTask();
			}
			
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);

			ArtifactUtil.addHandlers(this);
			RuleTemplateHelper.saveRuleTemplateInstance(this.selectedRuleTemplateInstance, this.getSelectedResource(), this.request, isViewTemplate(), getBindgingInfoList(), commandList, isMerge());
		}
		
		/**
		 * Validating for Rule Template Instance
		 * 
		 * @return
		 */
		@Override
		public boolean onValidate() {
			validate();
			return true;
		}
		
		/**
		 * Validating for Rule Template Instance
		 * 
		 * @return
		 */
		public void validate() {
			indeterminateProgress(globalMsgBundle.progressMessage_pleaseWait() + " " + globalMsgBundle.progressMessage_validatingBR(), false);
			RuleTemplateHelper.validateRuleTemplateInstance(getSelectedResource(), request);
			addHandlers(this);
		}

		@Override
		public void onSuccess(HttpSuccessEvent event) {
			boolean isValidEvent = false;
			if ((event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1
					|| event.getUrl().indexOf(ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL()) != -1)
					&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=ruletemplateinstance") != -1) {
				Element docElement = event.getData();
				String implementsPath = docElement.getElementsByTagName("implementsPath").item(0).getFirstChild()
						.getNodeValue();
				String artifactPath = docElement.getElementsByTagName("artifactPath").item(0).getFirstChild()
						.getNodeValue();
				
				isValidEvent = this.processRuleTemplateInstance(docElement, null, null, implementsPath, artifactPath, false);
				
				if (isReadOnly()) {
					makeReadOnly();
				}
			} else if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL()) != -1) {
				isValidEvent = true;
				
				super.postSave();
				
				if (this.isNewArtifact()) {
					this.setNewArtifact(false);
				}
			} else if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_VERSION_DIFF.getURL()) != -1
					&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=ruletemplateinstance") != -1) {
				
				Element docElement = event.getData();
				Element currentElementContentDocElement = DiffHelper.getCurrentVersionElement(docElement);
				Element previousElementContentDocElement = DiffHelper.getPreviousVersionElement(docElement);
				if (previousElementContentDocElement != null && currentElementContentDocElement != null) {
					String implementsPath = docElement.getElementsByTagName("implementsPath").item(0).getFirstChild()
							.getNodeValue();
					String artifactPath = docElement.getElementsByTagName("artifactPath").item(0).getFirstChild()
							.getNodeValue();
					if (event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_DIFFMODE + "=1") != -1) {
						//RTI diff for merged view.
						Element serverVersionDocElement = DiffHelper.getServerVersionElement(docElement);
						if (serverVersionDocElement != null) {
							setMerge(true);
							isValidEvent = this.processRuleTemplateInstance(currentElementContentDocElement, previousElementContentDocElement, serverVersionDocElement, implementsPath, artifactPath, false);
						}
					}
					else {
						isValidEvent = this.processRuleTemplateInstance(currentElementContentDocElement, previousElementContentDocElement, null, implementsPath, artifactPath, false);
					}
				}
				if (isReadOnly()) {
					makeReadOnly();
				}
			} else if (event.getUrl().indexOf(ServerEndpoints.RMS_VALIDATE.getURL()) != -1) {
				isValidEvent = true;
				Element docElement = event.getData();
				RuleTemplateHelper.postProblems(docElement);
				indeterminateProgress("", true);
			}

			if (isValidEvent) {
				LoadingMask.clearMask();
				ArtifactUtil.removeHandlers(this);
				this.getRtiPropertyTab().showRTIPropertiesPane();
			}
		}
		
		/**
		 * Process the RT/RTI data and render editor components
		 * 
		 * @param docElement
		 * @param prevDocElement	The previous version of RTI.
		 * @param serverVerDocElement
		 * @param implementsPath
		 * @param artifactPath
		 * @return
		 */
		public boolean processRuleTemplateInstance(Element docElement, Element prevDocElement, Element serverVerDocElement, String implementsPath, String artifactPath, boolean setMultiFilterType) {
			boolean isValidEvent = false;

			this.selectedRuleTemplateInstance = new RuleTemplateInstanceImpl();
			this.selectedRuleTemplateInstance.setName(artifactPath);
			this.selectedRuleTemplateInstance.setImplementsPath(implementsPath);
			
			// add description
			if (docElement.getElementsByTagName("description") != null) {
				Node descriptionNode = docElement.getElementsByTagName("description").item(0);
				if (descriptionNode != null && descriptionNode.getFirstChild() != null) {
					if (descriptionNode.getFirstChild().getNodeValue() != null) {
						this.selectedRuleTemplateInstance
								.setDescription(descriptionNode.getFirstChild().getNodeValue());
					} else {
						this.selectedRuleTemplateInstance.setDescription("");
					}
				} 
				else if (descriptionNode == null || descriptionNode.getFirstChild() == null) {
					this.selectedRuleTemplateInstance.setDescription("");
				}
			}
			
			// add priority
			if (docElement.getElementsByTagName("rulePriority") != null) {
				Node priorityNode = docElement.getElementsByTagName("rulePriority").item(0);
				if (priorityNode != null && priorityNode.getFirstChild()!= null) {
					this.selectedRuleTemplateInstance.setPriority(Integer.parseInt(priorityNode.getFirstChild().getNodeValue()));
				}
			}
			
			displayProperties = RuleTemplateHelper.getDisplayProperties(docElement);
			
			viewInfo = RuleTemplateHelper.getViewInfo(docElement);
			if (viewInfo.getBindings().size() > 0) {
				// View UI
				this.getSectionStack().destroy();
				if (viewInfo.getHtml() == null) {
					return false;
				}
				if (serverVerDocElement != null) {
					logger.debug("Merged Diff for RTI view.");
					ViewInfo serverVerViewInfo = RuleTemplateHelper.getViewInfo(serverVerDocElement);
					ViewInfo prevViewInfo = RuleTemplateHelper.getViewInfo(prevDocElement);
					MergedDiffHelper.processRTIViewMergedDiff(prevViewInfo.getBindings(), viewInfo.getBindings(), serverVerViewInfo.getBindings(), rtiBindingModifications);
					viewInfo = serverVerViewInfo;//Show server copy while rendering. Diffs will be shown on hover.
				}
				else if (prevDocElement != null) {
					logger.debug("Diff for RTI view.");
					ViewInfo prevViewInfo = RuleTemplateHelper.getViewInfo(prevDocElement);
					DiffHelper.processRTIViewDiff(prevViewInfo.getBindings(), viewInfo.getBindings(), rtiBindingModifications);
				}
				this.selectedRuleTemplateInstance.addChangeListener(this.getChangeHandler());
				this.getEditorLayout()
						.addMember(new BindingViewForm(this.selectedRuleTemplateInstance, viewInfo, this));
				String script = this.viewInfo.getScript();
				if (script != null) {
					String finalScript = script.replaceAll(RTI_NAME_TOKEN, RuleTemplateHelper.getArtifactName(artifactPath));
					this.viewInfo.setScript(finalScript);
				}
			} else {
				// Fetch RT Symbols
				this.symbols = RuleTemplateHelper.getSymbols(docElement);
				
				// Fetch RTI Conditions
				MultiFilter multiFilter = RuleTemplateHelper.getConditionFilter(docElement.getElementsByTagName("conditions").item(0), setMultiFilterType);
				
				if (serverVerDocElement != null) {
					logger.debug("Computing Merged Diff for RTI multifilter.");
					MultiFilter prevMultiFilter = RuleTemplateHelper.getConditionFilter(prevDocElement.getElementsByTagName("conditions").item(0), false);
					MultiFilter serverMultiFilter = RuleTemplateHelper.getConditionFilter(serverVerDocElement.getElementsByTagName("conditions").item(0), false);
					multiFilter = MergedDiffHelper.processMultiFilterMergedDiff(prevMultiFilter, multiFilter, serverMultiFilter, rtiFilterModifications);
				}
				else if (prevDocElement != null) {
					logger.debug("Computing Diff for RTI multifilter.");
					MultiFilter prevMultiFilter = RuleTemplateHelper.getConditionFilter(prevDocElement.getElementsByTagName("conditions").item(0), false);
					DiffHelper.processMultiFilterDiff(prevMultiFilter, multiFilter, rtiFilterModifications);
				}
				this.selectedRuleTemplateInstance.setConditionFilter(multiFilter);
				
				// Fetch RT Commands and RTI filters
				if (prevDocElement != null) {//Enter diff mode (MergedDiff/NormalDiff)
					List<Command> serverVerCommandList = serverVerDocElement == null ? null : RuleTemplateHelper.getCommandInfo(serverVerDocElement, this.selectedRuleTemplateInstance, this.commandSymbols);
					List<Command> localVerCommandList = RuleTemplateHelper.getCommandInfo(docElement, this.selectedRuleTemplateInstance, this.commandSymbols);
					List<Command> prevVerCommandList = prevDocElement == null ? null : RuleTemplateHelper.getCommandInfo(prevDocElement, this.selectedRuleTemplateInstance, this.commandSymbols);
					
					if (localVerCommandList != null && prevVerCommandList != null) {
						String[] actionTypes = new String[] { "create", "call", "modify" };
						for (String actionType : actionTypes) {
							Command prevVerCommand = null;
							Command localVerCommand = null;
							Command serverVerCommand = null;
							for (Command command : localVerCommandList) {
								if (actionType.equalsIgnoreCase(command.getActionType())) {
									localVerCommand = command;
									break;
								}
							}
							for (Command command : prevVerCommandList) {
								if (actionType.equalsIgnoreCase(command.getActionType())) {
									prevVerCommand = command;
									break;
								}
							}
							if (serverVerCommandList != null) {
								for (Command command : serverVerCommandList) {
									if (actionType.equalsIgnoreCase(command.getActionType())) {
										serverVerCommand = command;
										break;
									}
								}
							}
							if (serverVerCommand != null && localVerCommand != null && prevVerCommand != null) {//Merged Diff
								MergedDiffHelper.processFiltersMergedDiff(
										prevVerCommand.getSubClause() == null ? new ArrayList<Filter>() : prevVerCommand.getSubClause().getFilters(),
										localVerCommand.getSubClause() == null ? new ArrayList<Filter>() : localVerCommand.getSubClause().getFilters(),
										serverVerCommand.getSubClause() == null ? new ArrayList<Filter>() : serverVerCommand.getSubClause().getFilters(),
										rtiFilterModifications);
							}
							else if (localVerCommand!= null && prevVerCommand!= null) {//Normal Diff
								DiffHelper.processFiltersDiff(prevVerCommand.getSubClause() == null ? new ArrayList<Filter>() : prevVerCommand.getSubClause().getFilters(),
										localVerCommand.getSubClause( )== null ? new ArrayList<Filter>() : localVerCommand.getSubClause().getFilters(), rtiFilterModifications);
							}
						}
					}
					this.createCommandMenu(serverVerDocElement != null ? serverVerDocElement : prevDocElement);
				} else {
					this.createCommandMenu(docElement);
				}
				// Builder UI
				this.addSections(new SectionStackSection[] { this.createWhenSection(), this.createThenSection() });
			}
			
			this.propertyTab = new RuleTemplateInstancePropertyTab(this.selectedRuleTemplateInstance, this, isReadOnly());			
			this.setShowPropertyPane(true);
			
			isValidEvent = true;
			
			return isValidEvent;
		}

		/**
		 * Create Menu for commands
		 * 
		 * @param docElement
		 */
		private void createCommandMenu(Element docElement) {
			commandList = RuleTemplateHelper.getCommandInfo(docElement,
					this.selectedRuleTemplateInstance,
					this.commandSymbols);

			this.then = new VLayout(5);
			this.then.setMargin(5);
			this.then.setOverflow(Overflow.AUTO);
			this.then.setAnimateMembers(true);
			this.then.setCanAcceptDrop(false);
			this.then.setSnapToGrid(true);
			this.then.setCanDropComponents(false);

			if (commandList.size() > 0) {
				final Menu menu = new Menu();
				menu.setShowShadow(true);
				menu.setShadowDepth(10);

				List<CommandInfo> commands = new ArrayList<CommandInfo>();
				for (final Command cmd : commandList) {
					CommandInfo cmdInfo = new CommandInfo(cmd.getType(), cmd.getActionType(), cmd.getAlias());
					if (!commands.contains(cmdInfo)) {
						commands.add(cmdInfo);
						
						// handle the case for invalid RT, i.e. using modify w/o adding the artifact in scope
						// we just ignore that command
						if (cmd.getType() == null || cmd.getType().isEmpty()) {
							continue;
						}
						
						String menuItemText = getI18nCommandActionText(cmd.getActionType()) + " ";
						menuItemText += (cmd.getType().indexOf(".") != -1) ? cmd.getType().substring(0, cmd.getType().indexOf(".")) :  cmd.getType();
						menuItemText += " " + cmd.getAlias();
						
						String icon = "";
						if (COMMAND_ACTIONTYPE_CALL.equalsIgnoreCase(cmd.getActionType())) {
							icon = Page.getAppImgDir() + "icons/16/rulefunction.png";
						} else if (COMMAND_ACTIONTYPE_MODIFY.equalsIgnoreCase(cmd.getActionType())) {
							icon = Page.getAppImgDir() + "icons/16/save.gif";
						} else if (COMMAND_ACTIONTYPE_CREATE.equalsIgnoreCase(cmd.getActionType())) {
							icon = "[SKINIMG]/actions/add.png";
						}
						final MenuItem newItem = new MenuItem(menuItemText, icon);
						menu.addItem(newItem);						
						newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								Command command = new CommandImpl();
								BuilderSubClause clause = new BuilderSubClauseImpl();
								command.setSubClause(clause);
								command.setActionType(cmd.getActionType());
								command.setType(cmd.getType());
								command.setAlias(cmd.getAlias());
								RuleTemplateInstanceEditor.this.selectedRuleTemplateInstance.getActions()
								.addAction(command);
							}
						});
					}
				}

				IButton menuButton = new IButton(rtiMsgBundle.rtiCommand_action());
				menuButton.setIcon("[SKINIMG]/Menu/menu_button.png");
				menuButton.setIconHeight(4);
				menuButton.setIconWidth(7);
				menuButton.setIconOrientation("right");
				menuButton.setWidth(55);
				menuButton.setLeft(5);
				menuButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						menu.setPageLeft(event.getX());
						menu.setPageTop(event.getY() + 12);
						menu.show();

					}
				});
				
				this.then.addMember(menuButton);
			}
		}

		/**
		 * Create and add form for commands
		 * 
		 * @param cmd
		 */
		private void createAndAddCommandForm(final Command cmd) {
			final HLayout layout = new HLayout(5);
			layout.setCanDragReposition(true);
			layout.setCanDrag(true);
			layout.setCanDrop(true);
			layout.setDragAppearance(DragAppearance.OUTLINE);
			layout.setWidth100();
			layout.setHeight(30);
			layout.setBorder("1px solid lightgray");

			List<SymbolInfo> symbolList = this.commandSymbols.get(cmd.getAlias());
			if (symbolList == null) {
				symbolList = ArtifactUtil.filterSymbolsToRemoveAttributes(cmd.getType(), cmd.getAlias(), cmd.getActionType(), this.symbols);
			}

			final CommandForm form = new CommandForm(cmd, this, symbolList);
			String label = rtiMsgBundle.rticommand_add_parameters();
			label += ((cmd.getType().indexOf(".") != -1) ? cmd.getType().substring(0, cmd.getType().indexOf(".")) : cmd.getType());
			
			String commandIcon = "";
			if (COMMAND_ACTIONTYPE_CREATE.equalsIgnoreCase(cmd.getActionType())) {
				commandIcon = Page.getAppImgDir() + "icons/16/add.png";
			} else if (COMMAND_ACTIONTYPE_MODIFY.equalsIgnoreCase(cmd.getActionType())) {
				commandIcon = Page.getAppImgDir() + "icons/16/save.gif";
			} else if (COMMAND_ACTIONTYPE_CALL.equalsIgnoreCase(cmd.getActionType())) {
				commandIcon = Page.getAppImgDir() + "icons/16/rulefunction.png";
			}
			Label removeLabel = new Label(label);
			removeLabel.setBackgroundColor("lightgray");
			removeLabel.setHeight(10);
			removeLabel.setWidth("100%");
			removeLabel.setIcon(commandIcon);
			removeLabel.setIconAlign(Alignment.RIGHT.getValue());
			if (LocaleInfo.getCurrentLocale().isRTL()) {
				removeLabel.setAlign(Alignment.RIGHT);
			} else {
				removeLabel.setAlign(Alignment.LEFT);
			}
			removeLabel.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					layout.animateHide(AnimationEffect.WIPE, new AnimationCallback() {

						@Override
						public void execute(boolean earlyFinish) {
							layout.destroy();
							if (RuleTemplateInstanceEditor.this.commandForms.contains(form)) {
								RuleTemplateInstanceEditor.this.commandForms.remove(form);
								RuleTemplateInstanceEditor.this.selectedRuleTemplateInstance.getActions()
										.removeAction(cmd);
							}
							RuleTemplateInstanceEditor.this.makeDirty();
						}
					});
				}
			});
			form.addMember(removeLabel, 0);

			layout.addMember(form);
			this.commandForms.add(form);

			this.then.addMember(layout);
			layout.hide();
			layout.animateShow(AnimationEffect.WIPE);
		}
		
		@Override
		public void onFailure(HttpFailureEvent event) {
			if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL()) != -1) {
				// BE-20866 - not sure why this is here - it removes the RTI
				// from the project explorer if the save fails
				// ProjectExplorerUtil.updateGroups(getSelectedResource(),
				// false);
				super.onFailure(event);
			} else if (event.getUrl().indexOf(ServerEndpoints.RMS_VALIDATE.getURL()) != -1) {
				indeterminateProgress("", true);
				super.onFailure(event);
			} else {
				super.onFailure(event);
			}
		}

		/**
		 * Get symbols for commands
		 * 
		 * @return
		 */
		public Map<String, List<SymbolInfo>> getCommandSymbols() {
			return this.commandSymbols;
		}

		@Override
		public void close() {
			if (this.viewInfo != null && this.viewInfo.getScript() != null) {
				RuleTemplateHelper.checkExistingScriptAndRemove(RuleTemplateHelper.getArtifactName(this.selectedRuleTemplateInstance.getName()));
			}
			if (getRtiPropertyTab() != null) {
				getRtiPropertyTab().destroy();
				this.propertyTab = null;
			}
		}
		
		@Override
		public void makeReadOnly() {
			if (isViewTemplate()) {
				for (Canvas member : this.getEditorLayout().getMembers()) {
					if (member instanceof BindingViewForm) {
						BindingViewForm bindingViewForm = (BindingViewForm) member;
						bindingViewForm.disableHTMLWidgets();
					}
				}
				
			} else {
				if (this.sectionStack != null) {
					for (SectionStackSection section : this.sectionStack.getSections()) {
						checkLeafElements(section.getItems());
					}
				}
			}
		}
		
		@Override
		public String getEditorUrl() {
			return null;
		}
		
		/**
		 * Check if the RTI is View/Builder based
		 * 
		 * @return
		 */
		private boolean isViewTemplate() {
			if (viewInfo != null && viewInfo.getBindings().size() > 0) {
				return true;
			}

			return false;
		}
		
		@Override
		public void setSaveConfirmationProperties() {
			setConfirmSaveTitle(rtiMsgBundle.rtiSave_title());
			setConfirmSaveDescription(rtiMsgBundle.rtiSave_message());
		}
		
		@Override
		public String getURI() {
			return getSelectedResource().getId().substring(0, getSelectedResource().getId().indexOf(".")).replace("$", "/");
		}
		
		@Override
		public void gotoMarker(ProblemMarker marker) {
			// TODO
		}
		
		/**
		 * Recursively check for all the leaf node items
		 * 
		 * @param elements
		 */
		public void checkLeafElements(Canvas[] elements) {
			for (Canvas element : elements) {
				if (element.getChildren().length > 0) {
					checkLeafElements(element.getChildren());
				} else {
					makeElementReadOnly(element);
				}
			}
		}

		/**
		 * Disable the elements
		 * 
		 * @param element
		 */
		private void makeElementReadOnly(Object element) {
			if (element instanceof Label) {
				Label label = (Label) element;
				label.setDisabled(true);
			} else if (element instanceof ValueForm) {
				ValueForm valueForm = (ValueForm) element;
				valueForm.disable();
			} else if (element instanceof Button) {
				Button button = (Button) element;
				button.disable();
			} else if (element instanceof IMenuButton) {
				IMenuButton menuButton = (IMenuButton) element;
				Menu menu = menuButton.getMenu();
				for (MenuItem menuItem : menu.getItems()) {
					menuItem.setEnabled(false);
				}
			} else if (element instanceof Canvas) {
				Object layoutData = ((Canvas) element).getParentElement().getLayoutData();
				if (layoutData instanceof DateTimePicker) {
					((DateTimePicker) layoutData).setEnabled(false);
				}
			}
		}
		
		/**
		 * Get the Binding Info list
		 * @return
		 */
		public List<BindingInfo> getBindgingInfoList() {
			if (viewInfo != null) {
				return viewInfo.getBindings();
			}
			
			return null;
		}
		
		public Map<String, ModificationEntry> getFilterModifications() {
			return rtiFilterModifications;
		}
		
		public Map<BindingInfo, ModificationEntry> getBindingModifications() {
			return rtiBindingModifications;
		}
		
		@Override
		public void addArtifactDiffLegend(VLayout layout) {
			layout.addMember(DiffHelper.getDiffLegend());
		}

		public HashMap<String, String> getDisplayProperties() {
			return this.displayProperties;
		}
		
		public RuleTemplateInstance getSelectedRuleTemplateInstance() {
			return this.selectedRuleTemplateInstance;
		}
		
		@Override
		public boolean isMergeComplete() {
			if (isViewTemplate()) {
				if (rtiBindingModifications != null) {
					Collection<ModificationEntry> mods = rtiBindingModifications.values();
					for (ModificationEntry mod : mods) {
						if (!mod.isApplied()) {
							return false;
						}
					}
				}
			} else {
				if (rtiFilterModifications != null) {
					Collection<ModificationEntry> mods = rtiFilterModifications.values();
					for (ModificationEntry mod : mods) {
						if (!mod.isApplied()) {
							return false;
						}
					}
				}
			}
			return true;
		}
		
		/**
		 * Removes the filter(s) that were added as part of creating merged view
		 * but are not really meant to be saved. (Mainly used under merge from
		 * synchronise flow.)
		 */
		private void preSaveSyncMergeTask() {
			if (isSyncMerge()) {
				for (Filter filter : getFiltersToBeRemovedBeforeSave()) {
					// Delete filters that are not supposed to be actually saved
					// (those added in model just to depict deletion in DIFF
					// flows..)
					RuleTemplateHelper.removeFilter(this.selectedRuleTemplateInstance.getConditionFilter(), filter);
				}
			}
		}

		public List<Filter> getFiltersToBeRemovedBeforeSave() {
			return this.filtersToBeRemovedBeforeSave;
		}
		
		/**
		 * Add filter to be removed from model just before saving. (Used for
		 * filters that are are not supposed to be actually saved, but shown on
		 * UI just for depicting deletion in DIFF flows.)
		 * 
		 * @param filter
		 */
		public void addFilterToBeRemovedBeforeSave(Filter filter) {
			this.filtersToBeRemovedBeforeSave.add(filter);
		}
		
		/**
		 * Get Internationalized Command Action
		 * 
		 * @param actionCommand
		 * @return
		 */
		private String getI18nCommandActionText(String actionCommand) {
			String i18nActionCmd = actionCommand;
			if (COMMAND_ACTIONTYPE_CREATE.equalsIgnoreCase(i18nActionCmd)) {
				i18nActionCmd = rtiMsgBundle.rtiCommand_create();
			} else if (COMMAND_ACTIONTYPE_MODIFY.equalsIgnoreCase(i18nActionCmd)) {
				i18nActionCmd = rtiMsgBundle.rtiCommand_modify();
			} else if (COMMAND_ACTIONTYPE_CALL.equalsIgnoreCase(i18nActionCmd)) {
				i18nActionCmd = rtiMsgBundle.rtiCommand_call();
		}

			return i18nActionCmd;
		}
		
		
				
		/**
		 * Update the models on basis of drag and drop operation.
		 */
		public void updateModelsOnDnD() {
			BuilderSubClauseImpl sourceModel = (BuilderSubClauseImpl) this.getSourceModel();
			BuilderSubClauseImpl targetModel = (BuilderSubClauseImpl) this.getTargetModel();
			Object dndFilter = this.getDndFilter();
			int dndPoistion = this.getDropPosition();
			if (dndFilter instanceof SingleFilterImpl) {
				SingleFilterImpl filter = (SingleFilterImpl) dndFilter;
				sourceModel.removeFilter(filter, false);
				if (targetModel.getFilters().size() > dndPoistion) {
					targetModel.addFilter(dndPoistion, filter);
				} else {
					targetModel.addFilter(filter, false);
				}
			} else if (dndFilter instanceof MultiFilterImpl) {
				MultiFilterImpl filter = (MultiFilterImpl) dndFilter;
				sourceModel.removeFilter(filter, false);
				if (targetModel.getFilters().size() > dndPoistion) {
					targetModel.addFilter(dndPoistion, filter);
				} else {
					targetModel.addFilter(filter, false);
				}
			}
			this.setSourceModel(null);
			this.setTargetModel(null);
			this.setDropPosition(-1);
			this.setDndFilter(null);
			if (!this.isDirty()) {
				this.makeDirty();
			}
		}

		/**
		 * @return the sourceModel
		 */
		public Object getSourceModel() {
			return this.sourceModel;
		}

		/**
		 * @param sourceModel
		 *            the sourceModel to set
		 */
		public void setSourceModel(Object sourceModel) {
			this.sourceModel = sourceModel;
		}

		/**
		 * @return the targetModel
		 */
		public Object getTargetModel() {
			return this.targetModel;
		}

		/**
		 * @param targetModel
		 *            the targetModel to set
		 */
		public void setTargetModel(Object targetModel) {
			this.targetModel = targetModel;
		}

		/**
		 * @return the dndFilter
		 */
		public Object getDndFilter() {
			return this.dndFilter;
		}

		/**
		 * @param dndFilter
		 *            the dndFilter to set
		 */
		public void setDndFilter(Object dndFilter) {
			this.dndFilter = dndFilter;
		}

		/**
		 * @return the dropPosition
		 */
		public int getDropPosition() {
			return this.dropPosition;
		}

		/**
		 * @param dropPosition
		 *            the dropPosition to set
		 */
		public void setDropPosition(int dropPosition) {
			this.dropPosition = dropPosition;
		}

		/**
		 * @return the isCommandBuilder
		 */
		public boolean isCommandBuilder() {
			return this.isCommandBuilder;
		}

		/**
		 * @param isCommandBuilder
		 *            the isCommandBuilder to set
		 */
		public void setCommandBuilder(boolean isCommandBuilder) {
			this.isCommandBuilder = isCommandBuilder;
		}

		/**
		 * @return the showPropertyPane
		 */
		public boolean isShowPropertyPane() {
			return this.showPropertyPane;
		}

		/**
		 * @param showPropertyPane
		 *            the showPropertyPane to set
		 */
		public void setShowPropertyPane(boolean showPropertyPane) {
			this.showPropertyPane = showPropertyPane;
		}

		public void setRtiPropertyTab(RuleTemplateInstancePropertyTab propertyTab) {
			this.propertyTab = propertyTab;
		}

		public RuleTemplateInstancePropertyTab getRtiPropertyTab() {
			return this.propertyTab;
		}

	}
}
