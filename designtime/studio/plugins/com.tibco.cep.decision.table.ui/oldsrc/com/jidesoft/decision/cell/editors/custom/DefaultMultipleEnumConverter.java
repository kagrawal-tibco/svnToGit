package com.jidesoft.decision.cell.editors.custom;

import static com.jidesoft.decision.cell.editors.utils.CellEditorUtils.isAsterixedExpression;
import static com.tibco.cep.decisionproject.util.DTDomainUtil.ASTERISK;
import static com.tibco.cep.decisionproject.util.DTDomainUtil.DESC;
import static com.tibco.cep.decisionproject.util.DTDomainUtil.EMPTY;
import static com.tibco.cep.decisionproject.util.DTDomainUtil.VALUE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.EnumConverter;
import com.jidesoft.converter.MultipleEnumConverter;
/**
 * 
 * @author sasahoo
 *
 */
public class DefaultMultipleEnumConverter extends MultipleEnumConverter{

	public DefaultMultipleEnumConverter(String separator,
			EnumConverter converter) {
		super(separator, converter);
	}

	 /* (non-Javadoc)
	 * @see com.jidesoft.converter.MultipleEnumConverter#toString(java.lang.Object, com.jidesoft.converter.ConverterContext)
	 */
	public String toString(Object object, ConverterContext context) {
	        if (object == null) {
	            return "";
	        }
	        if (object.getClass().isArray()) {
	            int length = Array.getLength(object);
	            List<Object> objList = new ArrayList<Object>();
	            for (int i = 0; i < length; i++) {
	                Object o = Array.get(object, i);
	                if (o.toString().equals(EMPTY) 
	                		|| o.toString().equals(VALUE) 
	                		|| o.toString().equals(DESC)){
	                	continue;
	                }
	                objList.add(o);
	            }
	            Object[] values = new Object[objList.size()];
	            objList.toArray(values);
	            String value = arrayToString(values, context);
	            if (isAsterixedExpression(value)) {
	    			//Only asterix should be set as expression.
	    			value = ASTERISK;

	    		}
	            return value;
	        }
	        return "";
	    }
}
