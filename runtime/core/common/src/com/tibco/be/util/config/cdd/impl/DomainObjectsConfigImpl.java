/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DomainObjectConfig;
import com.tibco.be.util.config.cdd.DomainObjectModeConfig;
import com.tibco.be.util.config.cdd.DomainObjectsConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.runtime.util.SystemProperty;
import java.util.Map;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Objects Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getDefaultMode <em>Default Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getCheckForVersion <em>Check For Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getEnableTracking <em>Enable Tracking</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getEvictOnUpdate <em>Evict On Update</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getCacheLimited <em>Cache Limited</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getPreLoadEnabled <em>Pre Load Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getPreLoadHandles <em>Pre Load Handles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getDomainObject <em>Domain Object</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectsConfigImpl#getConceptTTL <em>Concept TTL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DomainObjectsConfigImpl extends ArtifactConfigImpl implements DomainObjectsConfig {
	/**
	 * The default value of the '{@link #getDefaultMode() <em>Default Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultMode()
	 * @generated
	 * @ordered
	 */
	protected static final DomainObjectModeConfig DEFAULT_MODE_EDEFAULT = DomainObjectModeConfig.CACHE;

	/**
	 * The cached value of the '{@link #getDefaultMode() <em>Default Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultMode()
	 * @generated
	 * @ordered
	 */
	protected DomainObjectModeConfig defaultMode = DEFAULT_MODE_EDEFAULT;

	/**
	 * This is true if the Default Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean defaultModeESet;

	/**
	 * The cached value of the '{@link #getCheckForVersion() <em>Check For Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCheckForVersion()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig checkForVersion;

	/**
	 * The cached value of the '{@link #getConstant() <em>Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstant()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig constant;

	/**
	 * The cached value of the '{@link #getEnableTracking() <em>Enable Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnableTracking()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig enableTracking;

	/**
	 * The cached value of the '{@link #getEvictOnUpdate() <em>Evict On Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvictOnUpdate()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig evictOnUpdate;

	/**
	 * The cached value of the '{@link #getCacheLimited() <em>Cache Limited</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheLimited()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig cacheLimited;

	/**
	 * The cached value of the '{@link #getSubscribe() <em>Subscribe</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubscribe()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig subscribe;

	/**
	 * The cached value of the '{@link #getPreLoadEnabled() <em>Pre Load Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreLoadEnabled()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig preLoadEnabled;

	/**
	 * The cached value of the '{@link #getPreLoadFetchSize() <em>Pre Load Fetch Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreLoadFetchSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig preLoadFetchSize;

	/**
	 * The cached value of the '{@link #getPreLoadHandles() <em>Pre Load Handles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreLoadHandles()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig preLoadHandles;

	/**
	 * The cached value of the '{@link #getDomainObject() <em>Domain Object</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainObject()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainObjectConfig> domainObject;

	/**
	 * The cached value of the '{@link #getConceptTTL() <em>Concept TTL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptTTL()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig conceptTTL;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainObjectsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDomainObjectsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig getDefaultMode() {
		return defaultMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultMode(DomainObjectModeConfig newDefaultMode) {
		DomainObjectModeConfig oldDefaultMode = defaultMode;
		defaultMode = newDefaultMode == null ? DEFAULT_MODE_EDEFAULT : newDefaultMode;
		boolean oldDefaultModeESet = defaultModeESet;
		defaultModeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE, oldDefaultMode, defaultMode, !oldDefaultModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDefaultMode() {
		DomainObjectModeConfig oldDefaultMode = defaultMode;
		boolean oldDefaultModeESet = defaultModeESet;
		defaultMode = DEFAULT_MODE_EDEFAULT;
		defaultModeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CddPackage.DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE, oldDefaultMode, DEFAULT_MODE_EDEFAULT, oldDefaultModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDefaultMode() {
		return defaultModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckForVersion() {
		return checkForVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckForVersion(OverrideConfig newCheckForVersion, NotificationChain msgs) {
		OverrideConfig oldCheckForVersion = checkForVersion;
		checkForVersion = newCheckForVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION, oldCheckForVersion, newCheckForVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckForVersion(OverrideConfig newCheckForVersion) {
		if (newCheckForVersion != checkForVersion) {
			NotificationChain msgs = null;
			if (checkForVersion != null)
				msgs = ((InternalEObject)checkForVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION, null, msgs);
			if (newCheckForVersion != null)
				msgs = ((InternalEObject)newCheckForVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION, null, msgs);
			msgs = basicSetCheckForVersion(newCheckForVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION, newCheckForVersion, newCheckForVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConstant() {
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstant(OverrideConfig newConstant, NotificationChain msgs) {
		OverrideConfig oldConstant = constant;
		constant = newConstant;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT, oldConstant, newConstant);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstant(OverrideConfig newConstant) {
		if (newConstant != constant) {
			NotificationChain msgs = null;
			if (constant != null)
				msgs = ((InternalEObject)constant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT, null, msgs);
			if (newConstant != null)
				msgs = ((InternalEObject)newConstant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT, null, msgs);
			msgs = basicSetConstant(newConstant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT, newConstant, newConstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnableTracking() {
		return enableTracking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnableTracking(OverrideConfig newEnableTracking, NotificationChain msgs) {
		OverrideConfig oldEnableTracking = enableTracking;
		enableTracking = newEnableTracking;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING, oldEnableTracking, newEnableTracking);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableTracking(OverrideConfig newEnableTracking) {
		if (newEnableTracking != enableTracking) {
			NotificationChain msgs = null;
			if (enableTracking != null)
				msgs = ((InternalEObject)enableTracking).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING, null, msgs);
			if (newEnableTracking != null)
				msgs = ((InternalEObject)newEnableTracking).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING, null, msgs);
			msgs = basicSetEnableTracking(newEnableTracking, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING, newEnableTracking, newEnableTracking));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEvictOnUpdate() {
		return evictOnUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvictOnUpdate(OverrideConfig newEvictOnUpdate, NotificationChain msgs) {
		OverrideConfig oldEvictOnUpdate = evictOnUpdate;
		evictOnUpdate = newEvictOnUpdate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE, oldEvictOnUpdate, newEvictOnUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvictOnUpdate(OverrideConfig newEvictOnUpdate) {
		if (newEvictOnUpdate != evictOnUpdate) {
			NotificationChain msgs = null;
			if (evictOnUpdate != null)
				msgs = ((InternalEObject)evictOnUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE, null, msgs);
			if (newEvictOnUpdate != null)
				msgs = ((InternalEObject)newEvictOnUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE, null, msgs);
			msgs = basicSetEvictOnUpdate(newEvictOnUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE, newEvictOnUpdate, newEvictOnUpdate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheLimited() {
		return cacheLimited;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheLimited(OverrideConfig newCacheLimited, NotificationChain msgs) {
		OverrideConfig oldCacheLimited = cacheLimited;
		cacheLimited = newCacheLimited;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED, oldCacheLimited, newCacheLimited);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheLimited(OverrideConfig newCacheLimited) {
		if (newCacheLimited != cacheLimited) {
			NotificationChain msgs = null;
			if (cacheLimited != null)
				msgs = ((InternalEObject)cacheLimited).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED, null, msgs);
			if (newCacheLimited != null)
				msgs = ((InternalEObject)newCacheLimited).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED, null, msgs);
			msgs = basicSetCacheLimited(newCacheLimited, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED, newCacheLimited, newCacheLimited));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSubscribe() {
		return subscribe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubscribe(OverrideConfig newSubscribe, NotificationChain msgs) {
		OverrideConfig oldSubscribe = subscribe;
		subscribe = newSubscribe;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE, oldSubscribe, newSubscribe);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubscribe(OverrideConfig newSubscribe) {
		if (newSubscribe != subscribe) {
			NotificationChain msgs = null;
			if (subscribe != null)
				msgs = ((InternalEObject)subscribe).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE, null, msgs);
			if (newSubscribe != null)
				msgs = ((InternalEObject)newSubscribe).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE, null, msgs);
			msgs = basicSetSubscribe(newSubscribe, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE, newSubscribe, newSubscribe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadEnabled() {
		return preLoadEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadEnabled(OverrideConfig newPreLoadEnabled, NotificationChain msgs) {
		OverrideConfig oldPreLoadEnabled = preLoadEnabled;
		preLoadEnabled = newPreLoadEnabled;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED, oldPreLoadEnabled, newPreLoadEnabled);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadEnabled(OverrideConfig newPreLoadEnabled) {
		if (newPreLoadEnabled != preLoadEnabled) {
			NotificationChain msgs = null;
			if (preLoadEnabled != null)
				msgs = ((InternalEObject)preLoadEnabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED, null, msgs);
			if (newPreLoadEnabled != null)
				msgs = ((InternalEObject)newPreLoadEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED, null, msgs);
			msgs = basicSetPreLoadEnabled(newPreLoadEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED, newPreLoadEnabled, newPreLoadEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadFetchSize() {
		return preLoadFetchSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadFetchSize(OverrideConfig newPreLoadFetchSize, NotificationChain msgs) {
		OverrideConfig oldPreLoadFetchSize = preLoadFetchSize;
		preLoadFetchSize = newPreLoadFetchSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE, oldPreLoadFetchSize, newPreLoadFetchSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadFetchSize(OverrideConfig newPreLoadFetchSize) {
		if (newPreLoadFetchSize != preLoadFetchSize) {
			NotificationChain msgs = null;
			if (preLoadFetchSize != null)
				msgs = ((InternalEObject)preLoadFetchSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE, null, msgs);
			if (newPreLoadFetchSize != null)
				msgs = ((InternalEObject)newPreLoadFetchSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE, null, msgs);
			msgs = basicSetPreLoadFetchSize(newPreLoadFetchSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE, newPreLoadFetchSize, newPreLoadFetchSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadHandles() {
		return preLoadHandles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadHandles(OverrideConfig newPreLoadHandles, NotificationChain msgs) {
		OverrideConfig oldPreLoadHandles = preLoadHandles;
		preLoadHandles = newPreLoadHandles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES, oldPreLoadHandles, newPreLoadHandles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadHandles(OverrideConfig newPreLoadHandles) {
		if (newPreLoadHandles != preLoadHandles) {
			NotificationChain msgs = null;
			if (preLoadHandles != null)
				msgs = ((InternalEObject)preLoadHandles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES, null, msgs);
			if (newPreLoadHandles != null)
				msgs = ((InternalEObject)newPreLoadHandles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES, null, msgs);
			msgs = basicSetPreLoadHandles(newPreLoadHandles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES, newPreLoadHandles, newPreLoadHandles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DomainObjectConfig> getDomainObject() {
		if (domainObject == null) {
			domainObject = new EObjectContainmentEList<DomainObjectConfig>(DomainObjectConfig.class, this, CddPackage.DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT);
		}
		return domainObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConceptTTL() {
		return conceptTTL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConceptTTL(OverrideConfig newConceptTTL, NotificationChain msgs) {
		OverrideConfig oldConceptTTL = conceptTTL;
		conceptTTL = newConceptTTL;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL, oldConceptTTL, newConceptTTL);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConceptTTL(OverrideConfig newConceptTTL) {
		if (newConceptTTL != conceptTTL) {
			NotificationChain msgs = null;
			if (conceptTTL != null)
				msgs = ((InternalEObject)conceptTTL).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL, null, msgs);
			if (newConceptTTL != null)
				msgs = ((InternalEObject)newConceptTTL).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL, null, msgs);
			msgs = basicSetConceptTTL(newConceptTTL, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL, newConceptTTL, newConceptTTL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION:
				return basicSetCheckForVersion(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT:
				return basicSetConstant(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING:
				return basicSetEnableTracking(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE:
				return basicSetEvictOnUpdate(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED:
				return basicSetCacheLimited(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE:
				return basicSetSubscribe(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED:
				return basicSetPreLoadEnabled(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE:
				return basicSetPreLoadFetchSize(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES:
				return basicSetPreLoadHandles(null, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT:
				return ((InternalEList<?>)getDomainObject()).basicRemove(otherEnd, msgs);
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL:
				return basicSetConceptTTL(null, msgs);
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
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE:
				return getDefaultMode();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION:
				return getCheckForVersion();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT:
				return getConstant();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING:
				return getEnableTracking();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE:
				return getEvictOnUpdate();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED:
				return getCacheLimited();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE:
				return getSubscribe();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED:
				return getPreLoadEnabled();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE:
				return getPreLoadFetchSize();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES:
				return getPreLoadHandles();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT:
				return getDomainObject();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL:
				return getConceptTTL();
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
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE:
				setDefaultMode((DomainObjectModeConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION:
				setCheckForVersion((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT:
				setConstant((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING:
				setEnableTracking((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE:
				setEvictOnUpdate((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED:
				setCacheLimited((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE:
				setSubscribe((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED:
				setPreLoadEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE:
				setPreLoadFetchSize((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES:
				setPreLoadHandles((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT:
				getDomainObject().clear();
				getDomainObject().addAll((Collection<? extends DomainObjectConfig>)newValue);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL:
				setConceptTTL((OverrideConfig)newValue);
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
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE:
				unsetDefaultMode();
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION:
				setCheckForVersion((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT:
				setConstant((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING:
				setEnableTracking((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE:
				setEvictOnUpdate((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED:
				setCacheLimited((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE:
				setSubscribe((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED:
				setPreLoadEnabled((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE:
				setPreLoadFetchSize((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES:
				setPreLoadHandles((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT:
				getDomainObject().clear();
				return;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL:
				setConceptTTL((OverrideConfig)null);
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
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DEFAULT_MODE:
				return isSetDefaultMode();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CHECK_FOR_VERSION:
				return checkForVersion != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONSTANT:
				return constant != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__ENABLE_TRACKING:
				return enableTracking != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__EVICT_ON_UPDATE:
				return evictOnUpdate != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CACHE_LIMITED:
				return cacheLimited != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__SUBSCRIBE:
				return subscribe != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_ENABLED:
				return preLoadEnabled != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_FETCH_SIZE:
				return preLoadFetchSize != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__PRE_LOAD_HANDLES:
				return preLoadHandles != null;
			case CddPackage.DOMAIN_OBJECTS_CONFIG__DOMAIN_OBJECT:
				return domainObject != null && !domainObject.isEmpty();
			case CddPackage.DOMAIN_OBJECTS_CONFIG__CONCEPT_TTL:
				return conceptTTL != null;
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
		result.append(" (defaultMode: ");
		if (defaultModeESet) result.append(defaultMode); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

 /**
     * @generated NOT
     */
    private Boolean booleanValueOf(
            OverrideConfig overrideConfig,
            boolean valueIfNotSet) {
        return booleanValueOf(CddTools.getValueFromMixed(overrideConfig), valueIfNotSet);
    }

    /**
     * @generated NOT
     */
    private Boolean booleanValueOf(
            String stringValue,
            boolean valueIfNotSet) {
        if ((null == stringValue) || stringValue.trim().isEmpty() || "default".equals(stringValue.trim())) {
            return valueIfNotSet;
        } else {
            return Boolean.valueOf(stringValue);
        }
    }


    /**
     * @generated NOT
     */
    public Boolean getCacheLimited(
            String uri) {
        final boolean defaultValue = booleanValueOf(this.cacheLimited, true);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        return booleanValueOf(domainObjectConfig.getCacheLimited(), defaultValue);
    }

    /**
     * @generated NOT
     */
    public Boolean getCheckForVersion(
            String uri) {
        final boolean defaultValue = booleanValueOf(this.checkForVersion, true);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        return booleanValueOf(domainObjectConfig.getCheckForVersion(), defaultValue);
    }

    /**
     * @generated NOT
     */
    public Boolean getConstant(
            String uri) {
        final boolean defaultValue = booleanValueOf(this.constant, false);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        return booleanValueOf(domainObjectConfig.getConstant(), defaultValue);
    }

    /**
     * @generated NOT
     */
    public Boolean getEnableTracking(
            String uri) {
        final boolean defaultValue = booleanValueOf(this.enableTracking, true);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        return booleanValueOf(domainObjectConfig.getEnableTracking(), defaultValue);
    }

    /**
     * @generated NOT
     */
    public Boolean getEvictOnUpdate(
            String uri) {
        final boolean defaultValue = booleanValueOf(this.evictOnUpdate, true);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        return booleanValueOf(domainObjectConfig.getEvictOnUpdate(), defaultValue);
    }

    /**
     * @generated NOT
     */
    public String getMode(
            String uri) {
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return this.getDefaultMode().getName();
        }
        final String mode = domainObjectConfig.getMode().getName();
        if ((null == mode) || mode.trim().isEmpty()) {
            return this.getDefaultMode().getName();
        }
        return mode;
    }

    /**
     * @generated NOT
     */
    public Boolean getPreLoadEnabled(
            String uri) {
        String defaultSetting = CddTools.getValueFromMixed(this.getPreLoadEnabled());
        if ((null == defaultSetting)
                || defaultSetting.trim().isEmpty()) {
            defaultSetting = null;
        } else if (Boolean.FALSE.toString().equals(defaultSetting)
                || "none".equals(defaultSetting)) {
            defaultSetting = (this.domainObject.size() > 0) ? "include" : "none";
        } else {
            defaultSetting = (this.domainObject.size() > 0) ? "exclude" : "all";
        }

        final boolean defaultValue = (null == defaultSetting)
                || "exclude".equals(defaultSetting)
                || "all".equals(defaultSetting);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        final String override = (null == domainObjectConfig)
                ? null : CddTools.getValueFromMixed(domainObjectConfig.getPreLoadEnabled());
        return booleanValueOf(override, defaultValue);
    }

    /**
     * @generated NOT
     */
    public Long getPreLoadFetchSize(
            String uri) {
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        Long defaultValue;
        try {
            defaultValue = Long.valueOf(CddTools.getValueFromMixed(this.preLoadFetchSize));
        } catch (NumberFormatException e) {
            defaultValue = null;
        }
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        final String size = CddTools.getValueFromMixed(domainObjectConfig.getPreLoadFetchSize());
        if ((null == size) || size.trim().isEmpty() || "default".equals(size.trim())) {
            return defaultValue;
        }
        try {
            return Long.valueOf(size);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * @generated NOT
     */
    public Boolean getPreLoadHandles(
            String uri) {
        String defaultSetting = CddTools.getValueFromMixed(this.getPreLoadHandles());
        if ((null == defaultSetting)
                || defaultSetting.trim().isEmpty()) {
            defaultSetting = null;
        } else if (Boolean.FALSE.toString().equals(defaultSetting)
                || "none".equals(defaultSetting)) {
            defaultSetting = (this.domainObject.size() > 0) ? "include" : "none";
        } else {
            defaultSetting = (this.domainObject.size() > 0) ? "exclude" : "all";
        }

        final boolean defaultValue = (null == defaultSetting)
                || "exclude".equals(defaultSetting)
                || "all".equals(defaultSetting);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        final String override = (null == domainObjectConfig)
                ? null : CddTools.getValueFromMixed(domainObjectConfig.getPreLoadHandles());
        return booleanValueOf(override, defaultValue);
    }

    /**
     * @generated NOT
     */
    public String getPreprocessor(
            String uri) {
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return null;
        }
        return domainObjectConfig.getPreProcessor();
    }

    /**
     * @generated NOT
     */
    public Boolean getSubscribe(
            String uri) {
        final boolean defaultValue = booleanValueOf(this.subscribe, false);
        final DomainObjectConfig domainObjectConfig = (DomainObjectConfig)
                CddTools.findByUri(this.getDomainObject(), uri);
        if (null == domainObjectConfig) {
            return defaultValue;
        }
        return booleanValueOf(domainObjectConfig.getSubscribe(), defaultValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Map<Object, Object> toProperties() {
        Map<Object, Object> properties = super.toProperties();
        if (domainObject != null && domainObject.isEmpty() == false) {
            for (DomainObjectConfig singleDomainObject : domainObject) {
                properties.putAll(singleDomainObject.toProperties());
            }
        }
        properties.put(SystemProperty.CLUSTER_MODE.getPropertyName(),
                this.defaultMode.getLiteral());
        CddTools.addEntryFromMixed(properties,
                SystemProperty.CLUSTER_IS_CACHE_LIMITED.getPropertyName(),
                this.cacheLimited, true);
        return properties;
    }} //DomainObjectsConfigImpl
