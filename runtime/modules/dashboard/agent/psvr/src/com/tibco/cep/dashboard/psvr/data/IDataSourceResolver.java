/**
 *
 */
package com.tibco.cep.dashboard.psvr.data;

import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.IResolver;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
public interface IDataSourceResolver extends IResolver {

	public DataSourceHandlerKey getKey(Logger logger, MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException;

}
