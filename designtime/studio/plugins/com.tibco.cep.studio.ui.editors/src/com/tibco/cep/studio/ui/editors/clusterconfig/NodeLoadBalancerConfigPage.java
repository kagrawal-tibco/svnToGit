package com.tibco.cep.studio.ui.editors.clusterconfig;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public abstract class NodeLoadBalancerConfigPage extends ClusterNodeDetailsPage {

	protected Text tName;
	protected LoadBalancerConfig config; 
	private LoadBalancerPropertyTreeProviderModel treeModel;
	private TreeProviderUi trPropsProviderUi;
	private Tree trProps;
	
	public NodeLoadBalancerConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		Composite comp = new Composite(client, SWT.None);
		comp.setLayout(new GridLayout(3, false));
		comp.setLayoutData(PanelUiUtil.getGridData(2));
		
		tName = createTextField(comp, "Name: ", LoadBalancerPairConfig.ELEMENT_NAME);
		PanelUiUtil.createLabelFiller(comp);
		
		createFields(comp);
		comp.pack();
		
		//Properties
		Composite propsComp = toolkit.createComposite(client);
		propsComp.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 250;
		gd.heightHint = 300;
		propsComp.setLayoutData(gd);
		
		Group gProps = new Group(propsComp, SWT.NONE);
		gProps.setLayout(new GridLayout(1, false));
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 250;
		gProps.setLayoutData(gd);
		gProps.setText("Properties");
		
		treeModel = new LoadBalancerPropertyTreeProviderModel(modelmgr);
		trPropsProviderUi = new TreeProviderUi(gProps, treeModel.columns, true, treeModel, treeModel.keys);
		trProps = trPropsProviderUi.createTree();     
		trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 0));
	    trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 1));
        //trProps.addMouseListener(trPropsProviderUi.treeTextModifyMouseListener(trProps, 2, 0, TreeProviderUi.TYPE_COMBO, treeModel.types));	    
	    trProps.addListener(SWT.Modify, treeModel.getTreeModifyListener(trProps));

	    gProps.pack();
		propsComp.pack();
		
		toolkit.paintBordersFor(section);
		section.setClient(client);
		
	}

	public abstract void createFields(Composite comp);
	

	protected Text createTextField(Composite parent, String label, String key) {
		PanelUiUtil.createLabel(parent, label);
		Text tField = PanelUiUtil.createText(parent);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, key));
		return tField;
	}
	
	private Listener getTextModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateLoadBalancerConfigValue(config, key, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() == 1)
			config = ((LoadBalancerConfig) ssel.getFirstElement());
		else
			config = null;
		update();
	}
	
	protected void update() {
		
		if (config != null) {
			tName.setText(config.values.get(LoadBalancerConfig.ELEMENT_NAME));
			
			treeModel.setLoadBalancerConfig(config);
			trPropsProviderUi.setTreeData(modelmgr.getLoadBalancerProperties(config));
		}
	}
}