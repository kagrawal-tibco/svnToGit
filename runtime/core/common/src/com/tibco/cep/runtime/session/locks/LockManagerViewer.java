package com.tibco.cep.runtime.session.locks;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

/*
* Author: Ashwin Jayaprakash Date: Oct 24, 2008 Time: 4:44:16 PM
*/
public class LockManagerViewer {
    /**
     * @param allRecords
     * @return
     */
    public StringBuilder collectEverything(
            ConcurrentMap<Object, LockRecorder.DoubleLevelLockRecord> allRecords, LockManager lockMgr) {
        StringBuilder sb = new StringBuilder();

        sb.append("[").append(LockManager.class.getSimpleName()).append("]");
        sb.append("\n================================= Locks =================================");

        for (Map.Entry<Object, LockRecorder.DoubleLevelLockRecord> entry :
                allRecords.entrySet()) {
            Object key = entry.getKey();
            LockRecorder.DoubleLevelLockRecord dll = entry.getValue();

            if (key == null || dll == null) {
                continue;
            }

            collect(sb, key, dll, lockMgr);
            sb.append('\n');
        }

        sb.append("\n~---------~---------~---------~---------~---------~---------~---------~--");

        return sb;
    }

    private void collect(StringBuilder sb, Object key,
                         LockRecorder.DoubleLevelLockRecord dll, LockManager lockMgr) {
        sb.append("\nKey [").append(key).append("] details:");

        //------------

        LockRecorder.Level1LockRecord level1LockRecord = dll.getLevel1();
        Object thread = level1LockRecord.getLockerThread();
        long timestamp = level1LockRecord.getLockTimeMillis();
        Exception trace = level1LockRecord.getLockerThreadTrace();
        StackTraceElement[] traceElements = null;
        if (trace != null) {
            traceElements = trace.getStackTrace();
        }
        LockManager.Level1Lock level1Lock = level1LockRecord.getLock();
        LockManager.LockLevel requestedLockLevel = level1Lock.getRequestedLockLevel();

        sb.append("\n  Requested level : ").append(requestedLockLevel.name());

        collect(sb, LockManager.LockLevel.LEVEL1, thread, traceElements, new Timestamp(timestamp), lockMgr);

        sb.append("\n     Other waiters   : ");
        Collection<Thread> waitingThreads = level1Lock.getQueuedThreads();
        if (waitingThreads == null || waitingThreads.isEmpty()) {
            sb.append("None");
        }
        else {
            for (Thread waitingThread : waitingThreads) {
                String name = waitingThread.getName();
                StackTraceElement[] stackTrace = waitingThread.getStackTrace();

                sb.append("\n                        [").append(name).append("]");

                if (stackTrace != null) {
                    for (StackTraceElement aStackTrace : stackTrace) {
                        sb.append("\n                        ").append(aStackTrace);
                    }
                    sb.append('\n');
                }
            }
        }

        //------------

        //Requested level is higher.
        if (requestedLockLevel != LockManager.LockLevel.LEVEL1) {
            LockRecorder.Level2LockRecord level2LockRecord = dll.getLevel2();

            //Level 1 succeeded.
            if (level2LockRecord != null) {
                thread = level2LockRecord.getLockerThread();
                timestamp = level2LockRecord.getLockTimeMillis();

                collect(sb, LockManager.LockLevel.LEVEL2, thread, null, new Timestamp(timestamp), lockMgr);
            }
            else {
                //Get the latest point where the thread is.
                thread = level1LockRecord.getLockerThread();
                traceElements = lockMgr.getLockContextStackTrace(thread);

                collect(sb, LockManager.LockLevel.LEVEL2, thread, traceElements, null, lockMgr);
            }
        }
    }

    /**
     * @param sb
     * @param lockLevel
     * @param thread
     * @param traceElements Can be <code>null</code>.
     * @param timestamp     Pass <code>null</code> to let the method analyze the thread's state and
     *                      report accordingly.
     */
    private void collect(StringBuilder sb, LockManager.LockLevel lockLevel, Object thread,
                         StackTraceElement[] traceElements, Timestamp timestamp, LockManager lockMgr) {
        String threadName = lockMgr.getLockContextName(thread);

        sb.append("\n  ").append(lockLevel.name());
        sb.append("\n     Thread          : ").append(threadName);

        if (timestamp != null) {
            sb.append("\n     Locked on       : ").append(timestamp);
        }

        if (traceElements != null && traceElements.length > 0) {
            if (timestamp != null) {
                sb.append("\n     Locked at       : ").append(traceElements[0]);
            }
            else {
                Thread.State state = lockMgr.getLockContextThreadState(thread);
                String stateName = null;
                if(state != null) stateName = state.name().toLowerCase();

                sb.append("\n     Lock not yet acquired. Status ").append(stateName);
                sb.append("\n     Currently at    : ").append(traceElements[0]);
            }

            for (int i = 1; i < traceElements.length; i++) {
                sb.append("\n                       ").append(traceElements[i]);
            }
        }
    }
    
    /**
     * Returns Held Locks count of the specified LockLevel.
     * @param allRecords
     * @param lockLevel
     * @return
     */
    public static long getLocksCountByLevel(
    		ConcurrentMap<Object, LockRecorder.DoubleLevelLockRecord> allRecords,
    		LockManager.LockLevel lockLevel) {
    	long count = 0;
    	if (allRecords != null) {
    		for (Entry<Object, LockRecorder.DoubleLevelLockRecord> lockRecordEntry : allRecords.entrySet()) {
    			LockRecorder.Level1LockRecord level1LockRecord = lockRecordEntry.getValue().getLevel1();
    			LockManager.Level1Lock level1Lock = level1LockRecord.getLock();
    			LockManager.LockLevel requestedLockLevel = level1Lock.getRequestedLockLevel();
    			if (lockLevel == requestedLockLevel) {
    				count++;
    			}
    		}
    	}
        return count;
    }
}
