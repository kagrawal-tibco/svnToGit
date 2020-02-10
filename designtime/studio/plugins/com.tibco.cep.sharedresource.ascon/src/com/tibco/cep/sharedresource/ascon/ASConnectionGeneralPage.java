package com.tibco.cep.sharedresource.ascon;

import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_AUTH_CREDENTIAL_USERPWD;
import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_AUTH_CREDENTIAL_X509V3;
import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_ROLE_CONTROLLER;
import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_ROLE_REQUESTER;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_CREDENTIAL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_DOMAIN;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_PASSWORD;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_KEY_FILE;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_PRIVATE_KEY;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_USERNAME;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_DESCRIPTION;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_DISCOVERY_URL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_ENABLE_SECURITY;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_ID_PASSWORD;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_LISTEN_URL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_REMOTE_LISTEN_URL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_MEMBER_NAME;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_METASPACE_NAME;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_POLICY_FILE;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_SECURITY_ROLE;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_TOKEN_FILE;

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
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author George Wang (nwang@tibco-support.com)
@date Jan 21, 2014
*/

public class ASConnectionGeneralPage extends AbstractSharedResourceEditorPage {

    private ASConnectionModelMgr modelMgr;

    // security panel
    private Composite securityContainer;
    private StackLayout securityContainerLayout;
    private Composite securityEnabledContainer;

    // role panel
    private Composite roleContainer;
    private StackLayout roleContainerLayout;
    private Composite roleControllerContainer;
    private Composite roleRequesterContainer;

    // authentication panel
    private Composite authContainer;
    private StackLayout authContainerLayout;
    private Composite authUserPwdContainer;
    private Composite authX509V3Container;
    
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

	private GvField gvMetaspaceName,gvMemberName,gvDiscoveryUrl,gvListenUrl,gvRemoteListenUrl,gvEnableSecurity,gvAuthDomain,gvAuthUserName,gvAuthPassword,gvAuthCredential,gvSecurityRole,gvPassword,gvAuthPrivateKey;

    public ASConnectionGeneralPage(ASConnectionEditor editor, Composite parent, ASConnectionModelMgr modelmgr) {
        this.editor = editor;
        this.editorParent = parent;
        this.modelMgr = modelmgr;
        if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
        //createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
        initialiseGvFieldTypeMap();
        SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

        Composite sConfig = sectionProvider.createSectionPart("Configuration", true);
        createConfigSectionContents(sConfig);

        managedForm.getForm().reflow(true);
        if(modelmgr.getProject() !=null){
        	validateFieldsForGv();
        }
        parent.layout();
        parent.pack();
    }
    
