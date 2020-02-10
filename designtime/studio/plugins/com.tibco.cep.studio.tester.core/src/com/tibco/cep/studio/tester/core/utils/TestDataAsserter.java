package com.tibco.cep.studio.tester.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.DebugTestDataInputTask;
import com.tibco.cep.studio.debug.input.TesterInputTask;
import com.tibco.cep.studio.tester.core.StudioTesterCorePlugin;
import com.tibco.cep.util.JSONXiNodeConversionUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * 
 * @author sasahoo
 *
 */
public class TestDataAsserter {

	protected Map<String, String> files;
	
    protected Map<String, String> assertFileslist = new LinkedHashMap<String, String>();
    
    protected Map<String, String> entityURIMap = new HashMap<String, String>();
	
    protected String testerSession;
	
	protected String ruleSession;
	
	protected String inputDir;	
	
	/**
	 * The studio/DM project
	 */
	protected IProject workspaceProject;
	
	protected Map<String/*filepath*/, Set<String>/*rowNum*/> processedFileRowNumMap = new HashMap<String, Set<String>>();
	protected List<XiNode> nodesTobeAsserted = new ArrayList<XiNode>();
	protected Map<XiNode,String> nodesTobeAssertedVsTestDataName=new HashMap<XiNode,String>();
	private String clusterName = null;

	/**
	 * @param testDataRootDirectory
	 * @param session
	 */
	public TestDataAsserter(IRuleRunTarget runTarget, 
			                String testerSession, 
			                String ruleSession, 
			                Map<String, String> files, 
			                String clusterName, 
			                Map<String, String> entityURIMap, 
			                String inputDir) {
		this.testerSession = testerSession;
		this.ruleSession = ruleSession;
		this.files = files;
		this.clusterName = clusterName;
		this.entityURIMap = entityURIMap;
		this.workspaceProject = runTarget.getWorkspaceProject();
		this.inputDir = "";
	}
	
	
	/**
	 * @param monitor
	 */
	@SuppressWarnings("unchecked")
	public void deployTestDataFiles(IProgressMonitor monitor) throws Exception {
		for (String file : files.keySet()) {
			if (file.endsWith(".scorecardtestdata")) {
				buildTestDataFilesToAssert(file, monitor);
			}
		}
		for (String file : files.keySet()) {
			if (file.endsWith(".concepttestdata")) {
				preNodeAssertProcess(null, file, -1);
				
			} 
		}
		int count = 0 ;
		for (XiNode node : nodesTobeAsserted) {
			XiNode sAttribElement = node.getAttribute(ExpandedName.makeName("isSelected"));
			if (sAttribElement != null) {
				String select = node.getAttributeStringValue(ExpandedName.makeName("isSelected"));
				if (!Boolean.parseBoolean(select)) {
					continue;
				}
				node.removeAttribute(ExpandedName.makeName("isSelected"));
				Iterator<XiNode> propertyNodeIterator = node.getChildren();
				while (propertyNodeIterator.hasNext()) {
					XiNode propertyNode = propertyNodeIterator.next();
					propertyNode=checkForGlobalVariables(propertyNode);
/*					XiNode resPathAttribElement = propertyNode.getAttribute(ExpandedName.makeName("resourcePath"));
					if (resPathAttribElement != null) {
						propertyNode.removeAttribute(ExpandedName.makeName("resourcePath"));
					}
					XiNode rowIdAttribElement = propertyNode.getAttribute(ExpandedName.makeName("rowNum"));
					if (rowIdAttribElement != null) {
						propertyNode.removeAttribute(ExpandedName.makeName("rowNum"));
					}
					XiNode typeAttribElement = propertyNode.getAttribute(ExpandedName.makeName("type"));
					if (typeAttribElement != null) {
						propertyNode.removeAttribute(ExpandedName.makeName("type"));
					}*/
				}
				StringWriter stringWriter = new StringWriter();
				DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(stringWriter, "UTF-8");
				node.serialize(handler);
				String entityString = stringWriter.toString();
				if(nodesTobeAssertedVsTestDataName.get(node)!=null){
					assertFileslist.put(nodesTobeAssertedVsTestDataName.get(node) + ".concepttestdata" + "_"+ count, entityString);
				}else{
					assertFileslist.put(node.getName().getLocalName() + ".concepttestdata" + "_"+ count, entityString);
				}
				count++;
				stringWriter.flush();
			}
		}
		for (String file : files.keySet()) {
			if (file.endsWith("eventtestdata")) {
				buildTestDataFilesToAssert(file, monitor);
			}
		}
	}
	
