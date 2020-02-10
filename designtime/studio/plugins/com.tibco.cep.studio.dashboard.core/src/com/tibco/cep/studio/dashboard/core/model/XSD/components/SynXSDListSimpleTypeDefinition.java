package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDListSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;

/**
 */
public class SynXSDListSimpleTypeDefinition extends SynXSDSimpleTypeDefinition implements ISynXSDListSimpleTypeDefinition {

	private ISynXSDSimpleTypeDefinition itemType;

	public SynXSDListSimpleTypeDefinition() {
		super();
	}

	public ISynXSDSimpleTypeDefinition getItemType() {
		return itemType;
	}

	public void setItemType(ISynXSDSimpleTypeDefinition itemType) {
		this.itemType = itemType;
	}

	public boolean isValid() throws Exception {
		return true;
	}

	public Object cloneThis() throws Exception {
		SynXSDListSimpleTypeDefinition clone = new SynXSDListSimpleTypeDefinition();
		super.cloneThis(clone);
		if (this.itemType == null) {
			clone.itemType = null;
		} else {
			clone.itemType = (ISynXSDSimpleTypeDefinition) this.itemType.cloneThis();
		}
		return clone;
	}
}
