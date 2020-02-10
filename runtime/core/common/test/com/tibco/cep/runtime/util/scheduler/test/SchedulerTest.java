package com.tibco.cep.runtime.util.scheduler.test;

import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.Scheduler;
import com.tibco.cep.runtime.util.scheduler.impl.ParametersImpl;
import com.tibco.cep.runtime.util.scheduler.impl.SchedulerImpl;
import com.tibco.cep.runtime.util.scheduler.test.util.CustomLogFormatter;
import com.tibco.cep.runtime.util.scheduler.test.util.JobHelper;
import com.tibco.cep.runtime.util.scheduler.test.util.MockJobImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class SchedulerTest {
    private static final Logger logger = Logger.getLogger(
            SchedulerTest.class.getName());
    private static final List<Id> ids = new LinkedList<Id>();
    private static final List<Job> jobs = new LinkedList<Job>();
    private static final int jobCount = 2;
    private static final long jobWaitTime = 100;
    private static final int timeJobIndex = 26;
    private static final int maxEvents = 50;
    private static final long timeInterval = 2000;
    private static final Scheduler scheduler = new SchedulerImpl();
    private static final ParametersImpl params = new ParametersImpl();
    // Log directory
    private static final File logDir = new File("C:\\temp\\jobQTest");
    // Log files
    private static final String qReport = "qReport.rtf";
    private static final String execReport = "execReport.rtf";
    private static final String jobReport = "jobReport.rtf";
    private static final String eventsReport = "eventsReport.rtf";
    // Handlers
    private static final Map<Logger, Handler> handlers = new HashMap<Logger, Handler>();
    private static final List<FileOutputStream> logFileStreams =
            new LinkedList<FileOutputStream>();
    private static final Level level = Level.INFO;
    private static final Formatter formatter = new CustomLogFormatter();

    public SchedulerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        initParams();
        initLoggers();
        for(int i = 0; i < jobCount; i++) {
            Job job = new MockJobImpl(scheduler, -1);
            ids.add(job.getId());
            jobs.add(job);
        }
    }

    private static void initParams() {
        params.setMaxThreads(10);
        params.setMinThreads(2);
        params.setName("Job");
    }

    private static void initLoggers() throws IOException {
        if(!logDir.exists()) {
            logDir.mkdir();
        }
        File qrFile = openOrCreateFile(qReport);
        File execrFile = openOrCreateFile(execReport);
        File jobrFile = openOrCreateFile(jobReport);
        File eventrFile = openOrCreateFile(eventsReport);
        setLogFile("com.tibco.cep.runtime.util.scheduler.internal.impl.CustomThreadpool",
                execrFile);
        setLogFile("com.tibco.cep.runtime.util.scheduler.internal.impl.JobQueueImpl",
                qrFile);
        setLogFile("com.tibco.cep.runtime.util.scheduler.impl.AbstractJobImpl", jobrFile);
        setLogFile("com.tibco.cep.runtime.util.scheduler.test.util.JobHelper", eventrFile);
    }

    private static File openOrCreateFile(String fileName) throws IOException {
        File file = new File(logDir, fileName);
        if(file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    private static void setLogFile(String loggerName, File logFile) throws FileNotFoundException {
        Logger log = Logger.getLogger(loggerName);
        log.setLevel(level);
        log.setFilter(null);
        FileOutputStream fileStream = new FileOutputStream(logFile, true);
        logFileStreams.add(fileStream);
        Handler handler = new StreamHandler(fileStream, formatter);
        log.addHandler(handler);
        handlers.put(log, handler);
        log.setUseParentHandlers(false);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        closeHandlers();
        closeLogFiles();
        for(int i = 0; i < jobCount; i++) {
            Id id = ids.get(i);
            scheduler.suspendFutureExecutions(id);
            scheduler.unregisterJob(id);
        }
        ids.clear();
        jobs.clear();
        scheduler.stop();
    }

    private static void closeHandlers() {
        // close handlers
        for(Logger log : handlers.keySet()) {
            Handler handler = handlers.get(log);
            log.removeHandler(handler);
            handler.close();
        }
        handlers.clear();
    }

    private static void closeLogFiles() {
        for(FileOutputStream fileStream : logFileStreams) {
            try {
                fileStream.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Unable to close file stream.", ex);
            }
        }
    }

    @Test
    public void testRegisterJob() {        
        scheduler.start(params);
        // Send events for 5 seconds.
        JobHelper.startEventSender(jobs, timeInterval, maxEvents);
        synchronized(this) {
            try {
                this.wait(5000);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        JobHelper.closeEventSender();
        synchronized(this) {
            try {
                this.wait(3000);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        scheduler.stop();
    }
}