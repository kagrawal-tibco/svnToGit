package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.HttpProperties;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Apr 21, 2010 10:58:48 PM
 */

public class NodeProcUnitHttpPropertiesPage extends ClusterNodeDetailsPage {

	private HttpProperties httpProps;
	private Text tConnectionTimeout, tAcceptCount, tConnectionLinger, tSocketBufferSize, tMaxProcessors, tDocRoot, tDocPage, tKeyManager, tTrustManager;
	private Button bTcpNoDelay, bStaleConnectionCheck;
	private Table taProtocols, taCiphers;
	private ProcUnitProtocolsTableProviderModel taProtocolsModel;
	private TableProviderUi taProtocolsProviderUi, taCiphersProviderUi;
	private ProcUnitCiphersTableProviderModel taCiphersModel;
	
	public NodeProcUnitHttpPropertiesPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		tConnectionTimeout = createConfigTextField(client, "Connection Timeout (msec): ", Elements.CONNECTION_TIMEOUT.localName);
		tAcceptCount = createConfigTextField(client, "Accept Count: ", Elements.ACCEPT_COUNT.localName);
		tConnectionLinger = createConfigTextField(client, "Connection Linger: ", Elements.CONNECTION_LINGER.localName);
		tSocketBufferSize = createConfigTextField(client, "Socket Output Buffer Size: ", Elements.SOCKET_OUTPUT_BUFFER_SIZE.localName);
		tMaxProcessors = createConfigTextField(client, "Max Processors: ", Elements.MAX_PROCESSORS.localName);
		bTcpNoDelay = createConfigButtonField(client, "TCP No Delay: ", Elements.TCP_NO_DELAY.localName);
		tDocRoot = createConfigTextField(client, "Document Root: ", Elements.DOCUMENT_ROOT.localName);
		tDocPage = createConfigTextField(client, "Document Page: ", Elements.DOCUMENT_PAGE.localName);
		bStaleConnectionCheck = createConfigButtonField(client, "Stale Connection Check: ", Elements.STALE_CONNECTION_CHECK.localName);

		Group gSsl = new Group(client, SWT.NONE);
		gSsl.setText("Ssl Properties");
		gSsl.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gSsl.setLayoutData(gd);
		tKeyManager = createConfigTextField(gSsl, "Key Manager Algorithm: ", Elements.KEY_MANAGER_ALGORITHM.localName);
		tTrustManager = createConfigTextField(gSsl, "Trust Manager Algorithm: ", Elements.TRUST_MANAGER_ALGORITHM.localName);
		
		Group gProtocols = new Group(gSsl, SWT.NONE);
		gProtocols.setText("Protocols");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gProtocols.setLayoutData(gd);
		gProtocols.setLayout(new GridLayout(1, false));
		taProtocolsModel = new ProcUnitProtocolsTableProviderModel(modelmgr);  
		taProtocolsProviderUi = new TableProviderUi(gProtocols, new String[]{"Protocol"}, true, taProtocolsModel);
		taProtocols = taProtocolsProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 60;
		taProtocols.setLayoutData(gd);
		taProtocols.addMouseListener(taProtocolsProviderUi.tableTextModifyMouseListener(taProtocols, 0));	    
		taProtocols.addListener(SWT.Modify, getProtocolsTableModifyListener());

