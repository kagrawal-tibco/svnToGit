/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decision.tree.common.model.node.DescriptionElement;
import com.tibco.cep.decision.tree.common.model.node.Loop;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.NodeFactory;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NodeFactoryImpl extends EFactoryImpl implements NodeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NodeFactory init() {
		try {
			NodeFactory theNodeFactory = (NodeFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decision/tree/model/node"); 
			if (theNodeFactory != null) {
				return theNodeFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NodeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeFactoryImpl() {
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
			case NodePackage.NODE_ELEMENT: return createNodeElement();
			case NodePackage.LOOP: return createLoop();
			case NodePackage.DESCRIPTION_ELEMENT: return createDescriptionElement();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement createNodeElement() {
		NodeElementImpl nodeElement = new NodeElementImpl();
		return nodeElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Loop createLoop() {
		LoopImpl loop = new LoopImpl();
		return loop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DescriptionElement createDescriptionElement() {
		DescriptionElementImpl descriptionElement = new DescriptionElementImpl();
		return descriptionElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodePackage getNodePackage() {
		return (NodePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NodePackage getPackage() {
		return NodePackage.eINSTANCE;
	}

} //NodeFactoryImpl
