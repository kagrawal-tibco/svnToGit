package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.SecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * Security Configuration Settings group base implementation 
 * @author vdhumal
 */
public abstract class SecurityConfigGroup {

	protected Composite securityGroupComposite;
	protected Group controllerConfigGroup, requesterConfigGroup;	
	protected Label lPolicyFilePath, lCntrlIdentityPassword, lTokenFilePath, lReqlIdentityPassword, lReqCertKeyFilePath, 
	lReqDomainName, lReqUser, lReqPassword;

	protected GvField tPolicyFilePath, tCntrlIdentityPassword;
	protected GvField tTokenFilePath, tReqlIdentityPassword, tReqDomainName, tReqUser, tReqPassword, tReqCertKeyFilePath;

	protected ClusterConfigModelMgr modelmgr;
	protected SecurityConfig securityConfig;
	
	public SecurityConfigGroup(Composite parent, int style, ClusterConfigModelMgr modelmgr) {				
		this.securityGroupComposite = new Composite(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gd.verticalIndent = 5;
		securityGroupComposite.setLayoutData(gd);
		securityGroupComposite.setLayout(gridLayout);		
		this.modelmgr = modelmgr;
	}
	
	/**
	 * Create Security configuration UI elements
	 */
	protected void createConfig() {
		createControllerConfig();
		createRequesterConfig();	
	}
	
	/**
	 * Create Security controller configuration UI elements
	 */
	protected void createControllerConfig() {
		controllerConfigGroup = new Group(securityGroupComposite, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gd.verticalIndent = 5;
		GridLayout gridLayout = new GridLayout(2, false);
		controllerConfigGroup.setLayout(gridLayout);
		controllerConfigGroup.setLayoutData(gd);
		controllerConfigGroup.setText("Security-Controller");
		
		//Policy File
		lPolicyFilePath = PanelUiUtil.createLabel(this.controllerConfigGroup, "Policy File: ");
//		tPolicyFilePath = PanelUiUtil.createText(this.securityConfigGroup);
//		tPolicyFilePath.addListener(SWT.Modify, getControllerValuesModifyListener(tPolicyFilePath, Elements.SECURITY_POLICY_FILE.localName));
		tPolicyFilePath = GvUiUtil.createTextGv(this.controllerConfigGroup, getControllerFieldValue(securityConfig, Elements.SECURITY_POLICY_FILE.localName));
		setControllerGvFieldListeners(tPolicyFilePath, SWT.Modify, Elements.SECURITY_POLICY_FILE.localName);
		
		//Identity password
		lCntrlIdentityPassword = PanelUiUtil.createLabel(this.controllerConfigGroup, "Policy File Identity Password: ");
//		tCntrlIdentityPassword = PanelUiUtil.createText(this.securityConfigGroup);
//		tCntrlIdentityPassword.addListener(SWT.Modify, getControllerValuesModifyListener(tCntrlIdentityPassword, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		tCntrlIdentityPassword = GvUiUtil.createPasswordGv(this.controllerConfigGroup, getControllerFieldValue(securityConfig, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		setControllerGvFieldListeners(tCntrlIdentityPassword, SWT.Modify, Elements.SECURITY_IDENTITY_PASSWORD.localName);		
	}

	/**
	 * Create Security requestor configuration UI elements
	 */
	protected void createRequesterConfig() {        
		requesterConfigGroup = new Group(securityGroupComposite, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.widthHint = 500;
		gd.verticalIndent = 5;
		GridLayout gridLayout = new GridLayout(2, false);
		requesterConfigGroup.setLayout(gridLayout);
		requesterConfigGroup.setLayoutData(gd);
		requesterConfigGroup.setText("Security-Requester");

		//Token file
		lTokenFilePath = PanelUiUtil.createLabel(this.requesterConfigGroup, "Token File: ");
//		tTokenFilePath = PanelUiUtil.createText(this.securityConfigGroup);
//		tTokenFilePath.addListener(SWT.Modify, getRequestorValuesModifyListener(tTokenFilePath, Elements.SECURITY_TOKEN_FILE.localName));
		tTokenFilePath = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_TOKEN_FILE.localName));
		setRequestorGvFieldListeners(tTokenFilePath, SWT.Modify, Elements.SECURITY_TOKEN_FILE.localName);

		//Identity password
		lReqlIdentityPassword = PanelUiUtil.createLabel(this.requesterConfigGroup, "Token File Identity Password: ");
//		tReqlIdentityPassword = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqlIdentityPassword.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqlIdentityPassword, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		tReqlIdentityPassword = GvUiUtil.createPasswordGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_IDENTITY_PASSWORD.localName));
		setRequestorGvFieldListeners(tReqlIdentityPassword, SWT.Modify, Elements.SECURITY_IDENTITY_PASSWORD.localName);		

		//Certificate-key file
		lReqCertKeyFilePath = PanelUiUtil.createLabel(this.requesterConfigGroup, "LDAP Identity File: ");
//		tReqCertKeyFilePath = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqCertKeyFilePath.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqCertKeyFilePath, Elements.SECURITY_CERT_KEY_FILE.localName));
		tReqCertKeyFilePath = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_CERT_KEY_FILE.localName));
		setRequestorGvFieldListeners(tReqCertKeyFilePath, SWT.Modify, Elements.SECURITY_CERT_KEY_FILE.localName);		

