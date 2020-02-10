package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * @deprecated
 */
public class SynAlertMessageType extends SynStringType {

    public SynAlertMessageType() {
        super();
        /*
         * Alert message has no practical length limit
         */
        setMaxLength(BasicValidations.MAX_STRNG_LENGTH);
    }

    public Object cloneThis() throws Exception {
    	SynAlertMessageType clone = new SynAlertMessageType();
    	super.cloneThis(clone);
    	return clone;
    }

}