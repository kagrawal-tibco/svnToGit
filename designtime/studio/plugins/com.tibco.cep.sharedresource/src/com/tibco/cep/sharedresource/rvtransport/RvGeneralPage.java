package com.tibco.cep.sharedresource.rvtransport;

import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_TRANSPORT;
import static com.tibco.cep.driver.hawk.HawkConstants.TRANSPORT_TYPE_RV;

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

import com.tibco.cep.sharedresource.ssl.SslConfigurationRvDialog;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 29, 2009 5:42:24 PM
 */
@SuppressWarnings({"unused"})
public class RvGeneralPage extends AbstractSharedResourceEditorPage {
	private static String ID_TYPE_RELIABLE = "reliable";
	private static String ID_TYPE_CERTIFIED = "certified";
	private static String ID_TYPE_CERTIFIEDQ = "certifiedQ";
	
	private static int TYPE_TAB_RELIABLE = 0;
	private static int TYPE_TAB_CERTIFIED = 1;
	private static int TYPE_TAB_CERTIFIEDQ = 2;
	
	private static String TYPE_RELIABLE = "Reliable";
	private static String TYPE_CERTIFIED = "Certified";
	private static String TYPE_CERTIFIEDQ = "Distributed Queue";

	private RvTransportModelMgr modelmgr;
	private Button bSslConfig, bLedgerFileBrowse;
//	private Combo cType;
	private Text tDesc;
	private Text tLedgerFile;
	private Text tSchedulerActivation, tSchedulerHeartbeat, tWorkerCompleteTime;
	private GvField tgSyncLedger, tgRequireOld, tgMessageTimeout, tgWorkerWeight, tgWorkerTasks, tgSchedulerWeight, bgSsl, tgDaemon, tgNetwork, tgService,tgCmName,tgRelayAgent,tgCmqName,cgType;
	
	private StackLayout typeTabLayout;
	private Control typeTabs[];
	private Composite typeComp;
	
	private Map<String, String> typeMap, idMap;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	
	public RvGeneralPage(RvTransportEditor editor, Composite parent, RvTransportModelMgr modelmgr) {
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
		
		Composite sAdvanced = sectionProvider.createSectionPart(sConfig, "Advanced", false);
		createAdvancedSectionContents(sAdvanced); 
		if(modelmgr.getProject() !=null){
			validateFieldsForGv();
		}
		
		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	
	private void initialiseGvFieldTypeMap() {
	        gvTypeFieldMap.put("relayAgent", "String");
	        gvTypeFieldMap.put("requireOld", "boolean");
	        gvTypeFieldMap.put("operationTimeout", "Integer");
	        gvTypeFieldMap.put("syncLedger", "boolean");
	        gvTypeFieldMap.put("workerWeight", "Integer");
	        gvTypeFieldMap.put("schedulerWeight", "Integer");
	        gvTypeFieldMap.put("workerTasks", "Integer");
	        gvTypeFieldMap.put("daemon", "String");
	        gvTypeFieldMap.put("network", "Integer");
	        gvTypeFieldMap.put("service", "Integer");
	        gvTypeFieldMap.put("cmName", "String");
	        gvTypeFieldMap.put("cmqName", "String");
	        gvTypeFieldMap.put("useSsl", "boolean");
	        gvTypeFieldMap.put("showExpertSettings", "String");
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
		
		tgDaemon = createGvTextField(comp, "Daemon: ", modelmgr, "daemon");
		tgNetwork = createGvTextField(comp, "Network: ", modelmgr, "network");
		tgService = createGvTextField(comp, "Service: ", modelmgr, "service");

		//Label lSsl = PanelUiUtil.createLabel(comp, "Use SSL: ");
		//bSsl = PanelUiUtil.createCheckBox(comp, "");
		//bSsl.setSelection(modelmgr.getBooleanValue("useSsl"));
		//bSsl.addListener(SWT.Selection, getListener(bSsl, "useSsl"));
		bgSsl = createGvCheckboxField(comp, "Use SSL: ", modelmgr, "useSsl");

		PanelUiUtil.createLabel(comp, "");
		bSslConfig = PanelUiUtil.createPushButton(comp, "Configure SSL...");
		bSslConfig.setEnabled(getSslSelection());
		bSslConfig.addListener(SWT.Selection, getSslConfigListener());
		
		comp.pack();
	}
	
	private void createAdvancedSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(2, false));

