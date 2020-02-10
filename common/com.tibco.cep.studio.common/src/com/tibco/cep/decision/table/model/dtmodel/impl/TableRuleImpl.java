/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl#getCond <em>Cond</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl#getAct <em>Act</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl#isModified <em>Modified</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableRuleImpl extends MetadatableImpl implements TableRule {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCond() <em>Cond</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCond()
	 * @generated
	 * @ordered
	 */
	protected EList<TableRuleVariable> cond;

	/**
	 * The cached value of the '{@link #getAct() <em>Act</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAct()
	 * @generated
	 * @ordered
	 */
	protected EList<TableRuleVariable> act;

	/**
	 * The cached value of the '{@link #getCond() <em>Cond</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCond()
	 * @generated NOT
	 * @ordered
	 */
	protected EList<TableRuleVariable> condition;

	/**
	 * The cached value of the '{@link #getAct() <em>Act</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAct()
	 * @generated NOT
	 * @ordered
	 */
	protected EList<TableRuleVariable> action;

	/**
	 * The default value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MODIFIED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected boolean modified = MODIFIED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.TABLE_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TableRuleVariable> getCond() {
		if (cond == null) {
			cond = new EObjectContainmentEList<TableRuleVariable>(TableRuleVariable.class, this, DtmodelPackage.TABLE_RULE__COND);
		}
		return cond;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TableRuleVariable> getAct() {
		if (act == null) {
			act = new EObjectContainmentEList<TableRuleVariable>(TableRuleVariable.class, this, DtmodelPackage.TABLE_RULE__ACT);
		}
		return act;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public EList<TableRuleVariable> getCondition() {
		if (condition == null) {
			//condition = new EObjectContainmentEList<TableRuleVariable>(TableRuleVariable.class, this, DtmodelPackage.TABLE_RULE__CONDITION);
			condition = new EDataTypeEList<TableRuleVariable>(TableRuleVariable.class, this, DtmodelPackage.TABLE_RULE__CONDITION);
		}
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public EList<TableRuleVariable> getAction() {
		if (action == null) {
			//action = new EObjectContainmentEList<TableRuleVariable>(TableRuleVariable.class, this, DtmodelPackage.TABLE_RULE__ACTION);
			action = new EDataTypeEList<TableRuleVariable>(TableRuleVariable.class, this, DtmodelPackage.TABLE_RULE__ACTION);
		}
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModified(boolean newModified) {
		boolean oldModified = modified;
		modified = newModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE__MODIFIED, oldModified, modified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE__CONDITION:
				return ((InternalEList<?>)getCond()).basicRemove(otherEnd, msgs);
			case DtmodelPackage.TABLE_RULE__ACTION:
				return ((InternalEList<?>)getAct()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated 
	 */
	
	/*public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.METADATABLE__MD:
				return basicSetMetadata(null, msgs);
			case DtmodelPackage.TABLE_RULE__CONDITION:
				return ((InternalEList<?>)getCondition()).basicRemove(otherEnd, msgs);
			case DtmodelPackage.TABLE_RULE__ACTION:
				return ((InternalEList<?>)getAction()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}*/

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE__ID:
				return getId();
			case DtmodelPackage.TABLE_RULE__COMMENT:
				return getComment();
			case DtmodelPackage.METADATABLE__MD:
				return getMd();
			case DtmodelPackage.TABLE_RULE__CONDITION:
				return getCondition();
			case DtmodelPackage.TABLE_RULE__ACTION:
				return getAction();
			case DtmodelPackage.TABLE_RULE__MODIFIED:
				return isModified() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE__ID:
				setId((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE__COMMENT:
				setComment((String)newValue);
				return;
			case DtmodelPackage.METADATABLE__MD:
				setMd((MetaData)newValue);
				return;
			case DtmodelPackage.TABLE_RULE__CONDITION:
				getCondition().clear();
				getCondition().addAll((Collection<? extends TableRuleVariable>)newValue);
				return;
			case DtmodelPackage.TABLE_RULE__ACTION:
				getAction().clear();
				getAction().addAll((Collection<? extends TableRuleVariable>)newValue);
				return;
			case DtmodelPackage.TABLE_RULE__MODIFIED:
				setModified(((Boolean)newValue).booleanValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE__ID:
				setId(ID_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case DtmodelPackage.METADATABLE__MD:
				setMd((MetaData)null);
				return;
			case DtmodelPackage.TABLE_RULE__CONDITION:
				getCondition().clear();
				return;
			case DtmodelPackage.TABLE_RULE__ACTION:
				getAction().clear();
				return;
			case DtmodelPackage.TABLE_RULE__MODIFIED:
				setModified(MODIFIED_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DtmodelPackage.TABLE_RULE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case DtmodelPackage.METADATABLE__MD:
				return md != null;
			case DtmodelPackage.TABLE_RULE__CONDITION:
				return condition != null && !condition.isEmpty();
			case DtmodelPackage.TABLE_RULE__ACTION:
				return action != null && !action.isEmpty();
			case DtmodelPackage.TABLE_RULE__MODIFIED:
				return modified != MODIFIED_EDEFAULT;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", comment: ");
		result.append(comment);
		result.append(", modified: ");
		result.append(modified);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//We will treat 2 table rule variables with same id as equal
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TableRule)) {
			return false;
		}
		TableRule other = (TableRule)obj;
		if (!(this.id).equals(other.getId())) {
			return false;
		}
		return true;
	}
	
	

} //TableRuleImpl
