package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;

/**
 * A generic wrapper around the a component that allows some 'loading' computation to be done before displaying.<br>
 * While loading (on a background thread), it displays a messages.
 */
public class LazyComponentLoader extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_isLoaded;
    private boolean m_isShown;
    private LoadRunner m_loadRunner;

    public LazyComponentLoader()
    {
        super(new BorderLayout());
    }

    public boolean isLoaded()
    {
        synchronized (this)
        {
            return m_isLoaded;
        }
    }

    /**
     * Immediately sets the component.<br>
     * This, of course, is against the purpose of the whole class, but for simple displays, such as an error,
     * where there is no appreciable loading time, this is a shortcut.
     * @param component The component to display
     */
    public void setComponent(JComponent component)
    {
        final JComponent fcomponent = component;
        setComponentLoading(new LazyComponentLoaderRunnable()
        {
            public Component run(CancelChecker cancel)
            {
                return fcomponent;
            }
        });
    }

    /**
     * Asynchronously loads the runnable, does not wait for previous load to finish.
     * @param r
     */
    public void setComponentLoading(final LazyComponentLoaderRunnable r)
    {
        synchronized (this)
        {
            if (m_loadRunner!=null)
            {
                m_loadRunner.cancel();
            }
        }
        m_loadRunner = null;
        m_isLoaded = false;
        m_isShown = false;

        m_loadRunner = new LoadRunner(r);
        Thread lazyLoadThread = new Thread(m_loadRunner,"LazyLoadComponentLoader");
        lazyLoadThread.setPriority(Thread.MIN_PRIORITY); // don't hog CPU.
        lazyLoadThread.start();
        // Wait this long to see if it can work synchronously.
        int waitSynchronousTime = 100;
        synchronized (this)
        {
            try
            {
                wait(waitSynchronousTime);
            }
            catch (InterruptedException ie)
            {
                // eat it.
            }
        }
        synchronized (this)
        {
            if (!m_isLoaded)
            {
                showWaiting();
            }
        }
    }

    class LoadRunner extends DefaultCancelChecker implements Runnable
    {
        private LazyComponentLoaderRunnable m_r;

        public LoadRunner(LazyComponentLoaderRunnable r)
        {
            m_r = r;
            setNiceMode(true); // make the gui less sluggish.
        }

        public void run()
        {
            synchronized (LazyComponentLoader.this)
            {
                m_isLoaded = false;
            }
            Component result;
            try
            {
                result = m_r.run(this);
            }
            catch (Throwable t)
            {
                t.printStackTrace(System.err); // shouldn't happen, but if it does, print out here; otherwise deadlock...
                result = null;
            }
            final Component fresult = result;
            synchronized (LazyComponentLoader.this)
            {
                if (hasBeenCancelled())
                {
                    LazyComponentLoader.this.notify();
                }
                else
                {
                    m_isLoaded = true;
                    LazyComponentLoader.this.notify();
                }
            }
            if (!hasBeenCancelled())
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        synchronized (LazyComponentLoader.this)
                        {
                            if (!hasBeenCancelled())
                            {
                                Component tr = fresult;
                                if (tr==null)
                                {
                                    tr = new JLabel("Null returned.");
                                }
                                showNow(tr);
                            }
                            // in case someone is waiting for us to finish.
                            LazyComponentLoader.this.notify();
                        }
                    }
                });
            }
        }
    }

    private synchronized void showNow(Component c)
    {
        if (m_isShown)
        {
            return;
        }
        m_isShown = true;
        removeAll();
        add(c);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        revalidate();
        repaint();
    }

    private void showWaiting()
    {
        removeAll();
        add(new JLabel("Loading..."));
        revalidate();
        repaint();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
}
