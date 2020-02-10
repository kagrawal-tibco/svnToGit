package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.query.QueryDef;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/12/12
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelBytesSerializer implements ModelSerializer<byte[]> {

    private ByteArrayOutputStream byteArrayOutputStream;

    public ModelBytesSerializer() {
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public void serialize(RtaSchema schema) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(schema);
    }

    @Override
    public void serialize(Fact fact) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(fact);
    }

    @Override
    public void serialize(List<Fact> facts) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(facts);
    }

    @Override
    public byte[] getTransformed() {
        return byteArrayOutputStream.toByteArray();
    }

	@Override
	public void serialize(QueryDef queryDef) throws Exception {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(queryDef);
	}

	@Override
	public void serialize(RuleDef ruleDef) throws Exception {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(ruleDef);		
	}

	@Override
    public void serializeRules(List<RuleDef> ruleDefs) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(ruleDefs);	    
    }

	@Override
    public void serialize(MetricFunctionDescriptor mfd) throws Exception {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(mfd);			    
    }

	@Override
    public void serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(mfds);	    	    
    }

	@Override
    public void serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(ads);	    	    	    
    }
}
