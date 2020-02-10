package com.tibco.cep.dashboard.plugin.beviews.mal;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;

public class MetricSourceElement extends ConceptSourceElement {

	protected MetricSourceElement(Metric metric, TypeDescriptor typeDescriptor) {
		super(metric, typeDescriptor, false);
	}

	@Override
	protected MALFieldMetaInfo createMALFieldMetaInfo(PropertyDefinition field, DataType dataType) {
		MALFieldMetaInfo fieldMetaInfo = super.createMALFieldMetaInfo(field, dataType);
		//add external URLs
		ExternalURL externalURL = EntityUtils.getURL(field);
		if (externalURL != null) {
			fieldMetaInfo.addAttribute(ExternalURL.KEY, externalURL);
		}
		return fieldMetaInfo;
	}

}