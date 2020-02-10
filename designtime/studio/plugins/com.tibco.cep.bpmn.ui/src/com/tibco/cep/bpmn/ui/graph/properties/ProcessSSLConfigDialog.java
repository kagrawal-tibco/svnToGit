package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessSSLConfigDialog extends Dialog implements SelectionListener {
	
	@SuppressWarnings("unused")
	private Button bOK, bCancel, bCipherSuite, bVerifyHostName;
	protected Label lCertFolder, lIdentity;
	protected Text tCertFolder, tIdentity;
	protected Button bCertFolderBrowse, bIdentityBrowse;
	private IProject project;
	private Map<String, Object> sslConfigMap;
	private boolean changed = false;

	public ProcessSSLConfigDialog(Shell parent, IProject project, Map<String, Object> sslConfigMap) {
		super(parent);
		this.project = project;
		if (sslConfigMap == null) {
			this.sslConfigMap = new HashMap<String, Object>();
		} else {
			this.sslConfigMap = sslConfigMap; 
		}
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		shell.setText(BpmnMessages.getString("processSSLConfigDialog_shell_label"));
		shell.setImage(BpmnUIPlugin.getDefault().getImage("icons/sslconfiguration.gif"));
	}
	
	/*
	 * @see Dialog.createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// initialize the dialog units
		initializeDialogUnits(parent);
		Point defaultSpacing = LayoutConstants.getSpacing();
		GridLayoutFactory.fillDefaults().margins(LayoutConstants.getMargins())
				.spacing(defaultSpacing.x * 2,
				defaultSpacing.y).numColumns(3).applyTo(parent);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
		createDialogAndButtonArea(parent);
		return parent;
	}
	
	/*
	 * @see IconAndMessageDialog#createDialogAndButtonArea(Composite)
	 */
	protected void createDialogAndButtonArea(Composite parent) {
		// create the dialog area and button bar
		dialogArea = createDialogArea(parent);
		buttonBar = createButtonBar(parent);
		// Apply to the parent so that the message gets it too.
		applyDialogFont(parent);
	}
	
	 
	protected Control createDialogArea(Composite parent) {
		createConfigFields(parent);
		return null;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		bOK = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		bCancel = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}
	
	public void createConfigFields(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(parent);
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(3, false));
		lCertFolder = PanelUiUtil.createLabel(composite, BpmnMessages.getString("processSSLConfigDialog_lCertFolder_label"));
		tCertFolder = PanelUiUtil.createText(composite);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		tCertFolder.setLayoutData(gd);
		bCertFolderBrowse = PanelUiUtil.createBrowsePushButton(composite, tCertFolder);
		bCertFolderBrowse.addListener(SWT.Selection, PanelUiUtil.getFolderResourceSelectionListener(composite, project, tCertFolder));
		
		lIdentity = PanelUiUtil.createLabel(composite, BpmnMessages.getString("processSSLConfigDialog_lIdentity_label"));
		tIdentity = PanelUiUtil.createText(composite);
		bIdentityBrowse = PanelUiUtil.createBrowsePushButton(composite, tIdentity);
		bIdentityBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(composite, project, new String[]{"id"}, tIdentity));
		
		PanelUiUtil.createLabel(composite,BpmnMessages.getString("processSSLConfigDialog_bVerifyHostName_label"));
		bVerifyHostName = PanelUiUtil.createCheckBox(composite, "");
		bVerifyHostName.setSelection(false);
		PanelUiUtil.createLabelFiller(composite);
		
		PanelUiUtil.createLabel(composite, BpmnMessages.getString("processSSLConfigDialog_bCipherSuite_label"));
		bCipherSuite = PanelUiUtil.createCheckBox(composite, "");
		
		PanelUiUtil.createLabelFiller(composite);
		
		if (sslConfigMap.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER)) {
			tCertFolder.setText((String)sslConfigMap.get(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER));
		}
		
		if (sslConfigMap.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY)) {
			tIdentity.setText((String)sslConfigMap.get(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY));
		}
		
		if (sslConfigMap.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME)) {
			bVerifyHostName.setSelection((Boolean)sslConfigMap.get(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME));
		}

		if (sslConfigMap.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY)) {
			bCipherSuite.setSelection((Boolean)sslConfigMap.get(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY));
		}
		
	}

	@Override
	protected void okPressed() {
		saveSslConfig(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER, tCertFolder.getText().trim());
		saveSslConfig(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY, tIdentity.getText().trim());
		saveSslConfig(BpmnMetaModelExtensionConstants.E_ATTR_VERIFY_HOST_NAME, new Boolean(bVerifyHostName.getSelection()));
		saveSslConfig(BpmnMetaModelExtensionConstants.E_ATTR_STRONG_CIPHER_SUITE_ONLY, new Boolean(bCipherSuite.getSelection()));
		super.okPressed();
	}
	
	private void saveSslConfig(String key, Object newValue) {
		if (sslConfigMap.containsKey(key)) {
			Object oldValue = sslConfigMap.get(key);
			if (oldValue.equals(newValue)) {
				
			} else {
				changed = true;
				sslConfigMap.put(key, newValue);
			}
		}
	}
	
	public Map<String, Object> getSslConfigMap() {
		return sslConfigMap;
	}
	
	public boolean isChanged() {
		return changed;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}