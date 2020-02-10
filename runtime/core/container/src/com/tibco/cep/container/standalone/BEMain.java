package com.tibco.cep.container.standalone;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import com.tibco.be.util.BEJarVersionsInspector;
import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.container.standalone.ClassPathReporter.ClassPathReportHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.ShutdownWatcher;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.service.management.process.EngineMBeansManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.ResourceManager;
import com.tibco.sdk.MServiceState;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 29, 2006
 * Time: 8:09:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class BEMain {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


    protected Properties env = new Properties();
    protected Vector propFiles = new Vector();   // list or property files
    protected static final String PROP_FILE_PFX = "be.property.file";
    protected static final String HAWK_RULE_ADMINISTRATOR = "com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator";
    protected static final Pattern JMX_NAME_PATTERN = Pattern.compile("(\\d+)@.+");
    protected String[] argv;
    boolean showEnv = false;

    public static final String BRK = System.getProperty("line.separator", "\n");
    private static boolean isMain = false;

    public static void main(String[] argv)
    {
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        registry.initBasic();
        registry.initShutdownWatcher();
        ShutdownWatcher shutdownWatcher = registry.getShutdownWatcher();

        try {
            isMain = true;
            BEMain main = new BEMain(argv);
            main.jumpStart();
        }
        catch (Throwable e) {
            LogManager logManager = LogManagerFactory.getLogManager();
            if (logManager != null) {
                logManager.getLogger(BEMain.class).log(Level.FATAL, e, e.getMessage());
            }
            else {
                e.printStackTrace();
            }
            shutdownWatcher.exitSystem(-1);
        }
    }

    public static boolean isMain() {
        return isMain;
    }


    protected BEMain(String[] args) {

        try {
            MServiceState.set(MServiceState.INITIALIZING);
        } catch (Exception ignored) {}

        this.argv = args;
        String defProp = System.getProperty("wrapper.tra.file", "be-engine.tra");
        //boolean showEnv = System.getProperty("be.trace.roles","").indexOf("debugRole") >= 0;

        env.setProperty("be.bootstrap.property.file", defProp);

        final String hawkHome = System.getenv("HAWK_HOME");
        if ((null != hawkHome) && new File(hawkHome).isDirectory()) {
            env.setProperty(SystemProperty.RULE_ADMINISTRATOR.getPropertyName(), HAWK_RULE_ADMINISTRATOR);
        }

        //This will update all java.properties in the tra file to be updated back in the
        // System.Properties. It will selectively replace only ones that are not defined.
        env.setProperty("__update.system.property__", "true");
        System.setProperty("tangosol.coherence.events.limit","0"); // set to avoid deadlock.
        parseArguments(args);
        
        String propfileName = null;
        for (int index=0; index<args.length; index++) {
        	if (args[index].trim().equalsIgnoreCase("--propFile".trim())) {
         		propfileName=args[index+1];
        		break;
        	}
        }
        if (propfileName != null)
        	System.out.println("Using property file: " + propfileName);
        else
        	System.out.println("Using property file: " + defProp);
        if (showEnv) {
            Iterator r = System.getProperties().entrySet().iterator();
            while (r.hasNext()) {
                Map.Entry e = (Map.Entry) r.next();
                System.out.println(e.getKey() + "=" + e.getValue());
            }
        }

    }

    /**
     * Jump Start the standalone container
     */
    public void jumpStart() throws Exception{
        RuleServiceProvider provider = RuleServiceProviderManager.getInstance().newProvider(null, env);
        RuleServiceProviderManager.addShutdownHook();
        RuleServiceProviderManager.getInstance().setDefaultProvider(provider);
        ResourceManager manager = ResourceManager.getInstance();
        manager.addResourceBundle("com.tibco.cep.container.messages", provider.getLocale());

        String prologue = printPrologue(argv);

        final Logger logger = provider.getLogger(this.getClass());
        logger.log(Level.INFO, prologue);

        String arg ="";
        for (int i=0; i < argv.length; i++) {
        	arg += " " + argv[i];
        }

        logger.log(Level.INFO, "%s %s.%s%s",
        		cep_containerVersion.getComponent(), cep_containerVersion.version, cep_containerVersion.build, arg);

       
        
        logJarVersions(logger);
        provider.initProject();
        
        EngineMBeansManager.createJMXConnectorServer(provider);
        EngineMBeansManager.registerManagementTableMbean(provider);
        
        if (provider.getProject().isCacheEnabled()) {
        	if (RuleServiceProviderManager.isConfigAsCacheServer(provider.getProperties()))
        		provider.configure(RuleServiceProvider.MODE_CLUSTER_CACHESERVER);
        	else {
        		provider.configure(RuleServiceProvider.MODE_CLUSTER);
        	}
        } else {
        	provider.configure(RuleServiceProvider.MODE_PRIMARY);
        }

        try {
        	new ClassPathReportHandler().registerSelf();
        } catch (Exception e) {
        	logger.log(Level.WARN, e, "Class path reporter MBean failed");
        }

        if (System.getProperty(SystemProperty.DEBUGGER_SERVICE_ENABLED.getPropertyName(), Boolean.FALSE.toString()).trim().equalsIgnoreCase(Boolean.TRUE.toString())) {
            logger.log(Level.INFO, "Starting debugger service...");

            DebuggerService debuggerService  = DebuggerService.getInstance();
            debuggerService.init(null,provider);
            debuggerService.start();
        }

        try {
            MServiceState.set(MServiceState.RUNNING);
        } catch(Throwable ignored) {}

        awaitUntilJVMTerminates();
    }


    protected void awaitUntilJVMTerminates()  {
    	ReentrantLock latch = new ReentrantLock();
    	Condition latchCondition = latch.newCondition();

    	latch.lock();
    	try {
    		try {
    			latchCondition.await();
    		} catch (InterruptedException e) {
    			//Ignore.
    		}
    	} finally {
    		latch.unlock();
    	}

    	Thread t = Thread.currentThread();
    	String msg = "Thread [" + t.getName() +"] exiting " + getClass().getName();
    	System.out.println(msg);
    }

    private void parseArguments(String argv[]) {
        try {
            int j = 1;
            boolean foundCdd = false;
            boolean foundEar = false;
            boolean foundPuid = false;
            for (int i = 0; i < argv.length; i++) {
                String key = argv[i];

                //-h or /h or -help or /help
                if (key.matches("-v|/v|-version|--version|/version|/--version")) {
                    System.out.println(MessageFormat.format("{0} {1}.{2}",
                            cep_containerVersion.getComponent(), cep_containerVersion.version, cep_containerVersion.build));
                    logJarVersions();

                    System.exit(0);
                } else if (key.matches("-h|/h|-help|/help|--help|/--help")) {
                    printUsage();
                    System.exit(0);
                } else if (key.matches("-property|/property|-system:propFile|--propFile|/--propFile")) {
                    ++i;
                    env.put("be.bootstrap.property.file", argv[i]);
                } else if (key.matches("(?:-c)|(?:/c)|(?:-clusterconfig)|(?:/clusterconfig)")) {
                    if (foundCdd) {
                        printUsage();
                        System.exit(-1);
                    }
                    ++i;
                    env.put(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName(), argv[i]);
                    foundCdd = true;
                } else if (key.matches("(?:-u)|(?:/u)|(?:-unit)|(?:/unit)")) {
                    if (foundPuid) {
                        printUsage();
                        System.exit(-1);
                    }
                    ++i;
                    env.put(SystemProperty.PROCESSING_UNIT_ID.getPropertyName(), argv[i]);
                    foundPuid = true;
                } else if (key.matches("-p|/p")) {
                    ++i;
                    // support multiple -p propFile instead of just one
                    //env.put("be.bootstrap.property.file2", argv[i]);
                    String propertyFile = PROP_FILE_PFX + "." + j;
                    env.put(propertyFile, argv[i]);
                    ++j;
                } else if (key.matches("-n|/n|-name|/name")) {
                    ++i;
                    env.put(SystemProperty.ENGINE_NAME.getPropertyName(), argv[i]);

                } else if (key.matches("-d|/d|-debug|/debug")) {
                    env.put(SystemProperty.DEBUGGER_SERVICE_ENABLED.getPropertyName(), Boolean.TRUE.toString());
                } else if (key.matches("-showenv")) {
                    showEnv = true;
                } else if (foundEar) {
                    printUsage();
                    System.exit(-1);
                } else {
                    env.put("tibco.repourl", argv[i]);
                    foundEar = true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            printUsage();
            System.exit(-1);
        }

        this.env.setProperty(SystemProperty.PROCESSING_UNIT_ID_DEFAULT.getPropertyName(), "default");
    }


    private void printUsage() {
        System.out.println("be-engine [-h] [--propFile <tra file>] [-p <custom property file>] [-n <engine name>] [-d]"
                +"-c <config file> -u <unit id> <ear>");

        System.out.println("-h  Displays this usage. Optional." + BRK
                + "    Same as -help or /help or /h." );

        System.out.println("--propFile Location of the TRA file. Optional." + BRK
                + "    Same as -property or /property or -system:propFile or /--propFile.");

        System.out.println("-p  Location of a custom property file. Optional." + BRK
                + "    Same as /p.");

        System.out.println("-n  Name for the engine. Optional. The default value is the host name." + BRK
                + "    Same as -name or /name or /n.");

        System.out.println("-d  Starts the debugger service on the engine for remote debugging. Optional."+ BRK
                + "    Same as -debug or /debug or /d.");

        System.out.println("-c  Cluster configuration file." + BRK
                + "    Same as -clusterconfig or /clusterconfig or /c." );

        System.out.println("-u  Processing unit name." + BRK
                + "    Same as -unit or /unit or /u.");

        System.out.println("<ear>  URL of the EAR." );

    }


    static public String printPrologue(String args[]) {

        final StringBuffer buffer = new StringBuffer(cep_containerVersion.line_separator)
            .append(cep_containerVersion.asterisks)
            .append(cep_containerVersion.line_separator)
            .append("\t").append(cep_containerVersion.getComponent()).append(" ").append(cep_containerVersion.version)
            .append(".").append(cep_containerVersion.build).append(" (").append(cep_containerVersion.buildDate)
            .append(")");
//        if ((VersionTag.RELEASE_PATCH_STR != null) && (VersionTag.RELEASE_PATCH_STR.length() > 0)) {
//            buffer.append(" ");
//            buffer.append("patch:"+VersionTag.RELEASE_PATCH_STR);
//        }

        buffer.append(cep_containerVersion.line_separator)
                .append("\t").append("Using arguments :");
        final StringBuffer userArgs = new StringBuffer();
        for (int i=0; i < args.length; i++) {
            userArgs.append(args[i]).append(" ");
        }

        buffer.append(userArgs)
            .append(cep_containerVersion.line_separator)
            .append("\t").append(cep_containerVersion.copyright)
            .append(cep_containerVersion.line_separator)
            .append("\t").append(cep_containerVersion.readme)
            .append(cep_containerVersion.line_separator)
            .append(cep_containerVersion.asterisks)
            .append(cep_containerVersion.line_separator);

        return buffer.toString();
    }

    private void logJarVersions() {
    	PrintStream errorStream = System.err;
    	try {
    		System.setErr(new PrintStream(new ByteArrayOutputStream()));
    		final BEJarVersionsInspector inspector = new BEJarVersionsInspector();
    		final SortedMap versions = inspector.getJarVersions(null);
    		int maxLength = 0;
    		for (Iterator it = versions.keySet().iterator(); it.hasNext();) {
    			final String jarName = (String) it.next();
    			maxLength = Math.max(maxLength, jarName.length());
    		}
    		final char[] charSpaces = new char[maxLength];
    		Arrays.fill(charSpaces, ' ');
    		final String spaces = new String(charSpaces);
    		for (Iterator it = versions.entrySet().iterator(); it.hasNext();) {
    			final Map.Entry e = (Map.Entry) it.next();
    			final BEJarVersionsInspector.Version version = (BEJarVersionsInspector.Version) e.getValue();
    			final String versionString = version.getVersion();
    			final String component = version.getComponent();
    			final String license = version.getLicense();
    			System.out.println(String.format("    - %s : %s - %s   %s",
    					(version.getName() + spaces).substring(0, maxLength),
    					((null == versionString) ? "" : versionString),
    					((null == component) ? "" : component),
    					((null == license) ? "" : license)));
    		}

    		printClassPathReport(versions);
    	} finally {
    		System.setErr(errorStream);
    	}
    }

    private void printClassPathReport(SortedMap versions) {
        Map<String, String> classPathResults = null;

        try {
        	classPathResults = ClassPathReporter.fetchClassPathReport();

        	if (classPathResults != null) {
        		StringBuilder builder = new StringBuilder();

        		for (Iterator it = versions.entrySet().iterator(); it.hasNext();) {
        			final Map.Entry e = (Map.Entry) it.next();
        			final BEJarVersionsInspector.Version version = (BEJarVersionsInspector.Version) e.getValue();

        			String path = classPathResults.get(version.getName());

        			if (path != null) {
        				builder.append(String.format("%n    [%s]", path));
        			}
        		}

        		if (builder.length() > 0) {
        			String msg = "Class path report:" + builder;

        			System.out.println(msg);
        		}
        	}
        } catch (Throwable t) {
        	System.err.println("Class path reporting failed\n");
        	t.printStackTrace();
        }
    }


    private void logJarVersions(Logger logger) {
        final Level level = Level.INFO;
        if (!logger.isEnabledFor(level)) {
            return;
        }
        final BEJarVersionsInspector inspector = new BEJarVersionsInspector();
        final SortedMap versions = inspector.getJarVersions(null);
        int maxLength = 0;
        for (Iterator it = versions.keySet().iterator(); it.hasNext();) {
            final String jarName = (String) it.next();
            maxLength = Math.max(maxLength, jarName.length());
        }
        final char[] charSpaces = new char[maxLength];
        Arrays.fill(charSpaces, ' ');
        final String spaces = new String(charSpaces);
        logger.log(level, "Versions currently running:");
        for (Iterator it = versions.entrySet().iterator(); it.hasNext();) {
            final Map.Entry e = (Map.Entry) it.next();
            final BEJarVersionsInspector.Version version = (BEJarVersionsInspector.Version) e.getValue();
            final String versionString = version.getVersion();
            final String component = version.getComponent();
            final String license = version.getLicense();
            logger.log(level, " %s : %s - %s   %s",
                    (version.getName() + spaces).substring(0, maxLength),
                    ((null == versionString) ? "" : versionString),
                    ((null == component) ? "" : component),
                    ((null == license) ? "" : license));
        }

        printClassPathReport(logger, level, versions);
    }

    private void printClassPathReport(Logger logger, Level level, SortedMap versions) {
        Map<String, String> classPathResults = null;

        try {
            classPathResults = ClassPathReporter.fetchClassPathReport();

            if (classPathResults != null) {
                StringBuilder builder = new StringBuilder();

                for (Iterator it = versions.entrySet().iterator(); it.hasNext();) {
                    final Map.Entry e = (Map.Entry) it.next();
                    final BEJarVersionsInspector.Version version = (BEJarVersionsInspector.Version) e.getValue();

                    String path = classPathResults.get(version.getName());

                    if (path != null) {
                        builder.append(String.format("%n    [%s]", path));
                    }
                }

                if (builder.length() > 0) {
                    String msg = "Class path report:" + builder;

                    logger.log(level, msg);
                }
            }
        }
        catch (Throwable t) {
            logger.log(Level.DEBUG, t, "Class path reporting failed");
        }
    }
}
