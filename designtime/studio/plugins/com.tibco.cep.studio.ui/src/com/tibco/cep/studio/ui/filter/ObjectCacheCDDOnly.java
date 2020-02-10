package com.tibco.cep.studio.ui.filter;

/**
 * @author smarathe
 * Filters only those cdd files which have cache manager as the object management Type
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.util.CommonUtil;

public class ObjectCacheCDDOnly extends ViewerFilter {

	protected boolean visible = false;
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		// TODO Auto-generated method stub
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isObjectCacheCDDFile(file);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(element instanceof SharedElementRootNode){
			return true;
		}else if(element instanceof DesignerElement){
			return true;
		}else{
			return showSubNode(element);
		}
	}
	
	protected boolean showSubNode(Object element){
		if(!(element instanceof IResource)){
			return false;
		}
		return true;
	}
	
	protected boolean isVisible(Object element) {
		
		Object[] object = CommonUtil.getResources((IFolder) element);
		
		for(Object obj : object){
			if(obj instanceof IFolder){
				isVisible(obj);
			}
			if(obj instanceof IFile){
				
				if(isObjectCacheCDDFile((IFile)obj)){
					visible = true;
				}
			}
		}
		
		if (visible == true) {
			return true;
		}
		return false;
	}

	private boolean isObjectCacheCDDFile(IFile file) {
		String filePath = file.getLocation().toOSString();
		if (filePath == null || new File(filePath).length() == 0)
			return false;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filePath);
			Document doc = builder.parse(fis);
			Element root = doc.getDocumentElement();
			NodeList fileNodeList = root.getChildNodes();
			for (int n=0; n<fileNodeList.getLength(); n++) {
				Node fileNode = fileNodeList.item(n);
				if (fileNode == null || !isValidFileNode(fileNode)) {
					continue;
				}
				String fileNodeName = fileNode.getLocalName();
				if(fileNodeName.equals("object-management")) {
					Node objectManagementTypeNode = fileNode.getFirstChild();
					while(!isValidFileNode(objectManagementTypeNode)) {
						objectManagementTypeNode = objectManagementTypeNode.getNextSibling();
					}
					String objectManagementType = objectManagementTypeNode.getLocalName();
					// Now since support for in memory CDD is also provided adding this check.
					if(objectManagementType.equals("cache-manager") || objectManagementType.equals("memory-manager")) {
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	private static boolean isValidFileNode(Node node) {
		if (node != null)
			return (isValidFileNode(node.getLocalName()));
		return false;
	}
	
	private static boolean isValidFileNode(String name) {
		if (name==null)
			return false;
		String ignList[] = { "#text", "#comment" };
		for (String ign: ignList) {
			if (ign.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

}
