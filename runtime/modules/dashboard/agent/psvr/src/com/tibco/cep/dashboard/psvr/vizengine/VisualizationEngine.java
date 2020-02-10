package com.tibco.cep.dashboard.psvr.vizengine;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALClassifierComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALClassifierVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.RowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.util.PerformanceMeasurement;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
public class VisualizationEngine extends ServiceDependent {

	private static VisualizationEngine instance = null;

	public static final synchronized VisualizationEngine getInstance() {
		if (instance == null) {
			instance = new VisualizationEngine();
		}
		return instance;
	}

	// stats
	private PerformanceMeasurement modelRequestTime;
	private PerformanceMeasurement dataRequestTime;
	private PerformanceMeasurement requestTime;

	private VisualizationEngine() {
		super("visualizationengine","Visualization Engine");
		modelRequestTime = new PerformanceMeasurement("Model Request Time");
		dataRequestTime = new PerformanceMeasurement("Data Request Time");
		requestTime = new PerformanceMeasurement("Request Time");
	}

	void init(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, Properties properties) {
		this.logger = logger;
		this.messageGenerator = messageGenerator;
	}

	public VisualizationModel getComponentVisualizationModelWithConfig(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, PluginException, DataException {
		long stime = System.currentTimeMillis();
		try {
			ComponentHandler componentHandler = ComponentHandlerFactory.getInstance().getHandler(component);
			VisualizationModel visualizationModel = componentHandler.getVisualizationModel(component, seriesConfigNames, pCtx);
			visualizationModel.setComponentID(component.getId());
			visualizationModel.setComponentName(component.getName());
			visualizationModel.setComponentType(component.getDefinitionType());
			VisualizationData visualizationHeaderDataModel = componentHandler.getVisualizationHeaderData(component, seriesConfigNames, pCtx);
			if (visualizationHeaderDataModel != null) {
				visualizationModel.setVisualizationData(visualizationHeaderDataModel);
			}
			return visualizationModel;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			modelRequestTime.add(System.currentTimeMillis(), timeTaken);
			requestTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	public VisualizationData getComponentVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, PluginException, DataException {
		long stime = System.currentTimeMillis();
		try {
			ComponentHandler componentHandler = ComponentHandlerFactory.getInstance().getHandler(component);
			VisualizationData visualizationDataModel = componentHandler.getVisualizationHeaderData(component, seriesConfigNames, pCtx);
			return visualizationDataModel;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			dataRequestTime.add(System.currentTimeMillis(), timeTaken);
			requestTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	public VisualizationData getComponentVisualizationData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {
		long stime = System.currentTimeMillis();
		try {
			ComponentHandler componentHandler = ComponentHandlerFactory.getInstance().getHandler(component);
			SeriesDataHolder seriesDataHolder = new SeriesDataHolder(component, seriesConfigNames, pCtx);
			VisualizationData visualizationDataModel = componentHandler.getVisualizationData(component, seriesDataHolder, pCtx);
			return visualizationDataModel;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			dataRequestTime.add(System.currentTimeMillis(), timeTaken);
			requestTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	public VisualizationModel getClassifierVisualizationModelWithConfig(MALClassifierComponent classifierComponent, List<Tuple> classifierData, PresentationContext pCtx) throws VisualizationException, IOException,
			MALException, ElementNotFoundException, PluginException, DataException {
		long stime = System.currentTimeMillis();
		try {
			MALClassifierVisualization classifierVisualization = (MALClassifierVisualization) classifierComponent.getVisualization(0);
			// for classifier visualization we always work with a TextModel
			TextModel visualizationModel = new TextModel();
			visualizationModel.setComponentID(classifierComponent.getId());
			visualizationModel.setComponentName(classifierComponent.getName());

			// for classifier visualization we always work with a TextConfig
			TextConfig textConfig = new TextConfig();
			visualizationModel.setTextConfig(textConfig);
			// get classifier configuration
			VisualizationHandler classifierVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(classifierVisualization);
			VisualizationSeriesContainer[] visualizationSeriesContainer = classifierVisualizationHandler.getVisualizationSeriesContainers(classifierComponent, classifierVisualization, pCtx);
			textConfig.setRowConfig((RowConfig[]) visualizationSeriesContainer);
			// get classifier header informaton
			MALSeriesConfig[] classifierSeriesConfigs = classifierVisualization.getSeriesConfig();
			if (classifierSeriesConfigs.length != 1) {
				String exMsg = messageGenerator.getMessage("invalid.classifiercomp.seriescfgcount", pCtx.getMessageGeneratorArgs(null, classifierComponent.toString(), new Integer(classifierSeriesConfigs.length)));
				throw new VisualizationException(exMsg);
			}
			MALSeriesConfig seriesConfig = classifierSeriesConfigs[0];
			SeriesDataHolder seriesDataHolder = new SeriesDataHolder(seriesConfig.getId(), classifierData);

			DataRow[] headerDataRows = classifierVisualizationHandler.getVisualizationHeaderData(classifierComponent, classifierVisualization, seriesDataHolder, null, pCtx);
			DataColumn[] classifierHeaderDataColumn = getAllHeaderDataColumns(headerDataRows);
			if (classifierHeaderDataColumn != null && classifierHeaderDataColumn.length != 0) {
				VisualizationData dataModel = new VisualizationData();
				DataRow classifierHeaderDataRow = new DataRow();
				// INFO hardcoding id to '1' for classifier header data row
				classifierHeaderDataRow.setId("1");
				classifierHeaderDataRow.setTemplateID(classifierVisualizationHandler.getHeaderRowTemplateID(classifierComponent, classifierVisualization, seriesConfig, pCtx));
				classifierHeaderDataRow.setVisualType(classifierVisualizationHandler.getHeaderRowVisualType(classifierComponent, classifierVisualization, seriesConfig, pCtx));
				classifierHeaderDataRow.setDataColumn(classifierHeaderDataColumn);
				dataModel.addDataRow(classifierHeaderDataRow);
				visualizationModel.setVisualizationData(dataModel);
			}
			return visualizationModel;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			modelRequestTime.add(System.currentTimeMillis(), timeTaken);
			requestTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	public VisualizationData getClassifierVisualizationData(MALClassifierComponent classifierComponent, List<Tuple> classifierData, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, PluginException, DataException {
		long stime = System.currentTimeMillis();
		try {
			MALClassifierVisualization classifierVisualization = (MALClassifierVisualization) classifierComponent.getVisualization(0);
			// for classifier visualization we always work with a TextModel
			VisualizationHandler classifierVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(classifierVisualization);
			MALSeriesConfig[] classifierSeriesConfigs = classifierVisualization.getSeriesConfig();
			if (classifierSeriesConfigs.length != 1) {
				String exMsg = messageGenerator.getMessage("invalid.classifiercomp.seriescfgcount", pCtx.getMessageGeneratorArgs(null, classifierComponent.toString(), new Integer(classifierSeriesConfigs.length)));
				throw new VisualizationException(exMsg);
			}

			SeriesDataHolder seriesDataHolder = new SeriesDataHolder(classifierSeriesConfigs[0].getId(), classifierData);

			VisualizationData dataModel = new VisualizationData();
			DataRow[] classifierDataRows = classifierVisualizationHandler.getVisualizationData(classifierComponent, classifierVisualization, seriesDataHolder, null, pCtx);
			dataModel.setDataRow(classifierDataRows);

			return dataModel;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			dataRequestTime.add(System.currentTimeMillis(), timeTaken);
			requestTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	public VisualizationModel getCompleteClassifierVisualizationModel(MALClassifierComponent classifierComponent, List<Tuple> classifierData, PresentationContext pCtx) throws VisualizationException, IOException,
			MALException, ElementNotFoundException, PluginException, DataException {
		long stime = System.currentTimeMillis();
		try {
			MALClassifierVisualization classifierVisualization = (MALClassifierVisualization) classifierComponent.getVisualization(0);
			// for classifier visualization we always work with a TextModel
			TextModel visualizationModel = new TextModel();
			// for classifier visualization we always work with a TextConfig
			TextConfig textConfig = new TextConfig();
			visualizationModel.setTextConfig(textConfig);
			// for any visualization we always work with VisualizationData
			VisualizationData dataModel = new VisualizationData();

			// get classifier configuration
			VisualizationHandler classifierVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(classifierVisualization);
			VisualizationSeriesContainer[] visualizationSeriesContainer = classifierVisualizationHandler.getVisualizationSeriesContainers(classifierComponent, classifierVisualization, pCtx);
			for (int i = 0; i < visualizationSeriesContainer.length; i++) {
				VisualizationSeriesContainer container = visualizationSeriesContainer[i];
				textConfig.addRowConfig((RowConfig) container);
			}
			// textConfig.setRowConfig((RowConfig[]) visualizationSeriesContainer);

			// get classifier header informaton
			MALSeriesConfig[] classifierSeriesConfigs = classifierVisualization.getSeriesConfig();
			if (classifierSeriesConfigs.length != 1) {
				String exMsg = messageGenerator.getMessage("invalid.classifiercomp.seriescfgcount", pCtx.getMessageGeneratorArgs(null, classifierComponent.toString(), new Integer(classifierSeriesConfigs.length)));
				throw new VisualizationException(exMsg);
			}

			MALSeriesConfig seriesConfig = classifierSeriesConfigs[0];
			SeriesDataHolder seriesDataHolder = new SeriesDataHolder(seriesConfig.getId(), classifierData);

			// header column information
			DataRow[] headerDataRows = classifierVisualizationHandler.getVisualizationHeaderData(classifierComponent, classifierVisualization, seriesDataHolder, null, pCtx);
			DataColumn[] classifierHeaderDataColumn = getAllHeaderDataColumns(headerDataRows);
			if (classifierHeaderDataColumn != null && classifierHeaderDataColumn.length != 0) {
				DataRow classifierHeaderDataRow = new DataRow();
				// INFO hardcoding id and templateid to '1' for classifier header data row
				classifierHeaderDataRow.setId("1");
				classifierHeaderDataRow.setTemplateID(classifierVisualizationHandler.getHeaderRowTemplateID(classifierComponent, classifierVisualization, seriesConfig, pCtx));
				classifierHeaderDataRow.setVisualType(classifierVisualizationHandler.getHeaderRowVisualType(classifierComponent, classifierVisualization, seriesConfig, pCtx));
				// classifierHeaderDataRow.setTemplateID("1");
				// classifierHeaderDataRow.setVisualType("classifierheaderrow");
				classifierHeaderDataRow.setDataColumn(classifierHeaderDataColumn);
				dataModel.addDataRow(classifierHeaderDataRow);
			}
			// actual data rows
			DataRow[] classifierDataRows = classifierVisualizationHandler.getVisualizationData(classifierComponent, classifierVisualization, seriesDataHolder, null, pCtx);

			for (int i = 0; i < classifierDataRows.length; i++) {
				dataModel.addDataRow(classifierDataRows[i]);
			}

			visualizationModel.setVisualizationData(dataModel);
			return visualizationModel;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			modelRequestTime.add(System.currentTimeMillis(), timeTaken);
			dataRequestTime.add(System.currentTimeMillis(), timeTaken);
			requestTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	private DataColumn[] getAllHeaderDataColumns(DataRow[] dataRows) {
		if (dataRows == null)
			return null;
		if (dataRows.length == 0)
			return null;
		if (dataRows.length > 1)
			throw new IllegalArgumentException("Wrong usage of this API. The datarow passed should not contain more than one category.");
		return dataRows[0].getDataColumn();
	}

	PerformanceMeasurement getModelRequestTime() {
		return modelRequestTime;
	}

	PerformanceMeasurement getDataRequestTime() {
		return dataRequestTime;
	}

	PerformanceMeasurement getRequestTime() {
		return requestTime;
	}

}