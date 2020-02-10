package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectCompositeIndex;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Mar 10, 2010 11:09:23 AM
 */

public class NodeDomainObjectPage extends ClusterNodeDetailsPage {

	private DomainObject domainObj;
	private Group gCache, gBackingStore, gProperties, gIndexes;
	private Label lUri, lPreloadFetchSize, lPreprocessor, lTableName;
	private Text tUri, tPreloadFetchSize, tPreprocessor, tTableName;
	private GvField tConceptTTLvalue;
	private Label lMode, lPreloadEntities, lPreloadHandles, lConceptTTLvalue;
	private Combo cMode, cPreloadEntities, cPreloadHandles, cCheckForVersion, cConstant, cEnableTracking, cEvictFromCacheOnUpdate, cIsCacheLimited, cSubscribe;
	private Label lSubscribe, lCheckForVersion, lIsCacheLimited, lEnableTracking, lEvictFromCacheOnUpdate, lConstant, lHasBackingStore;
	private Button bPreprocessorBrowse, bHasBackingStore;
	private Table taDomainObjectProperties;
	private DomainObjectPropertiesProviderModel taDomainObjectPropertiesProviderModel;
	private TableProviderUi taDomainObjectPropertiesProviderUi;
	private Table taCompIndexes;
	private CompIndexTableProviderModel indexTableProviderModel;
	private TableProviderUi compIndexTableProviderUi;
	private MouseListener indexColumnListener, maxSizeColumnListener, reverseReferencesColumnListener,encryptionColumnListener;
	
