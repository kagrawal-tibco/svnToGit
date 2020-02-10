package com.tibco.cep.dashboard.plugin.beviews.nextgenexport;

import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;

public interface ExportDataFormatter {

	public String convert(DrillDownDataTree tree, boolean includeSystemFields) throws NonFatalException;

}
