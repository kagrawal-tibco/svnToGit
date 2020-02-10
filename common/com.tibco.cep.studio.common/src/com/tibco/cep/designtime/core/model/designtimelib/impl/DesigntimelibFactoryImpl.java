/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib.impl;

import com.tibco.cep.designtime.core.model.designtimelib.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib;
import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibFactory;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DesigntimelibFactoryImpl extends EFactoryImpl implements DesigntimelibFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DesigntimelibFactory init() {
		try {
			DesigntimelibFactory theDesigntimelibFactory = (DesigntimelibFactory)EPackage.Registry.INSTANCE.getEFactory(DesigntimelibPackage.eNS_URI);
			if (theDesigntimelibFactory != null) {
				return theDesigntimelibFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DesigntimelibFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesigntimelibFactoryImpl() {
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
			case DesigntimelibPackage.DESIGN_TIME_LIB: return createDesignTimeLib();
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY: return createDesignTimeLibEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignTimeLib createDesignTimeLib() {
		DesignTimeLibImpl designTimeLib = new DesignTimeLibImpl();
		return designTimeLib;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignTimeLibEntry createDesignTimeLibEntry() {
		DesignTimeLibEntryImpl designTimeLibEntry = new DesignTimeLibEntryImpl();
		return designTimeLibEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesigntimelibPackage getDesigntimelibPackage() {
		return (DesigntimelibPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DesigntimelibPackage getPackage() {
		return DesigntimelibPackage.eINSTANCE;
	}

} //DesigntimelibFactoryImpl
