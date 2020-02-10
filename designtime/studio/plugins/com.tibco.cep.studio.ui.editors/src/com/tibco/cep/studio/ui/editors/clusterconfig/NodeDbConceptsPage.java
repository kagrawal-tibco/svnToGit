package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DbConcepts;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Apr 27, 2010 2:27:08 PM
 */

public class NodeDbConceptsPage extends ClusterNodeDetailsPage {
	
	private Text tCheckInterval, tInactivityTimeout, tInitialSize, tMaxSize, tMinSize, tPropertyCheckInterval, tRetryCount, tWaitTimeout;
	private DbConcepts dbConcepts;
	
	private Table taDbUris;
	private DbUrisTableProviderModel taDbUrisModel;
	private TableProviderUi taDbUrisProviderUi;
	
	public NodeDbConceptsPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		tCheckInterval = createTextField("Check Interval: ", Elements.CHECK_INTERVAL.localName);
		tInactivityTimeout = createTextField("Inactivity Timeout: ", Elements.INACTIVITY_TIMEOUT.localName);
		tInitialSize = createTextField("Initial Size: ", Elements.INITIAL_SIZE.localName);
		tMaxSize = createTextField("Max Size: ", Elements.MAX_SIZE.localName);
		tMinSize = createTextField("Min Size: ", Elements.MIN_SIZE.localName);
		tPropertyCheckInterval = createTextField("Property Check Interval: ", Elements.PROPERTY_CHECK_INTERVAL.localName);
		tRetryCount = createTextField("Retry Count: ", Elements.RETRY_COUNT.localName);
		tWaitTimeout = createTextField("Wait Timeout: ", Elements.WAIT_TIMEOUT.localName);

		Group gProtocols = new Group(client, SWT.NONE);
		gProtocols.setText("Database URIs");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gProtocols.setLayoutData(gd);
		gProtocols.setLayout(new GridLayout(1, false));
		taDbUrisModel = new DbUrisTableProviderModel(modelmgr);  
		taDbUrisProviderUi = new TableProviderUi(gProtocols, new String[]{"URI"}, true, taDbUrisModel);
		taDbUris = taDbUrisProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 200;
		taDbUris.setLayoutData(gd);
		taDbUris.addMouseListener(taDbUrisProviderUi.tableTextModifyMouseListener(taDbUris, 0));	    
		taDbUris.addListener(SWT.Modify, getDbUrisTableModifyListener());
		
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Text createTextField(String label, String modelId) {
		PanelUiUtil.createLabel(client, label);
		Text tField = PanelUiUtil.createText(client);
		tField.addListener(SWT.Modify, getDbConceptsModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getDbConceptsModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateDbConceptsMap(key, tField.getText());
			}
		};
		return listener;
	}
	
	private Listener getDbUrisTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateDbConceptsUris(taDbUris);
			}
		};
		return listener;
	}
	
    private void updateDbConceptsUris(Table taUris) {
        modelmgr.getModel().om.dbConcepts.dburis.clear();
        for (TableItem item: taUris.getItems()) {
            modelmgr.getModel().om.dbConcepts.dburis.add(item.getText(0));
        }
        modelmgr.modified();
    }

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			dbConcepts = ((DbConcepts) ssel.getFirstElement());
		else
			dbConcepts = null;
		update();
	}
	
	private void update() {
		if (dbConcepts != null) {
			tCheckInterval.setText(dbConcepts.values.get(Elements.CHECK_INTERVAL.localName));
			tInactivityTimeout.setText(dbConcepts.values.get(Elements.INACTIVITY_TIMEOUT.localName));
			tInitialSize.setText(dbConcepts.values.get(Elements.INITIAL_SIZE.localName));
			tMaxSize.setText(dbConcepts.values.get(Elements.MAX_SIZE.localName));
			tMinSize.setText(dbConcepts.values.get(Elements.MIN_SIZE.localName));
			tPropertyCheckInterval.setText(dbConcepts.values.get(Elements.PROPERTY_CHECK_INTERVAL.localName));
			tRetryCount.setText(dbConcepts.values.get(Elements.RETRY_COUNT.localName));
			tWaitTimeout.setText(dbConcepts.values.get(Elements.WAIT_TIMEOUT.localName));
			updateDbUrisTable();
			validateFields();
		}
	}
	
	private void updateDbUrisTable() {
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (String uri: dbConcepts.dburis) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	rowData.add(uri);
	    	tableData.add(rowData);
	    }
	    taDbUrisProviderUi.setTableData(tableData);
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