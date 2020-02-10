package com.tibco.cep.sharedresource.jms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.osgi.framework.Bundle;

import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.driver.jms.JMSChannelConfig;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.sharedresource.SharedResourcePlugin;
import com.tibco.cep.sharedresource.ssl.SslConfigurationJmsDialog;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.thirdparty.DelegateActivator;

/*
@author ssailapp
@date Dec 31, 2009 12:05:37 AM
 */
@SuppressWarnings({"unused"})
public class JmsConGeneralPage extends AbstractSharedResourceEditorPage {

    private static final String CLASS_JMS_CONNECTION = "javax.jms.Connection";
    private static final String CLASS_JMS_CONNECTION_FACTORY = "javax.jms.ConnectionFactory";
    private static final String CLASS_JMS_XA_CONNECTION_FACTORY = "javax.jms.XAConnectionFactory";
    private static final String CLASS_TIBJMS_CONNECTION_FACTORY = "com.tibco.tibjms.TibjmsConnectionFactory";
    private static final String CLASS_TIBJMS_XA_CONNECTION_FACTORY = "com.tibco.tibjms.TibjmsXAConnectionFactory";

    private Class<?> clsConnection, clsConnectionFactory, clsXaConnectionFactory;

    private static int TAB_CON_FACTORY_JNDI = 0;
    private static int TAB_CON_FACTORY_XA = 1;

    private static int TAB_JNDI_CUSTOM = 0;
    private static int TAB_JNDI_SHARED = 1;

    private JmsConModelMgr modelmgr;

    private StackLayout factTabLayout;
    private Control factTabs[];
    private Composite factComp;

    private StackLayout jndiTabLayout;
    private Control jndiTabs[];
    private Composite jndiComp;

    //private Combo cFactory;
    private Text tDesc, tTimeout;
	GvField tJndiConfig;
    private GvField tgJndiFactory, tgUsername,tgJndiContextUrl, tgJndiUsername, tgJndiPassword,tgClientId, tgPassword, tgAutoGenClientId, tgFactorySslPassword, tgProviderUrl,bgSsl,bUseJndiFactory,bUseSharedJndi;
    private Button bSslConfig, bTestConnection, bJndiConfigBrowse;
    private Label lFactorySslPassword;
    private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

    public JmsConGeneralPage(JmsConEditor editor, Composite parent, JmsConModelMgr modelmgr) {
        this.editor = editor;
        this.editorParent = parent;
        this.modelmgr = modelmgr;

        if (!editor.isEnabled() && fImage == null) {
            fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
        }
        initialiseGvFieldTypeMap();
        createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
//		createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
        SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

        Composite sConfig = sectionProvider.createSectionPart("Configuration", false);
        createConfigSectionContents(sConfig);
        if(modelmgr.getProject() !=null){
        	validateFieldsForGv();
        }
        managedForm.getForm().reflow(true);
        parent.layout();
        parent.pack();
    }

