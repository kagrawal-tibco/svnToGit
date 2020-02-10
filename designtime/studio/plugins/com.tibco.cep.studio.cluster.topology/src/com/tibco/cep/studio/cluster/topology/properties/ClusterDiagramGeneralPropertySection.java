package com.tibco.cep.studio.cluster.topology.properties;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.utils.Messages;

public class ClusterDiagramGeneralPropertySection extends AbstractClusterTopologyPropertySection {
	
	protected Text clusterNameText;
	protected Text nrHostsText;
	protected Text nrPUsText;
	protected Text descriptionText;
    protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);

		getWidgetFactory().createLabel(composite, Messages.getString("general.nrDUs"),  SWT.NONE);
		nrHostsText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 50;
		nrHostsText.setLayoutData(gd);
		nrHostsText.setEnabled(false);

		getWidgetFactory().createLabel(composite, Messages.getString("general.nrPUs"),  SWT.NONE);
		nrPUsText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 50;
		nrPUsText.setLayoutData(gd);
		nrPUsText.setEnabled(false);
	}
	
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseGraph != null) {
			if (getCluster() != null && getCluster().getDeploymentUnits() != null) {
				nrHostsText.setText(Integer.toString(getCluster().getDeploymentUnits().getDeploymentUnit().size()));
				int puCount = 0;
				for(DeploymentUnit du :  getCluster().getDeploymentUnits().getDeploymentUnit()){
					if (du.getProcessingUnitsConfig() != null)
						puCount = puCount + du.getProcessingUnitsConfig().getProcessingUnitConfig().size();
				}
				nrPUsText.setText(Integer.toString(puCount));
			}
		}
	}

}