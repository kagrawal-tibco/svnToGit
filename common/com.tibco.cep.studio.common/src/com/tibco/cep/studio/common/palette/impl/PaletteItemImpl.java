/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.JavaType;
import com.tibco.cep.studio.common.palette.ModelType;
import com.tibco.cep.studio.common.palette.PaletteItem;
import com.tibco.cep.studio.common.palette.PalettePackage;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getEmfItemType <em>Emf Item Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getJavaItemType <em>Java Item Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getModelItemType <em>Model Item Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getTitleRef <em>Title Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getTooltipRef <em>Tooltip Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getIconRef <em>Icon Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getTooltip <em>Tooltip</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getAttachedResource <em>Attached Resource</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getHelpContent <em>Help Content</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getColor <em>Color</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#isInternal <em>Internal</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl#isVisible <em>Visible</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PaletteItemImpl extends EObjectImpl implements PaletteItem {
	/**
	 * The cached value of the '{@link #getEmfItemType() <em>Emf Item Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfItemType()
	 * @generated
	 * @ordered
	 */
	protected EmfType emfItemType;

	/**
	 * The cached value of the '{@link #getJavaItemType() <em>Java Item Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaItemType()
	 * @generated
	 * @ordered
	 */
	protected JavaType javaItemType;

	/**
	 * The cached value of the '{@link #getModelItemType() <em>Model Item Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelItemType()
	 * @generated
	 * @ordered
	 */
	protected ModelType modelItemType;

	/**
	 * The default value of the '{@link #getTitleRef() <em>Title Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitleRef()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_REF_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getTitleRef() <em>Title Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitleRef()
	 * @generated
	 * @ordered
	 */
	protected String titleRef = TITLE_REF_EDEFAULT;

	/**
	 * This is true if the Title Ref attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean titleRefESet;

	/**
	 * The default value of the '{@link #getTooltipRef() <em>Tooltip Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltipRef()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLTIP_REF_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getTooltipRef() <em>Tooltip Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltipRef()
	 * @generated
	 * @ordered
	 */
	protected String tooltipRef = TOOLTIP_REF_EDEFAULT;

	/**
	 * This is true if the Tooltip Ref attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean tooltipRefESet;

	/**
	 * The default value of the '{@link #getIconRef() <em>Icon Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconRef()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_REF_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getIconRef() <em>Icon Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconRef()
	 * @generated
	 * @ordered
	 */
	protected String iconRef = ICON_REF_EDEFAULT;

	/**
	 * This is true if the Icon Ref attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean iconRefESet;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * This is true if the Title attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean titleESet;

	/**
	 * The default value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLTIP_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 */
	protected String tooltip = TOOLTIP_EDEFAULT;

	/**
	 * This is true if the Tooltip attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean tooltipESet;

	/**
	 * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected String icon = ICON_EDEFAULT;

	/**
	 * This is true if the Icon attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean iconESet;

	/**
	 * The default value of the '{@link #getAttachedResource() <em>Attached Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttachedResource()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTACHED_RESOURCE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getAttachedResource() <em>Attached Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttachedResource()
	 * @generated
	 * @ordered
	 */
	protected String attachedResource = ATTACHED_RESOURCE_EDEFAULT;

	/**
	 * This is true if the Attached Resource attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean attachedResourceESet;

	/**
	 * The cached value of the '{@link #getHelpContent() <em>Help Content</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpContent()
	 * @generated
	 * @ordered
	 */
	protected EList<Help> helpContent;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected String color = COLOR_EDEFAULT;

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
	 * The default value of the '{@link #isInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInternal()
	 * @generated
	 * @ordered
	 */
	protected boolean internal = INTERNAL_EDEFAULT;

	/**
	 * This is true if the Internal attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean internalESet;

	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * This is true if the Visible attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean visibleESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.PALETTE_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfType getEmfItemType() {
		return emfItemType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEmfItemType(EmfType newEmfItemType, NotificationChain msgs) {
		EmfType oldEmfItemType = emfItemType;
		emfItemType = newEmfItemType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE, oldEmfItemType, newEmfItemType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmfItemType(EmfType newEmfItemType) {
		if (newEmfItemType != emfItemType) {
			NotificationChain msgs = null;
			if (emfItemType != null)
				msgs = ((InternalEObject)emfItemType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE, null, msgs);
			if (newEmfItemType != null)
				msgs = ((InternalEObject)newEmfItemType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE, null, msgs);
			msgs = basicSetEmfItemType(newEmfItemType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE, newEmfItemType, newEmfItemType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaType getJavaItemType() {
		return javaItemType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJavaItemType(JavaType newJavaItemType, NotificationChain msgs) {
		JavaType oldJavaItemType = javaItemType;
		javaItemType = newJavaItemType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE, oldJavaItemType, newJavaItemType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaItemType(JavaType newJavaItemType) {
		if (newJavaItemType != javaItemType) {
			NotificationChain msgs = null;
			if (javaItemType != null)
				msgs = ((InternalEObject)javaItemType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE, null, msgs);
			if (newJavaItemType != null)
				msgs = ((InternalEObject)newJavaItemType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE, null, msgs);
			msgs = basicSetJavaItemType(newJavaItemType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE, newJavaItemType, newJavaItemType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelType getModelItemType() {
		return modelItemType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModelItemType(ModelType newModelItemType, NotificationChain msgs) {
		ModelType oldModelItemType = modelItemType;
		modelItemType = newModelItemType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE, oldModelItemType, newModelItemType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelItemType(ModelType newModelItemType) {
		if (newModelItemType != modelItemType) {
			NotificationChain msgs = null;
			if (modelItemType != null)
				msgs = ((InternalEObject)modelItemType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE, null, msgs);
			if (newModelItemType != null)
				msgs = ((InternalEObject)newModelItemType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE, null, msgs);
			msgs = basicSetModelItemType(newModelItemType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE, newModelItemType, newModelItemType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitleRef() {
		return titleRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitleRef(String newTitleRef) {
		String oldTitleRef = titleRef;
		titleRef = newTitleRef;
		boolean oldTitleRefESet = titleRefESet;
		titleRefESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__TITLE_REF, oldTitleRef, titleRef, !oldTitleRefESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTitleRef() {
		String oldTitleRef = titleRef;
		boolean oldTitleRefESet = titleRefESet;
		titleRef = TITLE_REF_EDEFAULT;
		titleRefESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__TITLE_REF, oldTitleRef, TITLE_REF_EDEFAULT, oldTitleRefESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTitleRef() {
		return titleRefESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTooltipRef() {
		return tooltipRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTooltipRef(String newTooltipRef) {
		String oldTooltipRef = tooltipRef;
		tooltipRef = newTooltipRef;
		boolean oldTooltipRefESet = tooltipRefESet;
		tooltipRefESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__TOOLTIP_REF, oldTooltipRef, tooltipRef, !oldTooltipRefESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTooltipRef() {
		String oldTooltipRef = tooltipRef;
		boolean oldTooltipRefESet = tooltipRefESet;
		tooltipRef = TOOLTIP_REF_EDEFAULT;
		tooltipRefESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__TOOLTIP_REF, oldTooltipRef, TOOLTIP_REF_EDEFAULT, oldTooltipRefESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTooltipRef() {
		return tooltipRefESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIconRef() {
		return iconRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIconRef(String newIconRef) {
		String oldIconRef = iconRef;
		iconRef = newIconRef;
		boolean oldIconRefESet = iconRefESet;
		iconRefESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__ICON_REF, oldIconRef, iconRef, !oldIconRefESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIconRef() {
		String oldIconRef = iconRef;
		boolean oldIconRefESet = iconRefESet;
		iconRef = ICON_REF_EDEFAULT;
		iconRefESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__ICON_REF, oldIconRef, ICON_REF_EDEFAULT, oldIconRefESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIconRef() {
		return iconRefESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		boolean oldTitleESet = titleESet;
		titleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__TITLE, oldTitle, title, !oldTitleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTitle() {
		String oldTitle = title;
		boolean oldTitleESet = titleESet;
		title = TITLE_EDEFAULT;
		titleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__TITLE, oldTitle, TITLE_EDEFAULT, oldTitleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTitle() {
		return titleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTooltip(String newTooltip) {
		String oldTooltip = tooltip;
		tooltip = newTooltip;
		boolean oldTooltipESet = tooltipESet;
		tooltipESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__TOOLTIP, oldTooltip, tooltip, !oldTooltipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTooltip() {
		String oldTooltip = tooltip;
		boolean oldTooltipESet = tooltipESet;
		tooltip = TOOLTIP_EDEFAULT;
		tooltipESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__TOOLTIP, oldTooltip, TOOLTIP_EDEFAULT, oldTooltipESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTooltip() {
		return tooltipESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIcon(String newIcon) {
		String oldIcon = icon;
		icon = newIcon;
		boolean oldIconESet = iconESet;
		iconESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__ICON, oldIcon, icon, !oldIconESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIcon() {
		String oldIcon = icon;
		boolean oldIconESet = iconESet;
		icon = ICON_EDEFAULT;
		iconESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__ICON, oldIcon, ICON_EDEFAULT, oldIconESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIcon() {
		return iconESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAttachedResource() {
		return attachedResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttachedResource(String newAttachedResource) {
		String oldAttachedResource = attachedResource;
		attachedResource = newAttachedResource;
		boolean oldAttachedResourceESet = attachedResourceESet;
		attachedResourceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE, oldAttachedResource, attachedResource, !oldAttachedResourceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAttachedResource() {
		String oldAttachedResource = attachedResource;
		boolean oldAttachedResourceESet = attachedResourceESet;
		attachedResource = ATTACHED_RESOURCE_EDEFAULT;
		attachedResourceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE, oldAttachedResource, ATTACHED_RESOURCE_EDEFAULT, oldAttachedResourceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAttachedResource() {
		return attachedResourceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Help> getHelpContent() {
		if (helpContent == null) {
			helpContent = new EObjectContainmentEList<Help>(Help.class, this, PalettePackage.PALETTE_ITEM__HELP_CONTENT);
		}
		return helpContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(String newColor) {
		String oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__COLOR, oldColor, color));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInternal() {
		return internal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInternal(boolean newInternal) {
		boolean oldInternal = internal;
		internal = newInternal;
		boolean oldInternalESet = internalESet;
		internalESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__INTERNAL, oldInternal, internal, !oldInternalESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInternal() {
		boolean oldInternal = internal;
		boolean oldInternalESet = internalESet;
		internal = INTERNAL_EDEFAULT;
		internalESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__INTERNAL, oldInternal, INTERNAL_EDEFAULT, oldInternalESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInternal() {
		return internalESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisible(boolean newVisible) {
		boolean oldVisible = visible;
		visible = newVisible;
		boolean oldVisibleESet = visibleESet;
		visibleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ITEM__VISIBLE, oldVisible, visible, !oldVisibleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVisible() {
		boolean oldVisible = visible;
		boolean oldVisibleESet = visibleESet;
		visible = VISIBLE_EDEFAULT;
		visibleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.PALETTE_ITEM__VISIBLE, oldVisible, VISIBLE_EDEFAULT, oldVisibleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVisible() {
		return visibleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE:
				return basicSetEmfItemType(null, msgs);
			case PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE:
				return basicSetJavaItemType(null, msgs);
			case PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE:
				return basicSetModelItemType(null, msgs);
			case PalettePackage.PALETTE_ITEM__HELP_CONTENT:
				return ((InternalEList<?>)getHelpContent()).basicRemove(otherEnd, msgs);
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
			case PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE:
				return getEmfItemType();
			case PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE:
				return getJavaItemType();
			case PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE:
				return getModelItemType();
			case PalettePackage.PALETTE_ITEM__TITLE_REF:
				return getTitleRef();
			case PalettePackage.PALETTE_ITEM__TOOLTIP_REF:
				return getTooltipRef();
			case PalettePackage.PALETTE_ITEM__ICON_REF:
				return getIconRef();
			case PalettePackage.PALETTE_ITEM__TITLE:
				return getTitle();
			case PalettePackage.PALETTE_ITEM__TOOLTIP:
				return getTooltip();
			case PalettePackage.PALETTE_ITEM__ICON:
				return getIcon();
			case PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE:
				return getAttachedResource();
			case PalettePackage.PALETTE_ITEM__HELP_CONTENT:
				return getHelpContent();
			case PalettePackage.PALETTE_ITEM__COLOR:
				return getColor();
			case PalettePackage.PALETTE_ITEM__ID:
				return getId();
			case PalettePackage.PALETTE_ITEM__INTERNAL:
				return isInternal();
			case PalettePackage.PALETTE_ITEM__VISIBLE:
				return isVisible();
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
			case PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE:
				setEmfItemType((EmfType)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE:
				setJavaItemType((JavaType)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE:
				setModelItemType((ModelType)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__TITLE_REF:
				setTitleRef((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__TOOLTIP_REF:
				setTooltipRef((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__ICON_REF:
				setIconRef((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__TITLE:
				setTitle((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__ICON:
				setIcon((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE:
				setAttachedResource((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__HELP_CONTENT:
				getHelpContent().clear();
				getHelpContent().addAll((Collection<? extends Help>)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__COLOR:
				setColor((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__ID:
				setId((String)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__INTERNAL:
				setInternal((Boolean)newValue);
				return;
			case PalettePackage.PALETTE_ITEM__VISIBLE:
				setVisible((Boolean)newValue);
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
			case PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE:
				setEmfItemType((EmfType)null);
				return;
			case PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE:
				setJavaItemType((JavaType)null);
				return;
			case PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE:
				setModelItemType((ModelType)null);
				return;
			case PalettePackage.PALETTE_ITEM__TITLE_REF:
				unsetTitleRef();
				return;
			case PalettePackage.PALETTE_ITEM__TOOLTIP_REF:
				unsetTooltipRef();
				return;
			case PalettePackage.PALETTE_ITEM__ICON_REF:
				unsetIconRef();
				return;
			case PalettePackage.PALETTE_ITEM__TITLE:
				unsetTitle();
				return;
			case PalettePackage.PALETTE_ITEM__TOOLTIP:
				unsetTooltip();
				return;
			case PalettePackage.PALETTE_ITEM__ICON:
				unsetIcon();
				return;
			case PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE:
				unsetAttachedResource();
				return;
			case PalettePackage.PALETTE_ITEM__HELP_CONTENT:
				getHelpContent().clear();
				return;
			case PalettePackage.PALETTE_ITEM__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case PalettePackage.PALETTE_ITEM__ID:
				setId(ID_EDEFAULT);
				return;
			case PalettePackage.PALETTE_ITEM__INTERNAL:
				unsetInternal();
				return;
			case PalettePackage.PALETTE_ITEM__VISIBLE:
				unsetVisible();
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
			case PalettePackage.PALETTE_ITEM__EMF_ITEM_TYPE:
				return emfItemType != null;
			case PalettePackage.PALETTE_ITEM__JAVA_ITEM_TYPE:
				return javaItemType != null;
			case PalettePackage.PALETTE_ITEM__MODEL_ITEM_TYPE:
				return modelItemType != null;
			case PalettePackage.PALETTE_ITEM__TITLE_REF:
				return isSetTitleRef();
			case PalettePackage.PALETTE_ITEM__TOOLTIP_REF:
				return isSetTooltipRef();
			case PalettePackage.PALETTE_ITEM__ICON_REF:
				return isSetIconRef();
			case PalettePackage.PALETTE_ITEM__TITLE:
				return isSetTitle();
			case PalettePackage.PALETTE_ITEM__TOOLTIP:
				return isSetTooltip();
			case PalettePackage.PALETTE_ITEM__ICON:
				return isSetIcon();
			case PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE:
				return isSetAttachedResource();
			case PalettePackage.PALETTE_ITEM__HELP_CONTENT:
				return helpContent != null && !helpContent.isEmpty();
			case PalettePackage.PALETTE_ITEM__COLOR:
				return COLOR_EDEFAULT == null ? color != null : !COLOR_EDEFAULT.equals(color);
			case PalettePackage.PALETTE_ITEM__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PalettePackage.PALETTE_ITEM__INTERNAL:
				return isSetInternal();
			case PalettePackage.PALETTE_ITEM__VISIBLE:
				return isSetVisible();
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
		result.append(" (titleRef: ");
		if (titleRefESet) result.append(titleRef); else result.append("<unset>");
		result.append(", tooltipRef: ");
		if (tooltipRefESet) result.append(tooltipRef); else result.append("<unset>");
		result.append(", iconRef: ");
		if (iconRefESet) result.append(iconRef); else result.append("<unset>");
		result.append(", title: ");
		if (titleESet) result.append(title); else result.append("<unset>");
		result.append(", tooltip: ");
		if (tooltipESet) result.append(tooltip); else result.append("<unset>");
		result.append(", icon: ");
		if (iconESet) result.append(icon); else result.append("<unset>");
		result.append(", attachedResource: ");
		if (attachedResourceESet) result.append(attachedResource); else result.append("<unset>");
		result.append(", color: ");
		result.append(color);
		result.append(", id: ");
		result.append(id);
		result.append(", internal: ");
		if (internalESet) result.append(internal); else result.append("<unset>");
		result.append(", visible: ");
		if (visibleESet) result.append(visible); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PaletteItemImpl
