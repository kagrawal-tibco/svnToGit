package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.Connection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 10, 2010 11:09:01 AM
 */

public class NodeBackingStoreConnectionPage extends ClusterNodeDetailsPage {
	
	private Text tUri, tMinSize, tMaxSize, tInitSize;
	private Connection connection;
	private Button bUriBrowse;
	
	public NodeBackingStoreConnectionPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		PanelUiUtil.createLabel(client, "URI: ");
		final Composite uriComp = new Composite(client, SWT.NONE);
		uriComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		uriComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tUri = PanelUiUtil.createText(uriComp);
		bUriBrowse = PanelUiUtil.createBrowsePushButton(uriComp, tUri);
		bUriBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(parent, modelmgr.project, new String[]{"sharedjdbc"}, tUri));
		tUri.addListener(SWT.Modify, getTextModifyListener(tUri, ConfigNS.Elements.URI.localName));
		
		tMinSize = createTextField(client, "Min Size: ", ConfigNS.Elements.MIN_SIZE.localName);
		tMaxSize = createTextField(client, "Max Size: ", ConfigNS.Elements.MAX_SIZE.localName);
		tInitSize = createTextField(client, "Initial Size: ", ConfigNS.Elements.INITIAL_SIZE.localName);
		
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
		
	private Listener getTextModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateBackingStoreConnection(connection, key, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			connection = ((Connection) ssel.getFirstElement());
		else
			connection = null;
		update();
	}
	
	private void update() {
		if (connection != null) {
			tUri.setText(connection.values.get(ConfigNS.Elements.URI.localName));
			tMinSize.setText(connection.values.get(ConfigNS.Elements.MIN_SIZE.localName));
			tMaxSize.setText(connection.values.get(ConfigNS.Elements.MAX_SIZE.localName));
			tInitSize.setText(connection.values.get(ConfigNS.Elements.INITIAL_SIZE.localName));
			String persistenceOption = modelmgr.getBackingStoreValue(BackingStore.PERSISTENCE_OPTION);
			String dataBaseType = modelmgr.getBackingStoreValue(ConfigNS.Elements.TYPE.localName);
			boolean enUri = false;
			if (persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) && !dataBaseType.equals(BackingStoreConfig.TYPE_BDB)) {
				enUri = true;
			}
			tUri.setEnabled(enUri);
			bUriBrowse.setEnabled(enUri);
			validateFields();
		}
	}

	public boolean validateFields() {
		boolean valid = true;
		return valid;
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