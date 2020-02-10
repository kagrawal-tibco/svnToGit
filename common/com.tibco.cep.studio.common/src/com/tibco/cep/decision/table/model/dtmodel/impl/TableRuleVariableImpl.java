/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Rule Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#isModified <em>Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getMetatData <em>Metat Data</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getColId <em>Col Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getExpr <em>Expr</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getColumnName <em>Column Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl#getDisplayValue <em>Display Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableRuleVariableImpl extends EObjectImpl implements TableRuleVariable {
	/**
	 * The default value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated NOT
	 * @ordered
	 */
	private ColumnType trvType ;
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
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMetatData() <em>Metat Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetatData()
	 * @generated
	 * @ordered
	 */
	protected MetaData metatData;

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
	 * The default value of the '{@link #getColId() <em>Col Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColId()
	 * @generated
	 * @ordered
	 */
	protected static final String COL_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getColId() <em>Col Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColId()
	 * @generated
	 * @ordered
	 */
	protected String colId = COL_ID_EDEFAULT;
	/**
	 * The default value of the '{@link #getExpr() <em>Expr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpr()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPR_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getExpr() <em>Expr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpr()
	 * @generated
	 * @ordered
	 */
	protected String expr = EXPR_EDEFAULT;
	/**
	 * The default value of the '{@link #getColumnName() <em>Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getColumnName() <em>Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnName()
	 * @generated
	 * @ordered
	 */
	protected String columnName = COLUMN_NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayValue() <em>Display Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_VALUE_EDEFAULT = "";
	/**
	 * The cached value of the '{@link #getDisplayValue() <em>Display Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayValue()
	 * @generated
	 * @ordered
	 */
	protected String displayValue = DISPLAY_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TableRuleVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.TABLE_RULE_VARIABLE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__MODIFIED, oldModified, modified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaData getMetatData() {
		return metatData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetatData(MetaData newMetatData, NotificationChain msgs) {
		MetaData oldMetatData = metatData;
		metatData = newMetatData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA, oldMetatData, newMetatData);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetatData(MetaData newMetatData) {
		if (newMetatData != metatData) {
			NotificationChain msgs = null;
			if (metatData != null)
				msgs = ((InternalEObject)metatData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA, null, msgs);
			if (newMetatData != null)
				msgs = ((InternalEObject)newMetatData).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA, null, msgs);
			msgs = basicSetMetatData(newMetatData, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA, newMetatData, newMetatData));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getColId() {
		return colId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColId(String newColId) {
		String oldColId = colId;
		colId = newColId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__COL_ID, oldColId, colId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExpr() {
		return expr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpr(String newExpr) {
		String oldExpr = expr;
		expr = newExpr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__EXPR, oldExpr, expr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColumnName(String newColumnName) {
		String oldColumnName = columnName;
		columnName = newColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__COLUMN_NAME, oldColumnName, columnName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayValue(String newDisplayValue) {
		String oldDisplayValue = displayValue;
		displayValue = newDisplayValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__DISPLAY_VALUE, oldDisplayValue, displayValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE_RULE_VARIABLE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA:
				return basicSetMetatData(null, msgs);
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
			case DtmodelPackage.TABLE_RULE_VARIABLE__MODIFIED:
				return isModified();
			case DtmodelPackage.TABLE_RULE_VARIABLE__COMMENT:
				return getComment();
			case DtmodelPackage.TABLE_RULE_VARIABLE__ENABLED:
				return isEnabled();
			case DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA:
				return getMetatData();
			case DtmodelPackage.TABLE_RULE_VARIABLE__ID:
				return getId();
			case DtmodelPackage.TABLE_RULE_VARIABLE__COL_ID:
				return getColId();
			case DtmodelPackage.TABLE_RULE_VARIABLE__EXPR:
				return getExpr();
			case DtmodelPackage.TABLE_RULE_VARIABLE__COLUMN_NAME:
				return getColumnName();
			case DtmodelPackage.TABLE_RULE_VARIABLE__PATH:
				return getPath();
			case DtmodelPackage.TABLE_RULE_VARIABLE__DISPLAY_VALUE:
				return getDisplayValue();
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
			case DtmodelPackage.TABLE_RULE_VARIABLE__MODIFIED:
				setModified((Boolean)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COMMENT:
				setComment((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA:
				setMetatData((MetaData)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__ID:
				setId((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COL_ID:
				setColId((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__EXPR:
				setExpr((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COLUMN_NAME:
				setColumnName((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__PATH:
				setPath((String)newValue);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__DISPLAY_VALUE:
				setDisplayValue((String)newValue);
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
			case DtmodelPackage.TABLE_RULE_VARIABLE__MODIFIED:
				setModified(MODIFIED_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA:
				setMetatData((MetaData)null);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__ID:
				setId(ID_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COL_ID:
				setColId(COL_ID_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__EXPR:
				setExpr(EXPR_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COLUMN_NAME:
				setColumnName(COLUMN_NAME_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case DtmodelPackage.TABLE_RULE_VARIABLE__DISPLAY_VALUE:
				setDisplayValue(DISPLAY_VALUE_EDEFAULT);
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
			case DtmodelPackage.TABLE_RULE_VARIABLE__MODIFIED:
				return modified != MODIFIED_EDEFAULT;
			case DtmodelPackage.TABLE_RULE_VARIABLE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case DtmodelPackage.TABLE_RULE_VARIABLE__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case DtmodelPackage.TABLE_RULE_VARIABLE__METAT_DATA:
				return metatData != null;
			case DtmodelPackage.TABLE_RULE_VARIABLE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DtmodelPackage.TABLE_RULE_VARIABLE__COL_ID:
				return COL_ID_EDEFAULT == null ? colId != null : !COL_ID_EDEFAULT.equals(colId);
			case DtmodelPackage.TABLE_RULE_VARIABLE__EXPR:
				return EXPR_EDEFAULT == null ? expr != null : !EXPR_EDEFAULT.equals(expr);
			case DtmodelPackage.TABLE_RULE_VARIABLE__COLUMN_NAME:
				return COLUMN_NAME_EDEFAULT == null ? columnName != null : !COLUMN_NAME_EDEFAULT.equals(columnName);
			case DtmodelPackage.TABLE_RULE_VARIABLE__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case DtmodelPackage.TABLE_RULE_VARIABLE__DISPLAY_VALUE:
				return DISPLAY_VALUE_EDEFAULT == null ? displayValue != null : !DISPLAY_VALUE_EDEFAULT.equals(displayValue);
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
		result.append(" (modified: ");
		result.append(modified);
		result.append(", comment: ");
		result.append(comment);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", id: ");
		result.append(id);
		result.append(", colId: ");
		result.append(colId);
		result.append(", expr: ");
		result.append(expr);
		result.append(", columnName: ");
		result.append(columnName);
		result.append(", path: ");
		result.append(path);
		result.append(", displayValue: ");
		result.append(displayValue);
		result.append(')');
		return result.toString();
	}
	/**
	 * This method checks equality of two DecisionTableRuleVariable.
	 * two DecisionTableRuleVariable are equal if body is interpreted same .
	 * example : 1) age<18&&>9
	 *           2) age < 18 && > 9
	 *           
	 *           both condition are same but because of white spaces body is different .
	 *           to check equality in this case alias ,path ,operator and value need to be check 
	 *           separately for equality ....value might have white spaces as shown in example below so replace all 
	 *           white spaces with "" then compare ...
	 * @generated not
	 */
	public void setTRVType(ColumnType trvType){
		this.trvType = trvType;
	}
	/**
	 * This method checks equality of two DecisionTableRuleVariable.
	 * two DecisionTableRuleVariable are equal if body is interpreted same .
	 * example : 1) age<18&&>9
	 *           2) age < 18 && > 9
	 *           
	 *           both condition are same but because of white spaces body is different .
	 *           to check equality in this case alias ,path ,operator and value need to be check 
	 *           separately for equality ....value might have white spaces as shown in example below so replace all 
	 *           white spaces with "" then compare ...
	 * @generated not
	 */
	
	@Override
	public boolean equals(Object object) {
		// TODO Auto-generated method stub

		if (this == object) {
			return true;

		}

		if (object == null || (this.getClass() != object.getClass())) {
			return false;
		}

		if (object != null) {
			TableRuleVariable trv = (TableRuleVariable) object;
			//Expression comparedExpr = trv.getExpression();
			String comparedExpr = trv.getExpr();
			if (expr == null && comparedExpr == null) {
				return true;
			}
			if (trv.isEnabled() != this.isEnabled()) {
				return false;
			}
			if (trv.getComment() != null && !trv.getComment().equals(getComment())) {
				return false;
			}
			if (expr != null && comparedExpr != null) {
				//String alias1 = expression.getAlias();
				//alias1 = alias1 != null ? alias1.trim() : alias1;
				//String alias2 = comparedExpr.getAlias();
				//alias2 = alias2 != null ? alias2.trim() : alias2;
				//String path1 = expression.getPath();
				//path1 = path1 != null ? path1.trim() : path1;
				//String path2 = comparedExpr.getPath();
				//path2 = path2 != null ? path2.trim() : path2;
				String otherId = trv.getColId();
				if ((checkEquality(colId, otherId))) {
					/*if(!checkEquality(this.getId(), trv.getId())) {
						return false;
					}*/
					//String body1 = expression.getBodies().get(0);
					//String body2 = comparedExpr.getBodies().get(0);
					// Split each body ";" as delimiter
					String[] splitBody1 = expr.split(";");
					String[] splitBody2 = comparedExpr.split(";");
					if (splitBody1.length != splitBody2.length)
						return false;
					// iterate through each split body
					// get propertyType Map
					Set<String> treeSet1 = new TreeSet<String>();
					Set<String> treeSet2 = new TreeSet<String>();
					for (String str : splitBody1) {						
						// all blank spaces from value ,rather trim it .
						String cellValue = null;
						if (trvType != null &&
								((ColumnType.CUSTOM_CONDITION.equals(trvType)) || (ColumnType.CUSTOM_ACTION.equals(trvType)))) {
							// it's a custom condition or action
							cellValue = str.trim();
						} else {
							// remove all blank spaces
							cellValue = removeNotRequiredSpacesFromCellValue(str);
						}
						treeSet1.add(cellValue);
					}
					for (String str : splitBody2) {

						String cellValue = null;
						if (trvType != null &&
								((ColumnType.CUSTOM_CONDITION.equals(trvType)) || (ColumnType.CUSTOM_ACTION.equals(trvType)))) {
							// it's a custom condition or action
							cellValue = str.trim();
						} else {
							// remove unnecessary blank spaces
							cellValue = removeNotRequiredSpacesFromCellValue(str);

						}
						treeSet2.add(cellValue);

					}
					if (treeSet1.equals(treeSet2)) {
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}

			}
		}

		return false;
	}
	
	private String removeNotRequiredSpacesFromCellValue(String str1){
		String cellValue = null;
		if (str1.indexOf("&&")!= -1){
			String[] splitValue = str1.split("&&");
			if (splitValue != null && splitValue.length != 0){
				StringBuffer sb = new StringBuffer("");
				int count = 1;
				for (String st : splitValue){
					String operator = getOperator(st);
					String str11 = null;
					if (operator.startsWith("=")) {
						str11 = getActualValue(operator, st);
					} else {
						str11 = operator + getActualValue(operator, st);
					}					
					sb.append(str11);
					if (count < splitValue.length){
						sb.append("&&");
					}
					count ++;
				}
				cellValue = sb.toString();
			}
			else {
				cellValue = str1.trim();
			}
			
		}
		else if (str1.indexOf("||") != -1){
			String[] splitValue = str1.split("\\|\\|");
			if (splitValue != null && splitValue.length != 0){
				StringBuffer sb = new StringBuffer("");
				int count = 1;
				for (String st : splitValue){
					String operator = getOperator(st);
					String str11 = null;
					if (operator.startsWith("=")) {
						str11 = getActualValue(operator, st);
					} else {
						str11 = operator + getActualValue(operator, st);
					}
					sb.append(str11);					
					if (count < splitValue.length){
						sb.append("||");
					}
					count ++;
				}
				cellValue = sb.toString();
			}
			else {
				cellValue = str1.trim();
			}
			
		}
		else {
			String operator = getOperator(str1);
			if (operator.startsWith("=")) {
				cellValue = getActualValue(operator, str1);
			} else {
				cellValue = operator + getActualValue(operator, str1);
			}
			
		}
		
		return cellValue;
	}
	/*
	 * @Override public boolean equals(Object object) { // TODO Auto-generated
	 * method stub
	 * 
	 * if (this == object){ return true; }
	 * 
	 * if (object == null || (this.getClass() != object.getClass())){ return
	 * false ; }
	 * 
	 * if (object != null){ Expression comparedExpr =
	 * ((TableRuleVariable)object).getExpression(); if (expression ==
	 * comparedExpr){ return true; } if (expression != null && comparedExpr !=
	 * null){
	 * 
	 * String alias1 = expression.getAlias(); alias1 = alias1 != null ?
	 * alias1.trim():alias1; String alias2 = comparedExpr.getAlias(); alias2 =
	 * alias2 != null ? alias2.trim():alias2; String path1 =
	 * expression.getPath(); path1 = path1 != null ? path1.trim():path1; String
	 * path2 = comparedExpr.getPath(); path2 = path2 != null ?
	 * path2.trim():path2;
	 * 
	 * String body1 = expression.getBodies().get(0); String body2 =
	 * comparedExpr.getBodies().get(0);
	 * 
	 * if (checkBodies(body1, body2) && (checkEquality(alias1, alias2)) &&
	 * (checkEquality(path1, path2))){ return true; } } }
	 * 
	 * return false; }
	 */
	private boolean checkBodies(String body1, String body2) {
		String value1 = null;
		String value2 = null;
		String operator1 = null;
		String operator2 = null;
		
		if (body1 == body2 && body1 == null) {
			return true;
		}
		
		if (body1 == null || body2 == null) {
			return false;
		}
		
		if (body1.indexOf("&&") != -1 && body2.indexOf("&&") != -1) {
			String[] body1nodes = body1.split("&&");
			String[] body2nodes = body2.split("&&");
			return checkBodies(body1nodes[0], body2nodes[0]) && checkBodies(body1nodes[1], body2nodes[1]);
		} else if (body1.indexOf("||") != -1 && body2.indexOf("||") != -1) {
			String[] body1nodes = body1.split("\\|\\|");
			String[] body2nodes = body2.split("\\|\\|");
			return checkBodies(body1nodes[0], body2nodes[0]) && checkBodies(body1nodes[1], body2nodes[1]);
		}
		
		if (body1.indexOf("&&") != -1 || body2.indexOf("&&") != -1) {
			return false; // one body has a '&&' and the other one doesn't, return false
		}

		if (body1.indexOf("||") != -1 || body2.indexOf("||") != -1) {
			return false; // one body has a '||' and the other one doesn't, return false
		}
		
		if (body1 != null){
			body1 = body1.trim();
			operator1 = getOperator(body1);
			if (operator1 != null){
				value1 = getActualValue(operator1, body1);						
			}
			else value1 = body1;
			value1 = value1 != null ? value1.trim():value1;			
		}				
		
		if (body2 != null){
			body2 = body2.trim();
			operator2 = getOperator(body2);
			if (operator2 != null){
				value2 = getActualValue(operator2, body2);						
			}
			else value2 = body2;
			value2 = value2 != null ? value2.trim():value2;			
		}							

		return checkEquality(value1, value2) && checkEquality(operator1, operator2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	
	private String getOperator(String cellValue){
		final  String[] OPERATORS = new String[]{"==",">=", "<=", "<>","!=","=",">", "<"};	
		for (String op:OPERATORS){
			/*
			if (cellValue.indexOf(op) != -1){
				return op;
			}
			*/
			if (cellValue.trim().startsWith(op)){
				return op;
			}
			
		}
		/*
		if ((cellValue.indexOf("(" ) != -1 )&& (cellValue.indexOf(")") != -1)){
			
			return null;
		}
		*/
    	return "=";
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	
    public static String getActualValue(String operator, String cellValue) {
		// if (operator != null){
    	String trimmedCellValue = cellValue.trim();
		if (("=".equals(operator))
				&& (!(trimmedCellValue.startsWith(operator)))) {
			return trimmedCellValue;
		}
		// return
		// cellValue.substring(cellValue.indexOf(operator)+operator.length()).trim();
		return trimmedCellValue.substring(operator.length()).trim();
		// }

		// else
		// return cellValue ;

	}
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	
	private boolean checkEquality(String str1,String str2){
		
		if (str1 == null && str2 == null){
			return true;
		}
		else if ((str1 != null && str2 == null) || (str2 != null && str1 == null) ){
			return false;
		}
		else {
             return str1.equals(str2);			
		}		
	}
	


	/**
	 * It returns same has code for same DecisionTableRuleVariable and different for different
	 * @generated not
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hashCode = 7 ;
		//int aliasHashCode = 0;
		//int pathHashCode = 0;	
		int idHashCode = 0;
		int valueHashCode = 0;		
		if (expr != null ){
			//String alias = expression.getAlias();
			//alias = alias != null ? alias.trim():alias;			
			//String path = expression.getPath();
			//path = path != null ? path.trim():path;
			//String body = expression.getBodies().size() > 0 ? expression.getBodies().get(0) : null;
			String body = expr;
			if (body != null){
				body = body.trim();	
				String[] splitBody = body.split(";");
				if (splitBody != null){
					for (String str : splitBody){
						String cellValue = null;
						if (trvType != null &&
								((ColumnType.CUSTOM_CONDITION.equals(trvType)) || (ColumnType.CUSTOM_ACTION.equals(trvType)))){
							// it's a custom condition or action 
							cellValue = str.trim();
						} else {
							// remove unnecessary blank spaces
							// getOperator			
							cellValue = removeNotRequiredSpacesFromCellValue(str);
							
						}							
						valueHashCode += cellValue != null ? cellValue.hashCode(): 0 ;
								
					}
				}
				
			}				
			//aliasHashCode = alias != null ? alias.hashCode():0;
			//pathHashCode = path != null ? path.hashCode():0;		
			idHashCode = colId != null ? colId.hashCode():0;
			
			
		}		
		
		//hashCode = 31*hashCode + aliasHashCode + pathHashCode + valueHashCode ;	
		hashCode = 31*hashCode + idHashCode + valueHashCode ;
		return hashCode;
	}



} //TableRuleVariableImpl
