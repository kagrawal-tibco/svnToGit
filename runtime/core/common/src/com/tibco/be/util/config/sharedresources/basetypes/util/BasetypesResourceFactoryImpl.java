/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.basetypes.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.basetypes.util.BasetypesResourceImpl
 * @generated
 */
public class BasetypesResourceFactoryImpl extends ResourceFactoryImpl {
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasetypesResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource(URI uri) {
		Resource result = new BasetypesResourceImpl(uri);
		return result;
	}

} //BasetypesResourceFactoryImpl
