package com.tibco.cep.dashboard.psvr.mal;

import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.security.SecurityToken;

public final class MALReferenceResolver {
	
	public static Object resolveReference(SecurityToken token, Object object){
		if (object instanceof MALExternalReference){
			MALExternalReference reference = (MALExternalReference) object;
			return reference.getExternalReference();
		}
		throw new IllegalArgumentException("Unknown type for reference resolution ["+object.getClass().getName()+"]");
	}
	
	public static Object[] resolveReferences(SecurityToken token, Object[] objects){
		Object[] references = new Object[objects.length];
		for (int i = 0; i < objects.length; i++) {
			references[i] = resolveReference(token, objects[i]);
		}
		return references;
	}
	
	public static MALFieldMetaInfo resolveFieldReference(SecurityToken token, Object sourceField){
		if (sourceField instanceof MALExternalReference){
			sourceField = ((MALExternalReference)sourceField).getExternalReference();
		}
		return new MALFieldMetaInfo(sourceField);
	}
	
	public static MALFieldMetaInfo[] resolveFieldReferences(SecurityToken token, Object[] sourceFields){
		if (sourceFields == null){
			return null;
		}
		MALFieldMetaInfo[] fields = new MALFieldMetaInfo[sourceFields.length];
		for (int i = 0; i < sourceFields.length; i++) {
			fields[i] = resolveFieldReference(token, sourceFields[i]);
		}
		return fields;
	}

}
