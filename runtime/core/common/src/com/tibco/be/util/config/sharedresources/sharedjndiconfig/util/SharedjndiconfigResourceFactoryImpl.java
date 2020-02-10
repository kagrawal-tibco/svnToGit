/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.util;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.util.SharedjndiconfigResourceImpl
 * @generated
 */
public class SharedjndiconfigResourceFactoryImpl extends ResourceFactoryImpl {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected ExtendedMetaData extendedMetaData;

	/**
     * Creates an instance of the resource factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SharedjndiconfigResourceFactoryImpl() {
        super();
        extendedMetaData = new BasicExtendedMetaData(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
        extendedMetaData.putPackage(null, SharedjndiconfigPackage.eINSTANCE);
    }

	/**
     * Creates an instance of the resource.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Resource createResource(URI uri) {
        XMLResource result = new SharedjndiconfigResourceImpl(uri);
        result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
        result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);

        result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

        result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
        result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

        result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
        return result;
    }

} //SharedjndiconfigResourceFactoryImpl
