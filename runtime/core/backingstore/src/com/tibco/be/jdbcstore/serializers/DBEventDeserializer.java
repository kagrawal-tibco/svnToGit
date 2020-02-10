package com.tibco.be.jdbcstore.serializers;

import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.tibco.be.jdbcstore.DateTimeValueTuple;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.be.jdbcstore.impl.DBEventMap;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;

public class DBEventDeserializer implements EventDeserializer {
    boolean error=false;
    String msg;

    DBEventMap entityMap;
    Object [] attributes;
    int cur=0;
    int curObjIndex=0;
    int pIndex=0;
    //PropertyMap curDesc;
    DBEntityMap.DBFieldMap curFieldMap;
    Connection conn;
    RuleServiceProvider rsp;
    int _systemFieldOffset;

    /**
     *
     * @param buf
     */
    public DBEventDeserializer(RuleServiceProvider rsp, DBEventMap entityMap, Connection conn, Object[] attributes) {
        this.entityMap=entityMap;
        this.conn=conn;
        this.attributes=attributes;
        this.rsp=rsp;
        this._systemFieldOffset = attributes.length - entityMap.getFieldCount();
    }

    /**
     *
     * @return
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),  oracle, attributes);
        return s.getOracleAttributes();
    }
     */

    public Object[] getAttributes() throws Exception {
        return attributes;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    Object currentDatum() throws Exception{
        return attributes[curObjIndex];
    }

    public boolean hasSchemaChanged() {
        return false;
    }

    public EventSerializer.EventMigrator getEventMigrator() {
        return null;
    }

    /**
     *
     */
    public long startEvent() {
        try {
            cur=_systemFieldOffset; // FIX THIS - This I believe is dummy
            return ((Long) attributes[0]).longValue();
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endEvent() {

    }

    /**
     *
     * @return
     */
    public String getExtId() {
        String extId= (String) attributes[1];
        return extId;
    }

    /**
     *
     * @return
     */
    public long getId() {
        try {
            Long id= (Long) attributes[0];
            if (id != null) {
                return id.longValue();
            }
            return 0L;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return
     */
    public int getType() {
        return EventSerializer.TYPE_STREAM;
    }

    /**
     *
     * @param propertyName
     * @param propertyIndex
     * @param isSet
     */
    public boolean startProperty(String propertyName, int propertyIndex) {
        try {
            curFieldMap= entityMap.getUserFieldMap(pIndex);
            //cur=curFieldMap.tableFieldIndex + _systemFieldOffset;
            cur = curFieldMap.tableFieldIndex;
            curObjIndex = curFieldMap.dataObjectFieldIndex;
            return (attributes[curObjIndex] != null);
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     */
    public void endProperty() {
        curFieldMap=null;
        ++pIndex;
    }

    /**
     *
     * @param s
     */
    public String getStringProperty() {
        try {
            Object ret=currentDatum();
            if (ret != null) {
                return (String) ret;
                /*
                if (ret instanceof oracle.sql.CHAR) {
                    oracle.sql.CHAR tmp=(oracle.sql.CHAR) currentDatum();
                    return tmp.stringValue();
                } else if (ret instanceof oracle.sql.CLOB) {
                    CLOB clob= (CLOB) ret;
                    String str ="";
                    StringBuilder clobcontent = new StringBuilder();
                    BufferedReader re = new BufferedReader(clob.getCharacterStream());
                    while ((str = re.readLine()) != null) {
                        clobcontent.append(str);
                    }
                    clob.getCharacterStream().close();
                    return  clobcontent.toString();
                }
                */
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param i
     */
    public int getIntProperty() {
        try {
            Integer ret=(Integer) currentDatum();
            if (ret != null) {
                return ret.intValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param b
     */
    public boolean getBooleanProperty() {
        try {
            Boolean ret=(Boolean) currentDatum();
            if (ret != null) {
                return ret.booleanValue();
            } else {
                return false;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param l
     */
    public long getLongProperty() {
        try {
            Long ret=(Long) currentDatum();
            if (ret != null) {
                return ret.longValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public ConceptOrReference getEntityRefProperty() {
        try {
            Long ret=(Long) currentDatum();
            if (ret != null) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(ret.longValue());
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public long getEntityRefPropertyAsLong() {
        try {
            Long ret=(Long) currentDatum();
            if (ret != null) {
                return ret.longValue();
            } else {
                return 0L;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param d
     */
    public double getDoubleProperty() {
        try {
            Double ret=(Double) currentDatum();
            if (ret != null) {
                return ret.doubleValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return
     */
    public Calendar getDateTimeProperty() {
        try {
            DateTimeValueTuple ret=(DateTimeValueTuple) currentDatum();
            if (ret != null) {
                String tz=ret.tz;
                long time=ret.ts.getTime();
                GregorianCalendar gc= new java.util.GregorianCalendar(java.util.TimeZone.getTimeZone(tz));
                gc.setTimeInMillis(time);
                return gc;
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public Object getPayload(ExpandedName eventType) {
        try {
            cur=6;
            curObjIndex=6;
            byte[] bytes = (byte[]) currentDatum();
            if (bytes != null) {
                return rsp.getTypeManager().getPayloadFactory().createPayload(eventType, bytes);
            }
            return null;
            /*
            Datum datum= currentDatum();
            if (datum instanceof BLOB) {
                BLOB blob = (BLOB) datum;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is = blob.getBinaryStream();
                int bufferSize = blob.getBufferSize();
                byte[] buffer = new byte[bufferSize];
                int bytesRead = 0;
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                byte[] buf = baos.toByteArray();
                //BLOB.freeTemporary(blob);
                //blob.close();
                return rsp.getTypeManager().getPayloadFactory().createPayload(eventType, buf);                
            } else if (datum instanceof CLOB) {
                CLOB clob= (CLOB) datum;
                String str ="";
                StringBuilder clobcontent = new StringBuilder();
                BufferedReader re = new BufferedReader(clob.getCharacterStream());
                while ((str = re.readLine()) != null) {
                    clobcontent.append(str);
                }
                clob.getCharacterStream().close();
                String xml =  clobcontent.toString();
                //CLOB.freeTemporary(clob);
                //clob.close();
                return rsp.getTypeManager().getPayloadFactory().createPayload(eventType, xml);
            } else {
                return null;
            }
            */
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }
}
