package com.tibco.cep.sharedresource.ssl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;

/**
 * 
 * @author moshaikh
 */
public class SslConfigurationJdbcDialog extends SslConfigurationDialog {

	private GvField gbClientAuth, gbVerifyHostName, gtExpHostName;
    private Map<String,String> gvTypeFieldMap = new LinkedHashMap<String,String>();
	
	public SslConfigurationJdbcDialog(Shell parent, SslConfigModel model, IProject project) {
		super(parent, model, project, true);
	}

	@Override
	public void createConfigFields(Shell dialog) {
		SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) model;
		initialiseGvFieldTypeMap();
		
		gbClientAuth = createGvCheckboxField(dialog, "Requires Client Authentication: ",
				SslConfigJdbcModel.ID_CLIENT_AUTH, sslConfigJdbcModel.getClientAuth());
	}
	
	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) model;
		
		gbVerifyHostName = createGvCheckboxField(dialog, "Verify Host Name: ", SslConfigJdbcModel.ID_VERIFY_HOST_NAME,
				sslConfigJdbcModel.getVerifyHostName());
		gtExpHostName = createGvTextField(dialog, "Expected Host Name: ", SslConfigJdbcModel.ID_EXPECTED_HOSTNAME,
				sslConfigJdbcModel.getExpectedHostName(), 2);
		
		toggleIdentityField(Boolean.parseBoolean(gbClientAuth.getGvDefinedValue(project)));
		toggleHostnameField(Boolean.parseBoolean(gbVerifyHostName.getGvDefinedValue(project)));
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
	}

	@Override
	public void saveFields() {
		SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) model;
		sslConfigJdbcModel.setClientAuth(gbClientAuth.getValue());
		sslConfigJdbcModel.setVerifyHostName(gbVerifyHostName.getValue());
		sslConfigJdbcModel.setExpectedHostName(gtExpHostName.getValue());
	}
	
	@Override
	public boolean isFieldsDirty() {
		SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) model;
		return (super.isFieldsDirty() || 
				!gbClientAuth.getValue().equals(sslConfigJdbcModel.getClientAuth()) ||
				!gbVerifyHostName.getValue().equals(sslConfigJdbcModel.getVerifyHostName()) ||
				!gtExpHostName.getValue().equals(sslConfigJdbcModel.getExpectedHostName()));
	}
	
	private void initialiseGvFieldTypeMap() {
		gvTypeFieldMap.put(SslConfigJdbcModel.ID_CLIENT_AUTH, "boolean");
		gvTypeFieldMap.put(SslConfigJdbcModel.ID_VERIFY_HOST_NAME, "boolean");
        gvTypeFieldMap.put(SslConfigJdbcModel.ID_EXPECTED_HOSTNAME, "String");
        gvTypeFieldMap.put(SslConfigModel.ID_TRUST_STORE_PASSWD, "Password");
        gvTypeFieldMap.put(SslConfigModel.ID_CERT_FOLDER, "String");
 	}

	@Override
	protected Listener getGvListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
                	String val=((Text)field).getText();
					if(GvUtil.isGlobalVar(val)){
						SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) model;
						if (SslConfigJdbcModel.ID_CLIENT_AUTH.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigJdbcModel.getClientAuth(), key, project.getName());
						} else if (SslConfigJdbcModel.ID_VERIFY_HOST_NAME.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigJdbcModel.getVerifyHostName(), key,project.getName());
						} else if (SslConfigJdbcModel.ID_EXPECTED_HOSTNAME.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigJdbcModel.getExpectedHostName(), key,project.getName());
						} else if (SslConfigModel.ID_TRUST_STORE_PASSWD.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),sslConfigJdbcModel.getTrustStorePasswd(), key,project.getName());
						} else {
							validateField((Text)field,"String", sslConfigJdbcModel.getCert(), key,project.getName());
						}
					}
                }
				
				if (key.equals(SslConfigJdbcModel.ID_CLIENT_AUTH)) {
					boolean isClientAuthEnabled = Boolean.parseBoolean(gbClientAuth.getGvDefinedValue(project));
					toggleIdentityField(isClientAuthEnabled);
				} else if (key.equals(SslConfigJdbcModel.ID_VERIFY_HOST_NAME)) {
					boolean isVerifyHostname = Boolean.parseBoolean(gbVerifyHostName.getGvDefinedValue(project));
					toggleHostnameField(isVerifyHostname);
				}
			}
		};
		return listener;
	}
	
	private void toggleIdentityField(boolean enable) {
		tIdentity.setEnabled(enable);
		bIdentityBrowse.setEnabled(enable);
	}
	
	private void toggleHostnameField(boolean enable) {
		gtExpHostName.getGvToggleButton().setEnabled(enable);
		gtExpHostName.getField().setEnabled(enable);
	}
}
