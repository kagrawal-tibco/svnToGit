/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.io.IOException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BEViewsConfigurationPackageImpl extends EPackageImpl implements BEViewsConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected String packageFilename = "beviewsconfig.ecore";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateDataConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertIndicatorStateEnumerationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertIndicatorStateMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass areaChartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assetPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backgroundFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass barChartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bluePrintComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass categoryGuidelineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartCategoryDataConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartCategoryGuidelineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartComponentColorSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartValueDataConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartValueGuidelineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentColorSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dashboardPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataExtractorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queryParamEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangeAlertEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visualAlertActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowLayoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowLayoutConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass foregroundFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass formatStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indicatorFieldFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass beViewsElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass footerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass headerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewLocaleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loginEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass skinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass layoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass layoutConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass legendFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lineChartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oneDimSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oneDimVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pageSelectorComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pageVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass panelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass partitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pieChartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass plotAreaFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateMachineComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantValueOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldReferenceValueOptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass progressBarFieldFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queryManagerComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangePlotChartSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangePlotChartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relatedAssetsComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scatterPlotChartVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass searchPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass searchViewComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass seriesColorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass seriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass seriesConfigGeneratorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textCategoryDataConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textCategoryGuidelineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textComponentColorSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textFieldFormatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textValueDataConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textValueGuidelineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass twoDimSeriesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass twoDimVisualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueGuidelineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueLabelStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visualizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rolePreferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentGalleryFolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass headerLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relatedPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relatedElementsComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum actionEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum anchorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum anchorPositionEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataPlottingEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum displayModeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fieldAlignmentEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fontStyleEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum gradientDirectionEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum lineEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum orientationEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum panelStateEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum partitionStateEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum pieChartDirectionEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum placementEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum plotShapeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum rangePlotChartSeriesTypeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum relativeAxisPositionEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum scaleEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum scrollBarConfigEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum seriesAnchorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum sortEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum thresholdUnitEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType actionEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType anchorEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType anchorPositionEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dataPlottingEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType displayModeEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType fieldAlignmentEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType fontStyleEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType gradientDirectionEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType lineEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType orientationEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType panelStateEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType partitionStateEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pieChartDirectionEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType placementEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType plotShapeEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rangePlotChartSeriesTypeEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType relativeAxisPositionEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType scaleEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType scrollBarConfigEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType seriesAnchorEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sortEnumObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BEViewsConfigurationPackageImpl() {
		super(eNS_URI, BEViewsConfigurationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link BEViewsConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @generated
	 */
	public static BEViewsConfigurationPackage init() {
		if (isInited) return (BEViewsConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		BEViewsConfigurationPackageImpl theBEViewsConfigurationPackage = (BEViewsConfigurationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BEViewsConfigurationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BEViewsConfigurationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ModelPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Load packages
		theBEViewsConfigurationPackage.loadPackage();

		// Fix loaded packages
		theBEViewsConfigurationPackage.fixPackageContents();

		// Mark meta-data to indicate it can't be changed
		theBEViewsConfigurationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BEViewsConfigurationPackage.eNS_URI, theBEViewsConfigurationPackage);
		return theBEViewsConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionDefinition() {
		if (actionDefinitionEClass == null) {
			actionDefinitionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(0);
		}
		return actionDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionDefinition_Text() {
        return (EAttribute)getActionDefinition().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionDefinition_Type() {
        return (EAttribute)getActionDefinition().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionDefinition_CommandURL() {
        return (EAttribute)getActionDefinition().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateDataConfig() {
		if (stateDataConfigEClass == null) {
			stateDataConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(3);
		}
		return stateDataConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateSeriesConfig() {
		if (stateSeriesConfigEClass == null) {
			stateSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(4);
		}
		return stateSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateVisualization() {
		if (stateVisualizationEClass == null) {
			stateVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(5);
		}
		return stateVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateVisualization_StateRefID() {
        return (EAttribute)getStateVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertComponent() {
		if (alertComponentEClass == null) {
			alertComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(6);
		}
		return alertComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlertComponent_Threshold() {
        return (EAttribute)getAlertComponent().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertIndicatorStateEnumeration() {
		if (alertIndicatorStateEnumerationEClass == null) {
			alertIndicatorStateEnumerationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(7);
		}
		return alertIndicatorStateEnumerationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlertIndicatorStateEnumeration_FieldName() {
        return (EAttribute)getAlertIndicatorStateEnumeration().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlertIndicatorStateEnumeration_Mapping() {
        return (EReference)getAlertIndicatorStateEnumeration().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertIndicatorStateMap() {
		if (alertIndicatorStateMapEClass == null) {
			alertIndicatorStateMapEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(8);
		}
		return alertIndicatorStateMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlertIndicatorStateMap_FieldValue() {
        return (EAttribute)getAlertIndicatorStateMap().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlertIndicatorStateMap_IndicatorState() {
        return (EAttribute)getAlertIndicatorStateMap().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertSeriesConfig() {
		if (alertSeriesConfigEClass == null) {
			alertSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(9);
		}
		return alertSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlertSeriesConfig_IndicatorStateEnumeration() {
        return (EReference)getAlertSeriesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertVisualization() {
		if (alertVisualizationEClass == null) {
			alertVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(10);
		}
		return alertVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAreaChartVisualization() {
		if (areaChartVisualizationEClass == null) {
			areaChartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(15);
		}
		return areaChartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAreaChartVisualization_FillOpacity() {
        return (EAttribute)getAreaChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssetPage() {
		if (assetPageEClass == null) {
			assetPageEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(16);
		}
		return assetPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackgroundFormat() {
		if (backgroundFormatEClass == null) {
			backgroundFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(17);
		}
		return backgroundFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBackgroundFormat_GradientDirection() {
        return (EAttribute)getBackgroundFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBarChartVisualization() {
		if (barChartVisualizationEClass == null) {
			barChartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(18);
		}
		return barChartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBarChartVisualization_Width() {
        return (EAttribute)getBarChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBarChartVisualization_TopCapThickness() {
        return (EAttribute)getBarChartVisualization().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBarChartVisualization_OverlapPercentage() {
        return (EAttribute)getBarChartVisualization().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBarChartVisualization_Orientation() {
        return (EAttribute)getBarChartVisualization().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBluePrintComponent() {
		if (bluePrintComponentEClass == null) {
			bluePrintComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(19);
		}
		return bluePrintComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCategoryGuidelineConfig() {
		if (categoryGuidelineConfigEClass == null) {
			categoryGuidelineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(20);
		}
		return categoryGuidelineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategoryGuidelineConfig_HeaderName() {
        return (EAttribute)getCategoryGuidelineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCategoryGuidelineConfig_HeaderFormatStyle() {
        return (EReference)getCategoryGuidelineConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCategoryGuidelineConfig_LabelFormatStyle() {
        return (EReference)getCategoryGuidelineConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategoryGuidelineConfig_SortOrder() {
        return (EAttribute)getCategoryGuidelineConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategoryGuidelineConfig_DuplicatesAllowed() {
        return (EAttribute)getCategoryGuidelineConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartCategoryDataConfig() {
		if (chartCategoryDataConfigEClass == null) {
			chartCategoryDataConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(21);
		}
		return chartCategoryDataConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartCategoryGuidelineConfig() {
		if (chartCategoryGuidelineConfigEClass == null) {
			chartCategoryGuidelineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(22);
		}
		return chartCategoryGuidelineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartCategoryGuidelineConfig_RelativePosition() {
        return (EAttribute)getChartCategoryGuidelineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartCategoryGuidelineConfig_Placement() {
        return (EAttribute)getChartCategoryGuidelineConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartCategoryGuidelineConfig_Rotation() {
        return (EAttribute)getChartCategoryGuidelineConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartCategoryGuidelineConfig_SkipFactor() {
        return (EAttribute)getChartCategoryGuidelineConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartComponent() {
		if (chartComponentEClass == null) {
			chartComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(23);
		}
		return chartComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartComponent_PlotArea() {
        return (EReference)getChartComponent().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartComponent_Legend() {
        return (EReference)getChartComponent().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartComponent_CategoryGuidelineConfig() {
        return (EReference)getChartComponent().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartComponent_ValueGuidelineConfig() {
        return (EReference)getChartComponent().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartComponentColorSet() {
		if (chartComponentColorSetEClass == null) {
			chartComponentColorSetEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(24);
		}
		return chartComponentColorSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartComponentColorSet_SeriesColor() {
        return (EReference)getChartComponentColorSet().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_GuideLineLabelFontColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_GuideLineValueLabelFontColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_DataPointLabelFontColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_TopCapColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_PieEdgeColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_PieDropShadowColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartComponentColorSet_LineDropShadowColor() {
        return (EAttribute)getChartComponentColorSet().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartSeriesConfig() {
		if (chartSeriesConfigEClass == null) {
			chartSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(25);
		}
		return chartSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartSeriesConfig_Anchor() {
        return (EAttribute)getChartSeriesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartSeriesConfig_ValueLabelStyle() {
        return (EReference)getChartSeriesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartValueDataConfig() {
		if (chartValueDataConfigEClass == null) {
			chartValueDataConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(26);
		}
		return chartValueDataConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartValueGuidelineConfig() {
		if (chartValueGuidelineConfigEClass == null) {
			chartValueGuidelineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(27);
		}
		return chartValueGuidelineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartValueGuidelineConfig_LabelFormatStyle() {
        return (EReference)getChartValueGuidelineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartValueGuidelineConfig_HeaderName() {
        return (EAttribute)getChartValueGuidelineConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartValueGuidelineConfig_RelativePosition() {
        return (EAttribute)getChartValueGuidelineConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartValueGuidelineConfig_Scale() {
        return (EAttribute)getChartValueGuidelineConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChartValueGuidelineConfig_Division() {
        return (EAttribute)getChartValueGuidelineConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChartVisualization() {
		if (chartVisualizationEClass == null) {
			chartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(28);
		}
		return chartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChartVisualization_SharedCategoryDataConfig() {
        return (EReference)getChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifierComponent() {
		if (classifierComponentEClass == null) {
			classifierComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(29);
		}
		return classifierComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifierSeriesConfig() {
		if (classifierSeriesConfigEClass == null) {
			classifierSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(30);
		}
		return classifierSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifierVisualization() {
		if (classifierVisualizationEClass == null) {
			classifierVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(31);
		}
		return classifierVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponent() {
		if (componentEClass == null) {
			componentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(32);
		}
		return componentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_DisplayName() {
        return (EAttribute)getComponent().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_HelpText() {
        return (EAttribute)getComponent().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_ComponentColorSetIndex() {
        return (EAttribute)getComponent().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_SeriesColorIndex() {
        return (EAttribute)getComponent().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Background() {
        return (EReference)getComponent().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Visualization() {
        return (EReference)getComponent().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_LayoutConstraint() {
        return (EReference)getComponent().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_SeriesConfigGenerator() {
        return (EReference)getComponent().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_RelatedElement() {
        return (EReference)getComponent().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentColorSet() {
		if (componentColorSetEClass == null) {
			componentColorSetEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(33);
		}
		return componentColorSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentColorSet_DisplayName() {
        return (EAttribute)getComponentColorSet().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDashboardPage() {
		if (dashboardPageEClass == null) {
			dashboardPageEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(34);
		}
		return dashboardPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataConfig() {
		if (dataConfigEClass == null) {
			dataConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(35);
		}
		return dataConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataConfig_Extractor() {
        return (EReference)getDataConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataConfig_Formatter() {
        return (EReference)getDataConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataExtractor() {
		if (dataExtractorEClass == null) {
			dataExtractorEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(36);
		}
		return dataExtractorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataExtractor_SourceField() {
        return (EReference)getDataExtractor().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataFormat() {
		if (dataFormatEClass == null) {
			dataFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(37);
		}
		return dataFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataFormat_ToolTipFormat() {
        return (EAttribute)getDataFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataFormat_DisplayFormat() {
        return (EAttribute)getDataFormat().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataFormat_FormatStyle() {
        return (EReference)getDataFormat().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataFormat_ActionConfig() {
        return (EReference)getDataFormat().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataFormat_ShowLabel() {
        return (EAttribute)getDataFormat().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionRule() {
		if (actionRuleEClass == null) {
			actionRuleEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(40);
		}
		return actionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionRule_DataSource() {
        return (EReference)getActionRule().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionRule_Threshold() {
        return (EAttribute)getActionRule().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionRule_ThresholdUnit() {
        return (EAttribute)getActionRule().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionRule_Alert() {
        return (EReference)getActionRule().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionRule_DrillableFields() {
        return (EReference)getActionRule().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionRule_Params() {
        return (EReference)getActionRule().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataSource() {
		if (dataSourceEClass == null) {
			dataSourceEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(41);
		}
		return dataSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataSource_Query() {
        return (EAttribute)getDataSource().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataSource_SrcElement() {
        return (EReference)getDataSource().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataSource_Params() {
        return (EReference)getDataSource().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueryParam() {
		if (queryParamEClass == null) {
			queryParamEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(42);
		}
		return queryParamEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueryParam_Value() {
        return (EAttribute)getQueryParam().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueryParam_Type() {
        return (EAttribute)getQueryParam().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlert() {
		if (alertEClass == null) {
			alertEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(43);
		}
		return alertEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlert_Action() {
        return (EReference)getAlert().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlert_Enabled() {
        return (EAttribute)getAlert().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRangeAlert() {
		if (rangeAlertEClass == null) {
			rangeAlertEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(44);
		}
		return rangeAlertEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeAlert_LowerValue() {
        return (EAttribute)getRangeAlert().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeAlert_UpperValue() {
        return (EAttribute)getRangeAlert().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertAction() {
		if (alertActionEClass == null) {
			alertActionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(45);
		}
		return alertActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlertAction_Enabled() {
        return (EAttribute)getAlertAction().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVisualAlertAction() {
		if (visualAlertActionEClass == null) {
			visualAlertActionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(46);
		}
		return visualAlertActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualAlertAction_FillColor() {
        return (EAttribute)getVisualAlertAction().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualAlertAction_FontSize() {
        return (EAttribute)getVisualAlertAction().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualAlertAction_FontStyle() {
        return (EAttribute)getVisualAlertAction().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualAlertAction_FontColor() {
        return (EAttribute)getVisualAlertAction().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualAlertAction_DisplayFormat() {
        return (EAttribute)getVisualAlertAction().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualAlertAction_TooltipFormat() {
        return (EAttribute)getVisualAlertAction().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		if (documentRootEClass == null) {
			documentRootEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(49);
		}
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)getDocumentRoot().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AlertComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AssetPageElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BluePrintComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ChartComponentColorSetElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ChartComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ClassifierComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DashboardPageElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsConfigElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsConfigVersionElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsElementElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsFooterElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsHeaderElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsLoginElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BeViewsSkinElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_PageSelectorComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_PanelElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_StateMachineComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_QueryManagerComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RelatedAssetsComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SearchPageElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SearchViewComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_TextComponentColorSetElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_TextComponentElem() {
        return (EReference)getDocumentRoot().getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFlowLayout() {
		if (flowLayoutEClass == null) {
			flowLayoutEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(52);
		}
		return flowLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFlowLayoutConstraint() {
		if (flowLayoutConstraintEClass == null) {
			flowLayoutConstraintEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(53);
		}
		return flowLayoutConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowLayoutConstraint_ComponentRowSpan() {
        return (EAttribute)getFlowLayoutConstraint().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowLayoutConstraint_ComponentColSpan() {
        return (EAttribute)getFlowLayoutConstraint().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getForegroundFormat() {
		if (foregroundFormatEClass == null) {
			foregroundFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(56);
		}
		return foregroundFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getForegroundFormat_Line() {
        return (EAttribute)getForegroundFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFormatStyle() {
		if (formatStyleEClass == null) {
			formatStyleEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(57);
		}
		return formatStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFormatStyle_FontSize() {
        return (EAttribute)getFormatStyle().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFormatStyle_FontStyle() {
        return (EAttribute)getFormatStyle().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndicatorFieldFormat() {
		if (indicatorFieldFormatEClass == null) {
			indicatorFieldFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(60);
		}
		return indicatorFieldFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIndicatorFieldFormat_ShowTextValue() {
        return (EAttribute)getIndicatorFieldFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIndicatorFieldFormat_TextValueAnchor() {
        return (EAttribute)getIndicatorFieldFormat().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getView() {
		if (viewEClass == null) {
			viewEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(61);
		}
		return viewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getView_DisplayName() {
        return (EAttribute)getView().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getView_DefaultPage() {
        return (EReference)getView().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getView_AccessiblePage() {
        return (EReference)getView().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getView_Skin() {
        return (EReference)getView().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getView_Locale() {
        return (EReference)getView().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getView_Attribute() {
        return (EReference)getView().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getViewAttribute() {
		if (viewAttributeEClass == null) {
			viewAttributeEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(62);
		}
		return viewAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewAttribute_Value() {
        return (EAttribute)getViewAttribute().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getViewVersion() {
		if (viewVersionEClass == null) {
			viewVersionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(63);
		}
		return viewVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewVersion_MajorVersionNumber() {
        return (EAttribute)getViewVersion().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewVersion_MinorVersionNumber() {
        return (EAttribute)getViewVersion().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewVersion_PointVersionNumber() {
        return (EAttribute)getViewVersion().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewVersion_Description1() {
        return (EAttribute)getViewVersion().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBEViewsElement() {
		if (beViewsElementEClass == null) {
			beViewsElementEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(64);
		}
		return beViewsElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEViewsElement_OriginalElementIdentifier() {
        return (EAttribute)getBEViewsElement().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEViewsElement_OriginalElementVersion() {
        return (EAttribute)getBEViewsElement().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEViewsElement_Version() {
        return (EAttribute)getBEViewsElement().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFooter() {
		if (footerEClass == null) {
			footerEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(65);
		}
		return footerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFooter_Link() {
        return (EAttribute)getFooter().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHeader() {
		if (headerEClass == null) {
			headerEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(66);
		}
		return headerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeader_Title() {
        return (EAttribute)getHeader().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeader_BrandingImage() {
        return (EAttribute)getHeader().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeader_BrandingText() {
        return (EAttribute)getHeader().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeader_Link() {
        return (EAttribute)getHeader().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHeader_HeaderLink() {
        return (EReference)getHeader().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getViewLocale() {
		if (viewLocaleEClass == null) {
			viewLocaleEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(67);
		}
		return viewLocaleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewLocale_DisplayName() {
        return (EAttribute)getViewLocale().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewLocale_Locale() {
        return (EAttribute)getViewLocale().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewLocale_TimeZone() {
        return (EAttribute)getViewLocale().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getViewLocale_TimeFormat() {
        return (EAttribute)getViewLocale().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogin() {
		if (loginEClass == null) {
			loginEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(68);
		}
		return loginEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_Title() {
        return (EAttribute)getLogin().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogin_ImageURL() {
        return (EAttribute)getLogin().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSkin() {
		if (skinEClass == null) {
			skinEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(69);
		}
		return skinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_DisplayName() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSkin_DefaultComponentColorSet() {
        return (EReference)getSkin().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSkin_ComponentColorSet() {
        return (EReference)getSkin().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_FontColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_ComponentBackGroundColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_ComponentBackGroundGradientEndColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_ComponentForeGroundColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_VisualizationBackGroundColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_VisualizationBackGroundGradientEndColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSkin_VisualizationForeGroundColor() {
        return (EAttribute)getSkin().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLayout() {
		if (layoutEClass == null) {
			layoutEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(70);
		}
		return layoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLayout_RepositioningAllowed() {
        return (EAttribute)getLayout().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLayout_ComponentWidth() {
        return (EAttribute)getLayout().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLayout_ComponentHeight() {
        return (EAttribute)getLayout().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLayoutConstraint() {
		if (layoutConstraintEClass == null) {
			layoutConstraintEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(71);
		}
		return layoutConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLegendFormat() {
		if (legendFormatEClass == null) {
			legendFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(72);
		}
		return legendFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLegendFormat_Orientation() {
        return (EAttribute)getLegendFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLegendFormat_Anchor() {
        return (EAttribute)getLegendFormat().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLineChartVisualization() {
		if (lineChartVisualizationEClass == null) {
			lineChartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(73);
		}
		return lineChartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineChartVisualization_LineThickness() {
        return (EAttribute)getLineChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineChartVisualization_LineSmoothness() {
        return (EAttribute)getLineChartVisualization().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineChartVisualization_DataPlotting() {
        return (EAttribute)getLineChartVisualization().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineChartVisualization_PlotShape() {
        return (EAttribute)getLineChartVisualization().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineChartVisualization_PlotShapeDimension() {
        return (EAttribute)getLineChartVisualization().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOneDimSeriesConfig() {
		if (oneDimSeriesConfigEClass == null) {
			oneDimSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(76);
		}
		return oneDimSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOneDimSeriesConfig_ValueDataConfig() {
        return (EReference)getOneDimSeriesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOneDimVisualization() {
		if (oneDimVisualizationEClass == null) {
			oneDimVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(77);
		}
		return oneDimVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOneDimVisualization_ValueGuidelineConfig() {
        return (EReference)getOneDimVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPage() {
		if (pageEClass == null) {
			pageEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(80);
		}
		return pageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPage_DisplayName() {
        return (EAttribute)getPage().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPage_DisplayMode() {
        return (EAttribute)getPage().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPage_Partition() {
        return (EReference)getPage().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPage_Visualization() {
        return (EReference)getPage().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPageSelectorComponent() {
		if (pageSelectorComponentEClass == null) {
			pageSelectorComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(81);
		}
		return pageSelectorComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPageVisualization() {
		if (pageVisualizationEClass == null) {
			pageVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(82);
		}
		return pageVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPanel() {
		if (panelEClass == null) {
			panelEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(83);
		}
		return panelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPanel_DisplayName() {
        return (EAttribute)getPanel().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPanel_Span() {
        return (EAttribute)getPanel().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPanel_ScrollBar() {
        return (EAttribute)getPanel().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPanel_State() {
        return (EAttribute)getPanel().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPanel_Maximizable() {
        return (EAttribute)getPanel().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPanel_Minimizable() {
        return (EAttribute)getPanel().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPanel_Component() {
        return (EReference)getPanel().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPanel_Layout() {
        return (EReference)getPanel().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPartition() {
		if (partitionEClass == null) {
			partitionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(86);
		}
		return partitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPartition_DisplayName() {
        return (EAttribute)getPartition().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPartition_Vertical() {
        return (EAttribute)getPartition().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPartition_Span() {
        return (EAttribute)getPartition().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPartition_State() {
        return (EAttribute)getPartition().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPartition_Panel() {
        return (EReference)getPartition().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPieChartVisualization() {
		if (pieChartVisualizationEClass == null) {
			pieChartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(91);
		}
		return pieChartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPieChartVisualization_StartingAngle() {
        return (EAttribute)getPieChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPieChartVisualization_Direction() {
        return (EAttribute)getPieChartVisualization().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPieChartVisualization_Sector() {
        return (EAttribute)getPieChartVisualization().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlotAreaFormat() {
		if (plotAreaFormatEClass == null) {
			plotAreaFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(94);
		}
		return plotAreaFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlotAreaFormat_Background() {
        return (EReference)getPlotAreaFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlotAreaFormat_Foreground() {
        return (EReference)getPlotAreaFormat().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateMachineComponent() {
		if (stateMachineComponentEClass == null) {
			stateMachineComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(97);
		}
		return stateMachineComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachineComponent_StateMachine() {
        return (EReference)getStateMachineComponent().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateMachineComponent_StateVisualizationWidth() {
        return (EAttribute)getStateMachineComponent().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateMachineComponent_StateVisualizationHeight() {
        return (EAttribute)getStateMachineComponent().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueOption() {
		if (valueOptionEClass == null) {
			valueOptionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(98);
		}
		return valueOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstantValueOption() {
		if (constantValueOptionEClass == null) {
			constantValueOptionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(99);
		}
		return constantValueOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstantValueOption_Value() {
        return (EAttribute)getConstantValueOption().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFieldReferenceValueOption() {
		if (fieldReferenceValueOptionEClass == null) {
			fieldReferenceValueOptionEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(100);
		}
		return fieldReferenceValueOptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldReferenceValueOption_Field() {
        return (EReference)getFieldReferenceValueOption().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProgressBarFieldFormat() {
		if (progressBarFieldFormatEClass == null) {
			progressBarFieldFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(101);
		}
		return progressBarFieldFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgressBarFieldFormat_MaxValue() {
        return (EReference)getProgressBarFieldFormat().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProgressBarFieldFormat_MinValue() {
        return (EReference)getProgressBarFieldFormat().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueryManagerComponent() {
		if (queryManagerComponentEClass == null) {
			queryManagerComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(102);
		}
		return queryManagerComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRangePlotChartSeriesConfig() {
		if (rangePlotChartSeriesConfigEClass == null) {
			rangePlotChartSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(103);
		}
		return rangePlotChartSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangePlotChartSeriesConfig_Type() {
        return (EAttribute)getRangePlotChartSeriesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRangePlotChartVisualization() {
		if (rangePlotChartVisualizationEClass == null) {
			rangePlotChartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(106);
		}
		return rangePlotChartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangePlotChartVisualization_PlotShape() {
        return (EAttribute)getRangePlotChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangePlotChartVisualization_PlotShapeDimension() {
        return (EAttribute)getRangePlotChartVisualization().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangePlotChartVisualization_WhiskerThickness() {
        return (EAttribute)getRangePlotChartVisualization().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangePlotChartVisualization_WhiskerWidth() {
        return (EAttribute)getRangePlotChartVisualization().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangePlotChartVisualization_Orientation() {
        return (EAttribute)getRangePlotChartVisualization().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelatedAssetsComponent() {
		if (relatedAssetsComponentEClass == null) {
			relatedAssetsComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(107);
		}
		return relatedAssetsComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScatterPlotChartVisualization() {
		if (scatterPlotChartVisualizationEClass == null) {
			scatterPlotChartVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(112);
		}
		return scatterPlotChartVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScatterPlotChartVisualization_PlotShape() {
        return (EAttribute)getScatterPlotChartVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScatterPlotChartVisualization_PlotShapeDimension() {
        return (EAttribute)getScatterPlotChartVisualization().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSearchPage() {
		if (searchPageEClass == null) {
			searchPageEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(115);
		}
		return searchPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSearchViewComponent() {
		if (searchViewComponentEClass == null) {
			searchViewComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(116);
		}
		return searchViewComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSeriesColor() {
		if (seriesColorEClass == null) {
			seriesColorEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(119);
		}
		return seriesColorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSeriesColor_BaseColor() {
        return (EAttribute)getSeriesColor().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSeriesColor_HighlightColor() {
        return (EAttribute)getSeriesColor().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSeriesConfig() {
		if (seriesConfigEClass == null) {
			seriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(120);
		}
		return seriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSeriesConfig_DisplayName() {
        return (EAttribute)getSeriesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSeriesConfig_ToolTip() {
        return (EAttribute)getSeriesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSeriesConfig_ActionRule() {
        return (EReference)getSeriesConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSeriesConfig_QueryLink() {
        return (EReference)getSeriesConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSeriesConfig_RollOverConfig() {
        return (EReference)getSeriesConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSeriesConfig_RelatedElement() {
        return (EReference)getSeriesConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSeriesConfigGenerator() {
		if (seriesConfigGeneratorEClass == null) {
			seriesConfigGeneratorEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(121);
		}
		return seriesConfigGeneratorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSeriesConfigGenerator_Field() {
        return (EReference)getSeriesConfigGenerator().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSeriesConfigGenerator_Condition() {
        return (EAttribute)getSeriesConfigGenerator().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextCategoryDataConfig() {
		if (textCategoryDataConfigEClass == null) {
			textCategoryDataConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(124);
		}
		return textCategoryDataConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextCategoryGuidelineConfig() {
		if (textCategoryGuidelineConfigEClass == null) {
			textCategoryGuidelineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(125);
		}
		return textCategoryGuidelineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextCategoryGuidelineConfig_HeaderAlignment() {
        return (EAttribute)getTextCategoryGuidelineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextCategoryGuidelineConfig_LabelAlignment() {
        return (EAttribute)getTextCategoryGuidelineConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextCategoryGuidelineConfig_Width() {
        return (EAttribute)getTextCategoryGuidelineConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextComponent() {
		if (textComponentEClass == null) {
			textComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(126);
		}
		return textComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextComponentColorSet() {
		if (textComponentColorSetEClass == null) {
			textComponentColorSetEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(127);
		}
		return textComponentColorSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_HeaderColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_HeaderRollOverColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_HeaderTextFontColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_HeaderSeparatorColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_CellColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_CellTextFontColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_RowSeparatorColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextComponentColorSet_RowRollOverColor() {
        return (EAttribute)getTextComponentColorSet().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextFieldFormat() {
		if (textFieldFormatEClass == null) {
			textFieldFormatEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(128);
		}
		return textFieldFormatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextSeriesConfig() {
		if (textSeriesConfigEClass == null) {
			textSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(129);
		}
		return textSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextValueDataConfig() {
		if (textValueDataConfigEClass == null) {
			textValueDataConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(130);
		}
		return textValueDataConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextValueDataConfig_HeaderName() {
        return (EAttribute)getTextValueDataConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTextValueDataConfig_HeaderFormatStyle() {
        return (EReference)getTextValueDataConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextValueDataConfig_Width() {
        return (EAttribute)getTextValueDataConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextValueDataConfig_Alignment() {
        return (EAttribute)getTextValueDataConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextValueGuidelineConfig() {
		if (textValueGuidelineConfigEClass == null) {
			textValueGuidelineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(131);
		}
		return textValueGuidelineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextValueGuidelineConfig_HeaderAlignment() {
        return (EAttribute)getTextValueGuidelineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextVisualization() {
		if (textVisualizationEClass == null) {
			textVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(132);
		}
		return textVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextVisualization_ShowHeader() {
        return (EAttribute)getTextVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTwoDimSeriesConfig() {
		if (twoDimSeriesConfigEClass == null) {
			twoDimSeriesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(133);
		}
		return twoDimSeriesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTwoDimSeriesConfig_CategoryDataConfig() {
        return (EReference)getTwoDimSeriesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTwoDimVisualization() {
		if (twoDimVisualizationEClass == null) {
			twoDimVisualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(134);
		}
		return twoDimVisualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTwoDimVisualization_CategoryGuidelineConfig() {
        return (EReference)getTwoDimVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueGuidelineConfig() {
		if (valueGuidelineConfigEClass == null) {
			valueGuidelineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(135);
		}
		return valueGuidelineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getValueGuidelineConfig_HeaderFormatStyle() {
        return (EReference)getValueGuidelineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueLabelStyle() {
		if (valueLabelStyleEClass == null) {
			valueLabelStyleEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(136);
		}
		return valueLabelStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValueLabelStyle_FontSize() {
        return (EAttribute)getValueLabelStyle().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValueLabelStyle_FontStyle() {
        return (EAttribute)getValueLabelStyle().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVisualization() {
		if (visualizationEClass == null) {
			visualizationEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(137);
		}
		return visualizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualization_DisplayName() {
        return (EAttribute)getVisualization().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVisualization_Background() {
        return (EReference)getVisualization().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualization_SeriesColorIndex() {
        return (EAttribute)getVisualization().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVisualization_Action() {
        return (EReference)getVisualization().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVisualization_SeriesConfig() {
        return (EReference)getVisualization().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVisualization_RelatedElement() {
        return (EReference)getVisualization().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRolePreference() {
		if (rolePreferenceEClass == null) {
			rolePreferenceEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(139);
		}
		return rolePreferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRolePreference_Role() {
        return (EAttribute)getRolePreference().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRolePreference_Gallery() {
        return (EReference)getRolePreference().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRolePreference_View() {
        return (EReference)getRolePreference().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentGalleryFolder() {
		if (componentGalleryFolderEClass == null) {
			componentGalleryFolderEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(140);
		}
		return componentGalleryFolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentGalleryFolder_SubFolder() {
        return (EReference)getComponentGalleryFolder().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentGalleryFolder_Component() {
        return (EReference)getComponentGalleryFolder().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHeaderLink() {
		if (headerLinkEClass == null) {
			headerLinkEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(141);
		}
		return headerLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeaderLink_UrlName() {
        return (EAttribute)getHeaderLink().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeaderLink_UrlLink() {
        return (EAttribute)getHeaderLink().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelatedPage() {
		if (relatedPageEClass == null) {
			relatedPageEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(142);
		}
		return relatedPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelatedElementsComponent() {
		if (relatedElementsComponentEClass == null) {
			relatedElementsComponentEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(143);
		}
		return relatedElementsComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getActionEnum() {
		if (actionEnumEEnum == null) {
			actionEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(1);
		}
		return actionEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAnchorEnum() {
		if (anchorEnumEEnum == null) {
			anchorEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(11);
		}
		return anchorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAnchorPositionEnum() {
		if (anchorPositionEnumEEnum == null) {
			anchorPositionEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(13);
		}
		return anchorPositionEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDataPlottingEnum() {
		if (dataPlottingEnumEEnum == null) {
			dataPlottingEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(38);
		}
		return dataPlottingEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDisplayModeEnum() {
		if (displayModeEnumEEnum == null) {
			displayModeEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(47);
		}
		return displayModeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFieldAlignmentEnum() {
		if (fieldAlignmentEnumEEnum == null) {
			fieldAlignmentEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(50);
		}
		return fieldAlignmentEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFontStyleEnum() {
		if (fontStyleEnumEEnum == null) {
			fontStyleEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(54);
		}
		return fontStyleEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getGradientDirectionEnum() {
		if (gradientDirectionEnumEEnum == null) {
			gradientDirectionEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(58);
		}
		return gradientDirectionEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLineEnum() {
		if (lineEnumEEnum == null) {
			lineEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(74);
		}
		return lineEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOrientationEnum() {
		if (orientationEnumEEnum == null) {
			orientationEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(78);
		}
		return orientationEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPanelStateEnum() {
		if (panelStateEnumEEnum == null) {
			panelStateEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(84);
		}
		return panelStateEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPartitionStateEnum() {
		if (partitionStateEnumEEnum == null) {
			partitionStateEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(87);
		}
		return partitionStateEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPieChartDirectionEnum() {
		if (pieChartDirectionEnumEEnum == null) {
			pieChartDirectionEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(89);
		}
		return pieChartDirectionEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPlacementEnum() {
		if (placementEnumEEnum == null) {
			placementEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(92);
		}
		return placementEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPlotShapeEnum() {
		if (plotShapeEnumEEnum == null) {
			plotShapeEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(95);
		}
		return plotShapeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRangePlotChartSeriesTypeEnum() {
		if (rangePlotChartSeriesTypeEnumEEnum == null) {
			rangePlotChartSeriesTypeEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(104);
		}
		return rangePlotChartSeriesTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRelativeAxisPositionEnum() {
		if (relativeAxisPositionEnumEEnum == null) {
			relativeAxisPositionEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(108);
		}
		return relativeAxisPositionEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getScaleEnum() {
		if (scaleEnumEEnum == null) {
			scaleEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(110);
		}
		return scaleEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getScrollBarConfigEnum() {
		if (scrollBarConfigEnumEEnum == null) {
			scrollBarConfigEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(113);
		}
		return scrollBarConfigEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSeriesAnchorEnum() {
		if (seriesAnchorEnumEEnum == null) {
			seriesAnchorEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(117);
		}
		return seriesAnchorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSortEnum() {
		if (sortEnumEEnum == null) {
			sortEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(122);
		}
		return sortEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getThresholdUnitEnum() {
		if (thresholdUnitEnumEEnum == null) {
			thresholdUnitEnumEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(138);
		}
		return thresholdUnitEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getActionEnumObject() {
		if (actionEnumObjectEDataType == null) {
			actionEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(2);
		}
		return actionEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAnchorEnumObject() {
		if (anchorEnumObjectEDataType == null) {
			anchorEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(12);
		}
		return anchorEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAnchorPositionEnumObject() {
		if (anchorPositionEnumObjectEDataType == null) {
			anchorPositionEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(14);
		}
		return anchorPositionEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDataPlottingEnumObject() {
		if (dataPlottingEnumObjectEDataType == null) {
			dataPlottingEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(39);
		}
		return dataPlottingEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDisplayModeEnumObject() {
		if (displayModeEnumObjectEDataType == null) {
			displayModeEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(48);
		}
		return displayModeEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFieldAlignmentEnumObject() {
		if (fieldAlignmentEnumObjectEDataType == null) {
			fieldAlignmentEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(51);
		}
		return fieldAlignmentEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFontStyleEnumObject() {
		if (fontStyleEnumObjectEDataType == null) {
			fontStyleEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(55);
		}
		return fontStyleEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getGradientDirectionEnumObject() {
		if (gradientDirectionEnumObjectEDataType == null) {
			gradientDirectionEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(59);
		}
		return gradientDirectionEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getLineEnumObject() {
		if (lineEnumObjectEDataType == null) {
			lineEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(75);
		}
		return lineEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getOrientationEnumObject() {
		if (orientationEnumObjectEDataType == null) {
			orientationEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(79);
		}
		return orientationEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPanelStateEnumObject() {
		if (panelStateEnumObjectEDataType == null) {
			panelStateEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(85);
		}
		return panelStateEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPartitionStateEnumObject() {
		if (partitionStateEnumObjectEDataType == null) {
			partitionStateEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(88);
		}
		return partitionStateEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPieChartDirectionEnumObject() {
		if (pieChartDirectionEnumObjectEDataType == null) {
			pieChartDirectionEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(90);
		}
		return pieChartDirectionEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPlacementEnumObject() {
		if (placementEnumObjectEDataType == null) {
			placementEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(93);
		}
		return placementEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPlotShapeEnumObject() {
		if (plotShapeEnumObjectEDataType == null) {
			plotShapeEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(96);
		}
		return plotShapeEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRangePlotChartSeriesTypeEnumObject() {
		if (rangePlotChartSeriesTypeEnumObjectEDataType == null) {
			rangePlotChartSeriesTypeEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(105);
		}
		return rangePlotChartSeriesTypeEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRelativeAxisPositionEnumObject() {
		if (relativeAxisPositionEnumObjectEDataType == null) {
			relativeAxisPositionEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(109);
		}
		return relativeAxisPositionEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getScaleEnumObject() {
		if (scaleEnumObjectEDataType == null) {
			scaleEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(111);
		}
		return scaleEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getScrollBarConfigEnumObject() {
		if (scrollBarConfigEnumObjectEDataType == null) {
			scrollBarConfigEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(114);
		}
		return scrollBarConfigEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSeriesAnchorEnumObject() {
		if (seriesAnchorEnumObjectEDataType == null) {
			seriesAnchorEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(118);
		}
		return seriesAnchorEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSortEnumObject() {
		if (sortEnumObjectEDataType == null) {
			sortEnumObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(BEViewsConfigurationPackage.eNS_URI).getEClassifiers().get(123);
		}
		return sortEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BEViewsConfigurationFactory getBEViewsConfigurationFactory() {
		return (BEViewsConfigurationFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isLoaded = false;

	/**
	 * Laods the package and any sub-packages from their serialized form.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void loadPackage() {
		if (isLoaded) return;
		isLoaded = true;

		URL url = getClass().getResource(packageFilename);
		if (url == null) {
			throw new RuntimeException("Missing serialized package: " + packageFilename);
		}
		URI uri = URI.createURI(url.toString());
		Resource resource = new EcoreResourceFactoryImpl().createResource(uri);
		try {
			resource.load(null);
		}
		catch (IOException exception) {
			throw new WrappedException(exception);
		}
		initializeFromLoadedEPackage(this, (EPackage)resource.getContents().get(0));
		createResource(eNS_URI);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isFixed = false;

	/**
	 * Fixes up the loaded package, to make it appear as if it had been programmatically built.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fixPackageContents() {
		if (isFixed) return;
		isFixed = true;
		fixEClassifiers();
	}

	/**
	 * Sets the instance class on the given classifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void fixInstanceClass(EClassifier eClassifier) {
		if (eClassifier.getInstanceClassName() == null) {
			eClassifier.setInstanceClassName("com.tibco.cep.designtime.core.model.beviewsconfig." + eClassifier.getName());
			setGeneratedClassName(eClassifier);
		}
	}

} //BEViewsConfigurationPackageImpl
