package com.tibco.cep.ws.util.wsdlimport.channel;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.config.sharedresources.sharedhttp.Config;
import com.tibco.be.util.config.sharedresources.sharedhttp.HttpSharedResource;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpFactory;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage;
import com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationContext;



public class WsdlHttpChannelBuilder
		extends AbstractWsdlChannelBuilder
{

	private static final int DEFAULT_HTTP_PORT = 80;
	private static final int DEFAULT_HTTPS_PORT = 443;
	
	
	public WsdlHttpChannelBuilder(
			IProject project,
			String transportFolderName)
	{
		super(project, transportFolderName);

		SharedhttpPackageImpl.eINSTANCE.eClass();
		final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
		registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
		this.sharedResources.setResourceFactoryRegistry(registry);
	}
	
	
	@Override
	protected String getOrAddConnectionResource(
            String existingResourcePath,
			BindingContext bindingContext,
			URI endpointUri)
			throws Exception
	{
	    //TODO merge with existing when possible
		final String path = this.makeSharedResourcePath(bindingContext, "." + SharedhttpPackage.eNAME);
		final boolean isSecure = "https".equals(endpointUri.getScheme()); 		
		final String host = endpointUri.getHost();
		int port = endpointUri.getPort();
		if (port < 0) {
			port = isSecure ? DEFAULT_HTTPS_PORT : DEFAULT_HTTP_PORT;
		}
				
        final org.eclipse.emf.common.util.URI uri =
                org.eclipse.emf.common.util.URI.createFileURI(this.project.getLocation().toString() + path);

//        EObject existing = CommonIndexUtils.loadEObject(new URI(uri.toString()), false);
        
		final SharedHttpRoot root = SharedhttpFactory.eINSTANCE.createSharedHttpRoot();
		final HttpSharedResource hsr = SharedhttpFactory.eINSTANCE.createHttpSharedResource();		
		final Config config = SharedhttpFactory.eINSTANCE.createConfig();
		root.setHttpSharedResource(hsr);
		hsr.setConfig(config);
		
		config.setHost(host);		
		config.setPort(port);
		config.setUseSsl(isSecure);		
		
		final Resource resource = this.sharedResources.createResource(uri);
		resource.getContents().add(root);
		
		return path;
	}
	

	@Override
	protected String getDriverTypeName()
	{
		return DriverManagerConstants.DRIVER_HTTP; 
	}

	
	@Override
	public Destination[] processOperation(
			OperationContext opContext,
			String soapAction,
			SimpleEvent inEvent,
			SimpleEvent outEvent,
			String ruleFnURI)	
			throws Exception
	{
//		final Channel channel = this.projectPathToChannel.get(channelPath);
		
		final Channel channel = (Channel) opContext.getOperationReferenceContext().getBindingContext()
				.getProperties().get("channel");
		
		if ((null == channel) || (null == soapAction) || soapAction.isEmpty()) {
			return null;
		}

		String destinationName = BEStringUtilities.startsWithInValidTibcoIdentifier(soapAction)
				? soapAction.substring(1)
				: soapAction;
		destinationName = BEStringUtilities.convertToValidTibcoIdentifier(destinationName, true)
				.replace("[\\(\\)]", "_");
		
		final String channelUri = channel.getFullPath();
		final Destination destination = ChannelFactory.eINSTANCE.createDestination();
		final DriverConfig driverConfig = channel.getDriver();		
		
		destination.setDriverConfig(driverConfig);
		destination.setName(destinationName);
		destination.setOwnerProjectName(channel.getOwnerProjectName());
		destination.setProperties(ModelFactory.eINSTANCE.createPropertyMap());
		destination.setSerializerDeserializerClass("com.tibco.cep.driver.http.serializer.SOAPMessageSerializer");

		driverConfig.getDestinations().add(destination);

		if (inEvent != null) {
			inEvent.setChannelURI(channelUri);
			inEvent.setDestinationName(destinationName);
			destination.setEventURI(inEvent.getFullPath());
		}
		
		if (outEvent != null) {
			outEvent.setChannelURI(channelUri);
			outEvent.setDestinationName(destinationName);
		}
		
        return new Destination[]{destination};
	}


}
