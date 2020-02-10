/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedhttp.util;

import com.tibco.be.util.config.sharedresources.sharedhttp.SharedhttpPackage;

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
public class SharedhttpXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedhttpXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		SharedhttpPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the SharedhttpResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new SharedhttpResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new SharedhttpResourceFactoryImpl());
		}
		return registrations;
	}

} //SharedhttpXMLProcessor
