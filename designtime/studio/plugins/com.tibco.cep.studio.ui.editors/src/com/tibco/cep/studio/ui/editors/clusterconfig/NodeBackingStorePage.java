package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 10, 2010 1:13:24 PM
 */

public class NodeBackingStorePage extends ClusterNodeDetailsPage {
	
	//private static final String DB_LICENSE_TEXT = "Users need to install the required database software separately."; 
	
	private static final int TAB_TYPE_ORACLE_SQLSERVER = 0;
	private static final int TAB_TYPE_BDB = 1;

	private static final int TAB_PERSISTENCE_OPTION_NONE = 0;
	private static final int TAB_PERSISTENCE_OPTION_SHARED_ALL = 1;
	private static final int TAB_PERSISTENCE_OPTION_SHARED_NOTHING = 2;
	
	private Button bCacheAside, bEnforcePools, bParallelRecovery;
	private Combo cOption, cType, cStrategy, cPersistencePolicy, cSharedAllPersistencePolicy;
	private Text tBdbDataStorePath;
	private GvField tgPersistencePath;
	@SuppressWarnings("unused")
	private Label lOption, lCacheAside, lEnforcePools, lStrategy, lType, lBdbDataStorePath, lPersistencePath, lPersistencePolicy, lParallelRecovery, lSharedAllPersistencePolicy;   
	
	private StackLayout optionTabLayout;
	private Control optionTabs[];
	private Composite optionComp;
	
	private StackLayout typeTabLayout;
	private Control typeTabs[];
	private Composite typeComp;
	
	public NodeBackingStorePage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		Composite storageComp = new Composite(client, SWT.NONE);
		storageComp.setLayout(new GridLayout(2, false));
		storageComp.setLayoutData(PanelUiUtil.getGridData(2));

		lOption = PanelUiUtil.createLabel(storageComp, "Persistence Option: ");
		cOption = createComboField(storageComp, BackingStore.PERSISTENCE_OPTION, modelmgr.getBackingStorePersistenceOptions(false));
		cOption.addListener(SWT.Selection, getOptionListener());
		
		createOptionTabs(client);
		
		/*
		bEnabled = createCheckboxField(client, "Enabled: ", ConfigNS.Elements.ENABLED.localName);
	
		
		rSharedAll = PanelUiUtil.createRadioButton(storageComp, "Shared All");
		rSharedAll.addListener(SWT.Selection, getCheckboxSelectionListener(rSharedAll, "shared-all"));
		PanelUiUtil.createLabelFiller(storageComp);
		createSharedAllGroup(storageComp);
		
		rSharedNothing = PanelUiUtil.createRadioButton(storageComp, "Shared Nothing");
		PanelUiUtil.createLabelFiller(storageComp);
		createSharedNothingGroup(storageComp);
		//rSharedNothing.addListener(SWT.Selection, getCheckboxSelectionListener(rSharedNothing, "shared-all"));
		
		 */

		storageComp.pack();
		
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private void createOptionTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		
		optionComp = new Composite(comp, SWT.NONE);
		optionComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		optionTabLayout = new StackLayout();
		optionComp.setLayout(optionTabLayout);
	    
	    optionTabs = new Control[3];
	    optionTabs[TAB_PERSISTENCE_OPTION_NONE] = createPersistenceOptionNoneTab();
	    optionTabs[TAB_PERSISTENCE_OPTION_SHARED_ALL] = createPersistenceOptionSharedAllTab();
	    optionTabs[TAB_PERSISTENCE_OPTION_SHARED_NOTHING] = createPersistenceOptionSharedNothingTab();
        optionTabLayout.topControl = typeTabs[TAB_PERSISTENCE_OPTION_NONE]; 
        
