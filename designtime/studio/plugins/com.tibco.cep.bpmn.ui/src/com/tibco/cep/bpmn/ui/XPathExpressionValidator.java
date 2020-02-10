package com.tibco.cep.bpmn.ui;

import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;

/**
 * 
 * @author majha
 *
 */
public class XPathExpressionValidator  implements IProcessXPathValidate{

	private String expectedType;

	public XPathExpressionValidator (String expectedType ){
		this.expectedType = expectedType;
	}
	
	public String getExpectedType(){
		return expectedType;
	}
	

	@Override
	public boolean isValid(SmSequenceType xtype) {
		// TODO Auto-generated method stub
		return true;
	}
	
    public SmType getSchemaTypeForAttribute(final SmSequenceType t)
    {
        final int tc = t.getTypeCode();
        switch (tc)
        {
        case SmSequenceType.TYPE_CODE_ATTRIBUTE:
            {
            	SmParticleTerm pterm = t.getParticleTerm();
            	if(pterm != null){
            		if(pterm instanceof SmAttribute){
            			SmAttribute attr = (SmAttribute) pterm;
            			return attr.getType();
            		}
            	}
            	return null;
            }
        case SmSequenceType.TYPE_CODE_REPEATS:
            {
                return getSchemaTypeForAttribute(t.getFirstChildComponent());
            }
        }
        return null;
    }
	
}
