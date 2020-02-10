package com.tibco.cep.studio.wizard.hawk.pages;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.studio.wizard.hawk.HawkWizardConstants;
import com.tibco.cep.studio.wizard.hawk.util.Messages;

public class SelectMonitorTypePage extends HawkWizardPage {
	private String monitorType = HawkConstants.MONITOR_TYPE_SUBSCRIPTION;
	final static String PREFIX = "MONITOR_TYPE";
	private String[] items;

	public SelectMonitorTypePage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.selectMonitorType.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.selectMonitorType.desc"));
		this.setPageComplete(false);
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		Label label = new Label(container, SWT.LEFT);
		label.setText("Monitor Type:");

		final List single = new List(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		single.setItems(items);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		single.setLayoutData(gd);

		single.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				if (single.getSelectionIndex() > -1) {
					monitorType = items[single.getSelectionIndex()];
					getContainer().showPage(getNextPage());
				}
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int[] sel = single.getSelectionIndices();

				if (sel != null && sel.length > 0) {
					monitorType = items[single.getSelectionIndex()];
					if (monitorType.equals(HawkConstants.MONITOR_TYPE_SUBSCRIPTION)) {
						setNextPage((HawkWizardPage) getWizard().getPage(HawkWizardConstants.PAGE_NAME_AGENT));
					} else {
						setNextPage((HawkWizardPage) getWizard().getPage(HawkWizardConstants.PAGE_NAME_NAME));
					}
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("hawk.wizard.page.selectMonitorType.tip"));
				}

			}

		});
		setControl(container);
	}

	public void prepareData() {
		Class<?> hawkConstants = null;
		ArrayList<String> itemsList = new ArrayList<String>();
		try {
			hawkConstants = Class.forName("com.tibco.cep.driver.hawk.HawkConstants");
			Field[] fields = hawkConstants.getDeclaredFields();

			for (Field field : fields) {
				if (field.getName().startsWith(PREFIX)) {

					itemsList.add((String) field.get(hawkConstants));
				}
			}
			items = new String[itemsList.size()];
			items = itemsList.toArray(items);
			setPrepared(true);
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
			setPrepared(false);
			return;
		}
	}

}