		typeComp.pack();
		comp.pack();
		
	}

	private Composite createPersistenceOptionNoneTab() {
		Composite comp = new Composite(optionComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		comp.pack();
		return comp;
	}
	
	private Composite createPersistenceOptionSharedAllTab() {
		Composite comp = new Composite(optionComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(PanelUiUtil.getGridData(2));
		
		lType = PanelUiUtil.createLabel(comp, "Database Type: ");
		cType = createComboField(comp, ConfigNS.Elements.TYPE.localName, modelmgr.getBackingStoreTypes(false));
		cType.addListener(SWT.Selection, getTypeListener());
		
		/*
		Composite comp = new Composite(gSharedAll, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactHeightGridLayout(1, false));
		comp.setLayoutData(PanelUiUtil.getGridData(1));
		//Label lLicense = PanelUiUtil.createLabel(comp, DB_LICENSE_TEXT);
		comp.pack();
		*/

		createTypeTabs(comp);
		comp.pack();
		return comp;
	}

	private Composite createPersistenceOptionSharedNothingTab() {
		Composite comp = new Composite(optionComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(PanelUiUtil.getGridData(2));
		
		/*lPersistencePath = PanelUiUtil.createLabel(comp, "Persistence Path: ");
		tPersistencePath = createTextField(comp, "data-store-path");
	*/	
		tgPersistencePath=createGvTextField(comp,"Persistence Path: ", "data-store-path");
		
		lPersistencePolicy = PanelUiUtil.createLabel(comp, "Persistence Policy: ");
		cPersistencePolicy = createComboField(comp, "persistence-policy", modelmgr.getBackingStorePersistencePolicies());
		
	//	lParallelRecovery = PanelUiUtil.createLabel(comp, "Parallel Recovery:");
	//	bParallelRecovery = createCheckboxField(comp, Elements.PARALLEL_RECOVERY.localName);
				
		comp.pack();
		return comp;
	}
	

	private Listener getOptionListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int index = cOption.getSelectionIndex();
				/*String persistenceMode=cOption.getItem(index);
				if (NodeInfAgentPage.bCheckForDuplicates != null) {
					if ("Shared Nothing".equalsIgnoreCase(persistenceMode)) {
						NodeInfAgentPage.bCheckForDuplicates.setEnabled(false);
					} else {
						NodeInfAgentPage.bCheckForDuplicates.setEnabled(true);
					}
				}*/
				setOptionCompStackTopControl(index);
				updateOnConfigurationChange();
			}
		};
		
		return listener;
	}
	
	private void setOptionCompStackTopControl(int top) {
		optionTabLayout.topControl = optionTabs[top];
		optionComp.layout();
	}
	
	private Listener getTypeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String text = cType.getText();
				if (text.equals(BackingStoreConfig.TYPE_BDB)) {
					setTypeCompStackTopControl(TAB_TYPE_BDB);
				} else {
					setTypeCompStackTopControl(TAB_TYPE_ORACLE_SQLSERVER);
				}
				updateOnConfigurationChange();
			}
		};
		
		return listener;
	}

	private void createTypeTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		
		typeComp = new Composite(comp, SWT.NONE);
		typeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		typeTabLayout = new StackLayout();
		typeComp.setLayout(typeTabLayout);
	    
	    typeTabs = new Control[2];
	    typeTabs[TAB_TYPE_ORACLE_SQLSERVER] = createOracleSqlserverTab();
	    typeTabs[TAB_TYPE_BDB] = createBdbTab();
        typeTabLayout.topControl = typeTabs[TAB_TYPE_ORACLE_SQLSERVER]; 
        
		typeComp.pack();
		comp.pack();
	}
	
	private Control createOracleSqlserverTab() {
		Group comp = new Group(typeComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		lStrategy = PanelUiUtil.createLabel(comp, "Strategy: ");
		cStrategy = createComboField(comp, ConfigNS.Elements.STRATEGY.localName, modelmgr.getBackingStoreStrategies());
		lSharedAllPersistencePolicy = PanelUiUtil.createLabel(comp, "Persistence Policy: ");
		cSharedAllPersistencePolicy = createComboField(comp, "persistence-policy", modelmgr.getBackingStorePersistencePolicies());
		lCacheAside = PanelUiUtil.createLabel(comp, "Cache Aside: ");
		bCacheAside = createCheckboxField(comp, ConfigNS.Elements.CACHE_ASIDE.localName);
		lEnforcePools = PanelUiUtil.createLabel(comp, "Enforce Pools: ");
		bEnforcePools = createCheckboxField(comp, ConfigNS.Elements.ENFORCE_POOLS.localName);
		
		comp.pack();
		return comp;
	}

	private Control createBdbTab() {
		Group comp = new Group(typeComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		lBdbDataStorePath = PanelUiUtil.createLabel(comp, "Berkeley DB Data Store Path: ");
		tBdbDataStorePath = createTextField(comp, "data-store-path");
		
		comp.pack();
		return comp;
	}
	
	private void setTypeCompStackTopControl(int top) {
		typeTabLayout.topControl = typeTabs[top];
		typeComp.layout();
	}
	
	private Text createTextField(Composite parent, String key) {
		Text tField = PanelUiUtil.createText(parent);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, key));
		return tField;
	}

	private Button createCheckboxField(Composite parent, String key) {		
		Button bField = PanelUiUtil.createCheckBox(parent, "");
		bField.addListener(SWT.Selection, getCheckboxSelectionListener(bField, key));
		return bField;
	}

	private Combo createComboField(Composite parent, String key, ArrayList<String> items) {
		Combo cField = PanelUiUtil.createComboBox(parent, items.toArray(new String[0]));
		cField.addListener(SWT.Selection, getComboSelectionListener(cField, key));
		return cField;
	}
			
	private Listener getTextModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateBackingStoreValue(key, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}

	private Listener getCheckboxSelectionListener(final Button bField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateBackingStoreValue(key, new Boolean(bField.getSelection()).toString());
			
				if(key.equals(ConfigNS.Elements.CACHE_ASIDE.localName) && bField.getSelection()){
					cSharedAllPersistencePolicy.setEnabled(false);
				}else if(key.equals(ConfigNS.Elements.CACHE_ASIDE.localName) && !bField.getSelection()){
					cSharedAllPersistencePolicy.setEnabled(true);
				} 
				
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getComboSelectionListener(final Combo cField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String value = cField.getText();
				boolean updated = modelmgr.updateBackingStoreValue(key, value);
				if (key.equals(ConfigNS.Elements.CACHE_LOADER_CLASS.localName)) {
					if (value.equals(BackingStore.CACHE_LOADER_ORACLE)) {
						bCacheAside.setEnabled(true);
						cType.setEnabled(false);
						cStrategy.setEnabled(false);
					} else {
						bCacheAside.setSelection(true);
						bCacheAside.setEnabled(false);
						bCacheAside.notifyListeners(SWT.Selection, new Event());
						cType.setEnabled(true);
						cStrategy.setEnabled(true);
					}
					cType.notifyListeners(SWT.Selection, new Event());
				} else if (key.equals(ConfigNS.Elements.TYPE.localName)) {
					if (cField.getEnabled() && (value.equals(BackingStoreConfig.TYPE_ORACLE) || value.equals("oracle"))) {
						cStrategy.setEnabled(true);
					} else {
						cStrategy.setEnabled(false);
					}
				} else if (key.equals("persistence-policy")){
					setPersistencePolicyComboBoxes(value);
				}
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		@SuppressWarnings("unused")
		IStructuredSelection ssel = (IStructuredSelection)selection;
		update();
	}
	
	private void update() {
		String option = modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION);

		String provider = modelmgr.getModel().om.cacheOm.provider;
		boolean isOracleCacheOm = provider.equals(CacheOm.PROVIDER_ORACLE) || provider.equals(CacheOm.PROVIDER_COHERENCE);
		cOption.removeAll();
		ArrayList<String> validPersistenceOptions = modelmgr.getBackingStorePersistenceOptions(isOracleCacheOm); 
		cOption.setItems(validPersistenceOptions.toArray(new String[0]));
		if (isOracleCacheOm && !validPersistenceOptions.contains(option)) {
			cOption.setText(BackingStoreConfig.PERSISTENCE_OPTION_NONE);
		} else {
			if (validPersistenceOptions.contains(option)) {
				cOption.setText(option);
			} else {
				cOption.setText(BackingStoreConfig.PERSISTENCE_OPTION_NONE);
			}
		}
		cOption.notifyListeners(SWT.Selection, new Event());
		
		//String activeOm = modelmgr.getModel().om.activeOm;
		//if (activeOm.equals(ObjectManagement.CACHE_MGR)) {
		//}

		String type = modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName);
		cType.removeAll();
		ArrayList<String> validTypes = modelmgr.getBackingStoreTypes(isOracleCacheOm); 
		cType.setItems(validTypes.toArray(new String[0]));
		if (isOracleCacheOm && !validTypes.contains(type)) {
			cType.setText(BackingStoreConfig.TYPE_ORACLE);
		} else {
			cType.setText(type);
		}
		
		if (type.equals(BackingStoreConfig.TYPE_BDB)) {
			//setTypeCompStackTopControl(TAB_TYPE_BDB);
		} else {
			//setTypeCompStackTopControl(TAB_TYPE_ORACLE_SQLSERVER);
			bEnforcePools.setSelection(modelmgr.getBooleanValue(modelmgr.getBackingStoreValue(ConfigNS.Elements.ENFORCE_POOLS.localName)));
			cStrategy.setText(modelmgr.getBackingStoreValue(ConfigNS.Elements.STRATEGY.localName));
		}
		cType.notifyListeners(SWT.Selection, new Event());
		tBdbDataStorePath.setText(modelmgr.getBackingStoreValue("data-store-path"));
		
		
	//	((Text)tgPersistencePath.getField()).setText(modelmgr.getBackingStoreValue("data-store-path"));
		
		
		setPersistencePolicyComboBoxes(modelmgr.getBackingStoreValue("persistence-policy"));	
	//	bParallelRecovery.setSelection(Boolean.parseBoolean(modelmgr.getBackingStoreValue(Elements.PARALLEL_RECOVERY.localName)));	
		bCacheAside.setSelection(modelmgr.getBooleanValue(modelmgr.getBackingStoreValue(ConfigNS.Elements.CACHE_ASIDE.localName)));
		if (!cSharedAllPersistencePolicy.isDisposed()) {
			cSharedAllPersistencePolicy.setEnabled(!modelmgr.getBooleanValue(modelmgr.getBackingStoreValue(ConfigNS.Elements.CACHE_ASIDE.localName)));
		}
		validateFields();
	}
	
	public boolean validateFields() {
		boolean valid = true;
		return valid;
	}
	
	private void updateOnConfigurationChange(){
		if(modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)
				|| ( modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) &&
							modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName).equals(BackingStoreConfig.TYPE_BDB) )){
			
			modelmgr.updateDomainObjectsValue(ConfigNS.Elements.PRE_LOAD_ENABLED.localName, "true");
			modelmgr.updateDomainObjectsValue(ConfigNS.Elements.PRE_LOAD_HANDLES.localName, "true");
		}
		
		if(modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)){
			modelmgr.updateCacheOmValues(Elements.EXPLICIT_TUPLE.localName, "true");
		}
		
		if( modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL)){
			switch(modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName)){
			case BackingStoreConfig.TYPE_SQLSERVER:
			case BackingStoreConfig.TYPE_DB2:
			case BackingStoreConfig.TYPE_MYSQL:
			case BackingStoreConfig.TYPE_POSTGRES:
				modelmgr.updateBackingStoreValue(ConfigNS.Elements.STRATEGY.localName,BackingStore.STRATEGY_JDBC);
				cStrategy.setText(BackingStore.STRATEGY_JDBC);
				break;
			default:
				cStrategy.setText(modelmgr.getBackingStoreValue(ConfigNS.Elements.STRATEGY.localName));
				break;
			}
		}
	}
	
	private void setPersistencePolicyComboBoxes(String text){
		cPersistencePolicy.setText(text);	
		cSharedAllPersistencePolicy.setText(text);
	}

	@Override
	public Listener getListener(final Control field, final String key) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(field instanceof Text){
					modelmgr.updateBackingStoreValue(key, ((Text)field).getText());
				}
			}
		};
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return modelmgr.getBackingStoreValue("data-store-path");
	}
}