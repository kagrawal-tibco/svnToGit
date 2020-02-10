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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 27, 2010 2:26:57 PM
 */

public class NodeCacheOmPage extends ClusterNodeDetailsPage {
	
	private Combo cProvider;
//	private Text tBackupCopies, tCacheAgentQuorum, tEntityCacheSize,
//			tObjectTableSize, tDiscoveryURL, tListenURL, tRemoteListenURL, tProtocolTimeout,
//			tReadTimeout, tWriteTimeout, tLockTimeout, tShutdownWait, tWorkerThreadsCount, tLogDirectoy, tLogFile;

	private GvField gtBackupCopies, gtCacheAgentQuorum, gtEntityCacheSize,
			gtObjectTableSize, gtDiscoveryURL, gtListenURL, gtRemoteListenURL,
			gtProtocolTimeout, gtReadTimeout, gtWriteTimeout, gtLockTimeout,
			gtShutdownWait, gtWorkerThreadsCount;
	private Label lDiscoveryURL, lListenURL, lRemoteListenURL, lProtocolTimeout,
	lReadTimeout, lWriteTimeout, lLockTimeout, lShutdownWait, lWorkerThreadsCount,lExplicitTuple;
	private CacheOm cacheOm;
	private Button bExplicitTuple;
	private SecurityConfigGroup securityConfigGroup;
	
	public NodeCacheOmPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		PanelUiUtil.createLabel(client, "Provider: ");
		cProvider = PanelUiUtil.createComboBox(client, new String[]{CacheOm.PROVIDER_TIBCO, CacheOm.PROVIDER_ORACLE});
		cProvider.addListener(SWT.Selection, getProviderModifyListener());
		
		//tCacheAgentQuorum = createTextField("Cache Agent Quorum: ", Elements.CACHE_AGENT_QUORUM.localName);
		gtCacheAgentQuorum = createGvTextField(client, "Cache Agent Quorum: ", Elements.CACHE_AGENT_QUORUM.localName);
		
		//tBackupCopies = createTextField("Number of Backup Copies: ", Elements.BACKUP_COPIES.localName);
		gtBackupCopies = createGvTextField(client, "Number of Backup Copies: ", Elements.BACKUP_COPIES.localName);
		
		//tEntityCacheSize = createTextField("Entity Cache Size: ", Elements.ENTITY_CACHE_SIZE.localName);
		gtEntityCacheSize = createGvTextField(client, "Entity Cache Size: ", Elements.ENTITY_CACHE_SIZE.localName);
		
		//PanelUiUtil.createLabel(client, "Object Table Cache Size: ");
		//tObjectTableSize = PanelUiUtil.createText(client);
		//tObjectTableSize.addListener(SWT.Modify, getObjectTableSizeModifyListener());
		gtObjectTableSize = createGvTextField(client, "Object Table Cache Size: ", GvFields.ObjectTableCacheSize.toString());
		
		//AS related configurations
		lDiscoveryURL = PanelUiUtil.createLabel(client, "Discovery URL: ");
		//tDiscoveryURL = createTextField(null, Elements.DISCOVERY_URL.localName);
		gtDiscoveryURL = createGvTextField(client, Elements.DISCOVERY_URL.localName);
		//gtDiscoveryURL.getGvToggleButton().setEnabled(false);

		lListenURL = PanelUiUtil.createLabel(client, "Listen URL: ");
		//tListenURL = createTextField(null, Elements.LISTEN_URL.localName);
		gtListenURL = createGvTextField(client, Elements.LISTEN_URL.localName);
//		gtListenURL.getGvToggleButton().setEnabled(false);

		lRemoteListenURL = PanelUiUtil.createLabel(client, "Remote Listen URL: ");
		//tRemoteListenURL = createTextField(null, Elements.REMOTE_LISTEN_URL.localName);
		gtRemoteListenURL = createGvTextField(client, Elements.REMOTE_LISTEN_URL.localName);
//		gtRemoteListenURL.getGvToggleButton().setEnabled(false);

		lProtocolTimeout = PanelUiUtil.createLabel(client, "Protocol Timeout (ms): ");
		//tProtocolTimeout = createTextField(null, Elements.PROTOCOL_TIMEOUT.localName);
		gtProtocolTimeout = createGvTextField(client, Elements.PROTOCOL_TIMEOUT.localName);

