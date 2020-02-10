package com.tibco.cep.studio.cluster.topology.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterDiagramSitePropertySection extends AbstractClusterTopologyPropertySection {
	
	protected Text nameText;
	protected Text descriptionText;
	protected Text nrClustersText;
	protected Text nrMachinesText;
	protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		
		getWidgetFactory().createLabel(composite, Messages.getString("site.name"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		nameText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				String siteName = nameText.getText();
				if(siteName != null && siteName.trim().length() > 0
					&& !siteName.equalsIgnoreCase(site.getName())){
					site.setName(siteName);
					TSENodeLabel hostnameLabel = getSelectedObjectLabel();
					if (hostnameLabel != null) {
						hostnameLabel.setName(siteName);
						getManager().refreshLabel(hostnameLabel);
					}
				}
			}});
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, Messages.getString("site.description"),  SWT.NONE);
		descriptionText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		descriptionText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(!descriptionText.getText().equalsIgnoreCase(site.getDescription())){
					site.setDescription(descriptionText.getText());
				}
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		gd.heightHint = 40;
		descriptionText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, Messages.getString("site.nrClusters"),  SWT.NONE);
		nrClustersText = getWidgetFactory().createText(composite,"",  SWT.SINGLE | SWT.BORDER);
		nrClustersText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(!nrClustersText.getText().equalsIgnoreCase(String.valueOf(site.getClusters().getCluster().size()))){
					nrClustersText.setText(String.valueOf(site.getClusters().getCluster().size()));
				}
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nrClustersText.setLayoutData(gd);
		nrClustersText.setEnabled(false);

		getWidgetFactory().createLabel(composite, Messages.getString("site.nrHosts"),  SWT.NONE);
		nrMachinesText = getWidgetFactory().createText(composite,"",  SWT.SINGLE | SWT.BORDER);
		nrMachinesText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(!nrMachinesText.getText().equalsIgnoreCase(String.valueOf(site.getHostResources().getHostResource().size()))){
					nrMachinesText.setText(String.valueOf(site.getHostResources().getHostResource().size()));
				}
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nrMachinesText.setLayoutData(gd);
		nrMachinesText.setEnabled(false);
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseNode != null) {
			if(site != null){
				nameText.setText(site.getName());
				if(site.getDescription() != null){
					descriptionText.setText(site.getDescription());
				}
				nrClustersText.setText(String.valueOf(site.getClusters().getCluster().size()));
				nrMachinesText.setText(String.valueOf(site.getHostResources().getHostResource().size()));
			}
		}
		if (tseEdge != null) { }
		if (tseGraph != null) { }
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}
}