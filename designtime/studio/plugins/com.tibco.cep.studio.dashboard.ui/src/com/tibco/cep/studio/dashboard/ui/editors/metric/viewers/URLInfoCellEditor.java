package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.forms.components.TextAndDialogCellEditor;

public class URLInfoCellEditor extends TextAndDialogCellEditor {

	public URLInfoCellEditor(Composite comp, String columnName, AbstractSaveableEntityEditorPart editor) {
		super(comp, columnName, editor);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		Table table = (Table) cellEditorWindow.getParent();
		TableItem[] selectedProperty = table.getSelection();
		LocalAttribute attribute = (LocalAttribute) selectedProperty[0].getData();
		if (columnName.equals("UrlLink")) {
			try {
				URLInfoSelector urlInfoSelector = new URLInfoSelector(parent.getShell(), attribute.getURLInfo());
				int returnCode = urlInfoSelector.open();
				if (returnCode == URLInfoSelector.OK) {
					attribute.setURLInfo(urlInfoSelector.getURLInfo());
					String[] a = urlInfoSelector.getURLInfo();
					//	return a[0] + "-" + a[1];
					return a[1];

				} else if (returnCode == URLInfoSelector.REMOVE) {
					attribute.setURLInfo(new String[] { "", "" });
					return "";
				}
			} catch (Exception e) {
				DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not edit url link of " + " in " + editor.getEditorInput(), e));
			}
		}
		return null;
	}

	class URLInfoSelector extends TitleAreaDialog {

		private static final String TITLE = "URL Information Editor";

		private static final String OK_LABEL = "Ok";

		private static final String REMOVE_LABEL = "Remove";

		private static final int REMOVE = 9999;

		private String[] urlInfo;

		private Text urlNameTxtFld;

		private Text urlLinkTxtArea;

