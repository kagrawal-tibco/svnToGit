/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Be</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Be#getTra <em>Tra</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Be#getHome <em>Home</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Be#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getBe()
 * @model extendedMetaData="name='be_._type' kind='elementOnly'"
 * @generated
 */
public interface Be extends EObject {
	/**
	 * Returns the value of the '<em><b>Tra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tra</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tra</em>' attribute.
	 * @see #setTra(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getBe_Tra()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='tra' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTra();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Be#getTra <em>Tra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tra</em>' attribute.
	 * @see #getTra()
	 * @generated
	 */
	void setTra(String value);

	/**
	 * Returns the value of the '<em><b>Home</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Home</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Home</em>' attribute.
	 * @see #setHome(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getBe_Home()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='home' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHome();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Be#getHome <em>Home</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Home</em>' attribute.
	 * @see #getHome()
	 * @generated
	 */
	void setHome(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"4.0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #setVersion(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getBe_Version()
	 * @model default="4.0.0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Be#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.core.model.topology.Be#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVersion()
	 * @see #getVersion()
	 * @see #setVersion(String)
	 * @generated
	 */
	void unsetVersion();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.core.model.topology.Be#getVersion <em>Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Version</em>' attribute is set.
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @see #setVersion(String)
	 * @generated
	 */
	boolean isSetVersion();

} // Be
