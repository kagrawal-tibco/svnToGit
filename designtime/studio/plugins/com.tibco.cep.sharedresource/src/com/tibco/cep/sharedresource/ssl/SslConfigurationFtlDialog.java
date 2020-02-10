package com.tibco.cep.sharedresource.ssl;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_NAME;

public class SslConfigurationFtlDialog extends SslConfigurationDialog{
	protected Button bSpecifiedTrustFile,bSpecifiedTrustString,bTrustEveryone,bBrowseTrustFile;
	protected Label lTrustType;
	protected GvField tTrustString;
	protected String previousTrustType="";
	public SslConfigurationFtlDialog(Shell parent, SslConfigModel model, IProject project) {
		super(parent, model, project, true,"FTL");
	}
	
	public SslConfigurationFtlDialog(Shell parent, SslConfigModel model, IProject project, String channelName) {
		super(parent, model, project, true, channelName);
	}

	@Override
	public void createConfigFields(Shell dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		lTrustType = PanelUiUtil.createLabel(dialog, "Trust Type: ");
		PanelUiUtil.setLabelBold(dialog, lTrustType);
		
		
		bSpecifiedTrustFile = PanelUiUtil.createRadioButton(dialog,SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE );
		bSpecifiedTrustFile.addListener(SWT.Selection, getRadioChangeListener(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE ));
		bSpecifiedTrustFile.setSelection(isTrustTypeSelected(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE ));
		bSpecifiedTrustString = PanelUiUtil.createRadioButton(dialog, SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING);
		bSpecifiedTrustString.addListener(SWT.Selection, getRadioChangeListener(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING));
		bSpecifiedTrustString.setSelection(isTrustTypeSelected(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING));
		GridData data = new GridData();
		bSpecifiedTrustString.setLayoutData(data);
		
		bTrustEveryone=PanelUiUtil.createRadioButton(dialog,SslConfigFtlModel.ID_HTTPS_CONNECTION_TRUST_EVERYONE);
		bTrustEveryone.addListener(SWT.Selection, getRadioChangeListener(SslConfigFtlModel.ID_HTTPS_CONNECTION_TRUST_EVERYONE));
		bTrustEveryone.setSelection(isTrustTypeSelected(SslConfigFtlModel.ID_HTTPS_CONNECTION_TRUST_EVERYONE));
		
	//	tTrustString = PanelUiUtil.createText(dialog);
		//tTrustString.setText(sslConfigFtlModel.getTrust_string());
		tTrustString = createGvTextField(dialog, "",sslConfigFtlModel.getTrust_string(),sslConfigFtlModel.getTrust_string(),1);
		tTrustString.setVisible(getTrustStringVisibility());
		
		Composite comp = new Composite(dialog, 1);
		comp.setLayoutData(new GridData(768));
		comp.setLayout(new GridLayout(3, false));
		
		lIdentity = PanelUiUtil.createLabel(comp, "Identity: ");
		lIdentity.setVisible(getFileBrowseVisibility());
		tIdentity = PanelUiUtil.createText(comp, 170);
		tIdentity.setText(sslConfigFtlModel.getTrust_file());
		tIdentity.addListener(SWT.Modify, getChangeListener(SslConfigModel.ID_IDENTITY));
		tIdentity.setVisible(getFileBrowseVisibility());
		bIdentityBrowse = PanelUiUtil.createBrowsePushButton(comp, tIdentity);
		bIdentityBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(comp, project, new String[]{"id"}, tIdentity));
		bIdentityBrowse.setVisible(getFileBrowseVisibility());
		if(this.channelName.equals(AS3_CHANNEL_NAME)){
			hideTrustString();
		}
	}
	public boolean isTrustTypeSelected(String type){
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		return type.equalsIgnoreCase(sslConfigFtlModel.trust_type);
	}
	public boolean getTrustStringVisibility(){
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		if(sslConfigFtlModel.getTrust_type().equals(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING))
			return true;
		return false;
	}
	public boolean getFileBrowseVisibility(){
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		if(sslConfigFtlModel.getTrust_type().equals(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE))
			return true;
		return false;
	}
	@Override
	public void saveFields() {
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		sslConfigFtlModel.setTrust_file(tIdentity.getText());
		sslConfigFtlModel.setTrust_string(tTrustString.getValue());
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Listener getGvListener(final Control field,final String key) {

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					String val=((Text)field).getText();
					if(GvUtil.isGlobalVar(val)){
						SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel) model;
						if(sslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING.equalsIgnoreCase(sslConfigFtlModel.getTrust_type())){
							validateField((Text)field,"String", sslConfigFtlModel.getTrust_string(), key,project.getName());
						}
					}
				}
			}
		};
		return listener;
	
	}
	@Override
	protected Listener getChangeListener(final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				validateFields();
			}
		};
		return listener;
	}
	private boolean validateFields() {
		boolean valid = true;
		valid &= PanelUiUtil.validateTextField(tIdentity, true, false);
		return valid;
	}
	@Override
	public boolean isFieldsDirty() {
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		return (!previousTrustType.equalsIgnoreCase(sslConfigFtlModel.getTrust_type()) || !tTrustString.getValue().equalsIgnoreCase(sslConfigFtlModel.getTrust_string()) || !tIdentity.getText().equalsIgnoreCase(sslConfigFtlModel.getTrust_file()));
	}
	protected Listener getRadioChangeListener(final String key) {
		final SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel)model;
		previousTrustType = sslConfigFtlModel.getTrust_type();
		final String channelName = this.channelName;
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (key.equals(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE)) {
					sslConfigFtlModel.setTrust_type(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE);
					tTrustString.setVisible(getTrustStringVisibility());
					lIdentity.setVisible(getFileBrowseVisibility());
					tIdentity.setVisible(getFileBrowseVisibility());
					bIdentityBrowse.setVisible(getFileBrowseVisibility());
				} else if (key.equals(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING)) {
					sslConfigFtlModel.setTrust_type(SslConfigFtlModel.ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING);
					tTrustString.setVisible(getTrustStringVisibility());
					lIdentity.setVisible(getFileBrowseVisibility());
					tIdentity.setVisible(getFileBrowseVisibility());
					bIdentityBrowse.setVisible(getFileBrowseVisibility());
				} else if (key.equals(SslConfigFtlModel.ID_HTTPS_CONNECTION_TRUST_EVERYONE)) {
					sslConfigFtlModel.setTrust_type(SslConfigFtlModel.ID_HTTPS_CONNECTION_TRUST_EVERYONE);
					tTrustString.setVisible(getTrustStringVisibility());
					lIdentity.setVisible(getFileBrowseVisibility());
					tIdentity.setVisible(getFileBrowseVisibility());
					bIdentityBrowse.setVisible(getFileBrowseVisibility());
				}
				if(channelName.equals(AS3_CHANNEL_NAME)){
					hideTrustString();
				}
			}
		};
		return listener;
	}
	
	private void hideTrustString(){
		tTrustString.setVisible(false);
		bSpecifiedTrustString.setVisible(false);
		GridData d = (GridData) bSpecifiedTrustString.getLayoutData();
		d.exclude = true;
	}
}
