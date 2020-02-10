package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
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


import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfigsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/*
@author ssailapp
@date Dec 16, 2009 1:43:01 PM
 */

public class GroupsBlock extends ClusterConfigBlock {
	private Tree trGroups;
	private Button bAdd, bRemove, bUp, bDown,bCopy;
	private final String strCopy="copy_";
	public GroupsBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		super(page, modelmgr);
		EXPAND_LEVEL = 3;
	}
	
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(RuleElementsList.class, new NodeRulesGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(RulesGrp.class, new NodeRulesGrpPage(modelmgr, viewer));
		detailsPart.registerPage(DestinationElementsList.class, new NodeDestinationsGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(DestinationsGrp.class, new NodeDestinationsGrpPage(modelmgr, viewer));
		detailsPart.registerPage(FunctionElementsList.class, new NodeFunctionsGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(FunctionsGrp.class, new NodeFunctionsGrpPage(modelmgr, viewer));
		detailsPart.registerPage(ProcessElementsList.class, new NodeProcessesGrpListPage(modelmgr, viewer));
		detailsPart.registerPage(ProcessesGrp.class, new NodeProcessesGrpPage(modelmgr, viewer));
		detailsPart.registerPage(LogConfigsList.class, new NodeLogConfigsListPage(modelmgr, viewer));
		detailsPart.registerPage(LogConfig.class, new NodeLogConfigPage(modelmgr, viewer));
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
		Section section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.setText("Collections");
		//section.setDescription("Define the configuration for Rule Collections, Destination Collections, Function Collections and Log Configurations.");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trGroups = toolkit.createTree(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		trGroups.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAdd = toolkit.createButton(buttonsClient, "Add", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getGroupsBlockAddListener(parent.getShell()));
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getGroupsBlockRemoveListener());
		bRemove.setEnabled(false);
		toolkit.createLabel(buttonsClient, "");
		bUp = toolkit.createButton(buttonsClient, "Move Up", SWT.PUSH);
		bUp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bUp.addListener(SWT.Selection, getMoveUpFunctionsListener(parent.getShell()));
		bUp.setEnabled(false);
		bDown = toolkit.createButton(buttonsClient, "Move Down", SWT.PUSH);
		bDown.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bDown.addListener(SWT.Selection, getMoveDownFunctionsListener(parent.getShell()));
		bDown.setEnabled(false);
		bCopy =toolkit.createButton(buttonsClient, "Copy", SWT.PUSH);
		bCopy.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bCopy.addListener(SWT.Selection, getGroupsBlockCopyListener());
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trGroups);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		addDoubleClickListener(viewer);
		
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		//registerContextMenu(); //TODO - To be enabled 
		registerSelectionListener();
		resetActionButtons();
		BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
	}
	
	@SuppressWarnings("unused")
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
						if (selObj instanceof RuleElementsList) {
							manager.add(new AddRuleGroupAction((RuleElementsList)selObj));
						} else if (selObj instanceof RulesGrp) {
							manager.add(new AddRuleAction((RulesGrp)selObj));
							manager.add(new RemoveGroupsAction("Remove Rule Group", selObj));
						} else if (selObj instanceof Rule) {
							manager.add(new RemoveGroupsAction("Remove Rule", selObj));
						} else if (selObj instanceof DestinationElementsList) {
							manager.add(new AddDestinationGroupAction((DestinationElementsList)selObj));
						} else if (selObj instanceof DestinationsGrp) {
							manager.add(new AddDestinationAction((DestinationsGrp)selObj));
							manager.add(new RemoveGroupsAction("Remove Input Destination Group", selObj));
						} else if (selObj instanceof Destination) {
							manager.add(new RemoveGroupsAction("Remove Input Destination", selObj));
						}
					}
				}
			}
		});
		menuMgr.setRemoveAllWhenShown(true);
		viewer.getControl().setMenu(menu);
	}
	
	class AddRuleGroupAction extends Action {
		private RuleElementsList rulesGrpList;
		public AddRuleGroupAction(RuleElementsList rulesGrpList) {
			super("Add Rules Group", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_ADD));
			this.rulesGrpList = rulesGrpList;
		}
		public void run() {
			RulesGrp rulesGrp = modelmgr.addRulesGrp();
			BlockUtil.refreshViewer(viewer, rulesGrpList, rulesGrp);
		}
	}

	class AddRuleAction extends Action {
		@SuppressWarnings("unused")
		private RulesGrp rulesGrp;
		public AddRuleAction(RulesGrp rulesGrp) {
			super("Add Rules", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_ADD));
			this.rulesGrp = rulesGrp;
		}
		public void run() {
			//Rule rule = modelmgr.addRule(rulesGrp);
			//BlockUtil.refreshViewer(viewer, rulesGrp, rule);
		}
	}
	
	class AddDestinationGroupAction extends Action {
		private DestinationElementsList destinationsGrpList;
		public AddDestinationGroupAction(DestinationElementsList destinationsGrpList) {
			super("Add Destinations Group", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_ADD));
			this.destinationsGrpList = destinationsGrpList;
		}
		public void run() {
			DestinationsGrp destinationsGrp = modelmgr.addDestinationsGrp();
			BlockUtil.refreshViewer(viewer, destinationsGrpList, destinationsGrp);
		}
	}
	
	class AddDestinationAction extends Action {
		private DestinationsGrp destinationsGrp;
		public AddDestinationAction(DestinationsGrp destinationsGrp) {
			super("Add Input Destinations", StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_ADD));
			this.destinationsGrp = destinationsGrp;
		}
		public void run() {
			Destination destination = null;
			BlockUtil.refreshViewer(viewer, destinationsGrp, destination);
		}
	}
	
	class RemoveGroupsAction extends Action {
		private Object selObj;
		public RemoveGroupsAction(String action, Object selObj) {
			super(action, StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE));
			this.selObj = selObj;
		}
		public void run() {
			removeGroupItem(selObj);
		}
	}
	
	@Override
	protected void resetActionButtons() {
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
		bCopy.setEnabled(false);
	}
	
	@Override
	protected void enableActionButtons(Object selObj) {
		bAdd.setEnabled(true);
		bRemove.setEnabled(true);
		bCopy.setEnabled(false);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
		if (selObj instanceof RuleElementsList)
			bRemove.setEnabled(false);
		else if (selObj instanceof Rule)
			bAdd.setEnabled(false);
		else if(selObj instanceof RulesGrp)
			bCopy.setEnabled(true);
		else if (selObj instanceof DestinationElementsList)
			bRemove.setEnabled(false);
		else if (selObj instanceof Destination)
			bAdd.setEnabled(false);
		else if (selObj instanceof DestinationsGrp)
			bCopy.setEnabled(true);
		else if (selObj instanceof FunctionElementsList)
			bRemove.setEnabled(false);
		else if (selObj instanceof Function) {
			bAdd.setEnabled(false);
			if (modelmgr.canMoveUpFunction((FunctionElement)selObj))
				bUp.setEnabled(true);
			if (modelmgr.canMoveDownFunction((FunctionElement)selObj))
				bDown.setEnabled(true);
		} 
		else if(selObj instanceof FunctionsGrp)
			bCopy.setEnabled(true);
		else if (selObj instanceof ProcessesGrp)
			bCopy.setEnabled(true);
		else if (selObj instanceof LogConfigsList)
			bRemove.setEnabled(false);
		else if (selObj instanceof LogConfig){
			bAdd.setEnabled(false);
			bCopy.setEnabled(true);
		}
		else if (selObj instanceof ProcessElementsList) {
				bRemove.setEnabled(false);
			} 
		else if (selObj instanceof Process)
			bAdd.setEnabled(false);
	}
	
	private Listener getGroupsBlockCopyListener(){
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				
				
				if (viewer.getSelection() instanceof IStructuredSelection) {
					ClusterConfigModel model= new ClusterConfigModel();
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					Object newObj = null;
					if (selObj != null) {
						
						if (selObj instanceof RulesGrp) {
							RulesGrp copiedObj= (RulesGrp)selObj;
							ArrayList<RuleElement> copiedRules=copiedObj.rules;
							RulesGrp newRulesGrp=modelmgr.addRulesGroupName(modelmgr.copiedCollectionsName(selObj,copiedObj.id));
							for(RuleElement rObj:copiedRules){
					            if (rObj instanceof Rule) {
					                Rule rule = (Rule) rObj;
					                Rule copyRule=model.new Rule(newRulesGrp,rule.uri,rule.isRef,rule.isTemplate);
					                newRulesGrp.rules.add(copyRule);
					            }
					             
							}
						} else	if (selObj instanceof DestinationsGrp) {
							DestinationsGrp copiedDestGrp =(DestinationsGrp)selObj;
							DestinationsGrp newDestGrp = modelmgr.addDestinationsGroupName(modelmgr.copiedCollectionsName(selObj,copiedDestGrp.id));
							ArrayList<DestinationElement> copiedDest = copiedDestGrp.destinations;
							for(DestinationElement destElement:copiedDest) {

					            if (destElement instanceof Destination) {
					            	Destination dest = (Destination) destElement;
					            	String id = null;
					            	if (!dest.isRef)
					            		id= modelmgr.copiedDestChldName(dest.id);
				            		else
				            			id=dest.id;
					            	Destination copyDest= model.new Destination(newDestGrp,id,dest.isRef);
					            	copyDest=modelmgr.copyDestClass(dest,copyDest);
			                	    newDestGrp.destinations.add(copyDest);
					            }
							}
							
							
							
						}else	if (selObj instanceof FunctionsGrp) {
							FunctionsGrp copiedFuncGrp = (FunctionsGrp) selObj;
							FunctionsGrp newFuncGrp=modelmgr.addFunctionsGroupName(modelmgr.copiedCollectionsName(selObj,copiedFuncGrp.id ));
							ArrayList<FunctionElement> copiedFunc = copiedFuncGrp.functions;
							for(FunctionElement funcElt:copiedFunc){
								if(funcElt instanceof Function){
									Function func= (Function) funcElt;
									Function copyFunc= model.new Function(newFuncGrp,func.uri,func.isRef);
									newFuncGrp.functions.add(copyFunc);
									
								}
							
							}
							
							
						}  else if (selObj instanceof ProcessesGrp) {
							ProcessesGrp  copiedPrGrp = (ProcessesGrp) selObj;
							ProcessesGrp newPrGrp=modelmgr.addProcessesGroupName(modelmgr.copiedCollectionsName(selObj,copiedPrGrp.id));
							ArrayList<ProcessElement> copiedPr = copiedPrGrp.processes;
							
							for(ProcessElement prElt: copiedPr){
								if(prElt instanceof Process){
									Process pr = (Process) prElt;
									Process copyPr=model.new Process(newPrGrp, pr.uri, pr.isRef);
									newPrGrp.processes.add(copyPr);
									
								}
							}
							
						} else if (selObj instanceof LogConfig) {
							
							LogConfig copiedLogConfig = (LogConfig) selObj;
							String selObjName=copiedLogConfig.id;
							String copiedLogName=strCopy+selObjName;
							int count=1;
							ArrayList<String> logName =  modelmgr.getLogConfigsName();
							while(logName.contains(copiedLogName)){
								copiedLogName=strCopy+selObjName+count++;
							}
							newObj = modelmgr.copyLogConfig(copiedLogConfig,copiedLogName);
						
						}
 						BlockUtil.refreshViewer(viewer, selObj, newObj);
					}
				}
			
				
				}
		};
		return listener;
	}
	private Listener getGroupsBlockAddListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					Object newObj = null;
					if (selObj != null) {
						if (selObj instanceof RuleElementsList) {
							newObj = modelmgr.addRulesGrp();
						} else	if (selObj instanceof RulesGrp) {
							RuleSelectionDialog dialog = new RuleSelectionDialog(trGroups.getShell(), ((RulesGrp)selObj).id);
							dialog.open(modelmgr.project, modelmgr.getRulesGroupNames(), modelmgr.getRuleNames((RulesGrp)selObj, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addRules((RulesGrp)selObj, selItems);
						} else if (selObj instanceof DestinationElementsList) {
							newObj = modelmgr.addDestinationsGrp();
						} else	if (selObj instanceof DestinationsGrp) {
							DestinationSelectionDialog dialog = new DestinationSelectionDialog(trGroups.getShell(), ((DestinationsGrp)selObj).id);
							dialog.open(modelmgr.project, modelmgr.getDestinationsGroupNames(), modelmgr.getDestinationNames((DestinationsGrp)selObj, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addDestinations((DestinationsGrp)selObj, selItems);
						} else if (selObj instanceof FunctionElementsList) {
							newObj = modelmgr.addFunctionsGrp();
						} else	if (selObj instanceof FunctionsGrp) {
							FunctionSelectionDialog dialog = new FunctionSelectionDialog(trGroups.getShell(), ((FunctionsGrp)selObj).id);
							dialog.open(modelmgr.project, modelmgr.getFunctionsGroupNames(), modelmgr.getFunctionNames((FunctionsGrp)selObj, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addFunction((FunctionsGrp)selObj, selItems);
						} else if (selObj instanceof ProcessElementsList) {
							newObj = modelmgr.addProcessesGrp();
						} else if (selObj instanceof ProcessesGrp) {
							ProcessSelectionDialog dialog = new ProcessSelectionDialog(trGroups.getShell(), ((ProcessesGrp)selObj).id);
							dialog.open(modelmgr.project, modelmgr.getProcessesGroupNames(), modelmgr.getProcessNames((ProcessesGrp)selObj, false));
							ArrayList<String> selItems = dialog.getSelectedItems();
							newObj = modelmgr.addProcesses((ProcessesGrp)selObj, selItems);
						} else if (selObj instanceof LogConfigsList) {
							ArrayList<String> names = modelmgr.getLogConfigsName();
							String name = IdUtil.generateSequenceId("LogConfig", names);
							newObj = modelmgr.addLogConfig(name);
						}
						BlockUtil.refreshViewer(viewer, selObj, newObj);
					}
				}
			}
		};
		return listener;	
	}


	private Listener getGroupsBlockRemoveListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					removeGroupItem(selObj);
				}
			}
		};
		return listener;
	}

	private void removeGroupItem(Object object) {
		if (object != null) {
			if (object instanceof RulesGrp) {
				modelmgr.removeRulesGroup((RulesGrp)object);
			} else if (object instanceof Rule) {
				modelmgr.removeRule((Rule)object);
			} else if (object instanceof DestinationsGrp) {
				modelmgr.removeDestinationsGroup((DestinationsGrp)object);
			} else if (object instanceof Destination) {
				modelmgr.removeDestination((Destination)object);
			} else if (object instanceof FunctionsGrp) {
				modelmgr.removeFunctionsGroup((FunctionsGrp)object);
			} else if (object instanceof Function) {
				modelmgr.removeFunction((Function)object);
			} else if (object instanceof ProcessesGrp) {
				modelmgr.removeProcessesGroup((ProcessesGrp)object);
			} else if (object instanceof Process) {
				modelmgr.removeProcess((Process)object);
			} else if (object instanceof LogConfig) {
				modelmgr.removeLogConfig((LogConfig)object);
			}
			BlockUtil.refreshViewer(viewer);
			resetActionButtons();
		}
	}
	
	class MasterContentProvider extends ClusterConfigBlockMasterContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add(modelmgr.getRulesGroupList());
			lists.add(modelmgr.getDestinationsGroupList());
			lists.add(modelmgr.getFunctionsGroupList());
			if (AddonUtil.isAddonInstalled(AddOnType.PROCESS)) {
				lists.add(modelmgr.getProcessesGroupList());
			}
			lists.add(modelmgr.getLogConfigsList());
			return lists.toArray(new Object[0]);
		}
	}

	class MasterLabelProvider extends ClusterConfigBlockMasterLabelProvider {
	}
}
