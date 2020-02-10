/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAlertComponentElem <em>Alert Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAssetPageElem <em>Asset Page Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBluePrintComponentElem <em>Blue Print Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentColorSetElem <em>Chart Component Color Set Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentElem <em>Chart Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getClassifierComponentElem <em>Classifier Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getDashboardPageElem <em>Dashboard Page Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigElem <em>Be Views Config Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigVersionElem <em>Be Views Config Version Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsElementElem <em>Be Views Element Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsFooterElem <em>Be Views Footer Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsHeaderElem <em>Be Views Header Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsLoginElem <em>Be Views Login Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsSkinElem <em>Be Views Skin Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPageSelectorComponentElem <em>Page Selector Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPanelElem <em>Panel Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getStateMachineComponentElem <em>State Machine Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getQueryManagerComponentElem <em>Query Manager Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getRelatedAssetsComponentElem <em>Related Assets Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchPageElem <em>Search Page Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchViewComponentElem <em>Search View Component Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentColorSetElem <em>Text Component Color Set Elem</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentElem <em>Text Component Elem</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Alert Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert Component Elem</em>' containment reference.
	 * @see #setAlertComponentElem(AlertComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_AlertComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AlertComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	AlertComponent getAlertComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAlertComponentElem <em>Alert Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alert Component Elem</em>' containment reference.
	 * @see #getAlertComponentElem()
	 * @generated
	 */
	void setAlertComponentElem(AlertComponent value);

	/**
	 * Returns the value of the '<em><b>Asset Page Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asset Page Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asset Page Elem</em>' containment reference.
	 * @see #setAssetPageElem(AssetPage)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_AssetPageElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssetPageElem' namespace='##targetNamespace'"
	 * @generated
	 */
	AssetPage getAssetPageElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAssetPageElem <em>Asset Page Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asset Page Elem</em>' containment reference.
	 * @see #getAssetPageElem()
	 * @generated
	 */
	void setAssetPageElem(AssetPage value);

	/**
	 * Returns the value of the '<em><b>Blue Print Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Blue Print Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Blue Print Component Elem</em>' containment reference.
	 * @see #setBluePrintComponentElem(BluePrintComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BluePrintComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BluePrintComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	BluePrintComponent getBluePrintComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBluePrintComponentElem <em>Blue Print Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Blue Print Component Elem</em>' containment reference.
	 * @see #getBluePrintComponentElem()
	 * @generated
	 */
	void setBluePrintComponentElem(BluePrintComponent value);

	/**
	 * Returns the value of the '<em><b>Chart Component Color Set Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Chart Component Color Set Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Chart Component Color Set Elem</em>' containment reference.
	 * @see #setChartComponentColorSetElem(ComponentColorSet)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_ChartComponentColorSetElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ChartComponentColorSetElem' namespace='##targetNamespace'"
	 * @generated
	 */
	ComponentColorSet getChartComponentColorSetElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentColorSetElem <em>Chart Component Color Set Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Chart Component Color Set Elem</em>' containment reference.
	 * @see #getChartComponentColorSetElem()
	 * @generated
	 */
	void setChartComponentColorSetElem(ComponentColorSet value);

	/**
	 * Returns the value of the '<em><b>Chart Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Chart Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Chart Component Elem</em>' containment reference.
	 * @see #setChartComponentElem(ChartComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_ChartComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ChartComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	ChartComponent getChartComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentElem <em>Chart Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Chart Component Elem</em>' containment reference.
	 * @see #getChartComponentElem()
	 * @generated
	 */
	void setChartComponentElem(ChartComponent value);

	/**
	 * Returns the value of the '<em><b>Classifier Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classifier Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classifier Component Elem</em>' containment reference.
	 * @see #setClassifierComponentElem(ClassifierComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_ClassifierComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ClassifierComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	ClassifierComponent getClassifierComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getClassifierComponentElem <em>Classifier Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classifier Component Elem</em>' containment reference.
	 * @see #getClassifierComponentElem()
	 * @generated
	 */
	void setClassifierComponentElem(ClassifierComponent value);

	/**
	 * Returns the value of the '<em><b>Dashboard Page Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dashboard Page Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dashboard Page Elem</em>' containment reference.
	 * @see #setDashboardPageElem(DashboardPage)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_DashboardPageElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='DashboardPageElem' namespace='##targetNamespace'"
	 * @generated
	 */
	DashboardPage getDashboardPageElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getDashboardPageElem <em>Dashboard Page Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dashboard Page Elem</em>' containment reference.
	 * @see #getDashboardPageElem()
	 * @generated
	 */
	void setDashboardPageElem(DashboardPage value);

	/**
	 * Returns the value of the '<em><b>Be Views Config Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 			Represents the top level element in the repository. Contains or leads to all the information needed to make BEViews come up for a user or user's role
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Be Views Config Elem</em>' containment reference.
	 * @see #setBeViewsConfigElem(View)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsConfigElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsConfigElem' namespace='##targetNamespace'"
	 * @generated
	 */
	View getBeViewsConfigElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigElem <em>Be Views Config Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Config Elem</em>' containment reference.
	 * @see #getBeViewsConfigElem()
	 * @generated
	 */
	void setBeViewsConfigElem(View value);

	/**
	 * Returns the value of the '<em><b>Be Views Config Version Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Views Config Version Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Views Config Version Elem</em>' containment reference.
	 * @see #setBeViewsConfigVersionElem(ViewVersion)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsConfigVersionElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsConfigVersionElem' namespace='##targetNamespace'"
	 * @generated
	 */
	ViewVersion getBeViewsConfigVersionElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigVersionElem <em>Be Views Config Version Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Config Version Elem</em>' containment reference.
	 * @see #getBeViewsConfigVersionElem()
	 * @generated
	 */
	void setBeViewsConfigVersionElem(ViewVersion value);

	/**
	 * Returns the value of the '<em><b>Be Views Element Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Views Element Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Views Element Elem</em>' containment reference.
	 * @see #setBeViewsElementElem(BEViewsElement)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsElementElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsElementElem' namespace='##targetNamespace'"
	 * @generated
	 */
	BEViewsElement getBeViewsElementElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsElementElem <em>Be Views Element Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Element Elem</em>' containment reference.
	 * @see #getBeViewsElementElem()
	 * @generated
	 */
	void setBeViewsElementElem(BEViewsElement value);

	/**
	 * Returns the value of the '<em><b>Be Views Footer Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Views Footer Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Views Footer Elem</em>' containment reference.
	 * @see #setBeViewsFooterElem(Footer)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsFooterElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsFooterElem' namespace='##targetNamespace'"
	 * @generated
	 */
	Footer getBeViewsFooterElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsFooterElem <em>Be Views Footer Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Footer Elem</em>' containment reference.
	 * @see #getBeViewsFooterElem()
	 * @generated
	 */
	void setBeViewsFooterElem(Footer value);

	/**
	 * Returns the value of the '<em><b>Be Views Header Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Views Header Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Views Header Elem</em>' containment reference.
	 * @see #setBeViewsHeaderElem(Header)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsHeaderElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsHeaderElem' namespace='##targetNamespace'"
	 * @generated
	 */
	Header getBeViewsHeaderElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsHeaderElem <em>Be Views Header Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Header Elem</em>' containment reference.
	 * @see #getBeViewsHeaderElem()
	 * @generated
	 */
	void setBeViewsHeaderElem(Header value);

	/**
	 * Returns the value of the '<em><b>Be Views Login Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Views Login Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Views Login Elem</em>' containment reference.
	 * @see #setBeViewsLoginElem(Login)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsLoginElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsLoginElem' namespace='##targetNamespace'"
	 * @generated
	 */
	Login getBeViewsLoginElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsLoginElem <em>Be Views Login Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Login Elem</em>' containment reference.
	 * @see #getBeViewsLoginElem()
	 * @generated
	 */
	void setBeViewsLoginElem(Login value);

	/**
	 * Returns the value of the '<em><b>Be Views Skin Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Views Skin Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Views Skin Elem</em>' containment reference.
	 * @see #setBeViewsSkinElem(Skin)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_BeViewsSkinElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BEViewsSkinElem' namespace='##targetNamespace'"
	 * @generated
	 */
	Skin getBeViewsSkinElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsSkinElem <em>Be Views Skin Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Views Skin Elem</em>' containment reference.
	 * @see #getBeViewsSkinElem()
	 * @generated
	 */
	void setBeViewsSkinElem(Skin value);

	/**
	 * Returns the value of the '<em><b>Page Selector Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Page Selector Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Page Selector Component Elem</em>' containment reference.
	 * @see #setPageSelectorComponentElem(PageSelectorComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_PageSelectorComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='PageSetSelectorComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	PageSelectorComponent getPageSelectorComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPageSelectorComponentElem <em>Page Selector Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page Selector Component Elem</em>' containment reference.
	 * @see #getPageSelectorComponentElem()
	 * @generated
	 */
	void setPageSelectorComponentElem(PageSelectorComponent value);

	/**
	 * Returns the value of the '<em><b>Panel Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Panel Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Panel Elem</em>' containment reference.
	 * @see #setPanelElem(Panel)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_PanelElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='PanelElem' namespace='##targetNamespace'"
	 * @generated
	 */
	Panel getPanelElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPanelElem <em>Panel Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Panel Elem</em>' containment reference.
	 * @see #getPanelElem()
	 * @generated
	 */
	void setPanelElem(Panel value);

	/**
	 * Returns the value of the '<em><b>State Machine Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Machine Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine Component Elem</em>' containment reference.
	 * @see #setStateMachineComponentElem(StateMachineComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_StateMachineComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='StateMachineComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	StateMachineComponent getStateMachineComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getStateMachineComponentElem <em>State Machine Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Machine Component Elem</em>' containment reference.
	 * @see #getStateMachineComponentElem()
	 * @generated
	 */
	void setStateMachineComponentElem(StateMachineComponent value);

	/**
	 * Returns the value of the '<em><b>Query Manager Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Manager Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Manager Component Elem</em>' containment reference.
	 * @see #setQueryManagerComponentElem(QueryManagerComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_QueryManagerComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='QueryManagerComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	QueryManagerComponent getQueryManagerComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getQueryManagerComponentElem <em>Query Manager Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Manager Component Elem</em>' containment reference.
	 * @see #getQueryManagerComponentElem()
	 * @generated
	 */
	void setQueryManagerComponentElem(QueryManagerComponent value);

	/**
	 * Returns the value of the '<em><b>Related Assets Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Assets Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Assets Component Elem</em>' containment reference.
	 * @see #setRelatedAssetsComponentElem(RelatedAssetsComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_RelatedAssetsComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RelatedAssetsComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	RelatedAssetsComponent getRelatedAssetsComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getRelatedAssetsComponentElem <em>Related Assets Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Assets Component Elem</em>' containment reference.
	 * @see #getRelatedAssetsComponentElem()
	 * @generated
	 */
	void setRelatedAssetsComponentElem(RelatedAssetsComponent value);

	/**
	 * Returns the value of the '<em><b>Search Page Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Search Page Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Search Page Elem</em>' containment reference.
	 * @see #setSearchPageElem(SearchPage)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_SearchPageElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SearchPageElem' namespace='##targetNamespace'"
	 * @generated
	 */
	SearchPage getSearchPageElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchPageElem <em>Search Page Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Search Page Elem</em>' containment reference.
	 * @see #getSearchPageElem()
	 * @generated
	 */
	void setSearchPageElem(SearchPage value);

	/**
	 * Returns the value of the '<em><b>Search View Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Search View Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Search View Component Elem</em>' containment reference.
	 * @see #setSearchViewComponentElem(SearchViewComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_SearchViewComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='SearchViewComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	SearchViewComponent getSearchViewComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchViewComponentElem <em>Search View Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Search View Component Elem</em>' containment reference.
	 * @see #getSearchViewComponentElem()
	 * @generated
	 */
	void setSearchViewComponentElem(SearchViewComponent value);

	/**
	 * Returns the value of the '<em><b>Text Component Color Set Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Component Color Set Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text Component Color Set Elem</em>' containment reference.
	 * @see #setTextComponentColorSetElem(TextComponentColorSet)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_TextComponentColorSetElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='TextComponentColorSetElem' namespace='##targetNamespace'"
	 * @generated
	 */
	TextComponentColorSet getTextComponentColorSetElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentColorSetElem <em>Text Component Color Set Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text Component Color Set Elem</em>' containment reference.
	 * @see #getTextComponentColorSetElem()
	 * @generated
	 */
	void setTextComponentColorSetElem(TextComponentColorSet value);

	/**
	 * Returns the value of the '<em><b>Text Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Component Elem</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text Component Elem</em>' containment reference.
	 * @see #setTextComponentElem(TextComponent)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDocumentRoot_TextComponentElem()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='TextComponentElem' namespace='##targetNamespace'"
	 * @generated
	 */
	TextComponent getTextComponentElem();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentElem <em>Text Component Elem</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text Component Elem</em>' containment reference.
	 * @see #getTextComponentElem()
	 * @generated
	 */
	void setTextComponentElem(TextComponent value);

} // DocumentRoot
