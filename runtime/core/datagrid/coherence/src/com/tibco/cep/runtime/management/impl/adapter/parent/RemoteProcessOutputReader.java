package com.tibco.cep.runtime.management.impl.adapter.parent;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: Apr 15, 2009 Time: 3:19:21 PM
*/
public class RemoteProcessOutputReader {
    protected Process process;

    protected Logger logger;

    protected String logTag;

    protected ReaderThread readerThread;

    public void init(Process process, String logTag, Logger logger) {
        this.process = process;
        this.logTag = logTag;
        this.logger = logger;
    }

    public void start() {
        readerThread = new ReaderThread();
        readerThread.start();
    }

    public void stop() {
        if (readerThread != null) {
            readerThread.stopReading();
        }
    }

    //------------

    protected class ReaderThread extends Thread {
        protected final AtomicBoolean stopFlag;

        public ReaderThread() {
            super(RemoteProcessOutputReader.class.getName() + ".Thread");
            setDaemon(true);

            this.stopFlag = new AtomicBoolean(false);
        }

        public void stopReading() {
            stopFlag.set(true);

            interrupt();
        }

        @Override
        public void run() {
            String newline = System.getProperty("line.separator", "\n");
            String logPrefix = newline + "Child process [" + RemoteProcessOutputReader.this
                    .logTag + "]" + newline + "{{" + newline;

            StringBuilder builder = new StringBuilder(logPrefix);

            try {
                Process childProcess = RemoteProcessOutputReader.this.process;
                InputStream iStream = childProcess.getInputStream();
                InputStreamReader in = new InputStreamReader(iStream);

                OuterLoop:
                while (stopFlag.get() == false) {
                    try {
                        int notReadyCount = 0;
                        while (in.ready() == false) {
                            notReadyCount++;

                            Thread.sleep(1000);

                            try {
                                int exitStatus = childProcess.exitValue();

                                break OuterLoop;
                            }
                            catch (IllegalThreadStateException e) {
                                //Still alive.
                            }

                            if (notReadyCount == 60 * 5 /*Wait for 5 mins.*/) {
                                break OuterLoop;
                            }
                        }

                        //------------

                        char c = (char) in.read();
                        if (c == -1) {
                            break;
                        }

                        builder.append(c);

                        if (c == '\n' && builder.length() > 1 /*Excluding the newline.*/) {
                            purge(logPrefix, builder);
                        }
                    }
                    catch (Exception e) {
                        logError(e);
                    }
                }

                //------------

                purge(logPrefix, builder);

                in.close();
            }
            catch (Exception e) {
                logError(e);
            }
        }

        private void logError(Exception e) {
            RemoteProcessOutputReader.this.logger.log(Level.ERROR, e,
                    "Error occurred while reading the child process's [%s] logs.",
                    RemoteProcessOutputReader.this.logTag);
        }

        private void purge(String logPrefix, StringBuilder builder) {
            if (builder.length() > 0) {
                builder.append("}}");

                RemoteProcessOutputReader.this.logger.log(Level.WARN, builder.toString());

                builder.delete(0, builder.length());
                builder.append(logPrefix);
            }
        }
    }
}
