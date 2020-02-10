/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.edge.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decision.tree.common.model.edge.Association;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.edge.EdgeFactory;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EdgeFactoryImpl extends EFactoryImpl implements EdgeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EdgeFactory init() {
		try {
			EdgeFactory theEdgeFactory = (EdgeFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decision/tree/model/edge"); 
			if (theEdgeFactory != null) {
				return theEdgeFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EdgeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EdgePackage.EDGE: return createEdge();
			case EdgePackage.ASSOCIATION: return createAssociation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge createEdge() {
		EdgeImpl edge = new EdgeImpl();
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Association createAssociation() {
		AssociationImpl association = new AssociationImpl();
		return association;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgePackage getEdgePackage() {
		return (EdgePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EdgePackage getPackage() {
		return EdgePackage.eINSTANCE;
	}

} //EdgeFactoryImpl
