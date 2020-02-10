package com.tibco.cep.dashboard.integration.embedded;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementConfigurator;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementService;
import com.tibco.cep.dashboard.plugin.beviews.runtime.CategoryValuesConsolidatorCache;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.MALSkinIndexer;
import com.tibco.cep.dashboard.psvr.mal.managers.MALChartComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSkinManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALTextComponentManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataExtractor;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSkin;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.store.GlobalIdentity;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationEngine;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.dashboard.preview.IEmbeddedPresentationServer;
import com.tibco.cep.studio.dashboard.preview.SeriesDataSet;

public class PresentationServer implements IEmbeddedPresentationServer {

	private static ManagementService managementService;

	private static Logger logger;

	private static VisualizationEngine vEngine;

	static {
		try {
			// create a dashboard session
			DashboardSession dashboardSession = new EDashboardSession();
			// configure the management service in embed mode
			ManagementConfigurator configurator = new ManagementConfigurator();
			configurator.setDashboardSession(dashboardSession);
			configurator.setMode(MODE.EMBED);
			// create the management service
			configurator.createManagementService();
			// initialize the service which will initialize psvr in embed mode
			configurator.init();
			managementService = configurator.getManagementService();
			// start the system
			managementService.start();
			// get runtime logger
			logger = LoggingService.getRuntimeLogger();
			// get an instance of engine
			vEngine = VisualizationEngine.getInstance();
			// PATCH think about cleaner way to initialize category value consolidator cache
			CategoryValuesConsolidatorCache.getInstance().init(logger);
		} catch (ManagementException e) {
			throw new RuntimeException("could not start presentation server",e);
		} catch (NamingException e) {
			throw new RuntimeException("could not start presentation server",e);
		}
	}

	private SecurityToken token;

	private PersistentStore persistentStore;

	private MALElementsCollector elementsCollector;

	private Skin skin;

	private MALSkinIndexer skinIndexer;

	public PresentationServer() {
		// create a fake token
		token = new ESecurityToken("embeddedpsvruser", "admin");
		// create a fake persistent store
		persistentStore = new EPersistentStore(null, GlobalIdentity.getInstance());
	}

	@Override
	public void setSkin(Skin skin) throws Exception {
		this.skin = skin;
		//create a new instance of the MALElementsCollector
		elementsCollector = new MALElementsCollector();
		//parse and load the sking (including into the MALElementsCollector)
		MALSkin malSkin = (MALSkin) new MALSkinManager(logger).load(persistentStore, skin, elementsCollector);
		//index the skin
		skinIndexer = new MALSkinIndexer(logger, malSkin);
	}

	@Override
	public void start() throws Exception {
		//do nothing
	}

