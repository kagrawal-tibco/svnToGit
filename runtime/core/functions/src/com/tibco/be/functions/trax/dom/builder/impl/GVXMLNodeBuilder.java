package com.tibco.be.functions.trax.dom.builder.impl;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.be.functions.trax.dom.builder.XMLNodeBuilder;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;


public class GVXMLNodeBuilder implements XMLNodeBuilder {
	
	private static Element gvDocumentElement;

	@Override
	public Element build(Document document, Object object, String rootTagName) {
		if(!(object instanceof GlobalVariables)){
			throw new RuntimeException("can handle event only");
		}
		
		if(gvDocumentElement != null){
			return gvDocumentElement;
		}
		GlobalVariables GVs = (GlobalVariables) object;

		synchronized (GVs) {
			LinkedHashMap<String, Node> vars = new LinkedHashMap<String, Node>();
			Node gvNode = document.createElementNS(null, "globalVariables");
			document.appendChild(gvNode);
			
			Collection<GlobalVariableDescriptor> _globalVariables = GVs.getVariables();
			for (GlobalVariableDescriptor _globalVariable : _globalVariables) {
		        final String name = _globalVariable.getName();
				final String value = _globalVariable.getValueAsString();
				final String[] path = name.split("/");
				Node node = gvNode;
				StringBuilder pathstr = new StringBuilder();
				for (int i = 0; i < path.length; i++) {
					pathstr.append(path[i]);
					// this ensures that folder's keys in the vars map always end in
					// /
					if (i < path.length - 1)
						pathstr.append("/");
					Node temp = (Node) vars.get(pathstr.toString());
					if (i == path.length - 1) {
						if (temp == null) {
							Element e = document.createElementNS(null, path[i]);
							node = node.appendChild(e);
							node.setTextContent(value);
							vars.put(pathstr.toString(), node);
						} else {
							temp.setTextContent(value);
						}
					} else {
						if (temp == null) {
							Element e = document.createElementNS(null, path[i]);
							node = node.appendChild(e);
							vars.put(pathstr.toString(), node);
						} else {
							node = temp;
						}
					}
				}
			}
			gvDocumentElement = document.getDocumentElement();
		}
		return gvDocumentElement;

	}

}
