package com.tibco.cep.as.kit.tuple;

import com.tibco.cep.runtime.model.serializers.ObjectCodecHook;

public class SerializableTupleCodec extends DefaultTupleCodec
{
	protected ObjectCodecHook objectCodecHook;

    public SerializableTupleCodec() {
    	super();
        this.objectCodecHook = new ObjectCodecHook();
    }

	@Override
    public ObjectCodecHook getObjectCodec() {
        return objectCodecHook;
    }
}
