package com.tibco.cep.studio.common.validation.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

public class CommonValidationUtils {
	
	

	/**
	 * constructs Model Error Object from the message and arguments for formatting message
	 * @param source --> Source of the error or warning
	 * @param key --> message key which is decales in bundle
	 * @param args --> formatting arguments
	 * @param isWarning --> if error is warning
	 * @return
	 */
	public static ModelError constructModelError(EObject source ,String key , List<Object> args , boolean isWarning ){
		ModelError me = ValidationFactory.eINSTANCE.createModelError();
		String formattedMsg = formatMessage(key, args);
		me.setMessage(formattedMsg);
		me.setSource(source);
		if (isWarning){
			me.setWarning(true);
		}
		
		return me;
	}

	/**
	 * it returns the formatted message based on the arguments passed
	 * @param key
	 * @param args
	 * @return
	 */
	public static String formatMessage(String key ,List<Object> args ){
		if (args == null || args.size() == 0) return Messages.getString(key);
		MessageFormat formatter = new MessageFormat(Messages.getString(key));
		Object[] objArgs = args.toArray();
		return formatter.format(objArgs);
		
	}

	/**
	 * Matches a property value with the pattern defined in the extended property 
	 * @param propDef
	 * @param errorList 
	 * @return
	 */
	public static void validatePropertyValue(PropertyDefinition propDef , List<ModelError> errorList){
		if (propDef == null) return ;		
		PropertyMap extPropMap = propDef.getExtendedProperties();
		if (extPropMap == null) return ;
		List<Entity> extPropList = extPropMap.getProperties();
		for (Entity ent : extPropList){
			if (ent instanceof PropertyDefinition){
				PropertyDefinition prop = (PropertyDefinition)ent;
				String name = prop.getName();
				if (DriverManagerConstants.ATTR_DRIVER_PROPERTY_PATTERN_NAME.equals(name)){
					String patternStr = prop.getDefaultValue();
					if (patternStr == null) return ;
					Pattern pattern = Pattern.compile(patternStr);
					String propValue = propDef.getDefaultValue();
					if (propValue == null) return;
					if(!pattern.matcher(propValue).matches()){
						ModelError me = null;
						List<Object> args = new ArrayList<Object>();
						if (propValue.matches(".*%%.+%%.*")){
							// Add Warning to the error list if it's Global Variable
							args.add(propDef.getName());
							args.add(propDef.getDefaultValue());
							me = constructModelError(propDef, "Channel.Driver.error.property.possiblyInvalid", args, true);
							errorList.add(me);
							
						} else {
							// Add Error
							args.add(propDef.getName());
							args.add(propDef.getDefaultValue());
							me = constructModelError(propDef, "Channel.Driver.error.property.possiblyInvalid", args, false);
							errorList.add(me);
						}
					}
					
				}
			}
		}		
		
		
	}

	/**
	 * resolve the resource from the relative path
	 * @param reference --> relative path of the resource with respect to project
	 * @param projectName --> Project Name
	 * @return
	 */
	public static boolean resolveReference(String reference , String projectName){
		if (reference == null || projectName == null || reference.trim().length() ==0 || 
				projectName.trim().length() == 0) return false;
		
		DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
		if (index == null) return false;
		String rootPath = index.getRootPath();
		if (!reference.startsWith("/") && !reference.startsWith("\\")) {
			reference = "/"+reference;
		}
		String path = rootPath + reference;
		File file = new File(path);
		if (file.exists()) {
			return true;
		}
		int idx = reference.lastIndexOf('.');
		if (idx > 0) {
			reference = reference.substring(0, idx);
		}
		DesignerElement element = CommonIndexUtils.getElement(projectName, reference);
		if (element instanceof SharedElement) {
			// return true here, there will be no file for a shared element
			return true;
		}
		if(reference.contains(".projlib")){
			StudioProjectConfiguration projectConfig = StudioProjectConfigurationCache.getInstance().get(projectName);
			EList<ProjectLibraryEntry> projectLibEntries = projectConfig.getProjectLibEntries();
			
			for(ProjectLibraryEntry entry:projectLibEntries) {	
				String projLibPath=entry.getPath();
				String projLibName=projLibPath.substring((projLibPath.lastIndexOf(File.separator)+1));
				if(reference.indexOf(projLibName)>0)
					return true;
			}
		}
		return false;

	}