	private XiNode checkForGlobalVariables(XiNode propertyNode) {
		String value = propertyNode.getStringValue();
		String valueOfGV = GvUtil.getGvDefinedValue(workspaceProject, value);
		propertyNode.setStringValue(valueOfGV);
		return propertyNode;
	}

	/**
	 * @param file
	 * @param monitor
	 */
	@SuppressWarnings("unchecked")
	private void buildTestDataFilesToAssert(String file, IProgressMonitor monitor) {
		File newFile = new File(file);
		if (newFile.exists() && newFile.length() > 0) {
			try {
				FileInputStream fis = new FileInputStream(newFile);
				XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(fis));
				XiNode firstChild = rootNode.getFirstChild();
				String rateStr = firstChild.getAttributeStringValue(ExpandedName.makeName("rate"));
				int rate = -1;
				if (rateStr != null && !rateStr.isEmpty()) {
					try {
						rate = Integer.parseInt(rateStr);
					} catch (Exception e) {
					}
				}
				BigDecimal bd = new BigDecimal(1000);
				String delay = rate != -1 ? bd.divide(BigDecimal.valueOf(rate), 3, RoundingMode.HALF_UP).toString() : null; 

				String preProcURI = firstChild.getAttributeStringValue(ExpandedName.makeName("preprocessor"));
				boolean invokePreprocessor = preProcURI != null && !preProcURI.isEmpty();
				Iterator<XiNode> nodeIterator = firstChild.getChildren();
				int count = 0;
				ExpandedName JSON = ExpandedName.makeName("json");
				while (nodeIterator.hasNext()) {
					XiNode node = nodeIterator.next();
					XiNode sAttribElement = node.getAttribute(ExpandedName.makeName("isSelected"));
					if (sAttribElement != null) {
						String select = node.getAttributeStringValue(ExpandedName.makeName("isSelected"));
						if (!Boolean.parseBoolean(select)) {
							continue;
						}
						if (delay != null) {
							node.setAttributeStringValue(ExpandedName.makeName("delay"), delay);
						}
						if (invokePreprocessor) {
							node.setAttributeStringValue(ExpandedName.makeName("preprocessor"), preProcURI);
						}
						node.removeAttribute(ExpandedName.makeName("isSelected"));
						Iterator<XiNode> propertyNodeIterator = node.getChildren();
						while (propertyNodeIterator.hasNext()) {
							XiNode propertyNode = propertyNodeIterator.next();
							String expandedLocalName = propertyNode.getName().getLocalName();
							//No check for GVs for event payload node
							if (!expandedLocalName.equals("payload")) {
								propertyNode = checkForGlobalVariables(propertyNode);
							} else {
								// check for json payload and convert
								String jsonStr = propertyNode.getAttributeStringValue(JSON);
								if (jsonStr != null) {
									propertyNode.removeAttribute(JSON);
									String xml = (String) JSONXiNodeConversionUtil.convertJSONToXiNode(jsonStr, node.getName().namespaceURI);
									XiNode payloadNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(xml)));
									propertyNode.appendChild(payloadNode.getFirstChild());
								}
							}
						}
						StringWriter stringWriter = new StringWriter();
						DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(stringWriter, "UTF-8");
						node.serialize(handler);
						String entityString = stringWriter.toString();
						assertFileslist.put(file + "_" + count , entityString);
						count++;
						stringWriter.flush();
					}
				}
			} catch (IOException e) {
				StudioTesterCorePlugin.log(e);
			}
			catch (SAXException e) {
				StudioTesterCorePlugin.log(e);
			}
		}
	}
	
	/**
	 * @param file
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void preNodeAssertProcess(XiNode node, String filename, int rowNum) throws Exception {
		File file = new File(filename);
		if (!file.isAbsolute()) {
			filename = workspaceProject.getLocation().toString() + filename;
			file = new File(filename);
		}
		if (!file.exists()) {
			IFile ifile = workspaceProject.getFile(filename);
			if (ifile.exists()) {
				filename = ifile.getLocation().toString();
				file = new File(filename);
			}
		}
		if (file.exists() && file.length() > 0 ) {
			if (!processedFileRowNumMap.containsKey(filename)) {
				processedFileRowNumMap.put(filename, new HashSet<String>());
			}
			int count = 0;
			FileInputStream fis = new FileInputStream(file);
			XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(fis));
			XiNode mainNode = rootNode.getFirstChild();
			
			XiNode nodeEntityPath=mainNode.getAttribute(ExpandedName.makeName("entityPath"));
			if (nodeEntityPath != null && node != null) {
				// the test data path might not equal the entity path, set this to allow proper assertion lookup
				String entPath = nodeEntityPath.getStringValue();
				node.setAttributeStringValue(ExpandedName.makeName("entityPath"), entPath);
				XiNode attr = node.removeAttribute(ExpandedName.makeName("type"));
				node.setAttribute(attr);
			}
			
			XiNode nodeName=mainNode.getAttribute(ExpandedName.makeName("name"));
			String testDataFileNameTobeAsserted = null;
			String testDataTypeoBeAsserted;
			if(nodeName!=null){
				testDataFileNameTobeAsserted = mainNode.getAttribute(ExpandedName.makeName("name")).getStringValue();
				testDataTypeoBeAsserted = mainNode.getAttribute(ExpandedName.makeName("type")).getStringValue();
			}
			
			Iterator<XiNode> subNodeIterator = mainNode.getChildren();
			while (subNodeIterator.hasNext()) {
				XiNode childNode = subNodeIterator.next();
				String select = childNode.getAttributeStringValue(ExpandedName.makeName("isSelected"));
				if (select != null && !Boolean.parseBoolean(select)) {
					continue;
				}

				if (processedFileRowNumMap.get(filename).contains(Integer.toString(count))) {
					count ++;
					continue;
				}
				
				if (rowNum != -1 && count != rowNum) {
					count ++;
					continue;
				}
				
				Iterator<XiNode> propertyNodeIterator = childNode.getChildren();
				List<XiNode> propertyNodes = new ArrayList<XiNode>();
				while (propertyNodeIterator.hasNext()) {
					XiNode propertyNode = propertyNodeIterator.next();
					propertyNodes.add(propertyNode);
				}
				List<XiNode> mulNodes = new ArrayList<XiNode>();
				for (int k =0; k < propertyNodes.size() ; k++) {
					if (k > 0 && propertyNodes.get(k).getName().equals(propertyNodes.get(k-1).getName())) {
						if (!mulNodes.contains(propertyNodes.get(k-1))) {
							mulNodes.add(propertyNodes.get(k-1));
						}
						if (!mulNodes.contains(propertyNodes.get(k))) {
							mulNodes.add(propertyNodes.get(k));
						}
						if ( (k + 1 < propertyNodes.size())
								&& (!propertyNodes.get(k).getName().equals(propertyNodes.get(k+1).getName()))) {
							preProcessMultipleRefNodes(mulNodes, inputDir);
						}
						if (k +1 == propertyNodes.size() && mulNodes.size() > 0 ) {
							preProcessMultipleRefNodes(mulNodes, inputDir);
						}
					} else {
						if ( (k + 1 < propertyNodes.size())
								&& (!propertyNodes.get(k).getName().equals(propertyNodes.get(k+1).getName()))) {
							preProcessSimpleRefNodes(propertyNodes.get(k), inputDir);
						} else {
							if (k == propertyNodes.size() -1){
								if (!mulNodes.contains(propertyNodes.get(k))) {
									preProcessSimpleRefNodes(propertyNodes.get(k), inputDir);
								}
							}
						}
						mulNodes = new ArrayList<XiNode>();
					}
				}
				if (!processedFileRowNumMap.get(filename).contains(Integer.toString(count))) {
					processedFileRowNumMap.get(filename).add(Integer.toString(count));
				}
				if (!nodesTobeAsserted.contains(childNode) ) {
					nodesTobeAsserted.add(childNode);
					nodesTobeAssertedVsTestDataName.put(childNode, testDataFileNameTobeAsserted);
				}
				count++;
			}
			fis.close();
		}
	}
	
	/**
	 * Simple Data Type
	 * @param node
	 * @param v
	 * @param value
	 */
	private void preProcessSimpleRefNodes(XiNode node, String inputDir) throws Exception {
		XiNode typeNode = node.getAttribute(ExpandedName.makeName("type"));
		if (typeNode != null ) {
			String rowNum = node.getAttributeStringValue(ExpandedName.makeName("rowNum"));
			String path = node.getAttributeStringValue(ExpandedName.makeName("resourcePath"));
			String file = inputDir + path +  ".concepttestdata";
			if (typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())) {
				if (!rowNum.trim().equals("")) {
					preNodeAssertProcess(node, file, Integer.parseInt(rowNum));
				}
			}
			if (typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT.getName())) {
				if (!rowNum.trim().equals("")) {
					preProcessContainedNode(node, file, Integer.parseInt(rowNum));
				}
			}
		} 
	}
	
	/**
	 * @param mulNodes
	 * @param inputDir
	 * @throws Exception
	 */
	private void preProcessMultipleRefNodes(List<XiNode> mulNodes, String inputDir) throws Exception {
		XiNode typeNode = mulNodes.get(0).getAttribute(ExpandedName.makeName("type"));
		if (typeNode != null) {
			if (typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())) {	
				for (int l = 0; l < mulNodes.size(); l++) {
					String rowNum = mulNodes.get(l).getAttributeStringValue(ExpandedName.makeName("rowNum"));
					String path = mulNodes.get(l).getAttributeStringValue(ExpandedName.makeName("resourcePath"));
					String file = inputDir + path +  ".concepttestdata";
					if (!rowNum.trim().equals("")) {
						preNodeAssertProcess(mulNodes.get(l), file, Integer.parseInt(rowNum));
					}
				}
			}
			if (typeNode.getStringValue().equals(PROPERTY_TYPES.CONCEPT.getName())) {
				for (int l = 0; l < mulNodes.size(); l++) {
					String rowNum = mulNodes.get(l).getAttributeStringValue(ExpandedName.makeName("rowNum"));
					String path = mulNodes.get(l).getAttributeStringValue(ExpandedName.makeName("resourcePath"));
					String file = inputDir + path +  ".concepttestdata";
					if (!rowNum.trim().equals("")) {
						preProcessContainedNode(mulNodes.get(l), file, Integer.parseInt(rowNum));
					}
				}
			}
		} 
	}
	
	/**
	 * @param node
	 * @param filename
	 * @param rowNum
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void preProcessContainedNode(XiNode node, 
			                             String filename, 
			                             int rowNum) throws Exception {
		
		File file = new File(filename);
		if (!file.isAbsolute()) {
			filename = workspaceProject.getLocation().toString() + filename;
			file = new File(filename);
		}
		if (!file.exists()) {
			IFile ifile = workspaceProject.getFile(filename);
			if (ifile.exists()) {
				filename = ifile.getLocation().toString();
				file = new File(filename);
			}
		}
		if (file.exists() && file.length() > 0 ) {
			if (!processedFileRowNumMap.containsKey(filename)) {
				processedFileRowNumMap.put(filename, new HashSet<String>());
			}
			int count = 0;
			FileInputStream fis = new FileInputStream(file);
			XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(fis));
			XiNode mainNode = rootNode.getFirstChild();
			
			XiNode nodeEntityPath=mainNode.getAttribute(ExpandedName.makeName("entityPath"));
			if (nodeEntityPath != null && node != null) {
				// the test data path might not equal the entity path, set this to allow proper assertion lookup
				String entPath = nodeEntityPath.getStringValue();
				node.setAttributeStringValue(ExpandedName.makeName("entityPath"), entPath);
				XiNode attr = node.removeAttribute(ExpandedName.makeName("type"));
				node.setAttribute(attr);
			}

			Iterator<XiNode> subNodeIterator = mainNode.getChildren();
			while (subNodeIterator.hasNext()) {
				XiNode childNode = subNodeIterator.next();
				if (rowNum != -1 && count != rowNum) {
					count ++;
					continue;
				}
				
				Iterator<XiNode> propertyNodeIterator = childNode.getChildren();
				while (propertyNodeIterator.hasNext()) {
					XiNode propertyNode = propertyNodeIterator.next();
					if(propertyNodeIterator.hasNext()){
						propertyNode.setStringValue(propertyNode.getStringValue()+";");
					}
					node.appendChild(propertyNode);
				}
				if (!processedFileRowNumMap.get(filename).contains(Integer.toString(count))) {
					processedFileRowNumMap.get(filename).add(Integer.toString(count));
				}
				count++;
			}
			fis.close();
		}
	}
	
	/**
	 * @param monitor
	 * @throws DebugException
	 */
	public void doAssert(IProgressMonitor monitor, IRuleRunTarget runTarget) throws DebugException {
		String[] entityArray = new String[assertFileslist.size()];
		entityArray = assertFileslist.values().toArray(entityArray);
		if (clusterName == null) {
			TesterInputTask inputTask = new TesterInputTask(runTarget, entityArray, ruleSession, testerSession);
			runTarget.addInputVmTask(inputTask);
		} else {
			
			StudioTesterCorePlugin.log("Cluster Found: {0}", clusterName);
			List<String> entityURIList = new ArrayList<String>();
			List<String> xmlDataList = new ArrayList<String>();
			List<String> destinationURIList = new ArrayList<String>();
			
			for (String key: assertFileslist.keySet()) {
				String f = key.substring(0, key.lastIndexOf("_"));
				
				String destinationURI = files.get(f);
				if(destinationURI==null){
					Set<String> set=files.keySet();
					for(String str:set){
						if(str.contains(f)){
							destinationURI=files.get(str);
							break;
						}
					}
				}
				destinationURIList.add(destinationURI);
				
				String xmlData = assertFileslist.get(key);
				xmlDataList.add(xmlData);
				
				String entityURI = entityURIMap.get(f);
				if(entityURI==null){
					Set<String> set=entityURIMap.keySet();
					for(String str:set){
						if(str.contains(f)){
							entityURI=entityURIMap.get(str);
							break;
						}
					}
				}
				
				
				if (entityURI.endsWith(".concepttestdata")) {
					entityURI = entityURI.replace(".concepttestdata", "");
				}
				if (entityURI.endsWith(".eventtestdata")) {
					entityURI = entityURI.replace(".eventtestdata", "");
				}
				if (entityURI.endsWith(".scorecardtestdata")) {
					entityURI = entityURI.replace(".scorecardtestdata", "");
				}
				entityURIList.add(entityURI);
				
				
				
				//For single entry xmldata/entity URI/destination URI  
//				VmTask task = new DebugInputTask(runTarget ,xmlData, entityURI,	destinationURI, ruleSession);
//				runTarget.addInputVmTask(task);
			}
			
			//processing batch entry for xmldata/entity URI/destination URI			
			DebugTestDataInputTask task = new DebugTestDataInputTask(runTarget, 
					xmlDataList.toArray(new String[xmlDataList.size()]), 
					entityURIList.toArray(new String[entityURIList.size()]), 
					destinationURIList.toArray(new String[destinationURIList.size()]), ruleSession, testerSession);
			runTarget.addInputVmTask(task);
		}
		
	}
}