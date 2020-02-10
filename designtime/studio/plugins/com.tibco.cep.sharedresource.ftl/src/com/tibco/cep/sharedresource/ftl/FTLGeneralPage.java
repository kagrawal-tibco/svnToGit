package com.tibco.cep.sharedresource.ftl;

import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_REALMSERVER;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_SECONDARY;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USESSL;

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

public class FTLGeneralPage extends AbstractSharedResourceEditorPage {
	private FTLModelMgr modelmgr;
	private Button bSslConfig;
	GvField bgSsl;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	public FTLGeneralPage(FTLEditor editor, Composite parent, FTLModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;

		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		//createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage() );
		initialiseGvFieldTypeMap();
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

		Composite sConfig = sectionProvider.createSectionPart("Configuration", true);
		createConfigSectionContents(sConfig); 

		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put(CHANNEL_PROPERTY_REALMSERVER, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_USERNAME, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_PASSWORD, "Password");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_SECONDARY, "String");
        gvTypeFieldMap.put(CHANNEL_PROPERTY_USESSL, "boolean");
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
		
		createGvTextField(comp, CHANNEL_PROPERTY_REALMSERVER + ": ", modelmgr, CHANNEL_PROPERTY_REALMSERVER);
		createGvTextField(comp, CHANNEL_PROPERTY_USERNAME + ": ", modelmgr, CHANNEL_PROPERTY_USERNAME);
		createGvPasswordField(comp, CHANNEL_PROPERTY_PASSWORD + ": ", modelmgr, CHANNEL_PROPERTY_PASSWORD);
		createGvTextField(comp, CHANNEL_PROPERTY_SECONDARY + ": ", modelmgr, CHANNEL_PROPERTY_SECONDARY);
		bgSsl = createGvCheckboxField(comp, "Use SSL: ", modelmgr, CHANNEL_PROPERTY_USESSL);
	   // bgSsl.getField().notifyListeners(SWT.Selection, new Event());
		
	    PanelUiUtil.createLabel(comp, "");
        bSslConfig = PanelUiUtil.createPushButton(comp, "Configure SSL...");
        bSslConfig.setEnabled(getSslSelection());
        bSslConfig.addListener(SWT.Selection, getSslConfigListener());
		comp.pack();

	}
	protected boolean getSslSelection() {
	     String stringSslCheck = modelmgr.getStringValue(CHANNEL_PROPERTY_USESSL);
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
            	SslConfigurationFtlDialog dialog = new SslConfigurationFtlDialog(editorParent.getShell(),modelmgr.getModel().sslConfigFtlModel, modelmgr.getProject());
                dialog.initDialog("for TIBCO FTL");
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
	public Listener getListener(final Control field, final String key)
	{
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
				}else if (field instanceof Button) {
                    modelmgr.getModel().values.put(key, String.valueOf(((Button) field).getSelection()));
                    modelmgr.modified();
				}
				if (key.equals(CHANNEL_PROPERTY_USESSL)) {
                    bSslConfig.setEnabled(getSslSelection());
                }
			}
		};
		return listener;
	}

}