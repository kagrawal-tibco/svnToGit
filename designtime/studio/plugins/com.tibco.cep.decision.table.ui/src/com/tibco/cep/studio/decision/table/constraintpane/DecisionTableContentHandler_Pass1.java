package com.tibco.cep.studio.decision.table.constraintpane;

import java.util.HashMap;
import java.util.Stack;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

/**
 * User: ssubrama
 * Creation Date: Aug 2, 2008
 * Creation Time: 8:25:16 AM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
class DecisionTableContentHandler_Pass1 implements XmlContentHandler {

    private HashMap<String, Column> colMap = new HashMap<String, Column>();
    private boolean inColumnsElement = false;
    Stack<Column> context = new Stack<Column>();

    public HashMap<String, Column> getColumnMap() {
        return colMap;
    }

/*
    <columns>
      <column id="1" name="accountdecisionconcept.AccountClassification" propertyPath="/Concepts/AccountDecisionConcept/AccountClassification" columnType="CONDITION"/>
     */
    
    public void attribute(ExpandedName name, String value, SmAttribute declaration) throws SAXException {
        if (context.size() <= 0 || !inColumnsElement) return;

        Column col = context.peek();
        String localname = name.getLocalName();
        if ("id".equalsIgnoreCase(localname)) {
            if(colMap.get(value) == null) {
                colMap.put(value, col);
            }
        }
        else if ("name".equalsIgnoreCase(localname)) {
            col.name = value;
        }
        else if ("propertyPath".equalsIgnoreCase(localname)) {
            col.propertyPath = value;
        }
        else if ("columnType".equalsIgnoreCase(localname)) {
            col.columnType = Column.ColumnType.get(value);
        }

    }

    public void attribute(ExpandedName name, XmlTypedValue value, SmAttribute declaration) throws SAXException {
       attribute(name, value.getAtom(0).castAsString(), declaration);
    }

    public void comment(String value) throws SAXException {

    }

    public void endDocument() throws SAXException {

    }

    public void endElement(ExpandedName name, SmElement element, SmType override) throws SAXException {
        if(inColumnsElement) {
            String localname = name.getLocalName();
            if ("column".equalsIgnoreCase(localname)) {
//                Column col = context.pop();
            } else if ("columns".equalsIgnoreCase(localname)) {
                inColumnsElement = false;
            }
        }
    }

    public void ignorableWhitespace(String value, boolean reserved) throws SAXException {

    }

    public void prefixMapping(String prefix, String uri) throws SAXException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void processingInstruction(String target, String data) throws SAXException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDocumentLocator(Locator locator) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void skippedEntity(String name) throws SAXException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startDocument() throws SAXException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startElement(ExpandedName name, SmElement element, SmType override) throws SAXException {
        String localname = name.getLocalName();
        if(!inColumnsElement) {
            if("columns".equalsIgnoreCase(localname)) {
                inColumnsElement = true;
            }
        } else {
            if ("column".equalsIgnoreCase(localname)) {
                context.push(new Column());
            }   
        }
    }

    public void text(String value, boolean reserved) throws SAXException {
        
    }

    public void text(XmlTypedValue value, boolean reserved) throws SAXException {
        
    }
}