		lReadTimeout = PanelUiUtil.createLabel(client, "Read Timeout (ms): ");
		//tReadTimeout = createTextField(null, Elements.READ_TIMEOUT.localName);
		gtReadTimeout = createGvTextField(client, Elements.READ_TIMEOUT.localName);

		lWriteTimeout = PanelUiUtil.createLabel(client, "Write Timeout (ms): ");
		//tWriteTimeout = createTextField(null, Elements.WRITE_TIMEOUT.localName);
		gtWriteTimeout = createGvTextField(client, Elements.WRITE_TIMEOUT.localName);

		lLockTimeout = PanelUiUtil.createLabel(client, "Lock Timeout (ms): ");
		//tLockTimeout = createTextField(null, Elements.LOCK_TIMEOUT.localName);
		gtLockTimeout = createGvTextField(client, Elements.LOCK_TIMEOUT.localName);
 
		lShutdownWait = PanelUiUtil.createLabel(client, "Shutdown Wait (ms): ");
		//tShutdownWait = createTextField(null, Elements.SHUTDOWN_WAIT.localName);
		gtShutdownWait = createGvTextField(client, Elements.SHUTDOWN_WAIT.localName);

		lWorkerThreadsCount = PanelUiUtil.createLabel(client, "Worker Threads Count: ");
		//tWorkerThreadsCount = createTextField(null, Elements.WORKERTHREADS_COUNT.localName);
		gtWorkerThreadsCount = createGvTextField(client, Elements.WORKERTHREADS_COUNT.localName);

		lExplicitTuple = PanelUiUtil.createLabel(client, "Store Properties As Individual Fields: ");
		bExplicitTuple = createCheckBox(null, Elements.EXPLICIT_TUPLE.localName);

		securityConfigGroup = new CacheOmSecurityConfigGroup(client, SWT.NONE, modelmgr);
		securityConfigGroup.createConfig();
				
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Listener getProviderModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String provider = cProvider.getText();
				if (provider.equalsIgnoreCase(CacheOm.PROVIDER_ORACLE)){
					provider = CacheOm.PROVIDER_COHERENCE;
					toggleTibcoProviderProperties(false);
				}
				else{
					toggleTibcoProviderProperties(true);
				}
				boolean updated = modelmgr.updateProvider(provider);
			}
		};
		return listener;
	}
	
//	private Listener getObjectTableSizeModifyListener() {
//		Listener listener = new Listener() {
//			@Override
//			public void handleEvent(Event event) {
//				String objectTableSize = tObjectTableSize.getText();
//				boolean updated = modelmgr.updateCacheOmObjectTableSize(objectTableSize);
//			}
//		};
//		return listener;
//	}
	
