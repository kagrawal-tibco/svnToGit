/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization#isShowHeader <em>Show Header</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextVisualization()
 * @model extendedMetaData="name='TextVisualization' kind='elementOnly'"
 * @generated
 */
public interface TextVisualization extends TwoDimVisualization {
	/**
	 * Returns the value of the '<em><b>Show Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Header</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Header</em>' attribute.
	 * @see #isSetShowHeader()
	 * @see #unsetShowHeader()
	 * @see #setShowHeader(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextVisualization_ShowHeader()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='showHeader'"
	 * @generated
	 */
	boolean isShowHeader();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization#isShowHeader <em>Show Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Header</em>' attribute.
	 * @see #isSetShowHeader()
	 * @see #unsetShowHeader()
	 * @see #isShowHeader()
	 * @generated
	 */
	void setShowHeader(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization#isShowHeader <em>Show Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowHeader()
	 * @see #isShowHeader()
	 * @see #setShowHeader(boolean)
	 * @generated
	 */
	void unsetShowHeader();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization#isShowHeader <em>Show Header</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Header</em>' attribute is set.
	 * @see #unsetShowHeader()
	 * @see #isShowHeader()
	 * @see #setShowHeader(boolean)
	 * @generated
	 */
	boolean isSetShowHeader();

} // TextVisualization
