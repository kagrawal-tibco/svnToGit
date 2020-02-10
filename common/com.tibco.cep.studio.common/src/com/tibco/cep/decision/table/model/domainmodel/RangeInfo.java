/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isLowerBoundIncluded <em>Lower Bound Included</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isUpperBoundIncluded <em>Upper Bound Included</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getRangeInfo()
 * @model
 * @generated
 */
public interface RangeInfo extends EntryValue {
	/**
	 * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound</em>' attribute.
	 * @see #setLowerBound(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getRangeInfo_LowerBound()
	 * @model
	 * @generated
	 */
	String getLowerBound();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' attribute.
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(String value);

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' attribute.
	 * @see #setUpperBound(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getRangeInfo_UpperBound()
	 * @model
	 * @generated
	 */
	String getUpperBound();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getUpperBound <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' attribute.
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(String value);

	/**
	 * Returns the value of the '<em><b>Lower Bound Included</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Bound Included</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound Included</em>' attribute.
	 * @see #setLowerBoundIncluded(boolean)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getRangeInfo_LowerBoundIncluded()
	 * @model
	 * @generated
	 */
	boolean isLowerBoundIncluded();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isLowerBoundIncluded <em>Lower Bound Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound Included</em>' attribute.
	 * @see #isLowerBoundIncluded()
	 * @generated
	 */
	void setLowerBoundIncluded(boolean value);

	/**
	 * Returns the value of the '<em><b>Upper Bound Included</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound Included</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound Included</em>' attribute.
	 * @see #setUpperBoundIncluded(boolean)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getRangeInfo_UpperBoundIncluded()
	 * @model
	 * @generated
	 */
	boolean isUpperBoundIncluded();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isUpperBoundIncluded <em>Upper Bound Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound Included</em>' attribute.
	 * @see #isUpperBoundIncluded()
	 * @generated
	 */
	void setUpperBoundIncluded(boolean value);

} // RangeInfo
