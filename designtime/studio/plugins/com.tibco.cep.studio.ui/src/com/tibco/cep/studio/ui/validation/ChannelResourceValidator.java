/**
 * 
 */
package com.tibco.cep.studio.ui.validation;

import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.getAllDestinationsURIMaps;
import static com.tibco.cep.studio.core.util.CommonUtil.showWarnings;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * @author rmishra
 *
 */
public class ChannelResourceValidator extends EntityResourceValidator {

	@Override
	public boolean canContinue() {
		// can continue to new channel if one got problem
		return true;
	}

	@Override
	public boolean enablesFor(IResource resource) {
		// TODO Auto-generated method stub
		return super.enablesFor(resource);
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		// delete markers
		//deleteMarkers(resource);
		// perform basic resource validation for folder etc
		super.validate(validationContext);
		// get Model Object
		DesignerElement modelObj = getModelObject(resource);
		if (modelObj instanceof EntityElement){
			EntityElement enityEle = (EntityElement) modelObj;
			if (enityEle.getEntity() instanceof Channel) {
				Channel channel = (Channel)enityEle.getEntity();
				// Post Channel resource problems
				for (ModelError me : channel.getModelErrors()){
					int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
					reportProblem(resource, me.getMessage(), severity);
				}
				DriverConfig dc = channel.getDriver();
				if (dc.getConfigMethod() == CONFIG_METHOD.PROPERTIES
						&& (channel.getDriver().getDriverType().getName().equals(DriverManagerConstants.DRIVER_KAFKA)
								|| channel.getDriver().getDriverType().getName().equals(DriverManagerConstants.DRIVER_KAFKA_STREAMS)
								|| channel.getDriver().getDriverType().getName().equals("ActiveSpaces 3.x"))) {
					PropertyMap properties = dc.getProperties();
					if (properties != null) {
						EList<Entity> propertiesList = properties.getProperties();

						Map<String,String> propertiesMap = new HashMap<String,String>();
						for (int i=0 ; i < propertiesList.size();i++) {
							SimplePropertyImpl simpleProperty = (SimplePropertyImpl) propertiesList.get(i);
							propertiesMap.put(simpleProperty.getName(), simpleProperty.getValue());
						}

						if (channel.getDriver().getDriverType().getName().equals(DriverManagerConstants.DRIVER_KAFKA)
								|| channel.getDriver().getDriverType().getName().equals(DriverManagerConstants.DRIVER_KAFKA_STREAMS)) {							
							if ("SSL".equalsIgnoreCase(propertiesMap.get("kafka.security.protocol")) || "SASL_SSL".equalsIgnoreCase(propertiesMap.get("kafka.security.protocol"))) {
								if (propertiesMap.get("kafka.trusted.certs.folder") == null || propertiesMap.get("kafka.trusted.certs.folder") == "")
									reportProblem(resource, "Empty trusted certificate", IMarker.SEVERITY_WARNING);
								else
									reportTrustCertifactFolderProblem(propertiesMap.get("kafka.trusted.certs.folder"), resource, "Empty trusted certificate", "Invalid Reference Error[Trusted certificate]");
								if(propertiesMap.get("kafka.keystore.identity") !=null && !propertiesMap.get("kafka.keystore.identity").equals("") )
									reportSSLProblem(resource,propertiesMap.get("kafka.keystore.identity"), "Empty keystore identity", "Invalid Reference Error[identity]", true);
							}
						} else if (channel.getDriver().getDriverType().getName().equals("ActiveSpaces 3.x")) {
							String realmServer = propertiesMap.get("RealmServer");
							if (realmServer == null || realmServer.isEmpty()) reportProblem(resource, "Empty Realm Server url", IMarker.SEVERITY_ERROR);
							
							if ("true".equalsIgnoreCase(propertiesMap.get("useSsl")) && "TrustFile".equalsIgnoreCase(propertiesMap.get("Trust_Type"))) {
								reportSSLProblem(resource,propertiesMap.get("Trust_File"), "Identity not specified", "Invalid Reference Error[identity]", true);
							}
						}
					}
				}
				if (dc != null) {
					for (Destination dest : dc.getDestinations()) {
						for (ModelError me : dest.getModelErrors()) {
							if (me != null) {
								int severity = me.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
								//							reportProblem(resource, me.getMessage(), severity);
								reportDestinationProblem(resource, dest.getName(),  me.getMessage(), severity);
							}
						}
					}
				}
			}
		}
		
		//Validate Default Destination for event
		validateEventResources(resource.getProject().getName());
		
		//Validate CDD resources, as they have destination definitions
		if (validationContext.getBuildType() != IncrementalProjectBuilder.FULL_BUILD) {
			ValidationUtils.validateResourceByExtension(resource.getProject(), "cdd");
		}
		
		return true;
	}
	
