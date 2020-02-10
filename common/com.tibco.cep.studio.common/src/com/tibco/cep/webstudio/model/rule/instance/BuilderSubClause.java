/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;

import java.io.Serializable;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Builder Sub Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause#getFilters <em>Filters</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface BuilderSubClause extends Serializable, IRuleTemplateInstanceObject  {

	public final int FILTERS_FEATURE_ID = 0;

	/**
	 * Returns the value of the '<em><b>Filters</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.webstudio.model.rule.instance.Filter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filters</em>' containment reference list.
	 * @generated
	 */
	List<Filter> getFilters();

	void addFilter(Filter filter);
	
	void addFilter(int index, Filter filter);
	
	void addFilter(Filter filter, boolean fireEvents);

	void removeFilter(Filter filter);
	
	void removeFilter(Filter filter, boolean fireEvents);

} // BuilderSubClause
