package com.tibco.cep.webstudio.server.logging;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tibco.cep.webstudio.client.process.ProcessLogService;
import com.tomsawyer.util.TSSystem;
import com.tomsawyer.util.logging.TSLogLevel;

/**
 * This class is used to set the log level for tomswayer.
 * 
 * @author sasahoo,dijadhav
 * 
 */
@SuppressWarnings("serial")
public class WebStudioProcessLogServiceImpl extends RemoteServiceServlet
		implements ProcessLogService {

	private static final String TS_LOGLEVEL = "tsLoglevel";

	// Default log level
	private TSLogLevel defaultLogLevel = TSLogLevel.Info;

	@Override
	public String setLogLevel(String s) {

		// Get the log level from web.xml init parameter
		String logLevel = this.getInitParameter(TS_LOGLEVEL);

		if (null != logLevel && !logLevel.trim().isEmpty()) {
			/*
			 * If log level is not matching with pre-defined levels the level is
			 * set as info.
			 */
			TSLogLevel tsLogLevel = TSLogLevel.Info;
			if (TSLogLevel.Debug.name().equalsIgnoreCase(logLevel)) {
				tsLogLevel = TSLogLevel.Debug;
			} else if (TSLogLevel.Error.name().equalsIgnoreCase(logLevel)) {
				tsLogLevel = TSLogLevel.Error;
			} else if (TSLogLevel.Fatal.name().equalsIgnoreCase(logLevel)) {
				tsLogLevel = TSLogLevel.Fatal;
			} else if (TSLogLevel.None.name().equalsIgnoreCase(logLevel)) {
				tsLogLevel = TSLogLevel.None;
			} else if (TSLogLevel.Trace.name().equalsIgnoreCase(logLevel)) {
				tsLogLevel = TSLogLevel.Trace;
			} else if (TSLogLevel.Warning.name().equalsIgnoreCase(logLevel)) {
				tsLogLevel = TSLogLevel.Warning;
			}
			TSSystem.setDebugLevel(tsLogLevel);
		} else {
			TSSystem.setDebugLevel(defaultLogLevel);
		}
		return "";
	}
}
