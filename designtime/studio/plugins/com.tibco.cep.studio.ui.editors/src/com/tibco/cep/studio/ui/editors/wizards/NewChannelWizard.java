package com.tibco.cep.studio.ui.editors.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;


public class NewChannelWizard extends AbstractNewEntityWizard<NewChannelCreationWizardPage> {

	private String driverType;
	public NewChannelWizard(){
		setWindowTitle(Messages.getString("new.channel.wizard.title"));
//		setDefaultPageImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/iconProcess48x48.png"));
	}
	
	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception{
		driverType = ((NewChannelCreationWizardPage)page).getDriverType();
		Channel newChannel = ChannelFactory.eINSTANCE.createChannel();
		populateDriverConfig(newChannel, driverType);
		
		populateEntity(filename, newChannel);
		StudioResourceUtils.persistEntity(newChannel, baseURI,project,monitor);			
	}

	private void populateDriverConfig(Channel newChannel, String driver_type){
		DRIVER_TYPE driverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driverType.setName(driver_type);
		DriverConfig newDriverConfig = (DriverManagerConstants.DRIVER_HTTP == driverType.getName().intern()) ? 
				ChannelFactory.eINSTANCE.createHttpChannelDriverConfig() : ChannelFactory.eINSTANCE.createDriverConfig();
		newChannel.setDriver(newDriverConfig);
		newDriverConfig.setDriverType(driverType);
		newDriverConfig.setConfigMethod(CONFIG_METHOD.get(1));
		//newDriverConfig.setReference(driver.getReference());
		newDriverConfig.setLabel(driver_type.toUpperCase());
		newDriverConfig.setChannel(newChannel);
	}
	
	public void addPages() {
		try {
			if(_selection != null){
				project =  StudioResourceUtils.getProjectForWizard(_selection);
			}
			page = new NewChannelCreationWizardPage(getWizardTitle(),_selection, getEntityType(), this);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_CHANNEL;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.channel.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.channel.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/channelWizard.png");
	}
}