package com.tibco.cep.runtime.service.om.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * 
 * @author bgokhale
 *
 * Default implementation. this is used by the Coherence/AS invocation wrappers to wrap the results returned 
 * by the remote invocations
 * 
 */
public class DefaultInvocableResult implements Invocable.Result {

    final static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(DefaultInvocableResult.class);

    // Result should be a Serializable or SerializableLite
    Object result;
    Invocable.Status status;
    Exception causal;
    
    public DefaultInvocableResult() {
    	this.status = Invocable.Status.SUCCESS;
    }

    public DefaultInvocableResult(UID uid, Object result) {
    	this.status = Invocable.Status.SUCCESS;
    	this.result = result;
    }
    
    public DefaultInvocableResult(Exception causal) {
    	this.status = Invocable.Status.ERROR;
    	this.causal = causal;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
    @Override
    public Object getResult() {
        return result;
    }

    public void setStatus(Invocable.Status status) {
        this.status = status;
    }

    @Override
    public Invocable.Status getStatus() {
        return this.status;
    }

    public void setException(Exception causal) {
        this.causal = causal;
    }

    @Override
    public Exception getException() {
        return causal;  
    }

	@Override
	public void readExternal(DataInput in) throws IOException {
		if (in.readBoolean()) { // is result not-null?
			if (in.readBoolean()) { // is it serializableLite?
				try {
					String clzNm = in.readUTF();
					//System.err.println("## Clz=" + clzNm);
					Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
					ClassLoader classLoader = cluster.getRuleServiceProvider().getClassLoader();
					Class klass = Class.forName(clzNm, true, classLoader);
					SerializableLite sLite = (SerializableLite) klass.newInstance();
					sLite.readExternal(in);
					result = sLite;
				} catch (Exception e) {
				    LOGGER.log(Level.WARN, "Failed to de-serialize", e);
				}
			} else { // it is java Serializable
				result = decode(in);
			}
		}
		status = Enum.valueOf(Invocable.Status.class, in.readUTF());
		if (in.readBoolean()) {
			causal = (Exception)decode(in);
		}
	}

	@Override
	public void writeExternal(DataOutput out) throws IOException {
		out.writeBoolean(result != null);
		if (result != null) {
			if (result instanceof SerializableLite) {
				out.writeBoolean(true);
				out.writeUTF(result.getClass().getName());
				((SerializableLite)result).writeExternal(out);
			} else if (result instanceof Serializable) {
				out.writeBoolean(false);
				encode(result, out);
			}
		}
		out.writeUTF(status.name());
    	out.writeBoolean(causal != null);
    	if (causal != null) {
			encode(causal, out);
		}
	}
	
    private void encode(Object object, DataOutput out) throws IOException {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ObjectOutputStream oos = new ObjectOutputStream(bos);
    	oos.writeObject(object);
    	oos.close();
    	byte[] bytes = bos.toByteArray();
    	
    	out.writeInt(bytes.length);
    	out.write(bytes);
    }

    private Object decode(DataInput in) throws IOException {
    	int size = in.readInt();
    	byte[] bytes = new byte[size];
    	in.readFully(bytes);
    	
    	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	ObjectInputStream ois = new ObjectInputStream(bis);
    	
    	Object retVal = null;
    	try {
    		retVal = ois.readObject();
    	} catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARN, "Failed to decode", e);
		}	
    	ois.close();
    	return retVal;
    }
    
    public String toString() {
        return "DefaultInvocableResult: " + this.status.name() + "/" + this.result;
    }
}
