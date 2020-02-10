package com.tibco.cep.studio.sb.ui.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.driver.sb.SBConstants;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class SBChannelCreationWizardPage extends EntityFileCreationWizard {

	private Text _typeDestName;

	public SBChannelCreationWizardPage(String pageName,
			IStructuredSelection selection, String type) {
		super(pageName, selection, type);
		setFileExtension(IndexUtils.CHANNEL_EXTENSION);
	}

	@Override
	protected InputStream getInitialContents() {

		Channel newChannel = ChannelFactory.eINSTANCE.createChannel();
		populateDriverConfig(newChannel, "StreamBase");

		newChannel.setName(new Path(getFileName()).removeFileExtension().toString());
		newChannel.setDescription(getTypeDesc());
		newChannel.setFolder(StudioResourceUtils.getFolder(getModelFile()));
		newChannel.setNamespace(StudioResourceUtils.getFolder(getModelFile()));
		newChannel.setGUID(GUIDGenerator.getGUID());
		newChannel.setOwnerProjectName(project.getName());
		
		try {
			return IndexUtils.getEObjectInputStream(newChannel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void populateDriverConfig(Channel newChannel, String driver_type){
		DRIVER_TYPE driverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driverType.setName(driver_type);
		DriverManager driverManager = StudioCorePlugin.getDefault().getDriverManager();
		DriverConfig newDriverConfig = (DriverManagerConstants.DRIVER_HTTP == driverType.getName().intern()) ? 
				ChannelFactory.eINSTANCE.createHttpChannelDriverConfig() : ChannelFactory.eINSTANCE.createDriverConfig();
		newChannel.setDriver(newDriverConfig);
		newDriverConfig.setDriverType(driverType);
		newDriverConfig.setConfigMethod(CONFIG_METHOD.get(0));
		{
			NewSBWizard wiz = (NewSBWizard) getWizard();
			SBServerDetails details = wiz.getServerDetails();
			PropertyMap chPropsMap = ModelFactory.eINSTANCE.createPropertyMap();
			newDriverConfig.setProperties(chPropsMap);
			SimpleProperty prop = ModelFactory.eINSTANCE.createSimpleProperty();
			prop.setName(SBConstants.CHANNEL_PROPERTY_SERVER_URI.localName);
			prop.setValue(details.getSbServerURI(false));
			chPropsMap.getProperties().add(prop);
			
			prop = ModelFactory.eINSTANCE.createSimpleProperty();
			prop.setName(SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.localName);
			prop.setValue(details.getUsername(false));
			chPropsMap.getProperties().add(prop);
			
			prop = ModelFactory.eINSTANCE.createSimpleProperty();
			prop.setName(SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.localName);
			prop.setValue(details.getPassword(false));
			chPropsMap.getProperties().add(prop);
		}
		
		newDriverConfig.setLabel(driver_type.toUpperCase());
		newDriverConfig.setChannel(newChannel);
		
		String destName = getDestinationName();
		if (destName != null) {
			Destination dest = ChannelFactory.eINSTANCE.createDestination();
			dest.setName(destName);
			dest.setDescription("");
			dest.setSerializerDeserializerClass(driverManager.getDefaultSerializer(driverType).getSerializerClass());
			dest.setDriverConfig(newDriverConfig);
			NewSBWizard wiz = (NewSBWizard) getWizard();
			String eventURI = wiz.getEventURI();
			dest.setEventURI(eventURI);
			dest.setOwnerProjectName(project.getName());
			dest.setGUID(GUIDGenerator.getGUID());
			
			PropertyMap instance = ModelFactory.eINSTANCE.createPropertyMap();
			dest.setProperties(instance);
			DestinationDescriptor destinationDescriptor = driverManager.getDestinationDescriptor(driverType);
			EList<PropertyDescriptorMapEntry> descriptors = destinationDescriptor.getDescriptors();
			for (PropertyDescriptorMapEntry descMapEntry : descriptors) {
				String key = descMapEntry.getKey();
				PropertyDescriptor value = descMapEntry.getValue();
				SimpleProperty prop = ModelFactory.eINSTANCE.createSimpleProperty();
				prop.setName(key);
				if (SBConstants.SB_DEST_PROP_STREAM_NAME.equals(key)) {
					String streamName=wiz.getSelectedSchemaName();
					prop.setValue(streamName);
				} else if (SBConstants.SB_DEST_PROP_CLIENT_TYPE.equals(key) && wiz.isInputStreamSchema()) {
					prop.setValue(SBConstants.SB_DEST_CLIENT_TYPE_ENQUEUER);
				} else {
					prop.setValue(value.getDefaultValue());
				}
				
				instance.getProperties().add(prop);
			}
			newDriverConfig.getDestinations().add(dest);
		}
		

	}

	@Override
	protected void createTypeDescControl(Composite parent) {
		super.createTypeDescControl(parent);

		Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.getString("new.sbchannel.wizard.destination.name"));
		_typeDestName = new Text(parent, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		_typeDestName.setLayoutData(gd);
	
	}
	
	public String getDestinationName() {
		if (!_typeDestName.isDisposed()) {
			return _typeDestName.getText();
		}
		return null;
	}

}
