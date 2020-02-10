/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibFactory
 * @model kind="package"
 * @generated
 */
public interface DesigntimelibPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "designtimelib";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/designtimelib";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "designtimelib";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DesigntimelibPackage eINSTANCE = com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibImpl <em>Design Time Lib</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibImpl
	 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl#getDesignTimeLib()
	 * @generated
	 */
	int DESIGN_TIME_LIB = 0;

	/**
	 * The feature id for the '<em><b>Design Time Lib Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY = 0;

	/**
	 * The number of structural features of the '<em>Design Time Lib</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGN_TIME_LIB_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibEntryImpl <em>Design Time Lib Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibEntryImpl
	 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl#getDesignTimeLibEntry()
	 * @generated
	 */
	int DESIGN_TIME_LIB_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGN_TIME_LIB_ENTRY__ALIAS = 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGN_TIME_LIB_ENTRY__LOCATION = 1;

	/**
	 * The number of structural features of the '<em>Design Time Lib Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGN_TIME_LIB_ENTRY_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib <em>Design Time Lib</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Design Time Lib</em>'.
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib
	 * @generated
	 */
	EClass getDesignTimeLib();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib#getDesignTimeLibEntry <em>Design Time Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Design Time Lib Entry</em>'.
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib#getDesignTimeLibEntry()
	 * @see #getDesignTimeLib()
	 * @generated
	 */
	EReference getDesignTimeLib_DesignTimeLibEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry <em>Design Time Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Design Time Lib Entry</em>'.
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry
	 * @generated
	 */
	EClass getDesignTimeLibEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getAlias()
	 * @see #getDesignTimeLibEntry()
	 * @generated
	 */
	EAttribute getDesignTimeLibEntry_Alias();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry#getLocation()
	 * @see #getDesignTimeLibEntry()
	 * @generated
	 */
	EAttribute getDesignTimeLibEntry_Location();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DesigntimelibFactory getDesigntimelibFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibImpl <em>Design Time Lib</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibImpl
		 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl#getDesignTimeLib()
		 * @generated
		 */
		EClass DESIGN_TIME_LIB = eINSTANCE.getDesignTimeLib();

		/**
		 * The meta object literal for the '<em><b>Design Time Lib Entry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY = eINSTANCE.getDesignTimeLib_DesignTimeLibEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibEntryImpl <em>Design Time Lib Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibEntryImpl
		 * @see com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl#getDesignTimeLibEntry()
		 * @generated
		 */
		EClass DESIGN_TIME_LIB_ENTRY = eINSTANCE.getDesignTimeLibEntry();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGN_TIME_LIB_ENTRY__ALIAS = eINSTANCE.getDesignTimeLibEntry_Alias();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGN_TIME_LIB_ENTRY__LOCATION = eINSTANCE.getDesignTimeLibEntry_Location();

	}

} //DesigntimelibPackage
