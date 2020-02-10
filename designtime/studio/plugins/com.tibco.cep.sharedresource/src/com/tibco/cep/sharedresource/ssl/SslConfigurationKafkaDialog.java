package com.tibco.cep.sharedresource.ssl;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/**
 * 
 * @author moshaikh
 */
public class SslConfigurationKafkaDialog extends SslConfigurationDialog {

	private HashMap<Object, Object> controls;
	private String driverType;

	public SslConfigurationKafkaDialog(Shell parent, SslConfigModel model, IProject project, HashMap<Object, Object> controls, String driverType) {
		super(parent, model, project, false);
		this.controls = controls;
		this.driverType = driverType;
	}

	@Override
	public void saveFields() {
		Object sslTruststoreField = controls.get(driverType + CommonIndexUtils.DOT + "kafka.trusted.certs.folder");
		if (sslTruststoreField instanceof Text) {
			((Text)sslTruststoreField).setText(model.getCert());
		}
		Object sslKeystoreField = controls.get(driverType + CommonIndexUtils.DOT + "kafka.keystore.identity");
		if (sslKeystoreField instanceof Text) {
			((Text)sslKeystoreField).setText(model.getIdentity());
		}
		Object sslTruststorePassword = controls.get(driverType + CommonIndexUtils.DOT + "kafka.truststore.password");
		if (sslTruststorePassword instanceof Text) {
			String pwd = model.getTrustStorePasswd();
			if (!model.isTurstStorePasswordGv() && !(pwd.equalsIgnoreCase("") || pwd==null)) {
				try {
					if (!ObfuscationEngine.hasEncryptionPrefix(pwd)) {
						pwd = ObfuscationEngine.encrypt(model.getTrustStorePasswd().toCharArray());
					}
				} catch (AXSecurityException e) {
//					e.printStackTrace();
				}
			}
			((Text) sslTruststorePassword).setText(pwd);
		}
	}
	@Override
	protected String getCertifactesFolderLabel() {
		return "Trusted Certificates Folder*: ";
	}
	@Override
	public void createConfigFields(Shell dialog) {
		// TODO Auto-generated method stub
	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Listener getGvListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					String val=((Text)field).getText();
					if(GvUtil.isGlobalVar(val)){
						if(SslConfigModel.ID_TRUST_STORE_PASSWD.equalsIgnoreCase(key)){
							validateField((Text)field, "Password", model.getTrustStorePasswd(), key,project.getName());
						}else{
							validateField((Text)field,"String", model.getCert(), key,project.getName());
						}
						
					}
				}
			}
		};
		return listener;
	}
}
