package com.tibco.cep.studio.ui.util;

import java.awt.AWTEvent;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;



class SwingDialogListener implements AWTEventListener, ComponentListener {
    
    @SuppressWarnings("unchecked")
	private final List modalDialogs = new ArrayList();
    private final Display display;
    
    SwingDialogListener(Display display) {
        assert display != null;
        
        this.display = display;
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.WINDOW_EVENT_MASK);
    }
    
    /**
     * @param awtDialog
     * @param removeListener
     */
    private void handleRemovedDialog(Dialog awtDialog, boolean removeListener) {
        assert awtDialog != null;
        assert modalDialogs != null;
        assert display != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        
        if (removeListener) {
            awtDialog.removeComponentListener(this);
        }
        if (modalDialogs.remove(awtDialog)) {
            display.asyncExec(new Runnable() {
                public void run() {
                    DisplayBlocker.unblock();
                }
            });            
        }
    }

    /**
     * @param awtDialog
     */
    private void handleAddedDialog(final Dialog awtDialog) {
        assert awtDialog != null;
        assert modalDialogs != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        if (modalDialogs.contains(awtDialog) || !awtDialog.isModal() || !awtDialog.isVisible()) {
            return;
        }
        modalDialogs.add(awtDialog);
        awtDialog.addComponentListener(this);
        display.asyncExec(new Runnable() {
            public void run() {
                DisplayBlocker.block();
            }
        });        
    }
    
    void requestFocus() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                assert modalDialogs != null;
                
                int size = modalDialogs.size();
                if (size > 0) {
                	final Dialog awtDialog = (Dialog)modalDialogs.get(size - 1);
                	if(awtDialog.isModal()){
                		awtDialog.requestFocus();
                		awtDialog.toFront();
                	}
                }
            }
        });
    }

    /**
     * @param event
     */
    private void handleOpenedWindow(WindowEvent event) {
        assert event != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        
        Window window = event.getWindow();
        if (window instanceof Dialog) {
            handleAddedDialog((Dialog)window);
        }
    }
    
    /**
     * @param event
     */
    private void handleClosedWindow(WindowEvent event) {
        assert event != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        
        // Dispose-based close
        Window window = event.getWindow();
        if (window instanceof Dialog) {
            // Remove dialog and component listener
            handleRemovedDialog((Dialog)window, true);
        }
    }

    /**
     * @param event
     */
    private void handleClosingWindow(WindowEvent event) {
        assert event != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        
        // System-based close 
        Window window = event.getWindow();
        if (window instanceof Dialog) {
            final Dialog dialog = (Dialog) window;
            // Defer until later. Bad things happen if 
            // handleRemovedDialog() is called directly from 
            // this event handler. The Swing dialog does not close
            // properly and its modality remains in effect.
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    // Remove dialog and component listener
                    handleRemovedDialog(dialog, true);
                }
            });
        }
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.AWTEventListener#eventDispatched(java.awt.AWTEvent)
     */
    public void eventDispatched(AWTEvent event) {
        assert event != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        
        switch (event.getID()) {
        case WindowEvent.WINDOW_OPENED:
            handleOpenedWindow((WindowEvent)event);
            break;
            
        case WindowEvent.WINDOW_CLOSED:
            handleClosedWindow((WindowEvent)event);
            break;

        case WindowEvent.WINDOW_CLOSING:
            handleClosingWindow((WindowEvent)event);
            break;

        default:
            break;
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
     */
    public void componentHidden(ComponentEvent e) {
        assert e != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        Object obj = e.getSource();
        if (obj instanceof Dialog) {
            // Remove dialog but keep listener in place so that we know if/when it is set visible
            handleRemovedDialog((Dialog)obj, false);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
     */
    public void componentShown(ComponentEvent e) {
        assert e != null;
        assert EventQueue.isDispatchThread();    // On AWT event thread
        Object obj = e.getSource();
        if (obj instanceof Dialog) {
            handleAddedDialog((Dialog)obj);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
     */
    public void componentResized(ComponentEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
     */
    public void componentMoved(ComponentEvent e) {
    }
        
}