        //Domain name
		lReqDomainName = PanelUiUtil.createLabel(this.requesterConfigGroup, "Domain: ");
//		tReqDomainName = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqDomainName.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqDomainName, Elements.SECURITY_DOMAIN_NAME.localName));
		tReqDomainName = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_DOMAIN_NAME.localName));
		setRequestorGvFieldListeners(tReqDomainName, SWT.Modify, Elements.SECURITY_DOMAIN_NAME.localName);		
		
		//user name
		lReqUser = PanelUiUtil.createLabel(this.requesterConfigGroup, "Username: ");
//		tReqUser = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqUser.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqUser, Elements.SECURITY_USER_NAME.localName));
		tReqUser = GvUiUtil.createTextGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_USER_NAME.localName));
		setRequestorGvFieldListeners(tReqUser, SWT.Modify, Elements.SECURITY_USER_NAME.localName);		
		
		//user password
		lReqPassword = PanelUiUtil.createLabel(this.requesterConfigGroup, "Password: ");
//		tReqPassword = PanelUiUtil.createText(this.securityConfigGroup);
//		tReqPassword.addListener(SWT.Modify, getRequestorValuesModifyListener(tReqPassword, Elements.SECURITY_USER_PASSWORD.localName));		
		tReqPassword = GvUiUtil.createPasswordGv(this.requesterConfigGroup, getRequesterFieldValue(securityConfig, Elements.SECURITY_USER_PASSWORD.localName));
		setRequestorGvFieldListeners(tReqPassword, SWT.Modify, Elements.SECURITY_USER_PASSWORD.localName);		

	}
		
	/**
	 * Update UI
	 * @param securityConfig
	 */
	protected void updateConfig(SecurityConfig securityConfig) {		
		this.securityConfig = securityConfig;
		
		boolean securityEnabled = modelmgr.getModel().om.cacheOm.securityConfig.securityEnabled;
		if (securityEnabled) {
			String policyFile = securityConfig.securityControllerValues.get(Elements.SECURITY_POLICY_FILE.localName);
			if (policyFile != null) 
				tPolicyFilePath.setValue(policyFile);
			else
				tPolicyFilePath.setValue("");
	
			String cntrlIdentityPassword = securityConfig.securityControllerValues.get(Elements.SECURITY_IDENTITY_PASSWORD.localName);
			if (cntrlIdentityPassword != null) 
				tCntrlIdentityPassword.setValue(cntrlIdentityPassword);
			else
				tCntrlIdentityPassword.setValue("");
	
			String tokenFile = securityConfig.securityRequesterValues.get(Elements.SECURITY_TOKEN_FILE.localName);
			if (tokenFile != null) 
				tTokenFilePath.setValue(tokenFile); 
			else
				tTokenFilePath.setValue("");
	
			String reqIdentityPassword = securityConfig.securityRequesterValues.get(Elements.SECURITY_IDENTITY_PASSWORD.localName);
			if (reqIdentityPassword != null) 
				tReqlIdentityPassword.setValue(reqIdentityPassword);
			else
				tReqlIdentityPassword.setValue("");
	
			String reqCertKeyFile = securityConfig.securityRequesterValues.get(Elements.SECURITY_CERT_KEY_FILE.localName);
			if (reqCertKeyFile != null) 
				tReqCertKeyFilePath.setValue(reqCertKeyFile);
			else
				tReqCertKeyFilePath.setValue("");
	
			String reqDomainName = securityConfig.securityRequesterValues.get(Elements.SECURITY_DOMAIN_NAME.localName);
			if (reqDomainName != null) 
				tReqDomainName.setValue(reqDomainName);
			else
				tReqDomainName.setValue("");
	
			String reqUserName = securityConfig.securityRequesterValues.get(Elements.SECURITY_USER_NAME.localName); 
			if (reqUserName != null) 
				tReqUser.setValue(reqUserName);
			else
				tReqUser.setValue("");
	
			String reqPassowrd = securityConfig.securityRequesterValues.get(Elements.SECURITY_USER_PASSWORD.localName);
			if (reqPassowrd != null) 
				tReqPassword.setValue(reqPassowrd);
			else
				tReqPassword.setValue("");
		}	
	}
	
	/**
	 * @param flag
	 */
	public void setVisible(boolean flag) {
		this.securityGroupComposite.setVisible(flag);
	}

	/**
	 * Set UI visibility
	 * @param visible
	 */
	protected void SetControllerConfigVisibility(boolean visible) {
		controllerConfigGroup.setVisible(visible);
	}

	/**
	 * Set UI visibility
	 * @param visible
	 */
	protected void setRequestorConfigVisibility(boolean visible) {
		requesterConfigGroup.setVisible(visible);
	}

	/**
	 * @param gvField
	 * @param eventType
	 * @param modelId
	 */
	protected void setControllerGvFieldListeners(GvField gvField, int eventType, String modelId) {
		gvField.setFieldListener(eventType, getControllerValuesModifyListener(gvField.getField(), modelId));
		gvField.setGvListener(getControllerValuesModifyListener(gvField.getGvText(), modelId));
	}

	/**
	 * @param gvField
	 * @param eventType
	 * @param modelId
	 */
	protected void setRequestorGvFieldListeners(GvField gvField, int eventType, String modelId) {
		gvField.setFieldListener(eventType, getRequestorValuesModifyListener(gvField.getField(), modelId));
		gvField.setGvListener(getRequestorValuesModifyListener(gvField.getGvText(), modelId));
	}
	
	/**
	 * @param field
	 * @param key
	 * @return
	 */
	protected Listener getControllerValuesModifyListener(final Control field, final String key) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(field instanceof Text){
					modelmgr.updateSecurityControllerValues(SecurityConfigGroup.this.securityConfig, key, ((Text) field).getText());
				}
				else if(field instanceof Button){
					modelmgr.updateSecurityControllerValues(SecurityConfigGroup.this.securityConfig, key, new Boolean(((Button) field).getSelection()).toString());
				}
				else if(field instanceof Combo){
					modelmgr.updateSecurityControllerValues(SecurityConfigGroup.this.securityConfig, key, ((Combo) field).getText());
				}
			}
		};	
		return listener;
	}
	
	/**
	 * @param field
	 * @param key
	 * @return
	 */
	protected Listener getRequestorValuesModifyListener(final Control field, final String key) {
		return new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(field instanceof Text){
					modelmgr.updateSecurityRequesterValues(SecurityConfigGroup.this.securityConfig, key, ((Text) field).getText());
				}
				else if(field instanceof Button){
					modelmgr.updateSecurityRequesterValues(SecurityConfigGroup.this.securityConfig, key, new Boolean(((Button) field).getSelection()).toString());
				}
				else if(field instanceof Combo){
					modelmgr.updateSecurityRequesterValues(SecurityConfigGroup.this.securityConfig, key, ((Combo) field).getText());
				}
			}
		};
	}

	/**
	 * @param securityConfig
	 * @param key
	 * @return
	 */
	protected String getControllerFieldValue(SecurityConfig securityConfig, String key) {
		if (securityConfig == null) {
			return "";
		}
		return securityConfig.securityControllerValues.get(key);
	}	

	/**
	 * @param securityConfig
	 * @param key
	 * @return
	 */
	protected String getRequesterFieldValue(SecurityConfig securityConfig, String key) {
		if (securityConfig == null) {
			return "";
		}
		return securityConfig.securityRequesterValues.get(key);
	}
	
    public GvField createGvComboField(Composite parent, String items[], String initialValue) {
    	GvField gvField = GvUiUtil.createComboGv(parent, initialValue);
		Combo combo = (Combo) gvField.getField();
		combo.setItems(items);
		return gvField;
    }	
}
