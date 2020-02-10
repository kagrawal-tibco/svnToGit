/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage
 * @generated
 */
public class BEViewsConfigurationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BEViewsConfigurationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsConfigurationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BEViewsConfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BEViewsConfigurationSwitch<Adapter> modelSwitch =
		new BEViewsConfigurationSwitch<Adapter>() {
			@Override
			public Adapter caseActionDefinition(ActionDefinition object) {
				return createActionDefinitionAdapter();
			}
			@Override
			public Adapter caseStateDataConfig(StateDataConfig object) {
				return createStateDataConfigAdapter();
			}
			@Override
			public Adapter caseStateSeriesConfig(StateSeriesConfig object) {
				return createStateSeriesConfigAdapter();
			}
			@Override
			public Adapter caseStateVisualization(StateVisualization object) {
				return createStateVisualizationAdapter();
			}
			@Override
			public Adapter caseAlertComponent(AlertComponent object) {
				return createAlertComponentAdapter();
			}
			@Override
			public Adapter caseAlertIndicatorStateEnumeration(AlertIndicatorStateEnumeration object) {
				return createAlertIndicatorStateEnumerationAdapter();
			}
			@Override
			public Adapter caseAlertIndicatorStateMap(AlertIndicatorStateMap object) {
				return createAlertIndicatorStateMapAdapter();
			}
			@Override
			public Adapter caseAlertSeriesConfig(AlertSeriesConfig object) {
				return createAlertSeriesConfigAdapter();
			}
			@Override
			public Adapter caseAlertVisualization(AlertVisualization object) {
				return createAlertVisualizationAdapter();
			}
			@Override
			public Adapter caseAreaChartVisualization(AreaChartVisualization object) {
				return createAreaChartVisualizationAdapter();
			}
			@Override
			public Adapter caseAssetPage(AssetPage object) {
				return createAssetPageAdapter();
			}
			@Override
			public Adapter caseBackgroundFormat(BackgroundFormat object) {
				return createBackgroundFormatAdapter();
			}
			@Override
			public Adapter caseBarChartVisualization(BarChartVisualization object) {
				return createBarChartVisualizationAdapter();
			}
			@Override
			public Adapter caseBluePrintComponent(BluePrintComponent object) {
				return createBluePrintComponentAdapter();
			}
			@Override
			public Adapter caseCategoryGuidelineConfig(CategoryGuidelineConfig object) {
				return createCategoryGuidelineConfigAdapter();
			}
			@Override
			public Adapter caseChartCategoryDataConfig(ChartCategoryDataConfig object) {
				return createChartCategoryDataConfigAdapter();
			}
			@Override
			public Adapter caseChartCategoryGuidelineConfig(ChartCategoryGuidelineConfig object) {
				return createChartCategoryGuidelineConfigAdapter();
			}
			@Override
			public Adapter caseChartComponent(ChartComponent object) {
				return createChartComponentAdapter();
			}
			@Override
			public Adapter caseChartComponentColorSet(ChartComponentColorSet object) {
				return createChartComponentColorSetAdapter();
			}
			@Override
			public Adapter caseChartSeriesConfig(ChartSeriesConfig object) {
				return createChartSeriesConfigAdapter();
			}
			@Override
			public Adapter caseChartValueDataConfig(ChartValueDataConfig object) {
				return createChartValueDataConfigAdapter();
			}
			@Override
			public Adapter caseChartValueGuidelineConfig(ChartValueGuidelineConfig object) {
				return createChartValueGuidelineConfigAdapter();
			}
			@Override
			public Adapter caseChartVisualization(ChartVisualization object) {
				return createChartVisualizationAdapter();
			}
			@Override
			public Adapter caseClassifierComponent(ClassifierComponent object) {
				return createClassifierComponentAdapter();
			}
			@Override
			public Adapter caseClassifierSeriesConfig(ClassifierSeriesConfig object) {
				return createClassifierSeriesConfigAdapter();
			}
			@Override
			public Adapter caseClassifierVisualization(ClassifierVisualization object) {
				return createClassifierVisualizationAdapter();
			}
			@Override
			public Adapter caseComponent(Component object) {
				return createComponentAdapter();
			}
			@Override
			public Adapter caseComponentColorSet(ComponentColorSet object) {
				return createComponentColorSetAdapter();
			}
			@Override
			public Adapter caseDashboardPage(DashboardPage object) {
				return createDashboardPageAdapter();
			}
			@Override
			public Adapter caseDataConfig(DataConfig object) {
				return createDataConfigAdapter();
			}
			@Override
			public Adapter caseDataExtractor(DataExtractor object) {
				return createDataExtractorAdapter();
			}
			@Override
			public Adapter caseDataFormat(DataFormat object) {
				return createDataFormatAdapter();
			}
			@Override
			public Adapter caseActionRule(ActionRule object) {
				return createActionRuleAdapter();
			}
			@Override
			public Adapter caseDataSource(DataSource object) {
				return createDataSourceAdapter();
			}
			@Override
			public Adapter caseQueryParam(QueryParam object) {
				return createQueryParamAdapter();
			}
			@Override
			public Adapter caseAlert(Alert object) {
				return createAlertAdapter();
			}
			@Override
			public Adapter caseRangeAlert(RangeAlert object) {
				return createRangeAlertAdapter();
			}
			@Override
			public Adapter caseAlertAction(AlertAction object) {
				return createAlertActionAdapter();
			}
			@Override
			public Adapter caseVisualAlertAction(VisualAlertAction object) {
				return createVisualAlertActionAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseFlowLayout(FlowLayout object) {
				return createFlowLayoutAdapter();
			}
			@Override
			public Adapter caseFlowLayoutConstraint(FlowLayoutConstraint object) {
				return createFlowLayoutConstraintAdapter();
			}
			@Override
			public Adapter caseForegroundFormat(ForegroundFormat object) {
				return createForegroundFormatAdapter();
			}
			@Override
			public Adapter caseFormatStyle(FormatStyle object) {
				return createFormatStyleAdapter();
			}
			@Override
			public Adapter caseIndicatorFieldFormat(IndicatorFieldFormat object) {
				return createIndicatorFieldFormatAdapter();
			}
			@Override
			public Adapter caseView(View object) {
				return createViewAdapter();
			}
			@Override
			public Adapter caseViewAttribute(ViewAttribute object) {
				return createViewAttributeAdapter();
			}
			@Override
			public Adapter caseViewVersion(ViewVersion object) {
				return createViewVersionAdapter();
			}
			@Override
			public Adapter caseBEViewsElement(BEViewsElement object) {
				return createBEViewsElementAdapter();
			}
			@Override
			public Adapter caseFooter(Footer object) {
				return createFooterAdapter();
			}
			@Override
			public Adapter caseHeader(Header object) {
				return createHeaderAdapter();
			}
			@Override
			public Adapter caseViewLocale(ViewLocale object) {
				return createViewLocaleAdapter();
			}
			@Override
			public Adapter caseLogin(Login object) {
				return createLoginAdapter();
			}
			@Override
			public Adapter caseSkin(Skin object) {
				return createSkinAdapter();
			}
			@Override
			public Adapter caseLayout(Layout object) {
				return createLayoutAdapter();
			}
			@Override
			public Adapter caseLayoutConstraint(LayoutConstraint object) {
				return createLayoutConstraintAdapter();
			}
			@Override
			public Adapter caseLegendFormat(LegendFormat object) {
				return createLegendFormatAdapter();
			}
			@Override
			public Adapter caseLineChartVisualization(LineChartVisualization object) {
				return createLineChartVisualizationAdapter();
			}
			@Override
			public Adapter caseOneDimSeriesConfig(OneDimSeriesConfig object) {
				return createOneDimSeriesConfigAdapter();
			}
			@Override
			public Adapter caseOneDimVisualization(OneDimVisualization object) {
				return createOneDimVisualizationAdapter();
			}
			@Override
			public Adapter casePage(Page object) {
				return createPageAdapter();
			}
			@Override
			public Adapter casePageSelectorComponent(PageSelectorComponent object) {
				return createPageSelectorComponentAdapter();
			}
			@Override
			public Adapter casePageVisualization(PageVisualization object) {
				return createPageVisualizationAdapter();
			}
			@Override
			public Adapter casePanel(Panel object) {
				return createPanelAdapter();
			}
			@Override
			public Adapter casePartition(Partition object) {
				return createPartitionAdapter();
			}
			@Override
			public Adapter casePieChartVisualization(PieChartVisualization object) {
				return createPieChartVisualizationAdapter();
			}
			@Override
			public Adapter casePlotAreaFormat(PlotAreaFormat object) {
				return createPlotAreaFormatAdapter();
			}
			@Override
			public Adapter caseStateMachineComponent(StateMachineComponent object) {
				return createStateMachineComponentAdapter();
			}
			@Override
			public Adapter caseValueOption(ValueOption object) {
				return createValueOptionAdapter();
			}
			@Override
			public Adapter caseConstantValueOption(ConstantValueOption object) {
				return createConstantValueOptionAdapter();
			}
			@Override
			public Adapter caseFieldReferenceValueOption(FieldReferenceValueOption object) {
				return createFieldReferenceValueOptionAdapter();
			}
			@Override
			public Adapter caseProgressBarFieldFormat(ProgressBarFieldFormat object) {
				return createProgressBarFieldFormatAdapter();
			}
			@Override
			public Adapter caseQueryManagerComponent(QueryManagerComponent object) {
				return createQueryManagerComponentAdapter();
			}
			@Override
			public Adapter caseRangePlotChartSeriesConfig(RangePlotChartSeriesConfig object) {
				return createRangePlotChartSeriesConfigAdapter();
			}
			@Override
			public Adapter caseRangePlotChartVisualization(RangePlotChartVisualization object) {
				return createRangePlotChartVisualizationAdapter();
			}
			@Override
			public Adapter caseRelatedAssetsComponent(RelatedAssetsComponent object) {
				return createRelatedAssetsComponentAdapter();
			}
			@Override
			public Adapter caseScatterPlotChartVisualization(ScatterPlotChartVisualization object) {
				return createScatterPlotChartVisualizationAdapter();
			}
			@Override
			public Adapter caseSearchPage(SearchPage object) {
				return createSearchPageAdapter();
			}
			@Override
			public Adapter caseSearchViewComponent(SearchViewComponent object) {
				return createSearchViewComponentAdapter();
			}
			@Override
			public Adapter caseSeriesColor(SeriesColor object) {
				return createSeriesColorAdapter();
			}
			@Override
			public Adapter caseSeriesConfig(SeriesConfig object) {
				return createSeriesConfigAdapter();
			}
			@Override
			public Adapter caseSeriesConfigGenerator(SeriesConfigGenerator object) {
				return createSeriesConfigGeneratorAdapter();
			}
			@Override
			public Adapter caseTextCategoryDataConfig(TextCategoryDataConfig object) {
				return createTextCategoryDataConfigAdapter();
			}
			@Override
			public Adapter caseTextCategoryGuidelineConfig(TextCategoryGuidelineConfig object) {
				return createTextCategoryGuidelineConfigAdapter();
			}
			@Override
			public Adapter caseTextComponent(TextComponent object) {
				return createTextComponentAdapter();
			}
			@Override
			public Adapter caseTextComponentColorSet(TextComponentColorSet object) {
				return createTextComponentColorSetAdapter();
			}
			@Override
			public Adapter caseTextFieldFormat(TextFieldFormat object) {
				return createTextFieldFormatAdapter();
			}
			@Override
			public Adapter caseTextSeriesConfig(TextSeriesConfig object) {
				return createTextSeriesConfigAdapter();
			}
			@Override
			public Adapter caseTextValueDataConfig(TextValueDataConfig object) {
				return createTextValueDataConfigAdapter();
			}
			@Override
			public Adapter caseTextValueGuidelineConfig(TextValueGuidelineConfig object) {
				return createTextValueGuidelineConfigAdapter();
			}
			@Override
			public Adapter caseTextVisualization(TextVisualization object) {
				return createTextVisualizationAdapter();
			}
			@Override
			public Adapter caseTwoDimSeriesConfig(TwoDimSeriesConfig object) {
				return createTwoDimSeriesConfigAdapter();
			}
			@Override
			public Adapter caseTwoDimVisualization(TwoDimVisualization object) {
				return createTwoDimVisualizationAdapter();
			}
			@Override
			public Adapter caseValueGuidelineConfig(ValueGuidelineConfig object) {
				return createValueGuidelineConfigAdapter();
			}
			@Override
			public Adapter caseValueLabelStyle(ValueLabelStyle object) {
				return createValueLabelStyleAdapter();
			}
			@Override
			public Adapter caseVisualization(Visualization object) {
				return createVisualizationAdapter();
			}
			@Override
			public Adapter caseRolePreference(RolePreference object) {
				return createRolePreferenceAdapter();
			}
			@Override
			public Adapter caseComponentGalleryFolder(ComponentGalleryFolder object) {
				return createComponentGalleryFolderAdapter();
			}
			@Override
			public Adapter caseHeaderLink(HeaderLink object) {
				return createHeaderLinkAdapter();
			}
			@Override
			public Adapter caseRelatedPage(RelatedPage object) {
				return createRelatedPageAdapter();
			}
			@Override
			public Adapter caseRelatedElementsComponent(RelatedElementsComponent object) {
				return createRelatedElementsComponentAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object) {
				return createEntityAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition <em>Action Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition
	 * @generated
	 */
	public Adapter createActionDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateDataConfig <em>State Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateDataConfig
	 * @generated
	 */
	public Adapter createStateDataConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateSeriesConfig <em>State Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateSeriesConfig
	 * @generated
	 */
	public Adapter createStateSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization <em>State Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization
	 * @generated
	 */
	public Adapter createStateVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent <em>Alert Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent
	 * @generated
	 */
	public Adapter createAlertComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration <em>Alert Indicator State Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration
	 * @generated
	 */
	public Adapter createAlertIndicatorStateEnumerationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap <em>Alert Indicator State Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap
	 * @generated
	 */
	public Adapter createAlertIndicatorStateMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig <em>Alert Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig
	 * @generated
	 */
	public Adapter createAlertSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertVisualization <em>Alert Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertVisualization
	 * @generated
	 */
	public Adapter createAlertVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization <em>Area Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization
	 * @generated
	 */
	public Adapter createAreaChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AssetPage <em>Asset Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AssetPage
	 * @generated
	 */
	public Adapter createAssetPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat <em>Background Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat
	 * @generated
	 */
	public Adapter createBackgroundFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization <em>Bar Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization
	 * @generated
	 */
	public Adapter createBarChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BluePrintComponent <em>Blue Print Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BluePrintComponent
	 * @generated
	 */
	public Adapter createBluePrintComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig <em>Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig
	 * @generated
	 */
	public Adapter createCategoryGuidelineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryDataConfig <em>Chart Category Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryDataConfig
	 * @generated
	 */
	public Adapter createChartCategoryDataConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig <em>Chart Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig
	 * @generated
	 */
	public Adapter createChartCategoryGuidelineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent <em>Chart Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent
	 * @generated
	 */
	public Adapter createChartComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet <em>Chart Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet
	 * @generated
	 */
	public Adapter createChartComponentColorSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig <em>Chart Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig
	 * @generated
	 */
	public Adapter createChartSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueDataConfig <em>Chart Value Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueDataConfig
	 * @generated
	 */
	public Adapter createChartValueDataConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig <em>Chart Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig
	 * @generated
	 */
	public Adapter createChartValueGuidelineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization <em>Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization
	 * @generated
	 */
	public Adapter createChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierComponent <em>Classifier Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierComponent
	 * @generated
	 */
	public Adapter createClassifierComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierSeriesConfig <em>Classifier Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierSeriesConfig
	 * @generated
	 */
	public Adapter createClassifierSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierVisualization <em>Classifier Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ClassifierVisualization
	 * @generated
	 */
	public Adapter createClassifierVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Component
	 * @generated
	 */
	public Adapter createComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet <em>Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet
	 * @generated
	 */
	public Adapter createComponentColorSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage <em>Dashboard Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage
	 * @generated
	 */
	public Adapter createDashboardPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig <em>Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig
	 * @generated
	 */
	public Adapter createDataConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor <em>Data Extractor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor
	 * @generated
	 */
	public Adapter createDataExtractorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat <em>Data Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat
	 * @generated
	 */
	public Adapter createDataFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule <em>Action Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule
	 * @generated
	 */
	public Adapter createActionRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataSource <em>Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataSource
	 * @generated
	 */
	public Adapter createDataSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam <em>Query Param</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam
	 * @generated
	 */
	public Adapter createQueryParamAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert <em>Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Alert
	 * @generated
	 */
	public Adapter createAlertAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert <em>Range Alert</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert
	 * @generated
	 */
	public Adapter createRangeAlertAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction <em>Alert Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction
	 * @generated
	 */
	public Adapter createAlertActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction <em>Visual Alert Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction
	 * @generated
	 */
	public Adapter createVisualAlertActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayout <em>Flow Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayout
	 * @generated
	 */
	public Adapter createFlowLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint <em>Flow Layout Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint
	 * @generated
	 */
	public Adapter createFlowLayoutConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat <em>Foreground Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat
	 * @generated
	 */
	public Adapter createForegroundFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle <em>Format Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle
	 * @generated
	 */
	public Adapter createFormatStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat <em>Indicator Field Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat
	 * @generated
	 */
	public Adapter createIndicatorFieldFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.View
	 * @generated
	 */
	public Adapter createViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute <em>View Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute
	 * @generated
	 */
	public Adapter createViewAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion <em>View Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion
	 * @generated
	 */
	public Adapter createViewVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement <em>BE Views Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement
	 * @generated
	 */
	public Adapter createBEViewsElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Footer <em>Footer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Footer
	 * @generated
	 */
	public Adapter createFooterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Header
	 * @generated
	 */
	public Adapter createHeaderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale <em>View Locale</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale
	 * @generated
	 */
	public Adapter createViewLocaleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Login
	 * @generated
	 */
	public Adapter createLoginAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin <em>Skin</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Skin
	 * @generated
	 */
	public Adapter createSkinAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Layout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Layout
	 * @generated
	 */
	public Adapter createLayoutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LayoutConstraint <em>Layout Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LayoutConstraint
	 * @generated
	 */
	public Adapter createLayoutConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat <em>Legend Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat
	 * @generated
	 */
	public Adapter createLegendFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization <em>Line Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization
	 * @generated
	 */
	public Adapter createLineChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig <em>One Dim Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig
	 * @generated
	 */
	public Adapter createOneDimSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization <em>One Dim Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization
	 * @generated
	 */
	public Adapter createOneDimVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Page
	 * @generated
	 */
	public Adapter createPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PageSelectorComponent <em>Page Selector Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PageSelectorComponent
	 * @generated
	 */
	public Adapter createPageSelectorComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PageVisualization <em>Page Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PageVisualization
	 * @generated
	 */
	public Adapter createPageVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Panel <em>Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Panel
	 * @generated
	 */
	public Adapter createPanelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Partition <em>Partition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Partition
	 * @generated
	 */
	public Adapter createPartitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization <em>Pie Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization
	 * @generated
	 */
	public Adapter createPieChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat <em>Plot Area Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat
	 * @generated
	 */
	public Adapter createPlotAreaFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent <em>State Machine Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent
	 * @generated
	 */
	public Adapter createStateMachineComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueOption <em>Value Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueOption
	 * @generated
	 */
	public Adapter createValueOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ConstantValueOption <em>Constant Value Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ConstantValueOption
	 * @generated
	 */
	public Adapter createConstantValueOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption <em>Field Reference Value Option</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption
	 * @generated
	 */
	public Adapter createFieldReferenceValueOptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat <em>Progress Bar Field Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat
	 * @generated
	 */
	public Adapter createProgressBarFieldFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryManagerComponent <em>Query Manager Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.QueryManagerComponent
	 * @generated
	 */
	public Adapter createQueryManagerComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig <em>Range Plot Chart Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig
	 * @generated
	 */
	public Adapter createRangePlotChartSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization <em>Range Plot Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization
	 * @generated
	 */
	public Adapter createRangePlotChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelatedAssetsComponent <em>Related Assets Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelatedAssetsComponent
	 * @generated
	 */
	public Adapter createRelatedAssetsComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization <em>Scatter Plot Chart Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization
	 * @generated
	 */
	public Adapter createScatterPlotChartVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SearchPage <em>Search Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SearchPage
	 * @generated
	 */
	public Adapter createSearchPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SearchViewComponent <em>Search View Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SearchViewComponent
	 * @generated
	 */
	public Adapter createSearchViewComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor <em>Series Color</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor
	 * @generated
	 */
	public Adapter createSeriesColorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig <em>Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig
	 * @generated
	 */
	public Adapter createSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator <em>Series Config Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator
	 * @generated
	 */
	public Adapter createSeriesConfigGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryDataConfig <em>Text Category Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryDataConfig
	 * @generated
	 */
	public Adapter createTextCategoryDataConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig <em>Text Category Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig
	 * @generated
	 */
	public Adapter createTextCategoryGuidelineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent <em>Text Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent
	 * @generated
	 */
	public Adapter createTextComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet <em>Text Component Color Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet
	 * @generated
	 */
	public Adapter createTextComponentColorSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextFieldFormat <em>Text Field Format</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextFieldFormat
	 * @generated
	 */
	public Adapter createTextFieldFormatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextSeriesConfig <em>Text Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextSeriesConfig
	 * @generated
	 */
	public Adapter createTextSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig <em>Text Value Data Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig
	 * @generated
	 */
	public Adapter createTextValueDataConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig <em>Text Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig
	 * @generated
	 */
	public Adapter createTextValueGuidelineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization <em>Text Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization
	 * @generated
	 */
	public Adapter createTextVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig <em>Two Dim Series Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig
	 * @generated
	 */
	public Adapter createTwoDimSeriesConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization <em>Two Dim Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization
	 * @generated
	 */
	public Adapter createTwoDimVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig <em>Value Guideline Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig
	 * @generated
	 */
	public Adapter createValueGuidelineConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle <em>Value Label Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle
	 * @generated
	 */
	public Adapter createValueLabelStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization <em>Visualization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.Visualization
	 * @generated
	 */
	public Adapter createVisualizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference <em>Role Preference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference
	 * @generated
	 */
	public Adapter createRolePreferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder <em>Component Gallery Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder
	 * @generated
	 */
	public Adapter createComponentGalleryFolderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink <em>Header Link</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink
	 * @generated
	 */
	public Adapter createHeaderLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelatedPage <em>Related Page</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelatedPage
	 * @generated
	 */
	public Adapter createRelatedPageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RelatedElementsComponent <em>Related Elements Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelatedElementsComponent
	 * @generated
	 */
	public Adapter createRelatedElementsComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //BEViewsConfigurationAdapterFactory
