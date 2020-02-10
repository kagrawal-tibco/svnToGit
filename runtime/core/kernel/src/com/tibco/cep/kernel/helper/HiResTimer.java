package com.tibco.cep.kernel.helper;

import java.util.Date;

import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 27, 2006
 * Time: 8:53:29 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * A time notification tool that takes advantage of the fact
 * all scheduling and execution comes through a single locked
 * WorkingMemory object.
 *
 * In particular, this locks the WorkingMemory while executing as many tasks
 * as it can in an effort to minimize timer drift in environments where
 * contention for the WorkingMemory lock is high.  The danger is that this
 * timer may 'hog' the log and prevent the other Engine threads from executing.
 * BE users should take care when using too many time sensitive operations.
 *
 * All synchronization from the java.util.Timer class now goes through
 * the WorkingMemory object used by this class.  For all methods that
 * require synchronization, and are invoked by the user, it is assumed
 * the caller will properly synchronize on the appropriate WorkingMemory
 * instance.
 *
 * @author ishaan
 * @version Jan 26, 2006, 6:48:15 PM
 */
public class HiResTimer {
    /**
     * The Object for which this timer operates.  It is used
     * for invoking actions as well as a synchronization lock
     */
    private WorkingMemory m_wm;

    /**
     * The maximum length of time for which to hold a lock on the
     * lock object without releasing it (sort of).
     */
    private long m_maxWMLockTimeMillis;

    /**
     * Used only for debugging
     */
    public int numTasksFiredLastCycle = 0;

    /**
     * The timer task m_queue.  This data structure is shared with the timer
     * m_thread.  The timer produces tasks, via its various schedule calls,
     * and the timer m_thread consumes, executing timer tasks as appropriate,
     * and removing them from the m_queue when they're obsolete.
     */
    private HiResTaskQueue m_queue    = new HiResTaskQueue();
    private HiResTimerThread m_thread = new HiResTimerThread(m_queue);


    public HiResTimer(WorkingMemory workingMemory, long timeToHoldLockMillis) {
        m_wm = workingMemory;
        m_maxWMLockTimeMillis = timeToHoldLockMillis;
        m_thread.setName(workingMemory.getName() + ".HiResTimer");
    }

    public void start(Action timerContext) {
        m_thread.start(timerContext);
    }

    public void shutdown() {
        m_thread.done = true;
        m_thread.interrupt();
    }


    private Object finalizer = new Object() {
        protected void finalize() throws Throwable {
            synchronized(m_queue) {
                m_thread.newTasksMayBeScheduled = false;
                m_queue.notifyAll(); // In case m_queue is empty.
            }
        }
    };

    public void scheduleOnceOnly(TimerTaskOnce task, long delay) {
        if(delay < 0) {
            throw new IllegalArgumentException("Negative delay.");
        }

        schOnceOnly(task, System.currentTimeMillis()+delay, 0);
    }


    public void scheduleOnceOnly(TimerTaskOnce task, Date time) {
        schOnceOnly(task, time.getTime(), 0);
    }

    public void scheduleRepeat(TimerTaskRepeat task, long delay, long period) {
        if(delay < 0) {
            throw new IllegalArgumentException("Negative delay.");
        }

        if(period <= 0) {
            throw new IllegalArgumentException("Non-positive period.");
        }

        schRepeat(task, System.currentTimeMillis()+delay, -period);
    }

    public void scheduleRepeat(TimerTaskRepeat task, Date firstTime, long period) {
        if(period <= 0) {
            throw new IllegalArgumentException("Non-positive period.");
        }

        schRepeat(task, firstTime.getTime(), -period);
    }

    public void scheduleAtFixedRate(TimerTaskRepeat task, long delay, long period) {
        if(delay < 0) {
            throw new IllegalArgumentException("Negative delay.");
        }

        if(period <= 0) {
            throw new IllegalArgumentException("Non-positive period.");
        }

        schRepeat(task, System.currentTimeMillis()+delay, period);
    }

    public void scheduleAtFixedRate(TimerTaskRepeat task, Date firstTime, long period) {
        if(period <= 0) {
            throw new IllegalArgumentException("Non-positive period.");
        }

        schRepeat(task, firstTime.getTime(), period);
    }

    private void schRepeat(TimerTaskRepeat task, long time, long period) {
        if(time < 0) {
            throw new IllegalArgumentException("Illegal execution time.");
        }
        synchronized(m_queue) {
            if (!m_thread.newTasksMayBeScheduled) {
                throw new IllegalStateException("Timer already cancelled.");
            }

            if (task.state != HiResTimerTask.VIRGIN) {
                throw new IllegalStateException("Task already scheduled or cancelled");
            }

            task.nextExecutionTime = time;
            task.period = period;
            task.state = HiResTimerTask.SCHEDULED;

            m_queue.add(task);
            if(m_queue.getMin() == task) {
                m_queue.notifyAll();
            }
        }
    }

