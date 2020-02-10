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
public class XPathLongExpressionValidator  extends XPathExpressionValidator{

	public XPathLongExpressionValidator (){
		super("long");
	}

	@Override
	public boolean isValid(SmSequenceType xtype) {
		SmType primitiveSchemaType = SmSequenceTypeSupport.getPrimitiveSchemaType(xtype);
		if(primitiveSchemaType != null){
			return primitiveSchemaType== XSDL.DOUBLE || primitiveSchemaType== XSDL.LONG || primitiveSchemaType== XSDL.INT || primitiveSchemaType== XSDL.INTEGER;
		}
		
		SmType schemaType = SmSequenceTypeSupport.getSchemaType(xtype);
		if(schemaType != null)
			return schemaType== XSDL.LONG || schemaType== XSDL.INT || schemaType== XSDL.INTEGER;
		else{
			SmType st = getSchemaTypeForAttribute(xtype);
			if(st != null){
				return st== XSDL.INT || st== XSDL.INTEGER || st==XSDL.LONG;
			}
		}
		
		return false;
	}
	
}
