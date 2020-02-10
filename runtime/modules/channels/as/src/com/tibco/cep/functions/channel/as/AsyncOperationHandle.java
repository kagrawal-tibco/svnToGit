package com.tibco.cep.functions.channel.as;

import com.tibco.as.space.SpaceResult;
import com.tibco.as.space.SpaceResultHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* Author: Suresh Subramani / Date: 10/16/12 / Time: 5:29 PM
*/

public class AsyncOperationHandle implements SpaceResultHandler {
    int nosofRequest = 0;
    int nosofReplies = 0;
    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();
    List<CallbackResult> callbackResults = new ArrayList<CallbackResult>();
    String ruleFnName;

    public AsyncOperationHandle(String ruleFnName) {
        this.ruleFnName = ruleFnName;
    }

    public void finish() {
        try {
        lock.lock();
        while (nosofRequest != nosofReplies) {
            try {
                condition.await();
            } catch (InterruptedException e) {
            }
        }
        } finally {
           lock.unlock();
        }
    }

    public void release() {
        try {
            lock.lock();
            ++nosofReplies;
            if (nosofReplies == nosofRequest) condition.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void acquire() {
        try {
            lock.lock();
            ++nosofRequest;
            if (nosofReplies == nosofRequest) condition.signalAll();
        }
        finally{
            lock.unlock();
        }
    }

    @Override
    public void onComplete(SpaceResult spaceResult, Object closure, SpaceResult.OperationType operationType) {
        callbackResults.add(new CallbackResult(spaceResult, closure));
        release();
    }

    public List<CallbackResult> getCallbackResults() {
        return this.callbackResults;
    }

    public String getRuleFunctionName() {
        return this.ruleFnName;
    }

    class CallbackResult {
        SpaceResult result;
        Object closure;

        public CallbackResult(SpaceResult result, Object closure) {
            this.result = result;
            this.closure = closure;
        }

        public SpaceResult getResult() {
            return result;
        }

        public Object getClosure() {
            return closure;
        }
    }
}
