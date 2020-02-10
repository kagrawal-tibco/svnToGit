package com.tibco.be.functions.object;
import java.util.Iterator;

import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;

/**
 * 
 * @author majha
 *
 */

public class XiNodeParser {

	private static final XiNodeParser INSTANCE = new XiNodeParser();
	
	private XiNodeParser(){
		
	}
	
	public static XiNodeParser getInstance() {
		return INSTANCE;
	}
	
	public void parse(XiNode node, XmlContentHandler handler) throws SAXException, XmlAtomicValueCastException{
		ExpandedName expandedName = node.getName();
		if (expandedName != null) {
			handler.startElement(expandedName, null, null);
        }

        XmlTypedValue typedValue = node.getTypedValue();

        if (typedValue != null) {
            boolean isEmpty = typedValue.isEmpty();
            if (!isEmpty) {
                handler.text(typedValue, true);
            }
        }

		
		String stringValue = node.getStringValue();
		if (stringValue != null) {
			handler.text(stringValue, true);
        }

        Iterator attributes = node.getAttributes();
		while (attributes.hasNext()) {
			XiNode type = (XiNode) attributes.next();
			XmlTypedValue attrTypedValue = type.getTypedValue();
			ExpandedName attrExpandedName = type.getName();
			if (attrExpandedName != null && attrTypedValue != null) {
				handler.attribute(attrExpandedName, attrTypedValue, null);
            }
        }
		
		Iterator childrens = node.getChildren();
		while (childrens.hasNext()) {
			XiNode childNode = (XiNode) childrens.next();
			parse(childNode, handler);
		}
		
		if (expandedName != null) {
			handler.endElement(expandedName, null, null);
        }
    }
}