package com.tibco.be.dbutils;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.logging.impl.LogManagerImpl;
import com.tibco.cep.runtime.util.SystemProperty;


/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Mar 21, 2005
 * Time: 7:03:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class  DbUtils {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    static final BEProperties beprops = BEProperties.getInstance();
    Properties argvProps = new Properties();

    public static final String ROOT_TABLENAMESPACE="Root";

    public static final String SQL_CONCEPTS_TABLE="BEConceptTable";
    //public static final String SQL_URIRECORD = "URIRecord";
    //public static final String SQL_CHECKPOINT_TABLE="BECheckPointEvents";
    public static final String SQL_STATE_TIMEOUT_TABLE = "BEStateTimeoutEvents";
    public static final String SQL_PROPERTYINDEX="BEPropertiesIndex";
    //public static final String SQL_SCORECARDIDS_TABLE = "BEScorecardIdsTable";
    public static final String SQL_PAYLOAD_TABLE = "BEPayloads";
    public static final String SQL_TABLENAMES_MAP = "BETableNamesMap";
    public static final String SQL_DB_VERSION_TABLE = "BEVersionTable";

    public static final String PROPERTYINDEX_MARKER="$MarkerRecord$"; // From ConceptsManager
    //static final String CPE_STRING=CheckpointEvent.class.getName();
    public static final String STATE_TIMEOUT_EVENT_CLASS_NAME = "com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl$StateTimeoutEvent";
    static final String MIGRATED_STATE_TIMEOUT_EVENT_CLASS_NAME = "com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl$MigratedStateTimeoutEvent";
    //static final String URIRECORD_CLASS_STRING = URIRecord.class.getName();
    public static final String URIRECORD_CLASS_STRING = "com.tibco.be.engine.om.URIRecord";
    public static final String TRANSITION_STATUSES = "TransitionStatuses"; // From Code generation
    public static final String BE_VERSION_RECORD="BEVersionRecord"; // From ObjectManager
    public static final String EVENT_PROP_COLUMN_PREFIX="$"; // From ObjectManager

    static final byte CONCEPT_RETRACTED=0x20; // From AbstractConceptImpl
    static final byte EVENT_RETRACTED = 0x02; // From AbstractEventImpl
    static final byte CONCEPT_NO_STATUS = 0;
    static final byte EVENT_NO_STATUS = 0;
    static boolean USE_SHORT_TABLENAMES = false; // Some db's do not support long table names.
    static boolean FORBID_EMPTY_STRINGS = false;
    static String EMPTY_STRING_ESCAPE = "☺";

    Logger logger = null;
    
    public DbUtils(String[] argv) throws Exception {
        String defProp = System.getProperty("wrapper.tra.file", "be-dbutils.tra");
        argvProps.put("be.dbutils.property.file", defProp);
        parseArgs(argv);
        Properties props = new Properties();
        props.load(new FileInputStream(argvProps.getProperty("be.dbutils.property.file")));
        beprops.mergeProperties(props);
        beprops.mergeProperties(argvProps);
        beprops.mergeProperties(System.getProperties());
        USE_SHORT_TABLENAMES = beprops.getBoolean("be.dbutils.jdbc.shortnames", false);
        FORBID_EMPTY_STRINGS = beprops.getBoolean("be.dbutils.jdbc.forbidEmptyStrings", false);
        EMPTY_STRING_ESCAPE = beprops.getString("be.dbutils.jdbc.emptyStringEscape", "☺");

        //create logger
            
        //add this so that the output doesn't say cep-engine
        if (beprops.get(SystemProperty.TRACE_NAME.getPropertyName()) == null) {
            beprops.put(SystemProperty.TRACE_NAME.getPropertyName(), "be-dbutils");
        }

        final LogManagerImpl logManager = new LogManagerImpl(beprops);
        this.logger = logManager.getLogger(DbUtils.class);

        String action = beprops.getProperty("be.dbutils.action");
        if(action == null) {
            printUsage();
            System.exit(0);
        }
        if(action.equals("create")) {
            try{
                CreateSQLSchema createSQLSchema = new CreateSQLSchema(beprops, logger);
                createSQLSchema.create();
            } catch (Exception e) {
                this.logger.log(Level.FATAL, e, e.getMessage());
                System.exit(1);
            }
        }
        else if(action.equals("dump")) {
            try
            {
                BEDump beDump = new BEDump(beprops, logger);
                beDump.dump();
            }
            catch (Exception e)
            {
                this.logger.log(Level.FATAL, e, e.getMessage());
                System.exit(1);

            }
        } else if(action.equals("load")) {
            try
            {
                BELoad beLoad = new BELoad(beprops, logger);
                beLoad.load();
            }
            catch (Exception e)
            {
                this.logger.log(Level.FATAL, e, e.getMessage());
                System.exit(1);

            }
        } else {
            System.out.println("Action can only be one of create, dump or load");
            printUsage();
            System.exit(1);
        }

    }

    private void parseArgs(String[] argv) {

        for (int i=0; i < argv.length; i++) {
            String key = argv[i];

            //-h or /h or -help or /help
            if (key.matches("-h|/h|-help|/help"))  {
                printUsage();
                System.exit(0);
            }
            else if (key.matches("-property|/property|-pr|/pr|-system:propFile")) {
                ++i;
                argvProps.put("be.dbutils.property.file", argv[i]);
            } else if (key.matches("-d|-db|/d|/db")) {
                ++i;
                argvProps.put("be.dbutils.jdbc.url",argv[i]);
            } else if (key.matches("-u|-user|/u|/user")) {
                ++i;
                argvProps.put("be.dbutils.jdbc.user", argv[i]);
            } else if (key.matches("-p|-password|/p|/password")) {
                ++i;
                argvProps.put("be.dbutils.jdbc.password", argv[i]);
            } else if (key.matches("-b|/b|-bedb|/bedb")) {
                ++i;
                argvProps.put("be.dbutils.berkeley.dbenv", argv[i]);
            }
            //this property is only used by load
            //and will delete existing bdb files in the directory 
            else if (key.matches("-n|/n|-newbedb|/newbedb")) {
                ++i;
                argvProps.put("be.dbutils.berkeley.newdbenv", argv[i]);
            } else if(key.matches("-r|/r|-repo|/repo")) {
                ++i;
                argvProps.put("tibco.repourl", argv[i]);
            } else if(key.matches("-s|/s|-shortnames|/shortnames")) {
                argvProps.put("be.dbutils.jdbc.shortnames", "true");
            } else
                argvProps.put("be.dbutils.action", argv[i]);
        }

    }

    private void printUsage() {
        System.out.println("\n\tUsage:\n");
        System.out.println("\tbe-dbutils <create|dump|load> [-pr <property file>] [-d <jdbc url>] [-u <jdbc user>] [-p <[password>] [-b <BerkeleyDB Dir>] [-n <New BerkeleyDB Dir>] [-s] [-r repoDirectory]");
        System.out.println();
        System.out.println("\tcreate: Create the SQL Schema");
        System.out.println("\tdump: Dump from BE to SQL db");
        System.out.println("\tload: Load from SQL db to BE");
        System.out.println("\t-pr Specify the property file. The default property file is be-dbutils.tra in the current directory.\n" +
                           "\t    Same as -property or /property or /p");
        System.out.println("\t-d  Specify the JDBC URL that will be passed to the JDBC driver");
        System.out.println("\t    Same as -db or /d or /db");
        System.out.println("\t-u  Specify the username used to connect to the database.");
        System.out.println("\t    Same as -user or /u or /user");
        System.out.println("\t-p  Specify the password for the user used to connect to the database.");
        System.out.println("\t    Same as -password or /p or /password");
        System.out.println("\t-b  Specify the directory where the BE Berkeley DB exists or is to be created.");
        System.out.println("\t    Same as -bedb or /b or /bedb");
        System.out.println("\t-n  Specify the directory where the new BE Berkeley DB is to be created.");
        System.out.println("\t    Any existing Berkeley DB files in this directory will be deleted.");
        System.out.println("\t    Same as -newbedb or /n or /newbedb");
        System.out.println("\t-s  Use Short table names. (For databases that do not support long table names)");
        System.out.println("\t    Same as -shortnames or /s or /shortnames");
        System.out.println("\t-r  Specify the directory where the designer repo lives.");
        System.out.println("\t    Same as -repo or /r or /repo");
        System.out.println("\t-h  Display this usage. Same as -help or /help or /h" );

    }

    public static void main(String[] argv) throws Exception {
        new DbUtils(argv);
    }

    public static String massageNameSpaceString(String ns) {
        if(ns == null)
            return ROOT_TABLENAMESPACE;
        ns = ROOT_TABLENAMESPACE + ns;
        while(ns.length() > 0 && ns.charAt(ns.length()-1) == '/')
            ns = ns.substring(0,ns.length()-1); // Trim the trailing /
        ns = ns.replace('/', '$'); // Now remove any / in the path and convert them to .
        return ns;
    }

    public static String RDFtoSQLType(int rdftype, Logger logger) {
        String sqltype = null;

        switch(rdftype) {

            case RDFTypes.STRING_TYPEID :
                sqltype =beprops.getString("be.dbutils.dbtype.string", "CHAR VARYING(4000)");
                break;
            case RDFTypes.DATETIME_TYPEID :
                sqltype =beprops.getString("be.dbutils.dbtype.datetime", "CHAR VARYING(4000)");
                break;
            case RDFTypes.INTEGER_TYPEID :
                sqltype =beprops.getString("be.dbutils.dbtype.int", "NUMERIC(20)");
                break;
            case RDFTypes.LONG_TYPEID :
                sqltype =beprops.getString("be.dbutils.dbtype.long", "NUMERIC(20)");
                break;
            case RDFTypes.CONCEPT_TYPEID :
            case RDFTypes.CONCEPT_REFERENCE_TYPEID :
                sqltype =beprops.getString("be.dbutils.dbtype.ref", "NUMERIC(20)");
                break;
            case RDFTypes.DOUBLE_TYPEID :
                sqltype = beprops.getString("be.dbutils.dbtype.double", "FLOAT");
                break;
            case RDFTypes.BOOLEAN_TYPEID :
                sqltype = beprops.getString("be.dbutils.dbtype.boolean", "NUMERIC");
                break;
            default:
                logger.log(Level.FATAL, "Unknown RDF Type encountered with type = %s",
                        RDFTypes.driverTypeStrings[rdftype]);
                System.exit(1);
        }

        return sqltype;
    }

    public static void prepareTableNamesMap(SQLConnection sqlcnx, Map tablenamesMap) throws SQLException {
        String sql = "SELECT * from " + SQL_TABLENAMES_MAP;
        SQLConnection.DBResource resource = sqlcnx.executeQuery(sql);
        ResultSet rs = resource.getResultSet();

        while(rs.next()) {
            tablenamesMap.put(rs.getString(1), rs.getString(2));
        }

        resource.close();
    }

    public static String EntityPathfromClass(String className) {
        String entityPath = className.substring(beprops.getString("be.codegen.rootPackage", "be.gen").length(), className.length());
        entityPath = entityPath.replace('.', '/');
        return entityPath;
    }

    public static String ClassNamefromEntityPath(String path) {
        String className = beprops.getString("be.codegen.rootPackage", "be.gen") + path;
        className = className.replace('/', '.');
        return className;
    }

    //Oracle doesn't differentiate null and empty string so this has to be done
    public static String javaStringToSQLString(String str) {
        if(FORBID_EMPTY_STRINGS) {
            if(str == null) return null;
            if("".equals(str) || str.endsWith(EMPTY_STRING_ESCAPE)) return str + EMPTY_STRING_ESCAPE;
        }
        return str;
    }

    public static String SQLStringToJavaString(String str) {
        if(FORBID_EMPTY_STRINGS) {
            if(str == null) return null;
            if(str.endsWith(EMPTY_STRING_ESCAPE)) return str.substring(0, str.length() - 1);
        }
        return str;
    }
}
