package com.tibco.cep.studio.cluster.topology.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.utils.Messages;

public class ClusterDiagramUnitPropertySection extends AbstractClusterTopologyPropertySection {
	
	protected Text unitIdText;
	protected Text nrAgentsText;
    protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		getWidgetFactory().createLabel(composite, Messages.getString("unit.id"),  SWT.NONE);
		unitIdText = getWidgetFactory().createText(composite,"",  SWT.BORDER);

		unitIdText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		unitIdText.setLayoutData(gd);

		
		getWidgetFactory().createLabel(composite, Messages.getString("unit.nrAgents"),  SWT.NONE);
		nrAgentsText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		nrAgentsText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nrAgentsText.setLayoutData(gd);
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseNode != null) {
//			setStringField(this.unitIdText, ClusterTopologyConstants.PU_ID);
			// TODO:
//			setStringField(this.nrAgentsText, 0);
		}
		
		if (tseEdge != null) { }
		if (tseGraph != null) { }
	}
}