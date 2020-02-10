//package com.tibco.cep.runtime.service.logging.impl;
//
//import com.tibco.cep.runtime.service.logging.TraceMessageLayout;
//import org.apache.log4j.*;
//import org.apache.log4j.spi.LoggerFactory;
//
//import java.lang.reflect.Constructor;
//import java.util.Properties;
//
///*
//* User: Nicolas Prade
//* Date: Oct 28, 2009
//* Time: 6:10:43 PM
//*/
//
//public class BELoggerFactory implements LoggerFactory {
//
//    static {
//        //todo
//        BasicConfigurator.configure();
//    }
//
//
//    protected RollingFileAppender fileAppender;
//    protected String logFilePath;
//    private Appender stdout;
//
//
//    public Logger makeNewLoggerInstance(String name) {
//        if (null == name) {
//            throw new IllegalArgumentException();
//        }
//
//        final LoggerImpl logger = new LoggerImpl(name);
////        logger.setAdditivity(false);
////
////        //todo
////        final Properties props = new Properties();
////        final Layout layout = this.getLayout(props);
////        if (!Boolean.valueOf(props.getProperty("be.trace.enable", "true"))) {
////            logger.setLevel(org.apache.log4j.Level.OFF);
////        }
////        try {
////            if (Boolean.valueOf(props.getProperty("be.trace.term.enable", "true"))) {
////                this.addConsoleAppender(logger, layout, props);
////            }
////            if (Boolean.valueOf(props.getProperty("be.trace.log.enable", "true"))) {
////                this.addFileAppender(logger, layout, props);
////            }
////            this.activateRoles(props);
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//
//        return (Logger) logger;
//    }
//
//
//    private void activateRoles(Logger logger, Properties props) {
//        logger.setLevel(org.apache.log4j.Level.INFO);
//        //todo
////        String roles = props.getProperty("roles", "infoRole, errorRole, warnRole, userRole, debugRole");
////        String rol[] = roles.split(",");
////        for (int i=0; i < rol.length; i++) {
////            String role = rol[i].trim();
////
////            if ("errorRole".equalsIgnoreCase(role)) {
////                errorLogger.setLevel(Level.ERROR);
////            }
////
////            if ("infoRole".equalsIgnoreCase(role)) {
////                infoLogger.setLevel(Level.INFO);
////            }
////            else if ("debugRole".equalsIgnoreCase(role)) {
////                debugLogger.setLevel(Level.DEBUG);
////            }
////            else if ("warnRole".equalsIgnoreCase(role)) {
////                warnLogger.setLevel(Level.WARN);
////            }
////            else if ("userRole".equalsIgnoreCase(role)) {
////                userLogger.setLevel(LevelEx.USER);
////            }
////        }
//    }
//
//
//    private void addConsoleAppender(Logger logger, Layout layout, Properties props) {
//        final boolean isTerm = Boolean.valueOf(props.getProperty("be.trace.term.enable", "true"));
//        if (isTerm) {
//            this.stdout = this.getAppender("stdout");
//            if ((this.stdout == null)) {
//                this.stdout = new ConsoleAppender(layout);
//                this.addAppender(this.stdout);
//            }
//
////todo?
////            if (Boolean.valueOf(props.getProperty("be.trace.term.sysout.redirect", "true"))) {
////                this.originalOut = System.out;
////                this.outLogger = new PrintStream(new SysOutLogger(this.baseLogger));
////                System.setOut(outLogger);
////            }
////
////            if (Boolean.valueOf(props.getProperty("be.trace.term.syserr.redirect", "false"))) {
////                this.originalErr = System.err;
////                this.errLogger = new PrintStream(new SysErrLogger(errorLogger));
////                System.setErr(errLogger);
////            }
//        }
//    }
//
//
//    private void addFileAppender(Logger logger, Layout layout, Properties props) throws Exception {
//        //todo ?
////        if (!Boolean.valueOf(props.getProperty("be.trace.log.enable", "true"))) {
////            return;
////        }
////
////        final long maxSize = Long.valueOf(props.getProperty("log.maxsize", "1000000"));
////        final int maxNum = Integer.valueOf(props.getProperty("log.maxnum", "10"));
////        final boolean append = Boolean.valueOf(props.getProperty("log.append", "true"));
////        final String logDir = props.getProperty("be.trace.log.dir", "logs");
////
////        String logFilePath = props.getProperty("be.trace.log.fileName", null);
////        if (logFilePath == null) {
////            logFilePath = logDir + File.separatorChar + this.getName() + ".log";
////        } else {
////            logFilePath = logDir + File.separatorChar + logFilePath;
////        }
////
////        final File logFile = new File(logFilePath).getCanonicalFile();
////        logFile.getParentFile().mkdirs();
////        this.logFilePath = logFile.getCanonicalPath();
////
////        this.fileAppender = new RollingFileAppender(layout, this.logFilePath, append);
////        this.fileAppender.setMaxBackupIndex(maxNum);
////        this.fileAppender.setMaximumFileSize(maxSize);
////
////        this.addAppender(this.fileAppender);
//    }
//
//
//    protected void close() {
//        //todo ?
//        try {
//            if (this.stdout != null) {
//                this.stdout.close();
//            }
//            this.fileAppender.close();
//            this.removeAllAppenders();
////            if (errLogger != null) {
////
////                System.setErr(orgErr);
////                errLogger.close();
////            }
////            if (outLogger != null) {
////                System.setOut(orgOut);
////                outLogger.close();
////            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private Layout getLayout(Properties props) {
//        final String name = props.getProperty("cluster.name", props.getProperty("be.trace.name", "cep-engine"));
//
//        final String layoutClass = props.getProperty("be.trace.layout.class.name");
//        if (layoutClass == null) {
//            return new TraceMessageLayout(name);
//        }
//
//        try {
//            final Class clazz = Class.forName(layoutClass);
//            final String arg = props.getProperty("be.trace.layout.class.arg"); //no arg means default constructor
//            if (arg == null) {
//                return (Layout) clazz.newInstance();
//            }
//            final Constructor constructor = clazz.getConstructor(new Class[]{String.class});
//            return (Layout) constructor.newInstance(arg);
//        }
//        catch (Exception e) {
//            System.out.println("Layout class [" + layoutClass + "] specified not found - using default");
//            return new TraceMessageLayout(name);
//        }
//    }
//}
