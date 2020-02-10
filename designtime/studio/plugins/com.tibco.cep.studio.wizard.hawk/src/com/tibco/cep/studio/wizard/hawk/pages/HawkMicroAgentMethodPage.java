package com.tibco.cep.studio.wizard.hawk.pages;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.wizard.hawk.HawkNewWizard;
import com.tibco.cep.studio.wizard.hawk.HawkWizardConstants;
import com.tibco.cep.studio.wizard.hawk.util.Messages;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;
import com.tibco.hawk.jshma.util.NamedTabularData;

public class HawkMicroAgentMethodPage extends HawkWizardPage {
	static final String LABEL_TEXT = "Available Methods of MicroAgent: ";
	private String methodName;
	private String timeInterval = "10";
	private String arguments = "";
	private Label label;
	private String[] items;
	private List methodsList;
	private boolean showArguments = false;

	private Label timeLabel, argLabel;
	private Text timeIntervalText, argText;

	public HawkMicroAgentMethodPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.method.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.method.desc"));
		this.setPageComplete(false);
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public void createControl(Composite parent) {
		// getMicroAgentMethods();
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		label = new Label(container, SWT.LEFT);
		String microAgentName = ((HawkMicroAgentPage) getWizard().getPage(HawkWizardConstants.PAGE_NAME_MICROAGENT))
				.getMicroAgentName();
		label.setText(LABEL_TEXT + "[" + microAgentName + "]");

		methodsList = new List(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

		methodsList.setItems(items);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		methodsList.setLayoutData(gd);
		methodsList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int[] sel = methodsList.getSelectionIndices();
				if (sel != null && sel.length > 0) {
					methodName = items[methodsList.getSelectionIndex()];
					if (!methodName.equals("")) {
						updateStatus(null);
					}
					if (showArguments) {
						String[] tips = getToolTip();
						methodsList.setToolTipText(tips[0]);
						argText.setText(tips[1]);
					}
				} else {
					updateStatus(Messages.getString("hawk.wizard.page.method.tip"));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		timeLabel = new Label(container, SWT.LEFT);
		timeLabel.setText("Time Interval:");
		timeLabel.setVisible(showArguments);
		timeIntervalText = new Text(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		timeIntervalText.setLayoutData(gd);
		timeIntervalText.setText(timeInterval);
		timeIntervalText.setVisible(showArguments);

		argLabel = new Label(container, SWT.LEFT);
		argLabel.setText(Messages.getString("hawk.wizard.page.method.label.argument"));
		argLabel.setVisible(showArguments);
		argText = new Text(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		argText.setLayoutData(gd);
		argText.setVisible(showArguments);

		ModifyListener listener = new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				Text text = (Text) e.getSource();

				String name = text.getText();

				if (text == timeIntervalText) {
					timeInterval = name;
				} else if (text == argText) {
					arguments = name;
				}
			}

		};
		timeIntervalText.addModifyListener(listener);
		argText.addModifyListener(listener);
		setControl(container);
	}

	/**
	 * get Hawk MicroAgent Methods list
	 */
	public void prepareData() {
		HawkNewWizard hawkNewWizard = ((HawkNewWizard) getWizard());
		HawkConsoleBase consoleBase = hawkNewWizard.getHawkConsoleBase();
		String agentName = ((HawkAgentPage) hawkNewWizard.getPage(HawkWizardConstants.PAGE_NAME_AGENT)).getAgentName();
		String microAgentName = ((HawkMicroAgentPage) hawkNewWizard.getPage(HawkWizardConstants.PAGE_NAME_MICROAGENT))
				.getMicroAgentName();

		NamedTabularData result = consoleBase.getAllMethodInfo(agentName, microAgentName);
		ArrayList<String> methods = new ArrayList<String>();
		int choice = hawkNewWizard.getWizardType();
		for (int i = 0; i < result.rowCount; i++) {
			String methodName = (String) result.get(i, "Method Name");
			String methodType = (String) result.get(i, "Method Type");
			String methodIsAsync = (String) result.get(i, "Asyncronous");
			if (choice == HawkNewWizard.WIZARD_TYPE_DEST_AND_EVENT&&!HawkWizardConstants.MICROAGENT_METHOD_TYPE_ACTION.equals(methodType)) {
				methods.add(methodName);
			} else if (choice == HawkNewWizard.WIZARD_TYPE_EVENT_ONLY
					&& "false".equals(methodIsAsync)) {
				if(consoleBase.getMethodArguments(null, agentName, microAgentName, methodName)>0){
					methods.add(methodName);
				}
			}
		}
		if (methods.size() > 0) {
			items = new String[methods.size()];
			items = methods.toArray(items);
		} else {
			items = new String[1];
			items[0] = "";
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES);
			if(choice==HawkNewWizard.WIZARD_TYPE_EVENT_ONLY){
				mb.setMessage(Messages.getString("hawk.wizard.warning.noIncokeMethod"));
			}else{
				mb.setMessage(Messages.getString("hawk.wizard.warning.noSubscribeMethod"));
			}
			mb.open();
			setPrepared(false);
			return;
		}
		if (choice == 0) {
			showArguments = true;
		} else {
			showArguments = false;
		}
		if (timeLabel != null) {
			timeLabel.setVisible(showArguments);
			timeIntervalText.setVisible(showArguments);
			argLabel.setVisible(showArguments);
			argText.setVisible(showArguments);
		}
		if (methodsList != null) {
			methodsList.setItems(items);
			methodsList.setToolTipText("");
			label.setText(LABEL_TEXT + "[" + microAgentName + "]");
			setPageComplete(false);
		}
		setPrepared(true);
	}
	
	public String[] getToolTip(){
		
		HawkNewWizard hawkNewWizard = ((HawkNewWizard) getWizard());
		HawkConsoleBase consoleBase = hawkNewWizard.getHawkConsoleBase();
		String agentName = ((HawkAgentPage) hawkNewWizard.getPage(HawkWizardConstants.PAGE_NAME_AGENT))
				.getAgentName();
		String microAgentName = ((HawkMicroAgentPage) hawkNewWizard
				.getPage(HawkWizardConstants.PAGE_NAME_MICROAGENT)).getMicroAgentName();
		String[] toolTips=consoleBase.getToolTip(agentName, microAgentName, methodName);
		if(toolTips[0].equals("")){
			toolTips[0] = Messages.getString("hawk.wizard.warning.no.argument");
		}
		return toolTips;
	}

}
