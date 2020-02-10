/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.designtime.core.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * This is the BE Views Configuration Schema
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface BEViewsConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "beviewsconfig";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/be/designer";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "beviewsconfig";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BEViewsConfigurationPackage eINSTANCE = com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsElementImpl <em>BE Views Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsElementImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getBEViewsElement()
	 * @generated
	 */
	int BE_VIEWS_ELEMENT = 48;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT__VERSION = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>BE Views Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BE_VIEWS_ELEMENT_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionDefinitionImpl <em>Action Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionDefinitionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getActionDefinition()
	 * @generated
	 */
	int ACTION_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__TEXT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__TYPE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Command URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION__COMMAND_URL = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Action Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEFINITION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl <em>Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSeriesConfig()
	 * @generated
	 */
	int SERIES_CONFIG = 80;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.OneDimSeriesConfigImpl <em>One Dim Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.OneDimSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getOneDimSeriesConfig()
	 * @generated
	 */
	int ONE_DIM_SERIES_CONFIG = 58;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TwoDimSeriesConfigImpl <em>Two Dim Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TwoDimSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTwoDimSeriesConfig()
	 * @generated
	 */
	int TWO_DIM_SERIES_CONFIG = 91;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextSeriesConfigImpl <em>Text Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextSeriesConfig()
	 * @generated
	 */
	int TEXT_SERIES_CONFIG = 87;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl <em>Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getVisualization()
	 * @generated
	 */
	int VISUALIZATION = 95;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.OneDimVisualizationImpl <em>One Dim Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.OneDimVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getOneDimVisualization()
	 * @generated
	 */
	int ONE_DIM_VISUALIZATION = 59;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TwoDimVisualizationImpl <em>Two Dim Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TwoDimVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTwoDimVisualization()
	 * @generated
	 */
	int TWO_DIM_VISUALIZATION = 92;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextVisualizationImpl <em>Text Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextVisualization()
	 * @generated
	 */
	int TEXT_VISUALIZATION = 90;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 26;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ClassifierComponentImpl <em>Classifier Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ClassifierComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getClassifierComponent()
	 * @generated
	 */
	int CLASSIFIER_COMPONENT = 23;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertComponentImpl <em>Alert Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlertComponent()
	 * @generated
	 */
	int ALERT_COMPONENT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateEnumerationImpl <em>Alert Indicator State Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateEnumerationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlertIndicatorStateEnumeration()
	 * @generated
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateMapImpl <em>Alert Indicator State Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateMapImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlertIndicatorStateMap()
	 * @generated
	 */
	int ALERT_INDICATOR_STATE_MAP = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ClassifierSeriesConfigImpl <em>Classifier Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ClassifierSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getClassifierSeriesConfig()
	 * @generated
	 */
	int CLASSIFIER_SERIES_CONFIG = 24;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertSeriesConfigImpl <em>Alert Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlertSeriesConfig()
	 * @generated
	 */
	int ALERT_SERIES_CONFIG = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ClassifierVisualizationImpl <em>Classifier Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ClassifierVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getClassifierVisualization()
	 * @generated
	 */
	int CLASSIFIER_VISUALIZATION = 25;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertVisualizationImpl <em>Alert Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlertVisualization()
	 * @generated
	 */
	int ALERT_VISUALIZATION = 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartVisualizationImpl <em>Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartVisualization()
	 * @generated
	 */
	int CHART_VISUALIZATION = 22;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl <em>Line Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLineChartVisualization()
	 * @generated
	 */
	int LINE_CHART_VISUALIZATION = 57;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AreaChartVisualizationImpl <em>Area Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AreaChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAreaChartVisualization()
	 * @generated
	 */
	int AREA_CHART_VISUALIZATION = 9;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 60;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AssetPageImpl <em>Asset Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AssetPageImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAssetPage()
	 * @generated
	 */
	int ASSET_PAGE = 10;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BackgroundFormatImpl <em>Background Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BackgroundFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getBackgroundFormat()
	 * @generated
	 */
	int BACKGROUND_FORMAT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BarChartVisualizationImpl <em>Bar Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BarChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getBarChartVisualization()
	 * @generated
	 */
	int BAR_CHART_VISUALIZATION = 12;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BluePrintComponentImpl <em>Blue Print Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BluePrintComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getBluePrintComponent()
	 * @generated
	 */
	int BLUE_PRINT_COMPONENT = 13;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl <em>Category Guideline Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getCategoryGuidelineConfig()
	 * @generated
	 */
	int CATEGORY_GUIDELINE_CONFIG = 14;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataConfigImpl <em>Data Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDataConfig()
	 * @generated
	 */
	int DATA_CONFIG = 29;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__EXTRACTOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG__FORMATTER = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Data Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONFIG_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateDataConfigImpl <em>State Data Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateDataConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getStateDataConfig()
	 * @generated
	 */
	int STATE_DATA_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__NAMESPACE = DATA_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__FOLDER = DATA_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__NAME = DATA_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__DESCRIPTION = DATA_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__LAST_MODIFIED = DATA_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__GUID = DATA_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__ONTOLOGY = DATA_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__EXTENDED_PROPERTIES = DATA_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__HIDDEN_PROPERTIES = DATA_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__TRANSIENT_PROPERTIES = DATA_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__OWNER_PROJECT_NAME = DATA_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__ORIGINAL_ELEMENT_VERSION = DATA_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__VERSION = DATA_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__EXTRACTOR = DATA_CONFIG__EXTRACTOR;

	/**
	 * The feature id for the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG__FORMATTER = DATA_CONFIG__FORMATTER;

	/**
	 * The number of structural features of the '<em>State Data Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_DATA_CONFIG_FEATURE_COUNT = DATA_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__TOOL_TIP = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__ACTION_RULE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__QUERY_LINK = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__ROLL_OVER_CONFIG = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG__RELATED_ELEMENT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__NAMESPACE = SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__FOLDER = SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__NAME = SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__DESCRIPTION = SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__LAST_MODIFIED = SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__GUID = SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__ONTOLOGY = SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES = SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES = SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES = SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME = SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__VERSION = SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__DISPLAY_NAME = SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__TOOL_TIP = SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__ACTION_RULE = SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__QUERY_LINK = SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG = SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__RELATED_ELEMENT = SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG = SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>One Dim Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_SERIES_CONFIG_FEATURE_COUNT = SERIES_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateSeriesConfigImpl <em>State Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getStateSeriesConfig()
	 * @generated
	 */
	int STATE_SERIES_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__NAMESPACE = ONE_DIM_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__FOLDER = ONE_DIM_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__NAME = ONE_DIM_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__DESCRIPTION = ONE_DIM_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__LAST_MODIFIED = ONE_DIM_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__GUID = ONE_DIM_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__ONTOLOGY = ONE_DIM_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__EXTENDED_PROPERTIES = ONE_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__HIDDEN_PROPERTIES = ONE_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__TRANSIENT_PROPERTIES = ONE_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__OWNER_PROJECT_NAME = ONE_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__VERSION = ONE_DIM_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__DISPLAY_NAME = ONE_DIM_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__TOOL_TIP = ONE_DIM_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__ACTION_RULE = ONE_DIM_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__QUERY_LINK = ONE_DIM_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__ROLL_OVER_CONFIG = ONE_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__RELATED_ELEMENT = ONE_DIM_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG__VALUE_DATA_CONFIG = ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The number of structural features of the '<em>State Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SERIES_CONFIG_FEATURE_COUNT = ONE_DIM_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__BACKGROUND = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__SERIES_COLOR_INDEX = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__ACTION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__SERIES_CONFIG = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION__RELATED_ELEMENT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUALIZATION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__NAMESPACE = VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__FOLDER = VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__NAME = VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__DESCRIPTION = VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__LAST_MODIFIED = VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__GUID = VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__ONTOLOGY = VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__EXTENDED_PROPERTIES = VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__HIDDEN_PROPERTIES = VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__TRANSIENT_PROPERTIES = VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__OWNER_PROJECT_NAME = VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__VERSION = VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__DISPLAY_NAME = VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__BACKGROUND = VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__SERIES_COLOR_INDEX = VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__ACTION = VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__SERIES_CONFIG = VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__RELATED_ELEMENT = VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG = VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>One Dim Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ONE_DIM_VISUALIZATION_FEATURE_COUNT = VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateVisualizationImpl <em>State Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getStateVisualization()
	 * @generated
	 */
	int STATE_VISUALIZATION = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__NAMESPACE = ONE_DIM_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__FOLDER = ONE_DIM_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__NAME = ONE_DIM_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__DESCRIPTION = ONE_DIM_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__LAST_MODIFIED = ONE_DIM_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__GUID = ONE_DIM_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__ONTOLOGY = ONE_DIM_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__EXTENDED_PROPERTIES = ONE_DIM_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__HIDDEN_PROPERTIES = ONE_DIM_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__TRANSIENT_PROPERTIES = ONE_DIM_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__OWNER_PROJECT_NAME = ONE_DIM_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__VERSION = ONE_DIM_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__DISPLAY_NAME = ONE_DIM_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__BACKGROUND = ONE_DIM_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__SERIES_COLOR_INDEX = ONE_DIM_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__ACTION = ONE_DIM_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__SERIES_CONFIG = ONE_DIM_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__RELATED_ELEMENT = ONE_DIM_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__VALUE_GUIDELINE_CONFIG = ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>State Ref ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION__STATE_REF_ID = ONE_DIM_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>State Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VISUALIZATION_FEATURE_COUNT = ONE_DIM_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__HELP_TEXT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT_COLOR_SET_INDEX = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SERIES_COLOR_INDEX = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__BACKGROUND = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VISUALIZATION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__LAYOUT_CONSTRAINT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SERIES_CONFIG_GENERATOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__RELATED_ELEMENT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Classifier Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__NAMESPACE = CLASSIFIER_COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__FOLDER = CLASSIFIER_COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__NAME = CLASSIFIER_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__DESCRIPTION = CLASSIFIER_COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__LAST_MODIFIED = CLASSIFIER_COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__GUID = CLASSIFIER_COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__ONTOLOGY = CLASSIFIER_COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__EXTENDED_PROPERTIES = CLASSIFIER_COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__HIDDEN_PROPERTIES = CLASSIFIER_COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__TRANSIENT_PROPERTIES = CLASSIFIER_COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__OWNER_PROJECT_NAME = CLASSIFIER_COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = CLASSIFIER_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__ORIGINAL_ELEMENT_VERSION = CLASSIFIER_COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__VERSION = CLASSIFIER_COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__DISPLAY_NAME = CLASSIFIER_COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__HELP_TEXT = CLASSIFIER_COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__COMPONENT_COLOR_SET_INDEX = CLASSIFIER_COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__SERIES_COLOR_INDEX = CLASSIFIER_COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__BACKGROUND = CLASSIFIER_COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__VISUALIZATION = CLASSIFIER_COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__LAYOUT_CONSTRAINT = CLASSIFIER_COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__SERIES_CONFIG_GENERATOR = CLASSIFIER_COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__RELATED_ELEMENT = CLASSIFIER_COMPONENT__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT__THRESHOLD = CLASSIFIER_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Alert Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_COMPONENT_FEATURE_COUNT = CLASSIFIER_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Field Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__FIELD_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION__MAPPING = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alert Indicator State Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_ENUMERATION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Field Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__FIELD_VALUE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Indicator State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP__INDICATOR_STATE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alert Indicator State Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_INDICATOR_STATE_MAP_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__NAMESPACE = ONE_DIM_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__FOLDER = ONE_DIM_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__NAME = ONE_DIM_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__DESCRIPTION = ONE_DIM_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__LAST_MODIFIED = ONE_DIM_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__GUID = ONE_DIM_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__ONTOLOGY = ONE_DIM_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__EXTENDED_PROPERTIES = ONE_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__HIDDEN_PROPERTIES = ONE_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__TRANSIENT_PROPERTIES = ONE_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__OWNER_PROJECT_NAME = ONE_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__VERSION = ONE_DIM_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__DISPLAY_NAME = ONE_DIM_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__TOOL_TIP = ONE_DIM_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__ACTION_RULE = ONE_DIM_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__QUERY_LINK = ONE_DIM_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__ROLL_OVER_CONFIG = ONE_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__RELATED_ELEMENT = ONE_DIM_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG__VALUE_DATA_CONFIG = ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The number of structural features of the '<em>Classifier Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_SERIES_CONFIG_FEATURE_COUNT = ONE_DIM_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__NAMESPACE = CLASSIFIER_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__FOLDER = CLASSIFIER_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__NAME = CLASSIFIER_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__DESCRIPTION = CLASSIFIER_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__LAST_MODIFIED = CLASSIFIER_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__GUID = CLASSIFIER_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__ONTOLOGY = CLASSIFIER_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__EXTENDED_PROPERTIES = CLASSIFIER_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__HIDDEN_PROPERTIES = CLASSIFIER_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__TRANSIENT_PROPERTIES = CLASSIFIER_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__OWNER_PROJECT_NAME = CLASSIFIER_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = CLASSIFIER_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = CLASSIFIER_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__VERSION = CLASSIFIER_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__DISPLAY_NAME = CLASSIFIER_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__TOOL_TIP = CLASSIFIER_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__ACTION_RULE = CLASSIFIER_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__QUERY_LINK = CLASSIFIER_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__ROLL_OVER_CONFIG = CLASSIFIER_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__RELATED_ELEMENT = CLASSIFIER_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__VALUE_DATA_CONFIG = CLASSIFIER_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Indicator State Enumeration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG__INDICATOR_STATE_ENUMERATION = CLASSIFIER_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Alert Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_SERIES_CONFIG_FEATURE_COUNT = CLASSIFIER_SERIES_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__NAMESPACE = ONE_DIM_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__FOLDER = ONE_DIM_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__NAME = ONE_DIM_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__DESCRIPTION = ONE_DIM_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__LAST_MODIFIED = ONE_DIM_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__GUID = ONE_DIM_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__ONTOLOGY = ONE_DIM_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__EXTENDED_PROPERTIES = ONE_DIM_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__HIDDEN_PROPERTIES = ONE_DIM_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__TRANSIENT_PROPERTIES = ONE_DIM_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__OWNER_PROJECT_NAME = ONE_DIM_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__VERSION = ONE_DIM_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__DISPLAY_NAME = ONE_DIM_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__BACKGROUND = ONE_DIM_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__SERIES_COLOR_INDEX = ONE_DIM_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__ACTION = ONE_DIM_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__SERIES_CONFIG = ONE_DIM_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__RELATED_ELEMENT = ONE_DIM_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION__VALUE_GUIDELINE_CONFIG = ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The number of structural features of the '<em>Classifier Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_VISUALIZATION_FEATURE_COUNT = ONE_DIM_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__NAMESPACE = CLASSIFIER_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__FOLDER = CLASSIFIER_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__NAME = CLASSIFIER_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__DESCRIPTION = CLASSIFIER_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__LAST_MODIFIED = CLASSIFIER_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__GUID = CLASSIFIER_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__ONTOLOGY = CLASSIFIER_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__EXTENDED_PROPERTIES = CLASSIFIER_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__HIDDEN_PROPERTIES = CLASSIFIER_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__TRANSIENT_PROPERTIES = CLASSIFIER_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__OWNER_PROJECT_NAME = CLASSIFIER_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = CLASSIFIER_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = CLASSIFIER_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__VERSION = CLASSIFIER_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__DISPLAY_NAME = CLASSIFIER_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__BACKGROUND = CLASSIFIER_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__SERIES_COLOR_INDEX = CLASSIFIER_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__ACTION = CLASSIFIER_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__SERIES_CONFIG = CLASSIFIER_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__RELATED_ELEMENT = CLASSIFIER_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION__VALUE_GUIDELINE_CONFIG = CLASSIFIER_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The number of structural features of the '<em>Alert Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_VISUALIZATION_FEATURE_COUNT = CLASSIFIER_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__NAMESPACE = ONE_DIM_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__FOLDER = ONE_DIM_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__NAME = ONE_DIM_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__DESCRIPTION = ONE_DIM_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__LAST_MODIFIED = ONE_DIM_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__GUID = ONE_DIM_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__ONTOLOGY = ONE_DIM_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__EXTENDED_PROPERTIES = ONE_DIM_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__HIDDEN_PROPERTIES = ONE_DIM_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__TRANSIENT_PROPERTIES = ONE_DIM_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__OWNER_PROJECT_NAME = ONE_DIM_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = ONE_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__VERSION = ONE_DIM_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__DISPLAY_NAME = ONE_DIM_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__BACKGROUND = ONE_DIM_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__SERIES_COLOR_INDEX = ONE_DIM_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__ACTION = ONE_DIM_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__SERIES_CONFIG = ONE_DIM_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__RELATED_ELEMENT = ONE_DIM_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG = ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = ONE_DIM_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Two Dim Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_VISUALIZATION_FEATURE_COUNT = ONE_DIM_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__NAMESPACE = TWO_DIM_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__FOLDER = TWO_DIM_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__NAME = TWO_DIM_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__DESCRIPTION = TWO_DIM_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__LAST_MODIFIED = TWO_DIM_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__GUID = TWO_DIM_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__ONTOLOGY = TWO_DIM_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__EXTENDED_PROPERTIES = TWO_DIM_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__HIDDEN_PROPERTIES = TWO_DIM_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__TRANSIENT_PROPERTIES = TWO_DIM_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__OWNER_PROJECT_NAME = TWO_DIM_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = TWO_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = TWO_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__VERSION = TWO_DIM_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__DISPLAY_NAME = TWO_DIM_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__BACKGROUND = TWO_DIM_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__SERIES_COLOR_INDEX = TWO_DIM_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__ACTION = TWO_DIM_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__SERIES_CONFIG = TWO_DIM_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__RELATED_ELEMENT = TWO_DIM_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = TWO_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = TWO_DIM_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VISUALIZATION_FEATURE_COUNT = TWO_DIM_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__NAMESPACE = CHART_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__FOLDER = CHART_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__NAME = CHART_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__DESCRIPTION = CHART_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__LAST_MODIFIED = CHART_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__GUID = CHART_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__ONTOLOGY = CHART_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__EXTENDED_PROPERTIES = CHART_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__HIDDEN_PROPERTIES = CHART_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__TRANSIENT_PROPERTIES = CHART_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__OWNER_PROJECT_NAME = CHART_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__VERSION = CHART_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__DISPLAY_NAME = CHART_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__BACKGROUND = CHART_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__SERIES_COLOR_INDEX = CHART_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__ACTION = CHART_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__SERIES_CONFIG = CHART_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__RELATED_ELEMENT = CHART_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Line Thickness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__LINE_THICKNESS = CHART_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Line Smoothness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS = CHART_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Data Plotting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__DATA_PLOTTING = CHART_VISUALIZATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Plot Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__PLOT_SHAPE = CHART_VISUALIZATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Plot Shape Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION = CHART_VISUALIZATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Line Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_CHART_VISUALIZATION_FEATURE_COUNT = CHART_VISUALIZATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__NAMESPACE = LINE_CHART_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__FOLDER = LINE_CHART_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__NAME = LINE_CHART_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__DESCRIPTION = LINE_CHART_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__LAST_MODIFIED = LINE_CHART_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__GUID = LINE_CHART_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__ONTOLOGY = LINE_CHART_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__EXTENDED_PROPERTIES = LINE_CHART_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__HIDDEN_PROPERTIES = LINE_CHART_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__TRANSIENT_PROPERTIES = LINE_CHART_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__OWNER_PROJECT_NAME = LINE_CHART_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = LINE_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = LINE_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__VERSION = LINE_CHART_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__DISPLAY_NAME = LINE_CHART_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__BACKGROUND = LINE_CHART_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__SERIES_COLOR_INDEX = LINE_CHART_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__ACTION = LINE_CHART_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__SERIES_CONFIG = LINE_CHART_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__RELATED_ELEMENT = LINE_CHART_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = LINE_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = LINE_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = LINE_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Line Thickness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__LINE_THICKNESS = LINE_CHART_VISUALIZATION__LINE_THICKNESS;

	/**
	 * The feature id for the '<em><b>Line Smoothness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__LINE_SMOOTHNESS = LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS;

	/**
	 * The feature id for the '<em><b>Data Plotting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__DATA_PLOTTING = LINE_CHART_VISUALIZATION__DATA_PLOTTING;

	/**
	 * The feature id for the '<em><b>Plot Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__PLOT_SHAPE = LINE_CHART_VISUALIZATION__PLOT_SHAPE;

	/**
	 * The feature id for the '<em><b>Plot Shape Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION = LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION;

	/**
	 * The feature id for the '<em><b>Fill Opacity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION__FILL_OPACITY = LINE_CHART_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Area Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_CHART_VISUALIZATION_FEATURE_COUNT = LINE_CHART_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Display Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__DISPLAY_MODE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Partition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__PARTITION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__VISUALIZATION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__NAMESPACE = PAGE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__FOLDER = PAGE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__DESCRIPTION = PAGE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__LAST_MODIFIED = PAGE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__GUID = PAGE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__ONTOLOGY = PAGE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__EXTENDED_PROPERTIES = PAGE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__HIDDEN_PROPERTIES = PAGE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__TRANSIENT_PROPERTIES = PAGE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__OWNER_PROJECT_NAME = PAGE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__ORIGINAL_ELEMENT_IDENTIFIER = PAGE__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__ORIGINAL_ELEMENT_VERSION = PAGE__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__VERSION = PAGE__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__DISPLAY_NAME = PAGE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Display Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__DISPLAY_MODE = PAGE__DISPLAY_MODE;

	/**
	 * The feature id for the '<em><b>Partition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__PARTITION = PAGE__PARTITION;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE__VISUALIZATION = PAGE__VISUALIZATION;

	/**
	 * The number of structural features of the '<em>Asset Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSET_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Gradient Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT__GRADIENT_DIRECTION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Background Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACKGROUND_FORMAT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__NAMESPACE = CHART_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__FOLDER = CHART_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__NAME = CHART_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__DESCRIPTION = CHART_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__LAST_MODIFIED = CHART_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__GUID = CHART_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__ONTOLOGY = CHART_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__EXTENDED_PROPERTIES = CHART_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__HIDDEN_PROPERTIES = CHART_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__TRANSIENT_PROPERTIES = CHART_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__OWNER_PROJECT_NAME = CHART_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__VERSION = CHART_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__DISPLAY_NAME = CHART_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__BACKGROUND = CHART_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__SERIES_COLOR_INDEX = CHART_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__ACTION = CHART_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__SERIES_CONFIG = CHART_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__RELATED_ELEMENT = CHART_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__WIDTH = CHART_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Top Cap Thickness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS = CHART_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Overlap Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE = CHART_VISUALIZATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION__ORIENTATION = CHART_VISUALIZATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Bar Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAR_CHART_VISUALIZATION_FEATURE_COUNT = CHART_VISUALIZATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Blue Print Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLUE_PRINT_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Header Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__HEADER_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Label Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sort Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__SORT_ORDER = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Duplicates Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Category Guideline Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryDataConfigImpl <em>Chart Category Data Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryDataConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartCategoryDataConfig()
	 * @generated
	 */
	int CHART_CATEGORY_DATA_CONFIG = 15;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__NAMESPACE = DATA_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__FOLDER = DATA_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__NAME = DATA_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__DESCRIPTION = DATA_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__LAST_MODIFIED = DATA_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__GUID = DATA_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__ONTOLOGY = DATA_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__EXTENDED_PROPERTIES = DATA_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__HIDDEN_PROPERTIES = DATA_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__TRANSIENT_PROPERTIES = DATA_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__OWNER_PROJECT_NAME = DATA_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__ORIGINAL_ELEMENT_VERSION = DATA_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__VERSION = DATA_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__EXTRACTOR = DATA_CONFIG__EXTRACTOR;

	/**
	 * The feature id for the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG__FORMATTER = DATA_CONFIG__FORMATTER;

	/**
	 * The number of structural features of the '<em>Chart Category Data Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_DATA_CONFIG_FEATURE_COUNT = DATA_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryGuidelineConfigImpl <em>Chart Category Guideline Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryGuidelineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartCategoryGuidelineConfig()
	 * @generated
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG = 16;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__NAMESPACE = CATEGORY_GUIDELINE_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__FOLDER = CATEGORY_GUIDELINE_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__NAME = CATEGORY_GUIDELINE_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__DESCRIPTION = CATEGORY_GUIDELINE_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__LAST_MODIFIED = CATEGORY_GUIDELINE_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__GUID = CATEGORY_GUIDELINE_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__ONTOLOGY = CATEGORY_GUIDELINE_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__EXTENDED_PROPERTIES = CATEGORY_GUIDELINE_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__HIDDEN_PROPERTIES = CATEGORY_GUIDELINE_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES = CATEGORY_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__OWNER_PROJECT_NAME = CATEGORY_GUIDELINE_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION = CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__VERSION = CATEGORY_GUIDELINE_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Header Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__HEADER_NAME = CATEGORY_GUIDELINE_CONFIG__HEADER_NAME;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE = CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Label Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE = CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Sort Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__SORT_ORDER = CATEGORY_GUIDELINE_CONFIG__SORT_ORDER;

	/**
	 * The feature id for the '<em><b>Duplicates Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED = CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED;

	/**
	 * The feature id for the '<em><b>Relative Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Placement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rotation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Skip Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Chart Category Guideline Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentImpl <em>Chart Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartComponent()
	 * @generated
	 */
	int CHART_COMPONENT = 17;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Plot Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__PLOT_AREA = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Legend</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__LEGEND = COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG = COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT__VALUE_GUIDELINE_CONFIG = COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Chart Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentColorSetImpl <em>Component Color Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentColorSetImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getComponentColorSet()
	 * @generated
	 */
	int COMPONENT_COLOR_SET = 27;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Component Color Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_COLOR_SET_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl <em>Chart Component Color Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartComponentColorSet()
	 * @generated
	 */
	int CHART_COMPONENT_COLOR_SET = 18;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__NAMESPACE = COMPONENT_COLOR_SET__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__FOLDER = COMPONENT_COLOR_SET__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__NAME = COMPONENT_COLOR_SET__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__DESCRIPTION = COMPONENT_COLOR_SET__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__LAST_MODIFIED = COMPONENT_COLOR_SET__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__GUID = COMPONENT_COLOR_SET__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__ONTOLOGY = COMPONENT_COLOR_SET__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__EXTENDED_PROPERTIES = COMPONENT_COLOR_SET__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__HIDDEN_PROPERTIES = COMPONENT_COLOR_SET__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__TRANSIENT_PROPERTIES = COMPONENT_COLOR_SET__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__OWNER_PROJECT_NAME = COMPONENT_COLOR_SET__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_VERSION = COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__VERSION = COMPONENT_COLOR_SET__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__DISPLAY_NAME = COMPONENT_COLOR_SET__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Series Color</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__SERIES_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guide Line Label Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__GUIDE_LINE_LABEL_FONT_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Guide Line Value Label Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__GUIDE_LINE_VALUE_LABEL_FONT_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Data Point Label Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__DATA_POINT_LABEL_FONT_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Top Cap Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__TOP_CAP_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Pie Edge Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__PIE_EDGE_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Pie Drop Shadow Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__PIE_DROP_SHADOW_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Line Drop Shadow Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET__LINE_DROP_SHADOW_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Chart Component Color Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_COMPONENT_COLOR_SET_FEATURE_COUNT = COMPONENT_COLOR_SET_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__NAMESPACE = ONE_DIM_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__FOLDER = ONE_DIM_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__NAME = ONE_DIM_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__DESCRIPTION = ONE_DIM_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__LAST_MODIFIED = ONE_DIM_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__GUID = ONE_DIM_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__ONTOLOGY = ONE_DIM_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES = ONE_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES = ONE_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES = ONE_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME = ONE_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = ONE_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__VERSION = ONE_DIM_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__DISPLAY_NAME = ONE_DIM_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__TOOL_TIP = ONE_DIM_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__ACTION_RULE = ONE_DIM_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__QUERY_LINK = ONE_DIM_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG = ONE_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__RELATED_ELEMENT = ONE_DIM_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG = ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG = ONE_DIM_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Two Dim Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TWO_DIM_SERIES_CONFIG_FEATURE_COUNT = ONE_DIM_SERIES_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartSeriesConfigImpl <em>Chart Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartSeriesConfig()
	 * @generated
	 */
	int CHART_SERIES_CONFIG = 19;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__NAMESPACE = TWO_DIM_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__FOLDER = TWO_DIM_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__NAME = TWO_DIM_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__DESCRIPTION = TWO_DIM_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__LAST_MODIFIED = TWO_DIM_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__GUID = TWO_DIM_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__ONTOLOGY = TWO_DIM_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__EXTENDED_PROPERTIES = TWO_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__HIDDEN_PROPERTIES = TWO_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__TRANSIENT_PROPERTIES = TWO_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__OWNER_PROJECT_NAME = TWO_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = TWO_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = TWO_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__VERSION = TWO_DIM_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__DISPLAY_NAME = TWO_DIM_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__TOOL_TIP = TWO_DIM_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__ACTION_RULE = TWO_DIM_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__QUERY_LINK = TWO_DIM_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__ROLL_OVER_CONFIG = TWO_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__RELATED_ELEMENT = TWO_DIM_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__VALUE_DATA_CONFIG = TWO_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__CATEGORY_DATA_CONFIG = TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Anchor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__ANCHOR = TWO_DIM_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value Label Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG__VALUE_LABEL_STYLE = TWO_DIM_SERIES_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Chart Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_SERIES_CONFIG_FEATURE_COUNT = TWO_DIM_SERIES_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueDataConfigImpl <em>Chart Value Data Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueDataConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartValueDataConfig()
	 * @generated
	 */
	int CHART_VALUE_DATA_CONFIG = 20;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__NAMESPACE = DATA_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__FOLDER = DATA_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__NAME = DATA_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__DESCRIPTION = DATA_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__LAST_MODIFIED = DATA_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__GUID = DATA_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__ONTOLOGY = DATA_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__EXTENDED_PROPERTIES = DATA_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__HIDDEN_PROPERTIES = DATA_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__TRANSIENT_PROPERTIES = DATA_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__OWNER_PROJECT_NAME = DATA_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__ORIGINAL_ELEMENT_VERSION = DATA_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__VERSION = DATA_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__EXTRACTOR = DATA_CONFIG__EXTRACTOR;

	/**
	 * The feature id for the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG__FORMATTER = DATA_CONFIG__FORMATTER;

	/**
	 * The number of structural features of the '<em>Chart Value Data Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_DATA_CONFIG_FEATURE_COUNT = DATA_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueGuidelineConfigImpl <em>Value Guideline Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueGuidelineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getValueGuidelineConfig()
	 * @generated
	 */
	int VALUE_GUIDELINE_CONFIG = 93;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Value Guideline Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_GUIDELINE_CONFIG_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl <em>Chart Value Guideline Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getChartValueGuidelineConfig()
	 * @generated
	 */
	int CHART_VALUE_GUIDELINE_CONFIG = 21;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__NAMESPACE = VALUE_GUIDELINE_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__FOLDER = VALUE_GUIDELINE_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__NAME = VALUE_GUIDELINE_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__DESCRIPTION = VALUE_GUIDELINE_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__LAST_MODIFIED = VALUE_GUIDELINE_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__GUID = VALUE_GUIDELINE_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__ONTOLOGY = VALUE_GUIDELINE_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__EXTENDED_PROPERTIES = VALUE_GUIDELINE_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__HIDDEN_PROPERTIES = VALUE_GUIDELINE_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES = VALUE_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__OWNER_PROJECT_NAME = VALUE_GUIDELINE_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION = VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__VERSION = VALUE_GUIDELINE_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE = VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Label Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Header Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__HEADER_NAME = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Relative Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__SCALE = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Division</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG__DIVISION = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Chart Value Guideline Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHART_VALUE_GUIDELINE_CONFIG_FEATURE_COUNT = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DashboardPageImpl <em>Dashboard Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.DashboardPageImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDashboardPage()
	 * @generated
	 */
	int DASHBOARD_PAGE = 28;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__NAMESPACE = PAGE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__FOLDER = PAGE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__DESCRIPTION = PAGE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__LAST_MODIFIED = PAGE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__GUID = PAGE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__ONTOLOGY = PAGE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__EXTENDED_PROPERTIES = PAGE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__HIDDEN_PROPERTIES = PAGE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__TRANSIENT_PROPERTIES = PAGE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__OWNER_PROJECT_NAME = PAGE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__ORIGINAL_ELEMENT_IDENTIFIER = PAGE__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__ORIGINAL_ELEMENT_VERSION = PAGE__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__VERSION = PAGE__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__DISPLAY_NAME = PAGE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Display Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__DISPLAY_MODE = PAGE__DISPLAY_MODE;

	/**
	 * The feature id for the '<em><b>Partition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__PARTITION = PAGE__PARTITION;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE__VISUALIZATION = PAGE__VISUALIZATION;

	/**
	 * The number of structural features of the '<em>Dashboard Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DASHBOARD_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataExtractorImpl <em>Data Extractor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataExtractorImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDataExtractor()
	 * @generated
	 */
	int DATA_EXTRACTOR = 30;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Source Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR__SOURCE_FIELD = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Extractor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_EXTRACTOR_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl <em>Data Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDataFormat()
	 * @generated
	 */
	int DATA_FORMAT = 31;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Tool Tip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__TOOL_TIP_FORMAT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Display Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__DISPLAY_FORMAT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__FORMAT_STYLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Action Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__ACTION_CONFIG = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Show Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT__SHOW_LABEL = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Data Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FORMAT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl <em>Action Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getActionRule()
	 * @generated
	 */
	int ACTION_RULE = 32;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Data Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__DATA_SOURCE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__THRESHOLD = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Threshold Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__THRESHOLD_UNIT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Alert</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__ALERT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Drillable Fields</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__DRILLABLE_FIELDS = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE__PARAMS = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Action Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_RULE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataSourceImpl <em>Data Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataSourceImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDataSource()
	 * @generated
	 */
	int DATA_SOURCE = 33;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__QUERY = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Src Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__SRC_ELEMENT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__PARAMS = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Data Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.QueryParamImpl <em>Query Param</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.QueryParamImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getQueryParam()
	 * @generated
	 */
	int QUERY_PARAM = 34;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__VALUE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM__TYPE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Query Param</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_PARAM_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertImpl <em>Alert</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlert()
	 * @generated
	 */
	int ALERT = 35;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__ACTION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT__ENABLED = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alert</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangeAlertImpl <em>Range Alert</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangeAlertImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRangeAlert()
	 * @generated
	 */
	int RANGE_ALERT = 36;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__NAMESPACE = ALERT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__FOLDER = ALERT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__NAME = ALERT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__DESCRIPTION = ALERT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__LAST_MODIFIED = ALERT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__GUID = ALERT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__ONTOLOGY = ALERT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__EXTENDED_PROPERTIES = ALERT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__HIDDEN_PROPERTIES = ALERT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__TRANSIENT_PROPERTIES = ALERT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__OWNER_PROJECT_NAME = ALERT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__ORIGINAL_ELEMENT_IDENTIFIER = ALERT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__ORIGINAL_ELEMENT_VERSION = ALERT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__VERSION = ALERT__VERSION;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__ACTION = ALERT__ACTION;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__ENABLED = ALERT__ENABLED;

	/**
	 * The feature id for the '<em><b>Lower Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__LOWER_VALUE = ALERT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT__UPPER_VALUE = ALERT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Range Alert</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_ALERT_FEATURE_COUNT = ALERT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertActionImpl <em>Alert Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertActionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAlertAction()
	 * @generated
	 */
	int ALERT_ACTION = 37;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION__ENABLED = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Alert Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALERT_ACTION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl <em>Visual Alert Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getVisualAlertAction()
	 * @generated
	 */
	int VISUAL_ALERT_ACTION = 38;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__NAMESPACE = ALERT_ACTION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__FOLDER = ALERT_ACTION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__NAME = ALERT_ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__DESCRIPTION = ALERT_ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__LAST_MODIFIED = ALERT_ACTION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__GUID = ALERT_ACTION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__ONTOLOGY = ALERT_ACTION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__EXTENDED_PROPERTIES = ALERT_ACTION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__HIDDEN_PROPERTIES = ALERT_ACTION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__TRANSIENT_PROPERTIES = ALERT_ACTION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__OWNER_PROJECT_NAME = ALERT_ACTION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__ORIGINAL_ELEMENT_IDENTIFIER = ALERT_ACTION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__ORIGINAL_ELEMENT_VERSION = ALERT_ACTION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__VERSION = ALERT_ACTION__VERSION;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__ENABLED = ALERT_ACTION__ENABLED;

	/**
	 * The feature id for the '<em><b>Fill Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__FILL_COLOR = ALERT_ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__FONT_SIZE = ALERT_ACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Font Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__FONT_STYLE = ALERT_ACTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__FONT_COLOR = ALERT_ACTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Display Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__DISPLAY_FORMAT = ALERT_ACTION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Tooltip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION__TOOLTIP_FORMAT = ALERT_ACTION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Visual Alert Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ALERT_ACTION_FEATURE_COUNT = ALERT_ACTION_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.DocumentRootImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 39;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Alert Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ALERT_COMPONENT_ELEM = 3;

	/**
	 * The feature id for the '<em><b>Asset Page Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSET_PAGE_ELEM = 4;

	/**
	 * The feature id for the '<em><b>Blue Print Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BLUE_PRINT_COMPONENT_ELEM = 5;

	/**
	 * The feature id for the '<em><b>Chart Component Color Set Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHART_COMPONENT_COLOR_SET_ELEM = 6;

	/**
	 * The feature id for the '<em><b>Chart Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHART_COMPONENT_ELEM = 7;

	/**
	 * The feature id for the '<em><b>Classifier Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CLASSIFIER_COMPONENT_ELEM = 8;

	/**
	 * The feature id for the '<em><b>Dashboard Page Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DASHBOARD_PAGE_ELEM = 9;

	/**
	 * The feature id for the '<em><b>Be Views Config Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_CONFIG_ELEM = 10;

	/**
	 * The feature id for the '<em><b>Be Views Config Version Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_CONFIG_VERSION_ELEM = 11;

	/**
	 * The feature id for the '<em><b>Be Views Element Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_ELEMENT_ELEM = 12;

	/**
	 * The feature id for the '<em><b>Be Views Footer Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_FOOTER_ELEM = 13;

	/**
	 * The feature id for the '<em><b>Be Views Header Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_HEADER_ELEM = 14;

	/**
	 * The feature id for the '<em><b>Be Views Login Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_LOGIN_ELEM = 15;

	/**
	 * The feature id for the '<em><b>Be Views Skin Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BE_VIEWS_SKIN_ELEM = 16;

	/**
	 * The feature id for the '<em><b>Page Selector Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PAGE_SELECTOR_COMPONENT_ELEM = 17;

	/**
	 * The feature id for the '<em><b>Panel Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PANEL_ELEM = 18;

	/**
	 * The feature id for the '<em><b>State Machine Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STATE_MACHINE_COMPONENT_ELEM = 19;

	/**
	 * The feature id for the '<em><b>Query Manager Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__QUERY_MANAGER_COMPONENT_ELEM = 20;

	/**
	 * The feature id for the '<em><b>Related Assets Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RELATED_ASSETS_COMPONENT_ELEM = 21;

	/**
	 * The feature id for the '<em><b>Search Page Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SEARCH_PAGE_ELEM = 22;

	/**
	 * The feature id for the '<em><b>Search View Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SEARCH_VIEW_COMPONENT_ELEM = 23;

	/**
	 * The feature id for the '<em><b>Text Component Color Set Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT_COMPONENT_COLOR_SET_ELEM = 24;

	/**
	 * The feature id for the '<em><b>Text Component Elem</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT_COMPONENT_ELEM = 25;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 26;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutImpl <em>Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLayout()
	 * @generated
	 */
	int LAYOUT = 54;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Repositioning Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__REPOSITIONING_ALLOWED = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__COMPONENT_WIDTH = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Component Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT__COMPONENT_HEIGHT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FlowLayoutImpl <em>Flow Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.FlowLayoutImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFlowLayout()
	 * @generated
	 */
	int FLOW_LAYOUT = 40;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__NAMESPACE = LAYOUT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__FOLDER = LAYOUT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__NAME = LAYOUT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__DESCRIPTION = LAYOUT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__LAST_MODIFIED = LAYOUT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__GUID = LAYOUT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__ONTOLOGY = LAYOUT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__EXTENDED_PROPERTIES = LAYOUT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__HIDDEN_PROPERTIES = LAYOUT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__TRANSIENT_PROPERTIES = LAYOUT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__OWNER_PROJECT_NAME = LAYOUT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__ORIGINAL_ELEMENT_IDENTIFIER = LAYOUT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__ORIGINAL_ELEMENT_VERSION = LAYOUT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__VERSION = LAYOUT__VERSION;

	/**
	 * The feature id for the '<em><b>Repositioning Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__REPOSITIONING_ALLOWED = LAYOUT__REPOSITIONING_ALLOWED;

	/**
	 * The feature id for the '<em><b>Component Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__COMPONENT_WIDTH = LAYOUT__COMPONENT_WIDTH;

	/**
	 * The feature id for the '<em><b>Component Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT__COMPONENT_HEIGHT = LAYOUT__COMPONENT_HEIGHT;

	/**
	 * The number of structural features of the '<em>Flow Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_FEATURE_COUNT = LAYOUT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutConstraintImpl <em>Layout Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutConstraintImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLayoutConstraint()
	 * @generated
	 */
	int LAYOUT_CONSTRAINT = 55;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The number of structural features of the '<em>Layout Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAYOUT_CONSTRAINT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FlowLayoutConstraintImpl <em>Flow Layout Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.FlowLayoutConstraintImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFlowLayoutConstraint()
	 * @generated
	 */
	int FLOW_LAYOUT_CONSTRAINT = 41;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__NAMESPACE = LAYOUT_CONSTRAINT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__FOLDER = LAYOUT_CONSTRAINT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__NAME = LAYOUT_CONSTRAINT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__DESCRIPTION = LAYOUT_CONSTRAINT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__LAST_MODIFIED = LAYOUT_CONSTRAINT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__GUID = LAYOUT_CONSTRAINT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__ONTOLOGY = LAYOUT_CONSTRAINT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__EXTENDED_PROPERTIES = LAYOUT_CONSTRAINT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__HIDDEN_PROPERTIES = LAYOUT_CONSTRAINT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__TRANSIENT_PROPERTIES = LAYOUT_CONSTRAINT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__OWNER_PROJECT_NAME = LAYOUT_CONSTRAINT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__ORIGINAL_ELEMENT_IDENTIFIER = LAYOUT_CONSTRAINT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__ORIGINAL_ELEMENT_VERSION = LAYOUT_CONSTRAINT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__VERSION = LAYOUT_CONSTRAINT__VERSION;

	/**
	 * The feature id for the '<em><b>Component Row Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN = LAYOUT_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component Col Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN = LAYOUT_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Flow Layout Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_LAYOUT_CONSTRAINT_FEATURE_COUNT = LAYOUT_CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ForegroundFormatImpl <em>Foreground Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ForegroundFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getForegroundFormat()
	 * @generated
	 */
	int FOREGROUND_FORMAT = 42;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT__LINE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Foreground Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOREGROUND_FORMAT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FormatStyleImpl <em>Format Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.FormatStyleImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFormatStyle()
	 * @generated
	 */
	int FORMAT_STYLE = 43;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__FONT_SIZE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Font Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE__FONT_STYLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Format Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAT_STYLE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextFieldFormatImpl <em>Text Field Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextFieldFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextFieldFormat()
	 * @generated
	 */
	int TEXT_FIELD_FORMAT = 86;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__NAMESPACE = DATA_FORMAT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__FOLDER = DATA_FORMAT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__NAME = DATA_FORMAT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__DESCRIPTION = DATA_FORMAT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__LAST_MODIFIED = DATA_FORMAT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__GUID = DATA_FORMAT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__ONTOLOGY = DATA_FORMAT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__EXTENDED_PROPERTIES = DATA_FORMAT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__HIDDEN_PROPERTIES = DATA_FORMAT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__TRANSIENT_PROPERTIES = DATA_FORMAT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__OWNER_PROJECT_NAME = DATA_FORMAT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = DATA_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__ORIGINAL_ELEMENT_VERSION = DATA_FORMAT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__VERSION = DATA_FORMAT__VERSION;

	/**
	 * The feature id for the '<em><b>Tool Tip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__TOOL_TIP_FORMAT = DATA_FORMAT__TOOL_TIP_FORMAT;

	/**
	 * The feature id for the '<em><b>Display Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__DISPLAY_FORMAT = DATA_FORMAT__DISPLAY_FORMAT;

	/**
	 * The feature id for the '<em><b>Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__FORMAT_STYLE = DATA_FORMAT__FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Action Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__ACTION_CONFIG = DATA_FORMAT__ACTION_CONFIG;

	/**
	 * The feature id for the '<em><b>Show Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT__SHOW_LABEL = DATA_FORMAT__SHOW_LABEL;

	/**
	 * The number of structural features of the '<em>Text Field Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FORMAT_FEATURE_COUNT = DATA_FORMAT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.IndicatorFieldFormatImpl <em>Indicator Field Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.IndicatorFieldFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getIndicatorFieldFormat()
	 * @generated
	 */
	int INDICATOR_FIELD_FORMAT = 44;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__NAMESPACE = TEXT_FIELD_FORMAT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__FOLDER = TEXT_FIELD_FORMAT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__NAME = TEXT_FIELD_FORMAT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__DESCRIPTION = TEXT_FIELD_FORMAT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__LAST_MODIFIED = TEXT_FIELD_FORMAT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__GUID = TEXT_FIELD_FORMAT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__ONTOLOGY = TEXT_FIELD_FORMAT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__EXTENDED_PROPERTIES = TEXT_FIELD_FORMAT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__HIDDEN_PROPERTIES = TEXT_FIELD_FORMAT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__TRANSIENT_PROPERTIES = TEXT_FIELD_FORMAT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__OWNER_PROJECT_NAME = TEXT_FIELD_FORMAT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = TEXT_FIELD_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__ORIGINAL_ELEMENT_VERSION = TEXT_FIELD_FORMAT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__VERSION = TEXT_FIELD_FORMAT__VERSION;

	/**
	 * The feature id for the '<em><b>Tool Tip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__TOOL_TIP_FORMAT = TEXT_FIELD_FORMAT__TOOL_TIP_FORMAT;

	/**
	 * The feature id for the '<em><b>Display Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__DISPLAY_FORMAT = TEXT_FIELD_FORMAT__DISPLAY_FORMAT;

	/**
	 * The feature id for the '<em><b>Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__FORMAT_STYLE = TEXT_FIELD_FORMAT__FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Action Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__ACTION_CONFIG = TEXT_FIELD_FORMAT__ACTION_CONFIG;

	/**
	 * The feature id for the '<em><b>Show Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__SHOW_LABEL = TEXT_FIELD_FORMAT__SHOW_LABEL;

	/**
	 * The feature id for the '<em><b>Show Text Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE = TEXT_FIELD_FORMAT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Text Value Anchor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR = TEXT_FIELD_FORMAT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Indicator Field Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDICATOR_FIELD_FORMAT_FEATURE_COUNT = TEXT_FIELD_FORMAT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 45;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DEFAULT_PAGE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Accessible Page</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ACCESSIBLE_PAGE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Skin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__SKIN = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Locale</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__LOCALE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ATTRIBUTE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewAttributeImpl <em>View Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewAttributeImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getViewAttribute()
	 * @generated
	 */
	int VIEW_ATTRIBUTE = 46;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE__VALUE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>View Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ATTRIBUTE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewVersionImpl <em>View Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewVersionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getViewVersion()
	 * @generated
	 */
	int VIEW_VERSION = 47;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Major Version Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__MAJOR_VERSION_NUMBER = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Minor Version Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__MINOR_VERSION_NUMBER = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Point Version Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__POINT_VERSION_NUMBER = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Description1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION__DESCRIPTION1 = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>View Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_VERSION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FooterImpl <em>Footer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.FooterImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFooter()
	 * @generated
	 */
	int FOOTER = 49;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Link</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER__LINK = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Footer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOTER_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl <em>Header</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getHeader()
	 * @generated
	 */
	int HEADER = 50;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__TITLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Branding Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__BRANDING_IMAGE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Branding Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__BRANDING_TEXT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Link</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__LINK = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Header Link</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__HEADER_LINK = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Header</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewLocaleImpl <em>View Locale</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewLocaleImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getViewLocale()
	 * @generated
	 */
	int VIEW_LOCALE = 51;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__LOCALE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Time Zone</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__TIME_ZONE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Time Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE__TIME_FORMAT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>View Locale</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_LOCALE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LoginImpl <em>Login</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.LoginImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLogin()
	 * @generated
	 */
	int LOGIN = 52;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__TITLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__IMAGE_URL = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Login</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl <em>Skin</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSkin()
	 * @generated
	 */
	int SKIN = 53;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Component Color Set</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__DEFAULT_COMPONENT_COLOR_SET = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Component Color Set</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__COMPONENT_COLOR_SET = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__FONT_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Component Back Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__COMPONENT_BACK_GROUND_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Component Back Ground Gradient End Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__COMPONENT_BACK_GROUND_GRADIENT_END_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Component Fore Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__COMPONENT_FORE_GROUND_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Visualization Back Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__VISUALIZATION_BACK_GROUND_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Visualization Back Ground Gradient End Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Visualization Fore Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN__VISUALIZATION_FORE_GROUND_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Skin</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIN_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LegendFormatImpl <em>Legend Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.LegendFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLegendFormat()
	 * @generated
	 */
	int LEGEND_FORMAT = 56;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__ORIENTATION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Anchor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT__ANCHOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Legend Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGEND_FORMAT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageSelectorComponentImpl <em>Page Selector Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageSelectorComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPageSelectorComponent()
	 * @generated
	 */
	int PAGE_SELECTOR_COMPONENT = 61;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Page Selector Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_SELECTOR_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__NAMESPACE = TWO_DIM_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__FOLDER = TWO_DIM_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__NAME = TWO_DIM_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__DESCRIPTION = TWO_DIM_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__LAST_MODIFIED = TWO_DIM_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__GUID = TWO_DIM_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__ONTOLOGY = TWO_DIM_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__EXTENDED_PROPERTIES = TWO_DIM_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__HIDDEN_PROPERTIES = TWO_DIM_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__TRANSIENT_PROPERTIES = TWO_DIM_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__OWNER_PROJECT_NAME = TWO_DIM_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = TWO_DIM_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = TWO_DIM_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__VERSION = TWO_DIM_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__DISPLAY_NAME = TWO_DIM_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__BACKGROUND = TWO_DIM_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__SERIES_COLOR_INDEX = TWO_DIM_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__ACTION = TWO_DIM_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__SERIES_CONFIG = TWO_DIM_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__RELATED_ELEMENT = TWO_DIM_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__VALUE_GUIDELINE_CONFIG = TWO_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Show Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION__SHOW_HEADER = TWO_DIM_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VISUALIZATION_FEATURE_COUNT = TWO_DIM_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageVisualizationImpl <em>Page Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPageVisualization()
	 * @generated
	 */
	int PAGE_VISUALIZATION = 62;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__NAMESPACE = TEXT_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__FOLDER = TEXT_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__NAME = TEXT_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__DESCRIPTION = TEXT_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__LAST_MODIFIED = TEXT_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__GUID = TEXT_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__ONTOLOGY = TEXT_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__EXTENDED_PROPERTIES = TEXT_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__HIDDEN_PROPERTIES = TEXT_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__TRANSIENT_PROPERTIES = TEXT_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__OWNER_PROJECT_NAME = TEXT_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = TEXT_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = TEXT_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__VERSION = TEXT_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__DISPLAY_NAME = TEXT_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__BACKGROUND = TEXT_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__SERIES_COLOR_INDEX = TEXT_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__ACTION = TEXT_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__SERIES_CONFIG = TEXT_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__RELATED_ELEMENT = TEXT_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__VALUE_GUIDELINE_CONFIG = TEXT_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = TEXT_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Show Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION__SHOW_HEADER = TEXT_VISUALIZATION__SHOW_HEADER;

	/**
	 * The number of structural features of the '<em>Page Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_VISUALIZATION_FEATURE_COUNT = TEXT_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl <em>Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPanel()
	 * @generated
	 */
	int PANEL = 63;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__SPAN = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scroll Bar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__SCROLL_BAR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__STATE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Maximizable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__MAXIMIZABLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Minimizable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__MINIMIZABLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__COMPONENT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__LAYOUT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl <em>Partition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPartition()
	 * @generated
	 */
	int PARTITION = 64;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__DISPLAY_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Vertical</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__VERTICAL = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__SPAN = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__STATE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Panel</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION__PANEL = BE_VIEWS_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Partition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTITION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PieChartVisualizationImpl <em>Pie Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PieChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPieChartVisualization()
	 * @generated
	 */
	int PIE_CHART_VISUALIZATION = 65;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__NAMESPACE = CHART_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__FOLDER = CHART_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__NAME = CHART_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__DESCRIPTION = CHART_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__LAST_MODIFIED = CHART_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__GUID = CHART_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__ONTOLOGY = CHART_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__EXTENDED_PROPERTIES = CHART_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__HIDDEN_PROPERTIES = CHART_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__TRANSIENT_PROPERTIES = CHART_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__OWNER_PROJECT_NAME = CHART_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__VERSION = CHART_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__DISPLAY_NAME = CHART_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__BACKGROUND = CHART_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__SERIES_COLOR_INDEX = CHART_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__ACTION = CHART_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__SERIES_CONFIG = CHART_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__RELATED_ELEMENT = CHART_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Starting Angle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__STARTING_ANGLE = CHART_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__DIRECTION = CHART_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sector</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION__SECTOR = CHART_VISUALIZATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Pie Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIE_CHART_VISUALIZATION_FEATURE_COUNT = CHART_VISUALIZATION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PlotAreaFormatImpl <em>Plot Area Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.PlotAreaFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPlotAreaFormat()
	 * @generated
	 */
	int PLOT_AREA_FORMAT = 66;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__BACKGROUND = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Foreground</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT__FOREGROUND = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Plot Area Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLOT_AREA_FORMAT_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateMachineComponentImpl <em>State Machine Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateMachineComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getStateMachineComponent()
	 * @generated
	 */
	int STATE_MACHINE_COMPONENT = 67;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__STATE_MACHINE = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State Visualization Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_WIDTH = COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State Visualization Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_HEIGHT = COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>State Machine Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueOptionImpl <em>Value Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueOptionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getValueOption()
	 * @generated
	 */
	int VALUE_OPTION = 68;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The number of structural features of the '<em>Value Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPTION_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ConstantValueOptionImpl <em>Constant Value Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ConstantValueOptionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getConstantValueOption()
	 * @generated
	 */
	int CONSTANT_VALUE_OPTION = 69;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__NAMESPACE = VALUE_OPTION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__FOLDER = VALUE_OPTION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__NAME = VALUE_OPTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__DESCRIPTION = VALUE_OPTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__LAST_MODIFIED = VALUE_OPTION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__GUID = VALUE_OPTION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__ONTOLOGY = VALUE_OPTION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__EXTENDED_PROPERTIES = VALUE_OPTION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__HIDDEN_PROPERTIES = VALUE_OPTION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__TRANSIENT_PROPERTIES = VALUE_OPTION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__OWNER_PROJECT_NAME = VALUE_OPTION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__ORIGINAL_ELEMENT_IDENTIFIER = VALUE_OPTION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__ORIGINAL_ELEMENT_VERSION = VALUE_OPTION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__VERSION = VALUE_OPTION__VERSION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION__VALUE = VALUE_OPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Constant Value Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_OPTION_FEATURE_COUNT = VALUE_OPTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FieldReferenceValueOptionImpl <em>Field Reference Value Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.FieldReferenceValueOptionImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFieldReferenceValueOption()
	 * @generated
	 */
	int FIELD_REFERENCE_VALUE_OPTION = 70;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__NAMESPACE = VALUE_OPTION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__FOLDER = VALUE_OPTION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__NAME = VALUE_OPTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__DESCRIPTION = VALUE_OPTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__LAST_MODIFIED = VALUE_OPTION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__GUID = VALUE_OPTION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__ONTOLOGY = VALUE_OPTION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__EXTENDED_PROPERTIES = VALUE_OPTION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__HIDDEN_PROPERTIES = VALUE_OPTION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__TRANSIENT_PROPERTIES = VALUE_OPTION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__OWNER_PROJECT_NAME = VALUE_OPTION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__ORIGINAL_ELEMENT_IDENTIFIER = VALUE_OPTION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__ORIGINAL_ELEMENT_VERSION = VALUE_OPTION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__VERSION = VALUE_OPTION__VERSION;

	/**
	 * The feature id for the '<em><b>Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION__FIELD = VALUE_OPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Field Reference Value Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_REFERENCE_VALUE_OPTION_FEATURE_COUNT = VALUE_OPTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ProgressBarFieldFormatImpl <em>Progress Bar Field Format</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ProgressBarFieldFormatImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getProgressBarFieldFormat()
	 * @generated
	 */
	int PROGRESS_BAR_FIELD_FORMAT = 71;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__NAMESPACE = INDICATOR_FIELD_FORMAT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__FOLDER = INDICATOR_FIELD_FORMAT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__NAME = INDICATOR_FIELD_FORMAT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__DESCRIPTION = INDICATOR_FIELD_FORMAT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__LAST_MODIFIED = INDICATOR_FIELD_FORMAT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__GUID = INDICATOR_FIELD_FORMAT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__ONTOLOGY = INDICATOR_FIELD_FORMAT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__EXTENDED_PROPERTIES = INDICATOR_FIELD_FORMAT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__HIDDEN_PROPERTIES = INDICATOR_FIELD_FORMAT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__TRANSIENT_PROPERTIES = INDICATOR_FIELD_FORMAT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__OWNER_PROJECT_NAME = INDICATOR_FIELD_FORMAT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER = INDICATOR_FIELD_FORMAT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__ORIGINAL_ELEMENT_VERSION = INDICATOR_FIELD_FORMAT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__VERSION = INDICATOR_FIELD_FORMAT__VERSION;

	/**
	 * The feature id for the '<em><b>Tool Tip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__TOOL_TIP_FORMAT = INDICATOR_FIELD_FORMAT__TOOL_TIP_FORMAT;

	/**
	 * The feature id for the '<em><b>Display Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__DISPLAY_FORMAT = INDICATOR_FIELD_FORMAT__DISPLAY_FORMAT;

	/**
	 * The feature id for the '<em><b>Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__FORMAT_STYLE = INDICATOR_FIELD_FORMAT__FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Action Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__ACTION_CONFIG = INDICATOR_FIELD_FORMAT__ACTION_CONFIG;

	/**
	 * The feature id for the '<em><b>Show Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__SHOW_LABEL = INDICATOR_FIELD_FORMAT__SHOW_LABEL;

	/**
	 * The feature id for the '<em><b>Show Text Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__SHOW_TEXT_VALUE = INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE;

	/**
	 * The feature id for the '<em><b>Text Value Anchor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__TEXT_VALUE_ANCHOR = INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR;

	/**
	 * The feature id for the '<em><b>Min Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__MIN_VALUE = INDICATOR_FIELD_FORMAT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT__MAX_VALUE = INDICATOR_FIELD_FORMAT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Progress Bar Field Format</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FIELD_FORMAT_FEATURE_COUNT = INDICATOR_FIELD_FORMAT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.QueryManagerComponentImpl <em>Query Manager Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.QueryManagerComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getQueryManagerComponent()
	 * @generated
	 */
	int QUERY_MANAGER_COMPONENT = 72;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Query Manager Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_MANAGER_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartSeriesConfigImpl <em>Range Plot Chart Series Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartSeriesConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRangePlotChartSeriesConfig()
	 * @generated
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG = 73;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__NAMESPACE = CHART_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__FOLDER = CHART_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__NAME = CHART_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__DESCRIPTION = CHART_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__LAST_MODIFIED = CHART_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__GUID = CHART_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__ONTOLOGY = CHART_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__EXTENDED_PROPERTIES = CHART_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__HIDDEN_PROPERTIES = CHART_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__TRANSIENT_PROPERTIES = CHART_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__OWNER_PROJECT_NAME = CHART_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = CHART_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = CHART_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__VERSION = CHART_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__DISPLAY_NAME = CHART_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__TOOL_TIP = CHART_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__ACTION_RULE = CHART_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__QUERY_LINK = CHART_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__ROLL_OVER_CONFIG = CHART_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__RELATED_ELEMENT = CHART_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__VALUE_DATA_CONFIG = CHART_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__CATEGORY_DATA_CONFIG = CHART_SERIES_CONFIG__CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Anchor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__ANCHOR = CHART_SERIES_CONFIG__ANCHOR;

	/**
	 * The feature id for the '<em><b>Value Label Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__VALUE_LABEL_STYLE = CHART_SERIES_CONFIG__VALUE_LABEL_STYLE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG__TYPE = CHART_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Range Plot Chart Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_SERIES_CONFIG_FEATURE_COUNT = CHART_SERIES_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl <em>Range Plot Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRangePlotChartVisualization()
	 * @generated
	 */
	int RANGE_PLOT_CHART_VISUALIZATION = 74;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__NAMESPACE = CHART_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__FOLDER = CHART_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__NAME = CHART_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__DESCRIPTION = CHART_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__LAST_MODIFIED = CHART_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__GUID = CHART_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__ONTOLOGY = CHART_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__EXTENDED_PROPERTIES = CHART_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__HIDDEN_PROPERTIES = CHART_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__TRANSIENT_PROPERTIES = CHART_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__OWNER_PROJECT_NAME = CHART_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__VERSION = CHART_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__DISPLAY_NAME = CHART_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__BACKGROUND = CHART_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__SERIES_COLOR_INDEX = CHART_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__ACTION = CHART_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__SERIES_CONFIG = CHART_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__RELATED_ELEMENT = CHART_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Plot Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE = CHART_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Plot Shape Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION = CHART_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Whisker Thickness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS = CHART_VISUALIZATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Whisker Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH = CHART_VISUALIZATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION = CHART_VISUALIZATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Range Plot Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_PLOT_CHART_VISUALIZATION_FEATURE_COUNT = CHART_VISUALIZATION_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RelatedAssetsComponentImpl <em>Related Assets Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RelatedAssetsComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRelatedAssetsComponent()
	 * @generated
	 */
	int RELATED_ASSETS_COMPONENT = 75;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Related Assets Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ASSETS_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ScatterPlotChartVisualizationImpl <em>Scatter Plot Chart Visualization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ScatterPlotChartVisualizationImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getScatterPlotChartVisualization()
	 * @generated
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION = 76;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__NAMESPACE = CHART_VISUALIZATION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__FOLDER = CHART_VISUALIZATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__NAME = CHART_VISUALIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__DESCRIPTION = CHART_VISUALIZATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__LAST_MODIFIED = CHART_VISUALIZATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__GUID = CHART_VISUALIZATION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__ONTOLOGY = CHART_VISUALIZATION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__EXTENDED_PROPERTIES = CHART_VISUALIZATION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__HIDDEN_PROPERTIES = CHART_VISUALIZATION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__TRANSIENT_PROPERTIES = CHART_VISUALIZATION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__OWNER_PROJECT_NAME = CHART_VISUALIZATION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER = CHART_VISUALIZATION__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION = CHART_VISUALIZATION__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__VERSION = CHART_VISUALIZATION__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__DISPLAY_NAME = CHART_VISUALIZATION__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__BACKGROUND = CHART_VISUALIZATION__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__SERIES_COLOR_INDEX = CHART_VISUALIZATION__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__ACTION = CHART_VISUALIZATION__ACTION;

	/**
	 * The feature id for the '<em><b>Series Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__SERIES_CONFIG = CHART_VISUALIZATION__SERIES_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__RELATED_ELEMENT = CHART_VISUALIZATION__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG = CHART_VISUALIZATION__VALUE_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG = CHART_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG;

	/**
	 * The feature id for the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG = CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Plot Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE = CHART_VISUALIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Plot Shape Dimension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION = CHART_VISUALIZATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Scatter Plot Chart Visualization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCATTER_PLOT_CHART_VISUALIZATION_FEATURE_COUNT = CHART_VISUALIZATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SearchPageImpl <em>Search Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.SearchPageImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSearchPage()
	 * @generated
	 */
	int SEARCH_PAGE = 77;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__NAMESPACE = PAGE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__FOLDER = PAGE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__DESCRIPTION = PAGE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__LAST_MODIFIED = PAGE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__GUID = PAGE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__ONTOLOGY = PAGE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__EXTENDED_PROPERTIES = PAGE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__HIDDEN_PROPERTIES = PAGE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__TRANSIENT_PROPERTIES = PAGE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__OWNER_PROJECT_NAME = PAGE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__ORIGINAL_ELEMENT_IDENTIFIER = PAGE__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__ORIGINAL_ELEMENT_VERSION = PAGE__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__VERSION = PAGE__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__DISPLAY_NAME = PAGE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Display Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__DISPLAY_MODE = PAGE__DISPLAY_MODE;

	/**
	 * The feature id for the '<em><b>Partition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__PARTITION = PAGE__PARTITION;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE__VISUALIZATION = PAGE__VISUALIZATION;

	/**
	 * The number of structural features of the '<em>Search Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SearchViewComponentImpl <em>Search View Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.SearchViewComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSearchViewComponent()
	 * @generated
	 */
	int SEARCH_VIEW_COMPONENT = 78;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Search View Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_VIEW_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesColorImpl <em>Series Color</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesColorImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSeriesColor()
	 * @generated
	 */
	int SERIES_COLOR = 79;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Base Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__BASE_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Highlight Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR__HIGHLIGHT_COLOR = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Series Color</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_COLOR_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigGeneratorImpl <em>Series Config Generator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigGeneratorImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSeriesConfigGenerator()
	 * @generated
	 */
	int SERIES_CONFIG_GENERATOR = 81;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__FIELD = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR__CONDITION = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Series Config Generator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIES_CONFIG_GENERATOR_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryDataConfigImpl <em>Text Category Data Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryDataConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextCategoryDataConfig()
	 * @generated
	 */
	int TEXT_CATEGORY_DATA_CONFIG = 82;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__NAMESPACE = DATA_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__FOLDER = DATA_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__NAME = DATA_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__DESCRIPTION = DATA_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__LAST_MODIFIED = DATA_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__GUID = DATA_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__ONTOLOGY = DATA_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__EXTENDED_PROPERTIES = DATA_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__HIDDEN_PROPERTIES = DATA_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__TRANSIENT_PROPERTIES = DATA_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__OWNER_PROJECT_NAME = DATA_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__ORIGINAL_ELEMENT_VERSION = DATA_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__VERSION = DATA_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__EXTRACTOR = DATA_CONFIG__EXTRACTOR;

	/**
	 * The feature id for the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG__FORMATTER = DATA_CONFIG__FORMATTER;

	/**
	 * The number of structural features of the '<em>Text Category Data Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_DATA_CONFIG_FEATURE_COUNT = DATA_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryGuidelineConfigImpl <em>Text Category Guideline Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryGuidelineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextCategoryGuidelineConfig()
	 * @generated
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG = 83;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__NAMESPACE = CATEGORY_GUIDELINE_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__FOLDER = CATEGORY_GUIDELINE_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__NAME = CATEGORY_GUIDELINE_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__DESCRIPTION = CATEGORY_GUIDELINE_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__LAST_MODIFIED = CATEGORY_GUIDELINE_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__GUID = CATEGORY_GUIDELINE_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__ONTOLOGY = CATEGORY_GUIDELINE_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__EXTENDED_PROPERTIES = CATEGORY_GUIDELINE_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__HIDDEN_PROPERTIES = CATEGORY_GUIDELINE_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES = CATEGORY_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__OWNER_PROJECT_NAME = CATEGORY_GUIDELINE_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION = CATEGORY_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__VERSION = CATEGORY_GUIDELINE_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Header Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_NAME = CATEGORY_GUIDELINE_CONFIG__HEADER_NAME;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE = CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Label Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE = CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Sort Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__SORT_ORDER = CATEGORY_GUIDELINE_CONFIG__SORT_ORDER;

	/**
	 * The feature id for the '<em><b>Duplicates Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED = CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED;

	/**
	 * The feature id for the '<em><b>Header Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG__WIDTH = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Text Category Guideline Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT = CATEGORY_GUIDELINE_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentImpl <em>Text Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextComponent()
	 * @generated
	 */
	int TEXT_COMPONENT = 84;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Text Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl <em>Text Component Color Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextComponentColorSet()
	 * @generated
	 */
	int TEXT_COMPONENT_COLOR_SET = 85;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__NAMESPACE = COMPONENT_COLOR_SET__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__FOLDER = COMPONENT_COLOR_SET__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__NAME = COMPONENT_COLOR_SET__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__DESCRIPTION = COMPONENT_COLOR_SET__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__LAST_MODIFIED = COMPONENT_COLOR_SET__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__GUID = COMPONENT_COLOR_SET__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__ONTOLOGY = COMPONENT_COLOR_SET__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__EXTENDED_PROPERTIES = COMPONENT_COLOR_SET__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__HIDDEN_PROPERTIES = COMPONENT_COLOR_SET__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__TRANSIENT_PROPERTIES = COMPONENT_COLOR_SET__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__OWNER_PROJECT_NAME = COMPONENT_COLOR_SET__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_VERSION = COMPONENT_COLOR_SET__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__VERSION = COMPONENT_COLOR_SET__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__DISPLAY_NAME = COMPONENT_COLOR_SET__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Header Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__HEADER_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Header Roll Over Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__HEADER_ROLL_OVER_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Header Text Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__HEADER_TEXT_FONT_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Header Separator Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__HEADER_SEPARATOR_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Cell Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__CELL_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Cell Text Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__CELL_TEXT_FONT_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Row Separator Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__ROW_SEPARATOR_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Row Roll Over Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET__ROW_ROLL_OVER_COLOR = COMPONENT_COLOR_SET_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Text Component Color Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_COMPONENT_COLOR_SET_FEATURE_COUNT = COMPONENT_COLOR_SET_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__NAMESPACE = TWO_DIM_SERIES_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__FOLDER = TWO_DIM_SERIES_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__NAME = TWO_DIM_SERIES_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__DESCRIPTION = TWO_DIM_SERIES_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__LAST_MODIFIED = TWO_DIM_SERIES_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__GUID = TWO_DIM_SERIES_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__ONTOLOGY = TWO_DIM_SERIES_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__EXTENDED_PROPERTIES = TWO_DIM_SERIES_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__HIDDEN_PROPERTIES = TWO_DIM_SERIES_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__TRANSIENT_PROPERTIES = TWO_DIM_SERIES_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__OWNER_PROJECT_NAME = TWO_DIM_SERIES_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = TWO_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION = TWO_DIM_SERIES_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__VERSION = TWO_DIM_SERIES_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__DISPLAY_NAME = TWO_DIM_SERIES_CONFIG__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__TOOL_TIP = TWO_DIM_SERIES_CONFIG__TOOL_TIP;

	/**
	 * The feature id for the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__ACTION_RULE = TWO_DIM_SERIES_CONFIG__ACTION_RULE;

	/**
	 * The feature id for the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__QUERY_LINK = TWO_DIM_SERIES_CONFIG__QUERY_LINK;

	/**
	 * The feature id for the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__ROLL_OVER_CONFIG = TWO_DIM_SERIES_CONFIG__ROLL_OVER_CONFIG;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__RELATED_ELEMENT = TWO_DIM_SERIES_CONFIG__RELATED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Value Data Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__VALUE_DATA_CONFIG = TWO_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG;

	/**
	 * The feature id for the '<em><b>Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG__CATEGORY_DATA_CONFIG = TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG;

	/**
	 * The number of structural features of the '<em>Text Series Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_SERIES_CONFIG_FEATURE_COUNT = TWO_DIM_SERIES_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueDataConfigImpl <em>Text Value Data Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueDataConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextValueDataConfig()
	 * @generated
	 */
	int TEXT_VALUE_DATA_CONFIG = 88;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__NAMESPACE = DATA_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__FOLDER = DATA_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__NAME = DATA_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__DESCRIPTION = DATA_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__LAST_MODIFIED = DATA_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__GUID = DATA_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__ONTOLOGY = DATA_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__EXTENDED_PROPERTIES = DATA_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__HIDDEN_PROPERTIES = DATA_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__TRANSIENT_PROPERTIES = DATA_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__OWNER_PROJECT_NAME = DATA_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = DATA_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__ORIGINAL_ELEMENT_VERSION = DATA_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__VERSION = DATA_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__EXTRACTOR = DATA_CONFIG__EXTRACTOR;

	/**
	 * The feature id for the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__FORMATTER = DATA_CONFIG__FORMATTER;

	/**
	 * The feature id for the '<em><b>Header Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__HEADER_NAME = DATA_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE = DATA_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__WIDTH = DATA_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG__ALIGNMENT = DATA_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Text Value Data Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_DATA_CONFIG_FEATURE_COUNT = DATA_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueGuidelineConfigImpl <em>Text Value Guideline Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueGuidelineConfigImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getTextValueGuidelineConfig()
	 * @generated
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG = 89;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__NAMESPACE = VALUE_GUIDELINE_CONFIG__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__FOLDER = VALUE_GUIDELINE_CONFIG__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__NAME = VALUE_GUIDELINE_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__DESCRIPTION = VALUE_GUIDELINE_CONFIG__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__LAST_MODIFIED = VALUE_GUIDELINE_CONFIG__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__GUID = VALUE_GUIDELINE_CONFIG__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__ONTOLOGY = VALUE_GUIDELINE_CONFIG__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__EXTENDED_PROPERTIES = VALUE_GUIDELINE_CONFIG__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__HIDDEN_PROPERTIES = VALUE_GUIDELINE_CONFIG__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES = VALUE_GUIDELINE_CONFIG__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__OWNER_PROJECT_NAME = VALUE_GUIDELINE_CONFIG__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER = VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION = VALUE_GUIDELINE_CONFIG__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__VERSION = VALUE_GUIDELINE_CONFIG__VERSION;

	/**
	 * The feature id for the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE = VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE;

	/**
	 * The feature id for the '<em><b>Header Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Value Guideline Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_VALUE_GUIDELINE_CONFIG_FEATURE_COUNT = VALUE_GUIDELINE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueLabelStyleImpl <em>Value Label Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueLabelStyleImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getValueLabelStyle()
	 * @generated
	 */
	int VALUE_LABEL_STYLE = 94;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__FONT_SIZE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Font Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE__FONT_STYLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Value Label Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_LABEL_STYLE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RolePreferenceImpl <em>Role Preference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RolePreferenceImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRolePreference()
	 * @generated
	 */
	int ROLE_PREFERENCE = 96;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__ROLE = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Gallery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__GALLERY = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE__VIEW = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Role Preference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PREFERENCE_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentGalleryFolderImpl <em>Component Gallery Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentGalleryFolderImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getComponentGalleryFolder()
	 * @generated
	 */
	int COMPONENT_GALLERY_FOLDER = 97;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Sub Folder</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__SUB_FOLDER = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER__COMPONENT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Component Gallery Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GALLERY_FOLDER_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderLinkImpl <em>Header Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderLinkImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getHeaderLink()
	 * @generated
	 */
	int HEADER_LINK = 98;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__NAMESPACE = BE_VIEWS_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__FOLDER = BE_VIEWS_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__NAME = BE_VIEWS_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__DESCRIPTION = BE_VIEWS_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__LAST_MODIFIED = BE_VIEWS_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__GUID = BE_VIEWS_ELEMENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__ONTOLOGY = BE_VIEWS_ELEMENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__EXTENDED_PROPERTIES = BE_VIEWS_ELEMENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__HIDDEN_PROPERTIES = BE_VIEWS_ELEMENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__TRANSIENT_PROPERTIES = BE_VIEWS_ELEMENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__OWNER_PROJECT_NAME = BE_VIEWS_ELEMENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__ORIGINAL_ELEMENT_IDENTIFIER = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__ORIGINAL_ELEMENT_VERSION = BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__VERSION = BE_VIEWS_ELEMENT__VERSION;

	/**
	 * The feature id for the '<em><b>Url Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__URL_NAME = BE_VIEWS_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Url Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK__URL_LINK = BE_VIEWS_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Header Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_LINK_FEATURE_COUNT = BE_VIEWS_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RelatedPageImpl <em>Related Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RelatedPageImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRelatedPage()
	 * @generated
	 */
	int RELATED_PAGE = 99;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__NAMESPACE = PAGE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__FOLDER = PAGE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__DESCRIPTION = PAGE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__LAST_MODIFIED = PAGE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__GUID = PAGE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__ONTOLOGY = PAGE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__EXTENDED_PROPERTIES = PAGE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__HIDDEN_PROPERTIES = PAGE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__TRANSIENT_PROPERTIES = PAGE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__OWNER_PROJECT_NAME = PAGE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__ORIGINAL_ELEMENT_IDENTIFIER = PAGE__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__ORIGINAL_ELEMENT_VERSION = PAGE__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__VERSION = PAGE__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__DISPLAY_NAME = PAGE__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Display Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__DISPLAY_MODE = PAGE__DISPLAY_MODE;

	/**
	 * The feature id for the '<em><b>Partition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__PARTITION = PAGE__PARTITION;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE__VISUALIZATION = PAGE__VISUALIZATION;

	/**
	 * The number of structural features of the '<em>Related Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RelatedElementsComponentImpl <em>Related Elements Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.RelatedElementsComponentImpl
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRelatedElementsComponent()
	 * @generated
	 */
	int RELATED_ELEMENTS_COMPONENT = 100;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__NAMESPACE = COMPONENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__FOLDER = COMPONENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__DESCRIPTION = COMPONENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__LAST_MODIFIED = COMPONENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__GUID = COMPONENT__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__ONTOLOGY = COMPONENT__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__EXTENDED_PROPERTIES = COMPONENT__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__HIDDEN_PROPERTIES = COMPONENT__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__TRANSIENT_PROPERTIES = COMPONENT__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__OWNER_PROJECT_NAME = COMPONENT__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER = COMPONENT__ORIGINAL_ELEMENT_IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__ORIGINAL_ELEMENT_VERSION = COMPONENT__ORIGINAL_ELEMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__VERSION = COMPONENT__VERSION;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__DISPLAY_NAME = COMPONENT__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__HELP_TEXT = COMPONENT__HELP_TEXT;

	/**
	 * The feature id for the '<em><b>Component Color Set Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__COMPONENT_COLOR_SET_INDEX = COMPONENT__COMPONENT_COLOR_SET_INDEX;

	/**
	 * The feature id for the '<em><b>Series Color Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__SERIES_COLOR_INDEX = COMPONENT__SERIES_COLOR_INDEX;

	/**
	 * The feature id for the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__BACKGROUND = COMPONENT__BACKGROUND;

	/**
	 * The feature id for the '<em><b>Visualization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__VISUALIZATION = COMPONENT__VISUALIZATION;

	/**
	 * The feature id for the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__LAYOUT_CONSTRAINT = COMPONENT__LAYOUT_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__SERIES_CONFIG_GENERATOR = COMPONENT__SERIES_CONFIG_GENERATOR;

	/**
	 * The feature id for the '<em><b>Related Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT__RELATED_ELEMENT = COMPONENT__RELATED_ELEMENT;

	/**
	 * The number of structural features of the '<em>Related Elements Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_ELEMENTS_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum <em>Action Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getActionEnum()
	 * @generated
	 */
	int ACTION_ENUM = 101;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum <em>Anchor Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAnchorEnum()
	 * @generated
	 */
	int ANCHOR_ENUM = 102;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum <em>Anchor Position Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAnchorPositionEnum()
	 * @generated
	 */
	int ANCHOR_POSITION_ENUM = 103;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum <em>Data Plotting Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDataPlottingEnum()
	 * @generated
	 */
	int DATA_PLOTTING_ENUM = 104;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum <em>Display Mode Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDisplayModeEnum()
	 * @generated
	 */
	int DISPLAY_MODE_ENUM = 105;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum <em>Field Alignment Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFieldAlignmentEnum()
	 * @generated
	 */
	int FIELD_ALIGNMENT_ENUM = 106;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum <em>Font Style Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFontStyleEnum()
	 * @generated
	 */
	int FONT_STYLE_ENUM = 107;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum <em>Gradient Direction Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getGradientDirectionEnum()
	 * @generated
	 */
	int GRADIENT_DIRECTION_ENUM = 108;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum <em>Line Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLineEnum()
	 * @generated
	 */
	int LINE_ENUM = 109;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum <em>Orientation Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getOrientationEnum()
	 * @generated
	 */
	int ORIENTATION_ENUM = 110;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum <em>Panel State Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPanelStateEnum()
	 * @generated
	 */
	int PANEL_STATE_ENUM = 111;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum <em>Partition State Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPartitionStateEnum()
	 * @generated
	 */
	int PARTITION_STATE_ENUM = 112;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum <em>Pie Chart Direction Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPieChartDirectionEnum()
	 * @generated
	 */
	int PIE_CHART_DIRECTION_ENUM = 113;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum <em>Placement Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPlacementEnum()
	 * @generated
	 */
	int PLACEMENT_ENUM = 114;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum <em>Plot Shape Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPlotShapeEnum()
	 * @generated
	 */
	int PLOT_SHAPE_ENUM = 115;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum <em>Range Plot Chart Series Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRangePlotChartSeriesTypeEnum()
	 * @generated
	 */
	int RANGE_PLOT_CHART_SERIES_TYPE_ENUM = 116;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum <em>Relative Axis Position Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRelativeAxisPositionEnum()
	 * @generated
	 */
	int RELATIVE_AXIS_POSITION_ENUM = 117;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum <em>Scale Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getScaleEnum()
	 * @generated
	 */
	int SCALE_ENUM = 118;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum <em>Scroll Bar Config Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getScrollBarConfigEnum()
	 * @generated
	 */
	int SCROLL_BAR_CONFIG_ENUM = 119;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum <em>Series Anchor Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSeriesAnchorEnum()
	 * @generated
	 */
	int SERIES_ANCHOR_ENUM = 120;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum <em>Sort Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSortEnum()
	 * @generated
	 */
	int SORT_ENUM = 121;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum <em>Threshold Unit Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getThresholdUnitEnum()
	 * @generated
	 */
	int THRESHOLD_UNIT_ENUM = 122;

	/**
	 * The meta object id for the '<em>Action Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getActionEnumObject()
	 * @generated
	 */
	int ACTION_ENUM_OBJECT = 123;

	/**
	 * The meta object id for the '<em>Anchor Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAnchorEnumObject()
	 * @generated
	 */
	int ANCHOR_ENUM_OBJECT = 124;

	/**
	 * The meta object id for the '<em>Anchor Position Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getAnchorPositionEnumObject()
	 * @generated
	 */
	int ANCHOR_POSITION_ENUM_OBJECT = 125;

	/**
	 * The meta object id for the '<em>Data Plotting Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDataPlottingEnumObject()
	 * @generated
	 */
	int DATA_PLOTTING_ENUM_OBJECT = 126;

	/**
	 * The meta object id for the '<em>Display Mode Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getDisplayModeEnumObject()
	 * @generated
	 */
	int DISPLAY_MODE_ENUM_OBJECT = 127;

	/**
	 * The meta object id for the '<em>Field Alignment Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFieldAlignmentEnumObject()
	 * @generated
	 */
	int FIELD_ALIGNMENT_ENUM_OBJECT = 128;

	/**
	 * The meta object id for the '<em>Font Style Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getFontStyleEnumObject()
	 * @generated
	 */
	int FONT_STYLE_ENUM_OBJECT = 129;

	/**
	 * The meta object id for the '<em>Gradient Direction Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getGradientDirectionEnumObject()
	 * @generated
	 */
	int GRADIENT_DIRECTION_ENUM_OBJECT = 130;

	/**
	 * The meta object id for the '<em>Line Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getLineEnumObject()
	 * @generated
	 */
	int LINE_ENUM_OBJECT = 131;

	/**
	 * The meta object id for the '<em>Orientation Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getOrientationEnumObject()
	 * @generated
	 */
	int ORIENTATION_ENUM_OBJECT = 132;

	/**
	 * The meta object id for the '<em>Panel State Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPanelStateEnumObject()
	 * @generated
	 */
	int PANEL_STATE_ENUM_OBJECT = 133;

	/**
	 * The meta object id for the '<em>Partition State Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPartitionStateEnumObject()
	 * @generated
	 */
	int PARTITION_STATE_ENUM_OBJECT = 134;

	/**
	 * The meta object id for the '<em>Pie Chart Direction Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPieChartDirectionEnumObject()
	 * @generated
	 */
	int PIE_CHART_DIRECTION_ENUM_OBJECT = 135;

	/**
	 * The meta object id for the '<em>Placement Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPlacementEnumObject()
	 * @generated
	 */
	int PLACEMENT_ENUM_OBJECT = 136;

	/**
	 * The meta object id for the '<em>Plot Shape Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getPlotShapeEnumObject()
	 * @generated
	 */
	int PLOT_SHAPE_ENUM_OBJECT = 137;

	/**
	 * The meta object id for the '<em>Range Plot Chart Series Type Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRangePlotChartSeriesTypeEnumObject()
	 * @generated
	 */
	int RANGE_PLOT_CHART_SERIES_TYPE_ENUM_OBJECT = 138;

	/**
	 * The meta object id for the '<em>Relative Axis Position Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getRelativeAxisPositionEnumObject()
	 * @generated
	 */
	int RELATIVE_AXIS_POSITION_ENUM_OBJECT = 139;

	/**
	 * The meta object id for the '<em>Scale Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getScaleEnumObject()
	 * @generated
	 */
	int SCALE_ENUM_OBJECT = 140;

	/**
	 * The meta object id for the '<em>Scroll Bar Config Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getScrollBarConfigEnumObject()
	 * @generated
	 */
	int SCROLL_BAR_CONFIG_ENUM_OBJECT = 141;

	/**
	 * The meta object id for the '<em>Series Anchor Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSeriesAnchorEnumObject()
	 * @generated
	 */
	int SERIES_ANCHOR_ENUM_OBJECT = 142;

	/**
	 * The meta object id for the '<em>Sort Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsConfigurationPackageImpl#getSortEnumObject()
	 * @generated
	 */
	int SORT_ENUM_OBJECT = 143;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition <em>Action Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Definition</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition
	 * @generated
	 */
	EClass getActionDefinition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getText()
	 * @see #getActionDefinition()
	 * @generated
	 */
	EAttribute getActionDefinition_Text();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getType()
	 * @see #getActionDefinition()
	 * @generated
	 */
	EAttribute getActionDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getCommandURL <em>Command URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command URL</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition#getCommandURL()
	 * @see #getActionDefinition()
	 * @generated
	 */
	EAttribute getActionDefinition_CommandURL();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateDataConfig <em>State Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateDataConfig
	 * @generated
	 */
	EClass getStateDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateSeriesConfig <em>State Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateSeriesConfig
	 * @generated
	 */
	EClass getStateSeriesConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization <em>State Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization
	 * @generated
	 */
	EClass getStateVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization#getStateRefID <em>State Ref ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Ref ID</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization#getStateRefID()
	 * @see #getStateVisualization()
	 * @generated
	 */
	EAttribute getStateVisualization_StateRefID();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent <em>Alert Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent
	 * @generated
	 */
	EClass getAlertComponent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent#getThreshold <em>Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threshold</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent#getThreshold()
	 * @see #getAlertComponent()
	 * @generated
	 */
	EAttribute getAlertComponent_Threshold();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration <em>Alert Indicator State Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Indicator State Enumeration</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration
	 * @generated
	 */
	EClass getAlertIndicatorStateEnumeration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getFieldName <em>Field Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Field Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getFieldName()
	 * @see #getAlertIndicatorStateEnumeration()
	 * @generated
	 */
	EAttribute getAlertIndicatorStateEnumeration_FieldName();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getMapping()
	 * @see #getAlertIndicatorStateEnumeration()
	 * @generated
	 */
	EReference getAlertIndicatorStateEnumeration_Mapping();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap <em>Alert Indicator State Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Indicator State Map</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap
	 * @generated
	 */
	EClass getAlertIndicatorStateMap();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getFieldValue <em>Field Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Field Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getFieldValue()
	 * @see #getAlertIndicatorStateMap()
	 * @generated
	 */
	EAttribute getAlertIndicatorStateMap_FieldValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getIndicatorState <em>Indicator State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Indicator State</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getIndicatorState()
	 * @see #getAlertIndicatorStateMap()
	 * @generated
	 */
	EAttribute getAlertIndicatorStateMap_IndicatorState();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig <em>Alert Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig
	 * @generated
	 */
	EClass getAlertSeriesConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig#getIndicatorStateEnumeration <em>Indicator State Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indicator State Enumeration</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig#getIndicatorStateEnumeration()
	 * @see #getAlertSeriesConfig()
	 * @generated
	 */
	EReference getAlertSeriesConfig_IndicatorStateEnumeration();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertVisualization <em>Alert Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertVisualization
	 * @generated
	 */
	EClass getAlertVisualization();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization <em>Area Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Area Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization
	 * @generated
	 */
	EClass getAreaChartVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization#getFillOpacity <em>Fill Opacity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fill Opacity</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization#getFillOpacity()
	 * @see #getAreaChartVisualization()
	 * @generated
	 */
	EAttribute getAreaChartVisualization_FillOpacity();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AssetPage <em>Asset Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asset Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AssetPage
	 * @generated
	 */
	EClass getAssetPage();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat <em>Background Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Background Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat
	 * @generated
	 */
	EClass getBackgroundFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat#getGradientDirection <em>Gradient Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gradient Direction</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat#getGradientDirection()
	 * @see #getBackgroundFormat()
	 * @generated
	 */
	EAttribute getBackgroundFormat_GradientDirection();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization <em>Bar Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bar Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization
	 * @generated
	 */
	EClass getBarChartVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getWidth()
	 * @see #getBarChartVisualization()
	 * @generated
	 */
	EAttribute getBarChartVisualization_Width();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getTopCapThickness <em>Top Cap Thickness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Top Cap Thickness</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getTopCapThickness()
	 * @see #getBarChartVisualization()
	 * @generated
	 */
	EAttribute getBarChartVisualization_TopCapThickness();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOverlapPercentage <em>Overlap Percentage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Overlap Percentage</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOverlapPercentage()
	 * @see #getBarChartVisualization()
	 * @generated
	 */
	EAttribute getBarChartVisualization_OverlapPercentage();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOrientation <em>Orientation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOrientation()
	 * @see #getBarChartVisualization()
	 * @generated
	 */
	EAttribute getBarChartVisualization_Orientation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BluePrintComponent <em>Blue Print Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Blue Print Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BluePrintComponent
	 * @generated
	 */
	EClass getBluePrintComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig <em>Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig
	 * @generated
	 */
	EClass getCategoryGuidelineConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderName <em>Header Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderName()
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getCategoryGuidelineConfig_HeaderName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderFormatStyle <em>Header Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderFormatStyle()
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	EReference getCategoryGuidelineConfig_HeaderFormatStyle();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getLabelFormatStyle <em>Label Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Label Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getLabelFormatStyle()
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	EReference getCategoryGuidelineConfig_LabelFormatStyle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getSortOrder <em>Sort Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sort Order</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getSortOrder()
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getCategoryGuidelineConfig_SortOrder();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#isDuplicatesAllowed <em>Duplicates Allowed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duplicates Allowed</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#isDuplicatesAllowed()
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getCategoryGuidelineConfig_DuplicatesAllowed();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryDataConfig <em>Chart Category Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Category Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryDataConfig
	 * @generated
	 */
	EClass getChartCategoryDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig <em>Chart Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Category Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig
	 * @generated
	 */
	EClass getChartCategoryGuidelineConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRelativePosition <em>Relative Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relative Position</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRelativePosition()
	 * @see #getChartCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartCategoryGuidelineConfig_RelativePosition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getPlacement <em>Placement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Placement</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getPlacement()
	 * @see #getChartCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartCategoryGuidelineConfig_Placement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRotation <em>Rotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rotation</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRotation()
	 * @see #getChartCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartCategoryGuidelineConfig_Rotation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getSkipFactor <em>Skip Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Skip Factor</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getSkipFactor()
	 * @see #getChartCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartCategoryGuidelineConfig_SkipFactor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent <em>Chart Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent
	 * @generated
	 */
	EClass getChartComponent();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getPlotArea <em>Plot Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Plot Area</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getPlotArea()
	 * @see #getChartComponent()
	 * @generated
	 */
	EReference getChartComponent_PlotArea();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getLegend <em>Legend</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Legend</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getLegend()
	 * @see #getChartComponent()
	 * @generated
	 */
	EReference getChartComponent_Legend();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getCategoryGuidelineConfig <em>Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Category Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getCategoryGuidelineConfig()
	 * @see #getChartComponent()
	 * @generated
	 */
	EReference getChartComponent_CategoryGuidelineConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getValueGuidelineConfig <em>Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getValueGuidelineConfig()
	 * @see #getChartComponent()
	 * @generated
	 */
	EReference getChartComponent_ValueGuidelineConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet <em>Chart Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Component Color Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet
	 * @generated
	 */
	EClass getChartComponentColorSet();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getSeriesColor <em>Series Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Series Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getSeriesColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EReference getChartComponentColorSet_SeriesColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineLabelFontColor <em>Guide Line Label Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guide Line Label Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineLabelFontColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_GuideLineLabelFontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineValueLabelFontColor <em>Guide Line Value Label Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guide Line Value Label Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineValueLabelFontColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_GuideLineValueLabelFontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getDataPointLabelFontColor <em>Data Point Label Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Point Label Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getDataPointLabelFontColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_DataPointLabelFontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getTopCapColor <em>Top Cap Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Top Cap Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getTopCapColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_TopCapColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieEdgeColor <em>Pie Edge Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pie Edge Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieEdgeColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_PieEdgeColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieDropShadowColor <em>Pie Drop Shadow Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pie Drop Shadow Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieDropShadowColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_PieDropShadowColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getLineDropShadowColor <em>Line Drop Shadow Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Drop Shadow Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getLineDropShadowColor()
	 * @see #getChartComponentColorSet()
	 * @generated
	 */
	EAttribute getChartComponentColorSet_LineDropShadowColor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig <em>Chart Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig
	 * @generated
	 */
	EClass getChartSeriesConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getAnchor <em>Anchor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Anchor</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getAnchor()
	 * @see #getChartSeriesConfig()
	 * @generated
	 */
	EAttribute getChartSeriesConfig_Anchor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getValueLabelStyle <em>Value Label Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Label Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getValueLabelStyle()
	 * @see #getChartSeriesConfig()
	 * @generated
	 */
	EReference getChartSeriesConfig_ValueLabelStyle();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueDataConfig <em>Chart Value Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Value Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueDataConfig
	 * @generated
	 */
	EClass getChartValueDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig <em>Chart Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Value Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig
	 * @generated
	 */
	EClass getChartValueGuidelineConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getLabelFormatStyle <em>Label Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Label Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getLabelFormatStyle()
	 * @see #getChartValueGuidelineConfig()
	 * @generated
	 */
	EReference getChartValueGuidelineConfig_LabelFormatStyle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getHeaderName <em>Header Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getHeaderName()
	 * @see #getChartValueGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartValueGuidelineConfig_HeaderName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getRelativePosition <em>Relative Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relative Position</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getRelativePosition()
	 * @see #getChartValueGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartValueGuidelineConfig_RelativePosition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getScale()
	 * @see #getChartValueGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartValueGuidelineConfig_Scale();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getDivision <em>Division</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Division</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getDivision()
	 * @see #getChartValueGuidelineConfig()
	 * @generated
	 */
	EAttribute getChartValueGuidelineConfig_Division();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization <em>Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization
	 * @generated
	 */
	EClass getChartVisualization();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization#getSharedCategoryDataConfig <em>Shared Category Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared Category Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization#getSharedCategoryDataConfig()
	 * @see #getChartVisualization()
	 * @generated
	 */
	EReference getChartVisualization_SharedCategoryDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierComponent <em>Classifier Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierComponent
	 * @generated
	 */
	EClass getClassifierComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierSeriesConfig <em>Classifier Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierSeriesConfig
	 * @generated
	 */
	EClass getClassifierSeriesConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierVisualization <em>Classifier Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierVisualization
	 * @generated
	 */
	EClass getClassifierVisualization();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getDisplayName()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getHelpText <em>Help Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getHelpText()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_HelpText();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getComponentColorSetIndex <em>Component Color Set Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Color Set Index</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getComponentColorSetIndex()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ComponentColorSetIndex();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesColorIndex <em>Series Color Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Series Color Index</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesColorIndex()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_SeriesColorIndex();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getBackground <em>Background</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Background</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getBackground()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Background();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getVisualization <em>Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getVisualization()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Visualization();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getLayoutConstraint <em>Layout Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Layout Constraint</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getLayoutConstraint()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_LayoutConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesConfigGenerator <em>Series Config Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Series Config Generator</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesConfigGenerator()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_SeriesConfigGenerator();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getRelatedElement <em>Related Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Related Element</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component#getRelatedElement()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_RelatedElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet <em>Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Color Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet
	 * @generated
	 */
	EClass getComponentColorSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet#getDisplayName()
	 * @see #getComponentColorSet()
	 * @generated
	 */
	EAttribute getComponentColorSet_DisplayName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage <em>Dashboard Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dashboard Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage
	 * @generated
	 */
	EClass getDashboardPage();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig <em>Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig
	 * @generated
	 */
	EClass getDataConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getExtractor <em>Extractor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extractor</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getExtractor()
	 * @see #getDataConfig()
	 * @generated
	 */
	EReference getDataConfig_Extractor();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getFormatter <em>Formatter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Formatter</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getFormatter()
	 * @see #getDataConfig()
	 * @generated
	 */
	EReference getDataConfig_Formatter();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor <em>Data Extractor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Extractor</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor
	 * @generated
	 */
	EClass getDataExtractor();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor#getSourceField <em>Source Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Field</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor#getSourceField()
	 * @see #getDataExtractor()
	 * @generated
	 */
	EReference getDataExtractor_SourceField();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat <em>Data Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat
	 * @generated
	 */
	EClass getDataFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getToolTipFormat <em>Tool Tip Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tool Tip Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getToolTipFormat()
	 * @see #getDataFormat()
	 * @generated
	 */
	EAttribute getDataFormat_ToolTipFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getDisplayFormat <em>Display Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getDisplayFormat()
	 * @see #getDataFormat()
	 * @generated
	 */
	EAttribute getDataFormat_DisplayFormat();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getFormatStyle <em>Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getFormatStyle()
	 * @see #getDataFormat()
	 * @generated
	 */
	EReference getDataFormat_FormatStyle();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getActionConfig <em>Action Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Action Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getActionConfig()
	 * @see #getDataFormat()
	 * @generated
	 */
	EReference getDataFormat_ActionConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#isShowLabel <em>Show Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Label</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#isShowLabel()
	 * @see #getDataFormat()
	 * @generated
	 */
	EAttribute getDataFormat_ShowLabel();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule <em>Action Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Rule</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule
	 * @generated
	 */
	EClass getActionRule();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDataSource <em>Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Source</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDataSource()
	 * @see #getActionRule()
	 * @generated
	 */
	EReference getActionRule_DataSource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThreshold <em>Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threshold</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThreshold()
	 * @see #getActionRule()
	 * @generated
	 */
	EAttribute getActionRule_Threshold();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThresholdUnit <em>Threshold Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threshold Unit</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThresholdUnit()
	 * @see #getActionRule()
	 * @generated
	 */
	EAttribute getActionRule_ThresholdUnit();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getAlert <em>Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Alert</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getAlert()
	 * @see #getActionRule()
	 * @generated
	 */
	EReference getActionRule_Alert();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDrillableFields <em>Drillable Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Drillable Fields</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDrillableFields()
	 * @see #getActionRule()
	 * @generated
	 */
	EReference getActionRule_DrillableFields();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getParams()
	 * @see #getActionRule()
	 * @generated
	 */
	EReference getActionRule_Params();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource <em>Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Source</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataSource
	 * @generated
	 */
	EClass getDataSource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getQuery()
	 * @see #getDataSource()
	 * @generated
	 */
	EAttribute getDataSource_Query();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getSrcElement <em>Src Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src Element</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getSrcElement()
	 * @see #getDataSource()
	 * @generated
	 */
	EReference getDataSource_SrcElement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataSource#getParams()
	 * @see #getDataSource()
	 * @generated
	 */
	EReference getDataSource_Params();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam <em>Query Param</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query Param</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam
	 * @generated
	 */
	EClass getQueryParam();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam#getValue()
	 * @see #getQueryParam()
	 * @generated
	 */
	EAttribute getQueryParam_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam#getType()
	 * @see #getQueryParam()
	 * @generated
	 */
	EAttribute getQueryParam_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert <em>Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Alert
	 * @generated
	 */
	EClass getAlert();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Alert#getAction()
	 * @see #getAlert()
	 * @generated
	 */
	EReference getAlert_Action();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Alert#isEnabled()
	 * @see #getAlert()
	 * @generated
	 */
	EAttribute getAlert_Enabled();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert <em>Range Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Alert</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert
	 * @generated
	 */
	EClass getRangeAlert();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getLowerValue <em>Lower Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getLowerValue()
	 * @see #getRangeAlert()
	 * @generated
	 */
	EAttribute getRangeAlert_LowerValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getUpperValue <em>Upper Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getUpperValue()
	 * @see #getRangeAlert()
	 * @generated
	 */
	EAttribute getRangeAlert_UpperValue();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction <em>Alert Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alert Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction
	 * @generated
	 */
	EClass getAlertAction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction#isEnabled()
	 * @see #getAlertAction()
	 * @generated
	 */
	EAttribute getAlertAction_Enabled();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction <em>Visual Alert Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Visual Alert Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction
	 * @generated
	 */
	EClass getVisualAlertAction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFillColor <em>Fill Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fill Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFillColor()
	 * @see #getVisualAlertAction()
	 * @generated
	 */
	EAttribute getVisualAlertAction_FillColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontSize <em>Font Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontSize()
	 * @see #getVisualAlertAction()
	 * @generated
	 */
	EAttribute getVisualAlertAction_FontSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontStyle <em>Font Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontStyle()
	 * @see #getVisualAlertAction()
	 * @generated
	 */
	EAttribute getVisualAlertAction_FontStyle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontColor <em>Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontColor()
	 * @see #getVisualAlertAction()
	 * @generated
	 */
	EAttribute getVisualAlertAction_FontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getDisplayFormat <em>Display Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getDisplayFormat()
	 * @see #getVisualAlertAction()
	 * @generated
	 */
	EAttribute getVisualAlertAction_DisplayFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getTooltipFormat <em>Tooltip Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tooltip Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getTooltipFormat()
	 * @see #getVisualAlertAction()
	 * @generated
	 */
	EAttribute getVisualAlertAction_TooltipFormat();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAlertComponentElem <em>Alert Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Alert Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAlertComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AlertComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAssetPageElem <em>Asset Page Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Asset Page Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getAssetPageElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AssetPageElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBluePrintComponentElem <em>Blue Print Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Blue Print Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBluePrintComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BluePrintComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentColorSetElem <em>Chart Component Color Set Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Chart Component Color Set Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentColorSetElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ChartComponentColorSetElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentElem <em>Chart Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Chart Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getChartComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ChartComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getClassifierComponentElem <em>Classifier Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Classifier Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getClassifierComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ClassifierComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getDashboardPageElem <em>Dashboard Page Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dashboard Page Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getDashboardPageElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DashboardPageElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigElem <em>Be Views Config Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Config Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsConfigElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigVersionElem <em>Be Views Config Version Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Config Version Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsConfigVersionElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsConfigVersionElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsElementElem <em>Be Views Element Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Element Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsElementElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsElementElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsFooterElem <em>Be Views Footer Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Footer Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsFooterElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsFooterElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsHeaderElem <em>Be Views Header Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Header Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsHeaderElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsHeaderElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsLoginElem <em>Be Views Login Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Login Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsLoginElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsLoginElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsSkinElem <em>Be Views Skin Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Be Views Skin Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getBeViewsSkinElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BeViewsSkinElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPageSelectorComponentElem <em>Page Selector Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Page Selector Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPageSelectorComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_PageSelectorComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPanelElem <em>Panel Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Panel Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getPanelElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_PanelElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getStateMachineComponentElem <em>State Machine Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>State Machine Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getStateMachineComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_StateMachineComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getQueryManagerComponentElem <em>Query Manager Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Query Manager Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getQueryManagerComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_QueryManagerComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getRelatedAssetsComponentElem <em>Related Assets Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Related Assets Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getRelatedAssetsComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RelatedAssetsComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchPageElem <em>Search Page Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Search Page Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchPageElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SearchPageElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchViewComponentElem <em>Search View Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Search View Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getSearchViewComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SearchViewComponentElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentColorSetElem <em>Text Component Color Set Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Text Component Color Set Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentColorSetElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TextComponentColorSetElem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentElem <em>Text Component Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Text Component Elem</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot#getTextComponentElem()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TextComponentElem();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayout <em>Flow Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Layout</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayout
	 * @generated
	 */
	EClass getFlowLayout();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint <em>Flow Layout Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Layout Constraint</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint
	 * @generated
	 */
	EClass getFlowLayoutConstraint();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentRowSpan <em>Component Row Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Row Span</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentRowSpan()
	 * @see #getFlowLayoutConstraint()
	 * @generated
	 */
	EAttribute getFlowLayoutConstraint_ComponentRowSpan();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentColSpan <em>Component Col Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Col Span</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint#getComponentColSpan()
	 * @see #getFlowLayoutConstraint()
	 * @generated
	 */
	EAttribute getFlowLayoutConstraint_ComponentColSpan();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat <em>Foreground Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foreground Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat
	 * @generated
	 */
	EClass getForegroundFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat#getLine <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat#getLine()
	 * @see #getForegroundFormat()
	 * @generated
	 */
	EAttribute getForegroundFormat_Line();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle <em>Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle
	 * @generated
	 */
	EClass getFormatStyle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontSize <em>Font Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontSize()
	 * @see #getFormatStyle()
	 * @generated
	 */
	EAttribute getFormatStyle_FontSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontStyle <em>Font Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontStyle()
	 * @see #getFormatStyle()
	 * @generated
	 */
	EAttribute getFormatStyle_FontStyle();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat <em>Indicator Field Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Indicator Field Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat
	 * @generated
	 */
	EClass getIndicatorFieldFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#isShowTextValue <em>Show Text Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Text Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#isShowTextValue()
	 * @see #getIndicatorFieldFormat()
	 * @generated
	 */
	EAttribute getIndicatorFieldFormat_ShowTextValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#getTextValueAnchor <em>Text Value Anchor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text Value Anchor</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#getTextValueAnchor()
	 * @see #getIndicatorFieldFormat()
	 * @generated
	 */
	EAttribute getIndicatorFieldFormat_TextValueAnchor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View#getDisplayName()
	 * @see #getView()
	 * @generated
	 */
	EAttribute getView_DisplayName();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getDefaultPage <em>Default Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Default Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View#getDefaultPage()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_DefaultPage();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getAccessiblePage <em>Accessible Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Accessible Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View#getAccessiblePage()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_AccessiblePage();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getSkin <em>Skin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Skin</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View#getSkin()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Skin();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getLocale <em>Locale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Locale</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View#getLocale()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Locale();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View#getAttribute()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Attribute();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute <em>View Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View Attribute</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute
	 * @generated
	 */
	EClass getViewAttribute();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute#getValue()
	 * @see #getViewAttribute()
	 * @generated
	 */
	EAttribute getViewAttribute_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion <em>View Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View Version</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion
	 * @generated
	 */
	EClass getViewVersion();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMajorVersionNumber <em>Major Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Major Version Number</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMajorVersionNumber()
	 * @see #getViewVersion()
	 * @generated
	 */
	EAttribute getViewVersion_MajorVersionNumber();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMinorVersionNumber <em>Minor Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minor Version Number</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getMinorVersionNumber()
	 * @see #getViewVersion()
	 * @generated
	 */
	EAttribute getViewVersion_MinorVersionNumber();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getPointVersionNumber <em>Point Version Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Point Version Number</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getPointVersionNumber()
	 * @see #getViewVersion()
	 * @generated
	 */
	EAttribute getViewVersion_PointVersionNumber();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getDescription1 <em>Description1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description1</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion#getDescription1()
	 * @see #getViewVersion()
	 * @generated
	 */
	EAttribute getViewVersion_Description1();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement <em>BE Views Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BE Views Element</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement
	 * @generated
	 */
	EClass getBEViewsElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementIdentifier <em>Original Element Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Original Element Identifier</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementIdentifier()
	 * @see #getBEViewsElement()
	 * @generated
	 */
	EAttribute getBEViewsElement_OriginalElementIdentifier();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementVersion <em>Original Element Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Original Element Version</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementVersion()
	 * @see #getBEViewsElement()
	 * @generated
	 */
	EAttribute getBEViewsElement_OriginalElementVersion();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getVersion()
	 * @see #getBEViewsElement()
	 * @generated
	 */
	EAttribute getBEViewsElement_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Footer <em>Footer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Footer</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Footer
	 * @generated
	 */
	EClass getFooter();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Footer#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Footer#getLink()
	 * @see #getFooter()
	 * @generated
	 */
	EAttribute getFooter_Link();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Header</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header
	 * @generated
	 */
	EClass getHeader();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header#getTitle()
	 * @see #getHeader()
	 * @generated
	 */
	EAttribute getHeader_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingImage <em>Branding Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Branding Image</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingImage()
	 * @see #getHeader()
	 * @generated
	 */
	EAttribute getHeader_BrandingImage();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingText <em>Branding Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Branding Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingText()
	 * @see #getHeader()
	 * @generated
	 */
	EAttribute getHeader_BrandingText();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header#getLink()
	 * @see #getHeader()
	 * @generated
	 */
	EAttribute getHeader_Link();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getHeaderLink <em>Header Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Header Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header#getHeaderLink()
	 * @see #getHeader()
	 * @generated
	 */
	EReference getHeader_HeaderLink();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale <em>View Locale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View Locale</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale
	 * @generated
	 */
	EClass getViewLocale();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getDisplayName()
	 * @see #getViewLocale()
	 * @generated
	 */
	EAttribute getViewLocale_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getLocale <em>Locale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locale</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getLocale()
	 * @see #getViewLocale()
	 * @generated
	 */
	EAttribute getViewLocale_Locale();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeZone <em>Time Zone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Zone</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeZone()
	 * @see #getViewLocale()
	 * @generated
	 */
	EAttribute getViewLocale_TimeZone();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeFormat <em>Time Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeFormat()
	 * @see #getViewLocale()
	 * @generated
	 */
	EAttribute getViewLocale_TimeFormat();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Login</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Login
	 * @generated
	 */
	EClass getLogin();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Login#getTitle()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login#getImageURL <em>Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image URL</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Login#getImageURL()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_ImageURL();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin <em>Skin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Skin</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin
	 * @generated
	 */
	EClass getSkin();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDisplayName()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_DisplayName();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDefaultComponentColorSet <em>Default Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Default Component Color Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDefaultComponentColorSet()
	 * @see #getSkin()
	 * @generated
	 */
	EReference getSkin_DefaultComponentColorSet();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentColorSet <em>Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Component Color Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentColorSet()
	 * @see #getSkin()
	 * @generated
	 */
	EReference getSkin_ComponentColorSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getFontColor <em>Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getFontColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_FontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundColor <em>Component Back Ground Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Back Ground Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_ComponentBackGroundColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundGradientEndColor <em>Component Back Ground Gradient End Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Back Ground Gradient End Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundGradientEndColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_ComponentBackGroundGradientEndColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentForeGroundColor <em>Component Fore Ground Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Fore Ground Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentForeGroundColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_ComponentForeGroundColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundColor <em>Visualization Back Ground Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visualization Back Ground Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_VisualizationBackGroundColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundGradientEndColor <em>Visualization Back Ground Gradient End Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visualization Back Ground Gradient End Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundGradientEndColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_VisualizationBackGroundGradientEndColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationForeGroundColor <em>Visualization Fore Ground Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visualization Fore Ground Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationForeGroundColor()
	 * @see #getSkin()
	 * @generated
	 */
	EAttribute getSkin_VisualizationForeGroundColor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Layout</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Layout
	 * @generated
	 */
	EClass getLayout();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#isRepositioningAllowed <em>Repositioning Allowed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repositioning Allowed</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Layout#isRepositioningAllowed()
	 * @see #getLayout()
	 * @generated
	 */
	EAttribute getLayout_RepositioningAllowed();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentWidth <em>Component Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Width</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentWidth()
	 * @see #getLayout()
	 * @generated
	 */
	EAttribute getLayout_ComponentWidth();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentHeight <em>Component Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Height</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Layout#getComponentHeight()
	 * @see #getLayout()
	 * @generated
	 */
	EAttribute getLayout_ComponentHeight();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LayoutConstraint <em>Layout Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Layout Constraint</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LayoutConstraint
	 * @generated
	 */
	EClass getLayoutConstraint();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat <em>Legend Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Legend Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat
	 * @generated
	 */
	EClass getLegendFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getOrientation <em>Orientation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getOrientation()
	 * @see #getLegendFormat()
	 * @generated
	 */
	EAttribute getLegendFormat_Orientation();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getAnchor <em>Anchor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Anchor</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getAnchor()
	 * @see #getLegendFormat()
	 * @generated
	 */
	EAttribute getLegendFormat_Anchor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization <em>Line Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization
	 * @generated
	 */
	EClass getLineChartVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineThickness <em>Line Thickness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Thickness</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineThickness()
	 * @see #getLineChartVisualization()
	 * @generated
	 */
	EAttribute getLineChartVisualization_LineThickness();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineSmoothness <em>Line Smoothness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Smoothness</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineSmoothness()
	 * @see #getLineChartVisualization()
	 * @generated
	 */
	EAttribute getLineChartVisualization_LineSmoothness();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getDataPlotting <em>Data Plotting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Plotting</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getDataPlotting()
	 * @see #getLineChartVisualization()
	 * @generated
	 */
	EAttribute getLineChartVisualization_DataPlotting();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShape <em>Plot Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plot Shape</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShape()
	 * @see #getLineChartVisualization()
	 * @generated
	 */
	EAttribute getLineChartVisualization_PlotShape();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plot Shape Dimension</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShapeDimension()
	 * @see #getLineChartVisualization()
	 * @generated
	 */
	EAttribute getLineChartVisualization_PlotShapeDimension();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig <em>One Dim Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One Dim Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig
	 * @generated
	 */
	EClass getOneDimSeriesConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig#getValueDataConfig <em>Value Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig#getValueDataConfig()
	 * @see #getOneDimSeriesConfig()
	 * @generated
	 */
	EReference getOneDimSeriesConfig_ValueDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization <em>One Dim Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>One Dim Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization
	 * @generated
	 */
	EClass getOneDimVisualization();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization#getValueGuidelineConfig <em>Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization#getValueGuidelineConfig()
	 * @see #getOneDimVisualization()
	 * @generated
	 */
	EReference getOneDimVisualization_ValueGuidelineConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayName()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayMode <em>Display Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Mode</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Page#getDisplayMode()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_DisplayMode();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getPartition <em>Partition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Partition</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Page#getPartition()
	 * @see #getPage()
	 * @generated
	 */
	EReference getPage_Partition();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page#getVisualization <em>Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Page#getVisualization()
	 * @see #getPage()
	 * @generated
	 */
	EReference getPage_Visualization();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PageSelectorComponent <em>Page Selector Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page Selector Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PageSelectorComponent
	 * @generated
	 */
	EClass getPageSelectorComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PageVisualization <em>Page Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PageVisualization
	 * @generated
	 */
	EClass getPageVisualization();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel <em>Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Panel</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel
	 * @generated
	 */
	EClass getPanel();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getDisplayName()
	 * @see #getPanel()
	 * @generated
	 */
	EAttribute getPanel_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getSpan <em>Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Span</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getSpan()
	 * @see #getPanel()
	 * @generated
	 */
	EAttribute getPanel_Span();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getScrollBar <em>Scroll Bar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scroll Bar</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getScrollBar()
	 * @see #getPanel()
	 * @generated
	 */
	EAttribute getPanel_ScrollBar();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getState()
	 * @see #getPanel()
	 * @generated
	 */
	EAttribute getPanel_State();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMaximizable <em>Maximizable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maximizable</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMaximizable()
	 * @see #getPanel()
	 * @generated
	 */
	EAttribute getPanel_Maximizable();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMinimizable <em>Minimizable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minimizable</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#isMinimizable()
	 * @see #getPanel()
	 * @generated
	 */
	EAttribute getPanel_Minimizable();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getComponent()
	 * @see #getPanel()
	 * @generated
	 */
	EReference getPanel_Component();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getLayout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Layout</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel#getLayout()
	 * @see #getPanel()
	 * @generated
	 */
	EReference getPanel_Layout();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition <em>Partition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Partition</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition
	 * @generated
	 */
	EClass getPartition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getDisplayName()
	 * @see #getPartition()
	 * @generated
	 */
	EAttribute getPartition_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#isVertical <em>Vertical</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vertical</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition#isVertical()
	 * @see #getPartition()
	 * @generated
	 */
	EAttribute getPartition_Vertical();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getSpan <em>Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Span</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getSpan()
	 * @see #getPartition()
	 * @generated
	 */
	EAttribute getPartition_Span();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getState()
	 * @see #getPartition()
	 * @generated
	 */
	EAttribute getPartition_State();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getPanel <em>Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Panel</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition#getPanel()
	 * @see #getPartition()
	 * @generated
	 */
	EReference getPartition_Panel();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization <em>Pie Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pie Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization
	 * @generated
	 */
	EClass getPieChartVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getStartingAngle <em>Starting Angle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Starting Angle</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getStartingAngle()
	 * @see #getPieChartVisualization()
	 * @generated
	 */
	EAttribute getPieChartVisualization_StartingAngle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getDirection()
	 * @see #getPieChartVisualization()
	 * @generated
	 */
	EAttribute getPieChartVisualization_Direction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getSector <em>Sector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sector</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization#getSector()
	 * @see #getPieChartVisualization()
	 * @generated
	 */
	EAttribute getPieChartVisualization_Sector();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat <em>Plot Area Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plot Area Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat
	 * @generated
	 */
	EClass getPlotAreaFormat();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getBackground <em>Background</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Background</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getBackground()
	 * @see #getPlotAreaFormat()
	 * @generated
	 */
	EReference getPlotAreaFormat_Background();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getForeground <em>Foreground</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Foreground</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getForeground()
	 * @see #getPlotAreaFormat()
	 * @generated
	 */
	EReference getPlotAreaFormat_Foreground();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent <em>State Machine Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent
	 * @generated
	 */
	EClass getStateMachineComponent();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>State Machine</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateMachine()
	 * @see #getStateMachineComponent()
	 * @generated
	 */
	EReference getStateMachineComponent_StateMachine();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationWidth <em>State Visualization Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Visualization Width</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationWidth()
	 * @see #getStateMachineComponent()
	 * @generated
	 */
	EAttribute getStateMachineComponent_StateVisualizationWidth();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationHeight <em>State Visualization Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State Visualization Height</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent#getStateVisualizationHeight()
	 * @see #getStateMachineComponent()
	 * @generated
	 */
	EAttribute getStateMachineComponent_StateVisualizationHeight();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueOption <em>Value Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Option</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueOption
	 * @generated
	 */
	EClass getValueOption();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ConstantValueOption <em>Constant Value Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant Value Option</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ConstantValueOption
	 * @generated
	 */
	EClass getConstantValueOption();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ConstantValueOption#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ConstantValueOption#getValue()
	 * @see #getConstantValueOption()
	 * @generated
	 */
	EAttribute getConstantValueOption_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption <em>Field Reference Value Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Reference Value Option</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption
	 * @generated
	 */
	EClass getFieldReferenceValueOption();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption#getField <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Field</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption#getField()
	 * @see #getFieldReferenceValueOption()
	 * @generated
	 */
	EReference getFieldReferenceValueOption_Field();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat <em>Progress Bar Field Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Progress Bar Field Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat
	 * @generated
	 */
	EClass getProgressBarFieldFormat();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMaxValue <em>Max Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMaxValue()
	 * @see #getProgressBarFieldFormat()
	 * @generated
	 */
	EReference getProgressBarFieldFormat_MaxValue();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMinValue <em>Min Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Min Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMinValue()
	 * @see #getProgressBarFieldFormat()
	 * @generated
	 */
	EReference getProgressBarFieldFormat_MinValue();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryManagerComponent <em>Query Manager Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query Manager Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.QueryManagerComponent
	 * @generated
	 */
	EClass getQueryManagerComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig <em>Range Plot Chart Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Plot Chart Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig
	 * @generated
	 */
	EClass getRangePlotChartSeriesConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig#getType()
	 * @see #getRangePlotChartSeriesConfig()
	 * @generated
	 */
	EAttribute getRangePlotChartSeriesConfig_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization <em>Range Plot Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Plot Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization
	 * @generated
	 */
	EClass getRangePlotChartVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShape <em>Plot Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plot Shape</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShape()
	 * @see #getRangePlotChartVisualization()
	 * @generated
	 */
	EAttribute getRangePlotChartVisualization_PlotShape();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plot Shape Dimension</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShapeDimension()
	 * @see #getRangePlotChartVisualization()
	 * @generated
	 */
	EAttribute getRangePlotChartVisualization_PlotShapeDimension();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerThickness <em>Whisker Thickness</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Whisker Thickness</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerThickness()
	 * @see #getRangePlotChartVisualization()
	 * @generated
	 */
	EAttribute getRangePlotChartVisualization_WhiskerThickness();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerWidth <em>Whisker Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Whisker Width</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerWidth()
	 * @see #getRangePlotChartVisualization()
	 * @generated
	 */
	EAttribute getRangePlotChartVisualization_WhiskerWidth();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getOrientation <em>Orientation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getOrientation()
	 * @see #getRangePlotChartVisualization()
	 * @generated
	 */
	EAttribute getRangePlotChartVisualization_Orientation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelatedAssetsComponent <em>Related Assets Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related Assets Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelatedAssetsComponent
	 * @generated
	 */
	EClass getRelatedAssetsComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization <em>Scatter Plot Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scatter Plot Chart Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization
	 * @generated
	 */
	EClass getScatterPlotChartVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShape <em>Plot Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plot Shape</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShape()
	 * @see #getScatterPlotChartVisualization()
	 * @generated
	 */
	EAttribute getScatterPlotChartVisualization_PlotShape();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plot Shape Dimension</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShapeDimension()
	 * @see #getScatterPlotChartVisualization()
	 * @generated
	 */
	EAttribute getScatterPlotChartVisualization_PlotShapeDimension();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SearchPage <em>Search Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Search Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SearchPage
	 * @generated
	 */
	EClass getSearchPage();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SearchViewComponent <em>Search View Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Search View Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SearchViewComponent
	 * @generated
	 */
	EClass getSearchViewComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor <em>Series Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Series Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor
	 * @generated
	 */
	EClass getSeriesColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getBaseColor <em>Base Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getBaseColor()
	 * @see #getSeriesColor()
	 * @generated
	 */
	EAttribute getSeriesColor_BaseColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getHighlightColor <em>Highlight Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Highlight Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getHighlightColor()
	 * @see #getSeriesColor()
	 * @generated
	 */
	EAttribute getSeriesColor_HighlightColor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig <em>Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig
	 * @generated
	 */
	EClass getSeriesConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getDisplayName()
	 * @see #getSeriesConfig()
	 * @generated
	 */
	EAttribute getSeriesConfig_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getToolTip <em>Tool Tip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tool Tip</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getToolTip()
	 * @see #getSeriesConfig()
	 * @generated
	 */
	EAttribute getSeriesConfig_ToolTip();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getActionRule <em>Action Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action Rule</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getActionRule()
	 * @see #getSeriesConfig()
	 * @generated
	 */
	EReference getSeriesConfig_ActionRule();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getQueryLink <em>Query Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Query Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getQueryLink()
	 * @see #getSeriesConfig()
	 * @generated
	 */
	EReference getSeriesConfig_QueryLink();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRollOverConfig <em>Roll Over Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Roll Over Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRollOverConfig()
	 * @see #getSeriesConfig()
	 * @generated
	 */
	EReference getSeriesConfig_RollOverConfig();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRelatedElement <em>Related Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Related Element</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRelatedElement()
	 * @see #getSeriesConfig()
	 * @generated
	 */
	EReference getSeriesConfig_RelatedElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator <em>Series Config Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Series Config Generator</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator
	 * @generated
	 */
	EClass getSeriesConfigGenerator();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getField <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Field</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getField()
	 * @see #getSeriesConfigGenerator()
	 * @generated
	 */
	EReference getSeriesConfigGenerator_Field();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getCondition()
	 * @see #getSeriesConfigGenerator()
	 * @generated
	 */
	EAttribute getSeriesConfigGenerator_Condition();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryDataConfig <em>Text Category Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Category Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryDataConfig
	 * @generated
	 */
	EClass getTextCategoryDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig <em>Text Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Category Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig
	 * @generated
	 */
	EClass getTextCategoryGuidelineConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Alignment</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getHeaderAlignment()
	 * @see #getTextCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getTextCategoryGuidelineConfig_HeaderAlignment();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getLabelAlignment <em>Label Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Alignment</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getLabelAlignment()
	 * @see #getTextCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getTextCategoryGuidelineConfig_LabelAlignment();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getWidth()
	 * @see #getTextCategoryGuidelineConfig()
	 * @generated
	 */
	EAttribute getTextCategoryGuidelineConfig_Width();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent <em>Text Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent
	 * @generated
	 */
	EClass getTextComponent();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet <em>Text Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Component Color Set</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet
	 * @generated
	 */
	EClass getTextComponentColorSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderColor <em>Header Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_HeaderColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderRollOverColor <em>Header Roll Over Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Roll Over Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderRollOverColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_HeaderRollOverColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderTextFontColor <em>Header Text Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Text Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderTextFontColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_HeaderTextFontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderSeparatorColor <em>Header Separator Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Separator Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderSeparatorColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_HeaderSeparatorColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellColor <em>Cell Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cell Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_CellColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellTextFontColor <em>Cell Text Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cell Text Font Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellTextFontColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_CellTextFontColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowSeparatorColor <em>Row Separator Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Row Separator Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowSeparatorColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_RowSeparatorColor();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowRollOverColor <em>Row Roll Over Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Row Roll Over Color</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowRollOverColor()
	 * @see #getTextComponentColorSet()
	 * @generated
	 */
	EAttribute getTextComponentColorSet_RowRollOverColor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextFieldFormat <em>Text Field Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Field Format</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextFieldFormat
	 * @generated
	 */
	EClass getTextFieldFormat();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextSeriesConfig <em>Text Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextSeriesConfig
	 * @generated
	 */
	EClass getTextSeriesConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig <em>Text Value Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Value Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig
	 * @generated
	 */
	EClass getTextValueDataConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderName <em>Header Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderName()
	 * @see #getTextValueDataConfig()
	 * @generated
	 */
	EAttribute getTextValueDataConfig_HeaderName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderFormatStyle <em>Header Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderFormatStyle()
	 * @see #getTextValueDataConfig()
	 * @generated
	 */
	EReference getTextValueDataConfig_HeaderFormatStyle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getWidth()
	 * @see #getTextValueDataConfig()
	 * @generated
	 */
	EAttribute getTextValueDataConfig_Width();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getAlignment <em>Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alignment</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getAlignment()
	 * @see #getTextValueDataConfig()
	 * @generated
	 */
	EAttribute getTextValueDataConfig_Alignment();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig <em>Text Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Value Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig
	 * @generated
	 */
	EClass getTextValueGuidelineConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header Alignment</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig#getHeaderAlignment()
	 * @see #getTextValueGuidelineConfig()
	 * @generated
	 */
	EAttribute getTextValueGuidelineConfig_HeaderAlignment();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization <em>Text Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization
	 * @generated
	 */
	EClass getTextVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization#isShowHeader <em>Show Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Header</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization#isShowHeader()
	 * @see #getTextVisualization()
	 * @generated
	 */
	EAttribute getTextVisualization_ShowHeader();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig <em>Two Dim Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Two Dim Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig
	 * @generated
	 */
	EClass getTwoDimSeriesConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig#getCategoryDataConfig <em>Category Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Category Data Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig#getCategoryDataConfig()
	 * @see #getTwoDimSeriesConfig()
	 * @generated
	 */
	EReference getTwoDimSeriesConfig_CategoryDataConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization <em>Two Dim Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Two Dim Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization
	 * @generated
	 */
	EClass getTwoDimVisualization();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization#getCategoryGuidelineConfig <em>Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Category Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization#getCategoryGuidelineConfig()
	 * @see #getTwoDimVisualization()
	 * @generated
	 */
	EReference getTwoDimVisualization_CategoryGuidelineConfig();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig <em>Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Guideline Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig
	 * @generated
	 */
	EClass getValueGuidelineConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig#getHeaderFormatStyle <em>Header Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header Format Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig#getHeaderFormatStyle()
	 * @see #getValueGuidelineConfig()
	 * @generated
	 */
	EReference getValueGuidelineConfig_HeaderFormatStyle();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle <em>Value Label Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Label Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle
	 * @generated
	 */
	EClass getValueLabelStyle();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle#getFontSize <em>Font Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Size</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle#getFontSize()
	 * @see #getValueLabelStyle()
	 * @generated
	 */
	EAttribute getValueLabelStyle_FontSize();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle#getFontStyle <em>Font Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Style</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle#getFontStyle()
	 * @see #getValueLabelStyle()
	 * @generated
	 */
	EAttribute getValueLabelStyle_FontStyle();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization <em>Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Visualization</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization
	 * @generated
	 */
	EClass getVisualization();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getDisplayName()
	 * @see #getVisualization()
	 * @generated
	 */
	EAttribute getVisualization_DisplayName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getBackground <em>Background</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Background</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getBackground()
	 * @see #getVisualization()
	 * @generated
	 */
	EReference getVisualization_Background();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesColorIndex <em>Series Color Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Series Color Index</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesColorIndex()
	 * @see #getVisualization()
	 * @generated
	 */
	EAttribute getVisualization_SeriesColorIndex();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getAction()
	 * @see #getVisualization()
	 * @generated
	 */
	EReference getVisualization_Action();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesConfig <em>Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Series Config</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesConfig()
	 * @see #getVisualization()
	 * @generated
	 */
	EReference getVisualization_SeriesConfig();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getRelatedElement <em>Related Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Related Element</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getRelatedElement()
	 * @see #getVisualization()
	 * @generated
	 */
	EReference getVisualization_RelatedElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference <em>Role Preference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Preference</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference
	 * @generated
	 */
	EClass getRolePreference();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getRole()
	 * @see #getRolePreference()
	 * @generated
	 */
	EAttribute getRolePreference_Role();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getGallery <em>Gallery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Gallery</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getGallery()
	 * @see #getRolePreference()
	 * @generated
	 */
	EReference getRolePreference_Gallery();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getView <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>View</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getView()
	 * @see #getRolePreference()
	 * @generated
	 */
	EReference getRolePreference_View();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder <em>Component Gallery Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Gallery Folder</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder
	 * @generated
	 */
	EClass getComponentGalleryFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder#getSubFolder <em>Sub Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Folder</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder#getSubFolder()
	 * @see #getComponentGalleryFolder()
	 * @generated
	 */
	EReference getComponentGalleryFolder_SubFolder();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder#getComponent()
	 * @see #getComponentGalleryFolder()
	 * @generated
	 */
	EReference getComponentGalleryFolder_Component();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink <em>Header Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Header Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink
	 * @generated
	 */
	EClass getHeaderLink();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlName <em>Url Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlName()
	 * @see #getHeaderLink()
	 * @generated
	 */
	EAttribute getHeaderLink_UrlName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlLink <em>Url Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlLink()
	 * @see #getHeaderLink()
	 * @generated
	 */
	EAttribute getHeaderLink_UrlLink();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelatedPage <em>Related Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related Page</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelatedPage
	 * @generated
	 */
	EClass getRelatedPage();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelatedElementsComponent <em>Related Elements Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related Elements Component</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelatedElementsComponent
	 * @generated
	 */
	EClass getRelatedElementsComponent();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum <em>Action Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Action Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum
	 * @generated
	 */
	EEnum getActionEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum <em>Anchor Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Anchor Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum
	 * @generated
	 */
	EEnum getAnchorEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum <em>Anchor Position Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Anchor Position Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum
	 * @generated
	 */
	EEnum getAnchorPositionEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum <em>Data Plotting Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Plotting Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum
	 * @generated
	 */
	EEnum getDataPlottingEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum <em>Display Mode Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Display Mode Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum
	 * @generated
	 */
	EEnum getDisplayModeEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum <em>Field Alignment Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Field Alignment Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @generated
	 */
	EEnum getFieldAlignmentEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum <em>Font Style Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Font Style Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum
	 * @generated
	 */
	EEnum getFontStyleEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum <em>Gradient Direction Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Gradient Direction Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum
	 * @generated
	 */
	EEnum getGradientDirectionEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum <em>Line Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Line Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum
	 * @generated
	 */
	EEnum getLineEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum <em>Orientation Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Orientation Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @generated
	 */
	EEnum getOrientationEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum <em>Panel State Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Panel State Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum
	 * @generated
	 */
	EEnum getPanelStateEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum <em>Partition State Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Partition State Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum
	 * @generated
	 */
	EEnum getPartitionStateEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum <em>Pie Chart Direction Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Pie Chart Direction Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum
	 * @generated
	 */
	EEnum getPieChartDirectionEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum <em>Placement Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Placement Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum
	 * @generated
	 */
	EEnum getPlacementEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum <em>Plot Shape Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Plot Shape Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum
	 * @generated
	 */
	EEnum getPlotShapeEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum <em>Range Plot Chart Series Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Range Plot Chart Series Type Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum
	 * @generated
	 */
	EEnum getRangePlotChartSeriesTypeEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum <em>Relative Axis Position Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relative Axis Position Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum
	 * @generated
	 */
	EEnum getRelativeAxisPositionEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum <em>Scale Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Scale Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum
	 * @generated
	 */
	EEnum getScaleEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum <em>Scroll Bar Config Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Scroll Bar Config Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum
	 * @generated
	 */
	EEnum getScrollBarConfigEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum <em>Series Anchor Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Series Anchor Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum
	 * @generated
	 */
	EEnum getSeriesAnchorEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum <em>Sort Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Sort Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum
	 * @generated
	 */
	EEnum getSortEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum <em>Threshold Unit Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Threshold Unit Enum</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum
	 * @generated
	 */
	EEnum getThresholdUnitEnum();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum <em>Action Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Action Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum"
	 *        extendedMetaData="name='ActionEnum:Object' baseType='ActionEnum'"
	 * @generated
	 */
	EDataType getActionEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum <em>Anchor Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Anchor Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum"
	 *        extendedMetaData="name='AnchorEnum:Object' baseType='AnchorEnum'"
	 * @generated
	 */
	EDataType getAnchorEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum <em>Anchor Position Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Anchor Position Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum"
	 *        extendedMetaData="name='AnchorPositionEnum:Object' baseType='AnchorPositionEnum'"
	 * @generated
	 */
	EDataType getAnchorPositionEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum <em>Data Plotting Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Data Plotting Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum"
	 *        extendedMetaData="name='DataPlottingEnum:Object' baseType='DataPlottingEnum'"
	 * @generated
	 */
	EDataType getDataPlottingEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum <em>Display Mode Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Display Mode Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum"
	 *        extendedMetaData="name='DisplayModeEnum:Object' baseType='DisplayModeEnum'"
	 * @generated
	 */
	EDataType getDisplayModeEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum <em>Field Alignment Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Field Alignment Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum"
	 *        extendedMetaData="name='FieldAlignmentEnum:Object' baseType='FieldAlignmentEnum'"
	 * @generated
	 */
	EDataType getFieldAlignmentEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum <em>Font Style Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Font Style Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum"
	 *        extendedMetaData="name='FontStyleEnum:Object' baseType='FontStyleEnum'"
	 * @generated
	 */
	EDataType getFontStyleEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum <em>Gradient Direction Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Gradient Direction Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum"
	 *        extendedMetaData="name='GradientDirectionEnum:Object' baseType='GradientDirectionEnum'"
	 * @generated
	 */
	EDataType getGradientDirectionEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum <em>Line Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Line Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum"
	 *        extendedMetaData="name='LineEnum:Object' baseType='LineEnum'"
	 * @generated
	 */
	EDataType getLineEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum <em>Orientation Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Orientation Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum"
	 *        extendedMetaData="name='OrientationEnum:Object' baseType='OrientationEnum'"
	 * @generated
	 */
	EDataType getOrientationEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum <em>Panel State Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Panel State Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum"
	 *        extendedMetaData="name='PanelStateEnum:Object' baseType='PanelStateEnum'"
	 * @generated
	 */
	EDataType getPanelStateEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum <em>Partition State Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Partition State Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum"
	 *        extendedMetaData="name='PartitionStateEnum:Object' baseType='PartitionStateEnum'"
	 * @generated
	 */
	EDataType getPartitionStateEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum <em>Pie Chart Direction Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pie Chart Direction Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum"
	 *        extendedMetaData="name='PieChartDirectionEnum:Object' baseType='PieChartDirectionEnum'"
	 * @generated
	 */
	EDataType getPieChartDirectionEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum <em>Placement Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Placement Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum"
	 *        extendedMetaData="name='PlacementEnum:Object' baseType='PlacementEnum'"
	 * @generated
	 */
	EDataType getPlacementEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum <em>Plot Shape Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Plot Shape Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum"
	 *        extendedMetaData="name='PlotShapeEnum:Object' baseType='PlotShapeEnum'"
	 * @generated
	 */
	EDataType getPlotShapeEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum <em>Range Plot Chart Series Type Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Range Plot Chart Series Type Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum"
	 *        extendedMetaData="name='RangePlotChartSeriesTypeEnum:Object' baseType='RangePlotChartSeriesTypeEnum'"
	 * @generated
	 */
	EDataType getRangePlotChartSeriesTypeEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum <em>Relative Axis Position Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Relative Axis Position Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum"
	 *        extendedMetaData="name='RelativeAxisPositionEnum:Object' baseType='RelativeAxisPositionEnum'"
	 * @generated
	 */
	EDataType getRelativeAxisPositionEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum <em>Scale Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Scale Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum"
	 *        extendedMetaData="name='ScaleEnum:Object' baseType='ScaleEnum'"
	 * @generated
	 */
	EDataType getScaleEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum <em>Scroll Bar Config Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Scroll Bar Config Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum"
	 *        extendedMetaData="name='ScrollBarConfigEnum:Object' baseType='ScrollBarConfigEnum'"
	 * @generated
	 */
	EDataType getScrollBarConfigEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum <em>Series Anchor Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Series Anchor Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum"
	 *        extendedMetaData="name='SeriesAnchorEnum:Object' baseType='SeriesAnchorEnum'"
	 * @generated
	 */
	EDataType getSeriesAnchorEnumObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum <em>Sort Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Sort Enum Object</em>'.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum
	 * @model instanceClass="com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum"
	 *        extendedMetaData="name='SortEnum:Object' baseType='SortEnum'"
	 * @generated
	 */
	EDataType getSortEnumObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BEViewsConfigurationFactory getBEViewsConfigurationFactory();

} //BEViewsConfigurationPackage