    private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("username", "String");
        gvTypeFieldMap.put("password", "Password");
        gvTypeFieldMap.put("clientID", "String");
        gvTypeFieldMap.put("autoGenClientID", "boolean");
        gvTypeFieldMap.put("useSsl", "boolean");
        gvTypeFieldMap.put("UseJNDI", "boolean");
        gvTypeFieldMap.put("NamingInitialContextFactory", "String");
        gvTypeFieldMap.put("NamingCredential", "Password");
        gvTypeFieldMap.put("NamingPrincipal", "String");
        gvTypeFieldMap.put("NamingURL", "String");
        gvTypeFieldMap.put("AdmFactorySslPassword", "Password");
        gvTypeFieldMap.put("UseXACF", "boolean");
        gvTypeFieldMap.put("UseSharedJndiConfig", "boolean");
        gvTypeFieldMap.put("ProviderURL", "String");
 	}
    
    public void validateFieldsForGv() {
		validateField((Text)tgUsername.getGvText(),gvTypeFieldMap.get("username"), modelmgr.getStringValue("username"), "username", modelmgr.getProject().getName());
		validateField((Text)tgPassword.getGvText(),gvTypeFieldMap.get("password"), modelmgr.getStringValue("password"), "password", modelmgr.getProject().getName());
		validateField((Text)tgClientId.getGvText(),gvTypeFieldMap.get("clientID"), modelmgr.getStringValue("clientID"), "clientID", modelmgr.getProject().getName());
		validateField((Text)tgAutoGenClientId.getGvText(),gvTypeFieldMap.get("autoGenClientID"), modelmgr.getStringValue("autoGenClientID"), "autoGenClientID", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("useSsl"), modelmgr.getStringValue("useSsl"), "useSsl", modelmgr.getProject().getName());
		validateField((Text)bUseJndiFactory.getGvText(),gvTypeFieldMap.get("UseJNDI"), modelmgr.getStringValue("UseJNDI"), "UseJNDI", modelmgr.getProject().getName());
		validateField((Text)tgJndiFactory.getGvText(),gvTypeFieldMap.get("NamingInitialContextFactory"), modelmgr.getStringValue("NamingInitialContextFactory"), "NamingInitialContextFactory", modelmgr.getProject().getName());
		validateField((Text)tgJndiPassword.getGvText(),gvTypeFieldMap.get("NamingCredential"), modelmgr.getStringValue("NamingCredential"), "NamingCredential", modelmgr.getProject().getName());
		validateField((Text)tgJndiUsername.getGvText(),gvTypeFieldMap.get("NamingPrincipal"), modelmgr.getStringValue("NamingPrincipal"), "NamingPrincipal", modelmgr.getProject().getName());
		validateField((Text)tgJndiContextUrl.getGvText(),gvTypeFieldMap.get("NamingURL"), modelmgr.getStringValue("NamingURL"), "NamingURL", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("AdmFactorySslPassword"), modelmgr.getStringValue("AdmFactorySslPassword"), "AdmFactorySslPassword", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("UseXACF"), modelmgr.getStringValue("UseXACF"), "UseXACF", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("UseSharedJndiConfig"), modelmgr.getStringValue("UseSharedJndiConfig"), "UseSharedJndiConfig", modelmgr.getProject().getName());
		validateField((Text)tgProviderUrl.getGvText(),gvTypeFieldMap.get("ProviderURL"), modelmgr.getStringValue("ProviderURL"), "ProviderURL", modelmgr.getProject().getName());
		validateField((Text)tJndiConfig.getGvText(),gvTypeFieldMap.get("JndiSharedConfiguration"), modelmgr.getStringValue("JndiSharedConfiguration"), "JndiSharedConfiguration", modelmgr.getProject().getName());
	}

	private void createConfigSectionContents(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        comp.setLayout(new GridLayout(2, false));

        Label lDesc = PanelUiUtil.createLabel(comp, "Description: ");
        lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
        tDesc = PanelUiUtil.createTextMultiLine(comp);
        tDesc.setText(modelmgr.getStringValue("description"));
        tDesc.addListener(SWT.Modify, getListener(tDesc, "description"));

        tgUsername = createGvTextField(comp, "User Name: ",modelmgr, "username");
        //tPassword = createConfigTextField(comp, "Password: ", "password");
        tgPassword = createGvPasswordField(comp, "Password: ", modelmgr, "password");

        //bAutoClientId = createConfigCheckboxField(comp, "Auto-generate Client ID: ", "autoGenClientID");
        tgAutoGenClientId = createGvCheckboxField(comp, "Auto-generate Client ID: ", modelmgr, "autoGenClientID");

        tgClientId = createGvTextField(comp, "Client ID: ",modelmgr, "clientID");

        //Label lSsl = PanelUiUtil.createLabel(comp, "Use SSL: ");
        //bUseSsl = PanelUiUtil.createCheckBox(comp, "");
        //bUseSsl.setSelection(modelmgr.getBooleanValue("useSsl"));
        //bUseSsl.addListener(SWT.Selection, getListener(bUseSsl, "useSsl"));
        bgSsl = createGvCheckboxField(comp, "Use SSL: ", modelmgr, "useSsl");

        PanelUiUtil.createLabel(comp, "");
        bSslConfig = PanelUiUtil.createPushButton(comp, "Configure SSL...");
        bSslConfig.setEnabled(getSslSelection());
        bSslConfig.addListener(SWT.Selection, getSslConfigListener());

        bUseJndiFactory = createGvCheckboxField(comp, "Use JNDI for Connection Factory: ",modelmgr, "UseJNDI");

        createFactTabs(comp);
        bUseJndiFactory.getField().notifyListeners(SWT.Selection, new Event());

        bTestConnection = PanelUiUtil.createPushButton(comp, "Test Connection");
        bTestConnection.addListener(SWT.Selection, getTestConnectionListener());

        validateFields();
        comp.pack();
    }

    private void setConFactoryTypeTab() {
        boolean useJndiFactory = ((Button)bUseJndiFactory.getField()).getSelection();
        if (useJndiFactory) {
            setFactCompStackTopControl(TAB_CON_FACTORY_JNDI);
        } else {
            setFactCompStackTopControl(TAB_CON_FACTORY_XA);
        }
    }
    
    private void setConFactoryTypeTabForGv(boolean gvVal) {
        if (gvVal) {
            setFactCompStackTopControl(TAB_CON_FACTORY_JNDI);
        } else {
            setFactCompStackTopControl(TAB_CON_FACTORY_XA);
        }
    }

    private void setFactCompStackTopControl(int top) {
        factTabLayout.topControl = factTabs[top];
        factComp.layout();
    }

    private void createFactTabs(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);

        factComp = new Composite(comp, SWT.NONE);
        factComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        factTabLayout = new StackLayout();
        factComp.setLayout(factTabLayout);

        factTabs = new Control[2];
        factTabs[TAB_CON_FACTORY_JNDI] = createJndiTab();
        factTabs[TAB_CON_FACTORY_XA] = createXaTab();
        factTabLayout.topControl = factTabs[TAB_CON_FACTORY_JNDI];

        factComp.pack();
        comp.pack();
    }

    private Text createConfigTextField(Composite comp, String label, String modelId) {
        PanelUiUtil.createLabel(comp, label);
        Text tField = PanelUiUtil.createText(comp);
        tField.setText(modelmgr.getStringValue(modelId));
        tField.addListener(SWT.Modify, getListener(tField, modelId));
        return tField;
    }

    private Text createConfigPasswordField(Composite comp, String label, String modelId) {
        PanelUiUtil.createLabel(comp, label);
        Text tField = PanelUiUtil.createTextPassword(comp);
        tField.setText(modelmgr.getStringValue(modelId));
        tField.addListener(SWT.Modify, getListener(tField, modelId));
        return tField;
    }

    private Button createConfigCheckboxField(Composite comp, String label, String modelId) {
        PanelUiUtil.createLabel(comp, label);
        Button bField = PanelUiUtil.createCheckBox(comp, "");
        bField.setSelection(modelmgr.getBooleanValue(modelId));
        bField.addListener(SWT.Selection, getListener(bField, modelId));
        return bField;
    }

    private Control createJndiTab() {
        Composite comp = new Composite(factComp, SWT.NONE);
        comp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));

        bUseSharedJndi = createGvCheckboxField(comp, "Use Shared JNDI Configuration: ",modelmgr, "UseSharedJndiConfig");

        createJndiTypeTabs(comp);

        lFactorySslPassword = PanelUiUtil.createLabel(comp, "Connection Factory SSL Password: ");
        tgFactorySslPassword = GvUiUtil.createPasswordGv(comp, modelmgr.getStringValue("AdmFactorySslPassword"));
        setGvFieldListeners(tgFactorySslPassword, SWT.Modify, "AdmFactorySslPassword");
		
		/*
		lFactorySslPassword = PanelUiUtil.createLabel(comp, "Connection Factory SSL Password: ");
		tFactorySslPassword = PanelUiUtil.createTextPassword(comp);
		tFactorySslPassword.setText(modelmgr.getStringValue("AdmFactorySslPassword"));
		tFactorySslPassword.addListener(SWT.Modify, getListener(tFactorySslPassword, "AdmFactorySslPassword"));
		*/

        bUseSharedJndi.getField().notifyListeners(SWT.Selection, new Event());

        bgSsl.getField().notifyListeners(SWT.Selection, new Event());
        //bUseSsl.notifyListeners(SWT.Selection, new Event());

        comp.pack();
        return comp;
    }

    private Control createXaTab() {
        Composite comp = new Composite(factComp, SWT.NONE);
        comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));

        tgProviderUrl = createGvTextField(comp, "Provider URL*: ", modelmgr, "ProviderURL");
