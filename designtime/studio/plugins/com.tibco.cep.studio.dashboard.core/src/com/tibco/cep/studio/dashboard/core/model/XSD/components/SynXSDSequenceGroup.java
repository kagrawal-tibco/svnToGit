package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSequenceGroup;

/**
 *
 */
public class SynXSDSequenceGroup extends SynXSDModelGroup implements ISynXSDSequenceGroup {

	public Object cloneThis() throws Exception {
		SynXSDSequenceGroup clone = new SynXSDSequenceGroup();
    	super.cloneThis(clone);
    	return clone;
    }

}
