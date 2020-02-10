/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;


import java.util.Iterator;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parent Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getParentResource()
 * @model abstract="true"
 * @generated
 */
public interface ParentResource extends AbstractResource {
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated NOT
	 */
	Iterator<? extends AbstractResource> getChildren();

	/**
	 * Return a <tt>AbstractResource</tt> if it is a child of
	 * this <tt>ParentResource</tt>
	 * @param The name of the resource to search
	 * @param The <code>ResourceType</code>
	 * @return child resource, or null if none.
	 * 
	 */
	AbstractResource findChild(String name,ResourceType resourceType);
	
	/**
	 * Return a <tt>AbstractResource</tt> if it is a child of
	 * this <tt>ParentResource</tt>
	 * @param The name of the resource to search
	 * @return child resource, or null if none.
	 * 
	 */
	AbstractResource findChild(String name);
	
	boolean addChild(AbstractResource abstractResource);
	
	/**
	 * Get the index of the {@link AbstractResource} child of this
	 * {@link ParentResource}, else -1
	 * @param abstractResource: The child element to obtain index
	 * @return position of this child
	 */
	int getChildIndex(AbstractResource abstractResource);

} // ParentResource