		public URLInfoSelector(Shell parentShell, String[] urlInfo) {
			super(parentShell);
			setHelpAvailable(false);
			setTitleImage(DashboardUIPlugin.getInstance().getImageRegistry().get("url_wizard.png"));
			// the below statement allows us to make the dialog resizable
			setShellStyle(getShellStyle() | SWT.RESIZE);
			this.urlInfo = urlInfo;
		}

		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText(TITLE);
			shell.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get("url_16x16.gif"));
		}

		@Override
		protected Control createDialogArea(Composite parent) {

			Composite urlInfoEditingComposite = new Composite(parent, SWT.NONE);

			GridData gData = new GridData(GridData.FILL_BOTH);
			gData.horizontalIndent = 5;
			gData.verticalIndent = 5;
			urlInfoEditingComposite.setLayoutData(gData);

			GridLayout gridLayout = new GridLayout(2, false);
			gridLayout.marginLeft = 5;
			gridLayout.marginRight = 5;
			gridLayout.marginTop = 5;
			gridLayout.marginBottom = 5;
			urlInfoEditingComposite.setLayout(gridLayout);

			Label urlNameLbl = new Label(urlInfoEditingComposite, SWT.RIGHT);
			urlNameLbl.setText("Name");
			gData = new GridData(GridData.END);
			urlNameLbl.setLayoutData(gData);

			urlNameTxtFld = new Text(urlInfoEditingComposite, SWT.BORDER);
			gData = new GridData(GridData.FILL_HORIZONTAL);
			urlNameTxtFld.setLayoutData(gData);

			Label urlLinkLbl = new Label(urlInfoEditingComposite, SWT.RIGHT);

			urlLinkLbl.setText("Link");
			gData = new GridData(GridData.END);
			urlLinkLbl.setLayoutData(gData);

			urlLinkTxtArea = new Text(urlInfoEditingComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
			gData = new GridData(GridData.FILL_BOTH);
			gData.widthHint = 200;
			gData.heightHint = 100;
			urlLinkTxtArea.setLayoutData(gData);

			setWidgetsState();
			attachListenersToWidgets();

			return urlInfoEditingComposite;
		}

		// creating and laying out Remove, Save and Cancel buttons
		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			Button removeBtn = createButton(parent, REMOVE, REMOVE_LABEL, false);
			// removeBtn.setEnabled(true);
			removeBtn.setEnabled(false);
			if (urlInfo != null && urlInfo.length > 1 && (urlInfo[0].length() > 0 || urlInfo[1].length() > 0)) {
				removeBtn.setEnabled(true);
			}

			removeBtn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					boolean okPressed = MessageDialog.openConfirm(getShell(), TITLE, "Are you sure you want to delete the URL ?");
					if (okPressed == true) {
						// set return code as REMOVE and close the dialog
						setReturnCode(REMOVE);
						close();
					}
				}
			});

			Button okBtn = createButton(parent, IDialogConstants.OK_ID, OK_LABEL, false);
			okBtn.setEnabled(false);

			Button cancelBtn = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
			cancelBtn.setEnabled(true);

		}

		private void attachListenersToWidgets() {
			urlNameTxtFld.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					updateUIState(false);
				}

			});

			urlLinkTxtArea.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					updateUIState(true);
				}

			});
		}

		private void updateUIState(boolean processLink) {

			setMessage(null, IMessageProvider.ERROR);
			setMessage(null, IMessageProvider.WARNING);

			String name = urlNameTxtFld.getText();
			String link = urlLinkTxtArea.getText();

			if ((name != null && name.trim().length() > 0) || (link != null && link.trim().length() > 0)) {
				getButton(REMOVE).setEnabled(true);
			}
			if ((name == null || name.trim().length() == 0) && (link == null || link.trim().length() == 0)) {
				getButton(REMOVE).setEnabled(false);
			}

			// check if the name is empty, name can never be empty
			if (name == null || name.trim().length() == 0) {
				setMessage("Enter a valid name", IMessageProvider.ERROR);
				getButton(IDialogConstants.OK_ID).setEnabled(false);
				return;
			}

			// check if the name contains non alphanumeric characters
			char[] charArray = name.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (Character.isLetterOrDigit(charArray[i]) == false && Character.isSpaceChar(charArray[i]) == false) {
					setMessage("Enter a valid name", IMessageProvider.ERROR);
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				}
			}

			if (processLink == false) {
				if (link == null || link.trim().length() == 0) {
					setMessage("Enter a valid link");
					return;
				}
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			}

			// name is not invalid, check if the link can be empty
			if (urlInfo[1] == null) {
				// initial URL is empty, then the new URL cannot be empty
				if (link == null || link.trim().length() == 0) {
					// new URL is empty, ask user to enter URL
					setMessage("Enter a valid link", IMessageProvider.ERROR);
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				}
				// new URL is not empty, check if it is valid
				else if (validateLink(link) == false) {
					// new URL is not valid
					setMessage("Link has invalid syntax", IMessageProvider.WARNING);
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
			// name is not valid and link is not empty, check for syntax
			if (validateLink(link) == false) {
				setMessage("Link is either blank or has invalid syntax", IMessageProvider.WARNING);
				getButton(IDialogConstants.OK_ID).setEnabled(false);
				return;
			}
			// everything is OK
			setMessage("Click " + OK_LABEL + " to save", IMessageProvider.INFORMATION);
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}

		private void setWidgetsState() {
			if (StringUtil.isEmpty(urlInfo[0]) == false && StringUtil.isEmpty(urlInfo[1]) == false) {
				urlNameTxtFld.setText(urlInfo[0]);
				urlLinkTxtArea.setText(urlInfo[1]);
			} else {
				setMessage("Enter a valid name and link");
			}
		}

		private boolean validateLink(String link) {
			if (link != null && link.trim().length() != 0) {
				// TODO validate link and return boolean
				return true;
			}
			// we don't have a link entered, user may want to delete
			// return true;
			// changed the logic. If the link needs to be removed, the the user should click on Remove button
			return false;

		}

		@Override
		protected void okPressed() {
			urlInfo[0] = urlNameTxtFld.getText();
			urlInfo[1] = urlLinkTxtArea.getText();
			super.okPressed();
		}

		public String[] getURLInfo() {
			return urlInfo;
		}

	}
}