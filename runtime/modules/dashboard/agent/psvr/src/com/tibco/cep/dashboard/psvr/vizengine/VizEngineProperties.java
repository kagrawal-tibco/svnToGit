package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;


public class VizEngineProperties implements PropertyKeys {

	/**
	 * @deprecated
	 */
	public final static PropertyKey LEGACY_SEARCH_PAGE_ENABLE = new PropertyKey("legacy.search.page.enabled","Indicates whether to switch to the legacy drilldown page",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

}
