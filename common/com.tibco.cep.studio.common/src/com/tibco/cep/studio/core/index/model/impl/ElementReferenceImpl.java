/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.IndexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#isAttRef <em>Att Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#isTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#getQualifier <em>Qualifier</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#isMethod <em>Method</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl#getBinding <em>Binding</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementReferenceImpl extends EObjectImpl implements ElementReference {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isAttRef() <em>Att Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAttRef()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ATT_REF_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAttRef() <em>Att Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAttRef()
	 * @generated
	 * @ordered
	 */
	protected boolean attRef = ATT_REF_EDEFAULT;

	/**
	 * The default value of the '{@link #isTypeRef() <em>Type Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTypeRef()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TYPE_REF_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTypeRef() <em>Type Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTypeRef()
	 * @generated
	 * @ordered
	 */
	protected boolean typeRef = TYPE_REF_EDEFAULT;

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
	 * The cached value of the '{@link #getQualifier() <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifier()
	 * @generated
	 * @ordered
	 */
	protected ElementReference qualifier;

	/**
	 * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected boolean array = ARRAY_EDEFAULT;

	/**
	 * The default value of the '{@link #isMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMethod()
	 * @generated
	 * @ordered
	 */
	protected static final boolean METHOD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMethod()
	 * @generated
	 * @ordered
	 */
	protected boolean method = METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected static final Object BINDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected Object binding = BINDING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.ELEMENT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAttRef() {
		return attRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttRef(boolean newAttRef) {
		boolean oldAttRef = attRef;
		attRef = newAttRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__ATT_REF, oldAttRef, attRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTypeRef() {
		return typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeRef(boolean newTypeRef) {
		boolean oldTypeRef = typeRef;
		typeRef = newTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__TYPE_REF, oldTypeRef, typeRef));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__OFFSET, oldOffset, offset));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementReference getQualifier() {
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQualifier(ElementReference newQualifier, NotificationChain msgs) {
		ElementReference oldQualifier = qualifier;
		qualifier = newQualifier;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__QUALIFIER, oldQualifier, newQualifier);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQualifier(ElementReference newQualifier) {
		if (newQualifier != qualifier) {
			NotificationChain msgs = null;
			if (qualifier != null)
				msgs = ((InternalEObject)qualifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.ELEMENT_REFERENCE__QUALIFIER, null, msgs);
			if (newQualifier != null)
				msgs = ((InternalEObject)newQualifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.ELEMENT_REFERENCE__QUALIFIER, null, msgs);
			msgs = basicSetQualifier(newQualifier, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__QUALIFIER, newQualifier, newQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArray() {
		return array;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArray(boolean newArray) {
		boolean oldArray = array;
		array = newArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__ARRAY, oldArray, array));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMethod() {
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(boolean newMethod) {
		boolean oldMethod = method;
		method = newMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__METHOD, oldMethod, method));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getBinding() {
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinding(Object newBinding) {
		Object oldBinding = binding;
		binding = newBinding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ELEMENT_REFERENCE__BINDING, oldBinding, binding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.ELEMENT_REFERENCE__QUALIFIER:
				return basicSetQualifier(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.ELEMENT_REFERENCE__NAME:
				return getName();
			case IndexPackage.ELEMENT_REFERENCE__ATT_REF:
				return isAttRef();
			case IndexPackage.ELEMENT_REFERENCE__TYPE_REF:
				return isTypeRef();
			case IndexPackage.ELEMENT_REFERENCE__OFFSET:
				return getOffset();
			case IndexPackage.ELEMENT_REFERENCE__LENGTH:
				return getLength();
			case IndexPackage.ELEMENT_REFERENCE__QUALIFIER:
				return getQualifier();
			case IndexPackage.ELEMENT_REFERENCE__ARRAY:
				return isArray();
			case IndexPackage.ELEMENT_REFERENCE__METHOD:
				return isMethod();
			case IndexPackage.ELEMENT_REFERENCE__BINDING:
				return getBinding();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IndexPackage.ELEMENT_REFERENCE__NAME:
				setName((String)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__ATT_REF:
				setAttRef((Boolean)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__TYPE_REF:
				setTypeRef((Boolean)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__OFFSET:
				setOffset((Integer)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__LENGTH:
				setLength((Integer)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__QUALIFIER:
				setQualifier((ElementReference)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__ARRAY:
				setArray((Boolean)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__METHOD:
				setMethod((Boolean)newValue);
				return;
			case IndexPackage.ELEMENT_REFERENCE__BINDING:
				setBinding(newValue);
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
			case IndexPackage.ELEMENT_REFERENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__ATT_REF:
				setAttRef(ATT_REF_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__TYPE_REF:
				setTypeRef(TYPE_REF_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__OFFSET:
				setOffset(OFFSET_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__QUALIFIER:
				setQualifier((ElementReference)null);
				return;
			case IndexPackage.ELEMENT_REFERENCE__ARRAY:
				setArray(ARRAY_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
			case IndexPackage.ELEMENT_REFERENCE__BINDING:
				setBinding(BINDING_EDEFAULT);
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
			case IndexPackage.ELEMENT_REFERENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IndexPackage.ELEMENT_REFERENCE__ATT_REF:
				return attRef != ATT_REF_EDEFAULT;
			case IndexPackage.ELEMENT_REFERENCE__TYPE_REF:
				return typeRef != TYPE_REF_EDEFAULT;
			case IndexPackage.ELEMENT_REFERENCE__OFFSET:
				return offset != OFFSET_EDEFAULT;
			case IndexPackage.ELEMENT_REFERENCE__LENGTH:
				return length != LENGTH_EDEFAULT;
			case IndexPackage.ELEMENT_REFERENCE__QUALIFIER:
				return qualifier != null;
			case IndexPackage.ELEMENT_REFERENCE__ARRAY:
				return array != ARRAY_EDEFAULT;
			case IndexPackage.ELEMENT_REFERENCE__METHOD:
				return method != METHOD_EDEFAULT;
			case IndexPackage.ELEMENT_REFERENCE__BINDING:
				return BINDING_EDEFAULT == null ? binding != null : !BINDING_EDEFAULT.equals(binding);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", attRef: ");
		result.append(attRef);
		result.append(", typeRef: ");
		result.append(typeRef);
		result.append(", offset: ");
		result.append(offset);
		result.append(", length: ");
		result.append(length);
		result.append(", array: ");
		result.append(array);
		result.append(", method: ");
		result.append(method);
		result.append(", binding: ");
		result.append(binding);
		result.append(')');
		return result.toString();
	}

} //ElementReferenceImpl
