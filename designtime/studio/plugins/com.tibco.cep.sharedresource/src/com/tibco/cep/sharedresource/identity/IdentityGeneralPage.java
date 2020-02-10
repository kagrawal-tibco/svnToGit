package com.tibco.cep.sharedresource.identity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 28, 2009 1:13:01 PM
 */
@SuppressWarnings({"unused"})
public class IdentityGeneralPage extends AbstractSharedResourceEditorPage {

	private IdentityConfigModelMgr modelmgr;
	private Text tDesc;
	private GvField tgFileUrl, tgFilePassword, tgKeyPassword, tgPassword, cgFileType, cgId,tgUsername,tgCertUrl,tgKeyUrl;
	private StackLayout idTabLayout;
	private Control idTabs[];
	private Composite idComp;
	private Button bCertUrlBrowse, bKeyUrlBrowse, bFileUrlBrowse;
	
	private static String ID_TYPE_CERT_KEY = "Certificate/Private Key";
	private static String ID_TYPE_FILE = "Identity File";
	private static String ID_TYPE_USERNAME = "Username/Password";
	
	private static String ID_USERNAME = "usernamePassword";
	private static String ID_KEY = "certPlusKeyURL";
	private static String ID_FILE = "url";
	
	private static int ID_TAB_EMPTY = 0;
	private static int ID_TAB_USERNAME = 1;
	private static int ID_TAB_KEY = 2;
	private static int ID_TAB_FILE = 3;
	
	private Map<String, String> typeMap, idMap;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

	public IdentityGeneralPage(IdentityConfigEditor editor, Composite parent, IdentityConfigModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;
		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		initialiseGvFieldTypeMap();
//		createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
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
        gvTypeFieldMap.put("url", "String");
        gvTypeFieldMap.put("passPhrase", "Password");
        gvTypeFieldMap.put("password", "Password");
        gvTypeFieldMap.put("fileType", "String");
        gvTypeFieldMap.put("objectType", "String");
        gvTypeFieldMap.put("username", "String");
        gvTypeFieldMap.put("certUrl", "String");
        gvTypeFieldMap.put("privateKeyUrl", "String");
 	}
	
	public void validateFieldsForGv() {
		validateField((Text)tgFileUrl.getGvText(),gvTypeFieldMap.get("url"), modelmgr.getStringValue("url"), "url", modelmgr.getProject().getName());
		validateField((Text)tgKeyPassword.getGvText(),gvTypeFieldMap.get("passPhrase"), modelmgr.getStringValue("passPhrase"), "passPhrase", modelmgr.getProject().getName());
		validateField((Text)tgPassword.getGvText(),gvTypeFieldMap.get("password"), modelmgr.getStringValue("password"), "password", modelmgr.getProject().getName());
		validateField((Text)cgFileType.getGvText(),gvTypeFieldMap.get("fileType"), modelmgr.getStringValue("fileType"), "fileType", modelmgr.getProject().getName());
		validateField((Text)cgId.getGvText(),gvTypeFieldMap.get("objectType"), modelmgr.getStringValue("objectType"), "objectType", modelmgr.getProject().getName());
		validateField((Text)tgUsername.getGvText(),gvTypeFieldMap.get("username"), modelmgr.getStringValue("username"), "username", modelmgr.getProject().getName());
		validateField((Text)tgCertUrl.getGvText(),gvTypeFieldMap.get("certUrl"), modelmgr.getStringValue("certUrl"), "certUrl", modelmgr.getProject().getName());
		validateField((Text)tgKeyUrl.getGvText(),gvTypeFieldMap.get("privateKeyUrl"), modelmgr.getStringValue("privateKeyUrl"), "privateKeyUrl", modelmgr.getProject().getName());
	}
	
	private void createGeneralSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		Label lDesc = PanelUiUtil.createLabel(comp, "Description: ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.setText(modelmgr.getStringValue("description"));
		tDesc.addListener(SWT.Modify, getListener(tDesc, "description"));
		
		initObjectTypeMap();
		//Label lId = PanelUiUtil.createLabel(comp, "Type*: ");
		String idTypes[] = { ID_TYPE_CERT_KEY, ID_TYPE_FILE, ID_TYPE_USERNAME };
		//cId = PanelUiUtil.createComboBox(comp, idTypes);
		//cId.setText(idMap.get(modelmgr.getStringValue("objectType")));
		//cId.addListener(SWT.Selection, getIdChangeListener(cId));
		cgId = createGvComboField(comp, "Type*: ", modelmgr, idTypes,
				"objectType");
		if (!cgId.isGvMode() && modelmgr.getStringValue("objectType") != null) {
			String typeVal = idMap.get(modelmgr.getStringValue("objectType"));
			for (int index = 0; index < idTypes.length; index++) {
				if (idTypes[index].equals(typeVal)) {
					((Combo) cgId.getField()).select(index);
				}
			}
		}
		
		createIdTabs(parent);
		cgId.getField().notifyListeners(SWT.Selection, new Event());
		
		validateFields();
		
		comp.pack();
	}

