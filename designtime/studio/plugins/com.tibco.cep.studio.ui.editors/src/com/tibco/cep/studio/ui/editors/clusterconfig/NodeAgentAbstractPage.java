package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 11, 2009 11:27:15 AM
 */

public class NodeAgentAbstractPage extends ClusterNodeDetailsPage {

	protected AgentClass agent;
	protected Text tName;
	
	private Tree trProps;
	private TreeProviderUi trPropsProviderUi;
	private AgentPropertyTreeProviderModel treeModel;
	protected boolean displayNameField = true;
	private ArrayList<PropertyPresentation> propertyPresentationList = null;
	private ArrayList<Control> propertyFields;
	
	public NodeAgentAbstractPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
		this.modelmgr = modelmgr;
		this.viewer = viewer;
		propertyFields = new ArrayList<Control>();
 	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		if (displayNameField) {
			toolkit.createLabel(client, "Name: ");
			tName = toolkit.createText(client, "", SWT.SINGLE | SWT.BORDER);
			tName.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if (modelmgr.updateAgentName(agent, tName.getText().trim()))
						BlockUtil.refreshViewer(viewer);
					validateFields();
				}
			});
			GridData gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
			gd.widthHint = 10;
			tName.setLayoutData(gd);
		}

		toolkit.paintBordersFor(section);
		section.setClient(client);
	}

	protected void createPropertyPresentationElements(Composite parent, String agentClassType) {
		if (agentClassType == null)
			return;
		initializePropertyPresentationList(agentClassType);
		for (PropertyPresentation propPres: propertyPresentationList) {
			createElement(parent, propPres);
		}
	}

	private void initializePropertyPresentationList(String agentClassType) {
		propertyPresentationList = new ArrayList<PropertyPresentation>();
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor("com.tibco.cep.studio.ui.editors.cddPropertyPresentation");
		try {
			for (IConfigurationElement configurationElement : config) {
				String agentType = configurationElement.getAttribute("agentType");
				if (!agentType.equals(agentClassType))
					continue;
				String propertyName = configurationElement.getAttribute("propertyName");
				String uiName = configurationElement.getAttribute("uiName");
				String propertyType = configurationElement.getAttribute("propertyType");
				String defaultValue = configurationElement.getAttribute("defaultValue");
				PropertyPresentation propPres = new PropertyPresentation(agentType, propertyName, uiName, propertyType, defaultValue);
				propertyPresentationList.add(propPres);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void createElement(Composite parent, final PropertyPresentation propPres) {
		Label label = PanelUiUtil.createLabel(parent, propPres.uiName + ": ");
		label.setToolTipText("[" + propPres.propertyName + "]");
		
		if (propPres.propertyType.equalsIgnoreCase(PropertyPresentation.TYPE_STRING)) {
			final Text tField = PanelUiUtil.createText(parent);
			if (propPres.currentValue != null) 
				tField.setText(propPres.currentValue);
			else if (propPres.defaultValue != null)
				tField.setText(propPres.defaultValue);
			tField.addListener(SWT.Modify, new Listener() {
				@Override
				public void handleEvent(Event event) {
					modelmgr.updateAgentProperty(agent, propPres.propertyName, tField.getText());
				}
			});
			tField.setData(propPres);
			propertyFields.add(tField);
		} else if (propPres.propertyType.equalsIgnoreCase(PropertyPresentation.TYPE_BOOLEAN)) {
			final Button bField = PanelUiUtil.createCheckBox(parent, "");
			if (propPres.currentValue != null)
				bField.setSelection(modelmgr.getBooleanValue(propPres.currentValue));
			else if (propPres.defaultValue != null)
				bField.setSelection(modelmgr.getBooleanValue(propPres.defaultValue));
			bField.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					modelmgr.updateAgentProperty(agent, propPres.propertyName, new Boolean(bField.getSelection()).toString());
				}
			});
			bField.setData(propPres);
			propertyFields.add(bField);
		}
	}

	
	public void createPropertiesGroup() {
		createPropertiesGroup(null);
	}
	
	public void createPropertiesGroup(String agentClassType) {
		//PanelUiUtil.createSpacer(toolkit, client, 2);
		createPropertyPresentationElements(client, agentClassType);
		
		Composite propsComp = toolkit.createComposite(client);
		propsComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 250;
		gd.heightHint = 300;
		propsComp.setLayoutData(gd);
		createPropertiesSectionContents(propsComp);
		propsComp.pack();
	}
	
	protected void createPropertiesSectionContents(Composite parent) {
		Group grProps = new Group(parent, SWT.NONE);
		grProps.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 280;
		grProps.setLayoutData(gd);
		grProps.setText("Properties");
		
		/*
		PropertyTreeProvider treeProvider = new PropertyTreeProvider(grProps);
		Composite trComp = treeProvider.createTree();
		*/
		
		treeModel = new AgentPropertyTreeProviderModel(modelmgr);
		trPropsProviderUi = new TreeProviderUi(grProps, treeModel.columns, true, treeModel, treeModel.keys);
		trProps = trPropsProviderUi.createTree();
		trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 0));
	    trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 1));
        //trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 2, 0, TreeProviderUi.TYPE_COMBO, treeModel.types));	    
	    trProps.addListener(SWT.Modify, treeModel.getTreeModifyListener(trProps));
		
	    grProps.pack();
		parent.pack();
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			agent = ((AgentClass) ssel.getFirstElement());
		else
			agent = null;
		update();
	}
	
	private void update() {
		if (agent != null) {
			tName.setText(agent.name);
			treeModel.setAgent(agent);
			filterPropertyPresentationList(agent.properties);
			trPropsProviderUi.setTreeData(agent.properties);
		}
	}
	
	private void filterPropertyPresentationList(PropertyElementList propList) {
		if (propertyPresentationList == null)
			return;
		Iterator<PropertyElement> propItr = propList.propertyList.iterator(); 
		while (propItr.hasNext()) {
			PropertyElement propElement = (PropertyElement) propItr.next();
			if (propElement instanceof Property) {
				Property prop = (Property) propElement;
				String name = prop.fieldMap.get("name");
				for (PropertyPresentation propPres: propertyPresentationList) {
					if (propPres.propertyName.equalsIgnoreCase(name)) {
						propPres.currentValue = prop.fieldMap.get("value");
						Property newProp = modelmgr.getModel().new Property();
						newProp.fieldMap.putAll(prop.fieldMap);
						propList.uiList.add(newProp);
						propItr.remove();
					}
				}
			}
		}
		Iterator<PropertyElement> propUiItr = propList.uiList.iterator(); 
		while (propUiItr.hasNext()) {
			PropertyElement propElement = (PropertyElement) propUiItr.next();
			if (propElement instanceof Property) {
				Property prop = (Property) propElement;
				String name = prop.fieldMap.get("name");
				for (PropertyPresentation propPres: propertyPresentationList) {
					if (propPres.propertyName.equalsIgnoreCase(name)) {
						propPres.currentValue = prop.fieldMap.get("value");
						updatePropertyField(propPres);
					}
				}
			}
		}
		
	}

	private void updatePropertyField(PropertyPresentation propPres) {
		for (Control field: propertyFields) {
			if (field.getData() instanceof PropertyPresentation) {
				PropertyPresentation fieldPres = (PropertyPresentation) field.getData();
				if (fieldPres.propertyName.equalsIgnoreCase(propPres.propertyName)) {
					if (field instanceof Text) {
						((Text)field).setText(propPres.currentValue);
					} else if (field instanceof Button) {
						((Button)field).setSelection(modelmgr.getBooleanValue(propPres.currentValue));
					}
				}
			}
		}
	}
	
	protected boolean validateFields() {
		boolean valid = true;
		valid &= PanelUiUtil.validateTextField(tName, true, false);
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
