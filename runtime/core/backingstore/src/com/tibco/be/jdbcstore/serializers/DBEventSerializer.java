package com.tibco.be.jdbcstore.serializers;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;

import com.tibco.be.jdbcstore.DateTimeValueTuple;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.be.jdbcstore.impl.DBEventMap;
import com.tibco.be.jdbcstore.impl.DBHelper;
import com.tibco.be.oracle.OracleLOBManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.EventSerializer;

public class DBEventSerializer implements EventSerializer {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBConceptSerializer.class);
    static final int SIMPLE_EVENT_OFFSET = 7;
    static final int TIME_EVENT_OFFSET = 9;
    static final int STATE_TIMEOUT_EVENT_OFFSET = 11;

    public boolean error=false;
    public String msg;

    //private ArrayList<BLOB> blobs = null;
    //private ArrayList<CLOB> clobs = null;

    DBEventMap eventMap;
    Object [] attributes;
    int cur=0;
    int curObjIndex=0;
    int pIndex=0;
    //PropertyMap curDesc;
    DBEntityMap.DBFieldMap curFieldMap;
    Connection conn;
    String qualifier=null;
    long lastTime=0;
    int _systemFieldOffset;
    int startIndex;
    PreparedStatement stmt;
    boolean useExplicitTemporaryBlobs;
    //int[] _systemFieldSqlTypes;

    public DBEventSerializer(DBEventMap eventMap, Connection conn, String qual, PreparedStatement stmt,
        int startIndex, boolean useExplicitTemporaryBlobs) {
        this.eventMap=eventMap;
        this.conn=conn;
        this.qualifier=qual;
        this.startIndex = startIndex;
        this.useExplicitTemporaryBlobs = useExplicitTemporaryBlobs;
        this.stmt = stmt;
        //this.blobs = new ArrayList<BLOB>();
        //this.clobs = new ArrayList<CLOB>();
    }

    /**
     *
     * @return
     * FIX THIS - Do we need this??
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(eventMap.getTypeDescriptor(), oracle, attributes);
        STRUCT s= new STRUCT(eventMap.getTypeDescriptor(oracle), oracle, attributes);
        return s.getOracleAttributes();
    }
     */

    public Object[] getAttributes() throws Exception {
        return attributes;
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
                int eventFieldCount = eventMap.getFieldCount();
                boolean isStateTimeOut = eventMap.isStateTimeOut();
                boolean isTimeEvent = eventMap.isTimeEvent();
                logger.log(Level.TRACE, "Event: %s has %d fields", clz.getName(), eventFieldCount);
                //attributes = new Object[eventMap.getTypeDescriptor().getLength()];
                //Add system fields based on the event type
                //not sure about the time event whether it's just time event or statemachine timeout event
                //payload is only for simple event I believe and it will occupy slot 6
                attributes = new Object[eventFieldCount];
                //_systemFieldSqlTypes = new int[eventFieldCount];
                attributes[0] = new Long(key); //new oracle.sql.NUMBER(key);
                //_systemFieldSqlTypes[0] = java.sql.Types.NUMERIC;
                stmt.setLong(startIndex + 0, key);
                if (extKey != null) {
                    attributes[1] = extKey; //new oracle.sql.CHAR(extKey,null);
                    //FIX THIS - should we use the call which includes the type parameter
                    stmt.setString(startIndex + 1, extKey);
                } else {
                    attributes[1] = null;
                    stmt.setNull(startIndex + 1, java.sql.Types.VARCHAR);
                }
                //_systemFieldSqlTypes[1] = java.sql.Types.VARCHAR;
                attributes[2] = "C"; //new oracle.sql.CHAR("C", null); // Place Holder for state information
                //_systemFieldSqlTypes[2] = java.sql.Types.CHAR;
                stmt.setString(startIndex + 2, "C");
                attributes[3] = new java.sql.Timestamp(System.currentTimeMillis());
                //_systemFieldSqlTypes[3] = java.sql.Types.TIMESTAMP;
                stmt.setTimestamp(startIndex + 3, (Timestamp) attributes[3]);
                // If this is a simple event
                if (!isTimeEvent) {
                    attributes[4]= new java.sql.Timestamp(System.currentTimeMillis());
                    attributes[5]= new java.sql.Timestamp(System.currentTimeMillis());
                    stmt.setTimestamp(startIndex + 4, (Timestamp) attributes[4]);
                    stmt.setTimestamp(startIndex + 5, (Timestamp) attributes[5]);
                    //_systemFieldSqlTypes[4] = java.sql.Types.TIMESTAMP;
                    //_systemFieldSqlTypes[5] = java.sql.Types.TIMESTAMP;
                }
                else {
                    //_systemFieldSqlTypes[4] = java.sql.Types.NUMERIC;
                    //_systemFieldSqlTypes[5] = java.sql.Types.NUMERIC;
                    //_systemFieldSqlTypes[6] = java.sql.Types.VARCHAR;
                    //_systemFieldSqlTypes[7] = java.sql.Types.NUMERIC;
                    //_systemFieldSqlTypes[8] = java.sql.Types.INTEGER;
                }
                if (isStateTimeOut) {
                    //_systemFieldSqlTypes[9] = java.sql.Types.NUMERIC;
                    //_systemFieldSqlTypes[10] = java.sql.Types.VARCHAR;
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
                curFieldMap = eventMap.getUserFieldMap(pIndex);
                cur=curFieldMap.tableFieldIndex;
                curObjIndex=curFieldMap.dataObjectFieldIndex;
                if (!isSet) {
                    stmt.setNull(startIndex + cur, curFieldMap.tableFieldSqlType);
                    if (curFieldMap.tableExtraFieldName1 != null) {
                        stmt.setNull(startIndex + cur + 1, java.sql.Types.VARCHAR);
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
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
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                if (s != null) {
                    //FIXME: This call will not work since runtime model can't have 'SqlType==CLOB'
                    if (curFieldMap.tableFieldSqlType == java.sql.Types.CLOB) {
                        byte[] bytes=s.getBytes();
                        if (useExplicitTemporaryBlobs) {
                            Blob blob = OracleLOBManager.createBLOB(conn, bytes);
                            stmt.setBlob(startIndex + cur, blob);
                            //Clob clob = OracleLOBManager.createCLOB(conn, s);
                            //stmt.setClob(startIndex + cur, clob);
                        } else {
                            DBHelper.setBlob(stmt, startIndex + cur, bytes);
                            //DBHelper.setClob(stmt, startIndex + cur, s);
                        }
                    } else {
                        stmt.setString(startIndex + cur, s);
                    }
                } else {
                    if (curFieldMap.tableFieldSqlType == java.sql.Types.CLOB) {
                        stmt.setNull(startIndex + cur, java.sql.Types.CLOB);
                    } else {
                        stmt.setNull(startIndex + cur, java.sql.Types.VARCHAR);
                    }
                }
                attributes[curObjIndex]=s;
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
                /*
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(i);
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
                 */
                attributes[curObjIndex] = new Integer(i);
                stmt.setObject(startIndex + cur, attributes[curObjIndex]);
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
                /*
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(b?1:0);
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
                 */
                attributes[curObjIndex] = new Integer(b ? 1 : 0);
                stmt.setObject(startIndex + cur, attributes[curObjIndex]);
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
                /*
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(l);
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
                */
                attributes[curObjIndex] = new Long(l);
                stmt.setObject(startIndex + cur, attributes[curObjIndex]);
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
               /*
               Datum [] oracleAtom= new Datum[1];
               oracleAtom[0]= new oracle.sql.NUMBER(l);
               attributes[cur]=
                   new oracle.sql.STRUCT(StructDescriptor.createDescriptor(qualifier + "T_ENTITY_REF", oracle), oracle, oracleAtom);;
               */
               attributes[curObjIndex] = new Long(l);
               stmt.setObject(startIndex + cur, attributes[curObjIndex]);
           }
        } catch (Exception ex) {
           error=true;
           msg=ex.toString() + ex.getMessage();
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
                /*
                Datum oracleAtom=null;
                if (Double.isNaN(d)) {
                    oracleAtom= new oracle.sql.NUMBER(0.00);
                } else {
                    oracleAtom= new oracle.sql.NUMBER(d);
                }
                stmt.setOracleObject(start_index+cur, oracleAtom);
                //attributes[cur]= oracleAtom;
                */
                if (Double.isNaN(d)) {
                    attributes[curObjIndex] = new Double(0.0);
                } else {
                    attributes[curObjIndex] = new Double(d);
                }
                stmt.setObject(startIndex + cur, attributes[curObjIndex]);
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
            msg=ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void writePayload(Object payload) {
        try {
            if (!error) {
                if (payload != null) {
                    byte[] bytes=((EventPayload)payload).toBytes();
                    if (useExplicitTemporaryBlobs) {
                        Blob blob = OracleLOBManager.createBLOB(conn, bytes);
                        stmt.setBlob(startIndex + 6, blob);
                    } else {
                        DBHelper.setBlob(stmt, startIndex + 6, bytes);
                    }
                } else {
					if (RDBMSType.sRuntimeType == RDBMSType.POSTGRES) {
						stmt.setNull(startIndex + 6, java.sql.Types.BINARY);
					} else {
						stmt.setNull(startIndex + 6, java.sql.Types.BLOB);
					}
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
                DateTimeValueTuple dtt = new DateTimeValueTuple(new Timestamp(lastTime), tz);
                attributes[curObjIndex++] = dtt;
                lastTime = 0;
                if (tz != null) {
                    stmt.setObject(startIndex + cur, dtt.ts);
                    stmt.setObject(startIndex + cur + 1, dtt.tz);
                }
                else {
                    stmt.setNull(startIndex + cur, java.sql.Types.TIMESTAMP);
                    stmt.setNull(startIndex + cur + 1, java.sql.Types.VARCHAR);
                }
                cur += 2;
                /*
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
                */
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
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
                if (cal != null) {
                    DateTimeValueTuple dtt = new DateTimeValueTuple(new Timestamp(cal.getTimeInMillis()), cal.getTimeZone().getID());
                    attributes[curObjIndex++] = dtt;
                    stmt.setObject(startIndex + cur, dtt.ts);
                    stmt.setObject(startIndex + cur + 1, dtt.tz);
                } else {
                    stmt.setNull(startIndex + cur, java.sql.Types.TIMESTAMP);
                    stmt.setNull(startIndex + cur + 1, java.sql.Types.VARCHAR);
                }
                cur += 2;
                /*
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
                */
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.toString() + ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void releaseLobTempSpace() {
    }
}
