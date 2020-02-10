/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.MetaData#getProp <em>Prop</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getMetaData()
 * @model
 * @generated
 */
public interface MetaData extends EObject {
	/**
	 * Returns the value of the '<em><b>Prop</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prop</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prop</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getMetaData_Prop()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProp();

	/**
	 * Search metadata for property with given name
	 * @param name
	 * @return
	 * @generated NOT
	 */
	Property search(String name);
	
	boolean remove(Property property);
	
	/**
	 * Search all instances of {@code Property} matching the
	 * given name.
	 * @param name
	 * @return a list of all <tt>Property</tt> instances matching the given name
	 * @see {@link Property}
	 */
	List<Property> searchAll(String name);

} // MetaData
