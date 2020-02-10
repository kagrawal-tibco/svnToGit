package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.tuple.HeavyTuple;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Author: Ashwin Jayaprakash Date: Dec 19, 2007 Time: 5:10:30 PM
 */

public abstract class TrackedTuple extends HeavyTuple {
    protected static TestSession testSession;

    protected final AtomicInteger refCount;

    public TrackedTuple(Number id) {
        this(id, null);
    }

    public TrackedTuple(Number id, Object[] columns) {
        super(id, columns);

        this.refCount = new AtomicInteger(1);

        if (id != null && testSession != null) {
            testSession.getTuples().put(id, this);
        }
    }

    //-----------

    public final void incrementRefCount() {
        refCount.incrementAndGet();
    }

    public final int getRefCount() {
        return refCount.get();
    }

    public final void decrementRefCount() {
        if (refCount.decrementAndGet() == 0) {
            discard();
        }
    }

    //-----------

    public static TestSession getTestSession() {
        return testSession;
    }

    public static void setTestSession(TestSession testSession) {
        TrackedTuple.testSession = testSession;
    }
}
