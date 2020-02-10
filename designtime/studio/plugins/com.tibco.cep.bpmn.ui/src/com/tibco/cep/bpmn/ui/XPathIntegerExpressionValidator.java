package com.tibco.cep.bpmn.ui;

import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * 
 * @author majha
 *
 */
public class XPathIntegerExpressionValidator  extends XPathExpressionValidator{

	public XPathIntegerExpressionValidator (){
		super("integer");
	}
	public boolean isloopCharacteristics = false ;
	public String type = null ;

	@Override
	public boolean isValid(SmSequenceType xtype) {
		if (isloopCharacteristics) {
			SmCardinality eltCardinality = SmSequenceTypeSupport
					.getTermOccurrence(xtype);
			eltCardinality.getMaxOccurs();
			if (eltCardinality.getMaxOccurs() > 1) {
				return false;
			}	
		}
		SmType primitiveSchemaType = SmSequenceTypeSupport.getPrimitiveSchemaType(xtype);
		if (primitiveSchemaType != null) {
			if (primitiveSchemaType == XSDL.DOUBLE
					|| primitiveSchemaType == XSDL.INT
					|| primitiveSchemaType == XSDL.INTEGER) {
				type = primitiveSchemaType.getName();
				return true;
			} else {
				return false;
			}
		}
		
		SmType schemaType = SmSequenceTypeSupport.getSchemaType(xtype);
		if(schemaType != null) {
			if ( schemaType== XSDL.INT || schemaType== XSDL.INTEGER ||  schemaType== XSDL.LONG ||  schemaType== XSDL.DOUBLE) {
				type = schemaType.getName() ;
				return true ;
			} else {
				return false ;
			}
		}
		else{
			SmType st = getSchemaTypeForAttribute(xtype);
			if(st != null){
				if ( st== XSDL.INT || st== XSDL.INTEGER || st==XSDL.DOUBLE || st==XSDL.LONG ) {
					type = st.getName();
					return true ;
				} else {
					return false ;
				}
			}
		}
		
		return false;
	}
	
}
