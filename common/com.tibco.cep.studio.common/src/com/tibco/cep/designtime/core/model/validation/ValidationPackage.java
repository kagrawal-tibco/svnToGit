/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.validation;

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
 * @see com.tibco.cep.designtime.core.model.validation.ValidationFactory
 * @model kind="package"
 * @generated
 */
public interface ValidationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "validation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/validation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "validation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ValidationPackage eINSTANCE = com.tibco.cep.designtime.core.model.validation.impl.ValidationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl <em>Model Error</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl
	 * @see com.tibco.cep.designtime.core.model.validation.impl.ValidationPackageImpl#getModelError()
	 * @generated
	 */
	int MODEL_ERROR = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ERROR__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>Warning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ERROR__WARNING = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ERROR__SOURCE = 2;

	/**
	 * The number of structural features of the '<em>Model Error</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ERROR_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.validation.ModelError <em>Model Error</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Error</em>'.
	 * @see com.tibco.cep.designtime.core.model.validation.ModelError
	 * @generated
	 */
	EClass getModelError();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.validation.ModelError#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see com.tibco.cep.designtime.core.model.validation.ModelError#getMessage()
	 * @see #getModelError()
	 * @generated
	 */
	EAttribute getModelError_Message();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.validation.ModelError#isWarning <em>Warning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Warning</em>'.
	 * @see com.tibco.cep.designtime.core.model.validation.ModelError#isWarning()
	 * @see #getModelError()
	 * @generated
	 */
	EAttribute getModelError_Warning();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.validation.ModelError#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see com.tibco.cep.designtime.core.model.validation.ModelError#getSource()
	 * @see #getModelError()
	 * @generated
	 */
	EReference getModelError_Source();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ValidationFactory getValidationFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl <em>Model Error</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.validation.impl.ModelErrorImpl
		 * @see com.tibco.cep.designtime.core.model.validation.impl.ValidationPackageImpl#getModelError()
		 * @generated
		 */
		EClass MODEL_ERROR = eINSTANCE.getModelError();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ERROR__MESSAGE = eINSTANCE.getModelError_Message();

		/**
		 * The meta object literal for the '<em><b>Warning</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ERROR__WARNING = eINSTANCE.getModelError_Warning();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_ERROR__SOURCE = eINSTANCE.getModelError_Source();

	}

} //ValidationPackage
