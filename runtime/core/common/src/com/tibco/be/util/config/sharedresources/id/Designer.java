/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Designer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Designer#getLockedProperties <em>Locked Properties</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Designer#getFixedChildren <em>Fixed Children</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Designer#getResourceDescriptions <em>Resource Descriptions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getDesigner()
 * @model extendedMetaData="name='designer-type' kind='elementOnly'"
 * @generated
 */
public interface Designer extends EObject {
	/**
	 * Returns the value of the '<em><b>Locked Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Locked Properties</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Locked Properties</em>' attribute.
	 * @see #setLockedProperties(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getDesigner_LockedProperties()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='lockedProperties'"
	 * @generated
	 */
    String getLockedProperties();

    /**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Designer#getLockedProperties <em>Locked Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locked Properties</em>' attribute.
	 * @see #getLockedProperties()
	 * @generated
	 */
    void setLockedProperties(String value);

    /**
	 * Returns the value of the '<em><b>Fixed Children</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fixed Children</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed Children</em>' attribute.
	 * @see #setFixedChildren(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getDesigner_FixedChildren()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='fixedChildren'"
	 * @generated
	 */
    String getFixedChildren();

    /**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Designer#getFixedChildren <em>Fixed Children</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed Children</em>' attribute.
	 * @see #getFixedChildren()
	 * @generated
	 */
    void setFixedChildren(String value);

    /**
	 * Returns the value of the '<em><b>Resource Descriptions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Descriptions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Descriptions</em>' containment reference.
	 * @see #setResourceDescriptions(ResourceDescriptions)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getDesigner_ResourceDescriptions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='resourceDescriptions'"
	 * @generated
	 */
	ResourceDescriptions getResourceDescriptions();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Designer#getResourceDescriptions <em>Resource Descriptions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Descriptions</em>' containment reference.
	 * @see #getResourceDescriptions()
	 * @generated
	 */
	void setResourceDescriptions(ResourceDescriptions value);

} // Designer
