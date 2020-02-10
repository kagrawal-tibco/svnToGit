/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.namespace.util;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import com.tibco.be.baas.security.authn.saml.model.namespace.NamespacePackage;
import com.tibco.be.baas.security.authn.saml.model.namespace.SpaceType;
import com.tibco.be.baas.security.authn.saml.model.namespace.XMLNamespaceDocumentRoot;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.namespace.NamespacePackage
 * @generated
 */
public class NamespaceValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final NamespaceValidator INSTANCE = new NamespaceValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "com.tibco.be.baas.security.authn.saml.model.namespace";

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
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return NamespacePackage.eINSTANCE;
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
			case NamespacePackage.XML_NAMESPACE_DOCUMENT_ROOT:
				return validateXMLNamespaceDocumentRoot((XMLNamespaceDocumentRoot)value, diagnostics, context);
			case NamespacePackage.SPACE_TYPE:
				return validateSpaceType((SpaceType)value, diagnostics, context);
			case NamespacePackage.LANG_TYPE:
				return validateLangType((String)value, diagnostics, context);
			case NamespacePackage.LANG_TYPE_NULL:
				return validateLangTypeNull((String)value, diagnostics, context);
			case NamespacePackage.SPACE_TYPE_OBJECT:
				return validateSpaceTypeObject((SpaceType)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateXMLNamespaceDocumentRoot(XMLNamespaceDocumentRoot xmlNamespaceDocumentRoot, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(xmlNamespaceDocumentRoot, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSpaceType(SpaceType spaceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLangType(String langType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateLangType_MemberTypes(langType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Lang Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLangType_MemberTypes(String langType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.LANGUAGE.isInstance(langType)) {
				if (xmlTypeValidator.validateLanguage(langType, tempDiagnostics, context)) return true;
			}
			if (NamespacePackage.Literals.LANG_TYPE_NULL.isInstance(langType)) {
				if (validateLangTypeNull(langType, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.LANGUAGE.isInstance(langType)) {
				if (xmlTypeValidator.validateLanguage(langType, null, context)) return true;
			}
			if (NamespacePackage.Literals.LANG_TYPE_NULL.isInstance(langType)) {
				if (validateLangTypeNull(langType, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLangTypeNull(String langTypeNull, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSpaceTypeObject(SpaceType spaceTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
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

} //NamespaceValidator
