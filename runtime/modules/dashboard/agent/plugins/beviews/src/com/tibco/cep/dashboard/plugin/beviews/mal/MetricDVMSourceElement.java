package com.tibco.cep.dashboard.plugin.beviews.mal;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;

public class MetricDVMSourceElement extends MetricSourceElement {

	protected MetricDVMSourceElement(Metric metric, TypeDescriptor typeDescriptor) {
		super(metric, typeDescriptor);
	}

	@Override
	protected MALFieldMetaInfo createMALFieldMetaInfo(PropertyDefinition field, DataType dataType) {
		MALFieldMetaInfo fieldMetaInfo = super.createMALFieldMetaInfo(field, dataType);
		boolean isParentIdentifierField = Boolean.parseBoolean(EntityUtils.getPropertyValue(field, "parentidentifier"));
		fieldMetaInfo.addAttribute("parentidentifier", isParentIdentifierField);
		return fieldMetaInfo;
	}

}
