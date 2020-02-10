package com.tibco.be.oracle.serializers;

import java.util.Calendar;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.OracleLOBManager;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleEventMap;
import com.tibco.be.oracle.impl.OracleTimeEventMap;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.EventSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 6, 2009
 * Time: 9:40:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEventSerializer_V2 implements EventSerializer {
    public boolean error=false;
    public String msg;

    OracleEventMap entityDescription;
    Object[] attributes;
    int cur=0;
    int pIndex=0;
    PropertyMap curDesc;
    OracleConnection oracle;
    String qualifier=null;
    long lastTime=0;
    OraclePreparedStatement stmt;
    int start_index;
    boolean useExplicitTemporaryBlobs;

    public OracleEventSerializer_V2(OracleEventMap entityDescription,
                                    OraclePreparedStatement stmt,
                                    int start_index,
                                    OracleConnection oracle, String qual, boolean useExplicitTemporaryBlobs) {
        this.entityDescription=entityDescription;
        this.stmt=stmt;
        this.start_index=start_index;
        this.oracle=oracle;
        this.qualifier=qual;
        this.useExplicitTemporaryBlobs=useExplicitTemporaryBlobs;
    }

    /**
     *
     * @return
     */
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),oracle, attributes);
        //return s.getOracleAttributes();
        return null;
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
                stmt.setOracleObject(start_index, new oracle.sql.NUMBER(key));
                if (extKey != null) {
                    stmt.setOracleObject(start_index+1, new oracle.sql.CHAR(extKey,null));
                } else {
                    stmt.setNull(start_index+1, java.sql.Types.VARCHAR);
                }
                stmt.setOracleObject(start_index+2, new oracle.sql.CHAR("C",null));
                stmt.setOracleObject(start_index+3, new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis()))); // Place Holder for state information);
                if (!(entityDescription instanceof OracleTimeEventMap)) {
                    stmt.setOracleObject(start_index+4,new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis()))); // Place Holder for state information
                    stmt.setOracleObject(start_index+5,new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis()))); // Place Holder for state information
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
                if (!isSet) {
                    if (curDesc.getSQLTypeCode() == java.sql.Types.STRUCT) {
                        stmt.setNull(start_index+cur, curDesc.getSQLTypeCode(), curDesc.getTypeDescriptorName());
                    } else {
                        stmt.setNull(start_index+cur, curDesc.getSQLTypeCode());
                    }
                }
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
                if (s != null) {
                    if (curDesc.getSQLTypeCode() == java.sql.Types.CLOB) {
                        if (useExplicitTemporaryBlobs) {
                            if (s.length() > 1024) {
                                CLOB clob = (CLOB)OracleLOBManager.createCLOB(oracle, s);
                                stmt.setOracleObject(start_index+cur, clob);
                            } else {
                                stmt.setStringForClob(start_index+cur, s);
                            }
                        } else {
                            //oracleAtom= new CLOB(oracle, s.getBytes());
                            stmt.setStringForClob(start_index+cur, s);
                        }
                    } else {
                        stmt.setOracleObject(start_index+cur,new oracle.sql.CHAR(s,null) );
                        //oracleAtom= new oracle.sql.CHAR(s,null);
                    }
                } else {
                    stmt.setNull(start_index+cur, curDesc.getSQLTypeCode());
                }
                //attributes[cur]= oracleAtom;
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
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
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
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
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
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
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
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
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
                if (payload != null) {
                    byte [] bytes=((EventPayload)payload).toBytes();
                    if (useExplicitTemporaryBlobs) {
                        if (bytes.length > 1024) {
                            BLOB blob = (BLOB)OracleLOBManager.createBLOB(oracle, bytes);
                            stmt.setOracleObject(start_index+6, blob);
                        } else {
                            stmt.setBytesForBlob(start_index+6, bytes);
                        }
                    } else {
                        stmt.setBytesForBlob(start_index+6, bytes);
                    }
                } else {
                    stmt.setNull(start_index+6, java.sql.Types.BLOB);
                }
                //attributes[6]= oracleAtom;
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
                    stmt.setOracleObject(start_index+cur, oracleAtom);
                } else {
                    stmt.setNull(start_index+cur, curDesc.getSQLTypeCode(), curDesc.getTypeDescriptor().getName());
                }
                //attributes[cur++]= oracleAtom;
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
                    stmt.setOracleObject(start_index+cur, oracleAtom);
                } else {
                    stmt.setNull(start_index+cur, curDesc.getSQLTypeCode(), curDesc.getTypeDescriptor().getName());
                }
                //attributes[cur++]= oracleAtom;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void releaseLobTempSpace() {
    }
}
