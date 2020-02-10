package com.tibco.be.functions.trax.dom.builder.impl;

import java.util.Calendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.tibco.be.functions.trax.dom.builder.XMLNodeBuilder;
import com.tibco.be.functions.trax.util.CalendarUtil;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;


public class ConceptXMLNodeBuilder implements XMLNodeBuilder {

	private Document document;

	@Override
	public Element build(Document doc, Object obj, String rootTagName) {
		if(!(obj instanceof Concept)){
			throw new RuntimeException("Can handle only concept instance");
		}
		Concept cept = (Concept) obj;
		setDocument(doc);
		Element ceptElement = toDOMNode(cept, rootTagName);
		return ceptElement;
	}
	
	private void setDocument(Document document){
		this.document = document;
	}
	
	private Document getDocument(){
		return this.document;
	}
	
	private Element toDOMNode(Concept cept, String rootTagName){
		Document doc = getDocument();
		Element ceptElement = doc.createElementNS(null, rootTagName);
		ceptElement.setAttributeNS(null, "Id", String.valueOf(cept.getId()));
		String extId = cept.getExtId();
		if(extId != null && !extId.isEmpty()){
			ceptElement.setAttributeNS(null, "extId", String.valueOf(extId));
		}
		
		Property[] props = cept.getProperties();
		for (Property property : props) {
			if(property == null){
				continue;
			}
			if(property instanceof PropertyAtom){
				PropertyAtom pa = (PropertyAtom) property;
				if(property instanceof PropertyAtomConcept && ((PropertyAtomConcept)property).getConcept() != null){
					Concept paConcept = ((PropertyAtomConcept)property).getConcept();
					Node node = toDOMNode(paConcept, paConcept.getExpandedName().getLocalName());
					ceptElement.appendChild(node);
				} else {
					addPropertyAtomNode(doc, ceptElement, pa);
				}
			} else if(property instanceof PropertyArray){
				PropertyAtom []paArray = ((PropertyArray)property).toArray();
				for (int j = 0; j < paArray.length; j++) {
					PropertyAtom pa = paArray[j];
					if(pa instanceof PropertyAtomConcept && ((PropertyAtomConcept)pa).getConcept() != null){
						Concept paConcept = ((PropertyAtomConcept)pa).getConcept();
						Node node = toDOMNode(paConcept, paConcept.getExpandedName().getLocalName());
						ceptElement.appendChild(node);
					} else {
						addPropertyAtomNode(doc, ceptElement, pa);
					}
				}
			}
		}
		
		return ceptElement;
	}
	private void addPropertyAtomNode(Document doc, Element ceptElement,
			PropertyAtom pa) {
		if(pa.getString() != null){
			Element paElement = doc.createElementNS(null, pa.getName());
			Text paText = null;
			if(pa instanceof PropertyAtomDateTime){
				Calendar cal = (Calendar)pa.getValue();
				String propValue = CalendarUtil.toString(cal);
				paText = doc.createTextNode(propValue);
			} else {
				paText = doc.createTextNode(pa.getString());
			}
			paElement.appendChild(paText);
			ceptElement.appendChild(paElement);
		}
	}
}
