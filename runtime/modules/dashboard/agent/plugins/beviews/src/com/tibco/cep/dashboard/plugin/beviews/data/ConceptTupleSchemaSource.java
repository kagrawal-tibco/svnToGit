package com.tibco.cep.dashboard.plugin.beviews.data;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.tibco.cep.dashboard.common.data.TupleSchemaSource;
import com.tibco.cep.dashboard.plugin.beviews.mal.ConceptSourceElement;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.designtime.core.model.element.Concept;

public class ConceptTupleSchemaSource extends TupleSchemaSource {

	private ConceptSourceElement sourceElement;

	ConceptTupleSchemaSource(ConceptSourceElement sourceElement) {
		this.sourceElement = sourceElement;
		setScopeName(sourceElement.getScopeName());
		setTypeID(sourceElement.getId());
		setTypeName(sourceElement.getName());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String guid = in.readUTF();
		Concept concept = (Concept) EntityCache.getInstance().getEntity(guid);
		if (concept == null){
			throw new IOException("could not find definition for "+guid);
		}
		this.sourceElement = (ConceptSourceElement) MALSourceElementCache.getInstance().getMALSourceElement(guid);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(sourceElement.getId());
	}

	/**
	 * @return
	 */
	MALSourceElement getSourceElement(){
		return sourceElement;
	}

}