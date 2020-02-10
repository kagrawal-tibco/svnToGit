/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Single Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.SingleFilter#getLinks <em>Links</em>}</li>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.SingleFilter#getOperator <em>Operator</em>}</li>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.SingleFilter#getFilterValue <em>Filter Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface SingleFilter extends Filter {
	
	public final int LINKS_FEATURE_ID = 0;
	public final int OPERATOR_FEATURE_ID = 1;
	public final int FILTER_VALUE_FEATURE_ID = 2;

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.webstudio.model.rule.instance.RelatedLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @generated
	 */
	List<RelatedLink> getLinks();

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see #setOperator(String)
	 * @generated
	 */
	String getOperator();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.SingleFilter#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(String value);

	/**
	 * Returns the value of the '<em><b>Filter Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filter Value</em>' containment reference.
	 * @see #setFilterValue(FilterValue)
	 * @generated
	 */
	FilterValue getFilterValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.SingleFilter#getFilterValue <em>Filter Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter Value</em>' containment reference.
	 * @see #getFilterValue()
	 * @generated
	 */
	void setFilterValue(FilterValue value);

	void addRelatedLink(RelatedLink link);

	void removeRelatedLink(RelatedLink link);
} // SingleFilter
