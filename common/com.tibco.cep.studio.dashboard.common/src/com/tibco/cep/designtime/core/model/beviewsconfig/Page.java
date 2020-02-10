/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Represents the page in the repository
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayMode <em>Display Mode</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getPartition <em>Partition</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getVisualization <em>Visualization</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPage()
 * @model abstract="true"
 *        extendedMetaData="name='Page' kind='elementOnly'"
 * @generated
 */
public interface Page extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPage_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Display Mode</b></em>' attribute.
	 * The default value is <code>"inplace"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Mode</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum
	 * @see #isSetDisplayMode()
	 * @see #unsetDisplayMode()
	 * @see #setDisplayMode(DisplayModeEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPage_DisplayMode()
	 * @model default="inplace" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='displayMode'"
	 * @generated
	 */
	DisplayModeEnum getDisplayMode();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayMode <em>Display Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Mode</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum
	 * @see #isSetDisplayMode()
	 * @see #unsetDisplayMode()
	 * @see #getDisplayMode()
	 * @generated
	 */
	void setDisplayMode(DisplayModeEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayMode <em>Display Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDisplayMode()
	 * @see #getDisplayMode()
	 * @see #setDisplayMode(DisplayModeEnum)
	 * @generated
	 */
	void unsetDisplayMode();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayMode <em>Display Mode</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Display Mode</em>' attribute is set.
	 * @see #unsetDisplayMode()
	 * @see #getDisplayMode()
	 * @see #setDisplayMode(DisplayModeEnum)
	 * @generated
	 */
	boolean isSetDisplayMode();

	/**
	 * Returns the value of the '<em><b>Partition</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Partition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Partition</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPage_Partition()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='partition'"
	 * @generated
	 */
	EList<Partition> getPartition();

	/**
	 * Returns the value of the '<em><b>Visualization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visualization</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visualization</em>' containment reference.
	 * @see #setVisualization(PageVisualization)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPage_Visualization()
	 * @model containment="true"
	 * @generated
	 */
	PageVisualization getVisualization();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getVisualization <em>Visualization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visualization</em>' containment reference.
	 * @see #getVisualization()
	 * @generated
	 */
	void setVisualization(PageVisualization value);

} // Page