	public NodeDomainObjectPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		Composite comp = new Composite(client, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		lUri = PanelUiUtil.createLabel(comp, "Entity URI: ");
		tUri = PanelUiUtil.createText(comp);
		tUri.addListener(SWT.Modify, getTextModifyListener(tUri, ConfigNS.Elements.URI.localName, false));

		String[] doModes = new String[] { CacheOm.DO_MODE_UI_MEMORY, CacheOm.DO_MODE_UI_CACHE };
		//cMode = createComboField(gCache, lMode, "Mode: ", ConfigNS.Elements.MODE.localName, doModes);
		lMode = PanelUiUtil.createLabel(comp, "Mode: ");
		cMode = PanelUiUtil.createComboBox(comp, doModes);
		cMode.addListener(SWT.Selection, getComboSelectionListener(cMode, ConfigNS.Elements.MODE.localName));
		
		comp.pack();
		
		createCacheGroup(client);
		PanelUiUtil.createLabelFiller(client);
		createBackingStoreGroup(client);
		PanelUiUtil.createLabelFiller(client);
		createPropertiesGroup(client);
		
		createCompositeIndexGroup(client);
		
		client.pack();	
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Composite createCacheGroup(Composite comp) {
		// Create "Cache" group
		gCache = new Group(comp, SWT.NONE);
		gCache.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gCache.setLayoutData(gd);
		gCache.setText("Cache");
		
		String[] doOverrideOptions = new String[] { "default", "true", "false" };
		//cPreloadEntities = createComboField(gCache, lPreloadEntities, "Preload Entities: ", ConfigNS.Elements.PRE_LOAD_ENABLED.localName, doPreloadOptions);
		lPreloadEntities = PanelUiUtil.createLabel(gCache, "Preload Entities: ");
		cPreloadEntities = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cPreloadEntities.addListener(SWT.Selection, getComboSelectionListener(cPreloadEntities, ConfigNS.Elements.PRE_LOAD_ENABLED.localName));

		//cPreloadHandles = createComboField(gCache, lPreloadHandles, "Preload Handles: ", ConfigNS.Elements.PRE_LOAD_HANDLES.localName, doPreloadOptions);
		lPreloadHandles = PanelUiUtil.createLabel(gCache, "Preload Handles: ");
		cPreloadHandles = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cPreloadHandles.addListener(SWT.Selection, getComboSelectionListener(cPreloadHandles, ConfigNS.Elements.PRE_LOAD_HANDLES.localName));

		//tPreloadFetchSize = createTextField(gCache, lPreloadFetchSize, "Preload Fetch Size: ", ConfigNS.Elements.PRE_LOAD_FETCH_SIZE.localName, false);
		lPreloadFetchSize = PanelUiUtil.createLabel(gCache, "Preload Fetch Size [Set 0 for All]: ");
		tPreloadFetchSize = PanelUiUtil.createText(gCache);
		tPreloadFetchSize.addListener(SWT.Modify, getTextModifyListener(tPreloadFetchSize, ConfigNS.Elements.PRE_LOAD_FETCH_SIZE.localName, false));
		
		lCheckForVersion = PanelUiUtil.createLabel(gCache, "Check for Version: ");
		cCheckForVersion = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cCheckForVersion.addListener(SWT.Selection, getComboSelectionListener(cCheckForVersion, ConfigNS.Elements.CHECK_FOR_VERSION.localName));
		
		lConstant = PanelUiUtil.createLabel(gCache, "Constant: ");
		cConstant = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cConstant.addListener(SWT.Selection, getComboSelectionListener(cConstant, ConfigNS.Elements.CONSTANT.localName));

		lEnableTracking = PanelUiUtil.createLabel(gCache, "Enable Tracking: ");
		cEnableTracking = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cEnableTracking.addListener(SWT.Selection, getComboSelectionListener(cEnableTracking, ConfigNS.Elements.ENABLE_TRACKING.localName));
		
		lEvictFromCacheOnUpdate = PanelUiUtil.createLabel(gCache, "Evict from Cache on Update: ");
		cEvictFromCacheOnUpdate = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cEvictFromCacheOnUpdate.addListener(SWT.Selection, getComboSelectionListener(cEvictFromCacheOnUpdate, ConfigNS.Elements.EVICT_ON_UPDATE.localName));
		
		lIsCacheLimited = PanelUiUtil.createLabel(gCache, "Is Cache Limited: ");
		cIsCacheLimited = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
		cIsCacheLimited.addListener(SWT.Selection, getComboSelectionListener(cIsCacheLimited, ConfigNS.Elements.CACHE_LIMITED.localName));
		
//		lSubscribe = PanelUiUtil.createLabel(gCache, "Subscribe Cluster: ");
//		cSubscribe = PanelUiUtil.createComboBox(gCache, doOverrideOptions);
//		cSubscribe.addListener(SWT.Selection, getComboSelectionListener(cSubscribe, ConfigNS.Elements.SUBSCRIBE.localName));
//
//		lPreprocessor = PanelUiUtil.createLabel(gCache, "Subscription Preprocessor: ");
//		final Composite preProcComp = new Composite(gCache, SWT.NONE);
//		preProcComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
//		preProcComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		tPreprocessor = PanelUiUtil.createText(preProcComp);
//		bPreprocessorBrowse = PanelUiUtil.createBrowsePushButton(preProcComp, tPreprocessor);
//		bPreprocessorBrowse.addListener(SWT.Selection, new Listener() {
//			@Override
//			public void handleEvent(Event event) {
//				PreprocessorSelectionDialog dialog = new PreprocessorSelectionDialog(preProcComp.getShell());
//				ArrayList<String> curList = new ArrayList<String>();
//				curList.add(tPreprocessor.getText());
//				dialog.open(ClusterConfigProjectUtils.getProjectRuleFunctionNames(modelmgr.project, ClusterConfigProjectUtils.RF_ARGS_TYPE_SUBSCRIPTION_PREPROCESSOR), curList);
//				String selFunction = dialog.getSelectedFunction();
//				if (selFunction.trim().equals(""))
//					return;
//				tPreprocessor.setText(selFunction);
//			}
//		});
//		tPreprocessor.addListener(SWT.Modify, getTextModifyListener(tPreprocessor, ConfigNS.Elements.PRE_PROCESSOR.localName, false));
//		preProcComp.pack();	
        
/*		lConceptTTLvalue = PanelUiUtil.createLabel(gCache, "Concept TTL:");
		tConceptTTLvalue = PanelUiUtil.createText(gCache);
		tConceptTTLvalue.addListener(SWT.Modify, getTextModifyListener(tConceptTTLvalue, ConfigNS.Elements.CONCEPT_TTL_VALUE.localName, false));
*/		
		
		tConceptTTLvalue = createGvTextField(client,"Concept TTL: ", Elements.CONCEPT_TTL_VALUE.localName);
		gCache.pack();
		
		return gCache;
	}
	
	private Composite createBackingStoreGroup(Composite comp) {
		// Create "BackingStore" group
		gBackingStore = new Group(comp, SWT.NONE);
		gBackingStore.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gBackingStore.setLayoutData(gd);
		gBackingStore.setText("Backing Store");
		
		//bHasBackingStore = createCheckboxField(gBackingStore, lHasBackingStore, "Has Backing Store: ", ConfigNS.Elements.ENABLED.localName, true);
		lHasBackingStore = PanelUiUtil.createLabel(gBackingStore, "Has Backing Store: ");
		bHasBackingStore = PanelUiUtil.createCheckBox(gBackingStore, "");
		bHasBackingStore.addListener(SWT.Selection, getCheckboxSelectionListener(bHasBackingStore,ConfigNS.Elements.ENABLED.localName, true));
		
		lTableName = PanelUiUtil.createLabel(gBackingStore, "Table Name: ");
		tTableName = PanelUiUtil.createText(gBackingStore);
		tTableName.addListener(SWT.Modify, getTextModifyListener(tTableName, ConfigNS.Elements.TABLE_NAME.localName, true));
		
		gBackingStore.pack();
		
		return gBackingStore;
	}
	
	private Composite createPropertiesGroup(Composite comp) {
		//Properties Metadata
		gProperties = new Group(comp, SWT.NONE);
		gProperties.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gProperties.setLayoutData(gd);
		gProperties.setText("Properties Metadata");
		
		String columns[] = new String[]{ "Property", "Present in Index","Encrypted", "Max Length", "Reverse References"};
		taDomainObjectPropertiesProviderModel = new DomainObjectPropertiesProviderModel(modelmgr);  
		taDomainObjectPropertiesProviderUi = new TableProviderUi(gProperties, columns, false, taDomainObjectPropertiesProviderModel);
		taDomainObjectProperties = taDomainObjectPropertiesProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 120;
		taDomainObjectProperties.setLayoutData(gd);
		
		indexColumnListener = taDomainObjectPropertiesProviderUi.tableTextModifyMouseListener(taDomainObjectProperties, 1, 0, TableProviderUi.TYPE_COMBO, new String[] {"true", "false"});
		taDomainObjectProperties.addMouseListener(indexColumnListener);
		
		encryptionColumnListener = taDomainObjectPropertiesProviderUi.tableTextModifyMouseListener(taDomainObjectProperties, 2, 0, TableProviderUi.TYPE_COMBO, new String[] {"true", "false"});
		taDomainObjectProperties.addMouseListener(encryptionColumnListener);
		
		maxSizeColumnListener = taDomainObjectPropertiesProviderUi.tableTextModifyMouseListener(taDomainObjectProperties, 3);
		taDomainObjectProperties.addMouseListener(maxSizeColumnListener);
		
		reverseReferencesColumnListener = taDomainObjectPropertiesProviderUi.tableTextModifyMouseListener(taDomainObjectProperties, 4, 0, TableProviderUi.TYPE_COMBO, new String[] {"true", "false"});
		taDomainObjectProperties.addMouseListener(reverseReferencesColumnListener);
		
		taDomainObjectProperties.addListener(SWT.Modify, getPropertiesTableModifyListener());
		gProperties.pack();
		return gProperties; 
	}
	
	
	private Composite createCompositeIndexGroup(Composite comp) {
        //Composite Indexes
	    gIndexes = new Group(comp, SWT.NONE);
	    gIndexes.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        gd.widthHint = 500;
        gIndexes.setLayoutData(gd);
        gIndexes.setText("Composite Indexes");
        
        String columns[] = new String[]{ "Index Name", "Properties"};
        indexTableProviderModel = new CompIndexTableProviderModel(comp);  
        compIndexTableProviderUi = new TableProviderUi(gIndexes, columns, true, indexTableProviderModel);
        taCompIndexes = compIndexTableProviderUi.createTable(true, false, false, true);
        
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 120;
        taCompIndexes.setLayoutData(gd);

        taCompIndexes.addListener(SWT.Modify, getCompIndexTableModifyListener());
        gIndexes.pack();
        return gIndexes;
    }
	
	private Listener getTextModifyListener(final Text tField, final String key, final boolean isBs) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = false;
				if (!isBs) {
					 updated = modelmgr.updateDomainObject(domainObj, key, tField.getText());
				} else {
					updated = modelmgr.updateDomainObjectBackingStore(domainObj, key, tField.getText());
				}
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getCheckboxSelectionListener(final Button bField, final String key, final boolean isBs) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = false;
				if (!isBs) {
					modelmgr.updateDomainObject(domainObj, key, new Boolean(bField.getSelection()).toString());
				} else {
					boolean selected = bField.getSelection();
					modelmgr.updateDomainObjectBackingStore(domainObj, key, new Boolean(selected).toString());
					if (key.equals(ConfigNS.Elements.ENABLED.localName))
						enableBackingStoreElements(selected);
				}
				if (updated)
					BlockUtil.refreshViewer(viewer);
				/*
				if (key.equals(ConfigNS.Elements.PRE_LOAD_ENABLED.localName)) {
					tPreloadFetchSize.setEnabled(bField.getSelection());
					cPreloadHandles.setEnabled(bField.getSelection());
				}
				*/
			}
		};
		return listener;
	}
	
	private Listener getComboSelectionListener(final Combo cField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String value = cField.getText();
				if (key.equals(ConfigNS.Elements.MODE.localName)) {
					enableCacheConfigElements(modelmgr.isCacheMode(value, true));	//use the combo's UI value to enable/disable fields & not the model's value
					if(modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)
							|| ( modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) &&
										modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName).equals(BackingStoreConfig.TYPE_BDB) )){
							
							cPreloadEntities.setEnabled(false);
							cPreloadHandles.setEnabled(false);
							tPreloadFetchSize.setEnabled(false);
						}
					if (!modelmgr.isCacheMode(value, true) && bHasBackingStore.getSelection()) {
						// CR BE-11471
						bHasBackingStore.setSelection(false);
						modelmgr.updateDomainObjectBackingStore(domainObj, ConfigNS.Elements.ENABLED.localName, "false");
					}
					value = modelmgr.getDomainObjectModeModelString(value);
				}
				boolean updated = modelmgr.updateDomainObject(domainObj, key, value);
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getPropertiesTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateDomainObjectProperties(domainObj, taDomainObjectProperties);
			}
		};
		return listener;
	}
	
    private void updateDomainObjectProperties(DomainObject domainObj, Table table) {
        if (table == null)
            return;
        domainObj.props.clear();
        for (TableItem item: table.getItems()) {
        	DomainObjectProperty property = domainObj.new DomainObjectProperty();
        	//property.values.put(property.keys[1], item.getText(1));
        	TableEditor editor = (TableEditor) item.getData();
        	String isInIndex = ((Button) editor.getEditor()).getSelection()? "true" : "false";
        	property.values.put(property.keys[0], isInIndex);
        	
        	if(isInIndex.equalsIgnoreCase("true")){
        		property.values.put(property.keys[1], "false");
        	//	table.removeMouseListener(encryptionColumnListener);
        		item.setBackground(2, Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        		
        	}
        	else{
        		property.values.put(property.keys[1], item.getText(2));
        	//	table.addMouseListener(encryptionColumnListener);
        		item.setBackground(2, item.getBackground(0));
        	}

        	for (int col=3; col<table.getColumnCount(); col++)
            	property.values.put(property.keys[col-1], item.getText(col));
            domainObj.props.put(item.getText(0), property);
        }
        modelmgr.modified();
    }
    
    private Listener getCompIndexTableModifyListener() {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                updateCompositeIndexes(domainObj, taCompIndexes);
            }
        };
        return listener;
    }
    
    private void updateCompositeIndexes(DomainObject domainObj, Table table) {
        if (table == null) {
            return;
        }
        domainObj.compIdxList.clear();
        for (TableItem item : table.getItems()) {
            DomainObjectCompositeIndex domainObjectCompositeIndex = domainObj.new DomainObjectCompositeIndex();
            domainObjectCompositeIndex.name = item.getText(0);
            domainObjectCompositeIndex.values = CompIndexTableProviderModel.getListFromString(item.getText(1));
            domainObj.compIdxList.add(domainObjectCompositeIndex);
        }
        modelmgr.modified();
    }
    
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1){
			domainObj = ((DomainObject) ssel.getFirstElement());
			if(domainObj!=null){
				if(domainObj.entity !=null){
						if(domainObj.entity.getElementType()!=ELEMENT_TYPES.CONCEPT){
						//	lConceptTTLvalue.setVisible(false);
							tConceptTTLvalue.setVisible(false);
							super.lField.setVisible(false);
						}else{
						//	lConceptTTLvalue.setVisible(true);
							tConceptTTLvalue.setVisible(true);
							super.lField.setVisible(true);
						}
				}
			}
		}else{
			domainObj = null;
		}
		update();
	}
	
	private void enableCacheConfigElements(boolean enabled) {
		gCache.setEnabled(enabled);
		lPreloadEntities.setEnabled(enabled);
		cPreloadEntities.setEnabled(enabled);
		lPreloadHandles.setEnabled(enabled);
		cPreloadHandles.setEnabled(enabled);
		lPreloadFetchSize.setEnabled(enabled);
		tPreloadFetchSize.setEnabled(enabled);
//		lPreprocessor.setEnabled(enabled);
//		tPreprocessor.setEnabled(enabled);
//		bPreprocessorBrowse.setEnabled(enabled);
		lCheckForVersion.setEnabled(enabled);
		cCheckForVersion.setEnabled(enabled);
		lConstant.setEnabled(enabled);
		cConstant.setEnabled(enabled);
		lEnableTracking.setEnabled(enabled);
		cEnableTracking.setEnabled(enabled);
		lEvictFromCacheOnUpdate.setEnabled(enabled);
		cEvictFromCacheOnUpdate.setEnabled(enabled);
		lIsCacheLimited.setEnabled(enabled);
		cIsCacheLimited.setEnabled(enabled);
//		lSubscribe.setEnabled(enabled);
//		cSubscribe.setEnabled(enabled);
//		lConceptTTLvalue.setEnabled(enabled);
		
		gBackingStore.setEnabled(enabled);
		lHasBackingStore.setEnabled(enabled);
		boolean isMasterBackingStoreEnabled = enabled && (modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL)
													||modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING));
		
		
		bHasBackingStore.setEnabled(isMasterBackingStoreEnabled);
		enableBackingStoreElements(isMasterBackingStoreEnabled);
		
		gProperties.setEnabled(enabled);
		taDomainObjectProperties.setEnabled(enabled);
		
		taDomainObjectProperties.removeMouseListener(indexColumnListener);
		if (enabled) {
			taDomainObjectProperties.addMouseListener(indexColumnListener);
		}
		for (TableItem item: taDomainObjectProperties.getItems()) {
			item.setBackground(1, enabled? item.getBackground(0): Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			if (item.getData() != null) {
				Button button = (Button) ((TableEditor)item.getData()).getEditor();
				if (button != null) {
					button.setBackground(enabled? item.getBackground(0): Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
					button.setEnabled(enabled);
				}
			}
		}
	}
	
	private void enableBackingStoreElements(boolean enabled) {
		boolean enBs = bHasBackingStore.getSelection();
		
		lTableName.setEnabled(enBs && enabled);
		tTableName.setEnabled(enBs && enabled);
		
		String provider=modelmgr.getModel().om.cacheOm.provider;
		boolean securityEnabled=modelmgr.getModel().om.cacheOm.securityConfig.securityEnabled;
		boolean enableEncryption=false;
		if("TIBCO".equalsIgnoreCase(provider)){
			if(securityEnabled==true){
				taDomainObjectProperties.addMouseListener(encryptionColumnListener);
				enableEncryption=true;
			}
			else{
				taDomainObjectProperties.removeMouseListener(encryptionColumnListener);
				enableEncryption=false;
			}
		}
		for (TableItem item: taDomainObjectProperties.getItems()) {
			item.setBackground(2, enableEncryption? item.getBackground(0): Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		}
		
		taDomainObjectProperties.removeMouseListener(maxSizeColumnListener);
		taDomainObjectProperties.removeMouseListener(reverseReferencesColumnListener);
		if (enabled) {
			taDomainObjectProperties.addMouseListener(maxSizeColumnListener);
			taDomainObjectProperties.addMouseListener(reverseReferencesColumnListener);
		}
		for (TableItem item: taDomainObjectProperties.getItems()) {
			item.setBackground(3, enBs && enabled? item.getBackground(0): Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			item.setBackground(4, enBs && enabled? item.getBackground(0): Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		}
	}
	
	private void update() {
		if (domainObj != null) {
			tUri.setText(domainObj.values.get(ConfigNS.Elements.URI.localName));
			
			String mode = domainObj.values.get(ConfigNS.Elements.MODE.localName);
			cMode.setText(modelmgr.getDomainObjectModeDisplayString(mode));
			cPreloadEntities.setText(domainObj.values.get(ConfigNS.Elements.PRE_LOAD_ENABLED.localName));
			cPreloadHandles.setText(domainObj.values.get(ConfigNS.Elements.PRE_LOAD_HANDLES.localName));
			tPreloadFetchSize.setText(domainObj.values.get(ConfigNS.Elements.PRE_LOAD_FETCH_SIZE.localName));
//			tPreprocessor.setText(domainObj.values.get(ConfigNS.Elements.PRE_PROCESSOR.localName));
//			cSubscribe.setText(domainObj.values.get(ConfigNS.Elements.SUBSCRIBE.localName));
			cCheckForVersion.setText(domainObj.values.get(ConfigNS.Elements.CHECK_FOR_VERSION.localName));
			cConstant.setText(domainObj.values.get(ConfigNS.Elements.CONSTANT.localName));
			cIsCacheLimited.setText(domainObj.values.get(ConfigNS.Elements.CACHE_LIMITED.localName));
			cEnableTracking.setText(domainObj.values.get(ConfigNS.Elements.ENABLE_TRACKING.localName));
			cEvictFromCacheOnUpdate.setText(domainObj.values.get(ConfigNS.Elements.EVICT_ON_UPDATE.localName));

			if(GvUtil.isGlobalVar(domainObj.values.get(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName))){
				tConceptTTLvalue.setGvModeValue(domainObj.values.get(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName));
				tConceptTTLvalue.onSetGvMode();
			}else{
				tConceptTTLvalue.setFieldModeValue(domainObj.values.get(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName));
				tConceptTTLvalue.onSetFieldMode();
//				((Text)tConceptTTLvalue.getField()).setText(domainObj.values.get(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName));
			}

			bHasBackingStore.setSelection(modelmgr.getBooleanValue(domainObj.bs.values.get(ConfigNS.Elements.ENABLED.localName)));
			tTableName.setText(domainObj.bs.values.get(ConfigNS.Elements.TABLE_NAME.localName));
			
			updatePropertiesTable();
			updateCompositeIndexes();
			enableCacheConfigElements(modelmgr.isCacheMode(cMode.getText(), true));
			if(modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)
					|| ( modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) &&
								modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName).equals(BackingStoreConfig.TYPE_BDB) )){
					
					cPreloadEntities.setEnabled(false);
					cPreloadHandles.setEnabled(false);
					tPreloadFetchSize.setEnabled(false);
				}
			//enableBackingStoreElements(bHasBackingStore.getSelection());
			
			validateFields();
		}
	}
	
	private void updateCompositeIndexes() {
	    indexTableProviderModel.setDomainObject(domainObj);
	    ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (DomainObjectCompositeIndex compIndex: domainObj.compIdxList) {
	        ArrayList<String> rowData = new ArrayList<String>();
	        rowData.add(compIndex.name);
	        rowData.add(CompIndexTableProviderModel.getCommaSeperatedValues(compIndex.values));
	        tableData.add(rowData);
	    }
	    compIndexTableProviderUi.setTableData(tableData, PanelUiUtil.TEXT_FIELD_SIZE_HINT);
    }

    private void updatePropertiesTable() {
		taDomainObjectPropertiesProviderModel.setDomainObject(domainObj);
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (Map.Entry<String, DomainObjectProperty> entry: domainObj.props.entrySet()) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	rowData.add(entry.getKey());
	    	for (int i=0; i<entry.getValue().keys.length; i++) {
	    		rowData.add(entry.getValue().values.get(entry.getValue().keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taDomainObjectPropertiesProviderUi.setTableData(tableData, PanelUiUtil.TEXT_FIELD_SIZE_HINT, 1);
	}
	
	public boolean validateFields() {
		boolean valid = true;
		return valid;
	}

	@Override
	public Listener getListener(final Control field, final String key) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(field instanceof Text){
					if(GvUtil.isGlobalVar(((Text)field).getText())){
						boolean updated = false;
						updated = modelmgr.updateDomainObject(domainObj, key, tConceptTTLvalue.getGvText().getText());
						if (updated)
							BlockUtil.refreshViewer(viewer);
					}else{
						modelmgr.updateDomainObject(domainObj,key,((Text)tConceptTTLvalue.getField()).getText());
					}
				}
			}
		};
	}

	@Override
	public String getValue(String key) {
		if(domainObj == null){
			return "";
		}
		return modelmgr.getDomainObjectsValue(key);
	}
}
