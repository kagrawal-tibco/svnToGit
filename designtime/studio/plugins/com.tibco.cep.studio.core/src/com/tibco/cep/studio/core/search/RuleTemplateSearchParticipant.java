package com.tibco.cep.studio.core.search;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;

public class RuleTemplateSearchParticipant extends RuleSearchParticipant {

	public RuleTemplateSearchParticipant() {
		super();
	}

	@Override
	protected ELEMENT_TYPES[] getElementTypes() {
		return new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_TEMPLATE };
	}

}
