package com.tibco.be.dbutils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Feb 3, 2005
 * Time: 3:26:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateSQLSchema {
    public static final String SQL_PAYLOAD_TABLE_EVENT_ID = "EventId";
    protected RepoAccess repo;
    protected SQLConnection cnx;
    BEProperties beprops;
    Logger logger = null;

    public CreateSQLSchema(BEProperties _beprops, Logger _logger) throws Exception {
        beprops = _beprops;
        logger  = _logger;
        String repoDir = beprops.getString("tibco.repourl");
        if(repoDir == null) {
            this.logger.log(Level.FATAL, "No BE repo specified");
            System.exit(1);
        }
        repo = new RepoAccess(beprops, logger);
        cnx = new SQLConnection(logger);
    }

    protected void doCreate() {

        try {
            createVersionTable(); // 1.1 onwards
            createTableNamesMap();
            createConceptTable();
            //createURIRecord();
            createEventsTables();
            createPayloadsTable();
            createPropertiesTables();
            createPropertiesIndexTable();
            //createScorecardIdsTable();
            this.logger.log(Level.INFO, "Creating tables for DbUTILS succeeded");
        } catch (Exception e) {
            cnx.close();
            this.logger.log(Level.FATAL, e.getMessage(), e);
            System.exit(1);
        }

    }

    public void create() throws Exception {
        this.logger.log(Level.INFO, "Creating tables for DbUTILS");
        this.doCreate();
    }

    private void createVersionTable() throws SQLException {
        String sql =
                "CREATE TABLE " + DbUtils.SQL_DB_VERSION_TABLE + "\n" +
                "( BEVersion CHAR VARYING(100) NOT NULL)\n";
        SQLConnection.DBResource res = cnx.executeSQL(sql);
        res.close();
    }

    protected void createTableNamesMap() throws SQLException {
        if(DbUtils.USE_SHORT_TABLENAMES) {
            String sql = "CREATE TABLE " + DbUtils.SQL_TABLENAMES_MAP + "\n" +
                    //TODO change this this back to 2000
                    "( LogicalName CHAR VARYING(500) PRIMARY KEY,\n" +
                    "RealTableName CHAR VARYING(30) NOT NULL)";
            SQLConnection.DBResource res = cnx.executeSQL(sql);
            res.close();
        }
    }

    protected void createConceptTable() throws SQLException {
        String sql =
                "CREATE TABLE " + DbUtils.SQL_CONCEPTS_TABLE + "\n" +
                "( ConceptName CHAR VARYING(1000) NOT NULL,\n" +
                "InternalId NUMERIC(20) PRIMARY KEY,\n" +
                "ExternalId CHAR VARYING(1000) NULL,\n" +
                "StatusFlags NUMERIC NOT NULL," +
                "TimeStamp NUMERIC(20) NOT NULL)\n";
        SQLConnection.DBResource res = cnx.executeSQL(sql);
        res.close();
    }

    /*protected void createURIRecord() throws SQLException {
        String sql =
                "CREATE TABLE " + DbUtils.SQL_URIRECORD + "\n" +
                "( LastInternalId NUMERIC(20) NOT NULL )\n";
        SQLConnection.DBResource res = cnx.executeSQL(sql);
        res.close();
    }*/

    protected void createEventsTables() throws Exception {
        StringBuffer sql;
        Collection events = repo.ontology().getEvents();
        int i=1;

        for(Iterator it = events.iterator(); it.hasNext();) {
            Event e = (Event)it.next();
            String tablename = DbUtils.massageNameSpaceString(e.getNamespace()) + "$" + e.getName();
            sql = new StringBuffer("CREATE TABLE ");
            if(DbUtils.USE_SHORT_TABLENAMES) {
                String realtablename = "EventTable_" + i++;
                String logicalname = tablename;
                tablename = realtablename;
                String mapsql =
                        "INSERT INTO " + DbUtils.SQL_TABLENAMES_MAP + " VALUES (\n" +
                        "'" + logicalname + "', '" + realtablename + "' )";
                cnx.executeSQL(mapsql);
            }
            sql.append(tablename);
            sql.append("(\n");
            appendEventHeaderColumns(sql);

            if(e.getType() == Event.SIMPLE_EVENT) {
                Collection propdefs = e.getAllUserProperties();
                for(Iterator pit = propdefs.iterator(); pit.hasNext();) {
                    EventPropertyDefinition pd = (EventPropertyDefinition) pit.next();
                    sql.append(pd.getPropertyName() + " ");
                    sql.append(DbUtils.RDFtoSQLType(RDFTypes.getIndex(pd.getType()), logger) + " NULL,\n");
                }
            } else {
                appendTimeEventColumns(sql);
            }

            sql.append("PRIMARY KEY (BE$InternalId) )\n" );

            SQLConnection.DBResource res = cnx.executeSQL(sql.toString());
            res.close();
        }

        createStateTimeOutEventsTable();
    }
    
    protected void appendEventHeaderColumns(StringBuffer sql) {
        sql.append( "BE$InternalId NUMERIC(20) NOT NULL,\n" +
                "BE$ExternalId CHAR VARYING(1000) NULL,\n" +
                "BE$StatusFlags NUMERIC NOT NULL,\n" +
                "BE$TimeStamp NUMERIC(20) NOT NULL, \n");
    }
    
    protected void appendTimeEventColumns(StringBuffer sql) {
        sql.append("BE$ScheduledTime ");
        sql.append(DbUtils.RDFtoSQLType(RDFTypes.LONG_TYPEID, logger) + " NULL,\n");
        sql.append("BE$Closure ");
        sql.append(DbUtils.RDFtoSQLType(RDFTypes.STRING_TYPEID, logger) + " NULL,\n");
        sql.append("BE$TTL ");
        sql.append(DbUtils.RDFtoSQLType(RDFTypes.LONG_TYPEID, logger) + " NULL,\n");
    }
    
    protected void createStateTimeOutEventsTable() throws SQLException {
        StringBuffer sql = new StringBuffer("CREATE TABLE " + DbUtils.SQL_STATE_TIMEOUT_TABLE +"(\n");
        appendEventHeaderColumns(sql);
        appendTimeEventColumns(sql);
        sql.append("BE$SMId ");
        sql.append(DbUtils.RDFtoSQLType(RDFTypes.LONG_TYPEID, logger) + " NULL,\n");
        sql.append("BE$Property_Name ");
        sql.append(DbUtils.RDFtoSQLType(RDFTypes.STRING_TYPEID, logger) + " NULL,\n");
        sql.append("PRIMARY KEY (BE$InternalId) )\n" );
        
        SQLConnection.DBResource res = cnx.executeSQL(sql.toString());
        res.close();
    }


    protected void createPayloadsTable() throws SQLException {
        String dburl = beprops.getString("be.dbutils.jdbc.url");
        String blobType = "BLOB";
        if (dburl.indexOf("sqlserver") != -1)
        {
            blobType = "image";
        }
        String sql = "CREATE TABLE " + DbUtils.SQL_PAYLOAD_TABLE +"\n" +
                "( " + SQL_PAYLOAD_TABLE_EVENT_ID + " NUMERIC(20) PRIMARY KEY,\n" +
                "PayloadType NUMERIC(10) NOT NULL,\n" +
                "PayloadData " + blobType + " NULL)\n";

        SQLConnection.DBResource res = cnx.executeSQL(sql);
        res.close();
    }

    protected void createPropertiesTables() throws Exception {
        Collection concepts = repo.ontology().getConcepts();

        int nameIndex =1;

        for(Iterator it = concepts.iterator(); it.hasNext();) {
            //String conceptkey = (String) it.next();
            Concept c = (Concept) it.next();
            //Concept c = (Concept) cd.getModel();
            Collection propdefs = c.getPropertyDefinitions(true);
            for(Iterator pit = propdefs.iterator(); pit.hasNext();) {
                PropertyDefinition pd = (PropertyDefinition) pit.next();
                createPropertyTable(c, pd, nameIndex++);
            }
            
            
            List smlist = c.getStateMachines();
            if(smlist != null) {
                for(Iterator smIt = smlist.iterator(); smIt.hasNext();) {
                    StateMachine sm = (StateMachine)smIt.next();
                    //create property for the StatemachineConcept in the owner concept
                    createPropertyTable(nameIndex++, c.getNamespace(), c.getName(), StateMachineHelper.getStateMachinePropertyName(sm), false, 0, PropertyDefinition.PROPERTY_TYPE_CONCEPT);
                    
                    ArrayList stateNames = new ArrayList();
                    StateMachineHelper.getStateMachineStateNames(sm.getMachineRoot(), stateNames);
                    for (int jj = 0; jj < stateNames.size(); jj++) {
                        createPropertyTable(nameIndex++, StateMachineHelper.getStateMachineNamespace(sm), StateMachineHelper.getStateMachineConceptName(sm), (String)stateNames.get(jj), false, 0, PropertyDefinition.PROPERTY_TYPE_INTEGER);
                    }
                }
            }
        }
    }

    /* Creates a table in the SQL database for the given property definition */
    protected void createPropertyTable(Entity c, PropertyDefinition pd, int nameindex) throws SQLException {
        createPropertyTable(nameindex, c.getNamespace(), c.getName(), pd.getName(), pd.isArray(), pd.getHistorySize(), pd.getType());
    }
    protected void createPropertyTable(int nameindex, String namespace, String conceptName, String propName, boolean isArray, int historySize, int type) throws SQLException {
        StringBuffer sql = new StringBuffer("CREATE TABLE ");
        String tablename= DbUtils.massageNameSpaceString(namespace) + "$" + conceptName + "$$" + propName;

        if(DbUtils.USE_SHORT_TABLENAMES) {
            String realtablename = "PropertyTable_" + nameindex;
            String logicalname = tablename;
            tablename = realtablename;
            String mapsql =
                    "INSERT INTO " + DbUtils.SQL_TABLENAMES_MAP + " VALUES (\n" +
                    "'" + logicalname + "', '" + realtablename + "' )";

            SQLConnection.DBResource res = cnx.executeSQL(mapsql);
            res.close();
        }

        sql.append(tablename);
        sql.append("(\n");

        sql.append("ConceptInstanceId NUMERIC(20) NOT NULL,\n");
        if(isArray)
            sql.append("ArrayIndex NUMERIC(10) NOT NULL,\n");
        sql.append("IsSetFlag NUMERIC NOT NULL,\n");

        if(historySize == 0) { // Special case for 0 History Size
            sql.append("Value ");
            sql.append(DbUtils.RDFtoSQLType(type, logger) + " NULL,\n");
        } else {
            sql.append("CurrentHistoryIndex NUMERIC(10) NOT NULL,\n");
            for (int i = 0; i < historySize; i++) {
                sql.append("Time" + i + " NUMERIC(20) NULL,\n");
                sql.append("Value" + i + " ");

                sql.append(DbUtils.RDFtoSQLType(type, logger) + " NULL,\n");
            }
        }
        sql.append("PRIMARY KEY (ConceptInstanceId" );
        if(isArray)
            sql.append(", ArrayIndex");
        sql.append(")\n)");

        SQLConnection.DBResource res = cnx.executeSQL(sql.toString());
        res.close();
    }

    protected void createPropertiesIndexTable() throws SQLException {
        String sql =
                "CREATE TABLE " + DbUtils.SQL_PROPERTYINDEX + "\n" +
                "( ConceptName CHAR VARYING(1000) NOT NULL,\n" +
                "PropertyName CHAR VARYING(1000) NOT NULL,\n" +
                "PropertyIndex NUMERIC(10) NOT NULL )\n";

        SQLConnection.DBResource res = cnx.executeSQL(sql);
        res.close();
    }

    //Not used in 2.0
    /*protected void createScorecardIdsTable() throws SQLException {
        String sql =
                "CREATE TABLE " + DbUtils.SQL_SCORECARDIDS_TABLE + "\n" +
                "( ScoreCardConceptName CHAR VARYING(1000) NOT NULL,\n" +
                "ScoreCardInternalId NUMERIC(20) NOT NULL )\n";

        SQLConnection.DBResource res = cnx.executeSQL(sql);
        res.close();
    }*/

    protected void recreatedb() {
        String sql = "DROP DATABASE \"BEdb\";\n" +
                "\n" +
                "CREATE DATABASE \"BEdb\"\n" +
                "  WITH OWNER = be";
        try {
            SQLConnection.DBResource res = cnx.executeSQL(sql);
            res.close();
        } catch (SQLException e) {
            this.logger.log(Level.FATAL, e.getMessage(), e);
            System.exit(1);
        }
    }

}
