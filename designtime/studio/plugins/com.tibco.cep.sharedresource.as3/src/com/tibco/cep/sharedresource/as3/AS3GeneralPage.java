package com.tibco.cep.sharedresource.as3;

import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_GRIDNAME;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_REALMSERVER;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_SECONDARY_REALM;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_USESSL;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_NAME;
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
import com.tibco.cep.sharedresource.ssl.SslConfigurationFtlDialog;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class AS3GeneralPage extends AbstractSharedResourceEditorPage {
	private AS3ModelMgr modelmgr;
	private Button bSslConfig;
	GvField bgSsl;
	private Map<String,String> gvTypeFieldMap = new LinkedHashMap<String,String>();
	
	public AS3GeneralPage(AS3Editor editor, Composite parent, AS3ModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;

		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		initialiseGvFieldTypeMap();
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

		Composite sConfig = sectionProvider.createSectionPart("Configuration", true);
		createConfigSectionContents(sConfig); 

		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	private void initialiseGvFieldTypeMap() {
		gvTypeFieldMap.put(AS3_CHANNEL_PROPERTY_GRIDNAME, "String");
		gvTypeFieldMap.put(AS3_CHANNEL_PROPERTY_REALMSERVER, "String");
        gvTypeFieldMap.put(AS3_CHANNEL_PROPERTY_USERNAME, "String");
        gvTypeFieldMap.put(AS3_CHANNEL_PROPERTY_PASSWORD, "Password");
        gvTypeFieldMap.put(AS3_CHANNEL_PROPERTY_SECONDARY_REALM, "String");
        gvTypeFieldMap.put(AS3_CHANNEL_PROPERTY_USESSL, "boolean");
  	}

	private void createConfigSectionContents(Composite parent) {
		Composite comp = new Composite(parent, 0);
		comp.setLayoutData(new GridData(768));
		comp.setLayout(new GridLayout(2, false));

		Label lDesc = PanelUiUtil.createLabel(comp, AS3_CHANNEL_PROPERTY_DESCRIPTION + ": ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		Text tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.setText(modelmgr.getStringValue(AS3_CHANNEL_PROPERTY_DESCRIPTION));
		tDesc.addListener(SWT.Modify, getListener(tDesc, AS3_CHANNEL_PROPERTY_DESCRIPTION));
		
		createGvTextField(comp, AS3_CHANNEL_PROPERTY_REALMSERVER + "*: ", modelmgr, AS3_CHANNEL_PROPERTY_REALMSERVER);
		createGvTextField(comp, AS3_CHANNEL_PROPERTY_GRIDNAME + ": ", modelmgr, AS3_CHANNEL_PROPERTY_GRIDNAME);
		createGvTextField(comp, AS3_CHANNEL_PROPERTY_USERNAME + ": ", modelmgr, AS3_CHANNEL_PROPERTY_USERNAME);
		createGvPasswordField(comp, AS3_CHANNEL_PROPERTY_PASSWORD + ": ", modelmgr, AS3_CHANNEL_PROPERTY_PASSWORD);
		createGvTextField(comp, AS3_CHANNEL_PROPERTY_SECONDARY_REALM + ": ", modelmgr, AS3_CHANNEL_PROPERTY_SECONDARY_REALM);
		bgSsl = createGvCheckboxField(comp, "Use SSL: ", modelmgr, AS3_CHANNEL_PROPERTY_USESSL);
		
	    PanelUiUtil.createLabel(comp, "");
        bSslConfig = PanelUiUtil.createPushButton(comp, "Configure SSL...");
        bSslConfig.setEnabled(getSslSelection());
        bSslConfig.addListener(SWT.Selection, getSslConfigListener());
		comp.pack();

	}
	protected boolean getSslSelection() {
	     String stringSslCheck = modelmgr.getStringValue(AS3_CHANNEL_PROPERTY_USESSL);
	     if (GvUtil.isGlobalVar(stringSslCheck)) {
	            stringSslCheck = GvUtil.getGvDefinedValue(modelmgr.getProject(),
	                    stringSslCheck);
	     }
	      return Boolean.parseBoolean(stringSslCheck);
	}
	private Listener getSslConfigListener() {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
            	SslConfigurationFtlDialog dialog = new SslConfigurationFtlDialog(editorParent.getShell(),modelmgr.getModel().sslConfigFtlModel, modelmgr.getProject(), AS3_CHANNEL_NAME );
                dialog.initDialog("for TIBCO ActiveSpaces 3.x");
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
		return valid;
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
					if (GvUtil.isGlobalVar(val)) {
						validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
					}
				} else if (field instanceof Button) {
					modelmgr.getModel().values.put(key, String.valueOf(((Button) field).getSelection()));
					modelmgr.modified();
				}
				if (key.equals(AS3_CHANNEL_PROPERTY_USESSL)) {
					bSslConfig.setEnabled(getSslSelection());
				}
			}
		};
		return listener;
	}

}