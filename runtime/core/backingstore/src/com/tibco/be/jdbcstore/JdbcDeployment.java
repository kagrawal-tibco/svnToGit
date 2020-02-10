/*
 * Copyright(c) 2004-2013.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.jdbcstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.xml.sax.InputSource;

import com.tibco.be.common.ConnectionPool;
import com.tibco.be.jdbcstore.JdbcTable.MemberDef;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.parser.codegen.RDFUtil;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.BEPropertiesFactory;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.nodes.Element;

//Make sure to check JDBC Export Wizard in studio when you make changes to this utility specifically
//com.tibco.cep.studio.ui.build.StudioJDBCDeployment

public class JdbcDeployment {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }

    private Ontology ontology;
    private SqlSchemaFile schemaFile;
    private FileOutputStream file_output;
    private SqlType sqlType;
    private JdbcUtil jdbcUtil;

    // This map contains only those types that are new. Used for generating alter script
    private StringBuilder migrationErrors = new StringBuilder();

    private String[] argv;
    private boolean useAnsi = true;
    private boolean showEnv = false;
    private static boolean optimize = false;
    private static boolean histIdNotNull = false; // allow null value for id field with history - t_entity_ref_hist_tuple
    private static boolean childIdNotNull = false; // allow null value for id field with contained concepts - t_concept_property

	//Oracle 12c supports varchar or nvarchar upto 32767 byte, when MAX_STRING_SIZE set to EXTENDED
	//https://docs.oracle.com/database/121/REFRN/GUID-D424D23B-0933-425F-BC69-9C0E6724693C.htm#REFRN10321
    //user argument maxstringsize when set to "extended", is used as a hint to expand string max size for oracle
    private static boolean expandMaxStringSize = false;
    
    private String clusterMode;
    private String databaseType;
    private String baseTypeFile;
    private String keywordFile;
    private String traFile;
    private String repoUrl;
    private String schemaOwner;
    private String schemaOutput;
    private static final String BRK = System.getProperty("line.separator", "\n");
    private Properties env = new Properties();
    private Map<String,JdbcType> systemTypeMap = new HashMap<String,JdbcType>();
    private Map<String,JdbcTable> systemTableMap = new HashMap<String,JdbcTable>();
    private Map<String,String> keywordMap = new HashMap<String,String>();
    private Map<String,String> entitiesUsingCacheOM = new HashMap<String,String>();

    protected PrintStream outStream;
    protected PrintStream errStream;

    public static void main (String [] args) {
        try {
            JdbcDeployment dbDeployment = new JdbcDeployment(args);
            dbDeployment.deploySql();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Constructor for tools
    public JdbcDeployment() {
        outStream = System.out;
        errStream = System.err;
    }

    // Constructor for extracting command line arguments
    public JdbcDeployment(String[] argv) {
        this(System.out, System.err, argv);
    }

    // Constructor for embedded usage
    public JdbcDeployment(PrintStream out, PrintStream err, String[] argv) {
        outStream = out;
        errStream = err;
        this.argv = argv;
        String defProp = System.getProperty("wrapper.tra.file", "be-jdbcdeploy.tra");
        //outStream.println("Using bootstrap property file: " + defProp);
        env.setProperty("be.bootstrap.property.file", defProp);
        parseArguments(argv);
        if (showEnv) {
            Iterator<Entry<Object, Object>> propEntrySetIterator = System.getProperties().entrySet().iterator();
            while (propEntrySetIterator.hasNext()) {
                Entry<Object, Object> e = propEntrySetIterator.next();
                outStream.println(e.getKey() + "=" + e.getValue());
            }
        }
    }

    // Constructor for sql scripts
    protected void deploySql() throws Exception {
        outStream.print(printPrologue(argv));
        /*
        String arg ="";
        for (int i=0; i < argv.length; i++) {
            arg += " " + argv[i];
        }
        */
        BEProperties beProp = createBEProperties(env);
        BEProperties bsProp = createBSProperties(env);
        this.repoUrl = beProp.getProperty("jdbcdeploy.repourl");
        this.traFile = beProp.getProperty("be.bootstrap.property.file");
        this.baseTypeFile = beProp.getProperty("jdbcdeploy.bootstrap.basetype.file");
        this.keywordFile = beProp.getProperty("jdbcdeploy.bootstrap.keyword.file");
        this.databaseType = beProp.getProperty("jdbcdeploy.database.type");
        if (this.databaseType != null) {
            this.databaseType = this.databaseType.trim();
        }
        RDBMSType.setSystemTableMap(systemTableMap);
        RDBMSType.setDBKeywordMap(keywordMap);
        RDBMSType.setDefaultSqlType(databaseType, useAnsi);
        RDBMSType.setHistIdNotNull(histIdNotNull);
        RDBMSType.setChildIdNotNull(childIdNotNull);
        if(RDBMSType.RDBMS_TYPE_NAME_ORACLE.equalsIgnoreCase(databaseType)) {
        	RDBMSType.setExpandMaxStringSize(expandMaxStringSize);
        }
        this.sqlType = RDBMSType.sSqlType;

        //--------------------------------------------------------------//

        outStream.println("Using jdbcdeploy property file: " + this.traFile);
        outStream.println("Using jdbcdeploy schema file: " + this.baseTypeFile);
        outStream.println("Using jdbcdeploy keyword file: " + this.keywordFile);
        outStream.println("Deploying to '" + RDBMSType.sRuntimeTypeName + "' using ansi type" +
                           " is " + (RDBMSType.sUseAnsiType ? "'enabled'" : "'disabled'"));
        if (this.sqlType == null) {
            outStream.println("Failed to lookup database type entry for " + this.databaseType);
        }
        this.schemaOutput = beProp.getProperty("jdbcdeploy.schema.output.file");
        String sqlPrologue = printSQLPrologue(argv);
        if (this.schemaOutput == null) {
            outStream.println("Error - No valid output file is defined. Exiting...");
            return;
        }
        if (checkFile(this.repoUrl) == false) {
            return;
        }

        // Need to do this after sqlType is initialized. Has a little bit of dependency here
        jdbcUtil = JdbcUtil.getInstance();
        jdbcUtil.setProperties(bsProp);

        // Initialize default cluster mode (cache, memory, or ...)
        this.clusterMode = bsProp.getProperty("be.engine.cluster.mode", "cache");

        //System.err.println("BE-PROPS:\n" + beProp);
        //System.err.println("BS-PROPS:\n" + bsProp);

        // Generate scripts
        generateScripts(sqlPrologue, beProp);
    }

    protected boolean checkFile(String url) {
        File file = new File(url);
        if (file.exists() && file.isFile()) {
            return true;
        }
        outStream.println("Error - Could not find a valid '" + url + "' file. Exiting...");
        return false;
    }

    // Setup bootstrap property list
    protected BEProperties createBEProperties(Properties env) {
        BEProperties beProperties = BEProperties.loadDefault();
        String oraPropertyFile = env.getProperty("be.bootstrap.property.file", "be-jdbcdeploy.tra");

        File file = new File(oraPropertyFile);
        Properties prop = new Properties();
        if (file.exists()) {
            outStream.println("Using bootstrap property file: " + env.getProperty("be.bootstrap.property.file"));
            try {
                prop.load(new FileInputStream(oraPropertyFile));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                env.store(baos, null);
                prop.load(new ByteArrayInputStream(baos.toByteArray()));
                beProperties.put("be.bootstrap.property.file", oraPropertyFile);
            } catch (IOException e) {
                throw new RuntimeException("Cannot open property file: " + oraPropertyFile, e);
            }
        }
        else {
            throw new RuntimeException("Specified property file does not exist: " + oraPropertyFile);
        }
        beProperties.mergeProperties(prop);
        beProperties.mergeProperties(System.getProperties());
        beProperties.substituteTibcoEnvironmentVariables();

        return beProperties;
    }

    // Setup deployment property list
    protected BEProperties createBSProperties(Properties env) {
        if (env.containsKey(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName())) {
            outStream.println("Using cluster deployment file: " + env.getProperty("com.tibco.be.config.path"));
            BEProperties beProperties = BEPropertiesFactory.getInstance().makeBEProperties(env);
            /**
            Enumeration names = beProperties.keys();
            while (names.hasMoreElements()) {
                String propName = (String) names.nextElement();
                if (propName.startsWith("be.engine.cluster")) {
                    errStream.println(propName+ "=" + beProperties.getProperty(propName));
                }
            }
            */
            return beProperties;
        }
        return new BEProperties();
    }

    protected void parseArguments(String argv[])  {
        if (argv.length == 1) {
            if (argv[0].matches("-h|/h|-help|/help|--help|/--help")) {
                printUsage(true);
            } else {
                printUsage(false);
            }
            System.exit(0);
        }
        if (argv.length == 0 || argv.length < 3 ) {
            printUsage(false);
            System.exit(0);
        }
        for (int i=0; i < argv.length; i++) {
            String key = argv[i];
            if (key.matches("-h|/h|-help|/help|--help|/--help"))  {
                printUsage(true);
                System.exit(0);
            }
            else if (key.matches("-p|/p|-property|/property")) {
                ++i;
                env.put("be.bootstrap.property.file", argv[i]);
            }
            else if (key.matches("-c|/c|-cdd|/cdd")) {
                ++i;
                env.put("com.tibco.be.config.path", argv[i]);
            }
            else if (key.matches("-o|/o|-out|/out")) {
                ++i;
                env.put("jdbcdeploy.schema.output.file", argv[i]);
            }
            else if (key.matches("-s|/s|-schema|/schema")) {
                ++i;
                env.put("jdbcdeploy.bootstrap.basetype.file", argv[i]);
            }
            else if (key.matches("-k|/k|-keyword|/keyword")) {
                ++i;
                env.put("jdbcdeploy.bootstrap.keyword.file", argv[i]);
            }
            else if (key.matches("-d|/d|-database|/database")) {
                ++i;
                env.put("jdbcdeploy.database.type", argv[i]);
            }
            else if (key.matches("-a|/a|-ansi|/ansi")) {
                ++i;
                if (argv[i].equalsIgnoreCase("false")) {
                    useAnsi = false;
                }
            }
            else if (key.matches("-maxstringsize|/maxstringsize")) {
                ++i;
                if (argv[i].equalsIgnoreCase("extended")) {
                    expandMaxStringSize = true;
                }
            }
            else if (key.matches("-showenv|/showenv")) {
                showEnv = true;
            }
            else if (key.matches("-optimize|/optimize")) {
                optimize = true;
            }
            else if (key.matches("-histidnotnull|/histidnotnull")) {
                histIdNotNull = true;
            }
            else if (key.matches("-childidnotnull|/childidnotnull")) {
                childIdNotNull = true;
            }
            else {
                env.put("jdbcdeploy.repourl", argv[i]);
            }
        }
    }

    private void printUsage(boolean verbose) {
        outStream.println("be-jdbcdeploy [-p <property file>] [-c <cdd file>] [-o <Jdbc schema output file>] [-a <true|false>] [-h] [EAR file path]");
        outStream.println("--propFile Location of the TRA file. The default TRA file is be-jdbcdeploy.tra in the current directory. Optional." + BRK +
                "    -system:propFile or /--propFile.");
        outStream.println("-p  Location of a custom property file. Optional. " + BRK +
                "    Same as -property or /property or /p");
        outStream.println("-c  Specify the cluster configuration file." + BRK +
                "    Same as -cdd or /cdd or /c");
        outStream.println("-o  Specify the Jdbc schema output Filename for deployment." + BRK +
                "    Same as -out or /out or /o");
        outStream.println("-a  Turn on/off ansi compatibility (default:"+useAnsi+")" + BRK +
                "    Same as -ansi or /ansi or /a");
        outStream.println("-h  Display the usage." + BRK +
                "    Same as -help or /help or /h");
        if (verbose) {
            outStream.println("" );
            outStream.println("optional flags:" );
            outStream.println("-optimize        Optimize sql generation for non-persisted entities");
            outStream.println("-histidnotnull   Disallow null values in history arrays");
            outStream.println("-childidnotnull  Disallow null values in child arrays");
            outStream.println("-maxstringsize   When set to extended, allows use of MAX_STRING_SIZE feature in Oracle 12c to expand varchar and nvarchar limit.");
        }
    }

    private String printPrologue(String args[]) {
        final StringBuffer buffer = new StringBuffer(be_jdbcstoreVersion.asterisks)
            .append(be_jdbcstoreVersion.line_separator)
            .append("\t").append(be_jdbcstoreVersion.getComponent()).append(" ").append(be_jdbcstoreVersion.version)
            .append(".").append(be_jdbcstoreVersion.build).append(" (").append(be_jdbcstoreVersion.buildDate)
            .append(")");

        buffer.append(be_jdbcstoreVersion.line_separator)
            .append("\t").append("Using arguments :");
        final StringBuffer userArgs = new StringBuffer();
        for (int i=0; i < args.length; i++) {
            userArgs.append(args[i]).append(" ");
        }

        buffer.append(userArgs)
            .append(be_jdbcstoreVersion.line_separator)
            .append("\t").append(be_jdbcstoreVersion.copyright)
            .append(be_jdbcstoreVersion.line_separator)
            .append(be_jdbcstoreVersion.asterisks)
            .append(be_jdbcstoreVersion.line_separator);

        return buffer.toString();
    }

    /*
    private void connect2Jdbc() {
        try {
            DriverManager.registerDriver(new OracleDriver());
            //DriverManager.getConnection()
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL("jdbc:oracle:thin:@localhost:1521");
            oracle = ds.getConnection("be", "be");
            outStream.println("Got connection...");
            //testJdbcObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /*
    private void testJdbcObjects() throws Exception {
        DatabaseMetaData dmd = oracle.getMetaData();
        OracleStatement os= (OracleStatement) oracle.createStatement();
        OracleResultSet rs= (OracleResultSet) os.executeQuery("SELECT * FROM ALL_ENTITIES");

        while (rs.next()) {
            Datum datum= rs.getOracleObject(1);
            //oracle.sql.STRUCT o= (oracle.sql.STRUCT) rs.getObject(1);
            //String [] names= o.getDescriptor().getAttributeJavaNames();
            outStream.println(datum);
        }
    }
    */

    // Support embedded usage
    protected DeployedProject getProject() throws Exception {
        //Class<?> workSpaceClass = Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace");
        //Workspace space = (Workspace) workSpaceClass.getMethod("getInstance").invoke(null);
        //BEArchiveResourceProvider aresProvider = new BEArchiveResourceProviderImpl();
        //return aresProvider.getBEArchives();
        Properties props = new Properties(env);
        props.put("tibco.repourl", repoUrl);
        Class projectClass  =  Class.forName("com.tibco.cep.studio.core.repo.emf.DeployedEMFProject");
        Constructor cstr = projectClass.getConstructor(Properties.class, ChangeListener.class);
        DeployedProject project  = (DeployedProject) cstr.newInstance(new Object[] {props, null});
        return project;
    }

    protected Collection<BEArchiveResource> getArchives(DeployedProject project) throws Exception {
        return project.getBEArchiveResourceProvider().getBEArchives();
    }


    // Support embedded usage
    protected Ontology getOntology() throws Exception {
        //Class<?> workSpaceClass = Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace");
        //Workspace space = (Workspace) workSpaceClass.getMethod("getInstance").invoke(null);
        //return space.loadProject(repoUrl).getOntology();
        //BEArchiveResourceProvider aresProvider;
        Properties props = new Properties(env);
        props.put("tibco.repourl", repoUrl);
        Class projectClass  =  Class.forName("com.tibco.cep.studio.core.repo.emf.DeployedEMFProject");
        Constructor cstr = projectClass.getConstructor(Properties.class, ChangeListener.class);
        DeployedProject project  = (DeployedProject) cstr.newInstance(new Object[] {props, null});
        project.load();
        return project.getOntology();
    }

    protected String evaluateGlobalVariable(DeployedProject project, String globalVariable) {
        if (project != null) {
            return project.getGlobalVariables().substituteVariables(globalVariable).toString();
        }
        return globalVariable;
    }

    /**
     *
     * @param sqlPrologue
     * @param en
     * @throws Exception
     */
    private void generateScripts(String sqlPrologue, Properties en) throws Exception {
        //connect2Jdbc();
        DeployedProject project = getProject();
        final Collection<BEArchiveResource> archives = getArchives(project);
        if (archives != null) {
            for (BEArchiveResource archive : archives) {
                Properties props = archive.getCacheConfig();
                if (props != null) {
                    for (Enumeration<?> key = props.propertyNames(); key.hasMoreElements();) {
                        String keyValue = (String) key.nextElement();
                        //outStream.println("Cache key : " + keyValue );
                        if (keyValue.equalsIgnoreCase("omtgAdvancedEntitySettings")) {
                            //outStream.println("OMTG AdvanceEntitySettings ...");
                            Object value = props.get(keyValue);
                            Element elem = (Element) value;
                            for (Iterator itrChild = elem.getChildren(); itrChild.hasNext();) {
                                Element child = (Element) itrChild.next();
                                String entityURI = child.getChildStringValue(ExpandedName.makeName("uri"));
                                String cacheMode = child.getChildStringValue(ExpandedName.makeName("cacheMode"));
                                //outStream.println("  child : " + child.getChildStringValue(ExpandedName.makeName("uri")) + ", cache mode : " + child.getChildStringValue(ExpandedName.makeName("cacheMode")));
                                if (cacheMode.equalsIgnoreCase("cache") || cacheMode.equalsIgnoreCase("cacheAndMemory")) {
                                    entitiesUsingCacheOM.put(entityURI, cacheMode);
                                }
                            }
                        }
                        /*
                        else if (keyValue.equalsIgnoreCase("cacheConfigResourceUri")) {
                            outStream.println("CacheConfigResourceUri");
                            Object value = props.get(keyValue);
                            outStream.println("  value : " + value.getClass().getName());
                        }
                        */
                    }
                }
            }
        }
        else {
            outStream.println("Error: No archives found in " + repoUrl);
        }

        /**
         Properties env = new Properties();
         env.put("tibco.repourl", repoUrl);
         env.put("be.bootstrap.property.file", traFile);
         RuleServiceProvider provider = RuleServiceProviderManager.getInstance().newProvider("JdbcDeployment", env);
         TypeManager typeManager = provider.getTypeManager();
         provider.configure(false);

         DeployedProject deployedProject= provider.getProject();
         ontology= deployedProject.getOntology();
         */
        env = en;
        ontology = getOntology();

        // Check if there is any entity from the given repourl, stop if not
        if (ontology.getEntities().size() < 1) {
            errStream.println("There is no entity from the given repourl. Exit.");
            return;
        }
        /**
        BEProject beProject = new BEProject();
        beProject.loadProject(repoUrl);
        */
        schemaFile = new SqlSchemaFile("tibco");
        final String schemaName;
        if (schemaOutput.endsWith(".sql")) {
            schemaName = schemaOutput.substring(0, schemaOutput.length() - ".sql".length());
        } else {
            schemaName = schemaOutput;
        }

        boolean popObjTab = Boolean.parseBoolean(env.getProperty("be.jdbc.schemamigration.populateObjectTable", "false").trim());

        final File schFile = new File(schemaName + ".sql");
        file_output = new FileOutputStream(schFile);
        final File cleanupFile = new File(schemaName + "_cleanup.sql");
        FileOutputStream cleanup_file = new FileOutputStream(cleanupFile);
        final File removeFile = new File(schemaName + "_remove.sql");
        FileOutputStream remove_file = new FileOutputStream(removeFile);
        final File alterFile = new File(schemaName + "_alter.sql");
        FileOutputStream alter_file = new FileOutputStream(alterFile);

        Properties aliases = null;
        Connection conn = getConn();
        if (conn != null) {
            aliases = loadAliasesFromDB(conn);
            //outStream.println("Connection successful ");
            //outStream.println("Database: " + conn.getMetaData().getDatabaseProductName() + " " + conn.getMetaData().getDatabaseProductVersion());
            //outStream.println("Driver:" + conn.getMetaData().getDriverName()+ " " + conn.getMetaData().getDriverVersion());
        } else {
            aliases = new Properties();
        }

        // Override with Aliases from file
        try {
            final File aliasesFile = new File(schemaName + ".aliases");
            if (aliasesFile.exists()) {
                FileInputStream aliases_file = new FileInputStream(aliasesFile);
                aliases.load(aliases_file);
                aliases_file.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jdbcUtil.setAliases(aliases);
        // Get system type definitions
        loadBaseTypeDefinitions(baseTypeFile);
        loadKeywordMap(keywordFile);
        boolean alterationPossible = generateJdbcScript(conn, project);

        schemaFile.setTableMapping(jdbcUtil.getClassNameToJdbcTable());
        schemaFile.setAliases(jdbcUtil.getAliases());

        StringBuffer sbo = schemaFile.toStringBuffer();
        outStream.println("Writing schema file :" + schFile.getCanonicalPath());
        PrintWriter pwSchema = new PrintWriter(file_output);
        pwSchema.print(sqlPrologue);
        pwSchema.print(sbo.toString());
        pwSchema.flush();

        outStream.println("Writing cleanup file :" + cleanupFile.getCanonicalPath());
        PrintWriter pwCleanup = new PrintWriter(cleanup_file);
        pwCleanup.print(sqlPrologue);
        pwCleanup.print(schemaFile.deleteQuery());
        pwCleanup.flush();

        outStream.println("Writing remove file :" + removeFile.getCanonicalPath());
        PrintWriter pwrRemove = new PrintWriter(remove_file);
        pwrRemove.print(sqlPrologue);
        pwrRemove.print(schemaFile.removeQuery());
        pwrRemove.flush();

        outStream.println("Writing alter file :" + alterFile.getCanonicalPath());
        PrintWriter pwrAlter = new PrintWriter(alter_file);
        pwrAlter.print(sqlPrologue);

        if (conn == null) {
            outStream.println(" No valid Database URL/Connection information provided. No data-migration script will be generated.");
            pwrAlter.print(" No valid Database URL/Connection information provided. No data-migration script is generated.");
        } else {
            if (!alterationPossible) {
                outStream.println("##### WARNING : Non-alterable Ontology changes found. Please see following errors." +
                        " Manual schema-migration is required." + migrationErrors);
                pwrAlter.print("--* ##### WARNING :  Non-alterable Ontology changes found. Please see following errors." +
                        " Manual schema-migration is required." + migrationErrors);
            }
            pwrAlter.print(schemaFile.getAlterBuffer());
        }
        pwrAlter.flush();

        if (popObjTab) {
            StringBuffer sb = schemaFile.migrateObjectTable(conn, schemaOwner);
            if (sb != null) {
                final File dupsFile = new File(schemaName + "_dups.txt");
                FileOutputStream dups_file = new FileOutputStream(dupsFile);
                PrintWriter pwrDups = new PrintWriter(dups_file);
                pwrDups.print(sqlPrologue);
                pwrDups.print(sb);
                pwrDups.flush();
                dups_file.flush();
                dups_file.close();
            }
        }

        StringBuffer sb = schemaFile.generateDeleteScript();
        if (sb != null) {
            final File dupsFile = new File(schemaName + "_delete.sql");
            outStream.println("Writing delete file :" + dupsFile.getCanonicalPath());
            FileOutputStream del_script = new FileOutputStream(dupsFile);
            PrintWriter pwrDups = new PrintWriter(del_script);
            pwrDups.print(sqlPrologue);
            pwrDups.print(sb);
            pwrDups.flush();
            del_script.flush();
            del_script.close();
        }

        final File aliasesFile = new File(schemaName + ".aliases");
        FileOutputStream aliases_file = new FileOutputStream(aliasesFile);
        outStream.println("Writing aliases file :" + aliasesFile.getCanonicalPath());
        aliases.store(aliases_file, "This file contains the aliases for column or table names that exceed maximum size(" + this.sqlType.getMaxIdentifierLength() + ") imposed by target database");

        aliases_file.flush();
        aliases_file.close();

        remove_file.flush();
        remove_file.close();
        file_output.flush();
        file_output.close();
        cleanup_file.flush();
        cleanup_file.close();
        alter_file.flush();
        alter_file.close();
    }

    private String printSQLPrologue(String args[]) {
        final StringBuffer buffer = new StringBuffer("--\t")
            .append(be_jdbcstoreVersion.asterisks)
            .append(be_jdbcstoreVersion.line_separator)
            .append("--\t").append(be_jdbcstoreVersion.getComponent()).append(" ").append(be_jdbcstoreVersion.version)
            .append(".").append(be_jdbcstoreVersion.build).append(" (").append(be_jdbcstoreVersion.buildDate)
            .append(")");

        buffer.append(be_jdbcstoreVersion.line_separator)
            .append("--\t").append("Using arguments :");
        final StringBuffer userArgs = new StringBuffer();
        for (int i=0; i < args.length; i++) {
            userArgs.append(args[i]).append(" ");
        }

        buffer.append(userArgs)
            .append(be_jdbcstoreVersion.line_separator)
            .append("--\t").append(be_jdbcstoreVersion.copyright)
            .append(be_jdbcstoreVersion.line_separator)
            .append("--\t").append(be_jdbcstoreVersion.asterisks)
            .append(be_jdbcstoreVersion.line_separator);

        return buffer.toString();
    }

    private Properties loadAliasesFromDB(Connection conn) {
        Properties alz = new  Properties();
        try {
            Statement stmt = conn.createStatement();
            String query = (schemaOwner == null) ? "Select BENAME, ALIAS from BEALIASES":
                               "Select BENAME, ALIAS from " + schemaOwner + ".BEALIASES";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String name = rs.getString(1);
                String als = rs.getString(2);
                alz.put(name,als);
            }
        }
        catch (Exception e) {
            errStream.println("Failed to load BEALIASES: " + e.getMessage());
        }
        return alz;
    }

    private boolean generateJdbcScript(Connection conn, DeployedProject project) throws Exception {
        if (conn != null) {
            Map tableToClassNameFromDB = getClassMappingFromDb(conn);
            jdbcUtil.setJdbcTableToClassNameFromDB(tableToClassNameFromDB);
            //outStream.println("Loaded class/table mappings :" + tableToClassNameFromDB);
        }
        // Alteration not possible if error encountered for either event or concept.
        // But you want to generate all errors in one go. Hence continue with concepts, even if there is error in events.
        boolean alterationPossible = generateEventTables(conn, project);
        if (!generateConceptTables(conn)) {
            alterationPossible = false;
        }
        if (!generateProcessTables(conn)) {
            alterationPossible = false;
        }

        updateTableHirarchy(); //FIXME: Why do we need this here

        return alterationPossible;
    }

    private Map<String,String> getClassMappingFromDb(Connection conn) {
        HashMap<String,String> map = new HashMap<String,String>();
        try {
            Statement stmt = conn.createStatement();
            String query = (schemaOwner == null) ? "Select TABLENAME, CLASSNAME, FIELDNAME from ClassToTable":
                               "Select TABLENAME, CLASSNAME, FIELDNAME from " + schemaOwner + ".ClassToTable";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String type = rs.getString(1);
                String clz = rs.getString(2);
                map.put(type.toUpperCase(), clz);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private void generateTimeEventType(Connection conn, Event event) {
        JdbcTable eventTable = new JdbcTable(event);
        Event superEvent = (Event) event.getSuperEvent();

        if (superEvent != null) {
            eventTable.setSuperTableName(jdbcUtil.name2JdbcTable(superEvent, jdbcUtil.name2JdbcType(superEvent)));
        } else {
            eventTable.setSuperTableName(JdbcType.BASE_TIMEEVENT_TYPE);
        }

        // If Migration intended
        Map<String, AttrColumnInfo> dbTableInfo = null;
        if (conn != null) {
            dbTableInfo = getTableInfoFromDB(conn, eventTable.getName());
            // If table exists
            if ((dbTableInfo != null) && (dbTableInfo.isEmpty() == false)) {
                // Mark the table as existing and unchanged
                eventTable.state = JdbcTable.State.UNCHANGED;
            }
        }
        schemaFile.addTable(eventTable);
    }

    /**
     *
     */
    private boolean generateEventTables(Connection conn, DeployedProject project) throws Exception {
        boolean alterationPossible = true;
        //errStream.println("Ontology="+ontology.getClass().getName()+"  Events["+ontology.getEvents().size()+
        //      "]="+ontology.getEvents().getClass().getName());
        Collection<Event> events = ontology.getEvents();
        List<Event> eventList = new ArrayList<Event>();
        for (Event event : events) {
            if (eventList.contains(event) == false) {
                eventList.add(event);
            }
        }

        // Sorting is done to reflect class hierarchy, such that super
        // concept is processed first. Others are sorted by name only.
        Collections.sort(eventList, new EntityNameComparator());

        Iterator allEvents = eventList.iterator();

        while (allEvents.hasNext()) {
            Event event = (Event) allEvents.next();
            String fullPath = event.getFullPath();

            String hasBackingStore = jdbcUtil.getEntityDeploymentProperty(event, JdbcUtil.PROP_HASBACKINGSTORE, "true");
            if ((hasBackingStore != null) && (hasBackingStore.equalsIgnoreCase("false"))) {
                outStream.println(" Event " + fullPath + " has backing store flag set to false - schema not generated");
                continue;
            }

            if (optimize == true) {
                String ttl = event.getTTL();
                if (ttl != null) {
                    ttl = evaluateGlobalVariable(project, ttl); //gvs.substituteVariables(ttl).toString();
                    try {
                        int ttlInt = Integer.parseInt(ttl);
                        if (ttlInt == 0) {
                            outStream.println(" Event " + fullPath + " has TTL=0 - schema not generated");
                            continue;
                        }
                    } catch(NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }

            String cacheMode = jdbcUtil.getEntityDeploymentProperty(event, JdbcUtil.PROP_CACHEMODE, this.clusterMode);
            if ((optimize == true) && (cacheMode.equalsIgnoreCase("memory"))) {
                outStream.println(" Event " + fullPath + " is not using cache based object management - schema not generated");
                continue;
            }

            if (event.getType() == Event.SIMPLE_EVENT) {
                JdbcTable eventTable = new JdbcTable(event);
                Event superEvent = (Event) event.getSuperEvent();

                if (superEvent != null) {
                    eventTable.setSuperTableName(jdbcUtil.name2JdbcTable(superEvent, jdbcUtil.name2JdbcType(superEvent)));
                } else {
                    eventTable.setSuperTableName(JdbcType.BASE_SIMPLEEVENT_TYPE);
                }

                /*
                if (event.getPayloadSchema() != null) {
                    eventTable.addMember(eventTable.getName() + "_p", JdbcType.TYPE_CLOB.getName());
                } else {
                    if (event.getSuperEvent() == null) {
                        eventTable.addMember(eventTable.getName() + "_p", JdbcType.TYPE_BLOB.getName());
                    }
                }
                */

                if (event.getSuperEvent() == null) {
                    // VWC - This is to add the payload field
                    //eventTable.addMember(jdbcUtil.getPDName(event, eventTable.getName() + "_p" ), JdbcType.TYPE_BLOB.getName(), null);
                    //eventTable.addMember(eventTable.getName() + "_p", JdbcType.TYPE_BLOB.getName(), null);
                    eventTable.addMember("payload__p", JdbcType.TYPE_BLOB.getName());
                }

                // Go through the properties
                Iterator it = event.getUserProperties();
                while (it.hasNext()) {
                    EventPropertyDefinition property = (EventPropertyDefinition) it.next();
                    String propertyName = property.getPropertyName();
                    RDFPrimitiveTerm type = property.getType();
                    int typeFlag = RDFUtil.getRDFTermTypeFlag(type);

                    switch (typeFlag) {
                        case RDFTypes.STRING_TYPEID:
                            // CDD stores the max length as <max-size>...</max-size>, so we will look for it first and
                            // then look for the legacy maxLength
                            String sizeAsStr = jdbcUtil.getAttributeDeploymentProperty(property, JdbcUtil.PROP_MAXSIZE, null);
                            if (sizeAsStr == null || sizeAsStr.trim().length() == 0) {
                                sizeAsStr = jdbcUtil.getAttributeDeploymentProperty(property, JdbcUtil.PROP_MAXLENGTH, null);
                            }
                            //TODO: Was this ever used [Answer:NO - Probably in the future]
                            //String columnName = jdbcUtil.getAttributeDeploymentProperty(property, JdbcUtil.PROP_COLUMNNAME, null);
                            //errStream.println("Event type=" + event.getName() + ", pd=" + property.getPropertyName() + ", maxLen=" + sizeAsStr);
                            int size = 0;
                            if ((sizeAsStr != null) && (sizeAsStr.trim().length() > 0)) {
                                try {
                                    size = Integer.valueOf(sizeAsStr);
                                } catch (Exception ex) {
                                    errStream.println("Ignoring maxLength for Property " + property.getPropertyName() + ", event=" + eventTable.getName() + ", exception=" + ex);
                                    size = 0;
                                }
                            }
                            eventTable.addMember(propertyName, JdbcType.TYPE_STRING.getName(), false, size);
                            break;
                        case RDFTypes.BOOLEAN_TYPEID:
                            eventTable.addMember(propertyName, JdbcType.TYPE_BOOLEAN.getName());
                            break;
                        case RDFTypes.INTEGER_TYPEID:
                            eventTable.addMember(propertyName, JdbcType.TYPE_INTEGER.getName());
                            break;
                        case RDFTypes.DATETIME_TYPEID:
                            eventTable.addMember(propertyName, JdbcType.TYPE_DATETIME.getName());
                            break;
                        case RDFTypes.LONG_TYPEID:
                            eventTable.addMember(propertyName, JdbcType.TYPE_LONG.getName());
                            break;
                        case RDFTypes.DOUBLE_TYPEID:
                            eventTable.addMember(propertyName, JdbcType.TYPE_DOUBLE.getName());
                            break;
                    }
                }

                // Update the hierarchy
                updateTableHierarchy(eventTable);

                // If migration intended
                Map<String, AttrColumnInfo> dbTableInfo = null;
                if (conn != null) {
                    dbTableInfo = getTableInfoFromDB(conn, eventTable.getName());
                    // If table exists
                    if ((dbTableInfo != null) && (dbTableInfo.isEmpty() == false)) {
                        eventTable.state = JdbcTable.State.UNCHANGED;
                        // Remove base concept type fields, for comparison
                        //dbTableInfo = filterBaseTypes(dbTableInfo, baseEventTypeFields);
                        if (!compare(dbTableInfo, eventTable, "Event " + event.getName())) {
                            alterationPossible = false;
                        }
                    }
                }

                schemaFile.addTable(eventTable);
            } else if (event.getType() == Event.TIME_EVENT) {
                generateTimeEventType(conn, event);
            }
        }
        return alterationPossible;
    }

    /**
     * Generate process tables
     * @param conn
     * @return
     * @throws Exception
     */
    private boolean generateProcessTables(Connection conn) throws Exception {
        boolean alterationPossible = true;
        //errStream.println("Ontology="+ontology.getClass().getName()+"  Concepts["+ontology.getConcepts().size()+
        //      "]="+ontology.getConcepts().getClass().getName());
        Collection<Entity> processes = ontology.getEntities(new ElementTypes[]{ElementTypes.PROCESS});
        List<ProcessModel> procList = new ArrayList<ProcessModel>();
        for (Entity proc : processes) {
            if (procList.contains(proc) == false) {
                procList.add((ProcessModel) proc);
            }
           if(proc instanceof ProcessModel) {
               ProcessModel pm = (ProcessModel) proc;
               Collection<SubProcessModel> subProcs = pm.getSubProcesses();
               procList.addAll(subProcs);
           }
        }

        // Sorting is done to reflect class hierarchy, such that super
        // concept is processed first. Others are sorted by name only.
        Collections.sort(procList, new EntityNameComparator());

        Iterator<ProcessModel> allProcs = procList.iterator();
        while (allProcs.hasNext()) {
            // make sure we cleanup processLoop and processMerge state tables in the generated scripts
            schemaFile.includeProcessSupportTables();
            ProcessModel proc = allProcs.next();
            String fullPath = proc.getFullPath();
            String hasBackingStore = jdbcUtil.getEntityDeploymentProperty(proc, JdbcUtil.PROP_HASBACKINGSTORE, "true");
            if ((hasBackingStore != null) && (hasBackingStore.equalsIgnoreCase("false"))) {
                outStream.println(" Process " + fullPath + " has backing store flag set to false - schema not generated");
                continue;
            }
            String cacheMode = jdbcUtil.getEntityDeploymentProperty(proc, JdbcUtil.PROP_CACHEMODE, this.clusterMode);
            if ((optimize == true) && (cacheMode.equalsIgnoreCase("memory"))) {
                outStream.println(" Process " + fullPath + " is not using cache based object management - schema not generated");
                continue;
            }

            JdbcTable procTable = new JdbcTable(proc);
            // Process has its own system properties
            procTable.setSuperTableName(JdbcType.BASE_PROCESS_TYPE); // no inheritance


            // Go through the properties
            Collection propDefs = proc.getPropertyDefinitions();
            for(Iterator it = propDefs.iterator(); it.hasNext();) {
                PropertyDefinition pd = (PropertyDefinition)it.next();
                switch(pd.getType()) {
                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                    createBooleanColumn(procTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                    createIntegerColumn(procTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                    createDateTimeColumn(procTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_LONG:
                    createLongColumn(procTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_REAL:
                    createRealColumn(procTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_STRING:
                    createStringColumn(procTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                    createConceptColumn(procTable, pd);
                    break;
                }
            }

         // Update the hierarchy
            updateTableHierarchy(procTable);

            //If Migration intended
            Map<String, AttrColumnInfo> dbTableInfo = null;
            if (conn != null) {
                dbTableInfo = getTableInfoFromDB(conn, procTable.getName());
                // If table exists
                if ((dbTableInfo != null) && (dbTableInfo.isEmpty() == false)) {
                    // Initially, mark the table as existing and unchanged
                    procTable.state = JdbcTable.State.UNCHANGED;
                    // Remove base concept type fields, for comparison
                    //dbTableInfo = filterBaseTypes(dbTableInfo, baseConceptTypeFields);
                    if (!compare(dbTableInfo, procTable, "Concept " + proc.getName())) {
                        alterationPossible = false;
                    }
                }
                Map<String, AttrColumnInfo> secondaryTableInfo = null;
                for (Iterator itr = procTable.getSecondaryTableInfoList().iterator(); itr.hasNext(); ) {
                    JdbcTable.SecondaryTableInfo secondaryTable = (JdbcTable.SecondaryTableInfo)itr.next();
                    secondaryTableInfo = getTableInfoFromDB(conn, secondaryTable.tableName);
                    if ((secondaryTableInfo != null) && (secondaryTableInfo.isEmpty() == false)) {
                        secondaryTable.state = JdbcTable.State.UNCHANGED;
                        if (!compare(secondaryTableInfo, secondaryTable, procTable)) {
                            String error= "--* For Concept " + proc.getName() + " complex field " + secondaryTable.fieldName + " has incompatible changes";
                            migrationErrors.append(be_jdbcstoreVersion.line_separator + error);
                            alterationPossible = false;
                        }
                    }
                }
            }

            // Add concept to the final list
            schemaFile.addTable(procTable);
        }

        return alterationPossible;
    }

    /**
     * @param conn
     * @return
     * @throws Exception
     */
    private boolean generateConceptTables(Connection conn) throws Exception {
        boolean alterationPossible = true;
        //errStream.println("Ontology="+ontology.getClass().getName()+"  Concepts["+ontology.getConcepts().size()+
        //      "]="+ontology.getConcepts().getClass().getName());
        Collection<Concept> concepts = ontology.getConcepts();
        List<Concept> conceptList = new ArrayList<Concept>();
        for (Concept cept : concepts) {
            if (conceptList.contains(cept) == false) {
                conceptList.add(cept);
            }
        }

        // Sorting is done to reflect class hierarchy, such that super
        // concept is processed first. Others are sorted by name only.
        Collections.sort(conceptList, new EntityNameComparator());

        Iterator allConcepts = conceptList.iterator();
        while (allConcepts.hasNext()) {
            Concept cept = (Concept) allConcepts.next();
            String fullPath = cept.getFullPath();
            String hasBackingStore = jdbcUtil.getEntityDeploymentProperty(cept, JdbcUtil.PROP_HASBACKINGSTORE, "true");
            if ((hasBackingStore != null) && (hasBackingStore.equalsIgnoreCase("false"))) {
                outStream.println(" Concept " + fullPath + " has backing store flag set to false - schema not generated");
                continue;
            }
            String cacheMode = jdbcUtil.getEntityDeploymentProperty(cept, JdbcUtil.PROP_CACHEMODE, this.clusterMode);
            if ((optimize == true) && (cacheMode.equalsIgnoreCase("memory"))) {
                outStream.println(" Concept " + fullPath + " is not using cache based object management - schema not generated");
                continue;
            }

            JdbcTable conceptTable = new JdbcTable(cept);
            Concept superConcept = cept.getSuperConcept();
            if (superConcept != null) {
                String superTable = jdbcUtil.getAssignedJdbcTable(superConcept);
                if (superTable == null) {
                    // This should not be the case under normal circumstances
                    // since super type should have already been processed
                    String superType = jdbcUtil.mangleJdbcType(superConcept, jdbcUtil.name2JdbcType(superConcept));
                    superTable = jdbcUtil.name2JdbcTable(superType);
                }
                //outStream.println("Concept="+cept.getName()+ " Table=" + conceptTable.getName() +
                //        "  SuperConcept="+superConcept.getName()+ " SuperTable="+superTable);
                conceptTable.setSuperTableName(superTable);
            } else {
                conceptTable.setSuperTableName(JdbcType.BASE_CONCEPT_TYPE);
            }

            // Go through the properties
            Collection propDefs = cept.getLocalPropertyDefinitions();
            for(Iterator it = propDefs.iterator(); it.hasNext();) {
                PropertyDefinition pd = (PropertyDefinition)it.next();
                switch(pd.getType()) {
                    case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                        createBooleanColumn(conceptTable, pd);
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                        createIntegerColumn(conceptTable, pd);
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                        createDateTimeColumn(conceptTable, pd);
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_LONG:
                        createLongColumn(conceptTable, pd);
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_REAL:
                        createRealColumn(conceptTable, pd);
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_STRING:
                        createStringColumn(conceptTable, pd);
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                    case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                        createConceptColumn(conceptTable, pd);
                        break;
                }
            }

            if (cept.getStateMachines() != null && cept.getStateMachines().size() > 0) {
                Iterator allSM= cept.getStateMachines().iterator();
                if (allSM != null) {
                    while (allSM.hasNext()) {
                        StateMachine sm = (StateMachine) allSM.next();
                        createStateMachineColumn(conceptTable, sm);
                    }
                }
            }

            // Update the hierarchy
            updateTableHierarchy(conceptTable);

            //If Migration intended
            Map<String, AttrColumnInfo> dbTableInfo = null;
            if (conn != null) {
                dbTableInfo = getTableInfoFromDB(conn, conceptTable.getName());
                // If table exists
                if ((dbTableInfo != null) && (dbTableInfo.isEmpty() == false)) {
                    // Initially, mark the table as existing and unchanged
                    conceptTable.state = JdbcTable.State.UNCHANGED;
                    // Remove base concept type fields, for comparison
                    //dbTableInfo = filterBaseTypes(dbTableInfo, baseConceptTypeFields);
                    if (!compare(dbTableInfo, conceptTable, "Concept " + cept.getName())) {
                        alterationPossible = false;
                    }
                }
                Map<String, AttrColumnInfo> secondaryTableInfo = null;
                for (Iterator itr = conceptTable.getSecondaryTableInfoList().iterator(); itr.hasNext(); ) {
                    JdbcTable.SecondaryTableInfo secondaryTable = (JdbcTable.SecondaryTableInfo)itr.next();
                    secondaryTableInfo = getTableInfoFromDB(conn, secondaryTable.tableName);
                    if ((secondaryTableInfo != null) && (secondaryTableInfo.isEmpty() == false)) {
                        secondaryTable.state = JdbcTable.State.UNCHANGED;
                        if (!compare(secondaryTableInfo, secondaryTable, conceptTable)) {
                            String error= "--* For Concept " + cept.getName() + " complex field " + secondaryTable.fieldName + " has incompatible changes";
                            migrationErrors.append(be_jdbcstoreVersion.line_separator + error);
                            alterationPossible = false;
                        }
                    }
                }
            }

            // Add concept to the final list
            schemaFile.addTable(conceptTable);
            // FIX THIS - generateNestedTables(cept, conceptTable);

            // Now create DDL for the state machine
            if (cept.getStateMachines() != null && cept.getStateMachines().size() > 0) {
                if (!generateStateMachineTable(cept, conn)) {
                    alterationPossible = false;
                }
            }

            // Prevent DVM table generation (DVM added to ontology to support cache only queries
            /*
            if (cept.isMetric()) {
                createMetricDVMTable(conn, cept);
            }
            */
        }

        /* VWC - No need to make this call anymore.  All SM should have a parent concept
        if (!generateStandAloneStateMachineTables(conn)) {
            alterationPossible = false;
        }
        */

        return alterationPossible;
    }

    private boolean createMetricDVMTable(Connection conn, Concept cept) throws Exception {
        boolean alterationPossible = true;
        cept.enableMetricTracking();
        JdbcTable conceptTable = new JdbcTable(cept);
        conceptTable.setIsDVM(true);

        // No inheritance for metric
        conceptTable.setSuperTableName(JdbcType.BASE_CONCEPT_TYPE);

        // Go through the properties
        Collection propDefs = cept.getLocalPropertyDefinitions();
        for(Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition)it.next();
            switch (pd.getType()) {
                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                    createBooleanColumn(conceptTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                    createIntegerColumn(conceptTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                    createDateTimeColumn(conceptTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_LONG:
                    createLongColumn(conceptTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_REAL:
                    createRealColumn(conceptTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_STRING:
                    createStringColumn(conceptTable, pd);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                    createConceptColumn(conceptTable, pd);
                    break;
            }
        }

        // Update the hierarchy
        updateTableHierarchy(conceptTable);

        //If Migration intended
        Map<String, AttrColumnInfo> dbTableInfo = null;
        if (conn != null) {
            dbTableInfo = getTableInfoFromDB(conn, conceptTable.getName());
            // If table exists
            if ((dbTableInfo != null) && (dbTableInfo.isEmpty() == false)) {
                // Initially, mark the table as existing and unchanged
                conceptTable.state = JdbcTable.State.UNCHANGED;
                if (!compare(dbTableInfo, conceptTable, "Concept " + cept.getName())) {
                    alterationPossible = false;
                }
            }
        }
        schemaFile.addTable(conceptTable);
        cept.disableMetricTracking();
        return alterationPossible;
    }

    private void updateTableHierarchy(JdbcTable jdbcTable) throws Exception {
        if (jdbcTable.getSuperTableName() == null) {
            return;
        }
        JdbcTable superTable = schemaFile.getTable(jdbcTable.getSuperTableName());
        if (superTable != null) {
            jdbcTable.setSuperTable(superTable);
            updateTableHierarchy(superTable);
        }
        else {
            outStream.println("  Failed to locate " + jdbcTable.name + "'s super table : " + jdbcTable.getSuperTableName());
        }
    }

    private void updateTableHirarchy() throws Exception {
        Iterator alltables = schemaFile.getTables() ;
        JdbcTable atab;
        while (alltables.hasNext()) {
            atab=(JdbcTable) alltables.next() ;
            JdbcTable superTable = null;
            String superTableName = atab.getSuperTableName();
            if (superTableName == null) {
                continue;
            }

            superTable = schemaFile.getTable(superTableName);
            if (superTable != null) {
                atab.setSuperTable(superTable);
            }
            else {
                outStream.println("  Failed to locate " + atab.name + "'s super table : " + superTableName);
            }
        }
    }

    /**
     * Used to compare primary table schemas
     */
    private boolean compare(Map<String, AttrColumnInfo> dbTableInfo, JdbcTable entityTable, String name) {
        boolean ret = true;
        for (String memberName : dbTableInfo.keySet()) {
            AttrColumnInfo dbInfo = dbTableInfo.get(memberName);
            //errStream.println("COMPARE:" + entityTable.getName() + ":=" + entityTable.hashCode() + " - " + dbInfo.attrName);
            JdbcTable.MemberDef info = entityTable.findMember(memberName.toUpperCase());
            if (info == null) {
                // Field is deleted - Represent as a DELETED member
                info = new JdbcTable.MemberDef(dbInfo.attrName, dbInfo.attrType);
                info.state = JdbcTable.MemberDef.Deleted;
                entityTable.addMember(memberName, info);
                entityTable.state = JdbcTable.State.MODIFIED;
            } else {
                if (info.state == JdbcTable.MemberDef.Deleted) {
                    // This field is marked as deleted (probably through parent);
                    // so do not process any further.
                    continue;
                }
                if (RDBMSType.sSqlType.columnTypeMatches(info.memberType, info.size, dbInfo.attrType, dbInfo.length)  &&!isStringDTlengthChanged(dbInfo, info)) {
                    // Mark as unchanged
                    info.state = JdbcTable.MemberDef.NotChanged;
                } else if (RDBMSType.sSqlType.columnTypeConvertable(info.memberType, info.size, dbInfo.attrType, dbInfo.length)) {
                    // Mark as changed
                    info.state = JdbcTable.MemberDef.Altered;
                    entityTable.state = JdbcTable.State.MODIFIED;
                } else {
                    // Mark as incompatible
                    String updatedType = RDBMSType.sSqlType.getColumnTypeForPrimitiveType(info.memberType, info.size);
                    String error= "--* For " + name + " field " + memberName + " type changed from " + dbInfo.attrType+ "("+dbInfo.length+ ")" + " to " + updatedType.toUpperCase();
                    info.state = JdbcTable.MemberDef.Incompatible;
                    entityTable.state = JdbcTable.State.MODIFIED;
                    migrationErrors.append(be_jdbcstoreVersion.line_separator + error);
                    ret = false;
                }
            }
        }

        // Find if the type should be marked as modified, because some attribute added
        if (entityTable.state != JdbcTable.State.MODIFIED) {
            // FIXME: No harm in marking it as changed, will force unnecessary processing
            entityTable.state = JdbcTable.State.MODIFIED;
        }
        return ret;
    }

    /*
     * Compares the column length for String datatype
     * If the column length is not changed the MemberDef has 0 Length
     */
    private boolean isStringDTlengthChanged(AttrColumnInfo dbInfo, JdbcTable.MemberDef info) {
        if (info.memberType.equals(JdbcType.TYPE_STRING.getName()) && info.size != 0) {
            return Integer.compare(info.size, dbInfo.length) != 0;
        }
        return false;
    }

    /**
     * Used to compare secondary table schemas
     */
    private boolean compare(Map<String, AttrColumnInfo> dbTableInfo, JdbcTable.SecondaryTableInfo secTableInfo, JdbcTable parentTable) {
        boolean ret = true;
        String tableOf = null; // Used to check if the type is an array type
        AttrColumnInfo dbColumnInfo = null;
        JdbcTable.MemberDef info = parentTable.findComplexMember(secTableInfo.fieldName.toUpperCase());
        JdbcTable systemTable = (JdbcTable) systemTableMap.get(info.memberType);
        //errStream.println(secTableInfo.tableName + " name:" + systemTable.getName() + " #" + systemTable.getSuperTableName());
        if (systemTable != null && systemTable.getTableType() != null) {
            dbColumnInfo = dbTableInfo.get("valPid$".toUpperCase());
            if (dbColumnInfo != null &&
                RDBMSType.sSqlType.columnTypeMatches("long", 0, dbColumnInfo.attrType, dbColumnInfo.length)) {
                // Mark as unchanged
                info.state = JdbcTable.MemberDef.NotChanged;
            } else {
                info.state = JdbcTable.MemberDef.Incompatible;
                return false;
            }
            tableOf = systemTable.getTableType().toUpperCase();
            systemTable = (JdbcTable) systemTableMap.get(tableOf);
        }
        if (systemTable != null) {
            List systemMembers = systemTable.getMembers();
            // Handle members
            for (int k = 0; k < systemMembers.size(); k++) {
                MemberDef sysDef = (MemberDef) systemMembers.get(k);
                if (sysDef.memberType.equals(JdbcType.DATETIME_TYPE) || sysDef.memberType.equals(JdbcType.BASE_ENTITYREF_TYPE)) {
                    JdbcTable stbl = (JdbcTable) systemTableMap.get(sysDef.memberType);
                    if (stbl != null) {
                        List sMembers = stbl.getMembers();
                        // process the columns of the system table used
                        for (int l = 0; l < sMembers.size(); l++) {
                            MemberDef sDef = (MemberDef) sMembers.get(l);
                            dbColumnInfo = dbTableInfo.get(sDef.memberName.toUpperCase());
                            if (dbColumnInfo != null &&
                                RDBMSType.sSqlType.columnTypeMatches(sDef.memberType, sDef.size, dbColumnInfo.attrType, dbColumnInfo.length)) {
                                // Mark as unchanged
                                info.state = JdbcTable.MemberDef.NotChanged;
                            } else {
                                info.state = JdbcTable.MemberDef.Incompatible;
                                return false;
                            }
                        }
                    }
                } else {
                    dbColumnInfo = dbTableInfo.get(sysDef.memberName.toUpperCase());
                    //errStream.println(secTableInfo.tableName + " compare:" + sysDef.memberName +
                    //        " system:" + sysDef.memberType + " database:" + dbColumnInfo.attrType);
                    if (dbColumnInfo != null &&
                        RDBMSType.sSqlType.columnTypeMatches(sysDef.memberType, sysDef.size, dbColumnInfo.attrType, dbColumnInfo.length)) {
                        // Mark as unchanged
                        info.state = JdbcTable.MemberDef.NotChanged;
                    } else {
                        info.state = JdbcTable.MemberDef.Incompatible;
                        return false;
                    }
                }
            }
            // Handle complex members
            List complexMembers = systemTable.getComplexMembers();
            for (int k = 0; k < complexMembers.size(); k++) {
                MemberDef sysDef = (MemberDef) complexMembers.get(k);
                //compare(sysDef, buf, sysDef.memberName, addComma);
                JdbcTable innerTable = (JdbcTable) systemTableMap.get(sysDef.memberType);
                List innerMembers = innerTable.getMembers();
                for (int l = 0; l < innerMembers.size(); l++) {
                    MemberDef innerDef = (MemberDef) innerMembers.get(l);
                    dbColumnInfo = dbTableInfo.get(innerDef.memberName.toUpperCase());
                    if (dbColumnInfo != null && RDBMSType.sSqlType.columnTypeMatches(innerDef.memberType, innerDef.size,
                            dbColumnInfo.attrType, dbColumnInfo.length)) {
                        if (innerDef.memberType.equals(JdbcType.TYPE_STRING.getName())
                                && info.size !=0 && Integer.compare(info.size, dbColumnInfo.length) != 0) {
                            ret = info.size > dbColumnInfo.length;
                            info.state = ret ? JdbcTable.MemberDef.SecondaryAlteredCompatible
                                    : JdbcTable.MemberDef.SecondaryAlteredIncompatible;
                        } else {
                            // Mark as unchanged
                            info.state = JdbcTable.MemberDef.NotChanged;
                        }
                    } else if (innerDef.memberType.toUpperCase().startsWith("T_")) {
                        // Ignore!
                    }
                    else {
                        info.state = JdbcTable.MemberDef.Incompatible;
                        return false;
                    }
                }
            }
        }
        // If the type is not prefix with "T_", it must be a primitive type
        else if (tableOf.indexOf("T_") != 0) {
            // It must be of basic type: not using the fieldName, use 'val' as the field name
            dbColumnInfo = dbTableInfo.get("val".toUpperCase());
            if (dbColumnInfo != null &&
                RDBMSType.sSqlType.columnTypeMatches(tableOf, info.size, dbColumnInfo.attrType, dbColumnInfo.length)) {
                // Mark as unchanged
                info.state = JdbcTable.MemberDef.NotChanged;
            } else if (RDBMSType.sSqlType.columnTypeConvertable(tableOf, info.size, dbColumnInfo.attrType, dbColumnInfo.length)) {
                // Mark as changed
                info.state = JdbcTable.MemberDef.Altered;
                return false;
            } else {
                info.state = JdbcTable.MemberDef.Incompatible;
                return false;
            }
        } else {
            //errStream.println("WARN " + parentTable.getName() + " compare:" + secTableInfo.tableName + " system:" + ((systemTable==null)?"NULL":systemTable.getName()));
        }
        return ret;
    }

    private static class AttrColumnInfo {
        String attrName;
        String attrType;
        public int length = -1;

        public AttrColumnInfo(String columnName, String columnType) {
            // Get rid of any schema name prefix
            int dotIndex = columnName.indexOf('.');
            if (dotIndex < 0) {
                attrName = columnName;
            }
            else {
                attrName = columnName.substring(dotIndex+1);
            }
            attrType = columnType;
        }

        public String toString() {
            return attrName + "[type=" + attrType + "]";
        }
    }

    /**
     * This method connects to the database and finds the structure of a given type, as it exists
     * in database today. If the type is not found in database, it is assumed to be new, and no
     * information is returned
     */
    private Map<String, AttrColumnInfo> getTableInfoFromDB(Connection conn, String table_name) {
        Map<String, AttrColumnInfo> typeMap = null;
        ResultSet rs = null;
        // Get the table descriptor
        try {
            DatabaseMetaData meta = conn.getMetaData();
            //errStream.println("TableInfoFromDB:" + table_name.toUpperCase());

            if (RDBMSType.sRuntimeType == RDBMSType.ORACLE) {
                rs = meta.getColumns(null, schemaOwner, table_name.toUpperCase(), null); // ORACLE
            } else {
                rs = meta.getColumns(null, schemaOwner, table_name.toUpperCase(), null); // MSSQL and others???
            }
            String attrColumnName, attrTypeName;
            typeMap = new HashMap<String, AttrColumnInfo>();
            while (rs.next()) {
                attrColumnName = rs.getString("COLUMN_NAME").toUpperCase();
                attrTypeName = rs.getString("TYPE_NAME");
                /*
                outStream.println(
                         "  CAT:"+rs.getString("TABLE_CAT")
                       + "  SCH:"+rs.getString("TABLE_SCHEM")
                       + "  TBL:"+rs.getString("TABLE_NAME")
                       + "  COL:"+rs.getString("COLUMN_NAME")
                       + "  TYP:"+rs.getString("TYPE_NAME")
                       + "  DTP:"+rs.getInt("DATA_TYPE")
                       + "  SIZ:"+rs.getInt("COLUMN_SIZE")
                       + "  POS:"+rs.getInt("ORDINAL_POSITION")
                       + "  NUL:"+rs.getString("IS_NULLABLE")
                       + "  DIG:"+rs.getInt("DECIMAL_DIGITS"));
                */
                AttrColumnInfo tInfo= new AttrColumnInfo(attrColumnName,attrTypeName);
                tInfo.length = rs.getInt("COLUMN_SIZE");

                typeMap.put(attrColumnName, tInfo);
            }
        } catch (SQLException sqe) {
            outStream.println("Error: " + sqe.getMessage());
            outStream.println(" Table " + table_name + " not found in database. Treating it as new.");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }
        return typeMap;
    }

    protected Connection getConn() throws Exception {
        String dbUrl= null;
        String dbDrv= null;
        try {
            dbUrl= env.getProperty("be.jdbc.schemamigration.url");
            if (dbUrl != null)
                dbUrl = dbUrl.trim();
            String dbUser = env.getProperty("be.jdbc.schemamigration.user");
            if (dbUser != null)
                dbUser = dbUser.trim();
            String dbPswd = env.getProperty("be.jdbc.schemamigration.pswd");
            if (dbPswd != null)
                dbPswd = CryptoUtil.decryptIfEncrypted(dbPswd.trim());
            schemaOwner = env.getProperty("be.jdbc.schemamigration.schema");
            if (schemaOwner != null)
                schemaOwner = schemaOwner.trim();
            if (dbUrl.indexOf(RDBMSType.RDBMS_TYPE_NAME_ORACLE.toLowerCase()) > 0) {
                dbDrv = "oracle.jdbc.OracleDriver";
            } else if (dbUrl.indexOf("tibcosoftwareinc") > 0 && dbUrl.indexOf(RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.toLowerCase()) > 0) {
            	dbDrv = "tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver";
            } else if (dbUrl.indexOf(RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.toLowerCase()) > 0) {
            	dbDrv = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            } else if (dbUrl.indexOf(RDBMSType.RDBMS_TYPE_NAME_DB2.toLowerCase()) > 0) {
                dbDrv = "com.ibm.db2.jcc.DB2Driver";
            } else if (dbUrl.indexOf(RDBMSType.RDBMS_TYPE_NAME_MYSQL.toLowerCase()) > 0) {
                dbDrv = "com.mysql.jdbc.Driver";
            } else if (dbUrl.indexOf(RDBMSType.RDBMS_TYPE_NAME_POSTGRES.toLowerCase()) > 0) {
                dbDrv = "org.postgresql.Driver";
            }
            else {
                dbDrv = "be.jdbc.schemamigration.driver";
            }

            Class.forName(dbDrv);
            
            Connection conn;
            JdbcSSLConnectionInfo sslConnectionInfo = JdbcSSLConnectionInfo.createConnectionInfo(dbUser, dbPswd, dbUrl, dbDrv);
            if (sslConnectionInfo.initializeConfigs(env)) {
            	sslConnectionInfo.loadSecurityProvider();
            	conn = DriverManager.getConnection(dbUrl, sslConnectionInfo.getProperties());
            }
            else {
            	conn = DriverManager.getConnection(dbUrl, dbUser, dbPswd);
            }
            ConnectionPool.unlockDDConnection(conn);

            if (conn != null) {
                outStream.println("Connected to database: " + dbUrl);
            }
            return conn;
        } catch (Exception e) {
            if (dbUrl != null) {
                outStream.println("Failed connecting to database: " + dbUrl);
                outStream.println("  Error:" + e.getMessage());
            }
        }
        return null;
    }
    
    private boolean generateStateMachineTable(Concept cept, Connection conn) throws Exception {
        boolean alterationPossible = true;
        List stateMachines = cept.getStateMachines();
        if (stateMachines != null) {
            for (int i = 0; i < stateMachines.size(); i++) {
                StateMachine sm = (StateMachine) stateMachines.get(i);
                if (!generateStateMachineTable(conn, sm)) {
                    alterationPossible = false;
                }
            }
        }
        return alterationPossible;
    }

    private boolean generateStateMachineTable(Connection conn, StateMachine sm) throws Exception {
        boolean alterationPossible = true;
        JdbcTable smTable = new JdbcTable(sm);

        // State machine does not have object hierarchy therefore its super type is always the same
        smTable.setSuperTableName(JdbcType.BASE_CONCEPT_TYPE);

        List<String> stateNames = new ArrayList<String>();
        getStateMachineStateNames(sm.getMachineRoot(), null, stateNames);
        for (int j = 0; j < stateNames.size(); j++) {
            smTable.addMember(stateNames.get(j), JdbcType.TYPE_INTEGER.getName());
        }

        // Update the hierarchy
        updateTableHierarchy(smTable);

        // Find is this state-machine table exists in database, if Migration intended
        Map<String, AttrColumnInfo> dbTableInfo = null;
        if (conn != null) {
            dbTableInfo = getTableInfoFromDB(conn, smTable.getName());
            // If table exists
            if ((dbTableInfo != null) && (dbTableInfo.isEmpty() == false)) {
                smTable.state = JdbcTable.State.UNCHANGED;
                // Remove base concept type fields, for comparison
                //dbTableInfo = filterBaseTypes(dbTableInfo, baseConceptTypeFields);
                if (!compare(dbTableInfo, smTable, "State-Machine " + sm.getName())) {
                    alterationPossible = false;
                }
            }
        }

        schemaFile.addTable(smTable);
        return alterationPossible;
    }

    private void getStateMachineStateNames(StateComposite stateComposite, String namePrefix, List<String> stateNames) {
        String prefix = null;
        if (namePrefix != null) {
            prefix = namePrefix + "$";
        } else {
            stateNames.add(stateComposite.getName());
        }
        List states = stateComposite.getEntities();
        if (states != null) {
            for (int i = 0; i < states.size(); i++) {
                State state = (State)states.get(i);
                if (state instanceof StateComposite) {
                    StateComposite comp = (StateComposite)state;
                    String nextPrefix = null;
                    if (prefix != null) {
                        nextPrefix = prefix + comp.getName();
                    } else {
                        nextPrefix = comp.getName();
                    }
                    stateNames.add(nextPrefix);
                    if (comp.isConcurrentState()) {
                        List regions = comp.getRegions();
                        if (regions != null) {
                            Iterator rit = regions.iterator();
                            while (rit.hasNext()) {
                                StateComposite region = (StateComposite) rit.next();
                                stateNames.add(nextPrefix + "$" + region.getName());
                                getStateMachineStateNames(region, nextPrefix + "$" + region.getName(), stateNames);
                            }
                        }
                    } else {
                        getStateMachineStateNames(comp, nextPrefix, stateNames);
                    }
                } else {
                    if (prefix == null) {
                        stateNames.add(state.getName());
                    } else {
                        stateNames.add(prefix + state.getName());
                    }
                }
            }
        }
    }

    private void createStringColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        // CDD stores the max length as <max-size>...</max-size>, so we will look for it first and
        // then look for the legacy maxLength
        String sizeAsStr = jdbcUtil.getAttributeDeploymentProperty(pd, JdbcUtil.PROP_MAXSIZE, null);
        if (sizeAsStr == null || sizeAsStr.trim().length() == 0) {
            sizeAsStr = jdbcUtil.getAttributeDeploymentProperty(pd, JdbcUtil.PROP_MAXLENGTH, null);
        }
        //TODO: Was this ever used [Answer:NO - Probably in the future]
        //String columnName = jdbcUtil.getAttributeDeploymentProperty(pd, JdbcUtil.PROP_COLUMNNAME, null);
        //errStream.println("Concept type=" + conceptTable.getName() + ", pd=" + pd.getName() + ", maxLen=" + sizeAsStr);
        int size = 0;
        if ((sizeAsStr != null) && (sizeAsStr.trim().length() > 0)) {
            try {
                size = Integer.valueOf(sizeAsStr);
            } catch (Exception ex) {
                errStream.println("Ignoring maxLength for Property " + pd.getName() + ", concept =" + conceptTable.getEntityName() + ", exception=" + ex);
                size = 0;
            }
        }
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.STRING_HIST_TYPE, false, size);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.TYPE_STRING.getName(), false, size);
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.STRING_COL_TYPE, true, size);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.STRING_COL_HIST_TYPE, true, size);
        }
    }

    private void createIntegerColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.INTEGER_HIST_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.TYPE_INTEGER.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.INTEGER_COL_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.INTEGER_COL_HIST_TYPE);
        }
    }

    private void createLongColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.LONG_HIST_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.TYPE_LONG.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.LONG_COL_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.LONG_COL_HIST_TYPE);
        }
    }

    private void createRealColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.DOUBLE_HIST_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.TYPE_DOUBLE.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.DOUBLE_COL_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.DOUBLE_COL_HIST_TYPE);
        }
    }

    private void createBooleanColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BOOLEAN_HIST_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.TYPE_BOOLEAN.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BOOLEAN_COL_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BOOLEAN_COL_HIST_TYPE);
        }
    }

    private void createDateTimeColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.DATETIME_HIST_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.TYPE_DATETIME.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.DATETIME_COL_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.DATETIME_COL_HIST_TYPE);
        }
    }

    private void createConceptColumn(JdbcTable conceptTable, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BASE_ENTITYREF_HIST_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BASE_ENTITYREF_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BASE_ENTITYREF_TABLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptTable.addMember(pd.getName(), JdbcType.BASE_ENTITYREF_COL_HIST_TYPE);
        }
    }

    private void createStateMachineColumn(JdbcTable conceptTable, StateMachine sm) {
        //conceptTable.addMember(sm.getOwnerConcept().getName() + "$" + sm.getName(), JdbcType.BASE_ENTITYREF_TYPE);
        //conceptTable.addMember(jdbcUtil.getSMPDName(sm), JdbcType.BASE_ENTITYREF_TYPE);
        conceptTable.addMember(jdbcUtil.getSMPDName(sm), JdbcType.BASE_ENTITYREF_TYPE);
    }

    protected void loadBaseTypeDefinitions(String baseTypeFile) {
        if (baseTypeFile == null) {
            outStream.println("Cannot locate base type schema file");
        }
        try {
            InputStream is = new FileInputStream(baseTypeFile);
            XiParser parser = XiParserFactory.newInstance();
            // open up the xml file
            final XiNode doc = parser.parse(new InputSource(is));
            // basetypes element - root element
            XiNode parent = doc.getFirstChild();
            // get a list of 'type' element
            for(Iterator itr = XiChild.getIterator(parent); itr.hasNext();) {
                String name = null;
                String superType = null;
                String arrayOf = null;
                String multiDimension = null;
                // 'type' element
                XiNode typeNode = (XiNode) itr.next();
                // create a new jdbc type - maintain in a global list
                // 'name' field of 'type' element
                XiNode valueNode = typeNode.getAttribute(ExpandedName.makeName("name"));
                name = valueNode.getStringValue();
                //outStream.println("Type name : " + name);
                valueNode = typeNode.getAttribute(ExpandedName.makeName("extends"));
                if (valueNode != null) {
                    superType = valueNode.getStringValue();
                    //outStream.println("Super type : " + superType);
                }
                valueNode = typeNode.getAttribute(ExpandedName.makeName("arrayof"));
                if (valueNode != null) {
                    arrayOf = valueNode.getStringValue();
                    //outStream.println("Array of type : " + arrayOf);
                }
                valueNode = typeNode.getAttribute(ExpandedName.makeName("multidimension"));
                if (valueNode != null) {
                    multiDimension = valueNode.getStringValue();
                    //outStream.println("Multi-dimension : " + multiDimension);
                }
                JdbcType jdbcType = new JdbcType(name, false);
                // All of these are abstract tables which are used for definitions only
                // we don't actually create them as stand alone tables in the db
                JdbcTable jdbcTable = new JdbcTable(name, true);
                if (superType != null) {
                    jdbcType.setSuperType(superType.toUpperCase());
                    jdbcTable.setSuperTableName(superType.toUpperCase());
                }
                if (arrayOf != null) {
                    jdbcType.setTableType(arrayOf);
                    jdbcTable.setTableType(arrayOf);
                }
                if (multiDimension != null && multiDimension.equalsIgnoreCase("true")) {
                    jdbcTable.setIsMultiDimension(true);
                }
                systemTypeMap.put(name.toUpperCase(), jdbcType);
                systemTableMap.put(name.toUpperCase(), jdbcTable);
                for(Iterator itrAttr = XiChild.getIterator(typeNode, ExpandedName.makeName("attribute")); itrAttr.hasNext();) {
                    name = null;
                    String attrType = null;
                    String isArray = null;
                    String size = null;
                    int iSize = 0;
                    XiNode attrNode = (XiNode) itrAttr.next();
                    valueNode = attrNode.getAttribute(ExpandedName.makeName("name"));
                    name = valueNode.getStringValue();
                    //outStream.println("   attr name : " + name);
                    valueNode = attrNode.getAttribute(ExpandedName.makeName("type"));
                    attrType = valueNode.getStringValue().toUpperCase();
                    //outStream.println("   type : " + attrType);
                    valueNode = attrNode.getAttribute(ExpandedName.makeName("isarray"));
                    boolean bArray = false;
                    if (valueNode != null) {
                        isArray = valueNode.getStringValue();
                        if (isArray.equalsIgnoreCase("true")) {
                            bArray = true;
                        }
                        //outStream.println("   isarray : " + isArray);
                    }
                    valueNode = attrNode.getAttribute(ExpandedName.makeName("size"));
                    if (valueNode != null) {
                        size = valueNode.getStringValue();
                        iSize = Integer.valueOf(size);
                        //outStream.println("   size : " + size);
                    }
                    jdbcType.addMember(name, attrType, bArray, iSize);
                    jdbcTable.addMember(name, attrType, bArray, iSize);
                }
                /* Enable these for debugging */
                /*
                StringBuffer sb1 = jdbcType.toStringBuffer(false);
                StringBuffer sb2 = jdbcType.toStringBuffer();
                StringBuffer sb3 = jdbcTable.toStringBuffer();
                outStream.println(sb1);
                outStream.println(sb2);
                outStream.println(sb3);
                */
                schemaFile.addTable(jdbcTable);
            }
            //generate JdbcType for each base type and keep them in a lookup table
            //during generateEventTables and generateConceptTables, we will need to
            //set the parent types using the base types
            //may need to extend jdbctype to add isarray attribute
            //then table generation will use the jdbctype to generate
            //table generation will walk the inheritance chain
            //during chain walking, create a list of external tables with jdbctypes
            //after main table is created, create the external tables
            //it's a recursive operation.
            //when a type a created, it points to its parent types.
            //during table generation, it creates all the primary columns by walking
            //the entire inheritance tree.
            //at the same time, maintain a list of external tables/types
            //then go through each external table and see it needs to create more external tables.
            //as each table get created, remove it from the list.
        } catch(Exception e) {
            throw new RuntimeException("Cannot process base type schema file : " + baseTypeFile, e);
        }
    }
    // Need to have meta data saved to maintain parent and child tables dependencies

    protected void loadKeywordMap(String keywordFile) {
        if (keywordFile == null) {
            outStream.println("Cannot locate base keyword file");
        }
        try {
            InputStream is = new FileInputStream(keywordFile);
            XiParser parser = XiParserFactory.newInstance();
            // open up the xml file
            final XiNode doc = parser.parse(new InputSource(is));
            // basetypes element - root element
            XiNode parent = doc.getFirstChild();
            // get a list of 'type' element
            for (Iterator itr = XiChild.getIterator(parent); itr.hasNext();) {
                String name = null;
                String mapName = null;
                // 'type' element
                XiNode typeNode = (XiNode) itr.next();
                // create a new jdbc type - maintain in a global list
                // 'name' field of 'type' element
                XiNode valueNode = typeNode.getAttribute(ExpandedName.makeName("name"));
                name = valueNode.getStringValue();
                //outStream.println("Type name : " + name);
                valueNode = typeNode.getAttribute(ExpandedName.makeName("mapname"));
                mapName = valueNode.getStringValue();
                //outStream.println("Map name : " + mapName);

                keywordMap.put(name.toLowerCase(), mapName);
            }
        } catch(Exception e) {
            throw new RuntimeException("Cannot process keyword map file : " + keywordFile, e);
        }
    }

    private class EntityNameComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            if ((o1 instanceof Entity) && (o2 instanceof Entity)) {
                Entity e1 = (Entity) o1;
                Entity e2 = (Entity) o2;
                String s1 = getHierarchicalName(e1);
                String s2 = getHierarchicalName(e2);
                if (s1.equalsIgnoreCase(s2)) {
                    return e1.getFullPath().compareTo(e2.getFullPath());
                } else {
                    return s1.toLowerCase().compareTo(s2.toLowerCase());
                }
            } else {
                outStream.println("Can't compare objects besides Entities :" + o1.getClass().getName());
                String s1 = String.valueOf(o1);
                String s2 = String.valueOf(o2);
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        }
    }

    private String getHierarchicalName(Entity entity) {
        if (entity instanceof Event) {
            return getHierarchicalName(null, (Event) entity);
        }
        if (entity instanceof Concept) {
            return getHierarchicalName(null, (Concept) entity);
        }
        return entity.getName();
    }

    private String getHierarchicalName(String path, Event event) {
        String pathString = (path == null) ? "" : "." + path;
        if (event.getSuperEvent() == null) {
            return event.getName() + pathString;
        }
        else {
            return getHierarchicalName(event.getName() + pathString, event.getSuperEvent());
        }
    }

    private String getHierarchicalName(String path, Concept cept) {
        String pathString = (path == null) ? "" : "." + path;
        if (cept.getSuperConcept() == null) {
            return cept.getName() + pathString;
        }
        else {
            return getHierarchicalName(cept.getName() + pathString, cept.getSuperConcept());
        }
    }

    // Allow reuse of JdbcDeploment in studio for various projects
    protected void clearJDBCUtils() {
        if (jdbcUtil != null) {
            jdbcUtil.clear();
        }
    }
}
