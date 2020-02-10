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
 * @author ssinghal
 *
 */
public class SslConfigurationMqttDialog extends SslConfigurationDialog{
	
	private GvField gvClientAuth;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	
	public SslConfigurationMqttDialog(Shell parent, SslConfigModel model, IProject project) {
		super(parent, model, project, true, "MQTT");
	}
	
	public SslConfigurationMqttDialog(Shell parent, SslConfigMqttModel model, IProject project, String channelName) {
		super(parent, model, project, true, channelName);
	}

	@Override
	public void createConfigFields(Shell dialog) {
		SslConfigMqttModel sslConfigMqttModel = (SslConfigMqttModel) model;
		initialiseGvFieldTypeMap(sslConfigMqttModel);
		gvClientAuth = createGvCheckboxField(dialog, "Requires Client Authentication: ",
				SslConfigMqttModel.ID_CLIENT_AUTH,	sslConfigMqttModel.clientAuth);
	}
	
	public boolean isFieldsDirty() {
		SslConfigMqttModel sslConfigMqttModel = (SslConfigMqttModel) model;
		return (super.isFieldsDirty() || !gvClientAuth.getValue().equals(sslConfigMqttModel.clientAuth) );
	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		
	}

	@Override
	public void saveFields() {
		SslConfigMqttModel sslConfigMqttModel = (SslConfigMqttModel) model;
		sslConfigMqttModel.clientAuth = gvClientAuth.getValue();
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
		
	}
	
	private void initialiseGvFieldTypeMap(SslConfigMqttModel sslConfigMqttModel) {
        gvTypeFieldMap.put(sslConfigMqttModel.ID_CLIENT_AUTH, "boolean");
        gvTypeFieldMap.put(sslConfigMqttModel.ID_CERT_FOLDER, "String");
        gvTypeFieldMap.put(sslConfigMqttModel.ID_IDENTITY, "String");
        gvTypeFieldMap.put(sslConfigMqttModel.ID_TRUST_STORE_PASSWD, "Password");
   	}

	@Override
	protected Listener getGvListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					String val=((Text)field).getText();
					if(GvUtil.isGlobalVar(val)){
						SslConfigMqttModel sslConfigMqttModel = (SslConfigMqttModel) model;
						if(SslConfigMqttModel.ID_CLIENT_AUTH.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigMqttModel.clientAuth, key,project.getName());
						}else if(SslConfigModel.ID_TRUST_STORE_PASSWD.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigMqttModel.getTrustStorePasswd(), key,project.getName());
						}else{
							validateField((Text)field,"String", sslConfigMqttModel.getCert(), key,project.getName());
						}
					}
				}
				
				if (key.equals(SslConfigMqttModel.ID_CLIENT_AUTH)) {
					boolean isClientAuthEnabled = Boolean.parseBoolean(gvClientAuth.getGvDefinedValue(project));
					toggleIdentityField(isClientAuthEnabled);
				}
			}
		};
		return listener;
	}
	
	private void toggleIdentityField(boolean enable) {
		tIdentity.setEnabled(enable);
		bIdentityBrowse.setEnabled(enable);
	}
	
	@Override
	public void createCertIdentityFields() {
		super.createCertIdentityFields();
		boolean isClientAuthEnabled = Boolean.parseBoolean(gvClientAuth.getGvDefinedValue(project));
		toggleIdentityField(isClientAuthEnabled);
	}

}
