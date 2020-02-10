package com.tibco.cep.ws.util.wsdlimport.channel;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.ws.util.WSDLImportUtil;
import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.PortContext;


public abstract class AbstractWsdlChannelBuilder
		implements WsdlChannelBuilder
{

	protected static final char PATH_SEPARATOR_CHAR = '/';
	protected static final String PATH_SEPARATOR = PATH_SEPARATOR_CHAR + "";
	protected static final Pattern URI_PATH_PATTERN = Pattern.compile("^(?:((?:[^/]*)+(?:/+[^/]+)*)/+)?([^/]+)/*");
	protected static final int URI_PATH_PATTERN_FOLDER_GROUP = 1;
	protected static final int URI_PATH_PATTERN_NAME_GROUP = 2;
	
	protected final IProject project;
	protected final String transportFolderName;
	
    protected final Set<Entity> createdEntities = new HashSet<Entity>();
    protected final Set<Entity> modifiedEntities = new HashSet<Entity>();
	protected final Map<String, Channel> projectPathToChannel = new HashMap<String, Channel>(); 
	protected final ResourceSet sharedResources = new ResourceSetImpl();		

	
	protected static class Path
	{			
		final String folder;
		final String file;
		
		public Path(
				String folder,
				String file)
		{
			this.folder = folder;
			this.file = file;
		}
	}


	public AbstractWsdlChannelBuilder(
				IProject project,
				String transportFolderName)
	{
			this.project = project;
			this.transportFolderName = transportFolderName;
	}


	protected Channel createChannel(
			Path channelPath)
	{
		final Channel channel = ChannelFactory.eINSTANCE.createChannel();
		WSDLImportUtil.populateEntity(
				this.project.getName(),
				channelPath.file,
				channelPath.folder + PATH_SEPARATOR_CHAR,
				channelPath.folder.isEmpty() ? PATH_SEPARATOR : channelPath.folder,
				channel);		
		
		return channel;
	}
	
	
	protected Path getChannelPath(
			URI uri)
	{
		String folderPath = null;
		String name = null;
		
		final String path = uri.getPath();
		if (null != path) {
			final Matcher m = URI_PATH_PATTERN.matcher(uri.getPath());		
			if (m.find()) {
				folderPath = m.group(URI_PATH_PATTERN_FOLDER_GROUP);
				folderPath = (null == folderPath)
						? ""
						: BEStringUtilities.convertToValidTibcoIdentifier(
								m.group(URI_PATH_PATTERN_FOLDER_GROUP), false);
				name = BEStringUtilities.convertToValidTibcoIdentifier(
						m.group(URI_PATH_PATTERN_NAME_GROUP), true);
				
			}
		}
		if (null == folderPath) {
			folderPath = this.getDefaultChannelFolder();
		}
		if ((null == name) || name.isEmpty()) {
			name = this.getDefaultChannelName();
		}
		
		return new Path(folderPath, name);
	}


	@Override
	public Map<String, Channel> getChannels()
	{
		return this.projectPathToChannel;
	}


    @Override
    public Set<Entity> getCreatedEntities()
    {
        return this.createdEntities;
    }

    
	protected String getDefaultChannelFolder()
	{
		return "";
	}

	
	protected String getDefaultChannelName()
	{
		return "Channel";
	}

	
	protected abstract String getDriverTypeName();

	
    @Override
    public Set<Entity> getModifiedEntities()
    {
        return this.modifiedEntities;
    }

    
	protected Channel getOrAddChannel(
			BindingContext bindingContext,
			URI endpointUri)
			throws Exception
	{
		Channel channel = (Channel) bindingContext.getProperties().get("channel");
		
		if (null == channel) {
			final Path channelPath = this.getChannelPath(endpointUri);
			final String basePath = channelPath.folder + PATH_SEPARATOR;
            final String extension = StudioResourceUtils
                    .getExtensionFor(StudioWorkbenchConstants._WIZARD_TYPE_NAME_CHANNEL);
            String projectPath;
            for (int i=0; ; i++) {
                final String fileName = channelPath.file + ((i < 1) ? "" : ("_" + i));
                projectPath = basePath + fileName + extension;
                channel = this.projectPathToChannel.get(projectPath);

                if (null == channel) {
    				channel = (Channel) CommonIndexUtils.loadEObject(
    						this.project.getFile(projectPath).getLocationURI(), false);
    				
    				if (null == channel) {
    					channel = this.createChannel(new Path(channelPath.folder, fileName));
    					this.getOrAddDriverConfig(channel, bindingContext, endpointUri);
    				    this.getCreatedEntities().add(channel);
    		            this.projectPathToChannel.put(projectPath, channel);
    					break;
    				}
    			}
    			
    			final DriverConfig driver = this.getOrAddDriverConfig(channel, bindingContext, endpointUri);
    			if (driver == channel.getDriver()) {
                    this.getModifiedEntities().add(channel);
                    this.projectPathToChannel.put(projectPath, channel);
    			    break;
    			}
            }

            bindingContext.getProperties().put("channel", channel);
		}

		return channel;
	}

	
	protected abstract String getOrAddConnectionResource(
	        String existingResourcePath,
			BindingContext bindingContext,
			URI endpointUri)
			throws Exception;


	protected DriverConfig getOrAddDriverConfig(
			Channel channel,
			BindingContext bindingContext,
			URI endpointUri)
			throws Exception
	{
		final String driverTypeName = this.getDriverTypeName();
		final DRIVER_TYPE driverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();		
		driverType.setName(driverTypeName);

		final DriverConfig newDriverConfig = ChannelFactory.eINSTANCE.createDriverConfig();
		newDriverConfig.setChannel(channel);
		newDriverConfig.setConfigMethod(CONFIG_METHOD.REFERENCE);
		newDriverConfig.setDriverType(driverType);
		newDriverConfig.setLabel(driverTypeName);

		final DriverConfig currentDriverConfig = channel.getDriver();
		if (null == currentDriverConfig) {
	        newDriverConfig.setReference(
	                this.getOrAddConnectionResource(null, bindingContext, endpointUri));
            channel.setDriver(newDriverConfig);
			if (!this.createdEntities.contains(channel)) {
			    this.modifiedEntities.add(channel);
			}
	        return newDriverConfig;
	
		} else if (!driverType.equals(currentDriverConfig.getDriverType())) {
			throw new Exception("Driver type conflict @ " + channel.getFullPath()
					+ " : [" + driverType + "] vs [" + newDriverConfig.getDriverType() + "]");
						
		} else {		
            final String currentResourcePath = currentDriverConfig.getReference(); 
    		final String newResourcePath = this.getOrAddConnectionResource(
    		        currentResourcePath, bindingContext, endpointUri);
    		if ((null == currentResourcePath) || !currentResourcePath.equals(newResourcePath)) {
                newDriverConfig.setReference(newResourcePath);
    		    return newDriverConfig;
    		} else {    	    
    		    return currentDriverConfig;
    		}
   		}		
	}


	@Override
	public ResourceSet getSharedResources()
	{
		return this.sharedResources;
	}


	protected String makeSharedResourcePath(
			BindingContext bindingContext,
			String extension)
	{
		final PortContext portContext = bindingContext.getPortContext();
		
		return new StringBuilder(PATH_SEPARATOR)
			.append(WSDLImportUtil.makeValidName(
					portContext.getServiceContext().getService().getLocalName()))
			.append(PATH_SEPARATOR)
			.append(this.transportFolderName)
			.append(PATH_SEPARATOR)
			.append(WSDLImportUtil.makeValidName(portContext.getPort().getLocalName()))
			.append(extension)
			.toString();			
	}


	@Override
	public Channel processBinding(
			BindingContext bindingContext)
			throws Exception
	{
		final String enpointUrl = WSDLImportUtil.getSOAPEndPointURL(
				bindingContext.getPortContext().getPort());

		if (null == enpointUrl) {
			return null;

		} else {	
			final Channel channel = this.getOrAddChannel(bindingContext, new URI(enpointUrl));
	
			bindingContext.getProperties().put("channel", channel);

			return channel;
		}
	}

	
}