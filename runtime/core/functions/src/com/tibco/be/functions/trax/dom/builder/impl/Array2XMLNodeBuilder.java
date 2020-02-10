package com.tibco.be.functions.trax.dom.builder.impl;

import java.util.Calendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.be.functions.trax.dom.builder.XMLNodeBuilder;
import com.tibco.be.functions.trax.util.CalendarUtil;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;


public class Array2XMLNodeBuilder implements XMLNodeBuilder {
	
	private static final String ELEMENTS = "elements";

	@Override
	public Element build(Document doc, Object object,
			String rootTagName) {
		Element rootElement = doc.createElementNS(null, rootTagName);
		if(object instanceof Concept[]){
			ConceptXMLNodeBuilder ceptDocBuilder = new ConceptXMLNodeBuilder();
			Concept[] cepts = (Concept[]) object;
			for (int i = 0; i < cepts.length; i++) {
				Concept cept = cepts[i];
				Element ceptElem = ceptDocBuilder.build(doc, cept, ELEMENTS);
				rootElement.appendChild(ceptElem);
			}
		} else if(object instanceof Event[]){
			SimpleEventXMLNodeBuilder eventDocBuilder = new SimpleEventXMLNodeBuilder();
			Event[] events = (Event[])object;
			for (int i = 0; i < events.length; i++) {
				Event event = events[i];
				Element eventElem = eventDocBuilder.build(doc, event, ELEMENTS);
				rootElement.appendChild(eventElem);
			}
		} else if (object instanceof Calendar[]){
				Calendar[] calArray = (Calendar[]) object;
				for (int j = 0; j < calArray.length; j++) {
					String dt = CalendarUtil.toString(calArray[j]);;
					Element e = doc.createElement(ELEMENTS);
					e.setTextContent(dt);
					rootElement.appendChild(e);
				}
		} else if (object instanceof Object[]) {
			Object[] arrObject = (Object[])object;
			for (int i = 0; i < arrObject.length; i++) {
				Element e = doc.createElement(ELEMENTS);
				e.setTextContent(String.valueOf(arrObject[i]));
				rootElement.appendChild(e);
			}
		} 
		return rootElement;
	}

}
