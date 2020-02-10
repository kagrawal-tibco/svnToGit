/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread;

import java.io.File;
import java.io.IOException;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.management.remote.JMXConnector;

import com.tibco.be.monitor.thread.report.ReportPublisherThread;
import com.tibco.be.monitor.thread.util.JMXUtil;

/**
 *
 * @author ksubrama
 */
public class Connector {

    private static final int DEFAULT_MAX_NUM_LOG_FILES =10;
    private static final int DEFAULT_LOG_SIZE = 10*1024*1024;   //10MB default log size
    private static final String[] LOGGER_NAMES = new String[]{
        "com.tibco.be.monitor.thread.analyzer",
        "com.tibco.be.monitor.thread.analyzer.impl",
        "com.tibco.be.monitor.thread.graph",
        "com.tibco.be.monitor.thread.graph.impl",
        "com.tibco.be.monitor.thread.graph.comparator",
        "com.tibco.be.monitor.thread.graph.visitor",
        "com.tibco.be.monitor.thread.graph.visitor.impl",};
    private static final Logger logger = Logger.getLogger(
            Connector.class.getName());
    private static ScheduledExecutorService threadPool;
    private static String folder;
    private static final String PROP_FOLDER = "be.monitor.log.dir";
    private static final String PROP_USER_DIR = "user.dir";
    private static final Map<String, FileHandler> logHandlers = new HashMap<String, FileHandler>();
    private static final Map<String, Logger> loggers = new HashMap<String, Logger>();
    private static Map<String, ScheduledFuture<?>> addressToScheduledFuture =
            new HashMap<String, ScheduledFuture<?>>();
    private static int logSize;
    private static int maxNumLogs;

    static {
        folder = System.getProperty(PROP_FOLDER);
        if (folder == null) {
            folder = System.getProperty(PROP_USER_DIR);
        }
        for (String LOGGER_NAME : LOGGER_NAMES) {
            // set the log level to warning.
            Logger.getLogger(LOGGER_NAME).setLevel(Level.WARNING);
        }
        //Create threadPool
        threadPool = Executors.newScheduledThreadPool(2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        logSize = DEFAULT_LOG_SIZE;
        maxNumLogs = DEFAULT_MAX_NUM_LOG_FILES;
        startThreadAnalyzerCmd(args);
    }

    public static void startThreadAnalyzer(String IntervalInSec, String address) {
        int intervalInSec = getIntervalInSec(new String[]{IntervalInSec});
        initShutdownHook();
        connectAndScheduleReportPrinter(intervalInSec, address);
    }

    // Only used for BE-MM. Not used by the command line
    public static final void stopThreadAnalyzer(String host, int port, String username, String pwd) throws IOException, SecurityException {
        //Try to get the JMX connector to see if the user specified the right credentials. If the credentials are wrong
        //an exception will be thrown and the Thread Analyzer will not stop. The exception is handled but this function's caller
        JMXUtil.getJMXConnector(host, port, username, pwd);

        final String key = host + ':' + port;
        ScheduledFuture<?> future = addressToScheduledFuture.get(key);
        if (future == null)
            return;

        future.cancel(true);
        if (future.isDone()) {
            addressToScheduledFuture.remove(key);
            closeLogHandler(host, port);
        }
    }

    private static void startThreadAnalyzerCmd(String[] args) {
        if (args.length != 2) {
            printErrorMsg();
        }

        int intervalInSec = getIntervalInSec(args);
        // Read comma separated entries.
        String[] addresses = args[1].split(",");
        initShutdownHook();

        for (String address : addresses) {
            connectAndScheduleReportPrinter(intervalInSec, address);
        }
    }

    public static void setDir(String folder) {
        Connector.folder = folder;
        //todo: check if the folder exists
    }

    public static void setLogSize(String logFileSize) {
        try {
            logSize = Integer.parseInt(logFileSize);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Specified illegal log file size: " + logFileSize +
                    " .Specify the intended file size in bytes. Used default log size of: 10MB");
            logSize = DEFAULT_LOG_SIZE;
        }
    }

