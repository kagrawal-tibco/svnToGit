/**
 * 
 */
package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig.EntityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * @author vpatil
 *
 */
public class NodeEntityConfigPage extends ClusterNodeDetailsPage {
	
	private EntityConfig entityConfig;
	private Text tEntityUri, tFilter, tTrimmingRule, tTrimmingField;
	private Button bEntityBrowse, bEnableTableTrimming;
	private Label lTrimmingField, lTrimmingRule;
	
	public NodeEntityConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		PanelUiUtil.createLabel(client, "Entity Uri: ");
		
		final Composite entityComp = new Composite(client, SWT.NONE);
		entityComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		entityComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		tEntityUri = PanelUiUtil.createText(entityComp);
		tEntityUri.addListener(SWT.Modify, getTextModifyListener(tEntityUri, Elements.URI.localName));
		bEntityBrowse = PanelUiUtil.createBrowsePushButton(entityComp, tEntityUri);
		bEntityBrowse.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				EntitySelectionDialog dialog = new EntitySelectionDialog(entityComp.getShell());
				ArrayList<String> curList = new ArrayList<String>();
				curList.add(tEntityUri.getText());
				dialog.open(ClusterConfigProjectUtils.getProjectConceptEventMetricName(modelmgr.project), curList);
				String selEntity = dialog.getSelectedEntity();
				if (selEntity.trim().equals(""))
					return;
				tEntityUri.setText(selEntity);
			}
		});
		
		tFilter = createPropertyTextField(client, "Entity Filter: ", Elements.FILTER.localName);
		
		Group gTrimming = new Group(client, SWT.NONE);
		gTrimming.setText("Table Trimming");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gTrimming.setLayoutData(gd);
		gTrimming.setLayout(new GridLayout(2, false));
		
		PanelUiUtil.createLabel(gTrimming, "Enable Table Trimming: ");
		bEnableTableTrimming = PanelUiUtil.createCheckBox(gTrimming, "");
		bEnableTableTrimming.setLayoutData(new GridData());
		bEnableTableTrimming.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelmgr.updateEntityConfigProperty(entityConfig, Elements.ENABLE_TRIMMING.localName, String.valueOf(bEnableTableTrimming.getSelection()));
				setTrimmingOptionVisibility(bEnableTableTrimming.getSelection());
			}
		});
		
		lTrimmingField = PanelUiUtil.createLabel(gTrimming, "Trimming TimeStamp Field (Optional): ");
		tTrimmingField = PanelUiUtil.createText(gTrimming);
		tTrimmingField.addListener(SWT.Modify, getTextModifyListener(tTrimmingField, Elements.TRIMMING_FIELD.localName));
		
		lTrimmingRule = PanelUiUtil.createLabel(gTrimming, "Trimming Rule: ");
		tTrimmingRule = PanelUiUtil.createText(gTrimming);
		tTrimmingRule.addListener(SWT.Modify, getTextModifyListener(tTrimmingRule, Elements.TRIMMING_RULE.localName));
		
		setTrimmingOptionVisibility(false);
	}
	
	private Text createPropertyTextField(Composite composite, String label, String modelId) {
		PanelUiUtil.createLabel(composite, label);
		Text tField = PanelUiUtil.createText(composite);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getTextModifyListener(final Text tField, final String modelId) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateEntityConfigProperty(entityConfig, modelId, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			entityConfig = ((EntityConfig) ssel.getFirstElement());
		else
			entityConfig = null;
		update();
	}
	
	private void update() {
		if (entityConfig != null) {
			tEntityUri.setText(entityConfig.entityUri);
			tFilter.setText(entityConfig.filter);
			
			tTrimmingRule.setText(entityConfig.trimmingRule);
			tTrimmingField.setText(entityConfig.trimmingField);
			boolean isTableTrimmingEnabled = Boolean.valueOf(entityConfig.enableTableTrimming);
			bEnableTableTrimming.setSelection(isTableTrimmingEnabled);
			setTrimmingOptionVisibility(isTableTrimmingEnabled);
		}
	}

	@Override
	public Listener getListener(Control field, String key) {
		return null;
	}

	@Override
	public String getValue(String key) {
		return null;
	}

	private void setTrimmingOptionVisibility(boolean visible) {
		lTrimmingField.setVisible(visible);
		tTrimmingField.setVisible(visible);
		lTrimmingRule.setVisible(visible);
		tTrimmingRule.setVisible(visible);
	}
}