	@Override
	public String getComponentConfigWithData(Component component, SeriesDataSet[] data) throws FatalException {
		if (skin == null || skinIndexer == null) {
			throw new IllegalStateException("No skin specified");
		}
		try {
			MALComponent malComponent = (MALComponent) parseComponent(component);
			EViewsConfigHelper viewsConfigHelper = new EViewsConfigHelper(token, skinIndexer, malComponent);
			ETokenRoleProfile tokenRoleProfile = new ETokenRoleProfile(viewsConfigHelper, token);
			EPresentationContext pCtx = new EPresentationContext(token, tokenRoleProfile, data);
			prepDataConfigs(malComponent, pCtx);
			VisualizationModel vModelWithConfig = vEngine.getComponentVisualizationModelWithConfig(malComponent, null, pCtx);
			VisualizationData vDataInVizModel = vModelWithConfig.getVisualizationData();
			VisualizationData vData = vEngine.getComponentVisualizationData(malComponent, null, pCtx);
			if (vDataInVizModel != null) {
				DataRow[] actualData = vData.getDataRow();
				for (int i = 0; i < actualData.length; i++) {
					vDataInVizModel.addDataRow(actualData[i]);
				}
			} else {
				vModelWithConfig.setVisualizationData(vData);
			}
			return OGLMarshaller.getInstance().marshall(null, vModelWithConfig);
		} catch (Exception e) {
			throw new FatalException("could not generate config and data xml for "+component.getFullPath(), e);
		} finally {
			elementsCollector.remove(component.getGUID());
		}
		// return "<chartmodelcomponentid=\"9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459\"componentname=\"Chart\"componenttype=\"ChartComponent\"><description/><actionconfigdisabled=\"false\"><text>ROOT</text><actionconfigcommand=\"showdialog\"disabled=\"false\"defaultitem=\"false\"><text>Enlarge</text><configparamname=\"dialogname\">overlay</configparam><dynamicparamname=\"componentid\">currentcomponent.id</dynamicparam></actionconfig><actionconfigdisabled=\"true\"><text>QuickEdit</text></actionconfig><actionconfigdisabled=\"true\"><text>RelatedCharts</text></actionconfig><actionconfigcommand=\"delete\"disabled=\"false\"defaultitem=\"false\"><text>Remove</text><configparamname=\"command\">removecomponent</configparam><dynamicparamname=\"pageid\">currentpage.id</dynamicparam><dynamicparamname=\"panelid\">currentpanel.id</dynamicparam><dynamicparamname=\"componentid\">currentcomponent.id</dynamicparam></actionconfig><actionconfigcommand=\"showdialog\"disabled=\"true\"defaultitem=\"false\"><text>AboutthisChart...</text><configparamname=\"dialogname\">helpdialog</configparam><dynamicparamname=\"title\">currentcomponent.title</dynamicparam><dynamicparamname=\"help\">currentcomponentmodel.help</dynamicparam></actionconfig></actionconfig><visualizationdatacomponentid=\"9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459\"><datarowid=\"1\"templatetype=\"header\"visualtype=\"categoryaxis\"templateid=\"categoryaxisvalue\"><datacolumnid=\"1\"><displayvalue>1</displayvalue><value>1</value></datacolumn><datacolumnid=\"2\"><displayvalue>2</displayvalue><value>2</value></datacolumn><datacolumnid=\"3\"><displayvalue>3</displayvalue><value>3</value></datacolumn><datacolumnid=\"4\"><displayvalue>4</displayvalue><value>4</value></datacolumn></datarow><datarowid=\"1\"templatetype=\"data\"visualtype=\"horizontalbar\"templateid=\"7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040\"><datacolumnid=\"1\"templateid=\"7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040\"><displayvalue>90</displayvalue><value>90</value><tooltip>1=90</tooltip><link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=9&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link><typespecificattributename=\"hrefprms\">tupid=9</typespecificattribute></datacolumn><datacolumnid=\"2\"templateid=\"7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040\"><displayvalue>100</displayvalue><value>100</value><tooltip>2=100</tooltip><link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=12&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link><typespecificattributename=\"hrefprms\">tupid=12</typespecificattribute></datacolumn><datacolumnid=\"3\"templateid=\"7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040\"><displayvalue>80</displayvalue><value>80</value><tooltip>3=80</tooltip><link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=17&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link><typespecificattributename=\"hrefprms\">tupid=17</typespecificattribute></datacolumn><datacolumnid=\"4\"templateid=\"7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040\"><displayvalue>30</displayvalue><value>30</value><basecolor>ff0000</basecolor><highlightcolor>FFFFFF</highlightcolor><tooltip>4=30</tooltip><link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=4&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link><typespecificattributename=\"hrefprms\">tupid=4</typespecificattribute><fontcolor=\"FFFFFF\"style=\"normal\"/></datacolumn></datarow></visualizationdata><chartconfigbackgroundcolor=\"C3CBD7\"componentid=\"9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459\"><legendconfigorientation=\"horizontal\"anchor=\"north\"/><plotareaconfigbackgroundcolor=\"E8F0F0\"gradientdirection=\"toptobottom\"><plotpatternconfigcolor=\"A9BACA\"lines=\"both\"/></plotareaconfig><categoryaxisconfigname=\"Category\"datatype=\"string\"fontsize=\"12\"fontcolor=\"000000\"fontstyle=\"italic\"relativeposition=\"below\"showticklabels=\"false\"><ticklabelconfigfontsize=\"12\"fontcolor=\"000000\"fontstyle=\"italic\"placement=\"automatic\"rotation=\"0\"skipfactor=\"0\"/></categoryaxisconfig><valueaxisconfigname=\"Value\"fontsize=\"12\"fontcolor=\"000000\"fontstyle=\"italic\"relativeposition=\"left\"showticklabels=\"false\"scale=\"normal\"noofvisualdivisions=\"7\"><ticklabelconfigfontsize=\"12\"fontcolor=\"000000\"fontstyle=\"italic\"placement=\"continous\"/></valueaxisconfig><charttypeconfigtype=\"horizontalbar\"><seriesconfigname=\"7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040\"displayname=\"Series_\"anchor=\"Q1\"basecolor=\"C86E4D\"highlightcolor=\"FFFFFF\"><valuelabelconfigfontsize=\"10\"fontcolor=\"000000\"fontstyle=\"normal\"/><actionconfigdisabled=\"false\"><text>ROOT</text><actionconfigcommand=\"launchinternallink\"disabled=\"false\"defaultitem=\"false\"><text>DrillDown</text><dynamicparamname=\"link\">currentdatacolumn.link</dynamicparam></actionconfig><actionconfigcommand=\"launchexternallink\"disabled=\"true\"defaultitem=\"false\"><text>Links</text></actionconfig></actionconfig></seriesconfig><typespecificattributename=\"width\">10</typespecificattribute><typespecificattributename=\"topcapthickness\">0.5</typespecificattribute><typespecificattributename=\"topcap\">true</typespecificattribute><typespecificattributename=\"topcapcolor\">000000</typespecificattribute><typespecificattributename=\"overlappercentage\">0</typespecificattribute><typespecificattributename=\"showvalues\">true</typespecificattribute></charttypeconfig></chartconfig></chartmodel>";

	}

