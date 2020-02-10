/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.beviewsconfig.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BEViewsConfigurationFactoryImpl extends EFactoryImpl implements BEViewsConfigurationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BEViewsConfigurationFactory init() {
		try {
			BEViewsConfigurationFactory theBEViewsConfigurationFactory = (BEViewsConfigurationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/be/designer"); 
			if (theBEViewsConfigurationFactory != null) {
				return theBEViewsConfigurationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BEViewsConfigurationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsConfigurationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case BEViewsConfigurationPackage.ACTION_DEFINITION: return createActionDefinition();
			case BEViewsConfigurationPackage.STATE_DATA_CONFIG: return createStateDataConfig();
			case BEViewsConfigurationPackage.STATE_SERIES_CONFIG: return createStateSeriesConfig();
			case BEViewsConfigurationPackage.STATE_VISUALIZATION: return createStateVisualization();
			case BEViewsConfigurationPackage.ALERT_COMPONENT: return createAlertComponent();
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION: return createAlertIndicatorStateEnumeration();
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP: return createAlertIndicatorStateMap();
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG: return createAlertSeriesConfig();
			case BEViewsConfigurationPackage.ALERT_VISUALIZATION: return createAlertVisualization();
			case BEViewsConfigurationPackage.AREA_CHART_VISUALIZATION: return createAreaChartVisualization();
			case BEViewsConfigurationPackage.ASSET_PAGE: return createAssetPage();
			case BEViewsConfigurationPackage.BACKGROUND_FORMAT: return createBackgroundFormat();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION: return createBarChartVisualization();
			case BEViewsConfigurationPackage.BLUE_PRINT_COMPONENT: return createBluePrintComponent();
			case BEViewsConfigurationPackage.CHART_CATEGORY_DATA_CONFIG: return createChartCategoryDataConfig();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG: return createChartCategoryGuidelineConfig();
			case BEViewsConfigurationPackage.CHART_COMPONENT: return createChartComponent();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET: return createChartComponentColorSet();
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG: return createChartSeriesConfig();
			case BEViewsConfigurationPackage.CHART_VALUE_DATA_CONFIG: return createChartValueDataConfig();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG: return createChartValueGuidelineConfig();
			case BEViewsConfigurationPackage.CLASSIFIER_COMPONENT: return createClassifierComponent();
			case BEViewsConfigurationPackage.CLASSIFIER_SERIES_CONFIG: return createClassifierSeriesConfig();
			case BEViewsConfigurationPackage.CLASSIFIER_VISUALIZATION: return createClassifierVisualization();
			case BEViewsConfigurationPackage.DASHBOARD_PAGE: return createDashboardPage();
			case BEViewsConfigurationPackage.DATA_EXTRACTOR: return createDataExtractor();
			case BEViewsConfigurationPackage.ACTION_RULE: return createActionRule();
			case BEViewsConfigurationPackage.DATA_SOURCE: return createDataSource();
			case BEViewsConfigurationPackage.QUERY_PARAM: return createQueryParam();
			case BEViewsConfigurationPackage.ALERT: return createAlert();
			case BEViewsConfigurationPackage.RANGE_ALERT: return createRangeAlert();
			case BEViewsConfigurationPackage.ALERT_ACTION: return createAlertAction();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION: return createVisualAlertAction();
			case BEViewsConfigurationPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case BEViewsConfigurationPackage.FLOW_LAYOUT: return createFlowLayout();
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT: return createFlowLayoutConstraint();
			case BEViewsConfigurationPackage.FOREGROUND_FORMAT: return createForegroundFormat();
			case BEViewsConfigurationPackage.FORMAT_STYLE: return createFormatStyle();
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT: return createIndicatorFieldFormat();
			case BEViewsConfigurationPackage.VIEW: return createView();
			case BEViewsConfigurationPackage.VIEW_ATTRIBUTE: return createViewAttribute();
			case BEViewsConfigurationPackage.VIEW_VERSION: return createViewVersion();
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT: return createBEViewsElement();
			case BEViewsConfigurationPackage.FOOTER: return createFooter();
			case BEViewsConfigurationPackage.HEADER: return createHeader();
			case BEViewsConfigurationPackage.VIEW_LOCALE: return createViewLocale();
			case BEViewsConfigurationPackage.LOGIN: return createLogin();
			case BEViewsConfigurationPackage.SKIN: return createSkin();
			case BEViewsConfigurationPackage.LEGEND_FORMAT: return createLegendFormat();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION: return createLineChartVisualization();
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG: return createOneDimSeriesConfig();
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION: return createOneDimVisualization();
			case BEViewsConfigurationPackage.PAGE_SELECTOR_COMPONENT: return createPageSelectorComponent();
			case BEViewsConfigurationPackage.PAGE_VISUALIZATION: return createPageVisualization();
			case BEViewsConfigurationPackage.PANEL: return createPanel();
			case BEViewsConfigurationPackage.PARTITION: return createPartition();
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION: return createPieChartVisualization();
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT: return createPlotAreaFormat();
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT: return createStateMachineComponent();
			case BEViewsConfigurationPackage.CONSTANT_VALUE_OPTION: return createConstantValueOption();
			case BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION: return createFieldReferenceValueOption();
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT: return createProgressBarFieldFormat();
			case BEViewsConfigurationPackage.QUERY_MANAGER_COMPONENT: return createQueryManagerComponent();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_SERIES_CONFIG: return createRangePlotChartSeriesConfig();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION: return createRangePlotChartVisualization();
			case BEViewsConfigurationPackage.RELATED_ASSETS_COMPONENT: return createRelatedAssetsComponent();
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION: return createScatterPlotChartVisualization();
			case BEViewsConfigurationPackage.SEARCH_PAGE: return createSearchPage();
			case BEViewsConfigurationPackage.SEARCH_VIEW_COMPONENT: return createSearchViewComponent();
			case BEViewsConfigurationPackage.SERIES_COLOR: return createSeriesColor();
			case BEViewsConfigurationPackage.SERIES_CONFIG_GENERATOR: return createSeriesConfigGenerator();
			case BEViewsConfigurationPackage.TEXT_CATEGORY_DATA_CONFIG: return createTextCategoryDataConfig();
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG: return createTextCategoryGuidelineConfig();
			case BEViewsConfigurationPackage.TEXT_COMPONENT: return createTextComponent();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET: return createTextComponentColorSet();
			case BEViewsConfigurationPackage.TEXT_FIELD_FORMAT: return createTextFieldFormat();
			case BEViewsConfigurationPackage.TEXT_SERIES_CONFIG: return createTextSeriesConfig();
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG: return createTextValueDataConfig();
			case BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG: return createTextValueGuidelineConfig();
			case BEViewsConfigurationPackage.TEXT_VISUALIZATION: return createTextVisualization();
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG: return createTwoDimSeriesConfig();
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION: return createTwoDimVisualization();
			case BEViewsConfigurationPackage.VALUE_LABEL_STYLE: return createValueLabelStyle();
			case BEViewsConfigurationPackage.ROLE_PREFERENCE: return createRolePreference();
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER: return createComponentGalleryFolder();
			case BEViewsConfigurationPackage.HEADER_LINK: return createHeaderLink();
			case BEViewsConfigurationPackage.RELATED_PAGE: return createRelatedPage();
			case BEViewsConfigurationPackage.RELATED_ELEMENTS_COMPONENT: return createRelatedElementsComponent();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case BEViewsConfigurationPackage.ACTION_ENUM:
				return createActionEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ANCHOR_ENUM:
				return createAnchorEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ANCHOR_POSITION_ENUM:
				return createAnchorPositionEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.DATA_PLOTTING_ENUM:
				return createDataPlottingEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.DISPLAY_MODE_ENUM:
				return createDisplayModeEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.FIELD_ALIGNMENT_ENUM:
				return createFieldAlignmentEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.FONT_STYLE_ENUM:
				return createFontStyleEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.GRADIENT_DIRECTION_ENUM:
				return createGradientDirectionEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.LINE_ENUM:
				return createLineEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ORIENTATION_ENUM:
				return createOrientationEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PANEL_STATE_ENUM:
				return createPanelStateEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PARTITION_STATE_ENUM:
				return createPartitionStateEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PIE_CHART_DIRECTION_ENUM:
				return createPieChartDirectionEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PLACEMENT_ENUM:
				return createPlacementEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PLOT_SHAPE_ENUM:
				return createPlotShapeEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_SERIES_TYPE_ENUM:
				return createRangePlotChartSeriesTypeEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.RELATIVE_AXIS_POSITION_ENUM:
				return createRelativeAxisPositionEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SCALE_ENUM:
				return createScaleEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SCROLL_BAR_CONFIG_ENUM:
				return createScrollBarConfigEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SERIES_ANCHOR_ENUM:
				return createSeriesAnchorEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SORT_ENUM:
				return createSortEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.THRESHOLD_UNIT_ENUM:
				return createThresholdUnitEnumFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ACTION_ENUM_OBJECT:
				return createActionEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ANCHOR_ENUM_OBJECT:
				return createAnchorEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ANCHOR_POSITION_ENUM_OBJECT:
				return createAnchorPositionEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.DATA_PLOTTING_ENUM_OBJECT:
				return createDataPlottingEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.DISPLAY_MODE_ENUM_OBJECT:
				return createDisplayModeEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.FIELD_ALIGNMENT_ENUM_OBJECT:
				return createFieldAlignmentEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.FONT_STYLE_ENUM_OBJECT:
				return createFontStyleEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.GRADIENT_DIRECTION_ENUM_OBJECT:
				return createGradientDirectionEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.LINE_ENUM_OBJECT:
				return createLineEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.ORIENTATION_ENUM_OBJECT:
				return createOrientationEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PANEL_STATE_ENUM_OBJECT:
				return createPanelStateEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PARTITION_STATE_ENUM_OBJECT:
				return createPartitionStateEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PIE_CHART_DIRECTION_ENUM_OBJECT:
				return createPieChartDirectionEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PLACEMENT_ENUM_OBJECT:
				return createPlacementEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.PLOT_SHAPE_ENUM_OBJECT:
				return createPlotShapeEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_SERIES_TYPE_ENUM_OBJECT:
				return createRangePlotChartSeriesTypeEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.RELATIVE_AXIS_POSITION_ENUM_OBJECT:
				return createRelativeAxisPositionEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SCALE_ENUM_OBJECT:
				return createScaleEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SCROLL_BAR_CONFIG_ENUM_OBJECT:
				return createScrollBarConfigEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SERIES_ANCHOR_ENUM_OBJECT:
				return createSeriesAnchorEnumObjectFromString(eDataType, initialValue);
			case BEViewsConfigurationPackage.SORT_ENUM_OBJECT:
				return createSortEnumObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case BEViewsConfigurationPackage.ACTION_ENUM:
				return convertActionEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ANCHOR_ENUM:
				return convertAnchorEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ANCHOR_POSITION_ENUM:
				return convertAnchorPositionEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.DATA_PLOTTING_ENUM:
				return convertDataPlottingEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.DISPLAY_MODE_ENUM:
				return convertDisplayModeEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.FIELD_ALIGNMENT_ENUM:
				return convertFieldAlignmentEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.FONT_STYLE_ENUM:
				return convertFontStyleEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.GRADIENT_DIRECTION_ENUM:
				return convertGradientDirectionEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.LINE_ENUM:
				return convertLineEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ORIENTATION_ENUM:
				return convertOrientationEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PANEL_STATE_ENUM:
				return convertPanelStateEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PARTITION_STATE_ENUM:
				return convertPartitionStateEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PIE_CHART_DIRECTION_ENUM:
				return convertPieChartDirectionEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PLACEMENT_ENUM:
				return convertPlacementEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PLOT_SHAPE_ENUM:
				return convertPlotShapeEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_SERIES_TYPE_ENUM:
				return convertRangePlotChartSeriesTypeEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.RELATIVE_AXIS_POSITION_ENUM:
				return convertRelativeAxisPositionEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SCALE_ENUM:
				return convertScaleEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SCROLL_BAR_CONFIG_ENUM:
				return convertScrollBarConfigEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SERIES_ANCHOR_ENUM:
				return convertSeriesAnchorEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SORT_ENUM:
				return convertSortEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.THRESHOLD_UNIT_ENUM:
				return convertThresholdUnitEnumToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ACTION_ENUM_OBJECT:
				return convertActionEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ANCHOR_ENUM_OBJECT:
				return convertAnchorEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ANCHOR_POSITION_ENUM_OBJECT:
				return convertAnchorPositionEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.DATA_PLOTTING_ENUM_OBJECT:
				return convertDataPlottingEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.DISPLAY_MODE_ENUM_OBJECT:
				return convertDisplayModeEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.FIELD_ALIGNMENT_ENUM_OBJECT:
				return convertFieldAlignmentEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.FONT_STYLE_ENUM_OBJECT:
				return convertFontStyleEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.GRADIENT_DIRECTION_ENUM_OBJECT:
				return convertGradientDirectionEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.LINE_ENUM_OBJECT:
				return convertLineEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.ORIENTATION_ENUM_OBJECT:
				return convertOrientationEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PANEL_STATE_ENUM_OBJECT:
				return convertPanelStateEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PARTITION_STATE_ENUM_OBJECT:
				return convertPartitionStateEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PIE_CHART_DIRECTION_ENUM_OBJECT:
				return convertPieChartDirectionEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PLACEMENT_ENUM_OBJECT:
				return convertPlacementEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.PLOT_SHAPE_ENUM_OBJECT:
				return convertPlotShapeEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_SERIES_TYPE_ENUM_OBJECT:
				return convertRangePlotChartSeriesTypeEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.RELATIVE_AXIS_POSITION_ENUM_OBJECT:
				return convertRelativeAxisPositionEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SCALE_ENUM_OBJECT:
				return convertScaleEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SCROLL_BAR_CONFIG_ENUM_OBJECT:
				return convertScrollBarConfigEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SERIES_ANCHOR_ENUM_OBJECT:
				return convertSeriesAnchorEnumObjectToString(eDataType, instanceValue);
			case BEViewsConfigurationPackage.SORT_ENUM_OBJECT:
				return convertSortEnumObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionDefinition createActionDefinition() {
		ActionDefinitionImpl actionDefinition = new ActionDefinitionImpl();
		return actionDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateDataConfig createStateDataConfig() {
		StateDataConfigImpl stateDataConfig = new StateDataConfigImpl();
		return stateDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateSeriesConfig createStateSeriesConfig() {
		StateSeriesConfigImpl stateSeriesConfig = new StateSeriesConfigImpl();
		return stateSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateVisualization createStateVisualization() {
		StateVisualizationImpl stateVisualization = new StateVisualizationImpl();
		return stateVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertComponent createAlertComponent() {
		AlertComponentImpl alertComponent = new AlertComponentImpl();
		return alertComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertIndicatorStateEnumeration createAlertIndicatorStateEnumeration() {
		AlertIndicatorStateEnumerationImpl alertIndicatorStateEnumeration = new AlertIndicatorStateEnumerationImpl();
		return alertIndicatorStateEnumeration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertIndicatorStateMap createAlertIndicatorStateMap() {
		AlertIndicatorStateMapImpl alertIndicatorStateMap = new AlertIndicatorStateMapImpl();
		return alertIndicatorStateMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertSeriesConfig createAlertSeriesConfig() {
		AlertSeriesConfigImpl alertSeriesConfig = new AlertSeriesConfigImpl();
		return alertSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertVisualization createAlertVisualization() {
		AlertVisualizationImpl alertVisualization = new AlertVisualizationImpl();
		return alertVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AreaChartVisualization createAreaChartVisualization() {
		AreaChartVisualizationImpl areaChartVisualization = new AreaChartVisualizationImpl();
		return areaChartVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssetPage createAssetPage() {
		AssetPageImpl assetPage = new AssetPageImpl();
		return assetPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackgroundFormat createBackgroundFormat() {
		BackgroundFormatImpl backgroundFormat = new BackgroundFormatImpl();
		return backgroundFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BarChartVisualization createBarChartVisualization() {
		BarChartVisualizationImpl barChartVisualization = new BarChartVisualizationImpl();
		return barChartVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BluePrintComponent createBluePrintComponent() {
		BluePrintComponentImpl bluePrintComponent = new BluePrintComponentImpl();
		return bluePrintComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartCategoryDataConfig createChartCategoryDataConfig() {
		ChartCategoryDataConfigImpl chartCategoryDataConfig = new ChartCategoryDataConfigImpl();
		return chartCategoryDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartCategoryGuidelineConfig createChartCategoryGuidelineConfig() {
		ChartCategoryGuidelineConfigImpl chartCategoryGuidelineConfig = new ChartCategoryGuidelineConfigImpl();
		return chartCategoryGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartComponent createChartComponent() {
		ChartComponentImpl chartComponent = new ChartComponentImpl();
		return chartComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartComponentColorSet createChartComponentColorSet() {
		ChartComponentColorSetImpl chartComponentColorSet = new ChartComponentColorSetImpl();
		return chartComponentColorSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartSeriesConfig createChartSeriesConfig() {
		ChartSeriesConfigImpl chartSeriesConfig = new ChartSeriesConfigImpl();
		return chartSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartValueDataConfig createChartValueDataConfig() {
		ChartValueDataConfigImpl chartValueDataConfig = new ChartValueDataConfigImpl();
		return chartValueDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartValueGuidelineConfig createChartValueGuidelineConfig() {
		ChartValueGuidelineConfigImpl chartValueGuidelineConfig = new ChartValueGuidelineConfigImpl();
		return chartValueGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierComponent createClassifierComponent() {
		ClassifierComponentImpl classifierComponent = new ClassifierComponentImpl();
		return classifierComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierSeriesConfig createClassifierSeriesConfig() {
		ClassifierSeriesConfigImpl classifierSeriesConfig = new ClassifierSeriesConfigImpl();
		return classifierSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierVisualization createClassifierVisualization() {
		ClassifierVisualizationImpl classifierVisualization = new ClassifierVisualizationImpl();
		return classifierVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DashboardPage createDashboardPage() {
		DashboardPageImpl dashboardPage = new DashboardPageImpl();
		return dashboardPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataExtractor createDataExtractor() {
		DataExtractorImpl dataExtractor = new DataExtractorImpl();
		return dataExtractor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionRule createActionRule() {
		ActionRuleImpl actionRule = new ActionRuleImpl();
		return actionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataSource createDataSource() {
		DataSourceImpl dataSource = new DataSourceImpl();
		return dataSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryParam createQueryParam() {
		QueryParamImpl queryParam = new QueryParamImpl();
		return queryParam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Alert createAlert() {
		AlertImpl alert = new AlertImpl();
		return alert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RangeAlert createRangeAlert() {
		RangeAlertImpl rangeAlert = new RangeAlertImpl();
		return rangeAlert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertAction createAlertAction() {
		AlertActionImpl alertAction = new AlertActionImpl();
		return alertAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VisualAlertAction createVisualAlertAction() {
		VisualAlertActionImpl visualAlertAction = new VisualAlertActionImpl();
		return visualAlertAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlowLayout createFlowLayout() {
		FlowLayoutImpl flowLayout = new FlowLayoutImpl();
		return flowLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlowLayoutConstraint createFlowLayoutConstraint() {
		FlowLayoutConstraintImpl flowLayoutConstraint = new FlowLayoutConstraintImpl();
		return flowLayoutConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForegroundFormat createForegroundFormat() {
		ForegroundFormatImpl foregroundFormat = new ForegroundFormatImpl();
		return foregroundFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatStyle createFormatStyle() {
		FormatStyleImpl formatStyle = new FormatStyleImpl();
		return formatStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndicatorFieldFormat createIndicatorFieldFormat() {
		IndicatorFieldFormatImpl indicatorFieldFormat = new IndicatorFieldFormatImpl();
		return indicatorFieldFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View createView() {
		ViewImpl view = new ViewImpl();
		return view;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewAttribute createViewAttribute() {
		ViewAttributeImpl viewAttribute = new ViewAttributeImpl();
		return viewAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewVersion createViewVersion() {
		ViewVersionImpl viewVersion = new ViewVersionImpl();
		return viewVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsElement createBEViewsElement() {
		BEViewsElementImpl beViewsElement = new BEViewsElementImpl();
		return beViewsElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Footer createFooter() {
		FooterImpl footer = new FooterImpl();
		return footer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Header createHeader() {
		HeaderImpl header = new HeaderImpl();
		return header;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewLocale createViewLocale() {
		ViewLocaleImpl viewLocale = new ViewLocaleImpl();
		return viewLocale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Login createLogin() {
		LoginImpl login = new LoginImpl();
		return login;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Skin createSkin() {
		SkinImpl skin = new SkinImpl();
		return skin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LegendFormat createLegendFormat() {
		LegendFormatImpl legendFormat = new LegendFormatImpl();
		return legendFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineChartVisualization createLineChartVisualization() {
		LineChartVisualizationImpl lineChartVisualization = new LineChartVisualizationImpl();
		return lineChartVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneDimSeriesConfig createOneDimSeriesConfig() {
		OneDimSeriesConfigImpl oneDimSeriesConfig = new OneDimSeriesConfigImpl();
		return oneDimSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneDimVisualization createOneDimVisualization() {
		OneDimVisualizationImpl oneDimVisualization = new OneDimVisualizationImpl();
		return oneDimVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageSelectorComponent createPageSelectorComponent() {
		PageSelectorComponentImpl pageSelectorComponent = new PageSelectorComponentImpl();
		return pageSelectorComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageVisualization createPageVisualization() {
		PageVisualizationImpl pageVisualization = new PageVisualizationImpl();
		return pageVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Panel createPanel() {
		PanelImpl panel = new PanelImpl();
		return panel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Partition createPartition() {
		PartitionImpl partition = new PartitionImpl();
		return partition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PieChartVisualization createPieChartVisualization() {
		PieChartVisualizationImpl pieChartVisualization = new PieChartVisualizationImpl();
		return pieChartVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlotAreaFormat createPlotAreaFormat() {
		PlotAreaFormatImpl plotAreaFormat = new PlotAreaFormatImpl();
		return plotAreaFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachineComponent createStateMachineComponent() {
		StateMachineComponentImpl stateMachineComponent = new StateMachineComponentImpl();
		return stateMachineComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstantValueOption createConstantValueOption() {
		ConstantValueOptionImpl constantValueOption = new ConstantValueOptionImpl();
		return constantValueOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldReferenceValueOption createFieldReferenceValueOption() {
		FieldReferenceValueOptionImpl fieldReferenceValueOption = new FieldReferenceValueOptionImpl();
		return fieldReferenceValueOption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProgressBarFieldFormat createProgressBarFieldFormat() {
		ProgressBarFieldFormatImpl progressBarFieldFormat = new ProgressBarFieldFormatImpl();
		return progressBarFieldFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryManagerComponent createQueryManagerComponent() {
		QueryManagerComponentImpl queryManagerComponent = new QueryManagerComponentImpl();
		return queryManagerComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RangePlotChartSeriesConfig createRangePlotChartSeriesConfig() {
		RangePlotChartSeriesConfigImpl rangePlotChartSeriesConfig = new RangePlotChartSeriesConfigImpl();
		return rangePlotChartSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RangePlotChartVisualization createRangePlotChartVisualization() {
		RangePlotChartVisualizationImpl rangePlotChartVisualization = new RangePlotChartVisualizationImpl();
		return rangePlotChartVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelatedAssetsComponent createRelatedAssetsComponent() {
		RelatedAssetsComponentImpl relatedAssetsComponent = new RelatedAssetsComponentImpl();
		return relatedAssetsComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScatterPlotChartVisualization createScatterPlotChartVisualization() {
		ScatterPlotChartVisualizationImpl scatterPlotChartVisualization = new ScatterPlotChartVisualizationImpl();
		return scatterPlotChartVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchPage createSearchPage() {
		SearchPageImpl searchPage = new SearchPageImpl();
		return searchPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchViewComponent createSearchViewComponent() {
		SearchViewComponentImpl searchViewComponent = new SearchViewComponentImpl();
		return searchViewComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeriesColor createSeriesColor() {
		SeriesColorImpl seriesColor = new SeriesColorImpl();
		return seriesColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeriesConfigGenerator createSeriesConfigGenerator() {
		SeriesConfigGeneratorImpl seriesConfigGenerator = new SeriesConfigGeneratorImpl();
		return seriesConfigGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextCategoryDataConfig createTextCategoryDataConfig() {
		TextCategoryDataConfigImpl textCategoryDataConfig = new TextCategoryDataConfigImpl();
		return textCategoryDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextCategoryGuidelineConfig createTextCategoryGuidelineConfig() {
		TextCategoryGuidelineConfigImpl textCategoryGuidelineConfig = new TextCategoryGuidelineConfigImpl();
		return textCategoryGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextComponent createTextComponent() {
		TextComponentImpl textComponent = new TextComponentImpl();
		return textComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextComponentColorSet createTextComponentColorSet() {
		TextComponentColorSetImpl textComponentColorSet = new TextComponentColorSetImpl();
		return textComponentColorSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextFieldFormat createTextFieldFormat() {
		TextFieldFormatImpl textFieldFormat = new TextFieldFormatImpl();
		return textFieldFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextSeriesConfig createTextSeriesConfig() {
		TextSeriesConfigImpl textSeriesConfig = new TextSeriesConfigImpl();
		return textSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextValueDataConfig createTextValueDataConfig() {
		TextValueDataConfigImpl textValueDataConfig = new TextValueDataConfigImpl();
		return textValueDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextValueGuidelineConfig createTextValueGuidelineConfig() {
		TextValueGuidelineConfigImpl textValueGuidelineConfig = new TextValueGuidelineConfigImpl();
		return textValueGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextVisualization createTextVisualization() {
		TextVisualizationImpl textVisualization = new TextVisualizationImpl();
		return textVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TwoDimSeriesConfig createTwoDimSeriesConfig() {
		TwoDimSeriesConfigImpl twoDimSeriesConfig = new TwoDimSeriesConfigImpl();
		return twoDimSeriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TwoDimVisualization createTwoDimVisualization() {
		TwoDimVisualizationImpl twoDimVisualization = new TwoDimVisualizationImpl();
		return twoDimVisualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueLabelStyle createValueLabelStyle() {
		ValueLabelStyleImpl valueLabelStyle = new ValueLabelStyleImpl();
		return valueLabelStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RolePreference createRolePreference() {
		RolePreferenceImpl rolePreference = new RolePreferenceImpl();
		return rolePreference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentGalleryFolder createComponentGalleryFolder() {
		ComponentGalleryFolderImpl componentGalleryFolder = new ComponentGalleryFolderImpl();
		return componentGalleryFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HeaderLink createHeaderLink() {
		HeaderLinkImpl headerLink = new HeaderLinkImpl();
		return headerLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelatedPage createRelatedPage() {
		RelatedPageImpl relatedPage = new RelatedPageImpl();
		return relatedPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelatedElementsComponent createRelatedElementsComponent() {
		RelatedElementsComponentImpl relatedElementsComponent = new RelatedElementsComponentImpl();
		return relatedElementsComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionEnum createActionEnumFromString(EDataType eDataType, String initialValue) {
		ActionEnum result = ActionEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActionEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorEnum createAnchorEnumFromString(EDataType eDataType, String initialValue) {
		AnchorEnum result = AnchorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnchorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorPositionEnum createAnchorPositionEnumFromString(EDataType eDataType, String initialValue) {
		AnchorPositionEnum result = AnchorPositionEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnchorPositionEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataPlottingEnum createDataPlottingEnumFromString(EDataType eDataType, String initialValue) {
		DataPlottingEnum result = DataPlottingEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataPlottingEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DisplayModeEnum createDisplayModeEnumFromString(EDataType eDataType, String initialValue) {
		DisplayModeEnum result = DisplayModeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDisplayModeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldAlignmentEnum createFieldAlignmentEnumFromString(EDataType eDataType, String initialValue) {
		FieldAlignmentEnum result = FieldAlignmentEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFieldAlignmentEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FontStyleEnum createFontStyleEnumFromString(EDataType eDataType, String initialValue) {
		FontStyleEnum result = FontStyleEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFontStyleEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GradientDirectionEnum createGradientDirectionEnumFromString(EDataType eDataType, String initialValue) {
		GradientDirectionEnum result = GradientDirectionEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGradientDirectionEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineEnum createLineEnumFromString(EDataType eDataType, String initialValue) {
		LineEnum result = LineEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLineEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrientationEnum createOrientationEnumFromString(EDataType eDataType, String initialValue) {
		OrientationEnum result = OrientationEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOrientationEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelStateEnum createPanelStateEnumFromString(EDataType eDataType, String initialValue) {
		PanelStateEnum result = PanelStateEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPanelStateEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartitionStateEnum createPartitionStateEnumFromString(EDataType eDataType, String initialValue) {
		PartitionStateEnum result = PartitionStateEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPartitionStateEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PieChartDirectionEnum createPieChartDirectionEnumFromString(EDataType eDataType, String initialValue) {
		PieChartDirectionEnum result = PieChartDirectionEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPieChartDirectionEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlacementEnum createPlacementEnumFromString(EDataType eDataType, String initialValue) {
		PlacementEnum result = PlacementEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlacementEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlotShapeEnum createPlotShapeEnumFromString(EDataType eDataType, String initialValue) {
		PlotShapeEnum result = PlotShapeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlotShapeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RangePlotChartSeriesTypeEnum createRangePlotChartSeriesTypeEnumFromString(EDataType eDataType, String initialValue) {
		RangePlotChartSeriesTypeEnum result = RangePlotChartSeriesTypeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRangePlotChartSeriesTypeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelativeAxisPositionEnum createRelativeAxisPositionEnumFromString(EDataType eDataType, String initialValue) {
		RelativeAxisPositionEnum result = RelativeAxisPositionEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRelativeAxisPositionEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaleEnum createScaleEnumFromString(EDataType eDataType, String initialValue) {
		ScaleEnum result = ScaleEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScaleEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScrollBarConfigEnum createScrollBarConfigEnumFromString(EDataType eDataType, String initialValue) {
		ScrollBarConfigEnum result = ScrollBarConfigEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScrollBarConfigEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeriesAnchorEnum createSeriesAnchorEnumFromString(EDataType eDataType, String initialValue) {
		SeriesAnchorEnum result = SeriesAnchorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSeriesAnchorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SortEnum createSortEnumFromString(EDataType eDataType, String initialValue) {
		SortEnum result = SortEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSortEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThresholdUnitEnum createThresholdUnitEnumFromString(EDataType eDataType, String initialValue) {
		ThresholdUnitEnum result = ThresholdUnitEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertThresholdUnitEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionEnum createActionEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createActionEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getActionEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActionEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertActionEnumToString(BEViewsConfigurationPackage.eINSTANCE.getActionEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorEnum createAnchorEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createAnchorEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getAnchorEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnchorEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAnchorEnumToString(BEViewsConfigurationPackage.eINSTANCE.getAnchorEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorPositionEnum createAnchorPositionEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createAnchorPositionEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getAnchorPositionEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnchorPositionEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAnchorPositionEnumToString(BEViewsConfigurationPackage.eINSTANCE.getAnchorPositionEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataPlottingEnum createDataPlottingEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createDataPlottingEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getDataPlottingEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataPlottingEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDataPlottingEnumToString(BEViewsConfigurationPackage.eINSTANCE.getDataPlottingEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DisplayModeEnum createDisplayModeEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createDisplayModeEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getDisplayModeEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDisplayModeEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDisplayModeEnumToString(BEViewsConfigurationPackage.eINSTANCE.getDisplayModeEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldAlignmentEnum createFieldAlignmentEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createFieldAlignmentEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getFieldAlignmentEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFieldAlignmentEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertFieldAlignmentEnumToString(BEViewsConfigurationPackage.eINSTANCE.getFieldAlignmentEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FontStyleEnum createFontStyleEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createFontStyleEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getFontStyleEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFontStyleEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertFontStyleEnumToString(BEViewsConfigurationPackage.eINSTANCE.getFontStyleEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GradientDirectionEnum createGradientDirectionEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createGradientDirectionEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getGradientDirectionEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGradientDirectionEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertGradientDirectionEnumToString(BEViewsConfigurationPackage.eINSTANCE.getGradientDirectionEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineEnum createLineEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createLineEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getLineEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLineEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertLineEnumToString(BEViewsConfigurationPackage.eINSTANCE.getLineEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrientationEnum createOrientationEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createOrientationEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getOrientationEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOrientationEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertOrientationEnumToString(BEViewsConfigurationPackage.eINSTANCE.getOrientationEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelStateEnum createPanelStateEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createPanelStateEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getPanelStateEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPanelStateEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPanelStateEnumToString(BEViewsConfigurationPackage.eINSTANCE.getPanelStateEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartitionStateEnum createPartitionStateEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createPartitionStateEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getPartitionStateEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPartitionStateEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPartitionStateEnumToString(BEViewsConfigurationPackage.eINSTANCE.getPartitionStateEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PieChartDirectionEnum createPieChartDirectionEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createPieChartDirectionEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getPieChartDirectionEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPieChartDirectionEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPieChartDirectionEnumToString(BEViewsConfigurationPackage.eINSTANCE.getPieChartDirectionEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlacementEnum createPlacementEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createPlacementEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getPlacementEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlacementEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPlacementEnumToString(BEViewsConfigurationPackage.eINSTANCE.getPlacementEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlotShapeEnum createPlotShapeEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createPlotShapeEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getPlotShapeEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlotShapeEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertPlotShapeEnumToString(BEViewsConfigurationPackage.eINSTANCE.getPlotShapeEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RangePlotChartSeriesTypeEnum createRangePlotChartSeriesTypeEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createRangePlotChartSeriesTypeEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getRangePlotChartSeriesTypeEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRangePlotChartSeriesTypeEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertRangePlotChartSeriesTypeEnumToString(BEViewsConfigurationPackage.eINSTANCE.getRangePlotChartSeriesTypeEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelativeAxisPositionEnum createRelativeAxisPositionEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createRelativeAxisPositionEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getRelativeAxisPositionEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRelativeAxisPositionEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertRelativeAxisPositionEnumToString(BEViewsConfigurationPackage.eINSTANCE.getRelativeAxisPositionEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaleEnum createScaleEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createScaleEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getScaleEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScaleEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertScaleEnumToString(BEViewsConfigurationPackage.eINSTANCE.getScaleEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScrollBarConfigEnum createScrollBarConfigEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createScrollBarConfigEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getScrollBarConfigEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScrollBarConfigEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertScrollBarConfigEnumToString(BEViewsConfigurationPackage.eINSTANCE.getScrollBarConfigEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeriesAnchorEnum createSeriesAnchorEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createSeriesAnchorEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getSeriesAnchorEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSeriesAnchorEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSeriesAnchorEnumToString(BEViewsConfigurationPackage.eINSTANCE.getSeriesAnchorEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SortEnum createSortEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createSortEnumFromString(BEViewsConfigurationPackage.eINSTANCE.getSortEnum(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSortEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSortEnumToString(BEViewsConfigurationPackage.eINSTANCE.getSortEnum(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsConfigurationPackage getBEViewsConfigurationPackage() {
		return (BEViewsConfigurationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BEViewsConfigurationPackage getPackage() {
		return BEViewsConfigurationPackage.eINSTANCE;
	}

} //BEViewsConfigurationFactoryImpl
