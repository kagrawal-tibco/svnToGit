/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.diagramming.menu.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import com.tibco.cep.diagramming.menu.MenuPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MenuXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		MenuPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the MenuResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new MenuResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new MenuResourceFactoryImpl());
		}
		return registrations;
	}

} //MenuXMLProcessor
