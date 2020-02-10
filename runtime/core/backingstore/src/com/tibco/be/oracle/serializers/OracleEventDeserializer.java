package com.tibco.be.oracle.serializers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import oracle.jdbc.OracleConnection;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.STRUCT;

import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleEventMap;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 23, 2006
 * Time: 2:43:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEventDeserializer implements EventDeserializer {
    boolean error=false;
    String msg;

    OracleEventMap entityDescription;
    Object [] attributes;
    int cur=0;
    int pIndex=0;
    PropertyMap curDesc;
    OracleConnection oracle;
    RuleServiceProvider rsp;

    /**
     *
     * @param buf
     */
    public OracleEventDeserializer(RuleServiceProvider rsp, OracleEventMap entityDescription, OracleConnection oracle, Datum[] attributes) {
        this.entityDescription=entityDescription;
        this.oracle=oracle;
        this.attributes=attributes;
        this.rsp=rsp;

    }

    /**
     *
     * @return
     */
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),oracle, attributes);
        return s.getOracleAttributes();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    Datum currentDatum() throws Exception{
        return (Datum) attributes[cur];
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
            cur=5;
            return ((oracle.sql.NUMBER) attributes[0]).longValue();
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
        oracle.sql.CHAR extId= (oracle.sql.CHAR) attributes[1];
        if (extId != null) {
            return extId.stringValue();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public long getId() {
        try {
            oracle.sql.NUMBER id= (oracle.sql.NUMBER) attributes[0];
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
            curDesc= entityDescription.getPropertyMap(pIndex);
            cur= curDesc.getColumnIndex();
            return (attributes[cur] != null);
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
        curDesc=null;
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
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
        return null;
    }

    /**
     *
     * @param i
     */
    public int getIntProperty() {
        try {
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
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
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
            if (ret != null) {
                return (ret.intValue() == 0)?false:true;
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
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
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
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                return new com.tibco.cep.runtime.model.element.impl.Reference(((oracle.sql.NUMBER)attributes[0]).longValue());
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
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                return ((oracle.sql.NUMBER)attributes[0]).longValue();
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
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
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
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                String tz=((oracle.sql.CHAR)attributes[1]).stringValue();
                long time=((oracle.sql.TIMESTAMP)attributes[0]).timestampValue().getTime();
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
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }
}
