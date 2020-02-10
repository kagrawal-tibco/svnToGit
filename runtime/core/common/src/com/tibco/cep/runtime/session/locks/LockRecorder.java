package com.tibco.cep.runtime.session.locks;

/*
* Author: Ashwin Jayaprakash Date: Dec 22, 2008 Time: 2:14:57 PM
*/
public class LockRecorder {
    public static class LockRecord {
        protected final Object lockerThread;

        protected final long lockTimeMillis;

        protected final Exception lockerThreadTrace;
        
        private int timesLocked = 1;
        
        public void incrLock() {
            timesLocked++;
        }
        public int decrLock() {
            return --timesLocked;
        }

        /**
         * @param lockerThread
         * @param lockTimeMillis
         * @param lockerThreadTrace Can be <code>null</code>.
         */
        public LockRecord(Object lockerThread, long lockTimeMillis,
                          Exception lockerThreadTrace) {
            this.lockerThread = lockerThread;
            this.lockTimeMillis = lockTimeMillis;
            this.lockerThreadTrace = lockerThreadTrace;
        }

        public LockRecord(Object lockerThread, long lockTimeMillis) {
            this(lockerThread, lockTimeMillis, null);
        }

        public Object getLockerThread() {
            return lockerThread;
        }

        public long getLockTimeMillis() {
            return lockTimeMillis;
        }

        /**
         * @return Can be <code>null</code>.
         */
        public Exception getLockerThreadTrace() {
            return lockerThreadTrace;
        }
    }

    //--------------

    public static class Level1LockRecord extends LockRecord {
        protected final LockManager.Level1Lock lock;

        /**
         * @param lockerThread
         * @param lockTimeMillis
         * @param lock
         * @param lockerThreadTrace Can be <code>null</code>.
         */
        public Level1LockRecord(Object lockerThread, long lockTimeMillis,
                                LockManager.Level1Lock lock, Exception lockerThreadTrace) {
            super(lockerThread, lockTimeMillis, lockerThreadTrace);

            this.lock = lock;
        }

        public Level1LockRecord(Object lockerThread, long lockTimeMillis,
                                LockManager.Level1Lock lock) {
            super(lockerThread, lockTimeMillis);

            this.lock = lock;
        }

        public LockManager.Level1Lock getLock() {
            return lock;
        }
    }

    //--------------

    /**
     * Does <b>not</b> capture the thread StackTrace.
     */
    public static class Level2LockRecord extends LockRecord {
        public Level2LockRecord(Object lockerThread, long lockTimeMillis) {
            super(lockerThread, lockTimeMillis);
        }
    }

    //--------------

    public static class DoubleLevelLockRecord {
        protected final Level1LockRecord level1;

        protected Level2LockRecord level2 = null;

        public DoubleLevelLockRecord(Level1LockRecord level1) {
            this.level1 = level1;
        }

        public Level1LockRecord getLevel1() {
            return level1;
        }

        public Level2LockRecord getLevel2() {
            return level2;
        }

        public void setLevel2(Level2LockRecord level2) {
            this.level2 = level2;
        }
    }
}
