package com.tibco.cep.sharedresource.httpconfig;

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

import com.tibco.cep.sharedresource.ssl.SslConfigurationHttpDialog;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 22, 2009 4:08:53 AM
 */
@SuppressWarnings({"unused"})
public class HttpGeneralPage extends AbstractSharedResourceEditorPage {

	private HttpConfigModelMgr modelmgr;
	private Text tDesc;
	private Button bSslConfig;
	private GvField tgHost, tgPort, bgSsl;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	//private Button bEnableLookups;
	//private Table taProps;
	//private TableProviderUi taPropsProviderUi;
	
    private String keys[] = new String[] { "PropertyName", "PropertyValue", "PropertyDefaultValue" };
	
	public HttpGeneralPage(HttpConfigEditor editor, Composite parent, HttpConfigModelMgr modelmgr) {
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

		Composite sConfig = sectionProvider.createSectionPart("Configuration", true);
		createConfigSectionContents(sConfig); 
		
		/*
		Composite sAdvanced = sectionProvider.createSectionPart(sConfig, "Advanced", true);
		createAdvancedSectionContents(sAdvanced); 
		*/
		if(modelmgr.getProject() !=null){
			validateFieldsForGv();
		}
		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("Host", "String");
        gvTypeFieldMap.put("Port", "Integer");
        gvTypeFieldMap.put("useSsl", "boolean");
  	}
	
    public void validateFieldsForGv() {
		validateField((Text)tgHost.getGvText(),gvTypeFieldMap.get("Host"), modelmgr.getStringValue("Host"), "Host", modelmgr.getProject().getName());
		validateField((Text)tgPort.getGvText(),gvTypeFieldMap.get("Port"), modelmgr.getStringValue("Port"), "Port", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("useSsl"), modelmgr.getStringValue("useSsl"), "useSsl", modelmgr.getProject().getName());
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
		
		/*
		Label lHost = PanelUiUtil.createLabel(comp, "Host: ");
		tHost = PanelUiUtil.createText(comp);
		tHost.setText(modelmgr.getStringValue("Host"));
		tHost.addListener(SWT.Modify, getListener(tHost, "Host"));
		*/
		tgHost = createGvTextField(comp, "Host*: ", modelmgr, "Host");
		
		tgPort = createGvTextField(comp, "Port*: ", modelmgr, "Port");
		/*
		tgPort = PanelUiUtil.createTextGv(comp, modelmgr.getStringValue("Port"));
		tgPort.setFieldListener(getListener(tgPort.getField(), "Port"));
		tgPort.setGvListener(getListener(tgPort.getGvText(), "Port"));
		*/
		
		/*
		//PanelUiUtil.setLabelBold(comp.getShell(), lPort);	//TODO - this makes it look ugly
		tPort = PanelUiUtil.createText(comp);
		tPort.setText();
		tPort.addListener(SWT.Modify, getListener(tPort, "Port"));
		bPortGv = PanelUiUtil.createGlobalVarPushButton(comp);
		PanelUiUtil.setDropTarget(tPort);
		*/
		
		//Label lSsl = PanelUiUtil.createLabel(comp, "Use SSL: ");
		//bSsl = PanelUiUtil.createCheckBox(comp, "");
		//bSsl.setSelection(modelmgr.getBooleanValue("useSsl"));
		//bSsl.addListener(SWT.Selection, getListener(bSsl, "useSsl"));
		bgSsl = createGvCheckboxField(comp, "Use SSL: ", modelmgr, "useSsl");

		PanelUiUtil.createLabel(comp, "");
		bSslConfig = PanelUiUtil.createPushButton(comp, "Configure SSL...");
		bSslConfig.setEnabled(getSslSelection());
		bSslConfig.addListener(SWT.Selection, getSslConfigListener());
		
		validateFields();
		comp.pack();
	}
	
	/*
	private void createAdvancedSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		Label lEnableLookups = PanelUiUtil.createLabel(comp, "Enable DNS Lookups: ");
		bEnableLookups = PanelUiUtil.createCheckBox(comp, "");
		bEnableLookups.setSelection(modelmgr.getBooleanValue("enableLookups"));
		bEnableLookups.addListener(SWT.Selection, getListener(bEnableLookups, "enableLookups"));
		
		Composite tableComp = new Composite(parent, SWT.NONE);
		tableComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tableComp.setLayout(new GridLayout(1, false));
		PanelUiUtil.createLabel(tableComp, "Connection Properties:");
		String columns[] = new String[]{ "Property Name", "Property Value", "Property Default Value"};
		taPropsProviderUi = new TableProviderUi(tableComp, columns, false, null);
		taProps = taPropsProviderUi.createTable();
		
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (LinkedHashMap<String, String> map: modelmgr.getModel().connectionProps) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	for (int i=0; i<keys.length; i++) {
	    		rowData.add(map.get(keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taPropsProviderUi.setTableData(tableData);
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 1));
	    taProps.addListener(SWT.Modify, getTableModifyListener());
        
		comp.pack();
	}
	*/
	
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					modelmgr.updateStringValue(key, ((Text) field).getText());
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						 validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
					}
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
				SslConfigurationHttpDialog dialog = new SslConfigurationHttpDialog(editorParent.getShell(), modelmgr.getModel().sslConfigHttpModel, modelmgr.getProject());
				dialog.initDialog("for HTTPS Connections");
				dialog.openDialog();
				if (dialog.isDirty())
					modelmgr.modified();
			}
		};
		return listener;
	}
	
	/*
	private Listener getTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = false;
				for (TableItem item: taProps.getItems()) {
					updated |= modelmgr.updateConnectionProperties(item.getText(0), item.getText(1));
				}
			}
		};
		return listener;
	}
	*/
	
	public boolean validateFields() {
		boolean valid = true;
		//valid &= GvUtil.validateGvField(tgPort, true, true);
		return valid;
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
		if (!stringSslCheck.equalsIgnoreCase("true")
				&& !stringSslCheck.equalsIgnoreCase("false")) {
			return false;
		} else {
			return true;
		}
	}
}
