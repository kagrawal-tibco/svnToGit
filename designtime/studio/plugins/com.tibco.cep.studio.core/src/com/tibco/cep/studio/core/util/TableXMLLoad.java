package com.tibco.cep.studio.core.util;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMILoadImpl;
import org.xml.sax.helpers.DefaultHandler;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.util.StudioConfig;

public class TableXMLLoad extends XMILoadImpl {

	public static final String DEFAULT_VALUE 		= "true";  // change if we want the default to be different
	public static final String PROP_DEMAND_LOAD 	= "bui.demand.load.table";
	
	public TableXMLLoad(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected DefaultHandler makeDefaultHandler() {
		String demandLoad = StudioConfig.getInstance().getProperty(PROP_DEMAND_LOAD, DEFAULT_VALUE);
		if (demandLoad == null || "".equals(demandLoad)) {
			demandLoad = DEFAULT_VALUE;
		}
		if (demandLoad != null && ("true".equalsIgnoreCase(demandLoad) || "on".equalsIgnoreCase(demandLoad)))  {
			if (options.get("RELOAD") != null && options.get("RELOAD").equals("true")) {
				StudioCorePlugin.debug(getClass().getName(), "Loading, using SAXXMIHandler");
				return new SAXXMIHandler(resource, helper, options);
			} else {
				StudioCorePlugin.debug(getClass().getName(), "Demand load, using DecisionTableSAXXMIHandler");
				return new TableSAXXMIHandler(resource, helper, options);
			}
		}

		StudioCorePlugin.debug(getClass().getName(), "Normal table load, using SAXXMIHandler");
		return new SAXXMIHandler(resource, helper, options);
	}

}