		initRvTypeMap();
		String rvTypes[] = { TYPE_RELIABLE, TYPE_CERTIFIED, TYPE_CERTIFIEDQ };
	//	Label lType = PanelUiUtil.createLabel(comp, "RV Type: ");
		cgType = createGvComboField(comp, "RV Type: ", modelmgr, rvTypes, "showExpertSettings");
		String selItem = modelmgr.getStringValue("showExpertSettings");
		if (!selItem.equals("")){
			if(TYPE_RELIABLE.equalsIgnoreCase(selItem))
				((Combo)cgType.getField()).setText(TYPE_RELIABLE);
			if(TYPE_CERTIFIED.equalsIgnoreCase(selItem))
				((Combo)cgType.getField()).setText(TYPE_CERTIFIED);
			if("certifiedq".equalsIgnoreCase(selItem))
				((Combo)cgType.getField()).setText(TYPE_CERTIFIEDQ);
		}	
		else if (((Combo)cgType.getField()).getItemCount()>0)
			((Combo)cgType.getField()).setText(((Combo)cgType.getField()).getItem(0));
		
//		((Combo)cgType.getField()).setText(idMap.get(modelmgr.getStringValue("showExpertSettings")));
		cgType.getField().addListener(SWT.Selection, getTypeChangeListener((Combo)cgType.getField()));

		createTypeTabs(comp);
		cgType.getField().notifyListeners(SWT.Selection, new Event());
		
