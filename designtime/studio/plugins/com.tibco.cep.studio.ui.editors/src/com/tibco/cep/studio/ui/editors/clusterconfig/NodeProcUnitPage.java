package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Feb 8, 2010 11:00:55 PM
 */

public class NodeProcUnitPage extends ClusterNodeDetailsPage {

	private Text tPUName, tLogs;
	private Button bLogsBrowse, bHotDeploy, bEnableCacheStorage, bEnableDbConcepts;
	private ProcessingUnit procUnit;
	private Table taAgents;
	private ProcUnitAgentsTableProviderModel taAgentsModel;
	private TableProviderUi taAgentsProviderUi;
	private Tree trProps;
	private TreeProviderUi trPropsProviderUi;
	private ProcUnitPropertyTreeProviderModel treeModel;
	private boolean autoEnableCacheStorage = false;
	private SecurityConfigGroup securityConfigGroup;
	
	public NodeProcUnitPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		Label lPUName = PanelUiUtil.createLabel(client, "Name: ");
		tPUName = PanelUiUtil.createText(client);
		tPUName.addListener(SWT.Modify, getProcUnitNameModifyListener());
		
		//PanelUiUtil.createSpacer(toolkit, client, 2);
		
		Label lLogs = PanelUiUtil.createLabel(client, "Log Configuration:  ");
		Composite logComp = new Composite(client, SWT.NONE);
		logComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		logComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tLogs = PanelUiUtil.createText(logComp);
		tLogs.addListener(SWT.Modify, getProcUnitLogModifyListener());
		tLogs.setEnabled(false);
		bLogsBrowse = PanelUiUtil.createBrowsePushButton(logComp, tLogs);
		bLogsBrowse.addListener(SWT.Selection, getProcUnitLogBrowseListener(logComp.getShell()));
		
		//PanelUiUtil.createSpacer(toolkit, client, 2);
		PanelUiUtil.createLabel(client, "Hot Deploy: ");
		bHotDeploy = PanelUiUtil.createCheckBox(client, "");
		bHotDeploy.addListener(SWT.Selection, getProcUnitHotDeployListener());
		
		if (!AddonUtil.isExpressEdition()) {
			PanelUiUtil.createLabel(client, "Enable Cache Storage: ");
			bEnableCacheStorage = PanelUiUtil.createCheckBox(client, "");
			bEnableCacheStorage.addListener(SWT.Selection, getProcUnitCacheStorageListener());
		}
		
		if (AddonUtil.isDataModelingAddonInstalled()) {
			PanelUiUtil.createLabel(client, "Enable Database Concepts: ");
			bEnableDbConcepts = PanelUiUtil.createCheckBox(client, "");
			bEnableDbConcepts.addListener(SWT.Selection, getProcUnitDbConceptsListener());
		}
		
		Group gAgents = new Group(client, SWT.NONE);
		gAgents.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gAgents.setLayoutData(gd);
		gAgents.setText("Agents");
		
		String columns[] = new String[]{ "Agent", "Key", "Priority"};
		taAgentsModel = new ProcUnitAgentsTableProviderModel(modelmgr);  
		taAgentsProviderUi = new TableProviderUi(gAgents, columns, true, taAgentsModel);
		taAgents = taAgentsProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		taAgents.setLayoutData(gd);
		
	    //taAgents.addMouseListener(taAgentsProviderUi.tableTextModifyMouseListener(taAgents, 0)); //Don't make the agent name editable
	    taAgents.addMouseListener(taAgentsProviderUi.tableTextModifyMouseListener(taAgents, 1));	    
	    taAgents.addMouseListener(taAgentsProviderUi.tableTextModifyMouseListener(taAgents, 2));
	    taAgents.addListener(SWT.Modify, getAgentsTableModifyListener());
		
		//PanelUiUtil.createSpacer(toolkit, client, 2);
		
		Composite propsComp = toolkit.createComposite(client);
		propsComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 250;
		gd.heightHint = 300;
		propsComp.setLayoutData(gd);
		
		Group gProps = new Group(propsComp, SWT.NONE);
		gProps.setLayout(new GridLayout(1, false));
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 250;
		gProps.setLayoutData(gd);
		gProps.setText("Properties");
		
