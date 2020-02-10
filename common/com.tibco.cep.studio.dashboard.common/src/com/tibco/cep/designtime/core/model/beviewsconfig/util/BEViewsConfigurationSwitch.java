/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage
 * @generated
 */
public class BEViewsConfigurationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BEViewsConfigurationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsConfigurationSwitch() {
		if (modelPackage == null) {
			modelPackage = BEViewsConfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case BEViewsConfigurationPackage.ACTION_DEFINITION: {
				ActionDefinition actionDefinition = (ActionDefinition)theEObject;
				T result = caseActionDefinition(actionDefinition);
				if (result == null) result = caseBEViewsElement(actionDefinition);
				if (result == null) result = caseEntity(actionDefinition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.STATE_DATA_CONFIG: {
				StateDataConfig stateDataConfig = (StateDataConfig)theEObject;
				T result = caseStateDataConfig(stateDataConfig);
				if (result == null) result = caseDataConfig(stateDataConfig);
				if (result == null) result = caseBEViewsElement(stateDataConfig);
				if (result == null) result = caseEntity(stateDataConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.STATE_SERIES_CONFIG: {
				StateSeriesConfig stateSeriesConfig = (StateSeriesConfig)theEObject;
				T result = caseStateSeriesConfig(stateSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(stateSeriesConfig);
				if (result == null) result = caseSeriesConfig(stateSeriesConfig);
				if (result == null) result = caseBEViewsElement(stateSeriesConfig);
				if (result == null) result = caseEntity(stateSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.STATE_VISUALIZATION: {
				StateVisualization stateVisualization = (StateVisualization)theEObject;
				T result = caseStateVisualization(stateVisualization);
				if (result == null) result = caseOneDimVisualization(stateVisualization);
				if (result == null) result = caseVisualization(stateVisualization);
				if (result == null) result = caseBEViewsElement(stateVisualization);
				if (result == null) result = caseEntity(stateVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT_COMPONENT: {
				AlertComponent alertComponent = (AlertComponent)theEObject;
				T result = caseAlertComponent(alertComponent);
				if (result == null) result = caseClassifierComponent(alertComponent);
				if (result == null) result = caseComponent(alertComponent);
				if (result == null) result = caseBEViewsElement(alertComponent);
				if (result == null) result = caseEntity(alertComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION: {
				AlertIndicatorStateEnumeration alertIndicatorStateEnumeration = (AlertIndicatorStateEnumeration)theEObject;
				T result = caseAlertIndicatorStateEnumeration(alertIndicatorStateEnumeration);
				if (result == null) result = caseBEViewsElement(alertIndicatorStateEnumeration);
				if (result == null) result = caseEntity(alertIndicatorStateEnumeration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_MAP: {
				AlertIndicatorStateMap alertIndicatorStateMap = (AlertIndicatorStateMap)theEObject;
				T result = caseAlertIndicatorStateMap(alertIndicatorStateMap);
				if (result == null) result = caseBEViewsElement(alertIndicatorStateMap);
				if (result == null) result = caseEntity(alertIndicatorStateMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT_SERIES_CONFIG: {
				AlertSeriesConfig alertSeriesConfig = (AlertSeriesConfig)theEObject;
				T result = caseAlertSeriesConfig(alertSeriesConfig);
				if (result == null) result = caseClassifierSeriesConfig(alertSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(alertSeriesConfig);
				if (result == null) result = caseSeriesConfig(alertSeriesConfig);
				if (result == null) result = caseBEViewsElement(alertSeriesConfig);
				if (result == null) result = caseEntity(alertSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT_VISUALIZATION: {
				AlertVisualization alertVisualization = (AlertVisualization)theEObject;
				T result = caseAlertVisualization(alertVisualization);
				if (result == null) result = caseClassifierVisualization(alertVisualization);
				if (result == null) result = caseOneDimVisualization(alertVisualization);
				if (result == null) result = caseVisualization(alertVisualization);
				if (result == null) result = caseBEViewsElement(alertVisualization);
				if (result == null) result = caseEntity(alertVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.AREA_CHART_VISUALIZATION: {
				AreaChartVisualization areaChartVisualization = (AreaChartVisualization)theEObject;
				T result = caseAreaChartVisualization(areaChartVisualization);
				if (result == null) result = caseLineChartVisualization(areaChartVisualization);
				if (result == null) result = caseChartVisualization(areaChartVisualization);
				if (result == null) result = caseTwoDimVisualization(areaChartVisualization);
				if (result == null) result = caseOneDimVisualization(areaChartVisualization);
				if (result == null) result = caseVisualization(areaChartVisualization);
				if (result == null) result = caseBEViewsElement(areaChartVisualization);
				if (result == null) result = caseEntity(areaChartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ASSET_PAGE: {
				AssetPage assetPage = (AssetPage)theEObject;
				T result = caseAssetPage(assetPage);
				if (result == null) result = casePage(assetPage);
				if (result == null) result = caseBEViewsElement(assetPage);
				if (result == null) result = caseEntity(assetPage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.BACKGROUND_FORMAT: {
				BackgroundFormat backgroundFormat = (BackgroundFormat)theEObject;
				T result = caseBackgroundFormat(backgroundFormat);
				if (result == null) result = caseBEViewsElement(backgroundFormat);
				if (result == null) result = caseEntity(backgroundFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION: {
				BarChartVisualization barChartVisualization = (BarChartVisualization)theEObject;
				T result = caseBarChartVisualization(barChartVisualization);
				if (result == null) result = caseChartVisualization(barChartVisualization);
				if (result == null) result = caseTwoDimVisualization(barChartVisualization);
				if (result == null) result = caseOneDimVisualization(barChartVisualization);
				if (result == null) result = caseVisualization(barChartVisualization);
				if (result == null) result = caseBEViewsElement(barChartVisualization);
				if (result == null) result = caseEntity(barChartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.BLUE_PRINT_COMPONENT: {
				BluePrintComponent bluePrintComponent = (BluePrintComponent)theEObject;
				T result = caseBluePrintComponent(bluePrintComponent);
				if (result == null) result = caseComponent(bluePrintComponent);
				if (result == null) result = caseBEViewsElement(bluePrintComponent);
				if (result == null) result = caseEntity(bluePrintComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG: {
				CategoryGuidelineConfig categoryGuidelineConfig = (CategoryGuidelineConfig)theEObject;
				T result = caseCategoryGuidelineConfig(categoryGuidelineConfig);
				if (result == null) result = caseBEViewsElement(categoryGuidelineConfig);
				if (result == null) result = caseEntity(categoryGuidelineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_CATEGORY_DATA_CONFIG: {
				ChartCategoryDataConfig chartCategoryDataConfig = (ChartCategoryDataConfig)theEObject;
				T result = caseChartCategoryDataConfig(chartCategoryDataConfig);
				if (result == null) result = caseDataConfig(chartCategoryDataConfig);
				if (result == null) result = caseBEViewsElement(chartCategoryDataConfig);
				if (result == null) result = caseEntity(chartCategoryDataConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG: {
				ChartCategoryGuidelineConfig chartCategoryGuidelineConfig = (ChartCategoryGuidelineConfig)theEObject;
				T result = caseChartCategoryGuidelineConfig(chartCategoryGuidelineConfig);
				if (result == null) result = caseCategoryGuidelineConfig(chartCategoryGuidelineConfig);
				if (result == null) result = caseBEViewsElement(chartCategoryGuidelineConfig);
				if (result == null) result = caseEntity(chartCategoryGuidelineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_COMPONENT: {
				ChartComponent chartComponent = (ChartComponent)theEObject;
				T result = caseChartComponent(chartComponent);
				if (result == null) result = caseComponent(chartComponent);
				if (result == null) result = caseBEViewsElement(chartComponent);
				if (result == null) result = caseEntity(chartComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET: {
				ChartComponentColorSet chartComponentColorSet = (ChartComponentColorSet)theEObject;
				T result = caseChartComponentColorSet(chartComponentColorSet);
				if (result == null) result = caseComponentColorSet(chartComponentColorSet);
				if (result == null) result = caseBEViewsElement(chartComponentColorSet);
				if (result == null) result = caseEntity(chartComponentColorSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG: {
				ChartSeriesConfig chartSeriesConfig = (ChartSeriesConfig)theEObject;
				T result = caseChartSeriesConfig(chartSeriesConfig);
				if (result == null) result = caseTwoDimSeriesConfig(chartSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(chartSeriesConfig);
				if (result == null) result = caseSeriesConfig(chartSeriesConfig);
				if (result == null) result = caseBEViewsElement(chartSeriesConfig);
				if (result == null) result = caseEntity(chartSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_VALUE_DATA_CONFIG: {
				ChartValueDataConfig chartValueDataConfig = (ChartValueDataConfig)theEObject;
				T result = caseChartValueDataConfig(chartValueDataConfig);
				if (result == null) result = caseDataConfig(chartValueDataConfig);
				if (result == null) result = caseBEViewsElement(chartValueDataConfig);
				if (result == null) result = caseEntity(chartValueDataConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG: {
				ChartValueGuidelineConfig chartValueGuidelineConfig = (ChartValueGuidelineConfig)theEObject;
				T result = caseChartValueGuidelineConfig(chartValueGuidelineConfig);
				if (result == null) result = caseValueGuidelineConfig(chartValueGuidelineConfig);
				if (result == null) result = caseBEViewsElement(chartValueGuidelineConfig);
				if (result == null) result = caseEntity(chartValueGuidelineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CHART_VISUALIZATION: {
				ChartVisualization chartVisualization = (ChartVisualization)theEObject;
				T result = caseChartVisualization(chartVisualization);
				if (result == null) result = caseTwoDimVisualization(chartVisualization);
				if (result == null) result = caseOneDimVisualization(chartVisualization);
				if (result == null) result = caseVisualization(chartVisualization);
				if (result == null) result = caseBEViewsElement(chartVisualization);
				if (result == null) result = caseEntity(chartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CLASSIFIER_COMPONENT: {
				ClassifierComponent classifierComponent = (ClassifierComponent)theEObject;
				T result = caseClassifierComponent(classifierComponent);
				if (result == null) result = caseComponent(classifierComponent);
				if (result == null) result = caseBEViewsElement(classifierComponent);
				if (result == null) result = caseEntity(classifierComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CLASSIFIER_SERIES_CONFIG: {
				ClassifierSeriesConfig classifierSeriesConfig = (ClassifierSeriesConfig)theEObject;
				T result = caseClassifierSeriesConfig(classifierSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(classifierSeriesConfig);
				if (result == null) result = caseSeriesConfig(classifierSeriesConfig);
				if (result == null) result = caseBEViewsElement(classifierSeriesConfig);
				if (result == null) result = caseEntity(classifierSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CLASSIFIER_VISUALIZATION: {
				ClassifierVisualization classifierVisualization = (ClassifierVisualization)theEObject;
				T result = caseClassifierVisualization(classifierVisualization);
				if (result == null) result = caseOneDimVisualization(classifierVisualization);
				if (result == null) result = caseVisualization(classifierVisualization);
				if (result == null) result = caseBEViewsElement(classifierVisualization);
				if (result == null) result = caseEntity(classifierVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.COMPONENT: {
				Component component = (Component)theEObject;
				T result = caseComponent(component);
				if (result == null) result = caseBEViewsElement(component);
				if (result == null) result = caseEntity(component);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.COMPONENT_COLOR_SET: {
				ComponentColorSet componentColorSet = (ComponentColorSet)theEObject;
				T result = caseComponentColorSet(componentColorSet);
				if (result == null) result = caseBEViewsElement(componentColorSet);
				if (result == null) result = caseEntity(componentColorSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.DASHBOARD_PAGE: {
				DashboardPage dashboardPage = (DashboardPage)theEObject;
				T result = caseDashboardPage(dashboardPage);
				if (result == null) result = casePage(dashboardPage);
				if (result == null) result = caseBEViewsElement(dashboardPage);
				if (result == null) result = caseEntity(dashboardPage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.DATA_CONFIG: {
				DataConfig dataConfig = (DataConfig)theEObject;
				T result = caseDataConfig(dataConfig);
				if (result == null) result = caseBEViewsElement(dataConfig);
				if (result == null) result = caseEntity(dataConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.DATA_EXTRACTOR: {
				DataExtractor dataExtractor = (DataExtractor)theEObject;
				T result = caseDataExtractor(dataExtractor);
				if (result == null) result = caseBEViewsElement(dataExtractor);
				if (result == null) result = caseEntity(dataExtractor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.DATA_FORMAT: {
				DataFormat dataFormat = (DataFormat)theEObject;
				T result = caseDataFormat(dataFormat);
				if (result == null) result = caseBEViewsElement(dataFormat);
				if (result == null) result = caseEntity(dataFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ACTION_RULE: {
				ActionRule actionRule = (ActionRule)theEObject;
				T result = caseActionRule(actionRule);
				if (result == null) result = caseBEViewsElement(actionRule);
				if (result == null) result = caseEntity(actionRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.DATA_SOURCE: {
				DataSource dataSource = (DataSource)theEObject;
				T result = caseDataSource(dataSource);
				if (result == null) result = caseBEViewsElement(dataSource);
				if (result == null) result = caseEntity(dataSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.QUERY_PARAM: {
				QueryParam queryParam = (QueryParam)theEObject;
				T result = caseQueryParam(queryParam);
				if (result == null) result = caseBEViewsElement(queryParam);
				if (result == null) result = caseEntity(queryParam);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT: {
				Alert alert = (Alert)theEObject;
				T result = caseAlert(alert);
				if (result == null) result = caseBEViewsElement(alert);
				if (result == null) result = caseEntity(alert);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.RANGE_ALERT: {
				RangeAlert rangeAlert = (RangeAlert)theEObject;
				T result = caseRangeAlert(rangeAlert);
				if (result == null) result = caseAlert(rangeAlert);
				if (result == null) result = caseBEViewsElement(rangeAlert);
				if (result == null) result = caseEntity(rangeAlert);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ALERT_ACTION: {
				AlertAction alertAction = (AlertAction)theEObject;
				T result = caseAlertAction(alertAction);
				if (result == null) result = caseBEViewsElement(alertAction);
				if (result == null) result = caseEntity(alertAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION: {
				VisualAlertAction visualAlertAction = (VisualAlertAction)theEObject;
				T result = caseVisualAlertAction(visualAlertAction);
				if (result == null) result = caseAlertAction(visualAlertAction);
				if (result == null) result = caseBEViewsElement(visualAlertAction);
				if (result == null) result = caseEntity(visualAlertAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.FLOW_LAYOUT: {
				FlowLayout flowLayout = (FlowLayout)theEObject;
				T result = caseFlowLayout(flowLayout);
				if (result == null) result = caseLayout(flowLayout);
				if (result == null) result = caseBEViewsElement(flowLayout);
				if (result == null) result = caseEntity(flowLayout);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT: {
				FlowLayoutConstraint flowLayoutConstraint = (FlowLayoutConstraint)theEObject;
				T result = caseFlowLayoutConstraint(flowLayoutConstraint);
				if (result == null) result = caseLayoutConstraint(flowLayoutConstraint);
				if (result == null) result = caseBEViewsElement(flowLayoutConstraint);
				if (result == null) result = caseEntity(flowLayoutConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.FOREGROUND_FORMAT: {
				ForegroundFormat foregroundFormat = (ForegroundFormat)theEObject;
				T result = caseForegroundFormat(foregroundFormat);
				if (result == null) result = caseBEViewsElement(foregroundFormat);
				if (result == null) result = caseEntity(foregroundFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.FORMAT_STYLE: {
				FormatStyle formatStyle = (FormatStyle)theEObject;
				T result = caseFormatStyle(formatStyle);
				if (result == null) result = caseBEViewsElement(formatStyle);
				if (result == null) result = caseEntity(formatStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT: {
				IndicatorFieldFormat indicatorFieldFormat = (IndicatorFieldFormat)theEObject;
				T result = caseIndicatorFieldFormat(indicatorFieldFormat);
				if (result == null) result = caseTextFieldFormat(indicatorFieldFormat);
				if (result == null) result = caseDataFormat(indicatorFieldFormat);
				if (result == null) result = caseBEViewsElement(indicatorFieldFormat);
				if (result == null) result = caseEntity(indicatorFieldFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VIEW: {
				View view = (View)theEObject;
				T result = caseView(view);
				if (result == null) result = caseBEViewsElement(view);
				if (result == null) result = caseEntity(view);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VIEW_ATTRIBUTE: {
				ViewAttribute viewAttribute = (ViewAttribute)theEObject;
				T result = caseViewAttribute(viewAttribute);
				if (result == null) result = caseBEViewsElement(viewAttribute);
				if (result == null) result = caseEntity(viewAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VIEW_VERSION: {
				ViewVersion viewVersion = (ViewVersion)theEObject;
				T result = caseViewVersion(viewVersion);
				if (result == null) result = caseBEViewsElement(viewVersion);
				if (result == null) result = caseEntity(viewVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT: {
				BEViewsElement beViewsElement = (BEViewsElement)theEObject;
				T result = caseBEViewsElement(beViewsElement);
				if (result == null) result = caseEntity(beViewsElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.FOOTER: {
				Footer footer = (Footer)theEObject;
				T result = caseFooter(footer);
				if (result == null) result = caseBEViewsElement(footer);
				if (result == null) result = caseEntity(footer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.HEADER: {
				Header header = (Header)theEObject;
				T result = caseHeader(header);
				if (result == null) result = caseBEViewsElement(header);
				if (result == null) result = caseEntity(header);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VIEW_LOCALE: {
				ViewLocale viewLocale = (ViewLocale)theEObject;
				T result = caseViewLocale(viewLocale);
				if (result == null) result = caseBEViewsElement(viewLocale);
				if (result == null) result = caseEntity(viewLocale);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.LOGIN: {
				Login login = (Login)theEObject;
				T result = caseLogin(login);
				if (result == null) result = caseBEViewsElement(login);
				if (result == null) result = caseEntity(login);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SKIN: {
				Skin skin = (Skin)theEObject;
				T result = caseSkin(skin);
				if (result == null) result = caseBEViewsElement(skin);
				if (result == null) result = caseEntity(skin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.LAYOUT: {
				Layout layout = (Layout)theEObject;
				T result = caseLayout(layout);
				if (result == null) result = caseBEViewsElement(layout);
				if (result == null) result = caseEntity(layout);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.LAYOUT_CONSTRAINT: {
				LayoutConstraint layoutConstraint = (LayoutConstraint)theEObject;
				T result = caseLayoutConstraint(layoutConstraint);
				if (result == null) result = caseBEViewsElement(layoutConstraint);
				if (result == null) result = caseEntity(layoutConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.LEGEND_FORMAT: {
				LegendFormat legendFormat = (LegendFormat)theEObject;
				T result = caseLegendFormat(legendFormat);
				if (result == null) result = caseBEViewsElement(legendFormat);
				if (result == null) result = caseEntity(legendFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION: {
				LineChartVisualization lineChartVisualization = (LineChartVisualization)theEObject;
				T result = caseLineChartVisualization(lineChartVisualization);
				if (result == null) result = caseChartVisualization(lineChartVisualization);
				if (result == null) result = caseTwoDimVisualization(lineChartVisualization);
				if (result == null) result = caseOneDimVisualization(lineChartVisualization);
				if (result == null) result = caseVisualization(lineChartVisualization);
				if (result == null) result = caseBEViewsElement(lineChartVisualization);
				if (result == null) result = caseEntity(lineChartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG: {
				OneDimSeriesConfig oneDimSeriesConfig = (OneDimSeriesConfig)theEObject;
				T result = caseOneDimSeriesConfig(oneDimSeriesConfig);
				if (result == null) result = caseSeriesConfig(oneDimSeriesConfig);
				if (result == null) result = caseBEViewsElement(oneDimSeriesConfig);
				if (result == null) result = caseEntity(oneDimSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION: {
				OneDimVisualization oneDimVisualization = (OneDimVisualization)theEObject;
				T result = caseOneDimVisualization(oneDimVisualization);
				if (result == null) result = caseVisualization(oneDimVisualization);
				if (result == null) result = caseBEViewsElement(oneDimVisualization);
				if (result == null) result = caseEntity(oneDimVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PAGE: {
				Page page = (Page)theEObject;
				T result = casePage(page);
				if (result == null) result = caseBEViewsElement(page);
				if (result == null) result = caseEntity(page);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PAGE_SELECTOR_COMPONENT: {
				PageSelectorComponent pageSelectorComponent = (PageSelectorComponent)theEObject;
				T result = casePageSelectorComponent(pageSelectorComponent);
				if (result == null) result = caseComponent(pageSelectorComponent);
				if (result == null) result = caseBEViewsElement(pageSelectorComponent);
				if (result == null) result = caseEntity(pageSelectorComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PAGE_VISUALIZATION: {
				PageVisualization pageVisualization = (PageVisualization)theEObject;
				T result = casePageVisualization(pageVisualization);
				if (result == null) result = caseTextVisualization(pageVisualization);
				if (result == null) result = caseTwoDimVisualization(pageVisualization);
				if (result == null) result = caseOneDimVisualization(pageVisualization);
				if (result == null) result = caseVisualization(pageVisualization);
				if (result == null) result = caseBEViewsElement(pageVisualization);
				if (result == null) result = caseEntity(pageVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PANEL: {
				Panel panel = (Panel)theEObject;
				T result = casePanel(panel);
				if (result == null) result = caseBEViewsElement(panel);
				if (result == null) result = caseEntity(panel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PARTITION: {
				Partition partition = (Partition)theEObject;
				T result = casePartition(partition);
				if (result == null) result = caseBEViewsElement(partition);
				if (result == null) result = caseEntity(partition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION: {
				PieChartVisualization pieChartVisualization = (PieChartVisualization)theEObject;
				T result = casePieChartVisualization(pieChartVisualization);
				if (result == null) result = caseChartVisualization(pieChartVisualization);
				if (result == null) result = caseTwoDimVisualization(pieChartVisualization);
				if (result == null) result = caseOneDimVisualization(pieChartVisualization);
				if (result == null) result = caseVisualization(pieChartVisualization);
				if (result == null) result = caseBEViewsElement(pieChartVisualization);
				if (result == null) result = caseEntity(pieChartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT: {
				PlotAreaFormat plotAreaFormat = (PlotAreaFormat)theEObject;
				T result = casePlotAreaFormat(plotAreaFormat);
				if (result == null) result = caseBEViewsElement(plotAreaFormat);
				if (result == null) result = caseEntity(plotAreaFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT: {
				StateMachineComponent stateMachineComponent = (StateMachineComponent)theEObject;
				T result = caseStateMachineComponent(stateMachineComponent);
				if (result == null) result = caseComponent(stateMachineComponent);
				if (result == null) result = caseBEViewsElement(stateMachineComponent);
				if (result == null) result = caseEntity(stateMachineComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VALUE_OPTION: {
				ValueOption valueOption = (ValueOption)theEObject;
				T result = caseValueOption(valueOption);
				if (result == null) result = caseBEViewsElement(valueOption);
				if (result == null) result = caseEntity(valueOption);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.CONSTANT_VALUE_OPTION: {
				ConstantValueOption constantValueOption = (ConstantValueOption)theEObject;
				T result = caseConstantValueOption(constantValueOption);
				if (result == null) result = caseValueOption(constantValueOption);
				if (result == null) result = caseBEViewsElement(constantValueOption);
				if (result == null) result = caseEntity(constantValueOption);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION: {
				FieldReferenceValueOption fieldReferenceValueOption = (FieldReferenceValueOption)theEObject;
				T result = caseFieldReferenceValueOption(fieldReferenceValueOption);
				if (result == null) result = caseValueOption(fieldReferenceValueOption);
				if (result == null) result = caseBEViewsElement(fieldReferenceValueOption);
				if (result == null) result = caseEntity(fieldReferenceValueOption);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.PROGRESS_BAR_FIELD_FORMAT: {
				ProgressBarFieldFormat progressBarFieldFormat = (ProgressBarFieldFormat)theEObject;
				T result = caseProgressBarFieldFormat(progressBarFieldFormat);
				if (result == null) result = caseIndicatorFieldFormat(progressBarFieldFormat);
				if (result == null) result = caseTextFieldFormat(progressBarFieldFormat);
				if (result == null) result = caseDataFormat(progressBarFieldFormat);
				if (result == null) result = caseBEViewsElement(progressBarFieldFormat);
				if (result == null) result = caseEntity(progressBarFieldFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.QUERY_MANAGER_COMPONENT: {
				QueryManagerComponent queryManagerComponent = (QueryManagerComponent)theEObject;
				T result = caseQueryManagerComponent(queryManagerComponent);
				if (result == null) result = caseComponent(queryManagerComponent);
				if (result == null) result = caseBEViewsElement(queryManagerComponent);
				if (result == null) result = caseEntity(queryManagerComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_SERIES_CONFIG: {
				RangePlotChartSeriesConfig rangePlotChartSeriesConfig = (RangePlotChartSeriesConfig)theEObject;
				T result = caseRangePlotChartSeriesConfig(rangePlotChartSeriesConfig);
				if (result == null) result = caseChartSeriesConfig(rangePlotChartSeriesConfig);
				if (result == null) result = caseTwoDimSeriesConfig(rangePlotChartSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(rangePlotChartSeriesConfig);
				if (result == null) result = caseSeriesConfig(rangePlotChartSeriesConfig);
				if (result == null) result = caseBEViewsElement(rangePlotChartSeriesConfig);
				if (result == null) result = caseEntity(rangePlotChartSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION: {
				RangePlotChartVisualization rangePlotChartVisualization = (RangePlotChartVisualization)theEObject;
				T result = caseRangePlotChartVisualization(rangePlotChartVisualization);
				if (result == null) result = caseChartVisualization(rangePlotChartVisualization);
				if (result == null) result = caseTwoDimVisualization(rangePlotChartVisualization);
				if (result == null) result = caseOneDimVisualization(rangePlotChartVisualization);
				if (result == null) result = caseVisualization(rangePlotChartVisualization);
				if (result == null) result = caseBEViewsElement(rangePlotChartVisualization);
				if (result == null) result = caseEntity(rangePlotChartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.RELATED_ASSETS_COMPONENT: {
				RelatedAssetsComponent relatedAssetsComponent = (RelatedAssetsComponent)theEObject;
				T result = caseRelatedAssetsComponent(relatedAssetsComponent);
				if (result == null) result = caseComponent(relatedAssetsComponent);
				if (result == null) result = caseBEViewsElement(relatedAssetsComponent);
				if (result == null) result = caseEntity(relatedAssetsComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION: {
				ScatterPlotChartVisualization scatterPlotChartVisualization = (ScatterPlotChartVisualization)theEObject;
				T result = caseScatterPlotChartVisualization(scatterPlotChartVisualization);
				if (result == null) result = caseChartVisualization(scatterPlotChartVisualization);
				if (result == null) result = caseTwoDimVisualization(scatterPlotChartVisualization);
				if (result == null) result = caseOneDimVisualization(scatterPlotChartVisualization);
				if (result == null) result = caseVisualization(scatterPlotChartVisualization);
				if (result == null) result = caseBEViewsElement(scatterPlotChartVisualization);
				if (result == null) result = caseEntity(scatterPlotChartVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SEARCH_PAGE: {
				SearchPage searchPage = (SearchPage)theEObject;
				T result = caseSearchPage(searchPage);
				if (result == null) result = casePage(searchPage);
				if (result == null) result = caseBEViewsElement(searchPage);
				if (result == null) result = caseEntity(searchPage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SEARCH_VIEW_COMPONENT: {
				SearchViewComponent searchViewComponent = (SearchViewComponent)theEObject;
				T result = caseSearchViewComponent(searchViewComponent);
				if (result == null) result = caseComponent(searchViewComponent);
				if (result == null) result = caseBEViewsElement(searchViewComponent);
				if (result == null) result = caseEntity(searchViewComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SERIES_COLOR: {
				SeriesColor seriesColor = (SeriesColor)theEObject;
				T result = caseSeriesColor(seriesColor);
				if (result == null) result = caseBEViewsElement(seriesColor);
				if (result == null) result = caseEntity(seriesColor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SERIES_CONFIG: {
				SeriesConfig seriesConfig = (SeriesConfig)theEObject;
				T result = caseSeriesConfig(seriesConfig);
				if (result == null) result = caseBEViewsElement(seriesConfig);
				if (result == null) result = caseEntity(seriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.SERIES_CONFIG_GENERATOR: {
				SeriesConfigGenerator seriesConfigGenerator = (SeriesConfigGenerator)theEObject;
				T result = caseSeriesConfigGenerator(seriesConfigGenerator);
				if (result == null) result = caseBEViewsElement(seriesConfigGenerator);
				if (result == null) result = caseEntity(seriesConfigGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_CATEGORY_DATA_CONFIG: {
				TextCategoryDataConfig textCategoryDataConfig = (TextCategoryDataConfig)theEObject;
				T result = caseTextCategoryDataConfig(textCategoryDataConfig);
				if (result == null) result = caseDataConfig(textCategoryDataConfig);
				if (result == null) result = caseBEViewsElement(textCategoryDataConfig);
				if (result == null) result = caseEntity(textCategoryDataConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG: {
				TextCategoryGuidelineConfig textCategoryGuidelineConfig = (TextCategoryGuidelineConfig)theEObject;
				T result = caseTextCategoryGuidelineConfig(textCategoryGuidelineConfig);
				if (result == null) result = caseCategoryGuidelineConfig(textCategoryGuidelineConfig);
				if (result == null) result = caseBEViewsElement(textCategoryGuidelineConfig);
				if (result == null) result = caseEntity(textCategoryGuidelineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_COMPONENT: {
				TextComponent textComponent = (TextComponent)theEObject;
				T result = caseTextComponent(textComponent);
				if (result == null) result = caseComponent(textComponent);
				if (result == null) result = caseBEViewsElement(textComponent);
				if (result == null) result = caseEntity(textComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET: {
				TextComponentColorSet textComponentColorSet = (TextComponentColorSet)theEObject;
				T result = caseTextComponentColorSet(textComponentColorSet);
				if (result == null) result = caseComponentColorSet(textComponentColorSet);
				if (result == null) result = caseBEViewsElement(textComponentColorSet);
				if (result == null) result = caseEntity(textComponentColorSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_FIELD_FORMAT: {
				TextFieldFormat textFieldFormat = (TextFieldFormat)theEObject;
				T result = caseTextFieldFormat(textFieldFormat);
				if (result == null) result = caseDataFormat(textFieldFormat);
				if (result == null) result = caseBEViewsElement(textFieldFormat);
				if (result == null) result = caseEntity(textFieldFormat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_SERIES_CONFIG: {
				TextSeriesConfig textSeriesConfig = (TextSeriesConfig)theEObject;
				T result = caseTextSeriesConfig(textSeriesConfig);
				if (result == null) result = caseTwoDimSeriesConfig(textSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(textSeriesConfig);
				if (result == null) result = caseSeriesConfig(textSeriesConfig);
				if (result == null) result = caseBEViewsElement(textSeriesConfig);
				if (result == null) result = caseEntity(textSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG: {
				TextValueDataConfig textValueDataConfig = (TextValueDataConfig)theEObject;
				T result = caseTextValueDataConfig(textValueDataConfig);
				if (result == null) result = caseDataConfig(textValueDataConfig);
				if (result == null) result = caseBEViewsElement(textValueDataConfig);
				if (result == null) result = caseEntity(textValueDataConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG: {
				TextValueGuidelineConfig textValueGuidelineConfig = (TextValueGuidelineConfig)theEObject;
				T result = caseTextValueGuidelineConfig(textValueGuidelineConfig);
				if (result == null) result = caseValueGuidelineConfig(textValueGuidelineConfig);
				if (result == null) result = caseBEViewsElement(textValueGuidelineConfig);
				if (result == null) result = caseEntity(textValueGuidelineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TEXT_VISUALIZATION: {
				TextVisualization textVisualization = (TextVisualization)theEObject;
				T result = caseTextVisualization(textVisualization);
				if (result == null) result = caseTwoDimVisualization(textVisualization);
				if (result == null) result = caseOneDimVisualization(textVisualization);
				if (result == null) result = caseVisualization(textVisualization);
				if (result == null) result = caseBEViewsElement(textVisualization);
				if (result == null) result = caseEntity(textVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG: {
				TwoDimSeriesConfig twoDimSeriesConfig = (TwoDimSeriesConfig)theEObject;
				T result = caseTwoDimSeriesConfig(twoDimSeriesConfig);
				if (result == null) result = caseOneDimSeriesConfig(twoDimSeriesConfig);
				if (result == null) result = caseSeriesConfig(twoDimSeriesConfig);
				if (result == null) result = caseBEViewsElement(twoDimSeriesConfig);
				if (result == null) result = caseEntity(twoDimSeriesConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION: {
				TwoDimVisualization twoDimVisualization = (TwoDimVisualization)theEObject;
				T result = caseTwoDimVisualization(twoDimVisualization);
				if (result == null) result = caseOneDimVisualization(twoDimVisualization);
				if (result == null) result = caseVisualization(twoDimVisualization);
				if (result == null) result = caseBEViewsElement(twoDimVisualization);
				if (result == null) result = caseEntity(twoDimVisualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG: {
				ValueGuidelineConfig valueGuidelineConfig = (ValueGuidelineConfig)theEObject;
				T result = caseValueGuidelineConfig(valueGuidelineConfig);
				if (result == null) result = caseBEViewsElement(valueGuidelineConfig);
				if (result == null) result = caseEntity(valueGuidelineConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VALUE_LABEL_STYLE: {
				ValueLabelStyle valueLabelStyle = (ValueLabelStyle)theEObject;
				T result = caseValueLabelStyle(valueLabelStyle);
				if (result == null) result = caseBEViewsElement(valueLabelStyle);
				if (result == null) result = caseEntity(valueLabelStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.VISUALIZATION: {
				Visualization visualization = (Visualization)theEObject;
				T result = caseVisualization(visualization);
				if (result == null) result = caseBEViewsElement(visualization);
				if (result == null) result = caseEntity(visualization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.ROLE_PREFERENCE: {
				RolePreference rolePreference = (RolePreference)theEObject;
				T result = caseRolePreference(rolePreference);
				if (result == null) result = caseBEViewsElement(rolePreference);
				if (result == null) result = caseEntity(rolePreference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER: {
				ComponentGalleryFolder componentGalleryFolder = (ComponentGalleryFolder)theEObject;
				T result = caseComponentGalleryFolder(componentGalleryFolder);
				if (result == null) result = caseBEViewsElement(componentGalleryFolder);
				if (result == null) result = caseEntity(componentGalleryFolder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.HEADER_LINK: {
				HeaderLink headerLink = (HeaderLink)theEObject;
				T result = caseHeaderLink(headerLink);
				if (result == null) result = caseBEViewsElement(headerLink);
				if (result == null) result = caseEntity(headerLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.RELATED_PAGE: {
				RelatedPage relatedPage = (RelatedPage)theEObject;
				T result = caseRelatedPage(relatedPage);
				if (result == null) result = casePage(relatedPage);
				if (result == null) result = caseBEViewsElement(relatedPage);
				if (result == null) result = caseEntity(relatedPage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BEViewsConfigurationPackage.RELATED_ELEMENTS_COMPONENT: {
				RelatedElementsComponent relatedElementsComponent = (RelatedElementsComponent)theEObject;
				T result = caseRelatedElementsComponent(relatedElementsComponent);
				if (result == null) result = caseComponent(relatedElementsComponent);
				if (result == null) result = caseBEViewsElement(relatedElementsComponent);
				if (result == null) result = caseEntity(relatedElementsComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionDefinition(ActionDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Data Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Data Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateDataConfig(StateDataConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateSeriesConfig(StateSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateVisualization(StateVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertComponent(AlertComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Indicator State Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Indicator State Enumeration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertIndicatorStateEnumeration(AlertIndicatorStateEnumeration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Indicator State Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Indicator State Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertIndicatorStateMap(AlertIndicatorStateMap object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertSeriesConfig(AlertSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertVisualization(AlertVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Area Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Area Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAreaChartVisualization(AreaChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Asset Page</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Asset Page</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssetPage(AssetPage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Background Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Background Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBackgroundFormat(BackgroundFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bar Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bar Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBarChartVisualization(BarChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Blue Print Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Blue Print Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBluePrintComponent(BluePrintComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category Guideline Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category Guideline Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategoryGuidelineConfig(CategoryGuidelineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Category Data Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Category Data Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartCategoryDataConfig(ChartCategoryDataConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Category Guideline Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Category Guideline Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartCategoryGuidelineConfig(ChartCategoryGuidelineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartComponent(ChartComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Component Color Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Component Color Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartComponentColorSet(ChartComponentColorSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartSeriesConfig(ChartSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Value Data Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Value Data Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartValueDataConfig(ChartValueDataConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Value Guideline Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Value Guideline Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartValueGuidelineConfig(ChartValueGuidelineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChartVisualization(ChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifierComponent(ClassifierComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifierSeriesConfig(ClassifierSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifierVisualization(ClassifierVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComponent(Component object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Component Color Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Component Color Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComponentColorSet(ComponentColorSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dashboard Page</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dashboard Page</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDashboardPage(DashboardPage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataConfig(DataConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Extractor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Extractor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataExtractor(DataExtractor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataFormat(DataFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionRule(ActionRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataSource(DataSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Param</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Param</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryParam(QueryParam object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlert(Alert object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Range Alert</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Range Alert</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangeAlert(RangeAlert object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alert Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alert Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlertAction(AlertAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Visual Alert Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Visual Alert Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisualAlertAction(VisualAlertAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Flow Layout</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow Layout</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFlowLayout(FlowLayout object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Flow Layout Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow Layout Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFlowLayoutConstraint(FlowLayoutConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foreground Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foreground Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForegroundFormat(ForegroundFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Format Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Format Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFormatStyle(FormatStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indicator Field Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indicator Field Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndicatorFieldFormat(IndicatorFieldFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseView(View object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>View Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>View Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViewAttribute(ViewAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>View Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>View Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViewVersion(ViewVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BE Views Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BE Views Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBEViewsElement(BEViewsElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Footer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Footer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFooter(Footer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Header</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Header</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeader(Header object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>View Locale</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>View Locale</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViewLocale(ViewLocale object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Login</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Login</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLogin(Login object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Skin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Skin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSkin(Skin object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Layout</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Layout</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLayout(Layout object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Layout Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Layout Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLayoutConstraint(LayoutConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Legend Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Legend Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLegendFormat(LegendFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Line Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Line Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLineChartVisualization(LineChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One Dim Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One Dim Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneDimSeriesConfig(OneDimSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One Dim Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One Dim Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneDimVisualization(OneDimVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Page</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Page</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePage(Page object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Page Selector Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Page Selector Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePageSelectorComponent(PageSelectorComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Page Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Page Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePageVisualization(PageVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Panel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Panel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePanel(Panel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Partition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Partition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePartition(Partition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pie Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pie Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePieChartVisualization(PieChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plot Area Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plot Area Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlotAreaFormat(PlotAreaFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Machine Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Machine Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStateMachineComponent(StateMachineComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Option</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Option</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueOption(ValueOption object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant Value Option</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant Value Option</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstantValueOption(ConstantValueOption object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Reference Value Option</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Reference Value Option</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldReferenceValueOption(FieldReferenceValueOption object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Progress Bar Field Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Progress Bar Field Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProgressBarFieldFormat(ProgressBarFieldFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Manager Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Manager Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryManagerComponent(QueryManagerComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Range Plot Chart Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Range Plot Chart Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangePlotChartSeriesConfig(RangePlotChartSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Range Plot Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Range Plot Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangePlotChartVisualization(RangePlotChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Related Assets Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related Assets Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelatedAssetsComponent(RelatedAssetsComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scatter Plot Chart Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scatter Plot Chart Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScatterPlotChartVisualization(ScatterPlotChartVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Search Page</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Search Page</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSearchPage(SearchPage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Search View Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Search View Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSearchViewComponent(SearchViewComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Series Color</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Series Color</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSeriesColor(SeriesColor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSeriesConfig(SeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Series Config Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Series Config Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSeriesConfigGenerator(SeriesConfigGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Category Data Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Category Data Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextCategoryDataConfig(TextCategoryDataConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Category Guideline Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Category Guideline Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextCategoryGuidelineConfig(TextCategoryGuidelineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextComponent(TextComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Component Color Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Component Color Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextComponentColorSet(TextComponentColorSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Field Format</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Field Format</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextFieldFormat(TextFieldFormat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextSeriesConfig(TextSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Value Data Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Value Data Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextValueDataConfig(TextValueDataConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Value Guideline Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Value Guideline Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextValueGuidelineConfig(TextValueGuidelineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextVisualization(TextVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Two Dim Series Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Two Dim Series Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTwoDimSeriesConfig(TwoDimSeriesConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Two Dim Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Two Dim Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTwoDimVisualization(TwoDimVisualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Guideline Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Guideline Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueGuidelineConfig(ValueGuidelineConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Label Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Label Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueLabelStyle(ValueLabelStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Visualization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Visualization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisualization(Visualization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role Preference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role Preference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRolePreference(RolePreference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Component Gallery Folder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Component Gallery Folder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComponentGalleryFolder(ComponentGalleryFolder object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Header Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Header Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeaderLink(HeaderLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Related Page</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related Page</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelatedPage(RelatedPage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Related Elements Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related Elements Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelatedElementsComponent(RelatedElementsComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //BEViewsConfigurationSwitch
