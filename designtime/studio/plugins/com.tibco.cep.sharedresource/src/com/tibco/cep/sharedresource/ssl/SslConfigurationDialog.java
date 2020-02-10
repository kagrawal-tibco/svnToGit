package com.tibco.cep.sharedresource.ssl;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.ui.util.SharedResourceImages;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_NAME;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_NAME;

/*
@author ssailapp
@date Dec 22, 2009 10:45:00 PM
 */

public abstract class SslConfigurationDialog extends Dialog{
	private Shell shell, dialog;
	private Button bOK, bCancel;
	protected Label lIdentity;
	protected Text tIdentity;
	protected Button bCertFolderBrowse, bIdentityBrowse;
	protected SslConfigModel model;
	protected IProject project;
	private boolean isDirty = false;
	private boolean identityOptional = false;
	private GvField gtStorePasswd;
	protected GvField gCertFolder;
	protected String channelName="";
	private static String FTL_CHANNEL=CHANNEL_NAME;
	private static String AS3_CHANNEL=AS3_CHANNEL_NAME;
	
	public SslConfigurationDialog(Shell parent, SslConfigModel model, IProject project, boolean identityOptional) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.model = model;
		this.project = project;
		this.identityOptional  = identityOptional;
	}
	public SslConfigurationDialog(Shell parent, SslConfigModel model, IProject project, boolean identityOptional,String channelName ) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.model = model;
		this.project = project;
		this.identityOptional  = identityOptional;
		this.channelName = channelName;
	}

	public void initDialog(String target) {
		shell = getParent();
		dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		dialog.setText("SSL Configuration " + target);
		dialog.setImage(SharedResourceImages.getImage(SharedResourceImages.IMG_SHAREDRES_SSL_CONFIG));
		if(channelName.equalsIgnoreCase(FTL_CHANNEL) || channelName.equalsIgnoreCase(AS3_CHANNEL)){
			dialog.setLayout(new GridLayout(1, false));
			createConfigFields(dialog);
			createAdvancedConfigFields(dialog);
			shell.addListener(SWT.Traverse, getTraverseListener(shell));
			createButtons();
		}else{
			dialog.setLayout(new GridLayout(3, false));
			createConfigFields(dialog);
			createCertIdentityFields();
			createAdvancedConfigFields(dialog);
			shell.addListener(SWT.Traverse, getTraverseListener(shell));
			createButtons();
			validateFields();
		}
	}

	public void createCertIdentityFields() {
		gCertFolder = createGvTextField(dialog, getCertifactesFolderLabel(), SslConfigModel.ID_CERT_FOLDER, model.getCert(), 1);
		bCertFolderBrowse = PanelUiUtil.createBrowsePushButton(dialog, (Text)gCertFolder.getField());
		bCertFolderBrowse.addListener(SWT.Selection, PanelUiUtil.getFolderResourceSelectionListener(dialog, project, (Text)gCertFolder.getField()));
		
		if (!identityOptional)
			lIdentity = PanelUiUtil.createLabel(dialog, "Identity*: ");
		else
			lIdentity = PanelUiUtil.createLabel(dialog, "Identity: ");
		tIdentity = PanelUiUtil.createText(dialog);
		tIdentity.setText(model.getIdentity());
		tIdentity.addListener(SWT.Modify, getChangeListener(SslConfigModel.ID_IDENTITY));
		bIdentityBrowse = PanelUiUtil.createBrowsePushButton(dialog, tIdentity);
		bIdentityBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(dialog, project, new String[]{"id"}, tIdentity));
		
		gtStorePasswd = createGvPasswordField(dialog, "Trust Store Password: ", SslConfigModel.ID_TRUST_STORE_PASSWD, model.getTrustStorePasswd());
		if(!model.isTurstStorePasswordGv()){
			gtStorePasswd.setGvModeValue("");
			gtStorePasswd.setFieldModeValue(model.getTrustStorePasswd());
			gtStorePasswd.onSetFieldMode();
		}
	}
	
	protected String getCertifactesFolderLabel() {
		return "Trusted Certificates Folder: ";
	}
	
	public abstract void createConfigFields(Shell dialog);
	public abstract void createAdvancedConfigFields(Shell dialog);
	
	private void createButtons() {
		Composite butComp = new Composite(dialog, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		butComp.setLayoutData(gd);
		butComp.setLayout(new GridLayout(3, false));
		
		@SuppressWarnings("unused")
		Label lFiller = PanelUiUtil.createLabelFiller(butComp);
		
		bOK = new Button(butComp, SWT.PUSH);
		bOK.setText("OK");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bOK.setLayoutData(gd);
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isFieldsDirty()) {
					isDirty = true;
					if(!channelName.equalsIgnoreCase(FTL_CHANNEL) && !channelName.equalsIgnoreCase(AS3_CHANNEL)){
						model.setIdentity(tIdentity.getText());
						model.setCert(gCertFolder.getValue());
						model.setTrustStorePasswd(gtStorePasswd.getValue(), gtStorePasswd.isGvMode());
					}
					saveFields();
				}
				dialog.dispose();
			}
		});
		
		bCancel = new Button(butComp, SWT.PUSH);
		bCancel.setText("Cancel");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bCancel.setLayoutData(gd);
		bCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				dialog.dispose();
			}
		});
		butComp.pack();
	}
	
	public boolean isFieldsDirty() {
		return (!tIdentity.getText().equals(model.getIdentity()) ||
				!gCertFolder.getValue().equals(model.getCert()) ||
				!gtStorePasswd.getValue().equals(model.getTrustStorePasswd()));
	}
	
	public abstract void saveFields();
	
	protected Listener getChangeListener(final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (key.equals(SslConfigModel.ID_CERT_FOLDER)) {
				} else if (key.equals(SslConfigModel.ID_IDENTITY)) {
				} else if (key.equals(SslConfigModel.ID_TRUST_STORE_PASSWD)) {
				}else {
					handleAdvancedFieldChanges(key);
				}
				validateFields();
			}
		};
		return listener;
	}
	
	public abstract void handleAdvancedFieldChanges(String key);
	
	public void openDialog() {
		dialog.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if(event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
			}
		});

		dialog.pack();
		//dialog.setSize(400, 300);
		Rectangle shellBounds = shell.getBounds();
        Point dialogSize = dialog.getSize();
        if(dialogSize.x < 450){
        	dialog.setSize(450, dialogSize.y);
        }
        dialog.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 4);

        dialog.open();
        
		Display display = shell.getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	private Listener getTraverseListener(final Shell shell) {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.detail) {
				case SWT.TRAVERSE_ESCAPE:
					shell.close ();
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
					break;
				}
			}

		};
		return listener;
	}
	
	private boolean validateFields() {
		boolean valid = true;
		valid &= PanelUiUtil.validateTextField(tIdentity, true, false);
		
		Text certFolderText = (Text)gCertFolder.getField(); 
		valid &= PanelUiUtil.validateTextField(certFolderText, certFolderText.getEnabled(), false);
		if (certFolderText.getEnabled())
			valid &= PanelUiUtil.validateTextField(certFolderText, true, false);
		//commenting out call to validate as it always returns true
		//valid &= PanelUiUtil.validateTextField(tStorePasswd, false, false);
		return valid;
	}
	
	public boolean isDirty() {
		return isDirty;
	}
	
	protected GvField createGvCheckboxField(Shell parent, String label,
			String fieldName, String value) {
		Label lField = PanelUiUtil.createLabel(parent, label);
		GvField gvField = GvUiUtil.createCheckBoxGv(parent, value);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gvField.getMasterComposite().setLayoutData(gd);
		setGvFieldListeners(gvField, SWT.Selection, fieldName);
		return gvField;
	}
	
	public GvField createGvPasswordField(Composite parent, String label, String fieldName, String value) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createPasswordGv(parent, value);
    	GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gvField.getMasterComposite().setLayoutData(gd);
		setGvFieldListeners(gvField, SWT.Modify, fieldName);
		return gvField;
    }
	
	 public GvField createGvTextField(Composite parent, String label, String fieldName, String value, int horizontalSpan) {
		 Label lField = PanelUiUtil.createLabel(parent, label);
		 GvField gvField = GvUiUtil.createTextGv(parent, value);
		 GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		 gd.horizontalSpan = horizontalSpan;
		 gvField.getMasterComposite().setLayoutData(gd);
		 setGvFieldListeners(gvField, SWT.Modify, fieldName);
		 return gvField;
	 }

	protected void setGvFieldListeners(GvField gvField, int eventType,
			String modelId) {
		gvField.setFieldListener(eventType,
				getGvListener(gvField.getField(), modelId));
		gvField.setGvListener(getGvListener(gvField.getGvText(), modelId));
	}
	
	protected static boolean validateField(Text textField, String type, String deafultValue,
			String displayName, String prjName) {
		final String problemMessage = validatePropertyValue(type, textField,
				deafultValue, displayName,prjName);
		if (problemMessage != null) {
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_RED));
			textField.setToolTipText(problemMessage);
			return false;
		}else if(problemMessage == null){
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_BLACK));
			textField.setToolTipText("");
			return true;
		}

		return true;
	}
	
	protected static String validatePropertyValue(String type, Text textField,
			String deafultValue, String propertyName, String projectName) {
		final String message = com.tibco.cep.studio.ui.util.Messages.getString(
				"invalid.property.entry", textField.getText(), propertyName,
				type);
		String text = textField.getText();
		boolean globalVar = false;
		if (text.length() > 4) {
			globalVar = GvUtil.isGlobalVar(text.trim());
		}
		if (globalVar) {
			// check if global var defined
			Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator
					.getGlobalVariableNameValues(projectName);

			GlobalVariableDescriptor gvd = glbVars.get(stripGvMarkers(text));
			if (gvd == null) {
				return text + " is not defined";
			}
			if (!gvd.getType().equalsIgnoreCase(type.intern())) {
				/*if (type.intern().equals("int")
						&& gvd.getType().equals("Integer")) {
					return null;
				}*/
				return "Type of "+ text +" is not "+ type.intern();
			}
			return null;
		}
		return null;
	}
	
	private static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		return stripVal;
	}

	
	protected abstract Listener getGvListener(final Control field,
			final String key);
}