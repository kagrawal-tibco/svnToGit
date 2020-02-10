package com.tibco.rta.runtime.model.serialize;

import java.io.File;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.model.serialize.impl.ModelXMLStaxDeserializer;
import com.tibco.rta.query.QueryDef;

public class RuntimeModelXMLStaxDeserializer  implements ModelDeserializer  {

    
    private ModelXMLStaxDeserializer delegate;
    
    public RuntimeModelXMLStaxDeserializer(ModelXMLStaxDeserializer delegate) {
        this.delegate = delegate;        
    }
    
    @Override
    public RtaSchema deserialize(File file) throws Exception {
         throw new Exception("NA");
    }

    @Override
    public RtaSchema deserializeSchema(InputSource InputSource) throws Exception {
    	throw new Exception("NA");
    }

    @Override
    public Fact deserializeFact(InputSource InputSource) throws Exception {
        FactImpl fact = (FactImpl)delegate.deserializeFact(InputSource);      
        return fact;
    }

    @Override
    public List<Fact> deserializeFacts(InputSource InputSource) throws Exception {
        return delegate.deserializeFacts(InputSource);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    @Override
    public QueryDef deserializeQuery(InputSource InputSource) throws Exception {
    	throw new Exception("NA");
    }
    
    
    @Override
    public RuleDef deserializeRule(InputSource InputSource) throws Exception {		
    	throw new Exception("NA");
    }

	@Override
    public List<RuleDef> deserializeRules(InputSource InputSource) throws Exception {
		throw new Exception("NA");
    }

}
