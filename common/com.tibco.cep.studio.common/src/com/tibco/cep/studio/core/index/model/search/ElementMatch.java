/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Match</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getMatchedElement <em>Matched Element</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getElementMatch()
 * @model
 * @generated
 */
public interface ElementMatch extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(EObject)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getElementMatch_Feature()
	 * @model
	 * @generated
	 */
	EObject getFeature();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(EObject value);

	/**
	 * Returns the value of the '<em><b>Matched Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Matched Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Matched Element</em>' reference.
	 * @see #setMatchedElement(EObject)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getElementMatch_MatchedElement()
	 * @model
	 * @generated
	 */
	EObject getMatchedElement();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getMatchedElement <em>Matched Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Matched Element</em>' reference.
	 * @see #getMatchedElement()
	 * @generated
	 */
	void setMatchedElement(EObject value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getElementMatch_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // ElementMatch
