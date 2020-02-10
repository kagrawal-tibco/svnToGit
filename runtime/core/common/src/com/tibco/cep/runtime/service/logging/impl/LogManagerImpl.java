package com.tibco.cep.runtime.service.logging.impl;

import static com.tibco.cep.runtime.service.logging.TraceMessageLayoutV2.PACKAGE_NAME_EXTRACTION_PATTERN;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.service.logging.TraceMessageLayoutV2;
import com.tibco.cep.runtime.util.SystemProperty;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggerRepository;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* User: Nicolas Prade
* Date: Oct 27, 2009
* Time: 7:36:49 PM
*/


public class LogManagerImpl
        implements LogManager, Service {

    //the default wild card name
    private static final String GLOBAL_WILDCARD = "*";
    //the default level to use
    private static final String DEFAULT_LEVEL = Level.INFO.toString();


    protected static final String CONSOLE_APPENDER_NAME = "console";
    protected static final LoggerRepository LOGGER_REPOSITORY = org.apache.log4j.LogManager.getLoggerRepository();
    protected static final ConcurrentHashMap<String, Logger> NAME_TO_LOGGER = new ConcurrentHashMap<String, Logger>();
    protected static final PrintStream ORIGINAL_ERR = System.err;
    protected static final PrintStream ORIGINAL_OUT = System.out;


    protected static PrintStream err = ORIGINAL_ERR;
    protected static PrintStream out = ORIGINAL_OUT;
    protected static boolean initialized = false;
    protected static boolean firstTime = true;

    protected static ConsoleAppender consoleAppender;
    protected static RollingFileAppender fileAppender;
    protected static RollingFileAppender loggerLoggerAppender;
    protected static org.apache.log4j.Logger loggerLogger;

    protected Level defaultLevel;
    protected LinkedHashMap<Pattern, Level> levels = new LinkedHashMap<Pattern, Level>();
    protected Collection<GlobalVariableDescriptor> gvs = new ArrayList<GlobalVariableDescriptor> ();

    final static Properties PROPERTIES = System.getProperties();
    final static boolean TRACE_ENABLE =
            Boolean.valueOf(PROPERTIES.getProperty(SystemProperty.TRACE_ENABLED.getPropertyName(), "true"));

    static {
        if(TRACE_ENABLE)
            init();
    }


    protected Constructor<? extends Logger> loggerConstructor;


    public LogManagerImpl() {
        synchronized (LogManagerImpl.class) {
            if (firstTime) { // Prevents overriding.
                firstTime = false;
                final Properties props = System.getProperties();
                try {
                    this.configureLoggerConstructor(props);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(TRACE_ENABLE) {
                    final Layout layout = getLayout(props);
                    configureConsoleAppender(props, layout);
                    configureFileAppender(props, layout);
                    configureLevels(props);
                }
            }
        }
    }


    public LogManagerImpl(Properties props) {
        synchronized (LogManagerImpl.class) {
            firstTime = false;
            try {
                this.configureLoggerConstructor(props);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(TRACE_ENABLE) {
                final Layout layout = getLayout(props);
                configureConsoleAppender(props, layout);
                configureFileAppender(props, layout);
                configureLevels(props);
            }
        }
    }

    public synchronized void close() {
        if (out != ORIGINAL_OUT) {
            System.setOut(ORIGINAL_OUT);
            out.close();
        }
        if (err != ORIGINAL_ERR) {
            System.setErr(ORIGINAL_ERR);
            err.close();
        }
        final org.apache.log4j.Logger rootLogger = LOGGER_REPOSITORY.getRootLogger();
        try {
            for (Logger log : NAME_TO_LOGGER.values()) {
                if (!rootLogger.equals(log)) {
                    log.close();
                }
            }
            for (Enumeration i = rootLogger.getAllAppenders(); i.hasMoreElements();) {
                final Appender appender = (Appender) i.nextElement();
                if (consoleAppender != null && appender != null && !consoleAppender.equals(appender)) {
                    appender.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rootLogger.removeAllAppenders();
        rootLogger.addAppender(consoleAppender);
        consoleAppender.setLayout(null);
        NAME_TO_LOGGER.clear();

        initialized = false;
    }


    protected static synchronized void configureConsoleAppender(Properties props, Layout layout) {

        final org.apache.log4j.Logger logger = LOGGER_REPOSITORY.getRootLogger();
        final String encoding = System.getProperty(SystemProperty.TRACE_TERM_ENCODING.getPropertyName(),
                System.getProperty("file.encoding"));

        if (out != ORIGINAL_OUT) {
            System.setOut(ORIGINAL_OUT);
            out.close();
            out = ORIGINAL_OUT;
        }
        if (Boolean.valueOf(props.getProperty(SystemProperty.TRACE_TERM_SYSOUT_REDIRECT.getPropertyName(), "true"))) {
            out = createPrintStream(logger, org.apache.log4j.Level.DEBUG, encoding);
            System.setOut(out);
        }

        if (err != ORIGINAL_ERR) {
            System.setErr(ORIGINAL_ERR);
            err.close();
            err = ORIGINAL_ERR;
        }
        if (Boolean.valueOf(props.getProperty(SystemProperty.TRACE_TERM_SYSERR_REDIRECT.getPropertyName(), "true"))) {
            err = createPrintStream(logger, org.apache.log4j.Level.ERROR, encoding);
            System.setErr(err);
        }

        consoleAppender.setLayout(layout);
        consoleAppender.setEncoding(encoding);

        if (!Boolean.valueOf(props.getProperty(SystemProperty.TRACE_TERM_ENABLED.getPropertyName(), "true"))) {
            // The consoleAppender cannot be removed because log4j wants at least one appender.
            logger.setLevel(org.apache.log4j.Level.OFF);
        }
    }

    /*
     * Create a PrintStream for LoggerOutputStream(level,logger) and UTF-8 encoding
     */
    private static PrintStream createPrintStream(
            org.apache.log4j.Logger logger,
            org.apache.log4j.Level level,
            String encoding) {
        try {
            return (new PrintStream(new LoggerOutputStream(level, logger), false, encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    protected static synchronized void configureFileAppender(Properties props, Layout layout) {
        fileAppender.setEncoding("UTF-8");
        fileAppender.setAppend(
                Boolean.valueOf(props.getProperty(SystemProperty.TRACE_FILE_APPEND.getPropertyName(), "true")));

		fileAppender.setLayout(layout);
		if (!isGlobalVar(props.getProperty(SystemProperty.TRACE_FILE_MAX_NUM.getPropertyName())))
			fileAppender.setMaxBackupIndex(
					Integer.valueOf(props.getProperty(SystemProperty.TRACE_FILE_MAX_NUM.getPropertyName(), "10")));

		if (!isGlobalVar(props.getProperty(SystemProperty.TRACE_FILE_MAX_SIZE.getPropertyName())))
			fileAppender.setMaximumFileSize(
					Long.valueOf(props.getProperty(SystemProperty.TRACE_FILE_MAX_SIZE.getPropertyName(), "1000000")));

        final String logDirPath = props.getProperty(SystemProperty.TRACE_FILE_DIR.getPropertyName(), "logs");
        String logFilePath = props.getProperty(SystemProperty.TRACE_FILE_NAME.getPropertyName(), null);
        if (logFilePath == null) {
            logFilePath = logDirPath + File.separatorChar + makeName(props) + ".log";
        } else {
            logFilePath = logDirPath + File.separatorChar + logFilePath;
        }

        final boolean enabled =
                Boolean.valueOf(props.getProperty(SystemProperty.TRACE_FILE_ENABLED.getPropertyName(), "true"));

        try {
            final File logFile = new File(logFilePath).getCanonicalFile();
            logFile.getParentFile().mkdirs();
            fileAppender.setFile(logFile.getCanonicalPath());
        } catch (Exception e) {
            if (enabled) {
                throw new RuntimeException(e);
            }
        }

        if (enabled) {
            LOGGER_REPOSITORY.getRootLogger().addAppender(fileAppender);
        } else {
            LOGGER_REPOSITORY.getRootLogger().removeAppender(fileAppender);
        }
    }


    protected synchronized void configureLevels(Properties props) {
        //add the default level
        setLevel(GLOBAL_WILDCARD, DEFAULT_LEVEL);
        //get the level configuration
        String levelString = props.getProperty(SystemProperty.TRACE_ROLES.getPropertyName());
        final List<String> errors = new ArrayList<String>();
        if(!isGlobalVar(levelString)){
			if (levelString != null) {
				// parse the level configuration
				for (final String roleWithLevel : levelString.split("\\s+")) {
					// further split the individual level configs
					final String[] parts = roleWithLevel.split(":");
					// call setLevel
					try {
						if (parts != null && parts.length == 2) setLevel(parts[0], parts[1]);
					} catch (Exception e) {
						errors.add(e.getMessage());
					}
				}
			}
			if (!errors.isEmpty()) {
				try {
					ORIGINAL_ERR.println("Warning: Log configuration error" + ((errors.size() > 1) ? "s:" : ":"));
					for (final String error : errors) {
						ORIGINAL_ERR.println(error);
					}
				} catch (Throwable ignored) {
				}
			}
        }
    }
    
    
    public synchronized void configureMaxNumWithGv(Properties props,String maxNumString) {
    	fileAppender.setMaxBackupIndex(Integer.valueOf(maxNumString));
    }
    
    public synchronized void configureMaxSizeWithGv(Properties props,String maxNumString) {
    	fileAppender.setMaximumFileSize(Integer.valueOf(maxNumString));
    }
    
    public synchronized void configureLevelsWithGv(Properties props,String levelString) {
        //add the default level
    	setLevel(GLOBAL_WILDCARD, DEFAULT_LEVEL);
        //get the level configuration
       		final List<String> errors = new ArrayList<String>();
			if (levelString != null) {
				// parse the level configuration
				for (final String roleWithLevel : levelString.split("\\s+")) {
					// further split the individual level configs
					final String[] parts = roleWithLevel.split(":");
					// call setLevel
					try {
						setLevel(parts[0], parts[1]);
					} catch (Exception e) {
						errors.add(e.getMessage());
					}
				}
			}
			if (!errors.isEmpty()) {
				try {
					ORIGINAL_ERR
							.println("Warning: Log configuration error" + ((errors.size() > 1) ? "s:" : ":"));
					for (final String error : errors) {
						ORIGINAL_ERR.println(error);
					}
				} catch (Throwable ignored) {
				}
			}
		
     }

    protected synchronized void configureLoggerConstructor(Properties properties)
            throws Exception {
        String className = properties.getProperty(SystemProperty.TRACE_LOGGER_CLASS_NAME.getPropertyName());
        final Class<? extends Logger> loggerClass;
        if ((null == className) || "".equals(className.trim())) {
            loggerClass = LoggerImpl.class;
        } else {
            loggerClass = (Class<? extends Logger>) Class.forName(className);
        }
        this.loggerConstructor = loggerClass.getDeclaredConstructor(new Class[]{String.class});
    }


    public String getId() {
        return LogManagerImpl.class.getName();
    }


    protected static Layout getLayout(Properties props) {
        final String name = props.getProperty("be.cluster.name", makeName(props));

        Boolean enabled = Boolean.valueOf(props.getProperty(SystemProperty.TRACE_LAYOUT_ENABLED.getPropertyName(), "false"));
        String dateFormat = props.getProperty(SystemProperty.TRACE_DATE_FORMAT.getPropertyName());

        if (enabled == false) {
            return new TraceMessageLayoutV2(name, dateFormat);
        }

        final String layoutClass = props.getProperty(SystemProperty.TRACE_LAYOUT_CLASS_NAME.getPropertyName());
        if (layoutClass == null) {
            return new TraceMessageLayoutV2(name, dateFormat);
        }

        try {
            final Class clazz = Class.forName(layoutClass);
            final String arg = props.getProperty(SystemProperty.TRACE_LAYOUT_CLASS_ARG.getPropertyName()); //no arg means default constructor
            if (arg == null) {
                return (Layout) clazz.newInstance();
            }
            final Constructor constructor = clazz.getConstructor(new Class[]{String.class});
            return (Layout) constructor.newInstance(arg);
        }
        catch (Exception e) {
            System.out.println("Layout class [" + layoutClass + "] specified not found - using default");
            return new TraceMessageLayoutV2(name, dateFormat);
        }
    }


    public Logger getLogger(String name) {
        Logger l = NAME_TO_LOGGER.get(name);
        if (null == l) {
            final Logger newLogger;
            try {
                newLogger = this.loggerConstructor.newInstance(name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            l = NAME_TO_LOGGER.putIfAbsent(name, newLogger);
            if ((null == l) && (null != loggerLogger)) {
                loggerLogger.info(name);
            }
            l = NAME_TO_LOGGER.get(name);
            //set the level on the logger
            //start with the default level
            if(TRACE_ENABLE) {
                l.setLevel(defaultLevel);
                for (Entry<Pattern, Level> entry : levels.entrySet()) {
                    if (entry.getKey().matcher(name.toLowerCase()).matches()) {
                        l.setLevel(entry.getValue());
                    }
                }
            }
        }
        return l;
    }


    public Logger getLogger(Class c) {
        if (null == c) {
            throw new IllegalArgumentException();
        }

        final Package p = c.getPackage();
        if (null == p) {
            return this.getLogger("default");
        }

        final String packageName = p.getName();
        final Matcher m = PACKAGE_NAME_EXTRACTION_PATTERN.matcher(packageName);
        if (m.find()) {
            return this.getLogger(m.group(1));
        } else {
            return this.getLogger(packageName);
        }
    }

    @Override
    public void setLevel(String logNamePattern, String level) {
        if (logNamePattern == null || logNamePattern.trim().length() == 0) {
            throw new IllegalArgumentException("Invalid log name pattern");
        }
        if (level == null || level.trim().length() == 0) {
            throw new IllegalArgumentException("Invalid level");
        }
        Level levelObj = Level.valueOf(level);
        if (levelObj == null) {
            try {
                levelObj = Level.valueOf(new Integer(level));
            } catch (NumberFormatException ignore) {
            }
            if (levelObj == null) {
                throw new IllegalArgumentException("Unknown level["+level+"]");
            }
        }
        //are we dealing with the global wild card?
        if (logNamePattern.equals(GLOBAL_WILDCARD) == true) {
            //yes, we are
            //register the level
            defaultLevel = levelObj;
            //update all existing loggers
            for (Logger logger : NAME_TO_LOGGER.values()) {
                logger.setLevel(defaultLevel);
            }
        }
        else {
            //no we have a specific level or level pattern
            //escape the logNamePattern and force it to lower case
            logNamePattern = logNamePattern.replaceAll("\\.", "\\\\.");
            logNamePattern = logNamePattern.replaceAll("\\*", ".*").toLowerCase();
            Pattern pattern = Pattern.compile(logNamePattern);
            levels.put(pattern, levelObj);
            //update all existing loggers
            for (Logger logger : NAME_TO_LOGGER.values()) {
                if (pattern.matcher(logger.getName().toLowerCase()).matches()) {
                    logger.setLevel(levelObj);
                }
            }
        }
    }

    @Override
    public Logger[] getLoggers() {
        return NAME_TO_LOGGER.values().toArray(new Logger[NAME_TO_LOGGER.size()]);
    }


    public void init(Configuration configuration, Object... args) throws Exception {
    }


    private static synchronized void init() {
        if (initialized) {
            return;
        }

        LOGGER_REPOSITORY.getRootLogger().removeAllAppenders();
        final Properties props = System.getProperties();
        final Layout layout = getLayout(props);
        initConsoleAppender(props, layout);
        initFileAppender(props, layout);
        initLoggerLogger(props, layout);

        initialized = true;
    }


    private static synchronized void initConsoleAppender(Properties props, Layout layout) {
        if (initialized) {
            return;
        }

        org.apache.log4j.Logger logger = LOGGER_REPOSITORY.getRootLogger();
        consoleAppender = (ConsoleAppender) logger.getAppender(CONSOLE_APPENDER_NAME);
        if (null == consoleAppender) {
            consoleAppender = new ConsoleAppender(layout);
            consoleAppender.setName(CONSOLE_APPENDER_NAME);
            logger.addAppender(consoleAppender); // Log4J wants at least one appender, so this is always added.
        }
        configureConsoleAppender(props, layout);
    }


    private static synchronized void initFileAppender(Properties props, Layout layout) {
        if (initialized) {
            return;
        }

        final boolean append = Boolean.valueOf(
                props.getProperty(SystemProperty.TRACE_FILE_APPEND.getPropertyName(), Boolean.TRUE.toString()));

        final String logDirPath = props.getProperty(SystemProperty.TRACE_FILE_DIR.getPropertyName(), "logs");
        String logFilePath = props.getProperty(SystemProperty.TRACE_FILE_NAME.getPropertyName(), null);
        if (logFilePath == null) {
            logFilePath = logDirPath + File.separatorChar + makeName(props) + ".log";
        } else {
            logFilePath = logDirPath + File.separatorChar + logFilePath;
        }

        try {
            final File logFile = new File(logFilePath).getCanonicalFile();
            logFile.getParentFile().mkdirs();
            logFilePath = logFile.getCanonicalPath();
            fileAppender = new BERollingFileAppender(layout, logFilePath, append);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        configureFileAppender(props, layout);
    }


    private static synchronized void initLoggerLogger(Properties props, Layout layout) {
        if (initialized) {
            return;
        }

        if (Boolean.valueOf(props.getProperty(SystemProperty.TRACE_TRACE.getPropertyName(), "false"))) {

            loggerLoggerAppender.setLayout(layout);
            loggerLoggerAppender.setMaxBackupIndex(10);
            loggerLoggerAppender.setMaximumFileSize(10000);
            try {
                final File logFile = new File("loggers.log").getCanonicalFile();
                logFile.getParentFile().mkdirs();
                loggerLoggerAppender.setFile(logFile.getCanonicalPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            loggerLogger = org.apache.log4j.Logger.getLogger("~LoggerLogger~");
            loggerLogger.setAdditivity(false);
            loggerLogger.setLevel(org.apache.log4j.Level.INFO);

            loggerLogger.addAppender(loggerLoggerAppender);
        }
    }


//    public static void main(String[] args) {
//        final LogManagerImpl lm = new LogManagerImpl(new Properties());
//        final Logger l = lm.getLogger("TEST");
//        l.log(Level.INFO, "********");
//    }


    private static String makeName(Properties props) {
        return props.getProperty(SystemProperty.TRACE_NAME.getPropertyName(),
                props.getProperty(SystemProperty.ENGINE_NAME.getPropertyName(),
                        System.getProperty(SystemProperty.TRACE_NAME.getPropertyName(),
                                System.getProperty(SystemProperty.ENGINE_NAME.getPropertyName(), "cep-engine"))));
    }


    public void start() throws Exception {
        System.out.println("Please Print");
    }


    public void stop() throws Exception {
    }


    protected static class LoggerOutputStream extends OutputStream {


        protected org.apache.log4j.Logger logger;
        protected org.apache.log4j.Level level;

        protected LoggerOutputStream(org.apache.log4j.Level level, org.apache.log4j.Logger logger) {
            this.logger = logger;
            this.level = level;
        }


        public void write(byte b[]) throws IOException {
            final String s = new String(b);
            if (!s.startsWith("log4j:")) {
                this.logger.log(this.level, s);
            }
        }

        public void write(byte b[], int off, int len) throws IOException {
            final String s = new String(b, off, len);
            if (!(s.startsWith("log4j:") || s.equalsIgnoreCase("\r\n") || (s.equalsIgnoreCase("\n")))) {
                this.logger.log(this.level, s);
            }
        }

        public void write(int b) throws IOException {
            logger.log(this.level, String.valueOf(b));
        }
    }
    
    public static boolean isGlobalVar(String str) {
		if (str==null)
			return false;
		
		if (str.startsWith("%%") && str.endsWith("%%")) {
			String[] tokens = str.split("%%");
			if (tokens.length==2)
				return true;
		}
		return false;
	}
    
    public static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		if(stripVal.indexOf("%%")!=-1){
			stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		}
		return	stripVal;
	}
}
