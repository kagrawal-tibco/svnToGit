package com.tibco.cep.studio.dashboard.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.Assertion;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementSelector;

public class GenericSelectionDialog extends TitleAreaDialog {

	private LocalElement[] elementChoices;

	private boolean multiSelect;

	private String dialogTitle = "Element selection dialog";

	private String messagePrompt = "Select an element from the list.";

	private ElementSelector viewer;

	private List<LocalElement> selectionList = new ArrayList<LocalElement>();

	public GenericSelectionDialog(LocalElement[] elementChoices, String dialogTitle, String messagePrompt, boolean multiSelect) {
		super(null);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		Assertion.isNull(elementChoices);
		this.elementChoices = elementChoices;
		this.messagePrompt = messagePrompt;
		this.dialogTitle = dialogTitle;
		this.multiSelect = multiSelect;
	}

	public GenericSelectionDialog(LocalElement[] elementChoices, boolean multiSelect) {
		super(null);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		Assertion.isNull(elementChoices);
		this.elementChoices = elementChoices;
		this.multiSelect = multiSelect;
	}

	protected Control createDialogArea(Composite parent) {
		// create the top level composite for the dialog area
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setFont(parent.getFont());

		// Build the separator line
		Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		viewer = new ElementSelector(parent, elementChoices, multiSelect, true);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				selectionList = viewer.getSelectedElements();
				validate();
			}
			
		});
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				selectionList = viewer.getSelectedElements();
				validate();
				if (getButton(OK) != null && getButton(OK).isEnabled() == true) {
					okPressed();
				}
			}
			
		});

		setTitle(dialogTitle);
		setMessage(messagePrompt);

		validate();
		
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.widthHint = 800;
		layoutData.heightHint = 250;
		viewer.getTable().setLayoutData(layoutData);
		
		return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(dialogTitle);
	}

	public List<LocalElement> getSelections() {
		return selectionList;
	}

	public LocalElement getSelection() {
		if (false == selectionList.isEmpty()) {
			return (LocalElement) selectionList.get(0);
		}
		return null;
	}

	private void validate() {
		Button b = getButton(OK);
		if (null != b) {
			if (null == selectionList || true == selectionList.isEmpty()) {
				b.setEnabled(false);
			} else {
				b.setEnabled(true);
				setMessage("Press OK to continue");
			}
		}
	}

	/**
	 * Overriding to insert a call to validate()
	 */
	protected Control createButtonBar(Composite parent) {
		Control buttonBar = super.createButtonBar(parent);
		validate();
		return buttonBar;
	}
}
