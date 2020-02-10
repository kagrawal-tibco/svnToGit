/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metadatable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Metadatable#getMd <em>Md</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getMetadatable()
 * @model
 * @generated
 */
public interface Metadatable extends EObject {
	/**
	 * Returns the value of the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Md</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Md</em>' containment reference.
	 * @see #setMd(MetaData)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getMetadatable_Md()
	 * @model containment="true"
	 * @generated
	 */
	MetaData getMd();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Metadatable#getMd <em>Md</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Md</em>' containment reference.
	 * @see #getMd()
	 * @generated
	 */
	void setMd(MetaData value);

} // Metadatable
