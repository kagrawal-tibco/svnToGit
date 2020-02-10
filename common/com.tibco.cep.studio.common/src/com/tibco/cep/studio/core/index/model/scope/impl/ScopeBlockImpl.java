/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getParentScopeDef <em>Parent Scope Def</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getChildScopeDefs <em>Child Scope Defs</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getDefs <em>Defs</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getRefs <em>Refs</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScopeBlockImpl extends EObjectImpl implements ScopeBlock {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final int TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected int type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildScopeDefs() <em>Child Scope Defs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildScopeDefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ScopeBlock> childScopeDefs;

	/**
	 * The cached value of the '{@link #getDefs() <em>Defs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefs()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalVariableDef> defs;

	/**
	 * The cached value of the '{@link #getRefs() <em>Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementReference> refs;

	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected static final int OFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected int offset = OFFSET_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScopeBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScopePackage.Literals.SCOPE_BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(int newType) {
		int oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.SCOPE_BLOCK__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopeBlock getParentScopeDef() {
		if (eContainerFeatureID() != ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF) return null;
		return (ScopeBlock)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentScopeDef(ScopeBlock newParentScopeDef, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentScopeDef, ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentScopeDef(ScopeBlock newParentScopeDef) {
		if (newParentScopeDef != eInternalContainer() || (eContainerFeatureID() != ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF && newParentScopeDef != null)) {
			if (EcoreUtil.isAncestor(this, newParentScopeDef))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentScopeDef != null)
				msgs = ((InternalEObject)newParentScopeDef).eInverseAdd(this, ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS, ScopeBlock.class, msgs);
			msgs = basicSetParentScopeDef(newParentScopeDef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF, newParentScopeDef, newParentScopeDef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScopeBlock> getChildScopeDefs() {
		if (childScopeDefs == null) {
			childScopeDefs = new EObjectContainmentWithInverseEList<ScopeBlock>(ScopeBlock.class, this, ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS, ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF);
		}
		return childScopeDefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalVariableDef> getDefs() {
		if (defs == null) {
			defs = new EObjectContainmentEList<LocalVariableDef>(LocalVariableDef.class, this, ScopePackage.SCOPE_BLOCK__DEFS);
		}
		return defs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ElementReference> getRefs() {
		if (refs == null) {
			refs = new EObjectContainmentEList<ElementReference>(ElementReference.class, this, ScopePackage.SCOPE_BLOCK__REFS);
		}
		return refs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffset(int newOffset) {
		int oldOffset = offset;
		offset = newOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.SCOPE_BLOCK__OFFSET, oldOffset, offset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.SCOPE_BLOCK__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentScopeDef((ScopeBlock)otherEnd, msgs);
			case ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildScopeDefs()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				return basicSetParentScopeDef(null, msgs);
			case ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS:
				return ((InternalEList<?>)getChildScopeDefs()).basicRemove(otherEnd, msgs);
			case ScopePackage.SCOPE_BLOCK__DEFS:
				return ((InternalEList<?>)getDefs()).basicRemove(otherEnd, msgs);
			case ScopePackage.SCOPE_BLOCK__REFS:
				return ((InternalEList<?>)getRefs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				return eInternalContainer().eInverseRemove(this, ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS, ScopeBlock.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ScopePackage.SCOPE_BLOCK__TYPE:
				return getType();
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				return getParentScopeDef();
			case ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS:
				return getChildScopeDefs();
			case ScopePackage.SCOPE_BLOCK__DEFS:
				return getDefs();
			case ScopePackage.SCOPE_BLOCK__REFS:
				return getRefs();
			case ScopePackage.SCOPE_BLOCK__OFFSET:
				return getOffset();
			case ScopePackage.SCOPE_BLOCK__LENGTH:
				return getLength();
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
			case ScopePackage.SCOPE_BLOCK__TYPE:
				setType((Integer)newValue);
				return;
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				setParentScopeDef((ScopeBlock)newValue);
				return;
			case ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS:
				getChildScopeDefs().clear();
				getChildScopeDefs().addAll((Collection<? extends ScopeBlock>)newValue);
				return;
			case ScopePackage.SCOPE_BLOCK__DEFS:
				getDefs().clear();
				getDefs().addAll((Collection<? extends LocalVariableDef>)newValue);
				return;
			case ScopePackage.SCOPE_BLOCK__REFS:
				getRefs().clear();
				getRefs().addAll((Collection<? extends ElementReference>)newValue);
				return;
			case ScopePackage.SCOPE_BLOCK__OFFSET:
				setOffset((Integer)newValue);
				return;
			case ScopePackage.SCOPE_BLOCK__LENGTH:
				setLength((Integer)newValue);
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
			case ScopePackage.SCOPE_BLOCK__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				setParentScopeDef((ScopeBlock)null);
				return;
			case ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS:
				getChildScopeDefs().clear();
				return;
			case ScopePackage.SCOPE_BLOCK__DEFS:
				getDefs().clear();
				return;
			case ScopePackage.SCOPE_BLOCK__REFS:
				getRefs().clear();
				return;
			case ScopePackage.SCOPE_BLOCK__OFFSET:
				setOffset(OFFSET_EDEFAULT);
				return;
			case ScopePackage.SCOPE_BLOCK__LENGTH:
				setLength(LENGTH_EDEFAULT);
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
			case ScopePackage.SCOPE_BLOCK__TYPE:
				return type != TYPE_EDEFAULT;
			case ScopePackage.SCOPE_BLOCK__PARENT_SCOPE_DEF:
				return getParentScopeDef() != null;
			case ScopePackage.SCOPE_BLOCK__CHILD_SCOPE_DEFS:
				return childScopeDefs != null && !childScopeDefs.isEmpty();
			case ScopePackage.SCOPE_BLOCK__DEFS:
				return defs != null && !defs.isEmpty();
			case ScopePackage.SCOPE_BLOCK__REFS:
				return refs != null && !refs.isEmpty();
			case ScopePackage.SCOPE_BLOCK__OFFSET:
				return offset != OFFSET_EDEFAULT;
			case ScopePackage.SCOPE_BLOCK__LENGTH:
				return length != LENGTH_EDEFAULT;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", offset: ");
		result.append(offset);
		result.append(", length: ");
		result.append(length);
		result.append(')');
		return result.toString();
	}

    public void doVisitChildren(IStructuredElementVisitor visitor) {
    	EList<LocalVariableDef> defs = getDefs();
    	for (LocalVariableDef localVariableDef : defs) {
    		localVariableDef.accept(visitor);
		}
    	EList<ScopeBlock> childScopes = getChildScopeDefs();
    	for (ScopeBlock scopeBlock : childScopes) {
    		scopeBlock.accept(visitor);
		}
    }

	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
            doVisitChildren(visitor);
        }
        visitor.postVisit(this);
	}

} //ScopeBlockImpl
