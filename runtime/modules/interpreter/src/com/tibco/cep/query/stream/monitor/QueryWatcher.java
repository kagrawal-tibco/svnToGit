package com.tibco.cep.query.stream.monitor;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import com.tibco.cep.query.stream.misc.Clock;

/*
 * Author: Ashwin Jayaprakash Date: Nov 28, 2007 Time: 12:28:23 PM
 */

/**
 * <p> <b>Not Thread-safe!</b> </p> <p> Thread-safety is not really a concern here. If the reader
 * wants to view the current data, then there is no synchronization on that and so data could be
 * incomplete. Some of the key fields are however, <code>volatile</code>. </p> <p/> <p>Modifier is
 * always single-threaded. Readers can get stale values because there is not
 * synchronization/volatile use except for the first/next-run references.</p>
 */
public class QueryWatcher {
    /**
     * {@value}
     */
    public static final int MAX_RECENT_RUNS = 50;

    protected ArrayBlockingQueue<Run> recentRuns;

    protected Run currentRun;

    protected long totalRuns;

    protected long totalSuccessfulRuns;

    public QueryWatcher() {
        this.recentRuns = new ArrayBlockingQueue<Run>(MAX_RECENT_RUNS);
    }

    public void discard() {
        currentRun = null;

        recentRuns.clear();
        recentRuns = null;
    }

    public Run initNewRun() {
        currentRun = new Run(this);

        return currentRun;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public Run getCurrentRun() {
        return currentRun;
    }

    protected void endCurrentRun() {
        if (currentRun.getStatus() == Status.SUCCESS) {
            totalSuccessfulRuns++;

            currentRun.setTotalSuccessfulRuns(totalSuccessfulRuns);
        }

        totalRuns++;
        currentRun.setTotalRuns(totalRuns);

        //---------

        if (recentRuns.offer(currentRun) == false) {
            //Remove the oldest element and make room.
            recentRuns.poll();

            //Try again.
            recentRuns.offer(currentRun);
        }

        currentRun = null;
    }

    public Iterator<Run> getRecentRunsIterator() {
        return recentRuns.iterator();
    }

    public Run peekOldestRun() {
        return recentRuns.peek();
    }

    public Run pollOldestRun() {
        return recentRuns.poll();
    }

    // ----------

    public static class Run {
        protected long startTime;

        protected long endTime;

        protected Status status;

        protected Throwable error;

        protected Step firstStep;

        protected Step currentStep;

        protected long totalRuns;

        protected long totalSuccessfulRuns;

        protected QueryWatcher watcher;

        /**
         * Initializes {@link #status} and a provisional {@link #startTime}.
         *
         * @param watcher
         */
        protected Run(QueryWatcher watcher) {
            this.status = Status.IDLE;
            this.startTime = Clock.getCurrentTimeMillis();
            this.watcher = watcher;
        }

        /**
         * @return <code>0</code> if it is not set.
         */
        public long getEndTime() {
            return endTime;
        }

        public Status getStatus() {
            return status;
        }

        public long getStartTime() {
            return startTime;
        }

        public Step getFirstStep() {
            return firstStep;
        }

        public Throwable getError() {
            return error;
        }

        public long getTotalRuns() {
            return totalRuns;
        }

        public long getTotalSuccessfulRuns() {
            return totalSuccessfulRuns;
        }

        public void setTotalSuccessfulRuns(long totalSuccessfulRuns) {
            this.totalSuccessfulRuns = totalSuccessfulRuns;
        }

        public void setTotalRuns(long totalRuns) {
            this.totalRuns = totalRuns;
        }

        /**
         * Sets {@link #startTime}, {@link #status} and {@link #firstStep}.
         *
         * @param resourceId
         * @return
         */
        protected Step recordStart(ResourceId resourceId) {
            firstStep = new Step(resourceId);

            startTime = firstStep.getStartTime();
            status = Status.IN_PROGRESS;

            return firstStep;
        }

        /**
         * <p> Automatically starts the run if this is the first step. </p>
         *
         * @param resourceId
         * @return
         */
        public Step appendNewStep(ResourceId resourceId) {
            Step step = null;

            if (firstStep == null) {
                step = recordStart(resourceId);
            }
            else {
                step = new Step(resourceId);
                currentStep.nextStep = step;
            }

            currentStep = step;

            return step;
        }

        public Step getCurrentStep() {
            return currentStep;
        }

        /**
         * Sets the {@link #endTime} and invokes {@link Step#recordEnd()} on the current Step if
         * there was any.
         *
         * @param throwable <code>null</code> if there was no error and the run completed
         *                  successfully.
         */
        public void recordEnd(Throwable throwable) {
            if (currentStep != null) {
                currentStep.recordEnd();
                endTime = currentStep.getEndTime();
            }
            else {
                endTime = Clock.getCurrentTimeMillis();
            }

            status = throwable == null ? Status.SUCCESS : Status.ERROR;
            error = throwable;

            watcher.endCurrentRun();
            watcher = null;

            currentStep = null;
        }
    }

    // ----------

    public static class Step {
        protected final ResourceId resourceId;

        protected final long startTime;

        protected long endTime;

        protected Step nextStep;

        /**
         * Initializes {@link #startTime} and {@link #resourceId}.
         *
         * @param resourceId
         */
        protected Step(ResourceId resourceId) {
            this.resourceId = resourceId;
            this.startTime = Clock.getCurrentTimeMillis();
        }

        /**
         * @return Can be <code>0</code> if this step did not complete successfully.
         */
        public long getEndTime() {
            return endTime;
        }

        public ResourceId getResourceId() {
            return resourceId;
        }

        public long getStartTime() {
            return startTime;
        }

        /**
         * Sets the {@link #endTime}.
         */
        public void recordEnd() {
            this.endTime = Clock.getCurrentTimeMillis();
        }

        public Step getNextStep() {
            return nextStep;
        }

        public void setNextStep(Step nextStep) {
            this.nextStep = nextStep;
        }
    }

    // ----------

    public static enum Status {
        SUCCESS, ERROR, IN_PROGRESS, IDLE
    }
}