//	private Listener getLogLevelModifyListener() {
//		Listener listener = new Listener() {
//			@Override
//			public void handleEvent(Event event) {
//				String logLevel = cLogLevel.getText();
//				boolean updated = modelmgr.updateCacheOmValues(Elements.LOG_LEVEL.localName, logLevel);
//			}
//		};
//		return listener;
//	}
	
	private Listener getCheckboxSelectionListener(final Button bField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateCacheOmValues(key, new Boolean(bField.getSelection()).toString());
			}
		};
		return listener;
	}
	
	private Button createCheckBox(String label, String key){
		if(label != null){
			PanelUiUtil.createLabel(client, label);
		}
		Button checkBox = PanelUiUtil.createCheckBox(client, "");
		checkBox.addListener(SWT.Selection, getCheckboxSelectionListener(checkBox,key));
		return checkBox;
	}
	
	private Text createTextField(String label, String key) {
		if(label != null){
			PanelUiUtil.createLabel(client, label);
		}
		Text tField = PanelUiUtil.createText(client);
		tField.addListener(SWT.Modify, getCacheOmValuesModifyListener(tField, key));
		return tField;
	}
	
	private Listener getCacheOmValuesModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateCacheOmValues(key, tField.getText());
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			cacheOm = ((CacheOm) ssel.getFirstElement());
		else
			cacheOm = null;
		update();
	}
	
	private void update() {
		if (cacheOm != null) {
			modelmgr.setProvider(cacheOm, cacheOm.provider);	// first ensure that a valid provider is set
			if(modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)){
				modelmgr.updateCacheOmValues(Elements.EXPLICIT_TUPLE.localName, "true");	
				bExplicitTuple.setEnabled(false);
			}else{
				bExplicitTuple.setEnabled(true);
			}
			if (cacheOm.provider.equalsIgnoreCase(CacheOm.PROVIDER_COHERENCE)){
				cProvider.setText(CacheOm.PROVIDER_ORACLE);
				toggleTibcoProviderProperties(false);
			}
			else{ 
				cProvider.setText(CacheOm.PROVIDER_TIBCO);
				toggleTibcoProviderProperties(true);
			}
			gtCacheAgentQuorum.setValue(cacheOm.values.get(Elements.CACHE_AGENT_QUORUM.localName));
			gtBackupCopies.setValue(cacheOm.values.get(Elements.BACKUP_COPIES.localName));
			gtEntityCacheSize.setValue(cacheOm.values.get(Elements.ENTITY_CACHE_SIZE.localName));
			gtObjectTableSize.setValue(cacheOm.objectTableSize);
			gtDiscoveryURL.setValue(cacheOm.values.get(Elements.DISCOVERY_URL.localName));
			gtListenURL.setValue(cacheOm.values.get(Elements.LISTEN_URL.localName));
			gtRemoteListenURL.setValue(cacheOm.values.get(Elements.REMOTE_LISTEN_URL.localName));
			gtProtocolTimeout.setValue(cacheOm.values.get(Elements.PROTOCOL_TIMEOUT.localName));
			gtReadTimeout.setValue(cacheOm.values.get(Elements.READ_TIMEOUT.localName));
			gtWriteTimeout.setValue(cacheOm.values.get(Elements.WRITE_TIMEOUT.localName));
			gtLockTimeout.setValue(cacheOm.values.get(Elements.LOCK_TIMEOUT.localName));
			gtShutdownWait.setValue(cacheOm.values.get(Elements.SHUTDOWN_WAIT.localName));
			gtWorkerThreadsCount.setValue(cacheOm.values.get(Elements.WORKERTHREADS_COUNT.localName));
			bExplicitTuple.setSelection(Boolean.parseBoolean(cacheOm.values.get(Elements.EXPLICIT_TUPLE.localName)));
			securityConfigGroup.updateConfig(cacheOm.securityConfig);
			validateFields();
		}
	}
	
	public boolean validateFields() {
		boolean valid = true;
		return valid;
	}
	
	private void toggleTibcoProviderProperties(Boolean flag) {
		//Labels
		lDiscoveryURL.setVisible(flag);
		lListenURL.setVisible(flag);
		lRemoteListenURL.setVisible(flag);
		lProtocolTimeout.setVisible(flag);
		lReadTimeout.setVisible(flag);
		lWriteTimeout.setVisible(flag);
		lLockTimeout.setVisible(flag);
		lShutdownWait.setVisible(flag);
		lWorkerThreadsCount.setVisible(flag);
		lExplicitTuple.setVisible(flag);
		//Text
		gtDiscoveryURL.setVisible(flag);
		gtListenURL.setVisible(flag);
		gtRemoteListenURL.setVisible(flag);
		gtProtocolTimeout.setVisible(flag);
		gtReadTimeout.setVisible(flag);
		gtWriteTimeout.setVisible(flag);
		gtLockTimeout.setVisible(flag);
		gtShutdownWait.setVisible(flag);
		gtWorkerThreadsCount.setVisible(flag);
		bExplicitTuple.setVisible(flag);
		securityConfigGroup.setVisible(flag);
	}

	@Override
	public Listener getListener(final Control field, final String key) {
		return new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(field instanceof Text){
					if(key.equals(GvFields.ObjectTableCacheSize.toString())){
						 modelmgr.updateCacheOmObjectTableSize(((Text) field).getText());
					}else{
						modelmgr.updateCacheOmValues(key, ((Text) field).getText());
					}
				}
				else if(field instanceof Button){
					modelmgr.updateCacheOmValues(key, new Boolean(((Button) field).getSelection()).toString());
				}
				else if(field instanceof Combo){
					modelmgr.updateCacheOmValues(key, ((Combo) field).getText());
				}
			}
		};
	}

	@Override
	public String getValue(String key) {
		if (cacheOm == null) {
			return "";
		}
		if (key.equals(GvFields.ObjectTableCacheSize.toString())) {
			return cacheOm.objectTableSize;
		} else {
			return cacheOm.values.get(key);
		}
	}
	
	private enum GvFields {
		ObjectTableCacheSize,
	}
}