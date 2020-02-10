package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.ChildClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.Notification;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Apr 29, 2010 2:02:59 PM
 */

public class NodeSetPropertyPage extends ClusterNodeDetailsPage {

	private static String VALUE_NORMAL = "normal";
	private static String VALUE_WARNING = "warning";
	private static String VALUE_CRITICAL = "critical";
	
	private static String CHILD_CLUSTER_MEMBER = "Child Cluster Member";
	private static String NOTIFICATION = "Alert";
	
	private SetProperty setProperty;
	private Text tId, tTolerance, tPathRange;
	//private Text tName;
	private Label lPathRange;
	private Combo cValue, cChildType;
	
	private Table taProperties;
	private SetPropertyPropertiesTableProviderModel taPropertiesModel;
	private TableProviderUi taPropertiesProviderUi;
	
	public NodeSetPropertyPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		tId = createPropertyTextField("Health Metric Rule ID: ", "id");
		/*
		tName = createPropertyTextField("Name: ", Elements.NAME.localName);
		tName.setEditable(false);
		*/
		
		//tValue = createPropertyTextField("Value: ", "value");
		
		PanelUiUtil.createLabel(client, "Health Level: ");
		cValue = PanelUiUtil.createComboBox(client, new String[]{VALUE_NORMAL, VALUE_WARNING, VALUE_CRITICAL});
		cValue.addListener(SWT.Selection, getValueChangeListener());
		
		PanelUiUtil.createLabel(client, "Condition Type: ");
		cChildType = PanelUiUtil.createComboBox(client, new String[]{CHILD_CLUSTER_MEMBER, NOTIFICATION});
		cChildType.addListener(SWT.Selection, getChildTypeChangeListener());
		
		lPathRange = PanelUiUtil.createLabel(client, "Path:   ");
		tPathRange = PanelUiUtil.createText(client);
		tPathRange.addListener(SWT.Modify, getTextModifyListener(tPathRange, "pathrange"));
		
		tTolerance = createPropertyTextField("Threshold: ", "tolerance");
		
		Group gProjection = new Group(client, SWT.NONE);
		gProjection.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gProjection.setLayoutData(gd);
		gProjection.setText("Properties");
		String columns[] = new String[]{ "Name", "Value"};
		taPropertiesModel = new SetPropertyPropertiesTableProviderModel(modelmgr);  
		taPropertiesProviderUi = new TableProviderUi(gProjection, columns, true, taPropertiesModel);
		taProperties = taPropertiesProviderUi.createTable(false, false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		gd.widthHint = 300;
		taProperties.setLayoutData(gd);
		
		taProperties.addMouseListener(taPropertiesProviderUi.tableTextModifyMouseListener(taProperties, 0));
	    taProperties.addMouseListener(taPropertiesProviderUi.tableTextModifyMouseListener(taProperties, 1));	    
	    taProperties.addListener(SWT.Modify, getPropertiesTableModifyListener());
		
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Text createPropertyTextField(String label, String modelId) {
		PanelUiUtil.createLabel(client, label);
		Text tField = PanelUiUtil.createText(client);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getTextModifyListener(final Text tField, final String modelId) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateSetProperty(setProperty, modelId, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getValueChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateSetProperty(setProperty, "value", cValue.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	

	private Listener getChildTypeChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateSetPropertyChildType(setProperty, cChildType.getText().equals(CHILD_CLUSTER_MEMBER));
				if (cChildType.getText().equals(CHILD_CLUSTER_MEMBER)) {
					lPathRange.setText("Path:   ");
					tPathRange.setText(((ChildClusterMember)setProperty.setPropertyChild).path);
				} else { 
					lPathRange.setText("Range: ");
					tPathRange.setText(((Notification)setProperty.setPropertyChild).range);
				}
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
				updateSetPropertyProperties(setProperty, taProperties);
			}
		};
		return listener;
	}
	
    private boolean updateSetPropertyProperties(SetProperty property, Table table) {
        if (table == null)
            return false;
        property.setPropertyChild.properties.clear();
        String keys[] = new String[] {Elements.NAME.localName, "value"};
        for (TableItem item: table.getItems()) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            for (int col=0; col<table.getColumnCount(); col++)
                map.put(keys[col], item.getText(col));
            property.setPropertyChild.properties.add(map);
        }
        modelmgr.modified();
        return true;
    }
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			setProperty = ((SetProperty) ssel.getFirstElement());
		else
			setProperty = null;
		update();
	}
	
	private void update() {
		if (setProperty != null) {
			tId.setText(setProperty.id);
			//tName.setText(setProperty.name);
			cValue.setText(setProperty.value);
			if (setProperty.setPropertyChild instanceof ChildClusterMember) {
				cChildType.setText(CHILD_CLUSTER_MEMBER);
			} else {
				cChildType.setText(NOTIFICATION);
			}
			cChildType.notifyListeners(SWT.Selection, new Event());
			tTolerance.setText(setProperty.setPropertyChild.tolerance);
			updateProjectionsTable();
		}
	}
	
	private void updateProjectionsTable() {
		taPropertiesModel.setSetProperty(setProperty);
	    String keys[] = new String[] {Elements.NAME.localName, "value"};
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (LinkedHashMap<String, String> map: setProperty.setPropertyChild.properties) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	for (int i=0; i<keys.length; i++) {
	    		rowData.add(map.get(keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taPropertiesProviderUi.setTableData(tableData);
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
