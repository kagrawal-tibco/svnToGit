package com.tibco.cep.studio.wizard.hawk.pages;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.wizard.hawk.HawkNewWizard;
import com.tibco.cep.studio.wizard.hawk.HawkWizardConstants;
import com.tibco.cep.studio.wizard.hawk.util.Messages;

public class SelectChannelPage extends HawkWizardPage {
	private String channelName;
	private String[] items;

	public SelectChannelPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.selectChannel.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.selectChannel.desc"));
		setPageComplete(false);
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();

		layout.numColumns = 1;

		container.setLayout(layout);

		Label label = new Label(container, SWT.LEFT);
		label.setText("Hawk Channel List:");

		final List list = new List(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setItems(items);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		list.setLayoutData(gd);

		list.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				if (list.getSelectionIndex() > -1) {
					channelName = items[list.getSelectionIndex()];
					getContainer().showPage(getNextPage());
				}
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (list.getSelectionIndex() > -1) {
					channelName = items[list.getSelectionIndex()];
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("hawk.wizard.page.selectChannel.tip"));
				}
			}

		});

		setControl(container);
	}

	public void prepareData() {
		Set<String> channelList = ((HawkNewWizard) getWizard()).getChannelMap().keySet();
		items = new String[channelList.size()];
		items = ((HawkNewWizard) getWizard()).getChannelMap().keySet().toArray(items);
		int choice = ((HawkNewWizard) getWizard()).getWizardType();
		if (choice == HawkNewWizard.WIZARD_TYPE_DEST_AND_EVENT) {
			setNextPage((HawkWizardPage) getWizard().getPage(HawkWizardConstants.PAGE_NAME_MONITORTYPE));
		} else {
			setNextPage((HawkWizardPage) getWizard().getPage(HawkWizardConstants.PAGE_NAME_AGENT));
		}
		setPrepared(true);
	}

}