    public static void setMaxNumLogs(String maxNumberLogs) {
        try {
            maxNumLogs = Integer.parseInt(maxNumberLogs);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Specified illegal max number of log files: " + maxNumLogs +
                    " .Provide a positive integer number. Used default log size of: " + DEFAULT_MAX_NUM_LOG_FILES);
            logSize = DEFAULT_LOG_SIZE;
        }

    }


    private static int getIntervalInSec(String args[]) {
        // Default is 5 minutes.
        int intervalInSec = 300;
        try {
            intervalInSec = Integer.parseInt(args[0]);
            if (intervalInSec <= 0 ) {
                int temp = intervalInSec;
                intervalInSec = 300;
                throw new IllegalArgumentException("Sampling interval must be a positive integer number. " +
                        temp + " is not a valid time interval. Using default value: " + intervalInSec + "s");
            }
        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, "Provided interval: " + args[0] +
                    " is not a valid integer. Using default value: " + intervalInSec + "s");
        } catch (IllegalArgumentException e) {
            String errMsg  = "Provided interval: " + args[0] + " is not a valid integer. Using default value: " + intervalInSec + "s";
            logger.log(Level.WARNING, errMsg);
        }
        return intervalInSec;
    }

    private static final void connectAndScheduleReportPrinter(int intervalInSec, String address) {
        String[] parts = address.trim().split(":");
        if (parts.length == 1 || parts.length == 2) {
            try {
                String host = getHost(address.trim());
                int port = getPort(address.trim());
                scheduleReportPrinter(host, port, intervalInSec,
                        getThreadMXBean(host, port));
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Unable to connect to the JMX server", ex);
            }
        } else if (parts.length == 4) {
            // Use SSL connections with username/password authentication.
            try {
                String host = getHost(address.trim());
                int port = getPort(address.trim());
                String user = getUsername(address.trim());
                String pass = getPassword(address.trim());
                // Split the host and port that are delimited by :
                scheduleReportPrinter(host, port, intervalInSec,
                        getThreadMXBean(host, port, user, pass));
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Unable to connect to the JMX server", ex);
            }
        } else {
            printErrorMsg();
        }
    } //connectAndScheduleReportPrinter

    private static final ThreadMXBean getThreadMXBean(String host, int port) throws IOException {
        JMXConnector connector = JMXUtil.getJMXConnector(host, port);
        return JMXUtil.getThreadMXBean(connector);
    }

    private static final ThreadMXBean getThreadMXBean(String host, int port,
            String user, String pass) throws IOException {
        JMXConnector connector = JMXUtil.getJMXConnector(host, port, user, pass);
        return JMXUtil.getThreadMXBean(connector);
    }

    private static final void scheduleReportPrinter(String host, int port, int interval,
            ThreadMXBean tmxBean) throws IOException {
        ScheduledFuture<?> scheduledFuture = threadPool.scheduleAtFixedRate(
                new ReportPublisherThread(getLogger(host, port),
                host, port, tmxBean), 0, interval, TimeUnit.SECONDS);
        addressToScheduledFuture.put(host + ':' + port, scheduledFuture);
    }

    private static final void printErrorMsg() {
        logger.severe("Usage: Connector <host_name_1>:<port>,"
                + "<host_name_2>:<port>");
        logger.severe("host_name - This is an optional parameter. "
                + "If host_name is not specified, "
                + "host is assumed as localhost.");
        logger.severe("port - port for connecting to the host.");
        logger.severe("Multiple host:port entries "
                + "should be seperated by a comma.");
        logger.severe("For SSL Enabled connections, Use the following format:");
        logger.severe("Connector <host_name_1>:<port>:<user_name>:<password>,"
                + "<host_name_2>:<port>:<user_name>:<password>");
        logger.severe("username - SSL Connection user name.");
        logger.severe("password - SSL Connection password.");
        // terminate program.
        Runtime.getRuntime().exit(0);
    }

    private static final int getPortNumber(String port) {
        try {
            // Verify if the port is a valid integer.
            return Integer.parseInt(port);
        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Port number is not a valid number.", ex);
        }
        return -1;
    }

    private static final String getUsername(String address) {
        String[] parts = address.split(":");
        return parts[2];
    }

    private static final String getPassword(String address) {
        String[] parts = address.split(":");
        return parts[3];
    }

    private static final String getHost(String address) {
        String[] parts = address.split(":");
        if (parts.length == 1) {
            return "localhost";
        }
        return parts[0];
    }

    private static final int getPort(String address) {
        String[] parts = address.split(":");
        if (parts.length == 1) {
            return getPortNumber(parts[0]);
        }
        return getPortNumber(parts[1]);
    }

    private static final void initShutdownHook() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    logger.log(Level.INFO, "Shutting down JVM");
                    logger.log(Level.INFO, "Closing the report generation threads.");
                    if (threadPool != null) {
                        threadPool.shutdown();
                        try {
                            int count = 0;
                            while (count++ < 10
                                    && threadPool.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                                threadPool.shutdown();
                            }
                            threadPool.shutdownNow();
                            if (threadPool.isTerminated()) {
                                logger.log(Level.INFO, "Thread pool successfully closed.");
                            } else {
                                logger.log(Level.WARNING, "Unable to close the Thread pool successfully.");
                            }
                            // close log handlers
                            closeLogHandlers();
                        } catch (InterruptedException ex) {
                            logger.log(Level.SEVERE, "Shutdown hook interrupted.", ex);
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while registering shutdown hook.", e);
        }
    }

    public static final String getLoggerName(String host, int port) {
        return folder + File.separator + host + "_" + port + "_%u.%g.log";
    }

    private static final void closeLogHandlers() {
        // Close all file handlers.
        for (String loggerName : logHandlers.keySet()) {
            // Remove handler from the logger
            Logger logg = loggers.get(loggerName);
            // Close the handler.
            FileHandler handler = logHandlers.get(loggerName);
            removeHandlerAndClose(logg, handler);
        }        
    }

    private static final void closeLogHandler(String host, int port) {
        String loggerName = getLoggerName(host, port);
        Logger logg = loggers.get(loggerName);
        FileHandler handler = logHandlers.get(loggerName);
        removeHandlerAndClose(logg, handler);
        loggers.remove(loggerName);
        logHandlers.remove(loggerName);
    }

    private static final Logger getLogger(String host, int port) throws IOException {
        String loggerName = getLoggerName(host, port);
        Logger log = Logger.getLogger(loggerName);
        loggers.put(loggerName, log);
        // Disable any parent handlers
        log.setUseParentHandlers(false);
        // Add the file handler
        FileHandler fileHandler = new FileHandler(loggerName,logSize, maxNumLogs,true);
        fileHandler.setFormatter(new SimpleFormatter());
        log.addHandler(fileHandler);
        logHandlers.put(loggerName, fileHandler);
        return log;
    }

    private static void removeHandlerAndClose(Logger logg, FileHandler handler) {
        if (logg != null && handler != null) {
            handler.flush();
            handler.close();
            logg.removeHandler(handler);
        }
    }
}
