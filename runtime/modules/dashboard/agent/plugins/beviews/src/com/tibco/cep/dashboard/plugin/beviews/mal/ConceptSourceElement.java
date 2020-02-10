package com.tibco.cep.dashboard.plugin.beviews.mal;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;

public class ConceptSourceElement extends MALSourceElement {

	protected String scopeName;

	protected Map<String, MALFieldMetaInfo> fields;

	protected boolean isASuperClass;

	protected ConceptSourceElement(Concept sourceElement, TypeDescriptor typeDescriptor, boolean isASuperClass) {
		super(sourceElement);
//		this.scopeName = typeDescriptor.getImplClass().getName();
		this.scopeName = sourceElement.getFullPath();
		this.isASuperClass = isASuperClass;
		fields = new LinkedHashMap<String, MALFieldMetaInfo>();
		setId(sourceElement.getGUID());
		setName(sourceElement.getName());
		for (PropertyDefinition field : sourceElement.getAllProperties()) {
			PROPERTY_TYPES type = field.getType();
			DataType resolvedDataType = BuiltInTypes.resolve(type.toString());
			if (resolvedDataType == null) {
				if (type.compareTo(PROPERTY_TYPES.CONCEPT) == 0 || type.compareTo(PROPERTY_TYPES.CONCEPT_REFERENCE) == 0) {
					resolvedDataType = new ReferenceDataType(EntityCache.getInstance().getEntityByFullPath(field.getConceptTypePath()));
				}
			}
			fields.put(field.getName(), createMALFieldMetaInfo(field, resolvedDataType));
		}
	}

	protected MALFieldMetaInfo createMALFieldMetaInfo(PropertyDefinition field, DataType dataType) {
		return new MALFieldMetaInfo(field, field.getGUID(), field.getName(), dataType, field.isGroupByField(), field.isArray());
	}

	@Override
	public String getScopeName() {
		return scopeName;
	}

	@Override
	public MALFieldMetaInfo getField(String name) {
		return fields.get(name);
	}

	@Override
	public MALFieldMetaInfo[] getFields() {
		return fields.values().toArray(new MALFieldMetaInfo[fields.size()]);
	}

	public boolean isASuperClass(){
		return isASuperClass;
	}

}