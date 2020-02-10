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
 * A representation of the model object '<em><b>Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Represents the panel in a partition. Panel can contain
 * 				concrete subsclasses of Component. If the panel contains
 * 				more then one component (as in metric viewer), then the
 * 				panel should have a layout defined [can be defaulted to
 * 				FlowLayout for 2.3]
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getSpan <em>Span</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getScrollBar <em>Scroll Bar</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMaximizable <em>Maximizable</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMinimizable <em>Minimizable</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getComponent <em>Component</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getLayout <em>Layout</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel()
 * @model extendedMetaData="name='Panel' kind='elementOnly'"
 * @generated
 */
public interface Panel extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Span</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Span</em>' attribute.
	 * @see #setSpan(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_Span()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='span'"
	 * @generated
	 */
	String getSpan();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getSpan <em>Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Span</em>' attribute.
	 * @see #getSpan()
	 * @generated
	 */
	void setSpan(String value);

	/**
	 * Returns the value of the '<em><b>Scroll Bar</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scroll Bar</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scroll Bar</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum
	 * @see #isSetScrollBar()
	 * @see #unsetScrollBar()
	 * @see #setScrollBar(ScrollBarConfigEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_ScrollBar()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='scrollBar'"
	 * @generated
	 */
	ScrollBarConfigEnum getScrollBar();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getScrollBar <em>Scroll Bar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scroll Bar</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum
	 * @see #isSetScrollBar()
	 * @see #unsetScrollBar()
	 * @see #getScrollBar()
	 * @generated
	 */
	void setScrollBar(ScrollBarConfigEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getScrollBar <em>Scroll Bar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetScrollBar()
	 * @see #getScrollBar()
	 * @see #setScrollBar(ScrollBarConfigEnum)
	 * @generated
	 */
	void unsetScrollBar();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getScrollBar <em>Scroll Bar</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scroll Bar</em>' attribute is set.
	 * @see #unsetScrollBar()
	 * @see #getScrollBar()
	 * @see #setScrollBar(ScrollBarConfigEnum)
	 * @generated
	 */
	boolean isSetScrollBar();

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum
	 * @see #isSetState()
	 * @see #unsetState()
	 * @see #setState(PanelStateEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_State()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='state'"
	 * @generated
	 */
	PanelStateEnum getState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum
	 * @see #isSetState()
	 * @see #unsetState()
	 * @see #getState()
	 * @generated
	 */
	void setState(PanelStateEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetState()
	 * @see #getState()
	 * @see #setState(PanelStateEnum)
	 * @generated
	 */
	void unsetState();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getState <em>State</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>State</em>' attribute is set.
	 * @see #unsetState()
	 * @see #getState()
	 * @see #setState(PanelStateEnum)
	 * @generated
	 */
	boolean isSetState();

	/**
	 * Returns the value of the '<em><b>Maximizable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maximizable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maximizable</em>' attribute.
	 * @see #isSetMaximizable()
	 * @see #unsetMaximizable()
	 * @see #setMaximizable(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_Maximizable()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='maximizable'"
	 * @generated
	 */
	boolean isMaximizable();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMaximizable <em>Maximizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maximizable</em>' attribute.
	 * @see #isSetMaximizable()
	 * @see #unsetMaximizable()
	 * @see #isMaximizable()
	 * @generated
	 */
	void setMaximizable(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMaximizable <em>Maximizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaximizable()
	 * @see #isMaximizable()
	 * @see #setMaximizable(boolean)
	 * @generated
	 */
	void unsetMaximizable();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMaximizable <em>Maximizable</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Maximizable</em>' attribute is set.
	 * @see #unsetMaximizable()
	 * @see #isMaximizable()
	 * @see #setMaximizable(boolean)
	 * @generated
	 */
	boolean isSetMaximizable();

	/**
	 * Returns the value of the '<em><b>Minimizable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minimizable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minimizable</em>' attribute.
	 * @see #isSetMinimizable()
	 * @see #unsetMinimizable()
	 * @see #setMinimizable(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_Minimizable()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='minimizable'"
	 * @generated
	 */
	boolean isMinimizable();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMinimizable <em>Minimizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimizable</em>' attribute.
	 * @see #isSetMinimizable()
	 * @see #unsetMinimizable()
	 * @see #isMinimizable()
	 * @generated
	 */
	void setMinimizable(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMinimizable <em>Minimizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinimizable()
	 * @see #isMinimizable()
	 * @see #setMinimizable(boolean)
	 * @generated
	 */
	void unsetMinimizable();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMinimizable <em>Minimizable</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Minimizable</em>' attribute is set.
	 * @see #unsetMinimizable()
	 * @see #isMinimizable()
	 * @see #setMinimizable(boolean)
	 * @generated
	 */
	boolean isSetMinimizable();

	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_Component()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='component'"
	 * @generated
	 */
	EList<Component> getComponent();

	/**
	 * Returns the value of the '<em><b>Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layout</em>' containment reference.
	 * @see #setLayout(Layout)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanel_Layout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='layout'"
	 * @generated
	 */
	Layout getLayout();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getLayout <em>Layout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout</em>' containment reference.
	 * @see #getLayout()
	 * @generated
	 */
	void setLayout(Layout value);

} // Panel
