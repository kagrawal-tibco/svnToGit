package com.tibco.cep.dashboard.plugin.beviews.mal;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;

public class MALExternalURLUtils {

	public static ExternalURL getURL(String typeid, String fieldName) {
		MALSourceElement sourceElement = MALSourceElementCache.getInstance().getMALSourceElement(typeid);
		if (sourceElement != null) {
			MALFieldMetaInfo field = sourceElement.getField(fieldName);
			if (field != null) {
				return (ExternalURL) field.getAttribute(ExternalURL.KEY);
			}
		}
		return null;
	}

	public static String getURLLink(String typeid, String fieldName) {
		ExternalURL externalURL = getURL(typeid, fieldName);
		if (externalURL != null) {
			return externalURL.getURL();
		}
		return null;
	}

	public static Map<String, ExternalURL> getFieldNameToURLsMap(String typeid) {
		Map<String, ExternalURL> urls = new LinkedHashMap<String, ExternalURL>();
		MALSourceElement sourceElement = MALSourceElementCache.getInstance().getMALSourceElement(typeid);
		if (sourceElement != null) {
			for (MALFieldMetaInfo field : sourceElement.getFields()) {
				ExternalURL url = (ExternalURL) field.getAttribute(ExternalURL.KEY);
				if (url != null) {
					urls.put(field.getName(), url);
				}
			}
		}
		return urls;
	}
}
