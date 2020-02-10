/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the layout for a panel.
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#isRepositioningAllowed <em>Repositioning Allowed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentWidth <em>Component Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentHeight <em>Component Height</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLayout()
 * @model abstract="true"
 *        extendedMetaData="name='Layout' kind='elementOnly'"
 * @generated
 */
public interface Layout extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Repositioning Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repositioning Allowed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repositioning Allowed</em>' attribute.
	 * @see #isSetRepositioningAllowed()
	 * @see #unsetRepositioningAllowed()
	 * @see #setRepositioningAllowed(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLayout_RepositioningAllowed()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='repositioningAllowed'"
	 * @generated
	 */
	boolean isRepositioningAllowed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#isRepositioningAllowed <em>Repositioning Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repositioning Allowed</em>' attribute.
	 * @see #isSetRepositioningAllowed()
	 * @see #unsetRepositioningAllowed()
	 * @see #isRepositioningAllowed()
	 * @generated
	 */
	void setRepositioningAllowed(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#isRepositioningAllowed <em>Repositioning Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRepositioningAllowed()
	 * @see #isRepositioningAllowed()
	 * @see #setRepositioningAllowed(boolean)
	 * @generated
	 */
	void unsetRepositioningAllowed();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#isRepositioningAllowed <em>Repositioning Allowed</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Repositioning Allowed</em>' attribute is set.
	 * @see #unsetRepositioningAllowed()
	 * @see #isRepositioningAllowed()
	 * @see #setRepositioningAllowed(boolean)
	 * @generated
	 */
	boolean isSetRepositioningAllowed();

	/**
	 * Returns the value of the '<em><b>Component Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Width</em>' attribute.
	 * @see #isSetComponentWidth()
	 * @see #unsetComponentWidth()
	 * @see #setComponentWidth(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLayout_ComponentWidth()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='componentWidth'"
	 * @generated
	 */
	int getComponentWidth();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentWidth <em>Component Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Width</em>' attribute.
	 * @see #isSetComponentWidth()
	 * @see #unsetComponentWidth()
	 * @see #getComponentWidth()
	 * @generated
	 */
	void setComponentWidth(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentWidth <em>Component Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetComponentWidth()
	 * @see #getComponentWidth()
	 * @see #setComponentWidth(int)
	 * @generated
	 */
	void unsetComponentWidth();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentWidth <em>Component Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Component Width</em>' attribute is set.
	 * @see #unsetComponentWidth()
	 * @see #getComponentWidth()
	 * @see #setComponentWidth(int)
	 * @generated
	 */
	boolean isSetComponentWidth();

	/**
	 * Returns the value of the '<em><b>Component Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Height</em>' attribute.
	 * @see #isSetComponentHeight()
	 * @see #unsetComponentHeight()
	 * @see #setComponentHeight(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLayout_ComponentHeight()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='componentHeight'"
	 * @generated
	 */
	int getComponentHeight();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentHeight <em>Component Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Height</em>' attribute.
	 * @see #isSetComponentHeight()
	 * @see #unsetComponentHeight()
	 * @see #getComponentHeight()
	 * @generated
	 */
	void setComponentHeight(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentHeight <em>Component Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetComponentHeight()
	 * @see #getComponentHeight()
	 * @see #setComponentHeight(int)
	 * @generated
	 */
	void unsetComponentHeight();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentHeight <em>Component Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Component Height</em>' attribute is set.
	 * @see #unsetComponentHeight()
	 * @see #getComponentHeight()
	 * @see #setComponentHeight(int)
	 * @generated
	 */
	boolean isSetComponentHeight();

} // Layout
