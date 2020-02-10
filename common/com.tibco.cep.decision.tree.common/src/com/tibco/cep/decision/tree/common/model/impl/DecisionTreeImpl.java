/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decision Tree</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getOwnerProject <em>Owner Project</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.impl.DecisionTreeImpl#getStartNode <em>Start Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DecisionTreeImpl extends EObjectImpl implements DecisionTree {
	/**
	 * The default value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected String folder = FOLDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnerProject() <em>Owner Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProject()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerProject() <em>Owner Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProject()
	 * @generated
	 * @ordered
	 */
	protected String ownerProject = OWNER_PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected Date creationDate = CREATION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_MODIFIED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected Date lastModified = LAST_MODIFIED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeElement> nodes;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> edges;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DecisionTreeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.DECISION_TREE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFolder(String newFolder) {
		String oldFolder = folder;
		folder = newFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DECISION_TREE__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwnerProject() {
		return ownerProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerProject(String newOwnerProject) {
		String oldOwnerProject = ownerProject;
		ownerProject = newOwnerProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DECISION_TREE__OWNER_PROJECT, oldOwnerProject, ownerProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationDate(Date newCreationDate) {
		Date oldCreationDate = creationDate;
		creationDate = newCreationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DECISION_TREE__CREATION_DATE, oldCreationDate, creationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModified(Date newLastModified) {
		Date oldLastModified = lastModified;
		lastModified = newLastModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DECISION_TREE__LAST_MODIFIED, oldLastModified, lastModified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NodeElement> getNodes() {
		if (nodes == null) {
			nodes = new EObjectResolvingEList<NodeElement>(NodeElement.class, this, ModelPackage.DECISION_TREE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getEdges() {
		if (edges == null) {
			edges = new EObjectResolvingEList<Edge>(Edge.class, this, ModelPackage.DECISION_TREE__EDGES);
		}
		return edges;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.DECISION_TREE__START_NODE, oldStartNode, startNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DECISION_TREE__START_NODE, oldStartNode, startNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.DECISION_TREE__FOLDER:
				return getFolder();
			case ModelPackage.DECISION_TREE__OWNER_PROJECT:
				return getOwnerProject();
			case ModelPackage.DECISION_TREE__CREATION_DATE:
				return getCreationDate();
			case ModelPackage.DECISION_TREE__LAST_MODIFIED:
				return getLastModified();
			case ModelPackage.DECISION_TREE__NODES:
				return getNodes();
			case ModelPackage.DECISION_TREE__EDGES:
				return getEdges();
			case ModelPackage.DECISION_TREE__START_NODE:
				if (resolve) return getStartNode();
				return basicGetStartNode();
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
			case ModelPackage.DECISION_TREE__FOLDER:
				setFolder((String)newValue);
				return;
			case ModelPackage.DECISION_TREE__OWNER_PROJECT:
				setOwnerProject((String)newValue);
				return;
			case ModelPackage.DECISION_TREE__CREATION_DATE:
				setCreationDate((Date)newValue);
				return;
			case ModelPackage.DECISION_TREE__LAST_MODIFIED:
				setLastModified((Date)newValue);
				return;
			case ModelPackage.DECISION_TREE__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends NodeElement>)newValue);
				return;
			case ModelPackage.DECISION_TREE__EDGES:
				getEdges().clear();
				getEdges().addAll((Collection<? extends Edge>)newValue);
				return;
			case ModelPackage.DECISION_TREE__START_NODE:
				setStartNode((NodeElement)newValue);
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
			case ModelPackage.DECISION_TREE__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case ModelPackage.DECISION_TREE__OWNER_PROJECT:
				setOwnerProject(OWNER_PROJECT_EDEFAULT);
				return;
			case ModelPackage.DECISION_TREE__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
				return;
			case ModelPackage.DECISION_TREE__LAST_MODIFIED:
				setLastModified(LAST_MODIFIED_EDEFAULT);
				return;
			case ModelPackage.DECISION_TREE__NODES:
				getNodes().clear();
				return;
			case ModelPackage.DECISION_TREE__EDGES:
				getEdges().clear();
				return;
			case ModelPackage.DECISION_TREE__START_NODE:
				setStartNode((NodeElement)null);
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
			case ModelPackage.DECISION_TREE__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case ModelPackage.DECISION_TREE__OWNER_PROJECT:
				return OWNER_PROJECT_EDEFAULT == null ? ownerProject != null : !OWNER_PROJECT_EDEFAULT.equals(ownerProject);
			case ModelPackage.DECISION_TREE__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
			case ModelPackage.DECISION_TREE__LAST_MODIFIED:
				return LAST_MODIFIED_EDEFAULT == null ? lastModified != null : !LAST_MODIFIED_EDEFAULT.equals(lastModified);
			case ModelPackage.DECISION_TREE__NODES:
				return nodes != null && !nodes.isEmpty();
			case ModelPackage.DECISION_TREE__EDGES:
				return edges != null && !edges.isEmpty();
			case ModelPackage.DECISION_TREE__START_NODE:
				return startNode != null;
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
		result.append(" (folder: ");
		result.append(folder);
		result.append(", ownerProject: ");
		result.append(ownerProject);
		result.append(", creationDate: ");
		result.append(creationDate);
		result.append(", lastModified: ");
		result.append(lastModified);
		result.append(')');
		return result.toString();
	}

} //DecisionTreeImpl
