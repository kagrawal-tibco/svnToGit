package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.MqttSSharedResourceImages;
import com.tibco.cep.sharedresource.ui.util.mqtt.Messages;


/**
 * @author ssinghal
 *
 */
public class NewMqttWizardPage extends NewSharedResourceWizardPage{
	
	private static final String PAGE_NAME = "NewMqttWizardPage";
	
	public NewMqttWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);setTitle(Messages.getString("new.mqtt.wizard.title", new Object[0]));
		setDescription(Messages.getString("new.mqtt.wizard.desc", new Object[0]));
		setImageDescriptor(MqttSSharedResourceImages.getImageDescriptor(MqttSSharedResourceImages.IMG_SHAREDRES_WIZ_MQTT));
		setFileExtension("sharedmqttcon");
	}

	

}
