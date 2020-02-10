package com.tibco.cep.dashboard.plugin.beviews.data;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.plugin.beviews.mal.ReferenceDataType;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;

public class ConceptTupleSchema extends TupleSchema {

	private static final long serialVersionUID = 3689633836192450861L;

	private TupleSchemaField extIdField;

	private TupleSchemaField parentIdField;

	public ConceptTupleSchema(ConceptTupleSchemaSource source, boolean dynamic) {
		super(source, dynamic);
		int fieldIdx = 0;
		// id field
		TupleSchemaField idField = new TupleSchemaField("@id", "id", BuiltInTypes.LONG, Long.MAX_VALUE, false, null, null, true, false);
		addField(fieldIdx, idField);
		fieldIdx++;
		setIDField(idField);
		// ext id field
		extIdField = new TupleSchemaField("@extId", "extId", BuiltInTypes.STRING, Long.MAX_VALUE, false, null, null, true, false);
		addField(fieldIdx, extIdField);
		fieldIdx++;
		MALSourceElement sourceElement = source.getSourceElement();
		// parent id field
		parentIdField = new TupleSchemaField("@parentid", "parentid", BuiltInTypes.LONG, Long.MAX_VALUE, true, null, null, true, true);
		addField(fieldIdx, parentIdField);
		fieldIdx++;
		// add all fields
		for (MALFieldMetaInfo field : sourceElement.getFields()) {
			boolean isArtificial = field.getDataType() instanceof ReferenceDataType;
			addField(fieldIdx, field.getName(), field.getId(), field.getDataType(), field.isMulti(), Long.MAX_VALUE, false, isArtificial);
			fieldIdx++;
		}
	}

	public ConceptTupleSchema(ConceptTupleSchemaSource source) {
		this(source, false);
	}

	public final TupleSchemaField getExtIdField() {
		return extIdField;
	}

	public final TupleSchemaField getParentIdField() {
		return parentIdField;
	}

}