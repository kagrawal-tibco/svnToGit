package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.be.model.rdf.primitives.*;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.AbstractStateTimeoutEvent;
import com.tibco.cep.runtime.model.element.impl.property.history.ImplSerializerHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.SimpleSerializerHelper;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.event.impl.VariableTTLTimeEventImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.values.XsDateTime;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 3, 2006
 * Time: 9:13:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultSerializer implements Serializer {

    private static DefaultSerializer instance;
    protected ClassLoader clzLoader;
    protected RuleServiceProvider rsp;
    protected TypeManager typeManager;

//    final static XiFactory xf= XiFactoryFactory.newInstance();
    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    static TypeRenderer java2xsd_dt_conv= registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);


    public DefaultSerializer(RuleServiceProvider _rsp) {
        rsp         = _rsp;
        clzLoader   = rsp.getClassLoader();
        typeManager = rsp.getTypeManager();
    }

    protected void serialize(Object obj, DataOutput dout) throws IOException, PropertyException {
        if(obj instanceof Entity) {
            dout.writeLong(((Entity)obj).getId());
        } else if(obj instanceof String) {
            dout.writeUTF(((String)obj));
        } else if(obj instanceof Long) {
            dout.writeLong(((Long)obj).longValue());
        } else if(obj instanceof Boolean) {
            dout.writeBoolean(((Boolean)obj).booleanValue());
        } else if(obj instanceof Double) {
            dout.writeDouble(((Double)obj).doubleValue());
        } else if(obj instanceof Integer) {
            dout.writeInt(((Integer)obj).intValue());
        } else if(obj instanceof PropertyBDBKey) {
            PropertyBDBKey key = (PropertyBDBKey) obj;
            dout.writeLong(key.getSubjectId());
            dout.writeInt(key.getFieldId());
        } else {
            throw new IOException("Unsupported type=" + obj);
        }
    }

    protected void serializeEventProperty(Object obj, RDFPrimitiveTerm term, DataOutput dout) throws IOException, PropertyException {
        if(obj == null) {
            dout.writeBoolean(false);
        } else {
            dout.writeBoolean(true);
            //dout.writeUTF(obj.getClass().getName());
            //serialize(obj, dout);
            if(term instanceof RDFStringTerm) {
                dout.writeUTF((String) obj);
            } else if(term instanceof RDFLongTerm) {
                dout.writeLong(((Long)obj).longValue());
            } else if(term instanceof RDFIntegerTerm) {
                dout.writeInt(((Integer)obj).intValue());
            } else if(term instanceof RDFBooleanTerm) {
                dout.writeBoolean(((Boolean)obj).booleanValue());
            } else if(term instanceof RDFDoubleTerm) {
                dout.writeDouble(((Double)obj).doubleValue());
            } else if(term instanceof RDFDateTimeTerm) {
                dout.writeUTF(writeCalendar((Calendar)obj));
            } else
                throw new IOException("Unsupported type=" + term.getName());
        }
    }

    protected Object deserializeEventProperty(RDFPrimitiveTerm term, DataInput din) throws IOException {
        //String type = din.readUTF();
        if(term instanceof RDFStringTerm) {
            return din.readUTF();
        } else if(term instanceof RDFLongTerm) {
            return new Long(din.readLong());
        } else if(term instanceof RDFIntegerTerm) {
            return new Integer(din.readInt());
        } else if(term instanceof RDFBooleanTerm) {
            return Boolean.valueOf(din.readBoolean());
        } else if(term instanceof RDFDoubleTerm) {
            return new Double(din.readDouble());
        } else if (term instanceof RDFDateTimeTerm) {
            return readCalendar(din.readUTF());
        }
        throw new IOException("Unsupported type=" + term.getName());
    }

    public DataOutput serialize(DataOutput output, Object obj) {

        DataOutput dout = null;

        if(output == null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            dout = null;
            dout = new DataOutputStream(out);
        } else
            dout = output;

        if(obj instanceof Entity) {
            Entity e = (Entity) obj;
            long checkpointTime = SerializeEntityWrapper.DEFAULT_CHECKPOINT_TIME;
            boolean wasDeleted = SerializeEntityWrapper.DEFAULT_WAS_DELETED;
            if(e instanceof SerializeEntityWrapper) {
                SerializeEntityWrapper sew = (SerializeEntityWrapper)e;
                e = sew.entity;
                sew.entity = null;
                checkpointTime = sew.checkpointTime;
                wasDeleted = sew.wasDeleted;
            }

            try {
                dout.writeLong(e.getId());
                if(e.getExtId() != null) {
                    dout.writeBoolean(true);
                    dout.writeUTF(e.getExtId());
                } else {
                    dout.writeBoolean(false);
                }
                
                dout.writeBoolean(wasDeleted);
                dout.writeUTF(e.getClass().getName());
                dout.writeLong(checkpointTime);

                if(e instanceof SimpleEvent) {
                    SimpleEvent evt = (SimpleEvent) e;
                    String[] props = evt.getPropertyNames();
                    com.tibco.cep.designtime.model.event.Event designTimeEvent = (com.tibco.cep.designtime.model.event.Event) rsp.getProject().getOntology().getEntity(evt.getExpandedName());
                    for(int i = 0; i < props.length; i++) {
                        Object prop = evt.getProperty(props[i]);
                        RDFPrimitiveTerm term = designTimeEvent.getPropertyType(props[i]);
                        serializeEventProperty(prop, term, dout);
                    }
                    EventPayload payload = ((SimpleEvent)e).getPayload();
                    if (payload != null) {
                        dout.writeBoolean(true);
                        int typeId = PayloadFactoryImpl.getPayloadTypeId(payload.getClass());
                        byte[] payloadBytes = payload.toBytes();
                        dout.writeInt(typeId);
                        dout.writeInt(payloadBytes.length);
                        dout.write(payloadBytes);
                    } else {
                        dout.writeBoolean(false);
                    }

                } else if(e instanceof TimeEvent) {
                    TimeEvent te = (TimeEvent) e;
                    dout.writeLong(te.getScheduledTimeMillis());
                    String closure = te.getClosure();
                    if(closure != null) {
                        dout.writeBoolean(true);
                        dout.writeUTF(closure);
                    } else
                        dout.writeBoolean(false);
                    dout.writeLong(te.getTTL());
                    if(e instanceof AbstractStateTimeoutEvent) {
                        dout.writeLong(((AbstractStateTimeoutEvent)e).sm_id);
                        dout.writeUTF(((AbstractStateTimeoutEvent)e).property_name);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1.getMessage(), e1);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1.getMessage(), e1);
            } catch (PropertyException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1.getMessage(), e1);
            } catch (Exception e1) {
                e1.printStackTrace();
                throw new RuntimeException(e1.getMessage(), e1);
            }
        } else if(obj instanceof Property) {
            Property p = (Property)obj;
            try {
                //dout.writeLong(p.getParent().getId());
                //dout.writeUTF(p.getName());
                //dout.writeInt(p.getHistorySize());
                if(p instanceof PropertyAtomSimple)
                    SimpleSerializerHelper.serialize((PropertyAtomSimple) p, dout);
                else if(p instanceof PropertyArraySimple)
                    SimpleSerializerHelper.serialize((PropertyArraySimple)p, dout);
                else if(p instanceof PropertyAtomImpl)
                    ImplSerializerHelper.serialize((PropertyAtomImpl) p, dout);
                else if(p instanceof PropertyArrayImpl)
                    ImplSerializerHelper.serialize((PropertyArrayImpl)p, dout);
                //serializeProperty(p, dout);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            try {
                serialize(obj, dout);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return dout;
    }

    public Object deserialize(DataInput input, Object obj) {
        if(obj == null) {
            try {
                long id = input.readLong();
                String extId=null;
                if(input.readBoolean()) {
                    extId = input.readUTF();
                }
                //wasDeleted, from entity header
                if(input.readBoolean()) {
                    return null;
                }
                Class clz = Class.forName(input.readUTF(), true, clzLoader);
                //checkpoint time, is only stored in BDB, is not loaded into memory
                input.readLong();
                
                Constructor cons = clz.getConstructor(new Class[] {long.class, String.class});
                obj = cons.newInstance(new Object[] {new Long(id), extId});
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if(obj instanceof SimpleEvent) {
            try {
                SimpleEvent evt = (SimpleEvent) obj;
                String[] props = evt.getPropertyNames();
                com.tibco.cep.designtime.model.event.Event designTimeEvent = (com.tibco.cep.designtime.model.event.Event) rsp.getProject().getOntology().getEntity(evt.getExpandedName());
                for(int i=0; i<props.length;i++) {
                        if(input.readBoolean()) {
                            RDFPrimitiveTerm term = designTimeEvent.getPropertyType(props[i]);
                            Object prop = deserializeEventProperty(term, input);
                            evt.setProperty(props[i], prop);
                        } else
                            evt.setProperty(props[i], (String)null);
                }
                boolean hasPayload = input.readBoolean();
                if (hasPayload) {
                    int typeId = input.readInt();
                    int len = input.readInt();
                    byte[] bytes = new byte[len];
                    input.readFully(bytes);
                    PayloadFactory payloadFactory = typeManager.getPayloadFactory();
                    evt.setPayload(payloadFactory.createPayload(evt.getExpandedName(), bytes));
                }
                return evt;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }


        } else if (obj instanceof TimeEvent) {
            TimeEvent te = (TimeEvent) obj;
            try {
                te.setScheduledTime(input.readLong());
                if(input.readBoolean())
                    te.setClosure(input.readUTF());
                long ttl = input.readLong();
                if(obj instanceof VariableTTLTimeEventImpl) te.setTTL(ttl);
                if(obj instanceof AbstractStateTimeoutEvent) {
                    ((AbstractStateTimeoutEvent)obj).sm_id = input.readLong();
                    ((AbstractStateTimeoutEvent)obj).property_name = input.readUTF();
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        else if(obj instanceof Property) {
            try {
                //input.readLong();
                //input.readUTF();
                if(obj instanceof PropertyAtomImpl)
                    ImplSerializerHelper.deserialize((PropertyAtomImpl) obj, input);
                else if(obj instanceof PropertyArrayImpl)
                    ImplSerializerHelper.deserialize((PropertyArrayImpl) obj, input);
                else if(obj instanceof PropertyAtomSimple)
                    SimpleSerializerHelper.deserialize((PropertyAtomSimple)obj, input);
                else if(obj instanceof PropertyArraySimple)
                    SimpleSerializerHelper.deserialize((PropertyArraySimple)obj, input);
                //deserializeProperty((Property)obj, input);
                else
                    throw new IOException("Unsupported type=" + obj);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        else if(obj instanceof PropertyBDBKey) {
            try {
                ((PropertyBDBKey)obj).setFieldId(input.readInt());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        else if(obj instanceof PersistentStore.VersionRecord) {
            try {
                ((PersistentStore.VersionRecord)obj).db_be_version = input.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return obj;
    }

    public static DefaultSerializer getInstance() {
        if(instance == null)
            instance = new DefaultSerializer(null);
        return instance;
    }

    public String writeCalendar(Calendar calendar) {
        try {
            return ((XsDateTime)java2xsd_dt_conv.convertToTypedValue(calendar)).castAsString();
        } catch (Exception e) {
            e.printStackTrace(); //Should never happen
            return null;
        }
    }

    public Calendar readCalendar(String calendarString) {
        try {
            return (Calendar) xsd2java_dt_conv.convertSimpleType(calendarString);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static class SerializeEntityWrapper implements Entity {
        public static final long DEFAULT_CHECKPOINT_TIME = 0;
        public static final boolean DEFAULT_WAS_DELETED = false;
        public Entity entity = null;
        public boolean wasDeleted = DEFAULT_WAS_DELETED;
        public long checkpointTime = DEFAULT_CHECKPOINT_TIME;
        
        public String getExtId() {
            return entity.getExtId();
        }

        public long getId() {
            return entity.getId();
        }

        public void start(Handle handle) {
            throw new RuntimeException("unimplemented");
        }

        public boolean isLoadedFromCache() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getType() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setPropertyValue(String name, Object value) throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object getPropertyValue(String name) throws NoSuchFieldException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setLoadedFromCache() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
