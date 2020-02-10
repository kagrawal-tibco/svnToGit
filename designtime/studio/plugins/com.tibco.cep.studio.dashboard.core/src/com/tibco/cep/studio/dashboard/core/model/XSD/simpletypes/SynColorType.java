package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * Color property must be valdated against the color format
 *
 */
public class SynColorType extends SynStringType {

    public SynColorType() {
        super();
        /*
         * Color is in HEX string FFFFFF
         */
        setPattern(BasicValidations.REG_EX_PATTERN_COLOR);
        setLength(6);
    }

    public boolean isValid(Object value) {
    	String s = (String) value;
    	 while (s.length() < 6) {
            s = "0" + s;
        }
    	return super.isValid(s);
    }

    public Object cloneThis() throws Exception {
    	SynColorType clone = new SynColorType();
    	super.cloneThis(clone);
    	return clone;
    }

}