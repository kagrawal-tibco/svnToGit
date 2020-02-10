package com.tibco.cep.dashboard.psvr.vizengine.formatters;

import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 * 
 */
public class DataFormatHandlerFactory extends AbstractHandlerFactory {

	private static DataFormatHandlerFactory instance = null;

	public static final synchronized DataFormatHandlerFactory getInstance() {
		if (instance == null) {
			instance = new DataFormatHandlerFactory();
		}
		return instance;
	}

	private DataFormatHandlerFactory() {
		super("dataformathandlerfactory", "DataFormat Handler Factory", ResolverType.DATAFORMAT_HANDLER);
	}

	public DataFormatHandler getHandler(MALDataFormat dataFormat) throws PluginException {
		return (DataFormatHandler) super.getHandler(dataFormat);
	}

}