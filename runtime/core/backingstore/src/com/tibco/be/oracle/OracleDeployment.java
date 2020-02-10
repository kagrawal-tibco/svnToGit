package com.tibco.be.oracle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.StructMetaData;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.StructDescriptor;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.util.ModelNameUtil;
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
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.Workspace;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.nodes.Element;

public class OracleDeployment {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    public final static String PROP_CACHEMODE = "mode";
    public final static String PROP_HASBACKINGSTORE = "hasBackingStore";
    public final static String PROP_NESTEDTABLENAME = "nestedTableName";
    public final static String PROP_TABLENAME = "tableName";
    public final static String PROP_PROPERTYNAME = "property";
    public final static String PROP_TYPENAME = "type";
    public final static String PROP_COLUMNNAME = "columnName";
    public final static String PROP_MAXLENGTH = "maxLength";
    public final static String PROP_MAXSIZE = "maxSize";

    protected Ontology ontology;
    protected OracleSchemaFile schemaFile;
    private FileOutputStream file_output;
    private static int staticNestedTableIndex = 0;
    private HashMap oracleTypeToClassName = new HashMap();
    private HashMap oracleClassNameToTable = new HashMap();

    // This map contains only those types that are new. Used for generating alter script
    private HashMap newOracleTypeToClassName = new HashMap();
    private StringBuilder migrationErrors = new StringBuilder();
    protected static HashMap oracleTableToClassName = new HashMap();
    protected static Properties aliases = new Properties();
    private static HashMap nestedTableNames = new HashMap();
    private HashMap entitiesUsingCacheOM = new HashMap();

    // Map with key= clzName, value= ClzDeps
    private static HashMap clzToCeptMap = new HashMap();

    private String[] argv;
    private boolean showEnv = false;
    private static boolean optimize = false;
    private String traFile;
    private String repoUrl;
    private String schemaOwner;
    private String schemaOutput;
    private static final String BRK = System.getProperty("line.separator", "\n");
    private Properties env = new Properties();
    private BEProperties beProp;
    private BEProperties bsProp;
    private Map oracleTypeToClassNameFromDB = new HashMap();
    private Map oracleTableToClassNameFromDB = new HashMap();

