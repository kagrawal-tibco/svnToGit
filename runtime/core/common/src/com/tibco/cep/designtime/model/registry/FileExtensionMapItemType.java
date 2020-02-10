/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Extension Map Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getExtension <em>Extension</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getElementType <em>Element Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getFileExtensionMapItemType()
 * @model extendedMetaData="name='FileExtensionMapItemType' kind='elementOnly'"
 * @generated
 */
public interface FileExtensionMapItemType extends EObject {
	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.model.registry.FileExtensionTypes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @see #isSetExtension()
	 * @see #unsetExtension()
	 * @see #setExtension(FileExtensionTypes)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getFileExtensionMapItemType_Extension()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='key'"
	 * @generated
	 */
	FileExtensionTypes getExtension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getExtension <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension</em>' attribute.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @see #isSetExtension()
	 * @see #unsetExtension()
	 * @see #getExtension()
	 * @generated
	 */
	void setExtension(FileExtensionTypes value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getExtension <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetExtension()
	 * @see #getExtension()
	 * @see #setExtension(FileExtensionTypes)
	 * @generated
	 */
	void unsetExtension();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getExtension <em>Extension</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Extension</em>' attribute is set.
	 * @see #unsetExtension()
	 * @see #getExtension()
	 * @see #setExtension(FileExtensionTypes)
	 * @generated
	 */
	boolean isSetExtension();

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type</em>' containment reference.
	 * @see #setElementType(SupportedElementTypes)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getFileExtensionMapItemType_ElementType()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='value'"
	 * @generated
	 */
	SupportedElementTypes getElementType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getElementType <em>Element Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type</em>' containment reference.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(SupportedElementTypes value);

} // FileExtensionMapItemType
