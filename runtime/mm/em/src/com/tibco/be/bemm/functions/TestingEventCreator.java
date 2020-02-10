package com.tibco.be.bemm.functions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;


import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;


public class TestingEventCreator extends EventCreator {

	TestingEventCreator() {
		super(null,null,null);
	}

	@Override
	SimpleEvent create() {
		return new DummySimpleEvent();
	}

	
	class DummySimpleEvent implements SimpleEvent{
		
		private Map<String, Object> properties = new HashMap<String, Object>();

		public void acknowledge() {
			throw new UnsupportedOperationException("acknowledge");
		}

		public void deserialize(EventDeserializer deserializer) {
			throw new UnsupportedOperationException("deserialize");
		}

		public EventContext getContext() {
			throw new UnsupportedOperationException("getContext");
		}

		public String getDestinationURI() {
			throw new UnsupportedOperationException("getDestinationURI");
		}
		
		@Override
		public boolean isAcknowledged() {
			// TODO Auto-generated method stub
			return false;
		}

		public ExpandedName getExpandedName() {
			throw new UnsupportedOperationException("getExpandedName");
		}

		public EventPayload getPayload() {
			throw new UnsupportedOperationException("getPayload");
		}

		public String getPayloadAsString() {
			throw new UnsupportedOperationException("getPayloadAsString");
		}

		public Object getProperty(String name) throws NoSuchFieldException {
			return properties.get(name);
		}

		public XmlTypedValue getPropertyAsXMLTypedValue(String name) throws Exception {
			throw new UnsupportedOperationException("getPropertyAsXMLTypedValue");
		}

		public String[] getPropertyNames() {
			return properties.keySet().toArray(new String[properties.size()]);
		}

		public boolean getRetryOnException() {
			throw new UnsupportedOperationException("getRetryOnException");
		}

		public void serialize(EventSerializer serializer) {
			throw new UnsupportedOperationException("serialize");
		}

		public void setContext(EventContext ctx) {
			throw new UnsupportedOperationException("setContext");
		}

		public void setExtId(String extId) throws ExtIdAlreadyBoundException {
			throw new UnsupportedOperationException("setExtId");
		}

		public void setPayload(EventPayload payload) {
			throw new UnsupportedOperationException("setPayload");
		}

		public void setProperty(String name, Object value) throws Exception {
			properties.put(name, value);
		}

		public void setProperty(String name, String value) throws Exception {
			properties.put(name, value);			
		}

		public void setProperty(String name, XmlTypedValue value) throws Exception {
			properties.put(name, value);
		}
		
		@Override
		public Object getPropertyValue(String name) throws NoSuchFieldException {
			throw new UnsupportedOperationException("getPropertyValue");
		}
		@Override
		public String getType() {
			throw new UnsupportedOperationException("getType");
		}
		
		@Override
		public void setPropertyValue(String name, Object value)
				throws Exception {
			throw new UnsupportedOperationException("setPropertyValue");
			
		}

		public void toXiNode(XiNode root) throws Exception {
			throw new UnsupportedOperationException("toXiNode");
		}

		public long getTTL() {
			throw new UnsupportedOperationException("getTTL");
		}

		public boolean hasExpiryAction() {
			throw new UnsupportedOperationException("hasExpiryAction");
		}

		public void onExpiry() {
			throw new UnsupportedOperationException("onExpiry");
		}

		public void setTTL(long ttl) {
			throw new UnsupportedOperationException("setTTL");
		}

		public String getExtId() {
			throw new UnsupportedOperationException("getExtId");
		}

		public long getId() {
			throw new UnsupportedOperationException("getId");
		}

		public void start(Handle handle) {
			throw new UnsupportedOperationException("start");
		}

        public void setLoadedFromCache() {
        }

        public boolean isLoadedFromCache() {
            return false;
        }

        @Override
		public String toString() {
			return properties.toString();
		}
        @Override
        public void readExternal(DataInput in) throws IOException {
        	throw new UnsupportedOperationException("readExternal");
        	
        }
        @Override
        public void writeExternal(DataOutput out) throws IOException {
        	throw new UnsupportedOperationException("writeExternal");
        	
        }
				
		public Set getPropertyNamesAsSet() {
	        return properties.entrySet();
	    }
	}
}