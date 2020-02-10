/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.validation.impl;

import com.tibco.cep.designtime.core.model.validation.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.designtime.core.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ValidationFactoryImpl extends EFactoryImpl implements ValidationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ValidationFactory init() {
		try {
			ValidationFactory theValidationFactory = (ValidationFactory)EPackage.Registry.INSTANCE.getEFactory(ValidationPackage.eNS_URI);
			if (theValidationFactory != null) {
				return theValidationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ValidationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValidationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ValidationPackage.MODEL_ERROR: return createModelError();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelError createModelError() {
		ModelErrorImpl modelError = new ModelErrorImpl();
		return modelError;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValidationPackage getValidationPackage() {
		return (ValidationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ValidationPackage getPackage() {
		return ValidationPackage.eINSTANCE;
	}

} //ValidationFactoryImpl