    private void schOnceOnly(TimerTaskOnce task, long time, long period) {
        if(time < 0) {
            throw new IllegalArgumentException("Illegal execution time.");
        }
        synchronized(m_queue){
            if (!m_thread.newTasksMayBeScheduled) {
                throw new IllegalStateException("Timer already cancelled.");
            }

            if (task.state != HiResTimerTask.VIRGIN) {
                throw new IllegalStateException("Task already scheduled or cancelled");
            }

            task.nextExecutionTime = time;
            task.state = HiResTimerTask.SCHEDULED;

            m_queue.add(task);
            if(m_queue.getMin() == task) {
                m_queue.notifyAll();
            }
        }
    }


    public void cancel() {
        synchronized(m_queue) {
            m_thread.newTasksMayBeScheduled = false;
            m_queue.clear();
            m_queue.notifyAll();
        }
    }

    class HiResTimerThread extends Thread {
        boolean newTasksMayBeScheduled = true;
        private HiResTaskQueue queue;
        Action m_timerContext;
        boolean done;


        HiResTimerThread(HiResTaskQueue queue) {
            this.queue = queue;
        }

        public void start() {
            throw new RuntimeException("ProgramError: use start(Action timerContext) instead of start()");
        }

        public void start(Action timerContext) {
            m_timerContext = timerContext;
            super.start();
        }

        public void run() {
            try {
                if(m_timerContext != null) {
                    m_timerContext.execute(null);
                    m_timerContext = null;
                }
                mainLoop();
            }
            finally {
                synchronized(m_queue) {
                    newTasksMayBeScheduled = false;
                    queue.clear();
                }
            }
        }

        private void mainLoop() {
            done = false;
            while(!done) {
                try {
                    HiResTimerTask task=null;
                    boolean taskFired;

                    synchronized(queue) {
                        // execute as many actions as allowed
                        // while we hold the m_wmLock lock.
                        numTasksFiredLastCycle = 0;
                        long releaseWMLockTime = System.currentTimeMillis() + m_maxWMLockTimeMillis;
                        while(System.currentTimeMillis() <= releaseWMLockTime) {
                            task=null;
                            while(queue.isEmpty() && newTasksMayBeScheduled) {
                                queue.wait();
                                releaseWMLockTime = System.currentTimeMillis() + m_maxWMLockTimeMillis;
                            }
                            if(queue.isEmpty()) {
                                done = true;
                                break;
                            }

                            // Queue nonempty; look at first evt and do the right thing
                            long currentTime, executionTime;
                            task = queue.getMin();
                            if(task.state == HiResTimerTask.CANCELLED) {
                                queue.removeMin();
                                continue;
                            }
                            currentTime = System.currentTimeMillis();
                            executionTime = task.nextExecutionTime;
                            if(taskFired = (executionTime <= currentTime)) {
                                if (task instanceof TimerTaskOnce) {
                                    queue.removeMin();
                                    task.state = HiResTimerTask.EXECUTED;
                                }
                                else {
                                    queue.rescheduleMin(((TimerTaskRepeat)task).period < 0 ? (currentTime - ((TimerTaskRepeat)task).period) : (executionTime + ((TimerTaskRepeat)task).period));
                                }
                            }
                            if(!taskFired) {
                                queue.wait(executionTime - currentTime);
                                releaseWMLockTime = System.currentTimeMillis() + m_maxWMLockTimeMillis;
                                task=null;
                            }
                            else {  // run task while the wm is locked
                                //task.execute(m_wm);
                                break;
                            }
                        }
                    }
                    if (task != null) {
                        task.execute(m_wm);
                        task=null;
                    }
                } catch (InterruptedException e) {
                    if(!done) e.printStackTrace();
                    else break;
                }
            }
        }
    }

    class HiResTaskQueue {
        private HiResTimerTask[] m_queue = new HiResTimerTask[128];
        private int size = 0;

        void add(HiResTimerTask task) {
            if(++size == m_queue.length) {
                HiResTimerTask[] newQueue = new HiResTimerTask[2*m_queue.length];
                System.arraycopy(m_queue, 0, newQueue, 0, size);
                m_queue = newQueue;
            }

            m_queue[size] = task;
            fixUp(size);
        }

        HiResTimerTask getMin() {
            return m_queue[1];
        }

        void removeMin() {
            m_queue[1] = m_queue[size];
            m_queue[size--] = null;
            fixDown(1);
        }

        void rescheduleMin(long newTime) {
            m_queue[1].nextExecutionTime = newTime;
            fixDown(1);
        }

        boolean isEmpty() {
            return size==0;
        }

        void clear() {
            for(int i=1; i<=size; i++) {
                m_queue[i] = null;
            }
            size = 0;
        }

        private void fixUp(int k) {
            while(k > 1) {
                int j = k >> 1;
                if(m_queue[j].nextExecutionTime <= m_queue[k].nextExecutionTime) {
                    break;
                }
                HiResTimerTask tmp = m_queue[j];  m_queue[j] = m_queue[k]; m_queue[k] = tmp;
                k = j;
            }
        }

        private void fixDown(int k) {
            int j;
            while((j = k << 1) <= size) {
                if(j < size && m_queue[j].nextExecutionTime > m_queue[j+1].nextExecutionTime) {
                    j++;
                }
                if(m_queue[k].nextExecutionTime <= m_queue[j].nextExecutionTime) {
                    break;
                }
                HiResTimerTask tmp = m_queue[j];  m_queue[j] = m_queue[k]; m_queue[k]   = tmp;
                k = j;
            }
        }
    }
}