		Group gCiphers = new Group(gSsl, SWT.NONE);
		gCiphers.setText("Ciphers");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gCiphers.setLayoutData(gd);
		gCiphers.setLayout(new GridLayout(1, false));
		taCiphersModel = new ProcUnitCiphersTableProviderModel(modelmgr);  
		taCiphersProviderUi = new TableProviderUi(gCiphers, new String[]{"Cipher"}, true, taCiphersModel);
		taCiphers = taCiphersProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 60;
		taCiphers.setLayoutData(gd);
		taCiphers.addMouseListener(taCiphersProviderUi.tableTextModifyMouseListener(taCiphers, 0));	    
		taCiphers.addListener(SWT.Modify, getCiphersTableModifyListener());
		
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}	
	
	private Text createConfigTextField(Composite comp, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		Text tField = PanelUiUtil.createText(comp);
		tField.addListener(SWT.Modify, getListener(tField, modelId));
		return tField;
	}

	private Button createConfigButtonField(Composite comp, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		Button bField = PanelUiUtil.createCheckBox(comp, "");
		bField.addListener(SWT.Selection, getListener(bField, modelId));
		return bField;
	}
	
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					String value = ((Text) field).getText(); 
					if (!key.equals(Elements.KEY_MANAGER_ALGORITHM.localName) && !key.equals(Elements.TRUST_MANAGER_ALGORITHM.localName))
						modelmgr.updateProcUnitHttpProperties(httpProps.procUnit, key, value);
					else
						modelmgr.updateProcUnitHttpSslProperties(httpProps.procUnit, key, value);
				} else if (field instanceof Button) {
					String value = new Boolean(((Button)field).getSelection()).toString();
					modelmgr.updateProcUnitHttpProperties(httpProps.procUnit, key, value);
				}
			}
		};
		return listener;
	}
	
	private Listener getProtocolsTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateProcUnitProtocols(httpProps.procUnit, taProtocols);
			}
		};
		return listener;
	}

	private Listener getCiphersTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateProcUnitCiphers(httpProps.procUnit, taCiphers);
			}
		};
		return listener;
	}
	

    public void updateProcUnitProtocols(ProcessingUnit procUnit, Table taProtocols) {
        if (procUnit == null)
            return;
        procUnit.httpProperties.ssl.protocols.clear();
        for (TableItem item: taProtocols.getItems()) {
            procUnit.httpProperties.ssl.protocols.add(item.getText(0));
        }
        modelmgr.modified();
    }

    public void updateProcUnitCiphers(ProcessingUnit procUnit, Table taCiphers) {
        if (procUnit == null)
            return;
        procUnit.httpProperties.ssl.ciphers.clear();
        for (TableItem item: taCiphers.getItems()) {
            procUnit.httpProperties.ssl.ciphers.add(item.getText(0));
        }
        modelmgr.modified();
    }
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			httpProps = ((HttpProperties) ssel.getFirstElement());
		else
			httpProps = null;
		update();
	}
	
	private void update() {
		if (httpProps != null) {
			tConnectionTimeout.setText(httpProps.properties.get(Elements.CONNECTION_TIMEOUT.localName));
			tAcceptCount.setText(httpProps.properties.get(Elements.ACCEPT_COUNT.localName));
			tConnectionLinger.setText(httpProps.properties.get(Elements.CONNECTION_LINGER.localName));
			tSocketBufferSize.setText(httpProps.properties.get(Elements.SOCKET_OUTPUT_BUFFER_SIZE.localName));
			tMaxProcessors.setText(httpProps.properties.get(Elements.MAX_PROCESSORS.localName));
			bTcpNoDelay.setSelection(modelmgr.getBooleanValue(httpProps.properties.get(Elements.TCP_NO_DELAY.localName)));
			tDocRoot.setText(httpProps.properties.get(Elements.DOCUMENT_ROOT.localName));
			tDocPage.setText(httpProps.properties.get(Elements.DOCUMENT_PAGE.localName));
			bStaleConnectionCheck.setSelection(modelmgr.getBooleanValue(httpProps.properties.get(Elements.STALE_CONNECTION_CHECK.localName)));
			tKeyManager.setText(httpProps.ssl.properties.get(Elements.KEY_MANAGER_ALGORITHM.localName));
			tTrustManager.setText(httpProps.ssl.properties.get(Elements.TRUST_MANAGER_ALGORITHM.localName));
			updateProtocolsTable();
			updateCiphersTable();
		}
	}
	
	private void updateProtocolsTable() {
		taProtocolsModel.setProcessingUnit(httpProps.procUnit);
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (String protocol: httpProps.ssl.protocols) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	rowData.add(protocol);
	    	tableData.add(rowData);
	    }
	    taProtocolsProviderUi.setTableData(tableData);
	}

	private void updateCiphersTable() {
		taCiphersModel.setProcessingUnit(httpProps.procUnit);
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (String cipher: httpProps.ssl.ciphers) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	rowData.add(cipher);
	    	tableData.add(rowData);
	    }
	    taCiphersProviderUi.setTableData(tableData);
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}