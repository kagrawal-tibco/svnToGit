package com.tibco.cep.studio.ui.editors.globalvar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesDescriptor;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesGroup;

/*
@author ssailapp
@date Dec 29, 2009 11:34:53 PM
 */

public class GlobalVariablesModelParser extends AbstractSharedResourceModelParser {
	
	/**
	 * @param gvGroupPath
	 * @param filePath
	 * @param modelmgr
	 */
	public static void loadModel(String gvGroupPath, String filePath, GlobalVariablesModelMgr modelmgr) {
		if (filePath == null || new File(filePath).length() == 0)
			return;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filePath); 
			Document doc = builder.parse(fis);
			GlobalVariablesGroup gvGrp = loadModel(gvGroupPath, doc, modelmgr, false);
			if (gvGroupPath.equals("/defaultVars.substvar"))
				modelmgr.getModel().gvGrp = gvGrp;
			else
				updateGvGroupModel(modelmgr.getModel().gvGrp, modelmgr, gvGroupPath, gvGrp);
			Collections.sort(modelmgr.getModel().gvGrp.gvs,new NameComparator());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Loads Global Variable Model from input stream
	 * @param gvGroupPath
	 * @param is
	 * @param modelmgr
	 * @param projectlib
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static GlobalVariablesGroup loadModel(GlobalVariablesModel model, String gvGroupPath, InputStream is, GlobalVariablesModelMgr modelmgr, boolean projectlib) 
	throws ParserConfigurationException , SAXException , IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		GlobalVariablesGroup gvGrp = loadModel(gvGroupPath, doc, modelmgr, projectlib);
		if (gvGroupPath.equals("/defaultVars.substvar")) {
			model.gvGrp = gvGrp;
		} else {
			updateGvGroupModel(model.gvGrp, modelmgr, gvGroupPath, gvGrp);
		}
		return gvGrp;
	}

	/**
	 * @param gvGroupPath
	 * @param doc
	 * @param modelmgr
	 * @param projectlib
	 * @return
	 */
	public static GlobalVariablesGroup loadModel(String gvGroupPath, 
			                                     Document doc, 
			                                     GlobalVariablesModelMgr modelmgr, 
			                                     boolean projectlib) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());

		GlobalVariablesGroup gvGrp = modelmgr.getModel().new GlobalVariablesGroup(gvGroupPath, projectlib);
		NodeList fileNodeList = root.getChildNodes();
		for (int n=0; n<fileNodeList.getLength(); n++) {
			Node fileNode = fileNodeList.item(n);
			if (fileNode == null || !isValidFileNode(fileNode)) {
				continue;
			}
			String fileNodeName = fileNode.getNodeName();
			fileNode.getAttributes();
			if (fileNodeName.equalsIgnoreCase("globalVariables")) {
				NodeList gvsNodeList = fileNode.getChildNodes();
				for (int c=0; c<gvsNodeList.getLength(); c++) {
					Node gvNode = gvsNodeList.item(c);
					if (!isValidFileNode(gvNode))
						continue;
					GlobalVariablesDescriptor desc = modelmgr.getModel().new GlobalVariablesDescriptor(projectlib);
					NodeList gvPropsList = gvNode.getChildNodes();
					for (int f=0; f<gvPropsList.getLength(); f++) {
						Node fieldNode = gvPropsList.item(f);
						if (!isValidFileNode(fieldNode))
							continue;
						String fieldName = fieldNode.getNodeName();
						String fieldValue = fieldNode.getTextContent();
						if (fieldName.equals("name")) {
							desc.setName(fieldValue);
						} else if (fieldName.equals("description")) {
							desc.setDescription(fieldValue);
						} else if (fieldName.equals("value")) {
							desc.setValue(fieldValue);
						} else if (fieldName.equals("deploymentSettable")) {
							desc.setDeploymentSettable(new Boolean(fieldValue).booleanValue());
						} else if (fieldName.equals("serviceSettable")) {
							desc.setServiceSettable(new Boolean(fieldValue).booleanValue());
						} else if (fieldName.equals("type")) {
							desc.setType(fieldValue);
						} else if (fieldName.equals("constraint")) {
							desc.setConstraint(fieldValue);
						} else if (fieldName.equals("modTime")) {
							desc.setModificationTime(new Long(fieldValue).longValue());
						}
					}
					gvGrp.gvs.add(desc);
					desc.parentGrp = gvGrp;
				}
			}
		}
		return gvGrp;
	}
	
	/**
	 * @param gvp
	 * @param modelmgr
	 * @param gvGroupPath
	 * @param gvGrp
	 */
	private static void updateGvGroupModel(GlobalVariablesGroup gvp, 
			                               GlobalVariablesModelMgr modelmgr, 
			                               String gvGroupPath, 
			                               GlobalVariablesGroup gvGrp) {
		String paths[] = gvGroupPath.split("/");
		GlobalVariablesGroup curGrp = gvp;
		boolean found = false;
		String cumulPath = "";
		for (int i=0; i<paths.length-2; i++) { 
			found = false;
			if (!paths[i].equals(""))
				cumulPath += "/" + paths[i];
			for (GlobalVariablesGroup grp: curGrp.gvGrps) {
				if (!grp.name.equals(paths[i]))
					continue;
				curGrp = grp;
				found = true;
				break;
			}
			if (!found && !cumulPath.equals("")) {
				GlobalVariablesGroup newGrp = modelmgr.getModel().new GlobalVariablesGroup(cumulPath, false);
				curGrp.gvGrps.add(newGrp);
				newGrp.parentGrp = curGrp;
				curGrp = newGrp;
			}
		}
		curGrp.gvGrps.add(gvGrp);
		gvGrp.parentGrp = curGrp;
	}

	/**
	 * @param doc
	 * @param gvs
	 */
	public static void saveModelParts(Document doc, ArrayList<GlobalVariablesDescriptor> gvs) {
		Element rootNode = doc.getDocumentElement();
		rootNode.setAttribute("xmlns", "http://www.tibco.com/xmlns/repo/types/2002");
		Element gvGrpNode = doc.createElement("globalVariables");
		for (GlobalVariablesDescriptor gv: gvs) {
			Element gvNode = doc.createElement("globalVariable");
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_NAME, gv.getName());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_VALUE, gv.getValueAsString());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_TYPE, gv.getType());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_ISDEPLOY, new Boolean(gv.isDeploymentSettable()).toString());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_ISSERVICE, new Boolean(gv.isServiceSettable()).toString());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_DESCRIPTION, gv.getDescription());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_CONSTRAINT, gv.getConstraint());
			createTextElementNode(doc, gvNode, GlobalVariablesModel.FIELD_MODTIME, new Long(gv.getModificationTime()).toString());
			gvGrpNode.appendChild(gvNode);
		}
		rootNode.appendChild(gvGrpNode);
	}

	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
	}

	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
	}
}
