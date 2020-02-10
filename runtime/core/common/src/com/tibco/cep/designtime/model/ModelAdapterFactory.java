package com.tibco.cep.designtime.model;

import org.eclipse.emf.ecore.EObject;

public interface ModelAdapterFactory  {

	/**
	 * @param <OE> Model Entity
	 * @param <NE> EMF Object
	 * @param entity
	 * @param emfOntology
	 * @param params
	 *            -> Optional parameters to aid the construction of object. <b>
	 *            Example : Instances of StateMachines. </b>
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public <OE extends Entity, NE extends EObject> OE createAdapter(NE entity,
			Ontology emfOntology, Object... params);

}
