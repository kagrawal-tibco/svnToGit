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
public class XPathBooleanExpressionValidator  extends XPathExpressionValidator{

	public XPathBooleanExpressionValidator (){
		super("boolean");
	}

	@Override
	public boolean isValid(SmSequenceType xtype) {
		boolean primitiveBoolean = SmSequenceTypeSupport.isPrimitiveBoolean(xtype);
		if(primitiveBoolean)
			return primitiveBoolean;
		
		SmType schemaType = SmSequenceTypeSupport.getSchemaType(xtype);
		
		if(schemaType != null){
			return schemaType== XSDL.BOOLEAN;
		}else{
			SmType st = getSchemaTypeForAttribute(xtype);
			if(st != null){
				return st == XSDL.BOOLEAN;
			}
		}
		return false;
	}
	
}
