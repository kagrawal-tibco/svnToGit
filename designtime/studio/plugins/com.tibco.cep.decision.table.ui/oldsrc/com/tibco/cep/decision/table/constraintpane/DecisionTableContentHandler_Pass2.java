package com.tibco.cep.decision.table.constraintpane;

import java.util.Map;
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
@SuppressWarnings({"rawtypes" , "unchecked", "unused"})
class DecisionTableContentHandler_Pass2 implements XmlContentHandler {

    private DecisionTable dt = new DecisionTable();
    private Map<String, Column> colMap;

    private Cell.CellInfo ci = null;
    private RuleTupleInfo ri = null;
    Stack context = new Stack();

    public DecisionTableContentHandler_Pass2(Map<String, Column> colMap) {
        this.colMap = colMap;
    }
    
    
    public DecisionTable getDecisionTable() {
        return dt;
    }


    public void attribute(ExpandedName name, String value, SmAttribute declaration) throws SAXException {
        if (context.size() <= 0) return;

        DecisionTableMutableAttributes dtma = (DecisionTableMutableAttributes) context.peek();
        String localname = name.getLocalName();
        if ("id".equalsIgnoreCase(localname)) {
            dtma.setId(value);
        }
        else if ("alias".equalsIgnoreCase(localname)) {
            dtma.setAlias(value);
        }
        else if ("path".equalsIgnoreCase(localname)) {
            dtma.setPath(value);
        }
        else if ("columnId".equalsIgnoreCase(localname)) {
            Column col = colMap.get(value);
            assert(col != null);
            if(col != null) {
                dtma.setAlias(col.name);
                dtma.setPath(col.propertyPath);
            }
        }
        
        /*
        columnId="5">
         */
        

    }

    public void attribute(ExpandedName name, XmlTypedValue value, SmAttribute declaration) throws SAXException {
       attribute(name, value.getAtom(0).castAsString(), declaration);
    }

    public void comment(String value) throws SAXException {

    }

    public void endDocument() throws SAXException {

    }

    public void endElement(ExpandedName name, SmElement element, SmType override) throws SAXException {
        String localname = name.getLocalName();
        if ("rule".equalsIgnoreCase(localname)) {
            RuleTupleInfo ri = (RuleTupleInfo) context.pop();
            dt.addRuleTupleInfo(ri);
        }
        else if ("condition".equalsIgnoreCase(localname)) {

            Cell c = (Cell) context.pop();
            dt.addCell(c);
        }
        else if ("expression".equalsIgnoreCase(localname)) {
            context.pop();
        }
        else if ("body".equalsIgnoreCase(localname)) {
            context.pop();
        }
        else if ("action".equalsIgnoreCase(localname)) {
            context.pop();
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
        if ("rule".equalsIgnoreCase(localname)) {
            context.push(new RuleTupleInfo(dt));
        }
        else if ("condition".equalsIgnoreCase(localname)) {

            Cell c = new Cell(Cell.CONDITION_CELL_TYPE);
            c.ri = (RuleTupleInfo) context.peek();
            context.push(c);
        }
        else if ("expression".equalsIgnoreCase(localname)) {
            DecisionTableMutableAttributes dtma = (DecisionTableMutableAttributes) context.peek();
            context.push(dtma);
        }
        else if ("body".equalsIgnoreCase(localname)) {
            DecisionTableMutableAttributes dtma = (DecisionTableMutableAttributes) context.peek();
            context.push(dtma);
        }
        else if ("action".equalsIgnoreCase(localname)) {
            Cell c = new Cell(Cell.ACTION_CELL_TYPE);
            c.ri = (RuleTupleInfo) context.peek();
            c.ri.addAction(c);
            context.push(c);
        }
    }

    public void text(String value, boolean reserved) throws SAXException {
        DecisionTableMutableAttributes dtma = (DecisionTableMutableAttributes) context.peek();
        dtma.setBody(value);
    }

    public void text(XmlTypedValue value, boolean reserved) throws SAXException {
        //skip \n
        if (value.getAtom(0).castAsString().startsWith("\n")) return;
        if (context.size() >0) {
        DecisionTableMutableAttributes dtma = (DecisionTableMutableAttributes) context.peek();
        dtma.setBody(value.getAtom(0).castAsString());
        }
    }
}