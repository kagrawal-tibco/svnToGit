/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.Loop;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl#getLoopType <em>Loop Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl#getLoopCondition <em>Loop Condition</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl#getStartNode <em>Start Node</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.impl.LoopImpl#getEndNodes <em>End Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoopImpl extends NodeElementImpl implements Loop {
	/**
	 * The default value of the '{@link #getLoopType() <em>Loop Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoopType()
	 * @generated
	 * @ordered
	 */
	protected static final Enumerator LOOP_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLoopType() <em>Loop Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoopType()
	 * @generated
	 * @ordered
	 */
	protected Enumerator loopType = LOOP_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLoopCondition() <em>Loop Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoopCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String LOOP_CONDITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLoopCondition() <em>Loop Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoopCondition()
	 * @generated
	 * @ordered
	 */
	protected String loopCondition = LOOP_CONDITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<FlowElement> elements;

	/**
	 * The cached value of the '{@link #getStartNode() <em>Start Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartNode()
	 * @generated
	 * @ordered
	 */
	protected NodeElement startNode;

	/**
	 * The cached value of the '{@link #getEndNodes() <em>End Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeElement> endNodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoopImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NodePackage.Literals.LOOP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Enumerator getLoopType() {
		return loopType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoopType(Enumerator newLoopType) {
		Enumerator oldLoopType = loopType;
		loopType = newLoopType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NodePackage.LOOP__LOOP_TYPE, oldLoopType, loopType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLoopCondition() {
		return loopCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoopCondition(String newLoopCondition) {
		String oldLoopCondition = loopCondition;
		loopCondition = newLoopCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NodePackage.LOOP__LOOP_CONDITION, oldLoopCondition, loopCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FlowElement> getElements() {
		if (elements == null) {
			elements = new EObjectResolvingEList<FlowElement>(FlowElement.class, this, NodePackage.LOOP__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement getStartNode() {
		if (startNode != null && startNode.eIsProxy()) {
			InternalEObject oldStartNode = (InternalEObject)startNode;
			startNode = (NodeElement)eResolveProxy(oldStartNode);
			if (startNode != oldStartNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NodePackage.LOOP__START_NODE, oldStartNode, startNode));
			}
		}
		return startNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeElement basicGetStartNode() {
		return startNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartNode(NodeElement newStartNode) {
		NodeElement oldStartNode = startNode;
		startNode = newStartNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NodePackage.LOOP__START_NODE, oldStartNode, startNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NodeElement> getEndNodes() {
		if (endNodes == null) {
			endNodes = new EObjectResolvingEList<NodeElement>(NodeElement.class, this, NodePackage.LOOP__END_NODES);
		}
		return endNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NodePackage.LOOP__LOOP_TYPE:
				return getLoopType();
			case NodePackage.LOOP__LOOP_CONDITION:
				return getLoopCondition();
			case NodePackage.LOOP__ELEMENTS:
				return getElements();
			case NodePackage.LOOP__START_NODE:
				if (resolve) return getStartNode();
				return basicGetStartNode();
			case NodePackage.LOOP__END_NODES:
				return getEndNodes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NodePackage.LOOP__LOOP_TYPE:
				setLoopType((Enumerator)newValue);
				return;
			case NodePackage.LOOP__LOOP_CONDITION:
				setLoopCondition((String)newValue);
				return;
			case NodePackage.LOOP__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends FlowElement>)newValue);
				return;
			case NodePackage.LOOP__START_NODE:
				setStartNode((NodeElement)newValue);
				return;
			case NodePackage.LOOP__END_NODES:
				getEndNodes().clear();
				getEndNodes().addAll((Collection<? extends NodeElement>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NodePackage.LOOP__LOOP_TYPE:
				setLoopType(LOOP_TYPE_EDEFAULT);
				return;
			case NodePackage.LOOP__LOOP_CONDITION:
				setLoopCondition(LOOP_CONDITION_EDEFAULT);
				return;
			case NodePackage.LOOP__ELEMENTS:
				getElements().clear();
				return;
			case NodePackage.LOOP__START_NODE:
				setStartNode((NodeElement)null);
				return;
			case NodePackage.LOOP__END_NODES:
				getEndNodes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NodePackage.LOOP__LOOP_TYPE:
				return LOOP_TYPE_EDEFAULT == null ? loopType != null : !LOOP_TYPE_EDEFAULT.equals(loopType);
			case NodePackage.LOOP__LOOP_CONDITION:
				return LOOP_CONDITION_EDEFAULT == null ? loopCondition != null : !LOOP_CONDITION_EDEFAULT.equals(loopCondition);
			case NodePackage.LOOP__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case NodePackage.LOOP__START_NODE:
				return startNode != null;
			case NodePackage.LOOP__END_NODES:
				return endNodes != null && !endNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (loopType: ");
		result.append(loopType);
		result.append(", loopCondition: ");
		result.append(loopCondition);
		result.append(')');
		return result.toString();
	}

} //LoopImpl
