package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.model.FTLDataModel;
import com.tibco.cep.studio.wizard.ftl.utils.FTLStringUtil;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;

public class SetNamePage extends FTLWizardPage {

	private String destName;
	private String eventFolderName = "Events";
	private String receiveEventName ;
	private String sendEventName;
	private Text destText;
    private Text eventFolderText;
    private Text receiveEventText;
    private Text sendEventText;
    
    private FTLDataModel newData;
	
	public String getEventFolderName() {
		return eventFolderName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	public void setEventFolderName(String eventFolderName) {
		this.eventFolderName = eventFolderName;
	}

	
	public String getReceiveEventName() {
		return receiveEventName;
	}

	public void setReceiveEventName(String receiveEventName) {
		this.receiveEventName = receiveEventName;
	}

	public String getSendEventName() {
		return sendEventName;
	}

	public void setSendEventName(String sendEventName) {
		this.sendEventName = sendEventName;
	}

	public SetNamePage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.setNameWizard.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.setNameWizard.desc"));
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		Label destLabel = new Label(container, SWT.LEFT);
		destLabel.setText("Destination Name:");
		destText = new Text(container, SWT.BORDER);
		destText.setLayoutData(gd);
		destText.setText(destName);
		
		Label eventFolderLabel = new Label(container, SWT.LEFT);
		eventFolderLabel.setText("Event Folder:");

		eventFolderText = new Text(container, SWT.BORDER);//
		eventFolderText.setLayoutData(gd);
		eventFolderText.setText(eventFolderName);

		Label receiveEventLabel = new Label(container, SWT.LEFT);
		receiveEventLabel.setText("Receive Event Name:");
		receiveEventText = new Text(container, SWT.BORDER);//
		receiveEventText.setLayoutData(gd);
		receiveEventText.setText(receiveEventName);

		Label sendEventLabel = new Label(container, SWT.LEFT);
		sendEventLabel.setText("Send Event Name:");
		sendEventText = new Text(container, SWT.BORDER);//
		sendEventText.setLayoutData(gd);
		sendEventText.setText(sendEventName);
	
		ModifyListener listener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {

				Text text = (Text) e.getSource();

				String name = text.getText();

				if (text == destText) {
					destName = name;
				} else if (text == eventFolderText) {
					eventFolderName = name;
				} else if (text == receiveEventText) {
					receiveEventName = name;
				} else if (text == sendEventText) {
					sendEventName = name;
				} 
				if (validateString(destName) && validateString(receiveEventName) && validateString(sendEventName)
						&& validateString(eventFolderName)) {
					
					newData.setDestName(destName);
					newData.setEventFolderName(eventFolderName);
					newData.setRecvEventName(receiveEventName);
					newData.setSendEventName(sendEventName);
					
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("ftl.wizard.page.setNameWizard.tip"));
				}
			}

		};

		if(destText != null) {
			destText.addModifyListener(listener);
		}

		if (eventFolderText != null) {
			eventFolderText.addModifyListener(listener);
		}

		if (receiveEventText != null) {
			receiveEventText.addModifyListener(listener);
		}
		if (sendEventText != null) {
			sendEventText.addModifyListener(listener);
		}
		setControl(container);
	}

	@Override
	protected void prepareData() {
		SelectAppPage appPage = (SelectAppPage)getWizard().getPage(FTLWizardConstants.PAGE_NAME_APP);
		SelectTransportPage transportPage = (SelectTransportPage)getWizard().getPage(FTLWizardConstants.PAGE_NAME_TRAN);
		
		newData = appPage.getNewModel();
		
		String tranName = transportPage.getSelectTran();// newData.getTransport();
		Map<String, List<String>> tranMap = transportPage.getTranMap();
		List<String> tranVs = tranMap.get(tranName);
		
		if(newData.getFormatName() == null) {
			newData.setFormatName("");
		}
		
//		String prefix = newData.getAppName()+UNDERSCORE+newData.getTransport()+UNDERSCORE+newData.getFormatName();
		String prefix = newData.getAppName();
		
		destName = FTLStringUtil.formatString(prefix + "_dest" + newData.hashCode()); 
		receiveEventName = FTLStringUtil.formatString(prefix+ "_recvEvent" + newData.hashCode()); 
		sendEventName = FTLStringUtil.formatString(prefix+ "_sendEvent" + newData.hashCode()); 
		
		newData.setDestName(destName);
		newData.setEventFolderName(eventFolderName);
		newData.setRecvEventName(receiveEventName);
		newData.setSendEventName(sendEventName);
		
		setPrepared(true);
		setPageComplete(true);
	}
	
	
	private boolean validateString(String str) {
		if (str != null&&str.length()>0) {
			char[] chars = str.toCharArray();
			if (!Character.isLetter(chars[0]) && chars[0] != '_')
				return false;
			for (int i = 1; i < chars.length; i++) {
				char ch = chars[i];

				if (!Character.isLetterOrDigit(ch) && ch != '_') {
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
		
	}
	

}
