package com.tibco.cep.runtime.service.dao.impl.tibas.versionedget;

import com.tibco.as.space.Tuple;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.serializers.SerializableLiteCodecHook;
import com.tibco.cep.runtime.model.serializers.as.ASSerializer;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

public class ASVG2 extends ASVersionedGet
{
	@Override
	public Integer getVersion(Tuple value) throws Exception {
    	//the below code depends on the code path leading up to
        //versionType = CONCEPT_BLOB_ASSERIALIZER
        //to know the type of the object codec and the name of the value column
		Integer cacheVersion = null;
    	byte[] bytes = value.getBlob(SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE);
    	if(SerializableLiteCodecHook.isSerializableLite(bytes)) {
    		cacheVersion = ASSerializer.getConceptVersion(SerializableLiteCodecHook
    				.getSerializableLiteDataInputStream(bytes));
    	} else if(bytes != null && bytes.length > 0) {
    		SerializableLiteCodecHook codec =((ASDaoProvider)RuleServiceProviderManager.getInstance()
				.getDefaultProvider().getCluster().getDaoProvider()).getTupleCodec().getObjectCodec();
    		VersionedObject vo = (VersionedObject)codec
    				.decode(bytes, SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE);
			if(vo != null) cacheVersion = vo.getVersion();
    	}
    	return cacheVersion;
	}
}