	private MALElement parseComponent(Component component) throws Exception {
		MALElementManager compManager = null;
		if (component instanceof ChartComponent) {
			compManager = new MALChartComponentManager(logger);
		} else if (component instanceof TextComponent) {
			compManager = new MALTextComponentManager(logger);
		} else {
			throw new IllegalArgumentException("Unknown supported component type " + component.getClass().getSimpleName());
		}
		return compManager.load(persistentStore, component, elementsCollector);
	}

	private void prepDataConfigs(MALComponent malComponent, PresentationContext pCtx) throws FatalException, MALException, ElementNotFoundException, VisualizationException {
		EMALTransaction transaction = null;
		try {
			transaction = new EMALTransaction();
			transaction.acquireThread();

			MALVisualization[] visualizations = malComponent.getVisualization();
			for (int i = 0; i < visualizations.length; i++) {
				MALVisualization visualization = visualizations[i];
				MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
				for (int j = 0; j < seriesConfigs.length; j++) {
					MALTwoDimSeriesConfig seriesConfig = (MALTwoDimSeriesConfig) seriesConfigs[j];
					EDataSourceHandler dataSourceHandler = (EDataSourceHandler) pCtx.getDataSourceHandler(seriesConfig);
					MALDataConfig categoryDataConfig = seriesConfig.getCategoryDataConfig();
					if (categoryDataConfig != null) {
						MALDataExtractor categoryFldExtractor = categoryDataConfig.getExtractor();
						if (categoryFldExtractor != null) {
							categoryFldExtractor.setSourceField(dataSourceHandler.getCategoryField());
						} else {
							categoryFldExtractor = createDataExtractor(seriesConfig, categoryDataConfig, dataSourceHandler.getCategoryField());
							categoryDataConfig.setExtractor(categoryFldExtractor);
						}
					} else {
						categoryDataConfig = createTempCategoryDataConfig(seriesConfig, dataSourceHandler);
						seriesConfig.setCategoryDataConfig(categoryDataConfig);
					}
					if (categoryDataConfig.getFormatter() != null) {
						categoryDataConfig.getFormatter().setDisplayFormat("{" + dataSourceHandler.getCategoryField().getName() + "}");
					}
					MALDataConfig[] valueDataConfigs = seriesConfig.getValueDataConfig();
					for (int k = 0; k < valueDataConfigs.length; k++) {
						MALDataConfig valueDataConfig = valueDataConfigs[k];
						if (valueDataConfig != null) {
							MALDataExtractor valueFldExtractor = valueDataConfig.getExtractor();
							if (valueFldExtractor != null) {
								valueFldExtractor.setSourceField(dataSourceHandler.getValueField());
							} else {
								MALDataExtractor dataExtractor = createDataExtractor(seriesConfig, valueDataConfig, dataSourceHandler.getValueField());
								valueDataConfig.setExtractor(dataExtractor);
							}
						} else {
							valueDataConfig = createTempValueDataConfig(seriesConfig, dataSourceHandler);
							seriesConfig.addValueDataConfig(k, valueDataConfig);
						}
						if (valueDataConfig.getFormatter() != null) {
							String formatStr = "{" + dataSourceHandler.getValueField().getName() + "}";
							valueDataConfig.getFormatter().setDisplayFormat(formatStr);
							valueDataConfig.getFormatter().setToolTipFormat(formatStr);
						}
					}
				}
			}
		} finally {
			try {
				transaction.relinquishThread();
			} catch (Exception ex) {
				System.err.println("ERROR: EPresentationServer.prepDataConfigs - Failed to relinquish EMALTransaction.\n" + ex.toString());
			}
		}
	}

	private MALDataConfig createTempCategoryDataConfig(MALTwoDimSeriesConfig seriesConfig, EDataSourceHandler dataSourceHandler) throws FatalException {
		throw new UnsupportedOperationException("EPresentationServer.createTempCategoryDataConfig");
	}

	private MALDataExtractor createDataExtractor(MALTwoDimSeriesConfig seriesConfig, MALDataConfig dataConfig, MALFieldMetaInfo fieldInfo) throws FatalException {
		throw new UnsupportedOperationException("EPresentationServer.createDataExtractor");
	}

	private MALDataConfig createTempValueDataConfig(MALTwoDimSeriesConfig seriesConfig, EDataSourceHandler dataSourceHandler) throws FatalException {
		throw new UnsupportedOperationException("EPresentationServer.createTempValueDataConfig");
	}

	@Override
	public void shutdown() {
		this.elementsCollector = null;
		this.persistentStore = null;
		this.skin = null;
		this.skinIndexer = null;
		this.token = null;
	}

}