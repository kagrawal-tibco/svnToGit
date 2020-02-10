package com.tibco.cep.studio.sb.sharedresource;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.driver.sb.SBConstants;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 28, 2009 1:13:01 PM
 */
@SuppressWarnings({"unused"})
public class SBGeneralPage extends AbstractSharedResourceEditorPage {

	private SBConfigModelMgr modelmgr;
	private Text sbDesc;
	private GvField sbServerUrl, sbPassword, sbUsername, trustStore, keyStore, keyStorePassword, trustStorePassword, keyPassword; 
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	private Button trustStoreBrowse;
	private Button keyStoreBrowse;
	
	public SBGeneralPage(SBConfigEditor editor, Composite parent, SBConfigModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;
		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		initialiseGvFieldTypeMap();
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

		Composite sGeneral = sectionProvider.createSectionPart("General", false);
		createGeneralSectionContents(sGeneral);
		if(modelmgr.getProject() !=null){
			validateFieldsForGv();
		}
		
		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName, "String");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName, "String");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName, "Password");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_TRUST_STORE.localName, "String");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.localName, "Password");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_KEY_STORE.localName, "String");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.localName, "Password");
        gvTypeFieldMap.put(SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.localName, "Password");
        
 	}
	
	public void validateFieldsForGv() {
		validateField((Text)sbServerUrl.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName), SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName, modelmgr.getProject().getName());
		validateField((Text)sbUsername.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName), SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName, modelmgr.getProject().getName());
		validateField((Text)sbPassword.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName), SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName, modelmgr.getProject().getName());
		validateField((Text)trustStore.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_TRUST_STORE.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_TRUST_STORE.localName), SBConstants.CHANNEL_PROPERTY_TRUST_STORE.localName, modelmgr.getProject().getName());
		validateField((Text)trustStorePassword.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.localName), SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.localName, modelmgr.getProject().getName());
		validateField((Text)keyStore.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_KEY_STORE.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_KEY_STORE.localName), SBConstants.CHANNEL_PROPERTY_KEY_STORE.localName, modelmgr.getProject().getName());
		validateField((Text)keyStorePassword.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.localName), SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.localName, modelmgr.getProject().getName());
		validateField((Text)keyPassword.getGvText(),gvTypeFieldMap.get(SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.localName), modelmgr.getStringValue(SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.localName), SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.localName, modelmgr.getProject().getName());
	}
	
	
	private void createGeneralSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		Label lDesc = PanelUiUtil.createLabel(comp, "Description: ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		sbDesc = PanelUiUtil.createTextMultiLine(comp);
		sbDesc.setText(modelmgr.getStringValue("description"));
		sbDesc.addListener(SWT.Modify, getListener(sbDesc, "description"));
		
		sbServerUrl = createGvTextField(comp, "StreamBase Server URI: ", modelmgr, SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName);
		sbUsername = createGvTextField(comp, "Username: ", modelmgr, SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName);
		sbPassword = createGvPasswordField(comp, "Password: ", modelmgr, SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName);
		createTrustStoreField(comp);

		trustStorePassword = createGvPasswordField(comp, "SSL Trust Store Password: ", modelmgr, SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.localName);
		createKeyStoreField(comp);
		keyStorePassword = createGvPasswordField(comp, "SSL Key Store Password: ", modelmgr, SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.localName);
		keyPassword = createGvPasswordField(comp, "SSL Key Password: ", modelmgr, SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.localName);
		
		validateFields();
		
		comp.pack();
	}

	private void createTrustStoreField(Composite parent) {
		trustStore = createGvTextField(parent, "SSL Trust Store File: ", modelmgr, SBConstants.CHANNEL_PROPERTY_TRUST_STORE.localName);
		trustStore.getMasterComposite().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		trustStore.getMasterComposite().setLayout(PanelUiUtil.getCompactGridLayout(3, false));
		trustStoreBrowse = PanelUiUtil.createBrowsePushButton(trustStore.getMasterComposite(), trustStore.getGvText());
		trustStoreBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), trustStore.getMasterComposite(), new String[]{"*"}, (Text) trustStore.getField()));
	}
	
	private void createKeyStoreField(Composite parent) {
		keyStore = createGvTextField(parent, "SSL Key Store File: ", modelmgr, SBConstants.CHANNEL_PROPERTY_KEY_STORE.localName);
		keyStore.getMasterComposite().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		keyStore.getMasterComposite().setLayout(PanelUiUtil.getCompactGridLayout(3, false));
		keyStoreBrowse = PanelUiUtil.createBrowsePushButton(keyStore.getMasterComposite(), keyStore.getGvText());
		keyStoreBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), keyStore.getMasterComposite(), new String[]{}, (Text) keyStore.getField()));
	}

	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					boolean updated = modelmgr.updateStringValue(key, ((Text)field).getText());
					validateFields();
					validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
				} 
			}
		};
		return listener;
	}
	
	@Override
	public boolean validateFields() {
		boolean valid = true;
	//	valid &= PanelUiUtil.validateTextField(sbUsername, true, false);
		return valid;
	}
	
	@Override
	public String getName() {
		return ("Configuration");
	}
}
