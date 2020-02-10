package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList.AlertConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Apr 28, 2010 11:59:18 PM
 */

public class NodeAlertConfigPage extends ClusterNodeDetailsPage {

	private AlertConfig alertConfig;
	private Group gCondition;
	private Text tId, tPath, tName, tThreshold, tReference;
	
	private Table taProjections;
	private AlertProjectionsTableProviderModel taProjectionsModel;
	private TableProviderUi taProjectionsProviderUi;
	
	public NodeAlertConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		PanelUiUtil.createLabel(client, "Alert ID: ");
		tId = PanelUiUtil.createText(client);
		tId.addListener(SWT.Modify, getIdModifyListener());
		
		gCondition = new Group(client, SWT.NONE);
		gCondition.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gCondition.setLayoutData(gd);
		gCondition.setText("Condition");
		tPath = createConditionValueField("Path: ", "path");
		tName = createConditionValueField("Alert Value: ", "name");
		tReference = createConditionValueField("Reference Value: ", "reference");
		tThreshold = createConditionValueField("Threshold: ", "threshold");
		gCondition.pack();
		
		Group gProjection = new Group(client, SWT.NONE);
		gProjection.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gProjection.setLayoutData(gd);
		gProjection.setText("Projection Properties");
		String columns[] = new String[]{ "Name", "Value"};
		taProjectionsModel = new AlertProjectionsTableProviderModel(modelmgr);  
		taProjectionsProviderUi = new TableProviderUi(gProjection, columns, true, taProjectionsModel);
		taProjections = taProjectionsProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		gd.widthHint = 300;
		taProjections.setLayoutData(gd);
		gProjection.pack();
		
		taProjections.addMouseListener(taProjectionsProviderUi.tableTextModifyMouseListener(taProjections, 0));
	    taProjections.addMouseListener(taProjectionsProviderUi.tableTextModifyMouseListener(taProjections, 1));	    
	    taProjections.addListener(SWT.Modify, getProjectionsTableModifyListener());
		
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Text createConditionValueField(String label, String modelId) {
		PanelUiUtil.createLabel(gCondition, label);
		Text tField = PanelUiUtil.createText(gCondition);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getIdModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateAlertConfigId(alertConfig, tId.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getTextModifyListener(final Text tField, final String modelId) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateAlertConfigCondition(alertConfig, modelId, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}

	private Listener getProjectionsTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateAlertConfigProjection(alertConfig, taProjections);
			}
		};
		return listener;
	}
	
    private boolean updateAlertConfigProjection(AlertConfig config, Table table) {
        if (table == null)
            return false;
        config.projection.properties.clear();
        String keys[] = new String[] {Elements.NAME.localName, "value"};
        for (TableItem item: table.getItems()) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            for (int col=0; col<table.getColumnCount(); col++)
                map.put(keys[col], item.getText(col));
            config.projection.properties.add(map);
        }
        modelmgr.modified();
        return true;
    }
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			alertConfig = ((AlertConfig) ssel.getFirstElement());
		else
			alertConfig = null;
		update();
	}
	
	private void update() {
		if (alertConfig != null) {
			tId.setText(alertConfig.id);
			tPath.setText(alertConfig.condition.values.get("path"));
			tName.setText(alertConfig.condition.values.get("name"));
			tThreshold.setText(alertConfig.condition.values.get("threshold"));
			tReference.setText(alertConfig.condition.values.get("reference"));
			updateProjectionsTable();
		}
	}
	
	private void updateProjectionsTable() {
		taProjectionsModel.setAlertConfig(alertConfig);
	    String keys[] = new String[] {Elements.NAME.localName, "value"};
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (LinkedHashMap<String, String> map: alertConfig.projection.properties) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	for (int i=0; i<keys.length; i++) {
	    		rowData.add(map.get(keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taProjectionsProviderUi.setTableData(tableData);
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
