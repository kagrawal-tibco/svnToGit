package com.tibco.cep.dashboard.plugin.beviews.data;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.tibco.cep.dashboard.plugin.beviews.mal.ConceptSourceElement;

class ConceptDynamicTupleSchemaSource extends ConceptTupleSchemaSource {

	ConceptDynamicTupleSchemaSource(ConceptSourceElement sourceElement) {
		super(sourceElement);
//		setTypeID(getTypeID());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		throw new UnsupportedOperationException("Dynamic tuples are not serializable");
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		throw new UnsupportedOperationException("Dynamic tuples are not serializable");
	}
}
