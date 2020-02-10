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

/*
 @author ssailapp
 @date Mar 2, 2010 3:29:55 PM
 */

public class SslConfigurationHttpDialog extends SslConfigurationDialog {

	// private Button bClientAuth, bCipherSuite;
	private GvField gbClientAuth, gbCipherSuite;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

	public SslConfigurationHttpDialog(Shell parent, SslConfigHttpModel model,
			IProject project) {
		super(parent, model, project, false);
	}

	@Override
	public void createConfigFields(Shell dialog) {
		SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) model;
		initialiseGvFieldTypeMap(sslConfigHttpModel);
		//PanelUiUtil.createLabel(dialog, "Requires Client Authentication: ");
		// bClientAuth = PanelUiUtil.createCheckBox(dialog, "");
		// bClientAuth.setSelection(sslConfigHttpModel.clientAuth);
		// bClientAuth.addListener(SWT.Selection,
		// getChangeListener(SslConfigHttpModel.ID_CLIENT_AUTH));
		gbClientAuth = createGvCheckboxField(dialog,
				"Requires Client Authentication: ",
				SslConfigHttpModel.ID_CLIENT_AUTH,
				sslConfigHttpModel.clientAuth);
	}
	
	private void initialiseGvFieldTypeMap(SslConfigHttpModel sslConfigHttpModel) {
        gvTypeFieldMap.put(SslConfigHttpModel.ID_CLIENT_AUTH, "boolean");
        gvTypeFieldMap.put(SslConfigHttpModel.ID_CIPHER_SUITE, "boolean");
        gvTypeFieldMap.put(SslConfigModel.ID_TRUST_STORE_PASSWD, "Password");
        gvTypeFieldMap.put(SslConfigModel.ID_CERT_FOLDER, "String");
   	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) model;
		// PanelUiUtil.createLabel(dialog, "Strong Cipher Suites Only: ");
		// bCipherSuite = PanelUiUtil.createCheckBox(dialog, "");
		// bCipherSuite.setSelection(sslConfigHttpModel.cipherSuites);
		// bCipherSuite.addListener(SWT.Selection,
		// getChangeListener(SslConfigHttpModel.ID_CIPHER_SUITE));

		gbCipherSuite = createGvCheckboxField(dialog,
				"Strong Cipher Suites Only: ",
				SslConfigHttpModel.ID_CIPHER_SUITE,
				sslConfigHttpModel.cipherSuites);

		// bClientAuth.notifyListeners(SWT.Selection, new Event());
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
		if (key.equals(SslConfigHttpModel.ID_CIPHER_SUITE)) {
		} else if (key.equals(SslConfigHttpModel.ID_CLIENT_AUTH)) {
			//lCertFolder.setEnabled(bClientAuth.getSelection());
			//tCertFolder.setEnabled(bClientAuth.getSelection());
			//bCertFolderBrowse.setEnabled(bClientAuth.getSelection());
		}
	}

	public boolean isFieldsDirty() {
		SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) model;
		return (super.isFieldsDirty() || !gbClientAuth.getValue().equals(sslConfigHttpModel.clientAuth) || !gbCipherSuite.getValue().equals(sslConfigHttpModel.cipherSuites));
	}

	@Override
	public void saveFields() {
		SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) model;
		sslConfigHttpModel.clientAuth = gbClientAuth.getValue();
		sslConfigHttpModel.cipherSuites = gbCipherSuite.getValue();
	}
	
	@Override
	protected Listener getGvListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) model;
						if(SslConfigHttpModel.ID_CLIENT_AUTH.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigHttpModel.clientAuth, key,project.getName());
						}else if(SslConfigHttpModel.ID_CIPHER_SUITE.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigHttpModel.cipherSuites, key,project.getName());
						}else if(SslConfigModel.ID_TRUST_STORE_PASSWD.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),sslConfigHttpModel.getTrustStorePasswd(), key,project.getName());
						}else{
							validateField((Text)field,"String", sslConfigHttpModel.getCert(), key,project.getName());
						}
					}
				}
				
				if (key.equals(SslConfigHttpModel.ID_CIPHER_SUITE)) {
					
				} else if (key.equals(SslConfigHttpModel.ID_CLIENT_AUTH)) {
					boolean isClientAuthEnabled = Boolean.parseBoolean(gbClientAuth.getGvDefinedValue(project));
					gCertFolder.setEnabled(isClientAuthEnabled);
					bCertFolderBrowse.setEnabled(isClientAuthEnabled);

				}
			}
		};
		return listener;
	}	

	@Override
	public void createCertIdentityFields() {
		super.createCertIdentityFields();
		boolean isClientAuthEnabled = Boolean.parseBoolean(gbClientAuth.getGvDefinedValue(project));
		toggleCertificateField(isClientAuthEnabled);
	}
	private void toggleCertificateField(boolean enable) {
		bCertFolderBrowse.setEnabled(enable);
		gCertFolder.setEnabled(enable);
	}
}
