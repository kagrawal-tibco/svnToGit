package com.tibco.be.functions.trax.dom.builder.impl;

import java.io.ByteArrayInputStream;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.tibco.be.functions.trax.dom.builder.XMLNodeBuilder;
import com.tibco.be.functions.trax.util.CalendarUtil;
import com.tibco.cep.runtime.model.event.SimpleEvent;


public class SimpleEventXMLNodeBuilder implements XMLNodeBuilder {

	@Override
	public Element build(Document doc, Object object, String rootTagName) {
		if(!(object instanceof SimpleEvent)){
			throw new RuntimeException("can handle event only");
		}
		
		SimpleEvent event = (SimpleEvent) object;
		Element rootElement = doc.createElementNS(null, rootTagName);
		rootElement.setAttributeNS(null, "Id", String.valueOf(event.getId()));
		String extId = event.getExtId();
		if(extId != null && !extId.isEmpty()){
			rootElement.setAttributeNS(null, "extId", String.valueOf(event.getExtId()));
		}
		String[] propNames = event.getPropertyNames();
		for (String name : propNames) {
			Element propElem = doc.createElementNS(null, name);
			try {
				Object propValue = event.getProperty(name);
				String text = "";
				if(propValue instanceof Calendar){
					text = CalendarUtil.toString((Calendar)propValue);
				} else {
					text = String.valueOf(propValue);
				}
				Text textNode = doc.createTextNode(text);
				propElem.appendChild(textNode);
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			}
			rootElement.appendChild(propElem);
		}
		Element payload = doc.createElementNS(null, "payload");
		rootElement.appendChild(payload);
		try {
			
			String payloadAsStr = event.getPayloadAsString();
			System.out.println("Event Payload As String :" + payloadAsStr);
			if(payloadAsStr != null && !payloadAsStr.isEmpty()){
				ByteArrayInputStream stream = new ByteArrayInputStream(payloadAsStr.getBytes("UTF-8"));
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc1 = builder.parse(stream);
				payload.appendChild(doc1.getDocumentElement());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return rootElement;
	}

}
