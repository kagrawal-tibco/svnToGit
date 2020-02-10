package com.tibco.be.functions.object;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.tibco.cep.runtime.model.PropertyNullValues;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.schema.flavor.XSDLConstants;

public class InstanceSerializationHelper {
	
	private static final char[] NEWLINE = "\n".toCharArray();
	private static final char[] SPACES  = "    ".toCharArray();
	private static final int NEWLINE_LEN = NEWLINE.length;
	private static final int SPACES_LEN  = SPACES.length;

	
    private static final ThreadLocal<AtomicInteger> indentCount = new ThreadLocal<AtomicInteger>() {
        public AtomicInteger initialValue() {
            return new AtomicInteger();
        }
    };
	
    private static final ThreadLocal<HashSet> serializedNodes = new ThreadLocal<HashSet>() {
        public HashSet initialValue() {
            return new HashSet();
        }
    };
    
    public static void serialize(ContentHandler handler, Concept cept, boolean pretti){
    	try {
        	String ceptName = cept.getExpandedName().getLocalName();
        	serialize(handler, cept, ceptName, pretti);
    	} finally { //compulsory clearing hash set
	    	Set nodes = (Set) serializedNodes.get();
	    	nodes.clear();
    	}
    }

    public static void serialize(ContentHandler handler, Concept cept, String ceptName, boolean pretti){
    	try {
			
			Set nodes = (Set) serializedNodes.get();
			if (nodes.contains(cept)) {
			    // then already its being serialized, so skip
				return;
			}
			String prefix = "";
			if(nodes.isEmpty()){
				handler.startDocument();
				prefix = "ns0";
				if(pretti){
					handler.ignorableWhitespace(NEWLINE, 0, NEWLINE_LEN);
				}
			}
			nodes.add(cept);
			
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", Concept.ATTRIBUTE_ID,    "", "CDATA", String.valueOf(cept.getId()));
			if (cept.getExtId() != null) {
				atts.addAttribute("", Concept.ATTRIBUTE_EXTID, "", "CDATA", cept.getExtId());
			}		
			atts.addAttribute("", Concept.ATTRIBUTE_TYPE,    "", "CDATA", String.valueOf(cept.getType()));
			
			String uri = cept.getExpandedName().getNamespaceURI();
			if(pretti){
				int count = indentCount.get().get();
				for(int i=0; i<count; i++){
					handler.ignorableWhitespace(SPACES, 0, SPACES_LEN);
				}
			}
			handler.startElement(uri, ceptName, prefix, atts);
			if(pretti){
				handler.ignorableWhitespace(NEWLINE, 0, NEWLINE_LEN);
				indentCount.get().incrementAndGet();
			}
			Property []p = null;
			if(((ConceptImpl)cept).excludeNullProps()){
				p = ((ConceptImpl)cept).getPropertiesNullOK();
			} else {
				p = cept.getProperties();
			}
			for (int i = 0; i < p.length; i++) {
				Property property = p[i];
				if(property == null){
					continue;
				}
				String propName = property.getName();
				if(property instanceof PropertyAtom){
					if(property instanceof PropertyAtomConcept && ((PropertyAtomConcept)property).getConcept() != null){
						serialize(handler, ((PropertyAtomConcept)property).getConcept(), propName, pretti);
					} else {
						addProperty(handler,(PropertyAtom)property, pretti);
					}
				} else if(property instanceof PropertyArray){
					PropertyArray pa = ((PropertyArray)property);
					//PropertyAtom []pa = ((PropertyArray)property).toArray();
					for (int j = 0; j < pa.length(); j++) {
						PropertyAtom propertyAtom = pa.get(j);
						if(propertyAtom instanceof PropertyAtomConcept && ((PropertyAtomConcept)propertyAtom).getConcept() != null){
							serialize(handler, ((PropertyAtomConcept)propertyAtom).getConcept(), propName, pretti);
						} else {
							addProperty(handler, propertyAtom, pretti);
						}
					}
				}
			}
			if(pretti){
				indentCount.get().decrementAndGet();
				int count = indentCount.get().get();
				for(int i=0; i<count; i++){
					handler.ignorableWhitespace(SPACES, 0, SPACES_LEN);
				}
			}
			handler.endElement(uri, ceptName, prefix);
			if(pretti){
				handler.ignorableWhitespace(NEWLINE, 0, NEWLINE_LEN);
			}
			nodes.remove(cept);
			if(nodes.isEmpty()){
				handler.endDocument();
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
	
	private static void addProperty(ContentHandler handler, PropertyAtom pa, boolean pretti) throws SAXException {
		if (PropertyNullValues.isPropertyValueNull(pa)) {
			if (!((ConceptImpl) pa.getParent()).excludeNullProps()) {
				AttributesImpl atts = new AttributesImpl();
				String uri = "";
				String localName = pa.getName();
				if (((ConceptImpl) pa.getParent()).setNilAttribs()) {
					// if xsd is defined with nillable attribute, set xsi:nil
					atts.addAttribute(XSDLConstants.ENAME_ATTR_NIL.namespaceURI, XSDLConstants.ENAME_ATTR_NIL.localName, "xsi", "CDATA", "true");
				}
				
				if(pretti){
					int count = ((AtomicInteger)indentCount.get()).get();
					for(int i=0; i<count; i++){
						handler.ignorableWhitespace(SPACES, 0, SPACES_LEN);
					}
				}

				handler.startElement(uri, localName, "", atts);
				handler.endElement(uri, localName, "");
				if(pretti){
					handler.ignorableWhitespace(NEWLINE, 0, NEWLINE_LEN);
				}
			}
		} else { // default behavior
			AttributesImpl atts = new AttributesImpl();
			String uri = "";
			String localName = pa.getName();
			if(pretti){
				int count = ((AtomicInteger)indentCount.get()).get();
				for(int i=0; i<count; i++){
					handler.ignorableWhitespace(SPACES, 0, SPACES_LEN);
				}
			}
			handler.startElement(uri, localName, "", atts);
			if (pa.getValue() != null) {
				char ch[] = pa.getXMLTypedValue().toString().toCharArray();
				handler.characters(ch, 0, ch.length);
			} else if (! ((ConceptImpl) pa.getParent()).excludeNullProps()) {
				//append the property, but not its value, since it is null
				handler.startElement(uri, localName, "", atts);
			}
			handler.endElement(uri, localName, "");
			if(pretti){
				handler.ignorableWhitespace(NEWLINE, 0, NEWLINE_LEN);
			}
		}
	}
}