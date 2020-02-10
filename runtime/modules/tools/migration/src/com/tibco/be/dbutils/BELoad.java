package com.tibco.be.dbutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.property.history.ImplSerializerHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayIntImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.xml.data.primitive.values.XsDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Mar 14, 2005
 * Time: 12:09:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BELoad {

    protected RepoAccess repo;
    protected Environment bdbenv;
    protected DatabaseConfig bdbconfig;
    protected SQLConnection sqlcnx;
    private DatabaseEntry keyEntry = new DatabaseEntry();
    private DatabaseEntry dataEntry = new DatabaseEntry();
    private ByteArrayOutputStream keybytes = new ByteArrayOutputStream();
    private ByteArrayOutputStream databytes = new ByteArrayOutputStream();
    private DataOutputStream keyos = new DataOutputStream(keybytes);
    DataOutputStream dataos = new DataOutputStream(databytes);
    BEProperties beprops = null;
    private HashMap propNameToIndex;
    private HashMap tablenamesMap = null;
    Logger logger = null;
    private static TypeConverter xsd2java_dt_conv= XSDTypeRegistry.getInstance().nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    private int sourceDBMajorVersion, sourceDBMinorVersion;
    //state machine migration creates new concepts and events, so this needs to be knowm
    private long nextEntityId = 1;
    
    private int propTableStart = -1;
    private boolean ver1db = false;

    public BELoad(BEProperties beprops, Logger logger) throws Exception {
        this.beprops = beprops;
        this.logger = logger;
        String repoFile = beprops.getProperty("tibco.repourl");
        if(repoFile == null) {
            this.logger.log(Level.FATAL, "No Repo specified");
            System.exit(1);
        }
        repo = new RepoAccess(beprops, logger);
        String dbdir = beprops.getProperty("be.dbutils.berkeley.newdbenv");
        boolean overwriteDb = false;
        if(dbdir == null || dbdir.length() == 0) {
            dbdir = beprops.getProperty("be.dbutils.berkeley.dbenv");
        } else {
            overwriteDb = true;
        }
        if(dbdir == null) {
            this.logger.log(Level.FATAL, "No Berkeley DB Directory specified");
            System.exit(1);
        }
        File envdir = new File(dbdir);
        if(!envdir.exists()) {
            envdir.mkdirs();
        } else if(overwriteDb){
            final Pattern jdbFile = Pattern.compile("[0-9a-f]{8}\\.jdb", Pattern.CASE_INSENSITIVE);
            final Pattern lckFile = Pattern.compile("je\\.lck", Pattern.CASE_INSENSITIVE);
            File [] files = envdir.listFiles(new FilenameFilter(){
                public boolean accept(File dir, String name) {
                    return jdbFile.matcher(name).matches() || lckFile.matcher(name).matches();
                }
            });
            for(int ii = 0; ii < files.length; ii++) {
                files[ii].delete();
            }
        }

        EnvironmentConfig envconfig = new EnvironmentConfig();
        envconfig.setAllowCreate(true);
        envconfig.setTransactional(true);
        bdbenv = new Environment(envdir, envconfig);
        bdbconfig = new DatabaseConfig();
        bdbconfig.setAllowCreate(true);
        bdbconfig.setExclusiveCreate(true);
        bdbconfig.setTransactional(true);
        bdbconfig.setSortedDuplicates(false);

        sqlcnx = new SQLConnection(logger);
        propNameToIndex = new HashMap();
    }

    public void load() throws Exception {
        this.logger.log(Level.INFO, "Loading data to Berkeley DB from external database");
        this.doDbLoad();
        this.logger.log(Level.INFO, "Loading data to Berkeley DB from external database succeeded");
    }

    protected void doDbLoad() throws Exception {
        if(DbUtils.USE_SHORT_TABLENAMES) {
            tablenamesMap = new HashMap();
            DbUtils.prepareTableNamesMap(sqlcnx, tablenamesMap);
        }
        loadVersionTable();
        Database conceptable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.conceptable"), bdbconfig);
        propTableStart = beprops.getInt("be.dbutils.propTableStart", -1);
        boolean skipToPropTable = propTableStart > 0;
        if(!skipToPropTable) loadConceptTable(conceptable);
        ver1db = sourceDBMajorVersion == 1 && sourceDBMinorVersion > 0; 
        //keep it open for state machine migration
        if(!ver1db) {
            conceptable.close();
            conceptable = null;
        }
        Database eventstable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.eventslog"), bdbconfig);
        if(!skipToPropTable) loadEventLog(eventstable);
        //keep it open for state machine migration
        if(!ver1db) {
            eventstable.close();
            eventstable = null;
        }
        loadPropertyIndexTable();
        loadPropertiesTable(conceptable, ver1db);
        if(conceptable != null) {
            conceptable.close();
        }
        if(eventstable!= null) {
            eventstable.close();
        }
        //the state machine migration that happens in loadPropertiesTable
        //may add more property indexes to the propNameToIndex Map
        //so loading them from SQL and writing them must happen separately
        writePropertyIndexTable();
    }

    protected void addRow(Database table) throws DatabaseException {
        keyEntry.setData(keybytes.toByteArray());
        dataEntry.setData(databytes.toByteArray());
        OperationStatus opstat = table.put(null, keyEntry, dataEntry);
        if(opstat != OperationStatus.SUCCESS)
            throw new DBException("Data insert got status = " + opstat);

    }

    private void loadVersionTable() throws DatabaseException, IOException, SQLException {
        Database versiontable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.beversiontable"), bdbconfig);

        keybytes.reset();
        databytes.reset();

        keyos.writeUTF(DbUtils.BE_VERSION_RECORD);
        String sql = "SELECT * from " + DbUtils.SQL_DB_VERSION_TABLE;
        SQLConnection.DBResource resource = sqlcnx.executeQuery(sql);
        ResultSet rs = resource.getResultSet();
        rs.next();
        String version = rs.getString(1);
        String[] splits = version.split("\\.");
        sourceDBMajorVersion = Integer.parseInt(splits[0]);
        sourceDBMinorVersion = Integer.parseInt(splits[1]);
        dataos.writeUTF(cep_commonVersion.version);

        addRow(versiontable);
        versiontable.close();
        resource.close();
    }

    protected void loadConceptTable(Database conceptable) throws SQLException, DatabaseException, IOException {
        String sql =
                "SELECT * from " + DbUtils.SQL_CONCEPTS_TABLE;
        SQLConnection.DBResource resource = sqlcnx.executeQuery(sql);
        ResultSet rs = resource.getResultSet();
        while(rs.next())
            loadConceptRow(rs, conceptable);
        resource.close();
    }

    protected void loadConceptRow(ResultSet rs, Database conceptable) throws SQLException, IOException, DatabaseException {
        String conceptName = rs.getString(1);

        //this isn't used in 2.0
        if(conceptName.equals(DbUtils.URIRECORD_CLASS_STRING)) return;

        long id = rs.getLong(2);
        String extId = rs.getString(3);
        byte status = rs.getByte(4);
        long timeStamp = rs.getLong(5);
        
        if(id >= nextEntityId) nextEntityId = id + 1;
        writeConceptRow(DbUtils.ClassNamefromEntityPath(conceptName), id,extId, status, timeStamp, conceptable);
    }
    
    private void writeConceptRow(String conceptName, long id, String extId, byte status, long timeStamp, Database conceptable) throws IOException, DatabaseException {
        keybytes.reset();
        databytes.reset();

        keyos.writeLong(id);
        dataos.writeLong(id);
        appendString(dataos, extId);
        dataos.writeBoolean(isConceptRetracted(status));

        dataos.writeUTF(conceptName);
        dataos.writeLong(timeStamp);

        addRow(conceptable);
    }

    protected void loadEventLog(Database eventstable) throws Exception {
        StringBuffer sql;
        Collection events = repo.ontology().getEvents();
        SQLConnection.DBResource resource = null;
        ResultSet rs = null;
        for(Iterator it = events.iterator(); it.hasNext();) {
            Event e = (Event)it.next();
            //avoid printing an exception caused by looking for a time event table that doesn't exist
            if(ver1db && e.getType() != Event.SIMPLE_EVENT) continue;
            sql = new StringBuffer("SELECT * from ");
            String tablename = DbUtils.massageNameSpaceString(e.getNamespace()) + "$" + e.getName();
            if(DbUtils.USE_SHORT_TABLENAMES)
                tablename = (String) tablenamesMap.get(tablename);

            sql.append(tablename);

            try {
                resource = null;
                resource = sqlcnx.executeQuery(sql.toString());
            }
            //this will come from the table not existing
            //most likely because the event is a time event
            //and the 1.4 dbutils doesn't dump them
            catch (SQLException sqe) {
                if(resource != null) resource.close();
                if(e != null && ver1db && e.getType() == Event.TIME_EVENT) {
                    this.logger.log(Level.DEBUG, "Got SQL exception for TimeEvent tablename " + tablename + ": " + sqe.getMessage());
                } else {
                    //if(logger.isError()) this.logger.log(Level.DEBUG, "Got SQL exception for tablename " + tablename + ": " + sqe.getMessage());
                    throw sqe;
                }
                continue;
            }

            rs = resource.getResultSet();

            if(e.getType() == Event.SIMPLE_EVENT) {
                while(rs.next())
                    loadSimpleEventRow(e, rs, eventstable);
                resource.close();
            } else if(e.getType() == Event.TIME_EVENT) {
                while(rs.next())
                    loadTimeEventRow(e, rs, eventstable);
                resource.close();
            }
        }

        if(sourceDBMajorVersion >= 2) {
            //try {
                resource = null;
                resource = sqlcnx.executeQuery("SELECT * from " + DbUtils.SQL_STATE_TIMEOUT_TABLE);
                rs = resource.getResultSet();
                while(rs.next()) {
                    loadStateTimeoutRow(rs, eventstable);
                }
                resource.close();
            //} catch(SQLException sqe) {
            //    if(logger.isError()) logger.logError("Got SQL exception for tablename " + DbUtils.SQL_STATE_TIMEOUT_TABLE + ": " + sqe.getMessage());
            //    if(resource != null) resource.close();
            //}
        }
    }

    private int loadEventHeader(Event e, ResultSet rs, long id) throws Exception {
        return loadEventHeader(DbUtils.ClassNamefromEntityPath(e.getNamespace() + e.getName()), rs, id);
    }
    private int loadEventHeader(String classname, ResultSet rs, long id) throws Exception {
        int index = 2;
        String extId = rs.getString(index++);
        byte status = rs.getByte(index++);
        long timestamp = rs.getLong(index++);
        
        writeEventHeader(id, extId, status, classname, timestamp);
        return index;
    }

    private void loadSimpleEventRow(Event e, ResultSet rs, Database eventstable) throws Exception {
        long id = rs.getLong(1);
        loadEventHeader(e, rs, id);
        Collection propdefs = e.getAllUserProperties();
        for(Iterator it=propdefs.iterator(); it.hasNext();) {
            EventPropertyDefinition pd = (EventPropertyDefinition) it.next();
            appendEventProperty(dataos, pd, rs);
        }
        if(e.hasPayload()) {
            appendEventPayload(id, dataos);
        } else {
            dataos.writeBoolean(false);
        }

        if(id >= nextEntityId) nextEntityId = id + 1;
        addRow(eventstable);
    }

    private void loadTimeEventRow(Event e, ResultSet rs, Database eventstable) throws Exception {
        long id = rs.getLong(1);
        int index = loadEventHeader(e, rs, id);
        loadTimeEventProperties(rs, index);

        if(id >= nextEntityId) nextEntityId = id + 1;
        addRow(eventstable);
    }

    private int loadTimeEventProperties(ResultSet rs, int index) throws SQLException, IOException{
        //scheduled time
        long scheduledTime = rs.getLong(index++); 
        //closure
        String closure = rs.getString(index++);
        //ttl
        long ttl = rs.getLong(index++);
        
        writeTimeEventProperties(scheduledTime, closure, ttl);
        
        return index;
    }

    private void writeEventHeader(long id, String extId, byte status, String classname, long timestamp) throws IOException {
        keybytes.reset();
        databytes.reset();

        keyos.writeLong(id);
        dataos.writeLong(id);
        appendString(dataos, extId);
        dataos.writeBoolean(isEventRetracted(status));
        dataos.writeUTF(classname);
        dataos.writeLong(timestamp);
    }
    private void writeTimeEventProperties(long scheduledTime, String closure, long ttl) throws IOException {
        dataos.writeLong(scheduledTime);
        appendString(dataos, closure);
        dataos.writeLong(ttl);
    }
    private void writeStateTimeoutProperties(long sm_id, String property_name) throws IOException {
        dataos.writeLong(sm_id);
        dataos.writeUTF(property_name);
    }
    
    private void loadStateTimeoutRow(ResultSet rs, Database eventstable) throws Exception {
        long id = rs.getLong(1);
        int index = loadEventHeader(DbUtils.STATE_TIMEOUT_EVENT_CLASS_NAME, rs, id);
        index = loadTimeEventProperties(rs, index);
        long sm_id = rs.getLong(index++);
        String property_name = rs.getString(index++);
        writeStateTimeoutProperties(sm_id, property_name);

        if(id >= nextEntityId) nextEntityId = id + 1;
        addRow(eventstable);
    }

    private void appendEventPayload(long eventId, DataOutputStream os) throws SQLException, IOException {
        String sql = "SELECT * from " + DbUtils.SQL_PAYLOAD_TABLE + " WHERE " + CreateSQLSchema.SQL_PAYLOAD_TABLE_EVENT_ID + " = " + eventId;

        SQLConnection.DBResource resource = sqlcnx.executeQuery(sql);
        ResultSet rs = resource.getResultSet();

        if(rs.next()) {
            int payloadtype = rs.getInt(2);
            byte[] payloadbytes = rs.getBytes(3);

            //convertPayloadTypeSQLToEngine returns -1 for XiNode object payload.
            payloadtype = PayloadTypes.convertPayloadTypeSQLToEngine(payloadtype);
            if(payloadtype < 0) {
                this.logger.log(Level.WARN, "Event with ID " + eventId + " has XiNode Object payload.  This type is not supported and the payload has been skipped");
                os.writeBoolean(false);
                return;
            }

            os.writeBoolean(true);
            os.writeInt(payloadtype);
            os.writeInt(payloadbytes.length);
            os.write(payloadbytes);
        } else {
            os.writeBoolean(false);
        }

        resource.close();
    }

    private void appendEventProperty(DataOutputStream os, EventPropertyDefinition pd, ResultSet rs) throws SQLException, IOException {
        int rdftype = RDFTypes.getIndex(pd.getType());
        String column = pd.getPropertyName();

        switch(rdftype) {

            case RDFTypes.STRING_TYPEID :
            case RDFTypes.DATETIME_TYPEID :
                String strval = rs.getString(column);
                appendString(os, strval);
                break;
            case RDFTypes.INTEGER_TYPEID :
                int intval=rs.getInt(column);
                os.writeBoolean(true);
                os.writeInt(intval);
                break;
            case RDFTypes.LONG_TYPEID :
            case RDFTypes.CONCEPT_TYPEID :
            case RDFTypes.CONCEPT_REFERENCE_TYPEID :
                long longval = rs.getLong(column);
                os.writeBoolean(true);
                os.writeLong(longval);
                break;
            case RDFTypes.DOUBLE_TYPEID :
                double dblval=rs.getDouble(column);
                os.writeBoolean(true);
                os.writeDouble(dblval);
                break;
            case RDFTypes.BOOLEAN_TYPEID :
                boolean boolval=rs.getBoolean(column);
                os.writeBoolean(true);
                os.writeBoolean(boolval);
                break;
            default:
                this.logger.log(Level.FATAL, "Unknown RDF Type encountered with type = " + RDFTypes.driverTypeStrings[rdftype]);
                System.exit(1);
        }

    }

    private boolean isEventRetracted(byte status) {
        return (status & DbUtils.EVENT_RETRACTED) != 0;
    }

    protected void loadPropertyIndexTable() throws DatabaseException, SQLException, IOException {
        String sql = "SELECT * from " + DbUtils.SQL_PROPERTYINDEX;

        SQLConnection.DBResource resource = sqlcnx.executeQuery(sql);
        ResultSet rs = resource.getResultSet();
        while(rs.next()) {
            loadPropertyIndexRow(rs);
        }
        resource.close();
    }

    private void loadPropertyIndexRow(ResultSet rs) throws SQLException {
        String propClassName;
        String subjectName = rs.getString(1);
        String propName = rs.getString(2);
        int index = rs.getInt(3);

        if(subjectName.equals(DbUtils.PROPERTYINDEX_MARKER)) {
            propClassName=DbUtils.PROPERTYINDEX_MARKER;
        } else if(subjectName.equals(DbUtils.TRANSITION_STATUSES)) {
            //transition status properties aren't used in 2.0
            return;
        } else {
            propClassName = DbUtils.ClassNamefromEntityPath(subjectName + "$$1z" + propName);
        }
        propNameToIndex.put(propClassName, new Integer(index));
    }
    
    private void writePropertyIndexTable() throws DatabaseException, IOException {
        Database propertyindex = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.propertyindextable"), bdbconfig);
        for(Iterator it = propNameToIndex.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            String propClassName = (String)entry.getKey();
            int index = ((Integer)entry.getValue()).intValue();

            keybytes.reset();
            databytes.reset();
            keyos.writeUTF(propClassName);
            dataos.writeInt(index);
            addRow(propertyindex);
        }
        propertyindex.close();
    }

    protected void loadPropertiesTable(Database conceptable, boolean ver1db) throws Exception {
        Database propertiestable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.propertiestable"), bdbconfig);
        Collection concepts = repo.ontology().getConcepts();
        Database migratedActiveStatesTable = null;
        if(ver1db) migratedActiveStatesTable = bdbenv.openDatabase(null, beprops.getString("be.engine.om.berkeleydb.migratedActiveStates", "BEMigratedActiveStates"), bdbconfig);
        
        for(Iterator it = concepts.iterator(); it.hasNext();) {
            Concept c = (Concept) it.next();
            Collection propdefs = c.getPropertyDefinitions(true);
            for(Iterator pit = propdefs.iterator(); pit.hasNext();) {
                PropertyDefinition pd = (PropertyDefinition) pit.next();
                handlePropertyDef(c, pd, propertiestable);
            }
            StateMachine main = StateMachineHelper.getMainStateMachine(c);
            List machines = c.getStateMachines();
            if(ver1db){
                if(main != null) {
                    migrate1_4StateMachines(c, main, propertiestable, conceptable, migratedActiveStatesTable);
                }
            } else if(machines != null && machines.size() > 0) {
                load2_0StateMachines(c, machines, propertiestable);
            }
        }
        propertiestable.close();
        if(ver1db) migratedActiveStatesTable.close();

    }
    
    //get all transition statuses rows (for a single concept type)
    //for each row, write one property entry for each active state
    //in 2.0 inherited state machine states have teh same property name as that
    //state in the parent class, so the set of new property names needs to be
    //preserved across concepts
    //update the propertyIndex map for all the new state properties
    private void migrate1_4StateMachines(Concept c, StateMachine main, Database propertiestable, Database concepttable, Database activeMigratedStatesTable) throws SQLException, IOException, DatabaseException {
        StateTransition[] transitions = StateMachineHelper.makeTransitionsArray(c, main);
        PropertyDefinition transitionStatusesDef = new DefaultMutablePropertyDefinition(null, DbUtils.TRANSITION_STATUSES, null, true,
                PropertyDefinition.PROPERTY_TYPE_INTEGER, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 2, null, -1);
        SQLConnection.DBResource resource = getPropertyArrayDBResource(c.getNamespace(), c.getName(),  DbUtils.TRANSITION_STATUSES);
        if(propTableStart > 0 && resource == null) return;
        ResultSet rs = resource.getResultSet();
        
        //propIndex doesn't matter here because the TransitionStatuses property isn't being written to BDB 
        int propIndex = 0;
        byte propTypeIndex = PropertyTypes.RDFToPropType(transitionStatusesDef, logger);
        int historySize=transitionStatusesDef.getHistorySize();
        ByteArrayOutputStream atomsbytes = new ByteArrayOutputStream();
        DataOutputStream atoms = new DataOutputStream(atomsbytes);
        
        if(rs.next()) {
            for(long ownerConceptId = rs.getLong(1), nextOwnerConceptId; ownerConceptId != -1; ownerConceptId = nextOwnerConceptId) {
                nextOwnerConceptId = serializeNextPropertyArray(ownerConceptId, propIndex, propTypeIndex, historySize, rs, atoms, atomsbytes);
                TransitionStatusesPropArray statuses = new TransitionStatusesPropArray(2, null);
                ImplSerializerHelper.deserialize(statuses, new DataInputStream(new ByteArrayInputStream(databytes.toByteArray())));
            
                Collection activeStates = StateMachineHelper.getActiveStates(statuses, transitions);
                this.logger.log(Level.DEBUG, "Active States");
                HashMap activeStateMachines = new HashMap();
                for(Iterator statesIt = activeStates.iterator(); statesIt.hasNext();) {
                    State state = (State)statesIt.next();
                    StateMachine ownerMachine = state.getOwnerStateMachine();
                    if(this.logger.isEnabledFor(Level.DEBUG)) {
                        this.logger.log(Level.DEBUG, ownerMachine.getOwnerConcept().getFullPath() + "/" + ownerMachine.getName() + "/" + state.getName());
                    }
                    long ownerStateMachineId = getOwnerStateMachineConceptId(ownerConceptId, ownerMachine, activeStateMachines, concepttable, propertiestable);
                
                    writePropertyAtomSimpleHeader(ownerStateMachineId, getPropertyIndex(state), true);
                    writePropertyAtomIntValue(dataos, PropertyStateMachineState.STATE_READY);
                    addRow(propertiestable);
                
                    addMigratedStateTimeoutEvent(ownerStateMachineId, StateMachineHelper.getStatePropertyName(state), activeMigratedStatesTable);
                }
                this.logger.log(Level.DEBUG, "Active Machines");
                    for(Iterator machinesIt = activeStateMachines.keySet().iterator(); machinesIt.hasNext();) {
                        this.logger.log(Level.DEBUG, String.valueOf(machinesIt.next()));
                    }
            }
        }
        resource.close();
    }
    
    private void addMigratedStateTimeoutEvent(long ownerMachineId, String statePropertyName, Database activeMigratedStatesTable) throws DatabaseException, IOException {
        writeEventHeader(nextEntityId++, null, DbUtils.EVENT_NO_STATUS, DbUtils.MIGRATED_STATE_TIMEOUT_EVENT_CLASS_NAME, System.currentTimeMillis());
        writeTimeEventProperties(0, null, 0);
        writeStateTimeoutProperties(ownerMachineId, statePropertyName);
        addRow(activeMigratedStatesTable);
    }
    
    private long getOwnerStateMachineConceptId(long ownerConceptId, StateMachine ownerMachine, Map activeStateMachines, Database concepttable, Database propertiestable) throws IOException, DatabaseException {
        Long machineId = (Long)activeStateMachines.get(ownerMachine.getFullPath());
        if(machineId == null) {
            machineId = new Long(nextEntityId++);
            activeStateMachines.put(ownerMachine.getFullPath(), machineId);
            //create new state machine concept
            writeConceptRow(StateMachineHelper.getStateMachineClassName(ownerMachine), machineId.longValue(), null, DbUtils.CONCEPT_NO_STATUS, System.currentTimeMillis(), concepttable);
            //set the state machine property in the owner concept to the new state machine concept
            writePropertyAtomSimpleHeader(ownerConceptId, getPropertyIndex(ownerMachine), true);
            writePropertyAtomConceptValue(dataos, machineId.longValue());
            addRow(propertiestable);
        }
        return machineId.longValue();
    }
    
    private int getPropertyIndex(State state) {
        return getPropertyIndex(StateMachineHelper.getStatePropertyClassName(state));
    }
    private int getPropertyIndex(StateMachine machine) {
        return getPropertyIndex(StateMachineHelper.getStateMachinePropertyClassName(machine));
    }
    private int getPropertyIndex(String key) {
        Integer indexObj = (Integer)propNameToIndex.get(key);
        if(indexObj == null) {
            indexObj = (Integer)propNameToIndex.get(DbUtils.PROPERTYINDEX_MARKER);
            propNameToIndex.put(key, indexObj);
            propNameToIndex.put(DbUtils.PROPERTYINDEX_MARKER, new Integer(indexObj.intValue() + 1));
        }
        return indexObj.intValue();
    }
    
    private void writePropertyAtomSimpleHeader(long subjectId, int propIndex, boolean isSet) throws IOException {
        keybytes.reset();
        databytes.reset();

        keyos.writeLong(subjectId);
        keyos.writeInt(propIndex);

        dataos.writeBoolean(isSet);
    }
    
    private void writePropertyAtomIntValue(DataOutputStream os, int intval) throws IOException {
        os.writeInt(intval);
    }
    
    private void writePropertyAtomConceptValue(DataOutputStream os, long conceptval) throws IOException {
        if(conceptval == 0L) // was NULL
            os.writeBoolean(false);
        else {
            os.writeBoolean(true);
            os.writeLong(conceptval);
        }
    }

    private void load2_0StateMachines(Concept c, List machines, Database propertiestable) throws DatabaseException, IOException, SQLException{
        for(Iterator machinesIt = machines.iterator(); machinesIt.hasNext();) {
            StateMachine sm = (StateMachine)machinesIt.next();
            //create property for the StatemachineConcept in the owner concept
            handlePropertyAtom(c.getNamespace(), c.getName(), StateMachineHelper.getStateMachinePropertyName(sm), PropertyTypes.propertyTypes_atomContainedConcept, 0, propertiestable);
            ArrayList stateNames = new ArrayList();
            StateMachineHelper.getStateMachineStateNames(sm.getMachineRoot(), stateNames);
            for (int jj = 0; jj < stateNames.size(); jj++) {
                handlePropertyAtom(StateMachineHelper.getStateMachineNamespace(sm), StateMachineHelper.getStateMachineConceptName(sm), (String)stateNames.get(jj), PropertyTypes.propertyTypes_atomContainedConcept, 0, propertiestable);
            }
        }
    }
    
    private void handlePropertyDef(Concept c, PropertyDefinition pd, Database propertiestable) throws SQLException, DatabaseException, IOException {
        if(pd.isArray())
            handlePropertyArray(c, pd, propertiestable);
        else
            handlePropertyAtom(c, pd, propertiestable);
    }

    private void handlePropertyAtom(Concept c, PropertyDefinition pd, Database propertiestable) throws SQLException, IOException, DatabaseException {
        handlePropertyAtom(c.getNamespace(), c.getName(), pd.getName(), PropertyTypes.RDFToPropType(pd, logger), pd.getHistorySize(), propertiestable);
    }
    private void handlePropertyAtom(String conceptNamespace, String conceptName, String propertyName, byte propTypeIndex, int historySize, Database propertiestable) throws SQLException, IOException, DatabaseException {
        String tablename = DbUtils.massageNameSpaceString(conceptNamespace) + "$" + conceptName + "$$" + propertyName;
        if(DbUtils.USE_SHORT_TABLENAMES)
            tablename = (String) tablenamesMap.get(tablename);

        if(propTableStart > 0 && Integer.parseInt(tablename.substring(tablename.lastIndexOf('_')+1)) < propTableStart) return;
        
        String sql = "SELECT * from " + tablename;
        SQLConnection.DBResource resource = sqlcnx.executeQuery(sql);
        ResultSet rs = resource.getResultSet();

        if(rs.next()) {
            Integer propIndexObj = (Integer) propNameToIndex.get(DbUtils.ClassNamefromEntityPath(conceptNamespace + conceptName) + "$$1z" + propertyName);
            //this should never happen since rs.next() will be false if an of the property was never
            //created and therefore never assigned a property index
            if(propIndexObj == null) {
                this.logger.log(Level.FATAL, "Missing property index for property atom " + conceptNamespace + "/" + conceptName + "/" + propertyName);
                resource.close();
                System.exit(1);
            }
            int propIndex = propIndexObj.intValue();
            //use do/while since rs.next is called once by the enclosing if statement
            do{
                long subjectId = rs.getLong(1);
                int isSet = rs.getInt(2);
                keybytes.reset();
                databytes.reset();

                keyos.writeLong(subjectId);
                keyos.writeInt(propIndex);

                dataos.writeBoolean(isSet == 1);

                if(historySize == 0) { // Special case for 0 history size
                    writeValue(dataos, rs, -1, propTypeIndex);
                } else {
                    short currHistoryIndex = rs.getShort(3);
                    dataos.writeInt(historySize);
                    dataos.writeShort(currHistoryIndex);

                    for (int i = 0; i < historySize; i++) {
                        long time = rs.getLong("Time" + i);
                        dataos.writeLong(time);
                    }

                    for (int i = 0; i < historySize; i++) {
                        writeValue(dataos, rs, i, propTypeIndex);
                    }
                }

                addRow(propertiestable);

            } while(rs.next());
        }

        resource.close();
    }
    
    private SQLConnection.DBResource getPropertyArrayDBResource(String conceptNamespace, String conceptName, String propertyName) throws SQLException{
        String tablename = DbUtils.massageNameSpaceString(conceptNamespace) + "$" + conceptName + "$$" + propertyName;
        if(DbUtils.USE_SHORT_TABLENAMES)
            tablename = (String) tablenamesMap.get(tablename);

        if(propTableStart > 0 && Integer.parseInt(tablename.substring(tablename.lastIndexOf('_')+1)) < propTableStart) return null;

        
        String sql = "SELECT * from " + tablename + " ORDER BY ConceptInstanceId, ArrayIndex";
        return sqlcnx.executeQuery(sql);
    }

    private void handlePropertyArray(Concept c, PropertyDefinition pd, Database propertiestable) throws SQLException, IOException, DatabaseException {
        SQLConnection.DBResource resource = getPropertyArrayDBResource(c.getNamespace(), c.getName(), pd.getName());
        if(propTableStart > 0 && resource == null) return;
        ResultSet rs = resource.getResultSet();

        if(rs.next()) {
            Integer propIndexObj = (Integer) propNameToIndex.get(DbUtils.ClassNamefromEntityPath(c.getNamespace() + c.getName()) + "$$1z" + pd.getName());
            //this should never happen since hasNext will be false if an instance
            //of the property was never created and therefore never assigned a property index
            if(propIndexObj == null) {
                this.logger.log(Level.FATAL, "Missing property index for property array " + c.getNamespace() + "/" + c.getName() + "/" + pd.getName());
                resource.close();
                System.exit(1);
            }
            
            int propIndex = propIndexObj.intValue();
            byte propTypeIndex = PropertyTypes.RDFToPropType(pd, logger);
            int historySize=pd.getHistorySize();
            //created once and then reset every loop iteration
            ByteArrayOutputStream atomsbytes = new ByteArrayOutputStream();
            DataOutputStream atoms = new DataOutputStream(atomsbytes);

            for(long subjectId = rs.getLong(1); subjectId != -1;) {
                subjectId = serializeNextPropertyArray(subjectId, propIndex, propTypeIndex, historySize, rs, atoms, atomsbytes);
                addRow(propertiestable);
            }
        }
        resource.close();
    }

    private long serializeNextPropertyArray(long subjectId, int propIndex, byte propTypeIndex, int historySize, ResultSet rs, DataOutputStream atoms, ByteArrayOutputStream atomsbytes) throws IOException, SQLException {
        keybytes.reset();
        databytes.reset();
        atomsbytes.reset();
        int arraySize = 0;

        keyos.writeLong(subjectId);
        keyos.writeInt(propIndex);

        boolean hasNext = true;
        long nextSubjectId = -1;
        do {
            int currArrIndex = rs.getInt(2);
            if(currArrIndex != -1) { // -1 is a special case for a 0 sized array
                arraySize++;
                int isSet = rs.getInt(3);
                atoms.writeBoolean(isSet == 1);

                if(historySize == 0) { // Special case for 0 history Size
                    writeValue(atoms, rs, -1, propTypeIndex);
                } else {
                    short currHistoryIndex = rs.getShort(4);
                    atoms.writeInt(historySize);
                    atoms.writeShort(currHistoryIndex);

                    for (int i = 0; i < historySize; i++) {
                        long time = rs.getLong("Time" + i);
                        atoms.writeLong(time);
                    }

                    for (int i = 0; i < historySize; i++) {
                        writeValue(atoms, rs, i, propTypeIndex);
                    }
                }
            }
            
            hasNext = rs.next();
            if(hasNext) nextSubjectId = rs.getLong(1);
            else nextSubjectId = -1L; // To break out of this inner loop.

        } while(subjectId == nextSubjectId);

        dataos.writeInt(arraySize); // First write the array size
        dataos.write(atomsbytes.toByteArray()); // Write the individual atoms.
        return nextSubjectId;
    }

    private void writeValue(DataOutputStream os, ResultSet rs, int index, byte propTypeIndex) throws SQLException, IOException {
        String columnName;
        if(index == -1)
            columnName="Value"; // 0 History Size special case.
        else
            columnName="Value" + index;

        switch(propTypeIndex) {
            case PropertyTypes.propertyTypes_atomBoolean:
            case PropertyTypes.propertyTypes_arrayBoolean:
                boolean boolval = rs.getBoolean(columnName);
                os.writeBoolean(boolval);
                break;

            case PropertyTypes.propertyTypes_atomConceptReference:
            case PropertyTypes.propertyTypes_atomContainedConcept:
            case PropertyTypes.propertyTypes_arrayConceptReference:
            case PropertyTypes.propertyTypes_arrayContainedConcept:
                long conceptval = rs.getLong(columnName);
                writePropertyAtomConceptValue(os, conceptval);
                break;

            case PropertyTypes.propertyTypes_atomLong:
            case PropertyTypes.propertyTypes_arrayLong:
                long longval = rs.getLong(columnName);
                os.writeLong(longval);
                break;

            case PropertyTypes.propertyTypes_atomDateTime:
            case PropertyTypes.propertyTypes_arrayDateTime:
                //A null timezone indicates that the value of 
                //the BE DateTime (actually a Java Calendar)
                //is null, regardless of the value of the time
                //in milliseconds
                Calendar cal = null;
                String dateTimeString = rs.getString(columnName);
                try {
                if(dateTimeString != null) {
                    cal = (Calendar) xsd2java_dt_conv.convertSimpleType(dateTimeString);
                }
                } catch (ConversionException ce) {
                    this.logger.log(Level.ERROR, ce, ce.getMessage());
                }
                long time = 0;
                String timezone = null;
                if(cal != null) {
                    time = cal.getTimeInMillis();
                    TimeZone tz = cal.getTimeZone();
                    if(tz != null) {
                        timezone = tz.getID();
                    }
                }
                os.writeLong(time);
                appendString(os, timezone);
                break;
            case PropertyTypes.propertyTypes_atomString:
            case PropertyTypes.propertyTypes_arrayString:
                String strval = rs.getString(columnName);
                if(strval != null) {
                    os.writeBoolean(true);
                    os.writeUTF(strval);
                } else
                    os.writeBoolean(false);
                break;

            case PropertyTypes.propertyTypes_atomDouble:
            case PropertyTypes.propertyTypes_arrayDouble:
                double dblval = rs.getDouble(columnName);
                os.writeDouble(dblval);
                break;

            case PropertyTypes.propertyTypes_atomInt:
            case PropertyTypes.propertyTypes_arrayInt:
                int intval = rs.getInt(columnName);
                writePropertyAtomIntValue(os, intval);
                break;

            default:
                this.logger.log(Level.FATAL, "Unknown Type encountered: " + propTypeIndex);
                System.exit(1);

        }
    }

    private static void appendString(DataOutputStream os, String str) throws IOException {
        if(str != null) {
            os.writeBoolean(true);
            os.writeUTF(str);
        } else
            os.writeBoolean(false);
    }

    private boolean isConceptRetracted(byte status) {
        return (status & DbUtils.CONCEPT_RETRACTED) != 0;
    }

    private static class TransitionStatusesPropArray extends PropertyArrayIntImpl
    {
        public TransitionStatusesPropArray(int historySize, com.tibco.cep.runtime.model.element.impl.ConceptImpl parent) {
            super(parent, 0);
        }
        public int getHistorySize() {
            return 2;
        }
    }
}
