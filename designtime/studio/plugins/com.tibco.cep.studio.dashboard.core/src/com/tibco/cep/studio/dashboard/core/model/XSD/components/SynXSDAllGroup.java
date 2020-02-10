package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAllGroup;

/**
 *
 */
public class SynXSDAllGroup extends SynXSDModelGroup implements ISynXSDAllGroup {

    public Object cloneThis() throws Exception {
    	SynXSDAllGroup clone = new SynXSDAllGroup();
    	super.cloneThis(clone);
    	return clone;
    }

}
