package com.tibco.cep.bpmn.ui.dialog;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 22, 2009 10:45:00 PM
 */

public abstract class WsSslConfigurationDialog extends Dialog {
	private Shell shell, dialog;
	private Button bOK, bCancel;
	protected Label lCertFolder, lIdentity;
	protected Text tCertFolder, tIdentity;
	protected Button bCertFolderBrowse, bIdentityBrowse;
	protected WsSslConfigModel model;
	private IProject project;
	private boolean isDirty = false;
	private boolean identityOptional = false;
	
	public WsSslConfigurationDialog(Shell parent, WsSslConfigModel model, IProject project, boolean identityOptional) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.model = model;
		this.project = project;
		this.identityOptional  = identityOptional;
	}

	public void initDialog(String target) {
		shell = getParent();
		dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		dialog.setText(BpmnMessages.getString("wsSslCOnfigDialog_shell_label") + target);
		dialog.setLayout(new GridLayout(3, false));

		createConfigFields(dialog);
		createCertIdentityFields();
		createAdvancedConfigFields(dialog);

		shell.addListener(SWT.Traverse, getTraverseListener(shell));
		
		createButtons();
		validateFields();
	}
	
	public void createCertIdentityFields() {
		lCertFolder = PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigDialog_lCertFolder_label"));
		tCertFolder = PanelUiUtil.createText(dialog);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		tCertFolder.setLayoutData(gd);
		tCertFolder.setText(model.cert);
		tCertFolder.addListener(SWT.Modify, getChangeListener(WsSslConfigModel.ID_CERT_FOLDER));
		bCertFolderBrowse = PanelUiUtil.createBrowsePushButton(dialog, tCertFolder);
		bCertFolderBrowse.addListener(SWT.Selection, PanelUiUtil.getFolderResourceSelectionListener(dialog, project, tCertFolder));
		
		if (!identityOptional)
			lIdentity = PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigDialog_lIdentity_label"));
		else
			lIdentity = PanelUiUtil.createLabel(dialog, BpmnMessages.getString("wsSslCOnfigDialog_lIdentityOptional_label"));
		tIdentity = PanelUiUtil.createText(dialog);
		tIdentity.setText(model.identity);
		tIdentity.addListener(SWT.Modify, getChangeListener(WsSslConfigModel.ID_IDENTITY));
		bIdentityBrowse = PanelUiUtil.createBrowsePushButton(dialog, tIdentity);
		bIdentityBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(dialog, project, new String[]{"id"}, tIdentity));

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
		bOK.setText(BpmnMessages.getString(("ok_label")));
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bOK.setLayoutData(gd);
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isFieldsDirty()) {
					isDirty = true;
					model.identity = tIdentity.getText();
					model.cert = tCertFolder.getText();
					saveFields();
				}
				dialog.dispose();
			}
		});
		
		bCancel = new Button(butComp, SWT.PUSH);
		bCancel.setText(BpmnMessages.getString("cancel_label"));
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
		return (!tIdentity.getText().equals(model.identity) ||
				!tCertFolder.getText().equals(model.cert));
	}
	
	public abstract void saveFields();
	
	protected Listener getChangeListener(final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (key.equals(WsSslConfigModel.ID_CERT_FOLDER)) {
				} else if (key.equals(WsSslConfigModel.ID_IDENTITY)) {
				} else if (key.equals(WsSslConfigModel.ID_TRUST_STORE_PASSWD)) {
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
		valid &= PanelUiUtil.validateTextField(tCertFolder, tCertFolder.getEnabled(), false);
		if (tCertFolder.getEnabled())
			valid &= PanelUiUtil.validateTextField(tCertFolder, true, false);
		return valid;
	}
	
	public boolean isDirty() {
		return isDirty;
	}
}