package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.ArrayList;

import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDRestrictionSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.facets.SynXSDFacet;

/**
 * Enumerated type has an Enumeration List with which input can be compared to for validation
 *
 */
public class SynEnumerationType extends SynXSDRestrictionSimpleTypeDefinition {

	public SynEnumerationType(String name, SynXSDSimpleTypeDefinition base) {
		super();
		/*
		 * Inititialize the restriction; enumerations only has the enumeration facet
		 */
		setName(name);
		setBase(base);
		addFacet(new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
	}

	public Object cloneThis() throws Exception {
		SynEnumerationType clone = new SynEnumerationType(this.getName(), (SynXSDSimpleTypeDefinition) this.getBase());
		super.cloneThis(clone);
		return clone;
	}

}