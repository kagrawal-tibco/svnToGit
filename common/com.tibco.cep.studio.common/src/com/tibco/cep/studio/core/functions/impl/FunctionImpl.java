/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.functions.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.functions.Function;
import com.tibco.cep.studio.core.functions.FunctionsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#getHelpUrl <em>Help Url</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#isMapper <em>Mapper</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#isAsync <em>Async</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#isTimesensitive <em>Timesensitive</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl#isModify <em>Modify</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionImpl extends EObjectImpl implements Function {
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
	 * The default value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected String class_ = CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDesc() <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesc()
	 * @generated
	 * @ordered
	 */
	protected static final String DESC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDesc() <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesc()
	 * @generated
	 * @ordered
	 */
	protected String desc = DESC_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelpUrl() <em>Help Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelpUrl() <em>Help Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpUrl()
	 * @generated
	 * @ordered
	 */
	protected String helpUrl = HELP_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #isMapper() <em>Mapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMapper()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAPPER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMapper() <em>Mapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMapper()
	 * @generated
	 * @ordered
	 */
	protected boolean mapper = MAPPER_EDEFAULT;

	/**
	 * This is true if the Mapper attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean mapperESet;

	/**
	 * The default value of the '{@link #isAsync() <em>Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAsync()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ASYNC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAsync() <em>Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAsync()
	 * @generated
	 * @ordered
	 */
	protected boolean async = ASYNC_EDEFAULT;

	/**
	 * This is true if the Async attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean asyncESet;

	/**
	 * The default value of the '{@link #isTimesensitive() <em>Timesensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTimesensitive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TIMESENSITIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTimesensitive() <em>Timesensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTimesensitive()
	 * @generated
	 * @ordered
	 */
	protected boolean timesensitive = TIMESENSITIVE_EDEFAULT;

	/**
	 * This is true if the Timesensitive attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean timesensitiveESet;

	/**
	 * The default value of the '{@link #isModify() <em>Modify</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModify()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MODIFY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isModify() <em>Modify</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModify()
	 * @generated
	 * @ordered
	 */
	protected boolean modify = MODIFY_EDEFAULT;

	/**
	 * This is true if the Modify attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean modifyESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FunctionsPackage.Literals.FUNCTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClass_() {
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(String newClass) {
		String oldClass = class_;
		class_ = newClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__CLASS, oldClass, class_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesc(String newDesc) {
		String oldDesc = desc;
		desc = newDesc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__DESC, oldDesc, desc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHelpUrl() {
		return helpUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHelpUrl(String newHelpUrl) {
		String oldHelpUrl = helpUrl;
		helpUrl = newHelpUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__HELP_URL, oldHelpUrl, helpUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMapper() {
		return mapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapper(boolean newMapper) {
		boolean oldMapper = mapper;
		mapper = newMapper;
		boolean oldMapperESet = mapperESet;
		mapperESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__MAPPER, oldMapper, mapper, !oldMapperESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMapper() {
		boolean oldMapper = mapper;
		boolean oldMapperESet = mapperESet;
		mapper = MAPPER_EDEFAULT;
		mapperESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FunctionsPackage.FUNCTION__MAPPER, oldMapper, MAPPER_EDEFAULT, oldMapperESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMapper() {
		return mapperESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsync(boolean newAsync) {
		boolean oldAsync = async;
		async = newAsync;
		boolean oldAsyncESet = asyncESet;
		asyncESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__ASYNC, oldAsync, async, !oldAsyncESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAsync() {
		boolean oldAsync = async;
		boolean oldAsyncESet = asyncESet;
		async = ASYNC_EDEFAULT;
		asyncESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FunctionsPackage.FUNCTION__ASYNC, oldAsync, ASYNC_EDEFAULT, oldAsyncESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAsync() {
		return asyncESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTimesensitive() {
		return timesensitive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimesensitive(boolean newTimesensitive) {
		boolean oldTimesensitive = timesensitive;
		timesensitive = newTimesensitive;
		boolean oldTimesensitiveESet = timesensitiveESet;
		timesensitiveESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__TIMESENSITIVE, oldTimesensitive, timesensitive, !oldTimesensitiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTimesensitive() {
		boolean oldTimesensitive = timesensitive;
		boolean oldTimesensitiveESet = timesensitiveESet;
		timesensitive = TIMESENSITIVE_EDEFAULT;
		timesensitiveESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FunctionsPackage.FUNCTION__TIMESENSITIVE, oldTimesensitive, TIMESENSITIVE_EDEFAULT, oldTimesensitiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTimesensitive() {
		return timesensitiveESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isModify() {
		return modify;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModify(boolean newModify) {
		boolean oldModify = modify;
		modify = newModify;
		boolean oldModifyESet = modifyESet;
		modifyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.FUNCTION__MODIFY, oldModify, modify, !oldModifyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetModify() {
		boolean oldModify = modify;
		boolean oldModifyESet = modifyESet;
		modify = MODIFY_EDEFAULT;
		modifyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, FunctionsPackage.FUNCTION__MODIFY, oldModify, MODIFY_EDEFAULT, oldModifyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetModify() {
		return modifyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FunctionsPackage.FUNCTION__NAME:
				return getName();
			case FunctionsPackage.FUNCTION__CLASS:
				return getClass_();
			case FunctionsPackage.FUNCTION__DESC:
				return getDesc();
			case FunctionsPackage.FUNCTION__HELP_URL:
				return getHelpUrl();
			case FunctionsPackage.FUNCTION__MAPPER:
				return isMapper() ? Boolean.TRUE : Boolean.FALSE;
			case FunctionsPackage.FUNCTION__ASYNC:
				return isAsync() ? Boolean.TRUE : Boolean.FALSE;
			case FunctionsPackage.FUNCTION__TIMESENSITIVE:
				return isTimesensitive() ? Boolean.TRUE : Boolean.FALSE;
			case FunctionsPackage.FUNCTION__MODIFY:
				return isModify() ? Boolean.TRUE : Boolean.FALSE;
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
			case FunctionsPackage.FUNCTION__NAME:
				setName((String)newValue);
				return;
			case FunctionsPackage.FUNCTION__CLASS:
				setClass((String)newValue);
				return;
			case FunctionsPackage.FUNCTION__DESC:
				setDesc((String)newValue);
				return;
			case FunctionsPackage.FUNCTION__HELP_URL:
				setHelpUrl((String)newValue);
				return;
			case FunctionsPackage.FUNCTION__MAPPER:
				setMapper(((Boolean)newValue).booleanValue());
				return;
			case FunctionsPackage.FUNCTION__ASYNC:
				setAsync(((Boolean)newValue).booleanValue());
				return;
			case FunctionsPackage.FUNCTION__TIMESENSITIVE:
				setTimesensitive(((Boolean)newValue).booleanValue());
				return;
			case FunctionsPackage.FUNCTION__MODIFY:
				setModify(((Boolean)newValue).booleanValue());
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
			case FunctionsPackage.FUNCTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FunctionsPackage.FUNCTION__CLASS:
				setClass(CLASS_EDEFAULT);
				return;
			case FunctionsPackage.FUNCTION__DESC:
				setDesc(DESC_EDEFAULT);
				return;
			case FunctionsPackage.FUNCTION__HELP_URL:
				setHelpUrl(HELP_URL_EDEFAULT);
				return;
			case FunctionsPackage.FUNCTION__MAPPER:
				unsetMapper();
				return;
			case FunctionsPackage.FUNCTION__ASYNC:
				unsetAsync();
				return;
			case FunctionsPackage.FUNCTION__TIMESENSITIVE:
				unsetTimesensitive();
				return;
			case FunctionsPackage.FUNCTION__MODIFY:
				unsetModify();
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
			case FunctionsPackage.FUNCTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FunctionsPackage.FUNCTION__CLASS:
				return CLASS_EDEFAULT == null ? class_ != null : !CLASS_EDEFAULT.equals(class_);
			case FunctionsPackage.FUNCTION__DESC:
				return DESC_EDEFAULT == null ? desc != null : !DESC_EDEFAULT.equals(desc);
			case FunctionsPackage.FUNCTION__HELP_URL:
				return HELP_URL_EDEFAULT == null ? helpUrl != null : !HELP_URL_EDEFAULT.equals(helpUrl);
			case FunctionsPackage.FUNCTION__MAPPER:
				return isSetMapper();
			case FunctionsPackage.FUNCTION__ASYNC:
				return isSetAsync();
			case FunctionsPackage.FUNCTION__TIMESENSITIVE:
				return isSetTimesensitive();
			case FunctionsPackage.FUNCTION__MODIFY:
				return isSetModify();
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
		result.append(", class: ");
		result.append(class_);
		result.append(", desc: ");
		result.append(desc);
		result.append(", helpUrl: ");
		result.append(helpUrl);
		result.append(", mapper: ");
		if (mapperESet) result.append(mapper); else result.append("<unset>");
		result.append(", async: ");
		if (asyncESet) result.append(async); else result.append("<unset>");
		result.append(", timesensitive: ");
		if (timesensitiveESet) result.append(timesensitive); else result.append("<unset>");
		result.append(", modify: ");
		if (modifyESet) result.append(modify); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FunctionImpl