    private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put(AS_CONFIG_METASPACE_NAME, "String");
        gvTypeFieldMap.put(AS_CONFIG_MEMBER_NAME, "String");
        gvTypeFieldMap.put(AS_CONFIG_DISCOVERY_URL, "String");
        gvTypeFieldMap.put(AS_CONFIG_LISTEN_URL, "String");
        gvTypeFieldMap.put(AS_CONFIG_REMOTE_LISTEN_URL, "String");
        gvTypeFieldMap.put(AS_CONFIG_ID_PASSWORD, "Password"); 
        gvTypeFieldMap.put(AS_CONFIG_SECURITY_ROLE, "String");
        gvTypeFieldMap.put(AS_CONFIG_ENABLE_SECURITY, "boolean");
        gvTypeFieldMap.put(AS_CONFIG_AUTH_CREDENTIAL, "String");
        gvTypeFieldMap.put(AS_CONFIG_AUTH_DOMAIN, "String");
        gvTypeFieldMap.put(AS_CONFIG_AUTH_USERNAME, "String");
        gvTypeFieldMap.put(AS_CONFIG_AUTH_PASSWORD, "Password");
        gvTypeFieldMap.put(AS_CONFIG_AUTH_PRIVATE_KEY, "Password");
  	}
    
    public void validateFieldsForGv() {
		validateField((Text)gvMemberName.getGvText(),gvTypeFieldMap.get(AS_CONFIG_MEMBER_NAME), modelMgr.getStringValue(AS_CONFIG_MEMBER_NAME), AS_CONFIG_MEMBER_NAME, modelMgr.getProject().getName());
		validateField((Text)gvMetaspaceName.getGvText(),gvTypeFieldMap.get(AS_CONFIG_METASPACE_NAME), modelMgr.getStringValue(AS_CONFIG_METASPACE_NAME), AS_CONFIG_METASPACE_NAME, modelMgr.getProject().getName());
		validateField((Text)gvDiscoveryUrl.getGvText(),gvTypeFieldMap.get(AS_CONFIG_DISCOVERY_URL), modelMgr.getStringValue(AS_CONFIG_DISCOVERY_URL), AS_CONFIG_DISCOVERY_URL, modelMgr.getProject().getName());
		validateField((Text)gvListenUrl.getGvText(),gvTypeFieldMap.get(AS_CONFIG_LISTEN_URL), modelMgr.getStringValue(AS_CONFIG_LISTEN_URL), AS_CONFIG_LISTEN_URL, modelMgr.getProject().getName());
		validateField((Text)gvRemoteListenUrl.getGvText(),gvTypeFieldMap.get(AS_CONFIG_REMOTE_LISTEN_URL), modelMgr.getStringValue(AS_CONFIG_REMOTE_LISTEN_URL), AS_CONFIG_REMOTE_LISTEN_URL, modelMgr.getProject().getName());
		validateField((Text)gvPassword.getGvText(),gvTypeFieldMap.get(AS_CONFIG_ID_PASSWORD), modelMgr.getStringValue(AS_CONFIG_ID_PASSWORD), AS_CONFIG_ID_PASSWORD, modelMgr.getProject().getName());
		validateField((Text)gvSecurityRole.getGvText(),gvTypeFieldMap.get(AS_CONFIG_SECURITY_ROLE), modelMgr.getStringValue(AS_CONFIG_SECURITY_ROLE), AS_CONFIG_SECURITY_ROLE, modelMgr.getProject().getName());
		validateField((Text)gvEnableSecurity.getGvText(),gvTypeFieldMap.get(AS_CONFIG_ENABLE_SECURITY), modelMgr.getStringValue(AS_CONFIG_ENABLE_SECURITY), AS_CONFIG_ENABLE_SECURITY, modelMgr.getProject().getName());
		validateField((Text)gvAuthCredential.getGvText(),gvTypeFieldMap.get(AS_CONFIG_AUTH_CREDENTIAL), modelMgr.getStringValue(AS_CONFIG_AUTH_CREDENTIAL), AS_CONFIG_AUTH_CREDENTIAL, modelMgr.getProject().getName());
		validateField((Text)gvAuthDomain.getGvText(),gvTypeFieldMap.get(AS_CONFIG_AUTH_DOMAIN), modelMgr.getStringValue(AS_CONFIG_AUTH_DOMAIN), AS_CONFIG_AUTH_DOMAIN, modelMgr.getProject().getName());
		validateField((Text)gvAuthUserName.getGvText(),gvTypeFieldMap.get(AS_CONFIG_AUTH_USERNAME), modelMgr.getStringValue(AS_CONFIG_AUTH_USERNAME), AS_CONFIG_AUTH_USERNAME, modelMgr.getProject().getName());
		validateField((Text)gvAuthPassword.getGvText(),gvTypeFieldMap.get(AS_CONFIG_AUTH_PASSWORD), modelMgr.getStringValue(AS_CONFIG_AUTH_PASSWORD), AS_CONFIG_AUTH_PASSWORD, modelMgr.getProject().getName());
		validateField((Text)gvAuthPrivateKey.getGvText(),gvTypeFieldMap.get(AS_CONFIG_AUTH_PRIVATE_KEY), modelMgr.getStringValue(AS_CONFIG_AUTH_PRIVATE_KEY), AS_CONFIG_AUTH_PRIVATE_KEY, modelMgr.getProject().getName());
	}

	private void createConfigSectionContents(Composite parent) {
	    Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		container.setLayout(new GridLayout(2, false));

		/**
		 * Desc., Metaspace Name, Member Name, Discovery URL, Listen URL, Enable Security
		 */
		Label lDesc = PanelUiUtil.createLabel(container, AS_CONFIG_DESCRIPTION + ": ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));		
		Text tDesc = PanelUiUtil.createTextMultiLine(container);
		tDesc.setText(modelMgr.getStringValue(AS_CONFIG_DESCRIPTION));
		tDesc.addListener(SWT.Modify, getListener(tDesc, AS_CONFIG_DESCRIPTION));

		gvMetaspaceName = createGvTextField(container, AS_CONFIG_METASPACE_NAME + ": ", modelMgr, AS_CONFIG_METASPACE_NAME);
		gvMemberName = createGvTextField(container, AS_CONFIG_MEMBER_NAME + ": ", modelMgr, AS_CONFIG_MEMBER_NAME);
		gvDiscoveryUrl = createGvTextField(container, AS_CONFIG_DISCOVERY_URL + ": ", modelMgr, AS_CONFIG_DISCOVERY_URL);
		gvListenUrl= createGvTextField(container, AS_CONFIG_LISTEN_URL + ": ", modelMgr, AS_CONFIG_LISTEN_URL);
		gvRemoteListenUrl = createGvTextField(container, AS_CONFIG_REMOTE_LISTEN_URL + ": ", modelMgr, AS_CONFIG_REMOTE_LISTEN_URL);
		gvEnableSecurity = createGvCheckboxField(container, AS_CONFIG_ENABLE_SECURITY + ":", modelMgr, AS_CONFIG_ENABLE_SECURITY);


		/**
		 *  Security Panel, two parts:
		 *  1. Security Enabled Panel
		 *  2. null
		 */
		securityContainer = new Composite(container, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		securityContainer.setLayoutData(gridData);
		securityContainerLayout = new StackLayout();
        securityContainer.setLayout(securityContainerLayout);

    		/**
    		 * Part 1: Security Enabled Panel
    		 */
    		securityEnabledContainer = new Composite(securityContainer, SWT.NONE);
    		GridLayout gridLayout = new GridLayout(2, false);
    		gridLayout.marginWidth = 0;
    		securityEnabledContainer.setLayout(gridLayout);

    		// role combo
            String[] securityRols = { AS_SECURITY_ROLE_CONTROLLER, AS_SECURITY_ROLE_REQUESTER };
            gvSecurityRole = createGvComboField(securityEnabledContainer, AS_CONFIG_SECURITY_ROLE + ": ", modelMgr, securityRols, AS_CONFIG_SECURITY_ROLE);
            // id pwd
            gvPassword= createGvPasswordField(securityEnabledContainer, AS_CONFIG_ID_PASSWORD + ": ", modelMgr, AS_CONFIG_ID_PASSWORD);

            // role container
            roleContainer = new Composite(securityEnabledContainer, SWT.NONE);
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.horizontalSpan = 2;
            roleContainer.setLayoutData(gridData);
            roleContainerLayout = new StackLayout();
            roleContainer.setLayout(roleContainerLayout);

                /**
                 *  Part 1: Controller Panel
                 */
                roleControllerContainer = new Composite(roleContainer, SWT.NONE);
                gridLayout = new GridLayout(2, false);
                gridLayout.marginWidth = 0;
                roleControllerContainer.setLayout(gridLayout);
                // policy file
                createFileField(roleControllerContainer, AS_CONFIG_POLICY_FILE, AS_CONFIG_POLICY_FILE + ":             ");

                /**
                 *  Part 2: Requester Panel
                 */
                roleRequesterContainer = new Composite(roleContainer, SWT.NONE);
                gridLayout = new GridLayout(2, false);
                gridLayout.marginWidth = 0;
                roleRequesterContainer.setLayout(gridLayout);
                // token file
                createFileField(roleRequesterContainer, AS_CONFIG_TOKEN_FILE, AS_CONFIG_TOKEN_FILE + ":            ");

                // Credential Combo
                String[] authCredentials = new String[] { AS_SECURITY_AUTH_CREDENTIAL_USERPWD, AS_SECURITY_AUTH_CREDENTIAL_X509V3 };
                gvAuthCredential = createGvComboField(roleRequesterContainer, AS_CONFIG_AUTH_CREDENTIAL + ":", modelMgr, authCredentials, AS_CONFIG_AUTH_CREDENTIAL);

                    /**
                     * Authentication Credential Panel, two parts:
                     * 1. UserPwd Panel
                     * 2. X509V3 Panel
                     */
                    authContainer = new Composite(roleRequesterContainer, SWT.NONE);
                    gridData = new GridData(GridData.FILL_HORIZONTAL);
                    gridData.horizontalSpan = 2;
                    authContainer.setLayoutData(gridData);
                    authContainerLayout = new StackLayout();
                    authContainer.setLayout(authContainerLayout);

                        /**
                         * Part 1: UserPwd Panel
                         */
                        authUserPwdContainer = new Composite(authContainer, SWT.NONE);
                        gridLayout = new GridLayout(2, false);
                        gridLayout.marginWidth = 0;
                        authUserPwdContainer.setLayout(gridLayout);
                        gvAuthDomain = createGvTextField(authUserPwdContainer,AS_CONFIG_AUTH_DOMAIN + ":              " , modelMgr, AS_CONFIG_AUTH_DOMAIN);
                        gvAuthUserName = createGvTextField(authUserPwdContainer,  AS_CONFIG_AUTH_USERNAME + ":" , modelMgr, AS_CONFIG_AUTH_USERNAME);
                        gvAuthPassword = createGvPasswordField(authUserPwdContainer,AS_CONFIG_AUTH_PASSWORD + ":" , modelMgr, AS_CONFIG_AUTH_PASSWORD);

                        /**
                         * Part 2: X509V3 Panel
                         */
                        authX509V3Container = new Composite(authContainer, SWT.NONE);
                        gridLayout = new GridLayout(2, false);
                        gridLayout.marginWidth = 0;
                        authX509V3Container.setLayout(gridLayout);
                        createFileField(authX509V3Container, AS_CONFIG_AUTH_KEY_FILE, AS_CONFIG_AUTH_KEY_FILE + ":  ");                       
                        gvAuthPrivateKey = createGvPasswordField(authX509V3Container, AS_CONFIG_AUTH_PRIVATE_KEY + ":            ", modelMgr, AS_CONFIG_AUTH_PRIVATE_KEY);

        container.pack();
        update();
       
	}
	
    @Override
	public boolean validateFields() {
		return true;
	}

	@Override
	public String getName() {
		return ("Configuration");
	}

    private void createFileField(Composite compoiste, String key, String label) {
        PanelUiUtil.createLabel(compoiste, label);
        Composite fileFieldContainer = new Composite(compoiste, 0);
        fileFieldContainer.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
        fileFieldContainer.setLayoutData(new GridData(768));
        Text fileText = PanelUiUtil.createText(fileFieldContainer);
        fileText.addListener(24, getListener(fileText, key));
        Button filebutton = PanelUiUtil.createBrowsePushButton(fileFieldContainer, fileText);
        filebutton.addListener(13, PanelUiUtil.getFileBrowseListener(modelMgr.getProject(), compoiste, null, fileText));
    }

    private void update() {
    	
    	String asConfigEnableSecurity = modelMgr.getStringValue(AS_CONFIG_ENABLE_SECURITY);
    	
    	if(asConfigEnableSecurity.contains("%%")){
    		if(GvUtil.isGlobalVar(asConfigEnableSecurity)){
    			asConfigEnableSecurity=GvUtil.getGvDefinedValue(modelMgr.getProject(),asConfigEnableSecurity);
			}
    	}
    	
        if (!Boolean.valueOf(asConfigEnableSecurity)) {
            securityContainerLayout.topControl = null;
            
        } else {
            securityContainerLayout.topControl = securityEnabledContainer;
            if(GvUtil.isGlobalVar(modelMgr.getStringValue(AS_CONFIG_SECURITY_ROLE))){
				String gvVal=GvUtil.getGvDefinedValue(modelMgr.getProject(),modelMgr.getStringValue(AS_CONFIG_SECURITY_ROLE));
				if (AS_SECURITY_ROLE_CONTROLLER.equals(gvVal)) {
            		roleContainerLayout.topControl = roleControllerContainer;
            	} else {
            		roleContainerLayout.topControl = roleRequesterContainer;
            	}
			}
            else{
            	if (AS_SECURITY_ROLE_CONTROLLER.equals(modelMgr.getStringValue(AS_CONFIG_SECURITY_ROLE))) {
            		roleContainerLayout.topControl = roleControllerContainer;
            	} else {
            		roleContainerLayout.topControl = roleRequesterContainer;
            	}
            }
            roleContainer.layout();
            
            if(GvUtil.isGlobalVar(modelMgr.getStringValue(AS_CONFIG_AUTH_CREDENTIAL))){
				String gvVal=GvUtil.getGvDefinedValue(modelMgr.getProject(),modelMgr.getStringValue(AS_CONFIG_AUTH_CREDENTIAL));
				if (AS_SECURITY_AUTH_CREDENTIAL_USERPWD.equals(gvVal)) {
					authContainerLayout.topControl = authUserPwdContainer;
            	} else {
            		authContainerLayout.topControl = authX509V3Container;
            	}
			}
            else{
            	if (AS_SECURITY_AUTH_CREDENTIAL_USERPWD.equals(modelMgr.getStringValue(AS_CONFIG_AUTH_CREDENTIAL))) {
            		authContainerLayout.topControl = authUserPwdContainer;
            	} else {
            		authContainerLayout.topControl = authX509V3Container;
            	}
            }
            authContainer.layout();
        }
        securityContainer.layout();
        
        
    }

	@Override
    public Listener getListener(Control field, String key) {
	    updateUI(field, key);
        return new Handler(field, key);
    }

	private void updateUI(Control field, String key) {
        // update UI: set value from model
        String value = modelMgr.getStringValue(key);
        if (field instanceof Text) {
            ((Text) field).setText(value);
        } else {
            if (field instanceof Button) {
                ((Button) field).setSelection(Boolean.valueOf(value));
            } else if (field instanceof Combo) {
                Combo combo = (Combo) field;
                String[] items = combo.getItems();
                for (int i = 0; i < items.length; i++) {
                    if (items[i].equals(value)) {
                        combo.select(i);
                        break;
                    }
                }
            }
        }
	}

	private class Handler implements Listener {
	    private Control field;
	    private String key;

	    private Handler(Control field, String key) {
	        this.field = field;
	        this.key = key;
	    }

        @Override
        public void handleEvent(Event event) {
            if (field instanceof Text) {
                modelMgr.updateStringValue(key, ((Text) field).getText());
                String val=((Text)field).getText();
				String gvVal=null;
				if(GvUtil.isGlobalVar(val)){
					gvVal=GvUtil.getGvDefinedValue(modelMgr.getProject(),val);
				}
				if("EnableSecurity".equalsIgnoreCase(key)){
					if(gvVal!=null){
						if (!Boolean.valueOf(gvVal)) {
				            securityContainerLayout.topControl = null;
						} else {
				            securityContainerLayout.topControl = securityEnabledContainer;
				            if (AS_SECURITY_ROLE_CONTROLLER.equals(modelMgr.getStringValue(AS_CONFIG_SECURITY_ROLE))) {
				                roleContainerLayout.topControl = roleControllerContainer;
				            } else {
				                roleContainerLayout.topControl = roleRequesterContainer;
				            }
				            roleContainer.layout();
				            if (AS_SECURITY_AUTH_CREDENTIAL_USERPWD.equals(modelMgr.getStringValue(AS_CONFIG_AUTH_CREDENTIAL))) {
				                authContainerLayout.topControl = authUserPwdContainer;
				            } else {
				                authContainerLayout.topControl = authX509V3Container;
				            }
				            authContainer.layout();
				        }
				        securityContainer.layout();
					}
				}
				update();
				validateField((Text)field,gvTypeFieldMap.get(key), modelMgr.getStringValue(key), key, modelMgr.getProject().getName());
            } else {
                String newValue = null;
                if (field instanceof Button){
                    newValue = String.valueOf(((Button) field).getSelection());
                } else if (field instanceof Combo) {
                    Combo combo = (Combo) field;
                    int selIndex = combo.getSelectionIndex();
                    newValue = combo.getItem(selIndex);
                }
                modelMgr.updateStringValue(key, newValue);
                update();
            }
        }
    }

}
