/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.util;

import com.tibco.be.util.config.sharedresources.id.IdPackage;

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
public class IdXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IdPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the IdResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new IdResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new IdResourceFactoryImpl());
		}
		return registrations;
	}

} //IdXMLProcessor
