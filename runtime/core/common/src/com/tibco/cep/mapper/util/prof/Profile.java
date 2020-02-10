package com.tibco.cep.mapper.util.prof;

import java.util.ArrayList;

public class Profile {
    private ThreadLocal mThreadLocal;
    private final String mName;
    private final String mCatName;
    private ArrayList mPieces;
    private boolean mLocked;
    private boolean mNested;
    private boolean mIsComparison;

    private static class Record {
        public boolean started; // are we currently inside of this.
        public long startTime;

        public long invokeCount;
        public long min;
        public long max;
        public long totalTime;
        public long timeSquare;
    }

    public Profile(String name) {
        mName = name;
        mCatName = null;
    }

    /**
     * Like above, but allows a 'category' (for 2-d comparison breakdowns)
     * @param name
     * @param catName
     */
    public Profile(String name, String catName) {
        mName = name;
        mCatName = catName;
    }

    /**
     * Makes a profile that wraps the nested one.  When printed out, the nested one's name, etc. won't appear because
     * it's assumed that the nested profile represents all of this one.<BR>
     * In effect, this just 'renames' a profile.
     */
    public Profile(String name, Profile nested) {
        mName = name;
        mCatName = null;
        addProfile(nested);
        mNested = true;
    }

    /**
     * Set to true if this profile is for comparing similar things rather than looking for time break-down.
     * The default is to look by time break-down, false.  This only affects the print-out.
     */
    public void setIsComparison(boolean val) {
        mIsComparison = val;
    }

    public boolean isComparison() {
        return mIsComparison;
    }

    private static final Statistics[] NULL_STATS_ARRAY = new Statistics[0];

    /**
     * Returns the current statistical snapshot (for a given thread):
     */
    public Statistics computeStatistics() {
        if (mNested) {
            return ((Profile)(mPieces.get(0))).computeStatistics(mName,mCatName);
        }
        return computeStatistics(mName,mCatName);
    }

    private Statistics computeStatistics(String name, String catName) {
        ThreadLocal tl = mThreadLocal;
        if (tl==null) {
            return blankStatistics();
        }
        Record r = (Record) mThreadLocal.get();
        if (r==null) {
            return blankStatistics();
        }
        Statistics[] c;
        if (mPieces==null) {
            c = NULL_STATS_ARRAY;
        } else {
            c = new Statistics[mPieces.size()];
            for (int i=0;i<c.length;i++) {
                c[i] = ((Profile) mPieces.get(i)).computeStatistics();
            }
        }
        Statistics stats = new Statistics(
            name,
            catName,
            r.invokeCount,
            r.min,
            r.max,
            r.totalTime,
            r.timeSquare,
            c
        );
        return stats;
    }

    private Statistics blankStatistics() {
        return new Statistics(
            mName,
            mCatName,
            0,
            0,
            0,
            0,
            0,
            NULL_STATS_ARRAY);
    }

    public void addProfile(Profile piece) {
        if (mNested) {
            throw new IllegalStateException("Can't add to a profile when it has a nested profile");
        }
        if (mLocked) {
            throw new IllegalStateException("Can't add to profile once enabled/disabled");
        }
        if (mPieces==null) {
            mPieces = new ArrayList();
        }
        if (piece==null) {
            throw new NullPointerException("Null profile");
        }
        mPieces.add(piece);
    }

    public void setEnabled(boolean val) {
        lock();
        if (mPieces!=null) {
            for (int i=0;i<mPieces.size();i++) {
                ((Profile) mPieces.get(i)).setEnabled(val);
            }
        }
        if (val) {
            if (mThreadLocal==null) {
                mThreadLocal = new ThreadLocal();
            }
        } else {
            mThreadLocal = null;
        }
    }

    public void start() {
        ThreadLocal tl = mThreadLocal; // get in local var. so enabled check is quick.
        if (tl==null) { // (don't have to synchronize)
            return;
        }
        Record rec = (Record) tl.get();
        if (rec==null) {
            rec = new Record();
            mThreadLocal.set(rec);
        }
        rec.startTime = System.currentTimeMillis();
        rec.started = true;
    }

    public void stop() {
        ThreadLocal tl = mThreadLocal; // get in local var.
        if (tl==null) {
            // not enabled.
            return;
        }
        Record rec = (Record) tl.get();
        if (rec==null) {
            return; // internal error...
        }
        if (!rec.started) {
            // two stops in a row... ugh.
            return;
        }
        rec.started = false;
        rec.invokeCount++;
        long time = System.currentTimeMillis()-rec.startTime;
        if (time<rec.min) {
            rec.min = time;
        }
        if (time>rec.max) {
            rec.max = time;
        }
        rec.totalTime += time;
        rec.timeSquare += time*time;
    }

    private void lock() {
        mLocked = true;
    }
}
