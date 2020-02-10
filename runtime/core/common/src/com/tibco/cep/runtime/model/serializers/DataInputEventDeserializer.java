/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.model.serializers;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.model.serializers.io.stream.SafeDataInputStream;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.xml.data.primitive.ExpandedName;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 19, 2006
 * Time: 8:11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataInputEventDeserializer implements EventDeserializer {
    protected boolean error=false;
    protected String msg;
    protected long id;
    protected String extId;
    protected TypeManager typeManager;
    protected boolean hasSchemaChanged=false;
    protected EventSerializer.EventMigrator migrator;
    protected int typeId;

    protected DataInput buf;
    /**
     *
     * @param buf
     */
    public DataInputEventDeserializer(DataInput buf, TypeManager typeManager) {
        this.buf=buf;
        this.typeManager=typeManager;
        if (this.typeManager == null) {
            try {
                this.typeManager= CacheClusterProvider.getInstance().getCacheCluster().getRuleServiceProvider().getTypeManager();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            this.typeId=buf.readInt();
            this.id = buf.readLong();
            if (buf.readBoolean()) {
                this.extId= buf.readUTF();
            }
        } catch (Exception e) {
            this.msg =e.getMessage();
            error=true;
            throw new RuntimeException(e);
        }
    }
    public DataInputEventDeserializer(DataInput buf, TypeManager typeManager, EventSerializer.EventMigrator migrator) throws IOException{
        this(buf, typeManager);
        this.hasSchemaChanged=true;
        this.migrator=migrator;
    }

    /**
     *
     * @return
     */
    public boolean hasSchemaChanged() {
        return hasSchemaChanged;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 
     * @return
     */
    public EventSerializer.EventMigrator getEventMigrator() {
        return migrator;
    }

    /**
     *
     * @return
     */
    public long startEvent() {
        return id;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getTypeId() {
        return typeId;
    }

    /**
     *
     * @return
     */
    public String getExtId() {
        return extId;
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
     * @param propertyIndex
     * @return
     */
    public boolean startProperty(String propertyName, int propertyIndex) {
        try {
            return _startProperty();

        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    protected boolean _startProperty() throws IOException {
    	return buf.readBoolean();
    }
    
    /**
     *
     */
    public void endProperty() {
        _endProperty();
    }
    
    protected void _endProperty() {
    	
    }

    /**
     *
     * @param s
     */
    public String getStringProperty() {
        try {
            if (buf.readBoolean()) {
                if (buf instanceof SafeDataInputStream) {
                    return ((SafeDataInputStream) buf).readSafeUTF();
                }
                else {
                return buf.readUTF();
                }
            } else {
                return null;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param i
     */
    public int getIntProperty() {
        try {
            return buf.readInt();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param b
     */
    public boolean getBooleanProperty() {
        try {
            return buf.readBoolean();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param l
     */
    public long getLongProperty() {
        try {
            return buf.readLong();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
    *
    * @param l
    */
   public long getEntityRefPropertyAsLong() {
       try {
           return buf.readLong();
       } catch (IOException ex) {
           error=true;
           msg= ex.getMessage();
           throw new RuntimeException(msg);
       }
   }
   
   /**
   *
   * @param l
   */
  public ConceptOrReference getEntityRefProperty() {
      try {
          return new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong());
      } catch (IOException ex) {
          error=true;
          msg= ex.getMessage();
          throw new RuntimeException(msg);
      }
  }
  
    /**
     *
     * @param d
     */
    public double getDoubleProperty() {
        try {
            return buf.readDouble();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param time
     */
    public long getDateTimePropertyDate() {
        try {
            return buf.readLong();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param tz
     */
    public String getDateTimePropertyTimeZone() {
        try {
            if (buf.readBoolean()) {
                return buf.readUTF();
            } else {
                return null;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @return
     */
    public Calendar getDateTimeProperty() {
        long time= getDateTimePropertyDate();
        String tz= getDateTimePropertyTimeZone();
        if (tz != null) {
            GregorianCalendar gc= new java.util.GregorianCalendar(java.util.TimeZone.getTimeZone(tz));
            gc.setTimeInMillis(time);
            return gc;
        }
        return null;
    }

    /**
     *
     * @param eventType
     * @return
     */
    public Object getPayload(ExpandedName eventType) {
    	Object[] result = readPayload();
    	if(result == null) return null;
    	return makePayload(eventType, result);
    }
    
    protected Object[] readPayload() {
    	try {
            if (buf.readBoolean()) {
                int typeId = buf.readInt();
                int len = buf.readInt();
                byte [] b = new byte[len];
                buf.readFully(b);
                return new Object[]{typeId, b};
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }
    
    protected Object makePayload(ExpandedName eventType, Object[] payloadRec) {
    	try {
    		TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(eventType);
    		return PayloadFactoryImpl.createPayload(descriptor, (int)payloadRec[0], (byte[])payloadRec[1]);
    	} catch (Exception ex) {
    		error=true;
    		msg= ex.getMessage();
    		throw new RuntimeException(ex);
    	}
    }

    public final static int getTypeId (DataInputStream buf) throws IOException{
        buf.reset();
        return buf.readInt();
    }


}