	private void initObjectTypeMap() {
		typeMap = new LinkedHashMap<String, String>();
		typeMap.put(ID_TYPE_FILE, ID_FILE);
		typeMap.put(ID_TYPE_CERT_KEY, ID_KEY);
		typeMap.put(ID_TYPE_USERNAME, ID_USERNAME);
		
		idMap = new LinkedHashMap<String, String>();
		idMap.put(ID_FILE, ID_TYPE_FILE);
		idMap.put(ID_KEY, ID_TYPE_CERT_KEY);
		idMap.put(ID_USERNAME, ID_TYPE_USERNAME);
	}

	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					boolean updated = modelmgr.updateStringValue(key, ((Text)field).getText());
					if (updated) {
						if (key.equals("passPhrase")) {
							if (field == tgFilePassword.getField()) {
								tgKeyPassword.setFieldModeValue(modelmgr.getStringValue(key));
							} else if (field == tgFilePassword.getGvText()) {
								tgKeyPassword.setGvModeValue(modelmgr.getStringValue(key));
							} else if (field == tgKeyPassword.getField()) {
								tgFilePassword.setFieldModeValue(modelmgr.getStringValue(key));
							} else if (field == tgKeyPassword.getGvText()) {
								tgFilePassword.setGvModeValue(modelmgr.getStringValue(key));
							}
						}
					}
					if(modelmgr.getProject() !=null){
						validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
					}
				} else if (field instanceof Combo) {
					if (!key.equals("objectType")) {
						modelmgr.updateStringValue(key,
								((Combo) field).getText());
					}

				}
				if (key.equals("objectType")) {
					String idType = "";
					if (cgId.isGvMode()) {
						modelmgr.updateStringValue("objectType",
								cgId.getGvValue());
						idType = idMap.get(cgId.getGvDefinedValue(modelmgr
								.getProject()));
					} else {
						idType = cgId.getFieldValue();
						modelmgr.updateStringValue("objectType",
								typeMap.get(idType));
					}

					if (idType == null) {
						setIdCompStackTopControl(ID_TAB_EMPTY);
					} else if (idType.equals(ID_TYPE_FILE)) {
						setIdCompStackTopControl(ID_TAB_FILE);
						tgFilePassword.initializeDropTarget();
					} else if (idType.equals(ID_TYPE_CERT_KEY)) {
						setIdCompStackTopControl(ID_TAB_KEY);
						tgKeyPassword.initializeDropTarget();
					} else if (idType.equals(ID_TYPE_USERNAME)) {
						setIdCompStackTopControl(ID_TAB_USERNAME);
					} else {
						setIdCompStackTopControl(ID_TAB_EMPTY);
					}
					validateFields();
				}
			}
		};
		return listener;
	}
	
	private void createIdTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		idComp = new Composite(comp, SWT.NONE);
		idComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		idTabLayout = new StackLayout();
		idComp.setLayout(idTabLayout);
	    
	    idTabs = new Control[4];
	    idTabs[ID_TAB_EMPTY] = createEmptyTab();
	    idTabs[ID_TAB_FILE] = createFileTab();
	    idTabs[ID_TAB_KEY] = createKeyTab();
	    idTabs[ID_TAB_USERNAME] = createUsernameTab();
        idTabLayout.topControl = idTabs[ID_TAB_EMPTY]; 
        
		idComp.pack();
		comp.pack();
		
	}

	private Control createFileTab() {
		Composite comp = new Composite(idComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			
		Label lFileUrl = PanelUiUtil.createLabel(comp, "URL*: ");
		Composite urlComp = new Composite(comp, SWT.NONE);
		urlComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		urlComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tgFileUrl = GvUiUtil.createTextGv(urlComp, modelmgr.getStringValue("url"));
		setGvFieldListeners(tgFileUrl, SWT.Modify, "url");
		bFileUrlBrowse = PanelUiUtil.createBrowsePushButton(urlComp, tgFileUrl.getGvText());
		bFileUrlBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), urlComp, null, (Text)tgFileUrl.getField()));
		tgFileUrl.getGvToggleButton().addListener(SWT.Selection, getFileUrlBrowseEnablement(tgFileUrl));
		urlComp.pack();
		
		//Label lFileType = PanelUiUtil.createLabel(comp, "File Type: ");
		String fileTypes[] = new String[]{"Entrust", "JCEKS", "JKS", "PEM", "PKCS12", "SSO"}; 
		//cFileType = PanelUiUtil.createComboBox(comp, fileTypes);
		//cFileType.setText(modelmgr.getStringValue("fileType"));
		//cFileType.addListener(SWT.Modify, getListener(cFileType, "fileType"));
		cgFileType = createGvComboField(comp, "File Type: ", modelmgr,
				fileTypes, "fileType");
		
		tgFilePassword = createGvPasswordField(comp, "Password*: ", modelmgr, "passPhrase");
		/*
		tFilePassword = PanelUiUtil.createTextPassword(comp);
		tFilePassword.setText(modelmgr.getStringValue("passPhrase"));
		tFilePassword.addListener(SWT.Modify, getListener(tFilePassword, "passPhrase"));
		*/
		
		comp.pack();
		return comp;
	}
	
	private Control createEmptyTab() {
		Composite comp = new Composite(idComp, SWT.NONE);
		comp.pack();
		return comp;
	}

	private Listener getFileUrlBrowseEnablement(final GvField tgFileUrl) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				bFileUrlBrowse.setEnabled(!tgFileUrl.isGvMode());
			}
		};
		return listener;
	}

	private Control createKeyTab() {
		Composite comp = new Composite(idComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lCertUrl = PanelUiUtil.createLabel(comp, "Certificate URL*: ");
		Composite urlComp1 = new Composite(comp, SWT.NONE);
		urlComp1.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		urlComp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tgCertUrl = GvUiUtil.createTextGv(urlComp1, modelmgr.getStringValue("certUrl"));
		setGvFieldListeners(tgCertUrl, SWT.Modify, "certUrl");
		bCertUrlBrowse = PanelUiUtil.createBrowsePushButton(urlComp1, tgCertUrl.getGvText());
		bCertUrlBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), urlComp1, null, (Text)tgCertUrl.getField()));
		tgCertUrl.getGvToggleButton().addListener(SWT.Selection, getFileUrlBrowseEnablement(tgCertUrl));
		urlComp1.pack();
		
		
		Label lKeyUrl = PanelUiUtil.createLabel(comp, "Key URL*: ");
		Composite urlComp2 = new Composite(comp, SWT.NONE);
		urlComp2.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		urlComp2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tgKeyUrl = GvUiUtil.createTextGv(urlComp2, modelmgr.getStringValue("privateKeyUrl"));
		setGvFieldListeners(tgKeyUrl, SWT.Modify, "privateKeyUrl");
		bKeyUrlBrowse = PanelUiUtil.createBrowsePushButton(urlComp2, tgKeyUrl.getGvText());
		bKeyUrlBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), urlComp2, null, (Text)tgKeyUrl.getField()));
		tgKeyUrl.getGvToggleButton().addListener(SWT.Selection, getFileUrlBrowseEnablement(tgKeyUrl));
		urlComp2.pack();
		
		tgKeyPassword = createGvPasswordField(comp, "Key Password*: ", modelmgr, "passPhrase");
		/*
		tKeyPassword = PanelUiUtil.createTextPassword(comp);
		tKeyPassword.setText(modelmgr.getStringValue("passPhrase"));
		tKeyPassword.addListener(SWT.Modify, getListener(tKeyPassword, "passPhrase"));
		*/
		
		comp.pack();
		return comp;
	}

	private Control createUsernameTab() {
		Composite comp = new Composite(idComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		tgUsername = createGvTextField(comp, "Username*: ",modelmgr, "username");
		
	//	Label lUsername = PanelUiUtil.createLabel(comp, "Username*: ");
		//tUsername = PanelUiUtil.createText(comp);
	//	tUsername.setText(modelmgr.getStringValue("username"));	
	//	tUsername.addListener(SWT.Modify, getListener(tUsername, "username"));
	//	PanelUiUtil.setDropTarget(tUsername);
		
		tgPassword = createGvPasswordField(comp, "Password*: ", modelmgr, "password");
		/*
		tPassword = PanelUiUtil.createTextPassword(comp);
		tPassword.setText(modelmgr.getStringValue("password"));
		tPassword.addListener(SWT.Modify, getListener(tPassword, "password"));
		*/
		
		comp.pack();
		return comp;
	}

	
	private Listener getIdChangeListener(final Combo cId) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String idType = cId.getText();
				if (idType.equals(ID_TYPE_FILE)) {
					setIdCompStackTopControl(ID_TAB_FILE);
					tgFilePassword.initializeDropTarget();
				} else if (idType.equals(ID_TYPE_CERT_KEY)) {
					setIdCompStackTopControl(ID_TAB_KEY);
					tgKeyPassword.initializeDropTarget();
				} else if (idType.equals(ID_TYPE_USERNAME)) {
					setIdCompStackTopControl(ID_TAB_USERNAME);
				} else {
					setIdCompStackTopControl(ID_TAB_EMPTY);
				}
				modelmgr.updateStringValue("objectType", typeMap.get(idType));
				validateFields();
			}
		};
		return listener;
	}

	private void setIdCompStackTopControl(int top) {
		idTabLayout.topControl = idTabs[top];
		idComp.layout();
	}
	
	@Override
	public boolean validateFields() {
		boolean valid = true;
//		valid &= PanelUiUtil.validateTextField(tCertUrl, true, false);
///		valid &= PanelUiUtil.validateTextField(tKeyUrl, true, false);
//		valid &= GvUtil.validateGvField(tgKeyPassword, true, false);
//		valid &= GvUtil.validateGvField(tgFileUrl, true, false);
//		valid &= GvUtil.validateGvField(tgFilePassword, true, false);
//		valid &= PanelUiUtil.validateTextField(tUsername, true, false);
//		valid &= GvUtil.validateGvField(tgPassword, true, false);
		return valid;
	}
	
	@Override
	public String getName() {
		return ("Configuration");
	}
}
