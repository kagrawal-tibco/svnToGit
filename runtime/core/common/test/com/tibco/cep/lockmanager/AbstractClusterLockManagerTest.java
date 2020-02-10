package com.tibco.cep.lockmanager;

import com.tangosol.net.NamedCache;
import com.tibco.cep.runtime.session.locks.LockManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
* Author: Ashwin Jayaprakash Date: Jul 10, 2008 Time: 6:28:59 PM
*/
public abstract class AbstractClusterLockManagerTest {
    protected boolean errorsOccurred;

    public boolean isErrorsOccurred() {
        return errorsOccurred;
    }

    //--------------

    protected abstract NamedCache createLockCache();

    protected abstract void discardLockCache(NamedCache namedCache);

    protected abstract LockManager createLockManager(NamedCache lockCache);

    protected abstract void discardLockManager(LockManager lockManager);

    //--------------

    /**
     * @param args Optional! First element is the log file path.
     * @throws FileNotFoundException
     */
    public void runTests(String[] args) throws FileNotFoundException {
        PrintStream ps = null;

        if (args.length > 0) {
            String log = args[0];
            ps = new PrintStream(new FileOutputStream(log, false));

            System.err.println(getClass() + " running and logging to: " + log);

            System.setErr(ps);
            System.setOut(ps);
        }
        else {
            System.err.println(getClass() + " running.");
        }

        try {
            performTest(1, 2, 10 * 1000);

            //----------

            System.gc();
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }

            performTest(1, 1, 10 * 1000);

            //----------

            System.gc();
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }

            performTest(2, 2, 10 * 1000);

            //----------

