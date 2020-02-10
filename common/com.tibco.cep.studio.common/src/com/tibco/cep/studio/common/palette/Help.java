/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Help</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.Help#getTab <em>Tab</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.Help#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.palette.PalettePackage#getHelp()
 * @model extendedMetaData="name='help' kind='elementOnly'"
 * @generated
 */
public interface Help extends EObject {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute.
	 * @see #isSetContent()
	 * @see #unsetContent()
	 * @see #setContent(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getHelp_Content()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='content'"
	 * @generated
	 */
	String getContent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.Help#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' attribute.
	 * @see #isSetContent()
	 * @see #unsetContent()
	 * @see #getContent()
	 * @generated
	 */
	void setContent(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.Help#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetContent()
	 * @see #getContent()
	 * @see #setContent(String)
	 * @generated
	 */
	void unsetContent();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.Help#getContent <em>Content</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Content</em>' attribute is set.
	 * @see #unsetContent()
	 * @see #getContent()
	 * @see #setContent(String)
	 * @generated
	 */
	boolean isSetContent();

	/**
	 * Returns the value of the '<em><b>Tab</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.common.palette.Tab}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tab</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tab</em>' attribute.
	 * @see com.tibco.cep.studio.common.palette.Tab
	 * @see #isSetTab()
	 * @see #unsetTab()
	 * @see #setTab(Tab)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getHelp_Tab()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='tab' namespace='##targetNamespace'"
	 * @generated
	 */
	Tab getTab();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.Help#getTab <em>Tab</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tab</em>' attribute.
	 * @see com.tibco.cep.studio.common.palette.Tab
	 * @see #isSetTab()
	 * @see #unsetTab()
	 * @see #getTab()
	 * @generated
	 */
	void setTab(Tab value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.Help#getTab <em>Tab</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTab()
	 * @see #getTab()
	 * @see #setTab(Tab)
	 * @generated
	 */
	void unsetTab();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.Help#getTab <em>Tab</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Tab</em>' attribute is set.
	 * @see #unsetTab()
	 * @see #getTab()
	 * @see #setTab(Tab)
	 * @generated
	 */
	boolean isSetTab();

} // Help
