package com.tibco.cep.sharedresource.ssl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
/*
@author ssailapp
@date Dec 28, 2009 12:18:39 PM
 */

public abstract class SslConfigModelParser extends AbstractSharedResourceModelParser {

	public void loadModel(SslConfigModel sslConfigModel, Node parentNode) {
   		String prefix = parentNode.getPrefix();
   		if (prefix != null)
   			prefix += ":";
   		else
   			prefix = "";
		NodeList fileNodeList = parentNode.getChildNodes();
		for (int c=0; c<fileNodeList.getLength(); c++) {
			Node node = fileNodeList.item(c);
			if (!isValidFileNode(node))
				continue;
			if (node.getNodeName().equals(prefix + SslConfigModel.ID_CERT_FOLDER)) {
				sslConfigModel.setCert(node.getTextContent());
			} else if (node.getNodeName().equals(prefix + SslConfigModel.ID_IDENTITY)) {
				sslConfigModel.setIdentity(node.getTextContent());
			}else if (node.getNodeName().equals(prefix + SslConfigModel.ID_TRUST_STORE_PASSWD)) {
				
				char [] decrypted = null;
				boolean isTrustStorePasswdGv = false;
				try {
					//if(!node.getTextContent().isEmpty()){
					if(GvUtil.isGlobalVar(node.getTextContent())){
						decrypted = node.getTextContent().toCharArray();
						isTrustStorePasswdGv = true;
					}
					else if (ObfuscationEngine.hasEncryptionPrefix(node.getTextContent())){ 
						decrypted = ObfuscationEngine.decrypt(node.getTextContent());
						isTrustStorePasswdGv = false;
					}
					//}
				} catch (AXSecurityException e) {
					e.printStackTrace();
				}
				if(decrypted==null){
					sslConfigModel.setTrustStorePasswd("", isTrustStorePasswdGv);
				}
				else{
					sslConfigModel.setTrustStorePasswd(new String(decrypted), isTrustStorePasswdGv); 
				}
				
			} else {
				loadAdvancedNodes(node, prefix, sslConfigModel);
			} 
		}
	}
	
	public abstract void loadAdvancedNodes(Node node, String prefix, SslConfigModel sslConfigModel);
	
	public void saveModelParts(Document doc, Element parentNode, SslConfigModel model) {
		Element sslNode = doc.createElement("ssl");
		sslNode.setAttribute("xmlns", "http://www.tibco.com/xmlns/aemeta/services/2002");
		Element certNode = createTextElementNode(doc, sslNode, SslConfigModel.ID_CERT_FOLDER, model.getCert());
		certNode.setAttribute("isRef", "true");
		Element idNode = createTextElementNode(doc, sslNode, SslConfigModel.ID_IDENTITY, model.getIdentity());
		idNode.setAttribute("isRef", "true");
		
		String encrypted = "";
		try {
			//if(!model.trustStorePasswd.isEmpty()){
			if(GvUtil.isGlobalVar(model.getTrustStorePasswd()) && model.isTurstStorePasswordGv()){
				encrypted = model.getTrustStorePasswd();
			}
			else if (!ObfuscationEngine.hasEncryptionPrefix(model.getTrustStorePasswd())) {
				encrypted = ObfuscationEngine.encrypt(model.getTrustStorePasswd().toCharArray());
			}
			//}
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		Element passNode = createTextElementNode(doc, sslNode, SslConfigModel.ID_TRUST_STORE_PASSWD, encrypted);
		
		saveAdvancedNodes(doc, sslNode, model);
		parentNode.appendChild(sslNode);
	}

	public abstract void saveAdvancedNodes(Document doc, Node sslNode, SslConfigModel sslConfigModel);
	
	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
	}
	
	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
	}
}