		treeModel = new ProcUnitPropertyTreeProviderModel(modelmgr);
		trPropsProviderUi = new TreeProviderUi(gProps, treeModel.columns, true, treeModel, treeModel.keys);
		trProps = trPropsProviderUi.createTree();     
		trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 0));
	    trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 1));
        //trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 2, 0, TreeProviderUi.TYPE_COMBO, treeModel.types));	    
	    trProps.addListener(SWT.Modify, treeModel.getTreeModifyListener(trProps));		
	    gProps.pack();
		propsComp.pack();
		
		securityConfigGroup = new ProcUnitSecurityConfigGroup(client, SWT.NONE, modelmgr);
		securityConfigGroup.createConfig();
		
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}	
	
	private Listener getProcUnitNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = tPUName.getText().trim();				
				boolean updated = modelmgr.updateProcUnitName(procUnit, newName);
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getProcUnitLogModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int update = modelmgr.updateProcUnitLogConfig(procUnit, tLogs.getText());
				if (update == -1 && !tLogs.getText().equals("")) {
					tLogs.setToolTipText("Invalid Configuration. This value will not be saved.");
					//tLogs.setBackground(PanelUiUtil.COLOR_INVALID);
				} else {
					tLogs.setToolTipText("");
					//tLogs.setBackground(PanelUiUtil.COLOR_VALID);
				}
			}
		};
		return listener;
	}
	
	private Listener getProcUnitLogBrowseListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ProcUnitLogConfigSelectionDialog dialog = new ProcUnitLogConfigSelectionDialog(shell);
				ArrayList<String> curList = new ArrayList<String>();
				curList.add(tLogs.getText());
				dialog.open(modelmgr.getLogConfigs(), curList);
				String selLogConfig = dialog.getSelectedLogConfig();
				if (selLogConfig.trim().equals(""))
					return;
				tLogs.setText(selLogConfig);
				//modelmgr.updateProcUnitLogConfig(procUnit, selLogConfig);
			}
		};
		return listener;
	}
	
	private Listener getProcUnitHotDeployListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateProcUnitHotDeploy(procUnit, bHotDeploy.getSelection());
			}
		};
		return listener;
	}
	
	private Listener getProcUnitCacheStorageListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateProcUnitCacheStorage(procUnit, bEnableCacheStorage.getSelection());
			}
		};
		return listener;
	}
	
	private Listener getProcUnitDbConceptsListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateProcUnitDbConcepts(procUnit, bEnableDbConcepts.getSelection());
			}
		};
		return listener;
	}
	
	private Listener getAgentsTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateProcUnitAgents(procUnit, taAgents);
				enableHotDeploy();
				enableCacheStorage();
				enableDbConcepts();
			}
		};
		return listener;
	}
	
    private void updateProcUnitAgents(ProcessingUnit procUnit, Table table) {
        if (table == null)
            return;
        procUnit.agentClasses.clear();
        String keys[] = new String[] {ProcessingUnit.AGENT_REF, ProcessingUnit.AGENT_KEY, ProcessingUnit.AGENT_PRIORITY};
        for (TableItem item: table.getItems()) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            for (int col=0; col<table.getColumnCount(); col++)
                map.put(keys[col], item.getText(col));
            procUnit.agentClasses.add(map);
        }
        modelmgr.modified();
    }
	
	private void enableHotDeploy() {
		bHotDeploy.setEnabled(true);
		if (modelmgr.isCachePU(procUnit)) {
			bHotDeploy.setSelection(false);
			bHotDeploy.setEnabled(false);
		}
		bHotDeploy.notifyListeners(SWT.Selection, new Event());
	}
	
	private void enableDbConcepts(){
		if (!AddonUtil.isDataModelingAddonInstalled()) {
			return; // bEnableDbConcepts is null
		}
		bEnableDbConcepts.setEnabled(true);
		if (modelmgr.isCachePU(procUnit)) {
			bEnableDbConcepts.setSelection(false);
			bEnableDbConcepts.setEnabled(false);
		}
		bEnableDbConcepts.notifyListeners(SWT.Selection, new Event());
	}
	
	private void enableCacheStorage() {
		bEnableCacheStorage.setEnabled(true);
		if (modelmgr.isCachePU(procUnit)) {
			if (!bEnableCacheStorage.getSelection())
				autoEnableCacheStorage = true;
			bEnableCacheStorage.setSelection(true);
			bEnableCacheStorage.setEnabled(false);
		} else if (modelmgr.isDashboardPU(procUnit)) {
			bEnableCacheStorage.setSelection(false);
			bEnableCacheStorage.setEnabled(false);
		} else {
			if (autoEnableCacheStorage) {
				bEnableCacheStorage.setSelection(false);	
				autoEnableCacheStorage = false;
			}
		}
		bEnableCacheStorage.notifyListeners(SWT.Selection, new Event());
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			procUnit = ((ProcessingUnit) ssel.getFirstElement());
		else
			procUnit = null;
		update();
	}
	
	private void update() {
		if (procUnit != null) {
			tPUName.setText(procUnit.name);
			tLogs.setText(modelmgr.getProcUnitLogConfig(procUnit));
			bHotDeploy.setSelection(procUnit.hotDeploy);
			if (!AddonUtil.isExpressEdition()) {
				bEnableCacheStorage.setSelection(procUnit.enableCacheStorage);
				bEnableCacheStorage.setEnabled(true);
				if (AddonUtil.isDataModelingAddonInstalled()) {
					bEnableDbConcepts.setEnabled(true);
				}
				if (modelmgr.isCachePU(procUnit) || modelmgr.isDashboardPU(procUnit))
					bEnableCacheStorage.setEnabled(false);
				if (modelmgr.isCachePU(procUnit)){
					if (AddonUtil.isDataModelingAddonInstalled()) {
						bEnableDbConcepts.setEnabled(false);
					}
				}
			}
			if (AddonUtil.isDataModelingAddonInstalled()) {
				bEnableDbConcepts.setSelection(procUnit.enableDbConcepts);
			}
			updateAgentTable();
			enableHotDeploy();
			treeModel.setProcUnit(procUnit);
			trPropsProviderUi.setTreeData(modelmgr.getProcUnitProperties(procUnit));
			if (securityConfigGroup != null) securityConfigGroup.updateConfig(procUnit.securityConfig);
		}
	}
	
	private void updateAgentTable() {
		taAgentsModel.setProcessingUnit(procUnit);
	    String keys[] = new String[] { "ref", "key", "priority" };
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (LinkedHashMap<String, String> map: procUnit.agentClasses) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	for (int i=0; i<keys.length; i++) {
	    		rowData.add(map.get(keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taAgentsProviderUi.setTableData(tableData);
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
