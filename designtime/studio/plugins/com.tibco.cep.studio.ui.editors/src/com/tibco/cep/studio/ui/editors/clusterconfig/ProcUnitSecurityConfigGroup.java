package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ObjectManagement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit.ProcessingUnitSecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.SecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * PU Level Security Configuration Settings group implementation
 * @author vdhumal
 */
public class ProcUnitSecurityConfigGroup extends SecurityConfigGroup {
	
	private Label lSecurityRole, lCntrlOverrideHdr, lReqOverrideHdr;
	private GvField cSecurityRole;
	
	private Button bPolicyFileOverride, bCntrlIdentityPasswordOverride, bTokenFileOverride, bReqlIdentityPasswordOverride,
	bReqCertKeyFileOverride, bReqDomainNameOverride, bReqUserOverride, bReqPasswordOverride; 

	public ProcUnitSecurityConfigGroup(Composite parent, int style, ClusterConfigModelMgr modelmgr) {
		super(parent, style, modelmgr);
	}
	
	@Override
	protected void createConfig() {
		lSecurityRole = PanelUiUtil.createLabel(this.securityGroupComposite, "Security Role: ");		
		cSecurityRole = createGvComboField(this.securityGroupComposite, getSecurityRoles(), ProcessingUnitSecurityConfig.ROLE_REQUESTER);
		setSecurityRoleGvFieldListeners(cSecurityRole, SWT.Selection);
		super.createConfig();
	}
	
	@Override
	protected void createControllerConfig() {
		controllerConfigGroup = new Group(securityGroupComposite, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gd.verticalIndent = 5;
		GridLayout gridLayout = new GridLayout(5, false);
		controllerConfigGroup.setLayout(gridLayout);
		controllerConfigGroup.setLayoutData(gd);
		controllerConfigGroup.setText("Security-Controller");
		
		lCntrlOverrideHdr = PanelUiUtil.createLabel(this.controllerConfigGroup, "Override");
		lCntrlOverrideHdr.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 5, 1));

		//Policy File
		PanelUiUtil.createLabel(this.controllerConfigGroup, "    ");
		bPolicyFileOverride = PanelUiUtil.createCheckBox(this.controllerConfigGroup, "");
		bPolicyFileOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.controllerConfigGroup, "    ");
		lPolicyFilePath = PanelUiUtil.createLabel(this.controllerConfigGroup, "Policy File: ");
//		tPolicyFilePath = PanelUiUtil.createText(this.securityConfigGroup);
//		tPolicyFilePath.addListener(SWT.Modify, getControllerValuesModifyListener(tPolicyFilePath, Elements.SECURITY_POLICY_FILE.localName));
		tPolicyFilePath = GvUiUtil.createTextGv(this.controllerConfigGroup, getControllerFieldValue(securityConfig, Elements.SECURITY_POLICY_FILE.localName));
		setControllerGvFieldListeners(tPolicyFilePath, SWT.Modify, Elements.SECURITY_POLICY_FILE.localName);
		bPolicyFileOverride.addSelectionListener(getControllerOverridesModifyListener(bPolicyFileOverride, tPolicyFilePath, Elements.SECURITY_POLICY_FILE.localName));
		
		//Identity password
		PanelUiUtil.createLabel(this.controllerConfigGroup, "    ");
		bCntrlIdentityPasswordOverride = PanelUiUtil.createCheckBox(this.controllerConfigGroup, "");
		bCntrlIdentityPasswordOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.controllerConfigGroup, "    ");
		lCntrlIdentityPassword = PanelUiUtil.createLabel(this.controllerConfigGroup, "Policy File Identity Password: ");
//		tCntrlIdentityPassword = PanelUiUtil.createText(this.securityConfigGroup);
//		tCntrlIdentityPassword.addListener(SWT.Modify, getControllerValuesModifyListener(tCntrlIdentityPassword, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		tCntrlIdentityPassword = GvUiUtil.createPasswordGv(this.controllerConfigGroup, getControllerFieldValue(securityConfig, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		setControllerGvFieldListeners(tCntrlIdentityPassword, SWT.Modify, Elements.SECURITY_IDENTITY_PASSWORD.localName);		
		bCntrlIdentityPasswordOverride.addSelectionListener(getControllerOverridesModifyListener(bCntrlIdentityPasswordOverride, tCntrlIdentityPassword, Elements.SECURITY_IDENTITY_PASSWORD.localName));
	}
	
	@Override
	protected void createRequesterConfig() {        
		requesterConfigGroup = new Group(securityGroupComposite, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gd.verticalIndent = 5;
		GridLayout gridLayout = new GridLayout(5, false);
		requesterConfigGroup.setLayout(gridLayout);
		requesterConfigGroup.setLayoutData(gd);
		requesterConfigGroup.setText("Security-Requestor");

		lReqOverrideHdr = PanelUiUtil.createLabel(this.requesterConfigGroup, "Override");
		lReqOverrideHdr.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 5, 1));

		//Token file
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		bTokenFileOverride = PanelUiUtil.createCheckBox(this.requesterConfigGroup, "");
		bTokenFileOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		lTokenFilePath = PanelUiUtil.createLabel(this.requesterConfigGroup, "Token File: ");
