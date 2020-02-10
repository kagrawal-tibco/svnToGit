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

/*
@author ssailapp
@date Mar 2, 2010 3:51:47 PM
 */

public class SslConfigurationRvDialog extends SslConfigurationDialog {
	
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

	public SslConfigurationRvDialog(Shell parent, SslConfigModel model, IProject project) {
		super(parent, model, project, false);
	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
	}

	@Override
	public void createConfigFields(Shell dialog) {
		initialiseGvFieldTypeMap(model);
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
	}

	@Override
	public void saveFields() {
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
						if(SslConfigModel.ID_CERT_FOLDER.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key), model.getCert(), key,project.getName());
						}else if(SslConfigModel.ID_TRUST_STORE_PASSWD.equalsIgnoreCase(key)){
							validateField((Text)field,gvTypeFieldMap.get(key),model.getTrustStorePasswd(), key,project.getName());
						}
					}
				}
				
			}
		};
		return listener;
	}
	
	@Override
	protected String getCertifactesFolderLabel() {
		return "Daemon Certificate*: ";
	}
	
	private void initialiseGvFieldTypeMap(SslConfigModel model) {
        gvTypeFieldMap.put(SslConfigModel.ID_TRUST_STORE_PASSWD, "Password");
        gvTypeFieldMap.put(SslConfigModel.ID_CERT_FOLDER, "String");
   	}

}
