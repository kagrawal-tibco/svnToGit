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
 * A representation of the model object '<em><b>Related Filter Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface RelatedFilterValue extends FilterValue {
	
	public final int LINKS_FEATURE_ID = 0;

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

	void removeLink(RelatedLink link);

	void addLink(RelatedLink link);

} // RelatedFilterValue
