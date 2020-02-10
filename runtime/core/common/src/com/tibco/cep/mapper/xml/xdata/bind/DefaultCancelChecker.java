package com.tibco.cep.mapper.xml.xdata.bind;



/**
 * This needs a better home, but it is a simple interface for checking if an operation has been cancelled.
 */
public class DefaultCancelChecker implements CancelChecker
{
    private boolean m_cancelled;
    private boolean m_niceMode;
    private long m_lastTime = -1;

    public DefaultCancelChecker()
    {
    }

    /**
     * Nice mode makes the thread sleep approx. 30% of the time to make the GUI more responsive -- windows threading stinks.<br>
     * This is because, on a super-machine, in 2003, even a thread running on minimum priority made the GUI noticably
     * sluggish.  Unbelievable!.<br>
     * Default is off (false).
     * @param nice If the nice mode is on (true) or off (false).
     */
    public void setNiceMode(boolean nice)
    {
        m_niceMode = nice;
    }

    /**
     * Sets the cancel flag to true.<br>
     * Since this mechanism is polling based, though, the actual operation will continue on until it explicitly checks
     * {@link #hasBeenCancelled}.
     */
    public void cancel()
    {
        synchronized (this)
        {
            m_cancelled = true;
        }
    }

    public boolean hasBeenCancelled()
    {
        if (m_cancelled)
        {
            return true;
        }
        if (m_niceMode)
        {
            // In 'nice' mode, will sleep approx 30 percent of the time.
            long t = System.currentTimeMillis();
            if (m_lastTime<0)
            {
                m_lastTime = t;
            }
            if (t>m_lastTime+10)
            {
                int sleepTime = 4;
                try
                {
                    Thread.currentThread().sleep(sleepTime);
                }
                catch (InterruptedException ie)
                {
                    // ignore it.
                }
                m_lastTime = System.currentTimeMillis();
            }
        }
        synchronized (this)
        {
            return m_cancelled;
        }
    }
}

