package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 10, 2010 1:13:28 PM
 */

public class NodeDomainObjectDefaultPage extends ClusterNodeDetailsPage {
	
	private Combo cDefaultMode;
	private Text tPreloadFetchSize;
	private GvField tConceptTTLvalue;
	private Button bPreloadEntities, bPreloadHandles, bCheckForVersion, bConstant, bEnableTracking, bEvictFromCacheOnUpdate, bIsCacheLimited, bSubscribe;
	
	public NodeDomainObjectDefaultPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		PanelUiUtil.createLabel(client, "Mode: ");
		String[] doTypes = new String[] { CacheOm.DO_MODE_UI_MEMORY, CacheOm.DO_MODE_UI_CACHE };
		cDefaultMode = PanelUiUtil.createComboBox(client, doTypes);
		cDefaultMode.addListener(SWT.Selection, getComboSelectionListener(cDefaultMode, ConfigNS.Elements.DEFAULT_MODE.localName));
		
		bPreloadEntities = createCheckBoxField(client, "Preload Entities: ", ConfigNS.Elements.PRE_LOAD_ENABLED.localName);
		bPreloadHandles = createCheckBoxField(client, "Preload Handles: ", ConfigNS.Elements.PRE_LOAD_HANDLES.localName);
		tPreloadFetchSize = createTextField(client, "Preload Fetch Size [Set 0 for All]: ", ConfigNS.Elements.PRE_LOAD_FETCH_SIZE.localName);
		
		bCheckForVersion = createCheckBoxField(client, "Check for Version: ", ConfigNS.Elements.CHECK_FOR_VERSION.localName);
		bConstant = createCheckBoxField(client, "Constant: ", ConfigNS.Elements.CONSTANT.localName);
		bEnableTracking = createCheckBoxField(client, "Enable Tracking: ", ConfigNS.Elements.ENABLE_TRACKING.localName);		
		bEvictFromCacheOnUpdate = createCheckBoxField(client, "Evict from Cache on Update: ", ConfigNS.Elements.EVICT_ON_UPDATE.localName);
		bIsCacheLimited = createCheckBoxField(client, "Is Cache Limited: ", ConfigNS.Elements.CACHE_LIMITED.localName);
//		bSubscribe = createCheckBoxField(client, "Subscribe Cluster: ", ConfigNS.Elements.SUBSCRIBE.localName);
	//	conceptTTLvalue = createTextField(client, "Concept TTL: ", ConfigNS.Elements.CONCEPT_TTL_VALUE.localName);
		tConceptTTLvalue = createGvTextField(client,"Concept TTL: ", Elements.CONCEPT_TTL_VALUE.localName);
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Text createTextField(Composite parent, String label, String key) {
		PanelUiUtil.createLabel(parent, label);
		Text tField = PanelUiUtil.createText(parent);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, key));
		return tField;
	}
	
	private Button createCheckBoxField(Composite parent, String label, String key) {
		PanelUiUtil.createLabel(parent, label);
		Button bField = PanelUiUtil.createCheckBox(parent, "");
		bField.addListener(SWT.Selection, getCheckboxSelectionListener(bField, key));
		return bField;
	}
	
	private Listener getTextModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateDomainObjectsValue(key, tField.getText());
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
				boolean updated = modelmgr.updateDomainObjectsValue(key, new Boolean(bField.getSelection()).toString());
				if (updated)
					BlockUtil.refreshViewer(viewer);
				/*
				if (key.equals(ConfigNS.Elements.PRE_LOAD_ENABLED.localName)) {
					tPreloadFetchSize.setEnabled(bField.getSelection());
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
				if (key.equals(ConfigNS.Elements.DEFAULT_MODE.localName)) {
					value = modelmgr.getDomainObjectModeModelString(value);
				}
				boolean updated = modelmgr.updateDomainObjectsValue(key, value);
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		update();
	}
	
	private void update() {
		String mode = modelmgr.getDomainObjectsValue(ConfigNS.Elements.DEFAULT_MODE.localName);
		cDefaultMode.setText(modelmgr.getDomainObjectModeDisplayString(mode));
		
		bPreloadEntities.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.PRE_LOAD_ENABLED.localName)));
		bPreloadHandles.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.PRE_LOAD_HANDLES.localName)));
		
		if(modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)
			|| ( modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) &&
						modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName).equals(BackingStoreConfig.TYPE_BDB) )){
			
			bPreloadEntities.setEnabled(false);
			bPreloadHandles.setEnabled(false);
			tPreloadFetchSize.setEnabled(false);
		}
		else{
			
			bPreloadEntities.setEnabled(true);
			bPreloadHandles.setEnabled(true);
			tPreloadFetchSize.setEnabled(true);
		}
		
		tPreloadFetchSize.setText(modelmgr.getDomainObjectsValue(ConfigNS.Elements.PRE_LOAD_FETCH_SIZE.localName));
		
		bConstant.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CONSTANT.localName)));
		bCheckForVersion.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CHECK_FOR_VERSION.localName)));
		bEnableTracking.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.ENABLE_TRACKING.localName)));
		bEvictFromCacheOnUpdate.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.EVICT_ON_UPDATE.localName)));
		bIsCacheLimited.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CACHE_LIMITED.localName)));
//		bSubscribe.setSelection(modelmgr.getBooleanValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.SUBSCRIBE.localName)));
	//	conceptTTLvalue.setText(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName));
		
		if(GvUtil.isGlobalVar(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName))){
			tConceptTTLvalue.setGvModeValue(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName));
			tConceptTTLvalue.onSetGvMode();
		}else{
			((Text)tConceptTTLvalue.getField()).setText(modelmgr.getDomainObjectsValue(ConfigNS.Elements.CONCEPT_TTL_VALUE.localName));
		}
		
		validateFields();
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
						boolean updated = modelmgr.updateDomainObjectsValue(key, tConceptTTLvalue.getGvText().getText());
						if (updated)
							BlockUtil.refreshViewer(viewer);
					}else{
						boolean updated = modelmgr.updateDomainObjectsValue(key, ((Text)tConceptTTLvalue.getField()).getText());
						if (updated)
							BlockUtil.refreshViewer(viewer);
					}
				}
			}
		};
	}

	@Override
	public String getValue(String key) {
		return modelmgr.getDomainObjectsValue(key);
	}
}
