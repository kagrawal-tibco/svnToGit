/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.BaseInstance;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.InstanceElement#getInstances <em>Instances</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.InstanceElement#getEntityPath <em>Entity Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getInstanceElement()
 * @model
 * @generated
 */
public interface InstanceElement<T extends BaseInstance> extends TypeElement {
	/**
	 * Returns the value of the '<em><b>Instances</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getInstanceElement_Instances()
	 * @model
	 * @generated
	 */
	EList<T> getInstances();

	/**
	 * Returns the value of the '<em><b>Entity Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Path</em>' attribute.
	 * @see #setEntityPath(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getInstanceElement_EntityPath()
	 * @model
	 * @generated
	 */
	String getEntityPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.InstanceElement#getEntityPath <em>Entity Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Path</em>' attribute.
	 * @see #getEntityPath()
	 * @generated
	 */
	void setEntityPath(String value);

} // InstanceElement
