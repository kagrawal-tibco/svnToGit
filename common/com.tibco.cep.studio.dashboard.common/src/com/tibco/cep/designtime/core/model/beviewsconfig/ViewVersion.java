/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Contains information abt the version number of the
 * 				BE Views Config. Only one instance would exist in a
 * 				repository and it should not be editable
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMajorVersionNumber <em>Major Version Number</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMinorVersionNumber <em>Minor Version Number</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getPointVersionNumber <em>Point Version Number</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getDescription1 <em>Description1</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewVersion()
 * @model extendedMetaData="name='BEViewsConfigVersion' kind='elementOnly'"
 * @generated
 */
public interface ViewVersion extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Major Version Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Major Version Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Major Version Number</em>' attribute.
	 * @see #setMajorVersionNumber(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewVersion_MajorVersionNumber()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='majorVersionNumber'"
	 * @generated
	 */
	String getMajorVersionNumber();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMajorVersionNumber <em>Major Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Major Version Number</em>' attribute.
	 * @see #getMajorVersionNumber()
	 * @generated
	 */
	void setMajorVersionNumber(String value);

	/**
	 * Returns the value of the '<em><b>Minor Version Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minor Version Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minor Version Number</em>' attribute.
	 * @see #setMinorVersionNumber(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewVersion_MinorVersionNumber()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='minorVersionNumber'"
	 * @generated
	 */
	String getMinorVersionNumber();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMinorVersionNumber <em>Minor Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minor Version Number</em>' attribute.
	 * @see #getMinorVersionNumber()
	 * @generated
	 */
	void setMinorVersionNumber(String value);

	/**
	 * Returns the value of the '<em><b>Point Version Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Point Version Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Point Version Number</em>' attribute.
	 * @see #setPointVersionNumber(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewVersion_PointVersionNumber()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='pointVersionNumber'"
	 * @generated
	 */
	String getPointVersionNumber();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getPointVersionNumber <em>Point Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point Version Number</em>' attribute.
	 * @see #getPointVersionNumber()
	 * @generated
	 */
	void setPointVersionNumber(String value);

	/**
	 * Returns the value of the '<em><b>Description1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description1</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description1</em>' attribute.
	 * @see #setDescription1(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewVersion_Description1()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description'"
	 * @generated
	 */
	String getDescription1();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getDescription1 <em>Description1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description1</em>' attribute.
	 * @see #getDescription1()
	 * @generated
	 */
	void setDescription1(String value);

} // ViewVersion
