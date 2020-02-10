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
public class XPathStringExpressionValidator  extends XPathExpressionValidator{

	public XPathStringExpressionValidator (){
		super("string");
	}

	@Override
	public boolean isValid(SmSequenceType xtype) {
		SmType primitiveSchemaType = SmSequenceTypeSupport.getPrimitiveSchemaType(xtype);
		if(primitiveSchemaType != null){
			return primitiveSchemaType== XSDL.STRING ;
		}
		SmType schemaType = SmSequenceTypeSupport.getSchemaType(xtype);
		if(schemaType != null)
			return schemaType== XSDL.STRING ;
		else{
			SmType st = getSchemaTypeForAttribute(xtype);
			if(st != null){
				return st == XSDL.STRING;
			}
		}
		
		return false;
	}
	
	
}
