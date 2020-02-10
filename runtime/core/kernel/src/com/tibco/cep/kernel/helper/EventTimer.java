package com.tibco.cep.kernel.helper;

import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.rule.Action;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 15, 2006
 * Time: 1:54:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventTimer {
    private TaskQueue queue = new TaskQueue();
    private TimerThread thread = new TimerThread(this, queue);
    static int PURGE_EVERY_CANCEL = 1000;
    Action m_timerContext;

    private Object threadReaper = new Object() {
        protected void finalize() throws Throwable {
            synchronized(queue) {
                thread.newTasksMayBeScheduled = false;
                queue.notify(); // In case queue is empty.
            }
        }
    };

    public EventTimer(String name) {
        thread.setName(name + ".EventTimer");
    }

    public void start(Action timerContext) {
        thread.start(timerContext);
    }

    public void shutdown() {
        thread.done = true;
        thread.interrupt();
    }

    public void schedule(AbstractEventHandle task, long time) {
        synchronized(queue) {
            if (!thread.newTasksMayBeScheduled)
                throw new IllegalStateException("EventTimer already cancelled.");

//            synchronized(task.lock) {
            task.setExpiryTime(System.currentTimeMillis() + time);
//            }

            queue.add(task);
            if (queue.getMin() == task)
                queue.notify();
        }
    }

    public void cancel() {
        synchronized(queue) {
            thread.newTasksMayBeScheduled = false;
            queue.clear();
            queue.notify();  // In case queue was already empty.
        }
    }

     public int purge() {
         int result = 0;

         synchronized(queue) {
             for (int i = queue.size(); i > 0; i--) {
                 if (queue.get(i).isTimerCancelled()) {
                     queue.quickRemove(i);
                     result++;
                 }
             }

             if (result != 0)
                 queue.heapify();
         }

         return result;
     }

    class TimerThread extends Thread {
        boolean newTasksMayBeScheduled = true;
        private EventTimer timer;
        private int numCancelled;
        Action m_timerContext;
        boolean done;
        TaskQueue queue;

        TimerThread(EventTimer eventTimer, TaskQueue queue) {
            this.queue = queue;
            this.timer = eventTimer;
        }

        public void start() {
            throw new RuntimeException("ProgramError: use start(Action timerContext) instead of start()");
        }

        public void start(Action timerContext) {
            m_timerContext = timerContext;
            super.start();
        }

        public void run() {
            done = false;
            try {
                if(m_timerContext != null) {
                    m_timerContext.execute(null);
                    m_timerContext = null;
                }
                mainLoop();
            } finally {
                // Someone killed this Thread, behave as if Timer cancelled
                synchronized(queue) {
                    newTasksMayBeScheduled = false;
                    queue.clear();  // Eliminate obsolete references
                }
            }
        }

        /**
         * The main timer loop.  (See class comment.)
         */
        private void mainLoop() {
            while (!done) {
                try {
                    AbstractEventHandle task;
                    boolean taskFired;
                    synchronized(queue) {
                        // Wait for queue to become non-empty
                        while (queue.isEmpty() && newTasksMayBeScheduled)
                            queue.wait();
                        if (queue.isEmpty())
                            break; // Queue is empty and will forever remain; die

                        // Queue nonempty; look at first evt and do the right thing
                        long currentTime, executionTime;
                        task = queue.getMin();
//                    synchronized(task.lock) {
                        if (task.isTimerCancelled()) {
                            queue.removeMin();
                            if(numCancelled++ == EventTimer.PURGE_EVERY_CANCEL ) {
                                queue.purge();
                                numCancelled = 0;
                            }
                            continue;  // No action required, poll queue again
                        }
                        currentTime = System.currentTimeMillis();
                        executionTime = task.getExpiryTime();
                        if (taskFired = (executionTime<=currentTime)) {
                            queue.removeMin();
                        }
//                    }
                        if (!taskFired)// Task hasn't yet fired; wait
                            queue.wait(executionTime - currentTime);
                    }
                    if (taskFired) { // Task fired; run it, holding no locks
                        ((WorkingMemoryImpl)task.getWorkingMemory()).expireEvent(task);
                        task.timerFired();
                    }
                } catch(InterruptedException e) {
                    if(!done) e.printStackTrace();
                    else break;
                }
            }
        }
    }



    class TaskQueue {

        private AbstractEventHandle[] queue = new AbstractEventHandle[128];
        private int size = 0;

        int size() {
            return size;
        }

        void add(AbstractEventHandle task) {
            if (++size == queue.length) {
                AbstractEventHandle[] newQueue = new AbstractEventHandle[2*queue.length];
                System.arraycopy(queue, 0, newQueue, 0, size);
                queue = newQueue;
            }

            queue[size] = task;
            fixUp(size);
        }

        AbstractEventHandle getMin() {
            return queue[1];
        }

        AbstractEventHandle get(int i) {
            return queue[i];
        }

        void removeMin() {
            queue[1] = queue[size];
            queue[size--] = null;
            fixDown(1);
        }

        void quickRemove(int i) {
            assert i <= size;

            queue[i] = queue[size];
            queue[size--] = null;
        }

        void rescheduleMin(long newTime) {
            queue[1].setExpiryTime(newTime);
            fixDown(1);
        }

        boolean isEmpty() {
            return size==0;
        }

        void clear() {
            for (int i=1; i<=size; i++)
                queue[i] = null;

            size = 0;
        }

        private void fixUp(int k) {
            while (k > 1) {
                int j = k >> 1;
                if (queue[j].getExpiryTime() <= queue[k].getExpiryTime())
                    break;
                AbstractEventHandle tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
                k = j;
            }
        }

        private void fixDown(int k) {
            int j;
            while ((j = k << 1) <= size && j > 0) {
                if (j < size &&
                    queue[j].getExpiryTime() > queue[j+1].getExpiryTime())
                    j++; // j indexes smallest kid
                if (queue[k].getExpiryTime() <= queue[j].getExpiryTime())
                    break;
                AbstractEventHandle tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
                k = j;
            }
        }

        void heapify() {
            for (int i = size/2; i >= 1; i--)
                fixDown(i);
        }

        int purge() {
            int result = 0;
            for (int i = size; i > 0; i--) {
                if (queue[i].isTimerCancelled()) {
                    quickRemove(i);
                    result++;
                }
            }
            if (result != 0)
                heapify();
            return result;
        }

    }
}
