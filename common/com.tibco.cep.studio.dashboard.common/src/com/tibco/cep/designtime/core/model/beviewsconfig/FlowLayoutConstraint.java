/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Layout Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the constraints for flow layout.
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentRowSpan <em>Component Row Span</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentColSpan <em>Component Col Span</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFlowLayoutConstraint()
 * @model extendedMetaData="name='FlowLayoutConstraint' kind='elementOnly'"
 * @generated
 */
public interface FlowLayoutConstraint extends LayoutConstraint {
	/**
	 * Returns the value of the '<em><b>Component Row Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Row Span</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Row Span</em>' attribute.
	 * @see #isSetComponentRowSpan()
	 * @see #unsetComponentRowSpan()
	 * @see #setComponentRowSpan(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFlowLayoutConstraint_ComponentRowSpan()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='componentRowSpan'"
	 * @generated
	 */
	int getComponentRowSpan();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentRowSpan <em>Component Row Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Row Span</em>' attribute.
	 * @see #isSetComponentRowSpan()
	 * @see #unsetComponentRowSpan()
	 * @see #getComponentRowSpan()
	 * @generated
	 */
	void setComponentRowSpan(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentRowSpan <em>Component Row Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetComponentRowSpan()
	 * @see #getComponentRowSpan()
	 * @see #setComponentRowSpan(int)
	 * @generated
	 */
	void unsetComponentRowSpan();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentRowSpan <em>Component Row Span</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Component Row Span</em>' attribute is set.
	 * @see #unsetComponentRowSpan()
	 * @see #getComponentRowSpan()
	 * @see #setComponentRowSpan(int)
	 * @generated
	 */
	boolean isSetComponentRowSpan();

	/**
	 * Returns the value of the '<em><b>Component Col Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Col Span</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Col Span</em>' attribute.
	 * @see #isSetComponentColSpan()
	 * @see #unsetComponentColSpan()
	 * @see #setComponentColSpan(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFlowLayoutConstraint_ComponentColSpan()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='componentColSpan'"
	 * @generated
	 */
	int getComponentColSpan();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentColSpan <em>Component Col Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Col Span</em>' attribute.
	 * @see #isSetComponentColSpan()
	 * @see #unsetComponentColSpan()
	 * @see #getComponentColSpan()
	 * @generated
	 */
	void setComponentColSpan(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentColSpan <em>Component Col Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetComponentColSpan()
	 * @see #getComponentColSpan()
	 * @see #setComponentColSpan(int)
	 * @generated
	 */
	void unsetComponentColSpan();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentColSpan <em>Component Col Span</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Component Col Span</em>' attribute is set.
	 * @see #unsetComponentColSpan()
	 * @see #getComponentColSpan()
	 * @see #setComponentColSpan(int)
	 * @generated
	 */
	boolean isSetComponentColSpan();

} // FlowLayoutConstraint
