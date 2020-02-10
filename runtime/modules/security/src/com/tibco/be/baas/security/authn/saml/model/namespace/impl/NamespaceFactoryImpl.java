/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.namespace.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.be.baas.security.authn.saml.model.namespace.NamespaceFactory;
import com.tibco.be.baas.security.authn.saml.model.namespace.NamespacePackage;
import com.tibco.be.baas.security.authn.saml.model.namespace.SpaceType;
import com.tibco.be.baas.security.authn.saml.model.namespace.XMLNamespaceDocumentRoot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NamespaceFactoryImpl extends EFactoryImpl implements NamespaceFactory {

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public static NamespaceFactory init() {
//        try {
//            EFactory eFactory = EPackage.Registry.INSTANCE.getEFactory("http://www.w3.org/XML/1998/namespace");
//            XMLNamespaceFactory theNamespaceFactory = (XMLNamespaceFactory)eFactory;
//            if (theNamespaceFactory != null) {
//                return null;
//            }
//        } catch (Exception exception) {
//            EcorePlugin.INSTANCE.log(exception);
//        }
        return new NamespaceFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NamespaceFactoryImpl() {
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
            case NamespacePackage.XML_NAMESPACE_DOCUMENT_ROOT:
                return createXMLNamespaceDocumentRoot();
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
            case NamespacePackage.SPACE_TYPE:
                return createSpaceTypeFromString(eDataType, initialValue);
            case NamespacePackage.LANG_TYPE:
                return createLangTypeFromString(eDataType, initialValue);
            case NamespacePackage.LANG_TYPE_NULL:
                return createLangTypeNullFromString(eDataType, initialValue);
            case NamespacePackage.SPACE_TYPE_OBJECT:
                return createSpaceTypeObjectFromString(eDataType, initialValue);
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
            case NamespacePackage.SPACE_TYPE:
                return convertSpaceTypeToString(eDataType, instanceValue);
            case NamespacePackage.LANG_TYPE:
                return convertLangTypeToString(eDataType, instanceValue);
            case NamespacePackage.LANG_TYPE_NULL:
                return convertLangTypeNullToString(eDataType, instanceValue);
            case NamespacePackage.SPACE_TYPE_OBJECT:
                return convertSpaceTypeObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLNamespaceDocumentRoot createXMLNamespaceDocumentRoot() {
        XMLNamespaceDocumentRootImpl xmlNamespaceDocumentRoot = new XMLNamespaceDocumentRootImpl();
        return xmlNamespaceDocumentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SpaceType createSpaceTypeFromString(EDataType eDataType, String initialValue) {
        SpaceType result = SpaceType.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSpaceTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createLangTypeFromString(EDataType eDataType, String initialValue) {
        if (initialValue == null) {
            return null;
        }
        String result = null;
        RuntimeException exception = null;
        try {
            result = (String) XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.LANGUAGE, initialValue);
            if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
                return result;
            }
        } catch (RuntimeException e) {
            exception = e;
        }
        try {
            result = createLangTypeNullFromString(NamespacePackage.Literals.LANG_TYPE_NULL, initialValue);
            if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
                return result;
            }
        } catch (RuntimeException e) {
            exception = e;
        }
        if (result != null || exception == null) {
            return result;
        }

        throw exception;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLangTypeToString(EDataType eDataType, Object instanceValue) {
        if (instanceValue == null) {
            return null;
        }
        if (XMLTypePackage.Literals.LANGUAGE.isInstance(instanceValue)) {
            try {
                String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.LANGUAGE, instanceValue);
                if (value != null) {
                    return value;
                }
            } catch (Exception e) {
                // Keep trying other member types until all have failed.
            }
        }
        if (NamespacePackage.Literals.LANG_TYPE_NULL.isInstance(instanceValue)) {
            try {
                String value = convertLangTypeNullToString(NamespacePackage.Literals.LANG_TYPE_NULL, instanceValue);
                if (value != null) {
                    return value;
                }
            } catch (Exception e) {
                // Keep trying other member types until all have failed.
            }
        }
        throw new IllegalArgumentException("Invalid value: '" + instanceValue + "' for datatype :" + eDataType.getName());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createLangTypeNullFromString(EDataType eDataType, String initialValue) {
        return (String) XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLangTypeNullToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SpaceType createSpaceTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createSpaceTypeFromString(NamespacePackage.Literals.SPACE_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSpaceTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertSpaceTypeToString(NamespacePackage.Literals.SPACE_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NamespacePackage getNamespacePackage() {
        return (NamespacePackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static NamespacePackage getPackage() {
        return NamespacePackage.eINSTANCE;
    }
} //NamespaceFactoryImpl
