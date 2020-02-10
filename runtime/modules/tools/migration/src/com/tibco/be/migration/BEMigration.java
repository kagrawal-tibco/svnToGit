package com.tibco.be.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Properties;

import com.tibco.be.migration.expimp.DefaultExpImpContext;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpProvider;
import com.tibco.be.migration.expimp.ExpImpProviderFactory;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.MigrationRuleServiceProvider;
import com.tibco.be.orcltojdbc.BEBackingStoreMigrator;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 14, 2008
 * Time: 1:42:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEMigration {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    //private static final String VERBOSE = "-v|-verbose|/v|/verbose";
    //private static final String DEBUG = "-d|-debug|/d|/debug";
    private static final String DB_COPY = "-copy|/copy";
    private static final String DB_EXPORT = "-E|-export|/e|/export";
    private static final String DB_IMPORT = "-I|-import|/I|/import";
    private static final String INPUT_PATH = "-input|-i|/input|/i";
    private static final String OUTPUT_PATH = "-output|-o|/output|/o";
    private static final String PROJECT_FILE_PATH = "-ear|/ear|-project|/project|repourl|/repourl";
    private static final String DATABASE = "-db|/db";
    private static final String DB_CONN_STR = "-conn|/conn";
    private static final String PARTITION_ID = "-partition|/partition";
    private static final String BDB = "-bdb|/bdb";
    private static final String HELP = "-h|-help|/h|/help";

    private static boolean verbose;
    private static boolean debug;
    EnvironmentInfo einfo = EnvironmentInfo.getDefault();
    private static boolean dbCopy = false; //default is export
    private static boolean bdoImport = false; //default is export
    private static String repoUrl;
    private static BEProperties beProperties;
    private static MigrationRuleServiceProvider ruleServiceProvider;

    public static void main(String [] args) {
        try {
            if (!initEnv(args, "be-migration", "be-migration.tra")) {
                return;
            }
            if (dbCopy) {
                copyFacts();
            } else if (bdoImport) {
                importFacts();
            } else {
                exportFacts();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("Failed in processing the migration. Please contact TIBCO Support with this exception: " + e.getMessage());
        }
    }


    private static boolean initEnv(String[] args, String name, String defaultTraFile) throws Exception {

        try {
            String propertyFile = System.getProperty("wrapper.tra.file", defaultTraFile);
            beProperties = BEProperties.loadDefault();
            File f = new File(propertyFile);
            if (f.exists()) {
                beProperties.load(new FileInputStream(f));
                Properties p = beProperties.getBranch("tibco.env");
                System.getProperties().putAll(p);
            }
            if (!processCommandLine(args)) {
                showUsage();
                return false;
            }
            repoUrl = beProperties.getProperty(BEMigrationProperties.PROP_PROJECT_FILE_PATH);
            ruleServiceProvider = new MigrationRuleServiceProvider(name, repoUrl, beProperties);

            String mode = beProperties.getProperty(BEMigrationProperties.PROP_MIGRATION_MODE);
            dbCopy = ((mode != null) && (BEMigrationConstants.DBCOPY.equalsIgnoreCase(mode)));
            bdoImport = ((mode != null) && (BEMigrationConstants.IMPORT.equalsIgnoreCase(mode)));
            String method = beProperties.getProperty(BEMigrationProperties.PROP_EXPIMP_METHOD);
            String outPath = beProperties.getProperty(BEMigrationProperties.PROP_OUTPUT_URL);
            String connStr = beProperties.getProperty(BEMigrationProperties.PROP_DB_CONN_STR);
            String barName = beProperties.getProperty(BEMigrationProperties.PROP_BAR_NAME);
            String pid = beProperties.getProperty(BEMigrationProperties.PROP_PARTITION_ID);
            if (args.length != 0) {
                System.out.println("Migration using " + EnvironmentInfo.getDefault().getCommandLineArgs2String());
            } else {
                System.err.println("No arguments specified. Using properties:\n\t"
                    + (mode == null ? "" : BEMigrationProperties.PROP_MIGRATION_MODE + " = " + mode + "\n\t")
                    + (method == null ? "" : BEMigrationProperties.PROP_EXPIMP_METHOD + " = " + method + "\n\t")
                    + BEMigrationProperties.PROP_INPUT_URL + " = " + beProperties.getProperty(BEMigrationProperties.PROP_INPUT_URL) + "\n\t"
                    + (outPath == null ? "" : BEMigrationProperties.PROP_OUTPUT_URL + " = " + outPath + "\n\t")
                    + BEMigrationProperties.PROP_PROJECT_FILE_PATH + " = " + beProperties.getProperty(BEMigrationProperties.PROP_PROJECT_FILE_PATH) + "\n\t"
                    + (connStr == null ? "" : BEMigrationProperties.PROP_DB_CONN_STR + " = " + connStr + "\n\t")
                    + (barName == null ? "" : BEMigrationProperties.PROP_BAR_NAME + " = " + barName + "\n\t")
                    + (pid == null ? "" : BEMigrationProperties.PROP_PARTITION_ID + " = " + pid));
            }
        } catch (Throwable e) {
            System.err.println("Failed initializing the Migration environment. Please check your environment, repoUrl, DB path");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void exportMain(String[] args) {
        try {
            if (!initEnv(args, "be-export", "be-export.tra")) {
                return;
            }
            exportFacts();
        }
        catch (Throwable t) {
            System.err.println("Failed in processing the migration. Please contact TIBCO Support with this exception");
            t.printStackTrace();
        }
        finally {
        }
    }

    private static void copyFacts() throws Exception {
        BEBackingStoreMigrator migrator = new BEBackingStoreMigrator();
        migrator.init(ruleServiceProvider, beProperties);
        migrator.migrate();
        migrator.shutdown();
    }
    
    private static void exportFacts() throws Exception {
        Properties prop = new Properties();
        prop.putAll(beProperties);  //Make a new copy

        GlobalVariablesProvider gvp = (GlobalVariablesProvider) ruleServiceProvider.getGlobalVariables();
        String versionKey = gvp.getPackagedComponentVersion("be-engine");
        ExpImpProviderFactory factory = ExpImpProviderFactory.getInstance();
        ExpImpProvider provider = factory.getExportProvider(versionKey);
        if (provider == null)
            throw new Exception("Unsupported version " + versionKey);

        ExpImpContext context = new DefaultExpImpContext(ruleServiceProvider, prop);
        ExpImpStats stats = new ExpImpStats();
        provider.doExport(context, stats);
    }

    /**
     * This Main method is for TRA purpose only
     *
     * @param args
     */
    public static void importMain(String[] args) {
        try {
            if (initEnv(args, "be-import", "be-import.tra")) {
                return;
            }
            importFacts();
        }
        catch (Throwable t) {
            System.err.println("Failed in processing the migration. Please contact TIBCO Support with this exception");
            t.printStackTrace();
        }
        finally {
        }
    }

    public static void importFacts() throws Exception {
        Properties prop = new Properties();
        prop.putAll(beProperties);  //Make a new copy

        GlobalVariablesProvider gvp = (GlobalVariablesProvider) ruleServiceProvider.getGlobalVariables();
        String versionKey = gvp.getPackagedComponentVersion("be-engine");
        ExpImpProviderFactory factory = ExpImpProviderFactory.getInstance();
        ExpImpProvider provider = factory.getImportProvider(versionKey);
        if (provider == null)
            throw new Exception("Unsupported version " + versionKey);

        ExpImpContext context = new DefaultExpImpContext(ruleServiceProvider, prop);
        ExpImpStats stats = new ExpImpStats();
        provider.doImport(context, stats);
    }

    private static boolean processCommandLine(String[] args) throws Exception {
        int configArgIndex = 0;
        boolean foundToJdbc = false;
        boolean foundImport = false;
        boolean foundExport = false;
        boolean foundInput = false;
        boolean foundOutput = false;
        boolean foundProject = false;
        boolean foundBdb = false;
        boolean foundDb = false;
        boolean foundDbConn = false;
        boolean foundPartitionId = false;
        int[] configArgs = null;

        if (args.length != 0) {
            EnvironmentInfo.getDefault().setAllArgs(args);
            configArgs = new int[args.length];
            configArgs[0] = -1; // need to initialize the first element to something that could not be an index.
        }

        for (int i = 0; i < args.length; i++) {
            boolean found = false;

            if (args[i].matches(HELP)) {
                showUsage();
            }
            
            // check for args without parameters (i.e., a flag arg)
            if (args[i].matches(DB_COPY)) {
                beProperties.setProperty(BEMigrationConstants.PROP_MIGRATION_MODE, BEMigrationConstants.DBCOPY);
                found = true;
                foundToJdbc = true;
            } else if (args[i].matches(DB_IMPORT)) {
                beProperties.setProperty(BEMigrationConstants.PROP_MIGRATION_MODE, BEMigrationConstants.IMPORT);
                found = true;
                foundImport = true;
            } else if (args[i].matches(DB_EXPORT)) {
                beProperties.setProperty(BEMigrationConstants.PROP_MIGRATION_MODE, BEMigrationConstants.EXPORT);
                found = true;
                foundExport = true;
            } else if (args[i].matches(DATABASE)) {
                beProperties.setProperty(BEMigrationConstants.PROP_EXPIMP_METHOD, BEMigrationConstants.DATABASE);
                found = true;
                foundDb = true;
            } else if (args[i].matches(BDB)) {
                beProperties.setProperty(BEMigrationConstants.PROP_EXPIMP_METHOD, BEMigrationConstants.BDB);
                found = true;
                foundBdb = true;
            }

            if (found) {
                configArgs[configArgIndex++] = i;
                continue;
            }
            // check for args with parameters. If we are at the last argument or if the next one
            // has a '-' as the first character, then we can't have an arg with a parm so continue.
            if (i == args.length - 1 || args[i + 1].startsWith("-")) {
                continue;
            }
            String arg = args[++i];

            // look for the input path
            if (args[i - 1].matches(INPUT_PATH)) {
                beProperties.setProperty(BEMigrationConstants.PROP_INPUT_URL, arg);
                found = true;
                foundInput = true;
            } else if (args[i - 1].matches(OUTPUT_PATH)) { // look for output path
                beProperties.setProperty(BEMigrationConstants.PROP_OUTPUT_URL, arg);
                found = true;
                foundOutput = true;
            } else if (args[i - 1].matches(PROJECT_FILE_PATH)) { // look for project path
                beProperties.setProperty(BEMigrationConstants.PROP_PROJECT_FILE_PATH, arg);
                found = true;
                foundProject = true;
            } else if (args[i - 1].matches(DB_CONN_STR)) { // look for connection string
                beProperties.setProperty(BEMigrationConstants.PROP_DB_CONN_STR, arg);
                found = true;
                foundDbConn = true;
            } else if (args[i - 1].matches(PARTITION_ID)) { // looks for partition
                String[] pkv = arg.split(":");
                if(pkv.length == 2) {
                    beProperties.setProperty(BEMigrationConstants.PROP_BAR_NAME, pkv[0]);
                    beProperties.setProperty(BEMigrationConstants.PROP_PARTITION_ID, pkv[1]);
                    found = true;
                    foundPartitionId = true;
                }
            }

            // done checking for args.  Remember where an arg was found
            if (found) {
                configArgs[configArgIndex++] = i - 1;
                configArgs[configArgIndex++] = i;
            }
        }

        EnvironmentInfo.getDefault().setAppArgs(args);

        if (!foundProject &&
                (beProperties.getProperty(BEMigrationConstants.PROP_PROJECT_FILE_PATH) == null || beProperties.getProperty(BEMigrationConstants.PROP_PROJECT_FILE_PATH).trim() == "")) {
            System.out.println("Argument -ear must be specified");
            return false;
        }
        if (foundToJdbc) {
            // Nothing more to do here - rest of the configuration is in the TRA file
            return true;
        }
        if (!foundImport) {
           if(beProperties.getProperty(BEMigrationConstants.PROP_MIGRATION_MODE) != null && beProperties.getProperty(BEMigrationConstants.PROP_MIGRATION_MODE).trim().equals(BEMigrationConstants.IMPORT))
              foundImport = true;
        }
        if (!foundExport) {
           if(beProperties.getProperty(BEMigrationConstants.PROP_MIGRATION_MODE) != null && beProperties.getProperty(BEMigrationConstants.PROP_MIGRATION_MODE).trim().equals(BEMigrationConstants.EXPORT))
              foundExport = true;
        }
        if (!foundImport && !foundExport) {
            System.out.println("\n\t****** Argument -import or -export not specified, default options -export -bdb will be used.******\n");
            foundExport = true;
            foundBdb = true;
        }
        if (foundBdb && foundDb) {
            System.out.println("Argument -bdb  cannot be used with -db");
            return false;
        }
        if (foundDb && !foundDbConn) {
            if (beProperties.getProperty(BEMigrationConstants.PROP_DB_CONN_STR) != null) {
               foundDbConn = true;
            } else {
                System.out.println("Argument -conn  must be specified with -db");
                return false;
            }
        }
        if (foundDb && !foundPartitionId) {
            if (beProperties.getProperty(BEMigrationConstants.PROP_PARTITION_ID) != null) {
                foundPartitionId = true;
            } else {
                System.out.println("Argument -partition  must be specified with -db");
                return false;
            }
        }
        if (foundExport && foundBdb && !foundInput) {
            if (beProperties.getProperty(BEMigrationConstants.PROP_INPUT_URL) != null) {
                foundInput = true;
            } else {
                System.out.println("Argument -input  must be specified for Bdb file location ");
                return false;
            }
        }
        if (foundExport && foundBdb && !foundOutput) {
            if (beProperties.getProperty(BEMigrationConstants.PROP_OUTPUT_URL) != null) {
                foundOutput = true;
            } else {
                System.out.println("Argument -output  must be specified for CSV file location ");
                return false;
            }
        }
        if (foundImport && foundBdb && !foundInput) {
            if (beProperties.getProperty(BEMigrationConstants.PROP_INPUT_URL) != null) {
                foundInput = true;
            } else {
                System.out.println("Argument -input  must be specified for CSV file location ");
                return false;
            }
        }
        /*
        if (foundImport && foundBdb && !foundOutput) {
            System.out.println("Argument -output  must be specified for Bdb file location ");
            return false;
        }
         */

        //logger = null;
        return true;
    }

    private static void showUsage() {
        PrintStream ps = System.err;
        System.out.println("\nUsage:\n");
      
        System.out.println("be-migration {-copy|-export -bdb|-import -db} -input <input_url> [-output <output_url>] -ear <EAR_path> [-conn <conn_str>] [-partition <pid>] [-help]\n");
        System.out.println("-copy         Copies an Oracle backing store database to a target database (default target is JDBC backing store). Same as /copy");
        System.out.println("-export -bdb  Export from a persistent database. Same as -E -bdb or /export /bdb or /E /bdb");
        System.out.println("-import -db   Import to an Oracle backing store database. Same as -I -db or /import /db or /I /db");
        System.out.println("              Default options are -export -bdb when not specified");
        System.out.println("-input        If -export -bdb specified, then path to the persistent database file location;");
        System.out.println("              If -import, the path to the CSV file location.");
        System.out.println("              Same as -i or /input or /i");
        System.out.println("-output       Path to the CSV file location, used when -export -dbd specified. Same as -o or /output or /o");
        System.out.println("-ear          Path to the BE ear file. Same as -repourl or -project or /ear or /repourl or /project");
        System.out.println("-conn         <jdbc>:<vendor>:<drivertype>:<user>/<password>@<host>:<port>:<database>. Same as /conn");
        System.out.println("-partition    <BusinessEvents archive name>:<partition id key>. Same as /partition");
        System.out.println("-help         Display this usage. Same as -h or /help or /h");

        System.exit(0);
    }
}
