/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Extractor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the extractor which solely extracts data from a 'tuple' using the 'sourceField'
 *             - sourceField is an actual md reference to a md field
 *             - The field can be actual field or a field from QuerySpec
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor#getSourceField <em>Source Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataExtractor()
 * @model extendedMetaData="name='DataExtractor' kind='elementOnly'"
 * @generated
 */
public interface DataExtractor extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Source Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Field</em>' reference.
	 * @see #setSourceField(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataExtractor_SourceField()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='sourceField'"
	 * @generated
	 */
	EObject getSourceField();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor#getSourceField <em>Source Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Field</em>' reference.
	 * @see #getSourceField()
	 * @generated
	 */
	void setSourceField(EObject value);

} // DataExtractor
