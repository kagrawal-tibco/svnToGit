package com.tibco.cep.query.client.console.swing.util;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author ksubrama
 */
public class SwingUtil {

    private SwingUtil() {
    }

    public static SwingWorker<Object, Object> runInBackground(final Work work) {
        SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>(){

            @Override
            protected Object doInBackground() throws Exception {
                if(work instanceof FireAndForgetWork) {
                    ((FireAndForgetWork)work).doWork();
                    return null;
                } else if(work instanceof ResultProducingWork) {
                    return ((ResultProducingWork)work).doWork();
                }
                return null;
            }
        };
        worker.execute();
        return worker;
    }

    public static void runInEDT(final FireAndForgetWork work) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                work.doWork();
            }
        });
    }


    public static interface Work {        
    }

    public static interface FireAndForgetWork extends Work {
        void doWork();
    }

    public static interface ResultProducingWork extends Work {
        Object doWork();
    }
}