	/**
	 * Report only Destination problems 
	 * & creating specific destination marker to store the destination name
	 * @param resource
	 * @param destination
	 * @param message
	 * @param severity
	 */
	protected void reportDestinationProblem (IResource resource, 
			                                 String destination, 
			                                 String message, 
			                                 int severity) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		try {
			IMarker marker = addMarker(resource, message, null, -1, -1, -1, severity);
			marker.setAttribute(IResourceValidator.DESTINATION_NAME_ATTRIBUTE, destination);
		} catch (CoreException e) {
			StudioUIPlugin.debug(e.getMessage());
		}
	}
	
	protected void reportSSLProblem(IResource resource, String val, String emptyMsg, String invalidMsg, boolean isCert) {
		if (val != null) {
			if (val.trim().equals("")) {
				if (isCert) {
					reportProblem(resource, emptyMsg,
							IMarker.SEVERITY_ERROR);
				}
			} else {
				String value = GvUtil.getGvDefinedValue(resource.getProject(), val); 
				if (value == null) {
					reportProblem(resource, invalidMsg, IMarker.SEVERITY_ERROR);
				} else { 
					if (isCert && value.endsWith("/.folder")) {
						value = value.substring(0, value.indexOf("/.folder"));
					}
					if (isCert || (!isCert && !value.equals("")) ) {
						if (value.startsWith("file:///")) {
							try {
								URI uri = new URI(value);
								String path = uri.getPath();
								if (!new File(path).exists())
									reportProblem(resource, invalidMsg, IMarker.SEVERITY_ERROR);
							} catch (URISyntaxException e) {
								e.printStackTrace();
							}
						} else if (!resource.getProject().exists(Path.fromOSString(value))) {
							reportProblem(resource, invalidMsg, IMarker.SEVERITY_ERROR);
						}
					}
				}
			}
		} else {
			reportProblem(resource, emptyMsg,IMarker.SEVERITY_ERROR);
		}
	}
	protected void reportTrustCertifactFolderProblem(String trustCertificatesFolder, IResource resource,String emptyTrustCertificatesFolderKey, String invalidTrustCertifactesFolderKey) {
		Map<String, GlobalVariableDescriptor> glbVars = getGlobalVariableNameValues(resource.getProject().getName());
		Map<String, GlobalVariableDescriptor> glbVarsDesc = getGlobalVariableDescriptors(resource.getProject().getName());
		boolean valid = false;
		String val = trustCertificatesFolder;

		// case for reference folder within BE project
		if (val.endsWith(".folder")) {
			reportSSLProblem(resource, trustCertificatesFolder, emptyTrustCertificatesFolderKey, invalidTrustCertifactesFolderKey, true);
		} else {
			// case of Global variable
			if (GvUtil.isGlobalVar(val)) {
				valid = validateStringField(resource, glbVars, val, emptyTrustCertificatesFolderKey, invalidTrustCertifactesFolderKey, IMarker.SEVERITY_ERROR);
				if (valid) {
					String variable = GvUtil.getGvVariable(val);
					GlobalVariableDescriptor desc = glbVarsDesc.get(variable);
					if (desc==null || !desc.isDeploymentSettable()) {		
						reportProblem(resource,invalidTrustCertifactesFolderKey, IMarker.SEVERITY_ERROR);
					}
				}
			} else {
				// case for a simple folder location entered manually
				try {
					val = val.replace("\\", "/");
					URI uri = new URI("file:///" + val);
					String path = uri.getPath();
					if (!new File(path).exists()) {
						reportProblem(resource,invalidTrustCertifactesFolderKey, IMarker.SEVERITY_ERROR);
					}
				} catch(URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static Map<String, GlobalVariableDescriptor> getGlobalVariableNameValues(String projectName) {
		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(projectName);
		Collection<GlobalVariableDescriptor> gVars = provider.getVariables();
		Map<String, GlobalVariableDescriptor> globalVarsMap = new LinkedHashMap<String, GlobalVariableDescriptor>(gVars.size());
		for (GlobalVariableDescriptor descriptor : gVars) {
			globalVarsMap.put(descriptor.getFullName(), descriptor);
		}
		return globalVarsMap;
	}
	public static Map<String, GlobalVariableDescriptor> getGlobalVariableDescriptors(String projectName) {
		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(projectName);
		Map<String, GlobalVariableDescriptor> gvn = new HashMap<String, GlobalVariableDescriptor>();
		for(GlobalVariableDescriptor descriptor:provider.getVariables()){
			gvn.put(descriptor.getFullName(), descriptor);
		}
		return gvn;
	}
	protected boolean validateStringField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String emptyMsg, String invalidMsg, int severity) {
		if (val != null) {
			if (GvUtil.isGlobalVar(val)) {
				String stripVal = val.substring(val.indexOf("%%") + 2);
				stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
				if (!glbVars.keySet().contains(stripVal)) {
					reportProblem(resource, invalidMsg,
							severity);
					return false;
				}
			} else if (val.trim().equals("")) {
				reportProblem(resource, emptyMsg,
						severity);
				return false;
			}
		} else {
			reportProblem(resource, emptyMsg, severity);
			return false;
		}
		return true;
	}
	/**
	 * @param ownerProjectName
	 */
	protected void validateEventResources(String ownerProjectName){
		List<Entity> eventList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.SIMPLE_EVENT); 
		Map<String, Destination> map = getAllDestinationsURIMaps(ownerProjectName);
		for (Entity entity : eventList){
			if(entity instanceof SimpleEvent){
				SimpleEvent event = (SimpleEvent)entity;
				StringBuilder sb = new StringBuilder();
				String channelURI = event.getChannelURI();
				if (channelURI != null && event.getDestinationName() != null) {
					String destination = sb.append(channelURI)/*.append(".channel")*/.append("/").append(event.getDestinationName()).toString();
					IFile file = IndexUtils.getFile(ownerProjectName, event);
					deleteMarkers(file, EVENT_DEFAULT_DESTINATION_VALIDATION_MARKER_TYPE);
					if(!map.containsKey(destination)){
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", destination, "Default Destination");
						reportProblem(file, problemMessage, IMarker.SEVERITY_WARNING, EVENT_DEFAULT_DESTINATION_VALIDATION_MARKER_TYPE);
					}
				}
			}
		}
	}
}
