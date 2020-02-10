package com.tibco.cep.runtime.model.serializers.as;

import java.util.logging.Logger;

import com.tibco.cep.as.kit.tuple.DefaultTupleCodec;
import com.tibco.cep.runtime.model.serializers.SerializableLiteCodecHook;

public class SerializableLiteTupleCodec extends DefaultTupleCodec
{
	protected SerializableLiteCodecHook codecHook;

    public SerializableLiteTupleCodec(ClassLoader classLoader, Logger logger) {
    	super();
        codecHook = new SerializableLiteCodecHook(classLoader, logger);
    }

    @Override
    public SerializableLiteCodecHook getObjectCodec() {
        return codecHook;
    }
}
