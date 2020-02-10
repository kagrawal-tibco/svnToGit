/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette.util;

import com.tibco.cep.studio.common.palette.PalettePackage;

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
public class PaletteXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		PalettePackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the PaletteResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new PaletteResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new PaletteResourceFactoryImpl());
		}
		return registrations;
	}

} //PaletteXMLProcessor
