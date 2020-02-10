package com.tibco.cep.studio.core.converter.sharedresource;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.cep.repo.GlobalVariableDescriptor;

public class GlobalVariableConverter  {
	private static DocumentBuilderFactory docBuilderFactory = null;
	private static DocumentBuilder docBuilder = null;

	/**
	 * persists all the global variable to the appropriate dir
	 * @param project
	 * @param gvVarDefaultFolder
	 * @param gvMap
	 * @throws Exception
	 */
	public static void convert(File projectPath, boolean  isEAR, String gvVarDefaultFolder, Map<String, List<GlobalVariableDescriptor>> gvMap) throws Exception {		
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(true);
		docBuilder = docBuilderFactory.newDocumentBuilder();
		
		
		for (Map.Entry<String, List<GlobalVariableDescriptor>> entry : gvMap.entrySet()){
			String folderPath = entry.getKey();
			// replace all segment separator to path seperator
			folderPath = File.separatorChar != SharedResourceElements.SEGMENT_SEPARATOR ? 
											folderPath.replace(SharedResourceElements.SEGMENT_SEPARATOR, File.separatorChar): folderPath;
			if (!folderPath.startsWith(File.separator)){
				folderPath = File.separator + folderPath;
			}
			String relPathToProject = gvVarDefaultFolder + folderPath;
			// check if this particular folder is there on file system 
			//String absolutePath = project.getWorkspace().getRoot().getLocation().toOSString() + project.getFullPath() + File.separator + relPathToProject;
			String absolutePath = projectPath.getAbsolutePath() + File.separator + relPathToProject; 
			File dir = new File(absolutePath);
			if (!dir.exists()){
				dir.mkdirs();
			}
			File gvFile = new File(absolutePath + SharedResourceElements.GLOBAL_VARIABLE_DEFAULT_FILE_NAME 
																			+ "." + SharedResourceElements.GLOBAL_VARIABLE_DEFAULT_FILE_EXTENSION);
            gvFile.createNewFile();
			List<GlobalVariableDescriptor> gvList = entry.getValue();
			populateGVFile(docBuilder ,gvFile ,gvList , isEAR);
			
		}

	}
	/**
	 * write Global variables to a single file
	 * @param docBuilder
	 * @param gvFile
	 * @param gvList
	 * @throws Exception
	 */
	public static void populateGVFile(DocumentBuilder docBuilder ,File gvFile , List<GlobalVariableDescriptor> gvList , boolean isEAR) throws Exception{
		if (gvFile == null) throw new IllegalArgumentException("File can not be null");
		Document doc = docBuilder.newDocument();
		Element root = doc.createElementNS(SharedResourceElements.REPO_NAMESPACE_URI, "repository");
		doc.appendChild(root);
		Element gvsNode = doc.createElement("globalVariables");
		root.appendChild(gvsNode);
		
		for (GlobalVariableDescriptor gvd : gvList){
			Node gvNode = getGlobalVariableNode(doc , gvd , isEAR);	
			gvsNode.appendChild(gvNode);		
			
		}
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();    
	    Transformer transformer = transformerFactory.newTransformer();
	    Source source = new DOMSource(doc);
	    Result target = new StreamResult(gvFile.getPath()); // Using gvFile directly does not work on Linux.  
	    transformer.transform(source, target);
	}
	public static Node getGlobalVariableNode(Document doc ,GlobalVariableDescriptor gvDescriptor , boolean isEAR){
		if (gvDescriptor == null) return null;
		String name = gvDescriptor.getName();
		if (isEAR){
			// get local name
			int index = name.lastIndexOf(SharedResourceElements.SEGMENT_SEPARATOR);
			if (index != -1){
				name = name.substring(index+1);
			}
		}
		String value = gvDescriptor.getValueAsString();		
		String description = gvDescriptor.getDescription();
		String type = gvDescriptor.getType();
		if (type == null) {
			type = "String";
		}
		long modificationTime = gvDescriptor.getModificationTime();
		boolean isDeploymentSettable = gvDescriptor.isDeploymentSettable();
		boolean isServiceSettable = gvDescriptor.isServiceSettable();
		Element gvElement = doc.createElement("globalVariable");
		
		Element element = doc.createElement("name");
		gvElement.appendChild(element);
		element.setTextContent(name);
		
		element = doc.createElement("description");
		gvElement.appendChild(element);
		element.setTextContent(description);
		
		element = doc.createElement("value");
		gvElement.appendChild(element);
		element.setTextContent(value);
		
		element = doc.createElement("deploymentSettable");
		gvElement.appendChild(element);
		element.setTextContent(Boolean.toString(isDeploymentSettable));
		
		element = doc.createElement("serviceSettable");
		gvElement.appendChild(element);
		element.setTextContent(Boolean.toString(isServiceSettable));
		
		element = doc.createElement("type");
		gvElement.appendChild(element);
		element.setTextContent(type);
		
		element = doc.createElement("modTime");
		gvElement.appendChild(element);
		element.setTextContent(Long.toString(modificationTime));
		
		return gvElement;
	}
}
