/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concept Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ConceptType#isIsScorecard <em>Is Scorecard</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getConceptType()
 * @model extendedMetaData="name='ConceptType' kind='elementOnly'"
 * @generated
 */
public interface ConceptType extends EntityType {
	/**
	 * Returns the value of the '<em><b>Is Scorecard</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Scorecard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Scorecard</em>' attribute.
	 * @see #isSetIsScorecard()
	 * @see #unsetIsScorecard()
	 * @see #setIsScorecard(boolean)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getConceptType_IsScorecard()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='isScorecard'"
	 * @generated
	 */
	boolean isIsScorecard();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ConceptType#isIsScorecard <em>Is Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Scorecard</em>' attribute.
	 * @see #isSetIsScorecard()
	 * @see #unsetIsScorecard()
	 * @see #isIsScorecard()
	 * @generated
	 */
	void setIsScorecard(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ConceptType#isIsScorecard <em>Is Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIsScorecard()
	 * @see #isIsScorecard()
	 * @see #setIsScorecard(boolean)
	 * @generated
	 */
	void unsetIsScorecard();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.ConceptType#isIsScorecard <em>Is Scorecard</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Is Scorecard</em>' attribute is set.
	 * @see #unsetIsScorecard()
	 * @see #isIsScorecard()
	 * @see #setIsScorecard(boolean)
	 * @generated
	 */
	boolean isSetIsScorecard();

} // ConceptType