//        bUseXaFactory = createConfigCheckboxField(comp, "Use XA Connection Factory: ", "UseXACF");//BE-22465

        comp.pack();
        return comp;
    }

    private void setJndiTypeTab() {
        boolean useShared = ((Button)bUseSharedJndi.getField()).getSelection();
        if (useShared) {
            setJndiTypeCompStackTopControl(TAB_JNDI_SHARED);
        } else {
            setJndiTypeCompStackTopControl(TAB_JNDI_CUSTOM);
        }
    }
    
    private void setJndiTypeTabForGv(boolean gvVal) {
        if (gvVal) {
            setJndiTypeCompStackTopControl(TAB_JNDI_SHARED);
        } else {
            setJndiTypeCompStackTopControl(TAB_JNDI_CUSTOM);
        }
    }

    private void setJndiTypeCompStackTopControl(int top) {
        jndiTabLayout.topControl = jndiTabs[top];
        jndiComp.layout();
    }

    private void createJndiTypeTabs(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);

        jndiComp = new Composite(comp, SWT.NONE);
        jndiComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        jndiTabLayout = new StackLayout();
        jndiComp.setLayout(jndiTabLayout);

        jndiTabs = new Control[2];
        jndiTabs[TAB_JNDI_CUSTOM] = createJndiCustomTab();
        jndiTabs[TAB_JNDI_SHARED] = createJndiSharedTab();
        jndiTabLayout.topControl = jndiTabs[TAB_JNDI_CUSTOM];

        jndiComp.pack();
        comp.pack();
    }

    private Control createJndiSharedTab() {
        final Composite comp = new Composite(jndiComp, SWT.NONE);
        comp.setLayout(PanelUiUtil.getCompactGridLayout(3, false));
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        tJndiConfig = createGvTextField(comp, "JNDI Configuration*:   ", modelmgr, "JndiSharedConfiguration");
//        tJndiConfig = createConfigTextField(comp, "JNDI Configuration*:   ", "JndiSharedConfiguration");
        bJndiConfigBrowse = PanelUiUtil.createBrowsePushButton(comp, tJndiConfig.getGvText());
		bJndiConfigBrowse.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String selected = PanelUiUtil.getFileResourceSelectionListener(comp, modelmgr.getProject(),
						new String[] { "sharedjndiconfig" });
				if (selected != null && tJndiConfig != null) {
					tJndiConfig.setValue(selected);
				}
			}
		});
		comp.pack();
        return comp;
    }

    private Control createJndiCustomTab() {
        Composite comp = new Composite(jndiComp, SWT.NONE);
        comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		/*
		cFactory = PanelUiUtil.createComboBox(comp, modelmgr.getFactoryList());
		String selFactory = modelmgr.getStringValue("NamingInitialContextFactory");
		if (!selFactory.equals(""))
			cFactory.setText(selFactory);
		*/
        tgJndiFactory = createGvComboField(comp, "JNDI Context Factory*: ", modelmgr, modelmgr.getFactoryList(), "NamingInitialContextFactory");

        tgJndiContextUrl = createGvTextField(comp, "JNDI Context URL*: ", modelmgr, "NamingURL");
        tgJndiUsername = createGvTextField(comp, "JNDI User Name: ", modelmgr, "NamingPrincipal");
        //tJndiPassword = createConfigPasswordField(comp, "JNDI Password: ", "NamingCredential");
        tgJndiPassword = createGvPasswordField(comp, "JNDI Password: ", modelmgr, "NamingCredential");

        tgJndiFactory.getField().addListener(SWT.Selection, getFactoryChangeListener());
        tgJndiFactory.setGvListener(getListener(tgJndiFactory.getGvText(), "NamingInitialContextFactory"));
        if (!tgJndiFactory.isGvMode() && tgJndiContextUrl.getValue().equals(""))
            tgJndiFactory.getField().notifyListeners(SWT.Selection, new Event());

        comp.pack();
        return comp;
    }

    private Listener getFactoryChangeListener() {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                String factory = ((Combo)tgJndiFactory.getField()).getText();
                modelmgr.updateStringValue("NamingInitialContextFactory", factory);

                String url = modelmgr.getDefaultUrlForFactory(factory);
                if (!url.equals("")) {
                    tgJndiContextUrl.setFieldModeValue(url);
                    tgJndiContextUrl.onSetFieldMode();
                    modelmgr.updateStringValue("NamingURL", url);
                }
            }
        };
        return listener;
    }

    public Listener getListener(final Control field, final String key) {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (field instanceof Text) {
                	String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),val);
					}
					if (key.equals("UseSharedJndiConfig")) {
                        setJndiTypeTabForGv(Boolean.parseBoolean(gvVal));
                    } else if (key.equals("UseJNDI")) {
                        setConFactoryTypeTabForGv(Boolean.parseBoolean(gvVal));
                    }
					String value = ((Text) field).getText();
                    modelmgr.updateStringValue(key, value);
                    validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
                } else if (field instanceof Combo) {
                    if (key.equals("NamingInitialContextFactory")) {
                        if (tgJndiFactory.isGvMode()) {
                            modelmgr.updateStringValue("NamingInitialContextFactory", tgJndiFactory.getGvValue());
                        }
                    }
                } else if (field instanceof Button) {
                    modelmgr.updateBooleanValue(key, ((Button) field).getSelection());
                    if (key.equals("UseSharedJndiConfig")) {
                        setJndiTypeTab();
                    } else if (key.equals("UseJNDI")) {
                        setConFactoryTypeTab();
                    }
                }
                if (key.equals("useSsl")) {
                    bSslConfig.setEnabled(getSslSelection());
                    lFactorySslPassword.setVisible(getSslSelection());
                    tgFactorySslPassword.setVisible(getSslSelection());
                    //tFactorySslPassword.setVisible(bUseSsl.getSelection());
                }
                if (key.equals("UseJNDI")) {
                	setConFactoryTypeTabForGv(getUseJNDISelection());
                    //tFactorySslPassword.setVisible(bUseSsl.getSelection());
                }
                if (key.equals("UseSharedJndiConfig")) {
                	setJndiTypeTabForGv(getUseSharedJndiConfig());
                    //tFactorySslPassword.setVisible(bUseSsl.getSelection());
                }
         //       validateFields();
            }
        };
        return listener;
    }

    private Listener getSslConfigListener() {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                SslConfigurationJmsDialog dialog = new SslConfigurationJmsDialog(editorParent.getShell(), modelmgr.getModel().sslConfigJmsModel, modelmgr.getProject());
                dialog.initDialog("for TIBCO E4JMS");
                dialog.openDialog();
                if (dialog.isDirty())
                    modelmgr.modified();
            }
        };
        return listener;
    }

    @Override
    public boolean validateFields() {
        boolean valid = true;
        //valid &= PanelUiUtil.validateTextField(tProviderUrl, true, false);
//        valid &= validateJndiConfigField();
        //valid &= PanelUiUtil.validateTextField(tJndiContextUrl, true, false);
        valid &=validateSslSelection();
        return valid;
    }

    public String getName() {
        return ("Configuration");
    }

    private Listener getTestConnectionListener() {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                boolean success = testConnection();
            }
        };
        return listener;
    }

    private boolean testConnection() {

        final Thread thread = Thread.currentThread();
        final ClassLoader oldCtxClassLoader = thread.getContextClassLoader();

        thread.setContextClassLoader(
                StudioProjectConfigurationManager.getInstance()
                        .getProjectConfigClassloader(modelmgr.getProject().getName()));
        try {
            final ChannelConfig proxyConfig = new ChannelConfig() {

                @Override
                public ConfigurationMethod getConfigurationMethod()
                {
                    return ConfigurationMethod.REFERENCE;
                }

                @Override
                public Collection<Object> getDestinations()
                {
                    return new ArrayList<Object>(0);
                }

                @Override
                public String getName()
                {
                    return "Test";
                }

                @Override
                public Properties getProperties()
                {
                    final Properties p = new Properties();
                    p.putAll(JmsConGeneralPage.this.modelmgr.getProperties());
                    return p;
                }

                @Override
                public String getReferenceURI()
                {
                    final IProject project = JmsConGeneralPage.this.modelmgr.getProject();

//                    final IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(
//                            new Path(JmsConGeneralPage.this.modelmgr.getFilePath()).toFile().toURI());
//                    
//                    final String relativePath = files[0].getFullPath().toString();

                    try {
                        final String projectPath = new File(project.getLocation().toOSString()).getCanonicalPath();
                        final String filePath = new File(JmsConGeneralPage.this.modelmgr.getFilePath()).getCanonicalPath();
                        return (filePath.startsWith(projectPath))
                                ? filePath.substring(projectPath.length())
                                : filePath;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public String getType()
                {
                    return DriverManagerConstants.DRIVER_JMS;
                }

                @Override
                public String getServerType()
                {
                    return null;
                }

                @Override
                public String getURI()
                {
                    return "";
                }

                @Override
                public boolean isActive()
                {
                    return true;
                }

                @Override
                public List<WebApplicationDescriptor> getWebApplicationDescriptors()
                {
                    return null;
                }

            };

            boolean connectionCreated = false;
            try {
                SharedResourcePlugin.log("Test connection: loading saved resource state");
                final BEProject project = new BEProject(this.modelmgr.getProject().getLocation().toString());
                project.load();
                final JMSChannelConfig jmsChannelConfig = new JMSChannelConfig(proxyConfig, project);

                SharedResourcePlugin.log("Test connection: attempting connection");
                connectionCreated = jmsChannelConfig.test();
            }
            catch (Throwable t) {
                t.printStackTrace();
                SharedResourcePlugin.log(t);
                MessageDialog.openError(editorParent.getShell(), "JMS Connection",
                        "JMS Connection test failed. \n\n" + t);
                return false;
            }

            if (connectionCreated) {
                SharedResourcePlugin.log("Test connection: created a connection");
                MessageDialog.openInformation(editorParent.getShell(), "JMS Connection",
                        "JMS Connection test successful.");
            }
            else {
                SharedResourcePlugin.log("Test connection: created no connection");
                MessageDialog.openWarning(editorParent.getShell(), "JMS Connection",
                        "No JMS connection was created.");
            }

            return true;
        }
        finally {
            thread.setContextClassLoader(oldCtxClassLoader);
        }
    }


    private Class<?> getClass(String className, boolean showErrors) {

        try {
            return Class.forName(className, true, Thread.currentThread().getContextClassLoader());

        } catch (Throwable t) {
            try {
                Bundle delegateBundle = Platform.getBundle(DelegateActivator.THIRDPARTY_DELEGATE_ID);
                if (delegateBundle != null) {
                    return delegateBundle.loadClass(className);
                } else {
                    if (showErrors)
                        MessageDialog.openError(editorParent.getShell(), "JMS Connection", "JMS Connection test failed. \n\nUnable to load classes in the studio.extended.classpath.");
                }
                //return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                SharedResourcePlugin.log(e);
                if (showErrors)
                    MessageDialog.openError(editorParent.getShell(), "JMS Connection", "JMS Connection test failed. \n\nClass not found: " + e.getMessage());
            }
        }
        return null;
    }


    protected boolean getSslSelection() {
        String stringSslCheck = modelmgr.getStringValue("useSsl");
        if (GvUtil.isGlobalVar(stringSslCheck)) {
            stringSslCheck = GvUtil.getGvDefinedValue(modelmgr.getProject(),
                    stringSslCheck);
        }
        return Boolean.parseBoolean(stringSslCheck);
    }
    
    protected boolean getUseJNDISelection() {
        String stringUseJNDICheck = modelmgr.getStringValue("UseJNDI");
        if (GvUtil.isGlobalVar(stringUseJNDICheck)) {
        	stringUseJNDICheck = GvUtil.getGvDefinedValue(modelmgr.getProject(),
        			stringUseJNDICheck);
        }
        return Boolean.parseBoolean(stringUseJNDICheck);
    }
    
    protected boolean getUseSharedJndiConfig() {
        String stringUseSharedJndiConfig = modelmgr.getStringValue("UseSharedJndiConfig");
        if (GvUtil.isGlobalVar(stringUseSharedJndiConfig)) {
        	stringUseSharedJndiConfig = GvUtil.getGvDefinedValue(modelmgr.getProject(),
        			stringUseSharedJndiConfig);
        }
        return Boolean.parseBoolean(stringUseSharedJndiConfig);
    }
    
    protected boolean validateSslSelection() {
        String stringSslCheck = modelmgr.getStringValue("useSsl");
        if (GvUtil.isGlobalVar(stringSslCheck)) {
            stringSslCheck = GvUtil.getGvDefinedValue(modelmgr.getProject(),
                    stringSslCheck);
        }
        if(stringSslCheck!=null){
            if (!stringSslCheck.equalsIgnoreCase("true")
                    && !stringSslCheck.equalsIgnoreCase("false")) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
