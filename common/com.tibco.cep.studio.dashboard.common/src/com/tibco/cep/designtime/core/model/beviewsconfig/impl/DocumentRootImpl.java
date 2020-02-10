/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.AssetPage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.BluePrintComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot;
import com.tibco.cep.designtime.core.model.beviewsconfig.Footer;
import com.tibco.cep.designtime.core.model.beviewsconfig.Header;
import com.tibco.cep.designtime.core.model.beviewsconfig.Login;
import com.tibco.cep.designtime.core.model.beviewsconfig.PageSelectorComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.Panel;
import com.tibco.cep.designtime.core.model.beviewsconfig.QueryManagerComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.RelatedAssetsComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.SearchPage;
import com.tibco.cep.designtime.core.model.beviewsconfig.SearchViewComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.View;
import com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getAlertComponentElem <em>Alert Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getAssetPageElem <em>Asset Page Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBluePrintComponentElem <em>Blue Print Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getChartComponentColorSetElem <em>Chart Component Color Set Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getChartComponentElem <em>Chart Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getClassifierComponentElem <em>Classifier Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getDashboardPageElem <em>Dashboard Page Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsConfigElem <em>Be Views Config Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsConfigVersionElem <em>Be Views Config Version Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsElementElem <em>Be Views Element Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsFooterElem <em>Be Views Footer Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsHeaderElem <em>Be Views Header Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsLoginElem <em>Be Views Login Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getBeViewsSkinElem <em>Be Views Skin Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getPageSelectorComponentElem <em>Page Selector Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getPanelElem <em>Panel Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getStateMachineComponentElem <em>State Machine Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getQueryManagerComponentElem <em>Query Manager Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getRelatedAssetsComponentElem <em>Related Assets Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getSearchPageElem <em>Search Page Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getSearchViewComponentElem <em>Search View Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getTextComponentColorSetElem <em>Text Component Color Set Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl#getTextComponentElem <em>Text Component Elem</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, BEViewsConfigurationPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, BEViewsConfigurationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, BEViewsConfigurationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertComponent getAlertComponentElem() {
		return (AlertComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_AlertComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlertComponentElem(AlertComponent newAlertComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_AlertComponentElem(), newAlertComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlertComponentElem(AlertComponent newAlertComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_AlertComponentElem(), newAlertComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssetPage getAssetPageElem() {
		return (AssetPage)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_AssetPageElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssetPageElem(AssetPage newAssetPageElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_AssetPageElem(), newAssetPageElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssetPageElem(AssetPage newAssetPageElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_AssetPageElem(), newAssetPageElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BluePrintComponent getBluePrintComponentElem() {
		return (BluePrintComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BluePrintComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBluePrintComponentElem(BluePrintComponent newBluePrintComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BluePrintComponentElem(), newBluePrintComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBluePrintComponentElem(BluePrintComponent newBluePrintComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BluePrintComponentElem(), newBluePrintComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentColorSet getChartComponentColorSetElem() {
		return (ComponentColorSet)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ChartComponentColorSetElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChartComponentColorSetElem(ComponentColorSet newChartComponentColorSetElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ChartComponentColorSetElem(), newChartComponentColorSetElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChartComponentColorSetElem(ComponentColorSet newChartComponentColorSetElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ChartComponentColorSetElem(), newChartComponentColorSetElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartComponent getChartComponentElem() {
		return (ChartComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ChartComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChartComponentElem(ChartComponent newChartComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ChartComponentElem(), newChartComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChartComponentElem(ChartComponent newChartComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ChartComponentElem(), newChartComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierComponent getClassifierComponentElem() {
		return (ClassifierComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ClassifierComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClassifierComponentElem(ClassifierComponent newClassifierComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ClassifierComponentElem(), newClassifierComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassifierComponentElem(ClassifierComponent newClassifierComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_ClassifierComponentElem(), newClassifierComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DashboardPage getDashboardPageElem() {
		return (DashboardPage)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_DashboardPageElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDashboardPageElem(DashboardPage newDashboardPageElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_DashboardPageElem(), newDashboardPageElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDashboardPageElem(DashboardPage newDashboardPageElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_DashboardPageElem(), newDashboardPageElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View getBeViewsConfigElem() {
		return (View)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsConfigElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsConfigElem(View newBeViewsConfigElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsConfigElem(), newBeViewsConfigElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsConfigElem(View newBeViewsConfigElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsConfigElem(), newBeViewsConfigElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewVersion getBeViewsConfigVersionElem() {
		return (ViewVersion)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsConfigVersionElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsConfigVersionElem(ViewVersion newBeViewsConfigVersionElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsConfigVersionElem(), newBeViewsConfigVersionElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsConfigVersionElem(ViewVersion newBeViewsConfigVersionElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsConfigVersionElem(), newBeViewsConfigVersionElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsElement getBeViewsElementElem() {
		return (BEViewsElement)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsElementElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsElementElem(BEViewsElement newBeViewsElementElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsElementElem(), newBeViewsElementElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsElementElem(BEViewsElement newBeViewsElementElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsElementElem(), newBeViewsElementElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Footer getBeViewsFooterElem() {
		return (Footer)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsFooterElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsFooterElem(Footer newBeViewsFooterElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsFooterElem(), newBeViewsFooterElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsFooterElem(Footer newBeViewsFooterElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsFooterElem(), newBeViewsFooterElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Header getBeViewsHeaderElem() {
		return (Header)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsHeaderElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsHeaderElem(Header newBeViewsHeaderElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsHeaderElem(), newBeViewsHeaderElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsHeaderElem(Header newBeViewsHeaderElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsHeaderElem(), newBeViewsHeaderElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Login getBeViewsLoginElem() {
		return (Login)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsLoginElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsLoginElem(Login newBeViewsLoginElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsLoginElem(), newBeViewsLoginElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsLoginElem(Login newBeViewsLoginElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsLoginElem(), newBeViewsLoginElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Skin getBeViewsSkinElem() {
		return (Skin)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsSkinElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeViewsSkinElem(Skin newBeViewsSkinElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsSkinElem(), newBeViewsSkinElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeViewsSkinElem(Skin newBeViewsSkinElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_BeViewsSkinElem(), newBeViewsSkinElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageSelectorComponent getPageSelectorComponentElem() {
		return (PageSelectorComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_PageSelectorComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPageSelectorComponentElem(PageSelectorComponent newPageSelectorComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_PageSelectorComponentElem(), newPageSelectorComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPageSelectorComponentElem(PageSelectorComponent newPageSelectorComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_PageSelectorComponentElem(), newPageSelectorComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Panel getPanelElem() {
		return (Panel)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_PanelElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPanelElem(Panel newPanelElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_PanelElem(), newPanelElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPanelElem(Panel newPanelElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_PanelElem(), newPanelElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachineComponent getStateMachineComponentElem() {
		return (StateMachineComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_StateMachineComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStateMachineComponentElem(StateMachineComponent newStateMachineComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_StateMachineComponentElem(), newStateMachineComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateMachineComponentElem(StateMachineComponent newStateMachineComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_StateMachineComponentElem(), newStateMachineComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryManagerComponent getQueryManagerComponentElem() {
		return (QueryManagerComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_QueryManagerComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueryManagerComponentElem(QueryManagerComponent newQueryManagerComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_QueryManagerComponentElem(), newQueryManagerComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueryManagerComponentElem(QueryManagerComponent newQueryManagerComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_QueryManagerComponentElem(), newQueryManagerComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelatedAssetsComponent getRelatedAssetsComponentElem() {
		return (RelatedAssetsComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_RelatedAssetsComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRelatedAssetsComponentElem(RelatedAssetsComponent newRelatedAssetsComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_RelatedAssetsComponentElem(), newRelatedAssetsComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedAssetsComponentElem(RelatedAssetsComponent newRelatedAssetsComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_RelatedAssetsComponentElem(), newRelatedAssetsComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchPage getSearchPageElem() {
		return (SearchPage)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_SearchPageElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSearchPageElem(SearchPage newSearchPageElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_SearchPageElem(), newSearchPageElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSearchPageElem(SearchPage newSearchPageElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_SearchPageElem(), newSearchPageElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchViewComponent getSearchViewComponentElem() {
		return (SearchViewComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_SearchViewComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSearchViewComponentElem(SearchViewComponent newSearchViewComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_SearchViewComponentElem(), newSearchViewComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSearchViewComponentElem(SearchViewComponent newSearchViewComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_SearchViewComponentElem(), newSearchViewComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextComponentColorSet getTextComponentColorSetElem() {
		return (TextComponentColorSet)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_TextComponentColorSetElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTextComponentColorSetElem(TextComponentColorSet newTextComponentColorSetElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_TextComponentColorSetElem(), newTextComponentColorSetElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTextComponentColorSetElem(TextComponentColorSet newTextComponentColorSetElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_TextComponentColorSetElem(), newTextComponentColorSetElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextComponent getTextComponentElem() {
		return (TextComponent)getMixed().get(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_TextComponentElem(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTextComponentElem(TextComponent newTextComponentElem, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_TextComponentElem(), newTextComponentElem, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTextComponentElem(TextComponent newTextComponentElem) {
		((FeatureMap.Internal)getMixed()).set(BEViewsConfigurationPackage.eINSTANCE.getDocumentRoot_TextComponentElem(), newTextComponentElem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ALERT_COMPONENT_ELEM:
				return basicSetAlertComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ASSET_PAGE_ELEM:
				return basicSetAssetPageElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BLUE_PRINT_COMPONENT_ELEM:
				return basicSetBluePrintComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_COLOR_SET_ELEM:
				return basicSetChartComponentColorSetElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_ELEM:
				return basicSetChartComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CLASSIFIER_COMPONENT_ELEM:
				return basicSetClassifierComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__DASHBOARD_PAGE_ELEM:
				return basicSetDashboardPageElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_ELEM:
				return basicSetBeViewsConfigElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_VERSION_ELEM:
				return basicSetBeViewsConfigVersionElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_ELEMENT_ELEM:
				return basicSetBeViewsElementElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_FOOTER_ELEM:
				return basicSetBeViewsFooterElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_HEADER_ELEM:
				return basicSetBeViewsHeaderElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_LOGIN_ELEM:
				return basicSetBeViewsLoginElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_SKIN_ELEM:
				return basicSetBeViewsSkinElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PAGE_SELECTOR_COMPONENT_ELEM:
				return basicSetPageSelectorComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PANEL_ELEM:
				return basicSetPanelElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__STATE_MACHINE_COMPONENT_ELEM:
				return basicSetStateMachineComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__QUERY_MANAGER_COMPONENT_ELEM:
				return basicSetQueryManagerComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__RELATED_ASSETS_COMPONENT_ELEM:
				return basicSetRelatedAssetsComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_PAGE_ELEM:
				return basicSetSearchPageElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_VIEW_COMPONENT_ELEM:
				return basicSetSearchViewComponentElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_COLOR_SET_ELEM:
				return basicSetTextComponentColorSetElem(null, msgs);
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_ELEM:
				return basicSetTextComponentElem(null, msgs);
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
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ALERT_COMPONENT_ELEM:
				return getAlertComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ASSET_PAGE_ELEM:
				return getAssetPageElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BLUE_PRINT_COMPONENT_ELEM:
				return getBluePrintComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_COLOR_SET_ELEM:
				return getChartComponentColorSetElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_ELEM:
				return getChartComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CLASSIFIER_COMPONENT_ELEM:
				return getClassifierComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__DASHBOARD_PAGE_ELEM:
				return getDashboardPageElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_ELEM:
				return getBeViewsConfigElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_VERSION_ELEM:
				return getBeViewsConfigVersionElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_ELEMENT_ELEM:
				return getBeViewsElementElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_FOOTER_ELEM:
				return getBeViewsFooterElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_HEADER_ELEM:
				return getBeViewsHeaderElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_LOGIN_ELEM:
				return getBeViewsLoginElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_SKIN_ELEM:
				return getBeViewsSkinElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PAGE_SELECTOR_COMPONENT_ELEM:
				return getPageSelectorComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PANEL_ELEM:
				return getPanelElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__STATE_MACHINE_COMPONENT_ELEM:
				return getStateMachineComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__QUERY_MANAGER_COMPONENT_ELEM:
				return getQueryManagerComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__RELATED_ASSETS_COMPONENT_ELEM:
				return getRelatedAssetsComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_PAGE_ELEM:
				return getSearchPageElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_VIEW_COMPONENT_ELEM:
				return getSearchViewComponentElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_COLOR_SET_ELEM:
				return getTextComponentColorSetElem();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_ELEM:
				return getTextComponentElem();
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
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ALERT_COMPONENT_ELEM:
				setAlertComponentElem((AlertComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ASSET_PAGE_ELEM:
				setAssetPageElem((AssetPage)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BLUE_PRINT_COMPONENT_ELEM:
				setBluePrintComponentElem((BluePrintComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_COLOR_SET_ELEM:
				setChartComponentColorSetElem((ComponentColorSet)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_ELEM:
				setChartComponentElem((ChartComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CLASSIFIER_COMPONENT_ELEM:
				setClassifierComponentElem((ClassifierComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__DASHBOARD_PAGE_ELEM:
				setDashboardPageElem((DashboardPage)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_ELEM:
				setBeViewsConfigElem((View)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_VERSION_ELEM:
				setBeViewsConfigVersionElem((ViewVersion)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_ELEMENT_ELEM:
				setBeViewsElementElem((BEViewsElement)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_FOOTER_ELEM:
				setBeViewsFooterElem((Footer)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_HEADER_ELEM:
				setBeViewsHeaderElem((Header)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_LOGIN_ELEM:
				setBeViewsLoginElem((Login)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_SKIN_ELEM:
				setBeViewsSkinElem((Skin)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PAGE_SELECTOR_COMPONENT_ELEM:
				setPageSelectorComponentElem((PageSelectorComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PANEL_ELEM:
				setPanelElem((Panel)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__STATE_MACHINE_COMPONENT_ELEM:
				setStateMachineComponentElem((StateMachineComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__QUERY_MANAGER_COMPONENT_ELEM:
				setQueryManagerComponentElem((QueryManagerComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__RELATED_ASSETS_COMPONENT_ELEM:
				setRelatedAssetsComponentElem((RelatedAssetsComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_PAGE_ELEM:
				setSearchPageElem((SearchPage)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_VIEW_COMPONENT_ELEM:
				setSearchViewComponentElem((SearchViewComponent)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_COLOR_SET_ELEM:
				setTextComponentColorSetElem((TextComponentColorSet)newValue);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_ELEM:
				setTextComponentElem((TextComponent)newValue);
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
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ALERT_COMPONENT_ELEM:
				setAlertComponentElem((AlertComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ASSET_PAGE_ELEM:
				setAssetPageElem((AssetPage)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BLUE_PRINT_COMPONENT_ELEM:
				setBluePrintComponentElem((BluePrintComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_COLOR_SET_ELEM:
				setChartComponentColorSetElem((ComponentColorSet)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_ELEM:
				setChartComponentElem((ChartComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CLASSIFIER_COMPONENT_ELEM:
				setClassifierComponentElem((ClassifierComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__DASHBOARD_PAGE_ELEM:
				setDashboardPageElem((DashboardPage)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_ELEM:
				setBeViewsConfigElem((View)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_VERSION_ELEM:
				setBeViewsConfigVersionElem((ViewVersion)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_ELEMENT_ELEM:
				setBeViewsElementElem((BEViewsElement)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_FOOTER_ELEM:
				setBeViewsFooterElem((Footer)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_HEADER_ELEM:
				setBeViewsHeaderElem((Header)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_LOGIN_ELEM:
				setBeViewsLoginElem((Login)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_SKIN_ELEM:
				setBeViewsSkinElem((Skin)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PAGE_SELECTOR_COMPONENT_ELEM:
				setPageSelectorComponentElem((PageSelectorComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PANEL_ELEM:
				setPanelElem((Panel)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__STATE_MACHINE_COMPONENT_ELEM:
				setStateMachineComponentElem((StateMachineComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__QUERY_MANAGER_COMPONENT_ELEM:
				setQueryManagerComponentElem((QueryManagerComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__RELATED_ASSETS_COMPONENT_ELEM:
				setRelatedAssetsComponentElem((RelatedAssetsComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_PAGE_ELEM:
				setSearchPageElem((SearchPage)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_VIEW_COMPONENT_ELEM:
				setSearchViewComponentElem((SearchViewComponent)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_COLOR_SET_ELEM:
				setTextComponentColorSetElem((TextComponentColorSet)null);
				return;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_ELEM:
				setTextComponentElem((TextComponent)null);
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
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ALERT_COMPONENT_ELEM:
				return getAlertComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__ASSET_PAGE_ELEM:
				return getAssetPageElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BLUE_PRINT_COMPONENT_ELEM:
				return getBluePrintComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_COLOR_SET_ELEM:
				return getChartComponentColorSetElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CHART_COMPONENT_ELEM:
				return getChartComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__CLASSIFIER_COMPONENT_ELEM:
				return getClassifierComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__DASHBOARD_PAGE_ELEM:
				return getDashboardPageElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_ELEM:
				return getBeViewsConfigElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_CONFIG_VERSION_ELEM:
				return getBeViewsConfigVersionElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_ELEMENT_ELEM:
				return getBeViewsElementElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_FOOTER_ELEM:
				return getBeViewsFooterElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_HEADER_ELEM:
				return getBeViewsHeaderElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_LOGIN_ELEM:
				return getBeViewsLoginElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__BE_VIEWS_SKIN_ELEM:
				return getBeViewsSkinElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PAGE_SELECTOR_COMPONENT_ELEM:
				return getPageSelectorComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__PANEL_ELEM:
				return getPanelElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__STATE_MACHINE_COMPONENT_ELEM:
				return getStateMachineComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__QUERY_MANAGER_COMPONENT_ELEM:
				return getQueryManagerComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__RELATED_ASSETS_COMPONENT_ELEM:
				return getRelatedAssetsComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_PAGE_ELEM:
				return getSearchPageElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__SEARCH_VIEW_COMPONENT_ELEM:
				return getSearchViewComponentElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_COLOR_SET_ELEM:
				return getTextComponentColorSetElem() != null;
			case BEViewsConfigurationPackage.DOCUMENT_ROOT__TEXT_COMPONENT_ELEM:
				return getTextComponentElem() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
