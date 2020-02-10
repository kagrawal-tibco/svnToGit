/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.util;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

import com.tibco.be.util.config.sharedresources.basetypes.util.BasetypesValidator;

import com.tibco.be.util.config.sharedresources.id.*;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage
 * @generated
 */
public class IdValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final IdValidator INSTANCE = new IdValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "com.tibco.be.util.config.sharedresources.id";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BasetypesValidator basetypesValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdValidator() {
		super();
		basetypesValidator = BasetypesValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return IdPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case IdPackage.DESIGNER:
				return validateDesigner((Designer)value, diagnostics, context);
			case IdPackage.IDENTITY:
				return validateIdentity((Identity)value, diagnostics, context);
			case IdPackage.ID_ROOT:
				return validateIdRoot((IdRoot)value, diagnostics, context);
			case IdPackage.NODE:
				return validateNode((Node)value, diagnostics, context);
			case IdPackage.REPOSITORY:
				return validateRepository((Repository)value, diagnostics, context);
			case IdPackage.RESOURCE_DESCRIPTIONS:
				return validateResourceDescriptions((ResourceDescriptions)value, diagnostics, context);
			case IdPackage.FILE_TYPE_ENUM:
				return validateFileTypeEnum((FileTypeEnum)value, diagnostics, context);
			case IdPackage.OBJECT_TYPE_ENUM:
				return validateObjectTypeEnum((ObjectTypeEnum)value, diagnostics, context);
			case IdPackage.FILE_TYPE:
				return validateFileType(value, diagnostics, context);
			case IdPackage.FILE_TYPE_ENUM_OBJECT:
				return validateFileTypeEnumObject((FileTypeEnum)value, diagnostics, context);
			case IdPackage.OBJECT_TYPE:
				return validateObjectType(value, diagnostics, context);
			case IdPackage.OBJECT_TYPE_ENUM_OBJECT:
				return validateObjectTypeEnumObject((ObjectTypeEnum)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDesigner(Designer designer, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(designer, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdentity(Identity identity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(identity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdRoot(IdRoot idRoot, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(idRoot, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNode(Node node, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(node, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRepository(Repository repository, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(repository, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceDescriptions(ResourceDescriptions resourceDescriptions, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(resourceDescriptions, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFileTypeEnum(FileTypeEnum fileTypeEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateObjectTypeEnum(ObjectTypeEnum objectTypeEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFileType(Object fileType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateFileType_MemberTypes(fileType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>File Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFileType_MemberTypes(Object fileType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (BasetypesPackage.Literals.GV.isInstance(fileType)) {
				if (basetypesValidator.validateGv((String)fileType, tempDiagnostics, context)) return true;
			}
			if (IdPackage.Literals.FILE_TYPE_ENUM.isInstance(fileType)) {
				if (validateFileTypeEnum((FileTypeEnum)fileType, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (BasetypesPackage.Literals.GV.isInstance(fileType)) {
				if (basetypesValidator.validateGv((String)fileType, null, context)) return true;
			}
			if (IdPackage.Literals.FILE_TYPE_ENUM.isInstance(fileType)) {
				if (validateFileTypeEnum((FileTypeEnum)fileType, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFileTypeEnumObject(FileTypeEnum fileTypeEnumObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateObjectType(Object objectType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateObjectType_MemberTypes(objectType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateObjectType_MemberTypes(Object objectType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (BasetypesPackage.Literals.GV.isInstance(objectType)) {
				if (basetypesValidator.validateGv((String)objectType, tempDiagnostics, context)) return true;
			}
			if (IdPackage.Literals.OBJECT_TYPE_ENUM.isInstance(objectType)) {
				if (validateObjectTypeEnum((ObjectTypeEnum)objectType, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (BasetypesPackage.Literals.GV.isInstance(objectType)) {
				if (basetypesValidator.validateGv((String)objectType, null, context)) return true;
			}
			if (IdPackage.Literals.OBJECT_TYPE_ENUM.isInstance(objectType)) {
				if (validateObjectTypeEnum((ObjectTypeEnum)objectType, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateObjectTypeEnumObject(ObjectTypeEnum objectTypeEnumObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //IdValidator
