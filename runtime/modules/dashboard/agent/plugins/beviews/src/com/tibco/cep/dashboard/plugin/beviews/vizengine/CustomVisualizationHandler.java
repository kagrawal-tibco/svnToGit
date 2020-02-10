package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import com.tibco.cep.kernel.service.logging.Level;

import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DataType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;

/**
 * @author anpatil
 *
 */
//PATCH DO I need this class? can't I convert getCategoryAxisDataType to a util function . What a waste of inheritence
public abstract class CustomVisualizationHandler extends VisualizationHandler {

	DataType getCategoryAxisDataType(MALComponent component, MALVisualization visualization, PresentationContext pCtx) throws VisualizationException {
		DataType dataType = null;
		MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
		for (int i = 0; i < seriesConfigs.length; i++) {
			MALSeriesConfig seriesConfig = seriesConfigs[i];
			if (seriesConfig instanceof MALTwoDimSeriesConfig) {
				MALDataConfig categoryDataConfig = null;
				try {
					MALTwoDimSeriesConfig twoDimSeriesCfg = (MALTwoDimSeriesConfig) seriesConfig;
					categoryDataConfig = twoDimSeriesCfg.getCategoryDataConfig();
					MALFieldMetaInfo catfieldMetaInfo = pCtx.resolveFieldRef(categoryDataConfig.getExtractor().getSourceField());
					if (catfieldMetaInfo.isDate() == true) {
						// if we are null we set the datatype to datetime
						if (dataType == null) {
							dataType = DataType.DATETIME;
						}
					} else {
						// if we find a non date category field, we force the
						// datatype to be string
						dataType = DataType.STRING;
					}
				} catch (MALException e) {
					String exMsg = getMessage("visualizationhandler.fieldrefresolution.mal.failure", pCtx.getLocale(), pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(categoryDataConfig)));
					handleException(exMsg, e, Level.WARN);
					return null;
				} catch (ElementNotFoundException e) {
					String exMsg = getMessage("visualizationhandler.fieldrefresolution.elementnotfound.failure", pCtx.getLocale(), pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(categoryDataConfig)));
					handleException(exMsg, e, Level.WARN);
					return null;
				}
			} else {
				String exMsg = getMessage("visualizationhandler.general.invalid.seriescfgtype", pCtx.getLocale(), pCtx.getMessageGeneratorArgs(null, URIHelper.getURI(seriesConfig)));
				throw new VisualizationException(exMsg);
			}
		}
		return dataType;
	}
}
