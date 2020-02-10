/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.UrisAndRefsConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Uris And Refs Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class UrisAndRefsConfigImpl extends ArtifactConfigImpl implements UrisAndRefsConfig {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UrisAndRefsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getUrisAndRefsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<String> getAllUris() {
		final List<UrisAndRefsConfig> used = new java.util.LinkedList<UrisAndRefsConfig>();
		used.add(this);
		return getAllUris(used);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private EList<String> getAllUris(java.util.List<UrisAndRefsConfig> used) {
		final EList<String> uris = new BasicEList<String>();
		uris.addAll(this.getUri());
		final EList<? extends UrisAndRefsConfig> refs = this.getRef();
		if (null != refs) {
			for (final UrisAndRefsConfig ref : refs) {
				if (!used.contains(ref)) {
					used.add(ref);
					uris.addAll(((UrisAndRefsConfigImpl) ref).getAllUris(used));
				}
			}
		}
		return uris;
	}
	
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<? extends UrisAndRefsConfig> getRef() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getUri() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

} //UrisAndRefsConfigImpl
