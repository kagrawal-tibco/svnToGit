package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.CacheOmSecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.SecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * Cluster Level Security Configuration Settings group 
 * @author vdhumal
 */
public class CacheOmSecurityConfigGroup extends SecurityConfigGroup {

	private Label lSecurityEnabled;
	private Button bSecurityEnabled;
	
	public CacheOmSecurityConfigGroup(Composite parent, int style, ClusterConfigModelMgr modelmgr) {
		super(parent, style, modelmgr);
	}
	
	@Override
	public void createConfig() {
		lSecurityEnabled = PanelUiUtil.createLabel(this.securityGroupComposite, "Security Enabled: ");
		bSecurityEnabled = PanelUiUtil.createCheckBox(this.securityGroupComposite, "");
		
		bSecurityEnabled.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				modelmgr.updateCacheOmSecurityEnabled(bSecurityEnabled.getSelection());
				
				CacheOmSecurityConfig cacheOmSecurityConfig = (CacheOmSecurityConfig) CacheOmSecurityConfigGroup.this.securityConfig;
				updateConfigOnSecurityEnabled(cacheOmSecurityConfig);
				
				SetControllerConfigVisibility(bSecurityEnabled.getSelection());
				setRequestorConfigVisibility(bSecurityEnabled.getSelection());				
			}
		});
		super.createConfig();
	}
	
	@Override
	protected void updateConfig(SecurityConfig securityConfig) {		
		if (securityConfig != null && securityConfig instanceof CacheOmSecurityConfig) {
			CacheOmSecurityConfig cacheOmSecurityConfig = (CacheOmSecurityConfig) securityConfig;
			bSecurityEnabled.setSelection(cacheOmSecurityConfig.securityEnabled);

			updateConfigOnSecurityEnabled(cacheOmSecurityConfig);
			
			SetControllerConfigVisibility(cacheOmSecurityConfig.securityEnabled);
			setRequestorConfigVisibility(cacheOmSecurityConfig.securityEnabled);
		}	
	}
	
	private void updateConfigOnSecurityEnabled(CacheOmSecurityConfig securityConfig) {
		super.updateConfig(securityConfig);		
	}
	
	@Override
	public void setVisible(boolean flag) {
		lSecurityEnabled.setVisible(flag);
		bSecurityEnabled.setVisible(flag);
		SetControllerConfigVisibility(flag && bSecurityEnabled.getSelection());
		setRequestorConfigVisibility(flag && bSecurityEnabled.getSelection());
	}	
}
