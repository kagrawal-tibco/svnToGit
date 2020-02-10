package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDChoiceGroup;

/**
 *
 */
public class SynXSDChoiceGroup extends SynXSDModelGroup implements ISynXSDChoiceGroup {

    public Object cloneThis() throws Exception {
    	SynXSDChoiceGroup clone = new SynXSDChoiceGroup();
    	super.cloneThis(clone);
    	return clone;
    }

}