//		tTokenFilePath = PanelUiUtil.createText(this.securityConfigGroup);
//		tTokenFilePath.addListener(SWT.Modify, getRequestorValuesModifyListener(tTokenFilePath, Elements.SECURITY_TOKEN_FILE.localName));
		tTokenFilePath = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_TOKEN_FILE.localName));
		setRequestorGvFieldListeners(tTokenFilePath, SWT.Modify, Elements.SECURITY_TOKEN_FILE.localName);
		bTokenFileOverride.addSelectionListener(getRequestorOverridesModifyListener(bTokenFileOverride, tTokenFilePath, Elements.SECURITY_TOKEN_FILE.localName));
		
		//Identity password
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		bReqlIdentityPasswordOverride = PanelUiUtil.createCheckBox(this.requesterConfigGroup, "");
		bReqlIdentityPasswordOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		lReqlIdentityPassword = PanelUiUtil.createLabel(this.requesterConfigGroup, "Token File Identity Password: ");
//		tReqlIdentityPassword = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqlIdentityPassword.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqlIdentityPassword, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		tReqlIdentityPassword = GvUiUtil.createPasswordGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		setRequestorGvFieldListeners(tReqlIdentityPassword, SWT.Modify, Elements.SECURITY_IDENTITY_PASSWORD.localName);		
		bReqlIdentityPasswordOverride.addSelectionListener(getRequestorOverridesModifyListener(bReqlIdentityPasswordOverride, tReqlIdentityPassword, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		
		//Certificate-key file
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		bReqCertKeyFileOverride = PanelUiUtil.createCheckBox(this.requesterConfigGroup, "");
		bReqCertKeyFileOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		lReqCertKeyFilePath = PanelUiUtil.createLabel(this.requesterConfigGroup, "LDAP Identity File: ");
//		tReqCertKeyFilePath = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqCertKeyFilePath.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqCertKeyFilePath, Elements.SECURITY_CERT_KEY_FILE.localName));
		tReqCertKeyFilePath = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_CERT_KEY_FILE.localName));
		setRequestorGvFieldListeners(tReqCertKeyFilePath, SWT.Modify, Elements.SECURITY_CERT_KEY_FILE.localName);		
		bReqCertKeyFileOverride.addSelectionListener(getRequestorOverridesModifyListener(bReqCertKeyFileOverride, tReqCertKeyFilePath, Elements.SECURITY_CERT_KEY_FILE.localName));
		
        //Domain name
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		bReqDomainNameOverride = PanelUiUtil.createCheckBox(this.requesterConfigGroup, "");
		bReqDomainNameOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		lReqDomainName = PanelUiUtil.createLabel(this.requesterConfigGroup, "Domain: ");
//		tReqDomainName = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqDomainName.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqDomainName, Elements.SECURITY_DOMAIN_NAME.localName));
		tReqDomainName = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_DOMAIN_NAME.localName));
		setRequestorGvFieldListeners(tReqDomainName, SWT.Modify, Elements.SECURITY_DOMAIN_NAME.localName);		
		bReqDomainNameOverride.addSelectionListener(getRequestorOverridesModifyListener(bReqDomainNameOverride, tReqDomainName, Elements.SECURITY_DOMAIN_NAME.localName));
		
		//User name
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		bReqUserOverride = PanelUiUtil.createCheckBox(this.requesterConfigGroup, "");
		bReqUserOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		lReqUser = PanelUiUtil.createLabel(this.requesterConfigGroup, "Username: ");
//		tReqUser = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqUser.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqUser, Elements.SECURITY_USER_NAME.localName));
		tReqUser = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_USER_NAME.localName));
		setRequestorGvFieldListeners(tReqUser, SWT.Modify, Elements.SECURITY_USER_NAME.localName);		
		bReqUserOverride.addSelectionListener(getRequestorOverridesModifyListener(bReqUserOverride, tReqUser, Elements.SECURITY_USER_NAME.localName));
		
		//User password
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		bReqPasswordOverride = PanelUiUtil.createCheckBox(this.requesterConfigGroup, "");
		bReqPasswordOverride.setToolTipText("Check to Override");
		PanelUiUtil.createLabel(this.requesterConfigGroup, "    ");
		lReqPassword = PanelUiUtil.createLabel(this.requesterConfigGroup, "Password: ");
