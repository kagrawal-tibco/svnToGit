package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentBaseFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentFunctionsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentShutdownFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentStartupFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig.EntityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.LDMConnection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList.AlertConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.InferenceEngine;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/*
@author ssailapp
@date Dec 7, 2009 4:14:39 PM
 */

public class AgentBlock extends ClusterConfigBlock {
	private Tree trAgents;
	private Button bAddAgent, bAdd, bRemove, bUp, bDown;
	private static final String REFERENCE_NODE = " [Reference]";
	
	public AgentBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		super(page, modelmgr);
		EXPAND_LEVEL = 2;
	}
	
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(CacheAgent.class, new NodeCacheAgentPage(modelmgr, viewer));
		detailsPart.registerPage(DashboardAgent.class, new NodeDashboardAgentPage(modelmgr, viewer));
		detailsPart.registerPage(InfAgent.class, new NodeInfAgentPage(modelmgr, viewer));
		detailsPart.registerPage(MMAgent.class, new NodeMMAgentPage(modelmgr, viewer));
		detailsPart.registerPage(ProcessAgent.class, new NodeProcessAgentPage(modelmgr, viewer));
		detailsPart.registerPage(QueryAgent.class, new NodeQueryAgentPage(modelmgr, viewer));
		detailsPart.registerPage(AgentRulesGrp.class, new NodeAgentRulesGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(AgentRulesGrpElement.class, new NodeRulesGrpPage(modelmgr, viewer));
		detailsPart.registerPage(AgentDestinationsGrp.class, new NodeAgentDestinationsGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(AgentDestinationsGrpElement.class, new NodeDestinationsGrpPage(modelmgr, viewer));
		detailsPart.registerPage(AgentStartupFunctionsGrp.class, new NodeAgentStartupFunctionsPage(modelmgr, viewer));
		detailsPart.registerPage(AgentShutdownFunctionsGrp.class, new NodeAgentShutdownFunctionsGrpPage(modelmgr, viewer));
		detailsPart.registerPage(AgentFunctionsGrpElement.class, new NodeFunctionsGrpPage(modelmgr, viewer));
		detailsPart.registerPage(AgentProcessesGrp.class, new NodeAgentProcessesGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(AgentProcessesGrpElement.class, new NodeProcessesGrpPage(modelmgr, viewer));
		detailsPart.registerPage(AlertConfigList.class, new NodeAlertConfigListPage(modelmgr, viewer));
		detailsPart.registerPage(AlertConfig.class, new NodeAlertConfigPage(modelmgr, viewer));
		detailsPart.registerPage(RuleConfigList.class, new NodeRuleConfigListPage(modelmgr, viewer));
		detailsPart.registerPage(ClusterMember.class, new NodeClusterMemberPage(modelmgr, viewer));
		detailsPart.registerPage(SetProperty.class, new NodeSetPropertyPage(modelmgr, viewer));
		detailsPart.registerPage(ActionConfigList.class, new NodeActionConfigListPage(modelmgr, viewer));
		detailsPart.registerPage(ActionConfig.class, new NodeActionConfigPage(modelmgr, viewer));
		detailsPart.registerPage(InferenceEngine.class, new NodeAgentInferenceEnginePage(modelmgr, viewer));
		detailsPart.registerPage(LiveViewAgent.class, new NodeLiveViewAgentPage(modelmgr, viewer));
		detailsPart.registerPage(LDMConnection.class, new NodeLDMConnectionConfigPage(modelmgr, viewer));
		detailsPart.registerPage(EntitySetConfig.class, new NodeEntitySetConfigPage(modelmgr, viewer));
		detailsPart.registerPage(EntityConfig.class, new NodeEntityConfigPage(modelmgr, viewer));
		super.registerPages(detailsPart);
	}

	@Override
	public void createContent(IManagedForm managedForm) {
		super.createContent(managedForm);
		sashForm.setWeights(new int[]{40,60});
	}
	
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Agent Classes");
		section.setDescription("Define the agent classes and their configuration");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trAgents = toolkit.createTree(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		trAgents.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAddAgent = toolkit.createButton(buttonsClient, "Add Agent", SWT.PUSH);
		bAddAgent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAddAgent.addListener(SWT.Selection, getAddAgentClassListener(parent.getShell()));
		bAdd = toolkit.createButton(buttonsClient, "Add", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getAddListener(parent.getShell()));
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getAgentBlockRemoveListener());
		toolkit.createLabel(buttonsClient, "");
		bUp = toolkit.createButton(buttonsClient, "Move Up", SWT.PUSH);
		bUp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bUp.addListener(SWT.Selection, getMoveUpFunctionsListener(parent.getShell()));
		bDown = toolkit.createButton(buttonsClient, "Move Down", SWT.PUSH);
		bDown.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bDown.addListener(SWT.Selection, getMoveDownFunctionsListener(parent.getShell()));
		
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trAgents);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		addDoubleClickListener(viewer);
		
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		//registerContextMenu(); //TODO - to be enabled
		registerSelectionListener();
		resetActionButtons();
		BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
	}
	
	private void registerContextMenu() {
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				if(viewer.getSelection().isEmpty()) {
					return;
				}

				if(viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					if (selObj != null) {
						if (selObj instanceof DashInfProcQueryAgent) {
							if (selObj instanceof InfAgent) {
								manager.add(new AddRuleGroupAction((InfAgent)selObj, null));
							}
							manager.add(new AddDestinationGroupAction((DashInfProcQueryAgent)selObj, null));
							//TODO manager.add(new AddStartupFunctionsGroupAction((InfQueryAgent)selObj, null));
							//TODO manager.add(new AddShutdownFunctionsGroupAction((InfQueryAgent)selObj, null));
						} else if (selObj instanceof AgentRulesGrp) {
							AgentRulesGrp agentRulesGrp = (AgentRulesGrp)selObj;
							DashInfProcAgent infAgent = agentRulesGrp.parentAgent;
							manager.add(new AddRuleGroupAction(infAgent, agentRulesGrp));
						}  else if (selObj instanceof RulesGrp) {
							RulesGrp rulesGrp = (RulesGrp)selObj;
							ITreeSelection trSelection = ((ITreeSelection)viewer.getSelection());
							AgentRulesGrp agentRulesGrp = (AgentRulesGrp) trSelection.getPaths()[0].getParentPath().getLastSegment();
							DashInfProcAgent infAgent = agentRulesGrp.parentAgent;
							manager.add(new RemoveRuleGroupAction(infAgent, rulesGrp));
						} else if (selObj instanceof AgentDestinationsGrp) {
							AgentDestinationsGrp agentDestinationsGrp = (AgentDestinationsGrp)selObj;
							DashInfProcQueryAgent agent = agentDestinationsGrp.parentAgent;
							manager.add(new AddDestinationGroupAction(agent, agentDestinationsGrp));
						}  else if (selObj instanceof DestinationsGrp) {
							DestinationsGrp destinationsGrp = (DestinationsGrp)selObj;
							ITreeSelection trSelection = ((ITreeSelection)viewer.getSelection());
							AgentDestinationsGrp agentDestinationsGrp = (AgentDestinationsGrp) trSelection.getPaths()[0].getParentPath().getLastSegment();
							DashInfProcQueryAgent agent = agentDestinationsGrp.parentAgent;
							manager.add(new RemoveDestinationGroupAction(agent, destinationsGrp));
						} 
					}
				}
			}
		});
		menuMgr.setRemoveAllWhenShown(true);
		viewer.getControl().setMenu(menu);
	}
	
	@Override
	protected void resetActionButtons() {
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
	}
	
	@Override
	protected void enableActionButtons(Object selObj) {
		bAdd.setEnabled(true);
		bRemove.setEnabled(true);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
		if (selObj instanceof AgentClass) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof AgentRulesGrp) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof RulesGrp) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof RuleElement) {
			bAdd.setEnabled(false);
			if (selObj instanceof Rule && (((Rule)selObj).parent instanceof RulesGrp)) { //This is part of a reference
				bRemove.setEnabled(false);
			}
		} else if (selObj instanceof AgentDestinationsGrp) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof DestinationsGrp) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof DestinationElement) {
			bAdd.setEnabled(false);
			if (selObj instanceof Destination && (((Destination)selObj).parent instanceof DestinationsGrp)) { //This is part of a reference
				bRemove.setEnabled(false);
			}
		} else if (selObj instanceof AgentBaseFunctionsGrp) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof FunctionsGrp) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof FunctionElement) {
			bAdd.setEnabled(false);
			if (selObj instanceof Function && (((Function)selObj).parent instanceof FunctionsGrp)) { //This is part of a reference
				bRemove.setEnabled(false);
			} else {
				if (modelmgr.canMoveUpFunction((FunctionElement)selObj))
					bUp.setEnabled(true);
				if (modelmgr.canMoveDownFunction((FunctionElement)selObj))
					bDown.setEnabled(true);	
			}
		} else if (selObj instanceof AlertConfigList) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof AlertConfig) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof RuleConfigList) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof SetProperty) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof ActionConfigList) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof ActionConfig) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof AgentProcessesGrp) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof ProcessesGrp) {
			bAdd.setEnabled(false);
		} else if (selObj instanceof ProcessElement) {
			bAdd.setEnabled(false);
			if (selObj instanceof Process && (((Process)selObj).parent instanceof ProcessesGrp)) { //This is part of a reference
				bRemove.setEnabled(false);
			}
		} else if (selObj instanceof LDMConnection) {
			bAdd.setEnabled(false);
			bRemove.setEnabled(false);
		} else if (selObj instanceof EntitySetConfig) {
			bRemove.setEnabled(false);
		} else if (selObj instanceof EntityConfig) {
			bAdd.setEnabled(false);
		}
	}
	
	class AddRuleGroupAction extends Action {
		private DashInfProcAgent agent;
		private AgentRulesGrp agentRulesGrp;
		public AddRuleGroupAction(DashInfProcAgent agent, AgentRulesGrp agentRulesGrp) {
			super("Add Rules Collection", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_ADD));
			this.agent = agent;
			this.agentRulesGrp = agentRulesGrp;
		}
	
		public void run() {
			addAgentRulesGroup(agent);
		}
	}
	
	private RulesGrp addAgentRulesGroup(DashInfProcAgent agent) {
		NewAgentRulesGrpDialog dialog = new NewAgentRulesGrpDialog(trAgents.getShell());
		dialog.open(modelmgr.getRulesGroup(), agent.agentRulesGrpObj.agentRules);
		ArrayList<RulesGrp> selRulesGrp = dialog.getSelectedRulesGrp();
		boolean updated = false;
		for (RulesGrp rulesGrp: selRulesGrp) {
			updated |= modelmgr.updateAgentRulesGroup(agent, rulesGrp);
		}
		if (updated && selRulesGrp.size()>0)
			return ((RulesGrp)selRulesGrp.get(0));
		return null;
	}

	class RemoveRuleGroupAction extends Action {
		private DashInfProcAgent agent;
		private RulesGrp rulesGrp;
		public RemoveRuleGroupAction(DashInfProcAgent agent, RulesGrp rulesGrp) {
			super("Remove Rules Collection", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE));
			this.agent = agent;
			this.rulesGrp = rulesGrp;
		}
		
		public void run() {
			//modelmgr.removeAgentRulesGrp(agent, rulesGrp); //TODO
			BlockUtil.refreshViewer(viewer);
		}
	}
	
	class AddDestinationGroupAction extends Action {
		private DashInfProcQueryAgent agent;
		private AgentDestinationsGrp agentDestinationsGrp;
		public AddDestinationGroupAction(DashInfProcQueryAgent agent, AgentDestinationsGrp agentDestinationsGrp) {
			super("Add Input Destination", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_ADD));
			this.agent = agent;
			this.agentDestinationsGrp = agentDestinationsGrp;
		}
	
		public void run() {
			addAgentDestinationsGroup(agent);
		}
	}
	
	private DestinationsGrp addAgentDestinationsGroup(DashInfProcQueryAgent agent) {
		NewAgentDestinationsGrpDialog dialog = new NewAgentDestinationsGrpDialog(trAgents.getShell());
		dialog.open(modelmgr.getDestinationsGroup(), agent.agentDestinationsGrpObj.agentDestinations);
		ArrayList<DestinationsGrp> selDestinationsGrp = dialog.getSelectedDestinations();
		boolean updated = false;
		for (DestinationsGrp destinationsGrp: selDestinationsGrp) {
			updated |= modelmgr.updateAgentDestinationsGroup(agent, destinationsGrp);
		}
		if (updated && selDestinationsGrp.size()>0)
			return ((DestinationsGrp)selDestinationsGrp.get(0));
		return null;
	}
	
	class RemoveDestinationGroupAction extends Action {
		private DashInfProcQueryAgent agent;
		private DestinationsGrp destinationsGrp;
		public RemoveDestinationGroupAction(DashInfProcQueryAgent agent, DestinationsGrp destinationsGrp) {
			super("Remove Input Destination", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE));
			this.agent = agent;
			this.destinationsGrp = destinationsGrp;
		}
		
		public void run() {
			//modelmgr.removeAgentDestinationsGrp(destinationsGrp); //TODO
			BlockUtil.refreshViewer(viewer);
		}
	}
	
	private FunctionsGrp addAgentStartupFunctionsGroup(DashInfProcQueryAgent agent) {
		NewAgentFunctionsGrpDialog dialog = new NewAgentFunctionsGrpDialog(trAgents.getShell());
		dialog.open(modelmgr.getFunctionsGroup(true), agent.agentStartupFunctionsGrpObj.agentFunctions);
		ArrayList<FunctionsGrp> selFunctionsGrp = dialog.getSelectedFunctionsGrp();
		boolean updated = false;
		for (FunctionsGrp functionsGrp: selFunctionsGrp) {
			updated |= modelmgr.updateAgentStartupFunctionsGroup(agent, functionsGrp);
		}
		if (updated && selFunctionsGrp.size()>0)
			return ((FunctionsGrp)selFunctionsGrp.get(0));
		return null;
	}
	
	private FunctionsGrp addAgentShutdownFunctionsGroup(DashInfProcQueryAgent agent) {
		NewAgentFunctionsGrpDialog dialog = new NewAgentFunctionsGrpDialog(trAgents.getShell());
		dialog.open(modelmgr.getFunctionsGroup(true), agent.agentShutdownFunctionsGrpObj.agentFunctions);
		ArrayList<FunctionsGrp> selFunctionsGrp = dialog.getSelectedFunctionsGrp();
		boolean updated = false;
		for (FunctionsGrp functionsGrp: selFunctionsGrp) {
			updated |= modelmgr.updateAgentShutdownFunctionsGroup(agent, functionsGrp);
		}
		if (updated && selFunctionsGrp.size()>0)
			return ((FunctionsGrp)selFunctionsGrp.get(0));
		return null;
	}
	
	private Listener getAddAgentClassListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				NewAgentClassDialog dlg = new NewAgentClassDialog(shell, modelmgr);
				dlg.open();
				String name = dlg.getName();
				if (name != null) {
					String type = dlg.getType();
					AgentClass agent = modelmgr.addAgent(name, type);
					BlockUtil.refreshViewer(viewer);
				}
			}
		};
		return listener;	
	}
	
	private Listener getAddListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					Object newObj = null;
					if (selObj != null) {
						if (selObj instanceof AgentRulesGrp) {
							RuleSelectionDialog dialog = new RuleSelectionDialog(trAgents.getShell(), "");
							dialog.open(modelmgr.project, modelmgr.getRulesGroupNames(), modelmgr.getAgentRulesGrpNames(((AgentRulesGrp)selObj).parentAgent, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentRules(((AgentRulesGrp)selObj).parentAgent, selItems);
						} else	if (selObj instanceof AgentDestinationsGrp) {
							DestinationSelectionDialog dialog = new DestinationSelectionDialog(trAgents.getShell(), "");
							dialog.open(modelmgr.project, modelmgr.getDestinationsGroupNames(), modelmgr.getAgentDestinationsGrpNames((((AgentDestinationsGrp)selObj).parentAgent).agentDestinationsGrpObj, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentDestinations(((AgentDestinationsGrp)selObj).parentAgent, selItems);
						} else if (selObj instanceof AgentStartupFunctionsGrp && isProcessAgent(((AgentStartupFunctionsGrp)selObj).parentAgent)) {
							AgentStartupFunctionsGrp agentFnGroup = (AgentStartupFunctionsGrp)selObj;
							ProcessSelectionDialog dialog = new ProcessSelectionDialog(trAgents.getShell(), "", false, true, false);
							dialog.open(modelmgr.project, modelmgr.getProcessesGroupNames(), modelmgr.getAgentStartupFunctionsGrpNames(agentFnGroup.parentAgent));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentFunctions(agentFnGroup.parentAgent, selItems, true);
						} else if (selObj instanceof AgentStartupFunctionsGrp) {
							AgentStartupFunctionsGrp agentFnGroup = (AgentStartupFunctionsGrp)selObj;
							FunctionSelectionDialog dialog = new FunctionSelectionDialog(trAgents.getShell(), "");
							dialog.open(modelmgr.project, modelmgr.getFunctionsGroupNames(), modelmgr.getAgentStartupFunctionsGrpNames(agentFnGroup.parentAgent));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentFunctions(agentFnGroup.parentAgent, selItems, true);
						} else if (selObj instanceof AgentShutdownFunctionsGrp && isProcessAgent(((AgentShutdownFunctionsGrp)selObj).parentAgent)) {
							AgentShutdownFunctionsGrp agentFnGroup = (AgentShutdownFunctionsGrp)selObj;
							ProcessSelectionDialog dialog = new ProcessSelectionDialog(trAgents.getShell(), "", false, true, false);
							dialog.open(modelmgr.project, modelmgr.getProcessesGroupNames(), modelmgr.getAgentShutdownFunctionsGrpNames(agentFnGroup.parentAgent));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentFunctions(agentFnGroup.parentAgent, selItems, false);
						} else if (selObj instanceof AgentShutdownFunctionsGrp) {
							AgentShutdownFunctionsGrp agentFnGroup = (AgentShutdownFunctionsGrp)selObj;
							FunctionSelectionDialog dialog = new FunctionSelectionDialog(trAgents.getShell(), "");
							dialog.open(modelmgr.project, modelmgr.getFunctionsGroupNames(), modelmgr.getAgentShutdownFunctionsGrpNames(agentFnGroup.parentAgent));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentFunctions(agentFnGroup.parentAgent, selItems, false);
						} else	if (selObj instanceof AgentProcessesGrp) {
							ProcessSelectionDialog dialog = new ProcessSelectionDialog(trAgents.getShell(), "", true, false, true);
							dialog.open(modelmgr.project, modelmgr.getProcessesGroupNames(), modelmgr.getAgentProcessesGrpNames(((AgentProcessesGrp)selObj).parentAgent, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addAgentProcesses(((AgentProcessesGrp)selObj).parentAgent, selItems);
						} else if (selObj instanceof AlertConfigList) {
							newObj = modelmgr.addAlertConfig((AlertConfigList)selObj);
						} else if (selObj instanceof RuleConfigList) {
							newObj = modelmgr.addClusterMember((RuleConfigList)selObj);
						} else if (selObj instanceof ClusterMember) {
							newObj = modelmgr.addSetProperty((ClusterMember)selObj);
						} else if (selObj instanceof ActionConfigList) {
							newObj = modelmgr.addActionConfig((ActionConfigList)selObj);
						} else if (selObj instanceof EntitySetConfig) {
							newObj = modelmgr.addEntityConfig((EntitySetConfig)selObj);
						}
						BlockUtil.refreshViewer(viewer, selObj, newObj);
					}
				}
			}
		};
		return listener;
	}

	private Listener getAgentBlockRemoveListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					removeAgentBlockItem(selObj);
				}
			}
		};
		return listener;
	}
	
	private void removeAgentBlockItem(Object selObj) {
		if (selObj != null) {
			if (selObj instanceof AgentClass) {
				modelmgr.removeAgent((AgentClass)selObj);
			} else if (selObj instanceof AgentRulesGrpElement) {
				modelmgr.removeAgentRulesGrp((AgentRulesGrpElement)selObj);
			} else if (selObj instanceof Rule) {
				modelmgr.removeRule((Rule) selObj);
			} else if (selObj instanceof AgentDestinationsGrpElement) {
				modelmgr.removeAgentDestinationsGrp((AgentDestinationsGrpElement)selObj);
			} else if (selObj instanceof Destination) {
				modelmgr.removeDestination((Destination) selObj);
			} else if (selObj instanceof AgentFunctionsGrpElement) {
				modelmgr.removeAgentFunctionsGrp((AgentFunctionsGrpElement)selObj);
			} else if (selObj instanceof Function) {
				modelmgr.removeFunction((Function) selObj);
			} else if (selObj instanceof Process) {
				modelmgr.removeProcess((Process) selObj);
			} else if (selObj instanceof AgentProcessesGrpElement) {
				modelmgr.removeAgentProcessessGrp((AgentProcessesGrpElement) selObj);
			}else if (selObj instanceof AlertConfig) {
				modelmgr.removeAlertConfig((AlertConfig)selObj);
			} else if (selObj instanceof ClusterMember) {
				modelmgr.removeClusterMember((ClusterMember)selObj);
			} else if (selObj instanceof SetProperty) {
				modelmgr.removeSetProperty((SetProperty)selObj);
			} else if (selObj instanceof ActionConfig) {
				modelmgr.removeActionConfig((ActionConfig)selObj);
			} else if (selObj instanceof EntityConfig) {
				modelmgr.removeEntityConfig((EntityConfig)selObj);
			}
			BlockUtil.refreshViewer(viewer);
			resetActionButtons();
		}
	}

	class MasterContentProvider extends ClusterConfigBlockMasterContentProvider {

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof AgentClass) {
				AgentClass agent = (AgentClass)element;
				ArrayList<Object> nodes = new ArrayList<Object>();
				if (isInfAgent(agent) && !(agent instanceof InferenceEngine)) {
					InfAgent infAgent = (InfAgent) agent;
					nodes.add(infAgent.agentRulesGrpObj);
					nodes.add(infAgent.agentDestinationsGrpObj);
					nodes.add(infAgent.agentStartupFunctionsGrpObj);
					nodes.add(infAgent.agentShutdownFunctionsGrpObj);
				} else if (isCacheAgent(agent)) {
				} else if (isQueryAgent(agent)) {
					QueryAgent queryAgent = (QueryAgent) agent;
					nodes.add(queryAgent.agentDestinationsGrpObj);
					nodes.add(queryAgent.agentStartupFunctionsGrpObj);
					nodes.add(queryAgent.agentShutdownFunctionsGrpObj);
				} else if (isDashboardAgent(agent)) {
					DashboardAgent dashAgent = (DashboardAgent) agent;
					//nodes.add(dashAgent.agentRulesGrpObj);
					nodes.add(dashAgent.agentDestinationsGrpObj);
					nodes.add(dashAgent.agentStartupFunctionsGrpObj);
					nodes.add(dashAgent.agentShutdownFunctionsGrpObj);
				} else if (isMMAgent(agent)) {
					MMAgent mmAgent = (MMAgent) agent;
					nodes.add(mmAgent.alertConfigList);
					nodes.add(mmAgent.ruleConfigList);
					nodes.add(mmAgent.actionConfigList);
				} else if (isProcessAgent(agent)) {
					ProcessAgent processAgent = (ProcessAgent) agent;
					nodes.add(processAgent.agentProcessesGrpObj);
					nodes.add(processAgent.agentDestinationsGrpObj);
					nodes.add(processAgent.agentStartupFunctionsGrpObj);
					nodes.add(processAgent.agentShutdownFunctionsGrpObj);
					//nodes.add(processAgent.infEngine);
				} else if (isLiveViewAgent(agent)) {
					LiveViewAgent lvAgent = (LiveViewAgent) agent;
					nodes.add(lvAgent.ldmConnection);
					nodes.add(lvAgent.entitySetConfig);
				}
				return nodes.toArray(new Object[0]);
			} else if (element instanceof AgentRulesGrp) { 
				return modelmgr.getAgentRules((AgentRulesGrp)element);
			} else if (element instanceof AgentDestinationsGrp) { 
				return modelmgr.getAgentDestinations((AgentDestinationsGrp)element);
			} else if (element instanceof AgentStartupFunctionsGrp) { 
				return modelmgr.getAgentFunctions((DashInfProcQueryAgent.AgentStartupFunctionsGrp)element);
			} else if (element instanceof AgentShutdownFunctionsGrp) { 
				return modelmgr.getAgentFunctions((DashInfProcQueryAgent.AgentShutdownFunctionsGrp)element);
			} else if (element instanceof AgentProcessesGrp) { 
				return modelmgr.getAgentProcesses((AgentProcessesGrp)element);
			} else if (element instanceof AlertConfigList) {
				return ((AlertConfigList)element).alertConfigs.toArray(new AlertConfig[0]);
			} else if (element instanceof RuleConfigList) {
				return ((RuleConfigList)element).clusterMembers.toArray(new ClusterMember[0]);
			} else if (element instanceof ClusterMember) {
				return ((ClusterMember)element).setProperties.toArray(new SetProperty[0]);
			} else if (element instanceof ActionConfigList) {
				return ((ActionConfigList)element).actionConfigs.toArray(new ActionConfig[0]);
			} else if (element instanceof EntitySetConfig) {
				return ((EntitySetConfig)element).entityElements.toArray(new EntityConfig[0]);
			}
			return (super.getChildren(element));
		}

		@Override
		public Object[] getElements(Object inputElement) {
			ArrayList<AgentClass> allAgents = modelmgr.getAgentClasses();
			for (Iterator<AgentClass> it=allAgents.iterator(); it.hasNext();) {
				AgentClass agent = (AgentClass) it.next();
				if (AddonUtil.isExpressEdition()) {
					if (isCacheAgent(agent) || isMMAgent(agent))
						it.remove();
				}
				if (isQueryAgent(agent) && !modelmgr.isQueryAgentEnabled())
					it.remove();
				if (isDashboardAgent(agent) && !modelmgr.isDashboardAgentEnabled())
					it.remove();
				if (isProcessAgent(agent) && !modelmgr.isProcessAgentEnabled())
					it.remove();
			}
			return allAgents.toArray(new AgentClass[0]);
			//return modelmgr.getAgentClasses().toArray(new AgentClass[0]);
		}
	}

	class MasterLabelProvider extends ClusterConfigBlockMasterLabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof InfAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_INF_AGENT));
			} else if (element instanceof CacheAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_CACHE_AGENT));
			} else if (element instanceof QueryAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_QUERY_AGENT));
			} else if (element instanceof DashboardAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DASHBOARD_AGENT));
			} else if (element instanceof MMAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_MM_AGENT));
			} else if (element instanceof ProcessAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_AGENT));
			} else if (element instanceof AgentClass) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_AGENT));
			} else if (element instanceof AgentRulesGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof AgentRulesGrpElement) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE));
			} else if (element instanceof AgentDestinationsGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof AgentDestinationsGrpElement) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE));
			} else if (element instanceof AgentStartupFunctionsGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof AgentShutdownFunctionsGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof AgentFunctionsGrpElement) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE));
			} else if (element instanceof AlertConfigList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof AlertConfig) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_ALERT_CONFIG));
			} else if (element instanceof RuleConfigList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof ClusterMember) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_CLUSTER_MEMBER));
			} else if (element instanceof SetProperty) {
				//return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_SET_PROPERTY));
			} else if (element instanceof ActionConfigList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof ActionConfig) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_ACTION_CONFIG));
			} else if (element instanceof AgentProcessesGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof AgentProcessesGrpElement) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE));
			} else if (element instanceof InferenceEngine) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_INF_AGENT));
			} else if (element instanceof LiveViewAgent) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DASHBOARD_AGENT));
			} else if (element instanceof EntitySetConfig) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof EntityConfig) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_ALERT_CONFIG));
			} else if (element instanceof LDMConnection) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_ACTION_CONFIG));
			}
			return (super.getImage(element));
		}

		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof InferenceEngine) {
				return ("Inference Engine");
			} else if (element instanceof AgentClass) {
				AgentClass agent = (AgentClass)element;
				return (getAgentDisplayName(agent.name, agent.type));
			} else if (element instanceof InfAgent.AgentRulesGrp) {
				return ("Rule Collections");
			} else if (element instanceof AgentRulesGrpElement) {
				return ((AgentRulesGrpElement)element).rulesGrp.id;
			} else if (element instanceof AgentDestinationsGrp) {
				return ("Input Destination Collections");
			} else if (element instanceof AgentDestinationsGrpElement) {
				return ((AgentDestinationsGrpElement)element).destinationsGrp.id;
			} else if (element instanceof AgentStartupFunctionsGrp && isProcessAgent(((AgentStartupFunctionsGrp)element).parentAgent)) {
				return ("Startup Processes");
			} else if (element instanceof AgentStartupFunctionsGrp) {
				return ("Startup Functions");
			} else if (element instanceof AgentShutdownFunctionsGrp && isProcessAgent(((AgentShutdownFunctionsGrp)element).parentAgent)) {
				return ("Shutdown Processes");
			} else if (element instanceof AgentShutdownFunctionsGrp) {
				return ("Shutdown Functions");
			} else if (element instanceof AgentFunctionsGrpElement) {
				return ((AgentFunctionsGrpElement)element).functionsGrp.id;
			} else if (element instanceof AgentProcessesGrp) {
				return ("Process Definitions");
			} else if (element instanceof AgentProcessesGrpElement) {
				return ((AgentProcessesGrpElement)element).processesGrp.id;
			} else if (element instanceof AlertConfigList) {
				return ("Alert Configurations");
			} else if (element instanceof AlertConfig) {
				return ((AlertConfig)element).id;
			} else if (element instanceof RuleConfigList) {
				return ("Health Metric Rule Configurations");
			} else if (element instanceof ClusterMember) {
				return ((ClusterMember)element).id;
			} else if (element instanceof SetProperty) {
				return ((SetProperty)element).id;
			} else if (element instanceof ActionConfigList) {
				return ("Action Configurations");
			} else if (element instanceof ActionConfig) {
				return ((ActionConfig)element).id;
			} else if (element instanceof EntitySetConfig) {
				return ("Entity Filter Configurations");
			} else if (element instanceof EntityConfig) {
				return ((EntityConfig)element).id;
			} else if (element instanceof LDMConnection) {
				return ("LDM Connection Configuration");
			}
			return (super.getText(element));
		}
		
		private String getAgentDisplayName(String name, String type) {
			return (name + " (" + type + ") ");
		}
	}

	public static boolean isInfAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_INFERENCE))
			return true;
		return false;	
	}

	public static boolean isCacheAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_CACHE))
			return true;
		return false;	
	}

	public static boolean isQueryAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_QUERY))
			return true;
		return false;	
	}

	public static boolean isDashboardAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_DASHBOARD))
			return true;
		return false;	
	}
	
	public static boolean isMMAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_MM))
			return true;
		return false;
	}
	
	public static boolean isProcessAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_PROCESS))
			return true;
		return false;
	}
	
	public static boolean isLiveViewAgent(AgentClass agent) {
		if (agent.type.equals(AgentClass.AGENT_TYPE_LIVEVIEW))
			return true;
		return false;
	}
}
