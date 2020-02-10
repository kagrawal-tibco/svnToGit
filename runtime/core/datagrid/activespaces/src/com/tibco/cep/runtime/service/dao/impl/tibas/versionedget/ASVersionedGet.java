package com.tibco.cep.runtime.service.dao.impl.tibas.versionedget;

import com.tibco.as.space.ASException;
import com.tibco.as.space.InvokeOptions;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.InvokeResult;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASEntityDao.VersionType;
import com.tibco.cep.runtime.service.om.api.ComparisonResult;

public abstract class ASVersionedGet implements com.tibco.as.space.remote.Invocable
{
	public static final String VERSION = "v";
	public static final String COMPARISON_RESULT = "r";

    public static Object doInvoke(long key, int version, VersionType versionType, SpaceMap spaceMap) throws ASException {
    	Tuple contextTuple = Tuple.create(1);
    	contextTuple.putInt(VERSION, version);
    	Tuple keyTuple = spaceMap.getTupleAdaptor().makeTuple(key);
    	InvokeOptions options = InvokeOptions.create().setContext(contextTuple);
    	InvokeResult result = null;
    	
    	switch(versionType) {
    		case CONCEPT_TUPLE:
    			result = spaceMap.getSpace().invoke(keyTuple, ASVG1.class.getName(), options);
    			break;
    		case CONCEPT_BLOB_ASSERIALIZER:
    			result = spaceMap.getSpace().invoke(keyTuple, ASVG2.class.getName(), options);
    			break;
    	}
    	
    	if(result == null) return null;
    	if(result.hasError()) throw result.getError();
    	Tuple resultTuple = result.getReturn();
    	if(resultTuple == null) return null;
    	if(resultTuple.size() == 1) {
    		return ComparisonResult.values()[resultTuple.getShort(COMPARISON_RESULT)];
    	}
    	return spaceMap.getTupleAdaptor().extractValue(resultTuple);
    }

	@Override
	public Tuple invoke(Space space, Tuple keyTuple, Tuple context) {
		try {
			Tuple value = space.get(keyTuple);
			Integer cacheVersion = null;
            if (value != null) {	            
            	cacheVersion = getVersion(value);
            }
            
            if(cacheVersion == null) {
            	Tuple result = Tuple.create(1); 
            	result.putShort(COMPARISON_RESULT, (short)ComparisonResult.VALUE_NOT_PRESENT.ordinal()); 
            	return result;	
            }

            if (cacheVersion > context.getInt(VERSION)) {
                return value;
            } else {
            	Tuple result = Tuple.create(1); 
            	result.putShort(COMPARISON_RESULT, (short)ComparisonResult.SAME_VERSION.ordinal());
            	return result;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	abstract public Integer getVersion(Tuple value) throws Exception;
}