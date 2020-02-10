package com.tibco.cep.sharedresource.ssl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 2, 2010 3:52:44 PM
 */

public class SslConfigurationJmsDialog extends SslConfigurationDialog {

	//private Button bCipherSuite, bTrace, bDebugTrace, bVerifyHostName;
	private GvField gbCipherSuite, gbTrace, gbDebugTrace, gbVerifyHostName, gtExpHostName;
    private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	
	public SslConfigurationJmsDialog(Shell parent, SslConfigModel model, IProject project) {
		super(parent, model, project, true);
	}

	@Override
	public void createConfigFields(Shell dialog) {
	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		SslConfigJmsModel sslConfigJmsModel = (SslConfigJmsModel) model;
		initialiseGvFieldTypeMap();
		
		//PanelUiUtil.createLabel(dialog, "Trace: ");
		//bTrace = PanelUiUtil.createCheckBox(dialog, "");
		//bTrace.setSelection(sslConfigJmsModel.trace);
		//bTrace.addListener(SWT.Selection, getChangeListener(SslConfigJmsModel.ID_TRACE));
		//PanelUiUtil.createLabelFiller(dialog);
		gbTrace = createGvCheckboxField(dialog, "Trace: ",
				SslConfigJmsModel.ID_TRACE, sslConfigJmsModel.trace);
		
		//PanelUiUtil.createLabel(dialog, "Debug Trace: ");
		//bDebugTrace = PanelUiUtil.createCheckBox(dialog, "");
		//bDebugTrace.setSelection(sslConfigJmsModel.debugTrace);
		//bDebugTrace.addListener(SWT.Selection, getChangeListener(SslConfigJmsModel.ID_DEBUG_TRACE));
		//PanelUiUtil.createLabelFiller(dialog);
		gbDebugTrace = createGvCheckboxField(dialog, "Debug Trace: ",
				SslConfigJmsModel.ID_DEBUG_TRACE, sslConfigJmsModel.debugTrace);
		
		//PanelUiUtil.createLabel(dialog, "Verify Host Name: ");
		//bVerifyHostName = PanelUiUtil.createCheckBox(dialog, "");
		//bVerifyHostName.setSelection(sslConfigJmsModel.verifyHostName);
		//bVerifyHostName.addListener(SWT.Selection, getChangeListener(SslConfigJmsModel.ID_VERIFY_HOST_NAME));
		//PanelUiUtil.createLabelFiller(dialog);
		gbVerifyHostName = createGvCheckboxField(dialog, "Verify Host Name: ",
				SslConfigJmsModel.ID_VERIFY_HOST_NAME,
				sslConfigJmsModel.verifyHostName);
		
		//PanelUiUtil.createLabel(dialog, "Expected Host Name: ");
		//tExpHostName = PanelUiUtil.createText(dialog);
		//tExpHostName.setText(sslConfigJmsModel.expectedHostName);
		//tExpHostName.addListener(SWT.Modify, getChangeListener(SslConfigJmsModel.ID_EXPECTED_HOSTNAME));
		//PanelUiUtil.createLabelFiller(dialog);
		gtExpHostName = createGvTextField(dialog, "Expected Host Name: ", SslConfigJmsModel.ID_EXPECTED_HOSTNAME, sslConfigJmsModel.expectedHostName, 2);
		
		//PanelUiUtil.createLabel(dialog, "Strong Cipher Suites Only: ");
		//bCipherSuite = PanelUiUtil.createCheckBox(dialog, "");
		//bCipherSuite.setSelection(sslConfigJmsModel.cipherSuites);
		//bCipherSuite.addListener(SWT.Selection, getChangeListener(SslConfigJmsModel.ID_CIPHER_SUITE));
		//PanelUiUtil.createLabelFiller(dialog);
		gbCipherSuite = createGvCheckboxField(dialog,
				"Strong Cipher Suites Only: ",
				SslConfigJmsModel.ID_CIPHER_SUITE,
				sslConfigJmsModel.cipherSuites);
	}
	
    private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put(SslConfigJmsModel.ID_CIPHER_SUITE, "boolean");
        gvTypeFieldMap.put(SslConfigJmsModel.ID_EXPECTED_HOSTNAME, "String");
        gvTypeFieldMap.put(SslConfigJmsModel.ID_VERIFY_HOST_NAME, "boolean");
        gvTypeFieldMap.put(SslConfigJmsModel.ID_DEBUG_TRACE, "boolean");
        gvTypeFieldMap.put(SslConfigJmsModel.ID_TRACE, "boolean");
        gvTypeFieldMap.put(SslConfigModel.ID_TRUST_STORE_PASSWD, "Password");
        gvTypeFieldMap.put(SslConfigModel.ID_CERT_FOLDER, "String");
 	}


	@Override
	public void handleAdvancedFieldChanges(String key) {
	}

	@Override
	public void saveFields() {
		SslConfigJmsModel sslConfigJmsModel = (SslConfigJmsModel) model;
		sslConfigJmsModel.trace = gbTrace.getValue();
		sslConfigJmsModel.debugTrace = gbDebugTrace.getValue();
		sslConfigJmsModel.verifyHostName = gbVerifyHostName.getValue();
		sslConfigJmsModel.expectedHostName = gtExpHostName.getValue();
		sslConfigJmsModel.cipherSuites = gbCipherSuite.getValue();
	}
	
	@Override
	public boolean isFieldsDirty() {
		SslConfigJmsModel sslConfigJmsModel = (SslConfigJmsModel) model;
		return (super.isFieldsDirty() || 
				!gbTrace.getValue().equals(sslConfigJmsModel.trace) ||
				!gbDebugTrace.getValue().equals(sslConfigJmsModel.debugTrace) ||
				!gbVerifyHostName.getValue().equals(sslConfigJmsModel.verifyHostName) ||
				!gtExpHostName.getValue().equals(sslConfigJmsModel.expectedHostName) ||
				!gbCipherSuite.getValue().equals(sslConfigJmsModel.cipherSuites));
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
						SslConfigJmsModel sslConfigJmsModel = (SslConfigJmsModel) model;
						if(SslConfigJmsModel.ID_EXPECTED_HOSTNAME.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigJmsModel.expectedHostName, key,project.getName());
						}else if(SslConfigJmsModel.ID_VERIFY_HOST_NAME.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), sslConfigJmsModel.verifyHostName, key,project.getName());
						}else if(SslConfigJmsModel.ID_DEBUG_TRACE.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),sslConfigJmsModel.debugTrace, key,project.getName());
						}else if(SslConfigJmsModel.ID_TRACE.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),sslConfigJmsModel.trace, key,project.getName());
						}else if(SslConfigJmsModel.ID_CIPHER_SUITE.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),sslConfigJmsModel.cipherSuites, key,project.getName());
						}else if(SslConfigModel.ID_TRUST_STORE_PASSWD.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),sslConfigJmsModel.getTrustStorePasswd(), key,project.getName());
						}else{
							validateField((Text)field,"String", sslConfigJmsModel.getCert(), key,project.getName());
						}
					}
                }
			}
		};
		return listener;
	}
}
