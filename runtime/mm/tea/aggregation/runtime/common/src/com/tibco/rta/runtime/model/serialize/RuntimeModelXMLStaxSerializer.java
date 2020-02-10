package com.tibco.rta.runtime.model.serialize;

import com.tibco.rta.Fact;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.model.serialize.impl.ModelXMLStaxSerializer;
import com.tibco.rta.query.QueryDef;

import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.List;

public class RuntimeModelXMLStaxSerializer implements ModelSerializer<XMLStreamWriter>  {

	private ModelXMLStaxSerializer delegate;

	public RuntimeModelXMLStaxSerializer(ModelXMLStaxSerializer delegate) {
		this.delegate = delegate;
	}

	@Override
	public void serialize(RtaSchema schema) throws Exception {
		throw new Exception("NA");
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
	public XMLStreamWriter getTransformed() {
		return delegate.getTransformed();
	}

	@Override
	public void serialize(QueryDef queryDef) throws Exception {
		throw new Exception("NA");
	}

	@Override
	public void serialize(RuleDef ruleDef) throws Exception {
		throw new Exception("NA");
	}

	@Override
    public void serializeRules(List<RuleDef> ruleDefs) throws Exception {
		throw new Exception("NA");
	    
    }

	@Override
    public void serialize(MetricFunctionDescriptor mfd) throws Exception {
		throw new Exception("NA");	    
    }

	@Override
    public void serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception {
		throw new Exception("NA");	    
    }

	@Override
    public void serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception {
		throw new Exception("NA");	    
    }

}
