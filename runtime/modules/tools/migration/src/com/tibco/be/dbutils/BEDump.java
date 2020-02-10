package com.tibco.be.dbutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.OperationStatus;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.data.primitive.values.XsDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Feb 14, 2005
 * Time: 2:04:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEDump {

    protected RepoAccess repo;
    protected Environment bdbenv;
    protected SQLConnection sqlcnx;
    private HashMap propIndexToPropInfo;
    private HashMap tablenamesMap = null;
    private HashMap propIndexToPropTypeIndexMap = null;
    BEProperties beprops = null;
    Logger logger = null;

    static TypeRenderer java2xsd_dt_conv= XSDTypeRegistry.getInstance().foreignToNative(XsDateTime.class, GregorianCalendar.class);

    public BEDump(BEProperties _beprops, Logger _logger) throws Exception {
        beprops = _beprops;
        logger = _logger;
        String repoFile = _beprops.getProperty("tibco.repourl");
        if (repoFile == null) {
            this.logger.log(Level.FATAL, "No Repo specified");
            System.exit(1);
        }
        repo = new RepoAccess(_beprops, logger);
        String dbdir = _beprops.getProperty("be.dbutils.berkeley.dbenv");
        if(dbdir == null) {
            this.logger.log(Level.FATAL, "No Berkeley DB Directory specified");
            System.exit(1);
        }
        File envdir = new File(dbdir);
        bdbenv = new Environment(envdir, null);
        sqlcnx = new SQLConnection(logger);
        propIndexToPropInfo = new HashMap();

    }

    public void dump() throws Exception {
        this.logger.log(Level.INFO, "Dumping Berkeley DB data to external database");
        this.doDbDump();
        this.logger.log(Level.INFO, "Dumping Berkeley DB data to external database succeeded");
    }

    protected void doDbDump() throws Exception {
        if(DbUtils.USE_SHORT_TABLENAMES) {
            tablenamesMap = new HashMap();
            DbUtils.prepareTableNamesMap(sqlcnx, tablenamesMap);
        }

        dumpVersionTable();
        dumpConceptTable();
        dumpEventLog();
        dumpPropertyIndexTable();
        dumpPropertiesTable();

    }

    private void dumpVersionTable() throws DatabaseException, IOException, SQLException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream dataos = new DataOutputStream(bytes);
        Database versiontable = null;
        try
        {
           String dbStr = beprops.getString("be.engine.om.berkeleydb.beversiontable");
           versiontable = bdbenv.openDatabase(null, dbStr, null);
        } catch (Exception e) {
            this.logger.log(Level.FATAL, e, e.getMessage());
            System.exit(1);
        }

        dataos.writeUTF(DbUtils.BE_VERSION_RECORD);
        keyEntry.setData(bytes.toByteArray());
        versiontable.get(null, keyEntry, dataEntry, null);
        DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));

        String dbVersion = datais.readUTF();
        StringBuffer sql = new StringBuffer("INSERT INTO " + DbUtils.SQL_DB_VERSION_TABLE + " VALUES (");
        appendString(sql, dbVersion);
        sql.append(")\n");

        // Execute
        SQLConnection.DBResource res = sqlcnx.executeSQL(sql.toString());
        res.close();
    }

    protected void dumpConceptTable() throws DatabaseException, IOException, SQLException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database conceptable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.conceptable"), null);
        Cursor cursor = conceptable.openCursor(null, null);
        //conceptNameToID = new HashMap();
        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            dumpConceptRow(datais);
        }

        cursor.close();
        conceptable.close();
    }

    protected void dumpConceptRow(DataInputStream datais) throws IOException, SQLException {

        EntityHeader header = new EntityHeader(datais);

        // Form the SQL
        StringBuffer sql = new StringBuffer("INSERT INTO " + DbUtils.SQL_CONCEPTS_TABLE + " VALUES (" );
        if(header.className.equals(DbUtils.URIRECORD_CLASS_STRING))
            appendString(sql, header.className);
        else
            appendString(sql, DbUtils.EntityPathfromClass(header.className));

        sql.append(", ?, ?, ?, ? )\n");

        PreparedStatement stmt = sqlcnx.getConnection().prepareStatement(sql.toString());

        stmt.setLong(1, header.id);
        if(header.extId == null)
            stmt.setNull(2, Types.VARCHAR);
        else
            stmt.setString(2, header.extId);
        stmt.setInt(3, header.getConceptStatus());
        stmt.setLong(4, header.timeStamp);

        // Execute
        SQLConnection.DBResource res = sqlcnx.executePreparedStatement(stmt);
        res.close();
    }

    protected void dumpEventLog() throws Exception {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database eventstable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.eventslog"), null);
        Cursor cursor = eventstable.openCursor(null, null);

        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            dumpEventRow(datais);
        }
        cursor.close();
        eventstable.close();

    }

    protected void dumpEventRow(DataInputStream datais) throws Exception {

        PreparedStatement stmt = null;
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        EntityHeader header = new EntityHeader(datais);

        String tablename = null;
        Event e = null;
        if(header.className.equals(DbUtils.STATE_TIMEOUT_EVENT_CLASS_NAME)) { //StateTimeoutEvent
            tablename = DbUtils.SQL_STATE_TIMEOUT_TABLE;
        } else {
            String eventPath=DbUtils.EntityPathfromClass(header.className);
            e = repo.ontology().getEvent(eventPath);
            tablename = DbUtils.massageNameSpaceString(e.getNamespace()) + "$" + e.getName();
            if(DbUtils.USE_SHORT_TABLENAMES)
                tablename = (String) tablenamesMap.get(tablename); // Get the real table name.
        }
        //System.out.println(tablename);
        sql.append(tablename + " VALUES ( ?, ?, ?, ? ");
        if(header.className.equals(DbUtils.STATE_TIMEOUT_EVENT_CLASS_NAME)) { //StateTimeoutEvent
            sql.append(", ?, ?, ?, ?, ?)\n");
            stmt = sqlcnx.getConnection().prepareStatement(sql.toString());

            int index = setEventHeader(stmt, header);
            index = setTimeEventProperties(datais, stmt, index);
            //sm_id
            stmt.setLong(index++, datais.readLong());
            //property_name
            stmt.setString(index++, datais.readUTF());
        } else if(e.getType() == Event.SIMPLE_EVENT) { 
            Iterator propdefs = e.getUserProperties();
            while(propdefs.hasNext()) {
                propdefs.next();
                sql.append(", ?");
            }

            sql.append(")\n");

            stmt = sqlcnx.getConnection().prepareStatement(sql.toString());

            int index = setEventHeader(stmt, header);

            propdefs = e.getUserProperties();
            while(propdefs.hasNext()) {
                EventPropertyDefinition pd = (EventPropertyDefinition) propdefs.next();
                appendEventProperty(stmt, RDFTypes.getIndex(pd.getType()), datais, index++);
            }
            if(datais.readBoolean()) {
                dumpPayloadRow(header.id,  datais);
            }
        } else if(e.getType() == Event.TIME_EVENT) {
            //scheduled time, closure, ttl
            sql.append(", ?, ?, ?)\n");

            stmt = sqlcnx.getConnection().prepareStatement(sql.toString());
                
            int index = setEventHeader(stmt, header);
            index = setTimeEventProperties(datais, stmt, index);
        }

        SQLConnection.DBResource res = sqlcnx.executePreparedStatement(stmt);
        res.close();
    }
    
    private static int setEventHeader(PreparedStatement stmt, EntityHeader header) throws SQLException {
        int index = 1;
        stmt.setLong(index++, header.id);
        setOptionalString(stmt, index++, header.extId);
        stmt.setInt(index++, header.getEventStatus());
        stmt.setLong(index++, header.timeStamp);
        return index;
    }

    private static int setTimeEventProperties(DataInputStream datais, PreparedStatement stmt, int index) throws SQLException, IOException {
        //scheduledTime
        stmt.setLong(index++, datais.readLong());
        //closure
        setOptionalString(stmt, index++, readOptionalString(datais));
        //ttl
        stmt.setLong(index++, datais.readLong());
        return index;
    }
    
    private static void setOptionalString(PreparedStatement stmt, int index, String str) throws SQLException {
        if(str == null) stmt.setNull(index, java.sql.Types.VARCHAR);
        else stmt.setString(index, str);
    }
    
    private static String readOptionalString(DataInputStream is) throws IOException {
        if(is.readBoolean()) {
            return is.readUTF();
        } else {
            return null;
        }
    }

    protected void dumpPropertyIndexTable() throws Exception {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
         propIndexToPropTypeIndexMap = new HashMap();
        Database propertyindex = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.propertyindextable"), null);
        Cursor cursor = propertyindex.openCursor(null, null);

        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream keyis = new DataInputStream(new ByteArrayInputStream(keyEntry.getData()));
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            dumpPropIndexRow(keyis, datais);
        }

        cursor.close();
        propertyindex.close();
    }

    private void dumpPropIndexRow(DataInputStream keyis, DataInputStream datais) throws Exception {
        boolean skipWritePropIndexToSQL = false;
        String subjectName, propName;
        String propClassName=keyis.readUTF();
        int index=datais.readInt();
        int historySize = 0;
        String sql = "";
        if(propClassName.equals(DbUtils.PROPERTYINDEX_MARKER)) {
            subjectName = propName = DbUtils.PROPERTYINDEX_MARKER;
        } else {
            String[] contents = DbUtils.EntityPathfromClass(propClassName).split("\\$\\$");
            subjectName = contents[0];
            propName = contents[1].substring(2); // Get rid of the 1z also.

            boolean isSMConcept = false;
            Concept c = repo.ontology().getConcept(subjectName);
            if (c == null) {
                if(StateMachineHelper.isStateMachineConcept(subjectName, repo.ontology())){
                    isSMConcept = true;
                } else {
                    this.logger.log(Level.FATAL, "Unknown Concept: " + subjectName);
                    System.exit(1);
                    skipWritePropIndexToSQL = true;
                }
            }
            if(!isSMConcept) {
                PropertyDefinition pd = c.getPropertyDefinition(propName, false);
                if (pd == null) {
                    if(StateMachineHelper.isStateMachineConceptProperty(c, propName)) {
                        historySize = 0;
                        byte propTypeIndex = PropertyTypes.propertyTypes_atomContainedConcept;
                        propIndexToPropTypeIndexMap.put(new Integer(index), new Byte(propTypeIndex)) ;
                    } else {
                        this.logger.log(Level.FATAL, "Unknown Property: %s of Concept: %s", propName, subjectName);
                        System.exit(1);
                        skipWritePropIndexToSQL = true;
                    }
                } else {
                    historySize = pd.getHistorySize();
                    byte propTypeIndex = PropertyTypes.RDFToPropType(pd, logger);
                    propIndexToPropTypeIndexMap.put(new Integer(index), new Byte(propTypeIndex)) ;
                }
            }
            //the concept is a SM concept, so the property must be a state property
            else {
                historySize = 0;
                byte propTypeIndex = PropertyTypes.propertyTypes_atomInt;
                propIndexToPropTypeIndexMap.put(new Integer(index), new Byte(propTypeIndex)) ;
            }
        }

        if (!skipWritePropIndexToSQL)
        {
              sql =
                "INSERT INTO " + DbUtils.SQL_PROPERTYINDEX + " VALUES (\n" +
                "'" + subjectName + "'" + ", " + "'" + propName + "'" + ", " + index + ")\n";

                propIndexToPropInfo.put(new Integer(index), new PropertyInfo(DbUtils.massageNameSpaceString(subjectName) + "$$" + propName, historySize));

                SQLConnection.DBResource res = sqlcnx.executeSQL(sql);
                res.close();
        }

    }


    private void appendEventProperty(PreparedStatement stmt, int rdftype, DataInputStream is, int index) throws Exception {

        switch(rdftype) {

            case RDFTypes.STRING_TYPEID :
            case RDFTypes.DATETIME_TYPEID :
                String strval;
                if(is.readBoolean())
                    strval=is.readUTF();
                else
                    strval=null;
                if(strval == null)
                    stmt.setNull(index, Types.VARCHAR);
                else
                    stmt.setString(index, strval);
                break;
            case RDFTypes.INTEGER_TYPEID :
                is.readBoolean();
                int intval=is.readInt();
                stmt.setInt(index, intval);
                break;
            case RDFTypes.LONG_TYPEID :
            case RDFTypes.CONCEPT_TYPEID :
            case RDFTypes.CONCEPT_REFERENCE_TYPEID :
                is.readBoolean();
                long longval = is.readLong();
                stmt.setLong(index, longval);
                break;
            case RDFTypes.DOUBLE_TYPEID :
                is.readBoolean();
                double dblval=is.readDouble();
                stmt.setDouble(index, dblval);
                break;
            case RDFTypes.BOOLEAN_TYPEID :
                is.readBoolean();
                boolean boolval=is.readBoolean();
                stmt.setInt(index, boolval ? 1 : 0);
                break;
            default:
                this.logger.log(Level.FATAL, "Unknown RDF Type encountered with type = %s",
                        RDFTypes.driverTypeStrings[rdftype]);
                System.exit(1);
        }
    }

    protected void appendString(StringBuffer sql, String val) {
        if(val == null)
            sql.append("NULL");
        else
            sql.append("'" + val + "'");
    }

    protected void dumpPropertiesTable() throws DatabaseException, IOException, SQLException {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        Database propertiestable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.propertiestable"), null);
        Cursor cursor = propertiestable.openCursor(null, null);

        while(cursor.getNext(keyEntry, dataEntry, null) != OperationStatus.NOTFOUND) {
            DataInputStream keyis = new DataInputStream(new ByteArrayInputStream(keyEntry.getData()));
            DataInputStream datais = new DataInputStream(new ByteArrayInputStream(dataEntry.getData()));
            dumpPropertyRow(keyis, datais);
        }

        cursor.close();
        propertiestable.close();

    }

    protected void dumpPropertyRow(DataInputStream keyis, DataInputStream datais) throws IOException, SQLException {
        long subjectId = keyis.readLong();
        int propIndex = keyis.readInt();
        int modelHistorySize = 0;
        Byte propTypeIndexObj = null ;
        byte propTypeIndex ;

        StringBuffer sql = new StringBuffer("INSERT INTO ");
        sql.append(propTableName(propIndex));
        sql.append(" VALUES (");
        sql.append(subjectId + ", ");  // Concept Instance
        try
        {
              modelHistorySize = ((PropertyInfo)propIndexToPropInfo.get(new Integer(propIndex))).historySize;
              propTypeIndexObj = (Byte)propIndexToPropTypeIndexMap.get(new Integer(propIndex));
              propTypeIndex = propTypeIndexObj.byteValue();
              if(!isArrayType(propTypeIndex)) {
                    handlePropertyAtom(sql, propTypeIndex, datais, modelHistorySize);
              }else {
                    handlePropertyArray(sql, propTypeIndex, datais, modelHistorySize);
              }
        }catch (Exception ex) {
            this.logger.log(Level.FATAL, ex, ex.getMessage());
            System.exit(1);
        }

    }

    private void handlePropertyArray(StringBuffer sql, byte propTypeIndex, DataInputStream is, int modelHistorySize) throws IOException, SQLException {
        int arraySize = is.readInt();

        if(arraySize == 0) { // Special case for any 0 sized arrays that might exist in the db.
            // Write a single row with -1 as array index indicating this was a 0 sized array

            if(modelHistorySize == 0) // Handle 0 history case differently
                sql.append("-1, 0, NULL )\n");
            else {
                sql.append("-1, 0, 0");
                for(int i=0; i < modelHistorySize; i++)
                    sql.append(", NULL, NULL");
                sql.append(")\n");
            }
            SQLConnection.DBResource res = sqlcnx.executeSQL(sql.toString());
            res.close();
            return;
        }

        for(int i=0; i < arraySize; i++) {
            StringBuffer arraysql = new StringBuffer(sql.toString());
            arraysql.append(i + ", ");
            handlePropertyAtom(arraysql, propTypeIndex, is, modelHistorySize);
        }
    }

    private void handlePropertyAtom(StringBuffer sql, byte propTypeIndex, DataInputStream is, int modelHistorySize) throws IOException, SQLException {

        int index = 1;

        boolean isSet = is.readBoolean();
        int historySize = 0;

        if(modelHistorySize == 0) { // Handle 0 history case differently
            sql.append("?, ?");
        } else {
            historySize = is.readInt();
            sql.append("?, ?");

            for (int i = 0; i < historySize; i++) {
                sql.append(", ?, ?");
            }
        }

        sql.append(")");

        StringBuffer sb = new StringBuffer(sql.toString() + " params " );
        PreparedStatement stmt = sqlcnx.getConnection().prepareStatement(sql.toString());

        if(modelHistorySize == 0) { // Handle 0 history case differently
            sb.append("("+ isSet);
            stmt.setInt(index++, isSet ? 1 : 0);
            readAndSetConceptPropertyValue(sb, is, propTypeIndex, stmt, index++);
        } else {
            sb.append("("+ isSet);
            stmt.setInt(index++, isSet ? 1 : 0);
            short currIndex = is.readShort(); // History Index
            sb.append(", " + currIndex);
            stmt.setShort(index++, currIndex);

            long[] timeArray = new long[historySize];
            for (int i = 0; i < historySize; i++) {
                timeArray[i] = is.readLong();
            }

            for (int i = 0; i < historySize; i++) {
                sb.append(", " + timeArray[i]);
                stmt.setLong(index++, timeArray[i]);
                readAndSetConceptPropertyValue(sb, is, propTypeIndex, stmt, index++);
            }
        }
        sb.append(")");
        logger.log(Level.DEBUG, sb.toString());

        SQLConnection.DBResource res = sqlcnx.executePreparedStatement(stmt);
        res.close();
    }

    private void readAndSetConceptPropertyValue(StringBuffer sb, DataInputStream is, byte propTypeIndex, PreparedStatement stmt, int index) throws IOException, SQLException {

        switch(propTypeIndex) {
            case PropertyTypes.propertyTypes_atomBoolean:
            case PropertyTypes.propertyTypes_arrayBoolean:
                boolean value =  is.readBoolean();
                sb.append(", " + value);
                stmt.setInt(index, value ? 1 : 0);
                break;
            case PropertyTypes.propertyTypes_atomConceptReference:
            case PropertyTypes.propertyTypes_atomContainedConcept:
            case PropertyTypes.propertyTypes_arrayConceptReference:
            case PropertyTypes.propertyTypes_arrayContainedConcept:
                if(is.readBoolean()) {
                    long valuel =  is.readLong();
                    sb.append(", " + valuel);
                    stmt.setLong(index, valuel);
                }
                else {
                    sb.append(", NULL");
                    stmt.setNull(index, Types.NUMERIC);
                }
                break;

            case PropertyTypes.propertyTypes_atomLong:
            case PropertyTypes.propertyTypes_arrayLong:
                long valuel =  is.readLong();
                sb.append(", " + valuel);
                stmt.setLong(index, valuel);
                break;

            case PropertyTypes.propertyTypes_atomDateTime:
            case PropertyTypes.propertyTypes_arrayDateTime:
                long time = is.readLong();
                String timezone = null;
                if(is.readBoolean()) {
                    timezone = is.readUTF();
                }
                Calendar datetime = new GregorianCalendar();
                if(timezone != null) {
                    datetime.setTimeZone(TimeZone.getTimeZone(timezone));
                }
                datetime.setTimeInMillis(time);
                String output = null;
                try {
                    output = ((XsDateTime)java2xsd_dt_conv.convertToTypedValue(datetime)).castAsString();
                } catch (ConversionException ce) {
                    this.logger.log(Level.ERROR, ce, ce.getMessage());
                }
                if(output == null) {
                    sb.append(", NULL");
                    stmt.setNull(index, Types.VARCHAR);
                } else {
                    sb.append(", " + output);
                    stmt.setString(index, output);
                }
                break;
            case PropertyTypes.propertyTypes_atomString:
            case PropertyTypes.propertyTypes_arrayString:
                if(is.readBoolean()) {
                    String value2 =  is.readUTF();
                    sb.append(", " + value2);
                    stmt.setString(index, value2);
                }
                else {
                    sb.append(", NULL");
                    stmt.setNull(index, Types.VARCHAR);
                }
                break;

            case PropertyTypes.propertyTypes_atomDouble:
            case PropertyTypes.propertyTypes_arrayDouble:
                double value3=  is.readDouble();
                sb.append(", " + value3);
                stmt.setDouble(index, value3);
                break;

            case PropertyTypes.propertyTypes_atomInt:
            case PropertyTypes.propertyTypes_arrayInt:
                int value4=  is.readInt();
                sb.append(", " + value4);
                stmt.setInt(index, value4);
                break;
            default:
                logger.log(Level.FATAL, "Unknown Type encountered: " + propTypeIndex);
                System.exit(1);
        }

    }

    private boolean isArrayType(byte propTypeIndex) {
        //return PropertyArray.class.isAssignableFrom(PropertyTypes.getPropertyType(propTypeIndex));
        // AtomContainedConcept is an exception in the numbering...
        return propTypeIndex >= PropertyTypes.propertyTypes_arrayBoolean && propTypeIndex != PropertyTypes.propertyTypes_atomContainedConcept;
    }

    protected String propTableName(int propIndex) {
        String tablename = ((PropertyInfo)propIndexToPropInfo.get(new Integer(propIndex))).tableName;
        if(DbUtils.USE_SHORT_TABLENAMES)
            tablename = (String) tablenamesMap.get(tablename);
        return tablename;
    }

    private void dumpPayloadRow(long eventid, DataInputStream datais) throws IOException, SQLException {
        String prepare = "INSERT INTO " + DbUtils.SQL_PAYLOAD_TABLE + " VALUES ( ?, ?, ?)";

        int payloadtype = datais.readInt();
        int payloadlength = datais.readInt();
        byte[] payloadbytes = new byte[payloadlength];
        datais.readFully(payloadbytes);

        this.logger.log(Level.DEBUG,"%s params (%s, %s, %s)", prepare, eventid, payloadtype, new String(payloadbytes));

        PreparedStatement stmt = sqlcnx.getConnection().prepareStatement(prepare);

        stmt.setLong(1, eventid);
        stmt.setInt(2, PayloadTypes.convertPayloadTypeEngineToSQL(payloadtype));
        stmt.setBytes(3, payloadbytes);

        SQLConnection.DBResource res = sqlcnx.executePreparedStatement(stmt);
        res.close();
    }

    private static class EntityHeader {

        boolean retractedFlag; // Concept/Event marked deleted? Part of status too.
        String className; // Concept/Event class name
        long timeStamp; // Time of add/delete -- introduced in 1.1
        long id; // Internal Id
        String extId; //External Id
        //byte status; // Status Flags

        EntityHeader(DataInputStream is) throws IOException {
            id = is.readLong();
             if(is.readBoolean())
                extId = is.readUTF();
            else
                extId = null;
            retractedFlag = is.readBoolean();
            className = is.readUTF();
            timeStamp = is.readLong();
        }

        public byte getConceptStatus() {
            if(retractedFlag) {
                return DbUtils.CONCEPT_RETRACTED;
            } else {
                return 0;
            }
        }

        public byte getEventStatus() {
            if(retractedFlag) {
                return DbUtils.EVENT_RETRACTED;
            } else {
                return 0;
            }
        }
    }

    private static class PropertyInfo {
        String tableName;
        int historySize;

        PropertyInfo(String _tableName, int _historySize) {
            tableName = _tableName;
            historySize = _historySize;
        }
    }
}
