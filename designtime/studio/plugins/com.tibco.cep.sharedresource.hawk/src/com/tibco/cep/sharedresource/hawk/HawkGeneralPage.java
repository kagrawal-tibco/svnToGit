package com.tibco.cep.sharedresource.hawk;

import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_HAWKDOAMIN;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVDAEMON;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVNETWORK;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVSERVICE;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_SERVER_URL;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_TRANSPORT;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.hawk.HawkConstants.TRANSPORT_TYPE_EMS;
import static com.tibco.cep.driver.hawk.HawkConstants.TRANSPORT_TYPE_RV;

import java.util.LinkedHashMap;
import java.util.Map;

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

import com.tibco.cep.driver.hawk.util.TestConnection;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.sharedresource.ui.util.hawk.Messages;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class HawkGeneralPage extends AbstractSharedResourceEditorPage {
	static final int RV_TYPE = 0;
	static final int EMS_TYPE = 1;
	private HawkModelMgr modelmgr;
	private Composite typeComp;
	private Control[] typeTabs;
	private StackLayout typeTabLayout;
	private Button testConnButton;
	private GvField transportTypeGv ;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

	// serviceText, networkText, daemonText;
	GvField[] transportFields = new GvField[3];
	private GvField gvDomain,gvRvService,gvRvDaemon,gvRvNetwork,gvEmsServerUrl,gvUsername,gvPassword;

	// serverUrlText, userNameText, passwordText;

	public HawkGeneralPage(HawkEditor editor, Composite parent, HawkModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;

		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		//createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
		initialiseGvFieldTypeMap();
		SectionProvider sectionProvider = new SectionProvider(this.managedForm, sashForm, editor.getEditorFileName(),
				editor);

		Composite sConfig = sectionProvider.createSectionPart("Configuration", false);
		createConfigSectionContents(sConfig);

		

		this.managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
		if(modelmgr.getProject() !=null){
			validateFieldsForGv();
		}
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put(CHANNEL_PROPERTY_TRANSPORT, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_HAWKDOAMIN, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_RVSERVICE, "Integer");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_RVNETWORK, "Integer");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_RVDAEMON, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_SERVER_URL, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_USERNAME, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_PASSWORD, "Password");
	}

	private void createConfigSectionContents(Composite parent) {
		Composite comp = new Composite(parent, 0);
		comp.setLayoutData(new GridData(768));
		comp.setLayout(new GridLayout(2, false));

		Label lDesc = PanelUiUtil.createLabel(comp, CHANNEL_PROPERTY_DESCRIPTION + ": ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.setText(modelmgr.getStringValue(CHANNEL_PROPERTY_DESCRIPTION));
		tDesc.addListener(SWT.Modify, getListener(tDesc, CHANNEL_PROPERTY_DESCRIPTION));

	//	PanelUiUtil.createLabel(comp, CHANNEL_PROPERTY_TRANSPORT);
		String items[] = { TRANSPORT_TYPE_RV, TRANSPORT_TYPE_EMS };
		
		
	//	transportType = PanelUiUtil.createComboBox(comp, items);
		transportTypeGv = createGvComboField(comp, CHANNEL_PROPERTY_TRANSPORT, modelmgr, items, CHANNEL_PROPERTY_TRANSPORT);
		
		if(modelmgr.getStringValue(CHANNEL_PROPERTY_TRANSPORT)!= null && !GvUtil.isGlobalVar(modelmgr.getStringValue(CHANNEL_PROPERTY_TRANSPORT))){
			if(TRANSPORT_TYPE_RV.equals(modelmgr.getStringValue(CHANNEL_PROPERTY_TRANSPORT))){
				((Combo)transportTypeGv.getField()).select(RV_TYPE);
			}else{
				((Combo)transportTypeGv.getField()).select(EMS_TYPE);
			}
		}
	
		((Combo)transportTypeGv.getField()).addListener(SWT.Selection, getListener(transportTypeGv.getField(), CHANNEL_PROPERTY_TRANSPORT));
		(transportTypeGv.getGvText()).addListener(SWT.Modify, getListener(transportTypeGv.getGvText(), CHANNEL_PROPERTY_TRANSPORT));
		gvDomain = createGvTextField(comp, CHANNEL_PROPERTY_HAWKDOAMIN + ": ", modelmgr, CHANNEL_PROPERTY_HAWKDOAMIN);

		createTypeTabs(comp);

		comp.pack();

	}

	
	public void validateFieldsForGv() {
		validateField((Text)transportTypeGv.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_TRANSPORT), modelmgr.getStringValue(CHANNEL_PROPERTY_TRANSPORT), CHANNEL_PROPERTY_TRANSPORT, modelmgr.getProject().getName());
		validateField((Text)gvDomain.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_HAWKDOAMIN), modelmgr.getStringValue(CHANNEL_PROPERTY_HAWKDOAMIN), CHANNEL_PROPERTY_HAWKDOAMIN, modelmgr.getProject().getName());
		validateField((Text)gvRvService.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_RVSERVICE), modelmgr.getStringValue(CHANNEL_PROPERTY_RVSERVICE), CHANNEL_PROPERTY_RVSERVICE, modelmgr.getProject().getName());
		validateField((Text)gvRvNetwork.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_RVNETWORK), modelmgr.getStringValue(CHANNEL_PROPERTY_RVNETWORK), CHANNEL_PROPERTY_RVNETWORK, modelmgr.getProject().getName());
		validateField((Text)gvRvDaemon.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_RVDAEMON), modelmgr.getStringValue(CHANNEL_PROPERTY_RVDAEMON), CHANNEL_PROPERTY_RVDAEMON, modelmgr.getProject().getName());
		validateField((Text)gvEmsServerUrl.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_SERVER_URL), modelmgr.getStringValue(CHANNEL_PROPERTY_SERVER_URL), CHANNEL_PROPERTY_SERVER_URL, modelmgr.getProject().getName());
		validateField((Text)gvUsername.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_USERNAME), modelmgr.getStringValue(CHANNEL_PROPERTY_USERNAME), CHANNEL_PROPERTY_USERNAME, modelmgr.getProject().getName());
		validateField((Text)gvPassword.getGvText(),gvTypeFieldMap.get(CHANNEL_PROPERTY_PASSWORD), modelmgr.getStringValue(CHANNEL_PROPERTY_PASSWORD), CHANNEL_PROPERTY_PASSWORD, modelmgr.getProject().getName());
	}

	@Override
	public String getName() {
		return ("Configuration");
	}

	@Override
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
						modelmgr.updateStringValue(key, ((Text) field).getText());
						String val=((Text)field).getText();
						String gvVal=null;
						if(GvUtil.isGlobalVar(val)){
							gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),val);
							if(TRANSPORT_TYPE_RV.equalsIgnoreCase(gvVal)){
								typeTabLayout.topControl = typeTabs[0];
								typeComp.layout();
								modelmgr.updateStringValue(key, ((Text) field).getText());
							}
							else if(TRANSPORT_TYPE_EMS.equalsIgnoreCase(gvVal)){
								typeTabLayout.topControl = typeTabs[1];
								typeComp.layout();
								modelmgr.updateStringValue(key, ((Text) field).getText());
							}
						}
						validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
						
					} else if (field instanceof Combo) {
						int choice = ((Combo) field).getSelectionIndex();
						typeTabLayout.topControl = typeTabs[choice];
						typeComp.layout();
						modelmgr.updateStringValue(key, ((Combo) field).getItem(choice));
					}
				}
			
		};
		return listener;
	}

	private void createTypeTabs(Composite parent) {
		Composite comp = new Composite(parent, 0);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		GridData gd = new GridData(1808);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);

		typeComp = new Composite(comp, 0);
		typeComp.setLayoutData(new GridData(1808));
		typeTabLayout = new StackLayout();
		typeComp.setLayout(typeTabLayout);
		typeTabs = new Control[2];
		typeTabs[RV_TYPE] = createRVTab();
		typeTabs[EMS_TYPE] = createEMSTab(parent);
		
		if(GvUtil.isGlobalVar(modelmgr.getStringValue(CHANNEL_PROPERTY_TRANSPORT))){
			String val = modelmgr.getStringValue(CHANNEL_PROPERTY_TRANSPORT);
			String gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),val);
			if(TRANSPORT_TYPE_RV.equalsIgnoreCase(gvVal)){
				typeTabLayout.topControl = typeTabs[0];
				typeComp.layout();
			}
			else{
				typeTabLayout.topControl = typeTabs[1];
				typeComp.layout();
			}
		}else{
			typeTabLayout.topControl = typeTabs[((Combo)transportTypeGv.getField()).getSelectionIndex()];
		}
	}

	private Control createRVTab() {
		Composite comp = new Composite(typeComp, 0);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		transportFields[0] = createGvTextField(comp, CHANNEL_PROPERTY_RVSERVICE + ": ", modelmgr,
				CHANNEL_PROPERTY_RVSERVICE);
		gvRvService = transportFields[0];
		transportFields[1] = createGvTextField(comp, CHANNEL_PROPERTY_RVNETWORK + ": ", modelmgr,
				CHANNEL_PROPERTY_RVNETWORK);
		gvRvNetwork = transportFields[1];
		transportFields[2] = createGvTextField(comp, CHANNEL_PROPERTY_RVDAEMON + ": ", modelmgr,
				CHANNEL_PROPERTY_RVDAEMON);
		gvRvDaemon = transportFields[2];
		return comp;
	}

	private Control createEMSTab(final Composite parent) {
		Composite comp = new Composite(typeComp, 0);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		transportFields[0] = createGvTextField(comp, CHANNEL_PROPERTY_SERVER_URL + ": ", modelmgr,
				CHANNEL_PROPERTY_SERVER_URL);
		gvEmsServerUrl = transportFields[0];
		
		transportFields[1] = createGvTextField(comp, CHANNEL_PROPERTY_USERNAME + ": ", modelmgr,
				CHANNEL_PROPERTY_USERNAME);
		gvUsername = transportFields[1];
		transportFields[2] = createGvPasswordField(comp, CHANNEL_PROPERTY_PASSWORD + ": ", modelmgr,
				CHANNEL_PROPERTY_PASSWORD);
		gvPassword = transportFields[2];

		testConnButton = PanelUiUtil.createPushButton(comp, "Test Connection");
		testConnButton.addListener(13, new Listener() {

			@Override
			public void handleEvent(Event event) {
				String serverUrl = transportFields[0].getGvDefinedValue(modelmgr.getProject());
				String userName = transportFields[1].getGvDefinedValue(modelmgr.getProject());
				String password = transportFields[2].getGvDefinedValue(modelmgr.getProject());
				try {
					boolean isConnect = TestConnection.testConnection(serverUrl, userName, password);
					if (isConnect) {
						MessageDialog.openInformation(editorParent.getShell(), "EMS Connection",
								"EMS Connection test successful.");
					} else {
						MessageDialog.openError(editorParent.getShell(), "EMS Connection",
								"EMS Connection test failed.");
					}
				} catch (NoClassDefFoundError er) {
					MessageDialog.openError(parent.getShell(), "Classpath_Error",
							Messages.getString("hawk.wizard.error.classpath"));
				}

			}
		});
		return comp;
	}
	

}