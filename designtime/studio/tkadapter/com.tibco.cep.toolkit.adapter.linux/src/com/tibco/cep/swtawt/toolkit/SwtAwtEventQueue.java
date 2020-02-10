package com.tibco.cep.swtawt.toolkit;

import org.eclipse.swt.graphics.Device;

import java.awt.*;
import java.awt.event.InvocationEvent;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 18, 2009
 * Time: 5:23:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwtAwtEventQueue extends EventQueue {

    public SwtAwtEventQueue() {

    }
//    public void postEvent(final AWTEvent theEvent) {
//        //System.out.println("Posting called..." + theEvent);
//        String threadName = Thread.currentThread().getName();
//        System.out.println("Thread-Name:" + threadName);
//        if (threadName.startsWith("AWT")) {
//            synchronized(Device.class) {
//                postEventPrivate(theEvent);
//            }
//          //  System.out.println("Posting done...");
//        }
//        else {
//          postEventPrivate(theEvent);
//            //System.out.println("NON LOCKING - Posting done...");
//        }
//    }

    protected void dispatchEvent(AWTEvent event) {
        //System.out.println("Calling dispatch event-" + event);
        synchronized(Device.class) {
            super.dispatchEvent(event);    
        }
    }

    protected void postEventPrivate(final AWTEvent theEvent) {
        if (theEvent instanceof InvocationEvent) {
            final InvocationEvent ie = new InvocationEvent(theEvent.getSource(), new DelegateEventRunner((InvocationEvent)theEvent));
            super.postEvent(ie);
            return;
        }
        super.postEvent(theEvent);

    }

    class DelegateEventRunner  implements Runnable
    {

        InvocationEvent ie;
        public DelegateEventRunner(InvocationEvent ie) {
            this.ie = ie;
        }
        public void run() {
            synchronized(Device.class) {
                //System.out.println("dispatching the actual runnable event-" + ie);
                ie.dispatch();
            }
        }
    }
}
