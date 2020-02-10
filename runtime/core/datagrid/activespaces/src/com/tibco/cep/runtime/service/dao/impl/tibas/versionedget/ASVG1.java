package com.tibco.cep.runtime.service.dao.impl.tibas.versionedget;

import com.tibco.as.space.Tuple;
import com.tibco.cep.runtime.model.serializers.as.ConceptTupleAdaptor;

//TODO put this in a package with only one character so that that the class name is shorter and less data is transmitted when invoking
public class ASVG1 extends ASVersionedGet
{
	@Override
	public Integer getVersion(Tuple value) {
		//the ability to use ConceptTupleAdapter relies on the code leading up to 
		//versionType = VersionType.CONCEPT_TUPLE;
		//adding a ConceptTupleAdapter in the Parameters for the SpaceMapCreator 
  	return ConceptTupleAdaptor.extractVersion(value);
	}
}