/**
 *(c) Copyright 2011, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from
 * TIBCO Software Inc.
 */

package com.tibco.cep.studio.core.tools;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;

/**
 * @author abhijit
 *
 */
public class ChannelMigrator extends DefaultStudioProjectMigrator {
	
	private static final String HTTP = "HTTP";
	private static final String RV = "RendezVous";
	private static final String JMS = "JMS";
	
	private static final String SERVER_TYPE = "serverType";
	private static Transformer TRANSFORMER;
	private static Transformer HTTP_TRANSFORMER;
	
	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!"channel".equalsIgnoreCase(ext)) {
			return;
		}
		monitor.subTask("- Converting Channel file "+file.getName());
		try {
			processChannelFile(file, ext, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	static {
		//load xslt
		ClassLoader classLoader = ChannelMigrator.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("com/tibco/cep/studio/core/tools/ChannelMigration.xsl");
		if (inputStream != null) {
			StreamSource streamSource = new StreamSource(inputStream);
			try {
				Templates templates = TransformerFactory.newInstance().newTemplates(streamSource);
				TRANSFORMER = templates.newTransformer();
			} catch (TransformerConfigurationException e) {
				StudioCorePlugin.log(e);
			} catch (TransformerFactoryConfigurationError e) {
				StudioCorePlugin.log(e);
			}
		}
	}
	
    /**
     * Migrate each channel in the project to new format
     * @param locationFile
     * @param deleteOrig
     * @throws Exception
     */
    public  void migrateChannels(File locationFile,
                                       String extension,
                                       boolean deleteOrig) throws Exception {
        if (locationFile.isDirectory()) {
            //List
            String[] children = locationFile.list(new FilenameFilter() {
                public boolean accept(File dir, String file) {
                    return !file.startsWith(".");
                }
            });

            for (String child : children) {
                //Recurse
                migrateChannels(new File(locationFile, child), extension, deleteOrig);
            }
        } else {
            processChannelFile(locationFile, extension, deleteOrig);
        }
    }

	private void processChannelFile(File locationFile, String extension,
			boolean deleteOrig) throws FileNotFoundException, IOException {
		//Process the file
		//rootDir can be a file also
 		if (locationFile.getAbsolutePath().endsWith(extension)) {
		    String name = locationFile.getName();   //Get Name
		    File parentDir = locationFile.getParentFile();   //Get parent dir
		    String newName = name.substring(0, name.lastIndexOf('.')) + "_modified" + "." + extension;

		    File newFile = new File(parentDir, newName);
		    FileOutputStream fos = new FileOutputStream(newFile);
		    try {
  		    	transform(locationFile, fos);
		    } catch (Exception e) {
		    	newFile.delete();
		    	return;
		    } finally {
		    	fos.close();
		    }
		    
		    //add httpProperties obtained from CDD-PU to channel if its HTTP
		    if(this.httpProperties != null) {
		    	addHttpProperties(newFile);
		    }
		    
		    //Add jms destination properties e.g. IsJSONPayload
		    addJmsProperties(newFile);
			
		    boolean isTransformed = StudioProjectMigrationUtils.checkIfFileTransformed(locationFile, newFile);
		    if (!isTransformed) {
		    	newFile.delete();
		    	return;
		    }
		    	
		    //Rename original to .backup
		    String backupName = name.substring(0, name.lastIndexOf('.')) + "." + extension + ".orig";
		    File backupFile = new File(parentDir, backupName);
		    locationFile.renameTo(backupFile);
		    locationFile = backupFile;

		    //Rename modified to original
		    File origFile = new File(parentDir, name);
		    newFile.renameTo(origFile);
		    
			try {
				newFile = new File(origFile.getAbsolutePath());
				migrate_XSIType(newFile);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}

			if (deleteOrig) {
				locationFile.delete();
			}
		}
	}

	/**
	 * Method to add xsi type attribute in the migration process.
	 * @param locationFile
	 */
	public void migrate_XSIType(File locationFile) {
		String outContent = transform_addXSIType(locationFile);
		BufferedWriter out;
		try {
			//out = new BufferedWriter(new FileWriter(locationFile));					//BE-24348 multibyte character support in migration. @rjain
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(locationFile), "UTF-8"));
			out.write(outContent);
			out.close();
		} catch (IOException e) {
			StudioCorePlugin.log(e);
		}
	}
	
	
	/**Method to add xsi type attribute in the migrated channels. (HTTP CHANNELS) 
	 * 
	 * @param inFile
	 * @return
	 */
	
	private static String transform_addXSIType(File inFile)  {
		try {
			if (HTTP_TRANSFORMER == null) {
				initializeHTTPTransformer();
			}
			FileInputStream xmlInStream = new FileInputStream(inFile);
			//BE-24348 multibyte character support in migration. @rjain
			//StringWriter writer = new StringWriter();
			//HTTP_TRANSFORMER.transform(new StreamSource(new InputStreamReader(xmlInStream)), new StreamResult(writer));

	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        StreamResult sr = new StreamResult(new OutputStreamWriter(bos, "UTF-8"));


	        HTTP_TRANSFORMER.transform(new StreamSource(new InputStreamReader(xmlInStream,StandardCharsets.UTF_8)), sr);
	        byte[] outputBytes = bos.toByteArray();
	        String strRepeatString = new String(outputBytes, "UTF-8");
	        
			//writer.flush();
			//return writer.toString();
	        return strRepeatString;
		} 
		catch(Exception e){
			StudioCorePlugin.log(e);
		}
		return null;
	}
	
	private static void initializeHTTPTransformer() throws Exception {
		ClassLoader classLoader = ChannelMigrator.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("com/tibco/cep/studio/core/tools/HTTPChannelMigration.xsl");
		TransformerFactory tFactory = TransformerFactory.newInstance();
		HTTP_TRANSFORMER = tFactory.newTransformer(new StreamSource(inputStream));
	}
	/**
	 * @param newFile
	 * @throws IOException
	 */
	private void addHttpProperties(final File newFile) throws IOException {
		
		Choice createChoice=null;
		registerEPackages();
		final ResourceSetImpl rset = new ResourceSetImpl();
		final Resource res = rset.createResource(URI.createFileURI(newFile.getAbsolutePath()));
		res.load(null);
		final ChannelImpl channel = (ChannelImpl) res.getContents().get(0);
		if (channel.getDriver().getDriverType().getName().equalsIgnoreCase(HTTP)) {
			final ExtendedConfiguration extendedConfiguration = channel.getDriver().getExtendedConfiguration();
			final EList<SimpleProperty> properties = extendedConfiguration.getProperties();
			String choiceValue = null;
			for(final SimpleProperty prop:properties){
				if(prop.getName().equals(SERVER_TYPE)) {
					if (prop.getValue().equals("BUILT-IN")) {
						prop.setValue("TOMCAT");
					}
					choiceValue = prop.getValue();
					break;
				}
			} 
			if (channel.getDriver().getChoice() == null) {
				createChoice = ChannelFactory.eINSTANCE.createChoice();
			} else {
				createChoice=channel.getDriver().getChoice();
			}
			ExtendedConfiguration extdConfig= addPropertiesForTomcat(channel);
			createChoice.getExtendedConfiguration().add(extdConfig);
			createChoice.setValue(choiceValue);
			channel.getDriver().setChoice(createChoice);

			for (Destination destination : channel.getDriver().getDestinations()) {
				boolean isContextPathProp = false, isJsonPayloadProperty = false, isTypeProperty = false;
				SimpleProperty pageFlowProperty = null;
				for (Entity entity : destination.getProperties().getProperties()) {
					if (entity instanceof SimpleProperty) {
						SimpleProperty simpleProperty = (SimpleProperty)entity;
						if (simpleProperty.getName().equalsIgnoreCase("be.http.jsonPayload")) {
							isJsonPayloadProperty = true;
						} else if (simpleProperty.getName().equalsIgnoreCase("be.http.PageFlow")) {
							pageFlowProperty = simpleProperty;
						} else if (simpleProperty.getName().equalsIgnoreCase("be.http.type")) {
							isTypeProperty = true;
						} else if (simpleProperty.getName().equalsIgnoreCase("be.http.contextPath")) {
							isContextPathProp = true;
						}
					}
				}
				if (!isJsonPayloadProperty) addJSONPayloadDestinationProperty(destination, channel.getDriver().getDriverType().getName());
				if (!isContextPathProp) addContextPathProperty(destination);
				if (pageFlowProperty != null) destination.getProperties().getProperties().remove(pageFlowProperty);
				
				String typeValue = pageFlowProperty == null ? "DEFAULT" : (Boolean.valueOf(pageFlowProperty.getValue()) ? "PAGEFLOW" : "DEFAULT");
				if (!isTypeProperty) addTypeProperty(destination, typeValue);
			}
		}
		//Add IncludeEventType property
		for (Destination destination : channel.getDriver().getDestinations()) {
			addIncludeEventTypeDestinationProperty(destination, channel.getDriver().getDriverType().getName());
		}
		res.save(null);
	}
	
	private void addJmsProperties(final File newFile) throws IOException {
		
		registerEPackages();
		final ResourceSetImpl rset = new ResourceSetImpl();
		final Resource res = rset.createResource(URI.createFileURI(newFile.getAbsolutePath()));
		res.load(null);
		final Channel channel = (Channel) res.getContents().get(0);
		if (channel.getDriver().getDriverType().getName().equalsIgnoreCase(JMS)) {
			for (Destination destination : channel.getDriver().getDestinations()) {
				boolean isJsonPayloadProperty = false;
				for (Entity entity : destination.getProperties().getProperties()) {
					if (entity instanceof SimpleProperty) {
						SimpleProperty simpleProperty = (SimpleProperty)entity;
						if (simpleProperty.getName().equalsIgnoreCase("IsJSONPayload")) {
							isJsonPayloadProperty = true;
							break;
						}
					}
				}
				if (!isJsonPayloadProperty) addJSONPayloadDestinationProperty(destination, channel.getDriver().getDriverType().getName());
			}
		}
		res.save(null);
	}
	
	/**
	 * @param destination
	 * @param driverType
	 */
	private void addIncludeEventTypeDestinationProperty(Destination destination, String driverType) {
		if (driverType.equalsIgnoreCase(HTTP) || driverType.equalsIgnoreCase(JMS) || driverType.equalsIgnoreCase(RV)) {
			
			String includeEventType_key = driverType.equalsIgnoreCase(HTTP) ? "be.http.IncludeEventType" : "IncludeEventType";
			
			//Do not add Include-Event-Type property if already exists, also cleanup duplicates as this bug is GAed.
			List<SimpleProperty> includeEventTypeProps = new ArrayList<>();
			for (Entity prop : destination.getProperties().getProperties()) {
				if (prop instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty)prop;
					if (simpleProperty.getName().equalsIgnoreCase(includeEventType_key)) {
						includeEventTypeProps.add(simpleProperty);
					}
				}
			}
			if (!includeEventTypeProps.isEmpty()) {
				if (includeEventTypeProps.size() > 1) {
					includeEventTypeProps.remove(includeEventTypeProps.size() - 1);
					destination.getProperties().getProperties().removeAll(includeEventTypeProps);// retain only the last be.http.IncludeEventType prop in dest props.
				}
				return;
			}

			PropertyMap propertyMap = destination.getProperties();
			if (destination.getProperties() == null) {
				propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			}
			SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(includeEventType_key);
			property.setValue("ALWAYS");
			propertyMap.getProperties().add(property);
			destination.setProperties(propertyMap);
		}
	}
	
	private void addJSONPayloadDestinationProperty(Destination destination, String driverType) {
		if (driverType.equalsIgnoreCase(HTTP)) {
			PropertyMap propertyMap = destination.getProperties();
			if (destination.getProperties() == null) {
				propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			}
			SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName("be.http.jsonPayload");
			property.setValue("false");
			propertyMap.getProperties().add(property);

			destination.setProperties(propertyMap);
		} else if(driverType.equalsIgnoreCase(JMS)) {
			PropertyMap propertyMap = destination.getProperties();
			if (destination.getProperties() == null) {
				propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			}
			SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName("IsJSONPayload");
			property.setValue("false");
			propertyMap.getProperties().add(property);

			destination.setProperties(propertyMap);
		}
	}
	
	private void addTypeProperty(Destination destination, String typeValue) {
		PropertyMap propertyMap = destination.getProperties();
		if (destination.getProperties() == null) {
			propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		}

		SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
		property.setName("be.http.type");
		property.setValue(typeValue);
		propertyMap.getProperties().add(property);
		
		destination.setProperties(propertyMap);
	}
	
	private void addContextPathProperty(Destination destination) {
		PropertyMap propertyMap = destination.getProperties();
		if (destination.getProperties() == null) {
			propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		}

		SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
		property.setName("be.http.contextPath");
		property.setValue("");
		propertyMap.getProperties().add(property);

		destination.setProperties(propertyMap);
	}
	
	private ExtendedConfiguration addPropertiesForTomcat(ChannelImpl channel) {
		Map<String,String> propertiesMapWithDefaultValues=generatePropertyList();
		Set<String> propertyList=propertiesMapWithDefaultValues.keySet();
		ExtendedConfiguration extdConfig;

		if (channel.getDriver().getChoice()==null) {
			extdConfig = ChannelFactory.eINSTANCE.createExtendedConfiguration();
		} else {
			EList<ExtendedConfiguration> extendedConfigurationList=channel.getDriver().getChoice().getExtendedConfiguration();
			Iterator<ExtendedConfiguration> it=extendedConfigurationList.iterator();
			extdConfig=(ExtendedConfiguration) it.next();
		}

		if (channel.getDriver().getChoice() == null) {		
			for (Map.Entry<String, String> entry:this.httpProperties.entrySet()) {
				//skip built-in properties
				if (entry.getKey().equals("be.http.async.staleConnectionCheck") ||
						entry.getKey().equals("be.http.ssl_server_keymanageralgorithm") ||
						entry.getKey().equals("be.http.ssl_server_trustmanageralgorithm") ||
						entry.getKey().equals("be.http.ssl_server_enabledprotocols") ||
						entry.getKey().equals("be.http.ssl_server_ciphers") ) {
					continue;
				}
				final SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
				property.setName(entry.getKey());
				property.setValue(entry.getValue());
				extdConfig.getProperties().add(property);
			}
		}

		if (channel.getDriver().getChoice() != null) {
			EList<ExtendedConfiguration> extdConfigurationList=channel.getDriver().getChoice().getExtendedConfiguration();
			Iterator<ExtendedConfiguration> it = extdConfigurationList.iterator();
			while (it.hasNext()) {
				EList<SimpleProperty> propertyListFromChannel=((ExtendedConfiguration)it.next()).getProperties();
				Iterator<SimpleProperty> propertyListIterator=propertyListFromChannel.iterator();
				while (propertyListIterator.hasNext()) {
					SimpleProperty property=(SimpleProperty)propertyListIterator.next();
					if (propertyList.contains(property.getName())){
						propertyList.remove(property.getName());
					}
					
					//set default value for be.http.ssl_server_enabledprotocols
					if ("be.http.ssl_server_enabledprotocols".equals(property.getName()) && (property.getValue() == null || property.getValue().isEmpty())) {
						property.setValue(StudioCore.HTTP_SSL_SERVER_ENABLED_PROTOCOLS);
					}

					// BE-27283 - set default value for be.http.ssl_server_ciphers
					if ("be.http.ssl_server_ciphers".equals(property.getName()) && (property.getValue() == null || property.getValue().isEmpty())) {
						property.setValue(StudioCore.HTTP_SSL_SERVER_CIPHERS);
					}
				}
			}
		}

		for (String propertyName : propertyList) {
			boolean changedScheme=false;
			boolean changedKeepAliveRequest=false;

			if (channel.getDriver().getChoice() != null) {
				if (("be.http.scheme").equals(propertyName)) {
					EList<ExtendedConfiguration> extendedConfiguration1=channel.getDriver().getChoice().getExtendedConfiguration();
					Iterator it=extendedConfiguration1.iterator();
					while (it.hasNext()) {
						EList<SimpleProperty> simpleProperty=((ExtendedConfiguration)it.next()).getProperties();
						Iterator simplePropertyIterator=simpleProperty.iterator();
						while (simplePropertyIterator.hasNext()) {
							SimpleProperty property=(SimpleProperty)simplePropertyIterator.next();
							if ((property.getName()).equals("be.http.protocol")) {
								property.setValue(property.getValue());
								property.setName("be.http.scheme");
								changedScheme=true;
							}

						}
					}
				}

				if(("be.http.maxKeepAliveRequests").equals(propertyName)) {
					EList<ExtendedConfiguration> extendedConfiguration1=channel.getDriver().getChoice().getExtendedConfiguration();
					Iterator it=extendedConfiguration1.iterator();
					while (it.hasNext()) {
						EList<SimpleProperty> simpleProperty=((ExtendedConfiguration)it.next()).getProperties();
						Iterator simplePropertyIterator=simpleProperty.iterator();
						while (simplePropertyIterator.hasNext()) {
							SimpleProperty property=(SimpleProperty)simplePropertyIterator.next();
							if (property.getName().equals("be.http.maxKeepAliveRequest")) {
								property.setValue(property.getValue());
								property.setName("be.http.maxKeepAliveRequests");
								changedKeepAliveRequest=true;
							}
						}
					}
				}
			}

			if (changedScheme == false && changedKeepAliveRequest == false) {
				addPropertyToExtendConfig(propertyName,propertiesMapWithDefaultValues.get(propertyName),extdConfig);
			}
		}
		
		return extdConfig;
	}
	
	
	private Map<String, String> generatePropertyList(){
	
		Map<String,String> propertiesWithDefaultValues=new LinkedHashMap<String,String>();
		propertiesWithDefaultValues.put("be.http.debug","false");
		propertiesWithDefaultValues.put("be.http.debugFolder","");
		propertiesWithDefaultValues.put("be.http.debugLogPattern","%{yyyy MMM dd HH:mm:ss.SSS 'GMT'X}t %A %I [%m] '%U' [%s] %bbytes %Dms");
		propertiesWithDefaultValues.put("be.http.enableDNSLookups","false");
		propertiesWithDefaultValues.put("be.http.compression","off");
		propertiesWithDefaultValues.put("be.http.useBodyEncodingForURI","false");
		propertiesWithDefaultValues.put("be.http.URIEncoding","");
		propertiesWithDefaultValues.put("be.http.maxKeepAliveRequests","-1");
		propertiesWithDefaultValues.put("be.http.maxHttpHeaderSize","4096");
		propertiesWithDefaultValues.put("be.http.maxPostSize","2097152");
		propertiesWithDefaultValues.put("be.http.scheme","");
		propertiesWithDefaultValues.put("be.http.maxSavePostSize","4096");
		propertiesWithDefaultValues.put("be.http.maxSpareThreads","50");
		propertiesWithDefaultValues.put("be.http.minSpareThreads","4");
		propertiesWithDefaultValues.put("be.http.compressableMimeType","text/html,text/xml,text/plain");
		propertiesWithDefaultValues.put("be.http.restrictedUserAgents","");
		propertiesWithDefaultValues.put("be.http.ssl_server_keymanageralgorithm","");
		propertiesWithDefaultValues.put("be.http.ssl_server_trustmanageralgorithm","");
		propertiesWithDefaultValues.put("be.http.ssl_server_enabledprotocols",StudioCore.HTTP_SSL_SERVER_ENABLED_PROTOCOLS);
		propertiesWithDefaultValues.put("be.http.ssl_server_ciphers",StudioCore.HTTP_SSL_SERVER_CIPHERS);
		propertiesWithDefaultValues.put("be.http.connectorType","NIO");
		propertiesWithDefaultValues.put("be.http.connectorInstances","1");
		propertiesWithDefaultValues.put("be.http.sessionTimeout","1800");
		propertiesWithDefaultValues.put("be.http.serverHeader", "Apache-Coyote/1.1");
		propertiesWithDefaultValues.put("be.http.ssl_server_useServerCipherOrder","false");
			
		return propertiesWithDefaultValues;
		
	}
	
	private ExtendedConfiguration addPropertiesForBuiltIn() {
		final ExtendedConfiguration extdConfig = ChannelFactory.eINSTANCE.createExtendedConfiguration();
		for (Map.Entry<String, String> entry:this.httpProperties.entrySet()) {
			final SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
			//skip tomcat properties
			if(entry.getKey().equals("be.http.connectionLinger")) {
				continue;
			}
			property.setName(entry.getKey());
			property.setValue(entry.getValue());
			extdConfig.getProperties().add(property);
		}
		//add remaining properties which are not coming via CDD
		addPropertyToExtendConfig("be.http.useBodyEncodingForURI", "false", extdConfig);
		addPropertyToExtendConfig("be.http.URIEncoding", "", extdConfig);
		
		return extdConfig;
	}
	
	private void addPropertyToExtendConfig(final String propName, final String propValue, final ExtendedConfiguration extdConfig) {
		
		final SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
		property.setName(propName);
		property.setValue(propValue);
		extdConfig.getProperties().add(property);
	}

	private static void registerEPackages() {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put(ChannelPackage.eNS_URI,
				ChannelPackage.eINSTANCE);
	}
	
//    private static boolean checkIfFileTransformed(File locationFile, File newFile) {
//    	// Better check will be to compare the file contents, or to compare CRC32/MD5 hash
//    	if (locationFile.length() == newFile.length()) {
//    		return false; 
//		}
//		return true;
//	}

	/**
     * Carry out the transformation
     * @param channelFile
     * @param newFileOutputStream
     * @throws Exception
     */
    private static void transform(File channelFile,
                                 FileOutputStream newFileOutputStream) throws Exception {
        
        if (TRANSFORMER != null) {
        	
	        //Create source file source
	        StreamSource xmlSource = new StreamSource(channelFile);
	        StreamResult outputResult = new StreamResult(newFileOutputStream);
	//		StudioCorePlugin.debug(Messages.getString("Migrate_Channel", channelFile));
	        TRANSFORMER.transform(xmlSource, outputResult);
        }
    }
    
	@Override
	public int getPriority() {
		return 0; // very high priority, run before others
	}

}
