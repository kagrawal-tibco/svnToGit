/**
 */
package com.tibco.be.util.config.cdd.util;

import com.tibco.be.util.config.cdd.CddPackage;

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
public class CddXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		CddPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the CddResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new CddResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new CddResourceFactoryImpl());
		}
		return registrations;
	}

} //CddXMLProcessor
