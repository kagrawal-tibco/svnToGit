package com.tibco.cep.studio.tester.ui.editor.data;

import com.tibco.cep.studio.tester.core.model.ConceptType;

/**
 * 
 * @author sasahoo
 *
 */
public class ConceptTypeElement extends EntityTypeElement {

	private ConceptType causalObject;
	

	/**
	 * @param causalObject
	 * @param concept
	 */
	public ConceptTypeElement(ConceptType causalObject) {
		super(causalObject);
		this.causalObject = causalObject;
	}

	/**
	 * @return
	 */
	public ConceptType getConceptType() {
		return causalObject;
	}
}
