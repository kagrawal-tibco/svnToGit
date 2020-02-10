/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decisionproject.ontology.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Column</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#isSubstitution <em>Substitution</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getPropertyPath <em>Property Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getColumnType <em>Column Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getPropertyRef <em>Property Ref</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#isArrayProperty <em>Array Property</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl#getDefaultCellText <em>Default Cell Text</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColumnImpl extends EObjectImpl implements Column {
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
	 * The default value of the '{@link #isSubstitution() <em>Substitution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubstitution()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUBSTITUTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSubstitution() <em>Substitution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubstitution()
	 * @generated
	 * @ordered
	 */
	protected boolean substitution = SUBSTITUTION_EDEFAULT;

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
	 * The default value of the '{@link #getPropertyPath() <em>Property Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyPath() <em>Property Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyPath()
	 * @generated
	 * @ordered
	 */
	protected String propertyPath = PROPERTY_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyType() <em>Property Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyType()
	 * @generated
	 * @ordered
	 */
	protected static final int PROPERTY_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPropertyType() <em>Property Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyType()
	 * @generated
	 * @ordered
	 */
	protected int propertyType = PROPERTY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getColumnType() <em>Column Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnType()
	 * @generated
	 * @ordered
	 */
	protected static final ColumnType COLUMN_TYPE_EDEFAULT = ColumnType.UNDEFINED;

	/**
	 * The cached value of the '{@link #getColumnType() <em>Column Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnType()
	 * @generated
	 * @ordered
	 */
	protected ColumnType columnType = COLUMN_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPropertyRef() <em>Property Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyRef()
	 * @generated
	 * @ordered
	 */
	protected Property propertyRef;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #isArrayProperty() <em>Array Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayProperty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_PROPERTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArrayProperty() <em>Array Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayProperty()
	 * @generated
	 * @ordered
	 */
	protected boolean arrayProperty = ARRAY_PROPERTY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultCellText() <em>Default Cell Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCellText()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_CELL_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultCellText() <em>Default Cell Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCellText()
	 * @generated
	 * @ordered
	 */
	protected String defaultCellText = DEFAULT_CELL_TEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ColumnImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.COLUMN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSubstitution() {
		return substitution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubstitution(boolean newSubstitution) {
		boolean oldSubstitution = substitution;
		substitution = newSubstitution;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__SUBSTITUTION, oldSubstitution, substitution));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyPath() {
		return propertyPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyPath(String newPropertyPath) {
		String oldPropertyPath = propertyPath;
		propertyPath = newPropertyPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__PROPERTY_PATH, oldPropertyPath, propertyPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPropertyType() {
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyType(int newPropertyType) {
		int oldPropertyType = propertyType;
		propertyType = newPropertyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__PROPERTY_TYPE, oldPropertyType, propertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColumnType getColumnType() {
		return columnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColumnType(ColumnType newColumnType) {
		ColumnType oldColumnType = columnType;
		columnType = newColumnType == null ? COLUMN_TYPE_EDEFAULT : newColumnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__COLUMN_TYPE, oldColumnType, columnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Property getPropertyRef() {
		//checkPropertyRef is not generated, the rest is.
		checkPropertyRef();
		if (propertyRef != null && propertyRef.eIsProxy()) {
			InternalEObject oldPropertyRef = (InternalEObject)propertyRef;
			propertyRef = (Property)eResolveProxy(oldPropertyRef);
			if (propertyRef != oldPropertyRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DtmodelPackage.COLUMN__PROPERTY_REF, oldPropertyRef, propertyRef));
			}
		}
		return propertyRef;
	}
	/**
	 * @generated NOT
	 */
	private void checkPropertyRef() {
		/*if(!getColumnType().isCustom() && basicGetPropertyRef() == null){
			String propPath = getPropertyPath();
			if(propPath != null && propPath.length() > 0) {
				Ontology o = DecisionProjectLoader.getInstance().getDecisionProject().getOntology();
				AbstractResource ar = DecisionProjectUtil.findResource(o, getPropertyPath());
				if(ar instanceof Property) {
					setPropertyRef((Property)ar);
				}
			}
		}	*/	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property basicGetPropertyRef() {
		return propertyRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyRef(Property newPropertyRef) {
		Property oldPropertyRef = propertyRef;
		propertyRef = newPropertyRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__PROPERTY_REF, oldPropertyRef, propertyRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArrayProperty() {
		return arrayProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrayProperty(boolean newArrayProperty) {
		boolean oldArrayProperty = arrayProperty;
		arrayProperty = newArrayProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__ARRAY_PROPERTY, oldArrayProperty, arrayProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultCellText() {
		return defaultCellText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultCellText(String newDefaultCellText) {
		String oldDefaultCellText = defaultCellText;
		defaultCellText = newDefaultCellText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.COLUMN__DEFAULT_CELL_TEXT, oldDefaultCellText, defaultCellText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DtmodelPackage.COLUMN__ID:
				return getId();
			case DtmodelPackage.COLUMN__SUBSTITUTION:
				return isSubstitution();
			case DtmodelPackage.COLUMN__NAME:
				return getName();
			case DtmodelPackage.COLUMN__PROPERTY_PATH:
				return getPropertyPath();
			case DtmodelPackage.COLUMN__PROPERTY_TYPE:
				return getPropertyType();
			case DtmodelPackage.COLUMN__COLUMN_TYPE:
				return getColumnType();
			case DtmodelPackage.COLUMN__PROPERTY_REF:
				if (resolve) return getPropertyRef();
				return basicGetPropertyRef();
			case DtmodelPackage.COLUMN__ALIAS:
				return getAlias();
			case DtmodelPackage.COLUMN__ARRAY_PROPERTY:
				return isArrayProperty();
			case DtmodelPackage.COLUMN__DEFAULT_CELL_TEXT:
				return getDefaultCellText();
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
			case DtmodelPackage.COLUMN__ID:
				setId((String)newValue);
				return;
			case DtmodelPackage.COLUMN__SUBSTITUTION:
				setSubstitution((Boolean)newValue);
				return;
			case DtmodelPackage.COLUMN__NAME:
				setName((String)newValue);
				return;
			case DtmodelPackage.COLUMN__PROPERTY_PATH:
				setPropertyPath((String)newValue);
				return;
			case DtmodelPackage.COLUMN__PROPERTY_TYPE:
				setPropertyType((Integer)newValue);
				return;
			case DtmodelPackage.COLUMN__COLUMN_TYPE:
				setColumnType((ColumnType)newValue);
				return;
			case DtmodelPackage.COLUMN__PROPERTY_REF:
				setPropertyRef((Property)newValue);
				return;
			case DtmodelPackage.COLUMN__ALIAS:
				setAlias((String)newValue);
				return;
			case DtmodelPackage.COLUMN__ARRAY_PROPERTY:
				setArrayProperty((Boolean)newValue);
				return;
			case DtmodelPackage.COLUMN__DEFAULT_CELL_TEXT:
				setDefaultCellText((String)newValue);
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
			case DtmodelPackage.COLUMN__ID:
				setId(ID_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__SUBSTITUTION:
				setSubstitution(SUBSTITUTION_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__PROPERTY_PATH:
				setPropertyPath(PROPERTY_PATH_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__PROPERTY_TYPE:
				setPropertyType(PROPERTY_TYPE_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__COLUMN_TYPE:
				setColumnType(COLUMN_TYPE_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__PROPERTY_REF:
				setPropertyRef((Property)null);
				return;
			case DtmodelPackage.COLUMN__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__ARRAY_PROPERTY:
				setArrayProperty(ARRAY_PROPERTY_EDEFAULT);
				return;
			case DtmodelPackage.COLUMN__DEFAULT_CELL_TEXT:
				setDefaultCellText(DEFAULT_CELL_TEXT_EDEFAULT);
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
			case DtmodelPackage.COLUMN__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DtmodelPackage.COLUMN__SUBSTITUTION:
				return substitution != SUBSTITUTION_EDEFAULT;
			case DtmodelPackage.COLUMN__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DtmodelPackage.COLUMN__PROPERTY_PATH:
				return PROPERTY_PATH_EDEFAULT == null ? propertyPath != null : !PROPERTY_PATH_EDEFAULT.equals(propertyPath);
			case DtmodelPackage.COLUMN__PROPERTY_TYPE:
				return propertyType != PROPERTY_TYPE_EDEFAULT;
			case DtmodelPackage.COLUMN__COLUMN_TYPE:
				return columnType != COLUMN_TYPE_EDEFAULT;
			case DtmodelPackage.COLUMN__PROPERTY_REF:
				return propertyRef != null;
			case DtmodelPackage.COLUMN__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case DtmodelPackage.COLUMN__ARRAY_PROPERTY:
				return arrayProperty != ARRAY_PROPERTY_EDEFAULT;
			case DtmodelPackage.COLUMN__DEFAULT_CELL_TEXT:
				return DEFAULT_CELL_TEXT_EDEFAULT == null ? defaultCellText != null : !DEFAULT_CELL_TEXT_EDEFAULT.equals(defaultCellText);
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * if two columns are identical
	 * for general condition and action : identical if title and column type (CUSTOM/NON-CUSTOM) are identical
	 * for custom condition and action : if refers to same memory location 
	 * @generated NOT
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (! (obj instanceof Column)) return false;
		if ( this == obj ) return true;
		Column otherColumn = (Column)obj;
		String otherName = otherColumn.getName();
		String otherId = otherColumn.getId();
		if (this.id != null && otherId != null){
			if (this.id.equals(otherId)){
				return true;
			} else {
				return false;
			}
		}
		ColumnType otherColumnType = otherColumn.getColumnType();
		if (((ColumnType.CONDITION.equals(columnType)) || (ColumnType.ACTION.equals(columnType))) && 
				 (columnType.equals(otherColumnType)) ){
			if (name != null){
				if (name.equals(otherName)){
					return true;
				} else {
					return false;
				}
			}
			else {
				 if (otherName == null) {
					 return true;
				 }
				 else {
					 return false;
				 }
			}
			
		}
		return false;
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
		result.append(", substitution: ");
		result.append(substitution);
		result.append(", name: ");
		result.append(name);
		result.append(", propertyPath: ");
		result.append(propertyPath);
		result.append(", propertyType: ");
		result.append(propertyType);
		result.append(", columnType: ");
		result.append(columnType);
		result.append(", alias: ");
		result.append(alias);
		result.append(", arrayProperty: ");
		result.append(arrayProperty);
		result.append(", defaultCellText: ");
		result.append(defaultCellText);
		result.append(')');
		return result.toString();
	}

} //ColumnImpl