		comp.pack();
	}
	
	private Text createConfigTextField(Composite comp, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		Text tField = PanelUiUtil.createText(comp);
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
	
	private Listener getTypeChangeListener(final Combo cType) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String rvType = cType.getText();
				if (rvType.equals(TYPE_RELIABLE)) {
					setTypeCompStackTopControl(TYPE_TAB_RELIABLE);
				} else if (rvType.equals(TYPE_CERTIFIED)) {
					setTypeCompStackTopControl(TYPE_TAB_CERTIFIED);
				} else if (rvType.equals(TYPE_CERTIFIEDQ)) {
					setTypeCompStackTopControl(TYPE_TAB_CERTIFIEDQ);
				}
				modelmgr.updateStringValue("showExpertSettings", typeMap.get(rvType));
				validateFields();
			}
		};
		return listener;
	}
	
	private void setTypeCompStackTopControl(int top) {
		typeTabLayout.topControl = typeTabs[top];
		typeComp.layout();
	}
	
	private void initRvTypeMap() {
		typeMap = new LinkedHashMap<String, String>();
		typeMap.put(TYPE_RELIABLE, ID_TYPE_RELIABLE);
		typeMap.put(TYPE_CERTIFIED, ID_TYPE_CERTIFIED);
		typeMap.put(TYPE_CERTIFIEDQ, ID_TYPE_CERTIFIEDQ);
		
		idMap = new LinkedHashMap<String, String>();
		idMap.put(ID_TYPE_RELIABLE, TYPE_RELIABLE);
		idMap.put(ID_TYPE_CERTIFIED, TYPE_CERTIFIED);
		idMap.put(ID_TYPE_CERTIFIEDQ, TYPE_CERTIFIEDQ);
	}

	private void createTypeTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		
		typeComp = new Composite(comp, SWT.NONE);
		typeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		typeTabLayout = new StackLayout();
		typeComp.setLayout(typeTabLayout);
	    
	    typeTabs = new Control[3];
	    typeTabs[TYPE_TAB_RELIABLE] = createReliableTab();
	    typeTabs[TYPE_TAB_CERTIFIED] = createCertifiedTab();
	    typeTabs[TYPE_TAB_CERTIFIEDQ] = createCertifiedQTab();
	    
	    if(GvUtil.isGlobalVar(modelmgr.getStringValue("showExpertSettings"))){
			String val = modelmgr.getStringValue("showExpertSettings");
			String gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),val);
			if (TYPE_RELIABLE.equals(gvVal)) {
				setTypeCompStackTopControl(TYPE_TAB_RELIABLE);
			} else if (TYPE_CERTIFIED.equals(gvVal)) {
				setTypeCompStackTopControl(TYPE_TAB_CERTIFIED);
			} else if (TYPE_CERTIFIEDQ.equals(gvVal)) {
				setTypeCompStackTopControl(TYPE_TAB_CERTIFIEDQ);
			}
		}else{
			typeTabLayout.topControl = typeTabs[TYPE_TAB_RELIABLE]; 
		}
		typeComp.pack();
		comp.pack();
		
	}

	private Control createReliableTab() {
		return typeComp;
	}
	
	private Control createCertifiedTab() {
		Composite comp = new Composite(typeComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		tgCmName = createGvTextField(comp, "CM Name*: ",modelmgr, "cmName");
		
		Label lFileUrl = PanelUiUtil.createLabel(comp, "Ledger File: ");
		Composite urlComp = new Composite(comp, SWT.NONE);
		urlComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		urlComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tLedgerFile = PanelUiUtil.createText(urlComp);
		tLedgerFile.setText(modelmgr.getStringValue("ledgerFile"));
		tLedgerFile.addListener(SWT.Modify, getListener(tLedgerFile, "ledgerFile"));
		bLedgerFileBrowse = PanelUiUtil.createBrowsePushButton(urlComp, tLedgerFile);
		bLedgerFileBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), urlComp, null, tLedgerFile));
		urlComp.pack();
		//tLedgerFile = createConfigTextField(comp, "Ledger File: ", "ledgerFile");
		
		//bSyncLedger = createConfigCheckboxField(comp, "Sync Ledger File: ", "syncLedger");
		tgSyncLedger = createGvCheckboxField(comp, "Sync Ledger File: ", modelmgr, "syncLedger");
		
		tgRelayAgent = createGvTextField(comp, "Relay Agent: ",modelmgr, "relayAgent");
		//bRequireOld = createConfigCheckboxField(comp, "Require Old Message: ", "requireOld");
		tgRequireOld = createGvCheckboxField(comp, "Require Old Message: ", modelmgr, "requireOld");
		//tMessageTimeout = createConfigTextField(comp, "Message Timeout (sec): ", "operationTimeout");
		tgMessageTimeout = createGvTextField(comp, "Message Timeout (sec)*: ", modelmgr, "operationTimeout");
		
		comp.pack();
		return comp;
	}

	private Control createCertifiedQTab() {
		Composite comp = new Composite(typeComp, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		tgCmqName = createGvTextField(comp, "CMQ Name*: ",modelmgr, "cmqName");
		//tWorkerWeight = createConfigTextField(comp, "Worker Weight: ", "workerWeight");
		tgWorkerWeight = createGvTextField(comp, "Worker Weight: ", modelmgr, "workerWeight");
		//tWorkerTasks = createConfigTextField(comp, "Worker Tasks: ", "workerTasks");		
		tgWorkerTasks = createGvTextField(comp, "Worker Tasks: ", modelmgr, "workerTasks");		
		tWorkerCompleteTime = createConfigTextField(comp, "Worker Complete Time: ", "workerCompleteTime");		
		//tSchedulerWeight = createConfigTextField(comp, "Scheduler Weight: ", "schedulerWeight");		
		tgSchedulerWeight = createGvTextField(comp, "Scheduler Weight: ", modelmgr, "schedulerWeight");
		tSchedulerHeartbeat = createConfigTextField(comp, "Scheduler Heartbeat: ", "scheduleHeartbeat");		
		tSchedulerActivation = createConfigTextField(comp, "Scheduler Activation: ", "scheduleActivation");		
		
		comp.pack();
		return comp;
	}

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
						if (TYPE_RELIABLE.equals(gvVal)) {
							setTypeCompStackTopControl(TYPE_TAB_RELIABLE);
						} else if (TYPE_CERTIFIED.equals(gvVal)) {
							setTypeCompStackTopControl(TYPE_TAB_CERTIFIED);
						} else if (TYPE_CERTIFIEDQ.equals(gvVal)) {
							setTypeCompStackTopControl(TYPE_TAB_CERTIFIEDQ);
						}
				//		modelmgr.updateStringValue("showExpertSettings", typeMap.get(val));
						validateFields();
					}
					
					 validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
				} else if (field instanceof Button) {
					modelmgr.updateBooleanValue(key, ((Button) field).getSelection());
				}
				if (key.equals("useSsl")) {
					bSslConfig.setEnabled(getSslSelection());
				}
				validateFields();
			}
		};
		return listener;
	}

	private Listener getSslConfigListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				SslConfigurationRvDialog dialog = new SslConfigurationRvDialog(editorParent.getShell(), modelmgr.getModel().sslConfigRvModel, modelmgr.getProject());
				dialog.initDialog("for TIBCO RV");
				dialog.openDialog();
				if (dialog.isDirty())
					modelmgr.modified();
			}
		};
		return listener;
	}
	
	
	public void validateFieldsForGv() {
		validateField((Text)tgWorkerWeight.getGvText(),gvTypeFieldMap.get("workerWeight"), modelmgr.getStringValue("workerWeight"), "workerWeight", modelmgr.getProject().getName());
		validateField((Text)tgWorkerTasks.getGvText(),gvTypeFieldMap.get("workerTasks"), modelmgr.getStringValue("workerTasks"), "workerTasks", modelmgr.getProject().getName());
		validateField((Text)tgSchedulerWeight.getGvText(),gvTypeFieldMap.get("schedulerWeight"), modelmgr.getStringValue("schedulerWeight"), "schedulerWeight", modelmgr.getProject().getName());
		validateField((Text)tgMessageTimeout.getGvText(),gvTypeFieldMap.get("operationTimeout"), modelmgr.getStringValue("operationTimeout"), "operationTimeout", modelmgr.getProject().getName());
		validateField((Text)tgRequireOld.getGvText(),gvTypeFieldMap.get("requireOld"), modelmgr.getStringValue("requireOld"), "requireOld", modelmgr.getProject().getName());
		validateField((Text)tgRelayAgent.getGvText(),gvTypeFieldMap.get("relayAgent"), modelmgr.getStringValue("relayAgent"), "relayAgent", modelmgr.getProject().getName());
		validateField((Text)tgSyncLedger.getGvText(),gvTypeFieldMap.get("syncLedger"), modelmgr.getStringValue("syncLedger"), "syncLedger", modelmgr.getProject().getName());
		validateField((Text)cgType.getGvText(),gvTypeFieldMap.get("showExpertSettings"), modelmgr.getStringValue("showExpertSettings"), "showExpertSettings", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("useSsl"), modelmgr.getStringValue("useSsl"), "useSsl", modelmgr.getProject().getName());
		validateField((Text)tgService.getGvText(),gvTypeFieldMap.get("service"), modelmgr.getStringValue("service"), "service", modelmgr.getProject().getName());
		validateField((Text)tgNetwork.getGvText(),gvTypeFieldMap.get("network"), modelmgr.getStringValue("network"), "network", modelmgr.getProject().getName());
		validateField((Text)tgDaemon.getGvText(),gvTypeFieldMap.get("daemon"), modelmgr.getStringValue("daemon"), "daemon", modelmgr.getProject().getName());
		validateField((Text)tgCmqName.getGvText(),gvTypeFieldMap.get("cmqName"), modelmgr.getStringValue("cmqName"), "cmqName", modelmgr.getProject().getName());
	}
	
	public String getName() {
		return ("Configuration");
	}
	
	protected boolean getSslSelection() {
		String stringSslCheck = modelmgr.getStringValue("useSsl");
		if (GvUtil.isGlobalVar(stringSslCheck)) {
			stringSslCheck = GvUtil.getGvDefinedValue(modelmgr.getProject(),
					stringSslCheck);
		}
		return Boolean.parseBoolean(stringSslCheck);
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
