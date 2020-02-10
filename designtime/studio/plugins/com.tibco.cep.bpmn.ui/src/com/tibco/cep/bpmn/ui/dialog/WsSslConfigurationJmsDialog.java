package com.tibco.cep.bpmn.ui.dialog;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 2, 2010 3:52:44 PM
 */

public class WsSslConfigurationJmsDialog extends WsSslConfigurationDialog {

	private Button bCipherSuite, bTrace, bDebugTrace, bVerifyHostName;
	private Text tExpHostName;
	
	public WsSslConfigurationJmsDialog(Shell parent, WsSslConfigModel model, IProject project) {
		super(parent, model, project, true);
	}

	@Override
	public void createConfigFields(Shell dialog) {
	}

	@Override
	public void createAdvancedConfigFields(Shell dialog) {
		WsSslConfigJmsModel sslConfigJmsModel = (WsSslConfigJmsModel) model;
		
		PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigJmsDialog_bTrace_label"));
		bTrace = PanelUiUtil.createCheckBox(dialog, "");
		bTrace.setSelection(sslConfigJmsModel.trace);
		bTrace.addListener(SWT.Selection, getChangeListener(WsSslConfigJmsModel.ID_TRACE));
		PanelUiUtil.createLabelFiller(dialog);
		
		PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigJmsDialog_bDebugTrace_label"));
		bDebugTrace = PanelUiUtil.createCheckBox(dialog, "");
		bDebugTrace.setSelection(sslConfigJmsModel.debugTrace);
		bDebugTrace.addListener(SWT.Selection, getChangeListener(WsSslConfigJmsModel.ID_DEBUG_TRACE));
		PanelUiUtil.createLabelFiller(dialog);
		
		PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigJmsDialog_bVerifyHostName_label"));
		bVerifyHostName = PanelUiUtil.createCheckBox(dialog, "");
		bVerifyHostName.setSelection(sslConfigJmsModel.verifyHostName);
		bVerifyHostName.addListener(SWT.Selection, getChangeListener(WsSslConfigJmsModel.ID_VERIFY_HOST_NAME));
		PanelUiUtil.createLabelFiller(dialog);
		
		PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigJmsDialog_tExpHostName_label"));
		tExpHostName = PanelUiUtil.createText(dialog);
		tExpHostName.setText(sslConfigJmsModel.expectedHostName);
		tExpHostName.addListener(SWT.Modify, getChangeListener(WsSslConfigJmsModel.ID_EXPECTED_HOSTNAME));
		PanelUiUtil.createLabelFiller(dialog);
		
		PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigJmsDialog_bCipherSuite_label"));
		bCipherSuite = PanelUiUtil.createCheckBox(dialog, "");
		bCipherSuite.setSelection(sslConfigJmsModel.cipherSuites);
		bCipherSuite.addListener(SWT.Selection, getChangeListener(WsSslConfigJmsModel.ID_CIPHER_SUITE));
		PanelUiUtil.createLabelFiller(dialog);
	}

	@Override
	public void handleAdvancedFieldChanges(String key) {
	}

	@Override
	public void saveFields() {
		WsSslConfigJmsModel sslConfigJmsModel = (WsSslConfigJmsModel) model;
		sslConfigJmsModel.trace = bTrace.getSelection();
		sslConfigJmsModel.debugTrace = bDebugTrace.getSelection();
		sslConfigJmsModel.verifyHostName = bVerifyHostName.getSelection();
		sslConfigJmsModel.expectedHostName = tExpHostName.getText();
		sslConfigJmsModel.cipherSuites = bCipherSuite.getSelection();
	}
	
	@Override
	public boolean isFieldsDirty() {
		WsSslConfigJmsModel sslConfigJmsModel = (WsSslConfigJmsModel) model;
		return (super.isFieldsDirty() || 
				bTrace.getSelection() ^ sslConfigJmsModel.trace ||
				bDebugTrace.getSelection() ^ sslConfigJmsModel.debugTrace ||
				bVerifyHostName.getSelection() ^ sslConfigJmsModel.verifyHostName ||
				!tExpHostName.getText().equals(sslConfigJmsModel.expectedHostName) ||
				bCipherSuite.getSelection() ^ sslConfigJmsModel.cipherSuites);
	}
}
