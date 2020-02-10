/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.tangosol._retired_;

import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.ImplSerializerHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.SimpleSerializerHelper;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.DefaultSerializer;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 21, 2006
 * Time: 1:51:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTangosolSerializer extends DefaultSerializer {

    private static HashMap instances = new HashMap();

    public DefaultTangosolSerializer(RuleServiceProvider rsp) {
        super(rsp);
        instances.put(rsp.getName(), this);
    }

    public static DefaultSerializer getInstance(String rspName) {
        return (DefaultSerializer) instances.get(rspName);
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return rsp;
    }

    public DataOutput serialize(DataOutput output, Object obj) {

        DataOutput dout = null;

        if (output == null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            dout = null;
            dout = new DataOutputStream(out);
        } else
            dout = output;

        if (obj instanceof Entity) {
            Entity e = (Entity) obj;

            try {
                dout.writeLong(e.getId());
                if (e.getExtId() != null) {
                    dout.writeBoolean(true);
                    dout.writeUTF(e.getExtId());
                } else
                    dout.writeBoolean(false);
                dout.writeUTF(e.getClass().getName());

                if (e instanceof SimpleEvent) {
                    SimpleEvent evt = (SimpleEvent) e;
                    String[] props = evt.getPropertyNames();
                    com.tibco.cep.designtime.model.event.Event designTimeEvent = (com.tibco.cep.designtime.model.event.Event) rsp.getProject().getOntology().getEntity(evt.getExpandedName());
                    for (int i = 0; i < props.length; i++) {
                        Object prop = evt.getProperty(props[i]);
                        RDFPrimitiveTerm term = designTimeEvent.getPropertyType(props[i]);
                        serializeEventProperty(prop, term, dout);
                    }
                } else if (e instanceof TimeEvent) {
                    TimeEvent te = (TimeEvent) e;
                    if (!te.isRepeating()) {
                        dout.writeLong(te.getScheduledTimeMillis());
                        String closure = te.getClosure();
                        if (closure != null) {
                            dout.writeBoolean(true);
                            dout.writeUTF(closure);
                        } else
                            dout.writeBoolean(false);
                    }
                } else if (e instanceof Concept) {
                    Concept cinst = (Concept) e;
                    Property[] props = cinst.getProperties();
                    for (int i = 0; i < props.length; i++) {
                        Property p = props[i];
                        if (p == null)
                            dout.writeBoolean(false);
                        else {
                            dout.writeBoolean(true);
                            if (p instanceof PropertyAtomImpl)
                                ImplSerializerHelper.serialize((PropertyAtomImpl) p, dout);
                            else if (p instanceof PropertyArrayImpl)
                                ImplSerializerHelper.serialize((PropertyArrayImpl) p, dout);
                            else if (p instanceof PropertyAtomSimple)
                                SimpleSerializerHelper.serialize((PropertyAtomSimple) p, dout);
                            else if (p instanceof PropertyArraySimple)
                                SimpleSerializerHelper.serialize((PropertyArraySimple) p, dout);
                        }
                    }

                    if (((ConceptImpl) cinst).sm != null) {
                        dout.writeBoolean(true);
                        Property p = ((ConceptImpl) cinst).getTransitionStatuses();
                        ImplSerializerHelper.serialize((PropertyArrayImpl) p, dout);
                        p = ((ConceptImpl) cinst).getMachineClosed();
                        SimpleSerializerHelper.serialize((PropertyAtomSimple) p, dout);
                    } else
                        dout.writeBoolean(false);
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
            }

        } else
            try {
                serialize(obj, dout);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }

        return dout;
    }

    public Object deserialize(DataInput input, Object obj) {
        if (obj == null) {
            try {
                long id = input.readLong();
                String extId = null;
                if (input.readBoolean()) {
                    extId = input.readUTF();
                }
                Class clz = Class.forName(input.readUTF(), true, clzLoader);
                if (TimeEvent.class.isAssignableFrom(clz)) {
                    Constructor cons = clz.getConstructor(new Class[]{long.class});
                    obj = cons.newInstance(new Object[]{new Long(id)});
                } else {
                    Constructor cons = clz.getConstructor(new Class[]{long.class, String.class});
                    obj = cons.newInstance(new Object[]{new Long(id), extId});
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (obj instanceof SimpleEvent) {
            SimpleEvent evt = (SimpleEvent) obj;
            String[] props = evt.getPropertyNames();
            com.tibco.cep.designtime.model.event.Event designTimeEvent = (com.tibco.cep.designtime.model.event.Event) rsp.getProject().getOntology().getEntity(evt.getExpandedName());
            for (int i = 0; i < props.length; i++) {
                try {
                    if (input.readBoolean()) {
                        RDFPrimitiveTerm term = designTimeEvent.getPropertyType(props[i]);
                        Object prop = deserializeEventProperty(term, input);
                        evt.setProperty(props[i], prop);
                    } else
                        evt.setProperty(props[i], (String) null);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else if (obj instanceof TimeEvent) {
            TimeEvent te = (TimeEvent) obj;
            try {
                te.setScheduledTime(input.readLong());
                if (input.readBoolean())
                    te.setClosure(input.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        } else if (obj instanceof ConceptImpl) {
            ConceptImpl cinst = (ConceptImpl) obj;
            Property[] props = cinst.getProperties();
            for (int i = 0; i < props.length; i++) {
                Property p = props[i];
                try {
                    if (input.readBoolean()) {
                        if (p instanceof PropertyAtomImpl)
                            ImplSerializerHelper.deserialize((PropertyAtomImpl) p, input);
                        else if (p instanceof PropertyArrayImpl)
                            ImplSerializerHelper.deserialize((PropertyArrayImpl) p, input);
                        else if (p instanceof PropertyAtomSimple)
                            SimpleSerializerHelper.deserialize((PropertyAtomSimple) p, input);
                        else if (p instanceof PropertyArraySimple)
                            SimpleSerializerHelper.deserialize((PropertyArraySimple) p, input);

                    } else {
//                        cinst.evictProperty(p.getName());  //todo - why evict?                        
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                }
            }

            try {
                if (input.readBoolean()) {
                    Property p = cinst.getTransitionStatuses();
                    ImplSerializerHelper.deserialize((PropertyArrayImpl) p, input);
                    p = cinst.getMachineClosed();
                    SimpleSerializerHelper.deserialize((PropertyAtomSimple) p, input);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return obj;
    }


}
