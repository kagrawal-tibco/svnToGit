/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.model.serializers;


import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.serializers.io.stream.SafeDataOutputStream;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 19, 2006
 * Time: 8:09:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataOutputEventSerializer implements EventSerializer {
    protected boolean error=false;
    protected String msg;
    protected int typeId;

    protected DataOutput buf;
    /**
     *
     * @param buf
     */
    public DataOutputEventSerializer(DataOutput buf, int typeId) {
        this.buf=buf;
        this.typeId=typeId;
    }

    public DataOutputEventSerializer(DataOutput buf, Class entityClz) {
        try {
            this.buf=buf;
            typeId= CacheClusterProvider.getInstance().getCacheCluster().getMetadataCache().getTypeId(entityClz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param clz
     * @param key
     * @param extKey
     * @param state
     */
    public void startEvent(Class clz, long key, String extKey, int state) {
        try {
            if (!error) {
                buf.writeInt(typeId);
                buf.writeLong(key);
                if (extKey != null) {
                    buf.writeBoolean(true);
                    buf.writeUTF(extKey);
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }


    public void endEvent() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @return
     */
    public int getType() {
        return EventSerializer.TYPE_STREAM;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @param propertyName
     * @param isSet
     */
    public void startProperty(String propertyName, int index, boolean isSet) {
        try {
            if (!error) {
                buf.writeBoolean(isSet);            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     */
    public void endProperty() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @param s
     */
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                if (s != null) {
                    buf.writeBoolean(true);
                    if (buf instanceof SafeDataOutputStream) {
                        ((SafeDataOutputStream) buf).writeSafeUTF(s);
                    }
                    else {
                    buf.writeUTF(s);
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param i
     */
    public void writeIntProperty(int i) {
        try {
            if (!error) {
                buf.writeInt(i);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param b
     */
    public void writeBooleanProperty(boolean b) {
        try {
            if (!error) {
                buf.writeBoolean(b);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param l
     */
    public void writeLongProperty(long l) {
        try {
            if (!error) {
                buf.writeLong(l);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
    *
    * @param l
    */
    public void writeEntityRefProperty(long l) {
    	writeLongProperty(l);
    }
    
    /**
    *
    * @param ref
    */
    public void writeEntityRefProperty(ConceptOrReference ref) {
    	writeEntityRefProperty(ref.getId());
    }
        
    /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d) {
        try {
            if (!error) {
                buf.writeDouble(d);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeDateTimePropertyCalendar(Calendar cal) {
        if (cal != null) {
            writeDateTimePropertyDate(cal.getTime().getTime());
            writeDateTimePropertyTimeZone(cal.getTimeZone().getID());
        }
    }

    /**
     *
     * @param time
     */
    public void writeDateTimePropertyDate(long time) {
        try {
            if (!error) {
                buf.writeLong(time);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param tz
     */
    public void writeDateTimePropertyTimeZone(String tz) {
        try {
            if (!error) {
                if (tz != null) {
                    buf.writeBoolean(true);
                    buf.writeUTF(tz);
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }

    }

    /**
     *
      * @param payload
     */
    public void writePayload(Object payload) {
        try {
            if (!error) {
                if (payload != null) {
                	EventPayload epl = (EventPayload) payload;
                	byte[] payloadBytes = epl.toBytes();
					if (payloadBytes != null && payloadBytes.length > 0) {
						buf.writeBoolean(true);
						int typeId = PayloadFactoryImpl.getPayloadTypeId(epl.getClass());
						buf.writeInt(typeId);
						buf.writeInt(payloadBytes.length);
						buf.write(payloadBytes);
					} else {
						buf.writeBoolean(false);
					}
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }
}