    public static void main (String [] args) {
        try {
            OracleDeployment main = new OracleDeployment(args);
            main.deploySql();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Constructor for tools
    public OracleDeployment() {

    }

    // Constructor for extracting command line arguments
    public OracleDeployment(String[] argv) {
        this.argv = argv;
        String defProp = System.getProperty("wrapper.tra.file", "be-oradeploy.tra");
        //System.out.println("Using bootstrap property file: " + defProp);
        env.setProperty("be.bootstrap.property.file", defProp);
        parseArguments(argv);
        if (showEnv) {
            Iterator r = System.getProperties().entrySet().iterator();
            while (r.hasNext()) {
                Map.Entry e = (Map.Entry) r.next();
                System.out.println(e.getKey() + "=" + e.getValue());
            }
        }
    }

    // Constructor for sql scripts
    private void deploySql() throws Exception {
        System.out.print(printPrologue(argv));
        String arg ="";
        for (int i=0; i < argv.length; i++) {
            arg += " " + argv[i];
        }
        beProp = createBEProperties(env);
        bsProp = createBSProperties(env);
        this.repoUrl = beProp.getProperty("oradeploy.repourl");
        this.traFile = beProp.getProperty("be.bootstrap.property.file");

        //--------------------------------------------------------------//

        System.out.println("Using oradeploy property file: " + this.traFile);
        this.schemaOutput = beProp.getProperty("oradeploy.schema.output.file");
        String sqlPrologue = printSQLPrologue(argv);
        if (this.schemaOutput == null) {
            System.out.println("Error - No valid output file is defined. Exiting...");
            return;
        }
        if (checkFile(this.repoUrl) == false) {
            return;
        }

        // Generate scripts
        generateScripts(this.repoUrl,this.traFile,this.schemaOutput, sqlPrologue, beProp);
    }

    private boolean checkFile(String url) {
        File file = new File(url);
        if (file.exists() && file.isFile()) {
            return true;
        }
        System.out.println("Error - Could not find a valid '" + url + "' file. Exiting...");
        return false;
    }
    
    // Setup bootstrap property list
    private BEProperties createBEProperties(Properties env) {
        BEProperties beProperties = BEProperties.loadDefault();
        String oraPropertyFile = env.getProperty("be.bootstrap.property.file", "be-oradeploy.tra");

        File file = new File(oraPropertyFile);
        Properties prop = new Properties(env);
        if (file.exists()) {
            System.out.println("Using bootstrap property file: " + System.getProperty("wrapper.tra.file"));
            try {
                prop.load(new FileInputStream(oraPropertyFile));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                env.store(baos, null);
                prop.load(new ByteArrayInputStream(baos.toByteArray()));
                beProperties.put("be.bootstrap.property.file", oraPropertyFile);
            } catch (IOException e) {
                throw new RuntimeException("Cannot open property file:" + oraPropertyFile, e);
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
    private BEProperties createBSProperties(Properties env) {
        if (env.containsKey(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName())) {
            System.out.println("Using cluster deployment file: " + env.getProperty("com.tibco.be.config.path"));
            BEProperties beProperties = BEPropertiesFactory.getInstance().makeBEProperties(env);
            /**
            Enumeration names = beProperties.keys();
            while (names.hasMoreElements()) {
                String propName = (String) names.nextElement();
                if (propName.startsWith("be.engine.cluster")) {
                    System.err.println(propName+ "=" + beProperties.getProperty(propName));
                }
            }
            */
            return beProperties;
        }
        return new BEProperties();
    }
    
    private void parseArguments(String argv[])  {
        if (argv.length == 0 || argv.length < 3 ) {
            printUsage();
            System.exit(0);
        }
        for (int i=0; i < argv.length; i++) {
            String key = argv[i];
            if (key.matches("-h|/h|-help|/help|--help|/--help"))  {
                printUsage();
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
                env.put("oradeploy.schema.output.file", argv[i]);
            }
            else if (key.matches("-showenv|/showenv")) {
                showEnv = true;
            }
            else if (key.matches("-optimize|/optimize")) {
                optimize = true;
            }
            else {
                env.put("oradeploy.repourl", argv[i]);
            }
        }
    }

    private void printUsage() {
        System.out.println("be-oradeploy [-p <property file>] [-c <cdd file>] [-o <Oracle schema output file>] [-h] [EAR file path]");
        System.out.println("-p  Specify the property file. The default property file is be-oradeploy.tra in the current directory." + BRK +
                "    Same as -property or /property or /p");
        System.out.println("-c  Specify the cluster configuration file." + BRK + 
                "    Same as -cdd or /cdd or /c");
        System.out.println("-o  Specify the Oracle schema output Filename for deployment.");
        System.out.println("-h  Display this usage. Same as -help or /help or /h" );
    }

    private static String printPrologue(String args[]) {
        final StringBuffer buffer = new StringBuffer(be_oracleVersion.asterisks)
            .append(be_oracleVersion.line_separator)
            .append("\t").append(be_oracleVersion.getComponent()).append(" ").append(be_oracleVersion.version)
            .append(".").append(be_oracleVersion.build).append(" (").append(be_oracleVersion.buildDate)
            .append(")");

        buffer.append(be_oracleVersion.line_separator)
            .append("\t").append("Using arguments :");
        final StringBuffer userArgs = new StringBuffer();
        for (int i=0; i < args.length; i++) {
            userArgs.append(args[i]).append(" ");
        }

        buffer.append(userArgs)
            .append(be_oracleVersion.line_separator)
            .append("\t").append(be_oracleVersion.copyright)
            .append(be_oracleVersion.line_separator)
            .append(be_oracleVersion.asterisks)
            .append(be_oracleVersion.line_separator);

        return buffer.toString();
    }

    /*
    private void connect2Oracle() {
        try {
            DriverManager.registerDriver(new OracleDriver());
            //DriverManager.getConnection()
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL("jdbc:oracle:thin:@localhost:1521");
            oracle = ds.getConnection("be", "be");
            System.out.println("Got connection...");
            //testOracleObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /*
    private void testOracleObjects() throws Exception {
        DatabaseMetaData dmd= oracle.getMetaData();
        OracleStatement os= (OracleStatement) oracle.createStatement();
        OracleResultSet rs= (OracleResultSet) os.executeQuery("SELECT * FROM ALL_ENTITIES");

        while (rs.next()) {
            Datum datum= rs.getOracleObject(1);
            //oracle.sql.STRUCT o= (oracle.sql.STRUCT) rs.getObject(1);
            //String [] names= o.getDescriptor().getAttributeJavaNames();
            System.out.println(datum);
        }
    }
    */

    /**
     *
     * @param repoUrl
     * @param traFile
     * @param sqlPrologue
     * @param en
     * @throws Exception
     */
    private void generateScripts(String repoUrl, String traFile, String schemaFileOutput, String sqlPrologue, Properties en) throws Exception {
        //connect2Oracle();
        Workspace space;
        Project project;
        BEArchiveResourceProvider aresProvider;

        space = (Workspace) Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace")
                .getMethod("getInstance").invoke(null);
        aresProvider = new BEArchiveResourceProviderImpl();
        project = space.loadProject(repoUrl);

        final Collection<BEArchiveResource> archives = aresProvider.getBEArchives();
        if (archives != null) {
            for (BEArchiveResource archive : archives) {
                Properties props = archive.getCacheConfig();
                if (props != null) {
                    for (Enumeration<?> key = props.propertyNames(); key.hasMoreElements();) {
                        String keyValue = (String) key.nextElement();
                        //System.out.println("Cache key : " + keyValue );
                        if (keyValue.equalsIgnoreCase("omtgAdvancedEntitySettings")) {
                            //System.out.println("OMTG AdvanceEntitySettings ...");
                            Object value = props.get(keyValue);
                            Element elem = (Element) value;
                            for (Iterator itrChild = elem.getChildren(); itrChild.hasNext();) {
                                Element child = (Element) itrChild.next();
                                String entityURI = child.getChildStringValue(ExpandedName.makeName("uri"));
                                String cacheMode = child.getChildStringValue(ExpandedName.makeName("cacheMode"));
                                //System.out.println("  child : " + child.getChildStringValue(ExpandedName.makeName("uri")) + ", cache mode : " + child.getChildStringValue(ExpandedName.makeName("cacheMode")));
                                if (cacheMode.equalsIgnoreCase("cache") || cacheMode.equalsIgnoreCase("cacheAndMemory")) {
                                    entitiesUsingCacheOM.put(entityURI, cacheMode);
                                }
                            }
                        }
                        /*
                        else if (keyValue.equalsIgnoreCase("cacheConfigResourceUri")) {
                            System.out.println("CacheConfigResourceUri");
                            Object value = props.get(keyValue);
                            System.out.println("  value : " + value.getClass().getName());
                        }
                        */
                    }
                }
            }
        }
        else {
            System.out.println("No archives found");
        }

        /**
         Properties env = new Properties();
         env.put("tibco.repourl", repoUrl);
         env.put("be.bootstrap.property.file", traFile);
         RuleServiceProvider provider = RuleServiceProviderManager.getInstance().newProvider("OracleDeployment", env);
         TypeManager typeManager = provider.getTypeManager();
         provider.configure(false);

         DeployedProject deployedProject= provider.getProject();
         ontology= deployedProject.getOntology();
         */
        env = en;
        ontology = project.getOntology();

        // Check if there is any entity from the given repourl, stop if not
        if (ontology.getEntities().size() < 1) {
            System.err.println("There is no entity from the given repourl. Exit.");
            return;
        }
        /**
        BEProject beProject = new BEProject();
        beProject.loadProject(repoUrl);
        */
        schemaFile = new OracleSchemaFile("tibco");
        schemaFile.setProperties(env);
        final String schemaName;
        if (schemaFileOutput.endsWith(".sql")) {
            schemaName = schemaFileOutput.substring(0, schemaFileOutput.length() - ".sql".length());
        } else {
            schemaName = schemaFileOutput;
        }

        boolean popObjTab = Boolean.parseBoolean(env.getProperty("be.oracle.schemamigration.populateObjectTable", "false").trim());

        final File schFile = new File(schemaName + ".sql");
        file_output = new FileOutputStream(schFile);
        //File iot_schema = new File(schemaName + "_IOT.sql");
        //FileOutputStream iot_schema_file = new FileOutputStream(iot_schema);
        final File cleanupFile = new File(schemaName + "_cleanup.sql");
        FileOutputStream cleanup_file = new FileOutputStream(cleanupFile);
        final File removeFile = new File(schemaName + "_remove.sql");
        FileOutputStream remove_file = new FileOutputStream(removeFile);
        final File alterFile = new File(schemaName + "_alter.sql");
        FileOutputStream alter_file = new FileOutputStream(alterFile);

        Connection conn = getConn();
        if (conn != null) {
            aliases = loadAliasesFromDB(conn);
            nestedTableNames = loadNestedTableNamesFromDB(conn);
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

        boolean alterationPossible = generateOracleScript(conn, project.getGlobalVariables());
        schemaFile.setMapping(oracleTypeToClassName);
        schemaFile.setNewTypesMapping(newOracleTypeToClassName);
        schemaFile.setTableMapping(oracleClassNameToTable);
        schemaFile.setAliases(aliases);
        schemaFile.setClzToCeptMapping(clzToCeptMap);

        StringBuffer sbo = schemaFile.toStringBuffer(false);
        System.out.println("Writing schema file :" + schFile.getCanonicalPath());
        PrintWriter pwSchema = new PrintWriter(file_output);
        pwSchema.print(sqlPrologue);
        pwSchema.print(sbo.toString());
        pwSchema.flush();

        //StringBuffer iot_sbo = schemaFile.toStringBuffer(true);
        //System.out.println("Writing IOT schema file :" + iot_schema.getCanonicalPath());
        //PrintWriter iot_pwSchema = new PrintWriter(iot_schema_file);
        //iot_pwSchema.print(sqlPrologue);
        //iot_pwSchema.print(iot_sbo.toString());
        //iot_pwSchema.flush();

        System.out.println("Writing cleanup file :" + cleanupFile.getCanonicalPath());
        PrintWriter pwCleanup = new PrintWriter(cleanup_file);
        pwCleanup.print(sqlPrologue);
        pwCleanup.print(schemaFile.deleteQuery());
        pwCleanup.flush();

        System.out.println("Writing remove file :" + removeFile.getCanonicalPath());
        PrintWriter pwrRemove = new PrintWriter(remove_file);
        pwrRemove.print(sqlPrologue);
        pwrRemove.print(schemaFile.removeQuery());
        pwrRemove.flush();

        System.out.println("Writing alter file :" + alterFile.getCanonicalPath());
        PrintWriter pwrAlter = new PrintWriter(alter_file);
        pwrAlter.print(sqlPrologue);

        if (conn == null) {
            System.out.println(" No valid Database URL/Connection information provided. No data-migration script will be generated.");
            pwrAlter.print(" No valid Database URL/Connection information provided. No data-migration script is generated.");
        } else {
            if (!alterationPossible) {
                System.out.println(" #####  WARNING : Non-alterable Ontology changes found. Please see preceding errors. Manual schema-migration is required.");
                pwrAlter.print("--  ##### WARNING :  Non-alterable Ontology changes found. Please see following errors. Manual schema-migration is required.");
                pwrAlter.print(migrationErrors);
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
            System.out.println("Writing delete file :" + dupsFile.getCanonicalPath());
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
        System.out.println("Writing aliases file :" + aliasesFile.getCanonicalPath());
        aliases.store(aliases_file, "This file contains the aliases for column or table names that exceed maximum size imposed by Oracle");

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
        final StringBuffer buffer = new StringBuffer("--")
            .append(be_oracleVersion.asterisks)
            .append(be_oracleVersion.line_separator)
            .append("--\t").append(be_oracleVersion.getComponent()).append(" ").append(be_oracleVersion.version)
            .append(".").append(be_oracleVersion.build).append(" (").append(be_oracleVersion.buildDate)
            .append(")");

        buffer.append(be_oracleVersion.line_separator)
            .append("--\t").append("Using arguments :");
        final StringBuffer userArgs = new StringBuffer();
        for (int i=0; i < args.length; i++) {
            userArgs.append(args[i]).append(" ");
        }

        buffer.append(userArgs)
            .append(be_oracleVersion.line_separator)
            .append("--\t").append(be_oracleVersion.copyright)
            .append(be_oracleVersion.line_separator)
            .append("--").append(be_oracleVersion.asterisks)
            .append(be_oracleVersion.line_separator);

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
            System.err.println("Failed to load BEALIASES: " + e.getMessage());
        }
        return alz;
    }

     private HashMap loadNestedTableNamesFromDB(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String query = (schemaOwner == null) ? "Select PARENT_TABLE_NAME || ':' || PARENT_TABLE_COLUMN, TABLE_NAME from USER_NESTED_TABLES":
                "Select PARENT_TABLE_NAME || ':' || PARENT_TABLE_COLUMN, TABLE_NAME from DBA_NESTED_TABLES where OWNER='" + schemaOwner + "'";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String key = rs.getString(1);
                String nesTabName = rs.getString(2);
                if (nesTabName.startsWith("SYS") == false) {
                    nestedTableNames.put(key,nesTabName);
                }
            }
        }
        catch (Exception e) {
            System.err.println("Failed to load USER_NESTED_TABLES: " + e.getMessage());
        }
        return nestedTableNames;
    }

    public boolean generateOracleScript(Connection conn, GlobalVariables gvs) throws Exception {
        if (conn != null) {
            //baseConceptTypeFields = getTypeInfoFromDB(conn, "T_CONCEPT");
            //baseEventTypeFields = getTypeInfoFromDB(conn, "T_SIMPLE_EVENT");
            getClassMappingFromDb(conn, oracleTypeToClassNameFromDB, oracleTableToClassNameFromDB);
        }
        // Alteration not possible if error encountered for either event or concept.
        // But you want to generate all errors in one go. Hence continue with concepts, even if there is error in events.
        boolean alterationPossible = generateEventTypes(conn, gvs);
        if (!generateConceptTypes(conn)) {
            alterationPossible = false;
        }
        return alterationPossible;
    }

    private void getClassMappingFromDb(Connection conn, Map typeMap, Map tableMap) {
        try {
            Statement stmt = conn.createStatement();
            String query = (schemaOwner == null) ? "Select ORACLETYPE, ORACLETABLE, CLASSNAME from ClassToOracleType":
                               "Select ORACLETYPE, ORACLETABLE, CLASSNAME from " + schemaOwner + ".ClassToOracleType";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String type = rs.getString(1);
                String table = rs.getString(2);
                String clz = rs.getString(3);
                typeMap.put(type.toUpperCase(),clz);
                tableMap.put(table.toUpperCase(), clz);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    @SuppressWarnings("unused")
    private void generateGlobalTypes() {
    }

    private void generateTimeEventType(Event event, Connection conn, GlobalVariables gvs) {
        OracleType eventType = new OracleType(mangleOracleType(event, name2OracleType(event)), false);
        Event superEvent = (Event) event.getSuperEvent();

        if (superEvent != null) {
            eventType.setSuperType(mangleOracleType(superEvent, name2OracleType(superEvent)));
        } else {
            eventType.setSuperType(OracleType.BASE_TIMEEVENT_ORACLE_TYPE);
        }
        // If Migration intended
        Map<String, AttrTypeInfo> dbTypeInfo = null;
        if (conn != null) {
            dbTypeInfo = getTypeInfoFromDB(conn, eventType.getName());
            // If type exists
            if (dbTypeInfo != null) {
                eventType.state = OracleType.State.UNCHANGED;
            }
        }
        schemaFile.addType(eventType);
        OracleTable typeTable = new OracleTable(mangleOracleTable(event, name2OracleTable(event, eventType.getName())));
        typeTable.addMember("CACHEID " , "NUMBER", null);
        //typeTable.addMember("AGENTNAME", "VARCHAR2(4000)", null);
        typeTable.addMember("ENTITY " , eventType.getName(), null);

        if (dbTypeInfo != null) {
            typeTable.state = OracleTable.State.PREEXISTING;
        }
        schemaFile.addTable(typeTable);
    }

    /**
     *
     */
    private boolean generateEventTypes(Connection conn, GlobalVariables gvs) {
        boolean alterationPossible = true;
        Iterator allEvents = ontology.getEvents().iterator();
        while (allEvents.hasNext()) {
            Event event = (Event) allEvents.next();
            String fullPath = event.getFullPath();
            String hasBackingStore = getEntityDeploymentProperty(event, OracleDeployment.PROP_HASBACKINGSTORE, "true");
            if ((hasBackingStore != null) && (hasBackingStore.equalsIgnoreCase("false"))) {
                System.out.println(" Event " + fullPath + " has backing store flag set to false - schema not generated");
                continue;
            }

            String ttl = event.getTTL();
            if(ttl != null) {
	            ttl = gvs.substituteVariables(ttl).toString();
	            try {
	            	int ttlInt = Integer.parseInt(ttl);
	            	if (optimize == true && ttlInt == 0) {
	            		System.out.println(" Event " + fullPath + " has TTL=0 - schema not generated");
	            		continue;
	            	}
	            } catch(NumberFormatException nfe) {
	            	nfe.printStackTrace();
	            }
            }
            
            String cacheMode = getEntityDeploymentProperty(event, OracleDeployment.PROP_CACHEMODE, "cache");
            if ((optimize == true) && (cacheMode.equalsIgnoreCase("memory"))) {
                System.out.println(" Event " + fullPath + " is not using cache based object management - schema not generated");
                continue;
            }

            if (event.getType() == Event.SIMPLE_EVENT) {
                OracleType eventType = new OracleType(mangleOracleType(event, name2OracleType(event)), false);
                Event superEvent = (Event) event.getSuperEvent();

                if (superEvent != null) {
                    eventType.setSuperType(mangleOracleType(superEvent, name2OracleType(superEvent)));
                } else {
                    eventType.setSuperType(OracleType.BASE_SIMPLEEVENT_ORACLE_TYPE);
                }

                /*
                if (event.getPayloadSchema() != null) {
                    eventType.addMember(eventType.getName() + "_p", OracleType.TYPE_CLOB.getName());
                } else {
                    if (event.getSuperEvent() == null) {
                        eventType.addMember(eventType.getName() + "_p", OracleType.TYPE_BLOB.getName());
                    }
                }
                 */

                if (event.getSuperEvent() == null) {
                    //eventType.addMember("\"" + eventType.getName() + "_p" + "\"", OracleType.TYPE_BLOB.getName());
                    eventType.addMember(getPDName(event, eventType.getName() + "_p" ), OracleType.TYPE_BLOB.getName());
                }

                // Go through the properties
                Iterator it = event.getUserProperties();
                while (it.hasNext()) {
                    EventPropertyDefinition property = (EventPropertyDefinition) it.next();
                    String propertyName = getPDName(event, property.getPropertyName());
                    RDFPrimitiveTerm type = property.getType();
                    int typeFlag = RDFUtil.getRDFTermTypeFlag(type);

                    switch (typeFlag) {
                        case RDFTypes.STRING_TYPEID:
                            String maxLength = getAttributeDeploymentProperty(property, OracleDeployment.PROP_MAXLENGTH, null);
                            //TODO: Was this ever used [Answer:NO - Probably in the future]
                            //String columnName= OracleDeployment.getBackingMapEventProperty(property, OracleDeployment.PROP_COLUMNNAME, null);
                            //System.err.println("Event type=" + event.getName() + ", pd=" + property.getPropertyName() + ", maxLen=" + maxLength);
                            int maxValue=-1;
                            if ((maxLength != null) && (maxLength.trim().length() > 0)) {
                                try {
                                    maxValue=Integer.valueOf(maxLength);
                                } catch (Exception ex) {
                                    System.err.println("Ignoring maxLength for Property " + property.getPropertyName() + ", event=" + eventType.getName() + ", exception=" + ex);
                                }
                            }
                            if (maxValue > 4000) {
                                eventType.addMember(propertyName, OracleType.TYPE_CLOB.getName());
                            } else if (maxValue > 0) {
                                eventType.addMember(propertyName, new OracleType("varchar2(" + maxValue + ")", true).getName(), maxValue);
                            } else {
                                eventType.addMember(propertyName, OracleType.TYPE_STRING.getName(), 255);
                            }
                            //eventType.addMember(propertyName, OracleType.TYPE_STRING.getName());
                            break;
                        case RDFTypes.BOOLEAN_TYPEID:
                            eventType.addMember(propertyName, OracleType.TYPE_BOOLEAN.getName());
                            break;
                        case RDFTypes.INTEGER_TYPEID:
                            eventType.addMember(propertyName, OracleType.TYPE_INTEGER.getName());
                            break;
                        case RDFTypes.DATETIME_TYPEID:
                            eventType.addMember(propertyName, OracleType.TYPE_DATETIME.getName());
                            break;
                        case RDFTypes.LONG_TYPEID:
                            eventType.addMember(propertyName, OracleType.TYPE_NUMBER.getName());
                            break;
                        case RDFTypes.DOUBLE_TYPEID:
                            eventType.addMember(propertyName, OracleType.TYPE_NUMBER.getName());
                            break;
                    }
                }

                // If Migration intended
                Map<String, AttrTypeInfo> dbTypeInfo = null;
                if (conn != null) {
                    dbTypeInfo = getTypeInfoFromDB(conn, eventType.getName());
                    // If type exists
                    if (dbTypeInfo != null) {
                        eventType.state = OracleType.State.UNCHANGED;
                        // Remove base concept type fields, for comparison
                        //dbTypeInfo = filterBaseTypes(dbTypeInfo, baseEventTypeFields);
                        if (!compare(dbTypeInfo, eventType, "Event " + event.getName())) {
                            alterationPossible = false;
                        }
                    }
                }

                // Look for the Payload
                schemaFile.addType(eventType);

                OracleTable typeTable = new OracleTable(mangleOracleTable(event, name2OracleTable(event, eventType.getName())));
                typeTable.addMember("CACHEID", "NUMBER", null);
                typeTable.addMember("ENTITY", eventType.getName(), null);

                if (dbTypeInfo != null) {
                    typeTable.state = OracleTable.State.PREEXISTING;
                }

                schemaFile.addTable(typeTable);
            } else if (event.getType() == Event.TIME_EVENT) {
                generateTimeEventType(event, conn, gvs);
            }
        }
        return alterationPossible;
    }

    private void generateNestedTables (Concept cept, OracleTable ceptTable) {
        Collection propDefs = cept.getAllPropertyDefinitions();
        for (Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition)it.next();
            if (pd.isArray() && pd.getHistorySize() == 0) {
                String parentTableClm=  "\"ENTITY\"." + getPDName(cept, pd.getName());
                ceptTable.addNestedTable(parentTableClm, name2NestedTable(ceptTable.getName(), parentTableClm));
            } else if (pd.isArray() && pd.getHistorySize() > 0) {
                String parentTableClm= "\"ENTITY\"." + getPDName(cept, pd.getName());
                String parentNestedTableName= name2NestedTable(ceptTable.getName(), parentTableClm);
                StringBuffer nestedTable= ceptTable.addNestedTable(parentTableClm, parentNestedTableName );
                parentTableClm =  "VALS";
                ceptTable.addNestedTable( nestedTable, "\"VALS\"", name2NestedTable(parentNestedTableName, parentTableClm));
            } else if (pd.getHistorySize() > 0) {
                String parentTableClm= "\"ENTITY\"." + getPDName(cept, pd.getName()) + ".\"VALS\"";
                ceptTable.addNestedTable(parentTableClm, name2NestedTable(ceptTable.getName(), parentTableClm));
            }
        }
    }

    private boolean generateConceptTypes(Connection conn) throws Exception {
        boolean alterationPossible = true;

        Iterator allConcepts = ontology.getConcepts().iterator();
        while (allConcepts.hasNext()) {
            Concept cept = (Concept) allConcepts.next();
            String fullPath = cept.getFullPath();
            String hasBackingStore = getEntityDeploymentProperty(cept, OracleDeployment.PROP_HASBACKINGSTORE, "true");
            if ((hasBackingStore != null) && (hasBackingStore.equalsIgnoreCase("false"))) {
                System.out.println(" Concept " + fullPath + " has backing store flag set to false - schema not generated");
                continue;
            }
            String cacheMode = getEntityDeploymentProperty(cept, OracleDeployment.PROP_CACHEMODE, "cache");
            if ((optimize == true) && (cacheMode.equalsIgnoreCase("memory"))) {
                System.out.println(" Concept " + fullPath + " is not using cache based object management - schema not generated");
                continue;
            }
            String beClz=ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
            clzToCeptMap.put(beClz, cept);

            OracleType conceptType = new OracleType(mangleOracleType(cept, name2OracleType(cept)), false);

            OracleTable typeTable = new OracleTable(mangleOracleTable(cept, name2OracleTable(cept, conceptType.getName())));
            typeTable.addMember("CACHEID " , "NUMBER", null);
            //typeTable.addMember("AGENTNAME", "VARCHAR2(4000)", null);
            typeTable.addViewMember("CACHEID ");
            typeTable.addMember("ENTITY" , conceptType.getName(), null);
            typeTable.addNestedTable("\"ENTITY\".\"REVERSEREFS$\"", name2NestedTable(typeTable.getName(), "\"ENTITY\".\"REVERSEREFS$\""));

            if (cept.getSuperConcept() != null) {
                String superType=mangleOracleType(cept.getSuperConcept(), name2OracleType(cept.getSuperConcept()));
                conceptType.setSuperType(superType);
                typeTable.setSuperTableName(mangleOracleTable(cept.getSuperConcept(), name2OracleTable(cept.getSuperConcept(), superType)));
            } else {
                conceptType.setSuperType(OracleType.BASE_CONCEPT_ORACLE_TYPE);
            }

            // Go through the properties
            Collection propDefs = cept.getLocalPropertyDefinitions();
            for (Iterator it = propDefs.iterator(); it.hasNext();) {
                PropertyDefinition pd = (PropertyDefinition)it.next();
                switch(pd.getType()) {
                    case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                        createBooleanColumn(conceptType, pd);
                        typeTable.addViewMember(getPDName(cept, pd.getName()) ,"t.ENTITY." + getPDName(cept, pd.getName()));
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                        createIntegerColumn(conceptType, pd);
                        typeTable.addViewMember(getPDName(cept, pd.getName()) ,"t.ENTITY." + getPDName(cept, pd.getName()));
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                        createDateTimeColumn(conceptType, pd);
                        typeTable.addViewMember(getPDName(cept, pd.getName()),"t.ENTITY." +getPDName(cept, pd.getName()));
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_LONG:
                        createNumberColumn(conceptType, pd);
                        typeTable.addViewMember(getPDName(cept, pd.getName()) ,"t.ENTITY." +getPDName(cept, pd.getName()));
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_REAL:
                        createNumberColumn(conceptType, pd);
                        typeTable.addViewMember(getPDName(cept, pd.getName()) ,"t.ENTITY." +getPDName(cept, pd.getName()));
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_STRING:
                        createStringColumn(conceptType, pd);
                        typeTable.addViewMember(getPDName(cept, pd.getName()) ,"t.ENTITY." +getPDName(cept, pd.getName()));
                        break;
                    case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                    case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                        createConceptColumn(conceptType, pd);
                        break;
                }
            }

            if (cept.getStateMachines() != null && cept.getStateMachines().size() > 0) {
                Iterator allSM= cept.getStateMachines().iterator();
                if (allSM != null) {
                    while (allSM.hasNext()) {
                        StateMachine sm = (StateMachine) allSM.next();
                        createStateMachineColumn(conceptType, sm);
                    }
                }
            }

            // If Migration intended
            Map<String, AttrTypeInfo> dbTypeInfo = null;
            if (conn != null) {
                dbTypeInfo = getTypeInfoFromDB(conn, conceptType.getName());
                // If type exists
                if (dbTypeInfo != null) {
                    // Initially, mark the type as existing and unchanged
                    conceptType.state = OracleType.State.UNCHANGED;
                    // Remove base concept type fields, for comparison
                    //dbTypeInfo = filterBaseTypes(dbTypeInfo, baseConceptTypeFields);
                    if (!compare(dbTypeInfo, conceptType, "Concept " + cept.getName())) {
                        alterationPossible = false;
                    }
                }
            }

            // Add concept to the final list
            schemaFile.addType(conceptType);
            generateNestedTables(cept, typeTable);

            // Now create DDL for the state machine
            if (cept.getStateMachines() != null && cept.getStateMachines().size() > 0) {
                if (!generateStateMachineType(cept, conceptType, typeTable, conn)) {
                    alterationPossible = false;
                }
            }
            schemaFile.addType(conceptType);
            // Mark as existing
            if (dbTypeInfo != null) {
               typeTable.state= OracleTable.State.PREEXISTING;
            }
            schemaFile.addTable(typeTable);

        }
        Iterator alltables = schemaFile.getTables() ;
        OracleTable atab;
        while (alltables.hasNext()) {
            atab=(OracleTable) alltables .next() ;
            if (atab.getSuperTableName() != null) {
                atab.setSuperTable(schemaFile.getTable(atab.getSuperTableName()));
            }
        }

        return alterationPossible;
    }

    @Deprecated
    @SuppressWarnings("unused")
    private Map<String, String> filterBaseTypes(Map<String, String>dbTypes, Map<String, String> baseTypes) {
        for (String type : baseTypes.keySet()){
            dbTypes.remove(type);
        }
        return dbTypes;
    }

    private boolean compare(Map<String, AttrTypeInfo> dbTypeInfo, OracleType entityType, String name) {
        boolean ret = true;
        int deleteCount=0;
        for (String memberName : dbTypeInfo.keySet()) {
            OracleType.MemberInfo info = entityType.memberMap.get(memberName.toUpperCase());
            if (info == null) {
                // Field deleted
                info = new OracleType.MemberInfo(memberName, "");
                info.state = OracleType.MemberInfo.Deleted;
                entityType.memberMap.put(memberName, info);
                entityType.state = OracleType.State.MODIFIED;
                deleteCount++;
            } else {
                String dbType = dbTypeInfo.get(memberName).attrTypeName;
                // Get rid of any schema name prefix
                int dotIndex = dbType.indexOf('.');
                if (dotIndex > -1)
                    dbType= dbType.substring(dotIndex+1);
                String updatedType = info.memberType.toUpperCase();
                //System.out.println("Name=" + name + ", member=" + memberName);
                //System.out.println("UpdatedType=" + updatedType);
                //System.out.println("DbType=" + dbType);
                //System.out.println("New length=" + info.length + ", old=" + dbTypeInfo.get(memberName).length);
                if (!updatedType.equals(dbType)) {
                    if (updatedType.startsWith("VARCHAR") && dbType.startsWith("VARCHAR")) {
                        //Any change in length of a string property?
                        if (info.length < dbTypeInfo.get(memberName).length) {
                            info.state = OracleType.MemberInfo.Error;
                            String error= "--* For " + name + " field " + memberName + " String length reduced from " + dbTypeInfo.get(memberName).length + " to " + info.length;
                            System.out.println(error);
                            migrationErrors.append(be_oracleVersion.line_separator + error);
                            ret = false;
                        }
                        else if (info.length > dbTypeInfo.get(memberName).length){
                            entityType.state = OracleType.State.MODIFIED;
                            info.state = OracleType.MemberInfo.Altered;
                        }
                        else {
                            // If no change in length, leave me alone
                            info.state = OracleType.MemberInfo.NotChanged;
                        }
                    } else if (((updatedType.equals("INTEGER") || updatedType.equals("INT")) && dbType.startsWith("NUMBER"))
                            ||
                            (updatedType.startsWith(dbType))
                            ||
                            (updatedType.equals("T_ENTITY_REF") && dbType.endsWith("T_ENTITY_REF"))
                            ) {
                        // Mark as unchanged
                        info.state = OracleType.MemberInfo.NotChanged;
                    } else {
                        info.state = OracleType.MemberInfo.Error;
                        String error= "--* For " + name + " field " + memberName + " type changed from " + dbType + " to " + updatedType;
                        System.out.println(error);
                        migrationErrors.append(be_oracleVersion.line_separator + error);
                        ret = false;
                    }
                } else {
                    // Mark as unchanged
                    info.state = OracleType.MemberInfo.NotChanged;
                }
            }
        }

        // Find if the type should be marked as modified, because some attribute added
        //if (ret && conceptType.state != OracleType.State.MODIFIED) {
        if (entityType.state != OracleType.State.MODIFIED) {
             if (dbTypeInfo.size() - deleteCount != entityType.memberMap.size())
                  entityType.state = OracleType.State.MODIFIED;
        }
        return ret;
    }

    private static class AttrTypeInfo {
        String attrName;
        String attrTypeName;
        public int length =-1;

        public AttrTypeInfo(String name, String typeName) {
            attrName = name; attrTypeName = typeName;
        }
    }

    /**
     * This method connects to the database and finds the structure of a given type, as it exists
     * in database today. If the type is not found in database, it is assumed to be new, and no
     * information is returned
     * @param conn
     * @param type_name
     * @return
     */
    private Map<String, AttrTypeInfo> getTypeInfoFromDB(Connection conn, String type_name) {
        Map typeMap = null;
        // get the type descriptor
        try {
            String typeName = (schemaOwner == null) ? type_name.toUpperCase():schemaOwner + "." + type_name.toUpperCase();
            StructDescriptor desc = StructDescriptor.createDescriptor(typeName, conn, false, false);
            typeMap = new HashMap<String, String>();
            StructMetaData md = (StructMetaData) desc.getMetaData();
            int numAttrs = desc.getLength();
            String attrName, attrTypeName;

            for (int i = 0; i < numAttrs; i++) {
                if (md.isInherited(i+1))
                    continue;
                attrName = md.getColumnName(i + 1);
                attrTypeName = md.getColumnTypeName(i + 1).toUpperCase();

                AttrTypeInfo tInfo= new AttrTypeInfo(attrName,attrTypeName);
                if (attrTypeName.startsWith("VARCHAR")) {
                    tInfo.length = md.getColumnDisplaySize(i+1);
                }
                //typeMap.put("\"" + attrName + "\"", attrTypeName.toUpperCase());
                typeMap.put("\"" + attrName + "\"", tInfo);
            }
        }
        catch (SQLException sqe) {
            //System.out.println("Err:" + sqe.getMessage());
            System.out.println(" Type " + type_name + " not found in database. Treating it as new.");
        }
        return typeMap;
    }

    protected Connection getConn() throws Exception {
        try {
            DriverManager.registerDriver(new OracleDriver());
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            String dbUrl = env.getProperty("be.oracle.schemamigration.url");
            String dbUser = env.getProperty("be.oracle.schemamigration.user");
            if (dbUser != null)
                dbUser = dbUser.trim();
            String dbPswd = env.getProperty("be.oracle.schemamigration.pswd");
            if (dbPswd != null)
                dbPswd = dbPswd.trim();
            schemaOwner = env.getProperty("be.oracle.schemamigration.schema");
            if (schemaOwner != null)
                schemaOwner = schemaOwner.trim();
            ds.setURL(dbUrl);
            Connection conn = ds.getConnection(dbUser, dbPswd);
            return conn;
        } catch (Exception e) {
            //No operation
            //System.out.println("Err:" + e.getMessage());
            //System.out.println(" Properties for Schema Migration (Database URL/User/Pswd) not found. Migration script will not be generated");
        }
        return null;
    }

    private boolean generateStateMachineType(Concept cept, OracleType ceptType, OracleTable ceptTable, Connection conn) {
        boolean alterationPossible = true;
        List stateMachines = cept.getStateMachines();
        if (stateMachines != null) {
            for (int i = 0; i < stateMachines.size(); i++) {
                StateMachine sm = (StateMachine) stateMachines.get(i);
                OracleType smType = new OracleType(mangleOracleStateMachineType(sm, name2OracleStateMachineType(sm)), false);

                // State machine does not have object hierarchy therefore its super type is always the same
                smType.setSuperType(OracleType.BASE_CONCEPT_ORACLE_TYPE);

                //String smName = "\"" + sm.getName() + "\"";
                //ceptType.addMember(smName, OracleType.BASE_ENTITYREF_TABLE_ORACLE_TYPE);
                //ceptTable.addNestedTable("ENTITY." + smName, name2NestedTable(cept));
                //ceptType.addMember(smName, OracleType.BASE_ENTITYREF_ORACLE_TYPE);

                List stateNames = new ArrayList();
                getStateMachineStateNames(sm.getMachineRoot(), null, stateNames);
                for (int j = 0; j < stateNames.size(); j++) {
                    //smType.addMember("\"" + (String)stateNames.get(j) + "\"", OracleType.TYPE_INTEGER.getName());
                    smType.addMember(getStateName(sm, (String) stateNames.get(j)),OracleType.TYPE_INTEGER.getName()) ;
                }

                //Find is this state-machine type exists in database, if Migration intended
                Map<String, AttrTypeInfo> dbTypeInfo = null;
                if (conn != null) {
                    dbTypeInfo = getTypeInfoFromDB(conn, smType.getName());
                    // If type exists
                    if (dbTypeInfo != null) {
                        smType.state = OracleType.State.UNCHANGED;
                        // Remove base concept type fields, for comparison
                        //dbTypeInfo = filterBaseTypes(dbTypeInfo, baseConceptTypeFields);
                        if (!compare(dbTypeInfo, smType, "State-Machine " + cept.getName())) {
                            alterationPossible = false;
                        }
                    }
                }

                schemaFile.addType(smType);

                OracleTable typeTable = new OracleTable(mangleOracleStateMachineTable(sm, name2OracleTable(sm, smType.getName())));
                typeTable.addMember("CACHEID", "NUMBER", null);
                //typeTable.addMember("AGENTNAME", "VARCHAR2(4000)", null);
                typeTable.addMember("ENTITY", smType.getName(), null);
                typeTable.addNestedTable("ENTITY.REVERSEREFS$", name2NestedTable(typeTable.getName(), "ENTITY.REVERSEREFS$"));
                if (dbTypeInfo != null) {
                    typeTable.state= OracleTable.State.PREEXISTING;
                }
                schemaFile.addTable(typeTable);
            }
        }
        return alterationPossible;
    }

    private void getStateMachineStateNames(StateComposite stateComposite, String namePrefix, List stateNames) {
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

    @Deprecated
    @SuppressWarnings("unused")
    private void generateTables() {
    }

    @Deprecated
    @SuppressWarnings("unused")
    private static void makeConceptFile(Concept cept, OracleSchemaFile schemaFile) throws Exception {
    }

    private void createStringColumn(OracleType conceptType, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(), pd.getName()), OracleType.STRING_HIST_ORACLE_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            String maxLength = getAttributeDeploymentProperty(pd, OracleDeployment.PROP_MAXLENGTH, null);
            //TODO: Was this ever used [Answer:NO - Probably in the future]
            //String columnName = getAttributeDeploymentProperty(pd, OracleDeployment.PROP_COLUMNNAME, null);
            //System.err.println("Concept type=" + conceptType.getName() + ", pd=" + pd.getName() + ", maxLen=" + maxLength);
            int maxValue=-1;
            if ((maxLength != null) && (maxLength.trim().length() > 0)) {
                try {
                    maxValue=Integer.valueOf(maxLength);
                } catch (Exception ex) {
                    System.err.println("Ignoring maxLength for Property " + pd.getName() + ", concept=" + conceptType.getName() + ", exception=" + ex);
                }
            }
            if (maxValue > 4000) {
                conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.TYPE_CLOB.getName());
            } else if (maxValue > 0) {
                conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), new OracleType("varchar2(" + maxValue + ")", true).getName(), maxValue);
            } else {
                conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.TYPE_STRING.getName(), 255);
            }
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.STRING_COL_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.STRING_COL_HIST_ORACLE_TYPE);
        }
    }

