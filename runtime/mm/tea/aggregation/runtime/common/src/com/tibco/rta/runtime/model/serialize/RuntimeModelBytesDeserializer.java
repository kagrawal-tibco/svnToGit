package com.tibco.rta.runtime.model.serialize;

import java.io.File;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.model.serialize.impl.ModelBytesDeserializer;
import com.tibco.rta.query.QueryDef;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/1/13
 * Time: 2:11 PM
 * Decorator to be used by runtime for deserialization from bytes.
 */
public class RuntimeModelBytesDeserializer implements ModelDeserializer {

    private ModelBytesDeserializer delegate;

    public RuntimeModelBytesDeserializer(ModelBytesDeserializer delegate) {
        this.delegate = delegate;
    }

    @Override
    public RtaSchema deserialize(File file) throws Exception {
        return delegate.deserialize(file);
    }

    @Override
    public RtaSchema deserializeSchema(InputSource InputSource) throws Exception {
        return delegate.deserializeSchema(InputSource);
    }

    @Override
    public Fact deserializeFact(InputSource InputSource) throws Exception {
        return delegate.deserializeFact(InputSource);
    }

    @Override
    public List<Fact> deserializeFacts(InputSource InputSource) throws Exception {
        List<Fact> facts = delegate.deserializeFacts(InputSource);
        for (Fact fact : facts) {
            if (fact instanceof FactImpl) {
                FactImpl factImpl = (FactImpl)fact;
                String ownerSchemaName = factImpl.getOwnerSchemaName();
                RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(ownerSchemaName);
                factImpl.setOwnerSchema(schema);
//                Measurement measurement =
//                        (Measurement) ModelRegistry.INSTANCE.lookup("/" + ownerSchemaName + "/" + factImpl.getOwnerMeasurementName());
//                factImpl.setMeasurement(measurement);
            }
        }
        return facts;
    }

    @Override
    public QueryDef deserializeQuery(InputSource InputSource) throws Exception {
        return delegate.deserializeQuery(InputSource);  
    }

	@Override
	public RuleDef deserializeRule(InputSource InputSource) throws Exception {		
		return delegate.deserializeRule(InputSource);
	}

	@Override
    public List<RuleDef> deserializeRules(InputSource InputSource) throws Exception {
		return delegate.deserializeRules(InputSource);
    }
}
