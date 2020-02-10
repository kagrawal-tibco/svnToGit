package com.tibco.cep.dashboard.plugin.beviews.export;

import java.io.PrintWriter;

/**
 * @author rajesh
 * 
 */
public interface ExportContentFormatter {

	String transform(ExportContentNode root, boolean isSystemFieldSupported) throws Exception;

	void transform(ExportContentNode root, boolean isSystemFieldSupported, PrintWriter printWriter) throws Exception;
}
