/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.util;

import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AemetaservicesXMLProcessor extends XMLProcessor {

	/**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AemetaservicesXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        AemetaservicesPackage.eINSTANCE.eClass();
    }
	
	/**
     * Register for "*" and "xml" file extensions the AemetaservicesResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null)
        {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new AemetaservicesResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new AemetaservicesResourceFactoryImpl());
        }
        return registrations;
    }

} //AemetaservicesXMLProcessor
