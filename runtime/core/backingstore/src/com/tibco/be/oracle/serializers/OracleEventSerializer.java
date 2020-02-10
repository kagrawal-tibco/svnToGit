package com.tibco.be.oracle.serializers;

import java.io.OutputStream;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import oracle.jdbc.OracleConnection;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleEventMap;
import com.tibco.be.oracle.impl.OracleTimeEventMap;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.EventSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 23, 2006
 * Time: 2:39:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEventSerializer implements EventSerializer {
    public boolean error=false;
    public String msg;

    private ArrayList<BLOB> blobs = null;
    private ArrayList<CLOB> clobs = null;

    OracleEventMap entityDescription;
    Object [] attributes;
    int cur=0;
    int pIndex=0;
    PropertyMap curDesc;
    OracleConnection oracle;
    String qualifier=null;
    long lastTime=0;

    public OracleEventSerializer(OracleEventMap entityDescription, OracleConnection oracle, String qual, boolean useExplicitTemporaryBlobs) {
        this.entityDescription=entityDescription;
        this.oracle=oracle;
        this.qualifier=qual;
        if (useExplicitTemporaryBlobs) {
            this.blobs = new ArrayList<BLOB>();
            this.clobs = new ArrayList<CLOB>();
        }
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
     * @param clz
     * @param key
     * @param extKey
     * @param state
     */
    public void startEvent(Class clz, long key, String extKey, int state) {
        try {
            if (!error) {
                attributes = new Object[entityDescription.getTypeDescriptor().getLength()];
                attributes[0] = new oracle.sql.NUMBER(key);
                if (extKey != null) {
                    attributes[1] = new oracle.sql.CHAR(extKey,null);
                } else {
                    attributes[1]=null;
                }
                attributes[2]= new oracle.sql.CHAR("C", null); // Place Holder for state information
                attributes[3]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
                if (!(entityDescription instanceof OracleTimeEventMap)) {
                    attributes[4]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
                    attributes[5]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }


    public void endEvent() {

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
    public void startProperty(String propertyName, int propertyIndex, boolean isSet) {
        try {
            if (!error) {
                curDesc= entityDescription.getPropertyMap(pIndex);
                cur=curDesc.getColumnIndex();
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
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
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                if (s != null) {
                    if (curDesc.getSQLTypeCode() == java.sql.Types.CLOB) {
                        if (clobs != null) {
                            CLOB clob= CLOB.createTemporary(oracle,false,CLOB.DURATION_SESSION);
                            clob.open(CLOB.MODE_READWRITE);
                            Writer writer= clob.setCharacterStream(0);
                            writer.write(s);
                            //os.write(bytes);
                            writer.flush();
                            writer.close();
                            clob.close();
                            oracleAtom= clob;
                            clobs.add(clob);
                        } else {
                            oracleAtom= new CLOB(oracle, s.getBytes());
                        }
                    } else {
                        oracleAtom= new oracle.sql.CHAR(s,null);
                    }
                }
                attributes[cur]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param i
     */
    public void writeIntProperty(int i) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(i);
                attributes[cur]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param b
     */
    public void writeBooleanProperty(boolean b) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(b?1:0);
                attributes[cur]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param l
     */
    public void writeLongProperty(long l) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(l);
                attributes[cur]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void writeEntityRefProperty(ConceptOrReference ref) {
        writeEntityRefProperty(ref.getId());
    }

    /**
    *
    * @param l
    */
   public void writeEntityRefProperty(long l) {
       try {
           if (!error) {
               Datum [] oracleAtom= new Datum[1];
               oracleAtom[0]= new oracle.sql.NUMBER(l);
               attributes[cur]=
                   new oracle.sql.STRUCT(StructDescriptor.createDescriptor(qualifier + "T_ENTITY_REF", oracle), oracle, oracleAtom);;
           }
       } catch (Exception ex) {
           error=true;
           msg= ex.toString() + ex.getMessage();
           throw new RuntimeException(ex);
       }
   }

    /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                if (Double.isNaN(d)) {
                    oracleAtom= new oracle.sql.NUMBER(0.00);
                } else {
                    oracleAtom= new oracle.sql.NUMBER(d);
                }
                attributes[cur]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param time
     */
    public void writeDateTimePropertyDate(long time) {
        try {
            if (!error) {
                lastTime=time;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void writePayload(Object payload) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                if (payload != null) {
                    byte [] bytes=((EventPayload)payload).toBytes();
                    if (blobs != null) {
                        BLOB blob = BLOB.createTemporary(oracle,true,BLOB.DURATION_SESSION);
                        OutputStream os= blob.setBinaryStream(0);
                        os.write(bytes);
                        os.close();
                        oracleAtom= blob;
                        blobs.add(blob);
                    } else {
                        oracleAtom= new BLOB(oracle, bytes);
                    }

//                    if (payload instanceof XiNodePayload) {
//                        String xml=((XiNodePayload) payload).toString();
//                        //byte [] bytes=((XiNodePayload)payload).toBytes();
//
//                        CLOB clob= CLOB.createTemporary(oracle,false,CLOB.DURATION_SESSION);
//                        clob.open(CLOB.MODE_READWRITE);
//
//                        Writer writer= clob.setCharacterStream(0);
//
//                        writer.write(xml);
//                        //os.write(bytes);
//                        writer.flush();
//                        writer.close();
//                        clob.close();
//                        oracleAtom= clob;
//                    } else {
//                        byte [] bytes=((ObjectPayload)payload).toBytes();
//                        BLOB clob= BLOB.createTemporary(oracle,true,BLOB.DURATION_SESSION);
//                        OutputStream os= clob.setBinaryStream(0);
//                        os.write(bytes);
//                        os.close();
//                        oracleAtom= clob;
//                    }
                }
                attributes[6]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param tz
     */
    public void writeDateTimePropertyTimeZone(String tz) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                Datum[] atts = new Datum[2];
                if (tz != null) {
                    atts[0]= new oracle.sql.DATE(new java.sql.Date(lastTime));
                    atts[1] = new oracle.sql.CHAR(tz,null);
                    oracleAtom=new oracle.sql.STRUCT((StructDescriptor) curDesc.getTypeDescriptor(), oracle, atts);
                    lastTime=0;
                }
                attributes[cur++]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param cal
     */
    public void writeDateTimePropertyCalendar(Calendar cal) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                Datum[] atts = new Datum[2];
                if (cal != null) {
                    atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(cal.getTimeInMillis()));
                    atts[1] = new oracle.sql.CHAR(cal.getTimeZone().getID(),null);
                    oracleAtom=new oracle.sql.STRUCT((StructDescriptor) curDesc.getTypeDescriptor(), oracle, atts);
                }
                attributes[cur++]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void releaseLobTempSpace() {
        if (blobs != null) {
            for (BLOB blob : blobs) {
                try {
                    blob.freeTemporary();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (clobs != null) {
            for (CLOB clob : clobs) {
                try {
                    clob.freeTemporary();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