	/**
	 * Validates Event Pay Load Schema
	 * @param payLoad
	 * @return
	 */
	public static boolean isEventPayLoadSchemaValid(boolean soapEvent, String payLoad) {
		// TODO
		if (payLoad == null) return false;
		if(soapEvent){
			XiNode payloadPropertyNode;
			try {
				payloadPropertyNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(payLoad)));
				XiNode node = payloadPropertyNode.getFirstChild();
				return  SOAPEventPayloadBuilder.validatePayload((com.tibco.xml.datamodel.nodes.Element)node);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		}
//		try {
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			dbf.setNamespaceAware(true);
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			
//			byte[] bytes = payLoad.getBytes();
//			InputStream ins = new ByteArrayInputStream(bytes);
//			Document payLoadDoc = db.parse(ins);
//			if (payLoadDoc == null) return true;
//			NodeList nodeList = payLoadDoc.getChildNodes();
//			int length = nodeList.getLength();
//			if (length > 1) return false;			
//			//NodeList elementNodeList = payLoadDoc.getElementsByTagName("element");
//			NodeList elementNodeList = payLoadDoc.getChildNodes();
//			if (elementNodeList == null || elementNodeList.getLength() == 0) return false;
//			Node foundEnvNode = null;
//		
//			for (int i =0 ; i < elementNodeList.getLength() ; i++){
//				Node node = elementNodeList.item(i);
//				if (node == null) return false;
//				String name = node.getNodeName();
//				String prefix = node.getPrefix();
//				name = removePrefix(prefix , name);				
//				if ("payload".equals(name)){
//					NodeList l = node.getChildNodes();
//					for (int j =0 ; j <l.getLength() ; ++j){
//						Node n = l.item(j);
//						name = n.getNodeName();
//						prefix = n.getPrefix();
//						name = removePrefix(prefix , name);
//						if ("element".equals(name)){
//							if (n instanceof Element){
//								Element element = (Element)n;
//								String nameAttrVal = element.getAttribute("name");
//								if ("Envelope".equals(nameAttrVal)) {
//									foundEnvNode = element;
//									break;
//								}
//							
//							}
//						}
//						
//					}
//	
//				}
//			}
//			if (foundEnvNode == null) return false;
//			// get sequence Node
//			Node seqNode = getComplexType(foundEnvNode);
//			if (seqNode == null) return false;
//			nodeList = seqNode.getChildNodes();
//			for (int i = 0 ; i< nodeList.getLength() ;++i){
//				Node node = nodeList.item(i);
//				if (node instanceof Element){
//					Element element = (Element)node;
//					String attValue = element.getAttribute("name");
//					if (attValue == null) return false;
//					if(!"Header".equals(attValue) && !"Body".equals(attValue)){
//						return false;
//					} else if("Body".equals(attValue) && getComplexType(element) == null){
//						return false;
//					}
//					
//				}
//				
//			}
//			
//		} catch (Exception e){
//			e.printStackTrace();
//			return false;
//		}
		
		
		return true;
	}
	
	private static Node getComplexType(Node envelopNode){
		if (envelopNode == null) return null;
		NodeList childNodeList = envelopNode.getChildNodes();
		if (childNodeList == null || childNodeList.getLength() == 0) return null;
	
		for (int i = 0 ; i < childNodeList.getLength() ; i++){
			Node childNode = childNodeList.item(i);
			String name = childNode.getNodeName();
			String prefix = childNode.getPrefix();
			name = removePrefix(prefix , name);
			if ("complexType".equals(name)){
				// got complex type node 
				 NodeList list = childNode.getChildNodes();
				 for (int j =0 ; j< list.getLength(); ++j){
					 Node nod = list.item(j);
					 prefix = nod.getPrefix();
					 String localName = nod.getNodeName();
					 localName = removePrefix(prefix, localName);
					 if ("sequence".equals(localName)){
						 return nod;
					 }
				 }
				
			}
		}
		return null;
	}

	private static String removePrefix(String prefix , String qualifiedValue){
		
		if (prefix == null || prefix.trim().length() == 0) return qualifiedValue;
		if (qualifiedValue == null || qualifiedValue.trim().length() == 0) return qualifiedValue;
		qualifiedValue = qualifiedValue.substring(prefix.length()).trim();
		if (qualifiedValue.startsWith(":")){
			qualifiedValue = qualifiedValue.substring(1).trim();
		}
		return qualifiedValue;
		
	}
	
	public static boolean isStringEmpty(String str){
		return str == null || str.trim().length() == 0;
	}

}