            System.gc();
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }

            performTest(10, 5, 25 * 1000);

            //----------

            System.gc();
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }

            performTest(5, 30, 30 * 1000);

            //----------

            System.gc();
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }

            performTest(100, 2, 30 * 1000);

            //----------

            System.gc();
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
            }

            performTest(300, 2, 35 * 1000);
        }
        finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    protected void performTest(int numTickets, int numThreadsPerTicket, long testRunTimeMillis) {
        NamedCache lockCache = createLockCache();
        System.out.println(lockCache + "service - " + lockCache.getCacheService().toString());
        lockCache.clear();

        LockManager lockManager = createLockManager(lockCache);

        try {
            performActualTest(numTickets, numThreadsPerTicket, testRunTimeMillis, lockManager);
        }
        finally {
            discardLockManager(lockManager);

            discardLockCache(lockCache);
        }
    }

    protected void performActualTest(int numTickets, int numThreadsPerTicket,
                                     long testRunTimeMillis, LockManager lockManager) {
        HashMap<Integer, TestArtifact> artifacts = new HashMap<Integer, TestArtifact>();

        for (int i = 0; i < numTickets; i++) {
            //Spread the ticket's hashcode so that they spread out in the CHM-Segments.
            String ticket = "ticket:" + System.nanoTime() + ":" + i + Math.random();
            SyncBlock syncBlock = new SyncBlock();

            HammerThread[] hammerThreads =
                    createHammerThreads(numThreadsPerTicket, lockManager, ticket, syncBlock);

            artifacts.put(i, new TestArtifact(ticket, syncBlock, hammerThreads));
        }

        //---------

        System.err
                .println("Starting test:: Tickets: " + numTickets + ", NumThreadsPerTicket: " +
                        numThreadsPerTicket);

        for (TestArtifact artifact : artifacts.values()) {
            HammerThread[] hammerThreads = artifact.getHammerThreads();

            for (HammerThread hammerThread : hammerThreads) {
                hammerThread.start();
            }
        }

        //---------

        System.err.println("Waiting for a while...");

        try {
            Thread.sleep(testRunTimeMillis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        //---------

        System.err.println("Ending test...");

        for (TestArtifact artifact : artifacts.values()) {
            HammerThread[] hammerThreads = artifact.getHammerThreads();

            for (HammerThread hammerThread : hammerThreads) {
                hammerThread.stopHammering();
            }
        }

        for (TestArtifact artifact : artifacts.values()) {
            HammerThread[] hammerThreads = artifact.getHammerThreads();

            for (HammerThread hammerThread : hammerThreads) {
                while (hammerThread.isAlive()) {
                    try {
                        hammerThread.join();
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        }

        //---------

        System.err.println("Evaluating...");

        evaluate(artifacts, testRunTimeMillis);

        if (errorsOccurred) {
            System.err.println("** ERRORS OCCURRED **");
        }

        System.err.println("-------------");
        System.err.println();
    }

    protected void evaluate(HashMap<Integer, TestArtifact> artifacts, long testTimeMillis) {
        int grandTotalHammerings = 0;

        for (TestArtifact artifact : artifacts.values()) {
            HammerThread[] hammerThreads = artifact.getHammerThreads();

            int totalHammerings = 0;

            for (HammerThread hammerThread : hammerThreads) {
                while (hammerThread.isAlive()) {
                    try {
                        hammerThread.join();
                    }
                    catch (InterruptedException e) {
                    }

                    if (hammerThread.getSuccessfulAttempts() != hammerThread.getTotalAttempts()) {
                        System.err.println("** Thread did not succeed fully: " +
                                hammerThread.getName() + ", Total attempts: " + totalHammerings +
                                ", Successes: " + hammerThread.getSuccessfulAttempts());

                        errorsOccurred = errorsOccurred || true;
                    }
                }

                errorsOccurred = errorsOccurred || hammerThread.isErrorsOccurred();

                totalHammerings = totalHammerings + hammerThread.getSuccessfulAttempts();
            }

            //-----------

            SyncBlock syncBlock = artifact.getSyncBlock();
            Collection<String> hammerAttempts = syncBlock.getWorkerIds();

            double tps = (1000 * totalHammerings) / (double) testTimeMillis;

            System.err.println("Ticket: " + artifact.getTicket() + ", Total reported hammerings: " +
                    totalHammerings + ", Total recorded: " + hammerAttempts.size() + ", TPS: " +
                    tps);

            if (hammerAttempts.size() != totalHammerings) {
                System.err.println(" ** Numbers do not match!!");

                errorsOccurred = errorsOccurred || true;
            }

            compareDetailed(artifact);

            grandTotalHammerings = grandTotalHammerings + totalHammerings;

            artifact.discard();
        }

        double grandTPS = (1000 * grandTotalHammerings) / (double) testTimeMillis;
        System.err.println("Total TPS: " + grandTPS);
    }

    private void compareDetailed(TestArtifact artifact) {
        HashMap<String, Integer> hammeringsReported = new HashMap<String, Integer>();
        HammerThread[] hammerThreads = artifact.getHammerThreads();
        for (HammerThread hammerThread : hammerThreads) {
            hammeringsReported.put(hammerThread.getName(), hammerThread.getSuccessfulAttempts());
        }

        HashMap<String, AtomicInteger> hammeringsRecorded = new HashMap<String, AtomicInteger>();
        Collection<String> workerIds = artifact.getSyncBlock().getWorkerIds();
        for (String workerId : workerIds) {
            AtomicInteger counter = hammeringsRecorded.get(workerId);
            if (counter == null) {
                counter = new AtomicInteger();
                hammeringsRecorded.put(workerId, counter);
            }

            counter.incrementAndGet();
        }

        if (hammeringsReported.size() != hammeringsRecorded.size()) {
            System.err.println(
                    " ** Actual total-trace does not match reported total-values. Reported total: " +
                            hammeringsReported.size() + ", Recorded total: " +
                            hammeringsRecorded.size() + "!!");

            errorsOccurred = errorsOccurred || true;
        }

        for (String key : hammeringsRecorded.keySet()) {
            AtomicInteger recordedCount = hammeringsRecorded.get(key);
            Integer reportedCount = hammeringsReported.get(key);

            if (reportedCount == null) {
                System.err.println(" ** No recordings for: " + key + "!!");

                errorsOccurred = errorsOccurred || true;
            }
            else if (reportedCount != recordedCount.get()) {
                System.err.println(
                        " ** Actual recorded counts does not match reported values. Reported: " +
                                reportedCount + ", Recorded: " + recordedCount + "!!");

                errorsOccurred = errorsOccurred || true;
            }
            else {
                System.err.println(" - Thread: " + key + " reported: " +
                        recordedCount + ", recorded: " + recordedCount.get());
            }
        }
    }

    protected HammerThread[] createHammerThreads(int threadCount,
                                                 LockManager lockManager,
                                                 String ticket, SyncBlock syncBlock) {
        HammerThread[] hammerThreads = new HammerThread[threadCount];

        for (int i = 0; i < hammerThreads.length; i++) {
            hammerThreads[i] = new HammerThread(lockManager, ticket, syncBlock);
        }

        return hammerThreads;
    }

    //---------------

    protected static class HammerThread extends Thread {
        protected AtomicBoolean stopFlag;

        protected LockManager lockManager;

        protected String ticket;

        protected SyncBlock syncBlock;

        protected int totalAttempts;

        protected int successfulAttempts;

        protected boolean errorsOccurred;

        public HammerThread(LockManager lockManager, String ticket,
                            SyncBlock syncBlock) {
            this.stopFlag = new AtomicBoolean(false);
            this.lockManager = lockManager;
            this.ticket = ticket;
            this.syncBlock = syncBlock;
        }

        public boolean isErrorsOccurred() {
            return errorsOccurred;
        }

        public String getTicket() {
            return ticket;
        }

        public int getTotalAttempts() {
            return totalAttempts;
        }

        public int getSuccessfulAttempts() {
            return successfulAttempts;
        }

        public void stopHammering() {
            stopFlag.set(true);
        }

        @Override
        public void run() {
            final String id = getName();
            final long waitTimeMillis = 2000;

            LinkedList<String> log = new LinkedList<String>();

            while (stopFlag.get() == false) {
                long start = System.nanoTime();

                if (lockManager.lock(ticket, waitTimeMillis, LockManager.LockLevel.LEVEL2)) {
                    successfulAttempts++;

                    try {
                        syncBlock.doWork(id);
                    }
                    finally {
                        lockManager.unlock(ticket);
                    }
                }
                else {
                    long actualWaitTime = (System.nanoTime() - start) / 1000000;

                    log.add("Not locked:: Ticket: " + ticket + ", Thread: " +
                            Thread.currentThread() + " after millis: " +
                            actualWaitTime);

                    if (actualWaitTime < waitTimeMillis) {
                        log.add("** " + Thread.currentThread() +
                                " could not lock and exited before timeout!!");

                        errorsOccurred = errorsOccurred || true;
                    }
                }

                totalAttempts++;
            }

            if (log.size() > 0) {
                for (Object o : log) {
                    System.err.println(o);
                }
            }
        }
    }

    protected static class SyncBlock {
        protected LinkedBlockingQueue<String> workerIds;

        public SyncBlock() {
            this.workerIds = new LinkedBlockingQueue<String>();
        }

        public Collection<String> getWorkerIds() {
            return workerIds;
        }

        public void doWork(String workId) {
            workerIds.add(workId);
        }

        public void discard() {
            if (workerIds != null) {
                workerIds.clear();
                workerIds = null;
            }
        }
    }

    protected static class TestArtifact {
        protected String ticket;

        protected SyncBlock syncBlock;

        protected HammerThread[] hammerThreads;

        public TestArtifact(String ticket, SyncBlock syncBlock,
                            HammerThread[] hammerThreads) {
            this.ticket = ticket;
            this.syncBlock = syncBlock;
            this.hammerThreads = hammerThreads;
        }

        public String getTicket() {
            return ticket;
        }

        public SyncBlock getSyncBlock() {
            return syncBlock;
        }

        public HammerThread[] getHammerThreads() {
            return hammerThreads;
        }

        public void discard() {
            ticket = null;
            hammerThreads = null;
            syncBlock.discard();
        }
    }
}
