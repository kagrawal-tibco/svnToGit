package com.tibco.cep.bpmn.ui;

import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * 
 * @author majha
 *
 */
public class XPathDateTimeExpressionValidator  extends XPathExpressionValidator{

	public XPathDateTimeExpressionValidator (){
		super("Date/DateTime");
	}

	@Override
	public boolean isValid(SmSequenceType xtype) {
		SmType primitiveSchemaType = SmSequenceTypeSupport.getPrimitiveSchemaType(xtype);
		if(primitiveSchemaType != null){
			return primitiveSchemaType== XSDL.DATE || primitiveSchemaType== XSDL.DATETIME ;
		}
		
		SmType schemaType = SmSequenceTypeSupport.getSchemaType(xtype);
		if(schemaType != null)
			return schemaType== XSDL.DATETIME || schemaType== XSDL.DATE;
		
		return false;
	}
	
}