//		tReqPassword = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqPassword.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqPassword, Elements.SECURITY_USER_PASSWORD.localName));		
		tReqPassword = GvUiUtil.createPasswordGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_USER_PASSWORD.localName));
		setRequestorGvFieldListeners(tReqPassword, SWT.Modify, Elements.SECURITY_USER_PASSWORD.localName);		
		bReqPasswordOverride.addSelectionListener(getRequestorOverridesModifyListener(bReqPasswordOverride, tReqPassword, Elements.SECURITY_USER_PASSWORD.localName));
	}
	
	@Override
	protected void updateConfig(SecurityConfig securityConfig) {    
		if (securityConfig != null && securityConfig instanceof ProcessingUnitSecurityConfig) {
			
			super.updateConfig(securityConfig);
			
			ProcessingUnitSecurityConfig procUnitSecurityConfig = (ProcessingUnitSecurityConfig)securityConfig;
			cSecurityRole.setValue(procUnitSecurityConfig.securityRole);
			
			Boolean policyFileOverride = procUnitSecurityConfig.securityControllerOverrides.get(Elements.SECURITY_POLICY_FILE.localName);
			bPolicyFileOverride.setSelection(Boolean.TRUE.equals(policyFileOverride));
			
			Boolean cntrlIdentityPasswordOverride = procUnitSecurityConfig.securityControllerOverrides.get(Elements.SECURITY_IDENTITY_PASSWORD.localName);
			bCntrlIdentityPasswordOverride.setSelection(Boolean.TRUE.equals(cntrlIdentityPasswordOverride));
			
			Boolean tokenFileOverride = procUnitSecurityConfig.securityRequesterOverrides.get(Elements.SECURITY_TOKEN_FILE.localName);
			bTokenFileOverride.setSelection(Boolean.TRUE.equals(tokenFileOverride));
			
			Boolean reqIdentityPasswordOverride = procUnitSecurityConfig.securityRequesterOverrides.get(Elements.SECURITY_IDENTITY_PASSWORD.localName);
			bReqlIdentityPasswordOverride.setSelection(Boolean.TRUE.equals(reqIdentityPasswordOverride));
			
			Boolean reqCertKeyFileOverride = procUnitSecurityConfig.securityRequesterOverrides.get(Elements.SECURITY_CERT_KEY_FILE.localName);
			bReqCertKeyFileOverride.setSelection(Boolean.TRUE.equals(reqCertKeyFileOverride));
			
			Boolean reqDomainNameOverride = procUnitSecurityConfig.securityRequesterOverrides.get(Elements.SECURITY_DOMAIN_NAME.localName);
			bReqDomainNameOverride.setSelection(Boolean.TRUE.equals(reqDomainNameOverride));
			
			Boolean reqUserNameOverride = procUnitSecurityConfig.securityRequesterOverrides.get(Elements.SECURITY_USER_NAME.localName); 
			bReqUserOverride.setSelection(Boolean.TRUE.equals(reqUserNameOverride));
			
			Boolean reqPassowrdOverride = procUnitSecurityConfig.securityRequesterOverrides.get(Elements.SECURITY_USER_PASSWORD.localName);
			bReqPasswordOverride.setSelection(Boolean.TRUE.equals(reqPassowrdOverride));
			
			if (cSecurityRole.isGvMode()) {
				ProcUnitSecurityConfigGroup.this.enableControllerConfig(true);
				ProcUnitSecurityConfigGroup.this.enableRequestorConfig(true);
			} else if (ProcessingUnitSecurityConfig.ROLE_CONTROLLER.equalsIgnoreCase(procUnitSecurityConfig.securityRole)) {
				enableControllerConfig(true);
				enableRequestorConfig(false);
			}	
			else {
				enableControllerConfig(false);
				enableRequestorConfig(true);
			}
		}
		
	    boolean isVisible = ObjectManagement.CACHE_MGR.equals(modelmgr.getModel().om.activeOm)
	    							&& ClusterConfigModel.CacheOm.PROVIDER_TIBCO.equalsIgnoreCase(modelmgr.getModel().om.cacheOm.provider)
	    							&& modelmgr.getModel().om.cacheOm.securityConfig.securityEnabled;
	    setVisible(isVisible);
	}	

	/**
	 * To enable/disable Controller config
	 * @param enable
	 */
	protected void enableControllerConfig(boolean enable) {
		lCntrlOverrideHdr.setEnabled(enable);
		bPolicyFileOverride.setEnabled(enable);
		lPolicyFilePath.setEnabled(enable);
		tPolicyFilePath.setEnabled(enable && bPolicyFileOverride.getSelection());
		bCntrlIdentityPasswordOverride.setEnabled(enable);
		lCntrlIdentityPassword.setEnabled(enable);
		tCntrlIdentityPassword.setEnabled(enable && bCntrlIdentityPasswordOverride.getSelection());
	}
	
	/**
	 * To enable/disable requestor config
	 * @param enable
	 */
	protected void enableRequestorConfig(boolean enable) {
		lReqOverrideHdr.setEnabled(enable);
		bTokenFileOverride.setEnabled(enable); 
		lTokenFilePath.setEnabled(enable);
		tTokenFilePath.setEnabled(enable && bTokenFileOverride.getSelection());
		bReqlIdentityPasswordOverride.setEnabled(enable);
		lReqlIdentityPassword.setEnabled(enable);
		tReqlIdentityPassword.setEnabled(enable && bReqlIdentityPasswordOverride.getSelection());
		bReqCertKeyFileOverride.setEnabled(enable);
		lReqCertKeyFilePath.setEnabled(enable);
		tReqCertKeyFilePath.setEnabled(enable && bReqCertKeyFileOverride.getSelection());
		bReqDomainNameOverride.setEnabled(enable);
		lReqDomainName.setEnabled(enable);
		tReqDomainName.setEnabled(enable && bReqDomainNameOverride.getSelection());
		bReqUserOverride.setEnabled(enable);
		lReqUser.setEnabled(enable);
		tReqUser.setEnabled(enable && bReqUserOverride.getSelection());
		bReqPasswordOverride.setEnabled(enable);
		lReqPassword.setEnabled(enable);
		tReqPassword.setEnabled(enable && bReqPasswordOverride.getSelection());
	}
	
	/**
	 * @param field
	 * @param key
	 * @return
	 */
	protected SelectionListener getControllerOverridesModifyListener(final Button overrideBtn, final GvField field, final String key) {
		SelectionListener listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				modelmgr.updateSecurityControllerOverrides((ProcessingUnitSecurityConfig)ProcUnitSecurityConfigGroup.this.securityConfig, key, overrideBtn.getSelection());
				field.setEnabled(overrideBtn.getSelection());
			}
		};	
		return listener;
	}
		
	/**
	 * @param field
	 * @param key
	 * @return
	 */
	protected SelectionListener getRequestorOverridesModifyListener(final Button overrideBtn, final GvField field, final String key) {
		SelectionListener listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				modelmgr.updateSecurityRequestorOverrides((ProcessingUnitSecurityConfig)ProcUnitSecurityConfigGroup.this.securityConfig, key, overrideBtn.getSelection());
				field.setEnabled(overrideBtn.getSelection());
			}
		};	
		return listener;
	}
	
	private void setSecurityRoleGvFieldListeners(GvField gvField, int eventType) {
		gvField.setFieldListener(eventType, getSecurityRoleGVFieldListener(gvField.getField()));
		gvField.setGvListener(getSecurityRoleGVFieldListener(gvField.getGvText()));
	}

	private Listener getSecurityRoleGVFieldListener(final Control field) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				ProcessingUnitSecurityConfig procUnitSecurityConfig = (ProcessingUnitSecurityConfig)ProcUnitSecurityConfigGroup.this.securityConfig;
				modelmgr.updateProcUnitSecurityRole(procUnitSecurityConfig, cSecurityRole.getValue());
				if (cSecurityRole.isGvMode()) {
					ProcUnitSecurityConfigGroup.this.enableControllerConfig(true);
					ProcUnitSecurityConfigGroup.this.enableRequestorConfig(true);
				} else if (ProcessingUnitSecurityConfig.ROLE_CONTROLLER.equalsIgnoreCase(cSecurityRole.getValue())) {
					ProcUnitSecurityConfigGroup.this.enableControllerConfig(true);
					ProcUnitSecurityConfigGroup.this.enableRequestorConfig(false);
				}	
				else {
					ProcUnitSecurityConfigGroup.this.enableControllerConfig(false);
					ProcUnitSecurityConfigGroup.this.enableRequestorConfig(true);
				}
			}
		};	
		return listener;
	}
	
	private String[] getSecurityRoles() {
		return new String[] {ProcessingUnitSecurityConfig.ROLE_REQUESTER, ProcessingUnitSecurityConfig.ROLE_CONTROLLER};
	}
}