    private static void createIntegerColumn(OracleType conceptType, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.INTEGER_HIST_ORACLE_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.TYPE_INTEGER.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.INTEGER_COL_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.INTEGER_COL_HIST_ORACLE_TYPE);
        }
    }

    private static void createNumberColumn(OracleType conceptType, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.NUMBER_HIST_ORACLE_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.TYPE_NUMBER.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.NUMBER_COL_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.NUMBER_COL_HIST_ORACLE_TYPE);
        }
    }

    private static void createBooleanColumn(OracleType conceptType, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BOOLEAN_HIST_ORACLE_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.TYPE_BOOLEAN.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BOOLEAN_COL_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BOOLEAN_COL_HIST_ORACLE_TYPE);
        }
    }

    private static void createDateTimeColumn(OracleType conceptType, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.DATETIME_HIST_ORACLE_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.TYPE_DATETIME.getName());
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.DATETIME_COL_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.DATETIME_COL_HIST_ORACLE_TYPE);
        }
    }

    private static void createConceptColumn(OracleType conceptType, PropertyDefinition pd) {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BASE_ENTITYREF_HIST_ORACLE_TYPE);
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BASE_ENTITYREF_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BASE_ENTITYREF_TABLE_ORACLE_TYPE);
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            conceptType.addMember(getPDName(pd.getOwner(),pd.getName()), OracleType.BASE_ENTITYREF_COL_HIST_ORACLE_TYPE);
        }
    }

    private static void createStateMachineColumn(OracleType conceptType, StateMachine sm) {
        //conceptType.addMember(sm.getOwnerConcept().getName() + "$" + sm.getName(), OracleType.BASE_ENTITYREF_ORACLE_TYPE);
        conceptType.addMember(getSMPDName(sm),OracleType.BASE_ENTITYREF_ORACLE_TYPE);
    }

    private String mangleOracleType(Entity entity, String oracleType) {
        String beClass= (String) oracleTypeToClassName.get(oracleType);
        String beClz= ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath());
        // Mangled name of the form ENTITYNAME$ is already used, so append $ and repeat
        if (beClass != null) {
            if (beClz.equals(beClass)) {
                return oracleType;
            } else {
                return mangleOracleType(entity, oracleType + "$");
            }
        }

        // New type added to the project and hence not present in the DB is added to the newOracleTypeToClassName HashMap
        if (oracleTypeToClassNameFromDB.get(oracleType.toUpperCase()) == null) {
            newOracleTypeToClassName.put(oracleType, beClz);
        }   // When the mangled_name->ClassName mapping in the existing DB does not match the just assigned mangled_name->ClassName mapping,
            //   search for another mangled_name until the mapping matches the one in the DB
        else {
            if (oracleTypeToClassNameFromDB.get(oracleType.toUpperCase()).equals(beClz) == false) {
                return mangleOracleType(entity, oracleType + "$");
            }
        }
        // Unconditionally put for the regular script
        oracleTypeToClassName.put(oracleType, beClz);
        return oracleType;
    }

    private String name2OracleType(Entity cept) {
        String ret= "T_" + cept.getName().replaceAll("\\.", "\\$");
        String alias= getOracleTypeAlias(cept, ret);

        String projectValue = getEntityDeploymentProperty(cept, OracleDeployment.PROP_TYPENAME, null);
        String typeName = null;

        if (ret.equalsIgnoreCase(alias)) {
            if ((projectValue != null) && (projectValue.trim().length() > 0)) {
                typeName=projectValue;
            } else {
                typeName=ret;
            }
        } else {
            typeName=alias;
        }

        if (typeName.length() > 30) {
            setOracleTypeAlias(cept, ret);
        }
        return typeName;
    }

    private String mangleOracleStateMachineType(StateMachine sm, String oracleType) {
        String beClass= (String) oracleTypeToClassName.get(oracleType);
        //String beClz= ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getName();
        String beClz= ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getOwnerConcept().getName() + "$" + sm.getName();
        if (beClass != null) {
            if (beClz.equals(beClass)) {
                return oracleType;
            }
        }
        if (oracleTypeToClassNameFromDB.get(oracleType.toUpperCase()) == null) {
            newOracleTypeToClassName.put(oracleType, beClz);
        }
        // Unconditionally put for the regular script
        oracleTypeToClassName.put(oracleType, beClz);
        return oracleType;
    }

    private static String name2OracleStateMachineType(StateMachine sm) {
        String ret= "T_" + sm.getOwnerConcept().getName().replaceAll("\\.", "\\$") + "$" + sm.getName();
        ret=getOracleSMTypeAlias(sm, ret);
        if (ret.length() > 30) {
            setOracleSMTypeAlias(sm, ret);
        }
        return ret;
    }

    public String getEntityDeploymentProperty(Entity entity, String property, String defaults) {
        String fulldesc = "be.engine.cluster." + 
            ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath()) + "." + property;
        if ((this.bsProp.getProperty(fulldesc) != null)&&(this.bsProp.getProperty(fulldesc).isEmpty() == false)) {
            //System.err.println(fulldesc + "=" + this.bsProp.getProperty(fulldesc));
            return this.bsProp.getString(fulldesc);
        } else {
            return defaults;
        }
    }
    
    public String getAttributeDeploymentProperty(PropertyDefinition attribute, String property, String defaults) {
        if (attribute.getOwner() == null) {
            //System.err.println("Parent concept is not found for " + attribute.getPropertyName());
            return defaults;
        }
        String fulldesc = "be.engine.cluster." + 
            ModelNameUtil.modelPathToGeneratedClassName(attribute.getOwner().getFullPath()) + 
            ".property." + attribute.getName() + "." + property;
        if ((this.bsProp.getProperty(fulldesc) != null)&&(this.bsProp.getProperty(fulldesc).isEmpty() == false)) {
            //System.err.println(fulldesc + "=" + this.bsProp.getProperty(fulldesc));
            return this.bsProp.getString(fulldesc);
        } else {
            return defaults;
        }
    }

    public String getAttributeDeploymentProperty(EventPropertyDefinition attribute, String property, String defaults) {
        if (attribute.getOwner() == null) {
            //System.err.println("Parent event is not found for " + attribute.getPropertyName());
            return defaults;
        }
        String fulldesc = "be.engine.cluster." + 
            ModelNameUtil.modelPathToGeneratedClassName(attribute.getOwner().getFullPath()) + 
            ".property." + attribute.getPropertyName() + "." + property;
        if ((this.bsProp.getProperty(fulldesc) != null)&&(this.bsProp.getProperty(fulldesc).isEmpty() == false)) {
            //System.err.println(fulldesc + "=" + this.bsProp.getProperty(fulldesc));
            return this.bsProp.getString(fulldesc);
        } else {
            return defaults;
        }
    }

    private String mangleOracleTable(Entity entity, String oracleTable) {
        String beClz= ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath());
        String beClass= (String) oracleTableToClassName.get(oracleTable);
        // Mangled name of the form TABLENAME$ is already used, so append $ and repeat
        if (beClass != null) {
            if (beClz.equals(beClass)) {
                return oracleTable;
            } else {
                return mangleOracleTable(entity, oracleTable + "$");
            }
        }

        if (oracleTableToClassNameFromDB.get(oracleTable.toUpperCase()) == null) {
            // NO-need: newOracleTableToClassName.put(oracleTable, beClz);
        }   // When the mangled_name->ClassName mapping in the existing DB does not match the just assigned mangled_name->ClassName mapping,
            //   search for another mangled_name until the mapping matches the one in the DB
        else {
            if (oracleTableToClassNameFromDB.get(oracleTable.toUpperCase()).equals(beClz) == false) {
                return mangleOracleTable(entity, oracleTable + "$");
            }
        }
        // Unconditionally put for the regular script
        oracleTableToClassName.put(oracleTable, beClz);
        oracleClassNameToTable.put(beClz, oracleTable);
        return oracleTable;
    }

    private String mangleOracleStateMachineTable(StateMachine sm, String oracleTable) {
        //String beClz= ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getName();
        String beClz= ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getOwnerConcept().getName() + "$" + sm.getName();
        String beClass= (String) oracleTableToClassName.get(oracleTable);
        // Mangled name of the form TABLENAME$ is already used, so append $ and repeat
        if (beClass != null) {
            if (beClz.equals(beClass)) {
                return oracleTable;
            }
        }

        if (oracleTableToClassNameFromDB.get(oracleTable.toUpperCase()) == null) {
            // NO-need: newOracleTableToClassName.put(oracleTable, beClz);
        }
        // Unconditionally put for the regular script
        oracleTableToClassName.put(oracleTable, beClz);
        oracleClassNameToTable.put(beClz, oracleTable);
        return oracleTable;
    }

    private String name2OracleTable(Entity entity, String oracleType) {
        String ret= name2OracleTable(oracleType);
        String alias= getOracleTableAlias(entity, ret);
        String projectValue=getEntityDeploymentProperty(entity, OracleDeployment.PROP_TABLENAME, null);
        String tableName=null;

        if (ret.equalsIgnoreCase(alias)) {
            if ((projectValue != null) && (projectValue.trim().length() > 0)) {
                tableName=projectValue;
            } else {
                tableName=ret;
            }
        } else {
            tableName=alias;
        }
        if (tableName.length() > 30) {
            setOracleTableAlias(entity, tableName);
        }
// TODO: Needed?
//        String beClz= ModelNameUtil.modelPathToGeneratedClassName(entity.getFullPath());
//        oracleClassNameToTable.put(beClz, tableName);
        return tableName;
    }

    private static String name2OracleTable(String oracleType) {
        return "D" + oracleType.substring(1);
    }

    private static String name2OracleTable(StateMachine sm, String oracleType) {
        String ret= "D_" + sm.getOwnerConcept().getName().replaceAll("\\.", "\\$") + "$" + sm.getName();
        ret=getOracleSMTableAlias(sm, ret);
        if (ret.length() > 30) {
            setOracleSMTableAlias(sm, ret);
        }
        String beClz= ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getOwnerConcept().getName() + "$" + sm.getName();
        oracleTableToClassName.put(beClz, ret);
        return ret;
    }

    private static String name2NestedTable(String table, String column) {
        String lookup;
        // Schema-Migration case: Check if name already assigned to this nestedTable in database
        lookup = table + ":" + column;
        if (nestedTableNames.get(lookup) != null)
            return (String) nestedTableNames.get(lookup);
        lookup = table.toUpperCase() + ":" + column;
        if (nestedTableNames.get(lookup) != null)
            return (String) nestedTableNames.get(lookup);

        // Schema-Migration case:  Make sure generated name not already in use
        String name = null;
        while (true) {
            name = "NESTEDTABLE" + (staticNestedTableIndex++) + "$$";
            if (!nestedTableNames.containsValue(name.toUpperCase()))
                break;
        }
        return name;
    }

    @Deprecated
    @SuppressWarnings("unused")
    private String name2OracleCollectionType(Concept cept) {
        String ret=name2OracleType(cept) + "_TABLE";
        if (ret.length() > 24) {
            aliases.put(ret,ret);
        }
        return ret;
    }

    @Deprecated
    @SuppressWarnings("unused")
    private String name2OracleHistoryType(Concept cept) {
        String ret= name2OracleType(cept) + "_HIST";
        if (ret.length() > 25) {
            aliases.put(ret,ret);
        }
        return ret;
    }

    @Deprecated
    @SuppressWarnings("unused")
    private String name2OracleCollectionHistoryType(Concept cept) {
        String ret = name2OracleType(cept) + "_TABLE_HIST";
        if (ret.length() > 20) {
            aliases.put(ret,ret);
        }
        return ret;
    }

    protected static String getPDName(Entity entity, String name) {
        String pdName =getPropertyAlias(entity, name, name);
        if (pdName.length() > 30) {
            setPropertyAlias(entity, name);
        }
        return '"' + pdName +'"';
    }

    protected static String getSMPDName(StateMachine sm) {
        String ret=sm.getOwnerConcept().getName() + "$" + sm.getName();
        String pdName =getSMPropertyAlias(sm,ret );
        if (ret.length() > 30) {
            setSMPropertyAlias(sm, ret);
        }
        return '"' + pdName +'"';
    }

    private static String getStateName(StateMachine sm, String state) {
        String pdName =getOracleSMStateAlias(sm, state, state);
        if (pdName.length() >= 30) {
            setOracleSMStateAlias(sm, state, state);
        }
        return '"' + pdName +'"';
    }

    private static String getPropertyAlias(Entity entity, String name, String defaultValue) {
        //return aliases.getProperty(entity.getFullPath() + ".PROPERTY." + name + ".alias", defaultValue);
        return aliases.getProperty("COLUMN." + name + ".alias", defaultValue);
    }

    private static void setPropertyAlias(Entity entity, String name) {
        if (aliases.getProperty("COLUMN." + name + ".alias") == null) {
            //aliases.setProperty(entity.getFullPath() + ".PROPERTY." + name + ".alias", name);
            aliases.setProperty("COLUMN." + name + ".alias", name);
        }
    }

    private static String getSMPropertyAlias(StateMachine sm, String defaultValue) {
        //return aliases.getProperty(cept.getFullPath() + ".SMPROPERTY." + sm.getName() + ".alias", defaultValue);
        return aliases.getProperty("COLUMN." + defaultValue + ".alias", defaultValue);
    }

    private static void setSMPropertyAlias(StateMachine sm, String name) {
        if (aliases.getProperty("COLUMN." + name + ".alias") == null) {
            //aliases.setProperty(cept.getFullPath() + ".SMPROPERTY." + sm.getName() + ".alias", name);
            aliases.setProperty("COLUMN." + name + ".alias", name);
        }
    }

    private static String getOracleTypeAlias(Entity entity, String defaultValue) {
        //return aliases.getProperty(entity.getFullPath() + ".TYPE.alias", defaultValue);
        return aliases.getProperty("TYPE." + defaultValue + ".alias", defaultValue);
    }

    private static void setOracleTypeAlias(Entity entity, String type) {
        if (aliases.getProperty("TYPE." + type + ".alias") == null) {
            aliases.setProperty("TYPE." + type + ".alias", type);
        }
    }

    private static String getOracleTableAlias(Entity entity, String defaultValue) {
        //return aliases.getProperty(entity.getFullPath() + ".TABLE.alias", defaultValue);
        return aliases.getProperty("TABLE." + defaultValue + ".alias", defaultValue);
    }

    private static void setOracleTableAlias(Entity entity, String table) {
        if (aliases.getProperty("TABLE." + table + ".alias") == null) {
            aliases.setProperty("TABLE." + table + ".alias", table);
        }
    }

    private static String getOracleSMTypeAlias(StateMachine sm, String smType) {
        return aliases.getProperty("TYPE." + smType + ".alias", smType);
    }

    private static void setOracleSMTypeAlias(StateMachine sm, String smType) {
        if (aliases.getProperty("TYPE." + smType + ".alias") == null) {
            aliases.setProperty("TYPE." + smType + ".alias", smType);
        }
    }

    private static String getOracleSMTableAlias(StateMachine sm, String smTable) {
        return aliases.getProperty("TABLE." + smTable + ".alias", smTable);
    }

    private static void setOracleSMTableAlias(StateMachine sm, String smTable) {
        if (aliases.getProperty("TABLE." + smTable + ".alias") == null) {
            aliases.setProperty("TABLE." + smTable + ".alias", smTable);
        }
    }

    private static String getOracleSMStateAlias(StateMachine sm, String state, String defaultValue) {
        return aliases.getProperty("COLUMN." + defaultValue + ".alias", defaultValue);
    }

    private static void setOracleSMStateAlias(StateMachine sm, String state, String alias) {
        if (aliases.getProperty("COLUMN." + alias + ".alias") == null) {
            //aliases.setProperty(entity.getFullPath() + ".PROPERTY." + name + ".alias", name);
            aliases.setProperty("COLUMN." + alias + ".alias", alias);
        }
    }
}
