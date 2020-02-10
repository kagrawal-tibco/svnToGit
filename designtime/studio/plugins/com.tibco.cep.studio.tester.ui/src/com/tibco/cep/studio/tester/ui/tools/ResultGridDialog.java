package com.tibco.cep.studio.tester.ui.tools;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public abstract class ResultGridDialog extends TitleAreaDialog {

	public ResultGridDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		return contents;
	}

	/**
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout glayout1 = new GridLayout();
		glayout1.marginWidth = glayout1.marginHeight = 0;
		glayout1.numColumns = 1;
		glayout1.makeColumnsEqualWidth = false;
	
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 500;
		data.heightHint = 120;
		composite.setLayoutData(data);
		composite.setLayout(glayout1);
		
		Table table = new Table(composite, SWT.FULL_SELECTION | SWT.MULTI);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setLinesVisible(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 480;
		data.heightHint =150;
		table.setLayoutData(gd);
		table.setHeaderVisible(true);
		createColumns(table);
		addResults(table);
		return composite;
	}
	
	protected abstract void createColumns(Table table);
	
	protected abstract void addResults(Table table);

	/**
	 * Creates the buttons for the button bar
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
	}

}