package com.tibco.cep.studio.wizard.hawk.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.wizard.hawk.HawkNewWizard;
import com.tibco.cep.studio.wizard.hawk.util.Messages;

public class ChooseWizardPage extends HawkWizardPage {

	public ChooseWizardPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.chooseWizard.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.chooseWizard.desc"));
		this.setPrepared(true);
		this.setPageComplete(true);
		this.setPrepared(true);
	}

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		final Button[] radios = new Button[2];
		radios[0] = new Button(container, SWT.RADIO);
		radios[0].setSelection(true);
		radios[0].setText("Create Destination and Event");

		radios[1] = new Button(container, SWT.RADIO);
		radios[1].setText("Create Event for Hawk Catalog Function");

		SelectionListener selectionListener = new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < radios.length; i++) {
					if (radios[i] == e.getSource()) {
						((HawkNewWizard) getWizard()).setWizardType(i);
						return;
					}
				}
			}

		};
		radios[0].addSelectionListener(selectionListener);
		radios[1].addSelectionListener(selectionListener);
		setControl(container);
	}

}
