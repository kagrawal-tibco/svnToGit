/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette.impl;

import com.tibco.cep.studio.common.palette.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PaletteFactoryImpl extends EFactoryImpl implements PaletteFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PaletteFactory init() {
		try {
			PaletteFactory thePaletteFactory = (PaletteFactory)EPackage.Registry.INSTANCE.getEFactory(PalettePackage.eNS_URI);
			if (thePaletteFactory != null) {
				return thePaletteFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PaletteFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteFactoryImpl() {
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
			case PalettePackage.DOCUMENT_ROOT: return createDocumentRoot();
			case PalettePackage.EMF_TYPE: return createEmfType();
			case PalettePackage.HELP: return createHelp();
			case PalettePackage.JAVA_TYPE: return createJavaType();
			case PalettePackage.MODEL_TYPE: return createModelType();
			case PalettePackage.PALETTE_GROUP: return createPaletteGroup();
			case PalettePackage.PALETTE_ITEM: return createPaletteItem();
			case PalettePackage.PALETTE_MODEL: return createPaletteModel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PalettePackage.TAB:
				return createTabFromString(eDataType, initialValue);
			case PalettePackage.TAB_OBJECT:
				return createTabObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PalettePackage.TAB:
				return convertTabToString(eDataType, instanceValue);
			case PalettePackage.TAB_OBJECT:
				return convertTabObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfType createEmfType() {
		EmfTypeImpl emfType = new EmfTypeImpl();
		return emfType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Help createHelp() {
		HelpImpl help = new HelpImpl();
		return help;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaType createJavaType() {
		JavaTypeImpl javaType = new JavaTypeImpl();
		return javaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelType createModelType() {
		ModelTypeImpl modelType = new ModelTypeImpl();
		return modelType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteGroup createPaletteGroup() {
		PaletteGroupImpl paletteGroup = new PaletteGroupImpl();
		return paletteGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteItem createPaletteItem() {
		PaletteItemImpl paletteItem = new PaletteItemImpl();
		return paletteItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteModel createPaletteModel() {
		PaletteModelImpl paletteModel = new PaletteModelImpl();
		return paletteModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tab createTabFromString(EDataType eDataType, String initialValue) {
		Tab result = Tab.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTabToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tab createTabObjectFromString(EDataType eDataType, String initialValue) {
		return createTabFromString(PalettePackage.Literals.TAB, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTabObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTabToString(PalettePackage.Literals.TAB, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PalettePackage getPalettePackage() {
		return (PalettePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PalettePackage getPackage() {
		return PalettePackage.eINSTANCE;
	}

} //PaletteFactoryImpl
