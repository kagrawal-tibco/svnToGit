/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.util;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SharedjndiconfigXMLProcessor extends XMLProcessor {

	/**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SharedjndiconfigXMLProcessor() {
        super(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
        extendedMetaData.putPackage(null, SharedjndiconfigPackage.eINSTANCE);
    }
	
	/**
     * Register for "*" and "xml" file extensions the SharedjndiconfigResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null)
        {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new SharedjndiconfigResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new SharedjndiconfigResourceFactoryImpl());
        }
        return registrations;
    }

} //SharedjndiconfigXMLProcessor
