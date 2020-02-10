package com.tibco.rta.runtime.model.serialize;

import com.tibco.rta.Fact;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.model.serialize.impl.ModelBytesSerializer;
import com.tibco.rta.query.QueryDef;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/1/13
 * Time: 2:08 PM
 * Decorator for runtime to serialize to bytes.
 */
public class RuntimeModelBytesSerializer implements ModelSerializer<byte[]> {

    private ModelBytesSerializer delegate;

    public RuntimeModelBytesSerializer(ModelBytesSerializer delegate) {
        this.delegate = delegate;
    }

    @Override
    public void serialize(RtaSchema schema) throws Exception {
        delegate.serialize(schema);
    }

    @Override
    public void serialize(Fact fact) throws Exception {
        delegate.serialize(fact);
    }

    @Override
    public void serialize(List<Fact> facts) throws Exception {
        delegate.serialize(facts);
    }

    @Override
    public byte[] getTransformed() {
        return delegate.getTransformed();
    }

    @Override
    public void serialize(QueryDef queryDef) throws Exception {
        delegate.serialize(queryDef);
    }

	@Override
	public void serialize(RuleDef ruleDef) throws Exception {
		delegate.serialize(ruleDef);
	}

	@Override
    public void serializeRules(List<RuleDef> ruleDefs) throws Exception {
	   delegate.serializeRules(ruleDefs);
	    
    }

	@Override
    public void serialize(MetricFunctionDescriptor mfd) throws Exception {
	    delegate.serialize(mfd);
    }

	@Override
    public void serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception {
	    delegate.serializeAllFunctionDesc(mfds);	    
    }

	@Override
    public void serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception {
		delegate.serializeAllActionDesc(ads);	    
    }
}
