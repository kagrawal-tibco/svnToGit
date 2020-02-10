package com.tibco.cep.studio.wizard.hawk.pages;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
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
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;
import com.tibco.hawk.jshma.util.NamedTabularData;

public class HawkMicroAgentPage extends HawkWizardPage {
	static final String LABEL_TEXT = "Available Hawk MicroAgent of Agent: ";
	private String microAgentName;
	private Map<String, String> microAgentsMap;
	private String[] items;
	private List microAgentsList;
	private Label label;

	public HawkMicroAgentPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.microAgent.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.microAgent.desc"));
		this.setPageComplete(false);
	}

	public String getMicroAgentName() {
		return microAgentName;
	}

	public void setMicroAgentName(String microAgentName) {
		this.microAgentName = microAgentName;
	}

	public void createControl(Composite parent) {
		// getMicroAgents();

		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		label = new Label(container, SWT.LEFT);
		String agentName = ((HawkAgentPage) getWizard().getPage(HawkWizardConstants.PAGE_NAME_AGENT)).getAgentName();
		label.setText(LABEL_TEXT + "[" + agentName + "]");

		microAgentsList = new List(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		microAgentsList.setItems(items);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		microAgentsList.setLayoutData(gd);
		microAgentsList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (microAgentsList.getSelectionIndex() > -1) {
					microAgentName = microAgentsMap.get(items[microAgentsList.getSelectionIndex()]);
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("hawk.wizard.page.microAgent.tip"));
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (microAgentsList.getSelectionIndex() > -1) {
					microAgentName = microAgentsMap.get(items[microAgentsList.getSelectionIndex()]);
					getContainer().showPage(getNextPage());
				}
			}
		});

		setControl(container);
	}

	/**
	 * get Hawk MicroAgent list
	 */
	public void prepareData() {
		HawkNewWizard hawkNewWizard = ((HawkNewWizard) getWizard());
		HawkConsoleBase consoleBase = hawkNewWizard.getHawkConsoleBase();

		int num = 0;
		String agentName = ((HawkAgentPage) hawkNewWizard.getPage(HawkWizardConstants.PAGE_NAME_AGENT)).getAgentName();

		NamedTabularData result = consoleBase.getAllMicroAgentInfo(agentName);
		num = result.rowCount;

		while (num < 1) {
			result = consoleBase.getAllMicroAgentInfo(((HawkAgentPage) hawkNewWizard
					.getPage(HawkWizardConstants.PAGE_NAME_AGENT)).getAgentName());
			num = result.rowCount;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				MessageDialog.openError(getShell(), "Error", e.getMessage());
				setPrepared(false);
				return;
			}
		}
		items = new String[num];
		microAgentsMap = new HashMap<String, String>();

		for (int i = 0; i < result.rowCount; i++) {
			items[i] = (String) result.get(i, "Display Name");
			microAgentsMap.put((String) result.get(i, "Display Name"), (String) result.get(i, "MicroAgent Name"));
		}
		if (microAgentsList != null) {
			microAgentsList.setItems(items);
			label.setText(LABEL_TEXT + "[" + agentName + "]");
			setPageComplete(false);
		}

		setPrepared(true);
	}

}
