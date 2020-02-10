package com.tibco.cep.studio.wizard.hawk.pages;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.sharedresource.hawk.HawkModelMgr;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.wizard.hawk.HawkNewWizard;
import com.tibco.cep.studio.wizard.hawk.HawkWizardConstants;
import com.tibco.cep.studio.wizard.hawk.util.Messages;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;

public class HawkAgentPage extends HawkWizardPage {
	private String agentName;
	private String[] items;
	private List agentsList;

	public HawkAgentPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.agent.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.agent.desc"));
		this.setPageComplete(false);
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void createControl(Composite parent) {

		final Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		Label label = new Label(container, SWT.LEFT);
		label.setText(Messages.getString("agent.list.label"));

		agentsList = new List(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		agentsList.setItems(items);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		agentsList.setLayoutData(gd);
		agentsList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (agentsList.getSelectionIndex() > -1) {
					agentName = items[agentsList.getSelectionIndex()];
					getContainer().showPage(getNextPage());
				}
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] sel = agentsList.getSelectionIndices();
				if (sel != null && sel.length > 0) {
					agentName = items[agentsList.getSelectionIndex()];
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("hawk.wizard.page.agent.tip"));
				}
			}
		});

		Button refreshButton = new Button(container, NONE);
		refreshButton.setText("Refresh");
		GridData layoutData = new GridData();
		refreshButton.setLayoutData(layoutData);
		refreshButton.addSelectionListener(new RefreshListener());

		setControl(container);
	}

	/**
	 * create HawkConsoleBase and get detected agent list
	 */
	public void prepareData() {
		try {
			HawkNewWizard hawkNewWizard = (HawkNewWizard) getWizard();
			String channelName = ((SelectChannelPage) hawkNewWizard.getPage(HawkWizardConstants.PAGE_NAME_CHANNEL))
					.getChannelName();
			Channel channel = hawkNewWizard.getChannelMap().get(channelName);
			DriverConfig driverConfig = channel.getDriver();

			Map<String, Object> props = new HashMap<String, Object>();
			// if channel use properties configuration
			if (driverConfig.getConfigMethod() == CONFIG_METHOD.PROPERTIES) {
				EList<Entity> elist = driverConfig.getProperties().getProperties();
				for (int i = 0; i < elist.size(); i++) {
					if (elist.get(i) instanceof SimpleProperty) {
						SimpleProperty sp = (SimpleProperty) elist.get(i);
						props.put(sp.getName(), sp.getValue());
					}
				}
			}
			// if channel use shared resources
			else {
				String reference = driverConfig.getReference();
				IResource resource = hawkNewWizard.getProject().getFile(reference);
				SharedResModelMgr modelMgr = new HawkModelMgr(resource);
				modelMgr.parseModel();
				props = modelMgr.getModel().values;
			}
			items = null;
			HawkConsoleBase consoleBase = createHawkConsoleBase(props);
			((HawkNewWizard) getWizard()).setHawkConsoleBase(consoleBase);
			int times = 0;
			while (items == null || items.length < 1) {
				Thread.sleep(5000);
				items = consoleBase.getAllAgentNames();
				times++;
				if (times > 10) {
					throw new Exception("Time out!");
				}
			}
			if (agentsList != null) {
				agentsList.setItems(items);
				setPageComplete(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(getShell(), "HawkConsole_Error",
					Messages.getString("hawk.wizard.error.hawkConsole.failed"));
			setPrepared(false);
			return;
		} catch (NoClassDefFoundError e) {
			MessageDialog.openError(getShell(), "Classpath_Error", Messages.getString("hawk.wizard.error.classpath"));
			setPrepared(false);
			return;
		}
		setPrepared(true);
	}

	/**
	 * 
	 * @param type
	 *            transport type "rv" or "ems"
	 * @param consoleBase
	 * @param props
	 * @throws Exception
	 */
	private HawkConsoleBase createHawkConsoleBase(Map<String, Object> props) throws Exception {
		HawkConsoleBase consoleBase = null;
		String hawkDomain = (String) props.get(HawkConstants.CHANNEL_PROPERTY_HAWKDOAMIN);
		String type = (String) props.get(HawkConstants.CHANNEL_PROPERTY_TRANSPORT);
		if (HawkConstants.TRANSPORT_TYPE_EMS.equals(type)) {

			HashMap<String, String> transportEnv = new HashMap<String, String>();

			String url = (String) props.get(HawkConstants.CHANNEL_PROPERTY_SERVER_URL);
			String username = (String) props.get(HawkConstants.CHANNEL_PROPERTY_USERNAME);
			String password = (String) props.get(HawkConstants.CHANNEL_PROPERTY_PASSWORD);
			if (username != null && !username.equals("")) {
				url += " " + username;
			}
			if (password != null && !password.equals("")) {
				url += " " + password;
			}
			transportEnv.put(HawkConsoleBase.HAWK_CONSOLE_PROPERTY_EMSTRANSPORT, url);
			consoleBase = new HawkConsoleBase(transportEnv, hawkDomain, false);
		} else {

			String service = (String) props.get(HawkConstants.CHANNEL_PROPERTY_RVSERVICE);
			String network = (String) props.get(HawkConstants.CHANNEL_PROPERTY_RVNETWORK);
			String daemon = (String) props.get(HawkConstants.CHANNEL_PROPERTY_RVDAEMON);
			consoleBase = new HawkConsoleBase(service, network, daemon, hawkDomain, false);
		}
		return consoleBase;
	}

	class RefreshListener implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			HawkNewWizard hawkNewWizard = ((HawkNewWizard) getWizard());
			HawkConsoleBase consoleBase = hawkNewWizard.getHawkConsoleBase();
			items = consoleBase.getAllAgentNames();
			HawkAgentPage.this.agentsList.setItems(items);
		}
	}
}
